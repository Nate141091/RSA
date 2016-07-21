package rsa_implementation;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.math.BigInteger;
import javax.swing.*;
import javax.swing.filechooser.*;

public class Encrypt extends JFrame
{
	JButton fbrowse, load, save, encrypt, clear, exit;
	JLabel fname, msgtxt, codedtxt, blank;
	JTextField fpath;
	JTextArea text, ciphertxt;
	JFileChooser fc;
	BigInteger n,e,msg1,msg2,cipher1,cipher2;
	
	public Encrypt()
	{
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//create the file chooser
		fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		
		
		/****Start of Components****/
		/****FIRST LINE****/		
			//JLabel : File name
			fname = new JLabel("File name: ", JLabel.RIGHT);
			c.fill = GridBagConstraints.CENTER;
			c.insets = new Insets(10,10,5,0);
			c.weightx = 0.5;
			c.gridx = 1;
			c.gridy = 0;
			add(fname,c);
			
			//Text box : File path
			fpath = new JTextField(30);
			c.fill = GridBagConstraints.BOTH;
			c.insets = new Insets(10,0,5,10);
//			c.weightx = 0.5;
			c.gridx = 2;
			c.gridy = 0;
			c.gridwidth = 3;
			add(fpath,c);
			
		/****SECOND LINE****/	
			//Button : Browse File
			fbrowse = new JButton("Browse");
//			c.fill = GridBagConstraints.NONE;
			c.gridwidth = 1;
			c.gridx = 2;
			c.gridy = 1;
			add(fbrowse, c);
			
			//Button : Load
			load = new JButton("Load");
//			c.fill = GridBagConstraints.NONE;
			c.gridwidth = 1;
			c.gridx = 3;
			c.gridy = 1;
			add(load, c);
			load.setEnabled(false);
		
		/****THIRD LINE****/	
			//JLabel : File Contents
			msgtxt = new JLabel("File contents");
			c.fill = GridBagConstraints.CENTER;
			c.insets = new Insets(10,0,0,0);
			c.gridx = 1;
			c.gridy = 2;
			c.gridwidth = 3;
			add(msgtxt, c);
			
			
		/****FOURTH LINE****/
			//Text field : Actual text
			text = new JTextArea(4,40);
			text.setEditable(false);
			text.setLineWrap(true);
			c.insets = new Insets(10,10,0,10);
			c.fill = GridBagConstraints.BOTH;
			c.ipadx = 3;
			c.gridwidth = 3;
			c.gridx = 1;
			c.gridy = 3;
//			add(text, c);
			JScrollPane txtpane = new JScrollPane(text);
			add(txtpane,c);
			c.ipadx = 0;
						
		/****FIFTH LINE****/
			//Button : Encrypt
			encrypt = new JButton("Encrypt");
//			c.fill = GridBagConstraints.BOTH;
			c.insets = new Insets(10,0,10,10);
			c.gridwidth = 1;
			c.gridx = 2;
			c.gridy = 6;
			add(encrypt, c);
			encrypt.setEnabled(false);
		
			//Button : Save
			save = new JButton("Save");
//			c.fill = GridBagConstraints.BOTH;
			c.insets = new Insets(10,0,10,10);
			c.gridx = 3;
			c.gridy = 6;
			add(save, c);
			save.setEnabled(false);
			
		/****SIXTH LINE****/
			//JLabel : Encrypted text
			codedtxt = new JLabel("Ecrypted text");
			c.fill = GridBagConstraints.CENTER;
			c.gridx = 1;
			c.gridy = 8;
			c.gridwidth = 3;
			add(codedtxt, c);
			c.gridwidth = 1;
			
		/****SEVENTH LINE****/
			//Text field : Cipher text
			ciphertxt = new JTextArea(4,40);
			ciphertxt.setEditable(false);
			ciphertxt.setLineWrap(true);
			c.insets = new Insets(10,10,0,10);
			c.fill = GridBagConstraints.BOTH;
			c.weightx = 0.5;			
			c.gridx = 1;
			c.gridwidth = 3;
			c.gridy = 9;
//			add(ciphertxt, c);
			JScrollPane cipherpane = new JScrollPane(ciphertxt);
			add(cipherpane,c);
		
		/****EIGHT LINE****/
			//Button : Clear
			clear = new JButton("Clear");
//			c.fill = GridBagConstraints.NONE;
			c.insets = new Insets(10,0,10,10);
			c.gridwidth = 1;
			c.gridx = 2;
			c.gridy = 12;
			add(clear, c);
			
			//Button : Exit
			exit = new JButton("Exit");
//			c.fill = GridBagConstraints.NONE;
			c.insets = new Insets(10,0,10,10);
			c.gridx = 3;
			c.gridy = 12;
			c.anchor = GridBagConstraints.PAGE_END; 
			add(exit, c);

		/****End of Components****/
		
		/****Add event calls****/
		event ev = new event();
		fbrowse.addActionListener(ev);
		load.addActionListener(ev);
		save.addActionListener(ev);
		encrypt.addActionListener(ev);
		clear.addActionListener(ev);
		exit.addActionListener(ev);
	}
	
