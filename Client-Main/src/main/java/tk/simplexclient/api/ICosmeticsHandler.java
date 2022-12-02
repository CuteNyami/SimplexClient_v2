package tk.simplexclient.api;

import net.minecraft.client.renderer.entity.RenderPlayer;
import tk.simplexclient.cosmetics.Cosmetic;

import java.util.List;

public interface ICosmeticsHandler {

    /**
     * Disable an enabled cosmetic if it is enabled
     *
     * @param cosmetic The given cosmetic
     */
    void disableCosmetic(Cosmetic cosmetic);

    /**
     * Disable multiple enabled cosmetics at once
     *
     * @param cosmetics The given cosmetics
     */
    void disableCosmetics(Cosmetic... cosmetics);

    /**
     * Enable a disabled cosmetic if it is disabled
     *
     * @param cosmetic The given cosmetic
     */
    void enableCosmetic(Cosmetic cosmetic);

    /**
     * Enable multiple disabled cosmetics at once
     *
     * @param cosmetics The given cosmetics
     */
    void enableCosmetics(Cosmetic... cosmetics);

    /**
     * Register multiple cosmetics at once
     *
     * @param cosmetics The not registered cosmetics
     */
    void registerCosmetics(Cosmetic... cosmetics);

    /**
     * Register a cosmetic
     *
     * @param cosmetic The not registered cosmetic
     */
    void registerCosmetic(Cosmetic cosmetic);

    /**
     * Lists all existing cosmetics in a list
     *
     * @return The existing cosmetics
     */
    List<Cosmetic> getCosmetics();

    /**
     * Lists all enabled cosmetics in a list
     *
     * @return The enabled cosmetics
     * @deprecated use {@link ICosmeticsHandler#getCosmetics()} instead
     */
    @Deprecated
    List<Cosmetic> getEnabledCosmetics();

    /**
     * Lists all disabled cosmetics in a list
     *
     * @return The disabled cosmetics
     */
    List<Cosmetic> getDisabledCosmetics();

    /**
     * Lists all cosmetics that exists in a list
     *
     * @return The registered cosmetics
     */
    List<Cosmetic> getAllCosmetics();

    /**
     * Get the current used Player renderer
     *
     * @return The player renderer that is currently used
     */
    RenderPlayer getPlayerRenderer();

    /**
     * Check if a specific cosmetic is enabled
     *
     * @param cosmetic The cosmetic that is used to check if it is enabled
     * @return true if it is enabled and false if it is disabled
     */
    boolean isEnabled(Cosmetic cosmetic);

}
