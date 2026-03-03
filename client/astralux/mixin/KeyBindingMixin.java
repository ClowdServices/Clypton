package client.astralux.mixin;

import client.astralux.Astralux;
import net.minecraft.class_304;
import net.minecraft.class_3675;
import net.minecraft.class_3675.class_306;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({class_304.class})
public abstract class KeyBindingMixin implements .bn {
   @Shadow
   private class_306 field_1655;

   @Shadow
   public abstract void method_23481(boolean var1);

   public boolean astralux$isActuallyPressed() {
      return class_3675.method_15987(Astralux.mc.method_22683().method_4490(), this.field_1655.method_1444());
   }

   public void astralux$resetPressed() {
      this.method_23481(this.astralux$isActuallyPressed());
   }
}
