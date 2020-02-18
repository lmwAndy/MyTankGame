/*
 *坦克游戏2.0
 *1,。画出坦克
 *2.我的坦克可以上下移动
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
		
		//注册监听
		this.addKeyListener(mp);
		
		this.setSize(400,300);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

//我的面板
class MyPanel extends JPanel implements KeyListener
{
	
	// 定义我的坦克
	Hero hero=null;
	
	//定义敌人的坦克
	
	Vector<EnemyTank> ets=new Vector<EnemyTank>();
	int ensize=3;
	
	//构造函数
	public  MyPanel()
	{
		hero=new Hero(100,100);
		
		//初始化敌人的坦克
		for(int i=0;i<ensize;i++)
		{
			//创建一辆敌人的坦克
			EnemyTank et=new EnemyTank((i+1)*50,0);
			et.setColor(0);
			ets.add(et);
		}
		
	}
	
	//先要重写paint
	public void paint(Graphics g)
	{
		super.paint(g);
		g.fillRect(0, 0, 800, 600);
		//画出自己的坦克
		this.drawTank(hero.getX(), hero.getY(), g, this.hero.direct, 0);  
		//画出敌人的坦克
		for(int i=0;i<ets.size();i++)
		{
			this.drawTank(ets.get(i).getX(), ets.get(i).getY(), g, ets.get(i).getDirect(), 1);
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
	
	
}
