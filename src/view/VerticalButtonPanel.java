package view;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class VerticalButtonPanel extends JPanel {
    public VerticalButtonPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.NORTH;
        add(new JButton("Button 1"), gbc);
        gbc.gridy = 1;
        add(new JButton("Button 2"), gbc);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Vertical Button Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new VerticalButtonPanel());
        frame.pack();
        frame.setVisible(true);
    }
}