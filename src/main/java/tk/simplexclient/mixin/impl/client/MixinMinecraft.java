package tk.simplexclient.mixin.impl.client;

import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.client.resources.data.IMetadataSerializer;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import tk.simplexclient.SimplexClient;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tk.simplexclient.access.AccessMinecraft;
import tk.simplexclient.event.impl.ClientTickEvent;
import net.minecraft.util.Timer;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft implements AccessMinecraft {

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
    @Invoker("resize")
    public abstract void resizeWindow(int width, int height);
}
