package utils;

public final class Constantes {

    //R�cup�rer tous les programmeurs
    public final static String ALLPROGS = "SELECT * from PROGRAMMEUR";

    //R�cup�rer un programmeurs par ID (ID �tant UNIQUE, pas besoin de LIMIT)
    public final static String PROGBYID = "SELECT * from PROGRAMMEUR where ID = ?";

    //R�cup�rer les programmeurs par nom
    public final static String PROGBYNAME = "SELECT * from PROGRAMMEUR where NOM like ?";

    //R�cup�rer les programmeurs par pr�nom
    public final static String PROGBYFIRSTNAME = "SELECT * from PROGRAMMEUR where PRENOM like ?";

    //R�cup�rer les programmeurs par ann�e de naissance
    public final static String PROGBYYEAR = "SELECT * from PROGRAMMEUR where ANNAISSANCE LIKE ?";

    //Supprimer un programeur � partir de son ID
    public final static String DELPROG = "DELETE FROM PROGRAMMEUR WHERE ID=?";

    //Changer le salaire d'un programmeur � partir de son ID
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

    //Cr�er un programmeur
    public final static String CREATEPROG = "INSERT INTO PROGRAMMEUR(NOM,PRENOM,ADRESSE,PSEUDO,RESPONSABLE,HOBBY,ANNAISSANCE,SALAIRE,PRIME) VALUES(?,?,?,?,?,?,?,?,?)";

    //Serveur
    public final static String SERVEUR = "54.154.23.110";

    //Nom BDD
    public final static String BDD = "APTN61_BD";

    //Url
    public final static String URL = "jdbc:mysql://" + SERVEUR + "/" + BDD;

    //User
    public final static String USER = "adm";

    //Mdp
    public final static String PASSWORD = "adm";

}
