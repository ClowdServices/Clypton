package client.astralux;

public final class Main implements net.fabricmc.api.ClientModInitializer {
   public static Astralux astralux;

   public void onInitializeClient() {
      astralux = new Astralux();
   }

   public static Astralux getAstralux() {
      return astralux;
   }
}
