/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.serwis.pgnig.enums;



/**
 *
 * @author bartosz.szymborski
 */
public enum TournamentRuleEnum{
    
    LICZBA_DRUZYN("liczba_druzyn"), 
    FAZA_GRUPOWA("faza_grupowa"),
    LICZBA_GRUP("liczba_grup"),
    ILE_DRUZYN_AWANSUJE_Z_GRUPY("ile_druzyn_awansuje_z_grupy"),
    DRUGA_FAZA_TURNIEJU_GRUPOWA("druga_faza_turnieju_grupowa"),
    LICZBA_PUNKTOW_ZA_ZWYCIESTWO("liczba_punktow_za_zwyciestwo"),
    LICZBA_PUNKTOW_ZA_REMIS("liczba_punktow_za_remis"),
    LICZBA_PUNKTOW_ZA_PORAZKE("liczba_punktow_za_porazke"),
    MECZ_REWANZOWY_W_FAZIE_GRUPOWEJ("mecz_rewanzowy_w_fazie_grupowej"),
    MECZ_REWANZOWY_W_FAZIE_PUCHAROWEJ("mecz_rewanzowy_w_fazie_pucharowej"),
    MECZ_O_TRZECIE_MIEJSCE("mecz_o_trzecie_miejsce");
    
     private final String messKey;

    private TournamentRuleEnum(String messKey) {
        this.messKey = messKey;
    }

    public String getMessKey() {
        return messKey;
    }

    @Override
    public String toString() {
        return this.getMessKey();
    }
    
    
}
