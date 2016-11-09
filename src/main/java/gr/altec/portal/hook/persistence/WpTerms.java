/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.altec.portal.hook.persistence;

import integration.annotations.PersistenceController;
import integration.controller.impl.jpa.GenericJPAController;
import integration.impl.jpa.BasePersistenceJPAEntity;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bats-pc
 */
@Entity
@PersistenceController(GenericJPAController.class)
@Table(name = "wp_terms", catalog = "wp_inegsee_rsc", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WpTerms.findAll", query = "SELECT w FROM WpTerms w"),
    @NamedQuery(name = "WpTerms.findByTermId", query = "SELECT w FROM WpTerms w WHERE w.termId = :termId"),
    @NamedQuery(name = "WpTerms.findByName", query = "SELECT w FROM WpTerms w WHERE w.name = :name"),
    @NamedQuery(name = "WpTerms.findBySlug", query = "SELECT w FROM WpTerms w WHERE w.slug = :slug"),
    @NamedQuery(name = "WpTerms.findByTermGroup", query = "SELECT w FROM WpTerms w WHERE w.termGroup = :termGroup")})
public class WpTerms extends BasePersistenceJPAEntity {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "term_id")
    private Long termId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "slug")
    private String slug;
    @Basic(optional = false)
    @Column(name = "term_group")
    private long termGroup;
    @OneToMany(mappedBy = "termId", fetch = FetchType.LAZY)
    private Collection<WpTermTaxonomy> wpTermTaxonomyCollection;
    public WpTerms() {
    }

    public WpTerms(Long termId) {
        this.termId = termId;
    }

    public WpTerms(Long termId, String name, String slug, long termGroup) {
        this.termId = termId;
        this.name = name;
        this.slug = slug;
        this.termGroup = termGroup;
    }

    public Long getTermId() {
        return termId;
    }

    public void setTermId(Long termId) {
        this.termId = termId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public long getTermGroup() {
        return termGroup;
    }

    public void setTermGroup(long termGroup) {
        this.termGroup = termGroup;
    }

    public Collection<WpTermTaxonomy> getWpTermTaxonomyCollection() {
        wpTermTaxonomyCollection = getLazyEntitiesCollection(wpTermTaxonomyCollection, "getWpTermTaxonomyCollection");
        return wpTermTaxonomyCollection;
    }

    public void setWpTermTaxonomyCollection(Collection<WpTermTaxonomy> wpTermTaxonomyCollection) {
        this.wpTermTaxonomyCollection = wpTermTaxonomyCollection;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (termId != null ? termId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WpTerms)) {
            return false;
        }
        WpTerms other = (WpTerms) object;
        if ((this.termId == null && other.termId != null) || (this.termId != null && !this.termId.equals(other.termId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gr.altec.portal.hook.WpTerms[ termId=" + termId + " ]";
    }
    
}
