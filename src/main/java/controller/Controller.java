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
    private final MenuView mv;
    private ResultatView rv;
    private HashMap<String, JButton> identificator;
    private ActionsBDDImpl model;
    private ProgrammeurView pv;
    private Integer typeRv = 1;

    public Controller(BasePanel bp) {
        this.mv = bp.getMv();
        this.rv = bp.getRv();
        this.fillHashMap();
        this.model = new ActionsBDDImpl();
    }

    private void fillHashMap() {
        this.identificator = new HashMap<>();
        for (JButton button : this.mv.getAllButtons()) {
            this.identificator.put(button.getText(), button);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        TreeMap<Integer, ProgrammeurBean> data = new TreeMap<>();
        if (e.getSource().equals(this.rv.getSearchButton())) {
            this.rv.recherche();
        } else {
            if (e.getSource().equals(this.identificator.get("Afficher tous les programmeurs"))) {
                data = this.model.getProgrammeurs();
                this.typeRv = 1;
            }
            if (e.getSource().equals(this.identificator.get("Afficher un programmeur"))) {
                data = this.model.getProgrammeurs();
                this.typeRv = 1;
            }
            if (e.getSource().equals(this.identificator.get("Supprimer un programmeur"))) {
                data = this.model.getProgrammeurs();
                this.typeRv = 3;
            }
            if (e.getSource().equals(this.identificator.get("Ajouter un programmeur"))) {
                data = this.model.getProgrammeurs();
                this.openModal(null, "add", true);
            }

            if (e.getActionCommand().equals("ajout")) {
                if (validate()) {
                    JOptionPane.showMessageDialog(null, "Veuillez réessayer avec des nombres dans salaire, prime, année de naissance");
                } else {
                    this.model.createProg(createProg());
                    FenetreMere fm = (FenetreMere) SwingUtilities.getWindowAncestor((Component) e.getSource());
                    fm.dispose();
                    data = this.model.getProgrammeurs();
                }
            }
            if (e.getActionCommand().equals("enregistrer")) {
                if (validate()) {
                    JOptionPane.showMessageDialog(null, "Veuillez réessayer avec des nombres dans salaire, prime, année de naissance");
                } else {
                    this.model.editProg(createProg());
                    FenetreMere fm = (FenetreMere) SwingUtilities.getWindowAncestor((Component) e.getSource());
                    fm.dispose();
                    data = this.model.getProgrammeurs();
                }
            }

            if (e.getSource().equals(this.identificator.get("Modifier le salaire"))) {
                data = this.model.getProgrammeurs();
                this.typeRv = 2;
            }
            if (e.getSource().equals(this.identificator.get("Quitter le programme"))) {
                System.exit(0);
            }
            if (e.getSource().equals(this.rv.getDeleteButton())) {
                deleteProg();
                data = this.model.getProgrammeurs();
            }
            this.rv.modifyPanel(this.typeRv, data, null);
        }
    }


    private void deleteProg(){
        Object[] options = { "Supprimer", "Annuler" };
        int answer = JOptionPane.showOptionDialog(null, "Êtes-vous sûr de votre choix ?", "Alerte Suppression",
                JOptionPane.YES_OPTION, JOptionPane.NO_OPTION,
                null, options, options[0]);
        if(answer == JOptionPane.YES_OPTION){
            for (int id : this.rv.getIdRowSelected()) {
                this.model.deleteProg(id);
            }
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
        if(this.pv.getAllTextFields().get("prime").getText() != null){
            prog.setId(Integer.parseInt((this.pv.getAllTextFields().get("id").getText())));
        }

        return prog;
    }

    private boolean validate() {

        if (!StringUtils.isNumeric(this.pv.getAllTextFields().get("naissance").getText())) {
            return true;
        }
        if (!this.pv.getAllTextFields().get("prime").getText().matches("[-+]?[0-9]*[.|,]?[0-9]+")) {
            return true;
        }
        return !this.pv.getAllTextFields().get("salaire").getText().matches("[-+]?[0-9]*[.|,]?[0-9]+");
    }

    public void setPv(ProgrammeurView pv) {
        this.pv = pv;
    }

    private void openModal(@Nullable ProgrammeurBean pb, String type, boolean modify) {
        String title = null;
        ProgrammeurView pv = null;
        if (pb == null) {
            title = "Ajout";
            pv = new ProgrammeurView();
        } else {
            title = pb.getNom().toUpperCase() + " " + pb.getPrenom();
            pv = new ProgrammeurView(pb, modify);
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

        if (e.getClickCount() == 2) {

            //Ouvre la modal pour l'édition d'un programmeur
            if (((DefaultCellEditor) laTable.getDefaultEditor(Object.class)).getClickCountToStart() == 1) {
                Object targetId = laTable.getValueAt(laTable.getSelectedRow(), laTable.getColumnModel().getColumnIndex("ID"));
                ProgrammeurBean prog = this.model.getListeProg().get(targetId);
                openModal(prog, "edit", true);
            }

            //Ouvre la modal en mode lecture uniquement
            if (((DefaultCellEditor) laTable.getDefaultEditor(Object.class)).getClickCountToStart() == 0) {
                Object targetId = laTable.getValueAt(laTable.getSelectedRow(), laTable.getColumnModel().getColumnIndex("ID"));
                ProgrammeurBean prog = this.model.getListeProg().get(targetId);
                openModal(prog, "display", false);
            }
        }
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
