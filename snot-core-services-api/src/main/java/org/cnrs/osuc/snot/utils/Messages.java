package org.cnrs.osuc.snot.utils;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author sophie
 * 
 */
public class Messages {


    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
                                                                .getBundle(Messages.BUNDLE_NAME);

    /**
     *
     */
    protected static final String BUNDLE_NAME = "org.cnrs.osuc.snot.dataset.messages";

    /**
     *
     * @param key
     * @return
     */
    public static String getString(String key) {
        try {
            return Messages.RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }

    private Messages() {
    }
}
