package com.game.window;

import javax.swing.*;
import java.awt.*;

import static com.game.constants.GameConstant.GAME_HEIGHT;
import static com.game.constants.GameConstant.GAME_WIDTH;

/**
 * GamePanel serves as the Swing canvas for rendering the game world.
 * Handles preferred sizing and custom painting via paintComponent.
 */
public class GamePanel extends JPanel {

    private final GameWorld gameWorld;

    /**
     * Initializes panel with game world reference, size, and input focus.
     * Called during GameInitializer before adding to GameFrame.
     */
    public GamePanel(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        setBackground(new Color(106, 55, 55)); // Earth tone
        setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        setFocusable(true);
    }

    /**
     * Custom paint method called by Swing ~60fps during game loop.
     * Clears background then delegates rendering to GameWorld.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Clear to background color (essential!)
        gameWorld.render(g);     // Draw player, enemies, UI, etc.
    }
}
