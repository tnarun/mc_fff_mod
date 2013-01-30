package fff.arts;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumArt;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MyRenderPainting extends Render {

	@Override
	public void doRender(Entity par1Entity, double par2, double par4,
			double par6, float par8, float par9) {
		this.renderThePainting((EntityArtReimu) par1Entity, par2, par4, par6,
				par8, par9);
	}

	public void renderThePainting(EntityArtReimu entity_painting, double par2,
			double par4, double par6, float par8, float par9) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) par2, (float) par4, (float) par6);
		GL11.glRotatef(par8, 0.0F, 1.0F, 0.0F);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
//		this.loadTexture("/fff/png/kz.png");
		this.loadTexture("/art/kz.png");
		EnumArt enum_art = EnumArt.Wither;
		float scalef = 0.0625F;
		GL11.glScalef(scalef, scalef, scalef);
		this.func_77010_a(entity_painting, enum_art.sizeX, enum_art.sizeY,
				enum_art.offsetX, enum_art.offsetY);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}

	private void func_77010_a(EntityArtReimu entity_painting, int size_x,
			int size_y, int offset_x, int offset_y) {
		float start_x = -size_x / 2.0F;
		float start_y = -size_y / 2.0F;
		float thickness = 0.5F; // 画的厚度

		float bgwood_left = 0.75F; // 12/16
		float bgwood_right = 0.8125F; // 13/16
		float bgwood_top = 0.0F; // 0/16
		float bgwood_bottom = 0.0625F; // 1/16

		float frame_left = 0.75F; // 12/16
		float frame_right = 0.8125F; // 13/16
		float frame_top = 0.001953125F; // 1/512
		float frame_bottom = 0.001953125F; // 1/512

		float frame_left_1 = 0.7519531F; // 12/16 + 1/512
		float frame_right_1 = 0.7519531F; // 12/16 + 1/512
		float frame_top_1 = 0.0F; // 0/16
		float frame_bottom_1 = 0.0625F; // 1/16

		for (int dx = 0; dx < size_x / 16; ++dx) {
			for (int dy = 0; dy < size_y / 16; ++dy) {
				float x_right_px = start_x + ((dx + 1) * 16);
				float x_left_px = start_x + (dx * 16);
				float y_bottom_px = start_y + ((dy + 1) * 16);
				float y_top_px = start_y + (dy * 16);
				this.func_77008_a(entity_painting,
						(x_right_px + x_left_px) / 2.0F,
						(y_bottom_px + y_top_px) / 2.0F);
				float right_x_spx = (offset_x + size_x - dx * 16) / 256.0F;
				float left_x_spx = (offset_x + size_x - (dx + 1) * 16) / 256.0F;
				float bottom_y_spx = (offset_y + size_y - dy * 16) / 256.0F;
				float top_y_spx = (offset_y + size_y - (dy + 1) * 16) / 256.0F;
				Tessellator tessellator = Tessellator.instance;
				tessellator.startDrawingQuads();

				// 正对玩家的一面，设置四个贴图顶点
				// setNormal 函数是设置贴图可以被看到的表面（对哪一面可见）
				// addVertexWithUV 函数，前三个参数是顶点的空间坐标，后两个参数是贴图上对应位置的偏移量
				tessellator.setNormal(0.0F, 0.0F, -1.0F);
				// 左下
				tessellator.addVertexWithUV(x_right_px,
						y_top_px, (-thickness),
						left_x_spx, bottom_y_spx);
				// 右下
				tessellator.addVertexWithUV(x_left_px,
						y_top_px, (-thickness),
						right_x_spx, bottom_y_spx);
				// 右上
				tessellator.addVertexWithUV(x_left_px,
						y_bottom_px, (-thickness),
						right_x_spx, top_y_spx);
				// 左上
				tessellator.addVertexWithUV(x_right_px,
						y_bottom_px, (-thickness),
						left_x_spx, top_y_spx);

				// 背对玩家的一面
				tessellator.setNormal(0.0F, 0.0F, 1.0F);
				tessellator.addVertexWithUV(x_right_px,
						y_bottom_px, thickness,
						bgwood_left, bgwood_top);
				tessellator.addVertexWithUV(x_left_px,
						y_bottom_px, thickness,
						bgwood_right, bgwood_top);
				tessellator.addVertexWithUV(x_left_px,
						y_top_px, thickness,
						bgwood_right, bgwood_bottom);
				tessellator.addVertexWithUV(x_right_px,
						y_top_px, thickness,
						bgwood_left, bgwood_bottom);

				// 上表面
				tessellator.setNormal(0.0F, 1.0F, 0.0F);
				tessellator.addVertexWithUV(x_right_px,
						y_bottom_px, (-thickness),
						frame_left, frame_top);
				tessellator.addVertexWithUV(x_left_px,
						y_bottom_px, (-thickness),
						frame_right, frame_top);
				tessellator.addVertexWithUV(x_left_px,
						y_bottom_px, thickness,
						frame_right, frame_bottom);
				tessellator.addVertexWithUV(x_right_px,
						y_bottom_px, thickness,
						frame_left, frame_bottom);

				// 下表面
				tessellator.setNormal(0.0F, -1.0F, 0.0F);
				tessellator.addVertexWithUV(x_right_px,
						y_top_px, thickness,
						frame_left, frame_top);
				tessellator.addVertexWithUV(x_left_px,
						y_top_px, thickness,
						frame_right, frame_top);
				tessellator.addVertexWithUV(x_left_px,
						y_top_px, (-thickness),
						frame_right, frame_bottom);
				tessellator.addVertexWithUV(x_right_px,
						y_top_px, (-thickness),
						frame_left, frame_bottom);

				// 左表面
				tessellator.setNormal(-1.0F, 0.0F, 0.0F);
				tessellator.addVertexWithUV(x_right_px,
						y_bottom_px, thickness,
						frame_right_1, frame_top_1);
				tessellator.addVertexWithUV(x_right_px,
						y_top_px, thickness,
						frame_right_1, frame_bottom_1);
				tessellator.addVertexWithUV(x_right_px,
						y_top_px, (-thickness),
						frame_left_1, frame_bottom_1);
				tessellator.addVertexWithUV(x_right_px,
						y_bottom_px, (-thickness),
						frame_left_1, frame_top_1);

				// 右表面
				tessellator.setNormal(1.0F, 0.0F, 0.0F);
				tessellator.addVertexWithUV(x_left_px,
						y_bottom_px, (-thickness),
						frame_right_1, frame_top_1);
				tessellator.addVertexWithUV(x_left_px,
						y_top_px, (-thickness),
						frame_right_1, frame_bottom_1);
				tessellator.addVertexWithUV(x_left_px,
						y_top_px, thickness,
						frame_left_1, frame_bottom_1);
				tessellator.addVertexWithUV(x_left_px,
						y_bottom_px, thickness,
						frame_left_1, frame_top_1);

				tessellator.draw();
			}
		}
	}

	private void func_77008_a(EntityArtReimu par1EntityPainting,
			float center_x, float center_y) {
		int x = MathHelper.floor_double(par1EntityPainting.posX);
		int y = MathHelper.floor_double(par1EntityPainting.posY
				+ (center_y / 16.0F));
		int z = MathHelper.floor_double(par1EntityPainting.posZ);

		if (par1EntityPainting.get_direction() == 2) {
			x = MathHelper.floor_double(par1EntityPainting.posX
					+ (center_x / 16.0F));
		}

		if (par1EntityPainting.get_direction() == 1) {
			z = MathHelper.floor_double(par1EntityPainting.posZ
					- (center_x / 16.0F));
		}

		if (par1EntityPainting.get_direction() == 0) {
			x = MathHelper.floor_double(par1EntityPainting.posX
					- (center_x / 16.0F));
		}

		if (par1EntityPainting.get_direction() == 3) {
			z = MathHelper.floor_double(par1EntityPainting.posZ
					+ (center_x / 16.0F));
		}

		int light = this.renderManager.worldObj.getLightBrightnessForSkyBlocks(
				x, y, z, 0);
		int var8 = light % 65536;
		int var9 = light / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit,
				var8, var9);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
	}
}
