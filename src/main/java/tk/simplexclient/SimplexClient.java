package tk.simplexclient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.KeyBinding;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import tk.simplexclient.event.EventManager;
import tk.simplexclient.font.FontRenderer;
import tk.simplexclient.gui.mod.theme.ThemeManager;
import tk.simplexclient.gui.mod.theme.themes.LightTheme;
import tk.simplexclient.gui.utils.SimplexGui;
import tk.simplexclient.listener.TickListener;
import tk.simplexclient.module.ModuleConfig;
import tk.simplexclient.module.ModuleCreator;
import tk.simplexclient.module.ModuleManager;
import tk.simplexclient.module.impl.hud.*;
import tk.simplexclient.module.settings.SettingsManager;
import tk.simplexclient.shader.RoundedShaderRenderer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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

    @Getter private SettingsManager settingsBuilder;

    @Getter @Setter private ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

    public KeyBinding CLICK_GUI = new KeyBinding("Open the Settings GUI", Keyboard.KEY_RSHIFT, "SimplexClient");
    public KeyBinding TEST_GUI = new KeyBinding("Just a gui to test some things", Keyboard.KEY_0, "SimplexClient");

    public void init() {
        //SplashProgress.setProgress(1, "Client - Initialising Client");
    }

    public void start() {
        //SplashProgress.setProgress(7, "Client - Starting Client");
        smoothFont = new FontRenderer("smooth", 15.0F);
        instance = this;
        gui = new SimplexGui();
        roundedShaderRenderer = new RoundedShaderRenderer();

        Display.setTitle("SimplexClient v2 | 1.8.9");

        // Module instances
        moduleConfig = new ModuleConfig();
        moduleManager = new ModuleManager();

        // Modules
        moduleManager.registerModules(
                new FPSModule(),
                new KeyStrokesModule(),
                new MemoryModule(),
                new PingModule(),
                new TimeModule(),
                new CoordinatesModule(),
                new TargetHUDModule(),
                new ArmorStatusModule(),
                //new ServerDisplayModule(),
                moduleManager.tntTimer,
                moduleManager.motionBlur,
                moduleManager.toggleSprint,
                moduleManager.itemPhysics,
                moduleManager.v1_7Visual
        );

        settingsBuilder = new SettingsManager();

        for (ModuleCreator moduleCreator : moduleManager.getModules()) {
            settingsBuilder.setup(moduleCreator);
        }

        // Keybindings
        registerKeyBind(CLICK_GUI);

        ThemeManager.registerTheme(new LightTheme());

        // Events
        EventManager.register(new TickListener());
        EventManager.register(moduleManager.v1_7Visual);

        for (ModuleCreator module : moduleManager.getModules()) {
            module.onEnable();
        }
    }

    public void stop() {
        logger.info("Saving module config...");
        moduleConfig.saveModuleConfig();

        EventManager.unregister(new TickListener());
        EventManager.unregister(moduleManager.v1_7Visual);

        for (ModuleCreator module : moduleManager.getModules()) {
            module.onDisable();
        }
    }

    public boolean isDebug() {
        File client = new File("simplex/client");
        boolean debug = false;
        if (client.exists()) {
            String type = readFile(client);
            debug = type.equals("debug");
        }
        return debug;
    }

    public String readFile(File file) {
        String string = null;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            string = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return string;
    }

    public static void registerKeyBind(KeyBinding key) {
        Minecraft.getMinecraft().gameSettings.keyBindings = ArrayUtils.add(Minecraft.getMinecraft().gameSettings.keyBindings, key);
    }

    public static void unregisterKeyBind(KeyBinding key) {
        if (Arrays.asList(Minecraft.getMinecraft().gameSettings.keyBindings).contains(key))
            Minecraft.getMinecraft().gameSettings.keyBindings = ArrayUtils.remove(Minecraft.getMinecraft().gameSettings.keyBindings, Arrays.asList(Minecraft.getMinecraft().gameSettings.keyBindings).indexOf(key));
    }
}
