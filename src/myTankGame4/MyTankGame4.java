/*
 *坦克游戏2.0

 *1,。画出坦克
 *2.我的坦克可以上下移动
 *3.可以连发子弹，子弹连发5发
 * 4.当我的坦克击中敌人是，敌人就消失（爆炸的效果）
 * 5.我被击中后，显示爆炸效果
 * 6.放置敌人坦克重叠运动
 * 		6.1决定吧判断是否碰撞的函数写到EnemyTank类
 * 7.可以分关
 * 		7.1做一个开始的Panel，她是一个空的
 * 		7.2闪烁效果
 * 8.可以再玩游戏的时候暂停和继续
 * 		8.1当用户暂停时，子弹的速度和坦克速度设为0，并让坦克的方向不要变化
 * 9.可以记录玩家的成绩
 * 		9.1用文件流
 * 		9.2单写一个类,完成对玩家的记录
 * 		9.3 明天做玩家的死亡次数
 * 10.java如何操作声音文件
 */
package myTankGame4;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Color;
import java.util.*;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;
public class MyTankGame4 extends JFrame implements ActionListener{
	
	MyPanel mp=null;
	
	//做出我需要的菜单
	JMenuBar jmb=null;
	//开始游戏
	JMenu jm1=null;
	JMenuItem jmil=null;
	JMenuItem jmi2=null;
	
	//定义一个开始的Panel
	MyStartPanel msp=null;
	public static void main(String[] args)
	{
		MyTankGame4 mtg=new MyTankGame4();
	}

	public MyTankGame4()
	{
		
		//mp=new MyPanel();
		
		//this.add(mp);
		
		//注册监听
		//this.addKeyListener(mp);
		
		//启动mp线程
		//Thread t=new Thread(mp);
		//t.start();
		
		//创建菜单及菜单选项
		jmb=new JMenuBar();
		jm1=new JMenu("游戏(G)");
		//设置快捷方式
		jm1.setMnemonic('G');
		jmil=new JMenuItem("开始新游戏(N)");
		jmi2=new JMenuItem("游戏暂停(S)");
		
		//对jmil进行相应
		jmil.addActionListener(this);
		jmil.setActionCommand("newgame");
		
		
		//对jmi2进行响应
		jmi2.addActionListener(this);
		jmi2.setActionCommand("Stop");
		
		
		jm1.add(jmil);
		jm1.add(jmi2);
		jmb.add(jm1);
		
		msp=new MyStartPanel();
		msp.setBackground(Color.CYAN);
		Thread t=new Thread(msp);
		t.start();
		
		
		this.setJMenuBar(jmb);
		this.add(msp);
		
		//mp.setBackground(Color.CYAN);
		this.setSize(1000,800);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//启动mp线程
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO 自动生成的方法存根
		//对用户不同的点击作出不同的处理
		if(arg0.getActionCommand().equals("newgame"))
		{
			//创建战场面板
			mp=new MyPanel();
			
			this.add(mp);
			
			//注册监听
			this.addKeyListener(mp);
			mp.setBackground(Color.CYAN);
			
			//启动mp线程
			this.remove(msp);
			Thread t=new Thread(mp);
			t.start();
			//显示 刷新
			this.setVisible(true);
			
		}
		
	}
}

//就是一个提示作用
class MyStartPanel extends JPanel implements Runnable
{
	
	int times=0;
	
	public void paint(Graphics g)
	{
		super.paint(g);
		g.fillRect(0, 0, 400, 300);
		//提示信息
		if(times%2==0)
		{
		g.setColor(Color.yellow);
		//开关信息的字体
		Font myFont=new Font("华文新魏",Font.BOLD,30);
		g.setFont(myFont);
		g.drawString("stage: 1", 150,150 );
		
		}
	}

	@Override
	public void run() {
		// TODO 自动生成的方法存根
		while(true)
		{
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			
			times++;
			
			//重画
			this.repaint();
		}
		
	}
}

//我的面板
class MyPanel extends JPanel implements KeyListener,Runnable
{
	// 定义我的坦克
	Hero hero=null;
	
	//定义敌人的坦克
	
	Vector<EnemyTank> ets=new Vector<EnemyTank>();
	int ensize=8;
	
