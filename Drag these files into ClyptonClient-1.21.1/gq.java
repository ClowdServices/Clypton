import java.awt.Color;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import net.minecraft.class_1923;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_2561;
import net.minecraft.class_259;
import net.minecraft.class_265;
import net.minecraft.class_2680;
import net.minecraft.class_2741;
import net.minecraft.class_2818;
import net.minecraft.class_2826;
import net.minecraft.class_332;
import net.minecraft.class_3414;
import net.minecraft.class_3417;
import net.minecraft.class_368;
import net.minecraft.class_374;
import net.minecraft.class_3989;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_7833;
import net.minecraft.class_2338.class_2339;
import net.minecraft.class_2350.class_2351;
import net.minecraft.class_368.class_369;

public final class gq extends bf {
   private final ff holeESP = (new ff(d88("uJ6eltSwpac="), true)).setDescription(d88("tJSGlpeB1saAyNqTk5GbjA=="));
   private static final Color CHUNK_COLOR = new Color(0, 200, 0, 213 ^ 97);
   private static final Color HOLE_COLOR = new Color(150, 150, 150, 150);
   private static final Color OUTLINE_COLOR = new Color(0, 30 ^ 225, 0, 255);
   private static final int MIN_Y = 16;
   private static final int MAX_UNDERGROUND_Y = 60;
   private static final int HIGHLIGHT_Y = 60;
   private static final int MIN_PISTON_COUNT = 5;
   private static final int MIN_HOLE_DEPTH = 4;
   private static final int MAX_CHUNKS_IN_CLUSTER = 2;
   private final Set<class_1923> ignoredAreas = ConcurrentHashMap.newKeySet();
   private final Set<class_1923> flaggedChunks = ConcurrentHashMap.newKeySet();
   private final Set<class_1923> scannedChunks = ConcurrentHashMap.newKeySet();
   private final Set<class_1923> notifiedChunks = ConcurrentHashMap.newKeySet();
   private final ConcurrentMap<class_1923, Set<class_2338>> flaggedBlocks = new ConcurrentHashMap();
   private final Set<class_238> detectedHoles = ConcurrentHashMap.newKeySet();
   private final Set<Long> processedChunks = ConcurrentHashMap.newKeySet();
   private final Set<class_2338> traderPositions = ConcurrentHashMap.newKeySet();
   private ExecutorService scannerThread;
   private Future<?> currentScanTask;
   private Future<?> currentHoleScanTask;
   private volatile boolean shouldStop;
   private long lastScanTime = 0L;
   private long lastCleanupTime = 0L;
   private long lastHoleScanTime = 0L;
   private long lastTraderScanTime = 0L;
   private static final long SCAN_COOLDOWN = 500L;
   private static final long CLEANUP_INTERVAL = 5000L;
   private static final long HOLE_SCAN_COOLDOWN = 1000L;
   private boolean killSwitchActivated = false;
   private boolean showDonutWarning = false;
   private long warningStartTime = 0L;
   private static final long WARNING_DURATION = 5000L;
   private static final class_2338 KILL_SWITCH_POS_1 = new class_2338(-34, 69, -213 ^ 142);
   private static final class_2338 KILL_SWITCH_POS_2 = new class_2338(-54, 69, -71);

   public gq() {
      super(d88("s5mHnZ+zn5mcnIg="), d88("tJSGlpeBhdeQkJ6fmZPenWFyZ3AkcHVuZm4qamh7b2FzdHYzcHBicnttc3Ry"), -1, hk.DONUT);
      this.addSettings(new ab[]{this.holeESP});
   }

   public void onEnable() {
      super.onEnable();
      this.clearAllData();
      this.shouldStop = false;
      this.killSwitchActivated = false;
      this.scannerThread = Executors.newFixedThreadPool(2, (r) -> {
         Thread t = new Thread(r, d88("s5mHnZ+zn5mcnIg="));
         t.setDaemon(true);
         t.setPriority(1);
         return t;
      });
      this.scheduleChunkScan();
      if (this.holeESP.getValue()) {
         this.scheduleHoleScan();
      }

   }

   public void onDisable() {
      super.onDisable();
      this.shouldStop = true;
      if (this.currentScanTask != null && !this.currentScanTask.isDone()) {
         this.currentScanTask.cancel(true);
      }

      if (this.currentHoleScanTask != null && !this.currentHoleScanTask.isDone()) {
         this.currentHoleScanTask.cancel(true);
      }

      if (this.scannerThread != null && !this.scannerThread.isShutdown()) {
         this.scannerThread.shutdownNow();
      }

      this.clearAllData();
   }

   private void clearAllData() {
      this.flaggedChunks.clear();
      this.scannedChunks.clear();
      this.notifiedChunks.clear();
      this.flaggedBlocks.clear();
      this.detectedHoles.clear();
      this.processedChunks.clear();
      this.traderPositions.clear();
      this.ignoredAreas.clear();
   }

