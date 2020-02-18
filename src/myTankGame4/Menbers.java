package myTankGame4;

import java.util.Vector;


//��¼�࣬ͬʱҲ���Ա�����ҵ�����
class Recorder
{
	//��¼ÿ���ж��ٵ���
	private static int enNum=8;
	
	//��¼�ҹ����˶��ٴ�
	private static int deathtime=0;
	
	public static int getDeathtime() {
		return deathtime;
	}
	public static void setDeathtime(int deathtime) {
		Recorder.deathtime = deathtime;
	}
	//��¼�ܹ������˶��ٵ���
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
	//�������ж��ٿ����õ���
	private static int myLife=3;
	
	//����һ������
	public static void reduceEnNum()
	{
		enNum--;
	}
	
	//���������
	public static void addEnNumRec()
	{
		allEnNum++;
	}
	
	//�ҵ���������
	public static void adddeathtime()
	{
		deathtime++;
	}
	
	
	
	//�����ҵ�һ������
	public static void reduceHero()
	{
		myLife--;
	}
	  
	
}


//c=ը����
class Bomb
{
	//����ը��������
	int x,y;
	//ը��������
	int life=11;
	boolean isLive=true;
	public Bomb(int x,int y)
	{
		this.x=x;  
		this.y=y;
	}
	
