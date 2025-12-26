package com.game.window;

import com.game.input_handler.KeyboardHandler;

public class GameInitializer {

    private GameWorld gameWorld;
    private GameFrame gameFrame;
    private GamePanel gamePanel;
    private GameLoop gameLoop;

    private KeyboardHandler keyboardHandler;

    public GameInitializer() {
        gameWorld = new GameWorld();
        gamePanel = new GamePanel(gameWorld);
        gameFrame = new GameFrame(gamePanel);

        keyboardHandler = new KeyboardHandler(gameWorld);
        gamePanel.addKeyListener(keyboardHandler);

        gameLoop = new GameLoop(gamePanel, gameWorld);
        gameLoop.start();
    }
}
