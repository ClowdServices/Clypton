import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import net.minecraft.class_1923;
import net.minecraft.class_1944;
import net.minecraft.class_2338;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_2818;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_7833;
import net.minecraft.class_2902.class_2903;

public final class em extends bf {
   private final gn chunkRadius = new gn(db.of(d241("OBQIEBSg0+Pn7fD1")), 1.0D, 8.0D, 2.0D, 1.0D);
   private final gn minY = new gn(db.of(d241("NhUTXiY=")), -64.0D, 319.0D, -63.0D, 1.0D);
   private final gn maxY = new gn(db.of(d241("Nh0FXiY=")), -64.0D, 319.0D, 0.0D, 1.0D);
   private final gn minLightLevel = new gn(db.of(d241("NhUTXjPp5ur3pMnj8e3l")), 0.0D, 15.0D, 5.0D, 1.0D);
   private final gn maxLightLevel = new gn(db.of(d241("Nh0FXjPp5ur3pMnj8e3l")), 0.0D, 15.0D, 15.0D, 1.0D);
   private final ff onlySurface = new ff(db.of(d241("NBIRB1/T9PDl5ebj")), true);
   private final ff blockLightOnly = new ff(db.of(d241("ORASHRSgzevk7PGmyObl8w==")), true);
   private final ff ignoreLowest = new ff(db.of(d241("MhsTEQ3loc7s8+D18w==")), false);
   private final Map<class_2338, Integer> lightLevels = new ConcurrentHashMap();
   private final Set<class_1923> scannedChunks = ConcurrentHashMap.newKeySet();
   private volatile class_1923 lastPlayerChunk = null;
   private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor((r) -> {
      Thread t = new Thread(r, d241("NxUaFgvE5OD246jV5Onn5O7+"));
      t.setDaemon(true);
      return t;
   });
   private ScheduledFuture<?> scanTask;
   private static final Color[] LIGHT_COLORS = new Color[]{new Color(200, 17 ^ 217, 234 ^ 34, 80), new Color(220, 175 ^ 27, 255, 100), new Color(180, 120, 255, 130), new Color(255, 255, 255, 180)};

   public em() {
      super(db.of(d241("NxUaFgvE5OD24w==")), db.of(d241("PxkfCxig4OznpO3v4ODl4+zk+a7j+fb657Tm+eLq+v/o")), -1, hk.RENDER);
      ab[] var10001 = new ab[111 ^ 103];
      var10001[0] = this.chunkRadius;
      var10001[1] = this.minY;
      var10001[2] = this.maxY;
      var10001[3] = this.minLightLevel;
      var10001[4] = this.maxLightLevel;
      var10001[5] = this.onlySurface;
      var10001[6] = this.blockLightOnly;
      var10001[7] = this.ignoreLowest;
      this.addSettings(var10001);
   }

   public void onEnable() {
      super.onEnable();
      this.lightLevels.clear();
      this.scannedChunks.clear();
      this.lastPlayerChunk = null;
      if (this.scanTask == null || this.scanTask.isCancelled()) {
         this.scanTask = this.executor.scheduleAtFixedRate(this::scanLights, 0L, 500L, TimeUnit.MILLISECONDS);
      }

   }

   public void onDisable() {
      super.onDisable();
      if (this.scanTask != null) {
         this.scanTask.cancel(false);
         this.scanTask = null;
      }

      this.lightLevels.clear();
      this.scannedChunks.clear();
      this.lastPlayerChunk = null;
   }

   private void scanLights() {
      if (mc != null && mc.field_1724 != null && mc.field_1687 != null) {
         try {
            class_1923 playerChunkPos = mc.field_1724.method_31476();
            int radius = (int)this.chunkRadius.getValue();
            boolean playerMovedChunk = !playerChunkPos.equals(this.lastPlayerChunk);
            if (playerMovedChunk) {
               this.lastPlayerChunk = playerChunkPos;
               this.cleanupDistantChunks(playerChunkPos, radius);
            }

            Set<class_1923> chunksToScan = new HashSet();

            int minYLevel;
            int maxYLevel;
            for(minYLevel = -radius; minYLevel <= radius; ++minYLevel) {
               for(maxYLevel = -radius; maxYLevel <= radius; ++maxYLevel) {
                  class_1923 chunkPos = new class_1923(playerChunkPos.field_9181 + minYLevel, playerChunkPos.field_9180 + maxYLevel);
                  if (!this.scannedChunks.contains(chunkPos)) {
                     chunksToScan.add(chunkPos);
                  }
               }
            }

            if (!chunksToScan.isEmpty()) {
               minYLevel = (int)this.minY.getValue();
               maxYLevel = (int)this.maxY.getValue();
               int minLight = (int)this.minLightLevel.getValue();
               int maxLight = (int)this.maxLightLevel.getValue();
               Iterator var9 = chunksToScan.iterator();

               while(var9.hasNext()) {
                  class_1923 chunkPos = (class_1923)var9.next();
                  this.scanChunk(chunkPos, minYLevel, maxYLevel, minLight, maxLight);
                  this.scannedChunks.add(chunkPos);
               }
            }
         } catch (Exception var11) {
         }

      }
   }

