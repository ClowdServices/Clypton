package client.astralux.mixin;

import net.minecraft.class_2794;
import net.minecraft.class_5868;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({class_5868.class})
public abstract class HeightContextMixin {
   @Redirect(
      method = {"<init>"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/world/gen/chunk/ChunkGenerator;getMinimumY()I"
)
   )
   private int onMinY(class_2794 cg) {
      return cg == null ? -9999999 : cg.method_33730();
   }

   @Redirect(
      method = {"<init>"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/world/gen/chunk/ChunkGenerator;getWorldHeight()I"
)
   )
   private int onHeight(class_2794 cg) {
      return cg == null ? 100000000 : cg.method_12104();
   }
}
