import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import javax.swing.*;
public class GUI extends JFrame
{
	private JPanel customersWindowPanel;
	
	private JPanel waitersPanel = makeWaitersPanel();
	private JPanel registersPanel = makeRegistersPanel();
	private JPanel chefsPanel = makeChefsPanel();
	
	JButton stopAndStartButton = new JButton("Durdur/Devam");
	
	public JPanel getWaitersPanel() {
		return waitersPanel;
	}
	public JPanel getRegistersPanel() {
		return registersPanel;
	}
	public JPanel getChefsPanel() {
		return chefsPanel;
	}


	private JTextField normalCustomerTextField;
	private JTextField priorityCustomerTextField;
	private JButton startButton;
	private JButton addButton;
	public GUI()
	{
		super();
		setVisible(true);
		setBackground(Color.cyan);
		setSize(500, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new FlowLayout());	
	}
	public void customersWindow()
	{
		customersWindowPanel = new JPanel();
		
		JLabel normalCustomerLabel = new JLabel("Normal Müşteri Sayısı");
		customersWindowPanel.add(normalCustomerLabel);
		
		normalCustomerTextField = new JTextField();
		normalCustomerTextField.setText("0");
		normalCustomerTextField.setColumns(2);
		customersWindowPanel.add(normalCustomerTextField);
		
		JLabel priorityCustomerLabel = new JLabel("Öncelikli Müşteri Sayısı");
		customersWindowPanel.add(priorityCustomerLabel);
		
		priorityCustomerTextField = new JTextField();
		priorityCustomerTextField.setText("0");
		priorityCustomerTextField.setColumns(2);
		customersWindowPanel.add(priorityCustomerTextField);
		
		addButton = new JButton("Yeni ekle");
		customersWindowPanel.add(addButton);
		
		startButton = new JButton("Başlat");
		customersWindowPanel.add(startButton);
		
		
		
		add(customersWindowPanel);
		
		repaint();
		setVisible(true);
		repaint();
	}
	public void restaurantWindow()
	{
		remove(customersWindowPanel);
		setSize(1600, 900);
		setLocationRelativeTo(null);
		setLayout(new GridLayout());
		
		add(stopAndStartButton);
		
		add(waitersPanel);
		add(chefsPanel);
		add(registersPanel);
		
		repaint();
		setVisible(true);
		repaint();
	}
	
	
	
	public JButton getStopAndStartButton() {
		return stopAndStartButton;
	}
	private JPanel makeWaitersPanel()
	{
		JPanel waitersPanel=new JPanel();
		waitersPanel.setBackground(Color.cyan);
		
		
		
		return waitersPanel;
	}
	
	private JPanel makeChefsPanel( )
	{
		JPanel chefsPanel=new JPanel(new FlowLayout());
		chefsPanel.setBackground(Color.yellow);
		return chefsPanel;
	}
	
	private JPanel makeRegistersPanel()
	{
		JPanel registersPanel=new JPanel(new FlowLayout());
		registersPanel.setBackground(Color.green);
		return registersPanel;
	}
	
	
	
	
	
	public JButton getStartButton()
	{
		return startButton;
	}
	
	
	public int[] getCustomerCounts()
	{
		int normalCustomerCount = Integer.parseInt(normalCustomerTextField.getText());
		int priorityCustomerCount = Integer.parseInt(priorityCustomerTextField.getText());
		
		int [] returnable = {normalCustomerCount,priorityCustomerCount};
		
		return returnable;
	}
	
}
