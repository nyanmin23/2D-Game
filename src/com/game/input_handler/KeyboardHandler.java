package com.game.input_handler;

import com.game.window.GameWorld;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardHandler implements KeyListener {

    public GameWorld gameWorld;

    public KeyboardHandler(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP -> gameWorld.up();
            case KeyEvent.VK_DOWN -> gameWorld.down();
            case KeyEvent.VK_LEFT -> gameWorld.left();
            case KeyEvent.VK_RIGHT -> gameWorld.right();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
