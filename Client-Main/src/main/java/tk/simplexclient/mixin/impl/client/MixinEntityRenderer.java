package tk.simplexclient.mixin.impl.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import org.lwjgl.util.glu.Project;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import tk.simplexclient.SimplexClient;

@Mixin(EntityRenderer.class)
public abstract class MixinEntityRenderer {

    @Shadow public Minecraft mc;

    @Shadow public int rendererUpdateCount;

    @Shadow public abstract void orientCamera(float partialTicks);

    @Shadow public boolean debugView;

    @Shadow public int debugViewDirection;

    @Shadow public abstract void hurtCameraEffect(float partialTicks);

    @Shadow public double cameraYaw;

    @Shadow public double cameraZoom;

    @Shadow public abstract float getFOVModifier(float partialTicks, boolean useFOVSetting);

    @Shadow public float farPlaneDistance;

    @Shadow public double cameraPitch;

    @Shadow public abstract void setupViewBobbing(float partialTicks);

    /**
     * @author Nyami
     * @reason minimal view bobbing
     */
    @Overwrite
    public void setupCameraTransform(float partialTicks, int pass)
    {
        this.farPlaneDistance = (float)(this.mc.gameSettings.renderDistanceChunks * 16);
        GlStateManager.matrixMode(5889);
        GlStateManager.loadIdentity();
        float f = 0.07F;

        if (this.mc.gameSettings.anaglyph)
        {
            GlStateManager.translate((float)(-(pass * 2 - 1)) * f, 0.0F, 0.0F);
        }

        if (this.cameraZoom != 1.0D)
        {
            GlStateManager.translate((float)this.cameraYaw, (float)(-this.cameraPitch), 0.0F);
            GlStateManager.scale(this.cameraZoom, this.cameraZoom, 1.0D);
        }

        Project.gluPerspective(this.getFOVModifier(partialTicks, true), (float)this.mc.displayWidth / (float)this.mc.displayHeight, 0.05F, this.farPlaneDistance * MathHelper.SQRT_2);
        GlStateManager.matrixMode(5888);
        GlStateManager.loadIdentity();

        if (this.mc.gameSettings.anaglyph)
        {
            GlStateManager.translate((float)(pass * 2 - 1) * 0.1F, 0.0F, 0.0F);
        }

        this.hurtCameraEffect(partialTicks);
        if (!SimplexClient.getInstance().getModuleManager().minimalViewBobbing.isEnabled()) {
            if (this.mc.gameSettings.viewBobbing) {
                this.setupViewBobbing(partialTicks);
            }
        }

        float f1 = this.mc.thePlayer.prevTimeInPortal + (this.mc.thePlayer.timeInPortal - this.mc.thePlayer.prevTimeInPortal) * partialTicks;

        if (f1 > 0.0F)
        {
            int i = 20;

            if (this.mc.thePlayer.isPotionActive(Potion.confusion))
            {
                i = 7;
            }

            float f2 = 5.0F / (f1 * f1 + 5.0F) - f1 * 0.04F;
            f2 = f2 * f2;
            GlStateManager.rotate(((float)this.rendererUpdateCount + partialTicks) * (float)i, 0.0F, 1.0F, 1.0F);
            GlStateManager.scale(1.0F / f2, 1.0F, 1.0F);
            GlStateManager.rotate(-((float)this.rendererUpdateCount + partialTicks) * (float)i, 0.0F, 1.0F, 1.0F);
        }

        this.orientCamera(partialTicks);

        if (this.debugView)
        {
            switch (this.debugViewDirection)
            {
                case 0:
                    GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
                    break;
                case 1:
                    GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                    break;
                case 2:
                    GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
                    break;
                case 3:
                    GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
                    break;
                case 4:
                    GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
            }
        }
    }

    /*
    @Override
    @Invoker("loadShader")
    public abstract void loadShader(ResourceLocation resourceLocationIn);

    @Override
    @Invoker("stopUseShader")
    public abstract void stopUseShader();

     */
}
