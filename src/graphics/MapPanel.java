package graphics;

import algorithm.Individual;
import data.Database;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by diogo on 22/05/2016.
 */
public class MapPanel extends JPanel{
    private BufferedImage map,court;
    private static final double LEFT_SIZE = -9.550542;
    private static final double TOP_SIZE = 42.114205;
    private static final double BOTTOM_SIZE = 36.923611;
    private static final double RIGHT_SIZE = -6.180207;
    private static double PIXEL_PER_COORD_HOR;
    private static double PIXEL_PER_COORD_VER;

    private Individual ind;

    public MapPanel(){
        ind=new Individual();
        ind.randGenes();
        try {
            map = ImageIO.read(new File(System.getProperty("user.dir") + "/resources/img/map.png"));
            court=ImageIO.read(new File(System.getProperty("user.dir") + "/resources/img/court.png"));
            PIXEL_PER_COORD_HOR=map.getWidth()/(RIGHT_SIZE-LEFT_SIZE);
            PIXEL_PER_COORD_VER=map.getHeight()/(TOP_SIZE-BOTTOM_SIZE);
            System.out.println("hor: " + PIXEL_PER_COORD_HOR +" ver: "+PIXEL_PER_COORD_VER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MapPanel(Individual ind){
        this.ind= ind;
        try {
            map = ImageIO.read(new File(System.getProperty("user.dir") + "/resources/img/map.png"));
            court=ImageIO.read(new File(System.getProperty("user.dir") + "/resources/img/court.png"));
            PIXEL_PER_COORD_HOR=map.getWidth()/(RIGHT_SIZE-LEFT_SIZE);
            PIXEL_PER_COORD_VER=map.getHeight()/(TOP_SIZE-BOTTOM_SIZE);
            System.out.println("hor: " + PIXEL_PER_COORD_HOR +" ver: "+PIXEL_PER_COORD_VER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(map,0,0,map.getWidth(),map.getHeight(),this);
        g.drawImage(court,0,0,court.getWidth(),court.getHeight(),this);
        double posX,posY;
        for(int i = 0; i< Individual.SIZE; i++){
            //if(ind.getGene(i)) {
                posX = (Database.getInstance().getCounty(i).getLongitude() - LEFT_SIZE) * PIXEL_PER_COORD_HOR;
                posY = (TOP_SIZE - Database.getInstance().getCounty(i).getLatitude()) * PIXEL_PER_COORD_VER;
                //g.drawImage(court,(int)posX,(int)posY,court.getWidth(),court.getHeight(),this);
                g.drawOval((int) posX, (int) posY, 5, 5);
                System.out.println("Court-> hor: " + posX + " ver: " + posY);
            //}
        }

    }


    public Individual getInd() {
        return ind;
    }

    public void setInd(Individual ind) {
        this.ind = ind;
    }


}
