package graphics;

import data.Database;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by diogo on 25/05/2016.
 */
public class SettingsPanel extends JPanel {
    public final int COUNTY_SIZE = Database.getInstance().getSize();
    public final static int MIN_COUNTY_SIZE = 20;
    private int constTribunal = 2;
    private int constContruction = 1;
    private int maxDist = 50000;

    public SettingsPanel() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        JButton button;
        this.setBorder(new EmptyBorder(5, 25, 5, 5));

        button = new JButton("Start");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.ipady = 25;
        this.add(button, c);

        button = new JButton("Stop");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        this.add(button, c);

        JLabel label = new JLabel("Max. Tribunais: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        this.add(label, c);

        JSlider slider = new JSlider(MIN_COUNTY_SIZE, COUNTY_SIZE, 100);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(50);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        this.add(slider, c);

        label = new JLabel("Constante Tribunais: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        this.add(label, c);

        slider = new JSlider(1, 5, this.constTribunal);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(1);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 2;
        this.add(slider, c);

        label = new JLabel("Constante Construção: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 3;
        this.add(label, c);

        slider = new JSlider(1, 5, this.constContruction);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(1);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 3;
        this.add(slider, c);

        label = new JLabel("Distancia Máxima: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 15;
        c.gridx = 0;
        c.gridy = 4;
        this.add(label, c);

        JTextField maxDistBox = new JTextField(20);
        maxDistBox.setText(String.valueOf(maxDist));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 4;
        this.add(maxDistBox, c);


        label = new JLabel("Algoritmo: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 5;
        this.add(label, c);

        //Create the radio buttons.
        JRadioButton ga = new JRadioButton("Algoritmo Genetico");
        ga.setSelected(true);
        JRadioButton sa = new JRadioButton("Arrefecimeno Simulado");
        JRadioButton gaTosa = new JRadioButton("GA + AS");

        //Group the radio buttons.
        ButtonGroup radioGroup = new ButtonGroup();
        radioGroup.add(ga);
        radioGroup.add(sa);
        radioGroup.add(gaTosa);

        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new GridLayout(3, 1));
        radioPanel.add(ga);
        radioPanel.add(sa);
        radioPanel.add(gaTosa);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 5;
        this.add(radioPanel, c);

    }
}
