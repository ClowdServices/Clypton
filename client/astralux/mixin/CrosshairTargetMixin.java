package client.astralux.mixin;

import client.astralux.Astralux;
import net.minecraft.class_239;
import net.minecraft.class_243;
import net.minecraft.class_310;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({class_239.class})
public class CrosshairTargetMixin {
   @Inject(
      method = {"getPos"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onGetPos(CallbackInfoReturnable<class_243> cir) {
      .cj freecam = (.cj)Astralux.INSTANCE.getModuleManager().getModuleByClass(.cj.class);
      if (freecam != null && freecam.isEnabled()) {
         StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
         boolean isInteractionCall = false;
         boolean isRenderingCall = false;
         StackTraceElement[] var6 = stackTrace;
         int var7 = stackTrace.length;
         int var8 = 0;

         while(var8 < var7) {
            StackTraceElement element = var6[var8];
            String className = element.getClassName();
            String methodName = element.getMethodName();
            if (!className.contains("InteractionManager") && !className.contains("ClientPlayerInteractionManager") && !className.contains("PlayerInteractionManager") && !methodName.contains("interact") && !methodName.contains("attack") && !methodName.contains("breakBlock") && !methodName.contains("useBlock") && !methodName.contains("useItem") && !methodName.contains("updateBlockBreakingProgress") && !methodName.contains("pickBlock")) {
               if (!className.contains("BlockESP") && !className.contains("StorageESP") && !className.contains("ESP") && !className.contains("Render") && !methodName.contains("render") && !methodName.contains("onRender")) {
                  ++var8;
                  continue;
               }

               isRenderingCall = true;
               break;
            }

            isInteractionCall = true;
            break;
         }

         if (!isInteractionCall) {
            if (isRenderingCall) {
               class_310 mc = class_310.method_1551();
               float tickDelta = mc.method_60646().method_60637(false);
               class_243 interpolatedPos = new class_243(freecam.getInterpolatedX(tickDelta), freecam.getInterpolatedY(tickDelta), freecam.getInterpolatedZ(tickDelta));
               float interpolatedYaw = (float)freecam.getInterpolatedYaw(tickDelta);
               float interpolatedPitch = (float)freecam.getInterpolatedPitch(tickDelta);
               class_243 direction = class_243.method_1030(interpolatedPitch, interpolatedYaw);
               cir.setReturnValue(interpolatedPos.method_1019(direction.method_1021(0.1D)));
            }
         }
      }
   }
}
