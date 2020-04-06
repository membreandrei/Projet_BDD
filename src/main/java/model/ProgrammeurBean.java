package model;

import java.io.Serializable;

public class ProgrammeurBean implements Serializable {

    private Integer id;
    private String nom;
    private String prenom;
    private int anNaissance;
    private float prime;
    private float salaire;
    private String pseudo;
    private String adresse;
    private String responsable;
    private String hobby;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getAnNaissance() {
        return anNaissance;
    }

    public void setAnNaissance(int anNaissance) {
        this.anNaissance = anNaissance;
    }

    public float getPrime() {
        return prime;
    }

    public void setPrime(float prime) {
        this.prime = prime;
    }

    public float getSalaire() {
        return salaire;
    }

    public void setSalaire(float salaire) {
        this.salaire = salaire;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    @Override
    public String toString() {
        return  "Id : " + id + '\n' +
                "Nom : " + nom + '\n' +
                "Prenom : " + prenom + '\n' +
                "Année de Naissance : " + anNaissance + '\n' +
                "Prime : " + prime + '\n' +
                "Salaire : " + salaire + '\n' +
                "Pseudo : " + pseudo + '\n' +
                "Adresse : " + adresse + '\n' +
                "Responsable : " + responsable + '\n' +
                "Hobby : " + hobby + '\n' +
                "\n";
    }
}
