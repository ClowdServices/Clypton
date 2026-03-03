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
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import net.minecraft.class_1923;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_2561;
import net.minecraft.class_2680;
import net.minecraft.class_2818;
import net.minecraft.class_327;
import net.minecraft.class_332;
import net.minecraft.class_3414;
import net.minecraft.class_3417;
import net.minecraft.class_368;
import net.minecraft.class_374;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_7833;
import net.minecraft.class_368.class_369;

public final class ir extends bf {
   private final ff notifyMe;
   private final ff detectVines;
   private final ff detectDripstone;
   private final ff detectAirPockets;
   private final gn vineMinLength;
   private final gn dripstoneMinLength;
   private final gn airPocketMinSize;
   private final gn chunkOpacity;
   private final gn scanSpeed;
   private static final Color ANOMALY_COLOR = new Color(238 ^ 17, 165, 0, 114 ^ 198);
   private static final Color OUTLINE_COLOR = new Color(255, 50 ^ 190, 0, 255);
   private static final int MIN_Y = 0;
   private static final int MAX_Y = 256;
   private static final int HIGHLIGHT_Y = 60;
   private final Map<class_1923, dp> anomalyChunks = new ConcurrentHashMap();
   private final Set<class_1923> scannedChunks = ConcurrentHashMap.newKeySet();
   private final Set<class_1923> notifiedChunks = ConcurrentHashMap.newKeySet();
   private ExecutorService scannerThread;
   private ScheduledExecutorService continuousScanner;
   private Future<?> currentScanTask;
   private volatile boolean shouldStop;
   private long lastScanTime = 0L;
   private long lastCleanupTime = 0L;
   private static final long CLEANUP_INTERVAL = 5000L;
   private int scanBatchSize = 4;
   private final Queue<class_1923> pendingScans = new ConcurrentLinkedQueue();
   private class_243 lastPlayerPos;

   public ir() {
      super(d504("S0VTUEJNS2JCSlxNTElf"), d504("W0VVR0BQVgZSRlxZXk1BDltVQ0BSXVsWUVdLV1pIVFFRMw=="), -1, hk.DONUT);
      this.lastPlayerPos = class_243.field_1353;
      this.notifyMe = new ff(d504("UU9VS0VdBWtC"), true);
      this.detectVines = new ff(d504("W0VVR0BQBXBORkxZ"), true);
      this.detectDripstone = new ff(d504("W0VVR0BQBWJVQVlZX0NDSw=="), true);
      this.detectAirPockets = new ff(d504("W0VVR0BQBWdOWgl6RE9GS1tD"), true);
      this.vineMinLength = new gn(d504("SUlPRwNpTEgHZExETFhF"), 1.0D, 50.0D, 8.0D, 1.0D);
      this.dripstoneMinLength = new gn(d504("W1JIUlBQSkhCCGRDRQxhS0FXRVo="), 1.0D, 50.0D, 6.0D, 1.0D);
      this.airPocketMinSize = new gn(d504("XklTAnNLRk1CXAlnQkINfUZKVA=="), 10.0D, 500.0D, 50.0D, 10.0D);
      this.chunkOpacity = new gn(d504("XEhUTEgEalZGS0BeUg=="), 1.0D, 255.0D, 180.0D, 1.0D);
      this.scanSpeed = new gn(d504("TENATAN3VUNCTA=="), 1.0D, 10.0D, 5.0D, 1.0D);
      ab[] var10001 = new ab[]{this.notifyMe, this.detectVines, this.detectDripstone, this.detectAirPockets, this.vineMinLength, this.dripstoneMinLength, null, null, null};
      var10001[43 ^ 45] = this.airPocketMinSize;
      var10001[7] = this.chunkOpacity;
      var10001[8] = this.scanSpeed;
      this.addSettings(var10001);
   }

   public void onEnable() {
      super.onEnable();
      this.clearAllData();
      this.shouldStop = false;
      this.lastPlayerPos = bf.mc.field_1724 != null ? bf.mc.field_1724.method_19538() : class_243.field_1353;
      this.scannerThread = Executors.newFixedThreadPool(3, (r) -> {
         Thread t = new Thread(r);
         t.setName(d504("S0VTUEJNS2dJR0RLR1VpS01FVlVWRhhlVFlXVF5O"));
         t.setDaemon(true);
         t.setPriority(6);
         return t;
      });
      this.continuousScanner = Executors.newScheduledThreadPool(1, (r) -> {
         Thread t = new Thread(r);
         t.setName(d504("S0VTUEJNS2dJR0RLR1VpS01FVlVWRhh1WFZNU1VJUktM"));
         t.setDaemon(true);
         t.setPriority(1);
         return t;
      });
      this.continuousScanner.scheduleAtFixedRate(this::continuousScan, 0L, 200L, TimeUnit.MILLISECONDS);
      this.scheduleChunkScan();
   }

