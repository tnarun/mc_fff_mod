package fff.blocks;

import fff.proxy.ClientProxy;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockTuzki extends Block {

	public BlockTuzki(int par1) {
		super(par1, Material.wood);
		
		setBlockName("block_tuzki");
		setCreativeTab(CreativeTabs.tabBlock);
		setTextureFile(ClientProxy.BLOCKS_PNG_PATH);
	}

}
