package model;

import utils.Constantes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActionsBDDImpl {

    private final TreeMap<Integer, Media> listeMedia = new TreeMap<>();
    private final TreeMap<Integer, Moment> listeMoment = new TreeMap<>();
    private final TreeMap<Integer, TempsDeParole> listeTempsDeParole = new TreeMap<>();
    private Media media;
    private Moment moment;
    private TempsDeParole tdp;
    private final ActionsBDD action = new ActionsBDD();
    private final Connection conn;
    private PreparedStatement stmt;
    private ResultSet rsMedia;
    private ResultSet rsMoment;
    private ResultSet rsTDP;

    public ActionsBDDImpl() {
        this.conn = this.action.getConnection();
    }

    /**
     * Initialisation d'un média
     * @param rs
     * @return Media
     */
    public Media initMedia(ResultSet rs) {
        try {
            media = new Media();
            media.setId(rs.getInt("id_media"));
            media.setIdIna(rs.getString("identifiant_ina"));
            media.setType(rs.getString("type"));
            media.setNom(rs.getString("nom"));
            media.setEstPublic(rs.getString("est_public").equals("1"));
        } catch (SQLException ex) {
            Logger.getLogger(ActionsBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return media;
    }

    /**
     * Initialisation d'un Moment
     * @param rs
     * @return Moment
     */
    public Moment initMoment(ResultSet rs) {
        try {
            moment = new Moment();
            moment.setId(rs.getInt("id_moment"));
            moment.setDateMoment(rs.getString("annee"));
            moment.setEstFerie(rs.getString("est_ferie").equals("1"));
            moment.setVacances(rs.getString("vacances"));
            moment.setHeure(Integer.parseInt(rs.getString("heure")));
            moment.setJour(rs.getString("jour"));
        } catch (SQLException ex) {
            Logger.getLogger(ActionsBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return moment;
    }

    /**
     * Initialisation des pourcentages de temps de parole
     * @param rs
     * @return Media
     */
    public TempsDeParole initTempsDeParole(ResultSet rs) {
        try {
            tdp = new TempsDeParole();
            tdp.setTempsFemmes(Float.parseFloat(rs.getString("temps_femme")));
            tdp.setTempsHommes(Float.parseFloat(rs.getString("temps_homme")));
            tdp.setTempsMusique(Float.parseFloat(rs.getString("temps_musique")));
            tdp.setMedia(getMediaById(rs.getInt("id_media")).get(rs.getInt("id_media")));
            tdp.setMoment(getMomentById(rs.getInt("id_moment")).get(rs.getInt("id_moment")));
        } catch (SQLException ex) {
            Logger.getLogger(ActionsBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tdp;
    }

    /**
     * Méthode d'exécution d'une requête préparée pour un média
     * @param statement
     * @return
     */
    public TreeMap<Integer, Media> doRequeteMedia(PreparedStatement statement) {
        this.listeMedia.clear();
        try {
            this.stmt = statement;
            this.rsMedia = this.action.getResultSet(this.stmt);
            while (this.rsMedia.next()) {
                this.listeMedia.put(this.rsMedia.getInt("id_media"), initMedia(this.rsMedia));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ActionsBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.listeMedia;
    }

    /**
     * Méthode d'exécution d'une requête préparée pour un Moment
     * @param statement
     * @return
     */
    public TreeMap<Integer, Moment> doRequeteMoment(PreparedStatement statement) {
        this.listeMedia.clear();
        try {
            this.stmt = statement;
            this.rsMoment = this.action.getResultSet(this.stmt);
            while (this.rsMoment.next()) {
                this.listeMoment.put(this.rsMoment.getInt("id_moment"), initMoment(this.rsMoment));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ActionsBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.listeMoment;
    }

    /**
     * Méthode d'exécution d'une requête préparée pour un temps de parole
     * @param statement
     * @return
     */
    public TreeMap<Integer, TempsDeParole> doRequeteTempsDeParole(PreparedStatement statement) {
        this.listeTempsDeParole.clear();
        try {
            this.stmt = statement;
            this.rsTDP = this.action.getResultSet(this.stmt);
            int index = 1;
            while (this.rsTDP.next()) {
                this.listeTempsDeParole.put(index, initTempsDeParole(this.rsTDP));
                index++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ActionsBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.listeTempsDeParole;
    }

    /**
     * Méthode d'exécution d'une requête préparée pour récup les max_id
     * @param statement
     * @return
     */
    public int doRequeteInt(PreparedStatement statement) {
        try {
            this.stmt = statement;
            this.rsMedia = this.action.getResultSet(this.stmt);

            while (this.rsMedia.next()) {
                return (this.rsMedia.getInt("max_id"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ActionsBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 1;
    }

    /**
     * Méthode d'exécution d'une requête préparée avec modification dans la base de données
     *
     * @param statement
     * @return
     */
    public int doRequeteUpdate(PreparedStatement statement) {
        this.listeMedia.clear();
        this.stmt = statement;

        return this.action.getResultSetModify(this.stmt);
    }

    /**
     * Exécution de la requête retournant tous les médias
     * @return
     */
    public TreeMap<Integer, Media> getMedia() {
        return doRequeteMedia(this.action.getPreparedStatement(this.conn, Constantes.ALLMEDIA));
    }

    /**
     * Exécution de la requête retournant un média grace à son id
     *
     * @param id
     * @return
     */
    public TreeMap<Integer, Media> getMediaById(int id) {
        return doRequeteMedia(this.action.getPreparedStatementInt(this.conn, Constantes.MEDIABYID, id));
    }

    /**
     * Exécution de la requête retournant un moment grace à son id
     *
     * @param id
     * @return
     */
    public TreeMap<Integer, Moment> getMomentById(int id) {
        return doRequeteMoment(this.action.getPreparedStatementInt(this.conn, Constantes.MOMENTBYID, id));
    }

    /**
     * Exécution de la requête retournant un ou des médias par leur nom
     *
     * @param name
     * @return
     */
    public TreeMap<Integer, Media> getMediaByName(String name) {
        return doRequeteMedia(this.action.getPreparedStatementString(this.conn, Constantes.MEDIABYNAME, name));
    }

    /**
     * Exécution de la requête retournant un ou des médias avec pourcentage de temps de parole par moment
     * @return TreeMap
     */
    public TreeMap<Integer, TempsDeParole> getPourcentageTDP() {
        return doRequeteTempsDeParole(this.action.getPreparedStatement(this.conn, Constantes.TEMPSPARMOMENT));
    }

    /**
     * Exécution de la requête retournant un ou des médias avec moyenne de temps de parole par temps Homme 2 fois supérieur à temps femme
     * @return TreeMap
     */
    public TreeMap<Integer, TempsDeParole> getTDPHomme2FoisSupFemme() {
        return doRequeteTempsDeParole(this.action.getPreparedStatement(this.conn, Constantes.TEMPSALLWHEREHOMME2FOISSUPFEMME));
    }

    /**
     * Exécution de la requête retournant un ou des médias avec pourcentage de temps de parole par année
     * @param year
     * @return TreeMap
     */
    public TreeMap<Integer, TempsDeParole> getPourcentageTDPByYear(Integer year) {
        return doRequeteTempsDeParole(this.action.getPreparedStatementInt(this.conn, Constantes.TEMPSPARMOMENTPARANNEE, year));
    }

    /**
     * Exécution de la requête retournant un ou des médias avec moyenne de temps de parole par nom de média
     * @param name
     * @return TreeMap
     */
    public TreeMap<Integer, TempsDeParole> getMoyenneTDPByName(String name) {
        return doRequeteTempsDeParole(this.action.getPreparedStatementString(this.conn, Constantes.TEMPSPARMOMENTPARCHAINEBYNAME, name));
    }

    /**
     * Exécution de la requête retournant la moyenne de temps de parole par média et par année
     * @return
     */
    public TreeMap<Integer, TempsDeParole> getMoyenneTDP() {
        return doRequeteTempsDeParole(this.action.getPreparedStatement(this.conn, Constantes.TEMPSPARMOMENTPARCHAINE));
    }

    /**
     * Exécution de la requête retournant le dernier id de média
     * @return
     */
    public int getMaxIdMedia() {
        return doRequeteInt(this.action.getPreparedStatement(this.conn, Constantes.GETMAXIDMEDIA));
    }

    /**
     * Exécution de la requête retournant le dernier id de moment
     * @return
     */
    public int getMaxIdMoment() {
        return doRequeteInt(this.action.getPreparedStatement(this.conn, Constantes.GETMAXIDMOMENT));
    }


    /**
     * Exécution de la requête retournant les chaînes de TV avec pourcentage temps de parole homme supérieur à X
     * @return
     */
    public TreeMap<Integer, TempsDeParole> getTVPourcentageHommeSupX(Integer percent) {
        return doRequeteTempsDeParole(this.action.getPreparedStatementInt(this.conn, Constantes.CHAINETVPOURCENTAGEHOMMESUPX, percent));
    }


    /**
     * récupère la liste des médias
     * @return
     */
    public TreeMap<Integer, Media> getListeMedia() {
        return this.listeMedia;
    }

    /**
     * Exécution de la requête permettant de supprimer un média et temps de parole par id de média
     *
     * @param id
     */
    public void deleteMedia(int id) {
        doRequeteUpdate(this.action.getPreparedStatementInt(this.conn, Constantes.DELTEMPSDEPAROLE, id));
        doRequeteUpdate(this.action.getPreparedStatementInt(this.conn, Constantes.DELMEDIA, id));
    }

    /**
     * Exécution de la requête permettant de créer un nouveau média
     *
     * @param media
     * @return
     */
    public int createMedia(Media media) {
        return doRequeteUpdate(this.action.getPreparedStatementInsertMedia(this.conn, Constantes.CREATEMEDIA, media));
    }

    /**
     * Exécution de la requête permettant de créer un nouveau moment
     *
     * @param media
     * @return
     */
    public int createMoment(Moment moment) {
        return doRequeteUpdate(this.action.getPreparedStatementInsertMoment(this.conn, Constantes.CREATEMOMENT, moment));
    }

    /**
     * Exécution de la requête permettant de créer un nouveau temps de parole
     *
     * @param media
     * @return
     */
    public int createTemspsDeParole(TempsDeParole tempsDeParole) {
        return doRequeteUpdate(this.action.getPreparedStatementInsertTempsDeParole(this.conn, Constantes.CREATETEMPSDEPAROLE, tempsDeParole));
    }
}