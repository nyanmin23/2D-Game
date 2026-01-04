package com.game.asset_helper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

import static com.game.asset_helper.ActionStore.PlayerAction;

/**
 * Loads and slices sprite sheets for player animations and map tiles.
 * Provides access to animation frames and individual map sprites.
 */
public class SpriteLoader {

    // Paths and dimensions
    private static final String BASE_PATH = "resources/img";
    private static final String PLAYER_IMG_PATH = "/player/Player";
    private static final String ENV_IMG_PATH = "/map/Env";
    private static final String IMG_EXTENSION = ".png";

    private static final int FRAME_WIDTH = 32, FRAME_HEIGHT = 32;
    private static final int MAP_FRAME_WIDTH = 16, MAP_FRAME_HEIGHT = 16;

    // Loaded sprite data
    public final Map<PlayerAction, BufferedImage[]> playerSprites =
            new EnumMap<>(PlayerAction.class);
    public final BufferedImage[] map = new BufferedImage[234];

    /**
     * Loads all player animations and map tiles from sprite sheets.
     */
    public SpriteLoader() {
        loadPlayerSprite();
        loadMapSprite();
    }

    /**
     * Slices player sprite sheet into animation arrays by row.
     */
    private void loadPlayerSprite() {
        BufferedImage sheet = loadImage(BASE_PATH + PLAYER_IMG_PATH + IMG_EXTENSION);

        for (PlayerAction action : PlayerAction.values()) {
            playerSprites.put(action, slice(sheet,
                    action.getFrameCount(), action.ordinal(),
                    FRAME_WIDTH, FRAME_HEIGHT));
        }

        System.out.println("Loaded " + playerSprites.size() + " player animations");
    }

    /**
     * Extracts all map tiles from environment sprite sheet into flat array.
     */
    private void loadMapSprite() {
        BufferedImage sheet = loadImage(BASE_PATH + ENV_IMG_PATH + IMG_EXTENSION);

        int cols = sheet.getWidth() / MAP_FRAME_WIDTH;
        int rows = sheet.getHeight() / MAP_FRAME_HEIGHT;

        int index = 0;
        for (int row = 0; row < rows && index < map.length; row++) {
            for (int col = 0; col < cols && index < map.length; col++) {
                map[index++] = sheet.getSubimage(
                        col * MAP_FRAME_WIDTH,
                        row * MAP_FRAME_HEIGHT,
                        MAP_FRAME_WIDTH,
                        MAP_FRAME_HEIGHT
                );
            }
        }
    }

    /**
     * Loads image file as BufferedImage.
     *
     * @throws RuntimeException if file missing
     */
    private BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load: " + path, e);
        }
    }

    /**
     * Slices horizontal animation strip from sprite sheet row.
     */
    private BufferedImage[] slice(BufferedImage sheet, int frameCount, int row,
                                  int frameWidth, int frameHeight) {
        BufferedImage[] frames = new BufferedImage[frameCount];

        for (int col = 0; col < frameCount; col++) {
            frames[col] = sheet.getSubimage(
                    col * frameWidth,
                    row * frameHeight,
                    frameWidth,
                    frameHeight
            );
        }
        return frames;
    }

    /**
     * Returns animation frames for player action.
     */
    public BufferedImage[] getPlayerSprite(PlayerAction action) {
        return playerSprites.get(action);
    }

    /**
     * Returns map tile by flat index.
     */
    public BufferedImage getMapSpriteByIndex(int index) {
        return map[index];
    }
}
