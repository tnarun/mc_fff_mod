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
	protected String texture;
	protected float shotSize;
	public EntityLiving userEntity;
	public Entity shootingEntity;
	public double shotSpeed;
	public double shotMaxSpeed;
	public double shotAddSpeed;
	public double shotGravity;
	boolean isGravity;
	protected int shotDamage;
	public int color;
	public int deadTime;
	public double xVec;
	public double yVec;
	public double zVec;
	protected int xTile;
	protected int yTile;
	protected int zTile;
	protected int inTile;
	protected boolean inGround;
	protected int ticksAlive;
	protected int ticksInAir;
	public double accelerationX;
	public double accelerationY;
	public double accelerationZ;
	public static double GRAVITY_DEFAULT = 0.03D;

	public EntityTHShot(World world) {
		super(world);
		this.xTile = -1;
		this.yTile = -1;
		this.zTile = -1;
		this.inTile = 0;
		this.inGround = false;
		this.ticksInAir = 0;
		setSize(1.0F, 1.0F);
	}

	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double d) {
		double d1 = this.boundingBox.getAverageEdgeLength() * 4.0D;
		d1 *= 64.0D;
		return d < d1 * d1;
	}

	public EntityTHShot(World world, EntityLiving entityUser, Entity entity,
			double xVector, double yVector, double zVector, double firstSpeed,
			double maxSpeed, double addSpeed, double gravity, int damage,
			int c, int dead) {
		super(world);
		this.userEntity = entityUser;
		this.shootingEntity = entity;
		this.shotSpeed = firstSpeed;
		this.isGravity = false;
		this.shotGravity = gravity;
		this.shotDamage = damage;
		this.color = c;
		this.deadTime = dead;
		this.xTile = -1;
		this.yTile = -1;
		this.zTile = -1;
		this.inTile = 0;
		this.inGround = false;
		this.ticksInAir = 0;
		this.shotSize = 1.0F;
		setSize(0.5F, 0.5F);
		setLocationAndAngles(entity.posX, entity.posY, entity.posZ,
				entity.rotationYaw, entity.rotationPitch);
		setPosition(entity.posX + this.xVec * 0.2D, entity.posY + this.yVec
				+ entityUser.getEyeHeight() - 0.1000000014901161D - 0.15D,
				entity.posZ + this.zVec * 0.2D);
		this.yOffset = 0.0F;
		this.xVec = xVector;
		this.yVec = yVector;
		this.zVec = zVector;
		this.motionX = (this.xVec * firstSpeed);
		this.motionY = (this.yVec * firstSpeed);
		this.motionZ = (this.zVec * firstSpeed);
		this.accelerationX = (this.xVec * maxSpeed * addSpeed);
		this.accelerationY = (this.yVec * maxSpeed * addSpeed);
		this.accelerationZ = (this.zVec * maxSpeed * addSpeed);
		this.shotMaxSpeed = maxSpeed;
		this.shotAddSpeed = addSpeed;
		setShotColor(this.color);
		setDeadTime(this.deadTime);
	}

	public EntityTHShot(World world, EntityLiving entityLiving, double xVector,
			double yVector, double zVector, double speed, int damage, int c) {
		this(world, entityLiving, entityLiving, xVector, yVector, zVector,
				speed, speed, 1.0D, 0.0D, damage, c, 1200);
	}

	public EntityTHShot(World world, EntityLiving entityLiving, double xVector,
			double yVector, double zVector, double firstSpeed, double maxSpeed,
			double addSpeed, int damage, int c) {
		this(world, entityLiving, entityLiving, xVector, yVector, zVector,
				firstSpeed, maxSpeed, addSpeed, 0.0D, damage, c, 1200);
	}

	protected void entityInit() {
		this.dataWatcher.addObject(19, new Integer(0));
		this.dataWatcher.addObject(18, new Integer(0));
	}

	public double getSpeed() {
		return MathHelper.sqrt_double(this.motionX * this.motionX
				+ this.motionY * this.motionY + this.motionZ * this.motionZ);
	}

	public void setVector() {
		if ((this.prevRotationPitch == 0.0F) && (this.prevRotationYaw == 0.0F)) {
			this.yVec = (-MathHelper
					.sin(this.rotationPitch / 180.0F * 3.141593F));
			this.xVec = (-MathHelper.sin(this.rotationYaw / 180.0F * 3.141593F) * MathHelper
					.cos(this.rotationPitch / 180.0F * 3.141593F));
			this.zVec = (MathHelper.cos(this.rotationYaw / 180.0F * 3.141593F) * MathHelper
					.cos(this.rotationPitch / 180.0F * 3.141593F));
			this.accelerationX = (this.xVec * this.shotMaxSpeed * this.shotAddSpeed);
			this.accelerationY = (this.yVec * this.shotMaxSpeed * this.shotAddSpeed);
			this.accelerationZ = (this.zVec * this.shotMaxSpeed * this.shotAddSpeed);
		}
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

		setGravityLevel(this.shotGravity);
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
		specialMotion();

		if (getSpeed() < this.shotMaxSpeed) {
			this.motionX += this.accelerationX;
			this.motionY += this.accelerationY;
			this.motionZ += this.accelerationZ;
		}

		setPosition(this.posX, this.posY, this.posZ);
	}

	public void specialMotion() {
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
		nbtTagCompound.setShort("xTile", (short) this.xTile);
		nbtTagCompound.setShort("yTile", (short) this.yTile);
		nbtTagCompound.setShort("zTile", (short) this.zTile);
		nbtTagCompound.setByte("inTile", (byte) this.inTile);
		nbtTagCompound.setByte("inGround", (byte) (this.inGround ? 1 : 0));
		nbtTagCompound.setTag("direction", newDoubleNBTList(new double[] {
				this.motionX, this.motionY, this.motionZ }));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbtTagCompound) {
		this.xTile = nbtTagCompound.getShort("xTile");
		this.yTile = nbtTagCompound.getShort("yTile");
		this.zTile = nbtTagCompound.getShort("zTile");
		this.inTile = (nbtTagCompound.getByte("inTile") & 0xFF);
		this.inGround = (nbtTagCompound.getByte("inGround") == 1);

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

	public void setGravityLevel(double g) {
		this.motionY -= g;
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	@Override
	public float getCollisionBorderSize() {
		return 1.0F;
	}

	protected boolean isReturnableShot() {
		return false;
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, int i) {
		setBeenAttacked();
		if ((isReturnableShot()) && (damagesource.getEntity() != null)) {
			Vec3 vec3d = damagesource.getEntity().getLookVec();
			if (vec3d != null) {
				this.motionX = vec3d.xCoord;
				this.motionY = vec3d.yCoord;
				this.motionZ = vec3d.zCoord;
				this.xVec = vec3d.xCoord;
				this.yVec = vec3d.yCoord;
				this.zVec = vec3d.zCoord;
				this.accelerationX = (this.motionX * 0.1D);
				this.accelerationY = (this.motionY * 0.1D);
				this.accelerationZ = (this.motionZ * 0.1D);
			}
			if ((damagesource.getEntity() instanceof EntityLiving)) {
				this.shootingEntity = ((EntityLiving) damagesource.getEntity());
			}
			return true;
		}

		return false;
	}

	public void setShotColor(int par1) {
		this.dataWatcher.updateObject(19, Integer.valueOf(par1));
	}

	public int getShotColor() {
		return this.dataWatcher.getWatchableObjectInt(19);
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