	//定义炸弹集合
	Vector<Bomb> bombs=new Vector<Bomb>();
	
	//定义三张图片
	Image image1=null;
	Image image2=null;
	Image image3=null;
	Image image4=null;
	Image image5=null;
	Image image6=null;
	
	//胜利图片
	Image image7=null;
	
	EnemyTank et=null;
	
	//构造函数
	public  MyPanel()
	{
		
		
		
		hero=new Hero(500,400);
		
		//初始化敌人的坦克
		for(int i=0;i<ensize;i++)
		{
			//创建一辆敌人的坦克
			et=new EnemyTank((i+1)*50,0);
			et.setColor(0);
			et.setDirect(2);
			
			//将MYPanel的敌人坦克向量交给该敌人坦克
			et.setEts(ets);
			
			//启动敌人的坦克
			Thread t=new Thread(et);
			t.start();
			
			//g给敌人坦克添加一颗子弹
			Shot s=new Shot(et.x+10,et.y+34,2);
			
			//加入给敌人
			et.ss.add(s);
			Thread t2=new Thread(s);
			t2.start();
			
			//加入
			ets.add(et);
			
		}
		
		/*try {

			image2=ImageIO.read(new File("111.jpg"));
			image3=ImageIO.read(new File("444.jpg"));
			image4=ImageIO.read(new File("222.jpg"));
			image5=ImageIO.read(new File("555.jpg"));
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}*/
		
		//初始化图片
		image1=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/666.jpg"));
		image2=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/111.jpg"));
		image3=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/444.jpg"));
		image4=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/222.jpg"));
		image5=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/555.jpg"));
		image7=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/888.jpg"));
	
		
	}
	