   private void cleanupDistantChunks(class_1923 playerChunk, int radius) {
      Iterator iterator = this.scannedChunks.iterator();

      while(true) {
         class_1923 chunk;
         int dx;
         int dz;
         do {
            if (!iterator.hasNext()) {
               return;
            }

            chunk = (class_1923)iterator.next();
            dx = Math.abs(chunk.field_9181 - playerChunk.field_9181);
            dz = Math.abs(chunk.field_9180 - playerChunk.field_9180);
         } while(dx <= radius && dz <= radius);

         this.lightLevels.keySet().removeIf((pos) -> {
            class_1923 blockChunk = new class_1923(pos);
            return blockChunk.equals(chunk);
         });
         iterator.remove();
      }
   }

   private void scanChunk(class_1923 chunkPos, int minYLevel, int maxYLevel, int minLight, int maxLight) {
      if (mc.field_1687 != null) {
         try {
            class_2818 chunk = mc.field_1687.method_8497(chunkPos.field_9181, chunkPos.field_9180);
            if (chunk == null) {
               return;
            }

            int startX = chunkPos.field_9181 << 4;
            int startZ = chunkPos.field_9180 << 4;
            Map<class_2338, Integer> chunkLights = new HashMap();

            for(int x = 0; x < 16; ++x) {
               for(int z = 0; z < 16; ++z) {
                  int highestY = chunk.method_12032(class_2903.field_13202).method_12603(x, z);
                  int scanMaxY = Math.min(maxYLevel, highestY + 5);

                  for(int y = minYLevel; y <= scanMaxY; ++y) {
                     class_2338 pos = new class_2338(startX + x, y, startZ + z);
                     int blockLight = mc.field_1687.method_8314(class_1944.field_9282, pos);
                     int skyLight = mc.field_1687.method_8314(class_1944.field_9284, pos);
                     int lightLevel;
                     if (this.blockLightOnly.getValue()) {
                        if (blockLight < minLight || blockLight > maxLight || blockLight == 0 || this.ignoreLowest.getValue() && blockLight == minLight) {
                           continue;
                        }

                        lightLevel = blockLight;
                     } else {
                        int totalLight = Math.max(blockLight, skyLight);
                        if (totalLight < minLight || totalLight > maxLight || this.ignoreLowest.getValue() && totalLight == minLight) {
                           continue;
                        }

                        lightLevel = totalLight;
                     }

                     chunkLights.put(pos, lightLevel);
                  }
               }
            }

            if (this.onlySurface.getValue()) {
               chunkLights.keySet().removeIf((posx) -> {
                  return !this.shouldRenderBlock(posx, chunkLights);
               });
            }

            this.lightLevels.putAll(chunkLights);
         } catch (Exception var20) {
         }

      }
   }

   private boolean shouldRenderBlock(class_2338 pos, Map<class_2338, Integer> allLights) {
      if (mc.field_1687 == null) {
         return false;
      } else {
         return !allLights.containsKey(pos.method_10074()) || !allLights.containsKey(pos.method_10084()) || !allLights.containsKey(pos.method_10095()) || !allLights.containsKey(pos.method_10072()) || !allLights.containsKey(pos.method_10067()) || !allLights.containsKey(pos.method_10078());
      }
   }

