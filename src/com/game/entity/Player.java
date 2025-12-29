package com.game.entity;

import com.game.asset_helper.ActionStore;
import com.game.asset_helper.AnimationStore;
import com.game.asset_helper.SpriteLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player implements Character {

    /**
     * ===== PLAYER STATE VARIABLES =====
     */
    int animationCount = 0;  // Frame counter for sprite animation (0-40)
    int i = 0;               // Current sprite frame index (0-3 for 4-frame animation)

    private Color color = Color.RED;              // Fallback color (when no sprite)
    private float x, y, width, height;            // Position + size (float = smooth movement)
    private float movementSpeed = 100;            // Pixels per SECOND (frame-rate independent)

    // ===== INPUT STATE - Which directions pressed? =====
    private boolean isLeft, isRight, isUp, isDown;  // True when key held down

    private boolean isMoving = false;              // Currently moving? (unused currently)
    private boolean isFacingLeft = true;           // Which way player faces (for flipping)
    private boolean isDead = false;                // Game over state

    private float scale = 2.0f;                    // Sprite size multiplier (2x bigger)
    private SpriteLoader spriteLoader;             // Loads sprite sheets/animations

    /**
     * ===== CONSTRUCTOR - Create Player =====
     * Sets starting position + size + sprite loader reference
     */
    public Player(float x, float y, float width, float height, SpriteLoader spriteLoader) {
        this.spriteLoader = spriteLoader;
        this.color = Color.RED;
        this.x = x;        // Starting X position
        this.y = y;        // Starting Y position
        this.width = width; // Base width (before scaling)
        this.height = height; // Base height (before scaling)
    }

    /**
     * ===== RENDER - Draw Player (60fps) =====
     * Called by GameWorld.render() → Draws sprite + animation
     * <p>
     * DRAWING LAYERS:
     * 1. Red rectangle (fallback/debug)
     * 2. Animated sprite ON TOP (IDLE_LEFT animation)
     */
    @Override
    public void render(Graphics g) {
        // 🟥 LAYER 1: DEBUG RECTANGLE (always draws behind sprite)
        g.setColor(color);
        g.fillRect((int) x, (int) y, (int) width, (int) height);

        // 🖼️ LAYER 2: ANIMATED SPRITE (main visual)
        // Gets 4-frame IDLE_LEFT animation array from sprite sheet
        BufferedImage[] image = spriteLoader.getPlayerSprite(ActionStore.PlayerAction.IDLE_LEFT);
        // Draws current frame 'i' at 2x scale
        g.drawImage(image[i], (int) x, (int) y, (int) (width * scale), (int) (height * scale), null);

        // ===== ANIMATION LOGIC (40 frames per cycle) =====
        // Counts frames → Switches sprite every 40 frames (~0.67s per cycle at 60fps)
        if (animationCount > 40) {
            if (i > 3) {  // End of 4-frame animation (0,1,2,3)
                i = 0;    // Loop back to first frame
            } else {
                i++;      // Next frame
            }
            animationCount = 0;  // Reset counter
        } else {
            animationCount++;    // Increment frame counter
        }
    }

    /**
     * ===== UPDATE - Game Logic (60fps) =====
     * Called by GameWorld.update() → Handles movement + physics
     */
    @Override
    public void update(float deltaTime) {
        move(deltaTime);  // Apply movement based on input state
    }

    /**
     * ===== MOVEMENT SYSTEM - Frame-rate Independent =====
     * <p>
     * CORE FORMULA: distance = speed × time
     * movementSpeed (100) × deltaTime (0.016s) = ~1.6px per frame
     * <p>
     * DIAGONAL FIX NEEDED: Currently no normalization → Diagonal = √2× faster!
     * TODO: Use vector normalization for perfect 45° movement
     */
    public void move(float deltaTime) {
        // ↔️ HORIZONTAL MOVEMENT (Left/Right priority: Left wins)
        if (isLeft && !isRight) {
            x -= movementSpeed * deltaTime;  // Move LEFT
        } else if (isRight && !isLeft) {
            x += movementSpeed * deltaTime;  // Move RIGHT
        }

        // ↕️ VERTICAL MOVEMENT (Up/Down priority: Up wins)
        if (isUp && !isDown) {
            y -= movementSpeed * deltaTime;  // Move UP (Y=0 is TOP)
        } else if (isDown && !isUp) {
            y += movementSpeed * deltaTime;  // Move DOWN
        }
    }

    /**
     * ===== INPUT SETTERS - Called by KeyboardHandler (60fps) =====
     * Set movement flags when keys pressed/released
     * KeyboardHandler calls these continuously while keys held!
     */
    public void setLeft(boolean left) {
        this.isLeft = left;
    }

    public void setRight(boolean right) {
        this.isRight = right;
    }

    public void setUp(boolean up) {
        this.isUp = up;
    }

    public void setDown(boolean down) {
        this.isDown = down;
    }

    /**
     * ===== UTILITY METHODS =====
     */
    public void stopMoving() {
        this.isMoving = false;  // Currently unused
    }
}
