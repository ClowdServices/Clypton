package client.astralux.mixin;

import net.minecraft.class_1297;
import net.minecraft.class_1309;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({class_1309.class})
public class LivingEntityNoFogMixin {
   @Inject(
      method = {"canSee"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onCanSee(class_1297 entity, CallbackInfoReturnable<Boolean> cir) {
      if (.d.isModuleEnabled()) {
         class_1309 self = (class_1309)this;
         if (self.method_5869() || self.method_5771()) {
            cir.setReturnValue(true);
         }
      }

   }
}
