package org.podcastpedia.admin.general;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class SocialApisTest {

	@Ignore @Test
	public void testFacebookGetNumberLikes(){
		RestTemplate rest = new RestTemplate();
		FbLike fbLike = new FbLike();
		ResponseEntity<FbLike> forEntity = rest.getForEntity("http://graph.facebook.com/http://www.podcastpedia.org/podcasts/1277/Discovery/episodes/143/Discovery-The-Heart-Has-its-Reasons", FbLike.class);
	}

}
