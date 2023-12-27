import java.util.concurrent.Semaphore;

public class Register extends Thread 
	{
		Semaphore registerSemaphore;
		Semaphore tablesSemaphore;
		Semaphore ordersSemaphore;
		Semaphore registerConfirm;
		public Register(Semaphore register,Semaphore tables,Semaphore orders,Semaphore register_confirm)
		{
			super();
			this.registerSemaphore=register;
			this.tablesSemaphore=tables;
			this.ordersSemaphore=orders;
			this.registerConfirm=register_confirm;
		}
		@Override
		public void run() 
		{
			super.run();
			try {
				while(true)
				{
					registerSemaphore.acquire();
					System.out.println("Odeme aliyorum");
					sleep(1000);
					
					tablesSemaphore.release();
					
					registerConfirm.release();
					
				}

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}