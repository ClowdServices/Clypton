package client.astralux.mixin;

import net.minecraft.class_4184;
import net.minecraft.class_5636;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({class_4184.class})
public class CameraNoFogMixin {
   @Inject(
      method = {"getSubmersionType"},
      at = {@At("RETURN")},
      cancellable = true
   )
   private void onGetSubmersionType(CallbackInfoReturnable<class_5636> cir) {
      .d module = .d.getInstance();
      if (module != null && module.isEnabled()) {
         class_5636 type = (class_5636)cir.getReturnValue();
         if (type == class_5636.field_27886 && module.shouldRemoveWaterFog()) {
            cir.setReturnValue(class_5636.field_27888);
         } else if (type == class_5636.field_27885 && module.shouldRemoveLavaFog()) {
            cir.setReturnValue(class_5636.field_27888);
         } else if (type == class_5636.field_27887 && module.shouldRemovePowderSnowFog()) {
            cir.setReturnValue(class_5636.field_27888);
         }
      }

   }
}
