package client.astralux.mixin;

import net.minecraft.class_309;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({class_309.class})
public class KeyboardMixin {
   @Inject(
      method = {"onKey"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onPress(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
      if (key != -1) {
         .hc event = new .hc(key, window, action);
         .ho.b(event);
         if (event.isCancelled()) {
            ci.cancel();
         }

      }
   }
}
