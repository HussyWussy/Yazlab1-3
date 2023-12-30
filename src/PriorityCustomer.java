import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;

public class PriorityCustomer extends Customer
	{
		public PriorityCustomer(Semaphore tables,Semaphore orders,Semaphore register,Semaphore order_confirm,Semaphore meal_confirm,Semaphore register_confirm,Semaphore gotOrder,GUI Interface)
		{
			super(tables,orders,register,order_confirm,meal_confirm,register_confirm,gotOrder,Interface);
			setPriority(MAX_PRIORITY);
			System.out.println(threadId()+" id li thread öncelikli olan");
			customerButton.setBorderPainted(true);
		}
		@Override
		public void run() 
		{
			super.run();
		}
	}