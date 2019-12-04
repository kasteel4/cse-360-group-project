import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

public class ResultsPanel extends JPanel
{
	private JList<String> errorLog;
	private JButton analyze, display, displayGraphs, createReport;
	private JScrollPane errors;
	JPanel panel1 = new JPanel(new BorderLayout());
	JPanel panel2 = new JPanel(new GridLayout(2,4));
	JPanel panel3 = new JPanel(new BorderLayout());
	JPanel panel4 = new JPanel(new BorderLayout());

	private ArrayList<Double> list;

	public ResultsPanel(ArrayList<Double> dataSet)
	{
		list = dataSet;

		JLabel errorHeader, placeholder1, placeholder2, placeholder3, placeholder4;
		errorLog = new JList<String>();
		errors = new JScrollPane(errorLog);

		analyze = new JButton("Analyze");
		display = new JButton("Display");
		displayGraphs = new JButton("Display Graphs");
		createReport = new JButton("Create Report");

		errorHeader = new JLabel("Error Log");
		placeholder1 = new JLabel();
		placeholder2 = new JLabel();
		placeholder3 = new JLabel();
		placeholder4 = new JLabel();

		panel2.add(placeholder1);
		panel2.add(analyze);
		analyze.addActionListener(new ButtonListener());
		panel2.add(display);
		display.addActionListener(new ButtonListener());
		panel2.add(placeholder2);
		panel2.add(placeholder3);
		panel2.add(displayGraphs);
		displayGraphs.addActionListener(new ButtonListener());
		panel2.add(createReport);
		createReport.addActionListener(new ButtonListener());
		panel2.add(placeholder4);

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
			//This is if analyze is pressed
			if(event.getSource() == analyze)
			{
				/*
				 * Get the data that we need to display here 
				 * from the arrayList or object that you guys gave created
				 */
			}
			//This is if display is pressed
			if(event.getSource() == display)
			{
				/*
				 * Get the arrayList in order so I can display the data
				 */
			}
			//This is if display graphs is pressed
			if(event.getSource() == displayGraphs)
			{
				/*
				 * Get the data that I need for the graphs here
				 */
			}
			//This is if create reports is pressed
			if(event.getSource() == createReport)
			{
				/*
				 * get all of the stuff for the report then write onto a file and I will
				 * use the file to save it with the file explorer
				 */
			}
		}
	}
}
