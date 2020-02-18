/*
 *̹����Ϸ2.0

 *1,������̹��
 *2.�ҵ�̹�˿��������ƶ�
 *3.���������ӵ����ӵ�����5��
 * 4.���ҵ�̹�˻��е����ǣ����˾���ʧ����ը��Ч����
 * 5.�ұ����к���ʾ��ըЧ��
 * 6.���õ���̹���ص��˶�
 * 		6.1�������ж��Ƿ���ײ�ĺ���д��EnemyTank��
 * 7.���Էֹ�
 * 		7.1��һ����ʼ��Panel������һ���յ�
 * 		7.2��˸Ч��
 * 8.����������Ϸ��ʱ����ͣ�ͼ���
 * 		8.1���û���ͣʱ���ӵ����ٶȺ�̹���ٶ���Ϊ0������̹�˵ķ���Ҫ�仯
 * 9.���Լ�¼��ҵĳɼ�
 * 		9.1���ļ���
 * 		9.2��дһ����,��ɶ���ҵļ�¼
 * 		9.3 ��������ҵ���������
 * 10.java��β��������ļ�
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
	
	//��������Ҫ�Ĳ˵�
	JMenuBar jmb=null;
	//��ʼ��Ϸ
	JMenu jm1=null;
	JMenuItem jmil=null;
	JMenuItem jmi2=null;
	
	//����һ����ʼ��Panel
	MyStartPanel msp=null;
	public static void main(String[] args)
	{
		MyTankGame4 mtg=new MyTankGame4();
	}

	public MyTankGame4()
	{
		
		//mp=new MyPanel();
		
		//this.add(mp);
		
		//ע�����
		//this.addKeyListener(mp);
		
		//����mp�߳�
		//Thread t=new Thread(mp);
		//t.start();
		
		//�����˵����˵�ѡ��
		jmb=new JMenuBar();
		jm1=new JMenu("��Ϸ(G)");
		//���ÿ�ݷ�ʽ
		jm1.setMnemonic('G');
		jmil=new JMenuItem("��ʼ����Ϸ(N)");
		jmi2=new JMenuItem("��Ϸ��ͣ(S)");
		
		//��jmil������Ӧ
		jmil.addActionListener(this);
		jmil.setActionCommand("newgame");
		
		
		//��jmi2������Ӧ
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
		//����mp�߳�
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO �Զ����ɵķ������
		//���û���ͬ�ĵ��������ͬ�Ĵ���
		if(arg0.getActionCommand().equals("newgame"))
		{
			//����ս�����
			mp=new MyPanel();
			
			this.add(mp);
			
			//ע�����
			this.addKeyListener(mp);
			mp.setBackground(Color.CYAN);
			
			//����mp�߳�
			this.remove(msp);
			Thread t=new Thread(mp);
			t.start();
			//��ʾ ˢ��
			this.setVisible(true);
			
		}
		
	}
}

//����һ����ʾ����
class MyStartPanel extends JPanel implements Runnable
{
	
	int times=0;
	
	public void paint(Graphics g)
	{
		super.paint(g);
		g.fillRect(0, 0, 400, 300);
		//��ʾ��Ϣ
		if(times%2==0)
		{
		g.setColor(Color.yellow);
		//������Ϣ������
		Font myFont=new Font("������κ",Font.BOLD,30);
		g.setFont(myFont);
		g.drawString("stage: 1", 150,150 );
		
		}
	}

	@Override
	public void run() {
		// TODO �Զ����ɵķ������
		while(true)
		{
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			
			times++;
			
			//�ػ�
			this.repaint();
		}
		
	}
}

//�ҵ����
class MyPanel extends JPanel implements KeyListener,Runnable
{
	// �����ҵ�̹��
	Hero hero=null;
	
	//������˵�̹��
	
	Vector<EnemyTank> ets=new Vector<EnemyTank>();
	int ensize=8;
	
	//����ը������
	Vector<Bomb> bombs=new Vector<Bomb>();
	
	//��������ͼƬ
	Image image1=null;
	Image image2=null;
	Image image3=null;
	Image image4=null;
	Image image5=null;
	Image image6=null;
	
	//ʤ��ͼƬ
	Image image7=null;
	
	EnemyTank et=null;
	
	//���캯��
	public  MyPanel()
	{
		
		
		
		hero=new Hero(500,400);
		
		//��ʼ�����˵�̹��
		for(int i=0;i<ensize;i++)
		{
			//����һ�����˵�̹��
			et=new EnemyTank((i+1)*50,0);
			et.setColor(0);
			et.setDirect(2);
			
			//��MYPanel�ĵ���̹�����������õ���̹��
			et.setEts(ets);
			
			//�������˵�̹��
			Thread t=new Thread(et);
			t.start();
			
			//g������̹�����һ���ӵ�
			Shot s=new Shot(et.x+10,et.y+34,2);
			
			//���������
			et.ss.add(s);
			Thread t2=new Thread(s);
			t2.start();
			
			//����
			ets.add(et);
			
		}
		
		/*try {

			image2=ImageIO.read(new File("111.jpg"));
			image3=ImageIO.read(new File("444.jpg"));
			image4=ImageIO.read(new File("222.jpg"));
			image5=ImageIO.read(new File("555.jpg"));
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}*/
		
		//��ʼ��ͼƬ
		image1=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/666.jpg"));
		image2=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/111.jpg"));
		image3=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/444.jpg"));
		image4=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/222.jpg"));
		image5=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/555.jpg"));
		image7=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/888.jpg"));
	
		
	}
	
	//������ʾ��Ϣ
	public void showTnfo(Graphics g)
	{
		//������ʾ��Ϣ̹�ˣ���̹�˲�����ս����
				this.drawTank(100, 550, g, 0, 1);
				g.setColor(Color.black);
				Font myFont=new Font("������κ",Font.BOLD,20);
				g.setFont(myFont);
				g.drawString(Recorder.getEnNum()+"", 140, 573);
				this.drawTank(100, 600, g, 0, 0);
				g.setColor(Color.black);
				g.drawString(Recorder.getMyLife()+"", 140, 623);
				
				//������ҵ��ܳɼ�
				g.setColor(Color.black);
				Font f=new Font("����",Font.BOLD,20);
				g.setFont(f);
				g.drawString("����ܳɼ�", 720, 30);
				
				this.drawTank(750, 50, g, 0, 0);
				
				g.setColor(Color.black);
				g.drawString(Recorder.getAllEnNum()+"",790 , 75);
				
				//���������������
				g.setColor(Color.black);
				Font h=new Font("����",Font.BOLD,20);
				g.setFont(h);
				g.drawString("�����������", 720, 130);
				
				this.drawTank(750, 150, g, 0, 0);
				
				g.setColor(Color.black);
				g.drawString(Recorder.getDeathtime()+"",790 , 175);
				
				
				
				
	}

	
	//��Ҫ��дpaint 
	public void paint(Graphics g)
	{
		
		super.paint(g);
		g.setColor(Color.black);
		g.fillRect(0, 0, 700, 500);
		
		
		if(Recorder.getEnNum()==0)
		{
			g.drawImage(image7, 200, 200, 200, 200,this);
		}
		
		//������ʾ��Ϣ
		this.showTnfo(g);
		
		//�����Լ���̹��
		if(Recorder.getMyLife()>0)
		{
			this.drawTank(hero.getX(), hero.getY(), g, this.hero.direct, 0); 
		}
		

		
		//�������˵�̹��
		for(int i=0;i<ets.size();i++)
		{
			EnemyTank et=ets.get(i);
			if(et.isLive)
			{
				this.drawTank(et.getX(),et.getY(), g, et.getDirect(), 1);
			
				//�������˵��ӵ�
				for(int j=0;j<et.ss.size();j++)
				{
					//ȡ���ӵ�
					Shot enemyShot=et.ss.get(j);
					if(enemyShot.isLive)
					{
						g.setColor(Color.red);
						g.draw3DRect(enemyShot.x, enemyShot.y, 2, 2, false);
					}else
					 {
						
						//��ss��ɾ�������ӵ�
						et.ss.remove(enemyShot);
					}
				}
			
			}
		}
		
		//��ss��ȡ��ÿһ���ӵ��������ƣ�������
		//�����ӵ�
		for(int i=0;i<this.hero.ss.size();i++)
		{
			//ȡ��һ���ӵ�
			Shot myShot=hero.ss.get(i);
			if(myShot!=null&&myShot.isLive==true)
			{
				g.setColor(Color.yellow);
				g.draw3DRect(myShot.x, myShot.y, 2, 2, false);
			
			}
			if(myShot.isLive==false)
			{
				//��ss��ɾ�������ӵ�
				hero.ss.remove(myShot);
			}
		}
		
		//����ɢ��
		for(int i=0;i<this.hero.ms.size();i++)
		{
			//ȡ��һ����ɢ��
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
				//��ms��ɾ�������ӵ�
				hero.ms.remove(myShotm1);
			}
			
			if(myShotm2!=null&&myShotm2.isLive==true)
			{
				g.setColor(Color.green);
				g.draw3DRect(myShotm2.x, myShotm2.y, 2, 2, false);
			
			}
			if(myShotm2.isLive==false)
			{
				//��ms��ɾ�������ӵ�
				hero.ms.remove(myShotm2);
			}
			
			if(myShotm3!=null&&myShotm3.isLive==true)
			{
				g.setColor(Color.green);
				g.draw3DRect(myShotm3.x, myShotm3.y, 2, 2, false);
			
			}
			if(myShotm3.isLive==false)
			{
				//��ms��ɾ�������ӵ�
				hero.ms.remove(myShotm3);
			}
			
		}
		
		
		//����ը��
		for(int i=0;i<bombs.size();i++)
		{
			//ȡ��ը��
			Bomb b=bombs.get(i);
			
			if(b.life>8)
			{
				g.drawImage(image1, b.x, b.y, 40, 40,this);
				System.out.println("fine��");
				
			}else if(b.life>7){
				g.drawImage(image2,  b.x, b.y, 40, 40,this);
				System.out.println("thanks��");
			}else if(b.life>5){
				g.drawImage(image3,  b.x, b.y, 40, 40,this);
				System.out.println("and��");
			}else if(b.life>3){
				g.drawImage(image4,  b.x, b.y, 40, 40,this);
				System.out.println("you��");
			}else{
				g.drawImage(image5,  b.x, b.y, 40, 40,this);
				System.out.println("���㣡");
			}
			
			//��b������ֵ��С
			b.lifeDown();
			//���ը������ֵΪ0���͸ðѸ�ը����bombs�����ͷ�
			if(b.life==0)
			{
				bombs.remove(b);
			}
		}
		
	}
	
	//���˵��ӵ��Ƿ����
	public void hitme()
	{
		//ȡ��ÿһ�����˵�̹��
		for(int i=0;i<this.ets.size();i++)
		{
			//ȡ�����˵�̹��
			EnemyTank et=ets.get(i);
			
			//ȡ��ÿһ���ӵ�
			for(int j=0;j<et.ss.size();j++)
			{
				//ȡ���ӵ�
				Shot enemyShot=et.ss.get(j);
				if(Recorder.getMyLife()>0)
				{
					this.hitMyTank(enemyShot, hero);
				
				}
			}
		}
	}
	
	
	//�ж��ҵ��ӵ��Ƿ���е��˵�̹��
	public void hitEnemyTank()
	{

		//�ж��Ƿ���е��˵�̹��
		for(int i=0;i<hero.ss.size();i++)
		{
			//ȡ���ӵ�
			Shot myShot=hero.ss.get(i);
			Shot myShotm1=hero.ms.get(i);
			Shot myShotm2=hero.ms.get(i);
			Shot myShotm3=hero.ms.get(i);
			
			
			
//			//�ж��ӵ��Ƿ���Ч
//			if(myShot.isLive||myShotm1.isLive||myShotm2.isLive||myShotm3.isLive)
//			{
//				//ȡ��ÿ��̹�ˣ������ж�
//				for(int j=0;j<ets.size();j++)
//				{
//					//ȡ��̹��
//					EnemyTank et=ets.get(j);
//					
//					if(et.isLive)
//					{
//						this.hitTank(myShot, et);
//					}
//				}
//			}
	}
		//�ŵ�������  ��ɢ�����ܻ�û��ɣ�����ɢ���������⻹û���������z
		//ֱ�Ӳ鿴Ϊ�������ɢ�������������¶��壬���ҵ��ӵ�ȴ���ö���
		
		
	}
	
	//дһ������ר���ж��ӵ��Ƿ��е���̹��
	public void hitTank(Shot s,Tank et,MoveShot ms1,MoveShot ms2,MoveShot ms3)
	{
		//�жϸ�̹�˵ķ���
		switch(et.direct)
		{
		//�������̹�˵ķ��������ϻ���������
		case 0:
		case 2:
			if(s.x>=et.x&&s.x<=et.x+20&&s.y>=et.y&&s.y<=et.y+30)
			{
				//����
				//�ӵ�����
				s.isLive=false;
				
				
				//����̹������
				et.isLive=false;
				//���ٵص�������
				Recorder.reduceEnNum();
				//�����ҵ�ɱ�м�¼
				Recorder.addEnNumRec();
				
			
				
				//����һ��ը��������Vector
				  Bomb b=new Bomb(et.x,et.y);
				  bombs.add(b);
			}else if(ms1.x>=et.x&&ms1.x<=et.x+20&&ms1.y>=et.y&&ms1.y<=et.y+30)
			{
				//����
				//�ӵ�����
				ms1.isLive=false;
				
				
				//����̹������
				et.isLive=false;
				//���ٵص�������
				Recorder.reduceEnNum();
				//�����ҵ�ɱ�м�¼
				Recorder.addEnNumRec();
			}else if(ms2.x>=et.x&&ms2.x<=et.x+20&&ms2.y>=et.y&&ms2.y<=et.y+30)
			{
				//����
				//�ӵ�����
				ms2.isLive=false;
				
				
				//����̹������
				et.isLive=false;
				//���ٵص�������
				Recorder.reduceEnNum();
				//�����ҵ�ɱ�м�¼
				Recorder.addEnNumRec();
			}else if(ms3.x>=et.x&&ms3.x<=et.x+20&&ms3.y>=et.y&&ms3.y<=et.y+30)
			{
				//����
				//�ӵ�����
				ms3.isLive=false;
				
				
				//����̹������
				et.isLive=false;
				//���ٵص�������
				Recorder.reduceEnNum();
				//�����ҵ�ɱ�м�¼
				Recorder.addEnNumRec();
			}
			break;
		case 1:
		case 3:
			if(s.x>=et.x&&s.x<=et.x+30&&s.y>=et.y&&s.y<=et.y+20)
			{
				//����
				//�ӵ�����
				s.isLive=false;
				
				//����̹������
				et.isLive=false;
				//���ٵ�������
				Recorder.reduceEnNum();
				//�����ҵ�ɱ�м�¼
				Recorder.addEnNumRec();
				
				
				
				
				//����һ��ը��������Vector
				  Bomb b=new Bomb(et.x,et.y);
				  bombs.add(b);
			}
			break;
		}
		
	}
	
	//дһ������ר���ж��ӵ��Ƿ����ҵ�̹��
		public void hitMyTank(Shot s,Tank hero)
		{
			//�жϸ�̹�˵ķ���
			switch(hero.direct)
			{
			//����ҵ�̹�˵ķ��������ϻ���������
			case 0:
			case 2:
				if(s.x>=hero.x&&s.x<=hero.x+20&&s.y>=hero.y&&s.y<=hero.y+30)
				{
					//����
					//�ӵ�����
					s.isLive=false;
					
					//�ҵ�̹������
					hero.isLive=false;
					//��������
					Recorder.reduceHero();
					//������������
					Recorder.adddeathtime();
					
					
					//����һ��ը��������Vector
					  Bomb b=new Bomb(hero.x,hero.y);
					  bombs.add(b);
				}
				break;
			case 1:
			case 3:
				if(s.x>=hero.x&&s.x<=hero.x+30&&s.y>=hero.y&&s.y<=hero.y+20)
				{
					//����
					//�ӵ�����
					s.isLive=false;
					
					//̹��̹������
					hero.isLive=false;
					
					
					Recorder.reduceHero();
					Recorder.adddeathtime();
					
					
					//����һ��ը��������Vector
					  Bomb b=new Bomb(hero.x,hero.y);
					  bombs.add(b);
				}
				break;
			}
			
		}
		
	
	
	
		
		//����̹�˵ĺ���(��չ)
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
			//�жϷ���
				switch(direct)
				{
				//������
				case 0:
					//��ɫ
					
					//�����ҵ�̹��(��ʱ�ٷ�װ��һ������)
					//1.������ߵľ���
					g.fill3DRect(x, y,8,35,false );
					//2.�����ұߵľ���
					g.fill3DRect(x+17, y,8,35,false );
					//3.�м����
					g.fill3DRect(x+4, y+7,15,20,false );
					//4.����Բ��
					g.fillOval(x+6, y+9,10,15);
					//������Ͳ
					g.drawLine(x+11, y+9,x+11,y);
					break;
				case 1:
					//��Ͳ����
					//��������ľ���
					g.fill3DRect(x, y, 35, 8, false);
					g.fill3DRect(x, y+17, 35, 8, false);
					g.fill3DRect(x+7,y+5, 20, 15,false);
					g.fillOval(x+9, y+6, 15, 10);
					g.drawLine(x+12, y+12, x+35, y+12);
					break;
				case 2:
				//����
					g.fill3DRect(x, y,8,35,false );
					//2.�����ұߵľ���
					g.fill3DRect(x+17, y,8,35,false );
				//3.�м����
					g.fill3DRect(x+4, y+7,15,20,false );
				//4.����Բ��
					g.fillOval(x+6, y+9,10,15);
				//������Ͳ
					g.drawLine(x+11, y+9,x+11,y+34);
					break;
				case 3:
					//����
					//��������ľ���
					g.fill3DRect(x, y, 35, 8, false);
					g.fill3DRect(x, y+17, 35, 8, false);
					g.fill3DRect(x+9,y+5, 20, 15,false);
					g.fillOval(x+10, y+6, 17, 10);
					g.drawLine(x+10, y+12, x, y+12);
					break;
				
				}
		
		
	}

		//�����´��� 
		public void keyPressed(KeyEvent arg0) {
			// TODO �Զ����ɵķ������
			if(arg0.getKeyCode()==KeyEvent.VK_W)
			{
				//�����ҵ�̹�˵ķ���
				this.hero.setDirect(0);
				this.hero.moveUp();
			}else if(arg0.getKeyCode()==KeyEvent.VK_D)
			{
				//����
				this.hero.setDirect(1);
				this.hero.moveRight();
			}else if(arg0.getKeyCode()==KeyEvent.VK_S)
			{
				//����
				this.hero.setDirect(2);
				this.hero.moveDown();
			}else if(arg0.getKeyCode()==KeyEvent.VK_A)
			{
				//����
				this.hero.setDirect(3);
				this.hero.moveLeft();
			}
			//�ж�����Ƿ���J
			if(Recorder.getMyLife()>0){
			if(arg0.getKeyCode()==KeyEvent.VK_J)
			{
				if(this.hero.ss.size()<=4)
				{
				//����
					this.hero.shotEnemy();
				}
			}
			
			if(arg0.getKeyCode()==KeyEvent.VK_K)
			{
				this.hero.moveshotEnemy();
			}
			}
			
			
			//�������»���Panel
			this.repaint();
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO �Զ����ɵķ������
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO �Զ����ɵķ������
			
		}
		
		public void run(){
			//ÿ��100����ȥ�ػ�
			while(true){
				try {
					Thread.sleep(100);
				} catch (Exception e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
				
				
				this.hitEnemyTank();
				this.hitme();
		
			
				//�ػ�
			this.repaint();
		}
	}
	
	
}
