package tk.simplexclient.ui;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.WorldSettings;

public class FakeNetworkPlayerInfo extends NetworkPlayerInfo {
    public FakeNetworkPlayerInfo(GameProfile gameProfile) {
        super(gameProfile);
    }

    @Override
    public IChatComponent getDisplayName() {
        return new ChatComponentText(getGameProfile().getName());
    }

    @Override
    public WorldSettings.GameType getGameType() {
        return WorldSettings.GameType.CREATIVE;
    }

    @Override
    public int getResponseTime() {
        return 0;
    }

    @Override
    public String getSkinType() {
        return "default";
    }

    @Override
    public ScorePlayerTeam getPlayerTeam() {
        return null;
    }

}
