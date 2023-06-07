package kr.co._29cm.homework.exception;

import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback(false)
public class SoldOutExcetpionTest {
	
	static int maxLimit = 10000;

	@Test
	void SoldOutExceptionTest() {
		try {
			
			Runnable r1 = new MultiThread("A");
			Thread tA = new Thread(r1);
			Runnable r2 = new MultiThread("B");
			Thread tB = new Thread(r2);
			
			tA.start();
			tB.start();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	class MultiThread implements Runnable{
		
		private String gubun = "";
		
		private MultiThread(String gubun) {
			this.gubun = gubun;
		}

		@Override
		public void run() {

			while(true) {
				try {
					int cnt = new Random().nextInt(100);
					maxLimit -= cnt;
					if(maxLimit < 0) {
						throw new SoldOutException("SoldOutException �߻�. �ֹ��� ��ǰ���� ������� Ů�ϴ�.");
					}
					System.out.println("### �� ���� : "+maxLimit+", ���� ���� : "+cnt+", ������ ���� : "+this.gubun);
				}catch (SoldOutException e) {
					System.out.println(e.getMessage());
					break;
				}
			}
		}
		
	}

}


