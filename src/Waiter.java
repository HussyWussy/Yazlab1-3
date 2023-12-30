import java.util.concurrent.Semaphore;

import javax.swing.JPanel;

public class Waiter extends Thread
	{
		Semaphore ordersSemaphore;
		Semaphore awaitingOrdersSemaphore;
		Semaphore orderConfirmSemaphore;
		Semaphore gotOrder;
		
		JPanel waitersPanel;
		JPanel chefsPanel;
		JPanel registersPanel;
		
		
		public Waiter(Semaphore orders,Semaphore awaiting_orders,Semaphore order_confirm,Semaphore gotOrder,GUI Interface)
		{
			super();
			this.ordersSemaphore=orders;
			this.awaitingOrdersSemaphore=awaiting_orders;
			this.orderConfirmSemaphore=order_confirm;
			this.gotOrder=gotOrder;
			
			this.waitersPanel=Interface.getWaitersPanel();
			this.chefsPanel=Interface.getChefsPanel();
			this.registersPanel=Interface.getRegistersPanel();
			
			
		}
		@Override
		public void run() 
		{
			super.run();
			try 
			{
				while(true)
				{
					//müşterinin gel kral demesini bekler
					ordersSemaphore.acquire();
					orderConfirmSemaphore.release();
					System.out.println(threadId()+" Siparis aliyorum");
					sleep(2000);
					//aşçıya yap reis der
					gotOrder.release();
					awaitingOrdersSemaphore.release();
				}
				

			} catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}