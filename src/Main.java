
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel; 


public class Main
{
	
	public static void main(String args[]) throws InterruptedException 
	{
		
		
		//burda kullandığım semaphore sistemi çok ilkel çift taraflı gibi davranan kaliteli bir sisteme geçiş yapılması gerek
		//bir semafor diğerinden diğer semafor da diğerinden istek istiyerek gibi çünkü altındaki kodun diğer koşul da gerçekleştiğinde çalışabilmesi acquire olması gerekiyor
		//o zaman bütün sorunlar halloluyor ve gui zart zurt problem 2 kalıyor
		
		final GUI Interface = new GUI();
		Interface.customersWindow();
		JButton startButton = Interface.getStartButton();
		final Restaurant rest = new Restaurant();

		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{		
				 
				rest.setCustomers(Interface.getCustomerCounts()[0], Interface.getCustomerCounts()[1]);
				Interface.restaurantWindow(rest.getTables(),rest.getCustomers(),rest.getPriorityCustomers(),rest.getWaiters(),rest.getChefs(),rest.getRegisters());
				rest.start();
				
				
			}
				
			
		});
		
		
		rest.join();
	
	}
		
		
		
		
		
		
	
}
	
	

