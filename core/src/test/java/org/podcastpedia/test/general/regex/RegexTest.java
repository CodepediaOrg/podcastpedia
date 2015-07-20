package org.podcastpedia.test.general.regex;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.junit.Test;

public class RegexTest {

	@Test
	public void testWordsSeparatedBySpacesCorrect(){
		
		String inputStringCorrect = "word wod2";
		
//		Pattern pattern = Pattern.compile("\\p{L}\\s+");
		Pattern pattern = Pattern.compile("[\\w+|\\s]");
		Matcher matcher = pattern.matcher(inputStringCorrect);
		
		boolean isValid = matcher.matches();
		
		assertFalse(isValid);
		
	}
	
	@Test
	public void testWordsSeparatedBySpacesFalse(){
		
		String inputStringFalse = "word words $; würzel";
		
//		Pattern pattern = Pattern.compile("\\p{L}\\s+");
		Pattern pattern = Pattern.compile("\\w+");
		Matcher matcher = pattern.matcher(inputStringFalse);
		
		boolean isValid = matcher.matches();
		
		assertFalse(isValid);
		
	}	
	
	@Test
	public void testStringSplitFunction(){
		String[] parts = "this is a test; another test;halleluia".split(";");
		assertTrue(parts[0].equals("this is a test"));
		assertTrue(parts[1].equals(" another test"));
		assertTrue(parts[2].equals("halleluia"));	
	}

    @Test
    public void testCommaSeparatedWords(){
        String correct = "word1, wörter, word2";
        String correctWithHyphen = "compound-word, wörter, word2";
        String incorrectWithCommaAtTheEnd = "word1, wörter, word2,";
        String incorrectWithSeparatedSemicolon = "word1, wörter; word2";
        String incorrectWithEmptyWord = "word1, wörter,,word2";

        Pattern pattern = Pattern.compile("^[-\\w\\s]+(?:,[-\\w\\s]+)*$", Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher;
        
        matcher = pattern.matcher(correct);
        assertTrue(matcher.matches());
        
        matcher = pattern.matcher(correctWithHyphen);
        assertTrue(matcher.matches());        

        matcher = pattern.matcher(incorrectWithCommaAtTheEnd);
        assertFalse(matcher.matches());
        
        matcher = pattern.matcher(incorrectWithSeparatedSemicolon);
        assertFalse(matcher.matches());    
        
        matcher = pattern.matcher(incorrectWithEmptyWord);
        assertFalse(matcher.matches());           

    }
}