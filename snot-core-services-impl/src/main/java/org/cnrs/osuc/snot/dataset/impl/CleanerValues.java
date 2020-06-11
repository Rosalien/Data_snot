/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.dataset.impl;

import java.util.Arrays;

/**
 *
 * @author tcherniatinsky
 */
public class CleanerValues {

    /**
     * The value index @link(int).
     */
    private int valueIndex = 0;
    /**
     * The values @link(String[]).
     */
    private String[] values;

    /**
     * Instantiates a new cleaner values.
     *
     * @param val
     *            the val
     * @link(String[]) the val
     */
    public CleanerValues(final String[] val) {
        if (val == null) {
            this.values = new String[0];
        } else {
            this.values = Arrays.copyOf(val, val.length);
        }
    }

    /**
     * Current token.
     *
     * @return the string
     */
    public String currentToken() {
        return this.values[this.valueIndex].toLowerCase().trim();
    }

    /**
     * Current token index.
     *
     * @return the int
     */
    public int currentTokenIndex() {
        return this.valueIndex;
    }

    /**
     * Next token.
     *
     * @return the string
     */
    public String nextToken() {
        return this.values[this.valueIndex++].toLowerCase().trim();
    }
    
}
