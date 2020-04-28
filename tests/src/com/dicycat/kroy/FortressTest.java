package com.dicycat.kroy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import com.dicycat.kroy.entities.Fortress;

@RunWith(GdxTestRunner.class)
public class FortressTest {

	Vector2 spawnPos = new Vector2(new Vector2(2903, 3211));
	Texture fortressTexture = new Texture("cliffords tower.png");
	Texture deadTexture = new Texture("cliffords tower dead.png");
	Texture bulletTexture = new Texture("bullet.png");
	Vector2 size = new Vector2(new Vector2(256, 218));
	float[] fortressStats = new float[]{400f, 10f};

	private Fortress fortress;

	//UNIT_TESTING_M - START OF MODIFICATION - NPSTUDIOS - BRUNO DAVIES
	//Removed all mock setup.
	//None of these were actually used in the testing. They were incorrectly used by the previous
	//group and were slowing test execution time thus have been taken out.
	// UNIT_TESTING_M - END OF MODIFICATION - NPSTUDIOS - BRUNO DAVIES

	//UNIT_TESTING_7 - START OF MODIFICATION - NPSTUDIOS - BRUNO DAVIES
	//Changed the fortress initialization to use the already defined variables. The previous group had set up the
	//variables but not used them.
	@Before
	public void init() {
		fortress=new Fortress(spawnPos, fortressTexture, deadTexture, new Vector2(100, 100), bulletTexture,
									fortressStats);
	}
	//UNIT_TESTING_7 - END OF MODIFICATION - NPSTUDIOS - BRUNO DAVIES


	//UNIT_TESTING_8 - START OF MODIFICATION - NPSTUDIOS - BRUNO DAVIES
	//Modified their 'location()' test so that it does not test multiple methods in one test and has a more appropriate
	//name given what it is testing. They also tested .getCentre() incorrectly causing fails in previous test

	@Test
	public void setPositionShouldSetTheCorrectPosition() {
		fortress.setPosition(new Vector2(3021, 3320));
		assertEquals(new Vector2(3021, 3320), fortress.getPosition());
	}

	@Test
	public void setPositionShouldSetNewPositionAndReturnCorrectCentre(){
		fortress.setPosition(new Vector2(3021, 3320));
		assertEquals(3071, fortress.getCentre().x, 0f);
		assertEquals(3370, fortress.getCentre().y, 0f);

	}
	//UNIT_TESTING_8 - END OF MODIFICATION - NPSTUDIOS - BRUNO DAVIES

	//UNIT_TESTING_9 - START OF MODIFICATION - NPSTUDIOS - BRUNO DAVIES
	//Incorrect naming
	/**
	 * HealthPoints should decrease when user call the Damage function
	 */
	@Test
    public void takeDamageShouldResultInCorrectDecreaseInHealth() {
		assertEquals(400, fortress.getHealthPoints(),0f);
        fortress.applyDamage(5);
        assertEquals(395, fortress.getHealthPoints(), 0f);
    }
	//UNIT_TESTING_9 - END OF MODIFICATION - NPSTUDIOS - BRUNO DAVIES

	//UNIT_TESTING_10 - START OF MODIFICATION - NPSTUDIOS - BRUNO DAVIES
	//Incorrect naming and usage of assertTrue
	/**
	 * Fortress should become disable when it dies
	 */
	@Test
	public void deathShouldChangeDisplayableTheFortress() {
		fortress.death();
		assertTrue(fortress.isDisplayable());
	}




}


