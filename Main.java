package cours3;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static final String GAME_TITLE = "RPG Game";
    private static final String ICON_PATH = "img/rpg-game.png";
    private static final Dimension WINDOW_SIZE = new Dimension(1200, 800);

    public static void main(String[] args) {
        // Lancer l'interface utilisateur sur le thread de l'EDT
        SwingUtilities.invokeLater(() -> {
            try {
                initializeGame();
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Erreur lors de l'initialisation du jeu.", e);
                JOptionPane.showMessageDialog(
                        null,
                        "Erreur : Impossible de démarrer l'application.",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }

    /**
     * Initialise et configure la fenêtre principale et les composants du jeu.
     */
    private static void initializeGame() {
        JFrame frame = createMainFrame();

        // Création du gestionnaire de cartes et du panneau principal
        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        // Initialisation des objets du jeu
        Player player = new Player("Joueur", "Sorcier"); // Exemple de joueur
        Dungeon dungeon = new Dungeon(5, 5); // Création du donjon
        WeaponStore weaponStore = new WeaponStore(); // Création du magasin d'armes

        // Initialisation et ajout des panneaux
        initializePanels(mainPanel, cardLayout, player, dungeon, weaponStore);

        // Ajouter le panneau principal au cadre
        frame.add(mainPanel);

        // Afficher le panneau de démarrage
        cardLayout.show(mainPanel, "MenuStartPanel");

        // Afficher la fenêtre
        frame.setVisible(true);
    }


    private static JFrame createMainFrame() {
        JFrame frame = new JFrame(GAME_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WINDOW_SIZE);
        frame.setLocationRelativeTo(null);

        // Ajouter une icône à la fenêtre, si disponible
        try {
            frame.setIconImage(new ImageIcon(ICON_PATH).getImage());
        } catch (Exception e) {
            LOGGER.warning("Icône non trouvée : " + ICON_PATH + ". Erreur : " + e.getMessage());
        }

        return frame;
    }

    /**
     * Initialise les panneaux et les ajoute au gestionnaire de cartes.
     *
     * @param mainPanel     Panneau principal utilisant CardLayout.
     * @param cardLayout    Gestionnaire de cartes pour basculer entre les panneaux.
     * @param player        Instance du joueur.
     * @param dungeon       Instance du donjon.
     * @param weaponStore   Instance du magasin d'armes.
     */
    private static void initializePanels(JPanel mainPanel, CardLayout cardLayout, Player player, Dungeon dungeon, WeaponStore weaponStore) {
        // Création des panneaux
        MenuStartPanel menuStartPanel = new MenuStartPanel(mainPanel, cardLayout, player, weaponStore);
        WeaponStorePanel weaponStorePanel = new WeaponStorePanel(player, weaponStore, mainPanel, cardLayout);
        GamePanel gamePanel = new GamePanel(player, dungeon, mainPanel, cardLayout);

        // Ajout des panneaux au gestionnaire de cartes
        mainPanel.add(menuStartPanel, "MenuStartPanel");
        mainPanel.add(weaponStorePanel, "WeaponStorePanel");
        mainPanel.add(gamePanel, "GamePanel");
    }
}
