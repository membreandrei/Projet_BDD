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

    //Initialisation d'un programmeur
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

    //Méthode d'exécution d'une requête préparée
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

    //Méthode d'exécution d'une requête préparée avec modification dans la base de donnée
    public int doRequeteUpdate(PreparedStatement statement) {
        this.listeProg.clear();
        this.stmt = statement;
        int changesCount = this.action.getResultSetModify(this.stmt);

        return changesCount;
    }

    //Exécution de la requête retournant tous les programmeurs
    public TreeMap<Integer, ProgrammeurBean> getProgrammeurs() {

        return doRequete(this.action.getPreparedStatement(this.conn, Constantes.ALLPROGS));

    }

    //Exécution de la requête retournant un programmeur grace à son id
    public TreeMap<Integer, ProgrammeurBean> getProgrammeurById(int id) {
        return doRequete(this.action.getPreparedStatementInt(this.conn, Constantes.PROGBYID, id));
    }

    //Exécution de la requête retournant un ou des programmeurs par leur nom
    public TreeMap<Integer, ProgrammeurBean> getProgrammeurByName(String name) {
        return doRequete(this.action.getPreparedStatementString(this.conn, Constantes.PROGBYNAME, name));
    }

    //Exécution de la requête retournant un ou des programmeurs par leur prénom
    public TreeMap<Integer, ProgrammeurBean> getProgrammeurByFirstName(String firstName) {
        return doRequete(this.action.getPreparedStatementString(this.conn, Constantes.PROGBYFIRSTNAME, firstName));
    }

    //Exécution de la requête retournant un ou des programmeurs par leur année de naissance
    public TreeMap<Integer, ProgrammeurBean> getProgrammeurByYear(Integer year) {
        return doRequete(this.action.getPreparedStatementString(this.conn, Constantes.PROGBYYEAR, Integer.toString(year)));
    }

    //TODO À déplacer dans ProgrammeurBean
    public TreeMap<Integer, ProgrammeurBean> getListeProg() {
        return this.listeProg;
    }

    //Exécution de la requête permettant de supprimer un user par id
    public void deleteProg(int id) {
        doRequeteUpdate(this.action.getPreparedStatementInt(this.conn, Constantes.DELPROG, id));
    }

    //Exécution de la requête permettant de changer le salaire via l'id
    public void editSalary(ProgrammeurBean prog) {
        doRequeteUpdate(this.action.getPreparedStatementModifySalary(this.conn, Constantes.CHANGESALARY, prog));
    }

    //Exécution de la requête permettant de créer un nouveau programmeur
    public void createProg(ProgrammeurBean prog) {
        doRequeteUpdate(this.action.getPreparedStatementInsert(this.conn, Constantes.CREATEPROG, prog));
    }
}