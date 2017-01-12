package tao.xue.li.physical;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/*
 * ����������������
 * ��1����ʱ�ػ���Ļ
 * ��2������֡����
 */

public class DrawThread extends Thread {
	BallView bv;
	SurfaceHolder surfaceHolder;
	boolean flag = false;	//�߳�ִ�б�־λ
	int sleepSpan = 30;	//����ʱ�� 
	long start = System.nanoTime();	//��¼��ʼʱ�䣬�ñ������ڼ���֡����
	int count = 0;	//��¼֡�����ñ������ڼ���֡����
	
	public DrawThread(BallView bv, SurfaceHolder surfaceHolder){
		this.bv = bv;
		this.surfaceHolder = surfaceHolder;
		this.flag = true;
	}
	
	public void run(){
		Canvas canvas = null;
		while(flag){
			try{
				canvas = surfaceHolder.lockCanvas(null);	//��ȡBallview�Ļ���
				synchronized(surfaceHolder){	//������
					bv.doDraw(canvas);	//����BallView��doDraw�������л���
				}
			}catch(Exception e){
				e.printStackTrace();	
			}finally{
				if(canvas != null){
					surfaceHolder.unlockCanvasAndPost(canvas);	//�ж�һ�£����������Ϊ�գ��ͽ�������
				}
			}
			this.count++;
			if(count == 20){	//�������20֡
				count = 0;	//��ռ�����
				long tempStamp = System.nanoTime();
				long span = tempStamp - start;	//��¼ʱ������������20֡��Ҫ��ʱ��
				start = tempStamp;
				double fps = Math.round(100000000000.0 / span * 20)/100.0;	//����֡����
																			//��100s�ڰ�����ʱ������span)����*20����100s�ܻ��Ƶ�֡����
				bv.fps = "FPS:" + fps;	//��֡����
			}
			try{
				Thread.sleep(sleepSpan);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
