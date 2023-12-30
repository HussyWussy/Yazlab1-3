import java.awt.Color;
import java.awt.Dimension;
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
		
		String state ="";
		JButton customerButton ;
		
		JPanel waitersPanel;
		JPanel chefsPanel;
		JPanel registersPanel;
		GUI Interface;
		
		public Customer(Semaphore tables,Semaphore orders,Semaphore register,Semaphore order_confirm,Semaphore meal_confirm,Semaphore register_confirm,Semaphore gotOrder ,GUI Interface)
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
				state = "beklemede";
				customerButton.setText(state);
				waitersPanel.add(customerButton);
				
				if(tablesSemaphore.tryAcquire(20L, TimeUnit.SECONDS))
				{
					
					
					//garson bekliyor
					customerButton.setBackground(Color.blue);
					
					
					System.out.println(this.threadId()+"numarali musteri diyor ki : Oturdum da");
					
					
					
					Interface.repaint();
					Interface.setVisible(true);
					Interface.repaint();
		
					//yemek getirin da
					state = "Oturuyor";
					customerButton.setText(state);
					ordersSemaphore.release();
					orderConfirm.acquire();
					
					//siparişi alınıyor
					customerButton.setBackground(Color.yellow);
					state = "Sipariş alınıyor";
					customerButton.setText(state);
					
					
			
					//burda garson sipariş alıyor
					
					System.out.println(this.threadId()+"numarali musteri diyor ki : Siparisim alınıyor da");
					gotOrder.acquire();
					System.out.println(this.threadId()+"numarali musteri diyor ki : Siparisim alındı da");
					customerButton.setBackground(Color.pink);
					state = "Sipariş alındı";
					customerButton.setText(state);
					//müşteri yemeğin gelmesini bekliyor
					mealConfirm.acquire();
					
					System.out.println(this.threadId()+"numarali musteri diyor ki : Yemegimi yiyorum da");
					//System.out.println(this.threadId()+"numaralı müşteri diyor ki : Yemeğimi yiyom  da");
					//müşterinin yemeği geldi yemeğinin yiyor
					//yemek bekliyor
					customerButton.setBackground(Color.green);
		
					state = "Yemek yiyor";
					customerButton.setText(state);
					
					sleep(3000);
					
					//System.out.println(this.threadId()+"numaralı müşteri diyor ki : Kasayı bekliyom da  da");
					//müşteri ödeme yapmayı bekliyor ödemeyi yaptıktan sonra çıkıp gidicektir 
					System.out.println(this.threadId()+"numarali musteri diyor ki : Yemeğim bitti da");
					
					customerButton.setBackground(Color.red);
					state = "Kasa sırasında";
					
					JButton temp = new JButton();
					temp.setText(state);
					temp.setBackground(Color.red);
					registersPanel.add(temp);
					
					
					customerButton.setText(state);
					
					registerSemaphore.release();
					registerConfirm.acquire();
					System.out.println(this.threadId()+"numarali musteri diyor ki : bb da");
					
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