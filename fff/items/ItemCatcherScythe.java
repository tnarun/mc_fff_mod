package fff.items;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import fff.proxy.ClientProxy;
import fff.utils.FFFPosition;

public class ItemCatcherScythe extends Item {

	static int MAX_RADIUS = 50;
	static int MIN_RADIUS = 10;
	static Random RAND = new Random();

	public ItemCatcherScythe(int item_id) {
		super(item_id);

		setItemName("item_catcher_scythe");
		setTextureFile(ClientProxy.ITEMS_PNG_PATH);
		setIconCoord(2, 3);
		setCreativeTab(CreativeTabs.tabTools);
	}

	@Override
	public float getStrVsBlock(ItemStack item_stack, Block block, int metadata) {
		return 0;
	}

	@Override
	public boolean onItemUse(ItemStack item_stack, EntityPlayer player,
			World world, int x, int y, int z, int side, float x_off,
			float y_off, float z_off) {
		if (world.isRemote) {
			return false;
		}

		CatcherPosition this_pos = new CatcherPosition(world, x, y, z);

		int radius = MathHelper.getRandomIntegerInRange(RAND, MIN_RADIUS,
				MAX_RADIUS);

		switch (RAND.nextInt(3)) {
		case 0:
			deal_list(create_lines(this_pos, radius));
			deal_list(create_circles(this_pos, radius));
			break;
		case 1:
			deal_list(create_grids(this_pos, radius));
			break;
		case 2:
			deal_list(create_rounds(this_pos, radius));
			break;
		}

		return true;
	}

	private void deal_list(List<CatcherPosition> list) {
		for (CatcherPosition pos : list) {
			if (pos.is_empty_grass_block()) {
				pos.flag = CatcherPosition.CROP;
			}
			if (pos.is_grown_crop()) {
				pos.flag = CatcherPosition.GRASS;
			}
		}

		for (CatcherPosition pos : list) {
			if (pos.flag == CatcherPosition.CROP) {
				pos.set_crop();
			} else if (pos.flag == CatcherPosition.GRASS) {
				pos.set_grass();
			}
		}
	}

	// 扇面
	private List<CatcherPosition> create_lines(CatcherPosition pos, int radius) {
		float pi = (float) Math.PI;

		int count = MathHelper.getRandomIntegerInRange(RAND, 2, 8);

		float angle = 2.0F * pi / count;
		float start_angle = RAND.nextFloat() * 2.0F * pi;

		List<CatcherPosition> re = new ArrayList<CatcherPosition>();

		for (int i = 0; i < count; i++) {
			for (float j = 0; j < angle / 2; j += 0.01) {
				float sin = MathHelper.sin(start_angle + i * angle + j);
				float cos = MathHelper.cos(start_angle + i * angle + j);

				for (int k = 4; k < radius; k++) {
					int dx = Math.round(k * sin);
					int dz = Math.round(k * cos);
					re.add(new CatcherPosition(pos.world, pos.x + dx, pos.y,
							pos.z + dz));
				}
			}
		}

		return re;
	}

	// 圆环套圆环
	private List<CatcherPosition> create_circles(CatcherPosition pos, int radius) {
		List<CatcherPosition> re = new ArrayList<CatcherPosition>();

		int i = 0;
		boolean is_crop = true;

		List<Boolean> boolean_arr = new ArrayList<Boolean>();

		while (i <= radius) {
			int j = MathHelper.getRandomIntegerInRange(RAND, 2, 10);

			for (int k = 0; k < j; k++) {
				boolean_arr.add(is_crop);
			}
			i = i + j;
			is_crop = !is_crop;
		}

		for (int dx = -radius; dx <= radius; dx++) {
			for (int dz = -radius; dz <= radius; dz++) {
				int l = Math.round((float) Math.sqrt(dx * dx + dz * dz));
				if (l > radius)
					continue;
				boolean is_pos_crop = boolean_arr.get(l);

				if (is_pos_crop) {
					re.add(new CatcherPosition(pos.world, pos.x + dx, pos.y,
							pos.z + dz));
				}
			}
		}

		return re;
	}

