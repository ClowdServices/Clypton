import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.blaze3d.systems.RenderSystem;
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
import java.util.concurrent.atomic.AtomicReference;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_1545;
import net.minecraft.class_1549;
import net.minecraft.class_1589;
import net.minecraft.class_1613;
import net.minecraft.class_1614;
import net.minecraft.class_1628;
import net.minecraft.class_1642;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_243;
import net.minecraft.class_2487;
import net.minecraft.class_2586;
import net.minecraft.class_2636;
import net.minecraft.class_2680;
import net.minecraft.class_2818;
import net.minecraft.class_2960;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_7833;
import net.minecraft.class_7923;
import net.minecraft.class_898;

public final class dv extends bf {
   private final gn maxRenderDistance = new gn(db.of(d741("mLev+J2zqKi8sLyF")), 16.0D, 512.0D, 256.0D, 16.0D);
   private final gn alpha = new gn(db.of(d741("lLqnsLg=")), 1.0D, 255.0D, 150.0D, 1.0D);
   private final gn mobHeadScale = new gn(db.of(d741("nbO2vPmJuL2xuw==")), 0.1D, 2.0D, 0.5D, 0.1D);
   private final ff tracers = new ff(db.of(d741("gaS2u7yoqA==")), true);
   private final ff showLabel = new ff(db.of(d741("hr64r/mWur64sqw=")), true);
   private final ff showMobHead = new ff(db.of(d741("hr64r/mXtL79lrqBhQ==")), true);
   private final ff showZombie = new ff(db.of(d741("j7m6urC/")), true);
   private final ff showSkeleton = new ff(db.of(d741("hr2ytLyutLI=")), true);
   private final ff showSpider = new ff(db.of(d741("hqa+vLyo")), true);
   private final ff showCaveSpider = new ff(db.of(d741("lrehvfmJq7W5u60=")), true);
   private final ff showBlaze = new ff(db.of(d741("l7q2orw=")), true);
   private final ff showMagmaCube = new ff(db.of(d741("mLewtbj6mKm/uw==")), true);
   private final ff showSilverfish = new ff(db.of(d741("hr+7rryovbWutg==")), true);
   private final ff showUnknown = new ff(db.of(d741("gLi8trattQ==")), true);
   private final Map<String, Color> mobTypeColors = new ConcurrentHashMap();
   private final Map<String, ff> mobTypeToggles = new ConcurrentHashMap();
   private final AtomicReference<List<fo>> cachedSpawnersRef = new AtomicReference(Collections.emptyList());
   private int tickTimer = 0;
   private static final String COLOR_CONFIG_FILE = "spawner_esp_colors.json";
   private final Gson gson = (new GsonBuilder()).setPrettyPrinting().create();

   private static String getConfigDir() {
      return FabricLoader.getInstance().getGameDir().resolve("config/clypton").toString();
   }

   public dv() {
      super(db.of(d741("hqa2r7e/qZmOjg==")), db.of(d741("hr64r6r6trO//qyQgJWNgZeVx5+AnoPMjoGDn4OB")), -1, hk.RENDER);
      this.initializeDefaultColors();
      this.initializeToggles();
      this.loadColors();
      ab[] var1 = new ab[]{this.maxRenderDistance, this.alpha, this.mobHeadScale, this.tracers, this.showLabel, this.showMobHead, this.showZombie, this.showSkeleton, this.showSpider, this.showCaveSpider, this.showBlaze, this.showMagmaCube, this.showSilverfish, this.showUnknown};
      this.addSettings(var1);
   }

   private void initializeToggles() {
      this.mobTypeToggles.put(d741("r7m6urC/"), this.showZombie);
      this.mobTypeToggles.put(d741("pr2ytLyutLI="), this.showSkeleton);
      this.mobTypeToggles.put(d741("pqa+vLyo"), this.showSpider);
      this.mobTypeToggles.put(d741("trehvYapq7W5u60="), this.showCaveSpider);
      this.mobTypeToggles.put(d741("t7q2orw="), this.showBlaze);
      this.mobTypeToggles.put(d741("uLewtbiFuKm/uw=="), this.showMagmaCube);
      this.mobTypeToggles.put(d741("pr+7rryovbWutg=="), this.showSilverfish);
      this.mobTypeToggles.put(d741("oLi8trattQ=="), this.showUnknown);
   }

