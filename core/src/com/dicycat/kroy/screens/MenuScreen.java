package com.dicycat.kroy.screens;
  
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.scenes.FireTruckSelectionScene;
import com.dicycat.kroy.scenes.HUD;
import com.dicycat.kroy.scenes.LoadWindow;
import com.dicycat.kroy.scenes.OptionsWindow;
//ADD_CONTROL_SCREEN_2 - START OF MODIFICATION - NP Studios - Jordan Spooner ---------------
import com.dicycat.kroy.scenes.ControlsWindow;
//ADD_CONTROL_SCREEN_2 - END OF MODIFICATION - NP Studios - Jordan Spooner -----------------

/**
 * Main Menu screen
 * 
 * @author Michele Imbriani
 *
 */
public class MenuScreen implements Screen{
  
  private Kroy game; 
  private OrthographicCamera gamecam;
  private Viewport gameport;
  private Texture playButton, 
  	playButtonActive, 
  	optionsButton, 
  	optionsButtonActive, 
  	exitButton,
  	exitButtonActive,
  	loadGameButton,
  	loadGameButtonActive,
 	// ADD_CONTROL_SCREEN_3 - START OF MODIFICATION - NP Studios - Jordan Spooner ---------------
  	controlsButton,
  	controlsButtonActive,
 	// ADD_CONTROL_SCREEN_3 - END OF MODIFICATION - NP Studios - Jordan Spooner -----------------
  	background;
  
  private Stage stage;
  
  private OptionsWindow optionsWindow;
  private LoadWindow loadWindow;
  private FireTruckSelectionScene fireTruckSelector;
	//ADD_CONTROL_SCREEN_4 - START OF MODIFICATION - NP Studios - Jordan Spooner ---------------
  private ControlsWindow controlsWindow;
	//ADD_CONTROL_SCREEN_4 - END OF MODIFICATION - NP Studios - Jordan Spooner -----------------
  
  public static Music music = Gdx.audio.newMusic(Gdx.files.internal("gamemusic.mp3"));
  public static float musicVolume = 0.4f;

  //coordinates for Play and Exit buttons 
  private int buttonWidth = 250;
  private int buttonHeight = 50;
  private int xAxisCentred = (Kroy.width/2) - (buttonWidth/2);
  private int playButtonY = (Kroy.height/2)+75;
  private int optionsButtonY = (Kroy.height/2)-75;
  private int loadgameButtonY = (Kroy.height/2);
  private int exitButtonY = (Kroy.height/2)-225;
//ADD_CONTROL_SCREEN_5 - START OF MODIFICATION - NP Studios - Jordan Spooner ---------------
  private int controlsButtonY = (Kroy.height/2)-150;
//ADD_CONTROL_SCREEN_5 - END OF MODIFICATION - NP Studios - Jordan Spooner -----------------
  
  private Pixmap pm = new Pixmap(Gdx.files.internal("handHD2.png")); //cursor
  private int xHotSpot = pm.getWidth() / 3;	//where the cursor's aim is 
  private int yHotSpot = 0;

  private boolean currentlyRunningGame = false;

  /**
   *  Used to define the current state of the screen, 
   *  MAINMENU is used mostly but then TRUCKSELECT used when the "NewGame" button has been pressed  
   * 
   *
   */
  public static enum MenuScreenState {
	  MAINMENU,
	  TRUCKSELECT,
	  OPTIONS,
	  LOAD,
	// ADD_CONTROL_SCREEN_6 - START OF MODIFICATION - NP Studios - Jordan Spooner ---------------
	  CONTROLS
	// ADD_CONTROL_SCREEN_6 - END OF MODIFICATION - NP Studios - Jordan Spooner -----------------
  }
  
  public MenuScreenState state = MenuScreenState.MAINMENU;

