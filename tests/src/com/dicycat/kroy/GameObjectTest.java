package com.dicycat.kroy;


import static org.junit.Assert.*;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.dicycat.kroy.GameObject;


@RunWith(GdxTestRunner.class)
public class GameObjectTest {
    //Placeholder for the tested GameObject
    private GameObjectClassTest testGameObject;

    //The test values needed to setup testGameObject
    private Vector2 testSpawn = new Vector2(0, 0 );
    private Texture testTexture = new Texture("fireTruck1.png");
    private Vector2 testImSize = new Vector2(50, 50 );

    //This, due to GdxTestRunner, will allow the testEntity to reset with these values between tests
    @Before
    public void init() {
        testGameObject = new GameObjectClassTest(testSpawn, testTexture, testImSize);
    }

    //TEST_GAMEOBJECT_1 - Test to ensure that the initialization of Entity has no hiccups
    @Test
    public void initializationShouldSetCorrectValuesToGameObject(){
        assertEquals(testSpawn, testGameObject.getPosition());
    }

    //TEST_GAMEOBJECT_2 - Test that changePosition() will change to the correct new position based off current position
    @Test
    public void changePositionShouldChangeToCorrectNewPosition(){
        Vector2 standardPosition = new Vector2(50, 50);

        assertEquals(testSpawn, testGameObject.getPosition());
        testGameObject.changePosition(standardPosition);
        assertEquals(new Vector2(50, 50), testGameObject.getPosition());
    }

    //TEST_GAMEOBJECT_3 - Test that changePosition() will change position to negative
    @Test
    public void changePositionShouldChangeToNegativePosition(){
        Vector2 negPosition = new Vector2(-5, -10);

        assertEquals(testSpawn, testGameObject.getPosition());
        testGameObject.changePosition(negPosition);
        assertEquals(negPosition, testGameObject.getPosition());
    }

    //TEST_GAMEOBJECT_4 - Test that getCentre() will return the correct centre
    @Test
    public void getCentreWillGiveCorrectCentreWithStandardPosition(){
        assertEquals(new Vector2(25, 25), testGameObject.getCentre());
    }

    //TEST_GAMEOBJECT_5 - Test that getCentre() will return the correct centre with negative position
    @Test
    public void getCentreWithNegativePosition(){
        testGameObject.changePosition(new Vector2(-50, -50));
        assertEquals(new Vector2(-50, -50), testGameObject.getPosition());
        assertEquals(new Vector2(-25, -25), testGameObject.getCentre());
    }

    //TEST_GAMEOBJECT_6 - Test quickly that setRotation() sets the rotation
    @Test
    public void setRotationShouldSetRotation(){
        assertEquals(0, testGameObject.getRotation(), 0f);
        testGameObject.setRotation(20f);
        assertEquals(20, testGameObject.getRotation(), 0f);
    }

    //TEST_GAMEOBJECT_7 - Test that setPosition() sets to exactly the input vector
    @Test
    public void setPositionShouldSetVectorToExactInput(){
        assertEquals(testSpawn, testGameObject.getPosition());
        testGameObject.setPosition(new Vector2(20, 20));
        assertEquals(new Vector2(20, 20), testGameObject.getPosition());
    }

    //TEST_GAMEOBJECT_8 - Test that setRemove() will set the variable remove to the input boolean
    @Test
    public void setRemoveShouldSetRemoveToInput(){
        assertFalse(testGameObject.isRemove());
        testGameObject.setRemove(true);
        assertTrue(testGameObject.isRemove());
    }

    //TEST_GAMEOBJECT_9 - Test that die() will set the variable remove to true
    @Test
    public void dieShouldAlwaysSetRemoveToTrue(){
        assertFalse(testGameObject.isRemove());
        testGameObject.die();
        assertTrue(testGameObject.isRemove());
    }



}




//UNIT_TESTING_2 - START OF MODIFICATION - NPSTUDIOS - BRUNO DAVIES
// This essentially implements GameObject in a non-abstract state to allow for testing
class GameObjectClassTest extends GameObject {
    public GameObjectClassTest(Vector2 spawnPos, Texture image, Vector2 imSize) {
        super(spawnPos, image, imSize);
    }
    @Override
    public void update() {
    }
}
//UNIT_TESTING_2 - END OF MODIFICATION - NPSTUDIOS - BRUNO DAVIES