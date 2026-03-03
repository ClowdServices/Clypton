import client.astralux.Astralux;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.Color;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_243;
import net.minecraft.class_2680;
import net.minecraft.class_2818;
import net.minecraft.class_2960;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_7833;
import net.minecraft.class_7923;
import net.minecraft.class_239.class_240;
import net.minecraft.class_2902.class_2903;

public final class dg extends bf {
   private final gn maxRenderBlocks = new gn(db.of(d222("iaS+55qspK+pvw==")), 500.0D, 10000.0D, 4000.0D, 100.0D);
   private final gn alpha = new gn(db.of(d222("ham2r6k=")), 1.0D, 255.0D, 125.0D, 1.0D);
   private final ff tracers = new ff(db.of(d222("kLenpK27uQ==")), false);
   private final il blocks = new il(db.of(d222("hqmppKO6")));
   private final Map<class_2248, Color> blockColors = new ConcurrentHashMap();
   private final AtomicReference<List<class_2338>> cachedBlocksRef = new AtomicReference(Collections.emptyList());
   private final AtomicReference<List<go>> cachedClustersRef = new AtomicReference(Collections.emptyList());
   private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor((r) -> {
      Thread t = new Thread(r, d222("hqmppKOMmZvhnq2uvr+3oQ=="));
      t.setDaemon(true);
      return t;
   });
   private ScheduledFuture<?> scanTask;
   private static final String COLOR_CONFIG_FILE = "blockesp_colors.json";
   private final Gson gson = (new GsonBuilder()).setPrettyPrinting().create();

   private static String getConfigDir() {
      return FabricLoader.getInstance().getGameDir().resolve("config/ClyptonClient").toString();
   }

   public dg() {
      super(db.of(d222("hqmppKOMmZs=")), db.of(d222("gqyoo+irq7ipvg==")), -1, hk.RENDER);
      this.initializeDefaultColors();
      this.loadColors();
      this.addSettings(new ab[]{this.maxRenderBlocks, this.alpha, this.tracers, this.blocks});
   }

   public Map<class_2248, Color> getBlockColors() {
      return this.blockColors;
   }

