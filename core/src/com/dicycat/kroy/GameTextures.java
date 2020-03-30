package com.dicycat.kroy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;

/**
 * Stores textures for classes to reference.
 * This means multiple of the same sprite use the same reference.
 * Because of this, render calls are reduced.
 * 
 * @author Riju De
 *
 */
public class GameTextures {
	private Texture truck, ufo, fireStation, fireStationDead, bullet;

	private Texture[] livingFortresses = {new Texture("cliffords tower.png"),
			new Texture("york minster.png"), new Texture("Memorial.png") ,
			new Texture("york museum.png") , new Texture("University.png"),
			new Texture("Station.png")};

	private Texture[] truckTextures = {new Texture ("fireTruck1.png"), new Texture("fireTruck2.png"),
			new Texture("fireTruck3.png"), new Texture("fireTruck4.png"),
			new Texture("fireTruck5.png"), new Texture("fireTruck6.png")};

	// REFACTOR_2 - END OF MODIFICATION - NP STUDIOS
	private Texture[] deadFortresses = {new Texture("cliffords tower dead.png"),
			new Texture("york minster dead.png"), new Texture("MemorialDamaged.png"),
			new Texture("york museum dead.png"), new Texture("UniversityDamaged.png"),
			new Texture("StationDamaged.png")};
	private String[] truckAddress = {"fireTruck1.png", "fireTruck2.png", "fireTruck3.png", "fireTruck4.png", "fireTruck5.png", "fireTruck6.png"};
	  
	 
	/**
	 * @param truckNum Which truck texture to get
	 */
	public GameTextures() {
		ufo = new Texture("ufo.png");
		fireStation = new Texture("FireStationTemp.png");
		fireStationDead = new Texture("FireStationTempDead.png");
		bullet = new Texture(("bullet.png"));
	}


	/**
	 * Gets the trucks texture from the array truckTextures
	 * @param truckNum the index of the truck's texture that is needed
	 * @return FireTruck's texture
	 */
	public Texture getTruck(int truckNum) {
		return truckTextures[truckNum];
	}
	// REFACTOR_3 - END OF MODIFICATION - NP STUDIOS

	public Texture getUFO() {
		return ufo;
	}

	// REFACTOR_4 - START OF MODIFICATION  - NP STUDIOS - LUCY IVATT
	// Fixed incorrect java doc as it said the return type was a list of textures when it is a single texture based
	// on an index input.
	/**
	 * Gets the fortress texture from the array of livingFortresses
	 * @param fortress index of the fortress texture required
	 * @return the alive fortress texture
	 */
	// REFACTOR_4 - END OF MODIFICATION  - NP STUDIOS
	public Texture getFortress(int fortress) {
		return livingFortresses[fortress];
	}

	// REFACTOR_5 - START OF MODIFICATION  - NP STUDIOS - LUCY IVATT
	// Fixed incorrect java doc as it said the return type was a list of textures when it is a single texture based
	// on an index input.
	/**
	 * @param fortress index of the fortress texture required
	 * @return the dead fortress texture
	 */
	// REFACTOR_5 - END OF MODIFICATION  - NP STUDIOS
	public Texture getDeadFortress(int fortress) {
		return deadFortresses[fortress];
	}
	
	/**
	 * @return the FireStation's texture
	 */
	public Texture getFireStation() {
		return fireStation;
	}
	
	/**
	 * @return the texture of the dead FireStation
	 */
	public Texture getFireStationDead() {
		return fireStationDead;
	}

	public Texture getBullet() {
		return bullet;
	}
}
