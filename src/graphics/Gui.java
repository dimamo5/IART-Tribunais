package graphics;

import algorithm.GeneticAlgorithm;

import javax.swing.*;
import java.awt.*;

/**
 * Created by diogo on 22/05/2016.
 */
public class Gui extends JFrame {
    public GeneticAlgorithm ga = new GeneticAlgorithm();
    private MapPanel map = new MapPanel();

    public Gui() {
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(500,900);
        setVisible(true);
        getContentPane().add(map, BorderLayout.CENTER);

    }


    public static void main(String[] args) {
        Gui g = new Gui();

    }

}