	//画出提示信息
	public void showTnfo(Graphics g)
	{
		//画出提示信息坦克（该坦克不参与战斗）
				this.drawTank(100, 550, g, 0, 1);
				g.setColor(Color.black);
				Font myFont=new Font("华文新魏",Font.BOLD,20);
				g.setFont(myFont);
				g.drawString(Recorder.getEnNum()+"", 140, 573);
				this.drawTank(100, 600, g, 0, 0);
				g.setColor(Color.black);
				g.drawString(Recorder.getMyLife()+"", 140, 623);
				
				//画出玩家的总成绩
				g.setColor(Color.black);
				Font f=new Font("宋体",Font.BOLD,20);
				g.setFont(f);
				g.drawString("你的总成绩", 720, 30);
				
				this.drawTank(750, 50, g, 0, 0);
				
				g.setColor(Color.black);
				g.drawString(Recorder.getAllEnNum()+"",790 , 75);
				
				//画出玩家死亡次数
				g.setColor(Color.black);
				Font h=new Font("宋体",Font.BOLD,20);
				g.setFont(h);
				g.drawString("你的死亡次数", 720, 130);
				
				this.drawTank(750, 150, g, 0, 0);
				
				g.setColor(Color.black);
				g.drawString(Recorder.getDeathtime()+"",790 , 175);
				
				
				
				
	}

	
	//先要重写paint 
	public void paint(Graphics g)
	{
		
		super.paint(g);
		g.setColor(Color.black);
		g.fillRect(0, 0, 700, 500);
		
		
		if(Recorder.getEnNum()==0)
		{
			g.drawImage(image7, 200, 200, 200, 200,this);
		}
		
		//画出提示信息
		this.showTnfo(g);
		
		//画出自己的坦克
		if(Recorder.getMyLife()>0)
		{
			this.drawTank(hero.getX(), hero.getY(), g, this.hero.direct, 0); 
		}
		

		
		//画出敌人的坦克
		for(int i=0;i<ets.size();i++)
		{
			EnemyTank et=ets.get(i);
			if(et.isLive)
			{
				this.drawTank(et.getX(),et.getY(), g, et.getDirect(), 1);
			
				//画出敌人的子弹
				for(int j=0;j<et.ss.size();j++)
				{
					//取出子弹
					Shot enemyShot=et.ss.get(j);
					if(enemyShot.isLive)
					{
						g.setColor(Color.red);
						g.draw3DRect(enemyShot.x, enemyShot.y, 2, 2, false);
					}else
					 {
						
						//从ss中删除掉该子弹
						et.ss.remove(enemyShot);
					}
				}
			
			}
		}
		
		//从ss中取出每一个子弹，并绘制（遍历）
		//画出子弹
		for(int i=0;i<this.hero.ss.size();i++)
		{
			//取出一个子弹
			Shot myShot=hero.ss.get(i);
			if(myShot!=null&&myShot.isLive==true)
			{
				g.setColor(Color.yellow);
				g.draw3DRect(myShot.x, myShot.y, 2, 2, false);
			
			}
			if(myShot.isLive==false)
			{
				//从ss中删除掉该子弹
				hero.ss.remove(myShot);
			}
		}
		
		//画出散弹
		for(int i=0;i<this.hero.ms.size();i++)
		{
			//取出一个颗散弹
			MoveShot myShotm1=hero.ms.get(i);
			MoveShot myShotm2=hero.ms.get(i);
			MoveShot myShotm3=hero.ms.get(i);
			
			
			if(myShotm1!=null&&myShotm1.isLive==true)
			{
				g.setColor(Color.green);
				g.draw3DRect(myShotm1.x, myShotm1.y, 2, 2, false);
			
			}
			if(myShotm1.isLive==false)
			{
				//从ms中删除掉该子弹
				hero.ms.remove(myShotm1);
			}
			
			if(myShotm2!=null&&myShotm2.isLive==true)
			{
				g.setColor(Color.green);
				g.draw3DRect(myShotm2.x, myShotm2.y, 2, 2, false);
			
			}
			if(myShotm2.isLive==false)
			{
				//从ms中删除掉该子弹
				hero.ms.remove(myShotm2);
			}
			
			if(myShotm3!=null&&myShotm3.isLive==true)
			{
				g.setColor(Color.green);
				g.draw3DRect(myShotm3.x, myShotm3.y, 2, 2, false);
			
			}
			if(myShotm3.isLive==false)
			{
				//从ms中删除掉该子弹
				hero.ms.remove(myShotm3);
			}
			
		}
		
		
		//画出炸弹
		for(int i=0;i<bombs.size();i++)
		{
			//取出炸弹
			Bomb b=bombs.get(i);
			
			if(b.life>8)
			{
				g.drawImage(image1, b.x, b.y, 40, 40,this);
				System.out.println("fine！");
				
			}else if(b.life>7){
				g.drawImage(image2,  b.x, b.y, 40, 40,this);
				System.out.println("thanks！");
			}else if(b.life>5){
				g.drawImage(image3,  b.x, b.y, 40, 40,this);
				System.out.println("and！");
			}else if(b.life>3){
				g.drawImage(image4,  b.x, b.y, 40, 40,this);
				System.out.println("you！");
			}else{
				g.drawImage(image5,  b.x, b.y, 40, 40,this);
				System.out.println("真香！");
			}
			
			//让b的生命值减小
			b.lifeDown();
			//如果炸弹生命值为0，就该把该炸弹从bombs向量释放
			if(b.life==0)
			{
				bombs.remove(b);
			}
		}
		
	}
	
	//敌人的子弹是否击我
	public void hitme()
	{
		//取出每一个敌人的坦克
		for(int i=0;i<this.ets.size();i++)
		{
			//取出敌人的坦克
			EnemyTank et=ets.get(i);
			
			//取出每一刻子弹
			for(int j=0;j<et.ss.size();j++)
			{
				//取出子弹
				Shot enemyShot=et.ss.get(j);
				if(Recorder.getMyLife()>0)
				{
					this.hitMyTank(enemyShot, hero);
				
				}
			}
		}
	}
	
	
	//判断我的子弹是否击中敌人的坦克
	public void hitEnemyTank()
	{

		//判断是否击中敌人的坦克
		for(int i=0;i<hero.ss.size();i++)
		{
			//取出子弹
			Shot myShot=hero.ss.get(i);
			Shot myShotm1=hero.ms.get(i);
			Shot myShotm2=hero.ms.get(i);
			Shot myShotm3=hero.ms.get(i);
			
			
			
//			//判断子弹是否有效
//			if(myShot.isLive||myShotm1.isLive||myShotm2.isLive||myShotm3.isLive)
//			{
//				//取出每个坦克，与他判断
//				for(int j=0;j<ets.size();j++)
//				{
//					//取出坦克
//					EnemyTank et=ets.get(j);
//					
//					if(et.isLive)
//					{
//						this.hitTank(myShot, et);
//					}
//				}
//			}
	}
		//吓到这里了  ，散弹功能还没完成，上面散弹参数问题还没解决，明天z
		//直接查看为何这里的散弹参数还需重新定义，而我的子弹却不用定义
		
		
	}
	
