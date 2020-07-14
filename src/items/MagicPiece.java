package items;

import main.GameAPI;

public class MagicPiece extends ConsumableItem implements CraftingItem {
	
	public MagicPiece () {
		super ();
		setProperty ("displayName", "Magic Piece");
		setProperty ("maxHealth", "10");
		setHealth (10);
	}
	
	@Override
	public boolean use () {
		GameAPI.getPlayer ().setMana (GameAPI.getPlayer ().getMana () + 20);
		damage (1);
		return true;
	}
	
	@Override
	public boolean useInCrafting () {
		damage (1);
		return true;
	}
}
