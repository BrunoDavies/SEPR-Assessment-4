package com.dicycat.kroy.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dicycat.kroy.Kroy;

/**
 * Window for selecting FireTruck type
 * 
 * @author Luke Taylor
 *
 */
public class FireTruckSelectionScene {

	public Stage stage;
	public Table table = new Table();
	private SpriteBatch sb;
	private NinePatchDrawable background = new NinePatchDrawable(new NinePatch(new Texture("Grey.png"), 3, 3, 3, 3));
	
    private Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

	// DIFFICULTY_8 - START OF MODIFICATION - NP STUDIOS - BRUNO DAVIES
	//Labels initialised, labelled and stylised
    public Label truckLabel1 = new Label("Speed", skin);
    public Label truckLabel2 = new Label("Speed + Damage", skin);
    public Label truckLabel3 = new Label("Damage", skin);
    public Label truckLabel4 = new Label("Capacity + Range", skin);
    public Label truckLabel5 = new Label("Capacity", skin);
    public Label truckLabel6 = new Label("Range", skin);

	//Buttons for difficulty select
	public TextButton difficultyButtonEasy = new TextButton("Easy", skin);
	public TextButton difficultyButtonMedium = new TextButton("Medium", skin);
	public TextButton difficultyButtonHard = new TextButton("Hard", skin);
	// DIFFICULTY_8 - END OF MODIFICATION - NP STUDIOS - BRUNO DAVIES




	private float width = Gdx.graphics.getWidth();
    private float centre = width* 0.7f;
    
    
	/**
	 * @param game Kroy instance
	 */
	public FireTruckSelectionScene(Kroy game) {
		sb = game.batch;
		Viewport viewport = new ScreenViewport(new OrthographicCamera());
		stage = new Stage(viewport, sb);

		table.setBackground(background);
		
		// Images + Buttons added to the screen
		table.add(new Image(new Texture("fireTruck1.png")));
		table.add(new Image(new Texture("fireTruck2.png")));
		table.add(new Image(new Texture("fireTruck3.png")));
		table.row();
		table.add(truckLabel1);
	    table.add(truckLabel2);
	    table.add(truckLabel3);
		
	    table.row();

		table.add(new Image(new Texture("fireTruck4.png"))).padTop(10f);
		table.add(new Image(new Texture("fireTruck5.png"))).padTop(10f);
		table.add(new Image(new Texture("fireTruck6.png"))).padTop(10f);
	    table.row();
	    table.add(truckLabel4);
	    table.add(truckLabel5);
	    table.add(truckLabel6);
		// DIFFICULTY_9 - START OF MODIFICATION - NP STUDIOS - BRUNO DAVIES
		table.row();
		table.add(difficultyButtonEasy).width(centre/3.0f).padTop(100f);
		table.add(difficultyButtonMedium).width(centre/3.0f).padTop(100f);
		table.add(difficultyButtonHard).width(centre/3.0f).padTop(100f);

		// DIFFICULTY_9 - END OF MODIFICATION - NP STUDIOS - BRUNO DAVIES


		table.setFillParent(true);
	    stage.addActor(table);
	    
	    
	}
	
	/**
	 * @param state		Makes the window visible or hidden
	 */
	public void visibility(boolean state){
		this.table.setVisible(state);
	}
}
