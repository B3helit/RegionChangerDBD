package test;

import java.awt.EventQueue;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.awt.event.ActionEvent;
import java.awt.Dialog.ModalExclusionType;


public class Frame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static String path;
	private static IpAndPingFinder iapf = new IpAndPingFinder();
	private static String ip = iapf.findAndSetAndGetIp(0);
	/**
	 * Launch the application.
	 * @throws UnsupportedLookAndFeelException 
	 */
	public static void main(String[] args) throws UnsupportedLookAndFeelException, FileNotFoundException {
		path = System.getProperty("user.home");
		int pathlong = path.length(); String newtxt = "";
				
		for(int i=0;i<pathlong;i++) {
			newtxt += path.charAt(i)+"";
			if(path.charAt(i) == '\\') {
				break;
			}
		}
		path=newtxt+"Windows\\System32\\drivers\\etc\\hosts";		
		
		try{
			deleteEdit(path);
		}catch(FileNotFoundException f) {
			JOptionPane.showMessageDialog(null, "Please open the file with Administrator.(high privilege)", 
                    "ERROR", JOptionPane.ERROR_MESSAGE);
			return;
		}
		iapf.refreshAllPings();
		FlatLightLaf.setup();
		UIManager.setLookAndFeel( new FlatDarkLaf() );

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame frame = new Frame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
					System.err.println( "Failed to initialize LaF" );
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Frame() {
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setTitle("DBD Region Changer by #b3helit");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 378, 448);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Select Region");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 40, 96, 26);
		contentPane.add(lblNewLabel);
		
		String choices[] = {"EU (Europe)", "NA (North America)", "NP (Asia) - currently not available -"};
		JComboBox comboBox = new JComboBox(choices);
		comboBox.setToolTipText("what da dog doin");
		comboBox.setBounds(116, 39, 113, 33);
		//comboBox.addItem(choices);
		contentPane.add(comboBox);
		
		JButton btnNewButton = new JButton("Refresh Regions");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton.setBounds(116, 262, 113, 22);
		contentPane.add(btnNewButton);
		
		
		JLabel lblNewLabel_2_1_1 = new JLabel(currentRegion(path)+"");
		lblNewLabel_2_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2_1_1.setBounds(143, 212, 162, 14);
		contentPane.add(lblNewLabel_2_1_1);
		
		
		JButton btnNewButton_1 = new JButton("Reset Back To Normal Servers");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					deleteEdit(path);
					lblNewLabel_2_1_1.setText(currentRegion(path)+"");
				}catch(FileNotFoundException fnfe) {
					JOptionPane.showMessageDialog(null, "Please open the file with Administrator.(high privilege)", 
		                    "ERROR", JOptionPane.ERROR_MESSAGE);
					return;
				}
				JOptionPane.showMessageDialog(null, "Reset has been done successfully.", "Done" , JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnNewButton_1.setBounds(83, 375, 187, 23);
		contentPane.add(btnNewButton_1);
		
		JLabel lblNewLabel_1 = new JLabel("Pings information");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(10, 129, 107, 22);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Best EU (Europe) :");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(143, 150, 113, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("Best NA (North America) :");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2_1.setBounds(143, 123, 162, 14);
		contentPane.add(lblNewLabel_2_1);
		
		JLabel lblNewLabel_2_2 = new JLabel("Best NP (Asia) :");
		lblNewLabel_2_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2_2.setBounds(143, 175, 86, 14);
		contentPane.add(lblNewLabel_2_2);
		
		JLabel lblNewLabel_3 = new JLabel(iapf.getBestPingNA()+""); // na ping
		lblNewLabel_3.setBounds(294, 123, 56, 14);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_3_1 = new JLabel(iapf.getBestPingEU()+""); // eu ping
		lblNewLabel_3_1.setBounds(261, 151, 63, 14);
		contentPane.add(lblNewLabel_3_1);
		
		JLabel lblNewLabel_3_2 = new JLabel(iapf.getBestPingNP()+""); // asia ping 
		lblNewLabel_3_2.setBounds(242, 176, 46, 14);
		contentPane.add(lblNewLabel_3_2);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				iapf.refreshed = false;
				try{deleteEdit(path);}
				catch(FileNotFoundException fnfe) {
					JOptionPane.showMessageDialog(null, "Please open the file with Administrator.(high privilege)", 
		                    "ERROR", JOptionPane.ERROR_MESSAGE);
					return;
				}
				iapf.refreshAllPings();
				lblNewLabel_3.setText(iapf.getBestPingNA()+"");
				lblNewLabel_3_1.setText(iapf.getBestPingEU()+"");
				lblNewLabel_3_2.setText(iapf.getBestPingNP()+"");
				JOptionPane.showMessageDialog(null, "Pings has been refreshed successfully.", "Done" , JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
 
		
		JButton btnNewButton_2 = new JButton("set");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBox.getSelectedItem() == "EU (Europe)") {
					if(checkText(path, "#Edited")) {
						try{deleteEdit(path);}
						catch(FileNotFoundException fnfe) {
							JOptionPane.showMessageDialog(null, "Please open the file with Administrator.(high privilege)", 
				                    "ERROR", JOptionPane.ERROR_MESSAGE);
							return;
						}
						addTextFile(path, iapf.stringEU());
					}else {
						// new one. (first time).
						addTextFile(path, iapf.stringEU());
					}
					lblNewLabel_2_1_1.setText("Europe (EU)");
					JOptionPane.showMessageDialog(null, "Server has been changed successfully.", "Done" , JOptionPane.INFORMATION_MESSAGE);
				}else if(comboBox.getSelectedItem() == "NA (North America)") {
					if(checkText(path, "#Edited")) {
						try{deleteEdit(path);}
						catch(FileNotFoundException fnfe) {
							JOptionPane.showMessageDialog(null, "Please open the file with Administrator.(high privilege)", 
				                    "ERROR", JOptionPane.ERROR_MESSAGE);
							return;
						}
						addTextFile(path, iapf.stringNA());
					}else {
						// new one. (first time).
						addTextFile(path, iapf.stringNA());
					}
					lblNewLabel_2_1_1.setText("North America (NA)");
					JOptionPane.showMessageDialog(null, "Server has been changed successfully.", "Done" , JOptionPane.INFORMATION_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(null, "Option is not available.", 
                            "ERROR", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		btnNewButton_2.setBounds(249, 44, 56, 23);
		contentPane.add(btnNewButton_2);
		
		JLabel lblNewLabel_5 = new JLabel("Refreshing Regions/Pings could take a while.");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblNewLabel_5.setBounds(70, 295, 218, 14);
		contentPane.add(lblNewLabel_5);
		
		JLabel lblNewLabel_1_1 = new JLabel("Current Region");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_1.setBounds(10, 206, 107, 22);
		contentPane.add(lblNewLabel_1_1);
		
	}
	static boolean checkText(String filePath, String text) {
		File fileToBeModified = new File(filePath);
        
        String oldContent = "";
         
        BufferedReader reader = null;
         
        try
        {
            reader = new BufferedReader(new FileReader(fileToBeModified));
             
            //Reading all the lines of input text file into oldContent
             
            String line = reader.readLine();
             
            while (line != null) 
            {
                oldContent = oldContent + line + System.lineSeparator();
                 
                line = reader.readLine();
            }                        
            if(oldContent.contains(text)) {
            	return true;
            }else {
            	return false;
            }
             
        }
        catch (IOException e)
        {
            e.printStackTrace();
            
        }
        System.err.println("Error occured in checking Text in file.");
        return false;
	}
	static void modifyFile(String filePath, String oldString, String newString)
    {
        File fileToBeModified = new File(filePath);
        String oldContent = "";
        BufferedReader reader = null;
        FileWriter writer = null;
        try
        {
            reader = new BufferedReader(new FileReader(fileToBeModified));
             
            //Reading all the lines of input text file into oldContent
             
            String line = reader.readLine();
             
            while (line != null) 
            {
                oldContent = oldContent + line + System.lineSeparator();
                 
                line = reader.readLine();
            }
            //Replacing oldString with newString in the oldContent
                        
            String newContent = oldContent.replaceAll(oldString, newString);
            //Rewriting the input text file with newContent
             
            writer = new FileWriter(fileToBeModified);
             
            writer.write(newContent);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                //Closing the resources
                 
                reader.close();
                 
                writer.close();
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
    }
	public static void addTextFile(String filePath, String newString) {
		File fileToBeModified = new File(filePath);
        
        String oldContent = "";
         
        BufferedReader reader = null;
         
        FileWriter writer = null;
         
		
        try
        {
            reader = new BufferedReader(new FileReader(fileToBeModified));
             
            //Reading all the lines of input text file into oldContent
             
            String line = reader.readLine();
             
            while (line != null) 
            {
                oldContent = oldContent + line + System.lineSeparator();
                 
                line = reader.readLine();
            }
            //Replacing oldString with newString in the oldContent
             
            String newContent = oldContent + "\n\n" +newString;
             
            //Rewriting the input text file with newContent
             
            writer = new FileWriter(fileToBeModified);
             
            writer.write(newContent);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                //Closing the resources
                 
                reader.close();
                 
                writer.close();
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
	}
	static void deleteEdit(String filePath) throws FileNotFoundException{
        File fileToBeModified = new File(filePath);
        String oldContent = "";
        BufferedReader reader = null;
        FileWriter writer = null;
        boolean entered = false;
        try
        {
            reader = new BufferedReader(new FileReader(fileToBeModified));
             
            //Reading all the lines of input text file into oldContent
             
            String line = reader.readLine();
             
            while (line != null) 
            {
                oldContent = oldContent + line + System.lineSeparator();
                 
                line = reader.readLine();
            }
            String newContent = "";
            if(oldContent.contains("#Edited")) {     
            	int index = oldContent.indexOf("#Edited");
            	newContent = oldContent.substring(0,index);
            
                // Deleting white spaces.
            	for(int i=newContent.length();i>0;i--) {
            		if(Character.isWhitespace(newContent.charAt(i-1)))
            			newContent = newContent.substring(0, i-1);
            		else 
            			break;
            	}  
            }else {
            	entered = true;
            	return;
            }
            
            writer = new FileWriter(fileToBeModified);
             
            writer.write(newContent);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                //Closing the resources
                 if(entered)
                	 return;
                 
                reader.close();
                 
                writer.close();
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
	}
	static String currentRegion(String filePath) {
		boolean a1 = checkText(filePath, "eu");
		boolean a2 = checkText(filePath, "ap");
		boolean a3 = checkText(filePath, "na");
		if(a1 && a2 && !a3) {
			return "North America (NA)";
		}else if (!a1 && a3 && a2) {
			return "Europe (EU)";
		}
		else if((!a1 && !a3 && !a2) || (a1 && a3 && a2)) {
			int x = iapf.getBestPingNA();
			int y = iapf.getBestPingEU();
			int z = iapf.getBestPingNP();
			if(x < y && x < z) {
				return "North America (NA)";
			}else if(y < x && y < z) {
				return "Europe (EU)";
			}else {
				return "Asia (NP)";
			}
		}
		else
			return "Asia (NP)";
	}
}
