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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import net.minecraft.class_2902.class_2903;

public final class fn extends bf {
   private final gn range = new gn(db.of(d924("lamnra4=")), 16.0D, 128.0D, 64.0D, 8.0D);
   private final gn oreOpacity = new gn(db.of(d924("iLqs6oS8rK2mpKg=")), 1.0D, 255.0D, 255.0D, 1.0D);
   private final gn blockOpacity = new gn(db.of(d924("haSmqaDsgr6us7imqg==")), 0.0D, 255.0D, 30.0D, 1.0D);
   private final ff tracers = new ff(db.of(d924("k7qoqa6+vg==")), false);
   private final ff hideOtherBlocks = new ff(db.of(d924("j6Gtr+uDuaaqovGQv7u2vaQ=")), true);
   private final il ores = new il(db.of(d924("iLqsuQ==")));
   private final Map<class_2248, Color> oreColors = new ConcurrentHashMap();
   private final AtomicReference<List<ip>> cachedOresRef = new AtomicReference(Collections.emptyList());
   private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor((r) -> {
      Thread t = new Thread(r, d924("n7qos+afrq+hvrSg"));
      t.setDaemon(true);
      return t;
   });
   private ScheduledFuture<?> scanTask;
   private boolean wasXrayActive = false;
   private static final String COLOR_CONFIG_FILE = "xray_colors.json";
   private final Gson gson = (new GsonBuilder()).setPrettyPrinting().create();

   private static String getConfigDir() {
      return FabricLoader.getInstance().getGameDir().resolve("config/ClyptonClient").toString();
   }

   public fn() {
      super(db.of(d924("n7qosw==")), db.of(d924("lK2s6qS+qL3vpLmgvKGyvvevuLa3rw==")), -1, hk.RENDER);
      this.initializeDefaultOres();
      this.loadColors();
      this.addSettings(new ab[]{this.range, this.oreOpacity, this.blockOpacity, this.tracers, this.hideOtherBlocks, this.ores});
   }

   public Map<class_2248, Color> getOreColors() {
      return this.oreColors;
   }

   private void initializeDefaultOres() {
      this.oreColors.put(class_2246.field_10442, new Color(0, 255, 76 ^ 179));
      this.oreColors.put(class_2246.field_29029, new Color(0, 200, 209 ^ 25));
      this.oreColors.put(class_2246.field_10013, new Color(0, 255, 0));
      this.oreColors.put(class_2246.field_29220, new Color(0, 200, 0));
      this.oreColors.put(class_2246.field_10571, new Color(255, 215, 0));
      this.oreColors.put(class_2246.field_29026, new Color(200, 170, 0));
      this.oreColors.put(class_2246.field_23077, new Color(255, 200, 0));
      this.oreColors.put(class_2246.field_10212, new Color(200, 200, 200));
      this.oreColors.put(class_2246.field_29027, new Color(174 ^ 56, 105 ^ 255, 150));
      this.oreColors.put(class_2246.field_10418, new Color(50, 161 ^ 147, 50));
      this.oreColors.put(class_2246.field_29219, new Color(30, 30, 25 ^ 7));
      this.oreColors.put(class_2246.field_10090, new Color(0, 100, 221 ^ 34));
      this.oreColors.put(class_2246.field_29028, new Color(0, 80, 48 ^ 248));
      this.oreColors.put(class_2246.field_10080, new Color(255, 0, 0));
      this.oreColors.put(class_2246.field_29030, new Color(200, 0, 0));
      this.oreColors.put(class_2246.field_27120, new Color(255, 127, 80));
      this.oreColors.put(class_2246.field_29221, new Color(200, 100, 60));
      this.oreColors.put(class_2246.field_10213, new Color(164 ^ 91, 4 ^ 251, 38 ^ 217));
      this.oreColors.put(class_2246.field_22109, new Color(139, 69, 193 ^ 210));
      Iterator var1 = this.oreColors.keySet().iterator();

      while(var1.hasNext()) {
         class_2248 ore = (class_2248)var1.next();
         this.ores.addBlock(ore);
      }

   }

   private void loadColors() {
      try {
         Path configPath = Paths.get(getConfigDir(), d924("v7qos5SvoqKgoqL8uae6uA=="));
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
                  this.oreColors.put(block, new Color(r, g, b, 68 ^ 187));
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
         Iterator var3 = this.oreColors.entrySet().iterator();

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

         Path configPath = Paths.get(getConfigDir(), d924("v7qos5SvoqKgoqL8uae6uA=="));
         Files.writeString(configPath, this.gson.toJson((JsonElement)jsonObject), new OpenOption[0]);
      } catch (Exception var8) {
      }

   }

   public void onEnable() {
      super.onEnable();
      this.cachedOresRef.set(Collections.emptyList());
      this.wasXrayActive = true;
      if (this.scanTask == null || this.scanTask.isCancelled()) {
         this.scanTask = this.executor.scheduleAtFixedRate(this::scanOres, 0L, 150L, TimeUnit.MILLISECONDS);
      }

      if (mc != null && mc.field_1769 != null) {
         mc.field_1769.method_3279();
      }

   }

