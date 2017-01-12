package tao.xue.li.physical;

import android.graphics.Bitmap;
import android.graphics.Canvas;


/*������Ƕ���һ��С����е����Ժͷ���*/
public class Movable {
	int startX = 0;
	int startY = 0;		//С��ĳ�ʼ����X,Y��С���ʵʱ����Ӧ�õ��ڳ�ʼ�����λ��
	int currentX;
	int currentY;		//ʵʱ����
//	float Ax = 0f;      //x������ٶ�
	float startVX = 0f;
	float startVY = 0f;		//��ʼʱˮƽ����ֱ������ٶ�
	float currentVX = 0f;
	float currentVY = 0f;	//ʵʱ�ٶ�
	int r;		//���ƶ�����İ뾶
	double timeX;
	double timeY;		//������X��Y���ƶ���ʱ�䣬�������һ���׶ν��뵽��һ���׶Σ������Ա�����
	Bitmap bitmap = null;		//����һ��Ҫʹ�õ�ͼƬ
	BallThread bt = null;		//�Լ�д���������棬Ϊһ���̣߳�����������ʽ��С���λ������������޸�
	boolean bFall = false;		//�ж�С���Ƿ��ľ������������
	float impactFactory = 0.5f;		//С��ײ�غ���ٶ�˥��ϵ��
	float impactFactoryX = 0.00000000001f;		//С��ײ�غ��X�ٶ�˥��ϵ��
	int finalX = 500;

	public Movable(int x, int y, float startVX,float startVY,int r, Bitmap bitmap){
		this.startX = x;
		this.startY = y;
		this.currentX = x;
		this.currentY = y;		//���캯����ʼ����ʱ�򣬳�ʼλ�ú�ʵʱλ����ͬ
		this.r = r;
		this.startVX = startVX;
		this.startVY = startVY;
		this.finalX = finalX;
		this.bitmap = bitmap;
		/*
		 * System.nanoTime��ר������������(����ʱ���)��System.nanoTime()���ص������룬nanoTime�����صĿ���������ʱ�䣬
		 * ���������Ǹ���
		 * System.currentTimeMillis()���صĺ��룬���������ʵ������1970��1��1��0ʱ��ĺ�������Date()��ʵ�����൱��
		 * Date(System.currentTimeMillis());��ΪDate�໹�й���Date(long date)����������long����1970��1��1��֮��ĺ���
		 */
		timeX = System.nanoTime();
		this.currentVX = startVX;
		this.currentVY = startVY;
		bt = new BallThread(this);
		bt.start();		//�������������������߳�
	}

	public void drawSelf(Canvas canvas){		//���Լ����ƣ�Bitmap��ͼ������������
		canvas.drawBitmap(bitmap, currentX, currentY, null);
	}
}