	//写一个函数专门判断子弹是否集中敌人坦克
	public void hitTank(Shot s,Tank et,MoveShot ms1,MoveShot ms2,MoveShot ms3)
	{
		//判断改坦克的方向
		switch(et.direct)
		{
		//如果敌人坦克的方向是向上或者是向下
		case 0:
		case 2:
			if(s.x>=et.x&&s.x<=et.x+20&&s.y>=et.y&&s.y<=et.y+30)
			{
				//击中
				//子弹死亡
				s.isLive=false;
				
				
				//敌人坦克死亡
				et.isLive=false;
				//减少地底人数量
				Recorder.reduceEnNum();
				//增加我的杀敌记录
				Recorder.addEnNumRec();
				
			
				
				//创建一颗炸弹，放入Vector
				  Bomb b=new Bomb(et.x,et.y);
				  bombs.add(b);
			}else if(ms1.x>=et.x&&ms1.x<=et.x+20&&ms1.y>=et.y&&ms1.y<=et.y+30)
			{
				//击中
				//子弹死亡
				ms1.isLive=false;
				
				
				//敌人坦克死亡
				et.isLive=false;
				//减少地底人数量
				Recorder.reduceEnNum();
				//增加我的杀敌记录
				Recorder.addEnNumRec();
			}else if(ms2.x>=et.x&&ms2.x<=et.x+20&&ms2.y>=et.y&&ms2.y<=et.y+30)
			{
				//击中
				//子弹死亡
				ms2.isLive=false;
				
				
				//敌人坦克死亡
				et.isLive=false;
				//减少地底人数量
				Recorder.reduceEnNum();
				//增加我的杀敌记录
				Recorder.addEnNumRec();
			}else if(ms3.x>=et.x&&ms3.x<=et.x+20&&ms3.y>=et.y&&ms3.y<=et.y+30)
			{
				//击中
				//子弹死亡
				ms3.isLive=false;
				
				
				//敌人坦克死亡
				et.isLive=false;
				//减少地底人数量
				Recorder.reduceEnNum();
				//增加我的杀敌记录
				Recorder.addEnNumRec();
			}
			break;
		case 1:
		case 3:
			if(s.x>=et.x&&s.x<=et.x+30&&s.y>=et.y&&s.y<=et.y+20)
			{
				//击中
				//子弹死亡
				s.isLive=false;
				
				//敌人坦克死亡
				et.isLive=false;
				//减少敌人数量
				Recorder.reduceEnNum();
				//增加我的杀敌记录
				Recorder.addEnNumRec();
				
				
				
				
				//创建一颗炸弹，放入Vector
				  Bomb b=new Bomb(et.x,et.y);
				  bombs.add(b);
			}
			break;
		}
		
	}
	
	//写一个函数专门判断子弹是否集中我的坦克
		public void hitMyTank(Shot s,Tank hero)
		{
			//判断改坦克的方向
			switch(hero.direct)
			{
			//如果我的坦克的方向是向上或者是向下
			case 0:
			case 2:
				if(s.x>=hero.x&&s.x<=hero.x+20&&s.y>=hero.y&&s.y<=hero.y+30)
				{
					//击中
					//子弹死亡
					s.isLive=false;
					
					//我的坦克死亡
					hero.isLive=false;
					//减少生命
					Recorder.reduceHero();
					//增加死亡次数
					Recorder.adddeathtime();
					
					
					//创建一颗炸弹，放入Vector
					  Bomb b=new Bomb(hero.x,hero.y);
					  bombs.add(b);
				}
				break;
			case 1:
			case 3:
				if(s.x>=hero.x&&s.x<=hero.x+30&&s.y>=hero.y&&s.y<=hero.y+20)
				{
					//击中
					//子弹死亡
					s.isLive=false;
					
					//坦克坦克死亡
					hero.isLive=false;
					
					
					Recorder.reduceHero();
					Recorder.adddeathtime();
					
					
					//创建一颗炸弹，放入Vector
					  Bomb b=new Bomb(hero.x,hero.y);
					  bombs.add(b);
				}
				break;
			}
			
		}
		
	
	
	
		
