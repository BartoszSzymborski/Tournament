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
@Table(name = "\"TournamentContender\"")
@NamedQueries({
    @NamedQuery(name = "TournamentContender.findAll", query = "SELECT t FROM TournamentContender t"),
    @NamedQuery(name = "TournamentContender.findById", query = "SELECT t FROM TournamentContender t WHERE t.id = :id")})
public class TournamentContender implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @JoinColumn(name = "\"idContender\"", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Contender idContender;
    @JoinColumn(name = "\"idTournament\"")
    @ManyToOne(optional = false)
    private Tournament tournament;

    public TournamentContender() {
    }

    public TournamentContender(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Contender getIdContender() {
        return idContender;
    }

    public void setIdContender(Contender idContender) {
        this.idContender = idContender;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
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
        if (!(object instanceof TournamentContender)) {
            return false;
        }
        TournamentContender other = (TournamentContender) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "szymborski.bartosz.serwis.pgnig.entity.TournamentContender[ id=" + id + " ]";
    }
    
}
