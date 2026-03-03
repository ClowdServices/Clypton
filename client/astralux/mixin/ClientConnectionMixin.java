package client.astralux.mixin;

import net.minecraft.class_2535;
import net.minecraft.class_2547;
import net.minecraft.class_2596;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({class_2535.class})
public class ClientConnectionMixin {
   @Inject(
      method = {"handlePacket"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private static void onPacketReceive(class_2596<?> packet, class_2547 listener, CallbackInfo ci) {
      .ak event = new .ak(packet);
      .ho.b(event);
      if (event.isCancelled()) {
         ci.cancel();
      }

   }

   @Inject(
      method = {"send(Lnet/minecraft/network/packet/Packet;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onPacketSend(class_2596<?> packet, CallbackInfo ci) {
      .ib event = new .ib(packet);
      .ho.b(event);
      if (event.isCancelled()) {
         ci.cancel();
      }

   }
}
