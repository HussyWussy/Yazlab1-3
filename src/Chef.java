import java.util.concurrent.Semaphore;

public class Chef extends Thread
	{
	
		Semaphore awaitingOrdersSemaphore;
		Semaphore mealConfirm;
		private Semaphore currentlyCookingSemaphore;
		public Chef(Semaphore awaiting_orders_semaphore,Semaphore meal_confirm) 
		{
			super();
			this.awaitingOrdersSemaphore=awaiting_orders_semaphore;
			this.currentlyCookingSemaphore = new Semaphore(2);
			this.mealConfirm=meal_confirm;
		}
		
		
		
		
		//aşçının içinde ayrı class var
		private class CookingSlot extends Thread
		{
			Semaphore currentlyCookingSemaphore;
			Semaphore awaitingOrdersSemaphore;
			Semaphore mealConfirm;
			public CookingSlot(Semaphore currently_cooking,Semaphore awaiting_orders,Semaphore meal_confirm)
			{
				super();
				currentlyCookingSemaphore = currently_cooking;
				this.awaitingOrdersSemaphore=awaiting_orders;
				this.mealConfirm=meal_confirm;
			}
			@Override
			public void run()
			{
				super.run();
				try
				{

						awaitingOrdersSemaphore.acquire();
						currentlyCookingSemaphore.acquire();
						

						sleep(3000);

						currentlyCookingSemaphore.release();
						mealConfirm.release();

						
					
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		
		
		
		
		
		
		
		@Override
		public void run() 
		{
			super.run();
			try {
				while(true)
				{
					//kendisinin boş yerinin olup olmamasını kontrol eder
					//garsonun yap reis demesini bekler
					CookingSlot slot1 = new CookingSlot(currentlyCookingSemaphore,awaitingOrdersSemaphore,mealConfirm);
					CookingSlot slot2 = new CookingSlot(currentlyCookingSemaphore,awaitingOrdersSemaphore,mealConfirm);
					
					slot1.start();
					slot2.start();
					
					slot1.join();
					slot2.join();
				
				
				}
			} 
			catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	