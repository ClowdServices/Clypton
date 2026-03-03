package client.astralux.mixin;

import client.astralux.Main;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_1944;
import net.minecraft.class_897;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({class_897.class})
public abstract class EntityRendererMixin {
   @ModifyReturnValue(
      method = {"getSkyLight"},
      at = {@At("RETURN")}
   )
   private int onGetSkyLight(int original) {
      .dh fullbright = (.dh)Main.getAstralux().getModuleManager().getModuleByClass(.dh.class);
      return fullbright != null && fullbright.isEnabled() ? Math.max(fullbright.getLuminance(class_1944.field_9284), original) : original;
   }

   @ModifyReturnValue(
      method = {"getBlockLight"},
      at = {@At("RETURN")}
   )
   private int onGetBlockLight(int original) {
      .dh fullbright = (.dh)Main.getAstralux().getModuleManager().getModuleByClass(.dh.class);
      return fullbright != null && fullbright.isEnabled() ? Math.max(fullbright.getLuminance(class_1944.field_9282), original) : original;
   }

   @Inject(
      method = {"hasLabel"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void hidePlayerNametags(class_1297 entity, CallbackInfoReturnable<Boolean> cir) {
      if (entity instanceof class_1657) {
         .bx nametagESP = (.bx)Main.getAstralux().getModuleManager().getModuleByClass(.bx.class);
         if (nametagESP != null && nametagESP.isEnabled()) {
            cir.setReturnValue(false);
         }
      }

   }
}
