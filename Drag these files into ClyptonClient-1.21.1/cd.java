import java.util.Base64;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_243;
import net.minecraft.class_2561;
import net.minecraft.class_2680;
import net.minecraft.class_2818;
import net.minecraft.class_3417;
import net.minecraft.class_370;
import net.minecraft.class_2902.class_2903;
import net.minecraft.class_370.class_9037;

public final class cd extends bf {
   private final gn scanRange = new gn(db.of(d661("p5aXmdirm5WbmA==")), 16.0D, 128.0D, 64.0D, 16.0D);
   private final gn scanDelay = new gn(db.of(d661("p5aXmdi9n5edhN7XbXIr")), 100.0D, 2000.0D, 500.0D, 100.0D);
   private final Set<class_2338> foundSpawners = ConcurrentHashMap.newKeySet();
   private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor((r) -> {
      Thread t = new Thread(r, d661("p4WXgJaciLWTiZeZaWRwLldmZ2lmbHg="));
      t.setDaemon(true);
      return t;
   });
   private ScheduledFuture<?> scanTask;
   private long lastNotificationTime = 0L;
   private static final long NOTIFICATION_COOLDOWN = 3000L;

   public cd() {
      super(db.of(d661("p4WXgJaciLWTiZeZaWRw")), db.of(d661("upqCnp6Qn4jcipaabiFxc2VyaGJ6eipqfmguaX9kfHc=")), -1, hk.MISC);
      this.addSettings(new ab[]{this.scanRange, this.scanDelay});
   }

   public void onEnable() {
      super.onEnable();
      this.foundSpawners.clear();
      if (this.scanTask == null || this.scanTask.isCancelled()) {
         long delay = this.scanDelay.getLongValue();
         this.scanTask = this.executor.scheduleAtFixedRate(this::scanForSpawners, 0L, delay, TimeUnit.MILLISECONDS);
      }

   }

   public void onDisable() {
      super.onDisable();
      if (this.scanTask != null) {
         this.scanTask.cancel(false);
         this.scanTask = null;
      }

      this.foundSpawners.clear();
   }

   private void scanForSpawners() {
      if (mc != null && mc.field_1724 != null && mc.field_1687 != null) {
         try {
            class_243 playerPos = mc.field_1724.method_19538();
            int range = this.scanRange.getIntValue();
            double rangeSq = (double)(range * range);
            double cleanupRangeSq = (double)((range + (243 ^ 179)) * (range + (68 ^ 4)));
            this.foundSpawners.removeIf((posx) -> {
               return posx.method_40081(playerPos.field_1352, playerPos.field_1351, playerPos.field_1350) > cleanupRangeSq;
            });
            Iterator var7 = ft.getLoadedChunks().toList().iterator();

            while(true) {
               class_2818 chunk;
               int chunkX;
               int chunkZ;
               do {
                  do {
                     if (!var7.hasNext()) {
                        return;
                     }

                     chunk = (class_2818)var7.next();
                     chunkX = chunk.method_12004().field_9181 << 4;
                     chunkZ = chunk.method_12004().field_9180 << 4;
                  } while(Math.abs((double)chunkX - playerPos.field_1352) > (double)range);
               } while(Math.abs((double)chunkZ - playerPos.field_1350) > (double)range);

               for(int x = 0; x < 16; ++x) {
                  for(int z = 0; z < 16; ++z) {
                     int highestY = chunk.method_12032(class_2903.field_13202).method_12603(x, z);

                     for(int y = mc.field_1687.method_31607(); y <= Math.min(highestY + 10, mc.field_1687.method_31600()); ++y) {
                        class_2338 pos = new class_2338(chunkX + x, y, chunkZ + z);
                        if (!this.foundSpawners.contains(pos) && !(pos.method_40081(playerPos.field_1352, playerPos.field_1351, playerPos.field_1350) > rangeSq)) {
                           class_2680 state = chunk.method_8320(pos);
                           if (state.method_26204() == class_2246.field_10260) {
                              this.foundSpawners.add(pos);
                              this.notifySpawnerFound(pos);
                           }
                        }
                     }
                  }
               }
            }
         } catch (Exception var17) {
         }
      }
   }

   private void notifySpawnerFound(class_2338 pos) {
      long currentTime = System.currentTimeMillis();
      if (currentTime - this.lastNotificationTime >= 3000L) {
         this.lastNotificationTime = currentTime;
         if (mc != null) {
            mc.execute(() -> {
               try {
                  if (mc.field_1724 != null) {
                     mc.field_1724.method_5783(class_3417.field_15195, 1.0F, 1.0F);
                  }

                  if (mc.method_1566() != null) {
                     int var10000 = pos.method_10263();
                     String coords = var10000 + " " + pos.method_10264() + " " + pos.method_10260();
                     class_370.method_1990(mc.method_1566(), class_9037.field_47583, class_2561.method_43470("§6§lSpawner Found!"), class_2561.method_43470("§7At: §f" + coords));
                  }
               } catch (Exception var2) {
               }

            });
         }

      }
   }

   // $FF: synthetic method
   private static String d661(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 6132 + var3 & 255);
      }

      return new String(var2);
   }
}
