package fff.clover_beacon;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import fff.proxy.ClientProxy;

public class BlockCloverBeacon extends BlockContainer {

	public BlockCloverBeacon(int par1) {
		super(par1, Material.wood);
		
		setBlockName("block_clover_beacon");
		setCreativeTab(CreativeTabs.tabBlock);
		setTextureFile(ClientProxy.BLOCKS_PNG_PATH);
		
		setLightValue(1);
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileEntityCloverBeacon();
	}

}
