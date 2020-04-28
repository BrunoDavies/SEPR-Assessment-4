package com.dicycat.kroy;

import static org.junit.Assert.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import com.dicycat.kroy.entities.Entity;
import com.dicycat.kroy.entities.UFO;
import com.dicycat.kroy.screens.GameScreen;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import com.dicycat.kroy.entities.FireTruck;
import com.badlogic.gdx.math.Rectangle;
import org.mockito.Mockito;


import java.util.ArrayList;

import static org.junit.Assert.assertTrue;


@RunWith(GdxTestRunner.class)
public class FireTruckTest {


	private FireTruck testTruck;

	Float[] truckStats={300f, 1.5f, 400f, 300f};

	private UFO testUFO;

	private Vector2 testSpawn = new Vector2(0, 0 );
	private Texture testTexture = new Texture("ufo.png");
	private Texture testBulletTexture = new Texture("bullet.png");




	//UNIT_TESTING_3 - START OF MODIFICATION - NPSTUDIOS - BRUNO DAVIES
	//Removed all mock setup.
	//None of these were actually used in the testing. They were incorrectly used by the previous
	//group and were slowing test execution time thus have been taken out.
	//UNIT_TESTING_3 - END OF MODIFICATION - NPSTUDIOS - BRUNO DAVIES

	@Before
	public void init() {

		testTruck = new FireTruck(new Vector2(0, 0), truckStats, new Texture("fireTruck1.png"));
		testUFO = new UFO(testSpawn, testTexture, testBulletTexture);
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
		assertTrue(testTruck.getHealthPoints() !=  102);  //HealthPoints didn't increase because it will get more than max HealthPoits
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

	//TEST_FIRETRUCK_3 - Test that setDefenceUp works sets the flag to passed arguement, sets time and icon
	@Test
	public void setDefenceUpShouldSetFlag(){
		assertFalse(testTruck.getDefenceUp());
	}
}









































