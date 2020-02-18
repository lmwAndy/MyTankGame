package myTankGame4;

import java.util.Vector;


//记录类，同时也可以保存玩家的设置
class Recorder
{
	//记录每关有多少敌人
	private static int enNum=8;
	
	//记录我共死了多少次
	private static int deathtime=0;
	
	public static int getDeathtime() {
		return deathtime;
	}
	public static void setDeathtime(int deathtime) {
		Recorder.deathtime = deathtime;
	}
	//记录总共消灭了多少敌人
	private static int allEnNum=0;
	
	public static int getAllEnNum() {
		return allEnNum;
	}
	public static void setAllEnNum(int allEnNum) {
		Recorder.allEnNum = allEnNum;
	}
	public static int getEnNum() {
		return enNum;
	}
	public static void setEnNum(int enNum) {
		Recorder.enNum = enNum;
	}
	public static int getMyLife() {
		return myLife;
	}
	public static void setMyLife(int myLife) {
		Recorder.myLife = myLife;
	}
	//设置我有多少可以用的人
	private static int myLife=3;
	
	//减掉一个敌人
	public static void reduceEnNum()
	{
		enNum--;
	}
	
	//消灭敌人数
	public static void addEnNumRec()
	{
		allEnNum++;
	}
	
	//我的死亡次数
	public static void adddeathtime()
	{
		deathtime++;
	}
	
	
	
	//减掉我的一条生命
	public static void reduceHero()
	{
		myLife--;
	}
	  
	
}


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
			if(x<0||x>700||y<0||y>500)
			{
				this.isLive=false;
				break;
			}
		}
	}
}

class MoveShot extends Shot implements Runnable
{

