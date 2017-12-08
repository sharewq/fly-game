import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Random;

import javax.swing.JPanel;

//��Ϸ������

public class GamePanel extends JPanel implements Runnable,KeyListener{

	//�߿�
	private int mScreenWidth=350;
	private int mScreenHeight=480;

	private static final int STATE_GAME=0;
	//��Ϸ��״̬
	private int mState=STATE_GAME;
	//����ͼƬ
	private Image mBitMenuBG0=null;
	private Image mBitMenuBG1=null;
	//����ͼƬ��y����
	private int mBitposY0=0;
	private int mBitposY1=0;
	//�ӵ����������
	final static int BULLET_POOL_COUNT=15;
	//�ɻ��ƶ��Ĳ���
	final static int PLAN_STEP=10;
	//ÿ500ms����һ���ӵ�
	final static int PLAN_TIME=500;
	//���˶��������
	final static int ENEMY_POOL_COUNT=5;

	final static int ENEMY_POS_OFF=65;
	private static final int BULLET_UP_OFFSET = 0;
	private static final int BULLET_LEFT_OFFSET = -20;

	private Thread mThread=null;

	private boolean mIsRunning=false;

	public int mAirPosX=0;
	public int mAirPosY=0;
	//�л���������
	Enemy mEnemy[]=null;

	Bullet mBuilet[]=null;
	public int mSendId=0;
	public Long mSendTime=0L;

	Image myPlanePic[];	//����ͼƬ��Դ
	public int myPlaneID=0;//��ҵ�ǰ��֡��

	public GamePanel(){
		setPreferredSize(new Dimension(mScreenWidth,mScreenHeight));
		setFocusable(true);
		addKeyListener(this);

		init();//��ʼ������

		setGameState(STATE_GAME);
		mIsRunning=true;
		mThread=new Thread(this);
		//�̲߳�����
		mThread.start();
		setVisible(true);

	}

	private void setGameState(int stateGame) {
		mState=stateGame;
		// TODO Auto-generated method stub

	}

	private void init() {
		try{
			//��Ϸ����ͼƬ
			mBitMenuBG0=Toolkit.getDefaultToolkit().getImage("images\\map_0.png");
			mBitMenuBG1=Toolkit.getDefaultToolkit().getImage("images\\map_1.png");
		}catch(Exception e){       //????????????????????IOException
			e.printStackTrace();
		}
		mBitposY0=0;
		mBitposY1=-mScreenHeight;
		//��ʼ��ҷɻ�������
		mAirPosX=150;
		mAirPosY=400;

		myPlanePic=new Image[6];
		//for(int i=0;i<6;i++){
			myPlanePic[0]=Toolkit.getDefaultToolkit().getImage("images\\plan_"+0+".png");
		//}


		//�л�����
		mEnemy=new Enemy[ENEMY_POOL_COUNT];
		for(int i=0;i<ENEMY_POOL_COUNT;i++){
			mEnemy[i]=new Enemy();
			mEnemy[i].init(i*ENEMY_POS_OFF,i*ENEMY_POS_OFF-300);
		}

		//�ӵ��Ķ���
		mBuilet=new Bullet[BULLET_POOL_COUNT];
		for(int i=0;i<BULLET_POOL_COUNT;i++){
			mBuilet[i]=new Bullet();

		}
		mSendTime=System.currentTimeMillis();


		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		while(mIsRunning){
			Draw();
			try{
				Thread.sleep(100);

			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}

		// TODO Auto-generated method stub

	}

	private void Draw() {
		switch(mState){
		case STATE_GAME:
			renderBg();//������Ϸ����
			updateBg();//������Ϸ�߼�
			break;
		}

		// TODO Auto-generated method stub

	}

	private void updateBg() {
		mBitposY0+=10;
		mBitposY1+=10;
		if(mBitposY0==mScreenHeight){
			mBitposY0=-mScreenHeight;
		}
		if(mBitposY1==mScreenHeight){
			mBitposY1=-mScreenHeight;
		}
		//�����ӵ�λ��
		for(int i=0;i<ENEMY_POOL_COUNT;i++){
			mBuilet[i].UpdateBullet();
		}
		//���µл�λ��
		for(int i=0;i<ENEMY_POOL_COUNT;i++){
			mEnemy[i].UpdateEnemy();
			if(mEnemy[i].mAnimState==Enemy.ENEMY_DEATH_STATE && mEnemy[i].mPlayID==6||mEnemy[i].m_posY>=mScreenHeight){
				mEnemy[i].init(UtilRandom(0,ENEMY_POOL_COUNT)*ENEMY_POS_OFF, 0);
			}
		}
		//�����ӵ�
		if(mSendId<BULLET_POOL_COUNT){
			long now=System.currentTimeMillis();
			if(now-mSendTime>=PLAN_TIME){
				mBuilet[mSendId].init(mAirPosX - BULLET_LEFT_OFFSET,mAirPosY-BULLET_UP_OFFSET);
				mSendTime=now;
				mSendId++;

			}else{
				mSendId=0;
			}
			Collision();
		}


		// TODO Auto-generated method stub

	}

	private void Collision() {

		for(int i=0;i<BULLET_POOL_COUNT;i++){
			for(int j=0;j<ENEMY_POOL_COUNT;j++){
				if(mBuilet[i].m_posX>=mEnemy[j].m_posX
						&&mBuilet[i].m_posX<=mEnemy[j].m_posX+30
						&&mBuilet[i].m_posY>=mEnemy[j].m_posY
						&&mBuilet[i].m_posY<=mEnemy[j].m_posY+30
						){
					mEnemy[j].mAnimState=Enemy.ENEMY_DEATH_STATE;
				}
			}
		}
		// TODO Auto-generated method stub

	}

	private int UtilRandom(int botton, int top) {
		// TODO Auto-generated method stub
		return ((Math.abs(new Random().nextInt())%(top-botton))+botton);
	}

	private void renderBg() {
		myPlaneID++;
		if(myPlaneID==6)
			myPlaneID=0;
		repaint();

		// TODO Auto-generated method stub

	}
	public void paint(Graphics g){
		//��Ϸ��ͼ
		g.drawImage(mBitMenuBG0,0,mBitposY0,this);
		g.drawImage(mBitMenuBG1,0,mBitposY1,this);
		//�Լ��ɻ�����
		g.drawImage(myPlanePic[myPlaneID], mAirPosX, mAirPosY,this);
		for(int i=0;i<BULLET_POOL_COUNT;i++)
		{
			mBuilet[i].DrawBullet(g,this);
		}
		for(int i=0;i<ENEMY_POOL_COUNT;i++)
		{
			mEnemy[i].DrawEnemy(g,this);
		}


	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key=e.getKeyCode();
		System.out.println(key);
		if(key==KeyEvent.VK_UP)
			mAirPosY-=PLAN_STEP;
		if(key==KeyEvent.VK_DOWN)
			mAirPosY+=PLAN_STEP;
		if(key==KeyEvent.VK_LEFT)
		{
			mAirPosX-=PLAN_STEP;
			if(mAirPosX<0)
				mAirPosX=0;
		}
		if(key==KeyEvent.VK_RIGHT)
		{
			mAirPosX+=PLAN_STEP;
			if(mAirPosX>mScreenWidth-30)
				mAirPosX=mScreenWidth-30;
		}
		System.out.println(mAirPosX+":"+mAirPosY);

		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
