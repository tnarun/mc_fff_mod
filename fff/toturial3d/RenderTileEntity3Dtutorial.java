package fff.toturial3d;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderTileEntity3Dtutorial extends TileEntitySpecialRenderer {
	@Override
	public void renderTileEntityAt(TileEntity tile_entity, double x,
			double y, double z, float partial_tick_time) {
        this.bindTextureByName("/fff/png/tuzki.png");
        
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
        
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        
        tessellator.addVertexWithUV(x    , y,     z, 0, 1);
        tessellator.addVertexWithUV(x - 1, y    , z, 1, 1);
        tessellator.addVertexWithUV(x - 2, y + 2, z, 1, 0);
        tessellator.addVertexWithUV(x,     y + 1, z, 0, 0);
        
        tessellator.draw();
        
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);
	}
}
