
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.management.RuntimeMBeanException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel; 


public class Main
{
	
	public static void main(String args[]) throws InterruptedException, IOException 
	{
		
		File log = new File("log.txt");
		final FileWriter filewriter = new FileWriter(log);
		final BufferedWriter bufferedwriter = new BufferedWriter(filewriter);
		
		
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				try {
					bufferedwriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					filewriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}));
		
		
		//burda kullandığım semaphore sistemi çok ilkel çift taraflı gibi davranan kaliteli bir sisteme geçiş yapılması gerek
		//bir semafor diğerinden diğer semafor da diğerinden istek istiyerek gibi çünkü altındaki kodun diğer koşul da gerçekleştiğinde çalışabilmesi acquire olması gerekiyor
		//o zaman bütün sorunlar halloluyor ve gui zart zurt problem 2 kalıyor
		
		final GUI Interface = new GUI();
		Interface.customersWindow();
		JButton startButton = Interface.getStartButton();
		final Restaurant rest = new Restaurant(Interface,bufferedwriter);
		
		final Calculator calc = new Calculator();
		
		JButton stopAndStartButton = Interface.getStopAndStartButton();
		JButton nextButton = Interface.getNextButton();
		JButton calculateButton = Interface.getCalculateButton();
		
		startButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{		
				Interface.restaurantWindow();
				rest.setCustomers(Interface.getCustomersList());
				rest.start();
				
				
				
				
				
			}
				
			
		});
		
		stopAndStartButton.addActionListener(new ActionListener() 
		{
			
			public void actionPerformed(ActionEvent e) 
			{
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
		
		nextButton.addActionListener(new ActionListener()
		{
			
			public void actionPerformed(ActionEvent e) 
			{
				rest.nextCustomers();
				
				
			}
		});
		
		calculateButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				calc.calculateEarning(Integer.valueOf(Interface.getCalcTimeTF().getText()), Integer.valueOf(Interface.getCalcCustomerTimeTF().getText()), Integer.valueOf(Interface.getCalcCustomersTF().getText()), Integer.valueOf(Interface.getCalcPriorityCustomersTF().getText()));
				Interface.setCalcResultText("Kazanc = "+calc.getEarnings()+"  Masa = "+calc.getTableCount()+"  Garson = "+calc.getWaiterCount()+"  Asci = "+calc.getChefCount());
				System.out.println("Kazanc = "+calc.getEarnings()+"  Masa = "+calc.getTableCount()+"  Garson = "+calc.getWaiterCount()+"  Asci = "+calc.getChefCount());
			}
		});
		
		
		
		rest.join();
		
		
	
	}
		
		
		
		
		
		
	
}
	
	

