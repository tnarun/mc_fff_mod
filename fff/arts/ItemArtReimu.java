package fff.arts;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import fff.proxy.ClientProxy;

public class ItemArtReimu extends Item {

	public ItemArtReimu(int par1) {
		super(par1);

		setItemName("item_art_reimu");
		setTextureFile(ClientProxy.ITEMS_PNG_PATH);
		setIconCoord(1, 4);
		setCreativeTab(CreativeTabs.tabDecorations);
	}

	@Override
	public boolean onItemUse(ItemStack item_stack, EntityPlayer player,
			World world, int x, int y, int z, int side, float x_off,
			float y_off, float z_off) {
		// 对其他方块的上下表面使用时，不触发任何效果
		if (side == 0)
			return false;
		if (side == 1)
			return false;

		int direction = Direction.vineGrowth[side];
		EntityArtReimu entity = new EntityArtReimu(world, x, y, z, direction);
		
		if (entity.onValidSurface()) {
			if (!world.isRemote) {
				world.spawnEntityInWorld(entity);
			}
			--item_stack.stackSize;
		}

		return true;
	}
}
