package com.dicycat.kroy.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dicycat.kroy.GameObject;
import com.dicycat.kroy.GameTextures;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.debug.DebugCircle;
import com.dicycat.kroy.debug.DebugDraw;
import com.dicycat.kroy.debug.DebugLine;
import com.dicycat.kroy.debug.DebugRect;
import com.dicycat.kroy.entities.FireStation;
import com.dicycat.kroy.entities.FireTruck;
import com.dicycat.kroy.entities.Fortress;
import com.dicycat.kroy.gamemap.TiledGameMap;
import com.dicycat.kroy.scenes.HUD;
import com.dicycat.kroy.scenes.OptionsWindow;
import com.dicycat.kroy.scenes.PauseWindow;


/**
 * Contains the main game logic
 * 
 * @author Riju De
 * @author lnt20
 *
 */
public class GameScreen implements Screen{

	public static enum GameScreenState{
		PAUSE,
		RUN,
		RESUME,
		OPTIONS
	}
	
	public Kroy game;
	public GameTextures textures;
	public float gameTimer; //Timer to destroy station
	
	
	public GameScreenState state = GameScreenState.RUN;
	
	public static TiledGameMap gameMap;
	
	private OrthographicCamera gamecam;	//follows along what the port displays
	private Viewport gameport;
	
	private HUD hud;
	private PauseWindow pauseWindow;
	private OptionsWindow optionsWindow;

	private Float[][] truckStats = {	//Each list is a configuration of a specific truck. {speed, flowRate, capacity, range}
			{400f, 1f, 400f, 300f},		//Speed
			{350f, 1.25f, 400f, 300f},	//Speed + Flow rate
			{300f, 1.5f, 400f, 300f},	//Flow rate
			{300f, 1f, 450f, 400f},  	//Capacity + Range
			{300f, 1f, 500f, 300f},		//Capacity
			{300f, 1f, 400f, 450f},		//Range
		};
	
	
	private int truckNum; // Identifies the truck thats selected in the menu screen
	private FireTruck truck1,truck2,truck3,truck4; //Reference to the player
	private FireTruck currentTruck;
	private int lives = 4;
	
	private int fortressesCount;
	private Vector2 spawnPosition;	//Coordinates the player spawns at
	
	private List<GameObject> gameObjects, deadObjects;	//List of active game objects
	private List<GameObject> objectsToRender = new ArrayList<GameObject>(); // List of game objects that have been updated but need rendering
	private List<GameObject> objectsToAdd;
	private List<DebugDraw> debugObjects; //List of debug items
	private ArrayList<FireTruck> firetrucks=new ArrayList<FireTruck>();
	private ArrayList<Texture> texture=new ArrayList<Texture>();

	/**
	 * @param _game
	 * @param truckNum
	 */
	public GameScreen(Kroy _game, int truckNum) {
		game = _game;
		gamecam = new OrthographicCamera();
		gameport = new FitViewport(Kroy.width, Kroy.height, gamecam);	//Mic:could also use StretchViewPort to make the screen stretch instead of adapt
		hud = new HUD(game.batch, this.game);
		gameMap = new TiledGameMap();										//or FitPort to make it fit into a specific width/height ratio
		pauseWindow = new PauseWindow(game);
		pauseWindow.visibility(false);
		optionsWindow = new OptionsWindow(game);
		optionsWindow.visibility(false);
		textures = new GameTextures(truckNum);
		spawnPosition = new Vector2(3750, 4000);
		gameTimer = 60 * 15; //Set timer to 15 minutes
		this.truckNum = truckNum;
	}

	/**
	 * Screen first shown
	 */
	@Override
	public void show() {
		objectsToAdd = new ArrayList<GameObject>();
		gameObjects = new ArrayList<GameObject>();
		deadObjects = new ArrayList<GameObject>();
		debugObjects = new ArrayList<DebugDraw>();
		
		// Initialises the FireTrucks
		truck1 = new FireTruck(spawnPosition,truckStats[1]); 
		truck2 = new FireTruck(spawnPosition,truckStats[2]);
		truck3 = new FireTruck(spawnPosition,truckStats[3]); 
		truck4 = new FireTruck(spawnPosition,truckStats[4]);
 				
		firetrucks.add(truck1);
		firetrucks.add(truck2);
		firetrucks.add(truck3);
		firetrucks.add(truck4);
		
		switchTrucks(truckNum);
		
		gamecam.translate(new Vector2(currentTruck.getX(),currentTruck.getY())); // sets initial Camera position
		
		gameObjects.add(new FireStation());
		gameObjects.add(new Fortress(new Vector2(2903,3211),textures.getFortress(0), textures.getDeadFortress(0), new Vector2(256, 218)));
		gameObjects.add(new Fortress(new Vector2(3200,5681), textures.getFortress(1), textures.getDeadFortress(1), new Vector2(256, 320)));
		gameObjects.add(new Fortress(new Vector2(2050,1937), textures.getFortress(2), textures.getDeadFortress(2), new Vector2(400, 240)));
		gameObjects.add(new Fortress(new Vector2(4350,900), textures.getFortress(3), textures.getDeadFortress(3), new Vector2(400, 240)));
		gameObjects.add(new Fortress(new Vector2(5900,1100), textures.getFortress(4), textures.getDeadFortress(4), new Vector2(400, 240)));
		gameObjects.add(new Fortress(new Vector2(520,3500), textures.getFortress(5), textures.getDeadFortress(5), new Vector2(400, 240)));

	}

