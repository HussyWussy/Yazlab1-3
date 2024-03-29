import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
		
		private Semaphore currentlyCookingSemaphore;
		
		ArrayList<CookingSlot> cookingslots = new ArrayList<Chef.CookingSlot>();
		ArrayList<JButton> cookingslotButtons = new ArrayList<JButton>();
		
		private int slotCount = 2;
	
		BufferedWriter filewriter;
		
		public Chef(Semaphore awaiting_orders_semaphore,Semaphore meal_confirm,GUI Interface , BufferedWriter filewriter) 
		{
			super();
			this.awaitingOrdersSemaphore=awaiting_orders_semaphore;
			this.currentlyCookingSemaphore = new Semaphore(2);
			this.mealConfirm=meal_confirm;
			this.waitersPanel=Interface.getWaitersPanel();
			this.chefsPanel=Interface.getChefsPanel();
			this.registersPanel=Interface.getRegistersPanel();
			this.Interface=Interface;
			
			this.filewriter = filewriter;
			
			for(int i = 0;i<slotCount;i++)
			{
				JButton cookingButton = new JButton();
				cookingslotButtons.add(cookingButton);
				
				CookingSlot cslot = new CookingSlot(currentlyCookingSemaphore,awaiting_orders_semaphore, meal_confirm, cookingButton,threadId());
				
				cookingslots.add(cslot);
				
				chefsPanel.add(cookingButton);
				
			}
			
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
			long parentThreadId;
			public CookingSlot(Semaphore currently_cooking,Semaphore awaiting_orders,Semaphore meal_confirm,JButton cookingButton,long parentThreadId)
			{
				super();
				currentlyCookingSemaphore = currently_cooking;
				this.awaitingOrdersSemaphore=awaiting_orders;
				this.mealConfirm=meal_confirm;
				this.cookingslotbutton=cookingButton;
				this.parentThreadId = parentThreadId;
				
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
						
						try {
							filewriter.write(parentThreadId + "li aşçının" + this.threadId()+" id li ocakta yemek pişiyor ");
							filewriter.newLine();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						sleep(3000);
						try {
							filewriter.write(parentThreadId + "li aşçının" + this.threadId()+" id li ocakta yemek pişti ");
							filewriter.newLine();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						cookingslotbutton.setBackground(Color.gray);
						
						
						currentlyCookingSemaphore.release();
						mealConfirm.release();

						
					
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		
		public void stopCooking()
		{
			for(CookingSlot cs : cookingslots)
			{
				cs.suspend();
			}
		}
		
		public void startCooking()
		{
			for(CookingSlot cs : cookingslots)
			{
				cs.resume();
			}
		}
		
		
		
		
		
		@Override
		public void run() 
		{
			super.run();
			try {
				
				while(true)
				{
					for(JButton btn :cookingslotButtons)
					{
						btn.setText(String.valueOf(currentThread().threadId()));
					}
					//kendisinin boş yerinin olup olmamasını kontrol eder
					//garsonun yap reis demesini bekler
					//burası çok kötü oldu düzeltilebilir
					cookingslots.clear();
					for(int i = 0;i<slotCount;i++)
					{
						
						JButton cookingButton = cookingslotButtons.get(i);
						
						CookingSlot cslot = new CookingSlot(currentlyCookingSemaphore,awaitingOrdersSemaphore, mealConfirm, cookingButton , threadId());
						
						cookingslots.add(cslot);
						
					}
					
					
					for(CookingSlot cs : cookingslots)
					{
						cs.start();
					}
					
					for(CookingSlot cs : cookingslots)
					{
						cs.join();
					}
					
				
				}
			} 
			catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	