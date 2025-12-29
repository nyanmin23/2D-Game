package com.game.window;

import com.game.asset_helper.SpriteLoader;
import com.game.entity.Player;

import java.awt.*;

public class GameWorld {

    // ===== GAME OBJECTS - Core entities =====
    private Player player;           // Main character (player controlled)
    private SpriteLoader spriteLoader;  // Loads all sprite animations

    /**
     * ===== STEP 0: CONSTRUCTOR - Initialize Game World =====
     * <p>
     * Runs ONCE when GameWorld created (GameInitializer step 1).
     * Purpose: Setup complete game state before window appears.
     * <p>
     * FLOW: Constructor → init() → Sprites loaded + Player spawned
     */
    public GameWorld() {
        init();  // Immediately setup player + sprites
    }

    /**
     * ===== STEP 0a: SETUP GAME OBJECTS =====
     * Called by constructor → Creates player + loads sprites
     * <p>
     * SPRITE LOADING FLOW:
     * 1. spriteLoader = new SpriteLoader() → Loads Player.png
     * 2. Slices sprite sheet → IDLE_LEFT[5], WALK_RIGHT[4], etc.
     * 3. player = new Player(10,10,32,32,spriteLoader) → Ready at top-left
     */
    public void init() {
        spriteLoader = new SpriteLoader();  // Loads ALL animations from Player.png
        player = new Player(10, 10, 32, 32, spriteLoader);  // Spawn at (10,10)
    }

    /**
     * ===== STEP 1: RENDER - Draw Everything (60fps) =====
     * Called by GamePanel.paintComponent() → 60x/second!
     * <p>
     * CURRENT RENDER ORDER:
     * 1. Player sprite + animation (IDLE_LEFT currently)
     * <p>
     * FLOW: GameLoop → paintImmediately → paintComponent → THIS METHOD
     */
    public void render(Graphics g) {
        // 🧍 DRAW PLAYER (ONLY for now)
        // Player.render(g) draws:
        // - Red debug rect (32×32)
        // - Animated IDLE_LEFT sprite (64×64 at 2x scale)
        // - Cycles frames every 40 frames (~0.67s)
        player.render(g);
    }

    /**
     * ===== STEP 2: UPDATE - Game Logic (60fps) =====
     * Called by GameLoop.update() → Every frame!
     * <p>
     * CURRENT UPDATE LOGIC:
     * 1. player.update(deltaTime) → Handles movement if keys pressed
     * <p>
     * FLOW: GameLoop → update(delta) → player.move(delta) → Next render shows new position
     */
    public void update(float deltaTime) {
        // 🚶 UPDATE PLAYER MOVEMENT
        // If KeyboardHandler set isLeft=true → player.move() changes x,y smoothly
        player.update(deltaTime);
    }

    /**
     * ===== ACCESSOR - For KeyboardHandler =====
     * KeyboardHandler needs player reference to call setLeft(), setRight(), etc.
     */
    public Player getPlayer() {
        return player;
    }

    // ===== FUTURE EXPANSION ROADMAP =====
    // TODO: Add Enemy → Red squares that chase player
    // TODO: Add Enemy Object → Enemy class with AI, health, collision
    // TODO: Add Trees → Static green trees for background/collision
}
