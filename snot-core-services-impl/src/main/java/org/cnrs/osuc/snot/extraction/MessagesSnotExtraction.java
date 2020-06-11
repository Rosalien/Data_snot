/*
 *
 */
package org.cnrs.osuc.snot.extraction;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class MessagesSnotExtraction.
 */
public final class MessagesSnotExtraction {

    private static final Logger LOGGER          = LoggerFactory
                                                        .getLogger(MessagesSnotExtraction.class
                                                                .getName());

    /**
     * The Constant BUNDLE_NAME @link(String).
     */
    static final String         BUNDLE_NAME     = "org.inra.ecoinfo.acbb.extraction.messages";

    /**
     * The Constant RESOURCE_BUNDLE @link(ResourceBundle).
     */
    static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
                                                        .getBundle(MessagesSnotExtraction.BUNDLE_NAME);

    /**
     * Gets the string.
     * 
     * @param key
     *            the key
     * @return the string
     */
    public static String getString(final String key) {
        try {
            return MessagesSnotExtraction.RESOURCE_BUNDLE.getString(key);
        } catch (final MissingResourceException e) {
            return '!' + key + '!';
        }
    }

    /**
     * Instantiates a new messages acbb extraction.
     */
    MessagesSnotExtraction() {
    }
}
