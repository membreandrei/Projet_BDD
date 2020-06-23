package utils;

public final class Constantes {

    /**
     * R�cup�rer tous les programmeurs
     */
    public final static String ALLMEDIA = "SELECT * from media";

    /**
     * R�cup�rer un programmeurs par ID (ID �tant UNIQUE, pas besoin de LIMIT)
     */
    public final static String MEDIABYID = "SELECT * from media where id_media = ?";

    /**
     * R�cup�rer un programmeurs par ID (ID �tant UNIQUE, pas besoin de LIMIT)
     */
    public final static String MOMENTBYID = "SELECT DATE_FORMAT(STR_TO_DATE(date_moment, '%d/%m/%Y'), '%Y') AS annee, " +
            "id_moment, " +
            "est_ferie, " +
            "vacances, " +
            "heure, " +
            "jour " +
            "from moment where id_moment = ?";

    /**
     * R�cup�rer les programmeurs par nom
     */
    public final static String MEDIABYNAME = "SELECT * from media where nom like ?";

    /**
     * R�cup�rer les programmeurs par pr�nom
     */
    public final static String PROGBYFIRSTNAME = "SELECT * from PROGRAMMEUR where PRENOM like ?";

    /**
     * R�cup�rer les programmeurs par ann�e de naissance
     */
    public final static String PROGBYYEAR = "SELECT * from PROGRAMMEUR where ANNAISSANCE LIKE ?";

    /**
     * Supprimer un média � partir de son ID
     */
    public final static String DELMEDIA = "DELETE FROM media WHERE id_media = ?";

    /**
     * Supprimer un temps_de_parole � partir de son ID
     */
    public final static String DELTEMPSDEPAROLE = "DELETE FROM temps_de_parole WHERE id_media = ?";

    /**
     * Temps de parole/musique par moment
     */
    public final static String TEMPSALLWHEREHOMME2FOISSUPFEMME = "select DISTINCT ME.nom                              as nom_media,\n" +
            "DATE_FORMAT(STR_TO_DATE(mo.date_moment, '%d/%m/%Y'), '%Y')                               AS annee,\n" +
            "                AVG(TDP.temps_musique)                                     AS temps_musique,\n" +
            "TDP.id_media   AS id_media,\n" +
            "    MO.id_moment                       as id_moment,\n" +
            "    AVG(TDP.temps_femme)               AS temps_femme,\n" +
            "    AVG(TDP.temps_homme)               AS temps_homme\n" +
            "    from media                         AS ME\n" +
            "    INNER JOIN temps_de_parole AS tdp on ME.id_media = tdp.id_media\n" +
            "    inner join moment AS MO ON MO.id_moment = tdp.id_moment\n" +
            "    WHERE (select AVG(tdp2.temps_femme)\n" +
            "    from temps_de_parole as tdp2\n" +
            "    where tdp2.id_media = ME.id_media) * 2 < (select AVG(tdp3.temps_homme)\n" +
            "    from temps_de_parole as tdp3\n" +
            "    where tdp3.id_media = ME.id_media)\n" +
            "    group by ME.id_media";

    public final static String TEMPSPARMOMENT = "select DATE_FORMAT(STR_TO_DATE(mo.date_moment, '%d/%m/%Y'), '%Y')                            AS annee,\n" +
            "       TDP.id_media   AS id_media,\n" +
            "                MO.id_moment                                                as id_moment,\n" +
            "       AVG((TDP.temps_femme / (TDP.temps_femme + TDP.temps_homme + TDP.temps_musique) * 100))   AS temps_femme,\n" +
            "       AVG((TDP.temps_homme / (TDP.temps_femme + TDP.temps_homme + TDP.temps_musique)) * 100)   AS temps_homme,\n" +
            "       AVG((TDP.temps_musique / (TDP.temps_femme + TDP.temps_homme + TDP.temps_musique)) * 100) AS temps_musique\n" +
            "from media AS ME\n" +
            "         INNER JOIN temps_de_parole AS tdp on ME.id_media = tdp.id_media\n" +
            "         inner join moment AS MO ON MO.id_moment = tdp.id_moment\n" +
            "group by annee;";