   public void onDisable() {
      super.onDisable();
      this.shouldStop = true;
      if (this.currentScanTask != null && !this.currentScanTask.isDone()) {
         this.currentScanTask.cancel(true);
      }

      if (this.scannerThread != null && !this.scannerThread.isShutdown()) {
         this.scannerThread.shutdownNow();
      }

      if (this.continuousScanner != null && !this.continuousScanner.isShutdown()) {
         this.continuousScanner.shutdownNow();
      }

      this.clearAllData();
   }

   private void clearAllData() {
      this.anomalyChunks.clear();
      this.scannedChunks.clear();
      this.notifiedChunks.clear();
      this.pendingScans.clear();
   }

   @cp
   public void onTick(hm event) {
      if (bf.mc.field_1687 != null && bf.mc.field_1724 != null) {
         long currentTime = System.currentTimeMillis();
         class_243 currentPos = bf.mc.field_1724.method_19538();
         double distanceMoved = currentPos.method_1022(this.lastPlayerPos);
         this.lastPlayerPos = currentPos;
         if (distanceMoved > 50.0D) {
            this.scanBatchSize = (int)this.scanSpeed.getValue() * 3;
         } else if (distanceMoved > 10.0D) {
            this.scanBatchSize = (int)this.scanSpeed.getValue() * 2;
         } else {
            this.scanBatchSize = (int)this.scanSpeed.getValue();
         }

         int fps = bf.mc.method_47599();
         long scanCooldown = fps > 60 ? 100L : (fps > (22 ^ 8) ? 300L : 500L);
         if (currentTime - this.lastScanTime > scanCooldown) {
            this.scheduleChunkScan();
            this.lastScanTime = currentTime;
         }

         if (currentTime - this.lastCleanupTime > 5000L) {
            this.cleanupDistantChunks();
            this.lastCleanupTime = currentTime;
         }

      }
   }

   private void continuousScan() {
      if (!this.shouldStop && mc.field_1687 != null && mc.field_1724 != null) {
         try {
            int viewDistance = Math.min((Integer)mc.field_1690.method_42503().method_41753(), 132 ^ 140);
            int playerChunkX = (int)mc.field_1724.method_23317() >> 4;
            int playerChunkZ = (int)mc.field_1724.method_23321() >> 4;

            for(int radius = 0; radius <= viewDistance; ++radius) {
               for(int dx = -radius; dx <= radius; ++dx) {
                  for(int dz = -radius; dz <= radius; ++dz) {
                     if (Math.abs(dx) == radius || Math.abs(dz) == radius) {
                        class_1923 chunkPos = new class_1923(playerChunkX + dx, playerChunkZ + dz);
                        if (!this.scannedChunks.contains(chunkPos) && !this.pendingScans.contains(chunkPos)) {
                           this.pendingScans.offer(chunkPos);
                        }
                     }
                  }
               }
            }
         } catch (Exception var8) {
         }

      }
   }

   private void scheduleChunkScan() {
      if (!this.shouldStop && this.scannerThread != null && !this.scannerThread.isShutdown()) {
         if (this.currentScanTask == null || this.currentScanTask.isDone()) {
            this.currentScanTask = this.scannerThread.submit(this::scanChunksBackground);
         }
      }
   }

