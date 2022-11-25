package tk.simplexclient.mixin.impl.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.Session;
import net.minecraft.util.Timer;
import net.minecraft.world.WorldSettings;
import org.apache.logging.log4j.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.access.AccessMinecraft;
import tk.simplexclient.animations.Delta;
import tk.simplexclient.event.impl.ClientTickEvent;
import tk.simplexclient.event.impl.SinglePlayerJoinEvent;
import tk.simplexclient.event.impl.PlayerJoinWorldEvent;
import tk.simplexclient.module.impl.MotionBlur;
import tk.simplexclient.utils.CapeUtils;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft implements AccessMinecraft {

    @Shadow public RenderGlobal renderGlobal;
    @Shadow public EntityPlayerSP thePlayer;
    @Shadow public boolean renderChunksMany;

    @Shadow protected abstract void updateDisplayMode() throws LWJGLException;

    @Shadow public int displayWidth;
    @Shadow public int displayHeight;
    @Shadow private boolean fullscreen;
    @Shadow public GameSettings gameSettings;
    @Shadow private int tempDisplayWidth;
    @Shadow private int tempDisplayHeight;
    @Shadow public GuiScreen currentScreen;

    @Shadow protected abstract void resize(int width, int height);

    @Shadow protected abstract void updateFramebufferSize();

    @Shadow public abstract void updateDisplay();

    @Shadow @Final private static Logger logger;
    private long lastFrame = getTime();

    private final ClientTickEvent event = new ClientTickEvent();

    private final SinglePlayerJoinEvent singlePlayerJoinEvent = new SinglePlayerJoinEvent();

    @Inject(method = "startGame", at = @At("RETURN"))
    public void injectStartGame(CallbackInfo ci) {
        new SimplexClient().start();
    }

    @Inject(method = "launchIntegratedServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/login/client/C00PacketLoginStart;<init>(Lcom/mojang/authlib/GameProfile;)V"))
    public void launchIntegratedServer(String folderName, String worldName, WorldSettings worldSettingsIn, CallbackInfo ci) {
        if (singlePlayerJoinEvent.isCancelled()) return;
        singlePlayerJoinEvent.call();
    }

    @Inject(method = "runGameLoop", at = @At("HEAD"))
    public void delta(CallbackInfo ci) {
        long currentTime = getTime();
        int deltaTime = (int) (currentTime - lastFrame);
        lastFrame = currentTime;
        Delta.DELTATIME = deltaTime;
    }

    @Inject(method = "loadWorld(Lnet/minecraft/client/multiplayer/WorldClient;Ljava/lang/String;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;preparePlayerToSpawn()V"))
    public void loadWorld(WorldClient worldClientIn, String loadingMessage, CallbackInfo ci) {
        PlayerJoinWorldEvent event = new PlayerJoinWorldEvent(worldClientIn, this.thePlayer);
        if (event.isCancelled()) return;
        event.call();
    }

    @Inject(method = "shutdownMinecraftApplet", at = @At("HEAD"))
    public void injectShutdown(CallbackInfo ci) {
        SimplexClient.getInstance().stop();
    }

    @Inject(method = "runGameLoop", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;updateDisplay()V"))
    public void createBlur(CallbackInfo ci) {
        if (SimplexClient.getInstance().getModuleManager().motionBlur.isEnabled()) {
            MotionBlur.createAccumulation();
        }
    }

    @Inject(method = "runTick", at = @At("TAIL"))
    public void runTick(CallbackInfo ci) {
        event.call();
    }

    /**
     * @author CuteNyami
     * @reason Borderless Fullscreen
     */
    @Overwrite
    public void toggleFullscreen() {
        try {
            this.fullscreen = !this.fullscreen;
            this.gameSettings.fullScreen = this.fullscreen;

            if (this.fullscreen) {
                System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
                Display.setResizable(false);
                this.updateDisplayMode();
                this.displayWidth = Display.getDisplayMode().getWidth();
                this.displayHeight = Display.getDisplayMode().getHeight();
            } else {
                System.setProperty("org.lwjgl.opengl.Window.undecorated", "false");
                Display.setDisplayMode(new DisplayMode(this.tempDisplayWidth, this.tempDisplayHeight));
                this.displayWidth = this.tempDisplayWidth;
                this.displayHeight = this.tempDisplayHeight;

            }
            if (this.displayWidth <= 0) {
                this.displayWidth = 1;
            }
            if (this.displayHeight <= 0) {
                this.displayHeight = 1;
            }

            if (this.currentScreen != null) {
                this.resize(this.displayWidth, this.displayHeight);
            } else {
                this.updateFramebufferSize();
            }

            Display.setVSyncEnabled(this.gameSettings.enableVsync);
            this.updateDisplay();
        } catch (Exception exception) {
            logger.error("Couldn't toggle fullscreen", exception);
        }
    }

    @Override
    @Accessor
    public abstract boolean isRunning();

    @Override
    @Accessor("mcDefaultResourcePack")
    public abstract DefaultResourcePack getDefaultResourcePack();

    @Override
    @Accessor("metadataSerializer_")
    public abstract IMetadataSerializer getMetadataSerializer();

    @Override
    @Accessor(value = "timer")
    public abstract Timer getTimerSC();

    @Override
    @Accessor(value = "session")
    public abstract void setSession(Session session);

    @Override
    @Invoker("resize")
    public abstract void resizeWindow(int width, int height);


    private long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }
}
