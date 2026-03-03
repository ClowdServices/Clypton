package client.astralux.mixin;

import java.util.List;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_1836;
import net.minecraft.class_2561;
import net.minecraft.class_9288;
import net.minecraft.class_9334;
import net.minecraft.class_1792.class_9635;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({class_1799.class})
public class ItemStackMixin {
   @Inject(
      method = {"getTooltip"},
      at = {@At("RETURN")}
   )
   private void onGetTooltip(class_9635 context, @Nullable class_1657 player, class_1836 type, CallbackInfoReturnable<List<class_2561>> cir) {
      if (.bw.INSTANCE != null && .bw.INSTANCE.isEnabled()) {
         if (.bw.INSTANCE.isTooltipMode()) {
            if (.bw.INSTANCE.isKeyPressed()) {
               class_1799 stack = (class_1799)this;
               if (.bw.SHULKER_COLORS.containsKey(stack.method_7909())) {
                  class_9288 container = (class_9288)stack.method_57824(class_9334.field_49622);
                  if (container != null) {
                     List<class_2561> tooltip = (List)cir.getReturnValue();
                     tooltip.add(class_2561.method_43470(""));
                     tooltip.add(class_2561.method_43470("§7Contents:"));
                     container.method_57489().filter((item) -> {
                        return !item.method_7960();
                     }).forEach((item) -> {
                        String var10001 = item.method_7964().getString();
                        tooltip.add(class_2561.method_43470("  §8- §r" + var10001 + " §7x" + item.method_7947()));
                     });
                  }
               }
            }
         }
      }
   }
}
