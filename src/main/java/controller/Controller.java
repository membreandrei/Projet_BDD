package controller;

import model.ActionsBDD;
import model.ActionsBDDImpl;
import model.ProgrammeurBean;
import view.*;

import javax.swing.*;
import javax.xml.transform.Result;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.WeakHashMap;

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
        if (e.getSource().equals(this.identificator.get("Afficher tous les programmeurs"))) {
            WeakHashMap<Integer, ProgrammeurBean> data = this.model.getProgrammeurs();
            this.rv.modifyPanel(0, data);
            String text = "";
            for (Integer key : data.keySet())
                text += data.get(key).toString() + "------------------------------------------------------------\n";
            text += " END ";
            this.rv.editText(text);
        }
        if (e.getSource().equals(this.identificator.get("Afficher un programmeur"))){
            WeakHashMap<Integer, ProgrammeurBean> data = this.model.getProgrammeurs();
            this.rv.modifyPanel(1, data);
        }
        if (e.getSource().equals(this.identificator.get("Supprimer un programmeur"))){
            //this.rv.modifyPanel(2, data);
        }
        if (e.getSource().equals(this.identificator.get("Ajouter un programmeur"))){
            //this.rv.modifyPanel(3, data);
        }
        if (e.getSource().equals(this.identificator.get("Modifier le salaire"))){
            //this.rv.modifyPanel(4, data);
        }
        if (e.getSource().equals(this.identificator.get("Quitter le programme"))) {
            System.exit(0);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JTable laTable = (JTable) e.getSource();
        Object targetId = laTable.getValueAt(laTable.getSelectedRow(), laTable.getColumnModel().getColumnIndex("ID"));
        ProgrammeurBean prog = this.model.getListeProg().get(targetId);
        ProgrammeurView pv = new ProgrammeurView(prog);

        FenetreMere fm = new FenetreMere(prog.getNom().toUpperCase() + " " + prog.getPrenom(), pv);

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
