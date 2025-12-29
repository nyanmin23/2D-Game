package com.game.window;

import com.game.input_handler.KeyboardHandler;

public class GameInitializer {

    // ===== GAME ARCHITECTURE - All 5 Core Components =====
    private GameWorld gameWorld;     // 🧠 Game logic, positions, enemies
    private GameFrame gameFrame;     // 🖼️ Visible window + borders
    private GamePanel gamePanel;     // 🎨 Drawing canvas (paintComponent)
    private GameLoop gameLoop;       // ⏰ 60fps update/render thread
    private KeyboardHandler keyboardHandler;  // ⌨️ Arrow key input → movement

    /**
     * ===== GRAND UNIFIED STARTUP - ONE METHOD LAUNCHES EVERYTHING =====
     * <p>
     * This is your game's MAIN METHOD in disguise!
     * Called from: public static void main(String[] args) { new GameInitializer(); }
     * <p>
     * CRITICAL EXECUTION ORDER (change this = game breaks):
     * <p>
     * 1️⃣ GameWorld FIRST → Must exist before anything references it
     * 2️⃣ GamePanel SECOND → Needs world for rendering
     * 3️⃣ GameFrame THIRD → Needs panel for sizing/display
     * 4️⃣ Input FOURTH → Needs world for up()/down() calls
     * 5️⃣ GameLoop LAST → Needs everything running first
     */
    public GameInitializer() {

        // ===== PHASE 1: CORE GAME ENGINE (3 seconds total) =====
        // 🧠 STEP 1: CREATE GAME BRAIN (0.001s)
        // Initializes x=0,y=0,speed=5 → Blue square ready
        gameWorld = new GameWorld();

        // 🎨 STEP 2: CREATE DRAWING SURFACE (0.001s)
        // setPreferredSize(960,640), setFocusable(true)
        // Now ready to receive paintComponent calls
        gamePanel = new GamePanel(gameWorld);

        // 🖼️ STEP 3: CREATE WINDOW (0.5s)
        // jframe.pack() → Uses panel's 960×640 preferredSize!
        // Window appears centered on screen
        gameFrame = new GameFrame(gamePanel);

        // ===== PHASE 2: INPUT SYSTEM (0.001s) =====
        // ⌨️ STEP 4: CONNECT KEYBOARD
        // KeyboardHandler calls gameWorld.up()/down()/left()/right()
        // gamePanel receives arrow key events directly
        keyboardHandler = new KeyboardHandler(gameWorld);
        gamePanel.addKeyListener(keyboardHandler);

        // ===== PHASE 3: GAME LOOP (∞ FOREVER) =====
        // ⏰ STEP 5: CREATE 60FPS ENGINE
        // Separate thread: update(δ) → render() → 16ms repeat
        gameLoop = new GameLoop(gamePanel, gameWorld);

        // 🚀 STEP 6: LAUNCH GAME (separate thread starts instantly)
        // Now: 60fps blue square + arrow key movement = COMPLETE GAME!
        gameLoop.start();
    }
}
