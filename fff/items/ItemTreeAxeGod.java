package fff.items;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import fff.proxy.ClientProxy;

public class ItemTreeAxeGod extends ItemTreeAxeIron {

	public ItemTreeAxeGod(int item_id) {
		super(item_id);

		setItemName("item_tree_axe_god");
		setTextureFile(ClientProxy.ITEMS_PNG_PATH);
		setIconCoord(2, 2);
		setCreativeTab(CreativeTabs.tabTools);
	}
	
	@Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return EnumRarity.epic;
	}
	
	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, int x, int y, int z,
			EntityPlayer player) {

		// 为了在创造模式能起作用，故调用此方法，而不是调用 onBlockDestroyed
		// 参考 PlayerControllerMP 的 onPlayerDestroyBlock 方法

		World world = player.worldObj;
				
		for (int dx = -50; dx <= 50; dx++) {
			for (int dz = -50; dz <= 50; dz++) {
				for (int dy = -10; dy <= 10; dy++) {
					TAPosition the_pos = new TAPosition(world, x + dx, y + dy, z + dz);
					if(dx == 0 && dy == 0 && dz == 0)
						continue;
					
					if (the_pos.is_wood_block() || the_pos.get_block_id() == Block.leaves.blockID){
						the_pos.drop_self();
					}
				}
			}
		}

		return false;
	}
	
}
