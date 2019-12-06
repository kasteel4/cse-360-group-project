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
	
	@org.junit.jupiter.api.Test
	void analyzeDataTest() {
		GradeAnalytics ga = new GradeAnalytics();
		ga.addData(2.0);
		ga.addData(3.0);
		ga.addData(3.0);
		ga.addData(4.0);
		ga.addData(5.0);
		ga.addData(6.0);
		ArrayList<Double> results = ga.analyzeData();
		assertTrue(results.get(0) == 2.0);
		assertTrue(results.get(1) == 6.0);
		assertTrue(results.get(2) == 23.0/6);
		assertTrue(results.get(3) == 3.5);
		assertTrue(results.get(4) == 3.0);
		assertTrue(results.get(5) == 3.0);
	}

}
