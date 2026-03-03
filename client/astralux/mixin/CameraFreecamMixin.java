package client.astralux.mixin;

import client.astralux.Astralux;
import net.minecraft.class_1297;
import net.minecraft.class_1922;
import net.minecraft.class_243;
import net.minecraft.class_4184;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({class_4184.class})
public abstract class CameraFreecamMixin {
   @Shadow
   protected abstract void method_19322(class_243 var1);

   @Shadow
   protected abstract void method_19325(float var1, float var2);

   @Inject(
      method = {"update"},
      at = {@At("RETURN")}
   )
   private void onUpdate(class_1922 area, class_1297 focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci) {
      .cj freecam = (.cj)Astralux.INSTANCE.getModuleManager().getModuleByClass(.cj.class);
      if (freecam != null && freecam.isEnabled()) {
         class_243 interpolatedPos = new class_243(freecam.getInterpolatedX(tickDelta), freecam.getInterpolatedY(tickDelta), freecam.getInterpolatedZ(tickDelta));
         float interpolatedYaw = (float)freecam.getInterpolatedYaw(tickDelta);
         float interpolatedPitch = (float)freecam.getInterpolatedPitch(tickDelta);
         this.method_19322(interpolatedPos);
         this.method_19325(interpolatedYaw, interpolatedPitch);
      }

   }
}
