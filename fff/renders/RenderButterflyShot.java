package fff.renders;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fff.entities.EntityButterflyShot;
import fff.proxy.ClientProxy;

@SideOnly(Side.CLIENT)
public class RenderButterflyShot extends Render {
	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		EntityButterflyShot entityButterflyShot = (EntityButterflyShot) entity;
		doRenderButterflyShot(entityButterflyShot, d, d1, d2, f, f1);
	}
	
	public void doRenderButterflyShot(EntityButterflyShot entitybutterflyshot,
			double d, double d1, double d2, float f, float f1) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) d, (float) d1, (float) d2);

		GL11.glEnable(GL11.GL_LIGHTING); //2896
		GL11.glEnable(GL11.GL_NORMALIZE); //2977
		GL11.glEnable(GL11.GL_BLEND); //3042
		GL11.glDisable(GL11.GL_CULL_FACE); //2884
		GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_COLOR); //1,769

		float scale = 1.0F;
		GL11.glScalef(scale, scale, scale);
		loadTexture(ClientProxy.BUTTERFLY_PNG_PATH);
		
		Tessellator tessellator = Tessellator.instance;
		
		double left = 0.0D;
		double right = 1.0D;
		double top = 0.0D;
		double bottom = 1.0D;
				
		float 角度 = (entitybutterflyshot.rotationYaw + 90.0F) / 180.0F * 3.141593F;
		GL11.glRotatef(90.0F - entitybutterflyshot.rotationPitch,
				MathHelper.sin(角度), 0.0F, MathHelper.cos(角度));

		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		
		
		GL11.glRotatef(180.0F + entitybutterflyshot.rotationYaw - entitybutterflyshot.getWingAngle(), 0.0F, 1.0F, 0.0F);
		draw_wing(tessellator, top, right, bottom, left);

		GL11.glRotatef(180.0F + entitybutterflyshot.getWingAngle() * 2, 0.0F, 1.0F, 0.0F);
		draw_wing(tessellator, top, right, bottom, left);

		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}
	
	private void draw_wing(Tessellator tessellator, double top, double right, double bottom, double left) {
		double y_off = 0.25D;
		
		// 画三层，看着更亮
		for (int i = 0; i < 3; i++) {
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(0.0D, 0.0D - y_off, 0.0D, right, bottom);
			tessellator.addVertexWithUV(0.5D, 0.0D - y_off, 0.0D, left, bottom);
			tessellator.addVertexWithUV(0.5D, 1.0D - y_off, 0.0D, left, top);
			tessellator.addVertexWithUV(0.0D, 1.0D - y_off, 0.0D, right, top);
			tessellator.draw();
		}
	}
}