	//��������ֵ
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

//�ӵ���
class Shot implements Runnable
{
	int x;
	int y;
	int direct;
	int speed=10;
	//�Ƿ�HIA����
	boolean isLive=true;
	public Shot(int x,int y,int direct)
	{
		this.x=x;
		this.y=y;
		this.direct=direct;
	}
	@Override
	public void run() {
		// TODO �Զ����ɵķ������
		while(true)
		{
			//��Ϣһ��ʱ��
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			switch(direct)
			{
			case 0:
				//��
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
			
			
		//�ӵ���ʱ����
			//�жϸ��ӵ��Ƿ�������Ե
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
		// TODO �Զ����ɵĹ��캯�����
	}
	
}
//̹����
class Tank  
{
	
	//̹�˵��ٶ�
	int speed=10;
	boolean isLive=true;
	

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

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
	
	//̹�˷���
	//0��ʾ��  1��ʾ���� 2��ʾ��  3��ʾ��
	  int direct=0;

	public int getDirect() {
		return direct;
	}

	public void setDirect(int direct) {
		this.direct = direct;
	}
	
	//��ɫ
	int color;

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	//̹��������
	int y=0;
	
	public  Tank(int x,int y)
	{
		this.x=x;
		this.y=y;
	}
	
	

}

//���˵�̹��
//�ɵ���̹�������߳�
class EnemyTank extends Tank implements Runnable
{
	int speed=3;
	
	int times=0;
	
	//����һ�����������Է��ʵ�MYPa������ �ĵ���̹��
	Vector<EnemyTank> ets=new Vector<EnemyTank>();
	
	//�ӵ�
		//Shot s=null;
		Vector<Shot> ss=new Vector<Shot>();
		//��������ӵ�,Ӧ���ڸոմ���̹�˺͵��˵�̹���ӵ�������
	public EnemyTank(int x,int y)
	{
		super(x,y);
	}
	
	//�õ�MYPanel�ĵ���̹������
	public void setEts(Vector<EnemyTank> vv)
	{
		this.ets=vv;
	}
	
	//�ж��Ƿ������˱��˵�̹��
	public boolean  isTouchotherEnemy()
	{
		boolean b=false;
		
		switch(this.direct)
		{
		case 0:
			//�ҵ�̹������
			//ȡ�����еĵ���̹��
			for(int i=0;i<ets.size();i++)
			{
				//ȡ����һ��̹��
				EnemyTank et=ets.get(i);
				//��������Լ�
				if(et!=this)
				{
					//������˵ķ��������»�����
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
					//������˵ķ����������������
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
			//̹������
			//ȡ�����еĵ���̹��
			for(int i=0;i<ets.size();i++)
			{
				//ȡ����һ��̹��
				EnemyTank et=ets.get(i);
				//��������Լ�
				if(et!=this)
				{
					//������˵ķ��������»�����
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
					//������˵ķ����������������
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
			//̹������
			//ȡ�����еĵ���̹��
			for(int i=0;i<ets.size();i++)
			{
				//ȡ����һ��̹��
				EnemyTank et=ets.get(i);
				//��������Լ�
				if(et!=this)
				{
					//������˵ķ��������»�����
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
					//������˵ķ����������������
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
			//̹����
			//ȡ�����еĵ���̹��
			for(int i=0;i<ets.size();i++)
			{
				//ȡ����һ��̹��
				EnemyTank et=ets.get(i);
				//��������Լ�
				if(et!=this)
				{
					//������˵ķ��������»�����
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
					//������˵ķ����������������
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
		// TODO �Զ����ɵķ������
		
		while(true)
		{
			
			switch(this.direct)
			{
			case 0:
				//˵��̹�����������ƶ�
				for(int i=0;i<30;i++)
				{
					if(y>0&&!this.isTouchotherEnemy()){
						y-=speed;
					}
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						// TODO �Զ����ɵ� catch ��
						e.printStackTrace();
					}
					
				}
				break;
			case 1:
				//����
				for(int i=0;i<50;i++)
				{
					//��֤̹�˲����߽�
					if(x<660&&!this.isTouchotherEnemy())
					{
						x+=speed;
					}
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO �Զ����ɵ� catch ��
						e.printStackTrace();
					}
				}
				break;
			case 2:
				//����
				for(int i=0;i<30;i++)
				{
					if(y<460&&!this.isTouchotherEnemy())
					{
						y+=speed;
					}
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO �Զ����ɵ� catch ��
						e.printStackTrace();
					}
				}
				break;
			case 3:
				//����
				for(int i=0;i<60;i++)
				{
					if(x>0&&!this.isTouchotherEnemy())
					{
						x-=speed;
					}
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO �Զ����ɵ� catch ��
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
						//û���ӵ�
						//���
						switch(direct)
						{
						case 0:
							//����һ���ӵ�
							s=new Shot(x+10, y,0);
							//���ӵ���������
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
						
						//���������ӵ��߳�
						Thread t=new Thread(s);
						t.start();
					}
				}
			}
			
			
			
			
			//��̹���������һ���µķ���
			this.direct=(int)(Math.random()*4);
			
			//�жϵ����Ƿ�����
			if(this.isLive==false)
			{
				//��̹���������˳��߳�
				break;
			}
			
			
			
			
		}
		
	}
}
//�ҵ�̹��
class Hero  extends Tank
{
	Vector<EnemyTank> ets=new Vector<EnemyTank>();
	
	
	public Hero(int x,int y)
	{
		super(x,y);
		
	}
	//�ӵ�
	//Shot s=null;
	Vector<Shot> ss=new Vector<Shot>();
	Shot s=null;
	
	Vector<MoveShot> ms=new Vector<MoveShot>();
	MoveShot ms1=null;
	MoveShot ms2=null;
	MoveShot ms3=null;
	
	
	//����
	public void shotEnemy()
	{
		switch(this.direct)
		{
		case 0:
			//����һ���ӵ�
			s=new Shot(x+10, y,0);
			//���ӵ���������
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
		//�����ӵ��߳�
		Thread t=new Thread(s);
		t.start();
		
	}
	
	//��k����
	public void moveshotEnemy()
	{
		switch(this.direct)
		{
		case 0:
			//����һ���ӵ�
			ms1=new MoveShot(x+10, y,0);
			ms2=new MoveShot(x+10,y,0);
			ms3=new MoveShot(x+10,y,0);
			
			//���ӵ���������
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
		//�����ӵ��߳�
		Thread t1=new Thread(ms1);
		Thread t2=new Thread(ms2);
		Thread t3=new Thread(ms3);
		t1.start();
		t2.start();
		t3.start();
		
	}
	
	//̹�������ƶ� 
	public void moveUp()
	{
		if(y>0){
			y-=speed;
		}
		
	}
	//̹�������ƶ�
	public void moveRight()
	{
		
		if(x<660)
		{
		
				x+=speed;
		
		}
	}
	//̹�������ƶ�
		public void moveDown()
		{
			
			if(y<460)
			{
				y+=speed;
			}
		}
	//̹�������ƶ�
				public void moveLeft()
				{
					
					if(x>0)
					{
						x-=speed;
					}
				}
				
			
				
}



