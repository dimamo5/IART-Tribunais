package graphics;

import algorithm.Evaluate;
import algorithm.GeneticAlgorithm;
import algorithm.SimAnnealing;
import data.Database;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by diogo on 25/05/2016.
 */
public class SettingsPanel extends JPanel {
    public final int COUNTY_SIZE = Database.getInstance().getSize();
    public final static int MIN_COUNTY_SIZE = 20;
    GridBagConstraints c = new GridBagConstraints();

    public SettingsPanel() {
        super();
        this.setLayout(new GridBagLayout());
        this.setBorder(new EmptyBorder(5, 25, 5, 5));
        c.weightx = 1;
        c.weighty = 1;
        c.anchor = GridBagConstraints.NORTH;

        JButton button = new JButton("Start");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Evaluate.MAX_NO_TRIBUNAL = ((JSlider) SettingsPanel.this.getComponent(3)).getValue();
                Evaluate.CONST_ACCESS_TRIBUNAL = ((JSlider) SettingsPanel.this.getComponent(5)).getValue();
                Evaluate.CONST_CONSTRUCT_TRIBUNAL = ((JSlider) SettingsPanel.this.getComponent(7)).getValue();
                Evaluate.MAX_DIST = Integer.valueOf(((JTextField) SettingsPanel.this.getComponent(9)).getText());

                GeneticAlgorithm.MAX_ITER = Integer.valueOf(((JTextField) SettingsPanel.this.getComponent(14)).getText());
                GeneticAlgorithm.CROSSOVER_RATE = (double) ((JSlider) SettingsPanel.this.getComponent(16)).getValue() / 100;
                GeneticAlgorithm.MUTATION_RATE = (double) ((JSlider) SettingsPanel.this.getComponent(18)).getValue() / 100;

                SimAnnealing.coolingRate = Double.valueOf(((JTextField) SettingsPanel.this.getComponent(21)).getText());
                SimAnnealing.STOP_CONDITION = Double.valueOf(((JTextField) SettingsPanel.this.getComponent(23)).getText());

                JPanel algorithmPanel = ((JPanel) SettingsPanel.this.getComponent(11));

                if (((JRadioButton) algorithmPanel.getComponent(0)).isSelected()) {
                    ((MapPanel) SettingsPanel.this.getParent().getComponent(1)).startGA(new GeneticAlgorithm());
                } else if (((JRadioButton) algorithmPanel.getComponent(1)).isSelected()) {
                    ((MapPanel) SettingsPanel.this.getParent().getComponent(1)).startSA(new SimAnnealing());
                } else if (((JRadioButton) algorithmPanel.getComponent(2)).isSelected()) {
                    ((MapPanel) SettingsPanel.this.getParent().getComponent(1)).startGASA();
                }

            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.ipady = 25;
        this.add(button, c);

        button = new JButton("Exit");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(1);
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        this.add(button, c);

        JLabel label = new JLabel("Max. Tribunais: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        this.add(label, c);

        JSlider slider = new JSlider(MIN_COUNTY_SIZE, COUNTY_SIZE, Evaluate.MAX_NO_TRIBUNAL);
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

        slider = new JSlider(1, 5, Evaluate.CONST_ACCESS_TRIBUNAL);
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

        slider = new JSlider(1, 5, Evaluate.CONST_CONSTRUCT_TRIBUNAL);
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

        JTextField maxDistBox = new JTextField(15);
        maxDistBox.setText(String.valueOf(Evaluate.MAX_DIST));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 4;
        this.add(maxDistBox, c);

        setAlgorithmOptions();
        setGAOptions();
        setSAOptions();

    }

    public void setSAOptions() {
        JLabel label = new JLabel("Opcoes Arrefecimento Simulado: ", SwingConstants.CENTER);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 10;
        c.gridwidth = 2;
        this.add(label, c);

        // TODO: 25/05/2016 Traduzir isto e da funcao abaixo
        label = new JLabel("Cooling Rate: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 11;
        this.add(label, c);

        JTextField ite = new JTextField(15);
        ite.setText(String.valueOf(SimAnnealing.coolingRate));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 11;
        this.add(ite, c);

        label = new JLabel("Stopping Temperature: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 12;
        this.add(label, c);

        ite = new JTextField(15);
        ite.setText(String.valueOf(SimAnnealing.STOP_CONDITION));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 12;
        this.add(ite, c);

    }

    public void setGAOptions() {
        JLabel label = new JLabel("Opcoes Algoritmo Genetico: ", SwingConstants.CENTER);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 2;
        this.add(label, c);

        label = new JLabel("Iterações: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        c.ipady = 15;
        c.gridx = 0;
        c.gridy = 7;
        this.add(label, c);

        JTextField ite = new JTextField(15);
        ite.setText(String.valueOf(GeneticAlgorithm.MAX_ITER));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 7;
        this.add(ite, c);

        label = new JLabel("Crossover Rate (%): ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 8;
        this.add(label, c);

        JSlider slider = new JSlider(10, 100, (int) (GeneticAlgorithm.CROSSOVER_RATE * 100));
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(10);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 8;
        this.add(slider, c);

        label = new JLabel("Mutation Rate (%): ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 9;
        this.add(label, c);

        slider = new JSlider(5, 50, (int) (GeneticAlgorithm.MUTATION_RATE * 100));
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(5);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 9;
        this.add(slider, c);


    }

    public void setAlgorithmOptions() {
        JLabel label = new JLabel("Algoritmo: ");
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
