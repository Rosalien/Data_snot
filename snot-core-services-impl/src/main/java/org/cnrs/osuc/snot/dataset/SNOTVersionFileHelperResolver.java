package org.cnrs.osuc.snot.dataset;

import java.util.Map;
import org.inra.ecoinfo.dataset.versioning.IVersionFileHelper;
import org.inra.ecoinfo.dataset.versioning.IVersionFileHelperResolver;
import org.inra.ecoinfo.dataset.versioning.exception.NoVersionFileHelperResolvedException;
import org.inra.ecoinfo.utils.Utils;

/**
 *
 * @author ptcherniati
 */
public class SNOTVersionFileHelperResolver implements IVersionFileHelperResolver {

    Map<String, IVersionFileHelper> datatypesVersionFileHelpersMap;

    /**
     *
     * @return
     */
    public Map<String, IVersionFileHelper> getDatatypesVersionFileHelpersMap() {
        return this.datatypesVersionFileHelpersMap;
    }

    /**
     *
     * @param datatypesVersionFileHelpersMap
     */
    public void setDatatypesVersionFileHelpersMap(Map<String, IVersionFileHelper> datatypesVersionFileHelpersMap) {
        this.datatypesVersionFileHelpersMap = datatypesVersionFileHelpersMap;
    }

    /**
     *
     * @param datatypeName
     * @return
     * @throws NoVersionFileHelperResolvedException
     */
    @Override
    public IVersionFileHelper resolveByDatatype(String datatypeName) throws NoVersionFileHelperResolvedException {
        String datatypeCode = Utils.createCodeFromString(datatypeName);
        if (!this.datatypesVersionFileHelpersMap.containsKey(datatypeCode)) {
            throw new NoVersionFileHelperResolvedException(datatypeCode);
        }
        return this.datatypesVersionFileHelpersMap.get(datatypeCode);
    }

}
