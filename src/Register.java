import java.util.concurrent.Semaphore;

import javax.swing.JPanel;

public class Register extends Thread 
	{
		Semaphore registerSemaphore;
		Semaphore tablesSemaphore;
		Semaphore ordersSemaphore;
		Semaphore registerConfirm;
		
		JPanel waitersPanel;
		JPanel chefsPanel;
		JPanel registersPanel;
		public Register(Semaphore register,Semaphore tables,Semaphore orders,Semaphore register_confirm,GUI Interface)
		{
			super();
			this.registerSemaphore=register;
			this.tablesSemaphore=tables;
			this.ordersSemaphore=orders;
			this.registerConfirm=register_confirm;
			this.waitersPanel=Interface.getWaitersPanel();
			this.chefsPanel=chefsPanel;
			this.registersPanel=registersPanel;
		}
		@Override
		public void run() 
		{
			super.run();
			try {
				while(true)
				{
					registerSemaphore.acquire();
					System.out.println("Odeme aliyorum");
					sleep(1000);
					
					tablesSemaphore.release();
					
					registerConfirm.release();
					
				}

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}