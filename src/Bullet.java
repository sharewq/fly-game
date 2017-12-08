import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;


public class Bullet {
	
	static final int BULLET_STEP_X=3;
	static final int BULLET_STEP_Y=15;
	static final int BULLET_WIDTH=40;
	
	 
	
	public int m_posX=0;
	public int m_posY=-20;
	
	boolean mFacus=true;
	private Image pic[]=null;
	private int mPlayID=0;
	
	public Bullet(){
		pic=new Image[4];
		for(int i=0;i<4;i++){
			pic[i]=Toolkit.getDefaultToolkit().getImage("images\\bullet_"+i+".png");
		}
	}
	//初始化坐标
	public void init(int x,int y){
		m_posX=x;
		m_posY=y;
		mFacus=true;
	}
	//子弹
	public void DrawBullet(Graphics g, GamePanel i) {
		g.drawImage(pic[mPlayID++], m_posX, m_posY, (ImageObserver)i);
		if(mPlayID==4)
			mPlayID=0;
		// TODO Auto-generated method stub
		
	}

	public void UpdateBullet() {
		if(mFacus)
			m_posY-=BULLET_STEP_Y;
		// TODO Auto-generated method stub
		
	}

}
