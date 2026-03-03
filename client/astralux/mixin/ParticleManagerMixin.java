package client.astralux.mixin;

import net.minecraft.class_2394;
import net.minecraft.class_243;
import net.minecraft.class_702;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({class_702.class})
public class ParticleManagerMixin {
   @Inject(
      method = {"addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)Lnet/minecraft/client/particle/Particle;"},
      at = {@At("HEAD")}
   )
   private void onAddParticle(class_2394 parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ, CallbackInfoReturnable<?> cir) {
      try {
         .ar tracker = .ar.getInstance();
         if (tracker.isEnabled()) {
            class_243 position = new class_243(x, y, z);
            class_243 velocity = new class_243(velocityX, velocityY, velocityZ);
            tracker.trackParticle(parameters, position, velocity);
         }
      } catch (Exception var18) {
      }

   }
}
