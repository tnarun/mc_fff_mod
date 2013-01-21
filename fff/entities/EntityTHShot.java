package fff.entities;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class EntityTHShot extends Entity {
	protected float shotSize;
	public EntityLiving userEntity;
	public Entity shootingEntity;
	
	public double shotSpeed;
	public double shotMaxSpeed;
	public double shotAddSpeed;
	protected int shotDamage;
		
	public double accelerationX;
	public double accelerationY;
	public double accelerationZ;
	
	public EntityTHShot(World world) {
		super(world);
		setSize(1.0F, 1.0F);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double d) {
		double d1 = this.boundingBox.getAverageEdgeLength() * 4.0D;
		d1 *= 64.0D;
		return d < d1 * d1;
	}

	public EntityTHShot(World world, EntityLiving entityUser, Entity entity,
			double xVector, double yVector, double zVector, double firstSpeed,
			double maxSpeed, double addSpeed, int damage, int dead) {
		super(world);
		this.userEntity = entityUser;
		this.shootingEntity = entity;
		this.shotSpeed = firstSpeed;
		this.shotDamage = damage;
		this.shotSize = 1.0F;
		setSize(0.5F, 0.5F);
		setLocationAndAngles(entity.posX, entity.posY, entity.posZ,
				entity.rotationYaw, entity.rotationPitch);
		setPosition(entity.posX + xVector * 0.2D, entity.posY + yVector
				+ entityUser.getEyeHeight() - 0.1000000014901161D - 0.15D,
				entity.posZ + zVector * 0.2D);
		this.yOffset = 0.0F;

		this.motionX = (xVector * firstSpeed);
		this.motionY = (yVector * firstSpeed);
		this.motionZ = (zVector * firstSpeed);
		this.accelerationX = (xVector * maxSpeed * addSpeed);
		this.accelerationY = (yVector * maxSpeed * addSpeed);
		this.accelerationZ = (zVector * maxSpeed * addSpeed);
		this.shotMaxSpeed = maxSpeed;
		this.shotAddSpeed = addSpeed;
		setDeadTime(dead);
	}

	public EntityTHShot(World world, EntityLiving entityLiving, double xVector,
			double yVector, double zVector, double speed, int damage) {
		this(world, entityLiving, entityLiving, xVector, yVector, zVector,
				speed, speed, 1.0D, damage, 1200);
	}

	public EntityTHShot(World world, EntityLiving entityLiving, double xVector,
			double yVector, double zVector, double firstSpeed, double maxSpeed,
			double addSpeed, int damage) {
		this(world, entityLiving, entityLiving, xVector, yVector, zVector,
				firstSpeed, maxSpeed, addSpeed, damage, 1200);
	}

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(19, new Integer(0));
		this.dataWatcher.addObject(18, new Integer(0));
	}

	public double getSpeed() {
		return MathHelper.sqrt_double(this.motionX * this.motionX
				+ this.motionY * this.motionY + this.motionZ * this.motionZ);
	}

	@Override
	public void onUpdate() {
		if ((!this.worldObj.isRemote)
				&& ((this.shootingEntity == null) || (this.shootingEntity.isDead))) {
			setDead();
			return;
		}

		super.onUpdate();

		if (this.ticksExisted >= getDeadTime()) {
			setDead();
		}

		Vec3 vec3d = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX,
				this.posY, this.posZ);
		Vec3 vec3d1 = this.worldObj.getWorldVec3Pool().getVecFromPool(
				this.posX + this.motionX, this.posY + this.motionY,
				this.posZ + this.motionZ);
		MovingObjectPosition movingObjectPosition = this.worldObj
				.rayTraceBlocks(vec3d, vec3d1);
		vec3d = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX,
				this.posY, this.posZ);
		vec3d1 = this.worldObj.getWorldVec3Pool().getVecFromPool(
				this.posX + this.motionX, this.posY + this.motionY,
				this.posZ + this.motionZ);
		if (movingObjectPosition != null) {
			vec3d1 = this.worldObj.getWorldVec3Pool().getVecFromPool(
					movingObjectPosition.hitVec.xCoord,
					movingObjectPosition.hitVec.yCoord,
					movingObjectPosition.hitVec.zCoord);
		}
		Entity entity = null;
		List<?> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(
				this,
				this.boundingBox.addCoord(this.motionX, this.motionY,
						this.motionZ).expand(this.shotSize * 2.0D,
						this.shotSize * 2.0D, this.shotSize * 2.0D));
		double d = 0.0D;
		for (int j = 0; j < list.size(); j++) {
			Entity entity1 = (Entity) list.get(j);

			if ((entity1.canBeCollidedWith())
					&& (!entity1.isEntityEqual(this.userEntity))
					&& (!entity1.isEntityEqual(this.shootingEntity))) {
				AxisAlignedBB axisalignedbb = entity1.boundingBox.expand(
						this.shotSize, this.shotSize, this.shotSize);
				MovingObjectPosition movingObjectPosition1 = axisalignedbb
						.calculateIntercept(vec3d, vec3d1);
				if (movingObjectPosition1 != null) {
					double d1 = vec3d
							.squareDistanceTo(movingObjectPosition1.hitVec);
					if ((d1 < d) || (d == 0.0D)) {
						entity = entity1;
						d = d1;
					}
				}
			}
		}
		if (entity != null) {
			movingObjectPosition = new MovingObjectPosition(entity);
		}
		if (movingObjectPosition != null) {
			onImpact(movingObjectPosition);
		}

		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;
		float f = MathHelper.sqrt_double(this.motionX * this.motionX
				+ this.motionZ * this.motionZ);
		this.rotationYaw = ((float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / 3.141592741012573D));
		for (this.rotationPitch = ((float) (Math.atan2(this.motionY, f) * 180.0D / 3.141592741012573D)); this.rotationPitch
				- this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
			;
		while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
			this.prevRotationPitch += 360.0F;
		while (this.rotationYaw - this.prevRotationYaw < -180.0F)
			this.prevRotationYaw -= 360.0F;
		while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
			this.prevRotationYaw += 360.0F;
		this.rotationPitch = (this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F);
		this.rotationYaw = (this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F);

		if (getSpeed() < this.shotMaxSpeed) {
			this.motionX += this.accelerationX;
			this.motionY += this.accelerationY;
			this.motionZ += this.accelerationZ;
		}

		setPosition(this.posX, this.posY, this.posZ);
	}

	protected void onImpact(MovingObjectPosition movingobjectposition) {
		if (!this.worldObj.isRemote) {
			Entity hitEntity = movingobjectposition.entityHit;

			if (hitEntity != null) {
				if (!(hitEntity instanceof EntityTHShot)) {
					if (!hitEntity.attackEntityFrom(DamageSource
							.causeIndirectMagicDamage(this, this.userEntity),
							this.shotDamage))
						;
					setDead();
				} else {
					EntityTHShot entityTHShot = (EntityTHShot) hitEntity;
					if (getShotStrength() != entityTHShot.getShotStrength()) {
						if (getShotStrength() < entityTHShot.getShotStrength()) {
							setDead();
						} else {
							entityTHShot.setDead();
						}
					}
				}
			} else
				setDead();
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbtTagCompound) {
		nbtTagCompound.setTag("direction", newDoubleNBTList(new double[] {
				this.motionX, this.motionY, this.motionZ }));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbtTagCompound) {
		if (nbtTagCompound.hasKey("direction")) {
			NBTTagList var2 = nbtTagCompound.getTagList("direction");
			this.motionX = ((NBTTagDouble) var2.tagAt(0)).data;
			this.motionY = ((NBTTagDouble) var2.tagAt(1)).data;
			this.motionZ = ((NBTTagDouble) var2.tagAt(2)).data;
		} else {
			setDead();
		}
	}

	public int getShotStrength() {
		return 10;
	}

	@Override
	public boolean canBeCollidedWith() {
		return false;
	}

	@Override
	public float getCollisionBorderSize() {
		return 1.0F;
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, int i) {
		setBeenAttacked();
		return false;
	}

	public void setDeadTime(int time) {
		this.dataWatcher.updateObject(18, Integer.valueOf(time));
	}

	public int getDeadTime() {
		return this.dataWatcher.getWatchableObjectInt(18);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getShadowSize() {
		return 0.0F;
	}

	@Override
	public float getBrightness(float f) {
		return 1.0F;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float f) {
		return 15728880;
	}
}
