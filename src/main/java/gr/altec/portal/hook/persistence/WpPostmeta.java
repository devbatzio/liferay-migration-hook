/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.altec.portal.hook.persistence;

import integration.annotations.PersistenceController;
import integration.controller.impl.jpa.GenericJPAController;
import integration.impl.jpa.BasePersistenceJPAEntity;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
@PersistenceController(GenericJPAController.class)
@Table(name = "wp_postmeta", catalog = "wp_inegsee_rsc", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WpPostmeta.findAll", query = "SELECT w FROM WpPostmeta w"),
    @NamedQuery(name = "WpPostmeta.findByMetaId", query = "SELECT w FROM WpPostmeta w WHERE w.metaId = :metaId"),
    @NamedQuery(name = "WpPostmeta.findByPostId", query = "SELECT w FROM WpPostmeta w WHERE w.postId = :postId"),
    @NamedQuery(name = "WpPostmeta.findByMetaKey", query = "SELECT w FROM WpPostmeta w WHERE w.metaKey = :metaKey")})
public class WpPostmeta extends BasePersistenceJPAEntity {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "meta_id")
    private Long metaId;
    @JoinColumn(name = "post_id", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private WpPosts postId;
    @Column(name = "meta_key")
    private String metaKey;
    @Lob
    @Column(name = "meta_value")
    private String metaValue;

    public WpPostmeta() {
    }

    public WpPostmeta(Long metaId) {
        this.metaId = metaId;
    }

    public WpPostmeta(Long metaId, WpPosts postId) {
        this.metaId = metaId;
        this.postId = postId;
    }

    public Long getMetaId() {
        return metaId;
    }

    public void setMetaId(Long metaId) {
        this.metaId = metaId;
    }

    public WpPosts getPostId() {
        return postId;
    }

    public void setPostId(WpPosts postId) {
        this.postId = postId;
    }

    public String getMetaKey() {
        return metaKey;
    }

    public void setMetaKey(String metaKey) {
        this.metaKey = metaKey;
    }

    public String getMetaValue() {
        return metaValue;
    }

    public void setMetaValue(String metaValue) {
        this.metaValue = metaValue;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (metaId != null ? metaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WpPostmeta)) {
            return false;
        }
        WpPostmeta other = (WpPostmeta) object;
        if ((this.metaId == null && other.metaId != null) || (this.metaId != null && !this.metaId.equals(other.metaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gr.altec.portal.hook.persistence.WpPostmeta[ metaId=" + metaId + " ]";
    }
    
}
