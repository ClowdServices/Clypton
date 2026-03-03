import de.jcm.discordgamesdk.Core;
import de.jcm.discordgamesdk.CreateParams;
import de.jcm.discordgamesdk.activity.Activity;
import de.jcm.discordgamesdk.activity.ActivityType;
import java.io.File;
import java.time.Instant;

public class gl {
   private static gl instance;
   private Core core;
   private Activity activity;
   private boolean initialized;
   private Thread callbackThread;

   private gl() {
   }

   public static gl getInstance() {
      if (instance == null) {
         instance = new gl();
      }

      return instance;
   }

   public void initialize() {
      this.initialize((File)null);
   }

   public void initialize(File minecraftDir) {
      if (!this.initialized) {
         try {
            String os = System.getProperty("os.name").toLowerCase();
            if (!os.contains("win")) {
               System.err.println("[Discord Presence] Only Windows is supported");
               return;
            }

            if (minecraftDir == null) {
               new File(System.getProperty("user.dir"));
            }

            File libDir = new File(System.getProperty("java.io.tmpdir"), "discord_game_sdk");
            if (!libDir.exists()) {
               libDir.mkdirs();
            }

            System.out.println("[Discord Presence] Using directory: " + libDir.getAbsolutePath());
            Core.init(libDir);
            CreateParams params = new CreateParams();
            params.setClientID(1442651977577140317L);
            params.setFlags(CreateParams.getDefaultFlags());
            this.core = new Core(params);
            params.close();
            this.activity = new Activity();
            this.activity.setType(ActivityType.PLAYING);
            this.activity.setDetails("Clypton Client");
            this.activity.setState("In Main Menu");
            this.activity.timestamps().setStart(Instant.now());
            this.activity.assets().setLargeImage("client_icon");
            this.activity.assets().setLargeText("Clypton Client");
            this.core.activityManager().updateActivity(this.activity);
            this.initialized = true;
            this.startCallbackThread();
            System.out.println("[Discord Presence] Successfully initialized!");
         } catch (UnsatisfiedLinkError var5) {
            System.err.println("[Discord Presence] Failed to load native library");
            System.err.println("[Discord Presence] Error: " + var5.getMessage());
            System.err.println("[Discord Presence] Try installing: https://aka.ms/vs/17/release/vc_redist.x64.exe");
            this.initialized = false;
         } catch (Exception var6) {
            System.err.println("[Discord Presence] Initialization failed: " + var6.getMessage());
            var6.printStackTrace();
            this.initialized = false;
         }

      }
   }

   private void startCallbackThread() {
      this.callbackThread = new Thread(() -> {
         while(true) {
            if (this.initialized && !Thread.currentThread().isInterrupted()) {
               try {
                  if (this.core != null) {
                     this.core.runCallbacks();
                  }

                  Thread.sleep(16L);
                  continue;
               } catch (InterruptedException var2) {
               } catch (Exception var3) {
                  System.err.println("[Discord Presence] Callback error: " + var3.getMessage());
                  continue;
               }
            }

            return;
         }
      }, "Clypton-Discord-RPC");
      this.callbackThread.setDaemon(true);
      this.callbackThread.start();
   }

   public void update(String details, String state, ActivityType type) {
      if (this.initialized && this.core != null && this.activity != null) {
         try {
            this.activity.setDetails(details);
            this.activity.setState(state);
            this.activity.setType(type);
            this.activity.party().setID("");
            this.activity.secrets().setJoinSecret("");
            this.core.activityManager().updateActivity(this.activity);
         } catch (Exception var5) {
            System.err.println("[Discord Presence] Update failed: " + var5.getMessage());
         }

      }
   }

   public void update(String details, String state, String buttonLabel, String buttonUrl) {
      if (this.initialized && this.core != null && this.activity != null) {
         try {
            this.activity.setDetails(details);
            this.activity.setState(state);
            this.activity.setType(ActivityType.PLAYING);
            this.activity.party().setID("astralux_party");
            this.activity.secrets().setJoinSecret(buttonUrl);
            this.core.activityManager().updateActivity(this.activity);
         } catch (Exception var6) {
            System.err.println("[Discord Presence] Update with button failed: " + var6.getMessage());
         }

      }
   }

   public void update(String details, String state) {
      this.update(details, state, ActivityType.PLAYING);
   }

   public void shutdown() {
      if (this.initialized) {
         this.initialized = false;
         if (this.callbackThread != null && this.callbackThread.isAlive()) {
            this.callbackThread.interrupt();

            try {
               this.callbackThread.join(1000L);
            } catch (InterruptedException var4) {
            }
         }

         if (this.core != null && this.activity != null) {
            try {
               this.activity.close();
            } catch (Exception var3) {
            }
         }

         if (this.core != null) {
            try {
               this.core.close();
            } catch (Exception var2) {
            }

            this.core = null;
         }

         this.activity = null;
         System.out.println("[Discord Presence] Shutdown complete");
      }
   }

   public void tick() {
   }

   public boolean isInitialized() {
      return this.initialized;
   }
}