	// 棋盘格子
	private List<CatcherPosition> create_grids(CatcherPosition pos, int radius) {
		List<CatcherPosition> re = new ArrayList<CatcherPosition>();

		List<Boolean> boolean_arr_x = new ArrayList<Boolean>();
		List<Boolean> boolean_arr_z = new ArrayList<Boolean>();

		int i = 0;
		boolean is_crop = RAND.nextBoolean();
		while (i <= radius * 2) {
			int j = MathHelper.getRandomIntegerInRange(RAND, 3, 6);

			for (int k = 0; k < j; k++) {
				boolean_arr_x.add(is_crop);
			}
			i = i + j;
			is_crop = !is_crop;
		}

		i = 0;
		is_crop = RAND.nextBoolean();
		while (i <= radius * 2) {
			int j = MathHelper.getRandomIntegerInRange(RAND, 3, 6);

			for (int k = 0; k < j; k++) {
				boolean_arr_z.add(is_crop);
			}
			i = i + j;
			is_crop = !is_crop;
		}

		for (int dx = -radius; dx <= radius; dx++) {
			for (int dz = -radius; dz <= radius; dz++) {
				boolean bx = boolean_arr_x.get(dx + radius);
				boolean bz = boolean_arr_z.get(dz + radius);

				if (bx == bz) {
					re.add(new CatcherPosition(pos.world, pos.x + dx, pos.y,
							pos.z + dz));
				}
			}
		}

		return re;
	}

	private List<CatcherPosition> create_rounds(CatcherPosition pos, int radius) {
		float pi = (float) Math.PI;

		List<CatcherPosition> re = new ArrayList<CatcherPosition>();

		int dx = 0;
		int dz = 0;
		float r = 2.0F;
		float angle = RAND.nextFloat() * 2.0F * pi;
		float dangle = 0.4F * pi;
		boolean dir = RAND.nextBoolean();

		while (Math.sqrt(dx * dx + dz * dz) < radius) {
			// 获得一个小圆
			int r0 = Math.round(r);

			for (int ddx = -r0; ddx <= r0; ddx++) {
				for (int ddz = -r0; ddz <= r0; ddz++) {
					int l = Math
							.round((float) Math.sqrt(ddx * ddx + ddz * ddz));
					if (l < r) {
						re.add(new CatcherPosition(pos.world, pos.x + dx + ddx,
								pos.y, pos.z + dz + ddz));
					}
				}
			}

			float sin = MathHelper.sin(angle);
			float cos = MathHelper.cos(angle);
			dx = Math.round(dx + 2 * (r + 1) * sin);
			dz = Math.round(dz + 2 * (r + 1) * cos);

			r = r * 1.1F;
			angle = dir ? angle + dangle : angle - dangle;
			dangle = dangle * 0.91F;
		}

		return re;
	}

	static class CatcherPosition extends FFFPosition {
		public static int NO_CHANGE = 0;
		public static int CROP = 1;
		public static int GRASS = 2;
		
		public int flag = NO_CHANGE; // 0 不改变 1 变为麦田 2 变为草

		public CatcherPosition(World world, int x, int y, int z) {
			super(world, x, y, z);
		}

		public boolean is_empty_grass_block() {
			boolean cond1 = get_block_id() == Block.grass.blockID;
			boolean cond2 = get_block_id() == Block.dirt.blockID;
			CatcherPosition pos_up = new CatcherPosition(this.world, this.x,
					this.y + 1, this.z);

			boolean cond3 = pos_up.get_block_id() == 0;
			boolean cond4 = pos_up.get_block_id() == 31;

			return (cond1 || cond2) && (cond3 || cond4);
		}

		public boolean is_grown_crop() {
			boolean cond1 = get_block_id() == Block.tilledField.blockID;
			CatcherPosition pos_up = new CatcherPosition(this.world, this.x,
					this.y + 1, this.z);

			boolean cond2 = pos_up.get_block_id() == Block.crops.blockID;
			boolean cond3 = pos_up.get_block_metadata() == 7;

			return cond1 && cond2 && cond3;
		}

		public void set_crop() {
			set_block_with_notify(Block.tilledField.blockID);
			new CatcherPosition(this.world, this.x, this.y + 1, this.z)
					.set_block_and_metadata_with_notify(Block.crops.blockID, 7);
		}

		public void set_grass() {
			new CatcherPosition(this.world, this.x, this.y + 1, this.z)
					.set_block_and_metadata(31, 1);
			set_block_with_notify(Block.grass.blockID);
		}
	}
}
