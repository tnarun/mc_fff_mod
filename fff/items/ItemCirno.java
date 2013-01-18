package fff.items;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import fff.proxy.ClientProxy;

public class ItemCirno extends Item {
	public ItemCirno(int par1) {
		super(par1);
		
		setItemName("item_cirno");
		setTextureFile(ClientProxy.ITEMS_PNG_PATH);
		setIconCoord(1, 1);
//		setIconIndex(17);
		setCreativeTab(CreativeTabs.tabTools);
	}
	
    @Override
    public boolean onItemUse(ItemStack item_stack, EntityPlayer player,
        World world, int x, int y, int z, int side, float x_off,
        float y_off, float z_off) {
    	
    	// side 
    	// 0-下表面 1-上表面 
    	// 2-北表面 3-南表面 
    	// 4-西表面 5-东表面
    	// off 鼠标点击处 距离方块 东-南-上 角的位置
      
    	if (world.isRemote) return true;
  
    	for (int dx = -4; dx <= 4; dx++) {
    		for (int dz = -4; dz <= 4; dz++) {        
    			if (Math.abs(dx) + Math.abs(dz) > 6) continue;
    			
    			int new_x = x + dx;
    			int new_y = y;
    			int new_z = z + dz;
    			
    			set_snow_or_ice(world, new_x, new_y, new_z);
			}
    	}
  
    	return true;
    }

	private void set_snow_or_ice(World world, int x, int y, int z) {
		int block_id = world.getBlockId(x, y, z);
		
		if (block_id == 0) return; // 如果坐标处没有任何方块，则结束
		
		// 给泥土，沙子，草方块覆盖雪块
		if (block_id == Block.sand.blockID || block_id == Block.dirt.blockID || block_id == Block.grass.blockID) {
			
			int y1 = y + 1;
			
			int bid = world.getBlockId(x, y1, z);
			
			if ( bid == 0 || bid == Block.tallGrass.blockID ) {
				world.setBlock(x, y1, z, Block.snow.blockID); // 如果符合要求，就设置为积雪
			}
		}
		
		// 替换水为冰块
		if (block_id == Block.waterMoving.blockID || block_id == Block.waterStill.blockID) {
			world.setBlock(x, y, z, Block.ice.blockID);
		}
	}
	
}
