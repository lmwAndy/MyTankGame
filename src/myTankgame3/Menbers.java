package myTankgame3;

import java.util.Vector;

//c=炸弹类
class Bomb
{
	//定义炸弹的坐标
	int x,y;
	//炸弹额生命
	int life=11;
	boolean isLive=true;
	public Bomb(int x,int y)
	{
		this.x=x;  
		this.y=y;
	}
	
	//减少生命值
	public void lifeDown()
	{
		
		if(life>0)
		{
			life--;
		}else{
			this.isLive=false;
		}
	}
}

//子弹类
class Shot implements Runnable
{
	int x;
	int y;
	int direct;
	int speed=10;
	//是否HIA或者
	boolean isLive=true;
	public Shot(int x,int y,int direct)
	{
		this.x=x;
		this.y=y;
		this.direct=direct;
	}
	@Override
	public void run() {
		// TODO 自动生成的方法存根
		while(true)
		{
			//休息一段时间
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			switch(direct)
			{
			case 0:
				//上
				y-=speed;
				break;
			case 1:
				x+=speed;
				break;
			case 2:
				y+=speed;
				break;
			case 3:
				x-=speed;
				break;
			}
			
			
		//子弹何时死亡
			//判断该子弹是否碰到边缘
			if(x<0||x>800||y<0||y>600)
			{
				this.isLive=false;
				break;
			}
		}
	}
}
//坦克类
class Tank  
{
	
	//坦克的速度
	int speed=10;
	boolean isLive=true;
	

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

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
	
	//坦克方向
	//0表示上  1表示下右 2表示下  3表示左
	int direct=0;

	public int getDirect() {
		return direct;
	}

	public void setDirect(int direct) {
		this.direct = direct;
	}
	
	//颜色
	int color;

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	//坦克纵坐标
	int y=0;
	
	public  Tank(int x,int y)
	{
		this.x=x;
		this.y=y;
	}
	
	

}

//敌人的坦克
//吧敌人坦克做成线程
class EnemyTank extends Tank implements Runnable
{
	int speed=3;
	
	int times=0;
	
	//子弹
		//Shot s=null;
		Vector<Shot> ss=new Vector<Shot>();
		//敌人添加子弹,应该在刚刚创建坦克和敌人的坦克子弹死亡后
	public EnemyTank(int x,int y)
	{
		super(x,y);
	}
	@Override
	public void run() {
		// TODO 自动生成的方法存根
		
		while(true)
		{
			
			switch(this.direct)
			{
			case 0:
				//说明坦克正在向上移动
				for(int i=0;i<30;i++)
				{
					if(y>0){
						y-=speed;
					}
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
					
				}
				break;
			case 1:
				//向右
				for(int i=0;i<50;i++)
				{
					//保证坦克不出边界
					if(x<760)
					{
						x+=speed;
					}
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				}
				break;
			case 2:
				//向下
				for(int i=0;i<30;i++)
				{
					if(y<560)
					{
						y+=speed;
					}
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				}
				break;
			case 3:
				//向左
				for(int i=0;i<60;i++)
				{
					if(x>0)
					{
						x-=speed;
					}
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				}
				break;
			}
			
			this.times++;
			if(times%2==0)
			{
				if(isLive)
				{
					if(ss.size()<5)
					{
						Shot s=null;
						//没有子弹
						//添加
						switch(direct)
						{
						case 0:
							//创建一颗子弹
							s=new Shot(x+10, y,0);
							//吧子弹加入向量
							ss.add(s);
							break;
						case 1:
							s=new Shot(x+35, y+12,1);
							ss.add(s);
							break;
						case 2:
							s=new Shot(x+10, y+34,2);
							ss.add(s);
							break;
						case 3:
							s=new Shot(x, y+12,3);
							ss.add(s);
							break;
						}
						
						//启动敌人子弹线程
						Thread t=new Thread(s);
						t.start();
					}
				}
			}
			
			
			
			
			//让坦克随机产生一个新的方向
			this.direct=(int)(Math.random()*4);
			
			//判断敌人是否死亡
			if(this.isLive==false)
			{
				//让坦克死亡后，退出线程
				break;
			}
			
			
			
			
		}
		
	}
}
//我的坦克
class Hero  extends Tank
{
	
	public Hero(int x,int y)
	{
		super(x,y);
		
	}
	//子弹
	//Shot s=null;
	Vector<Shot> ss=new Vector<Shot>();
	Shot s=null;
	
	//开火
	public void shotEnemy()
	{
		switch(this.direct)
		{
		case 0:
			//创建一颗子弹
			s=new Shot(x+10, y,0);
			//吧子弹加入向量
			ss.add(s);
			break;
		case 1:
			s=new Shot(x+35, y+12,1);
			ss.add(s);
			break;
		case 2:
			s=new Shot(x+10, y+34,2);
			ss.add(s);
			break;
		case 3:
			s=new Shot(x, y+12,3);
			ss.add(s);
			break;
			
		}
		//启动子弹线程
		Thread t=new Thread(s);
		t.start();
		
	}
	//坦克向上移动 
	public void moveUp()
	{
		if(y>0){
			y-=speed;
		}
		
	}
	//坦克向右移动
	public void moveRight()
	{
		
		if(x<760)
		{
		
				x+=speed;
		
		}
	}
	//坦克向下移动
		public void moveDown()
		{
			
			if(y<560)
			{
				y+=speed;
			}
		}
	//坦克向下移动
				public void moveLeft()
				{
					
					if(x>0)
					{
						x-=speed;
					}
				}
				
			
				
}



