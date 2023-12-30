
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel; 


public class Main
{
	
	public static void main(String args[]) throws InterruptedException 
	{
		
		File log = new File("log.txt");
		FileWriter filewriter = null;
		BufferedWriter bufferedwriter = null;
		try {
			filewriter = new FileWriter(log);
			bufferedwriter = new BufferedWriter(filewriter);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//burda kullandığım semaphore sistemi çok ilkel çift taraflı gibi davranan kaliteli bir sisteme geçiş yapılması gerek
		//bir semafor diğerinden diğer semafor da diğerinden istek istiyerek gibi çünkü altındaki kodun diğer koşul da gerçekleştiğinde çalışabilmesi acquire olması gerekiyor
		//o zaman bütün sorunlar halloluyor ve gui zart zurt problem 2 kalıyor
		
		final GUI Interface = new GUI();
		Interface.customersWindow();
		JButton startButton = Interface.getStartButton();
		final Restaurant rest = new Restaurant(Interface,bufferedwriter);
		
		JButton stopAndStartButton = Interface.getStopAndStartButton();
		
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{		
				Interface.restaurantWindow();
				rest.setCustomers(Interface.getCustomerCounts()[0], Interface.getCustomerCounts()[1]);
				rest.start();
				
				
			}
				
			
		});
		
		stopAndStartButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(!rest.paused)
				{
					rest.pauseAll();
				}
				else if(rest.paused)
				{
					rest.resumeAll();
				}
				
				
			}
		});
		
		
		rest.join();
		
		
	
	}
		
		
		
		
		
		
	
}
	
	

