package org.cnrs.osuc.snot.dataset.impl.fileNameCheckers;

import org.inra.ecoinfo.utils.DateUtil;

/**
 *
 * @author ptcherniati
 */
public class FileNameCheckermmyyyy extends AbstractSnotFileNameChecker {

    /**
     *
     * @return
     */
    @Override
    protected String getDatePattern() {
        return DateUtil.MM_YYYY_FILE;
    }
}
