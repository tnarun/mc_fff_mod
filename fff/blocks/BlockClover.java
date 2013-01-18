package fff.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.creativetab.CreativeTabs;
import fff.FFFMOD;
import fff.proxy.ClientProxy;

public class BlockClover extends BlockFlower {
	public BlockClover(int par1) {
		super(par1, 18); // 左边第二个参数是图标的序号 
		
		setBlockName("block_clover");
		setTextureFile(ClientProxy.ITEMS_PNG_PATH);
		setCreativeTab(CreativeTabs.tabDecorations);
		
		setLightValue(1);
	}
	
	@Override
	protected boolean canThisPlantGrowOnThisBlockID(int block_id) {
		// 我们的四叶草需要生长在 草，土，田，石，沙
		
		return block_id == Block.grass.blockID || 
				block_id == Block.dirt.blockID || 
				block_id == Block.tilledField.blockID ||
				block_id == Block.stone.blockID ||
				block_id == Block.sand.blockID;
	}
	
	@Override
	public int getRenderType() {
		return FFFMOD.RENDER_TYPE_BLOCK_CLOVER;
	}
}