  public MenuScreen(Kroy game) { 
	  this.game = game; 
	  exitButton = new Texture("quit.png"); 	//in later stages we could also have buttonActive and buttonInactive
	  exitButtonActive = new Texture("quitactive.png");
	  optionsButton = new Texture("options.png");
	  optionsButtonActive = new Texture("optionsActive.png");
	  playButton = new Texture("newgame.png");
	  playButtonActive = new Texture("newgameActive.png");
	  // MENU_REFACTOR_1 - START OF MODIFICATION  - NP STUDIOS - LUCY IVATT
	  // Added buttons to go to the LoadWindow and removed minigame button
	  loadGameButton = new Texture("loadgame.png");
	  loadGameButtonActive = new Texture("loadgameActive.png");
	  // MENU_REFACTOR_1 - END OF MODIFICATION  - NP STUDIOS - LUCY IVATT
	  background = new Texture ("fireforce.jpg");
	// ADD_CONTROL_SCREEN_7 - START OF MODIFICATION - NP Studios - Jordan Spooner ---------------
	  controlsButton = new Texture("controls.png");
	  controlsButtonActive = new Texture("ControlsActive.png");
	// ADD_CONTROL_SCREEN_7 - END OF MODIFICATION - NP Studios - Jordan Spooner -----------------
	  
	  gamecam = new OrthographicCamera();
	  gameport = new FitViewport(Kroy.width, Kroy.height, gamecam);
	  stage = new Stage(gameport);
	  
	  fireTruckSelector = new FireTruckSelectionScene(game);
	  fireTruckSelector.visibility(false);
	  
	  music.play();
	  music.setLooping(true);  
	  music.setVolume(musicVolume);  
	  
	  optionsWindow = new OptionsWindow(game);
	  // MENU_REFACTOR_2 - START OF MODIFICATION  - NP STUDIOS - LUCY IVATT
	  // initializes a LoadWindow instance.
	  loadWindow = new LoadWindow(game);
	  // MENU_REFACTOR_2 - END OF MODIFICATION  - NP STUDIOS - LUCY IVATT

	  optionsWindow.visibility(false);
	  
	// ADD_CONTROL_SCREEN_8 - START OF MODIFICATION - NP Studios - Jordan Spooner ---------------
	  controlsWindow = new ControlsWindow(game);
	  controlsWindow.visibility(false);
	// ADD_CONTROL_SCREEN_8 - END OF MODIFICATION - NP Studios - Jordan Spooner -----------------
	  
  }
  
  @Override 
  public void show() {}
  
  /**
   *	Enum allows to make the MenuScreen behave differently depending 
   *	on whether it's in mainMenu, Options or fireTruckSelection
   */
  @Override 
  public void render(float delta) { 
	  
	  switch(state) {
		  case MAINMENU:	// Display all buttons and the main menu		  
			  stage.act();	//allows the stage to interact with user input
			  
			  game.batch.setProjectionMatrix(gamecam.combined);
			  game.batch.begin();
			  
			  Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, xHotSpot, yHotSpot));
			  game.batch.draw(background, 0, 0);
			 
