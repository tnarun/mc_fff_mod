package fff.arts;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderArtReimu extends Render {

	@Override
	public void doRender(Entity entity, double pos_x, double pos_y,
			double pos_z, float rotation_yaw, float partial_tick_time) {
		this.renderThePainting((EntityArtReimu) entity, pos_x, pos_y, pos_z);
	}

	public void renderThePainting(EntityArtReimu entity_painting, double pos_x,
			double pos_y, double pos_z) {

		entity_painting.load_params();

		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		
		GL11.glTranslatef((float) pos_x, (float) pos_y, (float) pos_z);
		GL11.glRotatef(entity_painting.rotationYaw, 0.0F, 1.0F, 0.0F);
		
		loadTexture(EntityArtReimu.IMAGE_PATH);
		draw(entity_painting);
		
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}

	private void draw(EntityArtReimu entity) {
		float start_x = -EntityArtReimu.GRIDS_WIDTH / 2.0F;
		float start_y = EntityArtReimu.GRIDS_HEIGHT / 2.0F;
		float half_thickness = 0.03125F; // 画的厚度 1/16，一半的厚度为 1/32

		for (float i = 0; i < EntityArtReimu.GRIDS_WIDTH; i++) {
			for (float j = 0; j < EntityArtReimu.GRIDS_HEIGHT; j++) {
				
				float left   = start_x + i;
				float right  = left + 1;
				float top    = start_y - j;
				float bottom = top - 1;
				
				this.render_light(entity, 
						(right + left) / 2, 
						(bottom + top) / 2);
				
				float texture_left   =  i / EntityArtReimu.GRIDS_WIDTH;
				float texture_right  = (i + 1) / EntityArtReimu.GRIDS_WIDTH;
				float texture_top    =  j / EntityArtReimu.GRIDS_HEIGHT;
				float texture_bottom = (j + 1) / EntityArtReimu.GRIDS_HEIGHT;
				
				Tessellator tessellator = Tessellator.instance;
				tessellator.startDrawingQuads();

				// 正对玩家的一面，设置四个贴图顶点
				// setNormal 函数是设置贴图法线向量，关系到光照等
				// addVertexWithUV 函数，前三个参数是顶点的空间坐标，后两个参数是贴图上对应位置的偏移量（以比例表示）
				// 左上，右上，右下，左下。顺时针转一圈
				tessellator.setNormal(0.0F, 0.0F, -1.0F);
				tessellator.addVertexWithUV(right, top, half_thickness, texture_right, texture_top);
				tessellator.addVertexWithUV(left, top, half_thickness, texture_left, texture_top);
				tessellator.addVertexWithUV(left, bottom, half_thickness, texture_left, texture_bottom);
				tessellator.addVertexWithUV(right, bottom, half_thickness, texture_right, texture_bottom);

				// 背对玩家的一面
				tessellator.setNormal(0.0F, 0.0F, 1.0F);
				tessellator.addVertexWithUV(left, top, -half_thickness, 0, 0);
				tessellator.addVertexWithUV(right, top, -half_thickness, 0, 0);
				tessellator.addVertexWithUV(right, bottom, -half_thickness, 0, 0);
				tessellator.addVertexWithUV(left, bottom, -half_thickness, 0, 0);
				
				// 上边框
				tessellator.setNormal(0.0F, 1.0F, 0.0F);
				tessellator.addVertexWithUV(right, top, -half_thickness, 0, 0);
				tessellator.addVertexWithUV(left, top, -half_thickness, 0, 0);
				tessellator.addVertexWithUV(left, top, half_thickness, 0, 0);
				tessellator.addVertexWithUV(right, top, half_thickness, 0, 0);

				// 下边框
				tessellator.setNormal(0.0F, -1.0F, 0.0F);
				tessellator.addVertexWithUV(right, bottom, half_thickness, 0, 0);
				tessellator.addVertexWithUV(left, bottom, half_thickness, 0, 0);
				tessellator.addVertexWithUV(left, bottom, -half_thickness, 0, 0);
				tessellator.addVertexWithUV(right, bottom, -half_thickness, 0, 0);

				// 左表面
				tessellator.setNormal(-1.0F, 0.0F, 0.0F);
				tessellator.addVertexWithUV(left, top, half_thickness, 0, 0);
				tessellator.addVertexWithUV(left, top, -half_thickness, 0, 0);
				tessellator.addVertexWithUV(left, bottom, -half_thickness, 0, 0);
				tessellator.addVertexWithUV(left, bottom, half_thickness, 0, 0);
				
				// 右表面
				tessellator.setNormal(1.0F, 0.0F, 0.0F);
				tessellator.addVertexWithUV(right, top, -half_thickness, 0, 0);
				tessellator.addVertexWithUV(right, top, half_thickness, 0, 0);
				tessellator.addVertexWithUV(right, bottom, half_thickness, 0, 0);
				tessellator.addVertexWithUV(right, bottom, -half_thickness, 0, 0);

				tessellator.draw();
			}
		}
	}

	private void render_light(EntityArtReimu entity, float center_x, float center_y) {
		int x = MathHelper.floor_double(entity.posX);
		int y = MathHelper.floor_double(entity.posY + center_y);
		int z = MathHelper.floor_double(entity.posZ);

		if (entity.hanging_direction == 2) {
			x = MathHelper.floor_double(entity.posX + center_x);
		}

		if (entity.hanging_direction == 1) {
			z = MathHelper.floor_double(entity.posZ - center_x);
		}

		if (entity.hanging_direction == 0) {
			x = MathHelper.floor_double(entity.posX - center_x);
		}

		if (entity.hanging_direction == 3) {
			z = MathHelper.floor_double(entity.posZ + center_x);
		}

		int light = this.renderManager.worldObj.getLightBrightnessForSkyBlocks(x, y, z, 0);
		int var8 = light % 65536;
		int var9 = light / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var8, var9);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
	}
}
