package com.game.window;

public class GameLoop implements Runnable {

    private GamePanel gamePanel;
    private GameWorld gameWorld;
    private Thread gameThread;
    // volatile
    private boolean running; // 'volatile' keyword: Synchronize Check on Game Running boolean from Multiple Threads

    public GameLoop(GamePanel gamePanel, GameWorld gameWorld) {
        this.gamePanel = gamePanel;
        this.gameWorld = gameWorld;
    }

    // TODO: Game start(), stop() methods
    // 'synchronized' is used as start() can be used elsewhere.
    public synchronized void start() {
        if (running) {
            return;
        }

        /**
         * Since 'GameLoop' implements 'Runnable' interface,
         * 'this' keyword can be used and accepted
         * as parameter for Thread constructor as same 'Runnable' object.
         */
        running = true;
        gameThread = new Thread(this); // this: GameLoop Class Object
        gameThread.start();
    }

    public synchronized void stop() {
        if (!running) {
            return;
        }

        running = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {

        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        int frames = 0;

        while (running) {
            long now = System.nanoTime();
            float delta = (now - lastTime) / 1_000_000_000.0f;
            lastTime = now;

            // TODO: Implement update()
            update(delta);

            // TODO: Implement render()
            render();

            frames++;
            if (System.currentTimeMillis() - timer > 1_000) {
                System.out.println("FPS " + frames);
                frames = 0;
                timer = System.currentTimeMillis();
            }
        }
    }

    public void update(float delta) {
        gameWorld.update(delta);
    }


    public void render() {
        // need method from GamePanel
        gamePanel.paintImmediately(0, 0, gamePanel.getWidth(), gamePanel.getHeight());
    }
}
