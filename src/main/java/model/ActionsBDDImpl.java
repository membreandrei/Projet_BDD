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

    private TreeMap<Integer, Media> listeMedia = new TreeMap<>();
    private Media media;
    private ActionsBDD action = new ActionsBDD();
    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;

    public ActionsBDDImpl() {
        this.conn = this.action.getConnection();
    }

    /**
     * Initialisation d'un programmeur
     *
     * @param rs
     * @return
     */
    public Media initMedia(ResultSet rs) {
        try {
            media = new Media();
            media.setId(rs.getInt("id_media"));
            media.setIdIna(rs.getString("identifiant_ina"));
            media.setType(rs.getString("type"));
            media.setNom(rs.getString("nom"));
            media.setEstPublic(Boolean.parseBoolean(rs.getString("est_public")));
        } catch (SQLException ex) {
            Logger.getLogger(ActionsBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return media;
    }

    /**
     * M�thode d'ex�cution d'une requ�te pr�par�e
     *
     * @param statement
     * @return
     */
    public TreeMap<Integer, Media> doRequete(PreparedStatement statement) {
        this.listeMedia.clear();
        try {
            this.stmt = statement;
            this.rs = this.action.getResultSet(this.stmt);

            while (this.rs.next()) {
                this.listeMedia.put(this.rs.getInt("id_media"), initMedia(this.rs));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ActionsBDD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return this.listeMedia;
    }

    /*
    public int doRequeteIntMedia(PreparedStatement statement) {
        try {
            this.stmt = statement;
            this.rs = this.action.getResultSet(this.stmt);

            while (this.rs.next()) {
               return this.rs.getInt("id_media");
            }

        } catch (SQLException ex) {
            Logger.getLogger(ActionsBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 1;
    }
*/

    public int doRequeteInt(PreparedStatement statement) {
        try {
            this.stmt = statement;
            this.rs = this.action.getResultSet(this.stmt);

            while (this.rs.next()) {
                return (this.rs.getInt("max_id"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ActionsBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 1;
    }

    /**
     * M�thode d'ex�cution d'une requ�te pr�par�e avec modification dans la base de donn�es
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
     * Ex�cution de la requ�te retournant tous les programmeurs
     *
     * @return
     */
    public TreeMap<Integer, Media> getMedia() {
        return doRequete(this.action.getPreparedStatement(this.conn, Constantes.ALLMEDIA));
    }

    /**
     * Ex�cution de la requ�te retournant un programmeur grace � son id
     *
     * @param id
     * @return
     */
    public TreeMap<Integer, Media> getMediaById(int id) {
        return doRequete(this.action.getPreparedStatementInt(this.conn, Constantes.MEDIABYID, id));
    }

    /**
     * Ex�cution de la requ�te retournant un ou des programmeurs par leur nom
     *
     * @param name
     * @return
     */
    public TreeMap<Integer, Media> getMediaByName(String name) {
        System.out.println(name);
        return doRequete(this.action.getPreparedStatementString(this.conn, Constantes.MEDIABYNAME, name));
    }

    /**
     * Ex�cution de la requ�te retournant un ou des programmeurs par leur pr�nom
     *
     * @param firstName
     * @return
     */
    public TreeMap<Integer, Media> getProgrammeurByFirstName(String firstName) {
        return doRequete(this.action.getPreparedStatementString(this.conn, Constantes.PROGBYFIRSTNAME, firstName));
    }

    public int getMaxIdMedia() {
        return doRequeteInt(this.action.getPreparedStatement(this.conn, Constantes.GETMAXIDMEDIA));
    }

    public int getMaxIdMoment() {
        return doRequeteInt(this.action.getPreparedStatement(this.conn, Constantes.GETMAXIDMOMENT));
    }

    /**
     * Ex�cution de la requ�te retournant un ou des programmeurs par leur ann�e de naissance
     *
     * @param year
     * @return
     */
    public TreeMap<Integer, Media> getProgrammeurByYear(Integer year) {
        return doRequete(this.action.getPreparedStatementString(this.conn, Constantes.PROGBYYEAR, Integer.toString(year)));
    }

    public TreeMap<Integer, Media> getListeMedia() {
        return this.listeMedia;
    }

    /**
     * Ex�cution de la requ�te permettant de supprimer un user par id
     *
     * @param id
     */
    public void deleteProg(int id) {
        doRequeteUpdate(this.action.getPreparedStatementInt(this.conn, Constantes.DELPROG, id));
    }

    /**
     * Ex�cution de la requ�te permettant de changer le salaire via l'id
     *
     * @param prog
     */
    public void editProg(Media prog) {
        doRequeteUpdate(this.action.getPreparedStatementModifyProg(this.conn, Constantes.EDITPROG, prog));
    }

    /**
     * Ex�cution de la requ�te permettant de cr�er un nouveau programmeur
     *
     * @param media
     * @return
     */
    public int createMedia(Media media) {
        return doRequeteUpdate(this.action.getPreparedStatementInsertMedia(this.conn, Constantes.CREATEMEDIA, media));
    }

    public int createMoment(Moment moment) {
        return doRequeteUpdate(this.action.getPreparedStatementInsertMoment(this.conn, Constantes.CREATEMOMENT, moment));
    }

    public int createTemspsDeParole(TempsDeParole tempsDeParole) {
        return doRequeteUpdate(this.action.getPreparedStatementInsertTempsDeParole(this.conn, Constantes.CREATETEMPSDEPAROLE, tempsDeParole));
    }
}