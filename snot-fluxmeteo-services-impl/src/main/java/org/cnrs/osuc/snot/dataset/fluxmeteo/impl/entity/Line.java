package org.cnrs.osuc.snot.dataset.fluxmeteo.impl.entity;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author ptcherniati
 */
public class Line implements Comparable<Line> {

    private LocalDate date;
    private Long originalLineNumber;
    private LocalTime time;
    private List<VariableValue> variablesValues = new LinkedList<>();

    /**
     *
     */
    public Line() {
        
    }

    /**
     *
     * @param date
     * @param time
     */
    public Line(LocalDate date, LocalTime time) {
        super();
        this.date = date;
        this.time = time;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.date);
        hash = 89 * hash + Objects.hashCode(this.time);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Line other = (Line) obj;
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        return Objects.equals(this.time, other.time);
    }

    @Override
    public int compareTo(Line o) {
        return (int) (this.originalLineNumber - o.originalLineNumber);
    }

    /**
     * @param line
     */
    public void copy(Line line) {
        this.date = line.date;
        this.time = line.time;
        this.originalLineNumber = line.originalLineNumber;
        this.variablesValues = line.variablesValues;
    }

    /**
     *
     * @return
     */
    public LocalDate getDate() {
        return this.date;
    }

    /**
     *
     * @return
     */
    public Long getOriginalLineNumber() {
        return this.originalLineNumber;
    }

    /**
     *
     * @param originalLineNumber
     */
    public void setOriginalLineNumber(Long originalLineNumber) {
        this.originalLineNumber = originalLineNumber;
    }

    /**
     *
     * @return
     */
    public LocalTime getTime() {
        return this.time;
    }

    /**
     *
     * @return
     */
    public List<VariableValue> getVariablesValues() {
        return this.variablesValues;
    }

    /**
     *
     * @param variablesValues
     */
    public void setVariablesValues(List<VariableValue> variablesValues) {
        this.variablesValues = variablesValues;
    }
}
