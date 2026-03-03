import java.awt.Color;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.class_1923;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_243;
import net.minecraft.class_2680;
import net.minecraft.class_2818;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_7833;

public final class fa extends bf {
   private final gn alpha = new gn(db.of(d369("v5NwaWM=")), 1.0D, 255.0D, 125.0D, 1.0D);
   private final ff tracers = new ff(db.of(d369("qo1hYmdxdw==")), false);
   private final gn renderDistance = new gn(db.of(d369("rJpuZWdxJEFvdHxoZGhp")), 1.0D, 10.0D, 3.0D, 1.0D);
   private final ff useSeed = new ff(db.of(d369("q4xlIVFmYWE=")), false);
   private final bu seed = new bu(db.of(d369("rZplZQ==")), "0");
   private final gn updateDelay = new gn(db.of(d369("q49kYHZmJEFja2lwKiN4ZG1kYzg=")), 1.0D, 100.0D, 5.0D, 1.0D);
   private final ff diamondOre = new ff(db.of(d369("upZhbG1tYCVJdW0=")), true);
   private final ff deepslateDiamondOre = new ff(db.of(d369("upplcXFvZXFjJ0xga2ZjY2ovX2N3")), true);
   private final ff redstoneOre = new ff(db.of(d369("rJpkcnZsamAmSHps")), false);
   private final ff deepslateRedstoneOre = new ff(db.of(d369("upplcXFvZXFjJ1psbnh4YmBqMF5gdg==")), false);
   private final ff emeraldOre = new ff(db.of(d369("u5Jlc2NvYCVJdW0=")), false);
   private final ff deepslateEmeraldOre = new ff(db.of(d369("upplcXFvZXFjJ01kb3ltYWovX2N3")), false);
   private final ff goldOre = new ff(db.of(d369("uZBsZSJMdmA=")), false);
   private final ff deepslateGoldOre = new ff(db.of(d369("upplcXFvZXFjJ09mZm8sQnxq")), false);
   private final ff netherGoldOre = new ff(db.of(d369("sJp0aWdxJEJpa2wpRXlp")), false);
   private final ff ironOre = new ff(db.of(d369("t41vbyJMdmA=")), false);
   private final ff deepslateIronOre = new ff(db.of(d369("upplcXFvZXFjJ0F7ZWUsQnxq")), false);
   private final ff ancientDebris = new ff(db.of(d369("v5FjaGdtcCVCYmp7Y3g=")), false);
   private final Map<Long, List<bh>> chunkVeins = new ConcurrentHashMap();
   private final Set<Long> processedChunks = ConcurrentHashMap.newKeySet();
   private int lastChunkX = -2147483401 - 248;
   private int lastChunkZ = Integer.MAX_VALUE;
   private long parsedSeed = 0L;
   private String lastSeedValue = "";
   private int tickCounter = 0;

   public fa() {
      super(db.of(d369("sY1lR2ttYGB0")), db.of(d369("tpZnaW5qY21ydChmeG5/LXpnYn5ndHw1YXZ0dWk=")), -1, hk.RENDER);
      ab[] var10001 = new ab[102 ^ 116];
      var10001[0] = this.alpha;
      var10001[1] = this.tracers;
      var10001[2] = this.renderDistance;
      var10001[3] = this.useSeed;
      var10001[4] = this.seed;
      var10001[5] = this.updateDelay;
      var10001[6] = this.diamondOre;
      var10001[7] = this.deepslateDiamondOre;
      var10001[52 ^ 60] = this.redstoneOre;
      var10001[9] = this.deepslateRedstoneOre;
      var10001[10] = this.emeraldOre;
      var10001[66 ^ 73] = this.deepslateEmeraldOre;
      var10001[12] = this.goldOre;
      var10001[13] = this.deepslateGoldOre;
      var10001[14] = this.netherGoldOre;
      var10001[60 ^ 51] = this.ironOre;
      var10001[16] = this.deepslateIronOre;
      var10001[17] = this.ancientDebris;
      this.addSettings(var10001);
   }

   public void onEnable() {
      super.onEnable();
      this.chunkVeins.clear();
      this.processedChunks.clear();
      this.parseSeed();
      this.lastSeedValue = this.seed.getValue();
   }

