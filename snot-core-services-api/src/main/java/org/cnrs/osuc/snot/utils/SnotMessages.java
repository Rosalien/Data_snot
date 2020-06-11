package org.cnrs.osuc.snot.utils;

import org.inra.ecoinfo.localization.ILocalizationManager;

/**
 *
 * @author ptcherniati
 */
public class SnotMessages {


    /** The Constant ACBB_DATASET_BUNDLE_NAME. */
    public static final String          ACBB_DATASET_BUNDLE_NAME = "org.cnrs.osuc.snot.dataset.messages";

    private static ILocalizationManager localizationManager;

    /**
     * Gets the aCBB message.
     * 
     * @param key
     * @link(String) the key
     * @return the ACBB message from key {@link String} the key
     */
    public static final String getSnotMessage(final String key) {
        return SnotMessages.getSnotMessageWithBundle(SnotMessages.ACBB_DATASET_BUNDLE_NAME, key);
    }

    /**
     * Gets the aCBB message.
     * 
     * @param bundlePath
     * @link(String) the bundle path
     * @param key
     * @link(String) the key
     * @return the ACBB message from key
     * @link(String) the bundle path {@link String} the key
     */
    public static final String getSnotMessageWithBundle(final String bundlePath, final String key) {
        return SnotMessages.localizationManager == null ? key : SnotMessages.localizationManager
                .getMessage(bundlePath, key);
    }

    /**
     *
     * @param localizationManager
     */
    public static void setLocalizationManager(ILocalizationManager localizationManager) {
        SnotMessages.localizationManager = localizationManager;
    }

}
