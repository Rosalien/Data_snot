package org.cnrs.osuc.snot.dataset.impl.fileNameCheckers;

import org.inra.ecoinfo.utils.DateUtil;

/**
 *
 * @author ptcherniati
 */
public class FileNameCheckerddmmyyyy extends AbstractSnotFileNameChecker {

    /**
     *
     * @return
     */
    @Override
    protected String getDatePattern() {
        return DateUtil.DD_MM_YYYY_FILE;
    }
}
