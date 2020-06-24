package model;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import utils.Constantes;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActionsBDD {

    // Le Java Bean
    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;

    public ActionsBDD() {
    }

    /**
     * Connexion à la base de données
     *
     * @return
     */
    public Connection getConnection() {
        try {
            this.conn = DriverManager.getConnection(Constantes.URL, Constantes.USER, Constantes.PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(ActionsBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.conn;
    }

    /**
     * Methode pour construire dynamiquement une requete préparée standard.
     *
     * @param conn
     * @param requete
     * @return
     */
    public PreparedStatement getPreparedStatement(Connection conn, String requete) {
        try {
            this.stmt = conn.prepareStatement(requete);
        } catch (SQLException ex) {
            Logger.getLogger(ActionsBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.stmt;
    }

    /**
     * Methode pour construire dynamiquement une requete préparée prenant un int en argument
     *
     * @param conn
     * @param requete
     * @param id
     * @return
     */
    public PreparedStatement getPreparedStatementInt(Connection conn, String requete, int id) {
        try {
            this.stmt = conn.prepareStatement(requete);
            this.stmt.setInt(1, id);
        } catch (SQLException ex) {
            Logger.getLogger(ActionsBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.stmt;
    }

    /**
     * Methode pour construire dynamiquement une requete préparée prenant juste une string en arg
     */
    public PreparedStatement getPreparedStatementString(Connection conn, String requete, String name) {
        try {
            this.stmt = conn.prepareStatement(requete);
            this.stmt.setString(1, "%" + name + "%");
        } catch (SQLException ex) {
            Logger.getLogger(ActionsBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.stmt;
    }

    /**
     * Methode pour la construction d'une requête d'insert à partir de la structure d'un developpeur pour un média
     *
     * @param conn Connexion à la base de données
     * @param requete Requête à effectuer
     * @param media Média recherché
     * @return
     */
    public PreparedStatement getPreparedStatementInsertMedia(Connection conn, String requete, Media media) {
        try {
            this.stmt = conn.prepareStatement(requete);
            this.stmt.setString(1, media.getIdIna());
            this.stmt.setString(2, media.getType());
            this.stmt.setString(3, media.getNom());
            this.stmt.setBoolean(4, media.getEstPublic());

        } catch (SQLException ex) {
            Logger.getLogger(ActionsBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.stmt;
    }

    /**
     * Methode pour la construction d'une requête d'insert à partir de la structure d'un developpeur pour un moment
     *
     * @param conn Connexion à la base de données
     * @param requete Requête à effectuer
     * @param moment Média recherché
     * @return
     */
    public PreparedStatement getPreparedStatementInsertMoment(Connection conn, String requete, Moment moment) {
        try {
            this.stmt = conn.prepareStatement(requete);
            this.stmt.setString(1, moment.getDateMoment());
            this.stmt.setBoolean(2, moment.getEstFerie());
            this.stmt.setString(3, moment.getVacances());
            this.stmt.setInt(4, moment.getHeure());
            this.stmt.setString(5, moment.getJour());

        } catch (SQLException ex) {
            Logger.getLogger(ActionsBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.stmt;
    }
    /**
     * Methode pour la construction d'une requête d'insert à partir de la structure d'un developpeur pour un temps de parole
     *
     * @param conn Connexion à la base de données
     * @param requete Requête à effectuer
     * @param tempsDeParole Média recherché
     * @return
     */
    public PreparedStatement getPreparedStatementInsertTempsDeParole(Connection conn, String requete, TempsDeParole tempsDeParole) {
        try {
            this.stmt = conn.prepareStatement(requete);
            this.stmt.setFloat(1, tempsDeParole.getTempsFemmes());
            this.stmt.setFloat(2, tempsDeParole.getTempsHommes());
            this.stmt.setFloat(3, tempsDeParole.getTempsMusique());
            this.stmt.setInt(4, tempsDeParole.getMedia().getId());
            this.stmt.setInt(5, tempsDeParole.getMoment().getId());

        } catch (SQLException ex) {
            Logger.getLogger(ActionsBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.stmt;
    }

    /**
     * Méthode pour exécuter et récupérer le résultat d'une requête renvoyant un ResultSet
     *
     * @param stmt
     * @return
     */
    public ResultSet getResultSet(PreparedStatement stmt) {
        try {
            this.rs = stmt.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(ActionsBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.rs;
    }

    /**
     * Méthode pour exécuter une requête en récupérant le nombre de ligne(s) affectée(s) par la requête
     *
     * @param stmt
     * @return
     */
    public int getResultSetModify(PreparedStatement stmt) {
        Integer i = null;
        try {
            i = stmt.executeUpdate();
        } catch (MysqlDataTruncation ex) {
            return 0;
        } catch (SQLException ex) {
            Logger.getLogger(ActionsBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }
}

