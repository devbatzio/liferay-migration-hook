/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.altec.portal.hook.persistence;

import integration.annotations.PersistenceController;
import integration.controller.impl.jpa.GenericJPAController;
import integration.impl.jpa.BasePersistenceJPAEntity;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bats-pc
 */
@Entity
@Table(name = "wp_term_taxonomy", catalog = "wp_inegsee_rsc", schema = "")
@PersistenceController(GenericJPAController.class)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WpTermTaxonomy.findAll", query = "SELECT w FROM WpTermTaxonomy w"),
    @NamedQuery(name = "WpTermTaxonomy.findByTermTaxonomyId", query = "SELECT w FROM WpTermTaxonomy w WHERE w.termTaxonomyId = :termTaxonomyId"),
    @NamedQuery(name = "WpTermTaxonomy.findByTermId", query = "SELECT w FROM WpTermTaxonomy w WHERE w.termId = :termId"),
    @NamedQuery(name = "WpTermTaxonomy.findByTaxonomy", query = "SELECT w FROM WpTermTaxonomy w WHERE w.taxonomy = :taxonomy"),
    @NamedQuery(name = "WpTermTaxonomy.findByParent", query = "SELECT w FROM WpTermTaxonomy w WHERE w.parent = :parent"),
    @NamedQuery(name = "WpTermTaxonomy.findByCount", query = "SELECT w FROM WpTermTaxonomy w WHERE w.count = :count")})
public class WpTermTaxonomy extends BasePersistenceJPAEntity {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "term_taxonomy_id")
    private Long termTaxonomyId;
    @JoinColumn(name = "term_id" , referencedColumnName = "term_id")
    @ManyToOne()
    private WpTerms termId;
    @Basic(optional = false)
    @Column(name = "taxonomy")
    private String taxonomy;
    @Basic(optional = false)
    @Lob
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "parent")
    private long parent;
    @Basic(optional = false)
    @Column(name = "count")
    private long count;
    @ManyToMany(mappedBy = "wpTermTaxonomyCollection")
    private Collection<WpPosts> wpPostsCollection;

    public WpTermTaxonomy() {
    }

    public WpTermTaxonomy(Long termTaxonomyId) {
        this.termTaxonomyId = termTaxonomyId;
    }

    public WpTermTaxonomy(Long termTaxonomyId, WpTerms termId, String taxonomy, String description, long parent, long count) {
        this.termTaxonomyId = termTaxonomyId;
        this.termId = termId;
        this.taxonomy = taxonomy;
        this.description = description;
        this.parent = parent;
        this.count = count;
    }

    public Long getTermTaxonomyId() {
        return termTaxonomyId;
    }

    public void setTermTaxonomyId(Long termTaxonomyId) {
        this.termTaxonomyId = termTaxonomyId;
    }

    public WpTerms getTermId() {
        return termId;
    }

    public void setTermId(WpTerms termId) {
        this.termId = termId;
    }

    public String getTaxonomy() {
        return taxonomy;
    }

    public void setTaxonomy(String taxonomy) {
        this.taxonomy = taxonomy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getParent() {
        return parent;
    }

    public void setParent(long parent) {
        this.parent = parent;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Collection<WpPosts> getWpPostsCollection() {
        wpPostsCollection = getLazyEntitiesCollection(wpPostsCollection, "getWpPostsCollection");
        return wpPostsCollection;
    }

    public void setWpPostsCollection(Collection<WpPosts> wpPostsCollection) {
        this.wpPostsCollection = wpPostsCollection;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (termTaxonomyId != null ? termTaxonomyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WpTermTaxonomy)) {
            return false;
        }
        WpTermTaxonomy other = (WpTermTaxonomy) object;
        if ((this.termTaxonomyId == null && other.termTaxonomyId != null) || (this.termTaxonomyId != null && !this.termTaxonomyId.equals(other.termTaxonomyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gr.altec.portal.hook.WpTermTaxonomy[ termTaxonomyId=" + termTaxonomyId + " ]";
    }
    
}
