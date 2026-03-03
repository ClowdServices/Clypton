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
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicReference;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.class_2338;
import net.minecraft.class_243;
import net.minecraft.class_2580;
import net.minecraft.class_2586;
import net.minecraft.class_2589;
import net.minecraft.class_2595;
import net.minecraft.class_2601;
import net.minecraft.class_2605;
import net.minecraft.class_2608;
import net.minecraft.class_2611;
import net.minecraft.class_2614;
import net.minecraft.class_2627;
import net.minecraft.class_2636;
import net.minecraft.class_2646;
import net.minecraft.class_2669;
import net.minecraft.class_2818;
import net.minecraft.class_3719;
import net.minecraft.class_3722;
import net.minecraft.class_3866;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_7833;
import net.minecraft.class_239.class_240;

public final class if extends bf {
   private final gn alpha = new gn(db.of(d629("MB4DHBQ=")), 1.0D, 255.0D, 125.0D, 1.0D);
   private final ff tracers = (new ff(db.of(d629("JQASFxAEBA==")), false)).setDescription(db.of(d629("NQASAwZWFlgVExUZXRgN7+yi+uvw9Kf45evy6f+u+/+x5vvxteXj9+v7/Pm9/PPPwsk=")));
   private final ff clusters = (new ff(db.of(d629("Mh4GBwETBQs=")), true)).setDescription(db.of(d629("NgAcAQUFVxYcGwkeBF4M9O7w4uPgpuXk5ung/6364Pf05vvx5w==")));
   private final gn clusterDistance = new gn(db.of(d629("Mh4GBwETBVg9EwgIHBAc5Q==")), 1.0D, 20.0D, 8.0D, 1.0D);
   private final ff chests = new ff(db.of(d629("MhoWBwEF")), true);
   private final ff enderChests = new ff(db.of(d629("NBwXEQdWNBAcCQ8P")), true);
   private final ff spawners = new ff(db.of(d629("IgISAxsTBQs=")), true);
   private final ff shulkerBoxes = new ff(db.of(d629("IhoGGB4TBVg7FQMZDg==")), true);
   private final ff furnaces = new ff(db.of(d629("NwcBGhQVEgs=")), true);
   private final ff barrels = new ff(db.of(d629("MxMBBhAaBA==")), true);
   private final ff hoppers = new ff(db.of(d629("OR0DBBAEBA==")), true);
   private final ff droppers = new ff(db.of(d629("NQAcBAUTBQs=")), true);
   private final ff dispensers = new ff(db.of(d629("NRsABBAYBB0LCQ==")), true);
   private final ff enchant = new ff(db.of(d629("NBwQHBQYAxEXHVsoHBwT5fI=")), true);
   private final ff brewingStands = new ff(db.of(d629("MwAWAxwYEFgqDhoSGQ0=")), true);
   private final ff anvils = new ff(db.of(d629("MBwFHRkF")), true);
   private final ff beacons = new ff(db.of(d629("MxcSFxoYBA==")), true);
   private final ff craftingTables = new ff(db.of(d629("MgASEgEfGR9ZLhoeERsM")), false);
   private final ff lecterns = new ff(db.of(d629("PRcQABAEGQs=")), false);
   private final ff pistons = new ff(db.of(d629("IRsAABoYBA==")), false);
   private final gt storageColorsSetting = new gt(db.of(d629("Mh0fGwcF")));
   private final AtomicReference<List<ex>> cachedStorages = new AtomicReference(Collections.emptyList());
   private final AtomicReference<List<hv>> cachedClusters = new AtomicReference(Collections.emptyList());
   private int tickTimer = 0;
   private static final String COLOR_CONFIG_FILE = "storageesp_colors.json";
   private final Gson gson = (new GsonBuilder()).setPrettyPrinting().create();

   private static String getConfigDir() {
      return FabricLoader.getInstance().getGameDir().resolve("config/clypton").toString();
   }

   public if() {
      super(db.of(d629("IgYcBhQRElg8KSs=")), db.of(d629("IxcdEBAEBFgKDhQOHBkaoOPu7Ofu9af84fjk+ermr+fw/v/n")), -1, hk.RENDER);
      ab[] var1 = new ab[]{this.alpha, this.tracers, this.clusters, this.clusterDistance, this.storageColorsSetting, this.chests, this.enderChests, this.spawners, this.shulkerBoxes, this.furnaces, this.barrels, this.hoppers, this.droppers, this.dispensers, this.enchant, this.brewingStands, this.anvils, this.beacons, this.craftingTables, this.lecterns, this.pistons};
      this.addSettings(var1);
      this.initializeDefaultColors();
      this.loadColors();
   }

