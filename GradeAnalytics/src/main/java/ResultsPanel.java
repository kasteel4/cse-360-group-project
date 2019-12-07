import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

public class ResultsPanel extends JPanel
{
	private JTextArea errorLog;
	private ArrayList<String> errorList;
	private JButton analyze, display, displayGraphs, createReport;
	private JScrollPane errors;
	JPanel panel1 = new JPanel(new BorderLayout());
	JPanel panel2 = new JPanel(new GridLayout(2,4));
	JPanel panel3 = new JPanel(new BorderLayout());
	JPanel panel4 = new JPanel(new BorderLayout());

	private GradeAnalytics ga;

	public ResultsPanel(GradeAnalytics gaParam)
	{
		ga = gaParam;

		JLabel errorHeader, placeholder1, placeholder2, placeholder3, placeholder4;
		errorLog = new JTextArea();
		errorList = new ArrayList<String>();
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
			String errorDisplay = "";
			//This is if analyze is pressed
			if(event.getSource() == analyze)
			{
				int numEntries = 0;
				double high = 0, low = 0, mean = 0, median = 0;
				String modes = "";
				ArrayList<Double> results = ga.analyzeData();
				low = results.get(0);
				high = results.get(1);
				mean = Math.round(results.get(2) * 10.0)/10.0;
				median = results.get(3);
				for(int i = 4; i < results.size(); i++)
				{
					modes += results.get(i) + ", ";
				}

				JLabel entryLabel = new JLabel("     # of Entries: " + numEntries);
				JLabel highLabel = new JLabel("     High:              " + high);
				JLabel lowLabel = new JLabel("     Low:              " + low);
				JLabel meanLabel = new JLabel("     Mean:            " + mean);
				JLabel medianLabel = new JLabel("     Median:        " + median);
				JLabel modeLabel = new JLabel("     Mode:            " + modes);
				JPanel panel = new JPanel(new GridLayout(0,1));
				panel.add(entryLabel);
				panel.add(highLabel);
				panel.add(lowLabel);
				panel.add(meanLabel);
				panel.add(medianLabel);
				panel.add(modeLabel);
				JOptionPane.showConfirmDialog(null, panel, "Analyze", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE);
			}
			//This is if display is pressed
			if(event.getSource() == display)
			{
				//Could you update .getData() to return a sorted array in decreasing order
				//ArrayList from GA object
				ArrayList<Double> data = ga.displayData();
				String str = "";
				JLabel errorLabel = new JLabel();
				JTextArea column1 = new JTextArea();
				JScrollPane col1 = new JScrollPane(column1);
				JTextArea column2 = new JTextArea();
				JScrollPane col2 = new JScrollPane(column2);
				JTextArea column3 = new JTextArea();
				JScrollPane col3 = new JScrollPane(column3);
				JTextArea column4 = new JTextArea();
				JScrollPane col4 = new JScrollPane(column4);
				JPanel panel1 = new JPanel(new BorderLayout());
				JPanel panel = new JPanel(new GridLayout(0,4));
				JPanel panel2 = new JPanel(new BorderLayout());
				//I couldn't break the sections of the array into 4 even parts. This is the best I could get it to work
				//It has to be of size 6 or bigger. You guys are welcome to try to fix it. You can try moving the for loops
				//around to see if it can break it apart in 4 even arrays or something. 
				if(ga.getSize() > 5)
				{
					for(int i = 0; i < (int)Math.ceil((double)data.size()/4.0); i++)
					{
						str += data.get(i) + "\n";
					}
					column1.setText(str);
					str = "";
					for(int i = (int)Math.ceil((double)data.size()/4.0); i < (int)Math.ceil((double)data.size()/4.0)*2; i++)
					{
						str += data.get(i) + "\n";
					}
					column2.setText(str);
					str = "";
					for(int i = (int)Math.ceil((double)data.size()/4.0)*2; i < (int)Math.ceil((double)data.size()/4.0)*3; i++)
					{
						str += data.get(i) + "\n";
					}
					column3.setText(str);
					str = "";
					for(int i = (int)Math.ceil((double)data.size()/4.0)*3; i < ga.getSize(); i++)
					{
						str += data.get(i) + "\n";
					}
				}
				else
				{
					errorLabel.setText("Add more than 5 elements into the array\n");
				}
				column4.setText(str);
				panel1.add(errorLabel, BorderLayout.CENTER);
				panel.add(col1);
				panel.add(col2);
				panel.add(col3);
				panel.add(col4);
				panel2.add(panel,BorderLayout.CENTER);
				panel2.add(panel1, BorderLayout.NORTH);
				JOptionPane.showConfirmDialog(null, panel2, "Display", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE);
			}
			//This is if display graphs is pressed
			if(event.getSource() == displayGraphs)
			{
				ga.numEntriesGraphData();
				ga.avgGraphData();
				//Still working on the graphs
			}
			//This is if create reports is pressed
			if(event.getSource() == createReport)
			{
				JFileChooser fs = new JFileChooser(new File("c:\\"));
				fs.setDialogTitle("Save File");
				int result = fs.showSaveDialog(null);
				if(result == JFileChooser.APPROVE_OPTION)
				{
					//Replace content with the toString of steps taken throughout the program
					String newFile = fs.getSelectedFile().getPath();

					try
					{
						ga.saveReport(newFile);
					}
					catch (IOException e)
					{
						errorList.add(e.getMessage());
					}
				}
			}
			for(int i = 0; i < errorList.size(); i++)
			{
				errorDisplay += errorList.get(i);
			}
			errorLog.setText(errorDisplay);
		}
	}
}
