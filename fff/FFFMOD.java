package fff;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import fff.arts.EntityArtReimu;
import fff.arts.ItemArtReimu;
import fff.blocks.BlockClover;
import fff.blocks.BlockTuzki;
import fff.clover_beacon.BlockCloverBeacon;
import fff.clover_beacon.TileEntityCloverBeacon;
import fff.generators.GeneratorClover;
import fff.items.ItemCatcherScythe;
import fff.items.ItemCirno;
import fff.items.ItemTreeAxeGod;
import fff.items.ItemTreeAxeIron;
import fff.items.ItemUselessScythe;
import fff.proxy.Proxy;

@Mod(modid = "FFFMOD", name = "FFFMOD", version = "1.1.1.6")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class FFFMOD {
	
	@Instance("FFF")
	public static FFFMOD instance;
	
	@SidedProxy(clientSide = "fff.proxy.ClientProxy", serverSide = "fff.proxy.Proxy")
	public static Proxy proxy;
	
	final public static int RENDER_TYPE_BLOCK_CLOVER = 502;
	
	public static Block block_tuzki = new BlockTuzki(501);
	public static Block block_clover = new BlockClover(502, 18);
	public static Block block_clover_with_three_leaves = new BlockClover(503, 19);
	public static Block block_clover_beacon = new BlockCloverBeacon(504);
	
	public static Item item_ciron = new ItemCirno(10001); // 最大 32000
	public static Item item_tree_axe_iron = new ItemTreeAxeIron(10002);
	public static Item item_tree_axe_god = new ItemTreeAxeGod(10003);
	public static Item item_useless_scythe = new ItemUselessScythe(10004);
	public static Item item_catcher_scythe = new ItemCatcherScythe(10005);
	
	public static Item item_art_reimu = new ItemArtReimu(10006);
	
	@Init
	public void init(@SuppressWarnings("unused") FMLInitializationEvent event) {
		proxy.init();
				
		ModLoader.registerBlock(block_tuzki);
		ModLoader.addName(block_tuzki, "兔斯基方块");
		
		ModLoader.registerBlock(block_clover);
		ModLoader.registerBlock(block_clover_with_three_leaves);
		ModLoader.addName(block_clover, "四叶草");
		ModLoader.addName(block_clover_with_three_leaves, "三叶草");
		
		ModLoader.registerBlock(block_clover_beacon);
		ModLoader.registerTileEntity(TileEntityCloverBeacon.class, "tile_entity_clover_beacon");
		ModLoader.addName(block_clover_beacon, "四叶信标");
		
		ModLoader.addName(item_ciron, "琪露诺徽章");
		
		ModLoader.addName(item_tree_axe_iron, "砍树大斧头");
		ModLoader.addRecipe(new ItemStack(item_tree_axe_iron), new Object[] {
			"BBC",
			"BA ",
			"BA ",
			Character.valueOf('B'), Item.ingotIron,
			Character.valueOf('A'), Item.stick,
			Character.valueOf('C'), Block.blockSteel
		});
		
		ModLoader.addName(item_tree_axe_god, "源质氪金幽冥燃铁邪王延极真神斧·改");
		
		ModLoader.addName(item_useless_scythe, "没(quan)用(neng)的镰刀");
		ModLoader.addName(item_catcher_scythe, "麦田守望者");
		
		ModLoader.addName(item_art_reimu, "博丽灵梦的画像");
		
		// 地形创建器
		GameRegistry.registerWorldGenerator(new GeneratorClover());
		
		EntityRegistry.registerGlobalEntityID(EntityArtReimu.class, "EntityArtReimu", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerModEntity(EntityArtReimu.class, "EntityArtReimu", 1, this, 250, 5, true);
	}
}