   private void initializeDefaultColors() {
      this.blockColors.put(class_2246.field_10442, new Color(0, 255, 137 ^ 118));
      this.blockColors.put(class_2246.field_29029, new Color(0, 200, 175 ^ 103));
      this.blockColors.put(class_2246.field_10013, new Color(0, 255, 0));
      this.blockColors.put(class_2246.field_29220, new Color(0, 200, 0));
      this.blockColors.put(class_2246.field_10571, new Color(124 ^ 131, 215, 0));
      this.blockColors.put(class_2246.field_29026, new Color(90 ^ 146, 170, 0));
      this.blockColors.put(class_2246.field_10212, new Color(200, 200, 167 ^ 111));
      this.blockColors.put(class_2246.field_29027, new Color(150, 150, 150));
      this.blockColors.put(class_2246.field_10418, new Color(243 ^ 193, 50, 64 ^ 114));
      this.blockColors.put(class_2246.field_29219, new Color(30, 30, 30));
      this.blockColors.put(class_2246.field_10090, new Color(0, 0, 119 ^ 136));
      this.blockColors.put(class_2246.field_29028, new Color(0, 0, 2 ^ 202));
      this.blockColors.put(class_2246.field_10080, new Color(255, 0, 0));
      this.blockColors.put(class_2246.field_29030, new Color(200, 0, 0));
      this.blockColors.put(class_2246.field_27120, new Color(199 ^ 56, 127, 215 ^ 135));
      this.blockColors.put(class_2246.field_29221, new Color(200, 100, 60));
      this.blockColors.put(class_2246.field_23077, new Color(255, 66 ^ 138, 0));
      this.blockColors.put(class_2246.field_10213, new Color(88 ^ 167, 123 ^ 132, 255));
      this.blockColors.put(class_2246.field_22109, new Color(174 ^ 37, 109 ^ 40, 4 ^ 23));
      this.blockColors.put(class_2246.field_10034, new Color(255, 45 ^ 161, 0));
      this.blockColors.put(class_2246.field_10380, new Color(200, 92 ^ 7, 0));
      this.blockColors.put(class_2246.field_10443, new Color(29 ^ 183, 0, 140 ^ 115));
      this.blockColors.put(class_2246.field_16328, new Color(131 ^ 8, 69, 19));
      this.blockColors.put(class_2246.field_10603, new Color(255, 0, 255));
      this.blockColors.put(class_2246.field_10199, new Color(80 ^ 175, 183 ^ 72, 255));
      this.blockColors.put(class_2246.field_10407, new Color(219 ^ 36, 251 ^ 119, 0));
      this.blockColors.put(class_2246.field_10063, new Color(41 ^ 214, 0, 255));
      this.blockColors.put(class_2246.field_10203, new Color(100, 200, 28 ^ 227));
      this.blockColors.put(class_2246.field_10600, new Color(255, 255, 0));
      this.blockColors.put(class_2246.field_10275, new Color(0, 255, 0));
      this.blockColors.put(class_2246.field_10051, new Color(255, 220 ^ 28, 203));
      this.blockColors.put(class_2246.field_10140, new Color(128, 100 ^ 228, 173 ^ 45));
      this.blockColors.put(class_2246.field_10320, new Color(238 ^ 46, 86 ^ 150, 162 ^ 98));
      this.blockColors.put(class_2246.field_10532, new Color(0, 255, 255));
      this.blockColors.put(class_2246.field_10268, new Color(128, 0, 249 ^ 121));
      this.blockColors.put(class_2246.field_10605, new Color(0, 0, 158 ^ 97));
      this.blockColors.put(class_2246.field_10373, new Color(139, 69, 19));
      this.blockColors.put(class_2246.field_10055, new Color(0, 128, 0));
      this.blockColors.put(class_2246.field_10068, new Color(0 ^ 255, 0, 0));
      this.blockColors.put(class_2246.field_10371, new Color(50, 50, 50));
      this.blockColors.put(class_2246.field_10260, new Color(255, 255, 149 ^ 106));
      this.blockColors.put(class_2246.field_10181, new Color(128, 118 ^ 246, 201 ^ 73));
      this.blockColors.put(class_2246.field_16333, new Color(64, 64, 64));
      this.blockColors.put(class_2246.field_16334, new Color(192, 192, 192));
      this.blockColors.put(class_2246.field_10485, new Color(0, 150, 255));
      this.blockColors.put(class_2246.field_46797, new Color(0, 255, 200 ^ 55));
      this.blockColors.put(class_2246.field_10200, new Color(255, 0, 0));
      this.blockColors.put(class_2246.field_10228, new Color(38 ^ 217, 248 ^ 156, 0));
      this.blockColors.put(class_2246.field_10312, new Color(180, 180, 0));
      this.blockColors.put(class_2246.field_10327, new Color(0, 255, 255));
      this.blockColors.put(class_2246.field_10398, new Color(100, 255, 100));
      this.blockColors.put(class_2246.field_10316, new Color(18 ^ 146, 0, 55 ^ 200));
      this.blockColors.put(class_2246.field_10027, new Color(0, 0, 0));
   }

   private void loadColors() {
      try {
         Path configPath = Paths.get(getConfigDir(), d222("pqmppKOsubuTrqGjv6Oh/b6mubk="));
         if (!Files.exists(configPath, new LinkOption[0])) {
            return;
         }

         String json = Files.readString(configPath);
         JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
         Iterator var4 = jsonObject.keySet().iterator();

         while(var4.hasNext()) {
            String blockId = (String)var4.next();

            try {
               class_2960 identifier = class_2960.method_60654(blockId);
               class_2248 block = (class_2248)class_7923.field_41175.method_10223(identifier);
               if (block != null && block != class_2246.field_10124) {
                  JsonObject colorObj = jsonObject.getAsJsonObject(blockId);
                  int r = colorObj.get("r").getAsInt();
                  int g = colorObj.get("g").getAsInt();
                  int b = colorObj.get("b").getAsInt();
                  this.blockColors.put(block, new Color(r, g, b, 7 ^ 248));
               }
            } catch (Exception var12) {
            }
         }
      } catch (Exception var13) {
      }

   }

