package client.astralux;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class ClientModInitializer implements net.fabricmc.api.ClientModInitializer {
   public void onInitializeClient() {
      .gl.getInstance().initialize();
      ClientTickEvents.END_CLIENT_TICK.register((client) -> {
         .gl.getInstance().tick();
      });
   }
}