   private void initializeDefaultColors() {
      Map var1 = this.storageColorsSetting.getColors();
      if (var1.isEmpty()) {
         this.storageColorsSetting.setColor(d629("BQASBAUTEycaEh4PCQ=="), new Color(200, 91, 0));
         this.storageColorsSetting.setColor(d629("EhoWBwE="), new Color(156, 91, 0));
         this.storageColorsSetting.setColor(d629("FBwXEQcpFBAcCQ8="), new Color(117, 0, 255));
         this.storageColorsSetting.setColor(d629("AgISAxsTBQ=="), new Color(138, 126, 166));
         this.storageColorsSetting.setColor(d629("AhoGGB4TBScbFQM="), new Color(134, 0, 158));
         this.storageColorsSetting.setColor(d629("FwcBGhQVEg=="), new Color(125, 125, 125));
         this.storageColorsSetting.setColor(d629("ExMBBhAa"), new Color(255, 140, 140));
         this.storageColorsSetting.setColor(d629("GR0DBBAE"), new Color(50, 50, 50));
         this.storageColorsSetting.setColor(d629("FQAcBAUTBQ=="), new Color(128, 128, 128));
         this.storageColorsSetting.setColor(d629("FRsABBAYBB0L"), new Color(100, 100, 100));
         this.storageColorsSetting.setColor(d629("FBwQHBQYAxEXHSQIHBwT5Q=="), new Color(80, 80, 255));
         this.storageColorsSetting.setColor(d629("EwAWAxwYECcKDhoSGQ=="), new Color(255, 200, 100));
         this.storageColorsSetting.setColor(d629("ExcSFxoY"), new Color(0, 255, 255));
         this.storageColorsSetting.setColor(d629("HRcQABAEGQ=="), new Color(139, 69, 19));
         this.storageColorsSetting.setColor(d629("ARsAABoY"), new Color(35, 226, 0));
      }

   }

   private void loadColors() {
      try {
         Path var1 = Paths.get(getConfigDir(), d629("AgYcBhQREh0KCiQfEhIQ8vKs6ffq6A=="));
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
               this.storageColorsSetting.setColor(var5, new Color(var7, var8, var9, 255));
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
         Map var3 = this.storageColorsSetting.getColors();
         Iterator var4 = var3.entrySet().iterator();

         while(var4.hasNext()) {
            Entry var5 = (Entry)var4.next();
            JsonObject var6 = new JsonObject();
            Color var7 = (Color)var5.getValue();
            var6.addProperty("r", (Number)var7.getRed());
            var6.addProperty("g", (Number)var7.getGreen());
            var6.addProperty("b", (Number)var7.getBlue());
            var2.add((String)var5.getKey(), var6);
         }

         Path var9 = Paths.get(getConfigDir(), d629("AgYcBhQREh0KCiQfEhIQ8vKs6ffq6A=="));
         Files.writeString(var9, this.gson.toJson((JsonElement)var2), new OpenOption[0]);
      } catch (Exception var8) {
      }

   }

   public Map<String, Color> getStorageColors() {
      return this.storageColorsSetting.getColors();
   }

   public void onEnable() {
      super.onEnable();
      this.cachedStorages.set(Collections.emptyList());
      this.cachedClusters.set(Collections.emptyList());
      this.tickTimer = 0;
   }

   public void onDisable() {
      super.onDisable();
      this.saveColors();
      this.cachedStorages.set(Collections.emptyList());
      this.cachedClusters.set(Collections.emptyList());
   }

   public void onStartTick(hm var1) {
      if (this.isEnabled() && this.tickTimer++ % 10 == 0) {
         this.scanEntities();
      }

   }

   private class_243 getCrosshairPositionForESP(float var1) {
      bf var2 = mc.field_1724 == null ? null : Astralux.INSTANCE.getModuleManager().getModuleByClass(cj.class);
      if (var2 != null && var2.isEnabled()) {
         cj var3 = (cj)var2;
         if (mc.field_1765 != null && mc.field_1765.method_17783() != class_240.field_1333) {
            return mc.field_1765.method_17784();
         } else {
            class_243 var4 = new class_243(var3.getInterpolatedX(var1), var3.getInterpolatedY(var1), var3.getInterpolatedZ(var1));
            class_243 var5 = class_243.method_1030((float)var3.getInterpolatedPitch(var1), (float)var3.getInterpolatedYaw(var1));
            return var4.method_1019(var5.method_1021(4.0D));
         }
      } else {
         return mc.field_1765 != null ? mc.field_1765.method_17784() : mc.field_1724.method_5828(1.0F).method_1021(4.0D).method_1019(mc.field_1724.method_19538());
      }
   }

