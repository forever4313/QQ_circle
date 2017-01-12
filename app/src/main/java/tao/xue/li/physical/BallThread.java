package tao.xue.li.physical;

/*
 * ����Ϊһ���������棬��Ҫ�����Ǹı�С���˶��켣������android�У�����ϵ��Y����Ϊ��
 * ȫ�ǳ��о�ѧ���������Ҿ��ô��Ӧ���ܿ���
 * ������������������ĺ�����~~~���������氡���úÿ��ɣ��Ҿ���ע����ϸ��
 */

public class BallThread extends Thread {
	Movable father;	//����һ��С���ʵ��
	boolean flag = false;	//�߳��Ƿ�ִ�еı��
	int sleepSpan = 10;	//����ʱ��
	public static float g = 4000;	//�����µļ��ٶ�
	double current;	//��¼��ǰʱ��
	double currentX;
	public BallThread(Movable father){	//��ÿ��С��Ҫ�Լ����������棬���Լ�������ȥ�˶�
		this.father = father;
		currentX = father.timeX;
		this.flag = true;	//һ����ʼ���߳̾Ϳ�ʼִ��
	}

	//run�����������������run�����������������ĺ��ģ�
	//��1����������ʽ�ƶ��ı�С��λ��
	//��2����Ⲣ����С��ﵽ��ߵ��Լ�ײ��������¼�
	//���ң����λע�⣬ÿ���׶�һ��ʼ��ʱ��Ҫ�����е�ʱ��ļ�¼����X,Y���ٶȰ���X,Yλ�ð�֮���ȫ����
	public void run(){
		while(flag){	//ֻҪ�߳��ڣ���һֱѭ��
			current = System.nanoTime();	//ȡ�õ�ǰ��ʱ��
			System.out.println("father.currentX:  "+father.currentX);
			double timeSpanX = (double)((current - father.timeX)/1000/1000/1000);
			father.currentX = (int)(father.startX + father.currentVX * timeSpanX);


			//���洦����ֱ������˶�
			if(father.bFall){	//bFall���ж�С����û�д�ľ�������������������˲��������ٶ�
				double timeSpanY = (double)((current - father.timeY)/1000/1000/1000);
				father.currentY = (int)(father.startY + father.startVY * timeSpanY + timeSpanY * timeSpanY * g/2);	//s=vt+1/2at
				father.currentVY = (float)(father.startVY + g * timeSpanY);	//������ǰ�ٶȣ���ǰ�ٶ�=v + at(��������)
				//����������ж�С���Ƿ�ﵽ��ߵ�
				if(father.startVY < 0 && Math.abs(father.currentVY) <= BallView.UP_ZERO){	//startVY<0֤��С���������˶���
					father.timeY = System.nanoTime();	//�����µ��˶��׶���ֱ����Ŀ�ʼʱ��
					father.currentVY = 0;	//�����µ��˶��׶���ֱ�����ʵʱ�ٶȣ��ﵽ��ߵ���ʱ���ٶȿ϶�Ϊ0
					father.startVY = 0;	//�����µ��˶��׶���ֱ����ĳ�ʼ�ٶȣ��ﵽ��ߵ��Ժ�ʼ�ı䷽����ʼ�ٶ�Ϊ0
					father.startY = father.currentY;	//�����µ��˶��׶���ֱ����ĳ�ʼλ�ã���ʱ�ո�Ҫ�ı��˶��������Գ�ʼλ��=��ǰλ��
				}
				//�ж�С���Ƿ����
				if(father.currentY + father.r*2 >= BallView.GROUND_LING && father.currentVY > 0){
					father.currentVX = father.currentVX * (father.impactFactoryX);	//˥��ˮƽ������ٶ�
					father.currentVY = 0 - father.currentVY * (father.impactFactory);	//˥��������ٶȣ����ı��ٶȵķ��򣨷�����
					if(Math.abs(father.currentVY) < BallView.DOWN_ZERO){	//���˥������ٶ�С��DOWN_ZERO����ֹͣ�̵߳�ִ�У�Math.adb�����ֵ
						this.flag = false;
					}else{	//���˥�����ٶȻ������͵���
						father.startX = father.currentX;	//����ʱˮƽ�������ʼλ��
						father.timeX = System.nanoTime();	//��¼����ʱ��ʱ��
						father.startY = father.currentY;
						father.timeY = System.nanoTime();
						father.startVY = father.currentVY;
					}
				}
			}else {	//��ǰλ�ü���С��뾶��һ�룬�������ֵ��С��͵�����
				//BallView.WOODEDGE��ľ��ĳ���
				//��ʼ����ˮƽ������˶�
				//��ȡˮƽ�����߹���ʱ�䣬��ǰʱ�����С���ʼ��ʱ���ʱ�䣬����ʱ������3��1000�͵õ�s��nanotime�õ�����ns
				if(father.currentVY == 0 || father.currentVY > 0){
					father.timeY = System.nanoTime();	//��¼��ֱ�����ϵĿ�ʼ�˶�ʱ��
					father.bFall = true;	//һ������ִ�����else����ִ���������if����timeY������ֵ�����ж�����
				}else{


					father.currentVY = (int)(father.startVY + g * timeSpanX);
					father.currentY = (int)(father.startVY + father.startVY * timeSpanX + timeSpanX * timeSpanX * g/2);

//					System.out.println("father.currentY:  "+father.currentY);
				}


			}

			try{
				Thread.sleep(sleepSpan);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