	/**
	 * Called every frame
	 */
	@Override
	public void render(float delta) {
		Gdx.input.setInputProcessor(pauseWindow.stage);  //Set input processor
		pauseWindow.stage.act();

		switch (state) {
			case RUN:
				if (Gdx.input.isKeyPressed(Keys.P) || Gdx.input.isKeyPressed(Keys.O) || Gdx.input.isKeyPressed(Keys.M)|| Gdx.input.isKeyPressed(Keys.ESCAPE)){
					pauseWindow.visibility(true);
					pause();
				}
				gameTimer -= delta;		//Decrement timer

				updateLoop(); //Update all game objects positions but does not render them as to be able to render everything as quickly as possible

				gameMap.renderRoads(gamecam); // Render the background roads, fields and rivers
				gameMap.renderBuildings(gamecam); // Renders the buildings and the foreground items which are not entities

				game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
				game.batch.setProjectionMatrix(gamecam.combined);	//Mic:only renders the part of the map where the camera is
				game.batch.begin(); // Game loop Start

				hud.update(delta);

				renderObjects(); // Renders objects specified in the UpdateLoop() called previously

				game.batch.end();


				hud.stage.draw();
				pauseWindow.stage.draw();

				if (Kroy.debug) {
					DrawDebug(); //Draw all debug items as they have to be drawn outside the batch
				}

				break;
			case PAUSE:
				pauseWindow.stage.draw();
				clickCheck();
				break;
			case RESUME:
				pauseWindow.visibility(false);
				setGameState(GameScreenState.RUN);
				break;
			default:
				break;
		}
	}

	/**
	 * Updates all the active gameobjects and adds them to the render queue.
	 * Removes gameobjects from the active pool if they are marked for removal.
	 * Adds new gameobjects.
	 * Adds dead objects to render queue.
	 * Respawns the player if necessary.
	 */
	private void updateLoop() {
		List<GameObject> toRemove = new ArrayList<GameObject>();
		for (GameObject gObject : gameObjects) {	//Go through every game object
			gObject.update();						//Update the game object
			if (gObject.isRemove()) {				//Check if game object is to be removed
				toRemove.add(gObject);					//Set it to be removed
			}else {
				objectsToRender.add(gObject);
			}
		}
		
		currentTruck.update();
		
		for (GameObject rObject : toRemove) {	//Remove game objects set for removal
			gameObjects.remove(rObject);
			if (rObject.isDisplayable()) {
				deadObjects.add(rObject);
			}
		}
		for (GameObject aObject : objectsToAdd) {		//Add game objects to be added
			gameObjects.add(aObject);
		}
		objectsToAdd.clear();	// Clears list as not to add new objects twice

		for (GameObject dObject : deadObjects) { // loops through the destroyed but displayed items (such as destroyed bases)
			objectsToRender.add(dObject);
		}
		if (currentTruck.isRemove()) {	//If the player is set for removal, respawn
			updateLives();
		}
		switchTrucks(); 
	}

	/**
	 * Renders the objects in "objectsToRender" then clears the list
	 */
	private void renderObjects() {
		for (GameObject object : objectsToRender) {
			object.render(game.batch);
		}
		for (FireTruck truck : firetrucks) {
			truck.render(game.batch);
		}
		
		objectsToRender.clear();
	}

	/**
	 * Add a game object next frame
	 * @param gameObject gameObject to be added
	 */
	public void addGameObject(GameObject gameObject) {
		objectsToAdd.add(gameObject);
	}

	/**
	 * Allows external classes to access the player
	 * @return player
	 */
	public FireTruck getPlayer() {
		return currentTruck;
	}

	/**
	 * Draws all debug objects for one frame
	 */
	private void DrawDebug() {
		for (DebugDraw dObject : debugObjects) {
			dObject.Draw(gamecam.combined);
		}
		debugObjects.clear();
	}

	/**
	 * Draw a debug line
	 * @param start Start of the line
	 * @param end End of the line
	 * @param lineWidth Width of the line
	 * @param colour Colour of the line
	 */
	public void DrawLine(Vector2 start, Vector2 end, int lineWidth, Color colour) {
		if (Kroy.debug) {
			debugObjects.add(new DebugLine(start, end, lineWidth, colour));
		}
	}

