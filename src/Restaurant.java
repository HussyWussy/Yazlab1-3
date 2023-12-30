import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.*;

import javax.swing.JPanel;
import javax.swing.plaf.multi.MultiTextUI;

public class Restaurant extends Thread 
{
	int normalCustomerCount;
	int priorityCustomerCount;
	
	int TableCount = 6;
	int WaiterCount = 3;
	int RegisterCount = 1;
	int ChefCount = 2;
	boolean paused = false;
	
	Semaphore Tables;
	
	Semaphore Orders ;
	Semaphore RegisterSemaphore ;
	Semaphore AwaitingOrdersSemaphore ;
	Semaphore PaymentReadyCustomersSemaphore ;
	Semaphore MealConfirmSemaphore ;
	Semaphore RegisterConfirm ;
	Semaphore OrderConfirm ;
	Semaphore GotOrder;
	
	
	
	JPanel waitersPanel;
	JPanel chefsPanel;
	JPanel registersPanel;
	GUI Interface;

	ArrayList <Customer> customers = new ArrayList<Customer>();
	ArrayList<Waiter> waiters = new ArrayList<Waiter>();
	ArrayList<Register> registers = new ArrayList<Register>();
	ArrayList<Chef> chefs = new ArrayList<Chef>();
	ArrayList <PriorityCustomer> priorityCustomers = new ArrayList<PriorityCustomer>();
	
	public ArrayList<Customer> getCustomers() {
		return customers;
	}
	public ArrayList<Waiter> getWaiters() {
		return waiters;
	}
	public ArrayList<Register> getRegisters() {
		return registers;
	}
	public ArrayList<Chef> getChefs() {
		return chefs;
	}
	public ArrayList<PriorityCustomer> getPriorityCustomers() {
		return priorityCustomers;
	}
	
	BufferedWriter filewriter;
	
	public Restaurant(GUI Interface,BufferedWriter filewriter)
	{
		super();
		this.waitersPanel=Interface.getWaitersPanel();
		this.chefsPanel=Interface.getChefsPanel();
		this.registersPanel=Interface.getRegistersPanel();
		this.Interface=Interface;
		this.filewriter = filewriter;
	}
	void setCustomers(int normalCustomerCount,int priorityCustomerCount)
	{
		this.normalCustomerCount=normalCustomerCount;
		this.priorityCustomerCount=priorityCustomerCount;
	}
	
	void pauseAll()
	{
		
		for(Customer c : customers)
		{
			c.suspend();
		}
		for(PriorityCustomer pc : priorityCustomers)
		{
			pc.suspend();
		}
		for(Waiter w : waiters)
		{
			w.suspend();
		}
		for(Register r : registers)
		{
			r.suspend();
		}
		for(Chef cf : chefs)
		{
			cf.stopCooking();
			cf.suspend();
		}
		paused=true;
	}
	
	void resumeAll()
	{
		
		for(Customer c : customers)
		{
			c.resume();
		}
		for(PriorityCustomer pc : priorityCustomers)
		{
			pc.resume();
		}
		for(Waiter w : waiters)
		{
			w.resume();
		}
		for(Register r : registers)
		{
			r.resume();
		}
		for(Chef cf : chefs)
		{
			cf.resume();
			cf.startCooking();
		}
		paused=false;
	}
	
	@Override
	public void run() {
		super.run();
		
		
		Tables = new Semaphore(TableCount);
		
		Orders = new Semaphore(0,true);
		RegisterSemaphore = new Semaphore(0,true);
		AwaitingOrdersSemaphore = new Semaphore(0,true);
		PaymentReadyCustomersSemaphore = new Semaphore(0,true);
		MealConfirmSemaphore = new Semaphore(0,true);
		RegisterConfirm = new Semaphore(0,true);
		OrderConfirm = new Semaphore(0,true);
		GotOrder = new Semaphore(0,true);
		

		for(int i=0;i<normalCustomerCount;i++) 
		{
			customers.add(new Customer(Tables, Orders,RegisterSemaphore,OrderConfirm,MealConfirmSemaphore,RegisterConfirm,GotOrder,Interface,filewriter));
		}
		

		for(int i=0;i<WaiterCount;i++) 
		{
			waiters.add(new Waiter(Orders, AwaitingOrdersSemaphore,OrderConfirm,GotOrder,Interface,filewriter));
		}
		

		for(int i=0;i<RegisterCount;i++) 
		{
			registers.add(new Register(RegisterSemaphore, Tables, Orders,RegisterConfirm,Interface,filewriter));
		}
		

		for(int i=0;i<ChefCount;i++) 
		{
			chefs.add(new Chef(AwaitingOrdersSemaphore,MealConfirmSemaphore,Interface,filewriter));
		}
		

		for(int i=0;i<priorityCustomerCount;i++) 
		{
			priorityCustomers.add(new PriorityCustomer(Tables, Orders,RegisterSemaphore,OrderConfirm,MealConfirmSemaphore,RegisterConfirm,GotOrder,Interface,filewriter));
		}
		
		
	
		
		//başlatmlar
		for(Register register : registers)
		{
			register.start();
		}
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
			try {
				filewriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
			for(Register register : registers)
			{
				try {
					register.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
	}
	public int getNormalCustomerCount() {
		return normalCustomerCount;
	}
	public int getPriorityCustomerCount() {
		return priorityCustomerCount;
	}
	public int getTableCount() {
		return TableCount;
	}
	public int getWaiterCount() {
		return WaiterCount;
	}
	public int getRegisterCount() {
		return RegisterCount;
	}
	public int getChefCount() {
		return ChefCount;
	}
	public Semaphore getTables() {
		return Tables;
	}
	public Semaphore getOrders() {
		return Orders;
	}
	public Semaphore getRegisterSemaphore() {
		return RegisterSemaphore;
	}
	public Semaphore getAwaitingOrdersSemaphore() {
		return AwaitingOrdersSemaphore;
	}
	public Semaphore getPaymentReadyCustomersSemaphore() {
		return PaymentReadyCustomersSemaphore;
	}
	public Semaphore getMealConfirmSemaphore() {
		return MealConfirmSemaphore;
	}
	public Semaphore getRegisterConfirm() {
		return RegisterConfirm;
	}
	public Semaphore getOrderConfirm() {
		return OrderConfirm;
	}
}
