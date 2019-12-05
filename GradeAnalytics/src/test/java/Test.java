import static org.junit.jupiter.api.Assertions.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

class Test {
	
	@org.junit.jupiter.api.Test
	void getMeanTest() {
		GradeAnalytics ga = new GradeAnalytics();
		ga.addData(3.0);
		ga.addData(4.0);
		ga.addData(2.0);
		double mean = ga.getMean();
		assertTrue(mean == 3.0);
	}
	
	@org.junit.jupiter.api.Test
	void getModeTest() {
		ArrayList<Double> data = new ArrayList<Double>();
		GradeAnalytics ga = new GradeAnalytics();
		ga.addData(3.0);
		ga.addData(4.0);
		ga.addData(2.0);
		ga.addData(2.0);
		ArrayList<Double> mode = ga.getMode();
		assertTrue(mode.contains(2.0));
		ga.addData(3.0);
		mode = ga.getMode();
		assertTrue(mode.contains(2.0));
		assertTrue(mode.contains(3.0));
		
	}

}
