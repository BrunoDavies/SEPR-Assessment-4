package com.dicycat.kroy.powerups;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GameObject;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.entities.FireTruck;
import com.dicycat.kroy.screens.GameScreen;
import com.dicycat.kroy.screens.MinigameScreen;

public class Box extends GameObject {
    private Circle hitbox;

    public Box(Vector2 spawnPos){
        super(spawnPos, Kroy.mainGameScreen.textures.getBullet(), new Vector2(20,20));
        hitbox = new Circle(spawnPos.x, spawnPos.y, 10);
    }
    @Override

    public void update() {
        FireTruck truck = Kroy.mainGameScreen.getPlayer();
        if (truck.isAlive()) {
            if(Intersector.overlaps(hitbox, truck.getHitbox())){
                Kroy.mainGameScreen.setGameState(GameScreen.GameScreenState.MINIGAME);
                die();
                Kroy.mainGameScreen.game.setScreen(new MinigameScreen(Kroy.mainGameScreen.game));
            }
        }
    }
}
