package com.game.entity;

import com.game.asset_helper.SpriteLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

import static com.game.asset_helper.ActionStore.PlayerAction;
import static com.game.constants.GameConstant.GAME_HEIGHT;
import static com.game.constants.GameConstant.GAME_WIDTH;

/**
 * Player entity with sprite animation, movement, and death states.
 * Handles input, animation timing, and screen bounds.
 */
public class Player implements Character {

    private final float width, height; // Sprite dimensions
    private final float scale = 2.0f;  // Render scale
    // Dependencies
    private final SpriteLoader spriteLoader;
    private final Color color;
    // Core properties
    private float x, y;                // World position
    private float movementSpeed = 200f; // Pixels/second
    // Input state
    private boolean isLeft, isRight, isUp, isDown;
    private boolean isMoving;
    private boolean isFacingLeft;
    // Animation state
    private PlayerAction playerAction = PlayerAction.IDLE_RIGHT;
    private int animationIndex = 0;
    private float animationTimer = 0f;
    // Game state
    private boolean isDead = false;
    private boolean isFinallyDead = false;

    /**
     * Creates player at position with sprite loader.
     */
    public Player(float x, float y, float width, float height, SpriteLoader spriteLoader) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.spriteLoader = spriteLoader;
        this.color = Color.WHITE;
        this.animationIndex = 0;
        this.animationTimer = 0f;
    }

    /**
     * Renders player sprite with current animation frame.
     */
    @Override
    public void render(Graphics g) {
        if (isFinallyDead) return;

        // Draw animated sprite (safely clamped index)
        BufferedImage[] frames = spriteLoader.getPlayerSprite(playerAction);
        int safeIndex = Math.max(0, Math.min(animationIndex, frames.length - 1));
        g.drawImage(
                frames[safeIndex],
                (int) x, (int) y,
                (int) (width * scale), (int) (height * scale),
                null
        );
    }

    /**
     * Updates movement, action state, and animation each frame.
     */
    @Override
    public void update(float deltaTime) {
        updatePlayerAction();

        if (isDead) {
            animatePlayer(deltaTime);
            return;
        }
        move(deltaTime);
        animatePlayer(deltaTime);

    }

    /**
     * Updates movement based on input flags.
     */
    private void move(float deltaTime) {
        isMoving = false;

        // Apply movement (horizontal first)
        if (isLeft && !isRight) {
            x -= movementSpeed * deltaTime;
            isFacingLeft = true;
            isMoving = true;
        } else if (isRight && !isLeft) {
            x += movementSpeed * deltaTime;
            isFacingLeft = false;
            isMoving = true;
        }

        // Vertical movement
        if (isUp && !isDown) {
            y -= movementSpeed * deltaTime;
            isMoving = true;
        } else if (isDown && !isUp) {
            y += movementSpeed * deltaTime;
            isMoving = true;
        }

        keepInBounds();
    }

    /**
     * Updates current animation action based on input.
     */
    private void updatePlayerAction() {
        if (isDead) {
            changePlayerAction(PlayerAction.DIE);
            return;
        }

        if (isDown) {
            changePlayerAction(PlayerAction.WALK_DOWN);
        } else if (isUp) {
            changePlayerAction(PlayerAction.WALK_UP);
        } else if (isLeft) {
            changePlayerAction(PlayerAction.WALK_LEFT);
        } else if (isRight) {
            changePlayerAction(PlayerAction.WALK_RIGHT);
        } else {
            // Idle facing current direction
            playerAction = isFacingLeft ? PlayerAction.IDLE_LEFT : PlayerAction.IDLE_RIGHT;
        }
    }

    /**
     * Advances animation frame using delta time and action frame rate.
     */
    private void animatePlayer(float deltaTime) {
        animationTimer += deltaTime;
        float frameDuration = 1f / playerAction.getFrameRate();

        if (animationTimer >= frameDuration) {
            animationIndex++;

            // Death animation complete check
            if (isDead && animationIndex >= playerAction.getFrameCount()) {
                animationIndex = playerAction.getFrameCount() - 1;
                isFinallyDead = true;
            } else if (animationIndex >= playerAction.getFrameCount()) {
                animationIndex = 0; // Loop
            }

            animationTimer = 0f;
        }
    }

    /**
     * Changes animation action and resets frame/timer.
     */
    private void changePlayerAction(PlayerAction newAction) {
        if (playerAction == newAction) return;

        playerAction = newAction;
        animationIndex = 0;
        animationTimer = 0f;
    }

    /**
     * Clamps position to screen bounds.
     */
    private void keepInBounds() {
        x = Math.max(0, Math.min(GAME_WIDTH - width * scale, x));
        y = Math.max(0, Math.min(GAME_HEIGHT - height * scale, y));
    }

    // Input setters (called by KeyboardHandler)
    public void setLeft(boolean left) {
        isLeft = left;
    }

    public void setRight(boolean right) {
        isRight = right;
    }

    public void setUp(boolean up) {
        isUp = up;
    }

    public void setDown(boolean down) {
        isDown = down;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }
}
