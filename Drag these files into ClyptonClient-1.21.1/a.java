import net.minecraft.class_310;

public class a extends bf {
   public static final long APPLICATION_ID = 1442651977577140317L;
   public static final String ASSET_NAME = "client_icon";
   private final ff showServer = new ff("Show Server", true);
   private final ff showWorld = new ff("Show World", true);
   private long lastUpdate = 0L;
   private static final long UPDATE_INTERVAL = 5000L;

   public a() {
      super("Discord Presence", "Display your Minecraft activity on Discord (Windows Only)", 0, hk.CLIENT);
      this.addSettings(new ab[]{this.showServer, this.showWorld});
   }

   public void onEnable() {
      if (!this.isWindows()) {
         this.setEnabled(false);
      } else {
         try {
            class_310 mc = class_310.method_1551();
            gl.getInstance().initialize(mc.field_1697);
         } catch (UnsatisfiedLinkError | NoClassDefFoundError var2) {
            System.err.println("[Discord Presence] Failed to load Discord SDK: " + var2.getMessage());
            this.setEnabled(false);
         }

      }
   }

   public void onDisable() {
      gl.getInstance().shutdown();
   }

   @cp
   public void onGameJoined(aa event) {
      this.updatePresenceWithGameState();
   }

   @cp
   public void onGameLeft(co event) {
      gl.getInstance().update("In Main Menu", "Idle");
   }

   @cp
   public void onTick(fz event) {
      long now = System.currentTimeMillis();
      if (now - this.lastUpdate > 5000L) {
         this.updatePresenceWithGameState();
         this.lastUpdate = now;
      }

   }

   private void updatePresenceWithGameState() {
      class_310 mc = class_310.method_1551();
      if (mc != null && mc.field_1724 != null) {
         String details = "Playing Minecraft";
         String state = "In Game";
         if (this.showServer.getValue() && mc.method_1558() != null) {
            details = mc.method_1558().field_3761;
         }

         if (this.showWorld.getValue() && mc.field_1724 != null) {
            String worldName = mc.field_1687 != null ? mc.field_1687.method_27983().method_29177().method_12832() : "Unknown";
            state = "World: " + worldName;
         }

         this.updatePresence(details, state);
      }
   }

   public void updatePresence(String details, String state) {
      gl.getInstance().update(details, state, "Join our Discord!", "https://discord.gg/7TJwrKWucM");
   }

   private boolean isWindows() {
      return System.getProperty("os.name").toLowerCase().contains("win");
   }
}
