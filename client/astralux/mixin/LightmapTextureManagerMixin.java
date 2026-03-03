package client.astralux.mixin;

import client.astralux.Main;
import net.minecraft.class_765;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin({class_765.class})
public abstract class LightmapTextureManagerMixin {
   @ModifyArgs(
      method = {"update"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/texture/NativeImage;setColor(III)V"
)
   )
   private void onUpdateLightmap(Args args) {
      .dh fullbright = (.dh)Main.getAstralux().getModuleManager().getModuleByClass(.dh.class);
      if (fullbright != null && fullbright.isEnabled()) {
         args.set(2, -1);
      }

   }
}
