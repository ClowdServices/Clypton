package client.astralux.mixin;

import net.minecraft.class_2604;
import net.minecraft.class_2672;
import net.minecraft.class_2678;
import net.minecraft.class_634;
import net.minecraft.class_638;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({class_634.class})
public abstract class ClientPlayNetworkHandlerMixin {
   @Shadow
   private class_638 field_3699;
   @Unique
   private boolean worldNotNull;

   @Inject(
      method = {"onChunkData"},
      at = {@At("TAIL")}
   )
   private void onChunkData(class_2672 packet, CallbackInfo ci) {
      .ho.b(new .w(packet));
   }

   @Inject(
      method = {"onEntitySpawn"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onEntitySpawn(class_2604 packet, CallbackInfo ci) {
      .es event = new .es(packet);
      .ho.b(event);
      if (event.isCancelled()) {
         ci.cancel();
      }

   }

   @Inject(
      method = {"onGameJoin"},
      at = {@At("HEAD")}
   )
   private void onGameJoinHead(class_2678 packet, CallbackInfo ci) {
      this.worldNotNull = this.field_3699 != null;
   }

   @Inject(
      method = {"onGameJoin"},
      at = {@At("TAIL")}
   )
   private void onGameJoinTail(class_2678 packet, CallbackInfo info) {
      if (this.worldNotNull) {
         .ho.b(.co.get());
      }

      .ho.b(.aa.get());
   }
}
