package utils;

public class Constantes {
    public final static String ALLPROGS = "SELECT * from PROGRAMMEUR";
    public final static String PROGBYID = "SELECT * from PROGRAMMEUR where ID = ?";
    public final static String PROGBYNAME = "SELECT * from PROGRAMMEUR where NOM like ?";
    public final static String PROGBYFIRSTNAME = "SELECT * from PROGRAMMEUR where PRENOM like ?";
    public final static String PROGBYYEAR = "SELECT * from PROGRAMMEUR where ANNAISSANCE like ?";
}