   public void onRender3D(bg var1) {
      this.renderStorages(var1);
   }

   private void renderStorages(bg var1) {
      if (mc != null && mc.field_1724 != null && mc.field_1687 != null) {
         class_4184 var2 = iq.getCamera();
         if (var2 != null) {
            class_243 var3 = iq.getCameraPos();
            class_4587 var4 = var1.matrixStack;
            var4.method_22903();
            var4.method_22907(class_7833.field_40714.rotationDegrees(var2.method_19329()));
            var4.method_22907(class_7833.field_40716.rotationDegrees(var2.method_19330() + 180.0F));
            var4.method_22904(-var3.field_1352, -var3.field_1351, -var3.field_1350);
            int var5 = this.alpha.getIntValue();
            class_243 var6 = this.getCrosshairPositionForESP(var1.tickDelta);
            List var7;
            Iterator var8;
            Color var9;
            class_243 var10;
            Color var11;
            if (this.clusters.getValue()) {
               var7 = (List)this.cachedClusters.get();
               var8 = var7.iterator();

               while(var8.hasNext()) {
                  hv var13 = (hv)var8.next();
                  var9 = ch.safeColor(var13.r, var13.g, var13.b, var5);
                  iq.renderFilledBox(var4, (float)var13.minX + 0.05F, (float)var13.minY + 0.05F, (float)var13.minZ + 0.05F, (float)var13.maxX + 0.95F, (float)var13.maxY + 0.95F, (float)var13.maxZ + 0.95F, var9);
                  if (this.tracers.getValue()) {
                     var10 = new class_243((double)var13.centerX + 0.5D, (double)var13.centerY + 0.5D, (double)var13.centerZ + 0.5D);
                     var11 = ch.safeColor(var13.r, var13.g, var13.b, 255);
                     iq.renderLine(var4, var11, var6, var10);
                  }
               }
            } else {
               var7 = (List)this.cachedStorages.get();
               var8 = var7.iterator();

               while(var8.hasNext()) {
                  ex var12 = (ex)var8.next();
                  var9 = ch.safeColor(var12.r, var12.g, var12.b, var5);
                  iq.renderFilledBox(var4, (float)var12.x + 0.1F, (float)var12.y + 0.05F, (float)var12.z + 0.1F, (float)var12.x + 0.9F, (float)var12.y + 0.85F, (float)var12.z + 0.9F, var9);
                  if (this.tracers.getValue()) {
                     var10 = new class_243((double)var12.x + 0.5D, (double)var12.y + 0.5D, (double)var12.z + 0.5D);
                     var11 = ch.safeColor(var12.r, var12.g, var12.b, 255);
                     iq.renderLine(var4, var11, var6, var10);
                  }
               }
            }

            var4.method_22909();
         }
      }

   }

   private void scanEntities() {
      if (mc != null && mc.field_1724 != null && mc.field_1687 != null) {
         try {
            ArrayList var1 = new ArrayList();
            class_243 var2 = mc.field_1724.method_19538();
            double var3 = 27225.0D;
            Map var5 = this.storageColorsSetting.getColors();
            Iterator var6 = ft.getLoadedChunks().toList().iterator();

            while(var6.hasNext()) {
               class_2818 var7 = (class_2818)var6.next();
               Iterator var8 = var7.method_12021().iterator();

               while(var8.hasNext()) {
                  class_2338 var9 = (class_2338)var8.next();
                  double var10 = var2.field_1352 - ((double)var9.method_10263() + 0.5D);
                  double var12 = var2.field_1351 - ((double)var9.method_10264() + 0.5D);
                  double var14 = var2.field_1350 - ((double)var9.method_10260() + 0.5D);
                  double var16 = var10 * var10 + var12 * var12 + var14 * var14;
                  if (!(var16 > var3)) {
                     class_2586 var18 = mc.field_1687.method_8321(var9);
                     if (var18 != null && this.shouldRender(var18)) {
                        String var19 = getEntityType(var18);
                        Color var20 = (Color)var5.getOrDefault(var19, new Color(255, 255, 255));
                        var1.add(new ex(var9, var20));
                     }
                  }
               }
            }

            this.cachedStorages.set(Collections.unmodifiableList(var1));
            if (this.clusters.getValue()) {
               this.cachedClusters.set(Collections.unmodifiableList(this.buildClusters(var1)));
            }
         } catch (Exception var21) {
         }
      }

   }

