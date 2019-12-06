import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import errors.InvalidBoundaries;
import errors.InvalidFileTypeException;

import java.util.*;
import java.io.*;

public class HomePanel extends JPanel
{
	private JList<String> errorLog;
	private JButton loadFile, setBoundaries;
	private JScrollPane errors;
	private ResultsPanel resultPanel;
	private EditPanel editPanel;
	JPanel panel1 = new JPanel(new BorderLayout());
	JPanel panel2 = new JPanel(new GridLayout(2,3));
	JPanel panel3 = new JPanel(new BorderLayout());
	JPanel panel4 = new JPanel(new BorderLayout());

	private GradeAnalytics ga;
	
	public HomePanel(GradeAnalytics gaParam, EditPanel ePanel, ResultsPanel rPanel)
	{
		editPanel = ePanel;
		resultPanel = rPanel;
		ga = gaParam;
		
		JLabel errorHeader, placeholder1, placeholder2, placeholder3, placeholder4;
		errorLog = new JList<String>();
		errors = new JScrollPane(errorLog);
		
		loadFile = new JButton("Load File");
		setBoundaries = new JButton("Set Boundaries");
		
		errorHeader = new JLabel("Error Log");
		placeholder1 = new JLabel();
		placeholder2 = new JLabel();
		placeholder3 = new JLabel();
		placeholder4 = new JLabel();
		
		panel2.add(placeholder1);
		panel2.add(loadFile);
		loadFile.addActionListener(new ButtonListener());
		panel2.add(placeholder2);
		panel2.add(placeholder3);
		panel2.add(setBoundaries);
		setBoundaries.addActionListener(new ButtonListener());
		panel2.add(placeholder4);

		panel4.add(errorHeader, BorderLayout.NORTH);
		panel4.add(errors, BorderLayout.CENTER);

		setLayout(new GridLayout(4,1));
		add(panel1);
		add(panel2);
		add(panel3);
		add(panel4);
		
		
	}
	
	//Here are all the actions for the buttons
	private class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			//This is when load file is pressed
			if(event.getSource() == loadFile)
			{
				JFileChooser fs = new JFileChooser(new File("c:\\"));
				fs.setDialogTitle("Load File");
				int result = fs.showOpenDialog(null);
				if(result == JFileChooser.APPROVE_OPTION)
				{
					//Here is the selected file which will contain all of the data
					String fileName = fs.getSelectedFile().getPath();
					try {
						ga.parseFile(fileName);
						
					} catch (InvalidFileTypeException e) {
						//*****Should be added to error console instead of sys out
						System.out.println(e.getMessage());
					}
					
				}
			}
			//This is when set boundaries is clicked
			if(event.getSource() == setBoundaries)
			{
				double upperB, lowerB;
				int value;
				JLabel errorLabel = new JLabel("");
				JLabel label1 = new JLabel("Lower Boundary:\n(Default 0)");
				JTextField field1 = new JTextField();
				JLabel label2 = new JLabel("Upper Boundary:\n(Default 100)");
				JTextField field2 = new JTextField();
				JPanel panel = new JPanel(new GridLayout(0,1));
				panel.add(errorLabel);
				panel.add(label1);
				panel.add(field1);
				panel.add(label2);
				panel.add(field2);
				//This do loop requires the user to press cancel to exit
				do
				{
					value = JOptionPane.showConfirmDialog(null, panel, "Enter Boundaries",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
					if(value == JOptionPane.OK_OPTION)
					{
						try
						{
							//Here are values to update boundaries
							lowerB = Double.parseDouble(field1.getText());
							field1.setText("");
							upperB = Double.parseDouble(field2.getText());
							field2.setText("");
							ga.setBoundaries(upperB, lowerB);
							errorLabel.setText("Boundaries Set");
							errorLabel.setForeground(Color.green);
						}
						catch(NumberFormatException exception)
						{
							errorLabel.setText("Invalid Number");
							errorLabel.setForeground(Color.red);
						}
						catch(InvalidBoundaries exception)
						{
							errorLabel.setText(exception.getMessage());
							errorLabel.setForeground(Color.red);
						}
					}
				}
				while(value != JOptionPane.CANCEL_OPTION);
			}
		}
	}
}
