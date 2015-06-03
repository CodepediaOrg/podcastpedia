package org.podcastpedia.admin.update.metadata;

import java.util.List;

import org.apache.log4j.Logger;
import org.podcastpedia.admin.dao.DeleteDao;
import org.podcastpedia.admin.dao.InsertDao;
import org.podcastpedia.admin.dao.ReadDao;
import org.podcastpedia.admin.dao.UpdateDao;
import org.podcastpedia.common.domain.Podcast;
import org.podcastpedia.common.domain.PodcastCategory;
import org.podcastpedia.common.domain.PodcastTag;
import org.podcastpedia.common.domain.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class UpdateMetadataServiceImpl implements UpdateMetadataService{

	private static Logger LOG = Logger.getLogger(UpdateMetadataServiceImpl.class);

	@Autowired
	private DeleteDao deleteDao;

	@Autowired
	private ReadDao readDao;

	@Autowired
	private InsertDao insertDao;

	@Autowired
	private UpdateDao updateDao;
	
	@Override
	@Transactional	
	public void updatePodcastMetadata(Podcast podcast) {

		LOG.info("Updating metadata for podcast " + podcast.getPodcastId());
		int podcastId = podcast.getPodcastId();

		// first of all update the attribute of the podcast
		updateDao.updatePodcastOwnMetadatabyId(podcast);
				
		updatePodcastCategories(podcast.getCategoryIDs(), podcastId);

		updatePodcastTags(podcast.getTagsStr(), podcastId);
	}

	private void updatePodcastTags(String tagsString, int podcastId) {
		// first delete the existing categories
		deleteDao.deletePodcastTagsByPodcastId(podcastId);		
		// insert tags in the database
		// split the tags-string in the different tags - use ";" so that
		// complexer tags can be added like ("light cooking")
		String[] tags = tagsString.split(",");
		for (String tagName : tags) {
			// tags should be case sensitive . e.g. in German you say Computer
			// and in English computer -but trimmed should be; They are trimmed
			// also by insertion
			Tag tag = readDao.getTagByName(tagName.trim());
			if (null != tag) {
				insertDao.insertPodcastTag(new PodcastTag(podcastId, tag
						.getTagId()));
			} else {
				tag = new Tag();
				// make a trim on the tag to insert, to read after that properly
				tag.setName(tagName.trim());
				insertDao.insertTag(tag);
				insertDao.insertPodcastTag(new PodcastTag(podcastId, tag
						.getTagId()));
			}
		}
	}

	private void updatePodcastCategories(List<Integer> newCategoryIDs, int podcastId) {
		deleteDao.deletePodcastCategoriesByPodcastId(podcastId);		
		// insert again now the assigned categories in the intersection table
		PodcastCategory podcastCategory = new PodcastCategory();
		podcastCategory.setPodcastId(podcastId);
		for (Integer i : newCategoryIDs) {
			podcastCategory.setCategoryId(i);
			insertDao.insertPodcastCategory(podcastCategory);
		}
	}
}
