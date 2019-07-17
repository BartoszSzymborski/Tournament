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

/**
 *
 * @author bartosz.szymborski
 */
@Entity
@Table(name = "\"TournamentEncounter\"")
@NamedQueries({
    @NamedQuery(name = "TournamentEncounter.findAll", query = "SELECT t FROM TournamentEncounter t"),
    @NamedQuery(name = "TournamentEncounter.findById", query = "SELECT t FROM TournamentEncounter t WHERE t.id = :id"),
    @NamedQuery(name = "TournamentEncounter.findByCzyFazaGrupowa", query = "SELECT t FROM TournamentEncounter t WHERE t.czyFazaGrupowa = :czyFazaGrupowa")})
public class TournamentEncounter implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "\"czyFazaGrupowa\"")
    private boolean czyFazaGrupowa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTournamentEncounter")
    private Collection<TournamentEncounterContender> tournamentEncounterContenderCollection;
    @OneToMany(mappedBy = "idPreviousTournamentEncounter")
    private Collection<TournamentEncounterContender> tournamentEncounterContenderCollection1;

    public TournamentEncounter() {
    }

    public TournamentEncounter(Long id) {
        this.id = id;
    }

    public TournamentEncounter(Long id, boolean czyFazaGrupowa) {
        this.id = id;
        this.czyFazaGrupowa = czyFazaGrupowa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getCzyFazaGrupowa() {
        return czyFazaGrupowa;
    }

    public void setCzyFazaGrupowa(boolean czyFazaGrupowa) {
        this.czyFazaGrupowa = czyFazaGrupowa;
    }

    public Collection<TournamentEncounterContender> getTournamentEncounterContenderCollection() {
        return tournamentEncounterContenderCollection;
    }

    public void setTournamentEncounterContenderCollection(Collection<TournamentEncounterContender> tournamentEncounterContenderCollection) {
        this.tournamentEncounterContenderCollection = tournamentEncounterContenderCollection;
    }

    public Collection<TournamentEncounterContender> getTournamentEncounterContenderCollection1() {
        return tournamentEncounterContenderCollection1;
    }

    public void setTournamentEncounterContenderCollection1(Collection<TournamentEncounterContender> tournamentEncounterContenderCollection1) {
        this.tournamentEncounterContenderCollection1 = tournamentEncounterContenderCollection1;
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
        if (!(object instanceof TournamentEncounter)) {
            return false;
        }
        TournamentEncounter other = (TournamentEncounter) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "szymborski.bartosz.serwis.pgnig.entity.TournamentEncounter[ id=" + id + " ]";
    }
    
}
