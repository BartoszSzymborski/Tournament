/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.serwis.pgnig.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author bartosz.szymborski
 */
@Entity
@Table(name = "TournamentRule")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TournamentRule.findAll", query = "SELECT t FROM TournamentRule t"),
    @NamedQuery(name = "TournamentRule.findByName", query = "SELECT t FROM TournamentRule t WHERE t.name = :name"),
    @NamedQuery(name = "TournamentRule.findByIntegralType", query = "SELECT t FROM TournamentRule t WHERE t.integralType = :integralType"),
    @NamedQuery(name = "TournamentRule.findById", query = "SELECT t FROM TournamentRule t WHERE t.id = :id")})
public class TournamentRule implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 2147483647)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "integralType")
    private boolean integralType;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTournamentRule")
    private Collection<TorunamentTemplateItem> torunamentTemplateItemCollection;

    public TournamentRule() {
    }

    public TournamentRule(Long id) {
        this.id = id;
    }

    public TournamentRule(Long id, boolean integralType) {
        this.id = id;
        this.integralType = integralType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIntegralType() {
        return integralType;
    }

    public void setIntegralType(boolean integralType) {
        this.integralType = integralType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlTransient
    public Collection<TorunamentTemplateItem> getTorunamentTemplateItemCollection() {
        return torunamentTemplateItemCollection;
    }

    public void setTorunamentTemplateItemCollection(Collection<TorunamentTemplateItem> torunamentTemplateItemCollection) {
        this.torunamentTemplateItemCollection = torunamentTemplateItemCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TournamentRule)) {
            return false;
        }
        TournamentRule other = (TournamentRule) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "szymborski.bartosz.serwis.pgnig.entity.TournamentRule[ id=" + id + " ]";
    }
    
}
