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
import tk.simplexclient.event.impl.ClientTickEvent;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft implements AccessMinecraft {

    private long lastFrame = getTime();

    @Inject(method = "startGame", at = @At(value = "HEAD"))
    public void init(CallbackInfo ci) {
        new SimplexClient().init();
    }

    @Inject(method = "startGame", at = @At("RETURN"))
    public void injectStartGame(CallbackInfo ci) {
        new SimplexClient().start();
    }

    @Inject(method = "shutdownMinecraftApplet", at = @At("HEAD"))
    public void injectShutdown(CallbackInfo ci) {
        SimplexClient.getInstance().stop();
    }

    @Inject(method = "runTick", at = @At("TAIL"))
    public void runTick(CallbackInfo ci) {
        ClientTickEvent event = new ClientTickEvent();
        event.call();

        long currentTime = getTime();
        int deltaTime = (int) (currentTime - lastFrame);
        lastFrame = currentTime;
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
