import java.awt.Color;
import java.util.concurrent.Semaphore;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Chef extends Thread
	{
	
		Semaphore awaitingOrdersSemaphore;
		Semaphore mealConfirm;
		JPanel waitersPanel;
		JPanel chefsPanel;
		JPanel registersPanel;
		GUI Interface;
		JButton cookingslotbutton;
		JButton cookingslotbutton2;
		private Semaphore currentlyCookingSemaphore;
		public Chef(Semaphore awaiting_orders_semaphore,Semaphore meal_confirm,GUI Interface) 
		{
			super();
			this.awaitingOrdersSemaphore=awaiting_orders_semaphore;
			this.currentlyCookingSemaphore = new Semaphore(2);
			this.mealConfirm=meal_confirm;
			this.waitersPanel=Interface.getWaitersPanel();
			this.chefsPanel=Interface.getChefsPanel();
			this.registersPanel=Interface.getRegistersPanel();
			this.Interface=Interface;
			
			this.cookingslotbutton = new JButton();
			
			chefsPanel.add(cookingslotbutton);
			
			
			this.cookingslotbutton2 = new JButton();
			
			
			chefsPanel.add(cookingslotbutton2);
			
			Interface.repaint();
			Interface.setVisible(true);
			Interface.repaint();
			
		}
		
		
		
		
		//aşçının içinde ayrı class var
		private class CookingSlot extends Thread
		{
			Semaphore currentlyCookingSemaphore;
			Semaphore awaitingOrdersSemaphore;
			Semaphore mealConfirm;
			JButton cookingslotbutton;
			public CookingSlot(Semaphore currently_cooking,Semaphore awaiting_orders,Semaphore meal_confirm,JButton cookingButton)
			{
				super();
				currentlyCookingSemaphore = currently_cooking;
				this.awaitingOrdersSemaphore=awaiting_orders;
				this.mealConfirm=meal_confirm;
				this.cookingslotbutton=cookingButton;
				
				
			}
			@Override
			public void run()
			{
				super.run();
				try
				{
					
						awaitingOrdersSemaphore.acquire();
						currentlyCookingSemaphore.acquire();
						
						cookingslotbutton.setBackground(Color.green);
						
						
						sleep(3000);
						
						cookingslotbutton.setBackground(Color.gray);
						
						
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
				cookingslotbutton.setText(String.valueOf(currentThread().threadId()));
				cookingslotbutton2.setText(String.valueOf(currentThread().threadId()));
				while(true)
				{
					//kendisinin boş yerinin olup olmamasını kontrol eder
					//garsonun yap reis demesini bekler
					CookingSlot slot1 = new CookingSlot(currentlyCookingSemaphore,awaitingOrdersSemaphore,mealConfirm,cookingslotbutton);
					CookingSlot slot2 = new CookingSlot(currentlyCookingSemaphore,awaitingOrdersSemaphore,mealConfirm,cookingslotbutton2);
					
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
	