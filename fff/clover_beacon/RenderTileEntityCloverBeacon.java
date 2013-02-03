package fff.clover_beacon;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;

public class RenderTileEntityCloverBeacon extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tile_entity, double dx, double dy, double dz, float partial_tick_time) {		
        Tessellator tellsellator = Tessellator.instance;
        this.bindTextureByName("/misc/beam.png");
        
        int color = 0xCCFF00;
        
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, 10497.0F);
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, 10497.0F);
        
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_BLEND);
        
        GL11.glDepthMask(true);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        
        float time = tile_entity.worldObj.getTotalWorldTime() + partial_tick_time;
        float texture_bottom_offset = -time * 0.2F - MathHelper.floor_float(-time * 0.1F);
        double delta_angle = time * 0.025D * (1.0D - 1 * 2.5D);
        
        // 绘制内层光柱
        
        tellsellator.startDrawingQuads();
        tellsellator.setColorOpaque_I(color);
        
        double radius = 0.2D; // 中心到四角的距离
        
        double angle = Math.PI * 2 / 4;
        
        double dx0 = 0.5D + Math.cos(delta_angle) * radius;
        double dz0 = 0.5D + Math.sin(delta_angle) * radius;
        
        double dx1 = 0.5D + Math.cos(delta_angle + angle) * radius;
        double dz1 = 0.5D + Math.sin(delta_angle + angle) * radius;
        
        double dx2 = 0.5D + Math.cos(delta_angle + angle * 2) * radius;
        double dz2 = 0.5D + Math.sin(delta_angle + angle * 2) * radius;
        
        double dx3 = 0.5D + Math.cos(delta_angle + angle * 3) * radius;
        double dz3 = 0.5D + Math.sin(delta_angle + angle * 3) * radius;
        
        double light_height = 256.0F;
        
        double texture_left = 0.0D;
        double texture_right = 1.0D;
        double texture_bottom = (-1.0F + texture_bottom_offset);
        double texture_top = light_height * (0.5D / radius) + texture_bottom;
        
        tellsellator.addVertexWithUV(dx + dx0, dy, dz + dz0, texture_left, texture_bottom);
        tellsellator.addVertexWithUV(dx + dx0, dy + light_height, dz + dz0, texture_left, texture_top);
        tellsellator.addVertexWithUV(dx + dx1, dy + light_height, dz + dz1, texture_right, texture_top);
        tellsellator.addVertexWithUV(dx + dx1, dy, dz + dz1, texture_right, texture_bottom);
        
        tellsellator.addVertexWithUV(dx + dx1, dy, dz + dz1, texture_left, texture_bottom);
        tellsellator.addVertexWithUV(dx + dx1, dy + light_height, dz + dz1, texture_left, texture_top);
        tellsellator.addVertexWithUV(dx + dx2, dy + light_height, dz + dz2, texture_right, texture_top);
        tellsellator.addVertexWithUV(dx + dx2, dy, dz + dz2, texture_right, texture_bottom);
        
        tellsellator.addVertexWithUV(dx + dx2, dy, dz + dz2, texture_left, texture_bottom);
        tellsellator.addVertexWithUV(dx + dx2, dy + light_height, dz + dz2, texture_left, texture_top);
        tellsellator.addVertexWithUV(dx + dx3, dy + light_height, dz + dz3, texture_right, texture_top);
        tellsellator.addVertexWithUV(dx + dx3, dy, dz + dz3, texture_right, texture_bottom);
        
        tellsellator.addVertexWithUV(dx + dx3, dy, dz + dz3, texture_left, texture_bottom);
        tellsellator.addVertexWithUV(dx + dx3, dy + light_height, dz + dz3, texture_left, texture_top);
        tellsellator.addVertexWithUV(dx + dx0, dy + light_height, dz + dz0, texture_right, texture_top);
        tellsellator.addVertexWithUV(dx + dx0, dy, dz + dz0, texture_right, texture_bottom);
        
        tellsellator.draw();
        
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDepthMask(false);
        
        tellsellator.startDrawingQuads();
        tellsellator.setColorRGBA_I(color, 32);
        
        double var44 = 0.2D;
        double var15 = 0.2D;
        double var17 = 0.8D;
        double var19 = 0.2D;
        double var21 = 0.2D;
        double var23 = 0.8D;
        double var25 = 0.8D;
        double var27 = 0.8D;
        double var31 = 0.0D;
        double var33 = 1.0D;
        double var37 = light_height + texture_bottom;
        
        tellsellator.addVertexWithUV(dx + var44, dy + light_height, dz + var15, var33, var37);
        tellsellator.addVertexWithUV(dx + var44, dy, dz + var15, var33, texture_bottom);
        tellsellator.addVertexWithUV(dx + var17, dy, dz + var19, var31, texture_bottom);
        tellsellator.addVertexWithUV(dx + var17, dy + light_height, dz + var19, var31, var37);
        
        tellsellator.addVertexWithUV(dx + var25, dy + light_height, dz + var27, var33, var37);
        tellsellator.addVertexWithUV(dx + var25, dy, dz + var27, var33, texture_bottom);
        tellsellator.addVertexWithUV(dx + var21, dy, dz + var23, var31, texture_bottom);
        tellsellator.addVertexWithUV(dx + var21, dy + light_height, dz + var23, var31, var37);
        
        tellsellator.addVertexWithUV(dx + var17, dy + light_height, dz + var19, var33, var37);
        tellsellator.addVertexWithUV(dx + var17, dy, dz + var19, var33, texture_bottom);
        tellsellator.addVertexWithUV(dx + var25, dy, dz + var27, var31, texture_bottom);
        tellsellator.addVertexWithUV(dx + var25, dy + light_height, dz + var27, var31, var37);
        
        tellsellator.addVertexWithUV(dx + var21, dy + light_height, dz + var23, var33, var37);
        tellsellator.addVertexWithUV(dx + var21, dy, dz + var23, var33, texture_bottom);
        tellsellator.addVertexWithUV(dx + var44, dy, dz + var15, var31, texture_bottom);
        tellsellator.addVertexWithUV(dx + var44, dy + light_height, dz + var15, var31, var37);
        tellsellator.draw();
        
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDepthMask(true);
	}

}
