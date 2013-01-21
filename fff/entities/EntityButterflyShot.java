package fff.entities;

import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;

public class EntityButterflyShot extends EntityTHShot {
	public EntityButterflyShot(World par1World) {
		super(par1World);
	}

	public EntityButterflyShot(World par1World, EntityLiving entityliving,
			double d, double d1, double d2, double speed, int damage) {
		super(par1World, entityliving, d, d1, d2, speed, damage);
		this.shotSize = 0.2F;
	}

	public float getWingAngle() {
		return (float) Math.sin(this.ticksExisted / 3.0F) * 45.0F;
	}

	public float getVariationColor() {
		return (float) Math.sin(this.ticksExisted / 3.0F) / 3.0F + 0.5F;
	}
}
