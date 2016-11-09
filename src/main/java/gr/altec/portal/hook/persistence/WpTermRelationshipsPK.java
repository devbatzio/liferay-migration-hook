/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.altec.portal.hook.persistence;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author bats-pc
 */
@Embeddable
public class WpTermRelationshipsPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "object_id")
    private long objectId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "term_taxonomy_id")
    private long termTaxonomyId;

    public WpTermRelationshipsPK() {
    }

    public WpTermRelationshipsPK(long objectId, long termTaxonomyId) {
        this.objectId = objectId;
        this.termTaxonomyId = termTaxonomyId;
    }

    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    public long getTermTaxonomyId() {
        return termTaxonomyId;
    }

    public void setTermTaxonomyId(long termTaxonomyId) {
        this.termTaxonomyId = termTaxonomyId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) objectId;
        hash += (int) termTaxonomyId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WpTermRelationshipsPK)) {
            return false;
        }
        WpTermRelationshipsPK other = (WpTermRelationshipsPK) object;
        if (this.objectId != other.objectId) {
            return false;
        }
        if (this.termTaxonomyId != other.termTaxonomyId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gr.altec.portal.hook.persistence.WpTermRelationshipsPK[ objectId=" + objectId + ", termTaxonomyId=" + termTaxonomyId + " ]";
    }
    
}
