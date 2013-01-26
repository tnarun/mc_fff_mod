package fff.items;

import java.util.List;

import fff.proxy.ClientProxy;
import fff.utils.FFFPosition;
import net.minecraft.block.Block;
import net.minecraft.block.StepSound;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemUselessScythe extends ItemHoe {

	final static int CROPS_MAX_META = 7;

	public ItemUselessScythe(int item_id) {
		super(item_id, EnumToolMaterial.IRON);

		setItemName("item_useless_scythe");
		setTextureFile(ClientProxy.ITEMS_PNG_PATH);
		setIconCoord(1, 3);
		setCreativeTab(CreativeTabs.tabTools);
	}

	@Override
	public float getStrVsBlock(ItemStack item_stack, Block block, int metadata) {

		// 只有用这个工具敲成熟的麦子，才能敲下来
		if (block.blockID == Block.crops.blockID && metadata == CROPS_MAX_META) {
			// 7 是麦子长到最大时的 meta
			return super.getStrVsBlock(item_stack, block, metadata);
		}

		// 敲别的东西都不会造成破坏
		return 0;
	}

	@Override
	public boolean onBlockStartBreak(ItemStack item_stack, int x, int y, int z,
			EntityPlayer player) {
		World world = player.worldObj;

		if (world.isRemote)
			return false;

		FFFPosition this_pos = new FFFPosition(world, x, y, z);

		// 如果是成熟的麦子，一次同时收割周围8格的成熟麦子
		if (_is_ripe_wheat(this_pos)) {

			List<FFFPosition> pos_around = this_pos
					.get_horizontal_positions_around();

			for (FFFPosition pos : pos_around) {
				if (_is_ripe_wheat(pos)) {
					pos.drop_self();
				}
			}

			item_stack.damageItem(1, player);
		}

		return false;
	}

	@Override
	public boolean onItemUse(ItemStack item_stack, EntityPlayer player,
			World world, int x, int y, int z, int side, float x_off,
			float y_off, float z_off) {

		FFFPosition this_pos = new FFFPosition(world, x, y, z);

		if (this_pos.get_block_id() == Block.tilledField.blockID) {
			// 如果目标是被耕过的田，而且身上有小麦种子，则播种
			if (player.inventory.hasItem(Item.seeds.shiftedIndex)) {
				player.inventory.consumeInventoryItem(Item.seeds.shiftedIndex);
				StepSound sound = Block.soundGrassFootstep;
				world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D,
						sound.getStepSound(),
						(sound.getVolume() + 1.0F) / 2.0F,
						sound.getPitch() * 0.8F);
				world.setBlockWithNotify(x, y + 1, z, Block.crops.blockID);
			}
			return true;
		} else if (this_pos.get_block_id() == Block.crops.blockID
				&& !_is_ripe_wheat(this_pos)) {
			// 如果目标是小麦，且没有成熟，且身上有骨粉，则催熟
			if (_player_has_bone_meal(player)) {
				_consume_bone_meal(player);
				world.setBlockMetadataWithNotify(x, y, z, CROPS_MAX_META);
			}
			return true;
		}

		return super.onItemUse(item_stack, player, world, x, y, z, side, x_off,
				y_off, z_off);
	}

	private boolean _player_has_bone_meal(EntityPlayer player) {
		for (ItemStack item_stack : player.inventory.mainInventory) {
			if (_is_item_bone_meal(item_stack)) {
				return true;
			}
		}

		return false;
	}

	private void _consume_bone_meal(EntityPlayer player) {
		for (int i = 0; i < player.inventory.mainInventory.length; i++) {
			ItemStack item_stack = player.inventory.mainInventory[i];
			if (_is_item_bone_meal(item_stack)) {
				if (--item_stack.stackSize <= 0) {
					player.inventory.mainInventory[i] = null;
				}
				return;
			}
		}
	}

	private boolean _is_item_bone_meal(ItemStack item_stack) {
		if (null == item_stack)
			return false;
		return item_stack.itemID == Item.dyePowder.shiftedIndex
				&& item_stack.getItemDamage() == 15;
	}

	private boolean _is_ripe_wheat(FFFPosition pos) {
		return pos.get_block_id() == Block.crops.blockID
				&& pos.get_block_metadata() == CROPS_MAX_META;
	}
}
