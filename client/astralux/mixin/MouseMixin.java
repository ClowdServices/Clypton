package client.astralux.mixin;

import net.minecraft.class_312;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({class_312.class})
public abstract class MouseMixin {
   @Shadow
   private double field_1789;
   @Shadow
   private double field_1787;

   @Inject(
      method = {"onMouseButton"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onMouseButton(long window, int button, int action, int mods, CallbackInfo ci) {
      if (button != -1) {
         .de event = new .de(button, window, action);
         .ho.b(event);
         if (event.isCancelled()) {
            ci.cancel();
         }

      }
   }

   @Inject(
      method = {"onMouseScroll"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onMouseScroll(long window, double horizontal, double vertical, CallbackInfo ci) {
      .cf event = new .cf(vertical);
      .ho.b(event);
      if (event.isCancelled()) {
         ci.cancel();
      }

   }

   @Inject(
      at = {@At("HEAD")},
      method = {"tick()V"}
   )
   private void onTick(CallbackInfo ci) {
      .bk event = new .bk(this.field_1789, this.field_1787);
      .ho.b(event);
      this.field_1789 = event.getDeltaX();
      this.field_1787 = event.getDeltaY();
   }
}
