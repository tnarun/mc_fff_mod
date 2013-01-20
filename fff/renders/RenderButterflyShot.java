package fff.renders;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fff.entities.EntityButterflyShot;
import fff.proxy.ClientProxy;

@SideOnly(Side.CLIENT)
public class RenderButterflyShot extends Render {
	private Random random = new Random();

	public void doRenderButterflyShot(EntityButterflyShot entitybutterflyshot,
			double d, double d1, double d2, float f, float f1) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) d, (float) d1, (float) d2);

		GL11.glEnable(2896);

		GL11.glEnable(2977);
		GL11.glEnable(3042);
		GL11.glDisable(2884);
		GL11.glBlendFunc(1, 769);

		float f2 = 1.0F;
		GL11.glScalef(f2 / 1.0F, f2 / 1.0F, f2 / 1.0F);
		loadTexture(ClientProxy.BUTTERFLY_PNG_PATH);
		Tessellator tessellator = Tessellator.instance;
		int color = entitybutterflyshot.getShotColor();
		float f3 = (color * 32 + 0) / 128.0F;
		float f4 = (color * 32 + 32) / 128.0F;
		float f5 = 0.0F;
		float f6 = 1.0F;
		float f7 = 1.0F;
		float f8 = 0.5F;
		float f9 = 0.25F;

		GL11.glRotatef(
				90.0F - entitybutterflyshot.rotationPitch,
				(float) Math
						.sin((entitybutterflyshot.rotationYaw - 90.0F) / 180.0F * 3.141593F),
				0.0F,
				(float) Math
						.cos((entitybutterflyshot.rotationYaw - 90.0F) / 180.0F * 3.141593F));

		GL11.glRotatef(180.0F + entitybutterflyshot.rotationYaw
				- entitybutterflyshot.getWingAngle(), 0.0F, 1.0F, 0.0F);

		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		for (int i = 0; i < 3; i++) {
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(0.0D, 0.0F - f9, 0.0D, f4, f5);
			tessellator.addVertexWithUV(0.5D, 0.0F - f9, 0.0D, f3, f5);
			tessellator.addVertexWithUV(0.5D, 1.0F - f9, 0.0D, f3, f6);
			tessellator.addVertexWithUV(0.0D, 1.0F - f9, 0.0D, f4, f6);
			tessellator.draw();
		}

		GL11.glRotatef(180.0F + entitybutterflyshot.getWingAngle() * 2.0F,
				0.0F, 1.0F, 0.0F);
		for (int i = 0; i < 3; i++) {
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(0.0D, 0.0F - f9, 0.0D, f4, f5);
			tessellator.addVertexWithUV(0.5D, 0.0F - f9, 0.0D, f3, f5);
			tessellator.addVertexWithUV(0.5D, 1.0F - f9, 0.0D, f3, f6);
			tessellator.addVertexWithUV(0.0D, 1.0F - f9, 0.0D, f4, f6);
			tessellator.draw();
		}

		GL11.glDisable(3042);
		GL11.glEnable(2884);
		GL11.glDisable(2896);
		GL11.glPopMatrix();
	}

	@Override
	public void doRender(Entity entity, double d, double d1, double d2,
			float f, float f1) {
		EntityButterflyShot entityButterflyShot = (EntityButterflyShot) entity;
		doRenderButterflyShot(entityButterflyShot, d, d1, d2, f, f1);
	}
}
