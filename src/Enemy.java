import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;


public class Enemy {
	public static final Object ENEMY_DEATH_STATE = 1;
	public static final Object ENEMY_ALIVE_STATE = 0;
	static final int ENEMY_STEP_Y=5;
		
	public int m_posY=0;
	public int m_posX=0;
	
	public Object mAnimState=ENEMY_ALIVE_STATE;
	private Image enemyExplorePic[]=new Image[6];
	public int mPlayID;
	
	public Enemy()
	{
		for(int i=0;i<6;i++){
			enemyExplorePic[i]=Toolkit.getDefaultToolkit().getImage("images\\bomb_enemy_"+i+".png");
		}
	}
	
	public void init(int x, int y) {
		m_posX=x;
		m_posY=y;
		mAnimState=ENEMY_ALIVE_STATE;
		mPlayID=0;
		// TODO Auto-generated method stub
		
	}
		//»æÖÆµÐ»ú
	public void DrawEnemy(Graphics g, GamePanel i) {
		if(mAnimState==ENEMY_DEATH_STATE && mPlayID<6){
			g.drawImage(enemyExplorePic[mPlayID], m_posX, m_posY, (ImageObserver)i);
			mPlayID++;
			return;
		}
		Image pic=Toolkit.getDefaultToolkit().getImage("images/e1_0.png");
		g.drawImage(pic, m_posX, m_posY, (ImageObserver)i);
		// TODO Auto-generated method stub
		
	}

	public void UpdateEnemy() {
		m_posY+=ENEMY_STEP_Y;
		// TODO Auto-generated method stub
		
	}

}
