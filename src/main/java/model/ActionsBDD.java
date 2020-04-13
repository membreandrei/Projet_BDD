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
     * Methode pour la construction d'une requête d'insert à partir de la structure d'un developpeur
     * @param conn
     * @param requete
     * @param prog
     * @return
     */
    public PreparedStatement getPreparedStatementInsert(Connection conn, String requete, ProgrammeurBean prog) {
        try {
            this.stmt = conn.prepareStatement(requete);
            this.stmt.setString(1, prog.getNom());
            this.stmt.setString(2, prog.getPrenom());
            this.stmt.setString(3, prog.getAdresse());
            this.stmt.setString(4, prog.getPseudo());
            this.stmt.setString(5, prog.getResponsable());
            this.stmt.setString(6, prog.getHobby());
            this.stmt.setInt(7, prog.getAnNaissance());
            this.stmt.setFloat(8, prog.getSalaire());
            this.stmt.setFloat(9, prog.getPrime());
        } catch (SQLException ex) {
            Logger.getLogger(ActionsBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.stmt;
    }

    /**
     * Méthode pour préparer la requête de modification du salaire
     * @param conn
     * @param requete
     * @param prog
     * @return
     */
    public PreparedStatement getPreparedStatementModifyProg(Connection conn, String requete, ProgrammeurBean prog) {
        try {
            this.stmt = conn.prepareStatement(requete);
            this.stmt.setString(1, prog.getNom());
            this.stmt.setString(2, prog.getPrenom());
            this.stmt.setString(3, prog.getAdresse());
            this.stmt.setString(4, prog.getPseudo());
            this.stmt.setString(5, prog.getResponsable());
            this.stmt.setString(6, prog.getHobby());
            this.stmt.setInt(7, prog.getAnNaissance());
            this.stmt.setFloat(8, prog.getSalaire());
            this.stmt.setFloat(9, prog.getPrime());
            this.stmt.setInt(10, prog.getId());
        } catch (SQLException ex) {
            Logger.getLogger(ActionsBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.stmt;
    }

    /**
     * Méthode pour exécuter et récupérer le résultat d'une requête renvoyant un ResultSet
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
     * @param stmt
     * @return
     */
    public int getResultSetModify(PreparedStatement stmt) {
        Integer i = null;
        try {
            i = stmt.executeUpdate();
        } catch (MysqlDataTruncation ex){
            return 0;
        } catch (SQLException ex) {
            Logger.getLogger(ActionsBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }
}

