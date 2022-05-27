package tk.simplexclient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.input.Keyboard;
import tk.simplexclient.event.EventManager;
import tk.simplexclient.event.EventTarget;
import tk.simplexclient.event.impl.ClientTickEvent;
import tk.simplexclient.font.FontRenderer;
import tk.simplexclient.gui.utils.SimplexGui;
import tk.simplexclient.listener.TickListener;
import tk.simplexclient.module.ModuleConfig;
import tk.simplexclient.module.ModuleManager;
import tk.simplexclient.module.dragging.GuiModuleDrag;
import tk.simplexclient.module.impl.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tk.simplexclient.shader.ShaderManager;

import java.util.Arrays;

public final class SimplexClient {

    @Getter private static SimplexClient instance;

    // Client Logger
    @Getter private static final Logger logger = LogManager.getLogger();

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

    @Getter private SimplexGui gui;

    @Getter private ModuleManager moduleManager;

    @Getter private ModuleConfig moduleConfig;

    @Getter private ShaderManager shaderManager;

    @Getter private FontRenderer smoothFont;

    public KeyBinding CLICK_GUI = new KeyBinding("Open the Settings GUI", Keyboard.KEY_RSHIFT, "SimplexClient");

    public void init() {
    }

    public void start() {
        instance = this;
        gui = new SimplexGui();
        smoothFont = new FontRenderer("smooth", 15.0F);

        // Module instances
        moduleManager = new ModuleManager();
        moduleConfig = new ModuleConfig();

        // Shader
        shaderManager = new ShaderManager();

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

        // Events
        EventManager.register(new TickListener());
        EventManager.register(new V1_7VisualsMod());
    }

    public void stop() {
        logger.info("Saving module config...");
        moduleConfig.saveModuleConfig();

        EventManager.unregister(new TickListener());
        EventManager.unregister(new V1_7VisualsMod());
    }

    public static void registerKeyBind(KeyBinding key) {
        Minecraft.getMinecraft().gameSettings.keyBindings = ArrayUtils.add(Minecraft.getMinecraft().gameSettings.keyBindings, key);
    }

    public static void unregisterKeyBind(KeyBinding key) {
        if (Arrays.asList(Minecraft.getMinecraft().gameSettings.keyBindings).contains(key))
            Minecraft.getMinecraft().gameSettings.keyBindings = ArrayUtils.remove(Minecraft.getMinecraft().gameSettings.keyBindings, Arrays.asList(Minecraft.getMinecraft().gameSettings.keyBindings).indexOf(key));
    }

    @EventTarget
    public void onTick(ClientTickEvent event) {
        if (CLICK_GUI.isPressed()) {
            Minecraft.getMinecraft().displayGuiScreen(new GuiModuleDrag());
        }
    }
}
