package cours3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements KeyListener {
    private Player player;
    private Dungeon dungeon;
    private JButton[][] mapButtons;
    private JTextArea playerInfoArea; // Zone unique pour les informations du joueur
    private JPanel mainPanel;
    private CardLayout cardLayout;

    private String lastMessage = ""; // Stocke le dernier message à afficher

    private static final Dimension BUTTON_SIZE = new Dimension(60, 60);

    // Icônes redimensionnées pour le jeu
    private final ImageIcon playerIcon = resizeIcon("img/personne.png", 50, 50);
    private final ImageIcon monsterIcon = resizeIcon("img/monstre.png", 50, 50);
    private final ImageIcon obstacleIcon = resizeIcon("img/mur.png", 50, 50);
    private final ImageIcon exitIcon = resizeIcon("img/sortie.png", 50, 50);

    public GamePanel(Player player, Dungeon dungeon, JPanel mainPanel, CardLayout cardLayout) {
        this.player = player;
        this.dungeon = dungeon;
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.mapButtons = new JButton[5][5];

        setLayout(new BorderLayout());

        initializePlayerInfoArea();
        initializeMap();
        initializeControlButtons();

        setFocusable(true);
        addKeyListener(this);
        requestFocusInWindow();
    }

    /**
     * Initialise et configure la zone d'informations du joueur.
     */
    private void initializePlayerInfoArea() {
        playerInfoArea = new JTextArea();
        playerInfoArea.setEditable(false);
        playerInfoArea.setFont(new Font("Serif", Font.BOLD, 16));
        playerInfoArea.setLineWrap(true); // Permet le retour à la ligne automatique
        playerInfoArea.setWrapStyleWord(true); // Coupe les lignes aux mots complets
        playerInfoArea.setBorder(BorderFactory.createTitledBorder("Informations du Joueur"));

        // Ajoutez plus de lignes et colonnes pour une meilleure lisibilité
        playerInfoArea.setRows(15); // Augmente la hauteur de la zone
        playerInfoArea.setColumns(30); // Augmente la largeur de la zone

        // Augmentez la taille préférée du panneau contenant la zone
        JScrollPane infoScrollPane = new JScrollPane(playerInfoArea);
        infoScrollPane.setPreferredSize(new Dimension(400, 300)); // Dimensions augmentées

        add(infoScrollPane, BorderLayout.EAST);
    }


    /**
     * Initialise la carte du donjon.
     */
    private void initializeMap() {
        JPanel mapPanel = new JPanel(new GridLayout(5, 5));
        dungeon.initializeDungeon();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                mapButtons[i][j] = createMapButton();
                mapPanel.add(mapButtons[i][j]);
            }
        }
        updateMap();
        add(mapPanel, BorderLayout.CENTER);
    }

    /**
     * Crée un bouton pour la carte.
     *
     * @return JButton initialisé.
     */
    private JButton createMapButton() {
        JButton button = new JButton(" ");
        button.setEnabled(false); // Désactiver les boutons car ils ne sont pas cliquables
        return button;
    }

    /**
     * Redimensionne une icône à la taille spécifiée.
     *
     * @param path   Chemin de l'image.
     * @param width  Largeur souhaitée.
     * @param height Hauteur souhaitée.
     * @return ImageIcon redimensionnée.
     */
    private ImageIcon resizeIcon(String path, int width, int height) {
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(path));
            Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de l'icône : " + path);
            return new ImageIcon(); // Renvoie une icône vide pour éviter les null
        }
    }

    /**
     * Met à jour l'affichage de la carte du donjon.
     */
    private void updateMap() {
        char[][] map = dungeon.getMap();

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                JButton button = mapButtons[i][j];
                switch (map[i][j]) {
                    case Dungeon.PLAYER -> button.setIcon(playerIcon);
                    case Dungeon.MONSTER -> button.setIcon(monsterIcon);
                    case Dungeon.OBSTACLE -> button.setIcon(obstacleIcon);
                    case Dungeon.EXIT -> button.setIcon(exitIcon);
                    default -> button.setIcon(null);
                }
                button.setText(""); // Supprime tout texte superflu
            }
        }
    }


    /**
     * Gère le déplacement du joueur et met à jour les informations et la carte.
     *
     * @param direction Direction dans laquelle déplacer le joueur.
     */
    private void movePlayer(String direction) {
        String resultMessage = dungeon.movePlayer(direction, player);

        if (resultMessage.contains("Game Over")) {
            lastMessage = resultMessage;
            updatePlayerInfo();
            JOptionPane.showMessageDialog(this, resultMessage, "Fin de la partie", JOptionPane.ERROR_MESSAGE);
            disableControls();
            return;
        }

        if (dungeon.isAtExit(player)) {
            lastMessage = resultMessage;
            updatePlayerInfo();
            JOptionPane.showMessageDialog(this, "Félicitations ! Vous avez terminé le donjon !");
            disableControls();
            return;
        }

        if (!resultMessage.isEmpty()) {
            lastMessage = resultMessage;
        }

        updateMap();
        updatePlayerInfo();
    }

    private void disableControls() {
        // Désactiver tous les boutons de la carte
        for (int i = 0; i < mapButtons.length; i++) {
            for (int j = 0; j < mapButtons[i].length; j++) {
                mapButtons[i][j].setEnabled(false);
            }
        }
        // Désactiver les boutons de contrôle
        setFocusable(false);
    }


    /**
     * Met à jour la zone d'informations du joueur.
     */
    public void updatePlayerInfo() {
        playerInfoArea.setText(String.format(
                "Nom : %s\nClasse : %s\nSanté : %.2f\nMana : %.2f\nArgent : %.2f\nXP : %.2f\nArme : %s\n\n%s",
                player.getName(),
                player.getCharacterClass(),
                player.getHealth(),
                player.getMana(),
                player.getMoney(),
                player.getXP(),
                player.getEquippedWeaponName() != null ? player.getEquippedWeaponName() : "Aucune",
                lastMessage
        ));
    }
    /**
     * Initialise les boutons de contrôle.
     */
    private void initializeControlButtons() {
        JPanel controlPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        addControlButton(controlPanel, "↑", gbc, 1, 0, "haut");
        addControlButton(controlPanel, "←", gbc, 0, 1, "gauche");
        addControlButton(controlPanel, "→", gbc, 2, 1, "droite");
        addControlButton(controlPanel, "↓", gbc, 1, 2, "bas");

        gbc.gridx = 3;
        gbc.gridy = 1;
        JButton storeButton = createStyledButton("Magasin d'arme");
        storeButton.addActionListener(e -> openWeaponStore());
        controlPanel.add(storeButton, gbc);
        storeButton.setPreferredSize(new Dimension(150, 60));
        storeButton.setBackground(new Color(34, 139, 34)); // Vert forêt
        storeButton.setForeground(Color.WHITE);
        storeButton.setFont(new Font("Arial", Font.BOLD, 16));
        add(controlPanel, BorderLayout.SOUTH);
        gbc.gridx = 4;
        gbc.gridy = 1;
        JButton destroyButton = createStyledButton("Détruire l'obstacle");
        destroyButton.setBackground(new Color(255, 69, 0)); // Rouge vif
        destroyButton.setForeground(Color.WHITE); // Texte en blanc
        destroyButton.setPreferredSize(new Dimension(220, 60)); // Taille plus grande
        destroyButton.setFont(new Font("Arial", Font.BOLD, 16)); // Police en gras
        destroyButton.addActionListener(e -> attemptDestroyObstacle());
        controlPanel.add(destroyButton, gbc);


    }

    private void attemptDestroyObstacle() {
        int x = player.getX();
        int y = player.getY();

        // Vérifiez les cases adjacentes pour détecter un obstacle
        boolean destroyed = false;
        if (x > 0 && dungeon.getMap()[x - 1][y] == Dungeon.OBSTACLE) { // Haut
            destroyed = destroyObstacle(x - 1, y);
        } else if (x < dungeon.getMap().length - 1 && dungeon.getMap()[x + 1][y] == Dungeon.OBSTACLE) { // Bas
            destroyed = destroyObstacle(x + 1, y);
        } else if (y > 0 && dungeon.getMap()[x][y - 1] == Dungeon.OBSTACLE) { // Gauche
            destroyed = destroyObstacle(x, y - 1);
        } else if (y < dungeon.getMap()[0].length - 1 && dungeon.getMap()[x][y + 1] == Dungeon.OBSTACLE) { // Droite
            destroyed = destroyObstacle(x, y + 1);
        }

        if (destroyed) {
            updateMap();
            updatePlayerInfo();
        } else {
            JOptionPane.showMessageDialog(this, "Aucun obstacle adjacent ou pas assez de mana.", "Action échouée", JOptionPane.WARNING_MESSAGE);
        }

        // Redonne le focus au panneau principal
        requestFocusInWindow();
    }


    private boolean destroyObstacle(int x, int y) {
        Obstacle obstacle = dungeon.getObstacles()[x][y];
        if (obstacle == null) {
            JOptionPane.showMessageDialog(this, "Aucun obstacle présent ici.", "Action inutile", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        if (!player.useManaToDestroyObstacle(obstacle)) {
            JOptionPane.showMessageDialog(this, "Vous n'avez pas assez de mana pour détruire cet obstacle.", "Action échouée", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        dungeon.getObstacles()[x][y] = null; // Supprime l'obstacle
        dungeon.getMap()[x][y] = Dungeon.EMPTY; // Met à jour la carte
        JOptionPane.showMessageDialog(this, "Obstacle détruit avec succès en utilisant du mana !", "Succès", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }


    /**
     * Ajoute un bouton de contrôle.
     */
    private void addControlButton(JPanel controlPanel, String text, GridBagConstraints gbc, int x, int y, String direction) {
        gbc.gridx = x;
        gbc.gridy = y;
        JButton button = createStyledButton(text);
        button.addActionListener(e -> movePlayer(direction));
        controlPanel.add(button, gbc);
    }

    /**
     * Crée un bouton stylisé.
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(BUTTON_SIZE);
        button.setBackground(Color.DARK_GRAY);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return button;
    }

    /**
     * Ouvre le magasin d'armes.
     */
    private void openWeaponStore() {
        cardLayout.show(mainPanel, "WeaponStorePanel");
        SwingUtilities.invokeLater(() -> {
            removeKeyListener(this);
            mainPanel.requestFocusInWindow();
        });
    }

    @Override
    public void addNotify() {
        super.addNotify();
        requestFocusInWindow();
    }


    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> movePlayer("haut");
            case KeyEvent.VK_DOWN -> movePlayer("bas");
            case KeyEvent.VK_LEFT -> movePlayer("gauche");
            case KeyEvent.VK_RIGHT -> movePlayer("droite");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
