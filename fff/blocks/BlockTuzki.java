package fff.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import fff.entities.TileEntityTuzki;
import fff.proxy.ClientProxy;

public class BlockTuzki extends BlockContainer {

	public BlockTuzki(int par1) {
		super(par1, Material.wood);
		
		setBlockName("block_tuzki");
		setCreativeTab(CreativeTabs.tabBlock);
		setTextureFile(ClientProxy.BLOCKS_PNG_PATH);
	}
	
	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileEntityTuzki();
	}
	
//	@Override
//	public int getRenderType() {
//		// TODO Auto-generated method stub
//		return 34;
//	}
//
//	@Override
//	public boolean renderAsNormalBlock() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//	
//	@Override
//	public boolean isOpaqueCube() {
//		// TODO Auto-generated method stub
//		return false;
//	}
}
