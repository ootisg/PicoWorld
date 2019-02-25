package main;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;

import gameObjects.FlameElemental;
import gameObjects.FrostElemental;
import gameObjects.Particle;
import gameObjects.Player;
import gameObjects.Saveable;
import gameObjects.TestEnemy;
import items.*;

public class GameCode extends GameAPI {
	private GameWindow gameWindow;
	public void initialize () {
		//Runs on initialization
		//MainLoop.getWindow ().setResolution (480, 480);
		try {
			Saveable.loadData ("saves/save.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println ("HIA");
		try {
			getRoom ().loadRoom ("resources/maps/testmap3.cmf");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getPlayer ().declare (256, 32);
		new TestEnemy ().declare (128, 32);
		new FlameElemental ().declare (32, 32);
		new ItemDrop (new GoldSword ()).declare (128, 128);
		new ItemDrop (new Apple ()).declare (128, 150);
		/*for (int i = 0; i < 576; i ++) {
		 //MAKES APPLES
			for (int j = 0; j < 576; j ++) {
				new ItemDrop (new Apple ()).declare (429 + i * 1, 601 + j * 1);
			}
		}*/
		for (int i = 0; i < 3; i ++) {
			new ItemDrop (new Apple ()).declare (429 + i * 16, 200);
			new ItemDrop (new GoldSword ()).declare (429 + i * 16, 232);
			new ItemDrop (new SilverSword ()).declare (429 + i * 16, 264);
			new ItemDrop (new MythrilSword ()).declare (429 + i * 16, 296);
			new ItemDrop (new GoldBar ()).declare (429 + i * 16, 328);
			new ItemDrop (new SilverBar ()).declare (429 + i * 16, 360);
			new ItemDrop (new MythrilBar ()).declare (429 + i * 16, 392);
			new ItemDrop (new WoodPlanks ()).declare (429 + i * 16, 424);
			new ItemDrop (new WoodPlanks ()).declare (429 + i * 16, 440);
		}
		MainLoop.getWindow ().setResolution (512, 288);
		MainLoop.getWindow ().setSize (1024, 576);
	}
	public void gameLoop () {
		getRoom ().frameEvent ();
		//Saveable.printSaves ();
		//Runs once per frame
	}
}