//A container class for sprites

package resources;

public class SpriteContainer {
	//Please alphebitize spritesheets and sprites
	//Spritesheets
	public Spritesheet playerSheet = new Spritesheet ("resources/sprites/walk_sheet.png");
	public Spritesheet playerArmSheet = new Spritesheet ("resources/sprites/arms_sheet.png");
	public Spritesheet swordArmSheet = new Spritesheet ("resources/sprites/swordarmsheet.png");
	public Spritesheet fireballSheet = new Spritesheet ("resources/sprites/fireball_sheet.png");
	//public Spritesheet swordSheet = new Spritesheet ("resources/sprites/swordsheet.png");
	public Spritesheet spellSheet = new Spritesheet ("resources/sprites/spell_sheet.png");
	public Spritesheet textSheet = new Spritesheet ("resources/sprites/text.png");
	//Sprites
	public Sprite playerIdle = new Sprite (playerSheet, new int[] {0, 0, 0, 0}, new int[] {0, 16, 32, 48}, 16, 16);
	public Sprite playerArmsIdle = new Sprite (playerArmSheet, new int[] {0, 0, 0, 0}, new int[] {0, 16, 32, 48}, 16, 16);
	public Sprite[] playerWalkSprites = new Sprite[] {
			new Sprite (playerSheet, new int[] {16, 0, 32, 0}, new int[] {0, 0, 0, 0}, 16, 16),
			new Sprite (playerSheet, new int[] {16, 0, 32, 0}, new int[] {16, 16, 16, 16}, 16, 16),
			new Sprite (playerSheet, new int[] {16, 0, 32, 0}, new int[] {32, 32, 32, 32}, 16, 16),
			new Sprite (playerSheet, new int[] {16, 0, 32, 0}, new int[] {48, 48, 48, 48}, 16, 16)
	};
	public Sprite[] playerArmSprites = new Sprite[] {
			new Sprite (playerArmSheet, new int[] {16, 0, 32, 0}, new int[] {0, 0, 0, 0}, 16, 16),
			new Sprite (playerArmSheet, new int[] {16, 0, 32, 0}, new int[] {16, 16, 16, 16}, 16, 16),
			new Sprite (playerArmSheet, new int[] {16, 0, 32, 0}, new int[] {32, 32, 32, 32}, 16, 16),
			new Sprite (playerArmSheet, new int[] {16, 0, 32, 0}, new int[] {48, 48, 48, 48}, 16, 16)
	};
	public Sprite[] swordArmSprites = new Sprite[] {
			new Sprite (swordArmSheet, new int[] {0, 16, 32}, new int[] {0, 0, 0}, 16, 16),
			new Sprite (swordArmSheet, new int[] {0, 16, 32}, new int[] {16, 16, 16}, 16, 16),
			new Sprite (swordArmSheet, new int[] {0, 16, 32}, new int[] {32, 32, 32}, 16, 16),
			new Sprite (swordArmSheet, new int[] {0, 16, 32}, new int[] {48, 48, 48}, 16, 16)
	};
	/*public Sprite[] swordSprites = new Sprite[] {
			new Sprite (swordSheet, new int[] {0, 20, 40}, new int[] {0, 0, 0}, 20, 20),
			new Sprite (swordSheet, new int[] {0, 20, 40}, new int[] {20, 20, 20}, 20, 20),
			new Sprite (swordSheet, new int[] {0, 20, 40}, new int[] {40, 40, 40}, 20, 20),
			new Sprite (swordSheet, new int[] {0, 20, 40}, new int[] {60, 60, 60}, 20, 20)
	};*/
	public Sprite[] fireballSprites = new Sprite[] {
			new Sprite (fireballSheet, new int[] {0, 16, 32, 48}, new int[] {0, 0, 0, 0}, 16, 16),
			new Sprite (fireballSheet, new int[] {0, 16, 32, 48}, new int[] {16, 16, 16, 16}, 16, 16)
	};
	public Sprite spellSprites = new Sprite (spellSheet, 16, 16);
	public Sprite textSprite = new Sprite (textSheet, 8, 8);
	public Sprite underline = new Sprite ("resources/sprites/underline.png");
	public Sprite frostElemental = new Sprite ("resources/sprites/frostElemental.png");
	public Sprite frostEyes = new Sprite ("resources/sprites/frostEyes.png");
	public Sprite itemBorder = new Sprite ("resources/sprites/itemborder.png");
	public Sprite selectedBorder = new Sprite ("resources/sprites/selectedborder.png");
	public Sprite itemUi = new Sprite ("resources/sprites/itemui.png");
	public SpriteContainer () {
		
	}
}
