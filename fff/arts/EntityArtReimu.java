package fff.arts;

import java.util.Iterator;
import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import fff.FFFMOD;
import fff.utils.FFFPosition;

public class EntityArtReimu extends Entity {

	public int pic_width = 32;
	
	public EntityArtReimu(World world) {
		super(world);
		this.setSize(0.5F, 0.5F);
	}
	
	public EntityArtReimu(World world, int x, int y, int z, int direction) {
		this(world);
		
		set_pos(new FFFPosition(world, x, y, z));
		set_direction(direction);
	}

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(17, 0);
		this.dataWatcher.addObject(18, 0);
		this.dataWatcher.addObject(19, 0);
		this.dataWatcher.addObject(20, 0);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound var1) {
		// TODO Auto-generated method stub
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound var1) {
		// TODO Auto-generated method stub
	}

	private void set_pos(FFFPosition pos) {
		this.dataWatcher.updateObject(17, pos.x);
		this.dataWatcher.updateObject(18, pos.y);
		this.dataWatcher.updateObject(19, pos.z);
	}

	private void set_direction(int direction) {
		this.dataWatcher.updateObject(20, direction);
		
		this.prevRotationYaw = this.rotationYaw = direction * 90;
		
		float vx = pic_width;
		float vy = pic_width;
		float vz = pic_width;
		
		if (direction != 2 && direction != 0) {
			vx = 0.5F;
		} else {
			vz = 0.5F;
			this.rotationYaw = this.prevRotationYaw = Direction.footInvisibleFaceRemap[direction] * 90;
		}
		
		vx /= 32.0F;
		vy /= 32.0F;
		vz /= 32.0F;
		
		FFFPosition pos = this.get_pos();
		float fx = pos.x + 0.5F;
		float fy = pos.y + 0.5F;
		float fz = pos.z + 0.5F;
		float o = 0.5625F;
		
		if (direction == 2) {
			fz -= o;
			fx -= xxxx(this.pic_width);
		}
		
		if (direction == 1) {
			fx -= o;
			fz += xxxx(this.pic_width);
		}
		
		if (direction == 0) {
			fz += o;
			fx += xxxx(this.pic_width);
		}
		
		if (direction == 3) {
			fx += o;
			fz -= xxxx(this.pic_width);
		}
		
		fy += xxxx(this.pic_width);
		
		this.setPosition(fx, fy, fz);
		float v = -0.03125F;
		this.boundingBox.setBounds(
				fx - vx - v, fy - vy - v, fz - vz - v, 
				fx + vx + v, fy + vy + v, fz + vz + v
			);
		System.out.println("方向设置完毕");
	}

	private float xxxx(int size) {
		return size == 32 ? 0.5F : (size == 64 ? 0.5F : 0.0F);
	}
	
	public FFFPosition get_pos() {
		int x = this.dataWatcher.getWatchableObjectInt(17);
		int y = this.dataWatcher.getWatchableObjectInt(18);
		int z = this.dataWatcher.getWatchableObjectInt(19);
		return new FFFPosition(worldObj, x, y, z);
	}

	public int get_direction() {
		return this.dataWatcher.getWatchableObjectInt(20);
	}

	public boolean onValidSurface() {
		if (!this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox)
				.isEmpty()) {
			return false;
		}

		int size_x = Math.max(1, pic_width / 16);
		int size_y = Math.max(1, pic_width / 16);
		FFFPosition pos = this.get_pos();
		int x = pos.x;
		int y = pos.y;
		int z = pos.z;
		int direction = this.get_direction();
		
		System.out.println("鼠标位置：" + x + "," + y + "," + z + "," + direction);
		System.out.println("POS:" + this.posX + "," + this.posY + "," + this.posZ);

		if (direction == 2) {
			x = MathHelper.floor_double(this.posX - pic_width / 32.0F);
		}

		if (direction == 1) {
			z = MathHelper.floor_double(this.posZ - pic_width / 32.0F);
		}

		if (direction == 0) {
			x = MathHelper.floor_double(this.posX - pic_width / 32.0F);
		}

		if (direction == 3) {
			z = MathHelper.floor_double(this.posZ - pic_width / 32.0F);
		}

		y = MathHelper.floor_double(this.posY - pic_width / 32.0F);

		System.out.println("校准位置：" + x + "," + y + "," + z + "," + direction);
		
		for (int i = 0; i < size_x; ++i) {
			for (int j = 0; j < size_y; ++j) {
				Material material;

				if (direction != 2 && direction != 0) {
					material = this.worldObj.getBlockMaterial(pos.x, y + j, z + i);
				} else {
					material = this.worldObj.getBlockMaterial(x + i, y + j, pos.z);
				}

				if (!material.isSolid()) {
					return false;
				}
			}
		}

		List<?> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this,
				this.boundingBox);
		Iterator<?> iterator = list.iterator();
		Entity entity;
		do {
			if (!iterator.hasNext()) {
				return true;
			}

			entity = (Entity) iterator.next();
		} while (!(entity instanceof EntityArtReimu));

		return false;
	}
	
	@Override
	public boolean func_85031_j(Entity par1Entity) {
		return par1Entity instanceof EntityPlayer ? this.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)par1Entity), 0) : false;
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, int par2) {
		if (this.func_85032_ar())
			return false;
		
		if (!this.isDead && !this.worldObj.isRemote) {
			this.setDead();
			this.setBeenAttacked();
			EntityPlayer var3 = null;

			if (par1DamageSource.getEntity() instanceof EntityPlayer) {
				var3 = (EntityPlayer) par1DamageSource.getEntity();
			}

			if (var3 != null && var3.capabilities.isCreativeMode) {
				return true;
			}

			this.dropItemStack();
		}

		return true;
	}

	public void dropItemStack() {
		this.entityDropItem(new ItemStack(FFFMOD.item_art_reimu), 0.0F);
	}
	
	@Override
	public boolean canBeCollidedWith() {
		return true;
	}
}
