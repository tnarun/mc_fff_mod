package fff.arts;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import fff.proxy.ClientProxy;

public class ItemThPic extends Item {

	private int title_id;
	
	public ItemThPic(int par1, int title_id) {
		super(par1);
		this.title_id = title_id;
		
		setItemName("item_th_pic_" + title_id);
		setTextureFile(ClientProxy.ITEMS_PNG_PATH);
		setIconCoord(1 + title_id, 4);
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
		EntityThPic entity = new EntityThPic(world, x, y, z, direction, title_id);
		
		if (entity.onValidSurface()) {
			if (!world.isRemote) {
				world.spawnEntityInWorld(entity);
			}
			--item_stack.stackSize;
		}

		return true;
	}
}
