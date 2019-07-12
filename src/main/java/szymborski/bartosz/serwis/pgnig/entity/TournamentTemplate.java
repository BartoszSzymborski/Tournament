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
import javax.validation.constraints.Size;

/**
 *
 * @author bartosz.szymborski
 */
@Entity
@Table(name = "\"TournamentTemplate\"")
@NamedQueries({
    @NamedQuery(name = "TournamentTemplate.findAll", query = "SELECT t FROM TournamentTemplate t"),
    @NamedQuery(name = "TournamentTemplate.findByName", query = "SELECT t FROM TournamentTemplate t WHERE t.name = :name"),
    @NamedQuery(name = "TournamentTemplate.findById", query = "SELECT t FROM TournamentTemplate t WHERE t.id = :id")})
public class TournamentTemplate implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 2147483647)
    @Column(name = "name")
    private String name;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTournamentTemplate")
    private Collection<TorunamentTemplateItem> torunamentTemplateItemCollection;

    public TournamentTemplate() {
    }

    public TournamentTemplate(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
        if (!(object instanceof TournamentTemplate)) {
            return false;
        }
        TournamentTemplate other = (TournamentTemplate) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "szymborski.bartosz.serwis.pgnig.entity.TournamentTemplate[ id=" + id + " ]";
    }
    
}
