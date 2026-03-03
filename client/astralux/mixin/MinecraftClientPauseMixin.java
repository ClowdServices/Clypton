package client.astralux.mixin;

import net.minecraft.class_310;
import net.minecraft.class_437;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({class_310.class})
public class MinecraftClientPauseMixin {
   @Shadow
   public class_437 field_1755;

   @Inject(
      method = {"openGameMenu"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onOpenGameMenu(boolean pauseOnly, CallbackInfo ci) {
      if (.gh.shouldPreventPause()) {
         ci.cancel();
      }

   }

   @Inject(
      method = {"render"},
      at = {@At("HEAD")}
   )
   private void onRender(boolean tick, CallbackInfo ci) {
      if (.gh.shouldPreventPause()) {
         class_310 var3 = (class_310)this;
      }

   }
}