	protected static ImageIcon createImageIcon(String path) 
	{
        java.net.URL fileURL = Encrypt.class.getResource(path);
        if (fileURL != null) 
        {
            return new ImageIcon(fileURL);
        } else 
        {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
	
	public class event implements ActionListener
	{		
		public void actionPerformed(ActionEvent ev)
		{
			String buttontxt = ev.getActionCommand();
			if(buttontxt.equals("Exit"))
			{
				//Exit from UI
				System.exit(0);
			}
			else if(buttontxt.equals("Clear"))
			{
				//Clear all fields and disable buttons
				fpath.setText("");
				text.setText("");
				ciphertxt.setText("");
				fpath.grabFocus();
				save.setEnabled(false);
				encrypt.setEnabled(false);
				load.setEnabled(false);
			}
			else if(buttontxt.equals("Browse"))
			{
				//open file ui to select file
				//display filepath and file name
				int returnVal = fc.showOpenDialog(Encrypt.this);

	            if (returnVal == JFileChooser.APPROVE_OPTION) 
	            {
	                File file = fc.getSelectedFile();
	                fpath.setText(file.getPath());
	                load.setEnabled(true);
	            } 
	            else 
	            {
	            	JOptionPane.showMessageDialog(null, "Please select a file", "ERROR!", JOptionPane.ERROR_MESSAGE);
	            	fpath.grabFocus();
					fpath.selectAll();
	            }
//	            text.setCaretPosition(text.getDocument().getLength());
			}
			else if(buttontxt.equals("Load"))
			{
				//load text in text field
				BufferedReader br = null;
				try {
					br = new BufferedReader(new FileReader(fpath.getText()));
				    StringBuilder sb = new StringBuilder();
				    String line = br.readLine();

				    while (line != null) {
				        sb.append(line);
				        sb.append(System.lineSeparator());
				        line = br.readLine();
				    }
				    text.setText(sb.toString());
				} 
				catch (Exception e1) 
				{
					JOptionPane.showMessageDialog(null, "Invalid file or file does not exist!", "ERROR!", JOptionPane.ERROR_MESSAGE);
					fpath.setText("");
					fpath.grabFocus();	
					encrypt.setEnabled(false);
				}
				finally 
				{
				    try {
						br.close();
					} 
				    catch (Exception e1) {/*ignore*/}
				}
				encrypt.setEnabled(true);
			}
			else if(buttontxt.equals("Encrypt"))
			{
				//encrypt text and display in ciphertext field
				/****get encryption key file****/
				int returnVal = fc.showOpenDialog(Encrypt.this);
				String key = "";
	            if (returnVal == JFileChooser.APPROVE_OPTION) 
	            {
	                File file = fc.getSelectedFile();
	                key = file.getPath();
	            } 
	            else 
	            {
	            	JOptionPane.showMessageDialog(null, "Please select Public Key file", "ERROR!", JOptionPane.ERROR_MESSAGE);
	            	fpath.grabFocus();
					fpath.selectAll();
	            }
				
				/****Read Public Key from file C:\\Users\\Nathan\\Desktop\\publickey.txt ****/
					BufferedReader br = null;
					try {
						br = new BufferedReader(new FileReader(key));				    
					    br.readLine();	//ignore first line
					    n = new BigInteger(br.readLine());
					    e = new BigInteger(br.readLine());		
					} 
					catch (IOException e1) {/*ignore*/}
					finally 
					{
					    try {
							br.close();
						} 
					    catch (IOException e1) {/*ignore*/}
					}
					/****Public key obtained****/
					
					/****encode message****/
					String temp = text.getText();
					
					temp = temp.replace("\r\n", " ");		//replace the new lines with space
					
					String m = "";
					for(int i=0; i<temp.length(); i++)
					{
						int ascii = (int)temp.charAt(i);
						if(ascii<10)
							m = m + "00" + ascii;
						else if(ascii>9 && ascii<100)
							m = m + "0" + ascii;
						else
							m = m + ascii;	
					}
					//m = m + "032";
					
				/****encoding done****/
				
				/****encrypt message****/
					msg1 = new BigInteger(m.substring(0,m.length()/2-1));
					cipher1 = msg1.modPow(e, n);
					msg2 = new BigInteger(m.substring(m.length()/2,m.length()));
					cipher2 = msg2.modPow(e, n);
					
				/****encryption done****/

				//display cipher text
				ciphertxt.setText(cipher1.toString(16)+" "+cipher2.toString(16));
				save.setEnabled(true);
			}
			else if(buttontxt.equals("Save"))
			{
				//open file ui to save encrypted file to disk 
				int returnVal = fc.showSaveDialog(Encrypt.this);
	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	                File file = fc.getSelectedFile();
	                Writer writer = null;
	                try {
	                    writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file.getPath()), "utf-8"));
	                    writer.write(ciphertxt.getText());
	                } 
	                catch (IOException ex) {/*ignore*/}
	                finally 
	                {
	                   try {writer.close();} 
	                   catch (Exception ex) {/*ignore*/}
	                }
	                JOptionPane.showMessageDialog(null, "File saved Successfully!", "Save Successful", JOptionPane.INFORMATION_MESSAGE);
	            } else {
	            	JOptionPane.showMessageDialog(null, "Save cancelled!", "Save Failed", JOptionPane.WARNING_MESSAGE);
	            }
			}
		}
	}

	public static void main(String[] args) 
	{
		Encrypt gui = new Encrypt();
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setSize(500, 500);
		gui.setLocationRelativeTo(null);
		gui.setVisible(true);
		gui.setResizable(false);
		gui.pack();
		gui.setTitle("Encryptor");
	}
}
