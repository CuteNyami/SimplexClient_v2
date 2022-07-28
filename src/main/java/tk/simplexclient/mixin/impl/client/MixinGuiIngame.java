package tk.simplexclient.mixin.impl.client;

import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.optifine.shaders.Shaders;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.gl.Config;
import tk.simplexclient.gui.mod.GuiModMenu;
import tk.simplexclient.module.ModuleCreator;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tk.simplexclient.module.dragging.GuiModuleDrag;

@Mixin(GuiIngame.class)
public abstract class MixinGuiIngame extends Gui {

    @Shadow
    public abstract void renderVignette(float lightLevel, ScaledResolution scaledRes);

    @Shadow
    public abstract void renderPumpkinOverlay(ScaledResolution scaledRes);

    @Shadow
    public abstract void renderPortal(float timeInPortal, ScaledResolution scaledRes);

    @Shadow
    public abstract void renderTooltip(ScaledResolution sr, float partialTicks);

    @Shadow
    @Final
    public GuiSpectator spectatorGui;

    @Shadow
    public abstract void renderBossHealth();

    @Shadow
    public abstract void renderPlayerStats(ScaledResolution scaledRes);

    @Shadow
    public abstract void renderHorseJumpBar(ScaledResolution scaledRes, int x);

    @Shadow
    public abstract void renderExpBar(ScaledResolution scaledRes, int x);

    @Shadow
    public abstract void renderSelectedItem(ScaledResolution scaledRes);

    @Shadow
    public abstract void renderDemo(ScaledResolution scaledRes);

    @Shadow
    @Final
    public GuiOverlayDebug overlayDebug;
    @Shadow
    public int recordPlayingUpFor;
    @Shadow
    public boolean recordIsPlaying;

    @Shadow
    public abstract FontRenderer getFontRenderer();

    @Shadow
    public String recordPlaying;
    @Shadow
    public int field_175195_w;
    @Shadow
    public int field_175193_B;
    @Shadow
    public int field_175192_A;
    @Shadow
    public int field_175199_z;
    @Shadow
    public String field_175201_x;
    @Shadow
    public String field_175200_y;

    @Shadow
    public abstract void renderScoreboard(ScoreObjective objective, ScaledResolution scaledRes);

    @Shadow
    public int updateCounter;
    @Shadow
    @Final
    public GuiNewChat persistantChatGUI;
    @Shadow
    @Final
    public GuiPlayerTabOverlay overlayPlayerList;
    private final Minecraft mc = Minecraft.getMinecraft();

    private boolean isVignetteEnabled() {
        if (Shaders.shaderPackLoaded && !Shaders.isVignette()) {
            return false;
        } else if (mc.gameSettings.ofVignette == 0) {
            return mc.gameSettings.fancyGraphics;
        } else {
            return mc.gameSettings.ofVignette == 2;
        }
    }