   private void scanChunksBackground() {
      if (!this.shouldStop && bf.mc.field_1687 != null && bf.mc.field_1724 != null) {
         try {
            int scanned = 0;
            List chunks = this.getLoadedChunks();

            class_2818 chunk;
            while(scanned < this.scanBatchSize && !this.pendingScans.isEmpty()) {
               class_1923 pos = (class_1923)this.pendingScans.poll();
               if (pos == null) {
                  break;
               }

               chunk = bf.mc.field_1687.method_8497(pos.field_9181, pos.field_9180);
               if (chunk != null && !chunk.method_12223()) {
                  this.scanSingleChunk(chunk);
                  ++scanned;
               }
            }

            Iterator var8 = chunks.iterator();

            while(var8.hasNext()) {
               chunk = (class_2818)var8.next();
               if (this.shouldStop || scanned >= this.scanBatchSize) {
                  break;
               }

               if (chunk != null && !chunk.method_12223()) {
                  class_1923 pos = chunk.method_12004();
                  if (!this.scannedChunks.contains(pos)) {
                     this.scanSingleChunk(chunk);
                     ++scanned;
                     if (scanned % 2 == 0) {
                        Thread.sleep(1L);
                     }
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

   private void scanSingleChunk(class_2818 chunk) {
      if (chunk != null && !chunk.method_12223() && !this.shouldStop) {
         class_1923 pos = chunk.method_12004();
         if (!this.scannedChunks.contains(pos)) {
            dp report = new dp();
            if (this.detectVines.getValue()) {
               dk vines = this.analyzeVines(chunk);
               if (vines.hasAnomaly && (double)vines.maxLength >= this.vineMinLength.getValue()) {
                  report.addAnomaly(d504("U09PRQNyTEhCWw=="), vines.maxLength + " blocks long (" + vines.percentage + "%)", vines.maxLength);
               }
            }

            if (this.detectDripstone.getValue()) {
               ag dripstone = this.analyzeDripstone(chunk);
               if (dripstone.hasAnomaly && (double)dripstone.maxLength >= this.dripstoneMinLength.getValue()) {
                  report.addAnomaly(d504("U09PRQNgV09XW11FRUk="), dripstone.maxLength + " blocks long (" + dripstone.percentage + "%)", dripstone.maxLength);
               }
            }

            if (this.detectAirPockets.getValue()) {
               b airPockets = this.analyzeAirPockets(chunk);
               if (airPockets.hasAnomaly && (double)airPockets.size >= this.airPocketMinSize.getValue()) {
                  report.addAnomaly(d504("XklTAnNLRk1CXA=="), "Max " + airPockets.size + " blocks", airPockets.size);
               }
            }

            if (report.hasAnomalies()) {
               this.anomalyChunks.put(pos, report);
               if (!this.notifiedChunks.contains(pos)) {
                  this.notifyAnomalyFound(pos, report);
                  this.notifiedChunks.add(pos);
               }
            } else {
               this.anomalyChunks.remove(pos);
               this.notifiedChunks.remove(pos);
            }

            this.scannedChunks.add(pos);
         }
      }
   }

   private dk analyzeVines(class_2818 chunk) {
      class_1923 pos = chunk.method_12004();
      int startX = pos.method_8326();
      int startZ = pos.method_8328();
      int maxVineLength = 0;
      int totalVines = 0;
      int longVines = 0;

      int z;
      for(int x = startX; x < startX + (121 ^ 105); ++x) {
         for(z = startZ; z < startZ + (83 ^ 67); ++z) {
            for(int y = 256; y >= 0; --y) {
               class_2338 blockPos = new class_2338(x, y, z);
               class_2248 block = chunk.method_8320(blockPos).method_26204();
               if (block == class_2246.field_10597 || block == class_2246.field_22123 || block == class_2246.field_23078 || block == class_2246.field_28675) {
                  int vineLength = 0;

                  int checkY;
                  for(checkY = y; checkY >= 0; --checkY) {
                     class_2338 checkPos = new class_2338(x, checkY, z);
                     class_2248 checkBlock = chunk.method_8320(checkPos).method_26204();
                     if (checkBlock != block) {
                        break;
                     }

                     ++vineLength;
                  }

                  if (vineLength > 0) {
                     ++totalVines;
                     maxVineLength = Math.max(maxVineLength, vineLength);
                     if (vineLength >= 3) {
                        ++longVines;
                     }
                  }

                  y = checkY;
               }
            }
         }
      }

      boolean hasAnomaly = maxVineLength >= 3;
      z = totalVines > 0 ? (int)((double)longVines / (double)totalVines * 100.0D) : 0;
      return new dk(maxVineLength, hasAnomaly, z);
   }

   private ag analyzeDripstone(class_2818 chunk) {
      class_1923 pos = chunk.method_12004();
      int startX = pos.method_8326();
      int startZ = pos.method_8328();
      int maxDripstoneLength = 0;
      int totalDripstone = 0;
      int longDripstone = 0;

      int z;
      for(int x = startX; x < startX + 16; ++x) {
         for(z = startZ; z < startZ + 16; ++z) {
            for(int y = 256; y >= 0; --y) {
               class_2338 blockPos = new class_2338(x, y, z);
               class_2248 block = chunk.method_8320(blockPos).method_26204();
               if (block == class_2246.field_28048) {
                  int dripstoneLength = 0;

                  int checkY;
                  for(checkY = y; checkY >= 0; --checkY) {
                     class_2338 checkPos = new class_2338(x, checkY, z);
                     class_2248 checkBlock = chunk.method_8320(checkPos).method_26204();
                     if (checkBlock != class_2246.field_28048 && checkBlock != class_2246.field_28049) {
                        break;
                     }

                     ++dripstoneLength;
                  }

                  if (dripstoneLength > 0) {
                     ++totalDripstone;
                     maxDripstoneLength = Math.max(maxDripstoneLength, dripstoneLength);
                     if (dripstoneLength >= 3) {
                        ++longDripstone;
                     }
                  }

                  y = checkY;
               }
            }
         }
      }

      boolean hasAnomaly = maxDripstoneLength >= 3;
      z = totalDripstone > 0 ? (int)((double)longDripstone / (double)totalDripstone * 100.0D) : 0;
      return new ag(maxDripstoneLength, hasAnomaly, z);
   }

   private b analyzeAirPockets(class_2818 chunk) {
      class_1923 pos = chunk.method_12004();
      int startX = pos.method_8326();
      int startZ = pos.method_8328();
      Set<class_2338> visitedAir = new HashSet();
      int maxPocketSize = 0;

      for(int x = startX; x < startX + (146 ^ 130); ++x) {
         for(int z = startZ; z < startZ + 16; ++z) {
            for(int y = 10; y < 206; ++y) {
               class_2338 blockPos = new class_2338(x, y, z);
               if (!visitedAir.contains(blockPos)) {
                  class_2680 state = chunk.method_8320(blockPos);
                  if (state.method_26215()) {
                     Set<class_2338> pocketBlocks = new HashSet();
                     int pocketSize = this.floodFillAir(chunk, blockPos, visitedAir, pocketBlocks);
                     if (pocketSize >= 10 && this.isIsolatedPocket(pocketBlocks, startX, startZ)) {
                        maxPocketSize = Math.max(maxPocketSize, pocketSize);
                     }
                  }
               }
            }
         }
      }

      boolean hasAnomaly = maxPocketSize >= 10;
      return new b(maxPocketSize, hasAnomaly);
   }

   private int floodFillAir(class_2818 chunk, class_2338 start, Set<class_2338> visited, Set<class_2338> pocketBlocks) {
      Queue<class_2338> queue = new LinkedList();
      queue.add(start);
      visited.add(start);
      pocketBlocks.add(start);
      int count = 0;
      short maxSize = 500;

      while(!queue.isEmpty() && count < maxSize) {
         class_2338 current = (class_2338)queue.poll();
         ++count;
         class_2338[] var10000 = new class_2338[207 ^ 201];
         var10000[0] = current.method_10095();
         var10000[1] = current.method_10072();
         var10000[2] = current.method_10078();
         var10000[3] = current.method_10067();
         var10000[4] = current.method_10084();
         var10000[5] = current.method_10074();
         class_2338[] neighbors = var10000;
         class_2338[] var10 = neighbors;
         int var11 = neighbors.length;

         for(int var12 = 0; var12 < var11; ++var12) {
            class_2338 neighbor = var10[var12];
            if (!visited.contains(neighbor)) {
               try {
                  class_2680 state = chunk.method_8320(neighbor);
                  if (state.method_26215()) {
                     visited.add(neighbor);
                     pocketBlocks.add(neighbor);
                     queue.add(neighbor);
                  }
               } catch (Exception var15) {
               }
            }
         }
      }

      return count;
   }

   private boolean isIsolatedPocket(Set<class_2338> pocketBlocks, int chunkStartX, int chunkStartZ) {
      Iterator var4 = pocketBlocks.iterator();

      while(var4.hasNext()) {
         class_2338 pos = (class_2338)var4.next();
         int localX = pos.method_10263() - chunkStartX;
         int localZ = pos.method_10260() - chunkStartZ;
         if (localX > 0 && localX < (59 ^ 52) && localZ > 0 && localZ < (60 ^ 51)) {
            if (pos.method_10264() > 5 && pos.method_10264() < 201) {
               continue;
            }

            return false;
         }

         return false;
      }

      return true;
   }

   private void notifyAnomalyFound(class_1923 pos, dp report) {
      if (this.notifyMe.getValue()) {
         try {
            String primaryAnomaly = report.getPrimaryAnomaly();
            if (bf.mc.field_1724 != null) {
               bf.mc.field_1724.method_7353(class_2561.method_43470("§6[Debugger] §e" + primaryAnomaly), false);
            }

            if (bf.mc.method_1566() != null) {
               bf.mc.method_1566().method_1999(new class_368(this) {
                  private long startTime = -1L;

                  public class_369 method_1986(class_332 context, class_374 manager, long currentTime) {
                     if (this.startTime == -1L) {
                        this.startTime = currentTime;
                     }

                     context.method_25294(0, 0, 200, 32, -29696);
                     context.method_27535(ir.mc.field_1772, class_2561.method_43470("§6§lAnomaly Detected!"), 10, 7, 16777425 - 210);
                     class_327 var10001 = ir.mc.field_1772;
                     String var10002 = report.getShortDescription();
                     context.method_27535(var10001, class_2561.method_43470("§7" + var10002), 89 ^ 83, 18, 11184810);
                     return currentTime - this.startTime >= 5000L ? class_369.field_2209 : class_369.field_2210;
                  }

                  // $FF: synthetic method
                  private static String d769(String var0) {
                     byte[] var1 = Base64.getDecoder().decode(var0);
                     byte[] var2 = new byte[var1.length];

                     for(int var3 = 0; var3 < var1.length; ++var3) {
                        var2[var3] = (byte)(var1[var3] ^ 4692 - 63 + var3 & 255);
                     }

                     return new String(var2);
                  }
               });
            }

            if (bf.mc.field_1724 != null) {
               bf.mc.field_1724.method_5783((class_3414)class_3417.field_14622.comp_349(), 1.0F, 1.5F);
            }
         } catch (Exception var4) {
            var4.printStackTrace();
         }

      }
   }

   private List<class_2818> getLoadedChunks() {
      if (bf.mc.field_1687 == null) {
         return new ArrayList();
      } else {
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
      }
   }

   private void cleanupDistantChunks() {
      if (bf.mc.field_1724 != null) {
         class_2338 playerPos = class_2338.method_49638(bf.mc.field_1724.method_19538());
         double maxDistanceSq = Math.pow(128.0D, 2.0D);
         this.anomalyChunks.keySet().removeIf((chunkPos) -> {
            return chunkPos.method_8323().method_10262(playerPos) > maxDistanceSq;
         });
         this.scannedChunks.removeIf((chunkPos) -> {
            return chunkPos.method_8323().method_10262(playerPos) > maxDistanceSq;
         });
         this.notifiedChunks.removeIf((chunkPos) -> {
            return chunkPos.method_8323().method_10262(playerPos) > maxDistanceSq;
         });
      }
   }

   @cp
   public void onRender3D(bg event) {
      if (bf.mc.field_1724 != null && bf.mc.field_1687 != null) {
         class_4587 matrices = event.matrixStack;
         class_4184 camera = bf.mc.field_1773.method_19418();
         class_243 cameraPos = camera.method_19326();
         class_243 playerPos = bf.mc.field_1724.method_19538();
         matrices.method_22903();
         matrices.method_22907(class_7833.field_40714.rotationDegrees(camera.method_19329()));
         matrices.method_22907(class_7833.field_40716.rotationDegrees(camera.method_19330() + 180.0F));
         matrices.method_22904(-cameraPos.field_1352, -cameraPos.field_1351, -cameraPos.field_1350);
         Iterator var6 = this.anomalyChunks.entrySet().iterator();

         while(var6.hasNext()) {
            Entry<class_1923, dp> entry = (Entry)var6.next();
            class_1923 chunkPos = (class_1923)entry.getKey();
            double distSq = playerPos.method_1028((double)chunkPos.method_8326() + 8.0D, playerPos.field_1351, (double)chunkPos.method_8328() + 8.0D);
            if (!(distSq > 65536.0D)) {
               int startX = chunkPos.method_8326();
               int startZ = chunkPos.method_8328();
               class_238 chunkBox = new class_238((double)startX, 60.0D, (double)startZ, (double)(startX + (206 ^ 222)), 61.0D, (double)(startZ + (59 ^ 43)));
               Color chunkColor = new Color(ANOMALY_COLOR.getRed(), ANOMALY_COLOR.getGreen(), ANOMALY_COLOR.getBlue(), (int)this.chunkOpacity.getValue());
               this.renderBox(matrices, chunkBox, chunkColor, OUTLINE_COLOR);
            }
         }

         matrices.method_22909();
      }
   }

   private void renderBox(class_4587 matrices, class_238 box, Color color, Color outlineColor) {
      if (matrices != null && box != null && color != null) {
         iq.renderFilledBox(matrices, (float)box.field_1323, (float)box.field_1322, (float)box.field_1321, (float)box.field_1320, (float)box.field_1325, (float)box.field_1324, color);
         if (outlineColor != null) {
            class_4184 camera = bf.mc.field_1773.method_19418();
            class_243 cameraPos = camera.method_19326();
            iq.renderLine(matrices, outlineColor, new class_243(box.field_1323 - cameraPos.field_1352, box.field_1322 - cameraPos.field_1351, box.field_1321 - cameraPos.field_1350), new class_243(box.field_1320 - cameraPos.field_1352, box.field_1322 - cameraPos.field_1351, box.field_1321 - cameraPos.field_1350));
            iq.renderLine(matrices, outlineColor, new class_243(box.field_1320 - cameraPos.field_1352, box.field_1322 - cameraPos.field_1351, box.field_1321 - cameraPos.field_1350), new class_243(box.field_1320 - cameraPos.field_1352, box.field_1322 - cameraPos.field_1351, box.field_1324 - cameraPos.field_1350));
            iq.renderLine(matrices, outlineColor, new class_243(box.field_1320 - cameraPos.field_1352, box.field_1322 - cameraPos.field_1351, box.field_1324 - cameraPos.field_1350), new class_243(box.field_1323 - cameraPos.field_1352, box.field_1322 - cameraPos.field_1351, box.field_1324 - cameraPos.field_1350));
            iq.renderLine(matrices, outlineColor, new class_243(box.field_1323 - cameraPos.field_1352, box.field_1322 - cameraPos.field_1351, box.field_1324 - cameraPos.field_1350), new class_243(box.field_1323 - cameraPos.field_1352, box.field_1322 - cameraPos.field_1351, box.field_1321 - cameraPos.field_1350));
            iq.renderLine(matrices, outlineColor, new class_243(box.field_1323 - cameraPos.field_1352, box.field_1322 - cameraPos.field_1351, box.field_1321 - cameraPos.field_1350), new class_243(box.field_1323 - cameraPos.field_1352, box.field_1325 - cameraPos.field_1351, box.field_1321 - cameraPos.field_1350));
            iq.renderLine(matrices, outlineColor, new class_243(box.field_1320 - cameraPos.field_1352, box.field_1322 - cameraPos.field_1351, box.field_1321 - cameraPos.field_1350), new class_243(box.field_1320 - cameraPos.field_1352, box.field_1325 - cameraPos.field_1351, box.field_1321 - cameraPos.field_1350));
            iq.renderLine(matrices, outlineColor, new class_243(box.field_1320 - cameraPos.field_1352, box.field_1322 - cameraPos.field_1351, box.field_1324 - cameraPos.field_1350), new class_243(box.field_1320 - cameraPos.field_1352, box.field_1325 - cameraPos.field_1351, box.field_1324 - cameraPos.field_1350));
            iq.renderLine(matrices, outlineColor, new class_243(box.field_1323 - cameraPos.field_1352, box.field_1322 - cameraPos.field_1351, box.field_1324 - cameraPos.field_1350), new class_243(box.field_1323 - cameraPos.field_1352, box.field_1325 - cameraPos.field_1351, box.field_1324 - cameraPos.field_1350));
            iq.renderLine(matrices, outlineColor, new class_243(box.field_1323 - cameraPos.field_1352, box.field_1325 - cameraPos.field_1351, box.field_1321 - cameraPos.field_1350), new class_243(box.field_1320 - cameraPos.field_1352, box.field_1325 - cameraPos.field_1351, box.field_1321 - cameraPos.field_1350));
            iq.renderLine(matrices, outlineColor, new class_243(box.field_1320 - cameraPos.field_1352, box.field_1325 - cameraPos.field_1351, box.field_1324 - cameraPos.field_1350), new class_243(box.field_1323 - cameraPos.field_1352, box.field_1325 - cameraPos.field_1351, box.field_1324 - cameraPos.field_1350));
            iq.renderLine(matrices, outlineColor, new class_243(box.field_1323 - cameraPos.field_1352, box.field_1325 - cameraPos.field_1351, box.field_1324 - cameraPos.field_1350), new class_243(box.field_1323 - cameraPos.field_1352, box.field_1325 - cameraPos.field_1351, box.field_1321 - cameraPos.field_1350));
         }

      }
   }

   // $FF: synthetic method
   private static String d504(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 9247 + var3 & (121 ^ 134));
      }

      return new String(var2);
   }
}
