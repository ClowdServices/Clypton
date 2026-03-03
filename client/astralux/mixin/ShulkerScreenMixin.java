package client.astralux.mixin;

import net.minecraft.class_1735;
import net.minecraft.class_1799;
import net.minecraft.class_310;
import net.minecraft.class_465;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({class_465.class})
public abstract class ShulkerScreenMixin {
   @Inject(
      method = {"keyPressed"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onKeyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
      if (.bw.INSTANCE != null && .bw.INSTANCE.isEnabled()) {
         if (.bw.INSTANCE.isScreenMode()) {
            class_465<?> screen = (class_465)this;
            HandledScreenMixin accessor = (HandledScreenMixin)screen;
            class_1735 focusedSlot = accessor.getFocusedSlot();
            if (focusedSlot != null && .bw.INSTANCE.isPreviewKeyPressed(keyCode)) {
               class_1799 stack = focusedSlot.method_7677();
               if (.bw.SHULKER_COLORS.containsKey(stack.method_7909())) {
                  class_310 client = class_310.method_1551();
                  client.method_1507(new .f(stack, client.field_1755));
                  cir.setReturnValue(true);
               }
            }

         }
      }
   }
}
