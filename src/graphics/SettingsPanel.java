package graphics;

import data.Database;

import javax.swing.*;
import java.awt.*;

/**
 * Created by diogo on 25/05/2016.
 */
public class SettingsPanel extends JPanel {
    public final int COUNTY_SIZE = Database.getInstance().getSize();
    public final static int MIN_COUNTY_SIZE = 20;
    private int constTribunal = 1;
    private int constContruction = 1;
    private int maxDist = 50000;

    public SettingsPanel() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        JButton button;

        button = new JButton("Start");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        this.add(button, c);

        button = new JButton("Stop");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        this.add(button, c);

        button = new JButton("Button 3");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        this.add(button, c);

        button = new JButton("Long-Named Button 4");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 40;      //make this component tall
        c.weightx = 0.0;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        this.add(button, c);

        button = new JButton("5");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;       //reset to default
        c.weighty = 1.0;   //request any extra vertical space
        c.gridx = 1;       //aligned with button 2
        c.gridwidth = 2;   //2 columns wide
        c.gridy = 2;       //third row
        this.add(button, c);
    }
}
