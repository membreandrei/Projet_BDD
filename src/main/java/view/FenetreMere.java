package view;

import javax.swing.*;
import java.awt.*;

public class FenetreMere extends JFrame {

    private JPanel basePanel = new JPanel(new GridBagLayout());
    private GridBagConstraints gbc = new GridBagConstraints();

    public FenetreMere(String title) throws HeadlessException {

        //On créé la fenêtre mère
        super(title);

        //Lors d'un clic sur la croix rouge, on met fin au programme
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //On définit la taille de la fenêtre
        this.setPreferredSize(new Dimension(1000, 500));

        this.createLayout();


        this.setBounds(800, 350, 450, 300);
        this.pack();
        this.setVisible(true);
    }

    private void createLayout(){
        MenuView panelMenu = new MenuView();
        ResultatView panelResultat = new ResultatView();
        //On créé d'abord la zone dédiée au menu
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.weightx = 0;
        gbc.weighty = 0.5;
        gbc.gridx = 0;
        gbc.gridy = 0;
        basePanel.add(panelMenu, gbc);

        gbc = null;
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.gridx = 2;
        gbc.gridy = 0;
        basePanel.add(panelResultat, gbc);
        setContentPane(basePanel);
    }

}
