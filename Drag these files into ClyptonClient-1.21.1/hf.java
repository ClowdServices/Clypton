import client.astralux.Astralux;
import java.awt.Color;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.class_1011;
import net.minecraft.class_1043;
import net.minecraft.class_1291;
import net.minecraft.class_1293;
import net.minecraft.class_1799;
import net.minecraft.class_243;
import net.minecraft.class_2960;
import net.minecraft.class_332;
import net.minecraft.class_3532;
import net.minecraft.class_640;
import net.minecraft.class_742;

public final class hf extends bf {
   private static final CharSequence watermarkText = db.of(d774("raOpoaa8ug=="));
   private static final SimpleDateFormat timeFormatter = new SimpleDateFormat(d774("pqfKnJ8="));
   private static class_2960 logoTexture = null;
   private final Map<bf, Float> moduleAnimations = new HashMap();
   private final Map<bf, Float> moduleSlideAnimations = new HashMap();
   private int sessionKills = 0;
   private int sessionDamageDealt = 0;
   private boolean wasWPressed = false;
   private boolean wasAPressed = false;
   private boolean wasSPressed = false;
   private boolean wasDPressed = false;
   private boolean wasSpacePressed = false;
   private boolean wasLMBPressed = false;
   private boolean wasRMBPressed = false;
   public boolean showWatermark = true;
   public boolean showInfo = true;
   public boolean showModules = true;
   public boolean showTime = true;
   public boolean showCoordinates = true;
   public boolean showLogo = true;
   public boolean showRadar = true;
   public boolean showEffects = true;
   public boolean showHotbar = true;
   public boolean showKeystrokes = true;
   public boolean showCompass = true;
   public boolean showCombatStats = true;
   public boolean showArmorDurability = true;
   public boolean enableRainbow = false;
   public boolean enableAnimations = true;
   public float rainbowSpeed = 2.5F;
   public float opacity = 0.9F;
   public float cornerRadius = 6.0F;
   public it sortMode;
   public int watermarkX;
   public int watermarkY;
   public int logoX;
   public int logoY;
   public int infoX;
   public int infoY;
   public int timeX;
   public int timeY;
   public int coordsX;
   public int coordsY;
   public int moduleListX;
   public int moduleListY;
   public int radarX;
   public int radarY;
   public int effectsX;
   public int effectsY;
   public int hotbarX;
   public int hotbarY;
   public int keystrokesX;
   public int keystrokesY;
   public int compassX;
   public int compassY;
   public int combatStatsX;
   public int combatStatsY;
   public int armorDurabilityX;
   public int armorDurabilityY;
   public int logoSize;
   public int radarSize;
   public int radarRange;

   public hf() {
      super(db.of(d774("prq0")), db.of(d774("vIqUlIGak5uTk9iUlZ+Zj5DfSFRG")), -1, hk.RENDER);
      this.sortMode = it.LENGTH;
      this.watermarkX = 10;
      this.watermarkY = 10;
      this.logoX = 10;
      this.logoY = 10;
      this.infoX = 10;
      this.infoY = 80;
      this.timeX = 960;
      this.timeY = 10;
      this.coordsX = 182 ^ 188;
      this.coordsY = 1000;
      this.moduleListX = 1700;
      this.moduleListY = 10;
      this.radarX = 1706 ^ 216;
      this.radarY = 859 ^ 9;
      this.effectsX = 1700;
      this.effectsY = 10;
      this.hotbarX = 800;
      this.hotbarY = 799 ^ 169;
      this.keystrokesX = 1650;
      this.keystrokesY = 700;
      this.compassX = 960;
      this.compassY = 50;
      this.combatStatsX = 10;
      this.combatStatsY = 150;
      this.armorDurabilityX = 10;
      this.armorDurabilityY = 950;
      this.logoSize = 56;
      this.radarSize = 140;
      this.radarRange = 217 ^ 233;
   }

