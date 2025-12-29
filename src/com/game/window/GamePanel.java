package com.game.window;

import javax.swing.*;
import java.awt.*;

import static com.game.constants.GameConstant.*;

public class GamePanel extends JPanel {

    private GameWorld gameWorld;

    /**
     * ===== STEP 1: CONSTRUCTOR - Panel Initialization =====
     * <p>
     * This runs IMMEDIATELY after GameFrame creates this panel.
     * Purpose: Setup panel properties BEFORE it's added to the frame.
     * <p>
     * FLOW:
     * 1. Store reference to GameWorld (holds all game data/logic)
     * 2. Set panel's "ideal size" → Tells layout manager: "Make me this big!"
     * 3. Make panel focusable → Keyboard/mouse events go DIRECTLY to this panel
     * <p>
     * After this: GameFrame calls frame.pack() → Uses our preferred size!
     */
    public GamePanel(GameWorld gameWorld) {
        this.gameWorld = gameWorld;

        // 📏 Sets the "preferred" size this panel wants to be
        // Layout manager (BorderLayout) asks: "How big do you want?"
        // We answer: GAME_WIDTH x GAME_HEIGHT pixels
        // When frame.pack() runs → Frame sizes itself to fit this exactly!
        setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));

        // ⌨️ Makes this panel receive keyboard/mouse input directly
        // Without this: Input goes to JFrame instead → No game controls!
        setFocusable(true);
    }

    /**
     * ===== STEP 2: RENDERING - Drawing Game Graphics =====
     * <p>
     * Swing calls this AUTOMATICALLY 60+ times/second during game loop!
     * Purpose: Draw ONE complete frame of the game onto screen.
     * <p>
     * COMPLETE PAINT FLOW:
     * 1. Game loop calls panel.repaint() → "Redraw me now!"
     * 2. Swing queues paint job
     * 3. Swing calls paintComponent(g) → WE DRAW HERE
     * 4. Screen updates instantly
     * <p>
     * ⚠️ CRITICAL: Graphics 'g' is temporary → Use it NOW, don't store it!
     */
    @Override
    public void paintComponent(Graphics g) {
        // 🧹 STEP 2a: CLEAR SCREEN (MUST BE FIRST!)
        // Fills panel with background color → Erases old frame
        // Without this: Ghosting, smearing, old sprites linger!
        super.paintComponent(g);

        /**
         * STEP 2b: DRAW GAME WORLD
         * Delegates ALL drawing to GameWorld class
         * GameWorld knows: sprites, player, enemies, UI, effects
         *
         * Why delegate? Separation of concerns:
         * - GamePanel: Swing integration only
         * - GameWorld: Game logic + rendering
         *
         * GameWorld.render(g) will call:
         * g.drawImage(playerSprite, x, y, null);
         * g.fillRect(enemyX, enemyY, 32, 32);
         * g.setColor(Color.WHITE);
         * g.drawString("Score: " + score, 10, 20);
         */
        gameWorld.render(g);
    }
}
