package fff.items;

import java.util.Random;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import fff.entities.EntityButterflyShot;
import fff.proxy.ClientProxy;

public class ItemYuyukoFan extends ItemBow {
	public ItemYuyukoFan(int i) {
		super(i);
		setMaxDamage(444);
		setTextureFile(ClientProxy.ITEMS_PNG_PATH);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		ArrowNockEvent event = new ArrowNockEvent(par3EntityPlayer,
				par1ItemStack);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled()) {
			return event.result;
		}

		par3EntityPlayer.setItemInUse(par1ItemStack,
				getMaxItemUseDuration(par1ItemStack));

		return par1ItemStack;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack itemstack, World world,
			EntityPlayer entityPlayer, int par4) {
		EntityLiving entityplayer = entityPlayer;
		int shotnum = 32 + getMaxItemUseDuration(itemstack) - par4;
		if (shotnum > 72) {
			shotnum = 72;
		} else if (shotnum % 3 != 0) {
			shotnum = shotnum / 3 * 3;
		}
		int shotnum3 = shotnum / 3;
		float angle = 0.0F;
		float dangle = 1080.0F / shotnum;
		Random random = new Random();
		EntityButterflyShot[] entitybutterflyshot = new EntityButterflyShot[72];

		if (entityplayer.isSneaking()) {
			for (int j = 0; j < 3; j++) {
				int rand = random.nextInt(4);
				for (int i = 0; i < shotnum3; i++) {
					float ay = -MathHelper
							.sin(entityplayer.rotationPitch / 180.0F * 3.141593F)
							* MathHelper.cos(angle / 180.0F * 3.141593F);
					float ax = -MathHelper
							.sin((entityplayer.rotationYaw + angle) / 180.0F * 3.141593F)
							* MathHelper
									.cos(entityplayer.rotationPitch / 180.0F * 3.141593F);
					float az = MathHelper
							.cos((entityplayer.rotationYaw + angle) / 180.0F * 3.141593F)
							* MathHelper
									.cos(entityplayer.rotationPitch / 180.0F * 3.141593F);
					entitybutterflyshot[(j * shotnum3 + i)] = new EntityButterflyShot(
							world, entityplayer, ax, ay, az, 0.1D + j * 0.1D,
							3, rand);

					angle += dangle;
				}

			}

		}

		angle = -30.0F;
		dangle /= 6.0F;
		int rand = random.nextInt(4);

		for (int i = 0; i < shotnum / 3 + 1; i++) {
			float ay = -MathHelper
					.sin(entityplayer.rotationPitch / 180.0F * 3.141593F)
					* MathHelper.sin(angle / 180.0F * 3.141593F);
			float ax = -MathHelper
					.sin((entityplayer.rotationYaw + angle) / 180.0F * 3.141593F)
					* MathHelper
							.cos(entityplayer.rotationPitch / 180.0F * 3.141593F);
			float az = MathHelper
					.cos((entityplayer.rotationYaw + angle) / 180.0F * 3.141593F)
					* MathHelper
							.cos(entityplayer.rotationPitch / 180.0F * 3.141593F);
			entitybutterflyshot[i] = new EntityButterflyShot(world,
					entityplayer, ax, ay, az, 0.1D, 3, rand);

			angle += dangle;
		}

		if (!world.isRemote) {
			if (entityplayer.isSneaking()) {
				for (int i = 0; i < shotnum; i++) {
					world.spawnEntityInWorld(entitybutterflyshot[i]);
				}
			}

			for (int i = 0; i < shotnum / 3 + 1; i++) {
				world.spawnEntityInWorld(entitybutterflyshot[i]);
			}

		}

		world.playSoundAtEntity(entityplayer, "random.orb", 0.5F, 1.0F);
		itemstack.damageItem(1, entityplayer);
	}

	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.bow;
	}

	public boolean hasEffect(ItemStack itemstack) {
		return true;
	}

	public boolean isFull3D() {
		return true;
	}

	public int getItemEnchantability() {
		return 0;
	}
}
