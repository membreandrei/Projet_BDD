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

    private TreeMap<Integer, Media> listeProg = new TreeMap<>();
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
    public Media initProgrameur(ResultSet rs) {
        try {
            media = new Media();
            media.setId(rs.getInt("ID"));
           /* prog.setPrenom(rs.getString("PRENOM"));
            prog.setAnNaissance(Integer.parseInt(rs.getString("ANNAISSANCE")));
            prog.setNom(rs.getString("NOM"));
            prog.setPrime(Float.parseFloat(rs.getString("PRIME")));
            prog.setPseudo(rs.getString("PSEUDO"));
            prog.setSalaire(Float.parseFloat(rs.getString("SALAIRE")));
            prog.setAdresse(rs.getString("ADRESSE"));
            prog.setResponsable(rs.getString("RESPONSABLE"));
            prog.setHobby(rs.getString("HOBBY"));*/
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
        this.listeProg.clear();
        try {
            this.stmt = statement;
            this.rs = this.action.getResultSet(this.stmt);

            while (this.rs.next()) {
                this.listeProg.put(this.rs.getInt("ID"), initProgrameur(this.rs));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ActionsBDD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return this.listeProg;
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
        this.listeProg.clear();
        this.stmt = statement;

        return this.action.getResultSetModify(this.stmt);
    }

    /**
     * Ex�cution de la requ�te retournant tous les programmeurs
     *
     * @return
     */
    public TreeMap<Integer, Media> getProgrammeurs() {
        return doRequete(this.action.getPreparedStatement(this.conn, Constantes.ALLPROGS));
    }

    /**
     * Ex�cution de la requ�te retournant un programmeur grace � son id
     *
     * @param id
     * @return
     */
    public TreeMap<Integer, Media> getProgrammeurById(int id) {
        return doRequete(this.action.getPreparedStatementInt(this.conn, Constantes.PROGBYID, id));
    }

    /**
     * Ex�cution de la requ�te retournant un ou des programmeurs par leur nom
     *
     * @param name
     * @return
     */
    public TreeMap<Integer, Media> getProgrammeurByName(String name) {
        return doRequete(this.action.getPreparedStatementString(this.conn, Constantes.PROGBYNAME, name));
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

    public int getIdMediaByName() {
        return doRequeteInt(this.action.getPreparedStatement(this.conn, Constantes.GETIDMEDIABYNAME));
    }

    public int getIdMaxMoment() {
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

    public TreeMap<Integer, Media> getListeProg() {
        return this.listeProg;
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