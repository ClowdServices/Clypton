import java.awt.Color;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_259;
import net.minecraft.class_265;
import net.minecraft.class_2680;
import net.minecraft.class_2818;
import net.minecraft.class_2826;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_7833;
import net.minecraft.class_2338.class_2339;
import net.minecraft.class_2350.class_2351;

public final class dz extends bf {
   private final gn minDepth = new gn(db.of(d538("/NvdlPHTx8zR")), 1.0D, 20.0D, 4.0D, 1.0D);
   private final gn alpha = new gn(db.of(d538("8N7D3NQ=")), 1.0D, 255.0D, 95.0D, 5.0D);
   private final ff airOnly = new ff(db.of(d538("8NvBlPfa2NvSyZvz09LG")), false);
   private final ff detect1x1 = new ff(db.of(d538("9dfH0dbCl4nBi5v00tLasw==")), true);
   private final ff detect3x1 = new ff(db.of(d538("9dfH0dbCl4vBi5v00tLasw==")), true);
   private final ff detectTunnels = new ff(db.of(d538("9dfH0dbCl+zM1NXZ0c0=")), true);
   private final ff detectDiagonals = new ff(db.of(d538("9dfH0dbCl/zQ29zT09/T4JW3raqgqrQ=")), true);
   private final ff detectStaircases = new ff(db.of(d538("9dfH0dbCl+vN29LO3t/MpbI=")), true);
   private final gn minTunnelLen = new gn(db.of(d538("/NvdlOHD2dbc1pvw2NA=")), 1.0D, 20.0D, 3.0D, 1.0D);
   private final gn minTunnelHeight = new gn(db.of(d538("/NvdlOHD2dbc1pv02NfYqLU=")), 1.0D, 5.0D, 2.0D, 1.0D);
   private final gn maxTunnelHeight = new gn(db.of(d538("/NPLlOHD2dbc1pv02NfYqLU=")), 2.0D, 10.0D, 3.0D, 1.0D);
   private final gn minStairLen = new gn(db.of(d538("/NvdlObC1tHLmvfZ0w==")), 1.0D, 20.0D, 3.0D, 1.0D);
   private final Set<class_238> holes1x1 = Collections.newSetFromMap(new ConcurrentHashMap());
   private final Set<class_238> holes3x1 = Collections.newSetFromMap(new ConcurrentHashMap());
   private final Set<class_238> tunnels = Collections.newSetFromMap(new ConcurrentHashMap());
   private final Set<class_238> staircases = Collections.newSetFromMap(new ConcurrentHashMap());
   private final Set<Long> processedChunks = Collections.newSetFromMap(new ConcurrentHashMap());
   private static final class_2350[] DIRECTIONS;
   private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor((r) -> {
      Thread t = new Thread(r, d538("+d3f0fDl55Xq2drS09vN"));
      t.setDaemon(true);
      return t;
   });
   private ScheduledFuture<?> scanTask;

   public dz() {
      super(db.of(d538("+d3f0fDl5w==")), db.of(d538("+dvU3Nnf0NDNyZuNxY+T4PK68uStqautuubruLigobW9ofO1u7L3q627sq6+v6yFkg==")), -1, hk.RENDER);
      ab[] var10001 = new ab[]{this.minDepth, this.alpha, this.airOnly, this.detect1x1, this.detect3x1, this.detectTunnels, this.detectDiagonals, this.detectStaircases, this.minTunnelLen, this.minTunnelHeight, this.maxTunnelHeight, null};
      var10001[195 ^ 200] = this.minStairLen;
      this.addSettings(var10001);
   }

   public void onEnable() {
      super.onEnable();
      this.clearHoles();
      this.processedChunks.clear();
      if (this.scanTask == null || this.scanTask.isCancelled()) {
         this.scanTask = this.executor.scheduleAtFixedRate(this::scanChunks, 0L, 500L, TimeUnit.MILLISECONDS);
      }

   }

   public void onDisable() {
      super.onDisable();
      if (this.scanTask != null) {
         this.scanTask.cancel(false);
         this.scanTask = null;
      }

      this.clearHoles();
      this.processedChunks.clear();
   }

   private void clearHoles() {
      this.holes1x1.clear();
      this.holes3x1.clear();
      this.tunnels.clear();
      this.staircases.clear();
   }