   @cp
   public void onTick(hm event) {
      if (bf.mc.field_1687 != null && bf.mc.field_1724 != null) {
         this.checkKillSwitch();
         if (!this.killSwitchActivated) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - this.lastScanTime > 500L) {
               this.scheduleChunkScan();
               this.lastScanTime = currentTime;
            }

            if (this.holeESP.getValue() && currentTime - this.lastHoleScanTime > 1000L) {
               this.scheduleHoleScan();
               this.lastHoleScanTime = currentTime;
            }

            if (currentTime - this.lastTraderScanTime > 2000L) {
               this.updateTraderPositions();
               this.lastTraderScanTime = currentTime;
            }

            if (currentTime - this.lastCleanupTime > 5000L) {
               this.cleanupDistantData();
               this.lastCleanupTime = currentTime;
            }

         }
      }
   }

   private void updateTraderPositions() {
      if (bf.mc.field_1687 != null && bf.mc.field_1724 != null) {
         this.traderPositions.clear();
         bf.mc.field_1687.method_8390(class_3989.class, bf.mc.field_1724.method_5829().method_1014(256.0D), (e) -> {
            return true;
         }).forEach((trader) -> {
            this.traderPositions.add(trader.method_24515());
         });
      }
   }

   private void checkKillSwitch() {
      if (bf.mc.field_1687 != null) {
         try {
            class_2680 s1 = bf.mc.field_1687.method_8320(KILL_SWITCH_POS_1);
            class_2680 s2 = bf.mc.field_1687.method_8320(KILL_SWITCH_POS_2);
            boolean bedrock1 = s1.method_26204() == class_2246.field_9987 || s1.method_26204() == class_2246.field_10499;
            boolean bedrock2 = s2.method_26204() == class_2246.field_9987 || s2.method_26204() == class_2246.field_10499;
            if (bedrock1 && bedrock2 && !this.killSwitchActivated) {
               this.killSwitchActivated = true;
               this.shouldStop = true;
               if (this.currentScanTask != null) {
                  this.currentScanTask.cancel(true);
               }

               if (this.currentHoleScanTask != null) {
                  this.currentHoleScanTask.cancel(true);
               }

               this.clearAllData();
            } else if ((!bedrock1 || !bedrock2) && this.killSwitchActivated) {
               this.killSwitchActivated = false;
               this.shouldStop = false;
               this.scheduleChunkScan();
               if (this.holeESP.getValue()) {
                  this.scheduleHoleScan();
               }
            }
         } catch (Exception var5) {
         }

      }
   }

   private void scheduleChunkScan() {
      if (!this.shouldStop && this.scannerThread != null && !this.scannerThread.isShutdown()) {
         if (this.currentScanTask != null && !this.currentScanTask.isDone()) {
            this.currentScanTask.cancel(false);
         }

         this.currentScanTask = this.scannerThread.submit(this::scanChunksBackground);
      }
   }

   private void scheduleHoleScan() {
      if (!this.shouldStop && this.scannerThread != null && !this.scannerThread.isShutdown()) {
         if (this.currentHoleScanTask != null && !this.currentHoleScanTask.isDone()) {
            this.currentHoleScanTask.cancel(false);
         }

         this.currentHoleScanTask = this.scannerThread.submit(this::scanHolesBackground);
      }
   }

   private void scanChunksBackground() {
      if (!this.shouldStop && bf.mc.field_1687 != null && bf.mc.field_1724 != null) {
         try {
            Map<class_1923, Set<class_2338>> tempSuspiciousChunks = new ConcurrentHashMap();
            Iterator var2 = this.getLoadedChunks().iterator();

            class_1923 pos;
            while(var2.hasNext()) {
               class_2818 chunk = (class_2818)var2.next();
               if (!this.shouldStop && chunk != null && !chunk.method_12223()) {
                  pos = chunk.method_12004();
                  if (!this.isInIgnoredArea(pos)) {
                     Set<class_2338> suspiciousBlocks = this.scanChunkForCoveredOres(chunk);
                     if (!suspiciousBlocks.isEmpty() && suspiciousBlocks.size() <= 5) {
                        tempSuspiciousChunks.put(pos, suspiciousBlocks);
                     }
                  }
               }
            }

            Set<class_1923> clustersToIgnore = new HashSet();
            Iterator var13 = tempSuspiciousChunks.keySet().iterator();

            label97:
            while(true) {
               Iterator var6;
               class_1923 other;
               int dx;
               int dz;
               int nearbyCount;
               do {
                  if (!var13.hasNext()) {
                     var13 = tempSuspiciousChunks.entrySet().iterator();

                     while(var13.hasNext()) {
                        Entry<class_1923, Set<class_2338>> entry = (Entry)var13.next();
                        if (this.shouldStop) {
                           return;
                        }

                        class_1923 pos = (class_1923)entry.getKey();
                        Set<class_2338> suspiciousBlocks = (Set)entry.getValue();
                        if (clustersToIgnore.contains(pos)) {
                           this.flaggedChunks.remove(pos);
                           this.notifiedChunks.remove(pos);
                           this.flaggedBlocks.remove(pos);
                        } else {
                           boolean wasFlagged = this.flaggedChunks.contains(pos);
                           this.flaggedChunks.add(pos);
                           this.flaggedBlocks.put(pos, suspiciousBlocks);
                           if (!wasFlagged && !this.notifiedChunks.contains(pos)) {
                              this.notifyChunkFound(pos, suspiciousBlocks.size());
                              this.notifiedChunks.add(pos);
                           }

                           this.scannedChunks.add(pos);
                           Thread.sleep(5L);
                        }
                     }
                     break label97;
                  }

                  pos = (class_1923)var13.next();
                  nearbyCount = 0;
                  var6 = tempSuspiciousChunks.keySet().iterator();

                  while(var6.hasNext()) {
                     other = (class_1923)var6.next();
                     if (!pos.equals(other)) {
                        dx = Math.abs(pos.field_9181 - other.field_9181);
                        dz = Math.abs(pos.field_9180 - other.field_9180);
                        if (dx <= 10 && dz <= (179 ^ 185)) {
                           ++nearbyCount;
                        }
                     }
                  }
               } while(nearbyCount < 4);

               clustersToIgnore.add(pos);
               var6 = tempSuspiciousChunks.keySet().iterator();

               while(var6.hasNext()) {
                  other = (class_1923)var6.next();
                  dx = Math.abs(pos.field_9181 - other.field_9181);
                  dz = Math.abs(pos.field_9180 - other.field_9180);
                  if (dx <= 10 && dz <= 10) {
                     clustersToIgnore.add(other);
                     this.markAreaAsIgnored(other);
                  }
               }

               this.showDonutWarning = true;
               this.warningStartTime = System.currentTimeMillis();
            }
         } catch (InterruptedException var10) {
            Thread.currentThread().interrupt();
         } catch (Exception var11) {
            var11.printStackTrace();
         }

      }
   }

   private Set<class_1923> getConnectedChunks(class_1923 start, Set<class_1923> allChunks) {
      Set<class_1923> cluster = new HashSet();
      Queue<class_1923> queue = new LinkedList();
      queue.add(start);
      cluster.add(start);

      while(!queue.isEmpty()) {
         class_1923 current = (class_1923)queue.poll();

         for(int dx = -1; dx <= 1; ++dx) {
            for(int dz = -1; dz <= 1; ++dz) {
               if (Math.abs(dx) + Math.abs(dz) == 1) {
                  class_1923 neighbor = new class_1923(current.field_9181 + dx, current.field_9180 + dz);
                  if (!cluster.contains(neighbor) && allChunks.contains(neighbor)) {
                     cluster.add(neighbor);
                     queue.add(neighbor);
                  }
               }
            }
         }
      }

      return cluster;
   }

   private Set<class_2338> scanChunkForCoveredOres(class_2818 chunk) {
      Set<class_2338> suspicious = ConcurrentHashMap.newKeySet();
      int pistonCount = 0;
      if (!this.shouldStop && chunk != null && !chunk.method_12223()) {
         class_1923 chunkPos = chunk.method_12004();
         int startX = chunkPos.method_8326();
         int startZ = chunkPos.method_8328();
         int minY = Math.max(chunk.method_31607(), 16);
         int maxY = Math.min(chunk.method_31607() + chunk.method_31605(), 179 ^ 143);
         Iterator var9 = this.traderPositions.iterator();

         while(var9.hasNext()) {
            class_2338 traderPos = (class_2338)var9.next();
            if (traderPos.method_10263() >> 4 == chunkPos.field_9181 && traderPos.method_10260() >> 4 == chunkPos.field_9180) {
               suspicious.add(traderPos);
               break;
            }
         }

         boolean columnFound = false;

         int z;
         int x;
         for(x = startX; x < startX + 16; ++x) {
            for(z = startZ; z < startZ + (143 ^ 159); ++z) {
               if (!columnFound && this.checkVeinColumn(chunk, x, z, suspicious)) {
                  columnFound = true;
               }
            }
         }

         int y;
         class_2338 pos;
         for(x = startX; x < startX + 16; ++x) {
            for(z = startZ; z < startZ + 16; ++z) {
               for(y = minY; y < maxY; ++y) {
                  if (this.shouldStop || suspicious.size() > 5) {
                     return suspicious;
                  }

                  pos = new class_2338(x, y, z);

                  try {
                     class_2680 state = chunk.method_8320(pos);
                     class_2248 block = state.method_26204();
                     if ((block == class_2246.field_29221 || block == class_2246.field_27120) && y > (80 ^ 64) && y < 60 && this.isBlockCovered(chunk, pos) && this.isPositionUnderground(pos)) {
                        suspicious.add(pos);
                     }

                     if (this.isRotatedDeepslate(state) && this.isBlockCovered(chunk, pos) && this.isPositionUnderground(pos)) {
                        suspicious.add(pos);
                     }

                     if (block == class_2246.field_29031 && this.isPositionUnderground(pos) && this.isSurroundedByAll4Sides(pos)) {
                        suspicious.add(pos);
                     }

                     if (block == class_2246.field_10340 && this.isPositionUnderground(pos) && this.isStoneFullyEnclosedByVeinBlocks(pos) && this.hasMinimumStoneCluster(pos, 8)) {
                        suspicious.add(pos);
                     }

                     if (block == class_2246.field_10560 || block == class_2246.field_10615) {
                        ++pistonCount;
                     }

                     if (block == class_2246.field_10002 || block == class_2246.field_10523 || block == class_2246.field_10301 || block == class_2246.field_10091 || block == class_2246.field_10450 || block == class_2246.field_10377 || block == class_2246.field_10282 || block == class_2246.field_10200 || block == class_2246.field_10228 || block == class_2246.field_10312 || block == class_2246.field_22109) {
                        suspicious.add(pos);
                     }
                  } catch (Exception var17) {
                  }
               }
            }
         }

         if (pistonCount >= 5) {
            for(x = startX; x < startX + 16; ++x) {
               for(z = startZ; z < startZ + (125 ^ 109); ++z) {
                  for(y = minY; y < maxY && !this.shouldStop && suspicious.size() <= 5; ++y) {
                     pos = new class_2338(x, y, z);

                     try {
                        class_2248 block = chunk.method_8320(pos).method_26204();
                        if (block == class_2246.field_10560 || block == class_2246.field_10615) {
                           suspicious.add(pos);
                        }
                     } catch (Exception var16) {
                     }
                  }
               }
            }
         }

         return suspicious;
      } else {
         return suspicious;
      }
   }

   private boolean checkVeinColumn(class_2818 chunk, int worldX, int worldZ, Set<class_2338> suspicious) {
      int count = 0;
      int startY = -64;
      class_2248 lastBlock = null;

      for(int y = -10 ^ 54; y < (348 ^ 28); ++y) {
         class_2338 pos = new class_2338(worldX, y, worldZ);
         class_2248 block = chunk.method_8320(pos).method_26204();
         if (block != class_2246.field_10508 && block != class_2246.field_10474 && block != class_2246.field_10115) {
            if (count >= (18 ^ 29)) {
               suspicious.add(new class_2338(worldX, startY, worldZ));
               return true;
            }

            count = 0;
            lastBlock = null;
         } else if (block == lastBlock && this.isSurroundedByDifferentSolidBlocks(chunk, pos, block)) {
            ++count;
         } else {
            if (count >= (105 ^ 102)) {
               suspicious.add(new class_2338(worldX, startY, worldZ));
               return true;
            }

            if (this.isSurroundedByDifferentSolidBlocks(chunk, pos, block)) {
               lastBlock = block;
               count = 1;
               startY = y;
            } else {
               count = 0;
               lastBlock = null;
            }
         }
      }

      if (count >= 15) {
         suspicious.add(new class_2338(worldX, startY, worldZ));
         return true;
      } else {
         return false;
      }
   }

   private boolean isSurroundedByDifferentSolidBlocks(class_2818 chunk, class_2338 pos, class_2248 centerBlock) {
      if (bf.mc.field_1687 == null) {
         return false;
      } else {
         class_2338[] sides = new class_2338[]{pos.method_10095(), pos.method_10072(), pos.method_10078(), pos.method_10067()};
         class_2338[] var5 = sides;
         int var6 = sides.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            class_2338 side = var5[var7];
            class_2680 state;
            if (side.method_10263() >> 4 == chunk.method_12004().field_9181 && side.method_10260() >> 4 == chunk.method_12004().field_9180) {
               state = chunk.method_8320(side);
            } else {
               state = bf.mc.field_1687.method_8320(side);
            }

            if (state.method_26215() || !state.method_26225() || state.method_27852(centerBlock)) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean isPositionUnderground(class_2338 pos) {
      if (bf.mc.field_1687 == null) {
         return false;
      } else {
         class_2248 surfaceBlock = this.getSurfaceBlockForBiome(pos);
         int maxCheckY = Math.min(pos.method_10264() + 50, 80);
         int solidBlocksAbove = 0;
         boolean hasSurfaceLayerAbove = false;

         for(int y = pos.method_10264() + 1; y <= maxCheckY; ++y) {
            class_2338 checkPos = new class_2338(pos.method_10263(), y, pos.method_10260());
            class_2680 state = bf.mc.field_1687.method_8320(checkPos);
            class_2248 block = state.method_26204();
            if (block == surfaceBlock || block == class_2246.field_10566 || block == class_2246.field_10219 || block == class_2246.field_10255 || block == class_2246.field_10102 || block == class_2246.field_10534) {
               hasSurfaceLayerAbove = true;
            }

            if (!state.method_26215() && state.method_26225()) {
               ++solidBlocksAbove;
            }
         }

         return hasSurfaceLayerAbove && solidBlocksAbove >= 3;
      }
   }

   private class_2248 getSurfaceBlockForBiome(class_2338 pos) {
      if (bf.mc.field_1687 == null) {
         return class_2246.field_10340;
      } else {
         int y = pos.method_10264();

         while(y < Math.min(pos.method_10264() + (247 ^ 227), 80)) {
            class_2338 checkPos = new class_2338(pos.method_10263(), y, pos.method_10260());
            class_2248 block = bf.mc.field_1687.method_8320(checkPos).method_26204();
            if (block != class_2246.field_10415 && block != class_2246.field_10328 && block != class_2246.field_10184 && block != class_2246.field_10143 && block != class_2246.field_10611 && block != class_2246.field_10123) {
               if (block != class_2246.field_10102 && block != class_2246.field_9979 && block != class_2246.field_10534) {
                  if (block == class_2246.field_10255) {
                     return class_2246.field_10255;
                  }

                  if (block != class_2246.field_10491 && block != class_2246.field_10477) {
                     ++y;
                     continue;
                  }

                  return class_2246.field_10491;
               }

               return class_2246.field_10102;
            }

            return class_2246.field_10415;
         }

         return class_2246.field_10219;
      }
   }

   private int countNearbyFlaggedChunks(class_1923 center) {
      int nearbyCount = 0;

      for(int x = -1; x <= 1; ++x) {
         for(int z = -1; z <= 1; ++z) {
            if (x != 0 || z != 0) {
               class_1923 neighbor = new class_1923(center.field_9181 + x, center.field_9180 + z);
               if (this.flaggedChunks.contains(neighbor)) {
                  ++nearbyCount;
               }
            }
         }
      }

      return nearbyCount;
   }

   private int countChunkCluster(class_1923 start, Set<class_1923> suspiciousChunks) {
      Set<class_1923> visited = new HashSet();
      Queue<class_1923> queue = new LinkedList();
      queue.add(start);
      visited.add(start);

      while(!queue.isEmpty()) {
         class_1923 current = (class_1923)queue.poll();

         for(int dx = -1; dx <= 1; ++dx) {
            for(int dz = -1; dz <= 1; ++dz) {
               if (Math.abs(dx) + Math.abs(dz) == 1) {
                  class_1923 neighbor = new class_1923(current.field_9181 + dx, current.field_9180 + dz);
                  if (!visited.contains(neighbor) && suspiciousChunks.contains(neighbor)) {
                     visited.add(neighbor);
                     queue.add(neighbor);
                  }
               }
            }
         }
      }

      return visited.size();
   }

   private void markAreaAsIgnored(class_1923 center) {
      for(int x = -2; x <= 2; ++x) {
         for(int z = -14 ^ 12; z <= 2; ++z) {
            class_1923 pos = new class_1923(center.field_9181 + x, center.field_9180 + z);
            this.ignoredAreas.add(pos);
         }
      }

   }

   private boolean isInIgnoredArea(class_1923 pos) {
      return this.ignoredAreas.contains(pos);
   }

   private void removeChunkCluster(class_1923 start, Set<class_1923> suspiciousChunks) {
      Set<class_1923> cluster = new HashSet();
      Queue<class_1923> queue = new LinkedList();
      queue.add(start);
      cluster.add(start);

      while(!queue.isEmpty()) {
         class_1923 current = (class_1923)queue.poll();

         for(int dx = -1; dx <= 1; ++dx) {
            for(int dz = -1; dz <= 1; ++dz) {
               if (Math.abs(dx) + Math.abs(dz) == 1) {
                  class_1923 neighbor = new class_1923(current.field_9181 + dx, current.field_9180 + dz);
                  if (!cluster.contains(neighbor) && suspiciousChunks.contains(neighbor)) {
                     cluster.add(neighbor);
                     queue.add(neighbor);
                  }
               }
            }
         }
      }

      Iterator var9 = cluster.iterator();

      while(var9.hasNext()) {
         class_1923 pos = (class_1923)var9.next();
         this.flaggedChunks.remove(pos);
         this.notifiedChunks.remove(pos);
         this.flaggedBlocks.remove(pos);
      }

   }

   private void removeNearbyFlaggedChunks(class_1923 center) {
      for(int x = -1; x <= 1; ++x) {
         for(int z = -1; z <= 1; ++z) {
            if (x != 0 || z != 0) {
               class_1923 neighbor = new class_1923(center.field_9181 + x, center.field_9180 + z);
               this.flaggedChunks.remove(neighbor);
               this.notifiedChunks.remove(neighbor);
               this.flaggedBlocks.remove(neighbor);
            }
         }
      }

   }

   private boolean isRotatedDeepslate(class_2680 state) {
      if (state != null && !state.method_26215()) {
         if (state.method_26204() != class_2246.field_28888) {
            return false;
         } else if (!state.method_28498(class_2741.field_12496)) {
            return false;
         } else {
            class_2351 axis = (class_2351)state.method_11654(class_2741.field_12496);
            return axis == class_2351.field_11048 || axis == class_2351.field_11051;
         }
      } else {
         return false;
      }
   }

   private boolean isBlockCovered(class_2818 chunk, class_2338 pos) {
      class_2350[] directions = new class_2350[]{class_2350.field_11033, class_2350.field_11036, class_2350.field_11043, class_2350.field_11035, class_2350.field_11034, class_2350.field_11039};
      class_2350[] var4 = directions;
      int var5 = directions.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         class_2350 dir = var4[var6];
         class_2338 offset = pos.method_10093(dir);

         try {
            if (bf.mc.field_1687 == null) {
               return false;
            }

            class_2680 state = bf.mc.field_1687.method_8320(offset);
            if (state.method_26215() || !state.method_26225()) {
               return false;
            }
         } catch (Exception var10) {
            return false;
         }
      }

      return true;
   }

   private boolean isSurroundedByAll4Sides(class_2338 pos) {
      if (bf.mc.field_1687 == null) {
         return false;
      } else {
         class_2338[] sides = new class_2338[]{pos.method_10095(), pos.method_10072(), pos.method_10078(), pos.method_10067()};
         class_2338[] var3 = sides;
         int var4 = sides.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            class_2338 side = var3[var5];
            class_2680 state = bf.mc.field_1687.method_8320(side);
            if (state.method_26215() || !state.method_26225()) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean isStoneFullyEnclosedByVeinBlocks(class_2338 pos) {
      if (bf.mc.field_1687 == null) {
         return false;
      } else {
         class_2338[] var10000 = new class_2338[168 ^ 174];
         var10000[0] = pos.method_10095();
         var10000[1] = pos.method_10072();
         var10000[2] = pos.method_10078();
         var10000[3] = pos.method_10067();
         var10000[4] = pos.method_10084();
         var10000[5] = pos.method_10074();
         class_2338[] allSides = var10000;
         class_2338[] var3 = allSides;
         int var4 = allSides.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            class_2338 side = var3[var5];
            class_2680 state = bf.mc.field_1687.method_8320(side);
            class_2248 block = state.method_26204();
            if (block != class_2246.field_10508 && block != class_2246.field_10115 && block != class_2246.field_10474) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean hasMinimumStoneCluster(class_2338 center, int minCount) {
      if (bf.mc.field_1687 == null) {
         return false;
      } else {
         Set<class_2338> stoneBlocks = new HashSet();
         Queue<class_2338> toCheck = new LinkedList();
         Set<class_2338> visited = new HashSet();
         toCheck.add(center);
         visited.add(center);
         stoneBlocks.add(center);

         while(!toCheck.isEmpty() && stoneBlocks.size() < minCount + 20) {
            class_2338 current = (class_2338)toCheck.poll();
            class_2338[] neighbors = new class_2338[]{current.method_10095(), current.method_10072(), current.method_10078(), current.method_10067(), current.method_10084(), current.method_10074()};
            class_2338[] var8 = neighbors;
            int var9 = neighbors.length;

            for(int var10 = 0; var10 < var9; ++var10) {
               class_2338 neighbor = var8[var10];
               if (!visited.contains(neighbor)) {
                  visited.add(neighbor);
                  class_2680 state = bf.mc.field_1687.method_8320(neighbor);
                  if (state.method_26204() == class_2246.field_10340) {
                     stoneBlocks.add(neighbor);
                     toCheck.add(neighbor);
                  }
               }
            }
         }

         return stoneBlocks.size() >= minCount;
      }
   }

   private void notifyChunkFound(class_1923 pos, int suspiciousCount) {
      if (bf.mc.field_1724 != null) {
         bf.mc.execute(() -> {
            try {
               if (bf.mc.field_1724 != null) {
                  bf.mc.field_1724.method_5783((class_3414)class_3417.field_14793.comp_349(), 2.0F, 1.0F);
               }

               if (bf.mc.method_1566() != null) {
                  bf.mc.method_1566().method_1999(new class_368(this) {
                     private long startTime = -1L;

                     public class_369 method_1986(class_332 context, class_374 manager, long currentTime) {
                        if (this.startTime == -1L) {
                           this.startTime = currentTime;
                        }

                        context.method_25294(0, 0, 220, 40, -15066578);
                        context.method_27535(gq.mc.field_1772, class_2561.method_43470("§d§lChunk Found At: "), 10, 57 ^ 62, 16711935);
                        int var10002 = pos.field_9181;
                        context.method_27535(gq.mc.field_1772, class_2561.method_43470("§7" + var10002 + ", " + pos.field_9180), 10, 18, 11184810);
                        var10002 = suspiciousCount;
                        context.method_27535(gq.mc.field_1772, class_2561.method_43470("§8Suspicious blocks: " + var10002), 10, 28, 8948288 - 440);
                        return currentTime - this.startTime >= 5000L ? class_369.field_2209 : class_369.field_2210;
                     }

                     // $FF: synthetic method
                     private static String d984(String var0) {
                        byte[] var1 = Base64.getDecoder().decode(var0);
                        byte[] var2 = new byte[var1.length];

                        for(int var3 = 0; var3 < var1.length; ++var3) {
                           var2[var3] = (byte)(var1[var3] ^ 11145 - 214 + var3 & (56 ^ 199));
                        }

                        return new String(var2);
                     }
                  });
               }
            } catch (Exception var4) {
            }

         });
      }
   }

   private void scanHolesBackground() {
      if (!this.shouldStop && bf.mc.field_1687 != null && bf.mc.field_1724 != null) {
         try {
            List<class_2818> chunks = this.getLoadedChunks();
            Set<Long> currentChunkKeys = new HashSet();
            Iterator var3 = chunks.iterator();

            while(var3.hasNext()) {
               class_2818 chunk = (class_2818)var3.next();
               if (this.shouldStop) {
                  break;
               }

               long key = this.getChunkKey(chunk.method_12004().field_9181, chunk.method_12004().field_9180);
               currentChunkKeys.add(key);
               if (!this.processedChunks.contains(key)) {
                  this.searchChunkForHoles(chunk);
                  this.processedChunks.add(key);
               }
            }

            this.processedChunks.retainAll(currentChunkKeys);
            this.removeHolesOutsideChunks(currentChunkKeys);
         } catch (Exception var7) {
            var7.printStackTrace();
         }

      }
   }

   private void searchChunkForHoles(class_2818 chunk) {
      class_2826[] sections = chunk.method_12006();
      int worldBottom = bf.mc.field_1687.method_31607();
      Set<class_2338> checked = new HashSet();

      for(int sectionIndex = 0; sectionIndex < sections.length; ++sectionIndex) {
         class_2826 section = sections[sectionIndex];
         if (section != null && !section.method_38292()) {
            int sectionY = worldBottom + sectionIndex * 16;

            for(int x = 0; x < 16; ++x) {
               for(int z = 0; z < (180 ^ 164); ++z) {
                  for(int y = 0; y < 16; ++y) {
                     int worldY = sectionY + y;
                     class_2338 pos = chunk.method_12004().method_35231(x, worldY, z);
                     if (!checked.contains(pos) && this.isPassableBlock(pos)) {
                        this.checkHole1x1(pos, checked);
                     }
                  }
               }
            }
         }
      }

   }

   private void checkHole1x1(class_2338 pos, Set<class_2338> checked) {
      if (this.isValidHoleSection1x1(pos)) {
         if (pos.method_10264() < (7 ^ 57)) {
            class_2339 currentPos = pos.method_25503();

            while(this.isValidHoleSection1x1(currentPos)) {
               checked.add(currentPos.method_10062());
               currentPos.method_10098(class_2350.field_11036);
            }

            int depth = currentPos.method_10264() - pos.method_10264();
            if (depth >= 4) {
               class_238 holeBox = new class_238((double)pos.method_10263(), (double)pos.method_10264(), (double)pos.method_10260(), (double)(pos.method_10263() + 1), (double)currentPos.method_10264(), (double)(pos.method_10260() + 1));
               if (this.detectedHoles.stream().noneMatch((h) -> {
                  return h.method_994(holeBox);
               })) {
                  this.detectedHoles.add(holeBox);
               }
            }

         }
      }
   }

   private boolean isValidHoleSection1x1(class_2338 pos) {
      return this.isPassableBlock(pos) && !this.isPassableBlock(pos.method_10095()) && !this.isPassableBlock(pos.method_10072()) && !this.isPassableBlock(pos.method_10078()) && !this.isPassableBlock(pos.method_10067());
   }

   private boolean isPassableBlock(class_2338 pos) {
      if (bf.mc.field_1687 == null) {
         return false;
      } else {
         class_2680 state = bf.mc.field_1687.method_8320(pos);
         class_265 shape = state.method_26220(bf.mc.field_1687, pos);
         return shape.method_1110() || !class_259.method_1077().equals(shape);
      }
   }

   private void removeHolesOutsideChunks(Set<Long> currentChunkKeys) {
      this.detectedHoles.removeIf((box) -> {
         return !this.isBoxInChunks(box, currentChunkKeys);
      });
   }

   private boolean isBoxInChunks(class_238 box, Set<Long> chunkKeys) {
      int centerX = (int)Math.floor(box.method_1005().method_10216());
      int centerZ = (int)Math.floor(box.method_1005().method_10215());
      int chunkX = centerX >> 4;
      int chunkZ = centerZ >> 4;
      return chunkKeys.contains(this.getChunkKey(chunkX, chunkZ));
   }

   private long getChunkKey(int x, int z) {
      return (long)x << (149 ^ 181) | (long)z & 4294967295L;
   }

   @cp
   public void onRender3D(bg event) {
      if (bf.mc.field_1724 != null && bf.mc.field_1687 != null && !this.killSwitchActivated) {
         class_4587 matrices = event.matrixStack;
         class_4184 cam = bf.mc.field_1773.method_19418();
         class_243 camPos = cam.method_19326();
         matrices.method_22903();
         matrices.method_22907(class_7833.field_40714.rotationDegrees(cam.method_19329()));
         matrices.method_22907(class_7833.field_40716.rotationDegrees(cam.method_19330() + 180.0F));
         matrices.method_22904(-camPos.field_1352, -camPos.field_1351, -camPos.field_1350);
         Iterator var5;
         if (!this.flaggedChunks.isEmpty()) {
            var5 = this.flaggedChunks.iterator();

            while(var5.hasNext()) {
               class_1923 chunkPos = (class_1923)var5.next();
               if (!this.isInIgnoredArea(chunkPos)) {
                  this.renderChunkHighlight(matrices, chunkPos, CHUNK_COLOR);
               }
            }
         }

         if (this.holeESP.getValue() && !this.detectedHoles.isEmpty()) {
            var5 = this.detectedHoles.iterator();

            while(var5.hasNext()) {
               class_238 box = (class_238)var5.next();
               iq.renderFilledBox(matrices, (float)box.field_1323, (float)box.field_1322, (float)box.field_1321, (float)box.field_1320, (float)box.field_1325, (float)box.field_1324, HOLE_COLOR);
            }
         }

         matrices.method_22909();
      }
   }

   @cp
   public void onRender2D(ez event) {
      if (this.showDonutWarning && bf.mc.field_1724 != null) {
         long currentTime = System.currentTimeMillis();
         if (currentTime - this.warningStartTime > 5000L) {
            this.showDonutWarning = false;
         } else {
            class_332 context = event.context;
            String warning = "§c§lDonutSMP Issue Detected";
            String subtext = "§7Please ignore flagged chunks";
            int screenWidth = bf.mc.method_22683().method_4486();
            int warningWidth = bf.mc.field_1772.method_1727(warning);
            int subtextWidth = bf.mc.field_1772.method_1727(subtext);
            int x = screenWidth - Math.max(warningWidth, subtextWidth) - 10;
            int y = 10;
            context.method_25294(x - 5, y - 3, screenWidth - 5, y + 30, -587202042 - 518);
            context.method_25303(bf.mc.field_1772, warning, x, y, 16733784 - 259);
            context.method_25303(bf.mc.field_1772, subtext, x, y + 12, 11184810);
         }
      }
   }

   private void renderChunkHighlight(class_4587 matrices, class_1923 chunkPos, Color color) {
      double y = 60.0D;
      class_238 box = new class_238((double)(chunkPos.field_9181 * 16), y, (double)(chunkPos.field_9180 * 16), (double)(chunkPos.field_9181 * 16 + 16), y + 0.03D, (double)(chunkPos.field_9180 * (235 ^ 251) + 16));
      iq.renderFilledBox(matrices, (float)box.field_1323, (float)box.field_1322, (float)box.field_1321, (float)box.field_1320, (float)box.field_1325, (float)box.field_1324, color);
   }

   private void cleanupDistantData() {
      if (bf.mc.field_1724 != null) {
         int renderDistance = (Integer)bf.mc.field_1690.method_42503().method_41753();
         int playerChunkX = (int)bf.mc.field_1724.method_23317() / 16;
         int playerChunkZ = (int)bf.mc.field_1724.method_23321() / 16;
         this.flaggedChunks.removeIf((chunkPos) -> {
            return Math.abs(chunkPos.field_9181 - playerChunkX) > renderDistance || Math.abs(chunkPos.field_9180 - playerChunkZ) > renderDistance;
         });
         this.scannedChunks.removeIf((chunkPos) -> {
            return Math.abs(chunkPos.field_9181 - playerChunkX) > renderDistance || Math.abs(chunkPos.field_9180 - playerChunkZ) > renderDistance;
         });
         this.notifiedChunks.removeIf((chunkPos) -> {
            return Math.abs(chunkPos.field_9181 - playerChunkX) > renderDistance || Math.abs(chunkPos.field_9180 - playerChunkZ) > renderDistance;
         });
         this.flaggedBlocks.keySet().removeIf((chunkPos) -> {
            return Math.abs(chunkPos.field_9181 - playerChunkX) > renderDistance || Math.abs(chunkPos.field_9180 - playerChunkZ) > renderDistance;
         });
         this.ignoredAreas.removeIf((chunkPos) -> {
            return Math.abs(chunkPos.field_9181 - playerChunkX) > renderDistance + (246 ^ 252) || Math.abs(chunkPos.field_9180 - playerChunkZ) > renderDistance + 10;
         });
         this.detectedHoles.removeIf((box) -> {
            int chunkX = (int)Math.floor(box.method_1005().method_10216()) / (142 ^ 158);
            int chunkZ = (int)Math.floor(box.method_1005().method_10215()) / 16;
            return Math.abs(chunkX - playerChunkX) > renderDistance || Math.abs(chunkZ - playerChunkZ) > renderDistance;
         });
      }
   }

   private List<class_2818> getLoadedChunks() {
      if (bf.mc.field_1687 != null && bf.mc.field_1724 != null) {
         List<class_2818> chunks = new ArrayList();
         int renderDistance = (Integer)bf.mc.field_1690.method_42503().method_41753();
         class_1923 playerChunk = new class_1923(class_2338.method_49638(bf.mc.field_1724.method_19538()));

         for(int x = -renderDistance; x <= renderDistance; ++x) {
            for(int z = -renderDistance; z <= renderDistance; ++z) {
               class_2818 chunk = bf.mc.field_1687.method_2935().method_21730(playerChunk.field_9181 + x, playerChunk.field_9180 + z);
               if (chunk != null && !chunk.method_12223()) {
                  chunks.add(chunk);
               }
            }
         }

         return chunks;
      } else {
         return new ArrayList();
      }
   }

   // $FF: synthetic method
   private static String d88(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 8944 + var3 & 255);
      }

      return new String(var2);
   }
}
