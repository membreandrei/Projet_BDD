package model;

import utils.Constantes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActionsBDDImpl {

    private ArrayList<ProgrammeurBean> listeProg = new ArrayList<ProgrammeurBean>();
    private ProgrammeurBean prog;
    private ActionsBDD action = new ActionsBDD();
    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;

    public ActionsBDDImpl(){
    }

    public ProgrammeurBean initProgrameur(ResultSet rs){
        try {
            prog = new ProgrammeurBean();
            prog.setId(rs.getInt("ID"));
            prog.setPrenom(rs.getString("PRENOM"));
            prog.setAnNaissance(Integer.parseInt(rs.getString("ANNAISSANCE")));
            prog.setNom(rs.getString("NOM"));
            prog.setPrime(Float.parseFloat(rs.getString("PRIME")));
            prog.setPseudo(rs.getString("PSEUDO"));
            prog.setSalaire(Float.parseFloat(rs.getString("SALAIRE")));
            prog.setAdresse(rs.getString("ADRESSE"));
            prog.setResponsable(rs.getString("RESPONSABLE"));
            prog.setHobby(rs.getString("HOBBY"));
        } catch (SQLException ex) {
            Logger.getLogger(ActionsBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return prog;
    }

    public ArrayList<ProgrammeurBean> getProgrammeurs() {
        this.listeProg.clear();
        try {
            this.conn = this.action.getConnection();
            this.stmt = this.action.getPreparedStatement(this.conn, Constantes.ALLPROGS);
            this.rs = this.action.getResultSet(this.stmt);

            while (this.rs.next()) {
                this.listeProg.add(initProgrameur(this.rs));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ActionsBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.listeProg;
    }
}
