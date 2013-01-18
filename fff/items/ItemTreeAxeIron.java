package fff.items;

import java.util.ArrayList;
import java.util.Stack;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import fff.proxy.ClientProxy;
import fff.utils.FFFPosition;

public class ItemTreeAxeIron extends ItemAxe {
	
	public ItemTreeAxeIron(int item_id) {
		super(item_id, EnumToolMaterial.IRON);

		setItemName("item_tree_axe_iron");
		setTextureFile(ClientProxy.ITEMS_PNG_PATH);
		setIconCoord(1, 2);
		setCreativeTab(CreativeTabs.tabTools);
	}
	
	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, int x, int y, int z,
			EntityPlayer player) {

		// 为了在创造模式能起作用，故调用此方法，而不是调用 onBlockDestroyed
		// 参考 PlayerControllerMP 的 onPlayerDestroyBlock 方法

		World world = player.worldObj;
		TAPosition this_pos = new TAPosition(world, x, y, z);

		if (!this_pos.is_wood_block())
			return false;

		ArrayList<TAPosition> block_pos_arr = this_pos
				.get_connected_wood_blocks();

		for (TAPosition pos : block_pos_arr)
			if (!pos.equals(this_pos))
				pos.drop_self();

		return false;
	}

	static class TAPosition extends FFFPosition {

		static int[] WOOD_BLOCK_IDS = new int[] { Block.wood.blockID };

		public TAPosition(World world, int x, int y, int z) {
			super(world, x, y, z);
		}

		public TAPosition(FFFPosition pos, ForgeDirection dir) {
			super(pos, dir);
		}

		public boolean not_in(Stack<TAPosition> stack) {
			for (TAPosition pos1 : stack) {
				if (pos1.equals(this))
					return false;
			}
			return true;
		}

		public boolean not_in(ArrayList<TAPosition> list) {
			for (TAPosition pos1 : list) {
				if (pos1.equals(this))
					return false;
			}
			return true;
		}

		public boolean is_wood_block() {
			int this_block_id = get_block_id();

			for (int id : WOOD_BLOCK_IDS) {
				if (this_block_id == id)
					return true;
			}
			return false;
		}

		public ArrayList<TAPosition> get_connected_wood_blocks() {
			ArrayList<TAPosition> res = new ArrayList<TAPosition>();
			Stack<TAPosition> positions = new Stack<TAPosition>();

			positions.push(this);

			do {
				TAPosition pos = positions.pop();
				for (ForgeDirection to_dir : ForgeDirection.VALID_DIRECTIONS) {
					TAPosition new_pos = new TAPosition(pos, to_dir);
					if (new_pos.is_wood_block() && new_pos.not_in(positions)
							&& new_pos.not_in(res)) {
						positions.push(new_pos);
					}
				}
				res.add(pos);
			} while (!positions.empty());

			return res;
		}
	}
}