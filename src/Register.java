import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.Semaphore;

import javax.swing.JPanel;
import javax.swing.JButton;

public class Register extends Thread 
	{
		Semaphore registerSemaphore;
		Semaphore tablesSemaphore;
		Semaphore ordersSemaphore;
		Semaphore registerConfirm;
		
		JPanel waitersPanel;
		JPanel chefsPanel;
		JPanel registersPanel;
		
		BufferedWriter filewriter;
		
		JButton registerButton = new JButton();
		public Register(Semaphore register,Semaphore tables,Semaphore orders,Semaphore register_confirm,GUI Interface,BufferedWriter filewriter)
		{
			super();
			this.registerSemaphore=register;
			this.tablesSemaphore=tables;
			this.ordersSemaphore=orders;
			this.registerConfirm=register_confirm;
			this.waitersPanel=Interface.getWaitersPanel();
			this.chefsPanel=Interface.getChefsPanel();
			this.registersPanel=Interface.getRegistersPanel();
			
			this.filewriter = filewriter;
			
			registersPanel.add(registerButton);
			
			Interface.repaint();
			Interface.setVisible(true);
			Interface.repaint();
		}
		@Override
		public void run() 
		{
			registerButton.setText(String.valueOf(currentThread().threadId()) + "id li kasa");
			super.run();
			try {
				while(true)
				{
					registerButton.setBackground(Color.gray);
					registerSemaphore.acquire();
					System.out.println("Odeme aliyorum");

					try {
						filewriter.write(this.threadId()+"numarali kasa diyor ki : Odeme aliyorum");
						filewriter.newLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					registerButton.setBackground(Color.green);
					sleep(1000);
					
					tablesSemaphore.release();
					
					registerConfirm.release();
					registerButton.setBackground(Color.gray);
					
				}

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}