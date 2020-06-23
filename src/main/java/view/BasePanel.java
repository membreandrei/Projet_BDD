package view;

import controller.Controller;

import java.awt.*;

public class BasePanel extends ViewPanel {

    private GridBagConstraints gbc = new GridBagConstraints();
    private final MenuView mv;
    private ResultatView rv;
    private Controller controller;

    public BasePanel() {
        this.mv = new MenuView();
        this.rv = new ResultatView();
        this.setLayout(new GridBagLayout());
        this.createLayout();
    }

    /**
     * Cr�e les listeners pour le contr�leur
     */
    public void createListeners() {
        this.controller = new Controller(this);
        this.mv.addListeners(this.controller);
    }

    public MenuView getMv() {
        return mv;
    }

    public ResultatView getRv() {
        return rv;
    }

    public void setRv(ResultatView rv) {
        this.rv = rv;
    }

    public Controller getController() {
        return controller;
    }

    /**
     * Cr�� le layout du basePanel, afin d'organiser MenuView et ResultatView correctement
     */
    private void createLayout() {
        //On cr�� d'abord la zone d�di�e au menu
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.weightx = 0;
        gbc.weighty = 0.5;
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(this.mv, gbc);

        gbc = null;
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.gridx = 1;
        gbc.gridy = 0;
        this.add(this.rv, gbc);
    }
}