   private boolean isMobTypeEnabled(String var1) {
      ff var2 = (ff)this.mobTypeToggles.get(var1);
      return var2 == null || var2.getValue();
   }

   public Map<String, Color> getMobTypeColors() {
      return this.mobTypeColors;
   }

   private void initializeDefaultColors() {
      this.mobTypeColors.put(d741("r7m6urC/"), new Color(0, 150, 0));
      this.mobTypeColors.put(d741("pr2ytLyutLI="), new Color(200, 200, 200));
      this.mobTypeColors.put(d741("pqa+vLyo"), new Color(50, 50, 50));
      this.mobTypeColors.put(d741("trehvYapq7W5u60="), new Color(0, 100, 100));
      this.mobTypeColors.put(d741("t7q2orw="), new Color(255, 165, 0));
      this.mobTypeColors.put(d741("uLewtbiFuKm/uw=="), new Color(255, 50, 0));
      this.mobTypeColors.put(d741("pr+7rryovbWutg=="), new Color(150, 150, 150));
      this.mobTypeColors.put(d741("oLi8trattQ=="), new Color(128, 128, 128));
   }

   private void loadColors() {
      try {
         Path var1 = Paths.get(getConfigDir(), d741("pqa2r7e/qYO4ra+/go2Pi5eVyYKahYU="));
         if (!Files.exists(var1, new LinkOption[0])) {
            return;
         }

         String var2 = Files.readString(var1);
         JsonObject var3 = JsonParser.parseString(var2).getAsJsonObject();
         Iterator var4 = var3.keySet().iterator();

         while(var4.hasNext()) {
            String var5 = (String)var4.next();

            try {
               JsonObject var6 = var3.getAsJsonObject(var5);
               int var7 = var6.get("r").getAsInt();
               int var8 = var6.get("g").getAsInt();
               int var9 = var6.get("b").getAsInt();
               this.mobTypeColors.put(var5, new Color(var7, var8, var9, 255));
            } catch (Exception var10) {
            }
         }
      } catch (Exception var11) {
      }

   }

   public void saveColors() {
      try {
         Path var1 = Paths.get(getConfigDir());
         if (!Files.exists(var1, new LinkOption[0])) {
            Files.createDirectories(var1);
         }

         JsonObject var2 = new JsonObject();
         Iterator var3 = this.mobTypeColors.entrySet().iterator();

         while(var3.hasNext()) {
            Entry var4 = (Entry)var3.next();
            JsonObject var5 = new JsonObject();
            Color var6 = (Color)var4.getValue();
            var5.addProperty("r", (Number)var6.getRed());
            var5.addProperty("g", (Number)var6.getGreen());
            var5.addProperty("b", (Number)var6.getBlue());
            var2.add((String)var4.getKey(), var5);
         }

         Path var8 = Paths.get(getConfigDir(), d741("pqa2r7e/qYO4ra+/go2Pi5eVyYKahYU="));
         Files.writeString(var8, this.gson.toJson((JsonElement)var2), new OpenOption[0]);
      } catch (Exception var7) {
      }

   }

   public void onEnable() {
      super.onEnable();
      this.cachedSpawnersRef.set(Collections.emptyList());
      this.tickTimer = 0;
   }

   public void onDisable() {
      super.onDisable();
      this.saveColors();
      this.cachedSpawnersRef.set(Collections.emptyList());
   }

   public void onStartTick(hm var1) {
      if (this.isEnabled() && this.tickTimer++ % 10 == 0) {
         this.scanChunks();
      }

   }

