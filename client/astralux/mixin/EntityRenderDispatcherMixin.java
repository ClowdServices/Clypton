package client.astralux.mixin;

import client.astralux.Astralux;
import net.minecraft.class_238;
import net.minecraft.class_898;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin({class_898.class})
public class EntityRenderDispatcherMixin {
   @ModifyVariable(
      method = {"renderHitbox"},
      ordinal = 0,
      at = @At(
   value = "STORE",
   ordinal = 0
)
   )
   private static class_238 onRenderHitboxEditBox(class_238 box) {
      .bf hitboxes = Astralux.INSTANCE.MODULE_MANAGER.getModuleByClass(.ev.class);
      return hitboxes.isEnabled() ? box.method_1014(((.ev)hitboxes).getHitboxExpansion()) : box;
   }
}