   private void scanChunks() {
      if (mc != null && mc.field_1724 != null && mc.field_1687 != null) {
         try {
            List<class_2818> chunks = ft.getLoadedChunks().toList();
            Set<Long> currentChunkKeys = new HashSet();
            Iterator var3 = chunks.iterator();

            while(var3.hasNext()) {
               class_2818 chunk = (class_2818)var3.next();
               long key = this.getChunkKey(chunk.method_12004().field_9181, chunk.method_12004().field_9180);
               currentChunkKeys.add(key);
               if (!this.processedChunks.contains(key)) {
                  this.searchChunk(chunk);
                  this.processedChunks.add(key);
               }
            }

            this.processedChunks.retainAll(currentChunkKeys);
            this.removeBoxesOutsideChunks(currentChunkKeys);
         } catch (Exception var7) {
         }

      }
   }

   private void searchChunk(class_2818 chunk) {
      class_2826[] sections = chunk.method_12006();
      int worldBottom = mc.field_1687.method_31607();
      Set<class_2338> checked = new HashSet();

      for(int sectionIndex = 0; sectionIndex < sections.length; ++sectionIndex) {
         class_2826 section = sections[sectionIndex];
         if (section != null && !section.method_38292()) {
            int sectionY = worldBottom + sectionIndex * 16;

            for(int x = 0; x < (240 ^ 224); ++x) {
               for(int z = 0; z < 16; ++z) {
                  for(int y = 0; y < 16; ++y) {
                     int worldY = sectionY + y;
                     class_2338 pos = chunk.method_12004().method_35231(x, worldY, z);
                     if (!checked.contains(pos) && this.isPassableBlock(pos)) {
                        if (this.detect1x1.getValue()) {
                           this.checkHole1x1(pos, checked);
                        }

                        if (this.detect3x1.getValue()) {
                           this.checkHole3x1(pos, checked);
                        }

                        if (this.detectTunnels.getValue()) {
                           this.checkTunnel(pos, checked);
                           if (this.detectDiagonals.getValue()) {
                              this.checkDiagonalTunnel(pos, checked);
                           }
                        }

                        if (this.detectStaircases.getValue()) {
                           this.checkStaircase(pos, checked);
                        }
                     }
                  }
               }
            }
         }
      }

   }

   private void checkHole1x1(class_2338 pos, Set<class_2338> checked) {
      if (this.isValidHoleSection1x1(pos)) {
         class_2339 currentPos = pos.method_25503();

         while(this.isValidHoleSection1x1(currentPos)) {
            checked.add(currentPos.method_10062());
            currentPos.method_10098(class_2350.field_11036);
         }

         int depth = currentPos.method_10264() - pos.method_10264();
         if (depth >= this.minDepth.getIntValue()) {
            class_238 holeBox = new class_238((double)pos.method_10263(), (double)pos.method_10264(), (double)pos.method_10260(), (double)(pos.method_10263() + 1), (double)currentPos.method_10264(), (double)(pos.method_10260() + 1));
            if (this.holes1x1.stream().noneMatch((h) -> {
               return h.method_994(holeBox);
            })) {
               this.holes1x1.add(holeBox);
            }
         }

      }
   }

   private void checkHole3x1(class_2338 pos, Set<class_2338> checked) {
      class_2339 currentPos;
      int depth;
      class_238 holeBox;
      if (this.isValid3x1HoleSectionX(pos)) {
         currentPos = pos.method_25503();

         while(this.isValid3x1HoleSectionX(currentPos)) {
            checked.add(currentPos.method_10062());
            checked.add(currentPos.method_10078().method_10062());
            checked.add(currentPos.method_10089(2).method_10062());
            currentPos.method_10098(class_2350.field_11036);
         }

         depth = currentPos.method_10264() - pos.method_10264();
         if (depth >= this.minDepth.getIntValue()) {
            holeBox = new class_238((double)pos.method_10263(), (double)pos.method_10264(), (double)pos.method_10260(), (double)(pos.method_10263() + 3), (double)currentPos.method_10264(), (double)(pos.method_10260() + 1));
            if (this.holes3x1.stream().noneMatch((h) -> {
               return h.method_994(holeBox);
            })) {
               this.holes3x1.add(holeBox);
            }
         }
      }

      if (this.isValid3x1HoleSectionZ(pos)) {
         currentPos = pos.method_25503();

         while(this.isValid3x1HoleSectionZ(currentPos)) {
            checked.add(currentPos.method_10062());
            checked.add(currentPos.method_10072().method_10062());
            checked.add(currentPos.method_10077(2).method_10062());
            currentPos.method_10098(class_2350.field_11036);
         }

         depth = currentPos.method_10264() - pos.method_10264();
         if (depth >= this.minDepth.getIntValue()) {
            holeBox = new class_238((double)pos.method_10263(), (double)pos.method_10264(), (double)pos.method_10260(), (double)(pos.method_10263() + 1), (double)currentPos.method_10264(), (double)(pos.method_10260() + 3));
            if (this.holes3x1.stream().noneMatch((h) -> {
               return h.method_994(holeBox);
            })) {
               this.holes3x1.add(holeBox);
            }
         }
      }

   }

