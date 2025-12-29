package com.game.window;

import javax.swing.*;

import static com.game.constants.GameConstant.*;

public class GameFrame {

    /**
     * ===== STEP 0: GAME WINDOW CREATOR - The "Canvas" Setup =====
     * <p>
     * This runs FIRST in your entire game! Creates the visible window.
     * Purpose: Build complete Swing window + add GamePanel inside it.
     * <p>
     * COMPLETE FRAMEWORK FLOW:
     * 1. GameFrame constructor ← YOU ARE HERE
     * 2. Creates JFrame (window frame/border/titlebar)
     * 3. Adds GamePanel (game drawing area) inside frame
     * 4. pack() → "Make window exactly fit my contents!"
     * 5. Configure window properties
     * 6. Show window → Game starts!
     */
    private JFrame jframe;

    public GameFrame(GamePanel gamePanel) {
        // 🖼️ STEP 1: CREATE EMPTY WINDOW FRAME
        // JFrame = Window with title bar, borders, close button
        // GAME_TITLE comes from constants (like "My Awesome Game")
        jframe = new JFrame(GAME_TITLE);

        // 🗂️ STEP 2: ADD GAME PANEL INSIDE FRAME
        // GamePanel becomes the "content" of the window
        // BorderLayout automatically fills entire window with panel
        jframe.add(gamePanel);

        // 📐 STEP 3: SIZE WINDOW PERFECTLY (MAGIC HAPPENS HERE!)
        // pack() asks GamePanel: "How big do you want?"
        // GamePanel answers: GAME_WIDTH x GAME_HEIGHT (from setPreferredSize)
        // Frame sizes itself EXACTLY to fit panel + window borders!
        // Without pack(): Window would be tiny/default size
        jframe.pack();

        // 🔒 STEP 4: LOCK WINDOW SIZE
        // Players can't resize → Game canvas stays exact size
        // Prevents rendering issues from changing window size
        jframe.setResizable(false);

        // ❌ STEP 5: HANDLE CLOSE BUTTON
        // When player clicks X → Entire program exits cleanly
        // Alternatives: HIDE_ON_CLOSE (background), DO_NOTHING_ON_CLOSE
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 🎯 STEP 6: CENTER ON SCREEN
        // null = center relative to entire screen
        // Makes game look professional on any monitor size
        jframe.setLocationRelativeTo(null);

        // 👁️ STEP 7: MAKE VISIBLE (GAME STARTS!)
        // Window appears on screen, ready for game loop
        // GamePanel now receives input, paintComponent runs
        jframe.setVisible(true);
    }
}
