package model;

import java.io.Serializable;

public class Moment implements Serializable {

    private Integer id;
    private String dateMoment;
    private Boolean estFerie;
    private String vacances;
    private String jour;
    private Integer heure;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDateMoment() {
        return dateMoment;
    }

    public void setDateMoment(String dateMoment) {
        this.dateMoment = dateMoment;
    }

    public Boolean getEstFerie() {
        return estFerie;
    }

    public void setEstFerie(Boolean estFerie) {
        this.estFerie = estFerie;
    }

    public String getVacances() {
        return vacances;
    }

    public void setVacances(String vacances) {
        this.vacances = vacances;
    }

    public String getJour() {
        return jour;
    }

    public void setJour(String jour) {
        this.jour = jour;
    }

    public Integer getHeure() {
        return heure;
    }

    public void setHeure(Integer heure) {
        this.heure = heure;
    }

    @Override
    public String toString() {
        return "Moment{" +
                "id=" + id +
                ", dateMoment='" + dateMoment + '\'' +
                ", estFerie=" + estFerie +
                ", vacances='" + vacances + '\'' +
                ", jour='" + jour + '\'' +
                ", heure=" + heure +
                '}';
    }
}
