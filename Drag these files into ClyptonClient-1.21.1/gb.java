import java.util.Base64;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import net.minecraft.class_2561;
import net.minecraft.class_3417;
import net.minecraft.class_370;
import net.minecraft.class_742;
import net.minecraft.class_370.class_9037;

public final class gb extends bf {
   private final gn range = new gn(db.of(d35("Lx8R5+Q=")), 16.0D, 256.0D, 128.0D, 16.0D);
   private final gn scanDelay = new gn(db.of(d35("Lh0e7qHG5ujk/6eg5Pmi")), 100.0D, 2000.0D, 500.0D, 100.0D);
   private final ff playSound = new ff(db.of(d35("LRIe+aHR7PHr4g==")), true);
   private final ff showToast = new ff(db.of(d35("LhYQ96HW7OX28g==")), true);
   private final ff ignoreTeammates = new ff(db.of(d35("NBkR7/Pno9Dg5+rl6P7u/w==")), false);
   private final ff onlyNewPlayers = new ff(db.of(d35("MhAT+aHM5vOl1uvp8O/5/w==")), true);
   private final Set<UUID> knownPlayers = ConcurrentHashMap.newKeySet();
   private final Set<UUID> notifiedPlayers = ConcurrentHashMap.newKeySet();
   private final Map<UUID, Long> playerLastSeen = new ConcurrentHashMap();
   private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor((r) -> {
      Thread t = new Thread(r, d35("LRIe+eTwzevx7+Hh7Pim3+7v4f704A=="));
      t.setDaemon(true);
      return t;
   });
   private ScheduledFuture<?> scanTask;
   private long lastNotificationTime = 0L;
   private static final long NOTIFICATION_COOLDOWN = 2000L;
   private static final long PLAYER_TIMEOUT = 10000L;

   public gb() {
      super(db.of(d35("LRIe+eTwzevx7+Hh7Pg=")), db.of(d35("MxEL6efr5vel8e/t56r74Oz36uLisvLm8Lb5/fjo+eU=")), -1, hk.MISC);
      this.addSettings(new ab[]{this.range, this.scanDelay, this.playSound, this.showToast, this.ignoreTeammates, this.onlyNewPlayers});
   }

   public void onEnable() {
      super.onEnable();
      this.knownPlayers.clear();
      this.notifiedPlayers.clear();
      this.playerLastSeen.clear();
      if (this.scanTask == null || this.scanTask.isCancelled()) {
         long delay = this.scanDelay.getLongValue();
         this.scanTask = this.executor.scheduleAtFixedRate(this::scanForPlayers, 0L, delay, TimeUnit.MILLISECONDS);
      }

   }

   public void onDisable() {
      super.onDisable();
      if (this.scanTask != null) {
         this.scanTask.cancel(false);
         this.scanTask = null;
      }

      this.knownPlayers.clear();
      this.notifiedPlayers.clear();
      this.playerLastSeen.clear();
   }

   private void scanForPlayers() {
      if (mc != null && mc.field_1724 != null && mc.field_1687 != null) {
         try {
            long currentTime = System.currentTimeMillis();
            double rangeSq = this.range.getValue() * this.range.getValue();
            Set<UUID> currentPlayers = new HashSet();
            Iterator var6 = mc.field_1687.method_18456().iterator();

            while(true) {
               class_742 player;
               UUID uuid;
               double distSq;
               do {
                  do {
                     do {
                        if (!var6.hasNext()) {
                           this.playerLastSeen.entrySet().removeIf((entry) -> {
                              if (currentTime - (Long)entry.getValue() > 10000L) {
                                 this.notifiedPlayers.remove(entry.getKey());
                                 return true;
                              } else {
                                 return false;
                              }
                           });
                           this.notifiedPlayers.removeIf((uuidx) -> {
                              if (!currentPlayers.contains(uuidx)) {
                                 return true;
                              } else {
                                 class_742 player = this.getPlayerByUUID(uuidx);
                                 if (player == null) {
                                    return true;
                                 } else {
                                    return mc.field_1724.method_5858(player) > rangeSq;
                                 }
                              }
                           });
                           return;
                        }

                        player = (class_742)var6.next();
                     } while(player == mc.field_1724);

                     uuid = player.method_5667();
                     currentPlayers.add(uuid);
                     this.playerLastSeen.put(uuid, currentTime);
                     distSq = mc.field_1724.method_5858(player);
                  } while(distSq > rangeSq);
               } while(this.ignoreTeammates.getValue() && this.isTeammate(player));

               boolean isNewPlayer = !this.knownPlayers.contains(uuid);
               boolean alreadyNotified = this.notifiedPlayers.contains(uuid);
               if (this.onlyNewPlayers.getValue()) {
                  if (isNewPlayer && !alreadyNotified) {
                     this.notifyPlayerFound(player, distSq);
                     this.notifiedPlayers.add(uuid);
                  }
               } else if (!alreadyNotified) {
                  this.notifyPlayerFound(player, distSq);
                  this.notifiedPlayers.add(uuid);
               }

               this.knownPlayers.add(uuid);
            }
         } catch (Exception var13) {
         }
      }
   }

   private class_742 getPlayerByUUID(UUID uuid) {
      if (mc != null && mc.field_1687 != null) {
         Iterator var2 = mc.field_1687.method_18456().iterator();

         class_742 player;
         do {
            if (!var2.hasNext()) {
               return null;
            }

            player = (class_742)var2.next();
         } while(!player.method_5667().equals(uuid));

         return player;
      } else {
         return null;
      }
   }

   private boolean isTeammate(class_742 player) {
      return mc.field_1724 == null ? false : mc.field_1724.method_5722(player);
   }

   private void notifyPlayerFound(class_742 player, double distSq) {
      long currentTime = System.currentTimeMillis();
      if (currentTime - this.lastNotificationTime >= 2000L) {
         this.lastNotificationTime = currentTime;
         int distance = (int)Math.sqrt(distSq);
         String playerName = player.method_5477().getString();
         if (mc != null) {
            mc.execute(() -> {
               try {
                  if (this.playSound.getValue() && mc.field_1724 != null) {
                     mc.field_1724.method_5783(class_3417.field_14627, 1.0F, 1.0F);
                  }

                  if (this.showToast.getValue() && mc.method_1566() != null) {
                     class_370.method_1990(mc.method_1566(), class_9037.field_47583, class_2561.method_43470("§c§lPlayer Detected!"), class_2561.method_43470("§7" + playerName + " §f(" + distance + "m)"));
                  }
               } catch (Exception var4) {
               }

            });
         }

      }
   }

   // $FF: synthetic method
   private static String d35(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 6525 + var3 & 255);
      }

      return new String(var2);
   }
}
