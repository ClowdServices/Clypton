package client.astralux.mixin;

import client.astralux.Astralux;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_757;
import net.minecraft.class_9779;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({class_757.class})
public abstract class GameRendererMixin {
   @Shadow
   @Final
   private class_4184 field_18765;

   @Shadow
   protected abstract double method_3196(class_4184 var1, float var2, boolean var3);

   @Shadow
   public abstract Matrix4f method_22973(double var1);

   @Inject(
      method = {"renderWorld"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V",
   ordinal = 1
)}
   )
   private void onWorldRender(class_9779 rtc, CallbackInfo ci, @Local class_4587 matrixStack, @Local(ordinal = 1) Matrix4f matrix4f2, @Local(ordinal = 1) float tickDelta) {
      .ho.b(new .bg(new class_4587(), this.method_22973(this.method_3196(this.field_18765, rtc.method_60637(true), true)), rtc.method_60637(true)));
   }

   @Inject(
      method = {"shouldRenderBlockOutline"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onShouldRenderBlockOutline(CallbackInfoReturnable<Boolean> cir) {
      if (Astralux.INSTANCE.getModuleManager().getModuleByClass(.cj.class).isEnabled()) {
         cir.setReturnValue(false);
      }

   }
}
