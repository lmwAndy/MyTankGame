/*
 * ����Ŀ��̹�˴�ս��Ϸ
 * �漰��
 * 1.java���������
 * 2.������
 * 3.��ͼ����
 * 4.���߳�
 * 5.�ļ�i/o����
 * 6.���ݿ�
 * 
 * 
 */
package myTankGame1;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;
public class MyTankGame1 extends JFrame{
	
	MyPanel mp=null;
	public static void main(String[] args)
	{
		MyTankGame1 mtg=new MyTankGame1();
	}

	public MyTankGame1()
	{
		mp=new MyPanel();
		
		this.add(mp);
		this.setSize(400,300);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

//�ҵ����
class MyPanel extends JPanel 
{
	
	// �����ҵ�̹��
	Hero hero=null;
	
	//���캯��
	public  MyPanel()
	{
		hero=new Hero(100,100);
		
	}
	
	//��Ҫ��дpaint
	public void paint(Graphics g)
	{
		super.paint(g);
		g.fillRect(0, 0, 400, 300);
		
		this.drawTank(hero.getX(), hero.getY(), g, 0, 0);  
		
	}
		
		//����̹�˵ĺ���
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
				}
		
		
	}
	
	
}
//̹����
class Tank  
{
	//��ʾ̹�˵ĺ�����
	int x=0;
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	//̹��������
	int y=0;
	
	public void Tank(int x,int y)
	{
		this.x=x;
		this.y=y;
	}
	
	

}


//�ҵ�̹��
class Hero extends Tank  
{
	
	public Hero(int x,int y)
	{
		this.x=x;
		this.y=y;
	}
	
	
}