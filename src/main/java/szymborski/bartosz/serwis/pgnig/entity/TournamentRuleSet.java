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
@Table(name = "\"TournamentRuleSet\"")
@NamedQueries({
    @NamedQuery(name = "TournamentRuleSet.findAll", query = "SELECT t FROM TournamentRuleSet t"),
    @NamedQuery(name = "TournamentRuleSet.findById", query = "SELECT t FROM TournamentRuleSet t WHERE t.id = :id"),
    @NamedQuery(name = "TournamentRuleSet.findByIntegerValue", query = "SELECT t FROM TournamentRuleSet t WHERE t.integerValue = :integerValue"),
    @NamedQuery(name = "TournamentRuleSet.findByBooleanValue", query = "SELECT t FROM TournamentRuleSet t WHERE t.booleanValue = :booleanValue")})
public class TournamentRuleSet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "\"integerValue\"")
    private Short integerValue;
    @Column(name = "\"booleanValue\"")
    private Boolean booleanValue;
    @ManyToOne
    @JoinColumn(name = "\"idTournament\"", referencedColumnName = "id")
    private Tournament tournament;
    @ManyToOne
    @JoinColumn(name = "\"idTournamentRule\"", referencedColumnName = "id")
    private TournamentRule tournamentRule;

    public TournamentRuleSet() {
    }

    public TournamentRuleSet(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public TournamentRule getTournamentRule() {
        return tournamentRule;
    }

    public void setTournamentRule(TournamentRule tournamentRule) {
        this.tournamentRule = tournamentRule;
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
        if (!(object instanceof TournamentRuleSet)) {
            return false;
        }
        TournamentRuleSet other = (TournamentRuleSet) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "szymborski.bartosz.serwis.pgnig.entity.TournamentRuleSet[ id=" + id + " ]";
    }
    
}
