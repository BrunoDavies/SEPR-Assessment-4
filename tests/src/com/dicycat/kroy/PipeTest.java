/**
 *
 */
package com.dicycat.kroy;

import static org.junit.Assert.*;

import com.badlogic.gdx.graphics.Texture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.entities.Goose;
import com.dicycat.kroy.entities.Pipe;

/**
 * @author Sam Hutchings
 *
 */
@RunWith(GdxTestRunner.class)
public class PipeTest {

	private Pipe pipe;
	Texture testTexture = new Texture("pipe.png");

	@Before
	public void init() {
		pipe = new Pipe(new Vector2(800,-1000), testTexture);
	}

	/**
	 * Test method for movement.
	 */
	@Test
	public void movementTest() {
		// Gravity
		float x1 = pipe.getX();
		pipe.update();
		float x2 = pipe.getX();
		assertTrue(x1 > x2);

	}

	/**
	 * Test method for isRemove().
	 */
	@Test
	public void testIsRemove() {
		pipe.setLifeTime(-1);
		assertTrue(pipe.isRemove());
	}

	/**
	 * Test method for getHitboxes().
	 */
	@Test
	public void testGetHitboxes() {
		Rectangle rect = new Rectangle(400, 0, 30, 500);
		Rectangle hitbox = pipe.getHitboxes()[0];
		assertEquals(rect, hitbox);
	}

	/**
	 * Test method for gameEnd().
	 */
	@Test
	public void testGameEnd() {
		Goose player = new Goose();
		player.setPosition(new Vector2(800,-1000));
		player.update(); // Goose hitbox updated
		assertFalse(pipe.gameEnd(player));
		pipe.update(); // Pipe hitbox updated to intersect with the goose
		assertFalse(pipe.gameEnd(player));
	}

}
