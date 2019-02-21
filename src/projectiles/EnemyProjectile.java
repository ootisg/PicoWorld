package projectiles;

import main.GameObject;

public abstract class EnemyProjectile extends Projectile {
	
	@Override
	public void projectileFrame () {
		if (isColliding (getPlayer ())) {
			hitEvent (getPlayer ());
		}
	}
	
	public abstract void hitEvent (GameObject target);
}
