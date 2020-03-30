package model;

public class ProgrammeurBean {

    private String nom;
    private String prenom;
    private int anNaissance;
    private float prime;
    private float salaire;
    private String pseudo;

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
        return "ProgrammeurBean{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", anNaissance=" + anNaissance +
                ", prime=" + prime +
                ", salaire=" + salaire +
                ", pseudo='" + pseudo + '\'' +
                '}';
    }
}
