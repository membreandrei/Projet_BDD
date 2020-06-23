package model;

import java.io.Serializable;

public class Media implements Serializable {

    private Integer id;
    private String nom;
    private String idIna;
    private String type;
    private Boolean estPublic;

// Liste des m�thodes permettant de r�cup�rer les diff�rentes variables de la classe Média

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getIdIna() {
        return idIna;
    }

    public void setIdIna(String idIna) {
        this.idIna = idIna;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getEstPublic() {
        return estPublic;
    }

    public void setEstPublic(Boolean estPublic) {
        this.estPublic = estPublic;
    }

    /**
     * �dition de l'output du toString de la classe.
     *
     * @return
     */
    @Override
    public String toString() {
        return "Media{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", idIna='" + idIna + '\'' +
                ", type='" + type + '\'' +
                ", estPublic=" + estPublic +
                '}';
    }
}
