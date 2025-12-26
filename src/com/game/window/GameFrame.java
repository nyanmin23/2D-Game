package com.game.window;

import javax.swing.*;

import static com.game.constants.GameConstant.*;

public class GameFrame {

    /**
     * FIRST STEP BEFORE EVERYTHING FOR SETTING GAME DISPLAY CANVAS
     * <p>
     * Step 1. Use JFrame from Swing lib (Canvas)
     * Step 2. Stack JPanel on top of JFrame
     */
    private JFrame jframe;

    public GameFrame(GamePanel gamePanel) {
        jframe = new JFrame(GAME_TITLE);
        jframe.add(gamePanel);
        jframe.pack(); // Use JFrame and JPanel together (Same Size Window)
        jframe.setResizable(false);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setLocationRelativeTo(null); // Will be centered by default
        jframe.setVisible(true);
    }
}
