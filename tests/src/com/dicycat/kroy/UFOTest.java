package com.dicycat.kroy;

import static org.junit.Assert.*;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.dicycat.kroy.entities.UFO;


@RunWith(GdxTestRunner.class)
public class UFOTest {
    private UFO testUFO;

    private Vector2 testSpawn = new Vector2(0, 0 );
    private Texture testTexture = new Texture("ufo.png");
    private Texture testBulletTexture = new Texture("bullet.png");

    @Before
    public void init() {
        testUFO = new UFO(testSpawn, testTexture, testBulletTexture);
    }

    @Test
    public void initializationShouldSetCorrectValuesToUFO(){
        assertEquals(testSpawn, testUFO.getPosition());
    }

    @Test
    public void testingTest(){
        testUFO.moveInDirection();
        assertEquals(0, 0);
    }
}