import java.awt.BorderLayout;
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
	JButton nextButton = new JButton("Sonraki Müşteriler");
	
	public JPanel getWaitersPanel() {
		return waitersPanel;
	}
	public JPanel getRegistersPanel() {
		return registersPanel;
	}
	public JPanel getChefsPanel() {
		return chefsPanel;
	}


	private JTextField normalCustomerTextField = null;
	private JTextField priorityCustomerTextField = null;
	private JButton startButton = null;
	private JButton addButton = null;
	private ArrayList<int[]> customersList;
	
	
	public ArrayList<int[]> getCustomersList() {
		return customersList;
	}
	
	public GUI()
	{
		super();
		setVisible(true);
		setBackground(Color.cyan);
		setSize(600, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new FlowLayout());	
	}
	public void customersWindow()
	{
		
		customersList = new ArrayList<int[]>();
		
		customersWindowPanel = new JPanel();
		customersWindowPanel.setLayout(new BorderLayout());
		
		JPanel northPanel = new JPanel();
		final JPanel southPanel = new JPanel( new GridLayout(0,1));
		
		customersWindowPanel.add(northPanel,BorderLayout.NORTH);
		customersWindowPanel.add(southPanel,BorderLayout.SOUTH);
		
		JLabel normalCustomerLabel = new JLabel("Normal Müşteri Sayısı");
		northPanel.add(normalCustomerLabel);
		
		normalCustomerTextField = new JTextField();
		normalCustomerTextField.setText("0");
		normalCustomerTextField.setColumns(2);
		northPanel.add(normalCustomerTextField);
		
		JLabel priorityCustomerLabel = new JLabel("Öncelikli Müşteri Sayısı");
		northPanel.add(priorityCustomerLabel);
		
		priorityCustomerTextField = new JTextField();
		priorityCustomerTextField.setText("0");
		priorityCustomerTextField.setColumns(2);
		northPanel.add(priorityCustomerTextField);
		
		addButton = new JButton("Yeni ekle");
		northPanel.add(addButton);
		
		startButton = new JButton("Başlat");
		northPanel.add(startButton);
		
		addButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int normalcustomer = Integer.valueOf(normalCustomerTextField.getText());
				int prioritycustomer = Integer.valueOf(priorityCustomerTextField.getText());
				int [] temp = {normalcustomer,prioritycustomer};
				customersList.add(temp);
				
				JLabel labelShowingPrev = new JLabel(customersList.size()+" nci aşama normal müşteri : "+customersList.get(customersList.size()-1)[0] + "öncelikli müşteri : " + customersList.get(customersList.size()-1)[1]+" ");
				southPanel.add(labelShowingPrev);
				
				normalCustomerTextField.setText("0");
				priorityCustomerTextField.setText("0");
				
				repaint();
				setVisible(true);
				repaint();
				
			}
		});
		
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
		add(nextButton);
		
		add(waitersPanel);
		add(chefsPanel);
		add(registersPanel);
		
		repaint();
		setVisible(true);
		repaint();
	}
	
	
	
	public JButton getNextButton() {
		return nextButton;
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
