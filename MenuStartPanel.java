package cours3;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuStartPanel extends JPanel {
    private static final Color BACKGROUND_START_COLOR = new Color(70, 130, 180);
    private static final Color BACKGROUND_END_COLOR = new Color(60, 63, 65);
    private static final Color BUTTON_COLOR = new Color(34, 167, 240);
    private static final Color BUTTON_HOVER_COLOR = new Color(52, 152, 219);
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Font TITLE_FONT = new Font("Serif", Font.BOLD, 40);
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 22);
    private static final Font LABEL_FONT = new Font("Arial", Font.PLAIN, 20);
    private static final Font INPUT_FONT = new Font("Arial", Font.PLAIN, 20);

    private JTextField nameField;
    private JComboBox<String> classDropdown;

    public MenuStartPanel(JPanel mainPanel, CardLayout cardLayout, Player player, WeaponStore weaponStore) {
        setLayout(new GridBagLayout());
        setOpaque(false); // Permet d'afficher l'arrière-plan personnalisé

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addTitle(gbc);
        addNameInput(gbc);
        addClassDropdown(gbc);
        addStartButton(gbc, mainPanel, cardLayout, player);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        GradientPaint gradient = new GradientPaint(0, 0, BACKGROUND_START_COLOR, 0, getHeight(), BACKGROUND_END_COLOR);
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    private void addTitle(GridBagConstraints gbc) {
        JLabel titleLabel = new JLabel("RPG Game");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);
    }

    private void addNameInput(GridBagConstraints gbc) {
        JLabel nameLabel = new JLabel("Nom du joueur :");
        nameLabel.setFont(LABEL_FONT);
        nameLabel.setForeground(TEXT_COLOR);
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        add(nameLabel, gbc);

        nameField = new JTextField(20);
        nameField.setFont(INPUT_FONT);
        nameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BUTTON_COLOR, 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        gbc.gridx = 1;
        add(nameField, gbc);
    }

    private void addClassDropdown(GridBagConstraints gbc) {
        JLabel classLabel = new JLabel("Choisissez un Personnage:");
        classLabel.setFont(LABEL_FONT);
        classLabel.setForeground(TEXT_COLOR);
        gbc.gridy = 2;
        gbc.gridx = 0;
        add(classLabel, gbc);

        classDropdown = new JComboBox<>(new String[]{"Sorcier", "Guerrier", "Elfe"});
        classDropdown.setFont(INPUT_FONT);
        classDropdown.setBorder(BorderFactory.createLineBorder(BUTTON_COLOR, 2));
        gbc.gridx = 1;
        add(classDropdown, gbc);
        classDropdown.addActionListener(e -> nameField.requestFocusInWindow());

    }

    private void addStartButton(GridBagConstraints gbc, JPanel mainPanel, CardLayout cardLayout, Player player) {
        JButton startButton = new JButton("Démarrer");
        startButton.setFont(BUTTON_FONT);
        startButton.setForeground(TEXT_COLOR);
        startButton.setBackground(BUTTON_COLOR);
        startButton.setFocusPainted(false);
        startButton.setBorder(BorderFactory.createLineBorder(TEXT_COLOR, 2));
        startButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Hover effect
        startButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                startButton.setBackground(BUTTON_HOVER_COLOR);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                startButton.setBackground(BUTTON_COLOR);
            }
        });

        // Action for the button
        startButton.addActionListener(e -> handleStartButtonAction(mainPanel, cardLayout, player));

        // KeyListener for Enter key on the name field
        nameField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    startButton.doClick(); // Simulates a button click
                }
            }
        });

        // Add the button to the layout
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        add(startButton, gbc);
    }


    private void handleStartButtonAction(JPanel mainPanel, CardLayout cardLayout, Player player) {
        String playerName = nameField.getText().trim();
        String playerClass = (String) classDropdown.getSelectedItem();

        if (playerName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer un nom pour votre personnage.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            player.setName(playerName);
            player.setCharacterClass(playerClass);

            Component[] components = mainPanel.getComponents();
            for (Component comp : components) {
                if (comp instanceof GamePanel) {
                    ((GamePanel) comp).updatePlayerInfo();
                }
            }

            JOptionPane.showMessageDialog(this, "Bienvenue " + playerName + " le " + playerClass + " !");
            cardLayout.show(mainPanel, "GamePanel");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
