package utils;

public final class Constantes {

    /**
     * R�cup�rer tous les programmeurs
     */
    public final static String ALLPROGS = "SELECT * from PROGRAMMEUR";

    /**
     * R�cup�rer un programmeurs par ID (ID �tant UNIQUE, pas besoin de LIMIT)
     */
    public final static String PROGBYID = "SELECT * from PROGRAMMEUR where ID = ?";

    /**
     * R�cup�rer les programmeurs par nom
     */
    public final static String PROGBYNAME = "SELECT * from PROGRAMMEUR where NOM like ?";

    /**
     * R�cup�rer les programmeurs par pr�nom
     */
    public final static String PROGBYFIRSTNAME = "SELECT * from PROGRAMMEUR where PRENOM like ?";

    /**
     * R�cup�rer les programmeurs par ann�e de naissance
     */
    public final static String PROGBYYEAR = "SELECT * from PROGRAMMEUR where ANNAISSANCE LIKE ?";

    /**
     * Supprimer un programeur � partir de son ID
     */
    public final static String DELPROG = "DELETE FROM PROGRAMMEUR WHERE ID=?";

    /**
     * Changer le salaire d'un programmeur � partir de son ID
     */
    public final static String EDITPROG = "" +
            "UPDATE PROGRAMMEUR " +
            "SET NOM=?," +
            "    PRENOM=?," +
            "    ADRESSE=?," +
            "    PSEUDO=?," +
            "    RESPONSABLE=?," +
            "    HOBBY=?," +
            "    ANNAISSANCE=?," +
            "    SALAIRE=?," +
            "    PRIME=? " +
            "WHERE ID=?";

    /**
     * Cr�er un Media
     */
    public final static String CREATEMEDIA = "INSERT INTO media(identifiant_ina,type,nom,est_public) VALUES(?,?,?,?)";

    /**
     * Cr�er un Moment
     */
    public final static String CREATEMOMENT = "INSERT INTO moment(date_moment,est_ferie,vacances,heure,jour) VALUES(?,?,?,?,?)";

    /**
     * Cr�er un Temp de parole
     */

    public final static String CREATETEMPSDEPAROLE = "INSERT INTO temps_de_parole(\ttemps_femme,temps_homme,temps_musique,id_media,id_moment) VALUES(?,?,?,?,?)";

    /**
     * recupérer un id media a partir de son nom
     */

    public final static String GETIDMEDIABYNAME = "SELECT MAX(id_moment) AS max_id FROM media";

    /**
     * recupérer le dernier id de la table moment
     */

    public final static String GETMAXIDMOMENT = "SELECT MAX(id_moment) AS max_id FROM moment";

    /**
     * Adresse du serveur
     */
    public final static String SERVEUR = "localhost";

    /**
     * Nom de la base
     */
    public final static String BDD = "projet_bdd";

    /**
     * Url
     */
    public final static String URL = "jdbc:mysql://" + SERVEUR + "/" + BDD;

    /**
     * User
     */
    public final static String USER = "root";

    /**
     * Mot de passe
     */
    public final static String PASSWORD = "";

}
