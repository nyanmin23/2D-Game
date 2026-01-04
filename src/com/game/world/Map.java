package com.game.world;

import com.game.asset_helper.SpriteLoader;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Tilemap renderer for background and grass layers.
 * Loads map data from CSV files and renders scaled sprites.
 */
public class Map {

    // Map configuration
    private static final String MAP_BASE_PATH = "/map_resources/map/";
    private static final String MAP_EXTENSION = ".txt";
    private static final float SCALE = 2.0f;
    private static final float MAP_SPRITE_WIDTH = 16 * SCALE;
    private static final float MAP_SPRITE_HEIGHT = 16 * SCALE;
    // Dependencies
    private final SpriteLoader spriteLoader;
    // Map data
    int mapNumber = 1;
    int[][] background;
    int[][] grass;

    /**
     * Loads map data for current map number.
     */
    public Map(SpriteLoader spriteLoader) {
        this.spriteLoader = spriteLoader;
        background = loadMapData("background");
        grass = loadMapData("grass");
    }

    /**
     * Loads CSV map data into 20x30 tile grid.
     * Returns null tile (-1) for empty spaces.
     */
    private int[][] loadMapData(String mapName) {
        String fileName = "map" + mapNumber + "_" + mapName + MAP_EXTENSION;
        InputStream inputStream = getClass().getResourceAsStream(MAP_BASE_PATH + fileName);

        if (inputStream == null) {
            throw new RuntimeException("File not found: " + MAP_BASE_PATH + fileName);
        }

        int[][] map = new int[20][30];
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            int row = 0;
            String line;
            while ((line = reader.readLine()) != null && row < 20) {
                String[] values = line.split(",");
                for (int col = 0; col < Math.min(values.length, 30); col++) {
                    map[row][col] = Integer.parseInt(values[col].trim());
                }
                row++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * Renders background then grass layers tile by tile.
     * Skips empty tiles (index < 0).
     */
    public void render(Graphics g) {
        renderLayer(g, background);
        renderLayer(g, grass);
    }

    /**
     * Renders single map layer (background or grass).
     */
    private void renderLayer(Graphics g, int[][] layer) {
        for (int j = 0; j < layer.length; j++) {
            for (int i = 0; i < layer[j].length; i++) {
                int index = layer[j][i];
                if (index < 0) continue; // Skip empty tiles

                g.drawImage(
                        spriteLoader.getMapSpriteByIndex(index),
                        (int) (i * MAP_SPRITE_WIDTH),
                        (int) (j * MAP_SPRITE_HEIGHT),
                        (int) MAP_SPRITE_WIDTH,
                        (int) MAP_SPRITE_HEIGHT,
                        null
                );
            }
        }
    }
}
