package client.astralux.mixin;

import client.astralux.Astralux;
import net.minecraft.class_433;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({class_433.class})
public class MenuKeyPressMixin {
   @Inject(
      method = {"keyPressed"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onKeyPress(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
      if (keyCode == 344) {
         .bf astraluxModule = Astralux.INSTANCE.getModuleManager().getModuleByClass(.gm.class);
         if (astraluxModule != null) {
            astraluxModule.toggle();
            cir.setReturnValue(true);
         }
      }

   }
}