			  game.batch.draw(loadGameButton, xAxisCentred, loadgameButtonY, buttonWidth, buttonHeight);
			
			
			  //for play button: checks if the position of the cursor is inside the coordinates of the button
			  if(( (Gdx.input.getX() < (xAxisCentred + buttonWidth)) && (Gdx.input.getX() > xAxisCentred) ) && ( (Kroy.height - Gdx.input.getY() > playButtonY ) && (Kroy.height - Gdx.input.getY() < (playButtonY + buttonHeight)) ) ){
				  game.batch.draw(playButtonActive, xAxisCentred, playButtonY, buttonWidth, buttonHeight);
				  if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
					  this.dispose();
					  game.batch.end();
					  fireTruckSelector.visibility(true);// display the truck selection window
					  setGameState(MenuScreenState.TRUCKSELECT);// set the game state to run and run the selection screen code
					  return;
				  }
			  } else {
				  game.batch.draw(playButton, xAxisCentred, playButtonY, buttonWidth, buttonHeight);
			  }
			  
			//for exit button
			  if(( (Gdx.input.getX() < (xAxisCentred + buttonWidth)) && (Gdx.input.getX() > xAxisCentred) ) && ( (Kroy.height - Gdx.input.getY() > exitButtonY ) && (Kroy.height - Gdx.input.getY() < (exitButtonY + buttonHeight)) ) ){
				  game.batch.draw(exitButtonActive, xAxisCentred, exitButtonY, buttonWidth, buttonHeight);
				  if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
					  Gdx.app.exit();
				  }
			  } else {
				  game.batch.draw(exitButton, xAxisCentred, exitButtonY, buttonWidth, buttonHeight);
			  }
				
			  //for minigame button
			  if(( (Gdx.input.getX() < (xAxisCentred + buttonWidth)) && (Gdx.input.getX() > xAxisCentred) ) && ( (Kroy.height - Gdx.input.getY() > loadgameButtonY) && (Kroy.height - Gdx.input.getY() < (loadgameButtonY + buttonHeight)) ) ){
				  game.batch.draw(loadGameButtonActive, xAxisCentred, loadgameButtonY, buttonWidth, buttonHeight);
				  if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
					  // MENU_REFACTOR_3 - START OF MODIFICATION  - NP STUDIOS - LUCY IVATT
					  // Sets menuscreens state to the load window, removed old minigame initialization code.
					  setGameState(MenuScreenState.LOAD);
					  // MENU_REFACTOR_3 - END OF MODIFICATION  - NP STUDIOS - LUCY IVATT
						  }
					  } else {
						  game.batch.draw(loadGameButton, xAxisCentred, loadgameButtonY, buttonWidth, buttonHeight);
					  }
	
						  //for options button
			  if(( (Gdx.input.getX() < (xAxisCentred + buttonWidth)) && (Gdx.input.getX() > xAxisCentred) ) && ( (Kroy.height - Gdx.input.getY() > optionsButtonY ) && (Kroy.height - Gdx.input.getY() < (optionsButtonY + buttonHeight)) ) ){
				  game.batch.draw(optionsButtonActive, xAxisCentred, optionsButtonY, buttonWidth, buttonHeight);
				  if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
					  optionsWindow.visibility(true);
					  setGameState(MenuScreenState.OPTIONS);
				  }
			  } else {
				  game.batch.draw(optionsButton, xAxisCentred, optionsButtonY, buttonWidth, buttonHeight);
			  }
			  
		// ADD_CONTROL_SCREEN_9 - START OF MODIFICATION - NP Studios - Jordan Spooner ---------------
			//for controls button
			  if(( (Gdx.input.getX() < (xAxisCentred + buttonWidth)) && (Gdx.input.getX() > xAxisCentred) ) && ( (Kroy.height - Gdx.input.getY() > controlsButtonY ) && (Kroy.height - Gdx.input.getY() < (controlsButtonY + buttonHeight)) ) ){
				  game.batch.draw(controlsButtonActive, xAxisCentred, controlsButtonY, buttonWidth, buttonHeight);
				  if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
					  controlsWindow.visibility(true);
					  setGameState(MenuScreenState.CONTROLS);
				  }
			  } else {
				  game.batch.draw(controlsButton, xAxisCentred, controlsButtonY, buttonWidth, buttonHeight);
			  }
		// ADD_CONTROL_SCREEN_9 - END OF MODIFICATION - NP Studios - Jordan Spooner -----------------
			  
			  
			  game.batch.end();
				  
			  break;
		  case TRUCKSELECT: // Ran when the new game button pressed
			  Gdx.input.setInputProcessor(fireTruckSelector.stage);
			  fireTruckSelector.stage.act();
			  fireTruckSelector.stage.draw();
			  clickCheck();//Checks for any button presses
			  break;
		  case OPTIONS:
			  Gdx.input.setInputProcessor(optionsWindow.stage);
			  optionsWindow.stage.act();
			  optionsWindow.stage.draw();
			  optionsWindow.clickCheck(true);
			  break;
		  // MENU_REFACTOR_4 - START OF MODIFICATION  - NP STUDIOS - LUCY IVATT
		  // Created a load case which sets the correct input processor and draws and renders the correct screen.
		  case LOAD:
		  	Gdx.input.setInputProcessor(loadWindow.stage);
		  	loadWindow.stage.act();
		  	loadWindow.stage.draw();
		  	clickCheck();
		  	break;
		  // MENU_REFACTOR_4 - END OF MODIFICATION  - NP STUDIOS - LUCY IVATT
	// ADD_CONTROL_SCREEN_10 - START OF MODIFICATION - NP Studios - Jordan Spooner ---------------
		  case CONTROLS:
			  Gdx.input.setInputProcessor(controlsWindow.stage);
			  controlsWindow.stage.act();
			  controlsWindow.stage.draw();
			  controlsWindow.clickCheck();
			  break;
	// ADD_CONTROL_SCREEN_10 - END OF MODIFICATION - NP Studios - Jordan Spooner -----------------
		  }
  	}
  
	/**
	 * @param state
	 */
	public void setGameState(MenuScreenState state){
	    this.state = state;
	}
  
	/**
	 * Checks if any of the buttons have been pressed
	 * and then starts the game with the buttons corresponding difficulty
	 */

	// DIFFICULTY_7 - START OF MODIFICATION - NP STUDIOS - BRUNO DAVIES
	public void clickCheck() {
		//Truck 1 Selected
		fireTruckSelector.difficultyButtonEasy.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				startGame(0, 0);//Game begun with easy difficulty
				HUD.setScore(100000);
			}
		});
		//Truck 2 Selected
		fireTruckSelector.difficultyButtonMedium.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

				startGame(0, 1);//Game begun with medium difficulty
				HUD.setScore(100000);
			}
		});

		//Truck 3 Selected
		fireTruckSelector.difficultyButtonHard.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				startGame(0, 2);//Game begun with 2 (Damage) as the truck selected
				HUD.setScore(100000);
			}
		});

		// MENU_REFACTOR_5 - START OF MODIFICATION  - NP STUDIOS - LUCY IVATT
		// Added input handling code for the load buttons and the back button on the load window.
		LoadWindow.back.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Kroy.mainMenuScreen.state = MenuScreen.MenuScreenState.MAINMENU;
			}
		});

		//music page
		//playStopMusic button
		LoadWindow.load1.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				resumeGame(1);
			}
		});
		//playStopMusic button
		LoadWindow.load2.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				resumeGame(2);
			}
		});
		//volumeDown button
		LoadWindow.load3.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				resumeGame(3);
			}
		});
		// MENU_REFACTOR_5 - END OF MODIFICATION  - NP STUDIOS - LUCY IVATT
		// DIFFICULTY_7 - END OF MODIFICATION - NP STUDIOS - BRUNO DAVIES

	}


	/**
	 * Calls function in Kroy to start a new game and ensures only 1 GameScreen is currenly running - Updated by NP STUDIOS
 	 */
	public void startGame(int saveSlot, int difficultyChosen) {
		 if (!currentlyRunningGame) {	// Checks if a new GameScreen is currently running and either makes one or ignores the commands
			 currentlyRunningGame = true; // Makes sure that only one GameScreen is opened at once
			 game.newGame(saveSlot, difficultyChosen); // Calls the function in Kroy to start a new game
		 }
	}

	/**
	 * Calls function in Kroy to start a game associated with a saveslot and makes sure only 1 is running at once - NP STUDIOS
	 * @param saveSlot
	 */
	public void resumeGame(int saveSlot) {
		if (!currentlyRunningGame) {	// Checks if a new GameScreen is currently running and either makes one or ignores the commands
			currentlyRunningGame = true; // Makes sure that only one GameScreen is opened at once
			game.loadGame(saveSlot); // Calls the function in Kroy to start a new game
		}
	}

	/**
	 * Run the minigame
 	 */
	public void startMinigame() {
		 if (!currentlyRunningGame) {	// Checks if a new GameScreen is currently running and either makes one or ignores the commands
			 currentlyRunningGame = true; // Makes sure that only one GameScreen is opened at once
			 game.newMinigame(true); // Calls the function in Kroy to start a new minigame
		 }
	} 
  
  
  /**
   * @param state
   */
  public void setCurrentlyRunningGame(boolean state) {
	  currentlyRunningGame = state;
  }
  
  /**
   *
   */
  @Override 
  public void resize(int width, int height) {
	  gameport.update(width, height);
  }
  
  @Override 
  public void pause() {}
  
  @Override 
  public void resume() {}
  
  @Override 
  public void hide() {}
  
  @Override 
  public void dispose() {}
 }

