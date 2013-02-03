package fff.arts;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;

public class RenderMasterSpark extends Render {

	@Override
	public void doRender(Entity entity, double d, double d1, double d2,
			float f, float f1) {
		doRenderMasterSpark(entity, d, d1, d2, f, f1);
	}

	@SuppressWarnings("unused")
	public void doRenderMasterSpark(Entity entityMasterSpark, double d,
			double d1, double d2, float f, float f1) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) d, (float) d1, (float) d2);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_COLOR);
		GL11.glScalef(1.0F, 1.0F, 1.0F);
		loadTexture("/fff/png/master_spark.png");
		Tessellator tessellator = Tessellator.instance;
		
    	double size =  5.0F;
    	double oldSize = 0.0F;
    	float xlength = (float)size / ( (float)(Math.sin(Math.PI/8.0F) + Math.cos(Math.PI/8.0F) ) + 0.5F) / 2.0F * 1.0F;
    	float oldxlength = (float)oldSize / ((float)(Math.sin(Math.PI/8.0F) + Math.cos(Math.PI/8.0F) ) + 0.5F) / 2.0F * 1.0F;
    	double speed = 10.0D;//entityMasterSpark.speed;
    	double pi8 = Math.PI / 32.0D;
    	double angle = pi8;
		
    	int c = entityMasterSpark.ticksExisted + 0;
    	
    	GL11.glRotatef(entityMasterSpark.rotationPitch, -(float)Math.sin((entityMasterSpark.rotationYaw-90F)/180F * 3.141593F), 0.0F, (float)Math.cos((entityMasterSpark.rotationYaw-90F)/180F * 3.141593F));
    	GL11.glRotatef(180F - entityMasterSpark.rotationYaw, 0.0F, 1.0F, 0.0F);
    	
    	float color;
    	float colorRev = 3.141593F * 5.6125F / 180F;
    	float ticks = (float)entityMasterSpark.ticksExisted + c;
    	GL11.glRotatef((float)Math.sin(pi8*8F) / 3.141593F * 180F, 0.0F, 0.0F, 1.0F);
    	
    	for(int i = 0; i < 64; i++){
    		GL11.glRotatef((float)Math.sin(pi8) / 3.141593F * 180F, 0.0F, 0.0F, 1.0F);
    		color = ((float)angle+ticks) * colorRev;
    		
    		tessellator.startDrawingQuads();
    		tessellator.setColorRGBA_F((float)Math.sin(color), (float)Math.cos(color), -(float)Math.sin(color)*2F, 1.0F);
    		tessellator.setNormal(0.0F, 1.0F, 0.0F);
        	tessellator.addVertexWithUV(0.0F - xlength, size, -speed, 0.0F, 1.0F);
        	tessellator.addVertexWithUV(0.0F + xlength, size, -speed, 0.5F, 1.0F);
        	tessellator.addVertexWithUV(0.0F + oldxlength, oldSize,   0.0D, 0.5F, 0.5F);
        	tessellator.addVertexWithUV(0.0F - oldxlength, oldSize,   0.0D, 0.0F, 0.5F);
    		tessellator.draw();
    		
    		tessellator.startDrawingQuads();
    		tessellator.setColorRGBA_F(0.5F, 0.5F, 0.5F, 0.2F);
    		tessellator.setNormal(0.0F, 1.0F, 0.0F);
        	tessellator.addVertexWithUV(0.0F - xlength, size*0.8F, -speed, 0.0F, 1.0F);
        	tessellator.addVertexWithUV(0.0F + xlength, size*0.8F, -speed, 0.5F, 1.0F);
        	tessellator.addVertexWithUV(0.0F + oldxlength, oldSize*0.8F,   0.0D, 0.5F, 0.5F);
        	tessellator.addVertexWithUV(0.0F - oldxlength, oldSize*0.8F,   0.0D, 0.0F, 0.5F);
    		tessellator.draw();
    		
    		tessellator.startDrawingQuads();
    		tessellator.setColorRGBA_F(0.5F, 0.5F, 0.5F, 0.3F);
    		tessellator.setNormal(0.0F, 1.0F, 0.0F);
        	tessellator.addVertexWithUV(0.0F - xlength, size*0.5F, -speed, 0.0F, 1.0F);
        	tessellator.addVertexWithUV(0.0F + xlength, size*0.5F, -speed, 0.5F, 1.0F);
        	tessellator.addVertexWithUV(0.0F + oldxlength, oldSize*0.5F,   0.0D, 0.5F, 0.5F);
        	tessellator.addVertexWithUV(0.0F - oldxlength, oldSize*0.5F,   0.0D, 0.0F, 0.5F);
    		tessellator.draw();
    		
    		angle+=1.0D;
    		c++;
    	}
    	
    	GL11.glDisable(GL11.GL_BLEND);
    	GL11.glEnable(GL11.GL_LIGHTING);
    	GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glPopMatrix();
    	
	}
}