   public void onDisable() {
      super.onDisable();
      this.chunkVeins.clear();
      this.processedChunks.clear();
   }

   private void parseSeed() {
      try {
         String seedStr = this.seed.getValue().trim();
         if (seedStr.isEmpty()) {
            this.parsedSeed = 0L;
            return;
         }

         try {
            this.parsedSeed = Long.parseLong(seedStr);
         } catch (NumberFormatException var3) {
            this.parsedSeed = (long)seedStr.hashCode();
         }
      } catch (Exception var4) {
         this.parsedSeed = 0L;
      }

   }

   @cp
   public void onRender3D(bg render3DEvent) {
      if (mc.field_1724 != null && mc.field_1687 != null) {
         ++this.tickCounter;
         int currentChunkX = mc.field_1724.method_31476().field_9181;
         int currentChunkZ = mc.field_1724.method_31476().field_9180;
         boolean seedChanged = !this.seed.getValue().equals(this.lastSeedValue);
         if (seedChanged) {
            this.parseSeed();
            this.lastSeedValue = this.seed.getValue();
            this.chunkVeins.clear();
            this.processedChunks.clear();
            this.lastChunkX = -2147482691 - 958;
            this.lastChunkZ = Integer.MAX_VALUE;
            this.tickCounter = 0;
         }

         boolean shouldUpdate = this.tickCounter >= this.updateDelay.getIntValue();
         if (currentChunkX != this.lastChunkX || currentChunkZ != this.lastChunkZ || shouldUpdate) {
            if (this.useSeed.getValue()) {
               this.updateSeedBased(currentChunkX, currentChunkZ);
            } else {
               this.updateNormal();
            }

            this.lastChunkX = currentChunkX;
            this.lastChunkZ = currentChunkZ;
            this.tickCounter = 0;
         }

         this.renderOres(render3DEvent);
      }
   }

   private void updateNormal() {
      this.chunkVeins.clear();
      this.processedChunks.clear();
      if (mc.field_1687 != null && mc.field_1724 != null) {
         int playerChunkX = mc.field_1724.method_31476().field_9181;
         int playerChunkZ = mc.field_1724.method_31476().field_9180;
         int chunkRange = this.renderDistance.getIntValue();
         Iterator var4 = ft.getLoadedChunks().toList().iterator();

         while(var4.hasNext()) {
            class_2818 chunk = (class_2818)var4.next();
            int chunkX = chunk.method_12004().field_9181;
            int chunkZ = chunk.method_12004().field_9180;
            if (Math.abs(chunkX - playerChunkX) <= chunkRange && Math.abs(chunkZ - playerChunkZ) <= chunkRange) {
               this.scanChunkForOres(chunk);
            }
         }

      }
   }

   private void scanChunkForOres(class_2818 chunk) {
      Map<class_2338, class_2248> tempOres = new HashMap();
      int startX = chunk.method_12004().method_8326();
      int startZ = chunk.method_12004().method_8328();

      for(int x = 0; x < (178 ^ 162); ++x) {
         for(int z = 0; z < 16; ++z) {
            for(int y = -31 ^ 33; y < (104 ^ 40); ++y) {
               class_2338 pos = new class_2338(startX + x, y, startZ + z);
               class_2680 state = chunk.method_8320(pos);
               class_2248 block = state.method_26204();
               if (this.isOreEnabled(block)) {
                  tempOres.put(pos, block);
               }
            }
         }
      }

      this.groupOresIntoVeins(tempOres, chunk.method_12004().method_8324());
   }

