package tk.simplexclient.mixin;


import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Tweaker implements ITweaker {

    private final List<String> arguments = new ArrayList<>();

    @Override
    public final void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {
        //this.arguments.addAll(args);

        /*
        if (!args.contains("version") && profile != null) {
            this.arguments.add("--version");
            this.arguments.add(profile);
        }

        if (!args.contains("assetsDir") && assetsDir != null) {
            this.arguments.add("--assetsDir");
            this.arguments.add(assetsDir.getAbsolutePath());
        }

        if (!args.contains("gameDir") && gameDir != null) {
            this.arguments.add("--gameDir");
            this.arguments.add(gameDir.getAbsolutePath());
        }
*/

    }

    @Override
    public final void injectIntoClassLoader(LaunchClassLoader classLoader) {
        MixinBootstrap.init();

        MixinEnvironment environment = MixinEnvironment.getDefaultEnvironment();

        Mixins.addConfiguration("mixins.client.json");

        if (environment.getObfuscationContext() == null) {
            environment.setObfuscationContext("notch");
        }

        environment.setSide(MixinEnvironment.Side.CLIENT);
    }

    @Override
    public String getLaunchTarget() {
        return MixinBootstrap.getPlatform().getLaunchTarget();
    }

    @Override
    public String[] getLaunchArguments() {
        return this.arguments.toArray(new String[0]);
    }
}