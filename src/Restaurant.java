import java.util.ArrayList;
import java.util.concurrent.*;

import javax.swing.plaf.multi.MultiTextUI;

public class Restaurant extends Thread 
{	
	int normalCustomerCount;
	int priorityCustomerCount;
	public Restaurant()
	{
		super();
		
	}
	void setCustomers(int normalCustomerCount,int priorityCustomerCount)
	{
		this.normalCustomerCount=normalCustomerCount;
		this.priorityCustomerCount=priorityCustomerCount;
	}
	public void stopRestaurant(Semaphore mutex)
	{
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		super.run();
		
		int TableCount = 6;
		
		Semaphore Tables = new Semaphore(TableCount);
		Semaphore Orders = new Semaphore(0);
		Semaphore RegisterSemaphore = new Semaphore(0);
		Semaphore AwaitingOrdersSemaphore = new Semaphore(0);
		Semaphore PaymentReadyCustomersSemaphore = new Semaphore(0);
		Semaphore MealConfirmSemaphore = new Semaphore(0);
		Semaphore RegisterConfirm = new Semaphore(0);
		Semaphore OrderConfirm = new Semaphore(0);
		

		ArrayList <Customer> customers = new ArrayList<Customer>();
		for(int i=0;i<normalCustomerCount;i++) 
		{
			customers.add(new Customer(Tables, Orders,RegisterSemaphore,OrderConfirm,MealConfirmSemaphore,RegisterConfirm));
		}
		
		ArrayList<Waiter> waiters = new ArrayList<Waiter>();
		for(int i=0;i<2;i++) 
		{
			waiters.add(new Waiter(Orders, AwaitingOrdersSemaphore,OrderConfirm));
		}
		
		Register zaRegista = new Register(RegisterSemaphore, Tables, Orders,RegisterConfirm);
		
		ArrayList<Chef> chefs = new ArrayList<Chef>();
		for(int i=0;i<2;i++) 
		{
			chefs.add(new Chef(AwaitingOrdersSemaphore,MealConfirmSemaphore));
		}
		
		ArrayList <PriorityCustomer> priorityCustomers = new ArrayList<PriorityCustomer>();
		for(int i=0;i<priorityCustomerCount;i++) 
		{
			priorityCustomers.add(new PriorityCustomer(Tables, Orders,RegisterSemaphore,OrderConfirm,MealConfirmSemaphore,RegisterConfirm));
		}
		
		
	
		
		//başlatmlar
		zaRegista.start();
		for(Waiter waiter : waiters)
		{
			waiter.start();
		}
		for(Chef chef : chefs)
		{
			chef.start();
		}
		//öncelikli müştereileri normal müşterilerden önce start yapmak hangisinin daha önce başlamasını belirlemek içn yeterli gibi öncelik de içne yazdım ama o çok farketmyo sanırım
		for(PriorityCustomer pcustomer : priorityCustomers)
		{
			pcustomer.start();
		}
		for(Customer customer : customers)
		{
			customer.start();
		}
		
		//birleştirmeler
		
			for(Waiter waiter : waiters)
			{
				try {
					waiter.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			for(Chef chef : chefs)
			{
				try {
					chef.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				zaRegista.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(PriorityCustomer pcustomer : priorityCustomers)
			{
				try {
					pcustomer.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			for(Customer customer : customers)
			{
				try {
					customer.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}
}
