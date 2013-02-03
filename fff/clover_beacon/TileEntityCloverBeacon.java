package fff.clover_beacon;

import net.minecraft.tileentity.TileEntity;

public class TileEntityCloverBeacon extends TileEntity {
	@Override
	public double func_82115_m() {
		return 65536; // 256 ^ 2 最远可见距离为 256
	}
}
