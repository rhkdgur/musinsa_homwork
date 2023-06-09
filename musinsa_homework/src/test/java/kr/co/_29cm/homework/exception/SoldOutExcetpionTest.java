package kr.co._29cm.homework.exception;

import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import kr.co._29cm.homework.modules.product.dto.ProductDTO;
import kr.co._29cm.homework.modules.product.service.ProductService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback(false)
public class SoldOutExcetpionTest {
	
	@Autowired
	private ProductService productService;

	@Test
	void SoldOutExceptionTest() {
		try {
			ProductDTO productDTO = new ProductDTO();
			productDTO.setProductNum("768848");
			
			productDTO = productService.selectProduct(productDTO);
			//�ֹ�1
			Runnable order1 = new MultiThread("order1",productDTO);
			Thread th1 = new Thread(order1);
			//�ֹ�2
			Runnable order2 = new MultiThread("order2",productDTO);
			Thread th2 = new Thread(order2);
			
			th1.start();
			th2.start();
			
			
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	class MultiThread implements Runnable{
		
		private String gubun = "";
		
		private ProductDTO productDTO;
		
		private MultiThread(String gubun,ProductDTO productDTO) {
			this.gubun = gubun;
			this.productDTO = productDTO;
		}

		@Override
		public void run() {

			while(true) {
					try {	
						//���� ����
						int cnt = new Random().nextInt(10);
						int totalCnt = (productDTO.getCnt() - cnt);
						
						//���� ���� �� ������
						System.out.println("### ���� ���� : "+cnt+",���� ���� : "+totalCnt+", ������ ���� : "+this.gubun);
						if(totalCnt < 0) {
							throw new SoldOutException("SoldOutException �߻�. �ֹ��� ��ǰ���� ������� Ů�ϴ�. ������ ���� : "+this.gubun);
						}					
							
					}catch (Exception e) {
						System.err.println(e.getMessage());
						break;
					}
			}
		}
		
	}

}