   private void loadLogo() {
      (new Thread(() -> {
         try {
            String[] paths = new String[]{"/assets/clypton/textures/icon.png", "/assets/clypton/icon.png", "/icon.png"};
            InputStream stream = null;
            String[] var3 = paths;
            int var4 = paths.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               String path = var3[var5];
               stream = this.getClass().getResourceAsStream(path);
               if (stream != null) {
                  break;
               }
            }

            if (stream != null) {
               class_1011 image = class_1011.method_4309(stream);
               mc.execute(() -> {
                  logoTexture = mc.method_1531().method_4617(d774("j5yEg5OfgY2pm5eelQ=="), new class_1043(image));
               });
               stream.close();
            }
         } catch (Exception var7) {
            System.err.println("Failed to load logo: " + var7.getMessage());
         }

      }, d774("r5yEg5OfgY3Wu5eelduwkp+bZXM="))).start();
   }

   public void onEnable() {
      super.onEnable();
      if (logoTexture == null) {
         this.loadLogo();
      }

      this.loadConfig();
   }

   public void saveConfig() {
      Astralux.INSTANCE.getConfigManager().set(d774("hpqU356ck5qu"), this.logoX);
      Astralux.INSTANCE.getConfigManager().set(d774("hpqU356ck5qv"), this.logoY);
      Astralux.INSTANCE.getConfigManager().set(d774("hpqU34WSgJCEmpmLkaM="), this.watermarkX);
      Astralux.INSTANCE.getConfigManager().set(d774("hpqU34WSgJCEmpmLkaI="), this.watermarkY);
      Astralux.INSTANCE.getConfigManager().set(d774("hpqU35udkpqu"), this.infoX);
      Astralux.INSTANCE.getConfigManager().set(d774("hpqU35udkpqv"), this.infoY);
      Astralux.INSTANCE.getConfigManager().set(d774("hpqU34aamZCu"), this.timeX);
      Astralux.INSTANCE.getConfigManager().set(d774("hpqU34aamZCv"), this.timeY);
      Astralux.INSTANCE.getConfigManager().set(d774("hpqU35Gcm4eShKA="), this.coordsX);
      Astralux.INSTANCE.getConfigManager().set(d774("hpqU35Gcm4eShKE="), this.coordsY);
      Astralux.INSTANCE.getConfigManager().set(d774("hpqU35+ckICakrSQiY+k"), this.moduleListX);
      Astralux.INSTANCE.getConfigManager().set(d774("hpqU35+ckICakrSQiY+l"), this.moduleListY);
      Astralux.INSTANCE.getConfigManager().set(d774("hpqU34CSkJSErw=="), this.radarX);
      Astralux.INSTANCE.getConfigManager().set(d774("hpqU34CSkJSErg=="), this.radarY);
      Astralux.INSTANCE.getConfigManager().set(d774("hpqU35eVkpCVg4uh"), this.effectsX);
      Astralux.INSTANCE.getConfigManager().set(d774("hpqU35eVkpCVg4ug"), this.effectsY);
      Astralux.INSTANCE.getConfigManager().set(d774("hpqU35qcgJeXhaA="), this.hotbarX);
      Astralux.INSTANCE.getConfigManager().set(d774("hpqU35qcgJeXhaE="), this.hotbarY);
      Astralux.INSTANCE.getConfigManager().set(d774("hpqU35mWjYaChZeSn4ik"), this.keystrokesX);
      Astralux.INSTANCE.getConfigManager().set(d774("hpqU35mWjYaChZeSn4il"), this.keystrokesY);
      Astralux.INSTANCE.getConfigManager().set(d774("hpqU35GcmYWXhIuh"), this.compassX);
      Astralux.INSTANCE.getConfigManager().set(d774("hpqU35GcmYWXhIug"), this.compassY);
      Astralux.INSTANCE.getConfigManager().set(d774("hpqU35GcmZeXg6uNm4+PpQ=="), this.combatStatsX);
      Astralux.INSTANCE.getConfigManager().set(d774("hpqU35GcmZeXg6uNm4+PpA=="), this.combatStatsY);
      Astralux.INSTANCE.getConfigManager().set(d774("hpqU35OBmZqEs42Lm5mVkZeLeVk="), this.armorDurabilityX);
      Astralux.INSTANCE.getConfigManager().set(d774("hpqU35OBmZqEs42Lm5mVkZeLeVg="), this.armorDurabilityY);
      Astralux.INSTANCE.getConfigManager().set(d774("hpqU356ck5qlnoKc"), this.logoSize);
      Astralux.INSTANCE.getConfigManager().set(d774("hpqU34CSkJSEpJGDnw=="), this.radarSize);
      Astralux.INSTANCE.getConfigManager().set(d774("hpqU34CSkJSEpZmXnZ4="), this.radarRange);
      Astralux.INSTANCE.getConfigManager().set(d774("hpqU35edlZeakqqYk5Wekok="), this.enableRainbow);
      Astralux.INSTANCE.getConfigManager().set(d774("hpqU35edlZeakrmXk5adiZeQbnI="), this.enableAnimations);
      Astralux.INSTANCE.getConfigManager().set(d774("hpqU352DlZafg4E="), this.opacity);
      Astralux.INSTANCE.getConfigManager().set(d774("hpqU35GchpuThaqYnpKJjg=="), this.cornerRadius);
      Astralux.INSTANCE.getConfigManager().set(d774("hpqU34CSnZuUmI+qip6ZmQ=="), this.rainbowSpeed);
      Astralux.INSTANCE.getConfigManager().save();
   }

   public void loadConfig() {
      this.logoX = Astralux.INSTANCE.getConfigManager().getInt(d774("hpqU356ck5qu"), this.logoX);
      this.logoY = Astralux.INSTANCE.getConfigManager().getInt(d774("hpqU356ck5qv"), this.logoY);
      this.watermarkX = Astralux.INSTANCE.getConfigManager().getInt(d774("hpqU34WSgJCEmpmLkaM="), this.watermarkX);
      this.watermarkY = Astralux.INSTANCE.getConfigManager().getInt(d774("hpqU34WSgJCEmpmLkaI="), this.watermarkY);
      this.infoX = Astralux.INSTANCE.getConfigManager().getInt(d774("hpqU35udkpqu"), this.infoX);
      this.infoY = Astralux.INSTANCE.getConfigManager().getInt(d774("hpqU35udkpqv"), this.infoY);
      this.timeX = Astralux.INSTANCE.getConfigManager().getInt(d774("hpqU34aamZCu"), this.timeX);
      this.timeY = Astralux.INSTANCE.getConfigManager().getInt(d774("hpqU34aamZCv"), this.timeY);
      this.coordsX = Astralux.INSTANCE.getConfigManager().getInt(d774("hpqU35Gcm4eShKA="), this.coordsX);
      this.coordsY = Astralux.INSTANCE.getConfigManager().getInt(d774("hpqU35Gcm4eShKE="), this.coordsY);
      this.moduleListX = Astralux.INSTANCE.getConfigManager().getInt(d774("hpqU35+ckICakrSQiY+k"), this.moduleListX);
      this.moduleListY = Astralux.INSTANCE.getConfigManager().getInt(d774("hpqU35+ckICakrSQiY+l"), this.moduleListY);
      this.radarX = Astralux.INSTANCE.getConfigManager().getInt(d774("hpqU34CSkJSErw=="), this.radarX);
      this.radarY = Astralux.INSTANCE.getConfigManager().getInt(d774("hpqU34CSkJSErg=="), this.radarY);
      this.effectsX = Astralux.INSTANCE.getConfigManager().getInt(d774("hpqU35eVkpCVg4uh"), this.effectsX);
      this.effectsY = Astralux.INSTANCE.getConfigManager().getInt(d774("hpqU35eVkpCVg4ug"), this.effectsY);
      this.hotbarX = Astralux.INSTANCE.getConfigManager().getInt(d774("hpqU35qcgJeXhaA="), this.hotbarX);
      this.hotbarY = Astralux.INSTANCE.getConfigManager().getInt(d774("hpqU35qcgJeXhaE="), this.hotbarY);
      this.keystrokesX = Astralux.INSTANCE.getConfigManager().getInt(d774("hpqU35mWjYaChZeSn4ik"), this.keystrokesX);
      this.keystrokesY = Astralux.INSTANCE.getConfigManager().getInt(d774("hpqU35mWjYaChZeSn4il"), this.keystrokesY);
      this.compassX = Astralux.INSTANCE.getConfigManager().getInt(d774("hpqU35GcmYWXhIuh"), this.compassX);
      this.compassY = Astralux.INSTANCE.getConfigManager().getInt(d774("hpqU35GcmYWXhIug"), this.compassY);
      this.combatStatsX = Astralux.INSTANCE.getConfigManager().getInt(d774("hpqU35GcmZeXg6uNm4+PpQ=="), this.combatStatsX);
      this.combatStatsY = Astralux.INSTANCE.getConfigManager().getInt(d774("hpqU35GcmZeXg6uNm4+PpA=="), this.combatStatsY);
      this.armorDurabilityX = Astralux.INSTANCE.getConfigManager().getInt(d774("hpqU35OBmZqEs42Lm5mVkZeLeVk="), this.armorDurabilityX);
      this.armorDurabilityY = Astralux.INSTANCE.getConfigManager().getInt(d774("hpqU35OBmZqEs42Lm5mVkZeLeVg="), this.armorDurabilityY);
      this.logoSize = Astralux.INSTANCE.getConfigManager().getInt(d774("hpqU356ck5qlnoKc"), this.logoSize);
      this.radarSize = Astralux.INSTANCE.getConfigManager().getInt(d774("hpqU34CSkJSEpJGDnw=="), this.radarSize);
      this.radarRange = Astralux.INSTANCE.getConfigManager().getInt(d774("hpqU34CSkJSEpZmXnZ4="), this.radarRange);
      this.enableRainbow = Astralux.INSTANCE.getConfigManager().getBoolean(d774("hpqU35edlZeakqqYk5Wekok="), this.enableRainbow);
      this.enableAnimations = Astralux.INSTANCE.getConfigManager().getBoolean(d774("hpqU35edlZeakrmXk5adiZeQbnI="), this.enableAnimations);
      this.opacity = (float)Astralux.INSTANCE.getConfigManager().getDouble(d774("hpqU352DlZafg4E="), (double)this.opacity);
      this.cornerRadius = (float)Astralux.INSTANCE.getConfigManager().getDouble(d774("hpqU35GchpuThaqYnpKJjg=="), (double)this.cornerRadius);
      this.rainbowSpeed = (float)Astralux.INSTANCE.getConfigManager().getDouble(d774("hpqU34CSnZuUmI+qip6ZmQ=="), (double)this.rainbowSpeed);
   }

   @cp
   public void onRender2D(ez event) {
      if (mc.field_1755 != Astralux.INSTANCE.GUI) {
         class_332 context = event.context;
         iq.unscaledProjection();
         if (this.showLogo && logoTexture != null) {
            this.renderLogo(context, this.logoX, this.logoY);
         }

         if (this.showWatermark) {
            this.renderWatermark(context, this.watermarkX, this.watermarkY);
         }

         if (this.showInfo && mc.field_1724 != null) {
            this.renderInfo(context, this.infoX, this.infoY);
         }

         if (this.showTime) {
            this.renderTime(context, this.timeX, this.timeY);
         }

         if (this.showModules) {
            this.renderModuleList(context, this.moduleListX, this.moduleListY);
         }

         if (this.showRadar && mc.field_1724 != null && mc.field_1687 != null) {
            this.renderRadar(context, this.radarX, this.radarY);
         }

         if (this.showCoordinates && mc.field_1724 != null) {
            this.renderCoordinates(context, this.coordsX, this.coordsY);
         }

         if (this.showHotbar && mc.field_1724 != null) {
            this.renderHotbar(context, this.hotbarX, this.hotbarY);
         }

         if (this.showEffects && mc.field_1724 != null) {
            this.renderEffects(context, this.effectsX, this.effectsY);
         }

         if (this.showKeystrokes) {
            this.renderKeystrokes(context, this.keystrokesX, this.keystrokesY);
         }

         if (this.showCompass && mc.field_1724 != null) {
            this.renderCompass(context, this.compassX, this.compassY);
         }

         if (this.showCombatStats) {
            this.renderCombatStats(context, this.combatStatsX, this.combatStatsY);
         }

         if (this.showArmorDurability && mc.field_1724 != null) {
            this.renderArmorDurability(context, this.armorDurabilityX, this.armorDurabilityY);
         }

         iq.scaledProjection();
      }
   }

   private void renderLogo(class_332 context, int x, int y) {
      int size = this.logoSize;
      Color accentColor = this.enableRainbow ? this.getRainbowColor(0) : aq.getPrimaryAccent();
      iq.renderRoundedOutline(context, accentColor, (double)(x - 2), (double)(y - 2), (double)(x + size + 2), (double)(y + size + 2), (double)this.cornerRadius, (double)this.cornerRadius, (double)this.cornerRadius, (double)this.cornerRadius, 2.0D, 20.0D);
      context.method_25290(logoTexture, x, y, 0.0F, 0.0F, size, size, size, size);
   }

   private void renderWatermark(class_332 context, int x, int y) {
      int textWidth = i.getWidth(watermarkText);
      int height = 24;
      int width = textWidth + 20;
      Color bgColor = ch.safeColor(10, 238 ^ 228, 184 ^ 183, (int)(this.opacity * 200.0F));
      Color accentColor = this.enableRainbow ? this.getRainbowColor(0) : aq.getPrimaryAccent();
      iq.renderRoundedQuad(context.method_51448(), bgColor, (double)x, (double)y, (double)(x + width), (double)(y + height), (double)this.cornerRadius, 20.0D);
      iq.renderRoundedOutline(context, accentColor, (double)x, (double)y, (double)(x + width), (double)(y + height), (double)this.cornerRadius, (double)this.cornerRadius, (double)this.cornerRadius, (double)this.cornerRadius, 1.5D, 20.0D);
      i.drawString(watermarkText, context, x + (48 ^ 58), y + (78 ^ 70), accentColor.getRGB());
   }

   private void renderInfo(class_332 context, int x, int y) {
      String fps = mc.method_47599() + " fps";
      String ping = this.getPingInfo();
      int maxWidth = Math.max(i.getWidth(fps), i.getWidth(ping));
      int width = maxWidth + (43 ^ 63);
      int height = 42;
      Color bgColor = ch.safeColor(10, 10, 15, (int)(this.opacity * 200.0F));
      iq.renderRoundedQuad(context.method_51448(), bgColor, (double)x, (double)y, (double)(x + width), (double)(y + height), (double)this.cornerRadius, 20.0D);
      Color fpsColor = mc.method_47599() >= 60 ? ch.safeColor(55 ^ 79, 197 ^ 58, 140, 255) : ch.safeColor(255, 247 ^ 63, 120, 214 ^ 41);
      i.drawString(fps, context, x + 10, y + 8, fpsColor.getRGB());
      i.drawString(ping, context, x + (131 ^ 137), y + 24, ch.safeColor(224 ^ 84, 180, 200, 255).getRGB());
   }

   private void renderTime(class_332 context, int x, int y) {
      String time = timeFormatter.format(new Date());
      int textWidth = i.getWidth(time);
      int width = textWidth + (115 ^ 103);
      int height = 64 ^ 88;
      Color bgColor = ch.safeColor(10, 10, 15, (int)(this.opacity * 200.0F));
      Color accentColor = this.enableRainbow ? this.getRainbowColor(100) : aq.getPrimaryAccent();
      iq.renderRoundedQuad(context.method_51448(), bgColor, (double)x, (double)y, (double)(x + width), (double)(y + height), (double)this.cornerRadius, 20.0D);
      i.drawString(time, context, x + 10, y + 8, accentColor.getRGB());
   }

   private void renderCoordinates(class_332 context, int x, int y) {
      int xCoord = (int)mc.field_1724.method_23317();
      int yCoord = (int)mc.field_1724.method_23318();
      int zCoord = (int)mc.field_1724.method_23321();
      String coordText = String.format(d774("ts/VldKq1NCS16LZ358="), xCoord, yCoord, zCoord);
      int width = i.getWidth(coordText) + (99 ^ 119);
      int height = 191 ^ 167;
      Color bgColor = ch.safeColor(10, 10, 15, (int)(this.opacity * 200.0F));
      Color accentColor = this.enableRainbow ? this.getRainbowColor(0) : aq.getPrimaryAccent();
      iq.renderRoundedQuad(context.method_51448(), bgColor, (double)x, (double)y, (double)(x + width), (double)(y + height), (double)this.cornerRadius, 20.0D);
      i.drawString(coordText, context, x + 10, y + 8, accentColor.getRGB());
   }

   private void renderModuleList(class_332 context, int x, int y) {
      List<bf> modules = this.getSortedModules();
      int yOffset = y;
      boolean rightAligned = x > mc.method_22683().method_4486() / 2;

      for(int i = 0; i < modules.size(); ++i) {
         bf module = (bf)modules.get(i);
         float targetAlpha = 1.0F;
         float currentAlpha = (Float)this.moduleAnimations.getOrDefault(module, 0.0F);
         if (this.enableAnimations) {
            currentAlpha += (targetAlpha - currentAlpha) * 0.15F;
            this.moduleAnimations.put(module, currentAlpha);
         } else {
            currentAlpha = 1.0F;
         }

         float targetSlide = 1.0F;
         float currentSlide = (Float)this.moduleSlideAnimations.getOrDefault(module, 0.0F);
         if (this.enableAnimations) {
            currentSlide += (targetSlide - currentSlide) * 0.12F;
            this.moduleSlideAnimations.put(module, currentSlide);
         } else {
            currentSlide = 1.0F;
         }

         String moduleName = module.getName().toString();
         int textWidth = i.getWidth(moduleName);
         int moduleHeight = 228 ^ 240;
         int moduleWidth = textWidth + 16;
         Color bgColor = ch.safeColor(138 ^ 128, 10, 15, (int)(this.opacity * 200.0F * currentAlpha));
         Color moduleColor;
         if (this.enableRainbow) {
            moduleColor = this.getRainbowColor(i * (69 ^ 74));
         } else {
            Color accent = aq.getPrimaryAccent();
            Color secondary = aq.getSecondaryAccent();
            float progress = (float)i / (float)Math.max(modules.size() - 1, 1);
            moduleColor = this.blendColors(accent, secondary, progress);
         }

         int renderX;
         if (rightAligned) {
            renderX = (int)((float)(x - moduleWidth) + (float)moduleWidth * (1.0F - currentSlide));
         } else {
            renderX = (int)((float)x - (float)moduleWidth * (1.0F - currentSlide));
         }

         iq.renderRoundedQuad(context.method_51448(), bgColor, (double)renderX, (double)yOffset, (double)(renderX + moduleWidth), (double)(yOffset + moduleHeight), (double)this.cornerRadius, 20.0D);
         i.drawString(moduleName, context, renderX + (190 ^ 182), yOffset + 6, ch.safeColor(moduleColor.getRed(), moduleColor.getGreen(), moduleColor.getBlue(), (int)(255.0F * currentAlpha)).getRGB());
         yOffset += moduleHeight + 2;
      }

      this.moduleAnimations.keySet().removeIf((m) -> {
         return !modules.contains(m);
      });
      this.moduleSlideAnimations.keySet().removeIf((m) -> {
         return !modules.contains(m);
      });
   }

   private void renderRadar(class_332 context, int x, int y) {
      int size = this.radarSize;
      int range = this.radarRange;
      int centerX = x + size / 2;
      int centerY = y + size / 2;
      Color bgColor = ch.safeColor(134 ^ 140, 10, 15, (int)(this.opacity * 220.0F));
      Color accentColor = this.enableRainbow ? this.getRainbowColor(0) : aq.getPrimaryAccent();
      iq.renderCircle(context.method_51448(), bgColor, (double)centerX, (double)centerY, (double)(size / 2), 40);
      iq.renderCircle(context.method_51448(), ch.safeColor(accentColor.getRed(), accentColor.getGreen(), accentColor.getBlue(), 80), (double)centerX, (double)centerY, (double)(size / 2), 100 ^ 76);
      iq.renderCircle(context.method_51448(), bgColor, (double)centerX, (double)centerY, (double)(size / 2 - 1), 40);
      Color gridColor = ch.safeColor(255, 133 ^ 122, 255, 109 ^ 103);
      iq.renderCircle(context.method_51448(), gridColor, (double)centerX, (double)centerY, (double)(size / 4), 30);
      iq.renderCircle(context.method_51448(), gridColor, (double)centerX, (double)centerY, (double)(size / 3), 101 ^ 123);
      iq.renderCircle(context.method_51448(), accentColor, (double)centerX, (double)centerY, 3.0D, 123 ^ 119);
      if (mc.field_1687 != null) {
         Iterator var11 = mc.field_1687.method_18456().iterator();

         while(var11.hasNext()) {
            class_742 player = (class_742)var11.next();
            if (player != mc.field_1724) {
               class_243 playerPos = player.method_19538();
               class_243 selfPos = mc.field_1724.method_19538();
               double deltaX = playerPos.field_1352 - selfPos.field_1352;
               double deltaZ = playerPos.field_1350 - selfPos.field_1350;
               double distance = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
               if (!(distance > (double)range)) {
                  float yaw = mc.field_1724.method_36454();
                  double angle = Math.atan2(deltaZ, deltaX) - Math.toRadians((double)(yaw + 90.0F));
                  double scale = ((double)size / 2.0D - 10.0D) / (double)range;
                  int radarX = (int)((double)centerX + Math.cos(angle) * distance * scale);
                  int radarY = (int)((double)centerY + Math.sin(angle) * distance * scale);
                  double distFromCenter = Math.sqrt(Math.pow((double)(radarX - centerX), 2.0D) + Math.pow((double)(radarY - centerY), 2.0D));
                  if (!(distFromCenter > (double)(size / 2 - 10))) {
                     class_2960 skinTexture = player.method_52814().comp_1626();
                     int headSize = 241 ^ 249;
                     context.method_25293(skinTexture, radarX - headSize / 2, radarY - headSize / 2, headSize, headSize, 8.0F, 8.0F, 8, 53 ^ 61, 64, 64);
                  }
               }
            }
         }
      }

   }

   private void renderHotbar(class_332 context, int x, int y) {
      if (mc.field_1724 != null) {
         int slotSize = 18;
         int spacing = 2;
         int width = slotSize * (216 ^ 209) + spacing * 10;
         int height = slotSize + spacing * 2;
         Color bgColor = ch.safeColor(19 ^ 25, 116 ^ 126, 15, (int)(this.opacity * 200.0F));
         Color accentColor = this.enableRainbow ? this.getRainbowColor(150) : aq.getPrimaryAccent();
         iq.renderRoundedQuad(context.method_51448(), bgColor, (double)x, (double)y, (double)(x + width), (double)(y + height), (double)this.cornerRadius, 20.0D);

         for(int i = 0; i < 9; ++i) {
            class_1799 stack = mc.field_1724.method_31548().method_5438(i);
            int slotX = x + spacing + i * (slotSize + spacing);
            int slotY = y + spacing;
            if (i == mc.field_1724.method_31548().field_7545) {
               Color selectedBg = ch.safeColor(accentColor.getRed(), accentColor.getGreen(), accentColor.getBlue(), 30);
               iq.renderRoundedQuad(context.method_51448(), selectedBg, (double)(slotX - 1), (double)(slotY - 1), (double)(slotX + slotSize + 1), (double)(slotY + slotSize + 1), 3.0D, 16.0D);
               iq.renderRoundedOutline(context, accentColor, (double)(slotX - 1), (double)(slotY - 1), (double)(slotX + slotSize + 1), (double)(slotY + slotSize + 1), 3.0D, 3.0D, 3.0D, 3.0D, 1.5D, 16.0D);
            }

            if (!stack.method_7960()) {
               context.method_51427(stack, slotX, slotY);
               context.method_51431(mc.field_1772, stack, slotX, slotY);
            }
         }

      }
   }

   private void renderEffects(class_332 context, int x, int y) {
      if (mc.field_1724 != null) {
         Collection<class_1293> effects = mc.field_1724.method_6026();
         if (!effects.isEmpty()) {
            int yOffset = y;
            int width = 120;
            int height = 0 ^ 26;

            for(Iterator var8 = effects.iterator(); var8.hasNext(); yOffset += height + 2) {
               class_1293 effect = (class_1293)var8.next();
               String name = ((class_1291)effect.method_5579().comp_349()).method_5560().getString();
               int amplifier = effect.method_5578();
               String duration = this.formatDuration(effect.method_5584());
               String displayText = name + (amplifier > 0 ? " " + (amplifier + 1) : "");
               int textWidth = i.getWidth(displayText);
               int actualWidth = Math.max(width, textWidth + (41 ^ 1));
               Color bgColor = ch.safeColor(10, 10, 23 ^ 24, (int)(this.opacity * 200.0F));
               Color accentColor = this.enableRainbow ? this.getRainbowColor(yOffset) : aq.getPrimaryAccent();
               iq.renderRoundedQuad(context.method_51448(), bgColor, (double)x, (double)yOffset, (double)(x + actualWidth), (double)(yOffset + height), (double)this.cornerRadius, 20.0D);
               i.drawString(displayText, context, x + 8, yOffset + 5, accentColor.getRGB());
               int durationWidth = i.getWidth(duration);
               i.drawString(duration, context, x + actualWidth - durationWidth - 8, yOffset + (228 ^ 235), ch.safeColor(160, 160, 47 ^ 155, 255).getRGB());
            }

         }
      }
   }

   private String formatDuration(int ticks) {
      int seconds = ticks / 20;
      int minutes = seconds / 60;
      seconds %= 72 ^ 116;
      return String.format(d774("y4vK1MLBkA=="), minutes, seconds);
   }

   private void renderKeystrokes(class_332 context, int x, int y) {
      int keySize = 28;
      int spacing = 3;
      Color bgColor = ch.safeColor(154 ^ 144, 18 ^ 24, 15, (int)(this.opacity * 200.0F));
      Color accentColor = this.enableRainbow ? this.getRainbowColor(0) : aq.getPrimaryAccent();
      Color pressedColor = ch.safeColor(accentColor.getRed(), accentColor.getGreen(), accentColor.getBlue(), 100);
      boolean wPressed = mc.field_1690.field_1894.method_1434();
      boolean aPressed = mc.field_1690.field_1913.method_1434();
      boolean sPressed = mc.field_1690.field_1881.method_1434();
      boolean dPressed = mc.field_1690.field_1849.method_1434();
      boolean spacePressed = mc.field_1690.field_1903.method_1434();
      boolean lmbPressed = mc.field_1690.field_1886.method_1434();
      boolean rmbPressed = mc.field_1690.field_1904.method_1434();
      this.renderKey(context, x + keySize + spacing, y, keySize, keySize, "W", wPressed ? pressedColor : bgColor, wPressed);
      this.renderKey(context, x, y + keySize + spacing, keySize, keySize, "A", aPressed ? pressedColor : bgColor, aPressed);
      this.renderKey(context, x + keySize + spacing, y + keySize + spacing, keySize, keySize, "S", sPressed ? pressedColor : bgColor, sPressed);
      this.renderKey(context, x + (keySize + spacing) * 2, y + keySize + spacing, keySize, keySize, "D", dPressed ? pressedColor : bgColor, dPressed);
      this.renderKey(context, x, y + (keySize + spacing) * 2, keySize * 3 + spacing * 2, keySize, d774("w8Ld"), spacePressed ? pressedColor : bgColor, spacePressed);
      this.renderKey(context, x, y + (keySize + spacing) * 3, (keySize * 3 + spacing * 2) / 2 - spacing / 2, keySize, d774("oqKy"), lmbPressed ? pressedColor : bgColor, lmbPressed);
      this.renderKey(context, x + (keySize * 3 + spacing * 2) / 2 + spacing / 2, y + (keySize + spacing) * 3, (keySize * 3 + spacing * 2) / 2 - spacing / 2, keySize, d774("vKKy"), rmbPressed ? pressedColor : bgColor, rmbPressed);
   }

   private void renderKey(class_332 context, int x, int y, int width, int height, String text, Color bgColor, boolean pressed) {
      Color accentColor = this.enableRainbow ? this.getRainbowColor(0) : aq.getPrimaryAccent();
      iq.renderRoundedQuad(context.method_51448(), bgColor, (double)x, (double)y, (double)(x + width), (double)(y + height), (double)this.cornerRadius, 20.0D);
      if (pressed) {
         iq.renderRoundedOutline(context, accentColor, (double)x, (double)y, (double)(x + width), (double)(y + height), (double)this.cornerRadius, (double)this.cornerRadius, (double)this.cornerRadius, (double)this.cornerRadius, 2.0D, 20.0D);
      }

      int textWidth = i.getWidth(text);
      int textX = x + (width - textWidth) / 2;
      int textY = y + (height - (46 ^ 38)) / 2;
      i.drawString(text, context, textX, textY, pressed ? accentColor.getRGB() : ch.safeColor(200, 200, 220, 255).getRGB());
   }

   private void renderCompass(class_332 context, int x, int y) {
      int width = 100;
      int height = 108 ^ 116;
      Color bgColor = ch.safeColor(5 ^ 15, 10, 15, (int)(this.opacity * 200.0F));
      Color accentColor = this.enableRainbow ? this.getRainbowColor(0) : aq.getPrimaryAccent();
      iq.renderRoundedQuad(context.method_51448(), bgColor, (double)x, (double)y, (double)(x + width), (double)(y + height), (double)this.cornerRadius, 20.0D);
      float yaw = mc.field_1724.method_36454() % 360.0F;
      if (yaw < 0.0F) {
         yaw += 360.0F;
      }

      String direction;
      if (!((double)yaw >= 337.5D) && !((double)yaw < 22.5D)) {
         if ((double)yaw >= 22.5D && (double)yaw < 67.5D) {
            direction = "NE";
         } else if ((double)yaw >= 67.5D && (double)yaw < 112.5D) {
            direction = "E";
         } else if ((double)yaw >= 112.5D && (double)yaw < 157.5D) {
            direction = "SE";
         } else if ((double)yaw >= 157.5D && (double)yaw < 202.5D) {
            direction = "S";
         } else if ((double)yaw >= 202.5D && (double)yaw < 247.5D) {
            direction = "SW";
         } else if ((double)yaw >= 247.5D && (double)yaw < 292.5D) {
            direction = "W";
         } else {
            direction = "NW";
         }
      } else {
         direction = "N";
      }

      String displayText = direction + " [" + (int)yaw + "°]";
      int textWidth = i.getWidth(displayText);
      int textX = x + (width - textWidth) / 2;
      i.drawString(displayText, context, textX, y + 8, accentColor.getRGB());
   }

   private void renderCombatStats(class_332 context, int x, int y) {
      int width = 100;
      int height = 42;
      Color bgColor = ch.safeColor(10, 10, 15, (int)(this.opacity * 200.0F));
      Color accentColor = this.enableRainbow ? this.getRainbowColor(0) : aq.getPrimaryAccent();
      iq.renderRoundedQuad(context.method_51448(), bgColor, (double)x, (double)y, (double)(x + width), (double)(y + height), (double)this.cornerRadius, 20.0D);
      String killsText = "Kills: " + this.sessionKills;
      String damageText = "DMG: " + this.sessionDamageDealt;
      i.drawString(killsText, context, x + 10, y + (134 ^ 142), accentColor.getRGB());
      i.drawString(damageText, context, x + (236 ^ 230), y + (172 ^ 180), ch.safeColor(255, 100, 100, 56 ^ 199).getRGB());
   }

   private void renderArmorDurability(class_332 context, int x, int y) {
      if (mc.field_1724 != null) {
         int slotSize = 20;
         int barHeight = 3;
         int spacing = 4;
         int width = slotSize * 4 + spacing * 5;
         int height = slotSize + barHeight + spacing * 2;
         Color bgColor = ch.safeColor(10, 46 ^ 36, 113 ^ 126, (int)(this.opacity * 200.0F));
         iq.renderRoundedQuad(context.method_51448(), bgColor, (double)x, (double)y, (double)(x + width), (double)(y + height), (double)this.cornerRadius, 20.0D);
         int[] armorSlots = new int[]{54 ^ 17, 184 ^ 158, 37, 229 ^ 193};

         for(int i = 0; i < 4; ++i) {
            class_1799 armorPiece = mc.field_1724.method_31548().method_5438(armorSlots[i]);
            int slotX = x + spacing + i * (slotSize + spacing);
            int slotY = y + spacing;
            if (!armorPiece.method_7960()) {
               context.method_51427(armorPiece, slotX, slotY);
               if (armorPiece.method_7963()) {
                  int maxDurability = armorPiece.method_7936();
                  int currentDurability = maxDurability - armorPiece.method_7919();
                  float durabilityPercent = (float)currentDurability / (float)maxDurability;
                  Color barColor;
                  if (durabilityPercent > 0.5F) {
                     barColor = ch.safeColor(184 ^ 220, 124 ^ 131, 19 ^ 119, 255);
                  } else if (durabilityPercent > 0.25F) {
                     barColor = ch.safeColor(203 ^ 52, 255, 100, 255);
                  } else {
                     barColor = ch.safeColor(255, 100, 100, 251 ^ 4);
                  }

                  int barY = slotY + slotSize + 2;
                  int barWidth = (int)((float)slotSize * durabilityPercent);
                  context.method_25294(slotX, barY, slotX + slotSize, barY + barHeight, ch.safeColor(40, 40, 97 ^ 83, 255).getRGB());
                  if (barWidth > 0) {
                     context.method_25294(slotX, barY, slotX + barWidth, barY + barHeight, barColor.getRGB());
                  }
               }
            }
         }

      }
   }

   public void addKill() {
      ++this.sessionKills;
   }

   public void addDamage(int damage) {
      this.sessionDamageDealt += damage;
   }

   public void resetCombatStats() {
      this.sessionKills = 0;
      this.sessionDamageDealt = 0;
   }

   private List<bf> getSortedModules() {
      List<bf> modules = Astralux.INSTANCE.getModuleManager().getEnabledModules().stream().filter(bf::isVisible).toList();
      List var10000;
      switch(this.sortMode.ordinal()) {
      case 0:
         var10000 = modules.stream().sorted((m1, m2) -> {
            return Integer.compare(i.getWidth(m2.getName()), i.getWidth(m1.getName()));
         }).toList();
         break;
      case 1:
         var10000 = modules.stream().sorted(Comparator.comparing((module) -> {
            return module.getName().toString();
         })).toList();
         break;
      case 2:
         var10000 = modules.stream().sorted(Comparator.comparing(bf::getCategory).thenComparing((module) -> {
            return module.getName().toString();
         })).toList();
         break;
      default:
         throw new MatchException((String)null, (Throwable)null);
      }

      return var10000;
   }

   private String getPingInfo() {
      if (mc != null && mc.field_1724 != null && mc.method_1562() != null) {
         class_640 entry = mc.method_1562().method_2871(mc.field_1724.method_5667());
         return entry != null ? entry.method_2959() + "ms" : d774("3oKD");
      } else {
         return d774("3oKD");
      }
   }

   private Color getRainbowColor(int offset) {
      long time = System.currentTimeMillis();
      float hue = ((float)time * this.rainbowSpeed / 1000.0F + (float)offset / 100.0F) % 1.0F;
      return Color.getHSBColor(hue, 0.65F, 1.0F);
   }

   private Color blendColors(Color color1, Color color2, float ratio) {
      ratio = class_3532.method_15363(ratio, 0.0F, 1.0F);
      int r = (int)((float)color1.getRed() + (float)(color2.getRed() - color1.getRed()) * ratio);
      int g = (int)((float)color1.getGreen() + (float)(color2.getGreen() - color1.getGreen()) * ratio);
      int b = (int)((float)color1.getBlue() + (float)(color2.getBlue() - color1.getBlue()) * ratio);
      return ch.safeColor(r, g, b, 255);
   }

   // $FF: synthetic method
   private static String d774(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 8942 + var3 & 255);
      }

      return new String(var2);
   }
}