   private static String getEntityType(class_2586 var0) {
      if (var0 instanceof class_2646) {
         return d629("BQASBAUTEycaEh4PCQ==");
      } else if (var0 instanceof class_2595) {
         return d629("EhoWBwE=");
      } else if (var0 instanceof class_2611) {
         return d629("FBwXEQcpFBAcCQ8=");
      } else if (var0 instanceof class_2636) {
         return d629("AgISAxsTBQ==");
      } else if (var0 instanceof class_2627) {
         return d629("AhoGGB4TBScbFQM=");
      } else if (var0 instanceof class_3866) {
         return d629("FwcBGhQVEg==");
      } else if (var0 instanceof class_3719) {
         return d629("ExMBBhAa");
      } else if (var0 instanceof class_2614) {
         return d629("GR0DBBAE");
      } else if (var0 instanceof class_2608) {
         return d629("FQAcBAUTBQ==");
      } else if (var0 instanceof class_2601) {
         return d629("FRsABBAYBB0L");
      } else if (var0 instanceof class_2605) {
         return d629("FBwQHBQYAxEXHSQIHBwT5Q==");
      } else if (var0 instanceof class_2589) {
         return d629("EwAWAxwYECcKDhoSGQ==");
      } else if (var0 instanceof class_2580) {
         return d629("ExcSFxoY");
      } else if (var0 instanceof class_3722) {
         return d629("HRcQABAEGQ==");
      } else {
         return var0 instanceof class_2669 ? d629("ARsAABoY") : d629("BBwYGhoBGQ==");
      }
   }

   private boolean shouldRender(class_2586 var1) {
      if (var1 instanceof class_2646 && this.chests.getValue()) {
         return true;
      } else if (var1 instanceof class_2595 && this.chests.getValue()) {
         return true;
      } else if (var1 instanceof class_2611 && this.enderChests.getValue()) {
         return true;
      } else if (var1 instanceof class_2636 && this.spawners.getValue()) {
         return true;
      } else if (var1 instanceof class_2627 && this.shulkerBoxes.getValue()) {
         return true;
      } else if (var1 instanceof class_3866 && this.furnaces.getValue()) {
         return true;
      } else if (var1 instanceof class_3719 && this.barrels.getValue()) {
         return true;
      } else if (var1 instanceof class_2614 && this.hoppers.getValue()) {
         return true;
      } else if (var1 instanceof class_2608 && this.droppers.getValue()) {
         return true;
      } else if (var1 instanceof class_2601 && this.dispensers.getValue()) {
         return true;
      } else if (var1 instanceof class_2605 && this.enchant.getValue()) {
         return true;
      } else if (var1 instanceof class_2589 && this.brewingStands.getValue()) {
         return true;
      } else if (var1 instanceof class_2580 && this.beacons.getValue()) {
         return true;
      } else if (var1 instanceof class_3722 && this.lecterns.getValue()) {
         return true;
      } else {
         return var1 instanceof class_2669 && this.pistons.getValue();
      }
   }

   private List<hv> buildClusters(List<ex> var1) {
      if (var1.isEmpty()) {
         return Collections.emptyList();
      } else {
         ArrayList var2 = new ArrayList();
         HashSet var3 = new HashSet(var1);

         while(!var3.isEmpty()) {
            ex var4 = (ex)var3.iterator().next();
            List var5 = this.floodFill(var4, var3);
            var2.add(new hv(var5));
         }

         return var2;
      }
   }

   private List<ex> floodFill(ex var1, Set<ex> var2) {
      ArrayList var3 = new ArrayList();
      LinkedList var4 = new LinkedList();
      HashSet var5 = new HashSet();
      double var6 = this.clusterDistance.getValue() * this.clusterDistance.getValue();
      var4.add(var1);
      var5.add(var1);
      var2.remove(var1);

      while(!var4.isEmpty()) {
         ex var8 = (ex)var4.poll();
         var3.add(var8);
         ArrayList var9 = new ArrayList();
         Iterator var10 = var2.iterator();

         while(var10.hasNext()) {
            ex var11 = (ex)var10.next();
            if (!var5.contains(var11)) {
               double var12 = (double)(var8.x - var11.x);
               double var14 = (double)(var8.y - var11.y);
               double var16 = (double)(var8.z - var11.z);
               double var18 = var12 * var12 + var14 * var14 + var16 * var16;
               if (var18 <= var6) {
                  var5.add(var11);
                  var9.add(var11);
               }
            }
         }

         var4.addAll(var9);
         var2.removeAll(var5);
      }

      return var3;
   }

   private static String d629(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 3185 + var3 & 255);
      }

      return new String(var2);
   }
}
