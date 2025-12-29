package com.game.asset_helper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

public class SpriteLoader {

    // ===== SPRITE SHEET CONFIGURATION =====
    private static final String BASE_PATH = "resources/img";              // Folder containing sprites
    private static final String PLAYER_IMG_PATH = "/player/Player";       // Player sprite sheet name
    private static final String IMG_EXTENSION = ".png";                   // File format

    private static final int FRAME_WIDTH = 32;   // Each sprite frame = 32px wide
    private static final int FRAME_HEIGHT = 32;  // Each sprite frame = 32px tall

    // ===== MAIN DATA STORAGE - All Player Animations =====
    // EnumMap: One array per PlayerAction (IDLE_LEFT, WALK_RIGHT, etc.)
    public final Map<ActionStore.PlayerAction, BufferedImage[]> playerSprites =
            new EnumMap<>(ActionStore.PlayerAction.class);

    /**
     * ===== CONSTRUCTOR - Load ALL Sprites At Startup =====
     * Runs ONCE when first Player created → Loads entire sprite sheet
     * Populates playerSprites map with all animations
     */
    public SpriteLoader() {
        loadPlayerSprite();  // Load everything!
    }

    /**
     * ===== STEP 1: LOAD SPRITE SHEET - Single Massive Image =====
     * <p>
     * Loads "resources/img/player/Player.png"
     * Sprite sheet layout:
     * [IDLE_LEFT][WALK_LEFT][RUN_LEFT]  ← Row 0
     * [IDLE_RIGHT][WALK_RIGHT][RUN_RIGHT] ← Row 1
     * etc...
     */
    private void loadPlayerSprite() {
        // 📁 Load complete sprite sheet (ONE huge image)
        BufferedImage sheet = loadImage(BASE_PATH + PLAYER_IMG_PATH + IMG_EXTENSION);

        // 🔪 Slice sheet into animations (one per PlayerAction)
        for (ActionStore.PlayerAction action : ActionStore.PlayerAction.values()) {
            // Example: IDLE_LEFT = row 0, 4 frames → slices columns 0,1,2,3 from row 0
            playerSprites.put(action,
                    slice(sheet, action.getFrameCount(), action.ordinal(), FRAME_WIDTH, FRAME_HEIGHT));
        }

        System.out.println("Loaded " + playerSprites.size() + " sprites");  // Debug: "Loaded 8 sprites"
    }

    /**
     * ===== UTILITY: Load Single Image File =====
     * Converts file → BufferedImage → Ready for slicing
     * Throws exception if file missing → Game crashes with clear error
     */
    private BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(new File(path));  // File → Image
        } catch (IOException e) {
            throw new RuntimeException("Failed to load image " + path, e);
            // Example error: "Failed to load image resources/img/player/Player.png"
        }
    }

    /**
     * ===== STEP 2: SPRITE SHEET SLICER - Extract Animation Frames =====
     * <p>
     * CORE LOGIC: Sprite sheets pack animations horizontally by rows
     * <p>
     * EXAMPLE: slice(sheet, 4, 0, 32, 32) for IDLE_LEFT:
     * Frame 0: getSubimage(0, 0, 32, 32)   ← Column 0, Row 0
     * Frame 1: getSubimage(32, 0, 32, 32)  ← Column 1, Row 0
     * Frame 2: getSubimage(64, 0, 32, 32)  ← Column 2, Row 0
     * Frame 3: getSubimage(96, 0, 32, 32)  ← Column 3, Row 0
     */
    private BufferedImage[] slice(BufferedImage sheet, int column, int row, int frameWidth, int frameHeight) {
        BufferedImage[] frames = new BufferedImage[column];  // Array size = frame count

        // 🔪 Extract each frame horizontally
        for (int i = 0; i < column; i++) {
            // Formula: X = frameWidth * column_index, Y = row * frameHeight
            frames[i] = sheet.getSubimage(
                    frameWidth * i,      // X position in sheet
                    row * frameHeight,   // Y position (which row)
                    frameWidth,          // Width of one frame
                    frameHeight          // Height of one frame
            );
        }
        return frames;  // Return animation array [frame0, frame1, frame2, frame3]
    }

    /**
     * ===== PUBLIC ACCESSOR - Get Animation Array =====
     * Called by Player.render(): spriteLoader.getPlayerSprite(IDLE_LEFT)
     * Returns: BufferedImage[4] = {frame0, frame1, frame2, frame3}
     */
    public BufferedImage[] getPlayerSprite(ActionStore.PlayerAction action) {
        return playerSprites.get(action);  // Lookup animation → Return frame array
    }
}
