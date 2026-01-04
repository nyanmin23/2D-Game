package com.game.constants;

/**
 * Game configuration constants for window size and tile system.
 */
public class GameConstant {

    // Game metadata
    public static final String GAME_TITLE = "Top Down Adventure RPG";
    public static final String GAME_VERSION = "1.0";

    // Tile system (32x32 tiles, 25x15 map = 800x480 window)
    public static final int TILE_WIDTH = 32;
    public static final int TILE_HEIGHT = 32;
    public static final int MAP_WIDTH = 25;
    public static final int MAP_HEIGHT = 15;

    // Derived window dimensions
    public static final int GAME_WIDTH = TILE_WIDTH * MAP_WIDTH;  // 800px
    public static final int GAME_HEIGHT = TILE_HEIGHT * MAP_HEIGHT; // 480px
}
