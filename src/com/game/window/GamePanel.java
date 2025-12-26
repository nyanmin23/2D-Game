package com.game.window;

import javax.swing.*;
import java.awt.*;

import static com.game.constants.GameConstant.*;

public class GamePanel extends JPanel {

    private GameWorld gameWorld;

    /**
     * This step comes after JFrame/ GameFrame setup
     *
     */
    public GamePanel(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT)); // JPanel method
        setFocusable(true); // Input from KB/ Mouse will be direct to Panel instead of Frame
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        /**
         * render() method will be implemented in GameWorld Class
         */
        gameWorld.render(g);
    }
}
