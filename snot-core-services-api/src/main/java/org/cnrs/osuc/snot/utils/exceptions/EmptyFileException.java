/*
 *
 */
package org.cnrs.osuc.snot.utils.exceptions;

import org.inra.ecoinfo.utils.exceptions.BusinessException;

/**
 * The Class EmptyFileException.
 */
public class EmptyFileException extends BusinessException {

    /** The Constant serialVersionUID <long>. */
    static final long serialVersionUID = 1L;

    /**
     * Instantiates a new empty file exception.
     */
    public EmptyFileException() {
        super();
        
    }

    /**
     * Instantiates a new empty file exception.
     * 
     * @param arg0
     *            the arg0
     */
    public EmptyFileException(final String arg0) {
        super(arg0);
        
    }

    /**
     * Instantiates a new empty file exception.
     * 
     * @param arg0
     *            the arg0
     * @param arg1
     *            the arg1
     */
    public EmptyFileException(final String arg0, final Throwable arg1) {
        super(arg0, arg1);
        
    }

    /**
     * Instantiates a new empty file exception.
     * 
     * @param arg0
     *            the arg0
     */
    public EmptyFileException(final Throwable arg0) {
        super(arg0);
        
    }

}