	public MoveShot(int x, int y, int direct) {
		super(x, y, direct);
		// TODO 自动生成的构造函数存根
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
	
	//定义一个向量，可以访问到MYPa上所有 的敌人坦克
	Vector<EnemyTank> ets=new Vector<EnemyTank>();
	
	//子弹
		//Shot s=null;
		Vector<Shot> ss=new Vector<Shot>();
		//敌人添加子弹,应该在刚刚创建坦克和敌人的坦克子弹死亡后
	public EnemyTank(int x,int y)
	{
		super(x,y);
	}
	
	//得到MYPanel的敌人坦克向量
	public void setEts(Vector<EnemyTank> vv)
	{
		this.ets=vv;
	}
	
	//判断是否碰到了别人的坦克
	public boolean  isTouchotherEnemy()
	{
		boolean b=false;
		
		switch(this.direct)
		{
		case 0:
			//我的坦克向上
			//取出所有的敌人坦克
			for(int i=0;i<ets.size();i++)
			{
				//取出第一个坦克
				EnemyTank et=ets.get(i);
				//如果不是自己
				if(et!=this)
				{
					//如果敌人的方向是向下或向上
					if(et.direct==0||et.direct==2)
					{
						if(this.x>=et.x&&this.x<=et.x+25&&this.y>=et.y&&this.y<=et.y+35)
						{
							return true;
						}
						if(this.x+25>=et.x&&this.x+25<=et.x+25&&this.y>=et.y&&this.y<=et.y+35)
						{
							return true;
						}
					}
					//如果敌人的方向是向左或是向右
					if(et.direct==1||et.direct==3)
					{
						if(this.x>=et.x&&this.x<=et.x+35&&this.y>=et.y&&this.y<=et.y+25)
						{
							return true;
						}
						if(this.x+25>=et.x&&this.x+25<=et.x+35&&this.y>=et.y&&this.y<=et.y+25)
						{
							return true;
						}
					}
				}
			}
			break;
		case 1:
			//坦克向右
			//取出所有的敌人坦克
			for(int i=0;i<ets.size();i++)
			{
				//取出第一个坦克
				EnemyTank et=ets.get(i);
				//如果不是自己
				if(et!=this)
				{
					//如果敌人的方向是向下或向上
					if(et.direct==0||et.direct==2)
					{
						if(this.x+35>=et.x&&this.x+35<=et.x+25&&this.y>=et.y&&this.y<=et.y+35)
						{
							return true;
						}
						if(this.x+35>=et.x&&this.x+35<=et.x+25&&this.y+25>=et.y&&this.y+25<=et.y+35)
						{
							return true;
						}
					}
					//如果敌人的方向是向左或是向右
					if(et.direct==1||et.direct==3)
					{
						if(this.x+35>=et.x&&this.x+35<=et.x+35&&this.y>=et.y&&this.y<=et.y+25)
						{
							return true;
						}
						if(this.x+35>=et.x&&this.x+35<=et.x+35&&this.y+25>=et.y&&this.y+25<=et.y+25)
						{
							return true;
						}
					}
				}
			}
			break;
		case 2:
			//坦克向下
			//取出所有的敌人坦克
			for(int i=0;i<ets.size();i++)
			{
				//取出第一个坦克
				EnemyTank et=ets.get(i);
				//如果不是自己
				if(et!=this)
				{
					//如果敌人的方向是向下或向上
					if(et.direct==0||et.direct==2)
					{
						if(this.x>=et.x&&this.x<=et.x+25&&this.y+35>=et.y&&this.y+35<=et.y+35)
						{
							return true;
						}
						if(this.x+25>=et.x&&this.x+25<=et.x+25&&this.y+35>=et.y&&this.y+35<=et.y+35)
						{
							return true;
						}
					}
					//如果敌人的方向是向左或是向右
					if(et.direct==1||et.direct==3)
					{
						if(this.x>=et.x&&this.x<=et.x+35&&this.y>=et.y&&this.y<=et.y+25)
						{
							return true;
						}
						if(this.x+25>=et.x&&this.x+25<=et.x+35&&this.y+35>=et.y&&this.y+35<=et.y+25)
						{
							return true;
						}
					}
				}
			}
			break;
		case 3:
			//坦克左
			//取出所有的敌人坦克
			for(int i=0;i<ets.size();i++)
			{
				//取出第一个坦克
				EnemyTank et=ets.get(i);
				//如果不是自己
				if(et!=this)
				{
					//如果敌人的方向是向下或向上
					if(et.direct==0||et.direct==2)
					{
						if(this.x>=et.x&&this.x<=et.x+25&&this.y>=et.y&&this.y<=et.y+35)
						{
							return true;
						}
						if(this.x>=et.x&&this.x<=et.x+25&&this.y+25>=et.y&&this.y+25<=et.y+35)
						{
							return true;
						}
					}
					//如果敌人的方向是向左或是向右
					if(et.direct==1||et.direct==3)
					{
						if(this.x>=et.x&&this.x<=et.x+35&&this.y>=et.y&&this.y<=et.y+25)
						{
							return true;
						}
						if(this.x>=et.x&&this.x<=et.x+35&&this.y+25>=et.y&&this.y+25<=et.y+25)
						{
							return true;
						}
					}
				}
			}
			break;
			
			
		}
		
		
		return b;
		
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
					if(y>0&&!this.isTouchotherEnemy()){
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
					if(x<660&&!this.isTouchotherEnemy())
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
					if(y<460&&!this.isTouchotherEnemy())
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
					if(x>0&&!this.isTouchotherEnemy())
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
	Vector<EnemyTank> ets=new Vector<EnemyTank>();
	
	
	public Hero(int x,int y)
	{
		super(x,y);
		
	}
	//子弹
	//Shot s=null;
	Vector<Shot> ss=new Vector<Shot>();
	Shot s=null;
	
	Vector<MoveShot> ms=new Vector<MoveShot>();
	MoveShot ms1=null;
	MoveShot ms2=null;
	MoveShot ms3=null;
	
	
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
	
	//按k开火
	public void moveshotEnemy()
	{
		switch(this.direct)
		{
		case 0:
			//创建一颗子弹
			ms1=new MoveShot(x+10, y,0);
			ms2=new MoveShot(x+10,y,0);
			ms3=new MoveShot(x+10,y,0);
			
			//吧子弹加入向量
			ms.add(ms1);
			ms.add(ms2);
			ms.add(ms3);
			break;
		case 1:
			ms1=new MoveShot(x+35, y+12,1);
			ms2=new MoveShot(x+35, y+12,1);
			ms3=new MoveShot(x+35, y+12,1);
			ms.add(ms1);
			ms.add(ms2);
			ms.add(ms3);
			break;
		case 2:
			ms1=new MoveShot(x+10, y+34,2);
			ms2=new MoveShot(x+10, y+34,2);
			ms3=new MoveShot(x+10, y+34,2);
			ss.add(s);
			ms.add(ms1);
			ms.add(ms2);
			ms.add(ms3);
			break;
		case 3:
			ms1=new MoveShot(x, y+12,3);
			ms2=new MoveShot(x, y+12,3);
			ms3=new MoveShot(x, y+12,3);
			ms.add(ms1);
			ms.add(ms2);
			ms.add(ms3);
			break;
			
		}
		//启动子弹线程
		Thread t1=new Thread(ms1);
		Thread t2=new Thread(ms2);
		Thread t3=new Thread(ms3);
		t1.start();
		t2.start();
		t3.start();
		
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
		
		if(x<660)
		{
		
				x+=speed;
		
		}
	}
	//坦克向下移动
		public void moveDown()
		{
			
			if(y<460)
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



