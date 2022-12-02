package tk.simplexclient.ui;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.util.MovementInput;
import net.minecraft.world.World;

import java.util.UUID;

public class GuiPlayer extends EntityPlayerSP {

    public GuiPlayer(Minecraft mc, World world) {
        this(mc, world, getNullProfile());
    }

    public GuiPlayer(Minecraft mc, World world, GameProfile gameProfile) {
        super(mc, world, new NetHandlerPlayClient(mc, mc.currentScreen, new FakeNetworkManager(EnumPacketDirection.CLIENTBOUND), gameProfile) {
            @Override
            public NetworkPlayerInfo getPlayerInfo(String p_175104_1_) {
                return new FakeNetworkPlayerInfo(gameProfile);
            }
            @Override
            public NetworkPlayerInfo getPlayerInfo(UUID p_175102_1_) {
                return new FakeNetworkPlayerInfo(gameProfile);
            }
        }, null);

        this.dimension = 0;
        this.movementInput = new MovementInput();
        this.posX = 0;
        this.posY = 0;
        this.posZ = 0;
    }

    @Override
    public float getEyeHeight() {
        return 1.82F;
    }

    @Override
    public boolean isWearing(EnumPlayerModelParts modelParts) {
        return true;
    }

    @Override
    public boolean hasPlayerInfo() {
        return true;
    }

    @Override
    public NetworkPlayerInfo getPlayerInfo() {
        return new FakeNetworkPlayerInfo(getGameProfile());
    }

    private static GameProfile getNullProfile() {
        if(Minecraft.getMinecraft().getSession() == null || Minecraft.getMinecraft().getSession().getProfile() == null) {
            return new GameProfile(UUID.randomUUID(), "player");
        }
        return Minecraft.getMinecraft().getSession().getProfile();
    }
}
