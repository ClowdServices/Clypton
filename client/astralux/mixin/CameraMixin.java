package client.astralux.mixin;

import client.astralux.Astralux;
import net.minecraft.class_1297;
import net.minecraft.class_1922;
import net.minecraft.class_4184;
import net.minecraft.class_5636;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin({class_4184.class})
public class CameraMixin {
   @Unique
   private float tickDelta;

   @Inject(
      method = {"update"},
      at = {@At("HEAD")}
   )
   private void onUpdateHead(class_1922 area, class_1297 focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci) {
      this.tickDelta = tickDelta;
   }

   @ModifyArgs(
      method = {"update"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/render/Camera;setPos(DDD)V"
)
   )
   private void update(Args args) {
      .cj freecam = (.cj)Astralux.INSTANCE.MODULE_MANAGER.getModuleByClass(.cj.class);
      if (freecam.isEnabled()) {
         args.set(0, freecam.getInterpolatedX(this.tickDelta));
         args.set(1, freecam.getInterpolatedY(this.tickDelta));
         args.set(2, freecam.getInterpolatedZ(this.tickDelta));
      }

   }

   @ModifyArgs(
      method = {"update"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/render/Camera;setRotation(FF)V"
)
   )
   private void onUpdateSetRotationArgs(Args args) {
      .cj freecam = (.cj)Astralux.INSTANCE.MODULE_MANAGER.getModuleByClass(.cj.class);
      if (freecam.isEnabled()) {
         args.set(0, (float)freecam.getInterpolatedYaw(this.tickDelta));
         args.set(1, (float)freecam.getInterpolatedPitch(this.tickDelta));
      }

   }

   @Inject(
      at = {@At("HEAD")},
      method = {"getSubmersionType()Lnet/minecraft/block/enums/CameraSubmersionType;"},
      cancellable = true
   )
   private void onGetSubmersionType(CallbackInfoReturnable<class_5636> cir) {
      .be nfo = (.be)Astralux.INSTANCE.getModuleManager().getModuleByClass(.be.class);
      if (nfo.isEnabled() && nfo.removeWater.getValue()) {
         cir.setReturnValue(class_5636.field_27888);
      }

   }
}
