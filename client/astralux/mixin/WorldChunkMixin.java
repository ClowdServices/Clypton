package client.astralux.mixin;

import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_2680;
import net.minecraft.class_2818;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({class_2818.class})
public class WorldChunkMixin {
   @Shadow
   @Final
   class_1937 field_12858;

   @Inject(
      method = {"setBlockState"},
      at = {@At("TAIL")}
   )
   private void onSetBlockState(class_2338 pos, class_2680 state, boolean moved, CallbackInfoReturnable<class_2680> cir) {
      if (this.field_12858.field_9236) {
         .ho.b(new .im(pos, (class_2680)cir.getReturnValue(), state));
      }

   }
}