    /**
     * @author CuteNyami
     */
    @Overwrite
    public void renderGameOverlay(float partialTicks) {
        ScaledResolution scaledresolution = new ScaledResolution(this.mc);
        int i = scaledresolution.getScaledWidth();
        int j = scaledresolution.getScaledHeight();
        this.mc.entityRenderer.setupOverlayRendering();
        GlStateManager.enableBlend();
        if (isVignetteEnabled()) {
            this.renderVignette(this.mc.thePlayer.getBrightness(partialTicks), scaledresolution);
        } else {
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        }

        ItemStack itemstack = this.mc.thePlayer.inventory.armorItemInSlot(3);
        if (this.mc.gameSettings.thirdPersonView == 0 && itemstack != null && itemstack.getItem() == Item.getItemFromBlock(Blocks.pumpkin)) {
            this.renderPumpkinOverlay(scaledresolution);
        }

        if (!this.mc.thePlayer.isPotionActive(Potion.confusion)) {
            float f = this.mc.thePlayer.prevTimeInPortal + (this.mc.thePlayer.timeInPortal - this.mc.thePlayer.prevTimeInPortal) * partialTicks;
            if (f > 0.0F) {
                this.renderPortal(f, scaledresolution);
            }
        }

        if (this.mc.playerController.isSpectator()) {
            this.spectatorGui.renderTooltip(scaledresolution, partialTicks);
        } else {
            this.renderTooltip(scaledresolution, partialTicks);
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(icons);
        GlStateManager.enableBlend();
        if (this.showCrosshair()) {
            GlStateManager.tryBlendFuncSeparate(775, 769, 1, 0);
            GlStateManager.enableAlpha();
            this.drawTexturedModalRect(i / 2 - 7, j / 2 - 7, 0, 0, 16, 16);
        }

        GlStateManager.enableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        this.mc.mcProfiler.startSection("bossHealth");
        this.renderBossHealth();
        this.mc.mcProfiler.endSection();
        if (this.mc.playerController.shouldDrawHUD()) {
            this.renderPlayerStats(scaledresolution);
        }

        GlStateManager.disableBlend();
        float f3;
        int i2;
        int k1;
        if (this.mc.thePlayer.getSleepTimer() > 0) {
            this.mc.mcProfiler.startSection("sleep");
            GlStateManager.disableDepth();
            GlStateManager.disableAlpha();
            k1 = this.mc.thePlayer.getSleepTimer();
            f3 = (float) k1 / 100.0F;
            if (f3 > 1.0F) {
                f3 = 1.0F - (float) (k1 - 100) / 10.0F;
            }

            i2 = (int) (220.0F * f3) << 24 | 1052704;
            drawRect(0, 0, i, j, i2);
            GlStateManager.enableAlpha();
            GlStateManager.enableDepth();
            this.mc.mcProfiler.endSection();
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        k1 = i / 2 - 91;
        if (this.mc.thePlayer.isRidingHorse()) {
            this.renderHorseJumpBar(scaledresolution, k1);
        } else if (this.mc.playerController.gameIsSurvivalOrAdventure()) {
            this.renderExpBar(scaledresolution, k1);
        }

        if (this.mc.gameSettings.heldItemTooltips && !this.mc.playerController.isSpectator()) {
            this.renderSelectedItem(scaledresolution);
        } else if (this.mc.thePlayer.isSpectator()) {
            this.spectatorGui.func_175263_a(scaledresolution);
        }

        if (this.mc.isDemo()) {
            this.renderDemo(scaledresolution);
        }

        if (this.mc.gameSettings.showDebugInfo) {
            this.overlayDebug.renderDebugInfo(scaledresolution);
        }

        int j2;
        if (this.recordPlayingUpFor > 0) {
            this.mc.mcProfiler.startSection("overlayMessage");
            f3 = (float) this.recordPlayingUpFor - partialTicks;
            i2 = (int) (f3 * 255.0F / 20.0F);
            if (i2 > 255) {
                i2 = 255;
            }

            if (i2 > 8) {
                GlStateManager.pushMatrix();
                GlStateManager.translate((float) (i / 2), (float) (j - 68), 0.0F);
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                j2 = 16777215;
                if (this.recordIsPlaying) {
                    j2 = MathHelper.hsvToRGB(f3 / 50.0F, 0.7F, 0.6F) & 16777215;
                }

                this.getFontRenderer().drawString(this.recordPlaying, -this.getFontRenderer().getStringWidth(this.recordPlaying) / 2, -4, j2 + (i2 << 24 & -16777216));
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }

            this.mc.mcProfiler.endSection();
        }

        if (this.field_175195_w > 0) {
            this.mc.mcProfiler.startSection("titleAndSubtitle");
            f3 = (float) this.field_175195_w - partialTicks;
            i2 = 255;
            if (this.field_175195_w > this.field_175193_B + this.field_175192_A) {
                float f4 = (float) (this.field_175199_z + this.field_175192_A + this.field_175193_B) - f3;
                i2 = (int) (f4 * 255.0F / (float) this.field_175199_z);
            }

            if (this.field_175195_w <= this.field_175193_B) {
                i2 = (int) (f3 * 255.0F / (float) this.field_175193_B);
            }

            i2 = MathHelper.clamp_int(i2, 0, 255);
            if (i2 > 8) {
                GlStateManager.pushMatrix();
                GlStateManager.translate((float) (i / 2), (float) (j / 2), 0.0F);
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                GlStateManager.pushMatrix();
                GlStateManager.scale(4.0F, 4.0F, 4.0F);
                j2 = i2 << 24 & -16777216;
                this.getFontRenderer().drawString(this.field_175201_x, (float) (-this.getFontRenderer().getStringWidth(this.field_175201_x) / 2), -10.0F, 16777215 | j2, true);
                GlStateManager.popMatrix();
                GlStateManager.pushMatrix();
                GlStateManager.scale(2.0F, 2.0F, 2.0F);
                this.getFontRenderer().drawString(this.field_175200_y, (float) (-this.getFontRenderer().getStringWidth(this.field_175200_y) / 2), 5.0F, 16777215 | j2, true);
                GlStateManager.popMatrix();
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }

            this.mc.mcProfiler.endSection();
        }

        Scoreboard scoreboard = this.mc.theWorld.getScoreboard();
        ScoreObjective scoreobjective = null;
        ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(this.mc.thePlayer.getName());
        if (scoreplayerteam != null) {
            int i1 = scoreplayerteam.getChatFormat().getColorIndex();
            if (i1 >= 0) {
                scoreobjective = scoreboard.getObjectiveInDisplaySlot(3 + i1);
            }
        }

        ScoreObjective scoreobjective1 = scoreobjective != null ? scoreobjective : scoreboard.getObjectiveInDisplaySlot(1);
        if (scoreobjective1 != null) {
            this.renderScoreboard(scoreobjective1, scaledresolution);
        }

        if (!(mc.currentScreen instanceof GuiModuleDrag) && !mc.gameSettings.showDebugInfo) {
            for (ModuleCreator module : SimplexClient.getInstance().getModuleManager().getModules()) {
                if (module.isEnabled()) {
                    module.render();
                }
            }
        }

        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.disableAlpha();
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0F, (float) (j - 48), 0.0F);
        this.mc.mcProfiler.startSection("chat");
        this.persistantChatGUI.drawChat(this.updateCounter);
        this.mc.mcProfiler.endSection();
        GlStateManager.popMatrix();
        scoreobjective1 = scoreboard.getObjectiveInDisplaySlot(0);
        if (!this.mc.gameSettings.keyBindPlayerList.isKeyDown() || this.mc.isIntegratedServerRunning() && this.mc.thePlayer.sendQueue.getPlayerInfoMap().size() <= 1 && scoreobjective1 == null) {
            this.overlayPlayerList.updatePlayerList(false);
        } else {
            this.overlayPlayerList.updatePlayerList(true);
            this.overlayPlayerList.renderPlayerlist(i, scoreboard, scoreobjective1);
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableLighting();
        GlStateManager.enableAlpha();
    }

    /**
     * @author CuteNyami
     */
    @Overwrite
    public boolean showCrosshair() {
        if (mc.currentScreen instanceof GuiModMenu || mc.currentScreen instanceof GuiModuleDrag || mc.currentScreen instanceof GuiIngameMenu) {
            return false;
        } else if (this.mc.gameSettings.showDebugInfo && !this.mc.thePlayer.hasReducedDebug() && !this.mc.gameSettings.reducedDebugInfo) {
            return false;
        } else if (this.mc.playerController.isSpectator()) {
            if (this.mc.pointedEntity != null) {
                return true;
            } else {
                if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                    BlockPos blockpos = this.mc.objectMouseOver.getBlockPos();
                    return this.mc.theWorld.getTileEntity(blockpos) instanceof IInventory;
                }
                return false;
            }
        } else {
            return true;
        }
    }

}
