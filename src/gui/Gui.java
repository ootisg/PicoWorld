package gui;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import main.GameObject;
import main.GameWindow;
import main.MainLoop;
import main.TextInterface;
import resources.Sprite;

public class Gui extends GameObject {
	public int pauseCooldown;
	public TextInterface textInterface;
	private ItemMenu itemMenu;
	private CraftingMenu craftingMenu;
	public Gui () {
		this.declare (0, 0);
		this.itemMenu = new ItemMenu (0, 0);
		this.craftingMenu = new CraftingMenu ();
		craftingMenu.addItemContainer (itemMenu);
		craftingMenu.declare (0, 96);
		itemMenu.focus ();
		craftingMenu.setHidden (true);
	}
	public void frameEvent () {
		pauseCooldown --;
		if (keyPressed ('E') && pauseCooldown <= 0) {
			MainLoop.pause ();
			pauseCooldown = 10;
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
}