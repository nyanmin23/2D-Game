package com.game.window;

import com.game.asset_helper.SpriteLoader;
import com.game.entity.Player;
import com.game.world.Map;

import java.awt.*;

/**
 * Central game world containing player, map, and sprite loader.
 * Coordinates update/render calls from GameLoop/GamePanel.
 */
public class GameWorld {

    private SpriteLoader spriteLoader;
    // Game objects
    private Player player;
    private Map map;

    /**
     * Initializes all game objects and loads sprites.
     */
    public GameWorld() {
        init();
    }

    /**
     * Creates sprite loader, map, and player.
     */
    private void init() {
        spriteLoader = new SpriteLoader();
        map = new Map(spriteLoader);
        player = new Player(10, 10, 32, 32, spriteLoader);
    }

    /**
     * Renders map then player (back-to-front).
     */
    public void render(Graphics g) {
        map.render(g);
        player.render(g);
    }

    /**
     * Updates player logic each frame.
     */
    public void update(float deltaTime) {
        player.update(deltaTime);
    }

    /**
     * Returns player reference for input handling.
     */
    public Player getPlayer() {
        return player;
    }
}
