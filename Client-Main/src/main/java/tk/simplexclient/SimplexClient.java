package tk.simplexclient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.netty.buffer.Unpooled;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.optifine.http.HttpUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import tk.simplexclient.api.ICosmeticsHandler;
import tk.simplexclient.cosmetics.CosmeticsManager;
import tk.simplexclient.cosmetics.impl.DragonWings;
import tk.simplexclient.event.EventManager;
import tk.simplexclient.font.FontRenderer;
import tk.simplexclient.gui.mod.theme.ThemeManager;
import tk.simplexclient.gui.mod.theme.themes.LightTheme;
import tk.simplexclient.gui.utils.SimplexGui;
import tk.simplexclient.improvements.AIImprovements;
import tk.simplexclient.listener.TickListener;
import tk.simplexclient.module.ModuleConfig;
import tk.simplexclient.module.ModuleCreator;
import tk.simplexclient.module.ModuleManager;
import tk.simplexclient.module.impl.DiscordRP;
import tk.simplexclient.module.impl.HitColor;
import tk.simplexclient.module.impl.ParticlesMod;
import tk.simplexclient.module.impl.XPOrbColor;
import tk.simplexclient.module.impl.hud.*;
import tk.simplexclient.module.settings.SettingsManager;
import tk.simplexclient.packets.packet.PacketManager;
import tk.simplexclient.shader.RoundedShaderRenderer;
import tk.simplexclient.utils.CapeUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.regex.Pattern;

@Getter
@Setter
public final class SimplexClient {

    /**
     * Not deleting for getter bc @Getter at class work for non-static fields
     */

    @Getter
    private static SimplexClient instance;

    // Client Logger
    @Getter
    private static final Logger logger = LogManager.getLogger();

    @Getter
    private static RoundedShaderRenderer roundedShaderRenderer;

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

    private SimplexGui gui;

    private ModuleManager moduleManager;

    private ModuleConfig moduleConfig;
    private FontRenderer smoothFont;

    private SettingsManager settingsBuilder;

    private ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

    private ICosmeticsHandler cosmeticsHandler;

    @Getter
    AIImprovements aiImprovements;

    @Getter
    @Setter
    private boolean haveCape = false;

    public KeyBinding CLICK_GUI = new KeyBinding("Open the Settings GUI", Keyboard.KEY_RSHIFT, "SimplexClient");
    public KeyBinding COSMETICS_GUI_DEBUG = new KeyBinding("Debug Cosmetics GUI", Keyboard.KEY_Z, "SimplexClient");

    public void init() {
        /*
        try {
            Field field = net.optifine.player.CapeUtils.class.getDeclaredField("PATTERN_USERNAME");
            field.setAccessible(true);
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
            field.set(null, Pattern.compile(""));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        try {
            Field field = HttpUtils.class.getDeclaredField("playerItemsUrl");
            field.setAccessible(true);
            field.set(null, "");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

         */
    }

    public void start() {
        cosmeticsHandler = new CosmeticsManager();
        aiImprovements = new AIImprovements();
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
                new CPSModule(),
                new KeyStrokesModule(),
                new MemoryModule(),
                new PingModule(),
                new TimeModule(),
                new CoordinatesModule(),
                new TargetHUDModule(),
                new ArmorStatusModule(),
                new ComboCounterModule(),
                new ReachDisplayModule(),
                new PotionStatusModule(),
                new ParticlesMod(),
                new HitColor(),
                new XPOrbColor(),
                moduleManager.tntTimer,
                moduleManager.motionBlur,
                moduleManager.toggleSprint,
                moduleManager.itemPhysics,
                moduleManager.v1_7Visual,
                moduleManager.minimalViewBobbing
        );

        settingsBuilder = new SettingsManager();

        for (ModuleCreator moduleCreator : moduleManager.getModules()) {
            settingsBuilder.setup(moduleCreator);
        }

        PacketManager.registerPackets();

        // Keybindings
        registerKeyBind(CLICK_GUI);

        // Events
        EventManager.register(new TickListener());
        EventManager.register(new DiscordRP());
        EventManager.register(moduleManager.v1_7Visual);
        EventManager.register(aiImprovements);

        cosmeticsHandler.registerCosmetic(new DragonWings(cosmeticsHandler.getPlayerRenderer()));

        for (ModuleCreator module : moduleManager.getModules()) {
            module.onEnable();
        }

        haveCape = CapeUtils.haveCape(Minecraft.getMinecraft().getSession().getUsername());

        //sendPayloadToServer("simplex_client:api", "Test");
    }

    public void stop() {
        logger.info("Saving module config...");
        moduleConfig.saveModuleConfig();

        EventManager.unregister(new TickListener());
        EventManager.unregister(new DiscordRP());
        EventManager.unregister(moduleManager.v1_7Visual);
        EventManager.unregister(aiImprovements);

        for (ModuleCreator module : moduleManager.getModules()) {
            module.onDisable();
        }

        //DiscordRP.shutdown();
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

    public void sendPayloadToServer(String channel, String payload) {
        Minecraft mc = Minecraft.getMinecraft();
        mc.getNetHandler().addToSendQueue(new C17PacketCustomPayload(channel, new PacketBuffer(Unpooled.buffer()).writeString(payload)));
    }
}
