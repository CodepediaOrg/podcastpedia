package org.podcastpedia.common.domain.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Utilities {
	
	/**
	 * Returns the String values out of an enum 
	 * 
	 * @param <T>
	 * @param enumClass
	 * @return
	 */
	public static <T extends Enum<T>> List<String> getDisplayValues(Class<T> enumClass) {
	    try {
	        T[] items = enumClass.getEnumConstants();
	        Method accessor = enumClass.getMethod("toString");

	        List<String> names = new ArrayList<String>(items.length);
	        for (T item : items){
	            names.add("" + accessor.invoke(item));
	        }

	        return names;
	        
	    } catch (NoSuchMethodException ex) {
	        // Didn't actually implement getDisplayValue().
	    } catch (InvocationTargetException ex) {
	        // getDisplayValue() threw an exception.
	    } catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
	}

}