   private void scanChunks() {
      if (mc != null && mc.field_1724 != null && mc.field_1687 != null) {
         try {
            ArrayList var1 = new ArrayList();
            class_243 var2 = mc.field_1724.method_19538();
            double var3 = this.maxRenderDistance.getValue();
            double var5 = var3 * var3;
            Iterator var7 = ft.getLoadedChunks().toList().iterator();

            while(var7.hasNext()) {
               class_2818 var8 = (class_2818)var7.next();
               Iterator var9 = var8.method_12021().iterator();

               while(var9.hasNext()) {
                  class_2338 var10 = (class_2338)var9.next();
                  class_2680 var11 = var8.method_8320(var10);
                  if (var11.method_26204() == class_2246.field_10260) {
                     double var12 = var2.field_1352 - ((double)var10.method_10263() + 0.5D);
                     double var14 = var2.field_1351 - ((double)var10.method_10264() + 0.5D);
                     double var16 = var2.field_1350 - ((double)var10.method_10260() + 0.5D);
                     double var18 = var12 * var12 + var14 * var14 + var16 * var16;
                     if (var18 <= var5) {
                        String var20 = this.getMobTypeFromSpawner(var10);
                        if (this.isMobTypeEnabled(var20)) {
                           Color var21 = (Color)this.mobTypeColors.getOrDefault(var20, (Color)this.mobTypeColors.get(d741("oLi8trattQ==")));
                           Color var22 = new Color(var21.getRed(), var21.getGreen(), var21.getBlue(), this.alpha.getIntValue());
                           class_1299 var23 = this.getEntityTypeFromMobName(var20);
                           var1.add(new fo(var10, var20, var22, var23));
                        }
                     }
                  }
               }
            }

            this.cachedSpawnersRef.set(Collections.unmodifiableList(var1));
         } catch (Exception var24) {
         }
      }

   }

   private String getMobTypeFromSpawner(class_2338 var1) {
      try {
         class_2586 var2 = mc.field_1687.method_8321(var1);
         if (var2 instanceof class_2636) {
            class_2636 var3 = (class_2636)var2;
            class_2487 var4 = var3.method_38244(mc.field_1687.method_30349());
            if (var4.method_10545(d741("hqa2r7eeuqi8"))) {
               class_2487 var5 = var4.method_10562(d741("hqa2r7eeuqi8"));
               if (var5.method_10545(d741("sLijsa2j"))) {
                  class_2487 var6 = var5.method_10562(d741("sLijsa2j"));
                  if (var6.method_10545("id")) {
                     String var7 = var6.method_10558("id");
                     return this.extractMobName(var7);
                  }
               }
            }

            if (var4.method_10545(d741("hqa2r7eKtKi4sKuJgI6Q"))) {
               return d741("oLi8trattQ==");
            }
         }
      } catch (Exception var8) {
      }

      return d741("oLi8trattQ==");
   }

   private String extractMobName(String var1) {
      return var1.contains(":") ? var1.split(":")[1] : var1;
   }

   private class_1299<?> getEntityTypeFromMobName(String var1) {
      try {
         class_2960 var2 = class_2960.method_60655(d741("uL+5vbqourqp"), var1);
         return (class_1299)class_7923.field_41177.method_10223(var2);
      } catch (Exception var3) {
         return null;
      }
   }

