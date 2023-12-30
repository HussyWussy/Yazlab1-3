import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JPanel;

class Customer extends Thread
	{
		Semaphore tablesSemaphore;
		Semaphore ordersSemaphore;
		Semaphore registerSemaphore;
		Semaphore mealConfirm;
		Semaphore registerConfirm;
		Semaphore orderConfirm;
		Semaphore gotOrder;
		Semaphore PrioritySemaphore;
		
		String state ="";
		JButton customerButton ;
		
		JPanel waitersPanel;
		JPanel chefsPanel;
		JPanel registersPanel;
		GUI Interface;
		BufferedWriter filewriter;
		public Customer(Semaphore tables,Semaphore orders,Semaphore register,Semaphore order_confirm,Semaphore meal_confirm,Semaphore register_confirm,Semaphore gotOrder ,GUI Interface,BufferedWriter filewriter,Semaphore PrioritySemaphore)
		{
			super();
			setPriority(MIN_PRIORITY);
			this.tablesSemaphore=tables;
			this.ordersSemaphore=orders;
			this.registerSemaphore=register;
			this.mealConfirm=meal_confirm;
			this.orderConfirm=order_confirm;
			this.registerConfirm=register_confirm;
			this.gotOrder=gotOrder;
			this.PrioritySemaphore=PrioritySemaphore;
			
			this.filewriter = filewriter;
			
			this.waitersPanel=Interface.getWaitersPanel();
			this.chefsPanel=Interface.getChefsPanel();
			this.registersPanel=Interface.getRegistersPanel();
			this.Interface=Interface;
			
			customerButton = new JButton();
			customerButton.setMinimumSize(new Dimension(50,50));;
		}
		@Override
		public void run()
		{
			super.run();
			try {
				state = threadId()+"beklemede";
				customerButton.setText(state);
				waitersPanel.add(customerButton);
				if(PrioritySemaphore.availablePermits()>0) {
					if(!(this instanceof PriorityCustomer))
					{
						while(true)
						{
							if(PrioritySemaphore.availablePermits()==0)
							{
								break;
							}
						
						}
					}
						
					
				}
				if(tablesSemaphore.tryAcquire(20L, TimeUnit.SECONDS))
				{
					
					if(this instanceof PriorityCustomer)
					{
						PrioritySemaphore.acquire();
					}
					
					//garson bekliyor
					customerButton.setBackground(Color.blue);
					
					try {
						filewriter.write(threadId()+"numarali musteri diyor ki : Oturdum da");
						filewriter.newLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(this.threadId()+"numarali musteri diyor ki : Oturdum da");
					
					
					
					Interface.repaint();
					Interface.setVisible(true);
					Interface.repaint();
		
					//yemek getirin da
					state = threadId()+"Oturuyor";
					customerButton.setText(state);
					ordersSemaphore.release();
					orderConfirm.acquire();
					
					//siparişi alınıyor
					customerButton.setBackground(Color.yellow);
					state = threadId()+"Sipariş alınıyor";
					customerButton.setText(state);
					
					
			
					//burda garson sipariş alıyor
					try {
						filewriter.write(this.threadId()+"numarali musteri diyor ki : Siparisim alınıyor da");
						filewriter.newLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(this.threadId()+"numarali musteri diyor ki : Siparisim alınıyor da");
					
					
					
					gotOrder.acquire();
					
					try {
						filewriter.write(this.threadId()+"numarali musteri diyor ki : Siparisim alındı da");
						filewriter.newLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					System.out.println(this.threadId()+"numarali musteri diyor ki : Siparisim alındı da");
					customerButton.setBackground(Color.pink);
					state = threadId()+"Sipariş alındı";
					customerButton.setText(state);
					//müşteri yemeğin gelmesini bekliyor
					mealConfirm.acquire();
					
					

					try {
						filewriter.write(this.threadId()+"numarali musteri diyor ki : Yemegimi yiyorum da");
						filewriter.newLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					System.out.println(this.threadId()+"numarali musteri diyor ki : Yemegimi yiyorum da");
					//System.out.println(this.threadId()+"numaralı müşteri diyor ki : Yemeğimi yiyom  da");
					//müşterinin yemeği geldi yemeğinin yiyor
					//yemek bekliyor
					customerButton.setBackground(Color.green);
		
					state = threadId()+"Yemek yiyor";
					customerButton.setText(state);
					
					sleep(3000);
					
					//System.out.println(this.threadId()+"numaralı müşteri diyor ki : Kasayı bekliyom da  da");
					//müşteri ödeme yapmayı bekliyor ödemeyi yaptıktan sonra çıkıp gidicektir 
					System.out.println(this.threadId()+"numarali musteri diyor ki : Yemeğim bitti da");
					
					try {
						filewriter.write(this.threadId()+"numarali musteri diyor ki : Yemeğim bitti da");
						filewriter.newLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					customerButton.setBackground(Color.red);
					state = threadId()+"Kasa sırasında";
					
					JButton temp = new JButton();
					temp.setText(state);
					temp.setBackground(Color.red);
					registersPanel.add(temp);
					
					
					customerButton.setText(state);
					
					registerSemaphore.release();
					registerConfirm.acquire();
					System.out.println(this.threadId()+"numarali musteri diyor ki : bb da");
					
					try {
						filewriter.write(this.threadId()+"numarali musteri diyor ki : bb da");
						filewriter.newLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					waitersPanel.remove(customerButton);
					registersPanel.remove(temp);
					
					Interface.repaint();
					Interface.setVisible(true);
					Interface.repaint();
					
					
					//müşteri gidiyor bb
					//kasa halledicektir masa açımını
					
				}
				else {
					System.out.println(this.threadId()+"numaralı müşteri diyor ki : Adios");
					
					try {
						filewriter.write(this.threadId()+"numarali musteri diyor ki : yeterince bekledim adios");
						filewriter.newLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					waitersPanel.remove(customerButton);
					
					Interface.repaint();
					Interface.setVisible(true);
					Interface.repaint();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}