/*
 * 本项目是坦克大战游戏
 * 涉及到
 * 1.java面向对象编程
 * 2.界面编程
 * 3.绘图技术
 * 4.多线程
 * 5.文件i/o操作
 * 6.数据库
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

//我的面板
class MyPanel extends JPanel 
{
	
	// 定义我的坦克
	Hero hero=null;
	
	//构造函数
	public  MyPanel()
	{
		hero=new Hero(100,100);
		
	}
	
	//先要重写paint
	public void paint(Graphics g)
	{
		super.paint(g);
		g.fillRect(0, 0, 400, 300);
		
		this.drawTank(hero.getX(), hero.getY(), g, 0, 0);  
		
	}
		
		//画出坦克的函数
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
				}
		
		
	}
	
	
}
//坦克类
class Tank  
{
	//表示坦克的横坐标
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

	//坦克纵坐标
	int y=0;
	
	public void Tank(int x,int y)
	{
		this.x=x;
		this.y=y;
	}
	
	

}


//我的坦克
class Hero extends Tank  
{
	
	public Hero(int x,int y)
	{
		this.x=x;
		this.y=y;
	}
	
	
}