	/**
	 * Draw a debug circle (outline)
	 * @param position Centre of the circle
	 * @param radius Radius of the circle
	 * @param lineWidth Width of the outline
	 * @param colour Colour of the line
	 */
	public void DrawCircle(Vector2 position, float radius, int lineWidth, Color colour) {
		if (Kroy.debug) {
			debugObjects.add(new DebugCircle(position, radius, lineWidth, colour));
		}
	}

	/**
	 * Draw a debug rectangle (outline)
	 * @param bottomLefiretrucks Bottom lefiretrucks point of the rectangle
	 * @param dimensions Dimensions of the rectangle (Width, Length)
	 * @param lineWidth Width of the outline
	 * @param colour Colour of the line
	 */
	public void DrawRect(Vector2 bottomLefiretrucks, Vector2 dimensions, int lineWidth, Color colour) {
		if (Kroy.debug) {
			debugObjects.add(new DebugRect(bottomLefiretrucks, dimensions, lineWidth, colour));
		}
	}

	/**
	 * Updates the position of the camera to have the truck centre
	 */
	public void updateCamera() {
		gamecam.position.lerp(new Vector3(currentTruck.getX(),currentTruck.getY(),gamecam.position.z),0.1f);// sets the new camera position based on the current position of the FireTruck
		gamecam.update();
	}

	@Override
	public void resize(int width, int height) {
		gameport.update(width, height);
	}

	@Override
	public void pause() {
		setGameState(GameScreenState.PAUSE);
	}

	@Override
	public void resume() {
		setGameState(GameScreenState.RESUME);
	}

	@Override
	public void hide() {}

	@Override
	public void dispose() {
		Kroy.mainGameScreen = null;
	}

	/**
	 * @param s
	 */
	public void setGameState(GameScreenState s){
	    state = s;
	}

	/**
	 * @param index
	 * @return
	 */
	public GameObject getGameObject(int index) {
		if (index <= (gameObjects.size()-1)) {
			return gameObjects.get(index);
		}else {
			return null;
		}
	}

	/**
	 * @return
	 */
	public List<GameObject> getGameObjects(){
		return gameObjects;
	}
	
	public int getLives() {
		return lives;
	}

	/**
	 * Checks the pause buttons
	 */
	private void clickCheck() {
		//resume button
		pauseWindow.resume.addListener(new ClickListener() {
	    	@Override
	    	public void clicked(InputEvent event, float x, float y) {
	    		pauseWindow.visibility(false);
				resume();
	    	}
	    });

		//exit button
		pauseWindow.exit.addListener(new ClickListener() {
	    	@Override
	    	public void clicked(InputEvent event, float x, float y) {
	    		Gdx.app.exit();
	    	}
	    });
		//menu button
		pauseWindow.menu.addListener(new ClickListener() {
	    	@Override
	    	public void clicked(InputEvent event, float x, float y) {
	    		pauseWindow.visibility(false);
	    		dispose();
	    		game.backToMenu();
	    		return;
	    	}
	    });
	}

	/**
	 * Add one fortress to the count
	 */
	public void addFortress() {
		fortressesCount++;
	}

	/**
	 * Remove one fortress to the count
	 */
	public void removeFortress() {
		fortressesCount--;
	}

	/**
	 * How many fortresses are left?
	 * @return Number of fortresses remaining
	 */
	public int fortressesLeft() {
		return fortressesCount;
	}
	
	/**
	 * Switch to the game over screen
	 * @param won Did the player reach the win state?
	 */
	public void gameOver(boolean won) {
		game.setScreen(new GameOverScreen(game, truckNum, won));
	}

	/**
	 * switch to anothe truck if currenttruck dies
	 */
	public void updateLives() {
		if (lives>1) {
			lives -= 1;
			if(firetrucks.get(0).isAlive()) {
				switchTrucks(0);
			}else if(firetrucks.get(1).isAlive()) {
				switchTrucks(1);
			}else if(firetrucks.get(2).isAlive()) {
				switchTrucks(2);
			}else if(firetrucks.get(3).isAlive()) {
				switchTrucks(3);
			}
		} else {
			gameOver(false);
		}
	}
	
	private void switchTrucks(int n) {
		changeToTruck( firetrucks.get(n));
	}
	
	/**
	 * Check for inputs to switch between trucks.
	 */
	private void switchTrucks() {
		if (Gdx.input.isKeyPressed(Keys.NUM_1)) {
			changeToTruck(truck1);
		}
		if (Gdx.input.isKeyPressed(Keys.NUM_2)) {
			changeToTruck(truck2);
		}
		if (Gdx.input.isKeyPressed(Keys.NUM_3)) {
			changeToTruck(truck3);
		}
		if (Gdx.input.isKeyPressed(Keys.NUM_4)) {
			changeToTruck(truck4);
		}

	}

	/**
	 * Switches the camera to the specified truck.
	 *
	 * @param t The truck to switch to
	 */
	private void changeToTruck(FireTruck t) {
		currentTruck = t;
		
	}

	
	public HUD getHud(){
		return hud;
	}

	public Vector2 getSpawnPosition() {
		return spawnPosition;
	}
	
	
	

}
