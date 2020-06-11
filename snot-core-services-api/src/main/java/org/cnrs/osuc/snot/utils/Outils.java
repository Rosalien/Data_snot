package org.cnrs.osuc.snot.utils;

import java.time.temporal.TemporalAdjuster;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.inra.ecoinfo.AbstractJPADAO;
import static org.inra.ecoinfo.AbstractJPADAO.whereDateBetween;
import org.inra.ecoinfo.dataset.versioning.entity.Dataset;
import org.inra.ecoinfo.dataset.versioning.entity.VersionFile;
import org.inra.ecoinfo.mga.business.IUser;
import org.inra.ecoinfo.mga.business.composite.INodeable;
import org.inra.ecoinfo.mga.business.composite.NodeDataSet;
import org.inra.ecoinfo.mga.business.composite.NodeDataSet_;
import org.inra.ecoinfo.mga.business.composite.activities.ExtractActivity;
import org.inra.ecoinfo.mga.business.composite.activities.ExtractActivity_;
import org.inra.ecoinfo.mga.business.composite.groups.ICompositeGroup;
import org.inra.ecoinfo.mga.enums.WhichTree;

/**
 *
 * @author ptcherniati
 */
public class Outils {

    static public List<String> getGroups(IUser user) {
        List<String> l = new LinkedList();
        if (user instanceof ICompositeGroup) {
            l = ((ICompositeGroup) user).getAllGroups().stream()
                    .filter(g -> g.getWhichTree().equals(WhichTree.TREEDATASET))
                    .map(g -> g.getGroupName())
                    .collect(Collectors.toList());
        } else {
            l.add(user.getLogin());
        }
        return l;
    }

    /**
     *
     * @param <T>
     * @param dataset
     * @param nodeableType
     * @return
     */
    static public <T extends INodeable> Optional<T> chercherNodeable(Dataset dataset, Class<T> nodeableType) {
        return Optional.ofNullable(dataset)
                .map(d -> d.getRealNode())
                .map(rn -> rn.getNodeByNodeableTypeResource(nodeableType))
                .map(rn -> (T) rn.getNodeable());
    }

    /**
     *
     * @param <T>
     * @param versionFile
     * @param nodeableType
     * @return
     */
    static public <T extends INodeable> Optional<T> chercherNodeable(VersionFile versionFile, final Class<T> nodeableType) {
        return Optional.ofNullable(versionFile)
                .map(v -> v.getDataset())
                .map(d -> chercherNodeable(d, nodeableType).get());
    }

    /**
     *
     * @param user
     * @param criteria
     * @param predicatesAnd
     * @param builder
     * @param vns
     * @param dateMesure
     */
    static public void addRestrictiveRequestOnRoles(IUser user, CriteriaQuery criteria, List<Predicate> predicatesAnd, CriteriaBuilder builder, Path<NodeDataSet> vns, final Path<? extends TemporalAdjuster> dateMesure) {
        if (!user.getIsRoot()) {
            List<String> groups = getGroups(user);
            Root<ExtractActivity> er = criteria.from(ExtractActivity.class);
            predicatesAnd.add(er.get(ExtractActivity_.login).in(groups));
            predicatesAnd.add(builder.equal(er.get(ExtractActivity_.idNode), vns.get(NodeDataSet_.id)));
            Optional.ofNullable(dateMesure)
                    .ifPresent(d->predicatesAnd.add(AbstractJPADAO.whereDateBetween(d, er.get(ExtractActivity_.dateStart), er.get(ExtractActivity_.dateEnd))));
        }
    }

    /**
     *
     * @param user
     * @param criteria
     * @param predicatesAnd
     * @param builder
     * @param vns
     * @param dateMesure
     */
    static public void addRestrictiveRequestOnRolesForMounthValue(IUser user, CriteriaQuery criteria, List<Predicate> predicatesAnd, CriteriaBuilder builder, Path<NodeDataSet> vns, final Path<? extends TemporalAdjuster> dateMesure) {
        if (!user.getIsRoot()) {
            Root<ExtractActivity> er = criteria.from(ExtractActivity.class);
            predicatesAnd.add(builder.equal(er.get(ExtractActivity_.login), user.getLogin()));
            predicatesAnd.add(builder.equal(er.get(ExtractActivity_.idNode), vns.get(NodeDataSet_.id)));
            Optional.ofNullable(dateMesure)
                    .ifPresent(d -> predicatesAnd.add(AbstractJPADAO.whereDateBetween(d, er.get(ExtractActivity_.dateStart), er.get(ExtractActivity_.dateEnd))));
        }
    }
}
