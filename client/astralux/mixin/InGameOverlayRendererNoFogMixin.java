package client.astralux.mixin;

import net.minecraft.class_1058;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import net.minecraft.class_4603;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({class_4603.class})
public class InGameOverlayRendererNoFogMixin {
   @Inject(
      method = {"renderUnderwaterOverlay"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private static void onRenderUnderwaterOverlay(class_310 client, class_4587 matrices, CallbackInfo ci) {
      if (.d.isModuleEnabled() && .d.getInstance().shouldRemoveWaterFog()) {
         ci.cancel();
      }

   }

   @Inject(
      method = {"renderFireOverlay"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private static void onRenderFireOverlay(class_310 client, class_4587 matrices, CallbackInfo ci) {
      if (.d.isModuleEnabled() && .d.getInstance().shouldRemoveLavaFog()) {
         ci.cancel();
      }

   }

   @Inject(
      method = {"renderInWallOverlay"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private static void onRenderInWallOverlay(class_1058 sprite, class_4587 matrices, CallbackInfo ci) {
      if (.d.isModuleEnabled()) {
         ci.cancel();
      }

   }
}
