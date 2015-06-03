package org.podcastpedia.admin.general;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Test;

public class CollectionsTest {

	@Test
	public void testRemoveDuplicatesWithLinkedHashSet(){
		
		String [] tags = "a, b, a, a ,a , a    , a   a, a a, a a ,a a".split("\\s*,\\s*");		
		Set<String> uniqueTags = new TreeSet<String>(Arrays.asList(tags));
		
		Assert.assertTrue(uniqueTags.size() == 4);
	}
}
