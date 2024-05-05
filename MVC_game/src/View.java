import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.*;

import static javax.imageio.ImageIO.read;

public class View extends JFrame {

    private JTextField textField1;
    private JButton speichernButton;
    private JButton ladenButton;
    private JButton starteButton;
    private JTextField textField2;
    private Controller derController;
    private JPanel dasJPanel;

    public View() {
        super();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        int frameWidth = 300;
        int frameHeight = 300;
        setSize(frameWidth, frameHeight);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - getSize().width) / 2;
        int y = (d.height - getSize().height) / 2;
        setLocation(x, y);
        setTitle("Model.View");
        setResizable(false);
        Container cp = getContentPane();
        cp.setLayout(null);

        // Anfang Komponenten
        textField1 = new JTextField();
        textField1.setBounds(16, 8, 131, 36);
        textField1.setText("");
        cp.add(textField1);

        speichernButton = new JButton();
        speichernButton.setBounds(16, 56, 75, 25);
        speichernButton.setText("Speichern");
        speichernButton.setMargin(new Insets(2, 2, 2, 2));
        speichernButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                drueckeLadenActionEvent(evt);
            }
        });
        cp.add(speichernButton);

        ladenButton = new JButton();
        ladenButton.setBounds(16, 106, 75, 25);
        ladenButton.setText("Laden");
        ladenButton.setMargin(new Insets(2, 2, 2, 2));
        ladenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                drueckeSpeichernActionEvent(evt);
            }
        });
        cp.add(ladenButton);

        starteButton = new JButton();
        starteButton.setBounds(16, 186, 75, 25);
        starteButton.setText("Starte");
        starteButton.setMargin(new Insets(2, 2, 2, 2));
        starteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                drueckeStarteActionEvent(evt);
            }
        });
        cp.add(starteButton);

        textField2 = new JTextField();
        textField2.setBounds(16, 128, 131, 36);
        textField2.setText("");
        cp.add(textField2);
        // Ende Komponente
        derController = new Controller(this);
        setVisible(true);
    }
    public void drueckeLadenActionEvent(ActionEvent evt) {
        derController.laden();
    }
    public void drueckeSpeichernActionEvent(ActionEvent evt) {
        derController.speichern();
    }

    public void drueckeStarteActionEvent(ActionEvent evt) {
        derController.starte();
    }

    public void deleteReference(){
        Container cp = getContentPane();
        cp.remove(textField1);
        cp.remove(speichernButton);
        cp.remove(ladenButton);
        cp.remove(starteButton);
        cp.remove(textField2);
        ladenButton = new JButton();
        ladenButton.setBounds(207,78, 20, 20);
        ladenButton.setMargin(new Insets(2, 2, 2, 2));
        ladenButton.setOpaque(false);
        ladenButton.setContentAreaFilled(false);
        ladenButton.setBorderPainted(false);
        ladenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                drueckeSpinActionEvent(evt);
            }
        });
        cp.add(ladenButton);
        textField2 = new JTextField();
        textField2.setBounds(16, 8, 131, 36);
        textField2.setText("100");
        textField2.setEditable(false);
        cp.add(textField2);
        dasJPanel = new JPanel(){
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            derController.render(g);
        }
        };

        int frameWidth = 192;
        int frameHeight = 192;
        dasJPanel.setSize(frameWidth, frameHeight);
        dasJPanel.setLocation(54, 54);
        dasJPanel.setVisible(true);
        cp.add(dasJPanel);
        revalidate();
    }

    public String getWert() {
        return textField1.getText();
    }
    public void setWert(String pWert) {
        textField2.setText(pWert);
    }
    public void drueckeSpinActionEvent(ActionEvent evt) {
        derController.spin();
    }

    public void painting(){
        dasJPanel.repaint();
    }
}