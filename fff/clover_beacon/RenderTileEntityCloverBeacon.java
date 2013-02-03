package fff.clover_beacon;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;

public class RenderTileEntityCloverBeacon extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tile_entity, double dx, double dy, double dz, float partial_tick_time) {
		
		System.out.println(dx + "," + dy + "," + dz);
		
		float var9 = 1.0F;
		
        Tessellator tellsellator = Tessellator.instance;
        this.bindTextureByName("/misc/beam.png");
        
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, 10497.0F);
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, 10497.0F);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDepthMask(true);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        
        float var11 = tile_entity.getWorldObj().getTotalWorldTime() + partial_tick_time;
        float var12 = -var11 * 0.2F - MathHelper.floor_float(-var11 * 0.1F);
        byte var13 = 1;
        double var14 = var11 * 0.025D * (1.0D - (var13 & 1) * 2.5D);
        
        tellsellator.startDrawingQuads();
        tellsellator.setColorRGBA(255, 255, 255, 32);
        
        double var16 = var13 * 0.2D;
        double var18 = 0.5D + Math.cos(var14 + 2.356194490192345D) * var16;
        double var20 = 0.5D + Math.sin(var14 + 2.356194490192345D) * var16;
        double var22 = 0.5D + Math.cos(var14 + (Math.PI / 4D)) * var16;
        double var24 = 0.5D + Math.sin(var14 + (Math.PI / 4D)) * var16;
        double var26 = 0.5D + Math.cos(var14 + 3.9269908169872414D) * var16;
        double var28 = 0.5D + Math.sin(var14 + 3.9269908169872414D) * var16;
        double var30 = 0.5D + Math.cos(var14 + 5.497787143782138D) * var16;
        double var32 = 0.5D + Math.sin(var14 + 5.497787143782138D) * var16;
        double var34 = (256.0F * var9);
        double var36 = 0.0D;
        double var38 = 1.0D;
        double var40 = (-1.0F + var12);
        double var42 = (256.0F * var9) * (0.5D / var16) + var40;
        tellsellator.addVertexWithUV(dx + var18, dy + var34, dz + var20, var38, var42);
        tellsellator.addVertexWithUV(dx + var18, dy, dz + var20, var38, var40);
        tellsellator.addVertexWithUV(dx + var22, dy, dz + var24, var36, var40);
        tellsellator.addVertexWithUV(dx + var22, dy + var34, dz + var24, var36, var42);
        tellsellator.addVertexWithUV(dx + var30, dy + var34, dz + var32, var38, var42);
        tellsellator.addVertexWithUV(dx + var30, dy, dz + var32, var38, var40);
        tellsellator.addVertexWithUV(dx + var26, dy, dz + var28, var36, var40);
        tellsellator.addVertexWithUV(dx + var26, dy + var34, dz + var28, var36, var42);
        tellsellator.addVertexWithUV(dx + var22, dy + var34, dz + var24, var38, var42);
        tellsellator.addVertexWithUV(dx + var22, dy, dz + var24, var38, var40);
        tellsellator.addVertexWithUV(dx + var30, dy, dz + var32, var36, var40);
        tellsellator.addVertexWithUV(dx + var30, dy + var34, dz + var32, var36, var42);
        tellsellator.addVertexWithUV(dx + var26, dy + var34, dz + var28, var38, var42);
        tellsellator.addVertexWithUV(dx + var26, dy, dz + var28, var38, var40);
        tellsellator.addVertexWithUV(dx + var18, dy, dz + var20, var36, var40);
        tellsellator.addVertexWithUV(dx + var18, dy + var34, dz + var20, var36, var42);
        tellsellator.draw();
        
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDepthMask(false);
        tellsellator.startDrawingQuads();
        tellsellator.setColorRGBA(255, 255, 255, 32);
        double var44 = 0.2D;
        double var15 = 0.2D;
        double var17 = 0.8D;
        double var19 = 0.2D;
        double var21 = 0.2D;
        double var23 = 0.8D;
        double var25 = 0.8D;
        double var27 = 0.8D;
        double var29 = (256.0F * var9);
        double var31 = 0.0D;
        double var33 = 1.0D;
        double var35 = (-1.0F + var12);
        double var37 = (256.0F * var9) + var35;
        tellsellator.addVertexWithUV(dx + var44, dy + var29, dz + var15, var33, var37);
        tellsellator.addVertexWithUV(dx + var44, dy, dz + var15, var33, var35);
        tellsellator.addVertexWithUV(dx + var17, dy, dz + var19, var31, var35);
        tellsellator.addVertexWithUV(dx + var17, dy + var29, dz + var19, var31, var37);
        tellsellator.addVertexWithUV(dx + var25, dy + var29, dz + var27, var33, var37);
        tellsellator.addVertexWithUV(dx + var25, dy, dz + var27, var33, var35);
        tellsellator.addVertexWithUV(dx + var21, dy, dz + var23, var31, var35);
        tellsellator.addVertexWithUV(dx + var21, dy + var29, dz + var23, var31, var37);
        tellsellator.addVertexWithUV(dx + var17, dy + var29, dz + var19, var33, var37);
        tellsellator.addVertexWithUV(dx + var17, dy, dz + var19, var33, var35);
        tellsellator.addVertexWithUV(dx + var25, dy, dz + var27, var31, var35);
        tellsellator.addVertexWithUV(dx + var25, dy + var29, dz + var27, var31, var37);
        tellsellator.addVertexWithUV(dx + var21, dy + var29, dz + var23, var33, var37);
        tellsellator.addVertexWithUV(dx + var21, dy, dz + var23, var33, var35);
        tellsellator.addVertexWithUV(dx + var44, dy, dz + var15, var31, var35);
        tellsellator.addVertexWithUV(dx + var44, dy + var29, dz + var15, var31, var37);
        tellsellator.draw();
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDepthMask(true);
	}

}
