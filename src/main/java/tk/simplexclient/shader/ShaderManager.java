package tk.simplexclient.shader;

import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;
import sun.security.provider.SHA;

import java.util.ArrayList;

public class ShaderManager {

    private final Minecraft mc = Minecraft.getMinecraft();

    public void startBlurShader() {
        IMetadataSerializer metadataSerializer = new IMetadataSerializer();
        IReloadableResourceManager mcResourceManager = new SimpleReloadableResourceManager(metadataSerializer);
        EntityRenderer entityRenderer = new EntityRenderer(mc, mcResourceManager);

        this.mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/menu_blur.json"));
    }

    public void startStringShader(String shader) {
        IMetadataSerializer metadataSerializer = new IMetadataSerializer();
        IReloadableResourceManager mcResourceManager = new SimpleReloadableResourceManager(metadataSerializer);
        EntityRenderer entityRenderer = new EntityRenderer(mc, mcResourceManager);

        this.mc.entityRenderer.loadShader(new ResourceLocation(shader));

    }

    public void stopShader() {
        IMetadataSerializer metadataSerializer = new IMetadataSerializer();
        IReloadableResourceManager mcResourceManager = new SimpleReloadableResourceManager(metadataSerializer);
        EntityRenderer entityRenderer = new EntityRenderer(mc, mcResourceManager);

        this.mc.entityRenderer.stopUseShader();
    }

}
