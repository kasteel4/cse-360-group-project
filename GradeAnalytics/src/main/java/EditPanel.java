import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

public class EditPanel extends JPanel
{
	private JList<String> errorLog;
	private JButton appendData, addData, deleteData;
	private JScrollPane errors;
	private ResultsPanel resultPanel;
	JPanel panel1 = new JPanel(new BorderLayout());
	JPanel panel2 = new JPanel(new GridLayout(3,3));
	JPanel panel3 = new JPanel(new BorderLayout());
	JPanel panel4 = new JPanel(new BorderLayout());

	private GradeAnalytics ga;
	
	public EditPanel(GradeAnalytics gaParam, ResultsPanel rPanel)
	{
		resultPanel = rPanel;
		ga = gaParam;
		
		JLabel errorHeader, placeholder1, placeholder2, placeholder3, placeholder4,
		placeholder5, placeholder6;
		errorLog = new JList<String>();
		errors = new JScrollPane(errorLog);
		
		appendData = new JButton("Append Data");
		addData = new JButton("Add Data");
		deleteData = new JButton("Delete Data");
		
		errorHeader = new JLabel("Error Log");
		placeholder1 = new JLabel();
		placeholder2 = new JLabel();
		placeholder3 = new JLabel();
		placeholder4 = new JLabel();
		placeholder5 = new JLabel();
		placeholder6 = new JLabel();
		
		panel2.add(placeholder1);
		panel2.add(appendData);
		appendData.addActionListener(new ButtonListener());
		panel2.add(placeholder2);
		panel2.add(placeholder3);
		panel2.add(addData);
		addData.addActionListener(new ButtonListener());
		panel2.add(placeholder4);
		panel2.add(placeholder5);
		panel2.add(deleteData);
		deleteData.addActionListener(new ButtonListener());
		panel2.add(placeholder6);

		panel4.add(errorHeader, BorderLayout.NORTH);
		panel4.add(errors, BorderLayout.CENTER);

		setLayout(new GridLayout(4,1));
		add(panel1);
		add(panel2);
		add(panel3);
		add(panel4);
	}
	
	private class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			//This is when append data is pressed
			if(event.getSource() == appendData)
			{
				JFileChooser fs = new JFileChooser(new File("c:\\"));
				fs.setDialogTitle("Load File");
				int result = fs.showOpenDialog(null);
				if(result == JFileChooser.APPROVE_OPTION)
				{
					//Here is the selected file from which data should be appended
					String fileName = fs.getSelectedFile().getPath();
					try {
						ga.parseFile(fileName);
					} catch (InvalidFileTypeException e) {
						//*****Should be added to error console instead of sys out
						System.out.println(e.getMessage());
					}
					
				}
			}
			//This is when add data is pressed
			if(event.getSource() == addData)
			{
				int value;
				double numAdd;
				JLabel errorLabel = new JLabel("");
				JLabel label1 = new JLabel("Enter Value:");
				JTextField field1 = new JTextField();
				JPanel panel = new JPanel(new GridLayout(0,1));
				panel.add(errorLabel);
				panel.add(label1);
				panel.add(field1);
				//The do while loop requires the user to press cancel
				do
				{
					value = JOptionPane.showConfirmDialog(null, panel, "Add Data",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
					if(value == JOptionPane.OK_OPTION)
					{
						try
						{
							//Here is the value to add to the data set
							numAdd = Double.parseDouble(field1.getText());
							ga.addData(numAdd);
							field1.setText("");
							errorLabel.setText("Value Added");
							errorLabel.setForeground(Color.green);
						}
						catch(NumberFormatException exception)
						{
							//Here is an error if they do not enter a number
							errorLabel.setText("Invalid Entry");
							errorLabel.setForeground(Color.red);
						}
					}
				}
				while(value != JOptionPane.CANCEL_OPTION);
			}
			//This is if delete data is pressed
			if(event.getSource() == deleteData)
			{
				int numDelete, value;
				JLabel errorLabel = new JLabel("");
				JLabel label1 = new JLabel("Enter Value:");
				JTextField field1 = new JTextField();
				JPanel panel = new JPanel(new GridLayout(0,1));
				panel.add(errorLabel);
				panel.add(label1);
				panel.add(field1);
				//This do while loop requires the user to press cancel
				do
				{
					value = JOptionPane.showConfirmDialog(null, panel, "Delete Data",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
					if(value == JOptionPane.OK_OPTION)
					{
						try
						{
							//Here is the value to delete from data set
							numDelete = Integer.parseInt(field1.getText());
							/*
							 * Enter code here
							 * Search for value
							 * If it exists delete if not add to error list
							 */
							
							field1.setText("");
							errorLabel.setText("");
						}
						catch(NumberFormatException exception)
						{
							errorLabel.setText("Invalid Entry");
							errorLabel.setForeground(Color.red);
						}
					}
				}
				while(value != JOptionPane.CANCEL_OPTION);
			}
		}
	}
}
