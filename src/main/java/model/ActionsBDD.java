package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActionsBDD {

    private String url = "jdbc:mysql://54.154.23.110/APTN61_BD";
    private String user = "adm";
    private String password = "adm";

    // La liste dynamique
    ArrayList<ProgrammeurBean> listeProg;

    // Le Java Bean
    ProgrammeurBean prog;
    Connection conn;
    Statement stmt;
    ResultSet rs;

    public Connection getConnection() {
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            Logger.getLogger(ActionsBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }

    public Statement getStatement(Connection conn) {
        try {
            stmt = conn.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(ActionsBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stmt;
    }

    public ResultSet getResultSet(Statement stmt) {
        try {
            rs = stmt.executeQuery("SELECT NOM,PRENOM from PROGRAMMEUR");
        } catch (SQLException ex) {
            Logger.getLogger(ActionsBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

/*
    public void afficherProgrammeurs(ArrayList<ProgrammeurBean> listeProg) {
        for (ProgrammeurBean prog : listeProg) {
            {
                System.out.println(prog.toString());
            }
        }
    }
*/

    public ArrayList<ProgrammeurBean> getProgrammeurs() {
        try {
            listeProg = new ArrayList<>();
            conn = getConnection();
            stmt = getStatement(conn);
            rs = getResultSet(stmt);

            while (rs.next()) {
                prog = new ProgrammeurBean();
                prog.setPrenom(rs.getString("PRENOM"));
                listeProg.add(prog);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ActionsBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listeProg;
    }

}

