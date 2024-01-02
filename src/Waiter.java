import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InterfaceAddress;
import java.util.concurrent.Semaphore;
import javax.swing.JButton;

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
		
		JButton waiterButton = new JButton();
		
		BufferedWriter filewriter;
		
		public Waiter(Semaphore orders,Semaphore awaiting_orders,Semaphore order_confirm,Semaphore gotOrder,GUI Interface,BufferedWriter filewriter)
		{
			super();
			this.ordersSemaphore=orders;
			this.awaitingOrdersSemaphore=awaiting_orders;
			this.orderConfirmSemaphore=order_confirm;
			this.gotOrder=gotOrder;
			
			
			this.waitersPanel=Interface.getWaitersPanel();
			this.chefsPanel=Interface.getChefsPanel();
			this.registersPanel=Interface.getRegistersPanel();
			
			this.filewriter = filewriter;
			
			waitersPanel.add(waiterButton);
			Interface.repaint();
			Interface.setVisible(true);
			Interface.repaint();
			
			
		}
		@Override
		public void run() 
		{
			super.run();
			
			waiterButton.setText(String.valueOf(currentThread().threadId()) + "id li Garson");
			
			
			try 
			{
				while(true)
				{
					//müşterinin gel kral demesini bekler
					waiterButton.setBackground(Color.gray);
					
					ordersSemaphore.acquire();
					orderConfirmSemaphore.release();
					System.out.println(threadId()+" Siparis aliyorum");
					try {
						filewriter.write(this.threadId()+"garson kasa diyor ki : Siparis aliyorum");
						filewriter.newLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					waiterButton.setBackground(Color.green);
					sleep(2000);
					
					try {
						filewriter.write(this.threadId()+"garson kasa diyor ki : Siparis aldim");
						filewriter.newLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					//aşçıya yap reis der
					gotOrder.release();
					awaitingOrdersSemaphore.release();
					waiterButton.setBackground(Color.gray);
				}
				

			} catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}