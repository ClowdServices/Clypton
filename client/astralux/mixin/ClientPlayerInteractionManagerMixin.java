package client.astralux.mixin;

import net.minecraft.class_1268;
import net.minecraft.class_1269;
import net.minecraft.class_1657;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_636;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({class_636.class})
public class ClientPlayerInteractionManagerMixin {
   @Inject(
      method = {"attackBlock"},
      at = {@At("HEAD")}
   )
   private void onAttackBlock(class_2338 pos, class_2350 dir, CallbackInfoReturnable<Boolean> cir) {
      .ho.b(new .fs(pos, dir));
   }

   @Inject(
      method = {"interactItem"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onInteractItem(class_1657 player, class_1268 hand, CallbackInfoReturnable<class_1269> cir) {
      .eo event = .eo.get(hand);
      .ho.b(event);
      if (event.toReturn != null) {
         cir.setReturnValue(event.toReturn);
      }

   }
}
