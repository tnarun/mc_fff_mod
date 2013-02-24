package fff.proxy;

import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import fff.arts.EntityThPic;
import fff.arts.RenderThPic;
import fff.renders.RenderBlockClover;
import fff.toturial3d.RenderTileEntity3Dtutorial;
import fff.toturial3d.TileEntity3Dtutorial;

public class ClientProxy extends Proxy {

	public final static String BLOCKS_PNG_PATH = "/fff/png/blocks.png";
	public final static String ITEMS_PNG_PATH = "/fff/png/items.png";
		
	@Override
	public void init() {
		MinecraftForgeClient.preloadTexture(BLOCKS_PNG_PATH);
		MinecraftForgeClient.preloadTexture(ITEMS_PNG_PATH);
		
		// render block clover
		RenderingRegistry.registerBlockHandler(new RenderBlockClover());	
		RenderingRegistry.registerEntityRenderingHandler(EntityThPic.class, new RenderThPic());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntity3Dtutorial.class, new RenderTileEntity3Dtutorial());
	}
}