   private void updateSeedBased(int playerChunkX, int playerChunkZ) {
      int chunkRange = this.renderDistance.getIntValue();
      this.chunkVeins.entrySet().removeIf((entry) -> {
         long chunkKey = (Long)entry.getKey();
         int chunkX = class_1923.method_8325(chunkKey);
         int chunkZ = class_1923.method_8332(chunkKey);
         return Math.abs(chunkX - playerChunkX) > chunkRange || Math.abs(chunkZ - playerChunkZ) > chunkRange;
      });
      this.processedChunks.removeIf((chunkKeyx) -> {
         int chunkX = class_1923.method_8325(chunkKeyx);
         int chunkZ = class_1923.method_8332(chunkKeyx);
         return Math.abs(chunkX - playerChunkX) > chunkRange || Math.abs(chunkZ - playerChunkZ) > chunkRange;
      });

      for(int chunkX = playerChunkX - chunkRange; chunkX <= playerChunkX + chunkRange; ++chunkX) {
         for(int chunkZ = playerChunkZ - chunkRange; chunkZ <= playerChunkZ + chunkRange; ++chunkZ) {
            long chunkKey = class_1923.method_8331(chunkX, chunkZ);
            if (!this.processedChunks.contains(chunkKey)) {
               this.generateOresForChunk(chunkX, chunkZ);
               this.processedChunks.add(chunkKey);
            }
         }
      }

   }

   private void generateOresForChunk(int chunkX, int chunkZ) {
      if (mc.field_1687 != null) {
         Map<class_2338, class_2248> predictedOres = new HashMap();
         int startX = chunkX * (118 ^ 102);
         int startZ = chunkZ * (110 ^ 126);
         if (this.diamondOre.getValue() || this.deepslateDiamondOre.getValue()) {
            this.generateOreType(predictedOres, startX, startZ, chunkX, chunkZ, class_2246.field_10442, -64, 16, 1, 2, 4, 8);
         }

         if (this.redstoneOre.getValue() || this.deepslateRedstoneOre.getValue()) {
            this.generateOreType(predictedOres, startX, startZ, chunkX, chunkZ, class_2246.field_10080, -64, 16, 2, 4, 37 ^ 35, 10);
         }

         if (this.emeraldOre.getValue() || this.deepslateEmeraldOre.getValue()) {
            this.generateOreType(predictedOres, startX, startZ, chunkX, chunkZ, class_2246.field_10013, -16, 349 ^ 93, 1, 2, 3, 6);
         }

         if (this.goldOre.getValue() || this.deepslateGoldOre.getValue()) {
            this.generateOreType(predictedOres, startX, startZ, chunkX, chunkZ, class_2246.field_10571, -64, 107 ^ 75, 2, 3, 4, 8);
         }

         if (this.netherGoldOre.getValue()) {
            this.generateOreType(predictedOres, startX, startZ, chunkX, chunkZ, class_2246.field_23077, 161 ^ 171, 33 ^ 87, 4, 8, 8, 16);
         }

         if (this.ironOre.getValue() || this.deepslateIronOre.getValue()) {
            this.generateOreType(predictedOres, startX, startZ, chunkX, chunkZ, class_2246.field_10212, -64, 241 ^ 185, 3, 6, 6, 12);
         }

         if (this.ancientDebris.getValue()) {
            this.generateOreType(predictedOres, startX, startZ, chunkX, chunkZ, class_2246.field_22109, 8, 119, 1, 2, 1, 3);
         }

         predictedOres.entrySet().removeIf((entry) -> {
            class_2338 pos = (class_2338)entry.getKey();
            class_2680 state = mc.field_1687.method_8320(pos);
            return !state.method_26225();
         });
         this.groupOresIntoVeins(predictedOres, class_1923.method_8331(chunkX, chunkZ));
      }
   }

