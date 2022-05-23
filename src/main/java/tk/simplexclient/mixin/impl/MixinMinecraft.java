package tk.simplexclient.mixin.impl;

import net.minecraft.client.resources.DefaultResourcePack;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.gen.Accessor;
import tk.simplexclient.SimplexClient;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tk.simplexclient.event.impl.ClientTickEvent;
import tk.simplexclient.event.impl.KeyEvent;

@Mixin(Minecraft.class)
public class MixinMinecraft {

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
}
