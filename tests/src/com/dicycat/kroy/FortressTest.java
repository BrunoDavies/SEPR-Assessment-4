package com.dicycat.kroy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
import com.dicycat.kroy.bullets.BulletDispenser;
import com.dicycat.kroy.entities.Entity;
import com.dicycat.kroy.entities.FireStation;
import com.dicycat.kroy.entities.Fortress;
import com.dicycat.kroy.misc.StatBar;
import com.dicycat.kroy.screens.GameScreen;
import static org.junit.Assert.*;
@PrepareForTest( Fortress.class )
@RunWith(GdxTestRunner.class)
public class FortressTest {

	@Mock BulletDispenser dispenser;
	@Mock StatBar healthBar;

	@Mock Vector2 spawnPos = new Vector2(new Vector2(2903, 3211));
	@Mock Texture fortressTexture = new Texture("cliffords tower.png");
	@Mock Texture deadTexture = new Texture("cliffords tower dead.png");
	@Mock Vector2 size = new Vector2(new Vector2(256, 218));
	float[] fortressStats = new float[]{400f, 10f};

	@Mock
	private Fortress fortress;

	//UNIT_TESTING_5 - START OF MODIFICATION - NPSTUDIOS - BRUNO DAVIES
// 	As mentioned in the report, their use of mocking was incorrect and not used thus we deleted it which also helps
//	Execution time
	//UNIT_TESTING_5 - START OF MODIFICATION - NPSTUDIOS - BRUNO DAVIES

	@Before
	public void init() {
		fortress=new Fortress(new Vector2(0, 0 ), new Texture("fireTruck1.png"),
                new Texture("fireTruck2.png"), new Vector2(100, 100),
                new Texture("bullet.png"), fortressStats);
	}

	/**
	 * Check the Fortress was built at the right place
	 */
	@Test
	public void location() {
		fortress.setPosition(new Vector2(3021, 3320));
		assertEquals(new Vector2(3021, 3320), fortress.getPosition());
//		assertTrue(fortress.getCentre().x==3031);
//		assertTrue(fortress.getCentre().y==3320);

	}

	/**
	 * HealthPoints should decrease when user call the Damage function
	 */
	@Test
    public void takeDamage() {
		assertEquals(400, fortress.getHealthPoints(),0f);
        fortress.applyDamage(5);
        assertEquals(395, fortress.getHealthPoints(), 0f);
    }

	/**
	 * Fortress should become disable when it dies
	 */
	@Test
	public void deathCheck() {
		fortress.death();
		assertTrue(fortress.isDisplayable()==true);
	}

    //TESTING - START OF MODIFICATION - NPSTUDIOS - BRUNO DAVIES



}


