package rsa_implementation;

import java.math.*;
import java.util.Random;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.event.*;

public class Keygen extends JFrame
{
	BigInteger p, q, n, phi, e, d;
	BigInteger publickey[] = new BigInteger[2];
	private BigInteger privatekey[] = new BigInteger[2];
	JButton keygen, exit;
	JLabel loc;
	JTextField floc;	
	JFileChooser fc;

	public Keygen()
	{
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);	
		
		loc = new JLabel("Location: ");
		c.insets = new Insets(10,10,10,5);
		c.gridx = 0;
		c.gridy = 0;
		add(loc, c);
		
		floc = new JTextField(20);
		c.insets = new Insets(10,5,10,10);
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 3;
		add(floc, c);
		
		keygen = new JButton("Generate");
		c.insets = new Insets(0,10,10,5);
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		add(keygen, c);
		
		exit = new JButton("Exit");
		c.insets = new Insets(0,10,10,5);
		c.anchor = GridBagConstraints.PAGE_END;
		c.gridx = 2;
		c.gridy = 1;
		c.gridwidth = 1;
		add(exit, c);		
		
		event ev = new event();
		keygen.addActionListener(ev);
		exit.addActionListener(ev);
	}
	
	public class event implements ActionListener
	{	
		public void actionPerformed(ActionEvent ev) 
		{
			if(ev.getActionCommand()=="Generate")
			{
				int returnVal = fc.showOpenDialog(Keygen.this);

	            if (returnVal == JFileChooser.APPROVE_OPTION) 
	            {
	                File file = fc.getSelectedFile();
	                floc.setText(file.getPath());
	                
	                p = BigInteger.probablePrime(127, new Random());
	        		q = BigInteger.probablePrime(127, new Random());
	        		n = p.multiply(q);
	        		phi = (p.subtract(new BigInteger("1"))).multiply(q.subtract(new BigInteger("1")));
	        		
	        		e = BigInteger.probablePrime(20, new Random());
	        		while(!check(phi,e))
	        			e = BigInteger.probablePrime(20, new Random());
	        		d = e.modInverse(phi);
	        		
	        		Writer publickey = null;
	        		Writer privatekey = null;
	                try {
	                	publickey = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(floc.getText()+"\\publickey.txt")));
	                	publickey.write("*****PUBLIC KEY*****"+System.lineSeparator()+n+System.lineSeparator()+e);
	                	privatekey = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(floc.getText()+"\\privatekey.txt")));
	                	privatekey.write("*****PRIVATE KEY*****"+System.lineSeparator()+n+System.lineSeparator()+d);
	                } 
	                catch (IOException ex) {/*ignore*/}
	                finally 
	                {
	                   try {
	                	   publickey.close();
	                	   privatekey.close();
	                	   } 
	                   catch (Exception ex) {/*ignore*/}
	                }
	                JOptionPane.showMessageDialog(null, "Keys Generated Successfully!\nLocation: "+floc.getText()+"\nKeep the keys in a safe location", "Save Successful", JOptionPane.INFORMATION_MESSAGE);
	                JOptionPane.showMessageDialog(null, "DO NOT GENRATE KEYS AGAIN TO DECRYPT!!", "BE CAREFUL!", JOptionPane.WARNING_MESSAGE);
	                System.exit(0);
	            } 
	            else 
	            {
	            	JOptionPane.showMessageDialog(null, "Please select a file", "ERROR!", JOptionPane.ERROR_MESSAGE);
	            	floc.grabFocus();
	            	floc.setText("");
	            }
			}
			else if(ev.getActionCommand()=="Exit")
			{
				System.exit(0);
			}
		}
		
		boolean check(BigInteger a, BigInteger b)
		{
			BigInteger q, r;
			q = a.divide(b);
			r = a.subtract(q.multiply(b));
			while(r.compareTo(new BigInteger("1"))==1)
			{
				a = b;
				b = r;
				q = a.divide(b);
				r = a.subtract(q.multiply(b));
				if(r.compareTo(new BigInteger("1"))==0)
				{
					return true;
				}
			}
			return false;
		}
	}
	
	public static void main(String[] args) 
	{
		Keygen gui = new Keygen();
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setSize(200, 100);
		gui.pack();
		gui.setVisible(true);
		gui.setResizable(false);
		gui.setTitle("Key Generator");
		gui.setLocationRelativeTo(null);
	}

}
