package client.astralux.mixin;

import net.minecraft.class_2338;
import net.minecraft.class_852;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({class_852.class})
public abstract class ChunkOcclusionDataBuilderMixin {
   @Inject(
      method = {"markClosed"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onMarkClosed(class_2338 pos, CallbackInfo ci) {
      .hq event = new .hq();
      .ho.b(event);
      if (event.isCancelled()) {
         ci.cancel();
      }

   }
}
