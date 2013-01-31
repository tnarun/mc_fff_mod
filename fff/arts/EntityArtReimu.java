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

public class EntityArtReimu extends Entity {

	public int pic_width = 32;
	
	public int hanging_direction;
	public int block_pos_x;
	public int block_pos_y;
	public int block_pos_z;
	
	// 被框架自动调用
	public EntityArtReimu(World world) {
		super(world);
	}
	
	// 被 Item 调用
	public EntityArtReimu(World world, int x, int y, int z, int direction) {
		this(world);
		this.block_pos_x = x;
		this.block_pos_y = y;
		this.block_pos_z = z;
		this.hanging_direction = direction;
		
		this.init_params();
		this.save_params();
	}

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(18, 0);
		this.dataWatcher.addObject(19, 0);
		this.dataWatcher.addObject(20, 0);
		this.dataWatcher.addObject(21, 0);
	}
	
	private void save_params() {
		this.dataWatcher.updateObject(18, this.hanging_direction);
		this.dataWatcher.updateObject(19, this.block_pos_x);
		this.dataWatcher.updateObject(20, this.block_pos_y);
		this.dataWatcher.updateObject(21, this.block_pos_z);
	}

	public void load_params() {
		this.hanging_direction = this.dataWatcher.getWatchableObjectInt(18);
		this.block_pos_x = this.dataWatcher.getWatchableObjectInt(19);
		this.block_pos_y = this.dataWatcher.getWatchableObjectInt(20);
		this.block_pos_z = this.dataWatcher.getWatchableObjectInt(21);
		this.init_params();
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound var1) {
		this.hanging_direction = var1.getInteger("hanging_direction");
		this.block_pos_x = var1.getInteger("block_pos_x");
		this.block_pos_y = var1.getInteger("block_pos_y");
		this.block_pos_z = var1.getInteger("block_pos_z");
		
		init_params();
		save_params();
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound var1) {
		var1.setInteger("hanging_direction", this.hanging_direction);
		var1.setInteger("block_pos_x", this.block_pos_x);
		var1.setInteger("block_pos_y", this.block_pos_y);
		var1.setInteger("block_pos_z", this.block_pos_z);
	}

	public void init_params() {
		int direction = this.hanging_direction;
		
		float vx = this.pic_width;
		float vy = this.pic_width;
		float vz = this.pic_width;
		
		if (direction != 2 && direction != 0) {
			vx = 0.5F;
			this.rotationYaw = this.prevRotationYaw = direction * 90;
		} else {
			vz = 0.5F;
			this.rotationYaw = this.prevRotationYaw = Direction.footInvisibleFaceRemap[direction] * 90;
		}
		
		vx /= 32.0F;
		vy /= 32.0F;
		vz /= 32.0F;
		
		float fx = this.block_pos_x + 0.5F;
		float fy = this.block_pos_y + 0.5F;
		float fz = this.block_pos_z + 0.5F;
		float o = 0.5625F;
		
		if (direction == 2) {
			fz -= o;
			fx -= compute_size_offset(this.pic_width);
		}
		
		if (direction == 1) {
			fx -= o;
			fz += compute_size_offset(this.pic_width);
		}
		
		if (direction == 0) {
			fz += o;
			fx += compute_size_offset(this.pic_width);
		}
		
		if (direction == 3) {
			fx += o;
			fz -= compute_size_offset(this.pic_width);
		}
		
		fy += compute_size_offset(this.pic_width);
		
		this.setPosition(fx, fy, fz);
		float v = -0.03125F;
		this.boundingBox.setBounds(
				fx - vx - v, fy - vy - v, fz - vz - v, 
				fx + vx + v, fy + vy + v, fz + vz + v
			);
	}

	private float compute_size_offset(int size) {
		return size == 32 ? 0.5F : (size == 64 ? 0.5F : 0.0F);
	}

	public boolean onValidSurface() {
		if (!this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox)
				.isEmpty()) {
			return false;
		}

		int size_x = Math.max(1, pic_width / 16);
		int size_y = Math.max(1, pic_width / 16);
		
		int x = this.block_pos_x;
		int y = this.block_pos_y;
		int z = this.block_pos_z;
		int direction = this.hanging_direction;
		
		System.out.println("remote：" + worldObj.isRemote);
		System.out.println("鼠标位置：" + x + "," + y + "," + z + "," + direction);
		System.out.println("POS：" + this.posX + "," + this.posY + "," + this.posZ);

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

		System.out.println("校准位置：" + x + "," + y + "," + z + "," + direction + "\n");
		
		for (int i = 0; i < size_x; ++i) {
			for (int j = 0; j < size_y; ++j) {
				Material material;

				if (direction != 2 && direction != 0) {
					material = this.worldObj.getBlockMaterial(this.block_pos_x, y + j, z + i);
				} else {
					material = this.worldObj.getBlockMaterial(x + i, y + j, this.block_pos_z);
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
	
	// 让玩家可以击落画像为Item-----------------
	
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
