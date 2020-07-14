package gui;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import gameObjects.GlobalSave;
import main.GameObject;
import main.GameWindow;
import main.MainLoop;
import main.TextInterface;
import resources.Sprite;
import resources.Spritesheet;

public class Gui extends GameObject {
	public int pauseCooldown;
	public TextInterface textInterface;
	private ItemMenu itemMenu;
	private CraftingMenu craftingMenu;
	private StatsWindow statsWindow;
	private Textbox tbox;
	private MagicContainer magicContainer;
	public Gui () {
		this.declare (0, 0);
		this.itemMenu = new ItemMenu (0, 0);
		this.craftingMenu = new CraftingMenu ();
		this.statsWindow = new StatsWindow ();
		craftingMenu.addItemContainer (itemMenu);
		craftingMenu.declare (0, 96);
		statsWindow.declare (0, 0);
		itemMenu.focus ();
		craftingMenu.setHidden (true);
		this.setPersistent (true);
		magicContainer = new MagicContainer ();
		magicContainer.declare (494, 2);
		//tbox = new Textbox (new Spritesheet ("resources/sprites/gui_background.png"), 8, 4, "HELLO HOW ARE YOU TODAY MY NAME IS DAT BOIIIIIIIIIIIIIIIII");
		//tbox.declare (32, 128);
		//tbox.setHidden (true);
	}
	public void frameEvent () {
		pauseCooldown --;
		if (keyPressed ('E') && pauseCooldown <= 0) {
			MainLoop.pause ();
			itemMenu.setHidden (false);
			pauseCooldown = 10;
		}
		if (keyPressed ('\\')) {
			getSave ().writeToFile ();
		}
	}
	public void pauseEvent () {
		pauseCooldown --;
		if (keyPressed (KeyEvent.VK_TAB)) {
			if (itemMenu.hasFocus ()) {
				itemMenu.unfocus ();
				craftingMenu.focus ();
				craftingMenu.setHidden (false);
			} else {
				itemMenu.focus ();
				craftingMenu.unfocus ();
				craftingMenu.setHidden (true);
			}
		}
		if (keyPressed ('E') && pauseCooldown <= 0) {
			itemMenu.focus ();
			craftingMenu.unfocus ();
			craftingMenu.setHidden (true);
			pauseCooldown = 10;
			MainLoop.resume ();
		}
	}
	public void drawText (String text, int x, int y) {
		for (int i = 0; i < text.length (); i ++) {
			textInterface.drawChar (text.charAt (i), x + i * 8, y);
		}
	}
	public ItemMenu getItemMenu () {
		return itemMenu;
	}
	public MagicContainer getMagicContainer () {
		return magicContainer;
	}
}