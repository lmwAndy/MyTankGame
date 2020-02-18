/*
 *̹����Ϸ2.0
 *1,������̹��
 *2.�ҵ�̹�˿��������ƶ�
 *3.���������ӵ����ӵ�����5��
 * 4.���ҵ�̹�˻��е����ǣ����˾���ʧ����ը��Ч����
 * 5.�ұ����к���ʾ��ըЧ��
 * 6.���õ���̹���ص��˶�
 * 7.���Էֹ�
 * 8.����������Ϸ��ʱ����ͣ�ͼ���
 * 9.���Լ�¼��ҵĳɼ�
 * 10.java��β��������ļ�
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
		
		//ע�����
		this.addKeyListener(mp);
		
		//����mp�߳�
		Thread t=new Thread(mp);
		t.start();
		
		mp.setBackground(Color.CYAN);
		this.setSize(1000,800);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//����mp�߳�
		
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
	
	
	
	
	//���캯��
	public  MyPanel()
	{
		
		
		
		hero=new Hero(500,500);
		
		//��ʼ�����˵�̹��
		for(int i=0;i<ensize;i++)
		{
			//����һ�����˵�̹��
			EnemyTank et=new EnemyTank((i+1)*50,0);
			et.setColor(0);
			et.setDirect(2);
			
			
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
	}
	
	//��Ҫ��дpaint 
	public void paint(Graphics g)
	{
		super.paint(g);
		g.setColor(Color.black);
		g.fillRect(0, 0, 800, 600);
		
		//�����Լ���̹��
		if(hero.isLive)
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
		
		//����ը��
		for(int i=0;i<bombs.size();i++)
		{
			//ȡ��ը��
			Bomb b=bombs.get(i);
			
			if(b.life>8)
			{
				g.drawImage(image1, b.x, b.y, 30, 30,this);
				System.out.println("��ã�");
				
			}else if(b.life>7){
				g.drawImage(image2,  b.x, b.y, 30, 30,this);
				System.out.println("�Һã�");
			}else if(b.life>5){
				g.drawImage(image3,  b.x, b.y, 30, 30,this);
				System.out.println("���ã�");
			}else if(b.life>3){
				g.drawImage(image4,  b.x, b.y, 30, 30,this);
				System.out.println("���ã�");
			}else{
				g.drawImage(image5,  b.x, b.y, 30, 30,this);
				System.out.println("��ã�");
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
				
				this.hitTank(enemyShot, hero);
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
			//�ж��ӵ��Ƿ���Ч
			if(myShot.isLive)
			{
				//ȡ��ÿ��̹�ˣ������ж�
				for(int j=0;j<ets.size();j++)
				{
					//ȡ��̹��
					EnemyTank et=ets.get(j);
					
					if(et.isLive)
					{
						this.hitTank(myShot, et);
					}
				}
			}
		}
	}
	
	//дһ������ר���ж��ӵ��Ƿ��е���̹��
	public void hitTank(Shot s,Tank et)
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
				
				//����һ��ը��������Vector
				  Bomb b=new Bomb(et.x,et.y);
				  bombs.add(b);
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
				
				//����һ��ը��������Vector
				  Bomb b=new Bomb(et.x,et.y);
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
			if(arg0.getKeyCode()==KeyEvent.VK_J)
			{
				if(this.hero.ss.size()<=4)
				{
				//����
					this.hero.shotEnemy();
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
