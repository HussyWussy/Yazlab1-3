import java.util.concurrent.Semaphore;

public class Waiter extends Thread
	{
		Semaphore ordersSemaphore;
		Semaphore awaitingOrdersSemaphore;
		Semaphore orderConfirmSemaphore;
		public Waiter(Semaphore orders,Semaphore awaiting_orders,Semaphore order_confirm)
		{
			super();
			this.ordersSemaphore=orders;
			this.awaitingOrdersSemaphore=awaiting_orders;
			this.orderConfirmSemaphore=order_confirm;
			
		}
		@Override
		public void run() 
		{
			super.run();
			try 
			{
				while(true)
				{
					//müşterinin gel kral demesini bekler
					ordersSemaphore.acquire();
					orderConfirmSemaphore.release();
					
					sleep(2000);
					//aşçıya yap reis der

					awaitingOrdersSemaphore.release();
				}
				

			} catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}