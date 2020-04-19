package com.dicycat.kroy.powerups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GameObject;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.entities.Entity;
import com.dicycat.kroy.entities.FireTruck;
import com.dicycat.kroy.screens.GameScreen;
import com.dicycat.kroy.screens.MinigameScreen;

/**
 *
 * @author NPSTUDIOS - Bethany Gilmore
 *
 */
public class PowerupBox extends Entity {
    private Circle hitbox;

    public PowerupBox(Vector2 spawnPos){
        super(spawnPos, Kroy.mainGameScreen.textures.getPowerupBox(), new Vector2(50,50),10, 10);
        hitbox = new Circle(spawnPos.x + 25, spawnPos.y + 25, 30);
    }
    @Override

    public void update() {
        FireTruck truck = Kroy.mainGameScreen.getPlayer();
        if (truck.isAlive()) {
            if(Intersector.overlaps(hitbox, truck.getHitbox())){
                Kroy.mainGameScreen.setGameState(GameScreen.GameScreenState.MINIGAME);
                Kroy.mainGameScreen.addSpawnLocation(this.getPosition());
                this.die();
                Kroy.mainGameScreen.game.setScreen(new MinigameScreen(Kroy.mainGameScreen.game, false));
            }
        }
    }
}
