/*
 *坦克游戏2.0
 *1,。画出坦克
 *2.我的坦克可以上下移动
 *3.可以连发子弹，子弹连发5发
 * 4.当我的坦克击中敌人是，敌人就消失（爆炸的效果）
 * 5.我被击中后，显示爆炸效果
 * 6.放置敌人坦克重叠运动
 * 7.可以分关
 * 8.可以再玩游戏的时候暂停和继续
 * 9.可以记录玩家的成绩
 * 10.java如何操作声音文件
 */
package myTankgame3;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Color;
import java.util.*;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;
public class MyTankGame3 extends JFrame{
	
	MyPanel mp=null;
	public static void main(String[] args)
	{
		MyTankGame3 mtg=new MyTankGame3();
	}

	public MyTankGame3()
	{
		
		mp=new MyPanel();
		
		this.add(mp);
		
		//注册监听
		this.addKeyListener(mp);
		
		//启动mp线程
		Thread t=new Thread(mp);
		t.start();
		
		mp.setBackground(Color.CYAN);
		this.setSize(1000,800);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//启动mp线程
		
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
	
	
	
	
	//构造函数
	public  MyPanel()
	{
		
		
		
		hero=new Hero(500,500);
		
		//初始化敌人的坦克
		for(int i=0;i<ensize;i++)
		{
			//创建一辆敌人的坦克
			EnemyTank et=new EnemyTank((i+1)*50,0);
			et.setColor(0);
			et.setDirect(2);
			
			
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
	}
	
	//先要重写paint 
	public void paint(Graphics g)
	{
		super.paint(g);
		g.setColor(Color.black);
		g.fillRect(0, 0, 800, 600);
		
		//画出自己的坦克
		if(hero.isLive)
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
		
		//画出炸弹
		for(int i=0;i<bombs.size();i++)
		{
			//取出炸弹
			Bomb b=bombs.get(i);
			
			if(b.life>8)
			{
				g.drawImage(image1, b.x, b.y, 30, 30,this);
				System.out.println("你好！");
				
			}else if(b.life>7){
				g.drawImage(image2,  b.x, b.y, 30, 30,this);
				System.out.println("我好！");
			}else if(b.life>5){
				g.drawImage(image3,  b.x, b.y, 30, 30,this);
				System.out.println("都好！");
			}else if(b.life>3){
				g.drawImage(image4,  b.x, b.y, 30, 30,this);
				System.out.println("不好！");
			}else{
				g.drawImage(image5,  b.x, b.y, 30, 30,this);
				System.out.println("真好！");
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
				
				this.hitTank(enemyShot, hero);
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
			//判断子弹是否有效
			if(myShot.isLive)
			{
				//取出每个坦克，与他判断
				for(int j=0;j<ets.size();j++)
				{
					//取出坦克
					EnemyTank et=ets.get(j);
					
					if(et.isLive)
					{
						this.hitTank(myShot, et);
					}
				}
			}
		}
	}
	
	//写一个函数专门判断子弹是否集中敌人坦克
	public void hitTank(Shot s,Tank et)
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
				
				//创建一颗炸弹，放入Vector
				  Bomb b=new Bomb(et.x,et.y);
				  bombs.add(b);
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
				
				//创建一颗炸弹，放入Vector
				  Bomb b=new Bomb(et.x,et.y);
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
			if(arg0.getKeyCode()==KeyEvent.VK_J)
			{
				if(this.hero.ss.size()<=4)
				{
				//开火
					this.hero.shotEnemy();
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
