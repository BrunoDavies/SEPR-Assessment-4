package com.dicycat.kroy.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.screens.MenuScreen;
import com.dicycat.kroy.screens.MenuScreen.MenuScreenState;

/**
 * Static friendly Entity.
 * Player heals and replenishes water when within its radius.
 */
public class FireStation extends Entity {

	private Texture textureDead;

	/**
	 * extended
	 */
	public FireStation(Texture alive, Texture dead) {
		super(new Vector2(3595, 4000), alive, new Vector2(298, 175), 100, 300);
		this.textureDead = dead;
	}

	/**
	 * Removes from active pool and displays destroyed state
	 */
	@Override
	public void die() {
		super.die();
		sprite.setTexture(textureDead);
		displayable = true;
	}

	@Override
	public void update() {

		if (playerInRadius()) {
			Kroy.mainGameScreen.getPlayer().replenish();
		}

		if (playerInRadius() && Gdx.input.isKeyPressed(Keys.SPACE)) {
			//TODO Minigame call here?
		}

		if (Kroy.mainGameScreen.gameTimer <= 0) {        //Once timer is over
			applyDamage(100);    //Destroy fire station
		}
	}

	/**
	 * new
	 *
	 * @return texturedead
	 */
	public Texture getTexturedead() {
		return textureDead;
	}
}
