package org.podcastpedia.common.util.config;

import org.podcastpedia.common.types.OrderByOption;
import org.springframework.cache.annotation.Cacheable;


public class ConfigServiceImpl implements ConfigService {
	
	ConfigBean configBean;
	
	public OrderByOption getDefaultOrderByOptionForSearchBar() {
		return null; 
	}
	
	@Cacheable(value="referenceData", key="#property")	
	public String getValue(String property) {
		return configBean.get(property);
	}

	public ConfigBean getConfigBean() {
		return configBean;
	}

	public void setConfigBean(ConfigBean configBean) {
		this.configBean = configBean;
	}
	
}
