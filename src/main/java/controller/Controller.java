package controller;

import model.ActionsBDDImpl;
import model.ProgrammeurBean;
import org.apache.maven.shared.utils.StringUtils;
import org.sonatype.inject.Nullable;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.TreeMap;

public class Controller implements ActionListener, MouseListener {
    private MenuView mv;
    private ResultatView rv;
    private HashMap<String, JButton> identificator;
    private ActionsBDDImpl model;
    private ProgrammeurView pv;

    public Controller(BasePanel bp) {
        this.mv = bp.getMv();
        this.rv = bp.getRv();
        this.fillHashMap();
        this.model = new ActionsBDDImpl();
    }

    private void fillHashMap() {
        this.identificator = new HashMap<String, JButton>();
        for (JButton button : this.mv.getAllButtons()) {
            this.identificator.put(button.getText(), button);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        TreeMap<Integer, ProgrammeurBean> data;
        if (e.getSource().equals(this.identificator.get("Afficher tous les programmeurs"))) {
            data = this.model.getProgrammeurs();
            this.rv.modifyPanel(0, data, null);
            String text = "";
            for (Integer key : data.keySet())
                text += data.get(key).toString() + "------------------------------------------------------------\n";
            text += " END ";
            this.rv.editText(text);
        }
        if (e.getSource().equals(this.identificator.get("Afficher un programmeur"))) {
            data = this.model.getProgrammeurs();
            this.rv.modifyPanel(1, data, null);
        }
        if (e.getSource().equals(this.identificator.get("Supprimer un programmeur"))) {
            //this.rv.modifyPanel(2, data);
        }
        if (e.getSource().equals(this.identificator.get("Ajouter un programmeur"))) {
            this.openModal(null, "add");
        }

        if (e.getActionCommand().equals("ajout")) {
            if (!validate()) {
                JOptionPane.showMessageDialog(null, "Veuillez réessayer avec des nombres dans salaire, prime, année de naissance");
            } else {

                this.model.createProg(createProg());
                FenetreMere fm = (FenetreMere) SwingUtilities.getWindowAncestor((Component) e.getSource());
                fm.dispose();
            }
        }

        if (e.getSource().equals(this.identificator.get("Modifier le salaire"))) {
            //this.rv.modifyPanel(4, data);
        }
        if (e.getSource().equals(this.identificator.get("Quitter le programme"))) {
            System.exit(0);
        }
        if (e.getSource().equals(this.rv.getSearchButton())) {
            this.rv.recherche();
        }
    }

    private ProgrammeurBean createProg() {
        ProgrammeurBean prog = new ProgrammeurBean();

        prog.setNom(this.pv.getAllTextFields().get("nom").getText());
        prog.setPrenom(this.pv.getAllTextFields().get("prénom").getText());
        prog.setPseudo(this.pv.getAllTextFields().get("pseudo").getText());
        prog.setAdresse(this.pv.getAllTextFields().get("adresse").getText());
        prog.setAnNaissance(Integer.parseInt(this.pv.getAllTextFields().get("naissance").getText()));
        prog.setResponsable(this.pv.getAllTextFields().get("responsable").getText());
        prog.setHobby(this.pv.getAllTextFields().get("hobby").getText());
        prog.setSalaire(Float.parseFloat(this.pv.getAllTextFields().get("salaire").getText()));
        prog.setPrime(Float.parseFloat(this.pv.getAllTextFields().get("prime").getText()));

        return prog;
    }

    private boolean validate() {

        if (!StringUtils.isNumeric(this.pv.getAllTextFields().get("naissance").getText())) {
            return false;
        }

        if (!this.pv.getAllTextFields().get("prime").getText().matches("[-+]?[0-9]*\\,|.?[0-9]+")) {
            return false;
        }

        if (!this.pv.getAllTextFields().get("salaire").getText().matches("[-+]?[0-9]*\\,|.?[0-9]+")) {
            return false;
        }

        return true;
    }

    public void setPv(ProgrammeurView pv) {
        this.pv = pv;
    }

    private void openModal(@Nullable ProgrammeurBean pb, String type) {
        String title = null;
        ProgrammeurView pv = null;
        if (pb == null) {
            title = "Ajout";
            pv = new ProgrammeurView();
        } else {
            title = pb.getNom().toUpperCase() + " " + pb.getPrenom();
            pv = new ProgrammeurView(pb);
        }
        new FenetreMere(title, pv, type);
    }

    public TreeMap<Integer, ProgrammeurBean> getProgrammeurs() {
        return this.model.getProgrammeurs();
    }

    public TreeMap<Integer, ProgrammeurBean> getProgrammeurById(int id) {
        return this.model.getProgrammeurById(id);
    }

    public TreeMap<Integer, ProgrammeurBean> getProgrammeurByName(String name) {
        return this.model.getProgrammeurByName(name);
    }

    public TreeMap<Integer, ProgrammeurBean> getProgrammeurByFirstName(String firstName) {
        return this.model.getProgrammeurByFirstName(firstName);
    }

    public TreeMap<Integer, ProgrammeurBean> getProgrammeurByYear(Integer year) {
        return this.model.getProgrammeurByYear(year);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JTable laTable = (JTable) e.getSource();
        Object targetId = laTable.getValueAt(laTable.getSelectedRow(), laTable.getColumnModel().getColumnIndex("ID"));
        ProgrammeurBean prog = this.model.getListeProg().get(targetId);
        openModal(prog, "display");
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
