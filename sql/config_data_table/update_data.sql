-- no image url for prod 
update config_data
set value = 'http://www.podcastpedia.org/static/images/podcast-tm.jpg'
where property= 'NO_IMAGE_LOCAL_URL';

-- no image url for localhost
update config_data
set value = 'http://localhost:8080/static/images/podcast-tm.jpg'
where property= 'NO_IMAGE_LOCAL_URL';

-- adjust number of tags displayed per page 
update config_data
set value = '100'
where property= 'NR_TAGS_PER_PAGE';