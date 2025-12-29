package com.game.window;

public class GameLoop implements Runnable {

    // ===== GAME REFERENCES - Connect everything together =====
    private GamePanel gamePanel;    // Draws graphics (calls paintComponent)
    private GameWorld gameWorld;    // Game logic (positions, enemies, physics)
    private Thread gameThread;      // Separate thread for 60fps game loop

    // 🔒 THREAD-SAFE GAME STATE
    private volatile boolean running;
    // 'volatile': Ensures ALL threads see the SAME value instantly
    // Without it: One thread sees true, another sees false → Chaos!

    /**
     * ===== CONSTRUCTOR - Wire up Game Components =====
     * Runs ONCE during game startup.
     * Stores references to panel/world for later use in game loop.
     */
    public GameLoop(GamePanel gamePanel, GameWorld gameWorld) {
        this.gamePanel = gamePanel;
        this.gameWorld = gameWorld;
    }

    /**
     * ===== STEP 1: START GAME LOOP =====
     * Called from GameInitializer after window shows.
     * <p>
     * 'synchronized': Only ONE thread can call this at once
     * Purpose: Prevent multiple game threads from starting accidentally
     * <p>
     * FLOW:
     * 1. Check if already running → Skip if true
     * 2. Create dedicated GameLoop thread
     * 3. Start thread → run() method begins 60fps loop!
     */
    public synchronized void start() {
        if (running) {
            return;  // Already running → Do nothing
        }

        /**
         * GameLoop implements Runnable → Can be passed to Thread constructor
         * 'this' = Current GameLoop object = Runnable target
         * Thread will call this.run() when started
         */
        running = true;
        gameThread = new Thread(this); // this: GameLoop object
        gameThread.start();            // Launches separate game thread!
    }

    /**
     * ===== STEP 2: STOP GAME LOOP =====
     * Called when game closes (X button, ESC, etc.)
     * <p>
     * FLOW:
     * 1. Set running = false → Game loop will exit naturally
     * 2. join() → MAIN THREAD WAITS for game thread to finish
     * 3. Clean exit → No zombie threads hanging around
     */
    public synchronized void stop() {
        if (!running) {
            return;
        }

        running = false;  // Signal game loop to exit
        try {
            gameThread.join();  // Wait for game thread to finish
        } catch (InterruptedException e) {
            throw new RuntimeException(e);  // Rethrow as unchecked
        }
    }

    /**
     * ===== STEP 3: MAIN GAME LOOP (60fps) - HEART OF THE GAME =====
     * Runs in SEPARATE THREAD → Doesn't freeze UI window!
     * <p>
     * COMPLETE 60FPS CYCLE (16ms per frame):
     * 1. Calculate deltaTime (real time since last frame)
     * 2. update(delta) → Move enemies, check collisions
     * 3. render() → Draw new frame
     * 4. Count FPS → Print every second
     * 5. Repeat forever until running=false
     */
    @Override
    public void run() {
        // 🕐 PRECISE TIMING SETUP
        long lastTime = System.nanoTime();     // Last frame time (nanoseconds)
        long timer = System.currentTimeMillis(); // FPS timer (milliseconds)
        int frames = 0;                        // Frames this second

        while (running) {  // Main game loop!
            // ⏱️ CALCULATE DELTATIME (frame-rate independent movement)
            long now = System.nanoTime();
            float delta = (now - lastTime) / 1_000_000_000.0f; // Seconds
            lastTime = now;

            // 🔄 STEP 3a: UPDATE GAME LOGIC
            update(delta);  // Move enemies, player physics, collisions

            // 🖼️ STEP 3b: RENDER NEW FRAME
            render();       // Draw everything at new positions

            // 📊 STEP 3c: FPS COUNTER (prints every second)
            frames++;
            if (System.currentTimeMillis() - timer > 1_000) {
                System.out.println("FPS " + frames);  // ~60fps target!
                frames = 0;
                timer = System.currentTimeMillis();
            }
        }
    }

    /**
     * ===== UPDATE - Game Logic (Called 60fps) =====
     * Updates ALL game objects using real-time delta.
     * Delegates to GameWorld → Clean separation!
     */
    public void update(float delta) {
        gameWorld.update(delta);  // Enemies chase, spawning, collisions
    }

    /**
     * ===== RENDER - Draw Frame (Called 60fps) =====
     * Forces immediate repaint of entire GamePanel.
     * <p>
     * paintImmediately() = Emergency redraw → Bypasses Swing paint queue
     * Parameters: (x=0, y=0, width, height) = Entire panel
     * <p>
     * ⚠️ WARNING: Thread-safe but can cause flickering on some systems
     */
    public void render() {
        // ⚡ FORCE IMMEDIATE REPAINT (no delay!)
        // Triggers: GamePanel.paintComponent() → GameWorld.render()
        gamePanel.paintImmediately(0, 0, gamePanel.getWidth(), gamePanel.getHeight());
    }
}
