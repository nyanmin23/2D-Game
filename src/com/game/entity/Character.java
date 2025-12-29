package com.game.entity;

import java.awt.*;

/**
 * Implements crucial methods for entities
 */
public interface Character {
    void render(Graphics g);
    void update(float deltaTime);
}
