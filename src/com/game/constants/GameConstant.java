package com.game.constants;

public class GameConstant {

    // ===== GAME IDENTIFICATION =====
    public static final String GAME_TITLE = "Top Down Adventure RPG";
    // 📝 Window title bar text → Shows in taskbar + titlebar
    // Change this → Game name changes everywhere!

    public static final String GAME_VERSION = "1.0";
    // 📊 Version number → For debugging, updates, save files

    /**
     * ===== TILE-BASED WORLD SYSTEM =====
     * <p>
     * Your game uses TILE GRID layout (like Zelda, Pokemon)
     * Entire world = MAP_WIDTH x MAP_HEIGHT tiles
     * Each tile = TILE_WIDTH x TILE_HEIGHT pixels
     * <p>
     * EXAMPLE: MAP_WIDTH=15 → 15 tiles horizontally
     * TILE_WIDTH=64 → Each tile = 64px wide
     * GAME_WIDTH=960 → 15×64 = 960px total width
     */

    public static final int TILE_WIDTH = 64;
    // 🧱 Single tile width in pixels → Perfect for sprites!
    // 64px = Standard game tile size (power of 2)

    public static final int TILE_HEIGHT = 64;
    // 🧱 Single tile height → Square tiles (64×64)

    public static final int MAP_WIDTH = 15;
    // ↔️ Number of tiles horizontally → 15 tiles wide screen
    // Small enough for one screen, big enough for gameplay!

    public static final int MAP_HEIGHT = 10;
    // ↕️ Number of tiles vertically → 10 tiles tall screen

    /**
     * ===== FINAL GAME WINDOW SIZE (CALCULATED) =====
     * <p>
     * GamePanel.setPreferredSize() uses these!
     * GameFrame.pack() creates exactly this size window!
     * <p>
     * 15×64 = 960px wide
     * 10×64 = 640px tall
     * <p>
     * Perfect 960×640 window → Classic game resolution!
     */
    public static final int GAME_WIDTH = TILE_WIDTH * MAP_WIDTH;
    // → 64 × 15 = 960 pixels wide → Your game canvas width!

    public static final int GAME_HEIGHT = TILE_HEIGHT * MAP_HEIGHT;
    // → 64 × 10 = 640 pixels tall → Your game canvas height!
}
