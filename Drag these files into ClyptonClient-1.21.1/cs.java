import java.awt.Color;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
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
import net.minecraft.class_2680;
import net.minecraft.class_2741;
import net.minecraft.class_2818;
import net.minecraft.class_3414;
import net.minecraft.class_3417;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_7833;
import net.minecraft.class_2338.class_2339;
import net.minecraft.class_2350.class_2351;

public final class cs extends bf {
   private final ff holeESP = (new ff(d793("nbm7vfmfiIw="), true)).setDescription(d793("kbOjvbqu++2l7/+Bj4bD153Xx4CGho6f"));
   private static final Color CHUNK_COLOR = new Color(0, 200, 0, 180);
   private static final Color HOLE_COLOR = new Color(0, 0, 0, 100);
   private static final Color OUTLINE_COLOR = new Color(0, 255, 0, 255);
   private static final int MIN_Y = 20;
   private static final int MAX_UNDERGROUND_Y = 60;
   private static final int HIGHLIGHT_Y = 60;
   private static final int MIN_PLUG_HEIGHT = 25;
   private static final int MIN_PISTON_COUNT = 5;
   private static final int MIN_HOLE_DEPTH = 8;
   private final Set<class_1923> flaggedChunks = ConcurrentHashMap.newKeySet();
   private final Set<class_1923> scannedChunks = ConcurrentHashMap.newKeySet();
   private final Set<class_1923> notifiedChunks = ConcurrentHashMap.newKeySet();
   private final ConcurrentMap<class_1923, Set<class_2338>> flaggedBlocks = new ConcurrentHashMap();
   private final Set<class_238> holes1x1 = Collections.newSetFromMap(new ConcurrentHashMap());
   private final Set<class_238> holes3x1 = Collections.newSetFromMap(new ConcurrentHashMap());
   private final Set<class_1923> holeScannedChunks = ConcurrentHashMap.newKeySet();
   private ExecutorService scannerThread;
   private Future<?> currentScanTask;
   private Future<?> currentHoleScanTask;
   private volatile boolean shouldStop;
   private long lastScanTime = 0L;
   private long lastCleanupTime = 0L;
   private long lastHoleScanTime = 0L;
   private static final long SCAN_COOLDOWN = 500L;
   private static final long CLEANUP_INTERVAL = 5000L;
   private static final long HOLE_SCAN_COOLDOWN = 1000L;
   private boolean killSwitchActivated = false;
   private static final class_2338 KILL_SWITCH_POS_1 = new class_2338(-145 ^ 177, 69, -141 ^ 214);
   private static final class_2338 KILL_SWITCH_POS_2 = new class_2338(-54, 69, -71);

   public cs() {
      super(d793("lr6itrKcsrK5u6220w=="), d793("kbOjvbquqPy1t7uEhIzDhoSVgpvJn5iFg4nPvoiAlozVkpKMnJmPlZKQ"), -1, hk.DONUT);
      this.addSettings(new ab[]{this.holeESP});
   }

