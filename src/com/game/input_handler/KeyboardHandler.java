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
            case KeyEvent.VK_UP -> gameWorld.getPlayer().setUp(true);
            case KeyEvent.VK_DOWN -> gameWorld.getPlayer().setDown(true);
            case KeyEvent.VK_LEFT -> gameWorld.getPlayer().setLeft(true);
            case KeyEvent.VK_RIGHT -> gameWorld.getPlayer().setRight(true);
            case KeyEvent.VK_D -> gameWorld.getPlayer().setDead(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP -> gameWorld.getPlayer().setUp(false);
            case KeyEvent.VK_DOWN -> gameWorld.getPlayer().setDown(false);
            case KeyEvent.VK_LEFT -> gameWorld.getPlayer().setLeft(false);
            case KeyEvent.VK_RIGHT -> gameWorld.getPlayer().setRight(false);
        }
    }
}
