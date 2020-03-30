package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;

public class FenetreMere extends JFrame {

    private BasePanel basePanel = new BasePanel();

    public FenetreMere(String title) throws HeadlessException {

        //On créé la fenêtre mère
        super(title);

        //Lors d'un clic sur la croix rouge, on met fin au programme
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //On définit la taille de la fenêtre
        this.setPreferredSize(new Dimension(1000, 500));
        this.basePanel.createListeners();
        this.setContentPane(this.basePanel);

        this.setBounds(800, 350, 450, 300);
        this.pack();
        this.setVisible(true);
    }


}