    /**
     * Temps de parole/musique par moment par année
     */
    public final static String TEMPSPARMOMENTPARANNEE = "SELECT YEAR(STR_TO_DATE(mo.date_moment, '%d/%m/%Y'))                                            AS annee,\n" +
            "       ME.id_media                                                                              AS id_media,\n" +
            "       MO.id_moment                                                                             AS id_moment,\n" +
            "       AVG((TDP.temps_femme / (TDP.temps_femme + TDP.temps_homme + TDP.temps_musique) * 100))   AS temps_femme,\n" +
            "       AVG((TDP.temps_homme / (TDP.temps_femme + TDP.temps_homme + TDP.temps_musique)) * 100)   AS temps_homme,\n" +
            "       AVG((TDP.temps_musique / (TDP.temps_femme + TDP.temps_homme + TDP.temps_musique)) * 100) AS temps_musique\n" +
            "FROM media AS ME\n" +
            "         INNER JOIN temps_de_parole AS tdp ON ME.id_media = tdp.id_media\n" +
            "         INNER JOIN moment AS MO ON MO.id_moment = tdp.id_moment\n" +
            "WHERE YEAR(STR_TO_DATE(mo.date_moment, '%d/%m/%Y')) = ?\n" +
            "GROUP BY YEAR(STR_TO_DATE(mo.date_moment, '%d/%m/%Y'))";


    /**
     * Temps de parole/musique par média
     */
    public final static String TEMPSPARMOMENTPARCHAINE = "select DISTINCT ME.nom        as nom_media,\n" +
            "                ME.id_media                                                as id_media,\n" +
            "                MO.id_moment                                                as id_moment,\n" +
            "                DATE_FORMAT(STR_TO_DATE(mo.date_moment, '%d/%m/%Y'), '%Y') as annee,\n" +
            "                AVG(TDP.temps_femme)                                       AS temps_femme,\n" +
            "                AVG(TDP.temps_homme)                                       AS temps_homme,\n" +
            "                AVG(TDP.temps_musique)                                     AS temps_musique\n" +
            "from media AS ME\n" +
            "         INNER JOIN temps_de_parole AS tdp on ME.id_media = tdp.id_media\n" +
            "         inner join moment AS MO ON MO.id_moment = tdp.id_moment\n" +
            "group by ME.nom, DATE_FORMAT(STR_TO_DATE(mo.date_moment, '%d/%m/%Y'), '%Y')";

    /**
     * Temps de parole/musique par média
     */
    public final static String TEMPSPARMOMENTPARCHAINEBYNAME = "select DISTINCT ME.nom        as nom_media,\n" +
            "                ME.id_media                                                as id_media,\n" +
            "                MO.id_moment                                                as id_moment,\n" +
            "                DATE_FORMAT(STR_TO_DATE(mo.date_moment, '%d/%m/%Y'), '%Y') as annee,\n" +
            "                AVG(TDP.temps_femme)                                       AS temps_femme,\n" +
            "                AVG(TDP.temps_homme)                                       AS temps_homme,\n" +
            "                AVG(TDP.temps_musique)                                     AS temps_musique\n" +
            "from media AS ME\n" +
            "         INNER JOIN temps_de_parole AS tdp on ME.id_media = tdp.id_media\n" +
            "         inner join moment AS MO ON MO.id_moment = tdp.id_moment\n" +
            "WHERE ME.nom LIKE ?\n" +
            "group by ME.nom, DATE_FORMAT(STR_TO_DATE(mo.date_moment, '%d/%m/%Y'), '%Y')";

    /**
     * Temps de parole/musique par moment et par année
     */
    public final static String TEMPSPARMOMENTPARCHAINEPARANNEE = "select DISTINCT ME.nom as nom_media,\n" +
            "                ME.id_media                                                 as id_media,\n" +
            "                MO.id_moment                                                as id_moment,\n" +
            "                DATE_FORMAT(STR_TO_DATE(mo.date_moment, '%d/%m/%Y'), '%Y')  as annee,\n" +
            "                AVG(TDP.temps_femme)                                        AS temps_femme,\n" +
            "                AVG(TDP.temps_homme)                                        AS temps_homme,\n" +
            "                AVG(TDP.temps_musique)                                      AS temps_musique\n" +
            "from media AS ME\n" +
            "         INNER JOIN temps_de_parole AS tdp on ME.id_media = tdp.id_media\n" +
            "         inner join moment AS MO ON MO.id_moment = tdp.id_moment\n" +
            "group by ME.nom, DATE_FORMAT(STR_TO_DATE(mo.date_moment, '%d/%m/%Y'), '%Y')";


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

    public final static String CREATETEMPSDEPAROLE = "INSERT INTO temps_de_parole(temps_femme,temps_homme,temps_musique,id_media,id_moment) VALUES(?,?,?,?,?)";

    /**
     * recupérer un id media a partir de son nom
     */

    public final static String GETMAXIDMEDIA = "SELECT MAX(id_media) AS max_id FROM media";

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