		//画出坦克的函数(扩展)
		public void drawTank(int x,int y,Graphics g,int direct,int type)
		{
			switch(type)
			{
			case 0:
				g.setColor(Color.RED);
				break;
			case 1:
				g.setColor(Color.blue);
				break;
			}
			//判断方向
				switch(direct)
				{
				//向上走
				case 0:
					//颜色
					
					//画出我的坦克(到时再封装成一个函数)
					//1.画出左边的矩形
					g.fill3DRect(x, y,8,35,false );
					//2.画出右边的矩形
					g.fill3DRect(x+17, y,8,35,false );
					//3.中间矩形
					g.fill3DRect(x+4, y+7,15,20,false );
					//4.画出圆形
					g.fillOval(x+6, y+9,10,15);
					//画出炮筒
					g.drawLine(x+11, y+9,x+11,y);
					break;
				case 1:
					//炮筒向右
					//画出上面的矩形
					g.fill3DRect(x, y, 35, 8, false);
					g.fill3DRect(x, y+17, 35, 8, false);
					g.fill3DRect(x+7,y+5, 20, 15,false);
					g.fillOval(x+9, y+6, 15, 10);
					g.drawLine(x+12, y+12, x+35, y+12);
					break;
				case 2:
				//向下
					g.fill3DRect(x, y,8,35,false );
					//2.画出右边的矩形
					g.fill3DRect(x+17, y,8,35,false );
				//3.中间矩形
					g.fill3DRect(x+4, y+7,15,20,false );
				//4.画出圆形
					g.fillOval(x+6, y+9,10,15);
				//画出炮筒
					g.drawLine(x+11, y+9,x+11,y+34);
					break;
				case 3:
					//向左
					//画出上面的矩形
					g.fill3DRect(x, y, 35, 8, false);
					g.fill3DRect(x, y+17, 35, 8, false);
					g.fill3DRect(x+9,y+5, 20, 15,false);
					g.fillOval(x+10, y+6, 17, 10);
					g.drawLine(x+10, y+12, x, y+12);
					break;
				
				}
		
		
	}

		//键按下处理 
		public void keyPressed(KeyEvent arg0) {
			// TODO 自动生成的方法存根
			if(arg0.getKeyCode()==KeyEvent.VK_W)
			{
				//设置我的坦克的方向
				this.hero.setDirect(0);
				this.hero.moveUp();
			}else if(arg0.getKeyCode()==KeyEvent.VK_D)
			{
				//向右
				this.hero.setDirect(1);
				this.hero.moveRight();
			}else if(arg0.getKeyCode()==KeyEvent.VK_S)
			{
				//向下
				this.hero.setDirect(2);
				this.hero.moveDown();
			}else if(arg0.getKeyCode()==KeyEvent.VK_A)
			{
				//向左
				this.hero.setDirect(3);
				this.hero.moveLeft();
			}
			//判断玩家是否按下J
			if(Recorder.getMyLife()>0){
			if(arg0.getKeyCode()==KeyEvent.VK_J)
			{
				if(this.hero.ss.size()<=4)
				{
				//开火
					this.hero.shotEnemy();
				}
			}
			
			if(arg0.getKeyCode()==KeyEvent.VK_K)
			{
				this.hero.moveshotEnemy();
			}
			}
			
			
			//必须重新绘制Panel
			this.repaint();
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO 自动生成的方法存根
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO 自动生成的方法存根
			
		}
		
		public void run(){
			//每隔100毫秒去重绘
			while(true){
				try {
					Thread.sleep(100);
				} catch (Exception e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				
				
				this.hitEnemyTank();
				this.hitme();
		
			
				//重绘
			this.repaint();
		}
	}
	
	
}
