package com.game.window;

import java.awt.*;

public class GameWorld {

    int x, y = 0;
    int speed = 5;


    public GameWorld() {

    }

    public void render(Graphics g) {
        g.setColor(Color.blue);
        g.fillRect(x, y, 50, 50);

        // TODO: render player() and enemy()
    }

    public void update(float deltaTime) {
        // TODO: add player and update enemy logics
    }

    /**
     *
     */

    // TODO: Add Player

    // TODO: Add Enemy

    // TODO: Add Enemy Object

    // TODO: Add Trees
    public void up() {
        y -= speed;
    }

    public void down() {
        y += speed;
    }

    public void left() {
        x -= speed;
    }

    public void right() {
        x += speed;
    }


}
