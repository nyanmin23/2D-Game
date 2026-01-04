package com.game.asset_helper;

public class ActionStore {

    /**
     * ===== PLAYER ANIMATION DATABASE =====
     * Defines ALL possible player animations + their properties
     * <p>
     * FORMAT: ActionName(FRAME_COUNT, FRAMES_PER_SECOND)
     * <p>
     * Each enum = ONE ROW in sprite sheet
     * FRAME_COUNT = How many frames horizontally in that row
     * FRAMES_PER_SECOND = Animation speed (0.5f = 2 seconds per cycle)
     */
    public enum PlayerAction {
        // 🧍 IDLE ANIMATIONS (standing still)
        IDLE_LEFT(5, 15.5f),    // 5 frames, 0.5fps = 10 seconds per cycle (slow breathing)
        IDLE_RIGHT(5, 15.5f),   // Mirror image of left

        // 🚶 WALK ANIMATIONS (normal movement)
        WALK_LEFT(4, 10.5f),    // 4 frames, 0.5fps = 8 seconds per cycle
        WALK_RIGHT(4, 10.5f),   // Mirror image

        // ⬇️⬆️ DIRECTIONAL WALK (facing screen)
        WALK_DOWN(8, 10.5f),    // 8 frames = More detailed front view
        WALK_UP(8, 10.5f),      // 8 frames = Back view

        // 💥 COMBAT ANIMATIONS
        HURT(2, 40.3f),         // 2 frames, 0.3fps = Fast flinch (3.3s cycle)
        DIE(10, 15.5f);         // 10 frames = Dramatic death sequence

        private final int frameCount;  // How many sprites in this animation
        private final float frameRate; // Speed multiplier (frames per second)

        /**
         * ===== ENUM CONSTRUCTOR - Set Animation Properties =====
         * Called automatically when enum values defined above
         */
        PlayerAction(int frameCount, float frameRate) {
            this.frameCount = frameCount;  // Passed to SpriteLoader.slice()
            this.frameRate = frameRate;    // For future Player animation timing
        }

        /**
         * ===== SPRITE LOADER ACCESSOR =====
         * spriteLoader.slice(sheet, action.getFrameCount(), action.ordinal(), ...)
         * Example: IDLE_LEFT.getFrameCount() → Returns 5
         */
        public int getFrameCount() {
            return frameCount;
        }

        /**
         * ===== FUTURE ANIMATION TIMING =====
         * Player.render(): if(animationTimer > 1f/action.getFrameRate())
         * Example: WALK_LEFT.getFrameRate() → 0.5f → Next frame every 2 seconds
         */
        public float getFrameRate() {
            return frameRate;
        }
    }
}
