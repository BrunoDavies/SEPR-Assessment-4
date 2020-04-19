package com.dicycat.kroy.misc;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GameObject;

/**
 * ADD DESCRIPTION HERE - NEW CLASS
 *
 * @author Alasdair Pilmore-Bedford - NP STUDIOS
 *
 */
public class StatusIcon extends GameObject{
    private boolean enabled;

    public StatusIcon(Vector2 spawnPos, String texture) {
        super(spawnPos, new Texture(texture), new Vector2(25,25));
        enabled = false;
    }

    // returns isEnabled
    public boolean isEnabled(){
        return enabled;
    }


    // sets enabled to true
    public void setEnabled(boolean state){
        enabled = state;
    }

    @Override
    public void update() {}

}
