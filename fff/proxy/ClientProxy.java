package fff.proxy;

import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.RenderingRegistry;
import fff.entities.EntityButterflyShot;
import fff.renders.RenderBlockClover;
import fff.renders.RenderButterflyShot;

public class ClientProxy extends Proxy {

	public final static String BLOCKS_PNG_PATH = "/fff/png/blocks.png";
	public final static String ITEMS_PNG_PATH = "/fff/png/items.png";
	
	public final static String BUTTERFLY_PNG_PATH = "/fff/png/butterfly_wing.png";
	
	@Override
	public void init() {
		MinecraftForgeClient.preloadTexture(BLOCKS_PNG_PATH);
		MinecraftForgeClient.preloadTexture(ITEMS_PNG_PATH);
		
		// render block clover
		RenderingRegistry.registerBlockHandler(new RenderBlockClover());
		
		RenderingRegistry.registerEntityRenderingHandler(EntityButterflyShot.class, new RenderButterflyShot());
	}
}
