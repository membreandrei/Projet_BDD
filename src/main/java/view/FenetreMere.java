package view;

import utils.StyleHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class FenetreMere extends JFrame {

    /**
     * champ static basePanel, il s'agit du panel de base sur lequel viennent se greffer les autres panels
     */
    private static BasePanel basePanel = new BasePanel();

    /**
     * Construit la fen�tre g�n�rale de l'application
     * @param title
     * @throws HeadlessException
     */
    public FenetreMere(String title) throws HeadlessException {

        //On cr�� la fen�tre m�re
        super(title);

        //Lors d'un clic sur la croix rouge, on met fin au programme
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //On d�finit la taille de la fen�tre
        this.setPreferredSize(new Dimension(1300, 600));
        FenetreMere.basePanel.createListeners();
        this.setBackground(Color.decode("#303030"));
        this.setContentPane(FenetreMere.basePanel);
        this.setBounds(200, 250, 0, 0);
        this.pack();
        StyleHelper.setStyleJOptionPane();
        this.setVisible(true);
    }

    /**
     * Construit les sous-fen�tres d�di�es � l'affichage en d�tail d'un programmeur, ou bien l'ajout d'un nouveau
     * @param title
     * @param view
     * @param type
     */
    public FenetreMere(String title, MediaView view, String type){
        //On cr�� la fen�tre m�re
        super(title);

        //On d�finit la taille de la fen�tre
        this.setPreferredSize(new Dimension(600, 300));
        StyleHelper.setStyle(this);
        this.setLayout(new BorderLayout());
        this.add(view, BorderLayout.CENTER);

        JPanel jp = new JPanel();
        jp.setBackground(Color.decode("#303030"));

        JButton exit = new JButton("Annuler");
        exit.setActionCommand("close");
        exit.setMargin(new Insets(5,20,5,20));
        StyleHelper.setStyle(exit);

        ActionListener al = e -> dispose();
        exit.addActionListener(al);

        if(type.equals("add")) {
            this.fenetreAjout(jp, view);
        } else if(type.equals("edit")){
            this.fenetreEdit(jp, view);
        }

        jp.add(exit, BorderLayout.CENTER);

        this.add(jp, BorderLayout.PAGE_END);
        this.setBounds(700, 350, 450, 300);
        this.pack();
        this.setVisible(true);
    }

    /**
     * D�finit la fen�tre d'�dition d'un programmeur existant
     * @param jp
     * @param view
     */
    private void fenetreEdit(JPanel jp, MediaView view){
        JButton ajout = new JButton("Enregistrer");
        ajout.setActionCommand("enregistrer");
        StyleHelper.setStyle(ajout);
        ajout.setMargin(new Insets(5,20,5,20));

        jp.add(ajout, BorderLayout.CENTER);
        jp.add(Box.createHorizontalStrut(20));

        view.actionButtonAdd(this.getBasePanel().getController(), ajout);
    }

    /**
     * D�finit la fen�tre d'ajout d'un programmeur inexistant
     * @param jp
     * @param view
     */
    private void fenetreAjout(JPanel jp, MediaView view){
        JButton ajout = new JButton("Ajouter");
        ajout.setActionCommand("ajout");
        StyleHelper.setStyle(ajout);
        ajout.setMargin(new Insets(5,20,5,20));

        jp.add(ajout, BorderLayout.CENTER);
        jp.add(Box.createHorizontalStrut(20));

        view.actionButtonAdd(this.getBasePanel().getController(), ajout);
    }

    public BasePanel getBasePanel() {
        return basePanel;
    }
}