   public void onRender3D(bg var1) {
      if (mc != null && mc.field_1724 != null && mc.field_1687 != null) {
         List var2 = (List)this.cachedSpawnersRef.get();
         if (!var2.isEmpty()) {
            class_4184 var3 = iq.getCamera();
            if (var3 != null) {
               class_243 var4 = iq.getCameraPos();
               class_4587 var5 = var1.matrixStack;
               Iterator var6 = var2.iterator();

               fo var7;
               while(var6.hasNext()) {
                  var7 = (fo)var6.next();
                  var5.method_22903();
                  var5.method_22907(class_7833.field_40714.rotationDegrees(var3.method_19329()));
                  var5.method_22907(class_7833.field_40716.rotationDegrees(var3.method_19330() + 180.0F));
                  var5.method_22904(-var4.field_1352, -var4.field_1351, -var4.field_1350);
                  iq.renderFilledBox(var5, (float)var7.pos.method_10263(), (float)var7.pos.method_10264(), (float)var7.pos.method_10260(), (float)var7.pos.method_10263() + 1.0F, (float)var7.pos.method_10264() + 1.0F, (float)var7.pos.method_10260() + 1.0F, var7.color);
                  var5.method_22909();
                  if (this.tracers.getValue() && mc.field_1765 != null) {
                     var5.method_22903();
                     var5.method_22907(class_7833.field_40714.rotationDegrees(var3.method_19329()));
                     var5.method_22907(class_7833.field_40716.rotationDegrees(var3.method_19330() + 180.0F));
                     var5.method_22904(-var4.field_1352, -var4.field_1351, -var4.field_1350);
                     class_243 var8 = mc.field_1765.method_17784();
                     Color var9 = ch.safeColor(var7.color.getRed(), var7.color.getGreen(), var7.color.getBlue(), 255);
                     class_243 var10 = new class_243((double)var7.pos.method_10263() + 0.5D, (double)var7.pos.method_10264() + 0.5D, (double)var7.pos.method_10260() + 0.5D);
                     iq.renderLine(var5, var9, var8, var10);
                     var5.method_22909();
                  }

                  if (this.showMobHead.getValue() && var7.entityType != null) {
                     this.renderMobHead(var5, var7.pos, var7.entityType, var4);
                  }
               }

               String var11;
               String var12;
               if (this.showLabel.getValue()) {
                  for(var6 = var2.iterator(); var6.hasNext(); var11 = var12 + " Spawner") {
                     var7 = (fo)var6.next();
                     new class_243((double)var7.pos.method_10263() + 0.5D, (double)var7.pos.method_10264() + 1.2D, (double)var7.pos.method_10260() + 0.5D);
                     var12 = this.capitalizeFirst(var7.mobType);
                  }
               }
            }
         }
      }

   }

   private void renderMobHead(class_4587 var1, class_2338 var2, class_1299<?> var3, class_243 var4) {
      try {
         class_1297 var5 = var3.method_5883(mc.field_1687);
         if (var5 == null) {
            return;
         }

         class_898 var6 = mc.method_1561();
         RenderSystem.disableDepthTest();
         var1.method_22903();
         var1.method_22904((double)var2.method_10263() + 0.5D - var4.field_1352, (double)var2.method_10264() + 0.5D - var4.field_1351, (double)var2.method_10260() + 0.5D - var4.field_1350);
         var1.method_22907(class_7833.field_40716.rotationDegrees(-mc.field_1773.method_19418().method_19330()));
         var1.method_22907(class_7833.field_40714.rotationDegrees(mc.field_1773.method_19418().method_19329()));
         float var7 = this.mobHeadScale.getFloatValue();
         var1.method_22905(-var7, -var7, var7);
         float var8 = this.getHeadOffset(var5);
         var1.method_46416(0.0F, var8, 0.0F);
         var6.method_3954(var5, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F, var1, mc.method_22940().method_23000(), 15728880);
         var1.method_22909();
         RenderSystem.enableDepthTest();
      } catch (Exception var9) {
      }

   }

   private float getHeadOffset(class_1297 var1) {
      if (!(var1 instanceof class_1642) && !(var1 instanceof class_1613)) {
         if (var1 instanceof class_1628) {
            return 0.7F;
         } else if (var1 instanceof class_1549) {
            return 0.4F;
         } else if (var1 instanceof class_1545) {
            return 1.5F;
         } else if (var1 instanceof class_1589) {
            return 0.8F;
         } else {
            return var1 instanceof class_1614 ? 0.3F : 1.2F;
         }
      } else {
         return 1.8F;
      }
   }

   private String capitalizeFirst(String var1) {
      if (var1 != null && !var1.isEmpty()) {
         String var2 = var1.substring(0, 1).toUpperCase();
         return var2 + var1.substring(1).replace("_", " ");
      } else {
         return var1;
      }
   }

   private static String d741(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 5845 + var3 & 255);
      }

      return new String(var2);
   }
}
