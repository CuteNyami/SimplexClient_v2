package tk.simplexclient.cosmetics;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;

public abstract class Cosmetic implements LayerRenderer<AbstractClientPlayer> {

    @Getter
    @Setter
    private String name;

    public boolean enabled = false;

    protected RenderPlayer renderPlayer;

    public Cosmetic(RenderPlayer renderPlayer, String name) {
        this.renderPlayer = renderPlayer;
        this.name = name;
    }

    @Override
    public void doRenderLayer(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float headYaw, float headPitch, float scale) {
        if (player.hasPlayerInfo() && !player.isInvisible()) {
            if (enabled) {
                render(player, limbSwing, limbSwingAmount, partialTicks, ageInTicks, headYaw, headPitch, scale);
            }
        }
    }

    public abstract void render(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float headYaw, float headPitch, float scale);

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
