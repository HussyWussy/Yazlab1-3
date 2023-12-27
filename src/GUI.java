import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
public class GUI extends JFrame
{
	private JPanel customersWindowPanel;
	private JPanel waitersPanel;
	private JPanel registersPanel;
	private JPanel chefsPanel;
	
	private JTextField normalCustomerTextField;
	private JTextField priorityCustomerTextField;
	private JButton startButton;
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
		
		setLayout(new GridLayout());
		
		waitersPanel=new JPanel(new FlowLayout());
		chefsPanel=new JPanel(new FlowLayout());
		registersPanel=new JPanel(new FlowLayout());
		
		waitersPanel.setBackground(Color.cyan);
		chefsPanel.setBackground(Color.yellow);
		registersPanel.setBackground(Color.green);
		
		
		
		add(waitersPanel);
		add(chefsPanel);
		add(registersPanel);
		
		repaint();
		setVisible(true);
		repaint();
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
