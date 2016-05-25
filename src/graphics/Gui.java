package graphics;

import algorithm.GeneticAlgorithm;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by diogo on 22/05/2016.
 */
public class Gui extends JFrame {
    public GeneticAlgorithm ga = new GeneticAlgorithm();
    private MapPanel map = new MapPanel();
    private SettingsPanel settings = new SettingsPanel();

    public Gui() {
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(600, 1000);
        setVisible(true);
        JLabel title = new JLabel("Distribuição de Tribunais", SwingConstants.CENTER);
        title.setFont(new Font(title.getFont().getFontName(), Font.PLAIN, 25));
        title.setBorder(new EmptyBorder(5, 0, 20, 0));
        getContentPane().add(title, BorderLayout.NORTH);
        getContentPane().add(map, BorderLayout.CENTER);
        getContentPane().add(settings, BorderLayout.EAST);
        pack();
        setResizable(false);

    }


    public static void main(String[] args) {
        new Gui();

    }

}
