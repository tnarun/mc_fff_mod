package fff.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import fff.proxy.ClientProxy;

public class BlockTuzki extends Block {

	public BlockTuzki(int par1) {
		super(par1, Material.wood);
		
		setBlockName("block_tuzki");
		setCreativeTab(CreativeTabs.tabBlock);
		setTextureFile(ClientProxy.BLOCKS_PNG_PATH);
	}
}
