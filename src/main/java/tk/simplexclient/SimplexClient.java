package tk.simplexclient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import tk.simplexclient.event.EventManager;
import tk.simplexclient.font.FontRenderer;
import tk.simplexclient.gui.mod.theme.ThemeManager;
import tk.simplexclient.gui.mod.theme.themes.LightTheme;
import tk.simplexclient.gui.utils.SimplexGui;
import tk.simplexclient.listener.TickListener;
import tk.simplexclient.module.ModuleConfig;
import tk.simplexclient.module.ModuleManager;
import tk.simplexclient.module.impl.*;
import tk.simplexclient.shader.RoundedShaderRenderer;

import java.util.Arrays;

public final class SimplexClient {

    @Getter private static SimplexClient instance;

    // Client Logger
    @Getter private static final Logger logger = LogManager.getLogger();

    @Getter private static RoundedShaderRenderer roundedShaderRenderer;

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

    @Getter private SimplexGui gui;

    @Getter private ModuleManager moduleManager;

    @Getter private ModuleConfig moduleConfig;

    @Getter private FontRenderer smoothFont;

    public KeyBinding CLICK_GUI = new KeyBinding("Open the Settings GUI", Keyboard.KEY_RSHIFT, "SimplexClient");
    public KeyBinding TEST_GUI = new KeyBinding("Just a gui to test some things", Keyboard.KEY_0, "SimplexClient");

    public void init() {

    }

    public void start() {
        instance = this;
        gui = new SimplexGui();
        smoothFont = new FontRenderer("smooth", 15.0F);
        roundedShaderRenderer = new RoundedShaderRenderer();

        // Module instances
        moduleConfig = new ModuleConfig();
        moduleManager = new ModuleManager();

        // Modules
        moduleManager.registerModules(
                new FPSModule(),
                new KeyStrokesModule(),
                new MemoryMod(),
                new PingMod(),
                new TimeMod(),
                moduleManager.itemPhysics,
                moduleManager.v1_7Visual
        );

        // Keybindings
        registerKeyBind(CLICK_GUI);
        registerKeyBind(TEST_GUI);

        ThemeManager.registerTheme(new LightTheme());

        // Events
        EventManager.register(new TickListener());
        EventManager.register(moduleManager.v1_7Visual);
    }

    public void stop() {
        logger.info("Saving module config...");
        moduleConfig.saveModuleConfig();

        EventManager.unregister(new TickListener());
        EventManager.unregister(moduleManager.v1_7Visual);
    }

    public static void registerKeyBind(KeyBinding key) {
        Minecraft.getMinecraft().gameSettings.keyBindings = ArrayUtils.add(Minecraft.getMinecraft().gameSettings.keyBindings, key);
    }

    public static void unregisterKeyBind(KeyBinding key) {
        if (Arrays.asList(Minecraft.getMinecraft().gameSettings.keyBindings).contains(key))
            Minecraft.getMinecraft().gameSettings.keyBindings = ArrayUtils.remove(Minecraft.getMinecraft().gameSettings.keyBindings, Arrays.asList(Minecraft.getMinecraft().gameSettings.keyBindings).indexOf(key));
    }
}
