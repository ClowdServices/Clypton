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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.class_1688;
import net.minecraft.class_1694;
import net.minecraft.class_1696;
import net.minecraft.class_1700;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_7833;

public final class cq extends bf {
   private final gn minHeight = new gn(db.of(d43("+dzYl/Dc09zUyQ==")), -64.0D, 320.0D, -64.0D, 1.0D);
   private final gn maxHeight = new gn(db.of(d43("+dTOl/Dc09zUyQ==")), -64.0D, 320.0D, 320.0D, 1.0D);
   private final gn maxRenderDistance = new gn(db.of(d43("+dTOl/zQyc/d093a")), 16.0D, 512.0D, 128.0D, 8.0D);
   private final gn alpha = new gn(db.of(d43("9dnG39k=")), 1.0D, 255.0D, 150.0D, 1.0D);
   private final ff tracers = new ff(db.of(d43("4MfX1N3LyQ==")), true);
   private final ff chestMinecart = new ff(db.of(d43("993TxMyZ99LS2N3esrU=")), true);
   private final ff furnaceMinecart = new ff(db.of(d43("8sDE2dna35vx1NDao6Cwtw==")), true);
   private final ff hopperMinecart = new ff(db.of(d43("/NrGx93LmvbV09vcobO2")), true);
   private final Map<Class<? extends class_1688>, Color> minecartColors = new ConcurrentHashMap();
   private final ConcurrentMap<Integer, dm> cachedMinecarts = new ConcurrentHashMap();
   private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor((r) -> {
      Thread t = new Thread(r, d43("+dzY0tvYyM/57u4="));
      t.setDaemon(true);
      return t;
   });
   private ScheduledFuture<?> scanTask;
   private static final String COLOR_CONFIG_FILE = "minecart_debug_colors.json";
   private final Gson gson = (new GsonBuilder()).setPrettyPrinting().create();

   private static String getConfigDir() {
      return FabricLoader.getInstance().getGameDir().resolve("config/ClyptonClient").toString();
   }

   public cq() {
      super(db.of(d43("+dzY0tvYyM/42NzKpw==")), db.of(d43("8NDUwt+Z29XYndbWp6muqqOtsueloKSur6y8u6M=")), -1, hk.RENDER);
      this.initializeDefaultColors();
      this.loadColors();
      ab[] var10001 = new ab[49 ^ 57];
      var10001[0] = this.minHeight;
      var10001[1] = this.maxHeight;
      var10001[2] = this.maxRenderDistance;
      var10001[3] = this.alpha;
      var10001[4] = this.tracers;
      var10001[5] = this.chestMinecart;
      var10001[6] = this.furnaceMinecart;
      var10001[128 ^ 135] = this.hopperMinecart;
      this.addSettings(var10001);
   }

   public Map<Class<? extends class_1688>, Color> getMinecartColors() {
      return this.minecartColors;
   }

   private void initializeDefaultColors() {
      this.minecartColors.put(class_1694.class, new Color(255, 140, 0));
      this.minecartColors.put(class_1696.class, new Color(255, 50, 50));
      this.minecartColors.put(class_1700.class, new Color(128, 128, 128));
   }

   private void loadColors() {
      try {
         Path configPath = Paths.get(getConfigDir(), d43("2dzY0tvYyM/j2dvdtaadoKupqbW756C4o6M="));
         if (!Files.exists(configPath, new LinkOption[0])) {
            return;
         }

         String json = Files.readString(configPath);
         JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
         Iterator var4 = jsonObject.keySet().iterator();

         while(var4.hasNext()) {
            String className = (String)var4.next();

            try {
               Class<? extends class_1688> clazz = Class.forName(className);
               JsonObject colorObj = jsonObject.getAsJsonObject(className);
               int r = colorObj.get("r").getAsInt();
               int g = colorObj.get("g").getAsInt();
               int b = colorObj.get("b").getAsInt();
               this.minecartColors.put(clazz, new Color(r, g, b, 123 ^ 132));
            } catch (Exception var11) {
            }
         }
      } catch (Exception var12) {
      }

   }

   public void saveColors() {
      try {
         Path configDir = Paths.get(getConfigDir());
         if (!Files.exists(configDir, new LinkOption[0])) {
            Files.createDirectories(configDir);
         }

         JsonObject jsonObject = new JsonObject();
         Iterator var3 = this.minecartColors.entrySet().iterator();

         while(var3.hasNext()) {
            Entry<Class<? extends class_1688>, Color> entry = (Entry)var3.next();
            JsonObject colorObj = new JsonObject();
            Color color = (Color)entry.getValue();
            colorObj.addProperty("r", (Number)color.getRed());
            colorObj.addProperty("g", (Number)color.getGreen());
            colorObj.addProperty("b", (Number)color.getBlue());
            jsonObject.add(((Class)entry.getKey()).getName(), colorObj);
         }

         Path configPath = Paths.get(getConfigDir(), d43("2dzY0tvYyM/j2dvdtaadoKupqbW756C4o6M="));
         Files.writeString(configPath, this.gson.toJson((JsonElement)jsonObject), new OpenOption[0]);
      } catch (Exception var7) {
      }

   }

