import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;

public class PriorityCustomer extends Customer
	{
		public PriorityCustomer(Semaphore tables,Semaphore orders,Semaphore register,Semaphore order_confirm,Semaphore meal_confirm,Semaphore register_confirm,Semaphore gotOrder,GUI Interface,BufferedWriter filewriter,Semaphore PrioritySemaphore)
		{
			super(tables,orders,register,order_confirm,meal_confirm,register_confirm,gotOrder,Interface,filewriter,PrioritySemaphore);
			setPriority(MAX_PRIORITY);
			System.out.println(threadId()+" id li thread öncelikli olan");
			customerButton.setBorderPainted(true);
			customerButton.setForeground(Color.orange);
			PrioritySemaphore.release();
		}
		@Override
		public void run() 
		{
			
			super.run();
		}
	}