package tk.simplexclient.module.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumParticleTypes;
import tk.simplexclient.event.EventTarget;
import tk.simplexclient.event.impl.EntityAttackEvent;
import tk.simplexclient.module.ModuleCreator;
import tk.simplexclient.module.settings.Option;

public class ParticlesMod extends ModuleCreator {

    private final Minecraft mc = Minecraft.getMinecraft();

    @Option(text = "Particle Multiply", min = 1, max = 10)
    private float multiplier = 4;

    private boolean extra = true;

    public ParticlesMod() {
        super("particles", 0,0);
    }

    @EventTarget
    public void onAttack(EntityAttackEvent event) {
        if (isEnabled()) {
            EntityPlayer player = mc.thePlayer;

            if (!(event.target instanceof EntityLivingBase)) {
                return;
            }

            boolean critParticle = player.fallDistance > 0.0F && !player.onGround && !player.isOnLadder() && !player.isInWater() && !player.isPotionActive(Potion.blindness) && player.ridingEntity == null;

            if (critParticle) {
                for (int i = 0; i < getValDouble("multiplier") - 1; i++) {
                    mc.effectRenderer.emitParticleAtEntity(event.target, EnumParticleTypes.CRIT);
                }
            }

            boolean usuallySharpness = EnchantmentHelper.func_152377_a(player.getHeldItem(), ((EntityLivingBase) event.target).getCreatureAttribute()) > 0;

            if (extra || usuallySharpness) {
                for (int i = 0; i < (usuallySharpness ? getValDouble("multiplier") - 1 : getValDouble("multiplier")); i++) {
                    mc.effectRenderer.emitParticleAtEntity(event.target, EnumParticleTypes.CRIT);
                }
            }
        }
    }
}
