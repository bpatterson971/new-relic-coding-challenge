package org.bpatterson.newrelic.phrasecounter;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.Scanner;

import org.junit.Test;

import junit.framework.TestCase;

public class AppTest extends TestCase {

	private static final String RESOURCES_DIR = "src/test/resources/";

	private static final String MOBY_DICK = RESOURCES_DIR + "moby-dick.txt";
	private static final String BROTHERS_KARAMAZOV = RESOURCES_DIR + "brothers-karamazov.txt";
	
	private static final String MOBY_EXPECTED_RESULT = "the sperm whale - 87";
	private static final String BROTHERS_EXPECTED_RESULT = "the old man - 48";
	private static final String FILE_NOT_FOUND_EXPECTED_RESULT = "Unable to read";
	
	@Test
	public void testMoby() {
		OutputStream output = new ByteArrayOutputStream();
		System.setOut(new PrintStream(output));
		// Parse the Moby Dick file.
		String[] args = { MOBY_DICK };
		App.main(args);
		
		try (Scanner scanner = new Scanner(new StringReader(output.toString()))) {
			scanner.nextLine(); // will show the file being processed
			String firstLine = scanner.nextLine(); // Will have our first actual result
			assertEquals(firstLine, MOBY_EXPECTED_RESULT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testBrothers() {
		OutputStream output = new ByteArrayOutputStream();
		System.setOut(new PrintStream(output));
		// Parse the Moby Dick file.
		String[] args = { BROTHERS_KARAMAZOV };
		App.main(args);
		
		try (Scanner scanner = new Scanner(new StringReader(output.toString()))) {
			scanner.nextLine(); // will show the file being processed
			String firstLine = scanner.nextLine(); // Will have our first actual result
			assertEquals(firstLine, BROTHERS_EXPECTED_RESULT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testInvalidFile() {
		OutputStream output = new ByteArrayOutputStream();
		OutputStream errOutput = new ByteArrayOutputStream();
		System.setOut(new PrintStream(output));
		System.setErr(new PrintStream(errOutput));
		
		String[] args = { MOBY_DICK + "INVALID-FILE" }; // Obviously invalid file
		App.main(args);
		
		try (Scanner scanner = new Scanner(new StringReader(errOutput.toString()))) {
			String firstLine = scanner.nextLine(); // Will have our first actual result
			assertTrue(firstLine.startsWith(FILE_NOT_FOUND_EXPECTED_RESULT));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
