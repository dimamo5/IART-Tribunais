package graphics;

import algorithm.GeneticAlgorithm;
import algorithm.Individual;
import algorithm.SimAnnealing;
import data.Database;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by diogo on 22/05/2016.
 */
public class MapPanel extends JPanel {
    private static final double LEFT_SIZE = -9.560542;
    private static final double TOP_SIZE = 42.134205;
    private static final double BOTTOM_SIZE = 36.903611;
    private static final double RIGHT_SIZE = -6.180207;
    private static double PIXEL_PER_COORD_HOR;
    private static double PIXEL_PER_COORD_VER;
    private BufferedImage map;
    private Individual ind;

    public MapPanel() {
        try {
            map = ImageIO.read(new File(System.getProperty("user.dir") + "/resources/img/map.png"));
            PIXEL_PER_COORD_HOR = map.getWidth() / (RIGHT_SIZE - LEFT_SIZE);
            PIXEL_PER_COORD_VER = map.getHeight() / (TOP_SIZE - BOTTOM_SIZE);
            this.setMinimumSize(new Dimension(map.getWidth(), map.getHeight()));
            this.setSize(new Dimension(map.getWidth(), map.getHeight()));
            this.setPreferredSize(new Dimension(map.getWidth(), map.getHeight()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MapPanel(Individual ind) {
        this.ind = ind;
        try {
            map = ImageIO.read(new File(System.getProperty("user.dir") + "/resources/img/map.png"));
            PIXEL_PER_COORD_HOR = map.getWidth() / (RIGHT_SIZE - LEFT_SIZE);
            PIXEL_PER_COORD_VER = map.getHeight() / (TOP_SIZE - BOTTOM_SIZE);
            this.setMinimumSize(new Dimension(map.getWidth(), map.getHeight()));
            this.setSize(new Dimension(map.getWidth(), map.getHeight()));
            this.setPreferredSize(new Dimension(map.getWidth(), map.getHeight()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(map, 0, 0, map.getWidth(), map.getHeight(), this);
        double posX, posY;
        if (ind != null) {
            for (int i = 0; i < Individual.SIZE; i++) {
                if (ind.getGene(i)) {
                    posX = (Database.getInstance().getCounty(i).getLongitude() - LEFT_SIZE) * PIXEL_PER_COORD_HOR;
                    posY = (TOP_SIZE - Database.getInstance().getCounty(i).getLatitude()) * PIXEL_PER_COORD_VER;

                    Graphics2D g2d = (Graphics2D) g;
                    Ellipse2D.Double circle = new Ellipse2D.Double((int) posX, (int) posY, 5, 5);
                    g2d.fill(circle);
                    //g.drawOval((int) posX, (int) posY, 5, 5);
                }
            }
        }
    }


    public Individual getInd() {
        return ind;
    }

    public void setInd(Individual ind) {
        this.ind = ind;
    }

    public void startGA(GeneticAlgorithm ga) {
        for (int ite = 0; ite < GeneticAlgorithm.MAX_ITER; ite++) {
            ga.startIte(ite);
            if (ite % 5 == 0) {
                ind = ga.getBestIndiv();
                this.paintImmediately(0, 0, this.getWidth(), this.getHeight());
            }
        }
    }

    public void startSA(SimAnnealing sa) {
        while (sa.temperature > sa.stop_cond) {
            sa.runIte();
            ind = sa.getBestInd();
            this.paintImmediately(0, 0, this.getWidth(), this.getHeight());
        }
        System.out.println(sa.getBestInd());
    }

    public void startGASA() {
        GeneticAlgorithm ga = new GeneticAlgorithm();
        startGA(ga);
        System.out.println("GA: " + ga.getBestIndiv());
        SimAnnealing sa = new SimAnnealing(ga.getBestIndiv());
        startSA(sa);
        System.out.println("SA: " + sa.getBestInd());
    }
}
