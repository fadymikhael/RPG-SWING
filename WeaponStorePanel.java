package cours3;

import javax.swing.*;
import java.awt.*;

public class WeaponStorePanel extends JPanel {
    private Player player;
    private WeaponStore weaponStore;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private DefaultListModel<String> weaponListModel;
    private JTextArea asciiArtArea;

    public WeaponStorePanel(Player player, WeaponStore weaponStore, JPanel mainPanel, CardLayout cardLayout) {
        this.player = player;
        this.weaponStore = weaponStore;
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;

        setLayout(new BorderLayout());
        setBackground(new Color(60, 63, 65));

        // Titre
        JLabel titleLabel = new JLabel("Magasin d'armes");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Liste des armes
        weaponListModel = new DefaultListModel<>();
        for (Weapon weapon : weaponStore.getWeapons()) {
            weaponListModel.addElement(weapon.toString());
        }

        JList<String> weaponList = new JList<>(weaponListModel);
        weaponList.setFont(new Font("Arial", Font.PLAIN, 14));
        weaponList.setBackground(Color.LIGHT_GRAY);
        add(new JScrollPane(weaponList), BorderLayout.CENTER);

        // Zone ASCII pour afficher l'art de l'arme sélectionnée
        asciiArtArea = new JTextArea();
        asciiArtArea.setEditable(false);
        asciiArtArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        asciiArtArea.setBackground(Color.DARK_GRAY);
        asciiArtArea.setForeground(Color.WHITE);
        asciiArtArea.setLineWrap(true);
        asciiArtArea.setWrapStyleWord(true);
        asciiArtArea.setBorder(BorderFactory.createTitledBorder("Arme"));
        asciiArtArea.setRows(10);
        asciiArtArea.setColumns(30);
        asciiArtArea.setPreferredSize(new Dimension(300, 200));
        add(asciiArtArea, BorderLayout.EAST);

        // Ajout d'un écouteur pour afficher l'ASCII de l'arme sélectionnée
        weaponList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedWeaponName = weaponList.getSelectedValue();
                Weapon selectedWeapon = weaponStore.getWeapons().stream()
                        .filter(w -> selectedWeaponName != null && selectedWeaponName.contains(w.getName()))
                        .findFirst()
                        .orElse(null);

                if (selectedWeapon != null) {
                    asciiArtArea.setText(selectedWeapon.asciiArt());
                } else {
                    asciiArtArea.setText("Aucune arme sélectionnée.");
                }
            }
        });

        // Panel for buying a weapon or returning to the game
        JPanel bottomPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding for components

// Weapon input field
        JTextField weaponField = new JTextField(20);
        weaponField.setFont(new Font("Arial", Font.PLAIN, 16));
        weaponField.setBorder(BorderFactory.createLineBorder(new Color(34, 139, 34), 2)); // Green border

// "Buy" button
        JButton buyButton = new JButton("Acheter");
        buyButton.setFont(new Font("Arial", Font.BOLD, 16));
        buyButton.setBackground(new Color(34, 139, 34)); // Forest green background
        buyButton.setForeground(Color.WHITE); // White text
        buyButton.setPreferredSize(new Dimension(120, 40));
        buyButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        buyButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                buyButton.setBackground(new Color(46, 204, 113)); // Lighter green on hover
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                buyButton.setBackground(new Color(34, 139, 34)); // Revert to original green
            }
        });
        buyButton.addActionListener(e -> handleBuyWeapon(weaponField.getText(), weaponList, weaponField));

        JButton backButton = new JButton("Retour au jeu");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setBackground(new Color(70, 130, 180)); // Steel blue background
        backButton.setForeground(Color.WHITE); // White text
        backButton.setPreferredSize(new Dimension(150, 40));
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backButton.setBackground(new Color(100, 149, 237)); // Lighter blue on hover
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                backButton.setBackground(new Color(70, 130, 180)); // Revert to original blue
            }
        });
        backButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "GamePanel");
            SwingUtilities.invokeLater(() -> {
                for (Component comp : mainPanel.getComponents()) {
                    if (comp instanceof GamePanel) {
                        GamePanel gamePanel = (GamePanel) comp;
                        gamePanel.requestFocusInWindow();
                        gamePanel.addKeyListener(gamePanel);
                        break;
                    }
                }
            });
        });


        // Adding components to bottom panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        bottomPanel.add(new JLabel("Nom de l'arme : "), gbc);

        gbc.gridx = 1;
        bottomPanel.add(weaponField, gbc);

        gbc.gridx = 2;
        bottomPanel.add(buyButton, gbc);

        gbc.gridy = 1;
        gbc.gridx = 1;
        bottomPanel.add(backButton, gbc);

    // Add bottom panel to the main layout
        add(bottomPanel, BorderLayout.SOUTH);

    }

    // Méthode pour gérer l'achat d'une arme
    private void handleBuyWeapon(String weaponName, JList<String> weaponList, JTextField weaponField) {
        if (weaponName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer le nom d'une arme.", "Erreur", JOptionPane.ERROR_MESSAGE);
            weaponField.setBackground(Color.PINK);
            Timer timer = new Timer(1000, e -> weaponField.setBackground(Color.WHITE));
            timer.setRepeats(false);
            timer.start();
            return;
        }

        // Acheter l'arme via WeaponStore
        Weapon purchasedWeapon = weaponStore.buyWeapon(weaponName, player);

        if (purchasedWeapon != null) {
            player.addWeaponToInventory(purchasedWeapon);
            player.equipWeapon(purchasedWeapon); // Equip the purchased weapon

            // Notify the GamePanel to update the player info
            for (Component component : mainPanel.getComponents()) {
                if (component instanceof GamePanel) {
                    ((GamePanel) component).updatePlayerInfo();
                    break;
                }
            }
            String asciiArt = purchasedWeapon.asciiArt();
            JOptionPane.showMessageDialog(this,
                    "Vous avez acheté " + purchasedWeapon.getName() + " !\n" +
                            "Dégâts : " + purchasedWeapon.getDamage() + "\n" +
                            "Solde restant : " + player.getMoney() + " pièces.\n\n" +
                            "Arme :\n" + asciiArt,
                    "Achat réussi",
                    JOptionPane.INFORMATION_MESSAGE);

            weaponList.setSelectedValue(purchasedWeapon.toString(), true);
            weaponField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Achat échoué. Vérifiez votre argent ou le nom de l'arme.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        // Mettre à jour la liste des armes
        weaponListModel.clear();
        for (Weapon weapon : weaponStore.getWeapons()) {
            weaponListModel.addElement(weapon.toString());
        }

        // Remettre le focus sur le champ de texte
        weaponField.requestFocusInWindow();
    }

}
