package gui;

import java.util.ArrayList;

import items.GameItem;

public class CraftingRecipe {
	private ArrayList<GameItem> ingredients;
	private GameItem result;
	
	public CraftingRecipe (GameItem result) {
		this.result = result;
		ingredients = new ArrayList<GameItem> ();
	}
	public void addIngredient (GameItem ingredient) {
		ingredients.add (ingredient);
	}
	public ArrayList<GameItem> getIngredients () {
		return ingredients;
	}
	public GameItem getResult () {
		return result;
	}
}