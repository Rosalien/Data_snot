package org.cnrs.osuc.snot.dataset.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * @author philippe
 * 
 */
public enum QualityClass implements Serializable {

    /**
     *
     */
    EXCELLENT(0, "Mesure de bonne qualité"), 

    /**
     *
     */
    MOYEN(1, "Mesure de qualité moyenne"), 

    /**
     *
     */
    MEDIOCRE(2, "Mesure de mauvaise qualité."), ;

    /**
     *
     * @param name
     * @return
     */
    public static QualityClass getInstance(String name) {
        return QualityClass.INSTANCES.get(name);
    }

    /**
     *
     * @param name
     * @return
     */
    public static QualityClass getInstanceByNumber(int name) {
        switch (name) {
            case 0 :
                return EXCELLENT;
            case 1 :
                return MOYEN;
            case 2 :
                return MEDIOCRE;
            default :
                return null;
        }
    }

    private static final Map<String, QualityClass> INSTANCES = new HashMap<>();

    static {
        QualityClass.INSTANCES.put(EXCELLENT.toString(), EXCELLENT);
        QualityClass.INSTANCES.put(MOYEN.toString(), MOYEN);
        QualityClass.INSTANCES.put(MEDIOCRE.toString(), MEDIOCRE);
    }

    private final String name;

    private final int numero;

    QualityClass(int numero, String name) {
        this.name = name;
        this.numero = numero;
    }

    /**
     *
     * @return
     */
    public int getNumero() {
        return this.numero;
    }

    private Object readResolve() {
        return QualityClass.getInstance(this.name);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