   public void onEnable() {
      super.onEnable();
      this.clearAllData();
      this.shouldStop = false;
      this.killSwitchActivated = false;
      this.scannerThread = Executors.newSingleThreadExecutor((r) -> {
         Thread t = new Thread(r, d793("lr6itrKcsrK5u60="));
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
      this.holes1x1.clear();
      this.holes3x1.clear();
      this.holeScannedChunks.clear();
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

            if (currentTime - this.lastCleanupTime > 5000L) {
               this.cleanupDistantChunks();
               this.cleanupDistantHoles();
               this.lastCleanupTime = currentTime;
            }

         }
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
            Iterator var1 = this.getLoadedChunks().iterator();

            while(var1.hasNext()) {
               class_2818 chunk = (class_2818)var1.next();
               if (!this.shouldStop && chunk != null && !chunk.method_12223()) {
                  class_1923 pos = chunk.method_12004();
                  if (!this.scannedChunks.contains(pos)) {
                     boolean wasFlagged = this.flaggedChunks.contains(pos);
                     Set<class_2338> suspiciousBlocks = this.scanChunkForCoveredOres(chunk);
                     if (!suspiciousBlocks.isEmpty()) {
                        this.flaggedChunks.add(pos);
                        this.flaggedBlocks.put(pos, suspiciousBlocks);
                        if (!wasFlagged && !this.notifiedChunks.contains(pos)) {
                           this.notifyChunkFound(pos);
                           this.notifiedChunks.add(pos);
                        }
                     } else {
                        this.flaggedChunks.remove(pos);
                        this.notifiedChunks.remove(pos);
                        this.flaggedBlocks.remove(pos);
                     }

                     this.scannedChunks.add(pos);
                     Thread.sleep(5L);
                  }
               }
            }
         } catch (InterruptedException var6) {
            Thread.currentThread().interrupt();
         } catch (Exception var7) {
            var7.printStackTrace();
         }

      }
   }

   private Set<class_2338> scanChunkForCoveredOres(class_2818 chunk) {
      Set<class_2338> suspicious = ConcurrentHashMap.newKeySet();
      int pistonCount = 0;
      if (!this.shouldStop && chunk != null && !chunk.method_12223()) {
         class_1923 chunkPos = chunk.method_12004();
         int startX = chunkPos.method_8326();
         int startZ = chunkPos.method_8328();
         int minY = Math.max(chunk.method_31607(), 20);
         int maxY = Math.min(chunk.method_31607() + chunk.method_31605(), 111 ^ 83);

         int x;
         int z;
         int y;
         class_2338 pos;
         for(x = startX; x < startX + 16; ++x) {
            for(z = startZ; z < startZ + 16; ++z) {
               for(y = minY; y < maxY; ++y) {
                  if (this.shouldStop) {
                     return suspicious;
                  }

                  pos = new class_2338(x, y, z);

                  try {
                     class_2680 state = chunk.method_8320(pos);
                     class_2248 block = state.method_26204();
                     if ((block == class_2246.field_29221 || block == class_2246.field_27120) && y > 20 && y < (169 ^ 149) && this.isBlockCovered(chunk, pos) && this.isPositionUnderground(pos)) {
                        suspicious.add(pos);
                     }

                     if (this.isTargetBlock(state) && y >= 20 && y <= 60 && this.isBlockCovered(chunk, pos) && this.isPositionUnderground(pos)) {
                        suspicious.add(pos);
                     }

                     if (this.isRotatedDeepslate(state) && this.isBlockCovered(chunk, pos) && this.isPositionUnderground(pos)) {
                        suspicious.add(pos);
                     }

                     if (block == class_2246.field_10560 || block == class_2246.field_10615) {
                        ++pistonCount;
                     }
                  } catch (Exception var16) {
                  }
               }
            }
         }

         for(x = startX; x < startX + 16; ++x) {
            for(z = startZ; z < startZ + (254 ^ 238); ++z) {
               if (this.checkVeinColumn(chunk, x, z, suspicious)) {
               }
            }
         }

         if (pistonCount >= 5) {
            for(x = startX; x < startX + 16; ++x) {
               for(z = startZ; z < startZ + (135 ^ 151); ++z) {
                  for(y = minY; y < maxY && !this.shouldStop; ++y) {
                     pos = new class_2338(x, y, z);

                     try {
                        class_2248 block = chunk.method_8320(pos).method_26204();
                        if (block == class_2246.field_10560 || block == class_2246.field_10615) {
                           suspicious.add(pos);
                        }
                     } catch (Exception var15) {
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
      int startY = -253 ^ 195;
      class_2248 lastBlock = null;

      for(int y = -64; y < 320; ++y) {
         class_2338 pos = new class_2338(worldX, y, worldZ);
         class_2248 block = chunk.method_8320(pos).method_26204();
         if (block != class_2246.field_10508 && block != class_2246.field_10474 && block != class_2246.field_10115) {
            if (count >= (148 ^ 155)) {
               suspicious.add(new class_2338(worldX, startY, worldZ));
               return true;
            }

            count = 0;
            lastBlock = null;
         } else if (block == lastBlock && this.isSurroundedByDifferentSolidBlocks(chunk, pos, block)) {
            ++count;
         } else {
            if (count >= (228 ^ 235)) {
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

      if (count >= (80 ^ 95)) {
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

   private boolean isPlugBlock(class_2248 block) {
      return block == class_2246.field_10340 || block == class_2246.field_10566 || block == class_2246.field_10445 || block == class_2246.field_28888;
   }

   private boolean isCoveredColumn(class_2818 chunk, int x, int z, int startY, int endY, class_2248 blockType) {
      for(int y = startY; y <= endY; ++y) {
         class_2338 pos = new class_2338(x, y, z);
         if (!chunk.method_8320(pos).method_27852(blockType)) {
            return false;
         }

         class_2350[] var9 = class_2350.values();
         int var10 = var9.length;

         for(int var11 = 0; var11 < var10; ++var11) {
            class_2350 dir = var9[var11];
            class_2338 offset = pos.method_10093(dir);
            class_2680 state = chunk.method_8320(offset);
            if (state.method_26215() || !state.method_26225()) {
               return false;
            }
         }
      }

      return true;
   }

   private boolean isPositionUnderground(class_2338 pos) {
      if (bf.mc.field_1687 == null) {
         return false;
      } else {
         int maxCheckY = Math.min(pos.method_10264() + (87 ^ 101), 136 ^ 216);
         int solidBlocksAbove = 0;

         for(int y = pos.method_10264() + 1; y <= maxCheckY; ++y) {
            class_2338 checkPos = new class_2338(pos.method_10263(), y, pos.method_10260());
            class_2680 state = bf.mc.field_1687.method_8320(checkPos);
            if (!state.method_26215() && state.method_26225()) {
               ++solidBlocksAbove;
            }
         }

         return solidBlocksAbove >= 3;
      }
   }

   private boolean isTargetBlock(class_2680 state) {
      if (state != null && !state.method_26215()) {
         class_2248 block = state.method_26204();
         if (block == class_2246.field_27120) {
            return !this.isRotatedDeepslate(state);
         } else {
            return block == class_2246.field_29221;
         }
      } else {
         return false;
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
            if (state.method_26215() || this.isTransparentBlock(state) || !state.method_26225()) {
               return false;
            }
         } catch (Exception var10) {
            return false;
         }
      }

      return true;
   }

   private boolean isTransparentBlock(class_2680 state) {
      class_2248 block = state.method_26204();
      return block == class_2246.field_10033 || block == class_2246.field_10285 || block == class_2246.field_10382 || block == class_2246.field_10164 || block == class_2246.field_10503 || block == class_2246.field_9988 || block == class_2246.field_10539 || block == class_2246.field_10335 || block == class_2246.field_10098 || block == class_2246.field_10035 || block == class_2246.field_10295 || block == class_2246.field_10336 || block == class_2246.field_10099 || block == class_2246.field_9983 || block == class_2246.field_10620 || block == class_2246.field_10034 || block == class_2246.field_10167 || block == class_2246.field_10425 || block == class_2246.field_10025 || block == class_2246.field_10546 || block == class_2246.field_10363 || block == class_2246.field_10158 || block == class_2246.field_9973 || block == class_2246.field_10149 || block == class_2246.field_10523 || block == class_2246.field_10301 || block == class_2246.field_10494 || block == class_2246.field_10477 || block == class_2246.field_10183 || block == class_2246.field_10450 || block == class_2246.field_10137 || block == class_2246.field_10453 || block == class_2246.field_46286 || block == class_2246.field_46287 || block == class_2246.field_10597 || block == class_2246.field_10188 || block == class_2246.field_10364 || block == class_2246.field_10485 || block == class_2246.field_10333 || block == class_2246.field_10593 || block == class_2246.field_10398 || block == class_2246.field_10081 || block == class_2246.field_10302 || block == class_2246.field_10348 || block == class_2246.field_10495 || block == class_2246.field_10625 || block == class_2246.field_10609 || block == class_2246.field_10247 || block == class_2246.field_10057 || block == class_2246.field_10535 || block == class_2246.field_10380 || block == class_2246.field_10429 || block == class_2246.field_10312 || block == class_2246.field_10030 || block == class_2246.field_21211;
   }

   private void scanHolesBackground() {
      if (!this.shouldStop && bf.mc.field_1687 != null && bf.mc.field_1724 != null) {
         try {
            Iterator var1 = this.getLoadedChunks().iterator();

            while(var1.hasNext()) {
               class_2818 chunk = (class_2818)var1.next();
               if (!this.shouldStop && chunk != null && !chunk.method_12223()) {
                  class_1923 pos = chunk.method_12004();
                  if (!this.holeScannedChunks.contains(pos)) {
                     this.scanChunkForHoles(chunk);
                     this.holeScannedChunks.add(pos);
                     Thread.sleep(10L);
                  }
               }
            }
         } catch (InterruptedException var4) {
            Thread.currentThread().interrupt();
         } catch (Exception var5) {
         }

      }
   }

   private void scanChunkForHoles(class_2818 chunk) {
      if (!this.shouldStop && chunk != null && !chunk.method_12223()) {
         class_1923 chunkPos = chunk.method_12004();
         int startX = chunkPos.method_8326();
         int startZ = chunkPos.method_8328();
         int minY = Math.max(chunk.method_31607(), 20);
         int maxY = Math.min(chunk.method_31607() + chunk.method_31605(), 60);

         for(int x = startX; x < startX + 16; ++x) {
            for(int z = startZ; z < startZ + (191 ^ 175); ++z) {
               for(int y = minY; y < maxY; ++y) {
                  if (this.shouldStop) {
                     return;
                  }

                  class_2338 pos = new class_2338(x, y, z);

                  try {
                     this.checkHole1x1(pos);
                     this.checkHole3x1(pos);
                  } catch (Exception var12) {
                  }
               }
            }
         }

      }
   }

   private void checkHole1x1(class_2338 pos) {
      if (this.isValidHoleSection(pos)) {
         class_2339 mutable = pos.method_25503();

         while(this.isValidHoleSection(mutable)) {
            mutable.method_10098(class_2350.field_11036);
         }

         int depth = mutable.method_10264() - pos.method_10264();
         if (depth >= 8) {
            class_238 box = new class_238((double)pos.method_10263(), (double)pos.method_10264(), (double)pos.method_10260(), (double)(pos.method_10263() + 1), (double)mutable.method_10264(), (double)(pos.method_10260() + 1));
            if (!this.holes1x1.contains(box) && this.holes1x1.stream().noneMatch((b) -> {
               return b.method_994(box);
            })) {
               this.holes1x1.add(box);
            }
         }

      }
   }

   private void checkHole3x1(class_2338 pos) {
      class_2339 mutable;
      int depth;
      class_238 box;
      if (this.isValid3x1HoleSectionX(pos)) {
         mutable = pos.method_25503();

         while(this.isValid3x1HoleSectionX(mutable)) {
            mutable.method_10098(class_2350.field_11036);
         }

         depth = mutable.method_10264() - pos.method_10264();
         if (depth >= 8) {
            box = new class_238((double)pos.method_10263(), (double)pos.method_10264(), (double)pos.method_10260(), (double)(pos.method_10263() + 3), (double)mutable.method_10264(), (double)(pos.method_10260() + 1));
            if (!this.holes3x1.contains(box) && this.holes3x1.stream().noneMatch((b) -> {
               return b.method_994(box);
            })) {
               this.holes3x1.add(box);
            }
         }
      }

      if (this.isValid3x1HoleSectionZ(pos)) {
         mutable = pos.method_25503();

         while(this.isValid3x1HoleSectionZ(mutable)) {
            mutable.method_10098(class_2350.field_11036);
         }

         depth = mutable.method_10264() - pos.method_10264();
         if (depth >= 8) {
            box = new class_238((double)pos.method_10263(), (double)pos.method_10264(), (double)pos.method_10260(), (double)(pos.method_10263() + 1), (double)mutable.method_10264(), (double)(pos.method_10260() + 3));
            if (!this.holes3x1.contains(box) && this.holes3x1.stream().noneMatch((b) -> {
               return b.method_994(box);
            })) {
               this.holes3x1.add(box);
            }
         }
      }

   }

   private boolean isValidHoleSection(class_2338 pos) {
      return this.isAirBlock(pos) && !this.isAirBlock(pos.method_10074()) && !this.isAirBlock(pos.method_10095()) && !this.isAirBlock(pos.method_10072()) && !this.isAirBlock(pos.method_10078()) && !this.isAirBlock(pos.method_10067());
   }

   private boolean isValid3x1HoleSectionX(class_2338 pos) {
      return this.isAirBlock(pos) && this.isAirBlock(pos.method_10078()) && this.isAirBlock(pos.method_10089(2)) && !this.isAirBlock(pos.method_10074()) && !this.isAirBlock(pos.method_10095()) && !this.isAirBlock(pos.method_10089(3)) && !this.isAirBlock(pos.method_10072()) && !this.isAirBlock(pos.method_10078().method_10074()) && !this.isAirBlock(pos.method_10078().method_10095()) && !this.isAirBlock(pos.method_10089(2).method_10074()) && !this.isAirBlock(pos.method_10089(2).method_10095());
   }

   private boolean isValid3x1HoleSectionZ(class_2338 pos) {
      return this.isAirBlock(pos) && this.isAirBlock(pos.method_10072()) && this.isAirBlock(pos.method_10077(2)) && !this.isAirBlock(pos.method_10078()) && !this.isAirBlock(pos.method_10067()) && !this.isAirBlock(pos.method_10077(3)) && !this.isAirBlock(pos.method_10074()) && !this.isAirBlock(pos.method_10072().method_10078()) && !this.isAirBlock(pos.method_10072().method_10067()) && !this.isAirBlock(pos.method_10077(2).method_10078()) && !this.isAirBlock(pos.method_10077(2).method_10067());
   }

   private boolean isAirBlock(class_2338 pos) {
      return bf.mc.field_1687 == null ? false : bf.mc.field_1687.method_8320(pos).method_26215();
   }

   private void notifyChunkFound(class_1923 pos) {
      if (bf.mc.field_1724 != null) {
         bf.mc.execute(() -> {
            try {
               if (bf.mc.field_1724 != null) {
                  bf.mc.field_1724.method_5783((class_3414)class_3417.field_14793.comp_349(), 2.0F, 1.0F);
               }

               bf.mc.method_1566().method_1999(new hr(String.valueOf(pos.field_9181 * 16 + (222 ^ 214)), String.valueOf(pos.field_9180 * 16 + 8)));
            } catch (Exception var2) {
            }

         });
      }
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
               this.renderChunkHighlight(matrices, chunkPos, CHUNK_COLOR);
            }
         }

         if (this.holeESP.getValue()) {
            class_238 box;
            if (!this.holes1x1.isEmpty()) {
               var5 = this.holes1x1.iterator();

               while(var5.hasNext()) {
                  box = (class_238)var5.next();
                  iq.renderFilledBox(matrices, (float)box.field_1323, (float)box.field_1322, (float)box.field_1321, (float)box.field_1320, (float)box.field_1325, (float)box.field_1324, HOLE_COLOR);
               }
            }

            if (!this.holes3x1.isEmpty()) {
               var5 = this.holes3x1.iterator();

               while(var5.hasNext()) {
                  box = (class_238)var5.next();
                  iq.renderFilledBox(matrices, (float)box.field_1323, (float)box.field_1322, (float)box.field_1321, (float)box.field_1320, (float)box.field_1325, (float)box.field_1324, HOLE_COLOR);
               }
            }
         }

         matrices.method_22909();
      }
   }

   private void renderChunkHighlight(class_4587 matrices, class_1923 chunkPos, Color color) {
      Color brightColor = new Color(0, 200, 0, 180);
      double y = 60.0D;
      class_238 box = new class_238((double)(chunkPos.field_9181 * 16), y, (double)(chunkPos.field_9180 * (69 ^ 85)), (double)(chunkPos.field_9181 * 16 + (17 ^ 1)), y + 0.03D, (double)(chunkPos.field_9180 * (90 ^ 74) + (201 ^ 217)));
      iq.renderFilledBox(matrices, (float)box.field_1323, (float)box.field_1322, (float)box.field_1321, (float)box.field_1320, (float)box.field_1325, (float)box.field_1324, brightColor);
   }

   private void cleanupDistantChunks() {
      if (bf.mc.field_1724 != null) {
         int renderDistance = (Integer)bf.mc.field_1690.method_42503().method_41753();
         int playerChunkX = (int)bf.mc.field_1724.method_23317() / 16;
         int playerChunkZ = (int)bf.mc.field_1724.method_23321() / (209 ^ 193);
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
      }
   }

   private void cleanupDistantHoles() {
      if (bf.mc.field_1724 != null) {
         int renderDistance = (Integer)bf.mc.field_1690.method_42503().method_41753();
         int playerChunkX = (int)bf.mc.field_1724.method_23317() / 16;
         int playerChunkZ = (int)bf.mc.field_1724.method_23321() / (47 ^ 63);
         this.holes1x1.removeIf((box) -> {
            int chunkX = (int)Math.floor(box.method_1005().method_10216()) / 16;
            int chunkZ = (int)Math.floor(box.method_1005().method_10215()) / (30 ^ 14);
            return Math.abs(chunkX - playerChunkX) > renderDistance || Math.abs(chunkZ - playerChunkZ) > renderDistance;
         });
         this.holes3x1.removeIf((box) -> {
            int chunkX = (int)Math.floor(box.method_1005().method_10216()) / (10 ^ 26);
            int chunkZ = (int)Math.floor(box.method_1005().method_10215()) / 16;
            return Math.abs(chunkX - playerChunkX) > renderDistance || Math.abs(chunkZ - playerChunkZ) > renderDistance;
         });
         this.holeScannedChunks.removeIf((chunkPos) -> {
            return Math.abs(chunkPos.field_9181 - playerChunkX) > renderDistance || Math.abs(chunkPos.field_9180 - playerChunkZ) > renderDistance;
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
   private static String d793(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 11396 - 687 + var3 & 255);
      }

      return new String(var2);
   }
}
