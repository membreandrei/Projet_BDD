package controller;

import model.ActionsBDDImpl;
import model.ProgrammeurBean;
import view.*;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
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
            this.openModal();
        }
        if (e.getSource().equals(this.identificator.get("Modifier le salaire"))) {
            //this.rv.modifyPanel(4, data);
        }
        if (e.getSource().equals(this.identificator.get("Quitter le programme"))) {
            System.exit(0);
        }
        if (e.getSource().equals(this.rv.getSearchButton())) {

            JOptionPane jo = new JOptionPane();
            jo.setBackground(Color.BLUE);
            UIManager.put("OptionPane.background", Color.decode("#3a3a3a"));
            UIManager.put("Panel.background", Color.decode("#3a3a3a"));
            UIManager.put("OptionPane.messageForeground", Color.WHITE);
            UIManager.put("Button.background", Color.decode("#424242"));
            UIManager.put("Button.foreground", Color.WHITE);
            UIManager.put("Button.focusable", false);
            UIManager.put("Button.focus", new ColorUIResource(new Color(0, 0, 0, 0)));

            switch ((String) this.rv.getChoice().getSelectedItem()) {
                case "Par ID":
                    if (!this.rv.getSearchText().getText().matches("^[0-9]+$|^$")) {
                        jo.showMessageDialog(null, "Pas un nombre entier retaper");

                        data = this.model.getProgrammeurs();
                        this.rv.modifyPanel(1, data, "Par ID");
                    } else {
                        if (this.rv.getSearchText().getText().equals("")) {
                            data = this.model.getProgrammeurs();
                            this.rv.modifyPanel(1, data, "Par ID");
                        } else {
                            data = this.model.getProgrammeurById(Integer.parseInt(this.rv.getSearchText().getText()));
                            this.rv.modifyPanel(1, data, "Par ID");
                        }
                    }

                    break;

                case "Par Nom":

                    data = this.model.getProgrammeurByName(this.rv.getSearchText().getText());
                    this.rv.modifyPanel(1, data, "Par Nom");

                    break;

                case "Par Prénom":

                    data = this.model.getProgrammeurByFirstName(this.rv.getSearchText().getText());
                    this.rv.modifyPanel(1, data, "Par Prénom");

                    break;

                case "Par Année de naissance":
                    if (!this.rv.getSearchText().getText().matches("^[0-9]+$|^$")) {
                        jo.showMessageDialog(null, "Pas un nombre entier retaper");

                        data = this.model.getProgrammeurs();
                        this.rv.modifyPanel(1, data, "Par Année de naissance");
                    } else {
                        if (this.rv.getSearchText().getText().equals("")) {
                            data = this.model.getProgrammeurs();
                            this.rv.modifyPanel(1, data, "Par Année de naissance");
                        } else {
                            data = this.model.getProgrammeurByYear(Integer.parseInt(this.rv.getSearchText().getText()));
                            this.rv.modifyPanel(1, data, "Par Année de naissance");
                        }
                    }

                    break;

            }
        }
    }

    private void openModal(){
        //ProgrammeurView pv = new ProgrammeurView(null);
        new FenetreMere("Ajout", new ProgrammeurView(), true);
    }
    private void openModal(ProgrammeurBean pb){
        ProgrammeurView pv = new ProgrammeurView(pb);
        new FenetreMere(pb.getNom().toUpperCase() + " " + pb.getPrenom(), pv);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JTable laTable = (JTable) e.getSource();
        Object targetId = laTable.getValueAt(laTable.getSelectedRow(), laTable.getColumnModel().getColumnIndex("ID"));
        ProgrammeurBean prog = this.model.getListeProg().get(targetId);
        openModal(prog);
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
