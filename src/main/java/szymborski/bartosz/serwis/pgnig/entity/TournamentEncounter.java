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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author bartosz.szymborski
 */
@Entity
@Table(name = "\"TournamentEncounter\"")
@NamedQueries({
    @NamedQuery(name = "TournamentEncounter.findAll", query = "SELECT t FROM TournamentEncounter t"),
    @NamedQuery(name = "TournamentEncounter.findById", query = "SELECT t FROM TournamentEncounter t WHERE t.id = :id"),
    @NamedQuery(name = "TournamentEncounter.findByCzyFazaGrupowa", query = "SELECT t FROM TournamentEncounter t WHERE t.czyFazaGrupowa = :czyFazaGrupowa"),
    @NamedQuery(name = "TournamentEncounter.findByStage", query = "SELECT t FROM TournamentEncounter t WHERE t.stage = :stage"),
    @NamedQuery(name = "TournamentEncounter.findByAddInfo", query = "SELECT t FROM TournamentEncounter t WHERE t.addInfo = :addInfo")})
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
    @Basic(optional = false)
    @NotNull
    @Column(name = "stage")
    private long stage;
    @Size(max = 2147483647)
    @Column(name = "\"addInfo\"")
    private String addInfo;
    @JoinColumn(name = "\"idTournament\"", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Tournament idTournament;

    public TournamentEncounter() {
    }

    public TournamentEncounter(Long id) {
        this.id = id;
    }

    public TournamentEncounter(Long id, boolean czyFazaGrupowa, long stage) {
        this.id = id;
        this.czyFazaGrupowa = czyFazaGrupowa;
        this.stage = stage;
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

    public long getStage() {
        return stage;
    }

    public void setStage(long stage) {
        this.stage = stage;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public Tournament getIdTournament() {
        return idTournament;
    }

    public void setIdTournament(Tournament idTournament) {
        this.idTournament = idTournament;
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