   private void checkTunnel(class_2338 pos, Set<class_2338> checked) {
      class_2350[] var3 = DIRECTIONS;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         class_2350 dir = var3[var5];
         class_2339 currentPos = pos.method_25503();
         int stepCount = 0;
         class_2338 startPos = null;
         class_2338 endPos = null;

         int maxHeight;
         for(maxHeight = 0; this.isTunnelSection(currentPos, dir); ++stepCount) {
            if (startPos == null) {
               startPos = currentPos.method_10062();
            }

            int h = this.getPassableHeight(currentPos, this.maxTunnelHeight.getIntValue());
            maxHeight = Math.max(maxHeight, h);

            for(int i = 0; i < h; ++i) {
               checked.add(currentPos.method_10086(i).method_10062());
            }

            endPos = currentPos.method_10062();
            currentPos.method_10098(dir);
         }

         if (stepCount >= this.minTunnelLen.getIntValue() && maxHeight >= this.minTunnelHeight.getIntValue() && maxHeight <= this.maxTunnelHeight.getIntValue()) {
            class_238 tunnelBox = new class_238((double)Math.min(startPos.method_10263(), endPos.method_10263()), (double)startPos.method_10264(), (double)Math.min(startPos.method_10260(), endPos.method_10260()), (double)(Math.max(startPos.method_10263(), endPos.method_10263()) + 1), (double)(startPos.method_10264() + maxHeight), (double)(Math.max(startPos.method_10260(), endPos.method_10260()) + 1));
            if (this.tunnels.stream().noneMatch((hx) -> {
               return hx.method_994(tunnelBox);
            })) {
               this.tunnels.add(tunnelBox);
            }
         }
      }

   }

   private void checkDiagonalTunnel(class_2338 pos, Set<class_2338> checked) {
      class_2350[] var3 = DIRECTIONS;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         class_2350 dir = var3[var5];
         class_2339 currentPos = pos.method_25503();
         int stepCount = 0;
         List<class_238> potentialBoxes = new ArrayList();
         class_2350 checkingDir = dir;

         class_238 box;
         for(boolean turnRight = true; this.isDiagonalTunnelSection(currentPos, checkingDir); ++stepCount) {
            int h = this.getPassableHeight(currentPos, this.maxTunnelHeight.getIntValue());
            box = new class_238((double)currentPos.method_10263(), (double)currentPos.method_10264(), (double)currentPos.method_10260(), (double)(currentPos.method_10263() + 1), (double)(currentPos.method_10264() + h), (double)(currentPos.method_10260() + 1));
            potentialBoxes.add(box);

            for(int i = 0; i < h; ++i) {
               checked.add(currentPos.method_10086(i).method_10062());
            }

            if (turnRight) {
               checkingDir = checkingDir.method_10170();
               currentPos.method_10098(checkingDir);
               turnRight = false;
            } else {
               checkingDir = checkingDir.method_10160();
               currentPos.method_10098(checkingDir);
               turnRight = true;
            }
         }

         if (stepCount >= this.minTunnelLen.getIntValue()) {
            Iterator var15 = potentialBoxes.iterator();

            while(var15.hasNext()) {
               box = (class_238)var15.next();
               if (this.tunnels.stream().noneMatch((hx) -> {
                  return hx.method_994(box);
               })) {
                  this.tunnels.add(box);
               }
            }
         }
      }

   }

   private void checkStaircase(class_2338 pos, Set<class_2338> checked) {
      class_2350[] var3 = DIRECTIONS;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         class_2350 dir = var3[var5];
         class_2339 currentPos = pos.method_25503();
         int stepCount = 0;

         ArrayList potentialBoxes;
         class_238 box;
         for(potentialBoxes = new ArrayList(); this.isStaircaseSection(currentPos, dir); ++stepCount) {
            int h = this.getPassableHeight(currentPos, 5);
            box = new class_238((double)currentPos.method_10263(), (double)currentPos.method_10264(), (double)currentPos.method_10260(), (double)(currentPos.method_10263() + 1), (double)(currentPos.method_10264() + h), (double)(currentPos.method_10260() + 1));
            potentialBoxes.add(box);

            for(int i = 0; i < h; ++i) {
               checked.add(currentPos.method_10086(i).method_10062());
            }

            currentPos.method_10098(dir);
            currentPos.method_10098(class_2350.field_11036);
         }

         if (stepCount >= this.minStairLen.getIntValue()) {
            Iterator var13 = potentialBoxes.iterator();

            while(var13.hasNext()) {
               box = (class_238)var13.next();
               if (this.staircases.stream().noneMatch((hx) -> {
                  return hx.method_994(box);
               })) {
                  this.staircases.add(box);
               }
            }
         }
      }

   }

   private boolean isTunnelSection(class_2338 pos, class_2350 dir) {
      int height = this.getPassableHeight(pos, this.maxTunnelHeight.getIntValue());
      if (height >= this.minTunnelHeight.getIntValue() && height <= this.maxTunnelHeight.getIntValue()) {
         if (!this.isPassableBlock(pos.method_10074()) && !this.isPassableBlock(pos.method_10086(height))) {
            class_2350[] perpDirs = dir.method_10166() == class_2351.field_11048 ? new class_2350[]{class_2350.field_11043, class_2350.field_11035} : new class_2350[]{class_2350.field_11034, class_2350.field_11039};
            class_2350[] var5 = perpDirs;
            int var6 = perpDirs.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               class_2350 perpDir = var5[var7];

               for(int i = 0; i < height; ++i) {
                  if (this.isPassableBlock(pos.method_10086(i).method_10093(perpDir))) {
                     return false;
                  }
               }
            }

            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private boolean isDiagonalTunnelSection(class_2338 pos, class_2350 dir) {
      int height = this.getPassableHeight(pos, this.maxTunnelHeight.getIntValue());
      if (height >= this.minTunnelHeight.getIntValue() && height <= this.maxTunnelHeight.getIntValue()) {
         if (!this.isPassableBlock(pos.method_10074()) && !this.isPassableBlock(pos.method_10086(height))) {
            for(int i = 0; i < height; ++i) {
               if (this.isPassableBlock(pos.method_10086(i).method_10093(dir))) {
                  return false;
               }
            }

            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private boolean isStaircaseSection(class_2338 pos, class_2350 dir) {
      int height = this.getPassableHeight(pos, 5);
      if (height < 2) {
         return false;
      } else if (!this.isPassableBlock(pos.method_10074()) && !this.isPassableBlock(pos.method_10086(height))) {
         class_2350[] perpDirs = dir.method_10166() == class_2351.field_11048 ? new class_2350[]{class_2350.field_11043, class_2350.field_11035} : new class_2350[]{class_2350.field_11034, class_2350.field_11039};
         class_2350[] var5 = perpDirs;
         int var6 = perpDirs.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            class_2350 perpDir = var5[var7];

            for(int i = 0; i < height; ++i) {
               if (this.isPassableBlock(pos.method_10086(i).method_10093(perpDir))) {
                  return false;
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private int getPassableHeight(class_2338 pos, int max) {
      int height;
      for(height = 0; this.isPassableBlock(pos.method_10086(height)) && height < max; ++height) {
      }

      return height;
   }

   private boolean isValidHoleSection1x1(class_2338 pos) {
      return this.isPassableBlock(pos) && !this.isPassableBlock(pos.method_10095()) && !this.isPassableBlock(pos.method_10072()) && !this.isPassableBlock(pos.method_10078()) && !this.isPassableBlock(pos.method_10067());
   }

   private boolean isValid3x1HoleSectionX(class_2338 pos) {
      return this.isPassableBlock(pos) && this.isPassableBlock(pos.method_10078()) && this.isPassableBlock(pos.method_10089(2)) && !this.isPassableBlock(pos.method_10095()) && !this.isPassableBlock(pos.method_10072()) && !this.isPassableBlock(pos.method_10089(3)) && !this.isPassableBlock(pos.method_10067()) && !this.isPassableBlock(pos.method_10078().method_10095()) && !this.isPassableBlock(pos.method_10078().method_10072()) && !this.isPassableBlock(pos.method_10089(2).method_10095()) && !this.isPassableBlock(pos.method_10089(2).method_10072());
   }

   private boolean isValid3x1HoleSectionZ(class_2338 pos) {
      return this.isPassableBlock(pos) && this.isPassableBlock(pos.method_10072()) && this.isPassableBlock(pos.method_10077(2)) && !this.isPassableBlock(pos.method_10078()) && !this.isPassableBlock(pos.method_10067()) && !this.isPassableBlock(pos.method_10077(3)) && !this.isPassableBlock(pos.method_10095()) && !this.isPassableBlock(pos.method_10072().method_10078()) && !this.isPassableBlock(pos.method_10072().method_10067()) && !this.isPassableBlock(pos.method_10077(2).method_10078()) && !this.isPassableBlock(pos.method_10077(2).method_10067());
   }

   private boolean isPassableBlock(class_2338 pos) {
      if (mc.field_1687 == null) {
         return false;
      } else {
         class_2680 state = mc.field_1687.method_8320(pos);
         if (this.airOnly.getValue()) {
            return state.method_26215();
         } else {
            class_265 shape = state.method_26220(mc.field_1687, pos);
            return shape.method_1110() || !class_259.method_1077().equals(shape);
         }
      }
   }

   private void removeBoxesOutsideChunks(Set<Long> currentChunkKeys) {
      this.holes1x1.removeIf((box) -> {
         return !this.isBoxInChunks(box, currentChunkKeys);
      });
      this.holes3x1.removeIf((box) -> {
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
      return (long)x << 32 | (long)z & 4294967295L;
   }

   @cp
   public void onRender3D(bg event) {
      if (mc != null && mc.field_1724 != null && mc.field_1687 != null) {
         class_4184 cam = iq.getCamera();
         if (cam != null) {
            class_243 camPos = iq.getCameraPos();
            class_4587 matrices = event.matrixStack;
            matrices.method_22903();
            matrices.method_22907(class_7833.field_40714.rotationDegrees(cam.method_19329()));
            matrices.method_22907(class_7833.field_40716.rotationDegrees(cam.method_19330() + 180.0F));
            matrices.method_22904(-camPos.field_1352, -camPos.field_1351, -camPos.field_1350);
            int alphaValue = this.alpha.getIntValue();
            Color color3x1;
            Iterator var7;
            class_238 box;
            if (this.detect1x1.getValue()) {
               color3x1 = ch.safeColor(150, 150, 150, alphaValue);
               var7 = this.holes1x1.iterator();

               while(var7.hasNext()) {
                  box = (class_238)var7.next();
                  iq.renderFilledBox(matrices, (float)box.field_1323, (float)box.field_1322, (float)box.field_1321, (float)box.field_1320, (float)box.field_1325, (float)box.field_1324, color3x1);
               }
            }

            if (this.detect3x1.getValue()) {
               color3x1 = ch.safeColor(150, 206 ^ 88, 150, alphaValue);
               var7 = this.holes3x1.iterator();

               while(var7.hasNext()) {
                  box = (class_238)var7.next();
                  iq.renderFilledBox(matrices, (float)box.field_1323, (float)box.field_1322, (float)box.field_1321, (float)box.field_1320, (float)box.field_1325, (float)box.field_1324, color3x1);
               }
            }

            matrices.method_22909();
         }
      }
   }

   static {
      DIRECTIONS = new class_2350[]{class_2350.field_11034, class_2350.field_11039, class_2350.field_11043, class_2350.field_11035};
   }

   // $FF: synthetic method
   private static String d538(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 8537 - 936 + var3 & (20 ^ 235));
      }

      return new String(var2);
   }
}
