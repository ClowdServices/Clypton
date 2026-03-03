package client.astralux.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.class_310;
import net.minecraft.class_638;
import net.minecraft.class_742;
import net.minecraft.class_746;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({class_746.class})
public class ClientPlayerEntityMixin extends class_742 {
   @Shadow
   @Final
   protected class_310 field_3937;

   public ClientPlayerEntityMixin(class_638 world, GameProfile profile) {
      super(world, profile);
   }

   @Inject(
      method = {"sendMovementPackets"},
      at = {@At("HEAD")}
   )
   private void onSendMovementPackets(CallbackInfo ci) {
      .ho.b(new .fu());
   }

   @Inject(
      method = {"tick"},
      at = {@At("HEAD")}
   )
   private void onPlayerTick(CallbackInfo ci) {
      .ho.b(new .hm());
   }

   @Inject(
      at = {@At("HEAD")},
      method = {"sendMovementPackets()V"}
   )
   private void onSendMovementPacketsHEAD(CallbackInfo ci) {
      .ho.b(new .ge());
   }

   @Inject(
      at = {@At("TAIL")},
      method = {"sendMovementPackets()V"}
   )
   private void onSendMovementPacketsTAIL(CallbackInfo ci) {
      .ho.b(new .fg());
   }
}