   @cp
   public void onRender3D(bg event) {
      if (mc != null && mc.field_1724 != null && mc.field_1687 != null) {
         if (!this.lightLevels.isEmpty()) {
            RenderSystem.enableDepthTest();
            RenderSystem.disableCull();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            class_4184 cam = iq.getCamera();
            if (cam != null) {
               class_243 camPos = iq.getCameraPos();
               class_4587 matrices = event.matrixStack;
               matrices.method_22903();
               matrices.method_22907(class_7833.field_40714.rotationDegrees(cam.method_19329()));
               matrices.method_22907(class_7833.field_40716.rotationDegrees(cam.method_19330() + 180.0F));
               matrices.method_22904(-camPos.field_1352, -camPos.field_1351, -camPos.field_1350);
               int rendered = 0;
               int maxBoxes = 500;
               Iterator var7 = this.lightLevels.entrySet().iterator();

               while(var7.hasNext()) {
                  Entry<class_2338, Integer> entry = (Entry)var7.next();
                  if (rendered++ >= maxBoxes) {
                     break;
                  }

                  class_2338 pos = (class_2338)entry.getKey();
                  int lightLevel = (Integer)entry.getValue();
                  Color color = this.getColorForLight(lightLevel);
                  Color outlineColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), 255);
                  class_238 box = new class_238((double)pos.method_10263(), (double)pos.method_10264(), (double)pos.method_10260(), (double)(pos.method_10263() + 1), (double)(pos.method_10264() + 1), (double)(pos.method_10260() + 1));
                  iq.renderFilledBox(matrices, (float)box.field_1323, (float)box.field_1322, (float)box.field_1321, (float)box.field_1320, (float)box.field_1325, (float)box.field_1324, color);
                  this.renderBoxOutline(matrices, box, outlineColor);
               }

               matrices.method_22909();
               RenderSystem.disableBlend();
               RenderSystem.enableCull();
               RenderSystem.enableDepthTest();
            }
         }
      }
   }

   private Color getColorForLight(int lightLevel) {
      if (lightLevel <= 7) {
         return LIGHT_COLORS[0];
      } else if (lightLevel <= 10) {
         return LIGHT_COLORS[1];
      } else {
         return lightLevel <= 13 ? LIGHT_COLORS[2] : LIGHT_COLORS[3];
      }
   }

   private void renderBoxOutline(class_4587 matrices, class_238 box, Color color) {
      iq.renderLine(matrices, color, new class_243(box.field_1323, box.field_1322, box.field_1321), new class_243(box.field_1320, box.field_1322, box.field_1321));
      iq.renderLine(matrices, color, new class_243(box.field_1320, box.field_1322, box.field_1321), new class_243(box.field_1320, box.field_1322, box.field_1324));
      iq.renderLine(matrices, color, new class_243(box.field_1320, box.field_1322, box.field_1324), new class_243(box.field_1323, box.field_1322, box.field_1324));
      iq.renderLine(matrices, color, new class_243(box.field_1323, box.field_1322, box.field_1324), new class_243(box.field_1323, box.field_1322, box.field_1321));
      iq.renderLine(matrices, color, new class_243(box.field_1323, box.field_1322, box.field_1321), new class_243(box.field_1323, box.field_1325, box.field_1321));
      iq.renderLine(matrices, color, new class_243(box.field_1320, box.field_1322, box.field_1321), new class_243(box.field_1320, box.field_1325, box.field_1321));
      iq.renderLine(matrices, color, new class_243(box.field_1320, box.field_1322, box.field_1324), new class_243(box.field_1320, box.field_1325, box.field_1324));
      iq.renderLine(matrices, color, new class_243(box.field_1323, box.field_1322, box.field_1324), new class_243(box.field_1323, box.field_1325, box.field_1324));
      iq.renderLine(matrices, color, new class_243(box.field_1323, box.field_1325, box.field_1321), new class_243(box.field_1320, box.field_1325, box.field_1321));
      iq.renderLine(matrices, color, new class_243(box.field_1320, box.field_1325, box.field_1321), new class_243(box.field_1320, box.field_1325, box.field_1324));
      iq.renderLine(matrices, color, new class_243(box.field_1320, box.field_1325, box.field_1324), new class_243(box.field_1323, box.field_1325, box.field_1324));
      iq.renderLine(matrices, color, new class_243(box.field_1323, box.field_1325, box.field_1324), new class_243(box.field_1323, box.field_1325, box.field_1321));
   }

   // $FF: synthetic method
   private static String d241(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 9384 - 557 + var3 & 255);
      }

      return new String(var2);
   }
}