   public void saveColors() {
      try {
         Path configDir = Paths.get(getConfigDir());
         if (!Files.exists(configDir, new LinkOption[0])) {
            Files.createDirectories(configDir);
         }

         JsonObject jsonObject = new JsonObject();
         Iterator var3 = this.blockColors.entrySet().iterator();

         while(var3.hasNext()) {
            Entry<class_2248, Color> entry = (Entry)var3.next();
            class_2960 identifier = class_7923.field_41175.method_10221((class_2248)entry.getKey());
            if (identifier != null) {
               JsonObject colorObj = new JsonObject();
               Color color = (Color)entry.getValue();
               colorObj.addProperty("r", (Number)color.getRed());
               colorObj.addProperty("g", (Number)color.getGreen());
               colorObj.addProperty("b", (Number)color.getBlue());
               jsonObject.add(identifier.toString(), colorObj);
            }
         }

         Path configPath = Paths.get(getConfigDir(), d222("pqmppKOsubuTrqGjv6Oh/b6mubk="));
         Files.writeString(configPath, this.gson.toJson((JsonElement)jsonObject), new OpenOption[0]);
      } catch (Exception var8) {
      }

   }

   public void onEnable() {
      super.onEnable();
      this.cachedBlocksRef.set(Collections.emptyList());
      this.cachedClustersRef.set(Collections.emptyList());
      if (this.scanTask == null || this.scanTask.isCancelled()) {
         this.scanTask = this.executor.scheduleAtFixedRate(this::scanChunks, 0L, 100L, TimeUnit.MILLISECONDS);
      }

   }

   public void onDisable() {
      super.onDisable();
      this.saveColors();
      if (this.scanTask != null) {
         this.scanTask.cancel(false);
         this.scanTask = null;
      }

      this.cachedBlocksRef.set(Collections.emptyList());
      this.cachedClustersRef.set(Collections.emptyList());
   }

   private void scanChunks() {
      if (mc != null && mc.field_1724 != null && mc.field_1687 != null) {
         try {
            List<class_2338> found = new ArrayList();
            class_243 playerPos = mc.field_1724.method_19538();
            double maxDistSq = 262144.0D;
            int maxBlocks = this.maxRenderBlocks.getIntValue();
            Iterator var6 = ft.getLoadedChunks().toList().iterator();

            while(var6.hasNext()) {
               class_2818 chunk = (class_2818)var6.next();

               for(int x = 0; x < 16; ++x) {
                  for(int z = 0; z < 16; ++z) {
                     int highestY = chunk.method_12032(class_2903.field_13202).method_12603(x, z);

                     for(int y = mc.field_1687.method_31607(); y <= highestY + 5; ++y) {
                        class_2338 pos = new class_2338((chunk.method_12004().field_9181 << 4) + x, y, (chunk.method_12004().field_9180 << 4) + z);
                        class_2680 state = chunk.method_8320(pos);
                        if (this.blocks.hasBlock(state.method_26204())) {
                           double dx = playerPos.field_1352 - ((double)pos.method_10263() + 0.5D);
                           double dy = playerPos.field_1351 - ((double)pos.method_10264() + 0.5D);
                           double dz = playerPos.field_1350 - ((double)pos.method_10260() + 0.5D);
                           double distSq = dx * dx + dy * dy + dz * dz;
                           if (distSq <= maxDistSq && found.size() < maxBlocks) {
                              found.add(pos);
                           }
                        }
                     }
                  }
               }
            }

            this.cachedBlocksRef.set(Collections.unmodifiableList(found));
            this.cachedClustersRef.set(Collections.unmodifiableList(this.clusterBlocks(found)));
         } catch (Exception var22) {
         }

      }
   }

   private List<go> clusterBlocks(List<class_2338> blocks) {
      if (blocks.isEmpty()) {
         return Collections.emptyList();
      } else {
         List<go> clusters = new ArrayList();
         HashSet unclustered = new HashSet(blocks);

         while(!unclustered.isEmpty()) {
            class_2338 seed = (class_2338)unclustered.iterator().next();
            List<class_2338> group = this.floodFill(seed, unclustered);
            Color color = this.calculateClusterColor(group);
            clusters.add(new go(group, color));
         }

         return clusters;
      }
   }

   private List<class_2338> floodFill(class_2338 start, Set<class_2338> available) {
      List<class_2338> cluster = new ArrayList();
      Queue<class_2338> queue = new LinkedList();
      Set<class_2338> visited = new HashSet();
      queue.add(start);
      visited.add(start);
      available.remove(start);

      while(!queue.isEmpty()) {
         class_2338 current = (class_2338)queue.poll();
         cluster.add(current);
         class_2338[] neighbors = new class_2338[]{current.method_10069(1, 0, 0), current.method_10069(-1, 0, 0), current.method_10069(0, 1, 0), current.method_10069(0, -1, 0), current.method_10069(0, 0, 1), current.method_10069(0, 0, -1)};
         class_2338[] var8 = neighbors;
         int var9 = neighbors.length;

         for(int var10 = 0; var10 < var9; ++var10) {
            class_2338 neighbor = var8[var10];
            if (!visited.contains(neighbor) && available.contains(neighbor)) {
               visited.add(neighbor);
               available.remove(neighbor);
               queue.add(neighbor);
            }
         }
      }

      return cluster;
   }

