package tk.simplexclient.mixin.impl.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.ServerListEntryNormal;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import tk.simplexclient.access.AccessGuiMultiplayer;

import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

@Mixin(ServerListEntryNormal.class)
public abstract class MixinServerListEntryNormal {

    @Shadow @Final private GuiMultiplayer field_148303_c;

    @Shadow @Final private Minecraft mc;

    @Shadow protected abstract boolean func_178013_b();

    @Shadow @Final private ServerData field_148301_e;

    @Shadow @Final private static ThreadPoolExecutor field_148302_b;

    @Shadow private String field_148299_g;

    @Shadow protected abstract void prepareServerIcon();

    @Shadow private DynamicTexture field_148305_h;

    @Shadow protected abstract void func_178012_a(int p_178012_1_, int p_178012_2_, ResourceLocation p_178012_3_);

    @Shadow @Final private ResourceLocation field_148306_i;

    @Shadow @Final private static ResourceLocation UNKNOWN_SERVER;

    @Shadow @Final private static ResourceLocation SERVER_SELECTION_BUTTONS;

    /**
     * @author CuteNyami
     * @reason idk
     */
    @Overwrite
    public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected)
    {
        if (!this.field_148301_e.field_78841_f)
        {
            this.field_148301_e.field_78841_f = true;
            this.field_148301_e.pingToServer = -2L;
            this.field_148301_e.serverMOTD = "";
            this.field_148301_e.populationInfo = "";
            field_148302_b.submit(() -> {
                try
                {
                    this.field_148303_c.getOldServerPinger().ping(this.field_148301_e);
                }
                catch (UnknownHostException var2)
                {
                    this.field_148301_e.pingToServer = -1L;
                    this.field_148301_e.serverMOTD = EnumChatFormatting.DARK_RED + "Can\'t resolve hostname";
                }
                catch (Exception var3)
                {
                    this.field_148301_e.pingToServer = -1L;
                    this.field_148301_e.serverMOTD = EnumChatFormatting.DARK_RED + "Can\'t connect to server.";
                }
            });
        }

        boolean flag = this.field_148301_e.version > 47;
        boolean flag1 = this.field_148301_e.version < 47;
        boolean flag2 = flag || flag1;
        this.mc.fontRendererObj.drawString(this.field_148301_e.serverName, x + 32 + 3, y + 1, 16777215);
        List<String> list = this.mc.fontRendererObj.listFormattedStringToWidth(this.field_148301_e.serverMOTD, listWidth - 32 - 2);

        for (int i = 0; i < Math.min(list.size(), 2); ++i)
        {
            this.mc.fontRendererObj.drawString((String)list.get(i), x + 32 + 3, y + 12 + this.mc.fontRendererObj.FONT_HEIGHT * i, 8421504);
        }

        String s2 = flag2 ? EnumChatFormatting.DARK_RED + this.field_148301_e.gameVersion : this.field_148301_e.populationInfo;
        int j = this.mc.fontRendererObj.getStringWidth(s2);
        this.mc.fontRendererObj.drawString(s2, x + listWidth - j - 15 - 2, y + 1, 8421504);
        int k = 0;
        String s = null;
        int l;
        String s1;

        if (flag2)
        {
            l = 5;
            s1 = flag ? "Client out of date!" : "Server out of date!";
            s = this.field_148301_e.playerList;
        }
        else if (this.field_148301_e.field_78841_f && this.field_148301_e.pingToServer != -2L)
        {
            if (this.field_148301_e.pingToServer < 0L)
            {
                l = 5;
            }
            else if (this.field_148301_e.pingToServer < 150L)
            {
                l = 0;
            }
            else if (this.field_148301_e.pingToServer < 300L)
            {
                l = 1;
            }
            else if (this.field_148301_e.pingToServer < 600L)
            {
                l = 2;
            }
            else if (this.field_148301_e.pingToServer < 1000L)
            {
                l = 3;
            }
            else
            {
                l = 4;
            }

            if (this.field_148301_e.pingToServer < 0L)
            {
                s1 = "(no connection)";
            }
            else
            {
                s1 = this.field_148301_e.pingToServer + "ms";
                s = this.field_148301_e.playerList;
            }
        }
        else
        {
            k = 1;
            l = (int)(Minecraft.getSystemTime() / 100L + (long)(slotIndex * 2L) & 7L);

            if (l > 4)
            {
                l = 8 - l;
            }

            s1 = "Pinging...";
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(Gui.icons);
        Gui.drawModalRectWithCustomSizedTexture(x + listWidth - 15, y, (float)(k * 10), (float)(176 + l * 8), 10, 8, 256.0F, 256.0F);

        if (this.field_148301_e.getBase64EncodedIconData() != null && !this.field_148301_e.getBase64EncodedIconData().equals(this.field_148299_g))
        {
            this.field_148299_g = this.field_148301_e.getBase64EncodedIconData();
            this.prepareServerIcon();
            this.field_148303_c.getServerList().saveServerList();
        }

        if (this.field_148305_h != null)
        {
            this.func_178012_a(x, y, this.field_148306_i);
        }
        else
        {
            this.func_178012_a(x, y, UNKNOWN_SERVER);
        }

        int i1 = mouseX - x;
        int j1 = mouseY - y;

        if (i1 >= listWidth - 15 && i1 <= listWidth - 5 && j1 >= 0 && j1 <= 8)
        {
            this.field_148303_c.setHoveringText(s1);
        }
        else if (i1 >= listWidth - j - 15 - 2 && i1 <= listWidth - 15 - 2 && j1 >= 0 && j1 <= 8)
        {
            this.field_148303_c.setHoveringText(s);
        }

        if (this.mc.gameSettings.touchscreen || isSelected)
        {
            this.mc.getTextureManager().bindTexture(SERVER_SELECTION_BUTTONS);
            Gui.drawRect(x, y, x + 32, y + 32, -1601138544);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            int k1 = mouseX - x;
            int l1 = mouseY - y;

            if (this.func_178013_b())
            {
                if (k1 < 32 && k1 > 16)
                {
                    Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 32.0F, 32, 32, 256.0F, 256.0F);
                }
                else
                {
                    Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, 32, 32, 256.0F, 256.0F);
                }
            }

            if (method(slotIndex))
            {
                if (k1 < 16 && l1 < 16)
                {
                    Gui.drawModalRectWithCustomSizedTexture(x, y, 96.0F, 32.0F, 32, 32, 256.0F, 256.0F);
                }
                else
                {
                    Gui.drawModalRectWithCustomSizedTexture(x, y, 96.0F, 0.0F, 32, 32, 256.0F, 256.0F);
                }
            }


            if (method2(slotIndex))
            {
                if (k1 < 16 && l1 > 16)
                {
                    Gui.drawModalRectWithCustomSizedTexture(x, y, 64.0F, 32.0F, 32, 32, 256.0F, 256.0F);
                }
                else
                {
                    Gui.drawModalRectWithCustomSizedTexture(x, y, 64.0F, 0.0F, 32, 32, 256.0F, 256.0F);
                }
            }

        }
    }

    public boolean method(int p_175392_2_)
    {
        return p_175392_2_ > 0;
    }

    public boolean method2(int p_175394_2_)
    {
        return p_175394_2_ < ((AccessGuiMultiplayer) field_148303_c).getSavedServerList().countServers() - 1;
    }

}
