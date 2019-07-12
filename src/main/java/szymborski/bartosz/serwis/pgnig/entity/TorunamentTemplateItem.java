/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.serwis.pgnig.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author bartosz.szymborski
 */
@Entity
@Table(name = "\"TorunamentTemplateItem\"")
@NamedQueries({
    @NamedQuery(name = "TorunamentTemplateItem.findAll", query = "SELECT t FROM TorunamentTemplateItem t"),
    @NamedQuery(name = "TorunamentTemplateItem.findByIntegerValue", query = "SELECT t FROM TorunamentTemplateItem t WHERE t.integerValue = :integerValue"),
    @NamedQuery(name = "TorunamentTemplateItem.findByBooleanValue", query = "SELECT t FROM TorunamentTemplateItem t WHERE t.booleanValue = :booleanValue"),
    @NamedQuery(name = "TorunamentTemplateItem.findById", query = "SELECT t FROM TorunamentTemplateItem t WHERE t.id = :id")})
public class TorunamentTemplateItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "\"integerValue\"", nullable = true)
    private Short integerValue;
    @Column(name = "\"booleanValue\"", nullable = true)
    private Boolean booleanValue;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;
    @JoinColumn(name = "\"idTournamentRule\"", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TournamentRule idTournamentRule;
    @JoinColumn(name = "\"idTournamentTemplate\"", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TournamentTemplate idTournamentTemplate;

    public TorunamentTemplateItem() {
    }

    public TorunamentTemplateItem(Long id) {
        this.id = id;
    }

    public Short getIntegerValue() {
        return integerValue;
    }

    public void setIntegerValue(Short integerValue) {
        this.integerValue = integerValue;
    }

    public Boolean getBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(Boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TournamentRule getIdTournamentRule() {
        return idTournamentRule;
    }

    public void setIdTournamentRule(TournamentRule idTournamentRule) {
        this.idTournamentRule = idTournamentRule;
    }

    public TournamentTemplate getIdTournamentTemplate() {
        return idTournamentTemplate;
    }

    public void setIdTournamentTemplate(TournamentTemplate idTournamentTemplate) {
        this.idTournamentTemplate = idTournamentTemplate;
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
        if (!(object instanceof TorunamentTemplateItem)) {
            return false;
        }
        TorunamentTemplateItem other = (TorunamentTemplateItem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "szymborski.bartosz.serwis.pgnig.entity.TorunamentTemplateItem[ id=" + id + " ]";
    }
    
}
