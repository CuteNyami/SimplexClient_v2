package tk.simplexclient.mixin.impl.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.Session;
import net.minecraft.util.Timer;
import org.lwjgl.Sys;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.access.AccessMinecraft;
import tk.simplexclient.animations.Delta;
import tk.simplexclient.event.impl.ClientTickEvent;
import tk.simplexclient.module.impl.MotionBlur;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft implements AccessMinecraft {

    private long lastFrame = getTime();

    /*
    @Inject(method = "startGame", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/TextureMap;setBlurMipmapDirect(ZZ)V"))
    public void modelManagerProgress(CallbackInfo ci) {
        SplashProgress.setProgress(2, "Minecraft - ModelManager");
    }

    @Inject(method = "startGame", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderItem;<init>(Lnet/minecraft/client/renderer/texture/TextureManager;Lnet/minecraft/client/resources/model/ModelManager;)V"))
    public void renderItemProgress(CallbackInfo ci) {
        SplashProgress.setProgress(3, "Minecraft - RenderItem");
    }

    @Inject(method = "startGame", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderManager;<init>(Lnet/minecraft/client/renderer/texture/TextureManager;Lnet/minecraft/client/renderer/entity/RenderItem;)V"))
    public void renderManagerProgress(CallbackInfo ci) {
        SplashProgress.setProgress(4, "Minecraft - RenderManager");
    }

    @Inject(method = "startGame", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderGlobal;<init>(Lnet/minecraft/client/Minecraft;)V"))
    public void renderGlobal(CallbackInfo ci) {
        SplashProgress.setProgress(6, "Minecraft - RenderGlobal");
    }

    @Inject(method = "startGame", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/BlockRendererDispatcher;<init>(Lnet/minecraft/client/renderer/BlockModelShapes;Lnet/minecraft/client/settings/GameSettings;)V"))
    public void blockRenderDispatcherProgress(CallbackInfo ci) {
        SplashProgress.setProgress(5, "Minecraft - BlockRenderDispatcher");
    }
    */

    @Inject(method = "startGame", at = @At("RETURN"))
    public void injectStartGame(CallbackInfo ci) {
        new SimplexClient().start();
    }

    /**
     * @author CuteNyami
     * @reason idk
     */
    /*
    @Overwrite
    private void drawSplashScreen(TextureManager textureManagerInstance) {
        new SimplexClient().init();
        SplashProgress.draw(textureManagerInstance);
    }

     */
    @Inject(method = "runGameLoop", at = @At("HEAD"))
    public void delta(CallbackInfo ci) {
        long currentTime = getTime();
        int deltaTime = (int) (currentTime - lastFrame);
        lastFrame = currentTime;
        Delta.DELTATIME = deltaTime;
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
        ClientTickEvent event = new ClientTickEvent();
        event.call();
    }

    @Override
    @Accessor
    public abstract boolean isRunning();

    @Override
    @Accessor("mcDefaultResourcePack")
    public abstract DefaultResourcePack getDefaultResourcePack();

    @Override
    @Accessor("metadataSerializer_")
    public abstract IMetadataSerializer getMetadataSerialiser();

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
