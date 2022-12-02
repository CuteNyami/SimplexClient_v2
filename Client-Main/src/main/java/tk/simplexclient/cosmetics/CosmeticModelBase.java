package tk.simplexclient.cosmetics;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;

public class CosmeticModelBase extends ModelBase {

    protected final ModelBiped playerModel;

    protected final RenderPlayer player;

    public CosmeticModelBase(RenderPlayer player) {
        this.playerModel = player.getMainModel();
        this.player = player;
    }

}
