package com.dicycat.kroy;

import static org.junit.Assert.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.entities.Fortress;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;


import com.dicycat.kroy.entities.FireTruck;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(GdxTestRunner.class)
public class FireTruckTest {
	private Kroy testKroy;

	private FireTruck testTruck;
	 Float[] truckStats={300f, 1.5f, 400f, 300f};


	//UNIT_TESTING_3 - START OF MODIFICATION - NPSTUDIOS - BRUNO DAVIES
	//None of these were actually used in the testing of FireTruck Class. They were incorrectly used by the previous
	//group and were slowing test execution time thus have been taken out.
	//UNIT_TESTING_3 - END OF MODIFICATION - NPSTUDIOS - BRUNO DAVIES

	@Before
	public void init() {
		testKroy = new Kroy();
		testTruck = new FireTruck(new Vector2(0, 0), truckStats, new Texture("fireTruck1.png"));
	}

	/**
	 * Correct amounts should be assigned to truck
	 */	 
	@Test
	public void testInitialisation() {
		org.junit.Assert.assertTrue(testTruck.getHealthPoints() == 100);
	}
	
	/**
	 * Check the Hitbox
	 */
	@Test
	public void Hitbox() {		
		Rectangle hitbox = new Rectangle(20, 45, 20, 20);

		assertTrue((int) testTruck.getHitbox().x == hitbox.x);
		assertTrue((int) testTruck.getHitbox().y == hitbox.y);
		assertTrue((int) testTruck.getHitbox().width == hitbox.width);
		assertTrue((int) testTruck.getHitbox().height == hitbox.height);
	}
	

	/**
	 * Check the replenish method to see if HealthPoints and CurrentWater increase correctly
	 */
	@Test
	public void testRefill() {		
		testTruck.addHealth(2);
		testTruck.replenish();
		assertTrue(testTruck.getHealthPoints() !=  102);  //HealthPoits didn't increase because it will get more than max HealthPoits
		assertFalse(testTruck.getCurrentWater() ==  302);
		
		testTruck.applyDamage(10);
		
		assertTrue(testTruck.getHealthPoints() ==  90);
		
		testTruck.replenish();
		assertTrue(testTruck.getHealthPoints() ==  92);   //increased this time as HealthPoits is lower than max HealthPoits
		assertFalse(testTruck.getCurrentWater() ==  304);
		
		testTruck.addWater(96);
		
		assertFalse(testTruck.getCurrentWater() ==  400);
		
		testTruck.replenish();
		
		assertTrue(testTruck.getHealthPoints() ==  94);   //CurretWater didn't increase because it will get more than max water
		assertFalse(testTruck.getCurrentWater() ==  400);

	}
	
	/**
	 * Check the movements correctness when user press the movement keys
	 */
	@Test
	public void movementTest() {
		
		testTruck.setDirection(testTruck.DIRECTIONS.get("nw"));
		assertTrue(testTruck.getDirection() == 45);
		
		testTruck.setDirection(testTruck.DIRECTIONS.get("se"));
		assertTrue(testTruck.getDirection() == 225);

		testTruck.setDirection(testTruck.DIRECTIONS.get("s"));
		assertTrue(testTruck.getDirection() == 180);
			
		testTruck.setDirection(testTruck.DIRECTIONS.get("ns"));
		assertTrue(testTruck.getDirection() == null);           //it should not move when we press both up and down keys
		
		testTruck.setDirection(testTruck.DIRECTIONS.get("sn"));
		assertTrue(testTruck.getDirection() == null);
		
		testTruck.setDirection(testTruck.DIRECTIONS.get("we"));
		assertTrue(testTruck.getDirection() == null);           //it should not move when we press both left and right keys
		

	}

	//UNIT_TESTING - START OF MODIFICATION - NPSTUDIOS - BRUNO DAVIES
	Vector2 testSpawn = new Vector2(0, 0);
	Texture testTexture = new Texture("fireTruck1.png");

//	@Test
//	public void testingTest() {
////		FireTruck mockFireTruck = new FireTruck(testSpawn, truckStats, testTexture);
////		FireTruck spy = spy(mockFireTruck);
////
////		doReturn(false).when(spy).isOnCollidableTile(new Vector2(0, 0));
////
////		spy.moveInDirection();
////		assertFalse(spy.isOnCollidableTile(new Vector2(0, 0)));
////		ArrayList<Fortress> fortresses = new ArrayList<Fortress>();
////		float[] fortressStats = new float[]{400f, 10f};
////
////		fortresses.add(new Fortress(new Vector2(0, 0 ), new Texture("fireTruck1.png"),
////				new Texture("fireTruck2.png"), new Vector2(100, 100),
////				new Texture("bullet.png"), fortressStats));
////
////
////		Kroy kroy1 = mock(Kroy.class, RETURNS_DEEP_STUBS);
////		when(kroy1.mainGameScreen.getFortresses()).thenReturn(fortresses);
////		when(kroy1.mainGameScreen.getPlayer()).thenReturn(testTruck);
////
////		FireTruck testAH = kroy1.mainGameScreen.getPlayer();
////		testAH.die();
//
//	}

	//TEST_FIRETRUCK_1 - Test that objectInRange returns false if not in range
	@Test
	public void objectInRangeShouldReturnFalseIfNotInRange(){
		FireTruck rangeTestTruck = new FireTruck(new Vector2(1000, 1000), truckStats, testTexture);
		assertFalse(testTruck.objectInRange(rangeTestTruck));
	}

	//TEST_FIRETRUCK_2 - Test that objectInRange returns true if it is in range
	@Test
	public void objectInRangeShouldReturnTrueIfInRange(){
		FireTruck rangeTestTruck = new FireTruck(testSpawn, truckStats, testTexture);
		assertTrue(testTruck.objectInRange(rangeTestTruck));
	}

	//TEST_FIRETRUCK_3 - Test that replenish() will not fill if water is a max
	@Test
	public void moveInDirectionShouldChangeYWhenNoCollisionAndDirectionSetToN() {
		testTruck.moveInDirection();

		//The calculations to find the new position
		Vector2 movement = new Vector2(1,0);
		movement.setAngle(0+90);
		float posChange = 1f * Gdx.graphics.getDeltaTime();
		Matrix3 distance = new Matrix3().setToScaling(posChange,posChange);
		movement.nor();
		movement.mul(distance);
		Vector2 newPos = new Vector2(12.5f, 25.0f);
		newPos.add(movement);

		assertEquals(newPos, testTruck.getCentre());
	}

}









































