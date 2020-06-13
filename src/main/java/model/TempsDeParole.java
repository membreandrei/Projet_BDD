package model;

import java.io.Serializable;

public class TempsDeParole implements Serializable {

    private Integer id;
    private Float tempsFemmes;
    private Float tempsHommes;
    private Float tempsMusique;
    private Media media;
    private Moment moment;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getTempsFemmes() {
        return tempsFemmes;
    }

    public void setTempsFemmes(Float tempsFemmes) {
        this.tempsFemmes = tempsFemmes;
    }

    public Float getTempsHommes() {
        return tempsHommes;
    }

    public void setTempsHommes(Float tempsHommes) {
        this.tempsHommes = tempsHommes;
    }

    public Float getTempsMusique() {
        return tempsMusique;
    }

    public void setTempsMusique(Float tempsMusique) {
        this.tempsMusique = tempsMusique;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public Moment getMoment() {
        return moment;
    }

    public void setMoment(Moment moment) {
        this.moment = moment;
    }

    @Override
    public String toString() {
        return "TempsDeParole{" +
                "id=" + id +
                ", tempsFemmes=" + tempsFemmes +
                ", tempsHommes=" + tempsHommes +
                ", tempsMusique=" + tempsMusique +
                ", media=" + media +
                ", moment=" + moment +
                '}';
    }
}
