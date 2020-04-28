package com.dicycat.kroy;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GameObject;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.entities.Entity;
import com.dicycat.kroy.entities.FireStation;
import com.dicycat.kroy.screens.GameScreen;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class FireStationTest {
	//UNIT_TESTING_M - START OF MODIFICATION - NPSTUDIOS - BRUNO DAVIES

	//Removed most mock setup.
	//None of these were actually used in the testing. They were incorrectly used by the previous
	//group and were slowing test execution time thus have been taken out.

	// UNIT_TESTING_M - END OF MODIFICATION - NPSTUDIOS - BRUNO DAVIES

	 private FireStation firestation;
	 private Entity entity;
	 private GameScreen gameScreen;
	 Texture aliveTexture = new Texture("fireTruck1.png");
	 Texture deadTexture = new Texture("fireTruck2.png");

	@Before
	public void setupMock() {
	    entity = Mockito.mock(Entity.class);
	 }

	@Before
	public void init() {
		firestation = new FireStation(aliveTexture, deadTexture);
	}


	//UNIT_TESTING_11 - START OF MODIFICATION - NPSTUDIOS - BRUNO DAVIES
	//Change naming to fit conventions, and their incorrect use of mock and assertTrue
	/**
	 * Check the FireStation was built at the right place
	 */
	@Test
	public void fireStationShouldInitializeCorrectly() {
		assertEquals(3744, firestation.getCentre().x, 0f);
		assertEquals(4087.5, firestation.getCentre().y, 0f);

	}
	//UNIT_TESTING_11 - END OF MODIFICATION - NPSTUDIOS - BRUNO DAVIES

	//UNIT_TESTING_12 - START OF MODIFICATION - NPSTUDIOS - BRUNO DAVIES
	//Change naming to fit conventions, and their incorrect use of assertTrue/False
	/**
	 * If the FireStation dies, its texture should change
	 */
	@Test
	public void dieShouldChangeTheTextureOfTheFirestation() {
		assertEquals(aliveTexture, firestation.getTexture());
		assertNotEquals(firestation.getTexture(), firestation.getTexturedead());
		firestation.die();
		assertEquals(deadTexture, firestation.getTexturedead());
	}
	//UNIT_TESTING_12 - END OF MODIFICATION - NPSTUDIOS - BRUNO DAVIES


	//UNIT_TESTING_13 - START OF MODIFICATION - NPSTUDIOS - BRUNO DAVIES
	/*
	 * If a truck is near FireStation, it should be replenish
	 * After a certain time FireStation should be destroyed
	 */
	@Test
	public void updateOnFireStationShouldReplenishWater() {

		if(entity.playerInRadius()) {
			gameScreen.getPlayer().replenish();
			assertTrue(false);

			assertTrue(gameScreen.getPlayer().getHealthPoints() == 42);
		}

		if(gameScreen.gameTimer < 0 ) {
			assertTrue(false);

			assertTrue(firestation.getTexture()== gameScreen.textures.getFireStationDead());
		}
	}

}