   public void onEnable() {
      super.onEnable();
      this.cachedMinecarts.clear();
      if (this.scanTask == null || this.scanTask.isCancelled()) {
         this.scanTask = this.executor.scheduleAtFixedRate(this::scanMinecarts, 0L, 100L, TimeUnit.MILLISECONDS);
      }

   }

   public void onDisable() {
      super.onDisable();
      this.saveColors();
      if (this.scanTask != null) {
         this.scanTask.cancel(false);
         this.scanTask = null;
      }

      this.cachedMinecarts.clear();
   }

   private void scanMinecarts() {
      if (mc != null && mc.field_1724 != null && mc.field_1687 != null) {
         try {
            class_243 playerPos = mc.field_1724.method_19538();
            double maxDistSq = this.maxRenderDistance.getValue() * this.maxRenderDistance.getValue();
            double minY = this.minHeight.getValue();
            double maxY = this.maxHeight.getValue();
            List<class_1688> minecarts = new ArrayList();
            mc.field_1687.method_18112().forEach((entity) -> {
               if (entity instanceof class_1688) {
                  minecarts.add((class_1688)entity);
               }

            });
            Set<Integer> currentIds = new HashSet();
            Iterator var10 = minecarts.iterator();

            while(true) {
               class_1688 minecart;
               double distSq;
               do {
                  class_243 pos;
                  do {
                     do {
                        if (!var10.hasNext()) {
                           this.cachedMinecarts.keySet().removeIf((idx) -> {
                              return !currentIds.contains(idx);
                           });
                           return;
                        }

                        minecart = (class_1688)var10.next();
                        pos = minecart.method_19538();
                     } while(pos.field_1351 < minY);
                  } while(pos.field_1351 > maxY);

                  distSq = playerPos.method_1025(pos);
               } while(distSq > maxDistSq);

               boolean shouldRender = false;
               if (minecart instanceof class_1694 && this.chestMinecart.getValue()) {
                  shouldRender = true;
               } else if (minecart instanceof class_1696 && this.furnaceMinecart.getValue()) {
                  shouldRender = true;
               } else if (minecart instanceof class_1700 && this.hopperMinecart.getValue()) {
                  shouldRender = true;
               }

               if (shouldRender) {
                  int id = minecart.method_5628();
                  currentIds.add(id);
                  this.cachedMinecarts.put(id, new dm(minecart));
               }
            }
         } catch (Exception var17) {
         }
      }
   }

   @cp
   public void onRender3D(bg event) {
      if (mc != null && mc.field_1724 != null && mc.field_1687 != null) {
         if (!this.cachedMinecarts.isEmpty()) {
            class_4184 cam = iq.getCamera();
            if (cam != null) {
               class_243 camPos = iq.getCameraPos();
               class_4587 matrices = event.matrixStack;
               matrices.method_22903();
               matrices.method_22907(class_7833.field_40714.rotationDegrees(cam.method_19329()));
               matrices.method_22907(class_7833.field_40716.rotationDegrees(cam.method_19330() + 180.0F));
               matrices.method_22904(-camPos.field_1352, -camPos.field_1351, -camPos.field_1350);
               Iterator var5 = this.cachedMinecarts.values().iterator();

               while(var5.hasNext()) {
                  dm data = (dm)var5.next();
                  if (!data.entity.method_31481()) {
                     class_243 pos = data.entity.method_19538();
                     class_238 box = data.entity.method_5829();
                     Color baseColor = (Color)this.minecartColors.getOrDefault(data.entity.getClass(), new Color(255, 255, 255));
                     Color renderColor = new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), this.alpha.getIntValue());
                     Color outlineColor = new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), 255);
                     iq.renderFilledBox(matrices, (float)box.field_1323, (float)box.field_1322, (float)box.field_1321, (float)box.field_1320, (float)box.field_1325, (float)box.field_1324, renderColor);
                     this.renderBoxOutline(matrices, box, outlineColor);
                     if (this.tracers.getValue()) {
                        Color tracerColor = new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), 235 ^ 20);
                        class_243 target = new class_243(pos.field_1352, pos.field_1351 + 0.5D, pos.field_1350);
                        iq.renderLine(matrices, tracerColor, camPos, target);
                     }
                  }
               }

               matrices.method_22909();
            }
         }
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
   private static String d43(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 4020 + var3 & 255);
      }

      return new String(var2);
   }
}
