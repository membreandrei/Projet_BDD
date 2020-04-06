package utils;

public class Constantes {
    //Récupérer tous les programmeurs
    public final static String ALLPROGS = "SELECT * from PROGRAMMEUR"; 
    //Récupérer un programmeurs par ID (ID étant UNIQUE, pas besoin de LIMIT)
    public final static String PROGBYID = "SELECT * from PROGRAMMEUR where ID = ?";
    //Récupérer les programmeurs par nom
    public final static String PROGBYNAME = "SELECT * from PROGRAMMEUR where NOM like ?";
    //Récupérer les programmeurs par prénom
    public final static String PROGBYFIRSTNAME = "SELECT * from PROGRAMMEUR where PRENOM like ?";
    //Récupérer les programmeurs par année de naissance
    public final static String PROGBYYEAR = "SELECT * from PROGRAMMEUR where ANNAISSANCE like ?";
    //Supprimer un programeur à partir de son ID
    public final static String DELPROG = "DELETE FROM PROGRAMMEUR WHERE ID=?";
    //Changer le salaire d'un programmeur à partir de son ID
    public final static String CHANGESALARY = "UPDATE PROGRAMMEUR SET SALAIRE=? WHERE ID=?";
    //Créer un programmeur
    public final static String CREATEPROG = "INSERT INTO PROGRAMMEUR(NOM,PRENOM,ADRESSE,PSEUDO,RESPONSABLE,HOBBY,ANNAISSANCE,SALAIRE,PRIME) VALUES(?,?,?,?,?,?,?,?,?)";
}
