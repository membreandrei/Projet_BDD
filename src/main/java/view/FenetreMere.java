package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        this.setBackground(Color.decode("#303030"));
        this.setContentPane(this.basePanel);
        this.setBounds(500, 250, 0, 0);
        this.pack();
        this.setVisible(true);
    }

    public FenetreMere(String title, ProgrammeurView view) {
        //On créé la fenêtre mère
        super(title);

        //On définit la taille de la fenêtre
        this.setPreferredSize(new Dimension(600, 300));
        this.setBackground(Color.decode("#303030"));
        this.setLayout(new BorderLayout());
        this.add(view, BorderLayout.CENTER);
        JButton exit = new JButton("OK");

        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JPanel jp = new JPanel();
        exit.setFocusable(false);
        jp.setBackground(Color.decode("#303030"));
        exit.setBackground(Color.decode("#3a3a3a"));
        exit.setForeground(Color.white);
        exit.setMargin(new Insets(5,20,5,20));
        jp.add(exit, BorderLayout.CENTER);

        this.add(jp, BorderLayout.PAGE_END);
        this.setBounds(700, 350, 450, 300);
        this.pack();
        this.setVisible(true);
    }

    public BasePanel getBasePanel() {
        return basePanel;
    }
}
