package com.dicycat.kroy;

import static org.junit.Assert.*;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.dicycat.kroy.entities.Entity;


@RunWith(GdxTestRunner.class)
public class EntityTest {
    //Placeholder for entity
    private entityClassTest testEntity;

    //The test values needed to setup testEntity;
    private Vector2 testSpawn = new Vector2(0, 0 );
    private Texture testTexture = new Texture("fireTruck1.png");
    private Vector2 testImSize = new Vector2(50, 50 );
    private int testHealth = 100;
    private int testRadius = 50;

    //This, due to GdxTestRunner, will allow the testEntity to reset with these values between tests
    @Before
    public void init() {
        testEntity = new entityClassTest(testSpawn, testTexture, testImSize, testHealth, testRadius);
    }

    //TEST_ENTITY_1 - Test to ensure that the initialization of Entity has no hiccups
    @Test
    public void initializationShouldSetCorrectValuesToEntity(){
        assertEquals(100, testEntity.getMaxHealthPoints(), 0);
    }

    //TEST_ENTITY_2 - Test that isAlive(), which should 'true' if the Entity has greater than 0 health points, returns
    //                  true when the testEntity has its initialized health
    @Test
    public void isAliveShouldReturnTrueWhenHealthIsAboveZero(){
        assertTrue(testEntity.isAlive());
    }

    //TEST_ENTITY_3 - Test that applyDamage(), which (after checking the entity is alive) applies damage to an entity
    //                  amd checks the correct amount came off.
    @Test
    public void applyDamageShouldDecreaseHealthPointsByAmountGivenWhenEntityIsAlive(){
        assertTrue(testEntity.isAlive());
        testEntity.applyDamage(10f);
        assertEquals(90f, testEntity.getHealthPoints(), 0);
    }

    //TEST_ENTITY_4 - Test that applyDamage(), does not take negative damage that would heal it.
    @Test
    public void applyDamageShouldNotIncreaseHealthWithNegativeInputs() {
        testEntity.applyDamage(-10f);
        assertEquals(110f, testEntity.getHealthPoints(), 0f);
    }

    //TEST_ENTITY_4 - Test that applyDamage() allows for 0 damage and does not remove HP.
    @Test
    public void applyDamageShouldNotDecreaseHealthWithZeroAttack() {
        testEntity.applyDamage(0f);
        assertEquals(100f, testEntity.getHealthPoints(), 0f);
    }

    //TEST_ENTITY_5 - Test that setMaxHealthPointsForDifficulty() will set both the healthPoints (current) and the
    //                  maxHealthPoints which is needed for difficulty
    @Test
    public void setMaxHealthPointsForDifficultyShouldSetBothHealth() {
        testEntity.setMaxHealthPointsForDifficulty(150);
        assertEquals(150f, testEntity.getHealthPoints(), 0f);
        assertEquals(150f, testEntity.getMaxHealthPoints(), 0f);
    }

    //TEST_ENTITY_6 - Test that setMaxHealthPointsForDifficulty will not set negative health points and will keep the
    //                  the current values
    @Test
    public void setMaxHealthPointsForDifficultyDoesNotSetNegative() {
        testEntity.setMaxHealthPointsForDifficulty(-10);
        assertEquals(100f, testEntity.getMaxHealthPoints(), 0f);
        assertEquals(100f, testEntity.getHealthPoints(), 0f);
    }

    //TEST_ENTITY_7 - Test that setMaxHealthPointsForDifficulty will set the value of maxHealth and healthPoints to zero
    @Test
    public void setMaxHealthPointsForDifficultyWillSetZero() {
        testEntity.setMaxHealthPointsForDifficulty(0);
        assertEquals(0f, testEntity.getHealthPoints(), 0f);
        assertEquals(0f, testEntity.getMaxHealthPoints(), 0f);
    }

    //TEST_ENTITY_8 -
}

//UNIT_TESTING_1 - START OF MODIFICATION - NPSTUDIOS - BRUNO DAVIES
// This essentially implements entity in a non-abstract state to allow for testing
class entityClassTest extends Entity {
    /**
     * @param spawnPos The position the entity will spawn at.
     * @param img      The texture of the entity.
     * @param imSize   Size of the entity. Can be used to resize large/small textures
     * @param health   Hit points of the entity
     * @param radius
     */
    public entityClassTest(Vector2 spawnPos, Texture img, Vector2 imSize, float health, int radius) {
        super(spawnPos, img, imSize, health, radius);
    }
}
//UNIT_TESTING_1 - END OF MODIFICATION - NPSTUDIOS - BRUNO DAVIES