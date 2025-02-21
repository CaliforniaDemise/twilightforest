package twilightforest.client.renderer.tileentity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import twilightforest.tileentity.spawner.TileEntityTFBossSpawner;

public class TileEntityTFBossSpawnerRenderer extends TileEntitySpecialRenderer<TileEntityTFBossSpawner> {

    @Override
    public void render(TileEntityTFBossSpawner te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x + 0.5F, (float)y, (float)z + 0.5F);
        this.renderMob(te, x, y, z, partialTicks);
        GlStateManager.popMatrix();
    }

    public void renderMob(TileEntityTFBossSpawner te, double x, double y, double z, float partialTicks) {
        Entity entity = te.getDisplayEntity();
        if (entity == null) return;
        float f = 0.53125F;
        float f1 = Math.max(entity.width, entity.height);
        if ((double) f1 > 1.0D) f /= f1;
        float rotation = ((te.getRenderTick() + partialTicks) * 60.0F) % 360.0F;
        GlStateManager.translate(0.0F, 0.4F, 0.0F);
        GlStateManager.rotate(rotation, 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(0.0F, -0.2F, 0.0F);
        GlStateManager.rotate(-30.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(f, f, f);
        entity.setLocationAndAngles(x, y, z, 0.0F, 0.0F);
        Minecraft.getMinecraft().getRenderManager().renderEntity(entity, 0.0D, 0.0D, 0.0D, 0.0F, partialTicks, false);
    }
}
