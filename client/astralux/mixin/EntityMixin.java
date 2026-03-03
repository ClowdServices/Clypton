package client.astralux.mixin;

import client.astralux.Astralux;
import net.minecraft.class_1297;
import net.minecraft.class_4050;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({class_1297.class})
public class EntityMixin {
   @Inject(
      method = {"getTargetingMargin"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onSendMovementPackets(CallbackInfoReturnable<Float> cir) {
      .ho.b(new .bs((class_1297)class_1297.class.cast(this), cir));
   }

   @Inject(
      method = {"getPose"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onGetPose(CallbackInfoReturnable<class_4050> cir) {
      .ho.b(new .hb((class_1297)class_1297.class.cast(this), cir));
   }

   @Inject(
      method = {"changeLookDirection"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void updateChangeLookDirection(double cursorDeltaX, double cursorDeltaY, CallbackInfo ci) {
      if (Astralux.mc != null) {
         if (Astralux.mc.field_1724 != null) {
            if (class_1297.class.cast(this) == Astralux.mc.field_1724) {
               .cj freecam = (.cj)Astralux.INSTANCE.MODULE_MANAGER.getModuleByClass(.cj.class);
               if (freecam.isEnabled()) {
                  freecam.updateRotation(cursorDeltaX * 0.15D, cursorDeltaY * 0.15D);
                  ci.cancel();
               }

            }
         }
      }
   }
}
