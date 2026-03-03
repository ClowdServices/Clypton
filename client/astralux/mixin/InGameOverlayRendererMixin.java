package client.astralux.mixin;

import client.astralux.Astralux;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import net.minecraft.class_4603;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({class_4603.class})
public class InGameOverlayRendererMixin {
   @ModifyConstant(
      method = {"renderFireOverlay(Lnet/minecraft/client/MinecraftClient;Lnet/minecraft/client/util/math/MatrixStack;)V"},
      constant = {@Constant(
   floatValue = -0.3F
)}
   )
   private static float getFireOffset(float original) {
      .be nfo = (.be)Astralux.INSTANCE.getModuleManager().getModuleByClass(.be.class);
      return original - nfo.getOverlayOffset();
   }

   @Inject(
      at = {@At("HEAD")},
      method = {"renderUnderwaterOverlay(Lnet/minecraft/client/MinecraftClient;Lnet/minecraft/client/util/math/MatrixStack;)V"},
      cancellable = true
   )
   private static void onRenderUnderwaterOverlay(class_310 client, class_4587 matrices, CallbackInfo ci) {
      .be nfo = (.be)Astralux.INSTANCE.getModuleManager().getModuleByClass(.be.class);
      if (nfo.isEnabled() && nfo.removeWater.getValue()) {
         ci.cancel();
      }

   }
}
