import java.util.ArrayList;
import java.util.Iterator;

public class Calculator 
{
	private static  int Time;
	private static  int customerTime;
	private static  int customerCount;
	private static  int priorityCount;

	private static int waiterCount;
	private static  double chefCount;
	private static  int tableCount;
	private static  int registerCount = 1;
	
	private static ArrayList <calcCustomer> customers;
	
	private int earnings = 0;
	
	public Calculator() 
	{
		//öncelikli müşteriler de müşteri sayısına dahil geliyor 
		
		
	}
	
	private class calcCustomer
	{
		private int time = 0;
		private boolean sitting = false;
		private boolean waiting = false;
		private boolean orderGotten = false;
		private boolean waitingForFood = false;
		private boolean eating = false;
		private boolean paying = false;
		
		public calcCustomer(int time)
		{
			this.time=time;
		}
		
		public int getTime() {
			return time;
		}
		boolean sit(int currentTime)
		{
			if(tableCount>0 && !sitting)
			{
				tableCount--;
				sitting=true;
				time = currentTime;
			}
			if(time+20<=currentTime)
			{
				return true;
			}
			return false;
			
		}
		void getOrder(int currentTime)
		{
			if(waiterCount>0 && !waiting && sitting)
			{
				waiterCount--;
				waiting=true;
				time = currentTime;
			}
			
		}
		void gotOrder(int currentTime)
		{
			if(waiting && time+2<=currentTime && !orderGotten)
			{
				waiterCount++;
				orderGotten=true;
				time=currentTime;
			}
		}
		void waitForFood(int currentTime)
		{
			if(chefCount>0 && orderGotten && !waitingForFood)
			{
				chefCount=chefCount-0.5;
				waitingForFood=true;
				time=currentTime;
			}
		}
		void eat(int currentTime)
		{
			if(waitingForFood && !eating && time+3<=currentTime)
			{
				eating=true;
				chefCount=chefCount+0.5;
				time = currentTime;
				
			}
			
		}
		void pay(int currentTime)
		{
			if(registerCount>0 && eating &&  !paying && time+3<=currentTime)
			{
				registerCount--;
				time=currentTime;
				paying=true;
				
			}
			
		}
		boolean exit(int currentTime)
		{
			if(paying && time+1<=currentTime) {
				registerCount++;
				tableCount++;
				time = currentTime;
				return true;
			}
			else
			{
				return false;
			}
		}
	}
	
	public void calculateEarning(int Time,int customerTime,int customerCount,int priorityCount)
	{
		this.Time=Time;
		this.customerCount=customerCount;
		this.customerTime=customerTime;
		this.priorityCount=priorityCount;
		
		
		//başlangıç değerleri
		tableCount = 1;
		waiterCount =  1;
		chefCount = 1;
		
		int besttablecount = 1;
		int bestwaitercount = 1;
		double bestchefcount = 1;
		
		earnings=emulate();
		
		
		System.out.println(earnings + "  "+ tableCount+ "  " +chefCount +"  " +waiterCount +" "+ registerCount);
		for(int i=1;i<Math.ceil((Time*customerCount/customerTime+1)/2)+1;i++)
		{
			tableCount = i;
			int tableEarn = emulate();
			if(tableEarn >= earnings) {
				earnings = tableEarn;
				besttablecount = tableCount;
				bestwaitercount = waiterCount;
				bestchefcount = chefCount;
			}
			for(int j = 1;j<i+1;j++)
			{
				waiterCount = j;
				int waiterEarn= emulate();
				if(waiterEarn >= earnings) {
					earnings = waiterEarn;
					besttablecount = tableCount;
					bestwaitercount = waiterCount;
					bestchefcount = chefCount;
				}
				
				for(int k = 1;k<Math.ceil(i /2)+1;k++)
				{
					chefCount = k;
					int chefEarn = emulate();
					if(chefEarn >= earnings) {
						earnings = chefEarn;
						besttablecount = tableCount;
						bestwaitercount = waiterCount;
						bestchefcount = chefCount;
					}
				}
			}
		}
		
		tableCount=besttablecount;
		waiterCount=bestwaitercount;
		chefCount=bestchefcount;
	}
	
	private int emulate()
	{
		int earn = 0;
		customers= new ArrayList<calcCustomer>();
		Double tempChef = Double.valueOf(chefCount);
		int tempWait = Integer.valueOf(waiterCount);
		int tempregi = Integer.valueOf(registerCount);
		int tempTabl = Integer.valueOf(tableCount);
		
		for(int i=0;i<Time;i++)
		{
			if(i%customerTime==0)
			{
				for(int j=0;j<customerCount;j++)
				{
					customers.add(new calcCustomer(i));
				}
			}
			
			
			
			
			
			Iterator<calcCustomer> itr = customers.iterator();
			while(itr.hasNext())
			{
				calcCustomer c = itr.next();
				boolean exited = c.exit(i);
				if(exited) 
				{
					earn++;
					itr.remove();
				}
			}
			for(calcCustomer c : customers)
			{
				c.pay(i);
			}
			itr = customers.iterator();
			while(itr.hasNext())
			{
				calcCustomer c = itr.next();
				boolean timedout = c.sit(i);
				if(timedout) 
				{
					itr.remove();
				}
				
			}
			for(calcCustomer c : customers)
			{
				c.gotOrder(i);
			}
			for(calcCustomer c : customers)
			{
				c.getOrder(i);
			}
			for(calcCustomer c : customers)
			{
				c.eat(i);
			}
			for(calcCustomer c : customers)
			{
				c.waitForFood(i);
			}
			
		}
		
		
		tableCount=tempTabl;
		chefCount=tempChef;
		waiterCount=tempWait;
		registerCount=tempregi;
		
		
		return (int) (earn - (tableCount + chefCount + waiterCount));
	}
	
	
	
	
	public int getPriorityCount() {
		return priorityCount;
	}


	public int getTableCount() {
		return tableCount;
	}


	public int getWaiterCount() {
		return waiterCount;
	}


	public int getEarnings() {
		return earnings;
	}

	public int getChefCount() {
		return (int) chefCount;
	}
	
}
