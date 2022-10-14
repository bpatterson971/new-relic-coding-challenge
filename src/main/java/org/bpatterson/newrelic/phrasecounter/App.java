package org.bpatterson.newrelic.phrasecounter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

public class App {

	public static void main(String[] args) {
		PhraseCounter pc = new PhraseCounter();
		Map<String, Integer> phrases = Collections.emptyMap();
		
		if (args == null || args.length == 0) {
			// No files given, check stdin
			Reader stdinReader = new InputStreamReader(System.in);
			phrases = pc.countPhrases(stdinReader);
			outputPhrases(phrases);
		} else {
			for (String arg : args) {
				System.out.println("Processing " + arg + "...");
				try {
					BufferedReader br = Files.newBufferedReader(Paths.get(arg));
					phrases = pc.countPhrases(br);
					//Output phrases for the current file
					outputPhrases(phrases);
				} catch (NoSuchFileException nsfe) {
					System.err.println("Unable to read " + arg);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	private static void outputPhrases(Map<String, Integer> phrases) {
		phrases.entrySet()
		.stream()
		.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
		.limit(100)
		.forEach((entry) -> {
			System.out.println(entry.getKey() + " - " + entry.getValue());
	});
	}
}
