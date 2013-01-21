package fff.generators;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;
import fff.FFFMOD;

public class GeneratorClover implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		switch (world.provider.dimensionId) {
		case 0:
			generateSurface(world, random, chunkX * 16, chunkZ * 16);
			break;
		case -1:
			generateNether(world, random, chunkX * 16, chunkZ * 16);
			break;
		}
	}

	private void generateSurface(World world, Random rand, int block_x, int block_z) {
		// 这里是日常世界
		if (rand.nextInt(2) != 0) return; // 1/2 几率，是否在本块刷新
		
		int x = block_x + rand.nextInt(16);
		int z = block_z + rand.nextInt(16);
		int y = world.getHeightValue(x, z);
		
		for (int i = 0; i < 16; ++i) { // 最多刷新16个
			int set_x = x + rand.nextInt(8) - rand.nextInt(8);
			int set_y = y + rand.nextInt(4) - rand.nextInt(4);
			int set_z = z + rand.nextInt(8) - rand.nextInt(8);

			if (world.isAirBlock(set_x, set_y, set_z)
					&& set_y < 127
					&& FFFMOD.block_clover.canBlockStay(world, set_x, set_y, set_z)) { // 这里 y 不用减 1
				world.setBlock(set_x, set_y, set_z, FFFMOD.block_clover.blockID);
			}
		}
	}
	
	@SuppressWarnings("unused")
	private void generateNether(World world, Random rand, int block_x, int block_z) {
		// 这里是地狱
	}
}
