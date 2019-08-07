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
@Table(name = "\"TournamentMatch\"")
@NamedQueries({
    @NamedQuery(name = "TournamentMatch.findAll", query = "SELECT t FROM TournamentMatch t"),
    @NamedQuery(name = "TournamentMatch.findById", query = "SELECT t FROM TournamentMatch t WHERE t.id = :id"),
    @NamedQuery(name = "TournamentMatch.findByContenderOneScore", query = "SELECT t FROM TournamentMatch t WHERE t.contenderOneScore = :contenderOneScore"),
    @NamedQuery(name = "TournamentMatch.findByContenderTwoScore", query = "SELECT t FROM TournamentMatch t WHERE t.contenderTwoScore = :contenderTwoScore")})
public class TournamentMatch implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "\"contenderOneScore\"")
    private Short contenderOneScore;
    @Column(name = "\"contenderTwoScore\"")
    private Short contenderTwoScore;
    @JoinColumn(name = "\"idTournamentEncounterContenderTwo\"", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TournamentEncounterContender idTournamentEncounterContenderTwo;
    @JoinColumn(name = "\"idTournamentEncounterContenderWinner\"", referencedColumnName = "id")
    @ManyToOne
    private TournamentEncounterContender idTournamentEncounterContenderWinner;
    @JoinColumn(name = "\"idTournamentEncounterContenderONE\"", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TournamentEncounterContender idTournamentEncounterContenderONE;

    public TournamentMatch() {
    }

    public TournamentMatch(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Short getContenderOneScore() {
        return contenderOneScore;
    }

    public void setContenderOneScore(Short contenderOneScore) {
        this.contenderOneScore = contenderOneScore;
    }

    public Short getContenderTwoScore() {
        return contenderTwoScore;
    }

    public void setContenderTwoScore(Short contenderTwoScore) {
        this.contenderTwoScore = contenderTwoScore;
    }

    public TournamentEncounterContender getIdTournamentEncounterContenderTwo() {
        return idTournamentEncounterContenderTwo;
    }

    public void setIdTournamentEncounterContenderTwo(TournamentEncounterContender idTournamentEncounterContenderTwo) {
        this.idTournamentEncounterContenderTwo = idTournamentEncounterContenderTwo;
    }

    public TournamentEncounterContender getIdTournamentEncounterContenderWinner() {
        return idTournamentEncounterContenderWinner;
    }

    public void setIdTournamentEncounterContenderWinner(TournamentEncounterContender idTournamentEncounterContenderWinner) {
        this.idTournamentEncounterContenderWinner = idTournamentEncounterContenderWinner;
    }

    public TournamentEncounterContender getIdTournamentEncounterContenderONE() {
        return idTournamentEncounterContenderONE;
    }

    public void setIdTournamentEncounterContenderONE(TournamentEncounterContender idTournamentEncounterContenderONE) {
        this.idTournamentEncounterContenderONE = idTournamentEncounterContenderONE;
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
        if (!(object instanceof TournamentMatch)) {
            return false;
        }
        TournamentMatch other = (TournamentMatch) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "szymborski.bartosz.serwis.pgnig.entity.TournamentMatch[ id=" + id + " ]";
    }
    
}
