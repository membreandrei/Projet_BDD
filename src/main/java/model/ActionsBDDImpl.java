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

    private TreeMap<Integer, ProgrammeurBean> listeProg = new TreeMap<Integer, ProgrammeurBean>();
    private ProgrammeurBean prog;
    private ActionsBDD action = new ActionsBDD();
    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;

    public ActionsBDDImpl() {
        this.conn = this.action.getConnection();
    }
    public ProgrammeurBean initProgrameur(ResultSet rs) {
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

    public TreeMap<Integer, ProgrammeurBean> doRequete(PreparedStatement statement) {
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

    public TreeMap<Integer, ProgrammeurBean> getProgrammeurs() {

        return doRequete(this.action.getPreparedStatement(this.conn, Constantes.ALLPROGS));

    }

    public TreeMap<Integer, ProgrammeurBean> getProgrammeurById(int id) {

        return doRequete(this.action.getPreparedStatementInt(this.conn, Constantes.PROGBYID, id));

    }

    public TreeMap<Integer, ProgrammeurBean> getProgrammeurByName(String name) {

        return doRequete(this.action.getPreparedStatementString(this.conn, Constantes.PROGBYNAME, name));
    }

    public TreeMap<Integer, ProgrammeurBean> getProgrammeurByFirstName(String firstName) {

        return doRequete(this.action.getPreparedStatementString(this.conn, Constantes.PROGBYFIRSTNAME, firstName));

    }

    public TreeMap<Integer, ProgrammeurBean> getProgrammeurByYear(int year) {
        return doRequete(this.action.getPreparedStatementInt(this.conn, Constantes.PROGBYYEAR, year));
    }

    //TODO À déplacer dans ProgrammeurBean
    public TreeMap<Integer, ProgrammeurBean> getListeProg() {
        return this.listeProg;
    }

 
    public void CreateProg(ProgrammeurBean prog){
        doRequete(this.action.getPreparedStatementInsert(this.conn, Constantes.CREATEPROG, prog));
    }
}