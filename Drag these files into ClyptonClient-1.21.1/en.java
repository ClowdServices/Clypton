import client.astralux.mixin.CountPlacementModifierAccessor;
import client.astralux.mixin.HeightRangePlacementModifierAccessor;
import client.astralux.mixin.RarityFilterPlacementModifierAccessor;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;
import net.minecraft.class_1959;
import net.minecraft.class_2794;
import net.minecraft.class_2975;
import net.minecraft.class_3037;
import net.minecraft.class_310;
import net.minecraft.class_3124;
import net.minecraft.class_5317;
import net.minecraft.class_5321;
import net.minecraft.class_5363;
import net.minecraft.class_5539;
import net.minecraft.class_5868;
import net.minecraft.class_5875;
import net.minecraft.class_6016;
import net.minecraft.class_6017;
import net.minecraft.class_6122;
import net.minecraft.class_6793;
import net.minecraft.class_6795;
import net.minecraft.class_6796;
import net.minecraft.class_6797;
import net.minecraft.class_6799;
import net.minecraft.class_6816;
import net.minecraft.class_6880;
import net.minecraft.class_6885;
import net.minecraft.class_7145;
import net.minecraft.class_7510;
import net.minecraft.class_7887;
import net.minecraft.class_7924;
import net.minecraft.class_7225.class_7226;
import net.minecraft.class_7225.class_7874;
import net.minecraft.class_7510.class_6827;

public class en {
   public int step;
   public int index;
   public class_6017 count = class_6016.method_34998(1);
   public class_6122 heightProvider;
   public class_5868 heightContext;
   public float rarity = 1.0F;
   public float discardOnAirChance;
   public int size;
   public Color color;
   public boolean scattered;

   public static Map<class_5321<class_1959>, List<en>> getRegistry(bz dimension) {
      class_7874 registry = class_7887.method_46817();
      class_7226<class_6796> features = registry.method_46762(class_7924.field_41245);
      Map<class_5321<class_5363>, class_5363> reg = ((class_7145)registry.method_46762(class_7924.field_41250).method_46747(class_5317.field_25050).comp_349()).method_45546().comp_1014();
      class_5363 dim = (class_5363)reg.get(class_5363.field_25413);
      Set<class_6880<class_1959>> biomes = dim.comp_1013().method_12098().method_28443();
      List<class_6880<class_1959>> biomes1 = biomes.stream().toList();
      List<class_6827> indexer = class_7510.method_44210(biomes1, (biomeEntry) -> {
         return ((class_1959)biomeEntry.comp_349()).method_30970().method_30983();
      }, true);
      Map<class_6796, en> featureToOre = new HashMap();
      registerOre(featureToOre, indexer, features, class_6816.field_36048, 70 ^ 65, ch.safeColor(192 ^ 17, 27, 245, 255));
      registerOre(featureToOre, indexer, features, class_6816.field_36047, 95 ^ 88, ch.safeColor(195 ^ 18, 104 ^ 115, 245, 23 ^ 232));
      Map<class_5321<class_1959>, List<en>> biomeOreMap = new HashMap();
      biomes1.forEach((biome) -> {
         biomeOreMap.put((class_5321)biome.method_40230().get(), new ArrayList());
         Stream var10000 = ((class_1959)biome.comp_349()).method_30970().method_30983().stream().flatMap(class_6885::method_40239).map(class_6880::comp_349);
         Objects.requireNonNull(featureToOre);
         var10000.filter(featureToOre::containsKey).forEach((feature) -> {
            ((List)biomeOreMap.get(biome.method_40230().get())).add((en)featureToOre.get(feature));
         });
      });
      return biomeOreMap;
   }

   private static void registerOre(Map<class_6796, en> map, List<class_6827> indexer, class_7226<class_6796> oreRegistry, class_5321<class_6796> oreKey, int genStep, Color color) {
      try {
         class_6796 orePlacement = (class_6796)oreRegistry.method_46747(oreKey).comp_349();
         int index = ((class_6827)indexer.get(genStep)).comp_304().applyAsInt(orePlacement);
         en ore = new en(orePlacement, genStep, index, color);
         map.put(orePlacement, ore);
      } catch (Exception var9) {
         System.err.println("Failed to register ore: " + String.valueOf(oreKey.method_29177()));
         var9.printStackTrace();
      }

   }

   private en(class_6796 feature, int step, int index, Color color) {
      this.step = step;
      this.index = index;
      this.color = color;
      Iterator var5 = feature.comp_335().iterator();

      while(var5.hasNext()) {
         class_6797 modifier = (class_6797)var5.next();
         if (modifier instanceof class_6793) {
            this.count = ((CountPlacementModifierAccessor)modifier).getCount();
         } else if (modifier instanceof class_6795) {
            this.heightProvider = ((HeightRangePlacementModifierAccessor)modifier).getHeight();
         } else if (modifier instanceof class_6799) {
            this.rarity = (float)((RarityFilterPlacementModifierAccessor)modifier).getChance();
         }
      }

      class_3037 featureConfig = ((class_2975)feature.comp_334().comp_349()).comp_333();
      if (featureConfig instanceof class_3124) {
         class_3124 oreFeatureConfig = (class_3124)featureConfig;
         this.discardOnAirChance = oreFeatureConfig.field_29064;
         this.size = oreFeatureConfig.field_13723;
         this.scattered = ((class_2975)feature.comp_334().comp_349()).comp_332() instanceof class_5875;
      } else {
         throw new IllegalStateException("Config for " + String.valueOf(feature) + " is not OreFeatureConfig.class");
      }
   }

   public synchronized class_5868 getHeightContext() {
      if (this.heightContext == null) {
         class_310 client = class_310.method_1551();
         if (client.field_1687 != null) {
            int bottom = client.field_1687.method_31607();
            int height = client.field_1687.method_8597().comp_653();
            this.heightContext = new class_5868((class_2794)null, class_5539.method_39034(bottom, height));
         }
      }

      return this.heightContext;
   }

   public boolean isHeightContextReady() {
      return this.heightContext != null;
   }

   // $FF: synthetic method
   private static String d352(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 10392 - 599 + var3 & 255);
      }

      return new String(var2);
   }
}
