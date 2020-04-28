/**
 * 
 */
package com.dicycat.kroy;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.badlogic.gdx.math.Rectangle;
import com.dicycat.kroy.entities.Goose;

/**
 * @author Sam Hutchings
 *
 */
@RunWith(GdxTestRunner.class)
public class GooseTest {

	private Goose goose;

	@Before
	public void init() {
		goose = new Goose();
	}

	//UNIT_TESTING_14 - START OF MODIFICATION - NPSTUDIOS - BRUNO DAVIES
	//Changed naming
	@Test
	public void hitboxShouldBeToScaleOfGoose() {
		float scale = goose.getScale();
		Rectangle hitbox = new Rectangle(10, 10, 50, 70);
		assertEquals(hitbox, goose.getHitbox());
	}

	//UNIT_TESTING_14 - END OF MODIFICATION - NPSTUDIOS - BRUNO DAVIES

	//UNIT_TESTING_15 - START OF MODIFICATION - NPSTUDIOS - BRUNO DAVIES
	//Changed naming and split the test in two since each test should focus on one thing
	@Test
	public void movementShouldChangeWithUpdatesGravity() {
		// Gravity 
		float y1 = goose.getY(); 
		goose.update();
		float y2 = goose.getY();
		assertTrue(y1 > y2);
	}

	@Test
	public void movementShouldChangeWithUpdatesJumping(){
		goose.update();
		float y2 = goose.getY();
		// Jumping
		goose.setVelocity(10);
		goose.update();
		float y3 = goose.getY();
		assertTrue(y3 > y2);
	}
	//UNIT_TESTING_15 - END OF MODIFICATION - NPSTUDIOS - BRUNO DAVIES

	//UNIT_TESTING_16 - START OF MODIFICATION - NPSTUDIOS - BRUNO DAVIES
	//Deleted test since it did not accomplish anything and could not see what it was trying to test
	//UNIT_TESTING_16 - END OF MODIFICATION - NPSTUDIOS - BRUNO DAVIES


}
