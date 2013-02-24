package fff.toturial3d;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import fff.proxy.ClientProxy;

public class Block3DTutorial extends BlockContainer {

	public Block3DTutorial(int par1) {
		super(par1, 16, Material.wood);
		
		setBlockName("block_3d_tutorial");
		setCreativeTab(CreativeTabs.tabBlock);
		setTextureFile(ClientProxy.BLOCKS_PNG_PATH);
		
		setLightValue(1);
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileEntity3Dtutorial();
	}
}
