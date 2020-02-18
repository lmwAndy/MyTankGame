/*
 *̹����Ϸ2.0
 *1,������̹��
 *2.�ҵ�̹�˿��������ƶ�
 * 
 * 
 */
package myTankGame2;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

import javax.swing.*;
public class MyTankGame2 extends JFrame{
	
	MyPanel mp=null;
	public static void main(String[] args)
	{
		MyTankGame2 mtg=new MyTankGame2();
	}

	public MyTankGame2()
	{
		mp=new MyPanel();
		
		this.add(mp);
		
		//ע�����
		this.addKeyListener(mp);
		
		this.setSize(400,300);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

//�ҵ����
class MyPanel extends JPanel implements KeyListener
{
	
	// �����ҵ�̹��
	Hero hero=null;
	
	//������˵�̹��
	
	Vector<EnemyTank> ets=new Vector<EnemyTank>();
	int ensize=3;
	
	//���캯��
	public  MyPanel()
	{
		hero=new Hero(100,100);
		
		//��ʼ�����˵�̹��
		for(int i=0;i<ensize;i++)
		{
			//����һ�����˵�̹��
			EnemyTank et=new EnemyTank((i+1)*50,0);
			et.setColor(0);
			ets.add(et);
		}
		
	}
	
	//��Ҫ��дpaint
	public void paint(Graphics g)
	{
		super.paint(g);
		g.fillRect(0, 0, 800, 600);
		//�����Լ���̹��
		this.drawTank(hero.getX(), hero.getY(), g, this.hero.direct, 0);  
		//�������˵�̹��
		for(int i=0;i<ets.size();i++)
		{
			this.drawTank(ets.get(i).getX(), ets.get(i).getY(), g, ets.get(i).getDirect(), 1);
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
	
	
}
