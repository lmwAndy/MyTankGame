package myTankgame3;

import java.util.Vector;

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
			if(x<0||x>800||y<0||y>600)
			{
				this.isLive=false;
				break;
			}
		}
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
	
	//�ӵ�
		//Shot s=null;
		Vector<Shot> ss=new Vector<Shot>();
		//��������ӵ�,Ӧ���ڸոմ���̹�˺͵��˵�̹���ӵ�������
	public EnemyTank(int x,int y)
	{
		super(x,y);
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
					if(y>0){
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
					if(x<760)
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
					if(y<560)
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
					if(x>0)
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
	
	public Hero(int x,int y)
	{
		super(x,y);
		
	}
	//�ӵ�
	//Shot s=null;
	Vector<Shot> ss=new Vector<Shot>();
	Shot s=null;
	
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
		
		if(x<760)
		{
		
				x+=speed;
		
		}
	}
	//̹�������ƶ�
		public void moveDown()
		{
			
			if(y<560)
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



