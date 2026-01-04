package com.game.entity;

import com.game.asset_helper.SpriteLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

import static com.game.asset_helper.ActionStore.PlayerAction;
import static com.game.constants.GameConstant.GAME_HEIGHT;
import static com.game.constants.GameConstant.GAME_WIDTH;

/**
 * Player entity with sprite animation, smooth movement, and death states.
 * Handles keyboard input, diagonal speed normalization, and bounds checking.
 */
public class Player implements Character {

    private final float width, height;
    private final float scale = 2.0f;
    // Dependencies
    private final SpriteLoader spriteLoader;
    // Core properties
    private float x, y;
    private float baseSpeed = 200f;
    // Input flags
    private boolean isLeft, isRight, isUp, isDown;
    private boolean isMoving, isFacingLeft;
    // Animation state
    private PlayerAction playerAction = PlayerAction.IDLE_RIGHT;
    private int animationIndex = 0;
    private float animationTimer = 0f;
    // Game state
    private boolean isDead = false;
    private boolean isFinallyDead = false;

    public Player(float x, float y, float width, float height, SpriteLoader spriteLoader) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.spriteLoader = spriteLoader;
    }

    @Override
    public void render(Graphics g) {
        if (isFinallyDead) return;

        BufferedImage[] frames = spriteLoader.getPlayerSprite(playerAction);
        int safeIndex = Math.max(0, Math.min(animationIndex, frames.length - 1));
        g.drawImage(frames[safeIndex], (int) x, (int) y,
                (int) (width * scale), (int) (height * scale), null);
    }

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
     * Applies input-based movement with diagonal normalization.
     */
    private void move(float deltaTime) {
        isMoving = false;
        float speed = diagonalMovement() ? (float) (baseSpeed / Math.sqrt(2)) : baseSpeed;

        if (isLeft && !isRight) {
            x -= speed * deltaTime;
            isFacingLeft = true;
            isMoving = true;
        } else if (isRight && !isLeft) {
            x += speed * deltaTime;
            isFacingLeft = false;
            isMoving = true;
        }

        if (isUp && !isDown) {
            y -= speed * deltaTime;
            isMoving = true;
        } else if (isDown && !isUp) {
            y += speed * deltaTime;
            isMoving = true;
        }

        keepInBounds();
    }

    /**
     * Returns true if moving diagonally.
     */
    private boolean diagonalMovement() {
        return (isLeft && isUp) || (isLeft && isDown) ||
                (isRight && isUp) || (isRight && isDown);
    }

    /**
     * Sets animation action based on input or death state.
     */
    private void updatePlayerAction() {
        if (isDead) {
            changePlayerAction(PlayerAction.DIE);
            return;
        }

        if (isDown) changePlayerAction(PlayerAction.WALK_DOWN);
        else if (isUp) changePlayerAction(PlayerAction.WALK_UP);
        else if (isLeft) changePlayerAction(PlayerAction.WALK_LEFT);
        else if (isRight) changePlayerAction(PlayerAction.WALK_RIGHT);
        else playerAction = isFacingLeft ? PlayerAction.IDLE_LEFT : PlayerAction.IDLE_RIGHT;
    }

    /**
     * Advances animation frame using delta time.
     */
    private void animatePlayer(float deltaTime) {
        animationTimer += deltaTime;
        float frameDuration = 1f / playerAction.getFrameRate();

        if (animationTimer >= frameDuration) {
            animationIndex++;

            if (isDead && animationIndex >= playerAction.getFrameCount()) {
                animationIndex = playerAction.getFrameCount() - 1;
                isFinallyDead = true;
            } else if (animationIndex >= playerAction.getFrameCount()) {
                animationIndex = 0;
            }

            animationTimer = 0f;
        }
    }

    /**
     * Switches animation action and resets frame.
     */
    private void changePlayerAction(PlayerAction newAction) {
        if (playerAction == newAction) return;
        playerAction = newAction;
        animationIndex = 0;
        animationTimer = 0f;
    }

    /**
     * Clamps player to screen bounds.
     */
    private void keepInBounds() {
        x = Math.max(0, Math.min(GAME_WIDTH - width * scale, x));
        y = Math.max(0, Math.min(GAME_HEIGHT - height * scale, y));
    }

    // Input handlers
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
