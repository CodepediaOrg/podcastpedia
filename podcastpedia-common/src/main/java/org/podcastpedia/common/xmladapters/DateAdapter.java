package org.podcastpedia.common.xmladapters;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateAdapter extends XmlAdapter<String, Date> {
	
	private static final String ISO_8601_DATE_FORMAT_SHORT = "yyyy-MM-dd";
	private SimpleDateFormat dateFormat;
	
	public DateAdapter() {
		super();

		dateFormat = new SimpleDateFormat(ISO_8601_DATE_FORMAT_SHORT);
	}	

	@Override
	public Date unmarshal(String v) throws Exception {
		return dateFormat.parse(v);
	}

	@Override
	public String marshal(Date v) throws Exception {
		return dateFormat.format(v);
	}

}