   private void generateOreType(Map<class_2338, class_2248> oreMap, int startX, int startZ, int chunkX, int chunkZ, class_2248 oreType, int minY, int maxY, int minVeins, int maxVeins, int minSize, int maxSize) {
      Random random = new Random(this.parsedSeed);
      long chunkSeed = random.nextLong() ^ (long)chunkX * 341873128712L + (long)chunkZ * 132897987541L;
      random.setSeed(chunkSeed);
      int veinCount = minVeins + random.nextInt(maxVeins - minVeins + 1);

      for(int i = 0; i < veinCount; ++i) {
         int x = startX + random.nextInt(16);
         int z = startZ + random.nextInt(16);
         int y = minY + random.nextInt(maxY - minY);
         int veinSize = minSize + random.nextInt(maxSize - minSize + 1);

         for(int j = 0; j < veinSize; ++j) {
            int offsetX = x + (random.nextInt(5) - 2);
            int offsetY = y + (random.nextInt(5) - 2);
            int offsetZ = z + (random.nextInt(5) - 2);
            class_2338 pos = new class_2338(offsetX, offsetY, offsetZ);
            class_2248 block = oreType;
            if (offsetY < 0) {
               if (oreType == class_2246.field_10442 && this.deepslateDiamondOre.getValue()) {
                  block = class_2246.field_29029;
               } else if (oreType == class_2246.field_10080 && this.deepslateRedstoneOre.getValue()) {
                  block = class_2246.field_29030;
               } else if (oreType == class_2246.field_10013 && this.deepslateEmeraldOre.getValue()) {
                  block = class_2246.field_29220;
               } else if (oreType == class_2246.field_10571 && this.deepslateGoldOre.getValue()) {
                  block = class_2246.field_29026;
               } else if (oreType == class_2246.field_10212 && this.deepslateIronOre.getValue()) {
                  block = class_2246.field_29027;
               }
            }

            if (this.isOreEnabled(block)) {
               oreMap.put(pos, block);
            }
         }
      }

   }

   private void groupOresIntoVeins(Map<class_2338, class_2248> ores, long chunkKey) {
      Set<class_2338> processed = new HashSet();
      List<bh> veins = new ArrayList();
      Iterator var6 = ores.entrySet().iterator();

      while(true) {
         Entry entry;
         class_2338 pos;
         do {
            if (!var6.hasNext()) {
               this.chunkVeins.put(chunkKey, veins);
               return;
            }

            entry = (Entry)var6.next();
            pos = (class_2338)entry.getKey();
         } while(processed.contains(pos));

         class_2248 block = (class_2248)entry.getValue();
         bh vein = new bh(block);
         Queue<class_2338> queue = new LinkedList();
         queue.add(pos);
         processed.add(pos);
         vein.addBlock(pos);

         while(!queue.isEmpty() && vein.blocks.size() < 30) {
            class_2338 current = (class_2338)queue.poll();
            class_2338[] neighbors = new class_2338[]{current.method_10069(1, 0, 0), current.method_10069(-1, 0, 0), current.method_10069(0, 1, 0), current.method_10069(0, -1, 0), current.method_10069(0, 0, 1), current.method_10069(0, 0, -1)};
            class_2338[] var14 = neighbors;
            int var15 = neighbors.length;

            for(int var16 = 0; var16 < var15; ++var16) {
               class_2338 neighbor = var14[var16];
               if (!processed.contains(neighbor)) {
                  class_2248 neighborBlock = (class_2248)ores.get(neighbor);
                  if (neighborBlock != null && this.isSameOreType(block, neighborBlock)) {
                     queue.add(neighbor);
                     processed.add(neighbor);
                     vein.addBlock(neighbor);
                  }
               }
            }
         }

         veins.add(vein);
      }
   }

   private boolean isSameOreType(class_2248 a, class_2248 b) {
      if (a == b) {
         return true;
      } else if (a == class_2246.field_10442 && b == class_2246.field_29029 || a == class_2246.field_29029 && b == class_2246.field_10442) {
         return true;
      } else if (a == class_2246.field_10080 && b == class_2246.field_29030 || a == class_2246.field_29030 && b == class_2246.field_10080) {
         return true;
      } else if ((a != class_2246.field_10013 || b != class_2246.field_29220) && (a != class_2246.field_29220 || b != class_2246.field_10013)) {
         if ((a != class_2246.field_10571 || b != class_2246.field_29026) && (a != class_2246.field_29026 || b != class_2246.field_10571)) {
            return a == class_2246.field_10212 && b == class_2246.field_29027 || a == class_2246.field_29027 && b == class_2246.field_10212;
         } else {
            return true;
         }
      } else {
         return true;
      }
   }