   public void onDisable() {
      super.onDisable();
      this.saveColors();
      this.wasXrayActive = false;
      if (this.scanTask != null) {
         this.scanTask.cancel(false);
         this.scanTask = null;
      }

      this.cachedOresRef.set(Collections.emptyList());
      if (mc != null && mc.field_1769 != null) {
         mc.field_1769.method_3279();
      }

   }

   public boolean shouldShowBlock(class_2680 state) {
      if (!this.isEnabled()) {
         return true;
      } else {
         class_2248 block = state.method_26204();
         if (this.ores.hasBlock(block)) {
            return true;
         } else if (this.hideOtherBlocks.getValue()) {
            return state.method_26215() || block == class_2246.field_10382 || block == class_2246.field_10164;
         } else {
            return true;
         }
      }
   }

   public float getBlockOpacity(class_2680 state) {
      if (!this.isEnabled()) {
         return 1.0F;
      } else {
         class_2248 block = state.method_26204();
         if (this.ores.hasBlock(block)) {
            return (float)this.oreOpacity.getIntValue() / 255.0F;
         } else {
            return this.hideOtherBlocks.getValue() ? (float)this.blockOpacity.getIntValue() / 255.0F : 1.0F;
         }
      }
   }

   private void scanOres() {
      if (mc != null && mc.field_1724 != null && mc.field_1687 != null) {
         try {
            List<ip> found = new ArrayList();
            class_243 playerPos = mc.field_1724.method_19538();
            double maxRange = this.range.getValue();
            double maxDistSq = maxRange * maxRange;
            Iterator var7 = ft.getLoadedChunks().toList().iterator();

            while(var7.hasNext()) {
               class_2818 chunk = (class_2818)var7.next();

               for(int x = 0; x < 16; ++x) {
                  for(int z = 0; z < 16; ++z) {
                     int highestY = chunk.method_12032(class_2903.field_13202).method_12603(x, z);

                     for(int y = mc.field_1687.method_31607(); y <= highestY + 5; ++y) {
                        class_2338 pos = new class_2338((chunk.method_12004().field_9181 << 4) + x, y, (chunk.method_12004().field_9180 << 4) + z);
                        class_2680 state = chunk.method_8320(pos);
                        class_2248 block = state.method_26204();
                        if (this.ores.hasBlock(block)) {
                           double dx = playerPos.field_1352 - ((double)pos.method_10263() + 0.5D);
                           double dy = playerPos.field_1351 - ((double)pos.method_10264() + 0.5D);
                           double dz = playerPos.field_1350 - ((double)pos.method_10260() + 0.5D);
                           double distSq = dx * dx + dy * dy + dz * dz;
                           if (distSq <= maxDistSq) {
                              Color color = (Color)this.oreColors.getOrDefault(block, this.generateColorFromBlock(block));
                              found.add(new ip(pos, color, block));
                           }
                        }
                     }
                  }
               }
            }

            this.cachedOresRef.set(Collections.unmodifiableList(found));
         } catch (Exception var25) {
         }

      }
   }

   private Color generateColorFromBlock(class_2248 block) {
      int hash = block.hashCode();
      int r = Math.max((hash & 16711827 - 147) >> (53 ^ 37), 236 ^ 136);
      int g = Math.max((hash & 65883 - 603) >> 8, 100);
      int b = Math.max(hash & (188 ^ 67), 157 ^ 249);
      return new Color(r, g, b, 41 ^ 214);
   }

   @cp
   public void onRender3D(bg event) {
      if (mc != null && mc.field_1724 != null && mc.field_1687 != null) {
         List<ip> ores = (List)this.cachedOresRef.get();
         if (!ores.isEmpty()) {
            if (this.tracers.getValue() && mc.field_1765 != null) {
               class_4184 cam = iq.getCamera();
               if (cam == null) {
                  return;
               }

               class_243 camPos = iq.getCameraPos();
               class_243 crosshairPos = mc.field_1765.method_17784();
               class_4587 matrices = event.matrixStack;
               matrices.method_22903();
               matrices.method_22907(class_7833.field_40714.rotationDegrees(cam.method_19329()));
               matrices.method_22907(class_7833.field_40716.rotationDegrees(cam.method_19330() + 180.0F));
               matrices.method_22904(-camPos.field_1352, -camPos.field_1351, -camPos.field_1350);
               Iterator var7 = ores.iterator();

               while(var7.hasNext()) {
                  ip ore = (ip)var7.next();
                  Color tracerColor = ch.safeColor(ore.color.getRed(), ore.color.getGreen(), ore.color.getBlue(), 185 ^ 70);
                  class_243 target = new class_243((double)ore.pos.method_10263() + 0.5D, (double)ore.pos.method_10264() + 0.5D, (double)ore.pos.method_10260() + 0.5D);
                  iq.renderLine(matrices, tracerColor, crosshairPos, target);
               }

               matrices.method_22909();
            }

         }
      }
   }

   // $FF: synthetic method
   private static String d924(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 8391 + var3 & 255);
      }

      return new String(var2);
   }
}
