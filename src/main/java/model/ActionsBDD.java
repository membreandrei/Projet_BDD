package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Constantes;

public class ActionsBDD {

    private String url = "jdbc:mysql://54.154.23.110/APTN61_BD";
    private String user = "adm";
    private String password = "adm";

    // Le Java Bean
    private Connection conn;
    private PreparedStatement  stmt;
    private ResultSet rs;


    public ActionsBDD() {
    }

    public Connection getConnection() {
        try {
            this.conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            Logger.getLogger(ActionsBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.conn;
    }

    public PreparedStatement getPreparedStatement(Connection conn, String requete) {
        try {
            this.stmt = conn.prepareStatement(requete);
        } catch (SQLException ex) {
            Logger.getLogger(ActionsBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.stmt;
    }

    public PreparedStatement getPreparedStatementInt(Connection conn, String requete, int id) {
        try {
            this.stmt = conn.prepareStatement(requete);
            this.stmt.setInt(1,id);
        } catch (SQLException ex) {
            Logger.getLogger(ActionsBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.stmt;
    }

    public PreparedStatement getPreparedStatementString(Connection conn, String requete, String name) {
        try {
            this.stmt = conn.prepareStatement(requete);
            this.stmt.setString(1,"%" + name + "%");
        } catch (SQLException ex) {
            Logger.getLogger(ActionsBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.stmt;
    }
    
    public PreparedStatement getPreparedStatementInsert(Connection conn, String requete, ProgrammeurBean prog) {
        try {
            this.stmt = conn.prepareStatement(requete);
            this.stmt.setString(1,prog.getNom());
            this.stmt.setString(2,prog.getPrenom());
            this.stmt.setString(3,prog.getAdresse());
            this.stmt.setString(4,prog.getPseudo());
            this.stmt.setString(5, prog.getResponsable());
            this.stmt.setString(6, prog.getHobby());
            this.stmt.setInt(7, prog.getAnNaissance());
            this.stmt.setFloat(8, prog.getSalaire());
            this.stmt.setFloat(8, prog.getPrime());
        } catch (SQLException ex) {
            Logger.getLogger(ActionsBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.stmt;
    }

    
    public ResultSet getResultSet(PreparedStatement stmt) {
        try {
            this.rs = stmt.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(ActionsBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.rs;
    }
    
    public int getResultSetModify(PreparedStatement stmt) {
        Integer i = null;
        try {
           i = stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ActionsBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }
}

