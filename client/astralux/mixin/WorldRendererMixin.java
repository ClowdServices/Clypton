package client.astralux.mixin;

import client.astralux.Main;
import net.minecraft.class_1944;
import net.minecraft.class_761;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin({class_761.class})
public abstract class WorldRendererMixin {
   @ModifyVariable(
      method = {"getLightmapCoordinates(Lnet/minecraft/world/BlockRenderView;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;)I"},
      at = @At("STORE"),
      ordinal = 0
   )
   private static int modifySkyLight(int skyLight) {
      .dh fullbright = (.dh)Main.getAstralux().getModuleManager().getModuleByClass(.dh.class);
      return fullbright != null && fullbright.isEnabled() ? Math.max(fullbright.getLuminance(class_1944.field_9284), skyLight) : skyLight;
   }

   @ModifyVariable(
      method = {"getLightmapCoordinates(Lnet/minecraft/world/BlockRenderView;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;)I"},
      at = @At("STORE"),
      ordinal = 1
   )
   private static int modifyBlockLight(int blockLight) {
      .dh fullbright = (.dh)Main.getAstralux().getModuleManager().getModuleByClass(.dh.class);
      return fullbright != null && fullbright.isEnabled() ? Math.max(fullbright.getLuminance(class_1944.field_9282), blockLight) : blockLight;
   }
}
