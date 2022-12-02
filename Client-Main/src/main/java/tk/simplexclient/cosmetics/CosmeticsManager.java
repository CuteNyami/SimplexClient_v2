package tk.simplexclient.cosmetics;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import tk.simplexclient.api.ICosmeticsHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CosmeticsManager implements ICosmeticsHandler {

    private final RenderPlayer playerRenderer;

    private final List<Cosmetic> cosmetics = new ArrayList<>();

    public CosmeticsManager() {
        this.playerRenderer = Minecraft.getMinecraft().getRenderManager().playerRenderer;
    }

    @Override
    public void disableCosmetic(Cosmetic cosmetic) {
        /*
        if (this.playerRenderer.getLayerRenderers().contains(cosmetic)) {
            this.playerRenderer.removeLayer(cosmetic);
            cosmetic.setEnabled(false);
        }
         */
        cosmetic.enabled = false;
    }

    @Override
    public void disableCosmetics(Cosmetic... cosmetics) {
        for (Cosmetic cosmetic : cosmetics) {
            /*
            if (this.playerRenderer.getLayerRenderers().contains(cosmetic)) {
                this.playerRenderer.removeLayer(cosmetic);
                cosmetic.setEnabled(false);
            }
             */
            cosmetic.enabled = false;
        }
    }

    @Override
    public void enableCosmetic(Cosmetic cosmetic) {
        /*
        if (!this.playerRenderer.getLayerRenderers().contains(cosmetic)) {
            this.playerRenderer.addLayer(cosmetic);
            cosmetic.setEnabled(true);
        }

         */
        cosmetic.enabled = true;
    }

    @Override
    public void enableCosmetics(Cosmetic... cosmetics) {
        for (Cosmetic cosmetic : cosmetics) {
            /*
            if (!this.playerRenderer.getLayerRenderers().contains(cosmetic)) {
                this.playerRenderer.addLayer(cosmetic);
                cosmetic.setEnabled(true);
            }
             */
            cosmetic.enabled = true;
        }
    }

    @Override
    public void registerCosmetics(Cosmetic... cosmetics) {
        for (Cosmetic cosmetic : cosmetics) {
            if (!this.cosmetics.contains(cosmetic)) {
                this.cosmetics.add(cosmetic);
                if (!this.playerRenderer.getLayerRenderers().contains(cosmetic)) {
                    this.playerRenderer.addLayer(cosmetic);
                }
            }

        }
    }

    @Override
    public void registerCosmetic(Cosmetic cosmetic) {
        if (!this.cosmetics.contains(cosmetic)) {
            this.cosmetics.add(cosmetic);
            if (!this.playerRenderer.getLayerRenderers().contains(cosmetic)) {
                this.playerRenderer.addLayer(cosmetic);
            }
        }
    }

    @Override
    public List<Cosmetic> getCosmetics() {
        List<Cosmetic> cosmetics = new ArrayList<>();
        for (LayerRenderer<?> layer : this.playerRenderer.getLayerRenderers()) {
            if (layer instanceof Cosmetic) {
                Cosmetic cosmetic = (Cosmetic) layer;
                if (cosmetic.enabled) {
                    cosmetics.add(cosmetic);
                }
            }
        }
        return cosmetics;
    }

    @Override
    @Deprecated
    public List<Cosmetic> getEnabledCosmetics() {
        return getCosmetics();
    }

    @Override
    public List<Cosmetic> getDisabledCosmetics() {
        return this.cosmetics.stream().filter(cosmetic -> !cosmetic.enabled).collect(Collectors.toList());
    }

    @Override
    public List<Cosmetic> getAllCosmetics() {
        return this.cosmetics;
    }

    @Override
    public RenderPlayer getPlayerRenderer() {
        return playerRenderer;
    }

    @Override
    public boolean isEnabled(Cosmetic cosmetic) {
        return getCosmetics().contains(cosmetic);
    }
}
