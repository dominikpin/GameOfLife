import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyFrame extends JFrame implements ActionListener, KeyListener {

    private JPanel mainPanel;
    private JLabel pausedLabel;

    public MyFrame(int sizeY, int sizeX, int RESIZE) {
        JFrame frame = new JFrame();    
        this.setTitle("Game Of Life");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(sizeX, sizeY);
        this.setBackground(Color.WHITE);
        this.setLocationRelativeTo(null);
        this.setFocusable(true);
        addKeyListener(this);


        this.setLayout(new BorderLayout());
        mainPanel = new JPanel(); 
        this.add(mainPanel, BorderLayout.CENTER);
        mainPanel.setLayout(new GridLayout(sizeY/RESIZE, sizeX/RESIZE));
    }

    public void updateButtonArray(int[][] array) {
        Component[] components = mainPanel.getComponents();
        for (Component c : components) {
            if (c instanceof JButton) {
                String panelName = c.getName();
                String[] stringCoordinates = panelName.split(",");
                int value = array[Integer.parseInt(stringCoordinates[0])][Integer.parseInt(stringCoordinates[1])];
                //System.out.println(value + " " + c.getName());
                if (value == 0) {
                    c.setBackground(Color.WHITE);
                    //System.out.println(y + " " + x);
                } else {
                    c.setBackground(Color.BLACK);
                    //System.out.println(y + " " + x);
                }
            }
        }
    }

    public void makeButtonArray(int[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                JButton button = new JButton();
                if (array[i][j] == 0) {
                    button.setBackground(Color.WHITE);
                } else {
                    button.setBackground(Color.BLACK);
                }
                button.setName(i + "," + j);
                button.addActionListener(this);
                mainPanel.add(button);
            }
        }
        setVisible(true);
    }

    public void showPausedMessage(Boolean isInProgress) {
        if (!isInProgress) {
            pausedLabel = new JLabel("PAUSED");
            pausedLabel.setName("PAUSED");
            this.add(pausedLabel, BorderLayout.SOUTH);
            pausedLabel.setFont(pausedLabel.getFont().deriveFont(50.0f));
            pausedLabel.setForeground(Color.RED);
            pausedLabel.setOpaque(false);
            pausedLabel.setBackground(new Color(0, 0, 0, 0));
            pausedLabel.setHorizontalAlignment(JLabel.CENTER);
            pausedLabel.setVerticalAlignment(JLabel.CENTER);
            Dimension size = pausedLabel.getPreferredSize();
            this.setSize(this.getWidth(), this.getHeight() + size.height);
            return;
        } 
        Component[] components = this.getContentPane().getComponents();
        for (Component c : components) {
            if (c instanceof JLabel && c.getName().equals("PAUSED")) {
                Dimension size = c.getPreferredSize();
                this.setSize(this.getWidth(), this.getHeight() - size.height);
                this.remove(c);
                return;
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        this.requestFocusInWindow();
        JButton button = ((JButton) e.getSource());
        String buttonName = button.getName();
        String[] stringCoordinates = buttonName.split(",");
        int[][] array = Main.getMyArray();
        if (button.getBackground() == Color.BLACK) {
            button.setBackground(Color.WHITE);
            array[Integer.parseInt(stringCoordinates[0])][Integer.parseInt(stringCoordinates[1])] = 0;
        } else {
            button.setBackground(Color.BLACK);
            array[Integer.parseInt(stringCoordinates[0])][Integer.parseInt(stringCoordinates[1])] = 1;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            showPausedMessage(Main.startOrStopGame());
        }
        if (e.getKeyCode() == KeyEvent.VK_R) {
            Main.restartArray();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}