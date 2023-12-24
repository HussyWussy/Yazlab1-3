
import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.*; 

public class Main
{

	
	public static void main(String args[]) throws InterruptedException 
	{
		int TableCount = 6;
		
		//burda kullandığım semaphore sistemi çok ilkel çift taraflı gibi davranan kaliteli bir sisteme geçiş yapılması gerek
		//bir semafor diğerinden diğer semafor da diğerinden istek istiyerek gibi çünkü altındaki kodun diğer koşul da gerçekleştiğinde çalışabilmesi acquire olması gerekiyor
		//o zaman bütün sorunlar halloluyor ve gui zart zurt problem 2 kalıyor
		Semaphore Tables = new Semaphore(TableCount);
		System.out.println(Tables.isFair());
		Semaphore Orders = new Semaphore(0);
		Semaphore RegisterSemaphore = new Semaphore(0);
		Semaphore AwaitingOrdersSemaphore = new Semaphore(0);
		Semaphore PaymentReadyCustomersSemaphore = new Semaphore(0);
		Semaphore MealConfirmSemaphore = new Semaphore(0);
		Semaphore RegisterConfirm = new Semaphore(0);
		Semaphore OrderConfirm = new Semaphore(0);
		
		
		
		//ayarlamalar
		ArrayList <Customer> customers = new ArrayList<Customer>();
		for(int i=0;i<10;i++) 
		{
			customers.add(new Customer(Tables, Orders,RegisterSemaphore,OrderConfirm,MealConfirmSemaphore,RegisterConfirm));
		}
		
		ArrayList<Waiter> waiters = new ArrayList<Waiter>();
		for(int i=0;i<3;i++) 
		{
			waiters.add(new Waiter(Orders, AwaitingOrdersSemaphore,OrderConfirm));
		}
		
		Register zaRegista = new Register(RegisterSemaphore, Tables, Orders,RegisterConfirm);
		
		ArrayList<Chef> chefs = new ArrayList<Chef>();
		for(int i=0;i<2;i++) 
		{
			chefs.add(new Chef(AwaitingOrdersSemaphore,MealConfirmSemaphore));
		}
		
		
		PriorityCustomer gustavoFring = new PriorityCustomer(Tables, Orders, RegisterSemaphore,OrderConfirm ,MealConfirmSemaphore, RegisterConfirm );
		
		
		
		//başlatmlar
		gustavoFring.start();
		zaRegista.start();
		//öncelikli müştereileri normal müşterilerden önce start yapmak hangisinin daha önce başlamasını belirlemek içn yeterli gibi öncelik de içne yazdım ama o çok farketmyo sanırım
		
		for(Customer customer : customers)
		{
			customer.start();
		}
		for(Waiter waiter : waiters)
		{
			waiter.start();
		}
		for(Chef chef : chefs)
		{
			chef.start();
		}
		//birleştirmeler
		
		
		
		
		zaRegista.join();
		for(Customer customer : customers)
		{
			customer.join();
		}
		for(Waiter waiter : waiters)
		{
			waiter.join();
		}
		for(Chef chef : chefs)
		{
			chef.join();
		}
		gustavoFring.join();
		
	}
}
