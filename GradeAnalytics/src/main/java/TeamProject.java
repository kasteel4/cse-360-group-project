import javax.swing.*;
import java.util.*;

public class TeamProject extends JApplet
{
	private int APPLET_WIDTH = 500, APPLET_HEIGHT = 300;

	private JTabbedPane tPane;
	private ResultsPanel resultPanel;
	private EditPanel editPanel;
	private HomePanel homePanel;
	private ArrayList<Double> currentData;
	private GradeAnalytics ga;

	//There is no main. All of the variables are instantiated here
	//Instead of ArrayList<Double> I don't know if you guys want to create a class object so you guys
	//can have methods for it like order them decreasing and stuff like that
	public void init()
	{
		ga = new GradeAnalytics();
		currentData = new ArrayList<Double>();
		resultPanel = new ResultsPanel(ga);
		editPanel = new EditPanel(ga, resultPanel);
		homePanel = new HomePanel(ga, editPanel, resultPanel);

		tPane = new JTabbedPane();
		tPane.addTab("Home", homePanel);
		tPane.addTab("Edit Data", editPanel);
		tPane.addTab("Data Results", resultPanel);

		getContentPane().add(tPane);
		setSize(APPLET_WIDTH, APPLET_HEIGHT);
		
	}
}