   private boolean isOreEnabled(class_2248 block) {
      if (block == class_2246.field_10442 && this.diamondOre.getValue()) {
         return true;
      } else if (block == class_2246.field_29029 && this.deepslateDiamondOre.getValue()) {
         return true;
      } else if (block == class_2246.field_10080 && this.redstoneOre.getValue()) {
         return true;
      } else if (block == class_2246.field_29030 && this.deepslateRedstoneOre.getValue()) {
         return true;
      } else if (block == class_2246.field_10013 && this.emeraldOre.getValue()) {
         return true;
      } else if (block == class_2246.field_29220 && this.deepslateEmeraldOre.getValue()) {
         return true;
      } else if (block == class_2246.field_10571 && this.goldOre.getValue()) {
         return true;
      } else if (block == class_2246.field_29026 && this.deepslateGoldOre.getValue()) {
         return true;
      } else if (block == class_2246.field_23077 && this.netherGoldOre.getValue()) {
         return true;
      } else if (block == class_2246.field_10212 && this.ironOre.getValue()) {
         return true;
      } else if (block == class_2246.field_29027 && this.deepslateIronOre.getValue()) {
         return true;
      } else {
         return block == class_2246.field_22109 && this.ancientDebris.getValue();
      }
   }

   private Color getOreColor(class_2248 block, int a) {
      if (block != class_2246.field_10442 && block != class_2246.field_29029) {
         if (block != class_2246.field_10080 && block != class_2246.field_29030) {
            if (block == class_2246.field_10013) {
               return ch.safeColor(0, 255, 0, a);
            } else if (block == class_2246.field_29220) {
               return ch.safeColor(0, 180, 0, a);
            } else if (block != class_2246.field_10571 && block != class_2246.field_29026 && block != class_2246.field_23077) {
               if (block != class_2246.field_10212 && block != class_2246.field_29027) {
                  return block == class_2246.field_22109 ? ch.safeColor(101, 67, 24 ^ 57, a) : ch.safeColor(255, 1 ^ 254, 3 ^ 252, 0);
               } else {
                  return ch.safeColor(200, 200, 200, a);
               }
            } else {
               return ch.safeColor(255, 98 ^ 181, 0, a);
            }
         } else {
            return ch.safeColor(255, 0, 0, a);
         }
      } else {
         return ch.safeColor(0, 230 ^ 25, 22 ^ 233, a);
      }
   }

   private void renderOres(bg event) {
      class_4184 cam = iq.getCamera();
      if (cam != null) {
         class_243 camPos = iq.getCameraPos();
         class_4587 matrices = event.matrixStack;
         matrices.method_22903();
         matrices.method_22907(class_7833.field_40714.rotationDegrees(cam.method_19329()));
         matrices.method_22907(class_7833.field_40716.rotationDegrees(cam.method_19330() + 180.0F));
         matrices.method_22904(-camPos.field_1352, -camPos.field_1351, -camPos.field_1350);
         Iterator var5 = this.chunkVeins.values().iterator();

         label43:
         while(var5.hasNext()) {
            List<bh> veins = (List)var5.next();
            Iterator var7 = veins.iterator();

            while(true) {
               bh vein;
               Color color;
               do {
                  if (!var7.hasNext()) {
                     continue label43;
                  }

                  vein = (bh)var7.next();
                  color = this.getOreColor(vein.type, this.alpha.getIntValue());
               } while(color.getAlpha() <= 0);

               Iterator var10 = vein.blocks.iterator();

               while(var10.hasNext()) {
                  class_2338 blockPos = (class_2338)var10.next();
                  iq.renderFilledBox(matrices, (float)((double)blockPos.method_10263() + 0.1D), (float)((double)blockPos.method_10264() + 0.05D), (float)((double)blockPos.method_10260() + 0.1D), (float)((double)blockPos.method_10263() + 0.9D), (float)((double)blockPos.method_10264() + 0.85D), (float)((double)blockPos.method_10260() + 0.9D), color);
               }

               if (this.tracers.getValue() && mc.field_1765 != null && vein.center != null) {
                  iq.renderLine(matrices, ch.safeColor(color.getRed(), color.getGreen(), color.getBlue(), 98 ^ 157), mc.field_1765.method_17784(), new class_243((double)vein.center.method_10263() + 0.5D, (double)vein.center.method_10264() + 0.5D, (double)vein.center.method_10260() + 0.5D));
               }
            }
         }

         matrices.method_22909();
      }
   }

   // $FF: synthetic method
   private static String d369(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 6142 + var3 & (183 ^ 72));
      }

      return new String(var2);
   }
}
