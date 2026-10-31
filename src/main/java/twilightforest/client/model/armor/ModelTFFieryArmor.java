package twilightforest.client.model.armor;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class ModelTFFieryArmor extends ModelTFArmor {

	public ModelTFFieryArmor(float expand) {
		super(expand);
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		// FULL BRIGHT
		float prevX = OpenGlHelper.lastBrightnessX, prevY = OpenGlHelper.lastBrightnessY;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);

        GL11.glColorMaterial(GL11.GL_FRONT_AND_BACK, GL11.GL_EMISSION);
        GL11.glColor4f(1f, 1f, 1f, 1f);

		super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

        GL11.glColor4f(0f, 0f, 0f, 1f);
		GL11.glColorMaterial(GL11.GL_FRONT_AND_BACK, GL11.GL_AMBIENT_AND_DIFFUSE);

		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, prevX, prevY);
	}
}
