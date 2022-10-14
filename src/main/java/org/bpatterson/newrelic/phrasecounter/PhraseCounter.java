package org.bpatterson.newrelic.phrasecounter;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhraseCounter {
	
	private static final Pattern WORD_PATTERN = Pattern.compile("([a-z`'’-]+)(?:\\W+)", Pattern.CASE_INSENSITIVE);
	
	// So we're not creating a bunch of strings for the garbage collector all the time
	private static final String SPACE = " ";
	
	/**
	 * Extracts each three-word phrase from the provided reader, and returns back a 
	 * map with each phrase and the occurrences.
	 * @param reader The reader which contains the content to parse
	 * @return A instance of Map containing the results
	 */
	public Map<String, Integer> countPhrases(Reader reader) {
		// Make sure we have a buffered reader for the best performance.
		BufferedReader br = null;
		if (!(reader instanceof BufferedReader)) {
			br = new BufferedReader(reader);
		} else {
			br = (BufferedReader) reader;
		}
		
		Map<String, Integer> output = new HashMap<>();
		List<String> words = new ArrayList<>();
		
		// Extract the words
		br.lines().forEach((line) -> {
			Matcher m = WORD_PATTERN.matcher(line);
			while (m.find()) {
				words.add(m.group(1));
			}
		});
		
		// Extract the phrases
		while (words.size() >= 3) {
			String phrase = buildPhrase(words.get(0), words.get(1), words.get(2));
			if (output.containsKey(phrase)) {
				Integer prevCount = output.get(phrase);
				output.put(phrase, prevCount+1);
			} else {
				output.put(phrase, 1);
			}
			
			words.remove(0);
		}
		
		return output;
	}
	
	/**
	 * Concatenates three strings together with spaces between
	 * @param w1 First string
	 * @param w2 Second string
	 * @param w3 Third string
	 * @return w1 + " " + w2 + " " w3
	 */
	protected String buildPhrase(String w1, String w2, String w3) {
		StringBuilder sb = new StringBuilder();
		sb.append(w1);
		sb.append(SPACE);
		sb.append(w2);
		sb.append(SPACE);
		sb.append(w3);
		
		return sb.toString().toLowerCase();
	}
}
