import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class PriorityCustomer extends Customer
	{
		public PriorityCustomer(Semaphore tables,Semaphore orders,Semaphore register,Semaphore order_confirm,Semaphore meal_confirm,Semaphore register_confirm)
		{
			super(tables,orders,register,order_confirm,meal_confirm,register_confirm);
		}
		@Override
		public void run() 
		{
			super.run();
			
		}
	}