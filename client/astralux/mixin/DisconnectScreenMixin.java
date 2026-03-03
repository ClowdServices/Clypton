package client.astralux.mixin;

import java.lang.reflect.Method;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_4185;
import net.minecraft.class_419;
import net.minecraft.class_437;
import net.minecraft.class_639;
import net.minecraft.class_642;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({class_419.class})
public class DisconnectScreenMixin extends class_437 {
   @Shadow
   private class_437 field_2456;
   private int reconnectTimer = 0;
   private class_4185 reconnectButton;
   private class_642 lastServer;

   protected DisconnectScreenMixin(class_2561 title) {
      super(title);
   }

   @Inject(
      method = {"init"},
      at = {@At("RETURN")}
   )
   private void onInit(CallbackInfo ci) {
      .bd module = .bd.getInstance();
      if (module != null && module.isEnabled()) {
         this.reconnectTimer = module.getDelayTicks();
         if (this.field_22787 != null && this.field_22787.method_1542()) {
            return;
         }

         if (this.field_22787 != null) {
            this.lastServer = this.field_22787.method_1558();
         }

         this.reconnectButton = class_4185.method_46430(class_2561.method_43470("Reconnecting in " + this.reconnectTimer / 20 + "s"), (button) -> {
            this.reconnect();
         }).method_46434(this.field_22789 / 2 - 100, this.field_22790 / 2 + 50, 200, 20).method_46431();
         this.method_37063(this.reconnectButton);
      }

   }

   @Inject(
      method = {"tick"},
      at = {@At("HEAD")}
   )
   private void onTick(CallbackInfo ci) {
      .bd module = .bd.getInstance();
      if (module != null && module.isEnabled() && this.reconnectTimer > 0) {
         --this.reconnectTimer;
         if (this.reconnectButton != null) {
            this.reconnectButton.method_25355(class_2561.method_43470("Reconnecting in " + this.reconnectTimer / 20 + "s"));
         }

         if (this.reconnectTimer <= 0) {
            this.reconnect();
         }
      }

   }

   private void reconnect() {
      if (this.field_22787 != null && this.lastServer != null) {
         try {
            Class<?> connectScreenClass = Class.forName("net.minecraft.client.gui.screen.multiplayer.ConnectScreen");
            Method connectMethod = connectScreenClass.getMethod("connect", class_437.class, class_310.class, class_639.class, class_642.class);
            class_639 serverAddress = class_639.method_2950(this.lastServer.field_3761);
            connectMethod.invoke((Object)null, this.field_2456, this.field_22787, serverAddress, this.lastServer);
         } catch (Exception var4) {
            System.err.println("[AutoReconnect] Failed to reconnect: " + var4.getMessage());
            if (this.field_2456 != null) {
               this.field_22787.method_1507(this.field_2456);
            }
         }

      } else {
         if (this.field_2456 != null) {
            this.field_22787.method_1507(this.field_2456);
         }

      }
   }
}