   private Color calculateClusterColor(List<class_2338> positions) {
      int r = 0;
      int g = 0;
      int b = 0;

      Color color;
      for(Iterator var5 = positions.iterator(); var5.hasNext(); b += color.getBlue()) {
         class_2338 pos = (class_2338)var5.next();
         class_2248 block = mc.field_1687.method_8320(pos).method_26204();
         color = (Color)this.blockColors.getOrDefault(block, this.generateColorFromBlock(block));
         r += color.getRed();
         g += color.getGreen();
      }

      int size = positions.size();
      return new Color(r / size, g / size, b / size, this.alpha.getIntValue());
   }

   private Color generateColorFromBlock(class_2248 block) {
      int hash = block.hashCode();
      int r = Math.max((hash & 16711680) >> 16, 100);
      int g = Math.max((hash & '\uff00') >> (242 ^ 250), 198 ^ 162);
      int b = Math.max(hash & 255, 100);
      return new Color(r, g, b, 255);
   }

   private class_243 getCrosshairPositionForESP(float tickDelta) {
      bf freecam = mc.field_1724 == null ? null : Astralux.INSTANCE.getModuleManager().getModuleByClass(cj.class);
      if (freecam != null && freecam.isEnabled()) {
         cj fc = (cj)freecam;
         if (mc.field_1765 != null && mc.field_1765.method_17783() != class_240.field_1333) {
            return mc.field_1765.method_17784();
         } else {
            class_243 freecamPos = new class_243(fc.getInterpolatedX(tickDelta), fc.getInterpolatedY(tickDelta), fc.getInterpolatedZ(tickDelta));
            class_243 direction = class_243.method_1030((float)fc.getInterpolatedPitch(tickDelta), (float)fc.getInterpolatedYaw(tickDelta));
            return freecamPos.method_1019(direction.method_1021(4.0D));
         }
      } else {
         return mc.field_1765 != null ? mc.field_1765.method_17784() : mc.field_1724.method_5828(1.0F).method_1021(4.0D).method_1019(mc.field_1724.method_19538());
      }
   }

   @cp
   public void onRender3D(bg event) {
      if (mc != null && mc.field_1724 != null && mc.field_1687 != null) {
         List<go> clusters = (List)this.cachedClustersRef.get();
         if (!clusters.isEmpty()) {
            class_4184 cam = iq.getCamera();
            if (cam != null) {
               class_243 camPos = iq.getCameraPos();
               class_4587 matrices = event.matrixStack;
               matrices.method_22903();
               matrices.method_22907(class_7833.field_40714.rotationDegrees(cam.method_19329()));
               matrices.method_22907(class_7833.field_40716.rotationDegrees(cam.method_19330() + 180.0F));
               matrices.method_22904(-camPos.field_1352, -camPos.field_1351, -camPos.field_1350);
               class_243 crosshairPos = this.getCrosshairPositionForESP(event.tickDelta);
               Iterator var7 = clusters.iterator();

               while(var7.hasNext()) {
                  go cluster = (go)var7.next();
                  iq.renderFilledBox(matrices, (float)cluster.min.method_10263() + 0.05F, (float)cluster.min.method_10264() + 0.05F, (float)cluster.min.method_10260() + 0.05F, (float)cluster.max.method_10263() + 0.95F, (float)cluster.max.method_10264() + 0.95F, (float)cluster.max.method_10260() + 0.95F, cluster.color);
                  if (this.tracers.getValue()) {
                     class_243 target = new class_243((double)cluster.center.method_10263() + 0.5D, (double)cluster.center.method_10264() + 0.5D, (double)cluster.center.method_10260() + 0.5D);
                     Color tracerColor = new Color(cluster.color.getRed(), cluster.color.getGreen(), cluster.color.getBlue(), 255);
                     iq.renderLine(matrices, tracerColor, crosshairPos, target);
                  }
               }

               matrices.method_22909();
            }
         }
      }
   }

   // $FF: synthetic method
   private static String d222(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 3524 + var3 & 255);
      }

      return new String(var2);
   }
}
