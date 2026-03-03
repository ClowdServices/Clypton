package client.astralux.mixin;

import net.minecraft.class_2586;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({class_2586.class})
public class BlockEntityValidationMixin {
   @Inject(
      method = {"method_61175"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private static void onValidateBlockEntity(CallbackInfo var0) {
      var0.cancel();
   }
}
