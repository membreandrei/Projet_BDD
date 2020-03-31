package controller;

import model.ActionsBDD;
import model.ActionsBDDImpl;
import model.ProgrammeurBean;
import view.BasePanel;
import view.MenuView;
import view.ResultatView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class Controller implements ActionListener {
    private MenuView mv;
    private ResultatView rv;
    private HashMap<String, JButton> identificator;
    private ActionsBDDImpl model;

    public Controller(BasePanel bp){
        this.mv = bp.getMv();
        this.rv = bp.getRv();
        this.fillHashMap();
        this.model = new ActionsBDDImpl();
    }

    private void fillHashMap(){
        this.identificator = new HashMap<String, JButton>();
        for (JButton button : this.mv.getAllButtons()){
            this.identificator.put(button.getText(), button);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.identificator.get("Afficher tous les programmeurs"))){
            String text = "";
            for (ProgrammeurBean prog : this.model.getProgrammeurs())
                text += prog.toString() + "------------------------------------------------------------\n";
            text += " END ";
            this.rv.editText(text);
        }
    }
}
