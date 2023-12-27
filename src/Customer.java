import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

class Customer extends Thread
	{
		Semaphore tablesSemaphore;
		Semaphore ordersSemaphore;
		Semaphore registerSemaphore;
		Semaphore mealConfirm;
		Semaphore registerConfirm;
		Semaphore orderConfirm;
		
		public Customer(Semaphore tables,Semaphore orders,Semaphore register,Semaphore order_confirm,Semaphore meal_confirm,Semaphore register_confirm)
		{
			super();
			setPriority(MIN_PRIORITY);
			this.tablesSemaphore=tables;
			this.ordersSemaphore=orders;
			this.registerSemaphore=register;
			this.mealConfirm=meal_confirm;
			this.orderConfirm=order_confirm;
			this.registerConfirm=register_confirm;
		}
		@Override
		public void run()
		{
			super.run();
			try {
				if(tablesSemaphore.tryAcquire(20L, TimeUnit.SECONDS))
				{
					System.out.println(this.threadId()+"numarali musteri diyor ki : Oturdum da");
					//yemek getirin da
					ordersSemaphore.release();
					orderConfirm.acquire();
					//burda garson sipariş alıyor
					
					System.out.println(this.threadId()+"numarali musteri diyor ki : Siparisim alınıyor da");
					//müşteri yemeğin gelmesini bekliyor
					mealConfirm.acquire();
					
					System.out.println(this.threadId()+"numarali musteri diyor ki : Yemegimi yiyorum da");
					//System.out.println(this.threadId()+"numaralı müşteri diyor ki : Yemeğimi yiyom  da");
					//müşterinin yemeği geldi yemeğinin yiyor
					
					
					sleep(3000);
					
					//System.out.println(this.threadId()+"numaralı müşteri diyor ki : Kasayı bekliyom da  da");
					//müşteri ödeme yapmayı bekliyor ödemeyi yaptıktan sonra çıkıp gidicektir 
					System.out.println(this.threadId()+"numarali musteri diyor ki : Yemeğim bitti da");
					
					registerSemaphore.release();
					registerConfirm.acquire();
					System.out.println(this.threadId()+"numarali musteri diyor ki : bb da");
					//müşteri gidiyor bb
					//kasa halledicektir masa açımını
					
				}
				else {
					System.out.println(this.threadId()+"numaralı müşteri diyor ki : Adios");
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}