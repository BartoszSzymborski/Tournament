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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author bartosz.szymborski
 */
@Entity
@Table(name = "\"TournamentEncounterContender\"")
@NamedQueries({
    @NamedQuery(name = "TournamentEncounterContender.findAll", query = "SELECT t FROM TournamentEncounterContender t"),
    @NamedQuery(name = "TournamentEncounterContender.findById", query = "SELECT t FROM TournamentEncounterContender t WHERE t.id = :id")})
public class TournamentEncounterContender implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTournamentEncounterContenderTwo")
    private Collection<TournamentMatch> tournamentMatchCollection;
    @OneToMany(mappedBy = "idTournamentEncounterContenderWinner")
    private Collection<TournamentMatch> tournamentMatchCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTournamentEncounterContenderONE")
    private Collection<TournamentMatch> tournamentMatchCollection2;
    @JoinColumn(name = "idTournamentContender", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TournamentContender idTournamentContender;
    @JoinColumn(name = "idTournamentEncounter", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TournamentEncounter idTournamentEncounter;
    @JoinColumn(name = "idPreviousTournamentEncounter", referencedColumnName = "id")
    @ManyToOne
    private TournamentEncounter idPreviousTournamentEncounter;

    public TournamentEncounterContender() {
    }

    public TournamentEncounterContender(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Collection<TournamentMatch> getTournamentMatchCollection() {
        return tournamentMatchCollection;
    }

    public void setTournamentMatchCollection(Collection<TournamentMatch> tournamentMatchCollection) {
        this.tournamentMatchCollection = tournamentMatchCollection;
    }

    public Collection<TournamentMatch> getTournamentMatchCollection1() {
        return tournamentMatchCollection1;
    }

    public void setTournamentMatchCollection1(Collection<TournamentMatch> tournamentMatchCollection1) {
        this.tournamentMatchCollection1 = tournamentMatchCollection1;
    }

    public Collection<TournamentMatch> getTournamentMatchCollection2() {
        return tournamentMatchCollection2;
    }

    public void setTournamentMatchCollection2(Collection<TournamentMatch> tournamentMatchCollection2) {
        this.tournamentMatchCollection2 = tournamentMatchCollection2;
    }

    public TournamentContender getIdTournamentContender() {
        return idTournamentContender;
    }

    public void setIdTournamentContender(TournamentContender idTournamentContender) {
        this.idTournamentContender = idTournamentContender;
    }

    public TournamentEncounter getIdTournamentEncounter() {
        return idTournamentEncounter;
    }

    public void setIdTournamentEncounter(TournamentEncounter idTournamentEncounter) {
        this.idTournamentEncounter = idTournamentEncounter;
    }

    public TournamentEncounter getIdPreviousTournamentEncounter() {
        return idPreviousTournamentEncounter;
    }

    public void setIdPreviousTournamentEncounter(TournamentEncounter idPreviousTournamentEncounter) {
        this.idPreviousTournamentEncounter = idPreviousTournamentEncounter;
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
        if (!(object instanceof TournamentEncounterContender)) {
            return false;
        }
        TournamentEncounterContender other = (TournamentEncounterContender) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "szymborski.bartosz.serwis.pgnig.entity.TournamentEncounterContender[ id=" + id + " ]";
    }
    
}
