import client.astralux.Astralux;
import java.awt.Color;
import java.util.Base64;

public class aq {
   private static Astralux astraluxInstance;
   private static long lastRgbUpdate = System.currentTimeMillis();
   private static float currentRgbHue = 0.0F;
   private static Color customPrimaryColor = null;

   private static fd getThemeManager() {
      try {
         if (astraluxInstance != null) {
            return (fd)astraluxInstance.getModuleManager().getModuleByClass(fd.class);
         }
      } catch (Exception var1) {
      }

      return null;
   }

   private static ai getCurrentTheme() {
      try {
         if (astraluxInstance != null && astraluxInstance.GUI != null) {
            return astraluxInstance.GUI.currentTheme;
         }
      } catch (Exception var1) {
      }

      return ai.MIDNIGHT_PURPLE;
   }

   public static void setCustomPrimaryColor(Color color) {
      customPrimaryColor = color;
   }

   public static Color getCustomPrimaryColor() {
      return customPrimaryColor;
   }

   private static void updateRgbAnimation() {
      long currentTime = System.currentTimeMillis();
      float deltaTime = (float)(currentTime - lastRgbUpdate) / 1000.0F;
      lastRgbUpdate = currentTime;
      fd tm = getThemeManager();
      if (tm != null && tm.getRgbMode().getValue()) {
         float speed = tm.getRgbSpeed().getFloatValue();
         currentRgbHue += speed * deltaTime * 0.1F;
         currentRgbHue %= 1.0F;
      }

   }

   public static Color getRgbColor() {
      fd tm = getThemeManager();
      if (tm != null && tm.getRgbMode().getValue()) {
         updateRgbAnimation();
         float sat = tm.getRgbSaturation().getFloatValue();
         float bright = tm.getRgbBrightness().getFloatValue();
         return Color.getHSBColor(currentRgbHue, sat, bright);
      } else {
         return null;
      }
   }

   public static Color getRgbColorWithOffset(float offset) {
      fd tm = getThemeManager();
      if (tm != null && tm.getRgbMode().getValue()) {
         updateRgbAnimation();
         float sat = tm.getRgbSaturation().getFloatValue();
         float bright = tm.getRgbBrightness().getFloatValue();
         float hue = (currentRgbHue + offset) % 1.0F;
         return Color.getHSBColor(hue, sat, bright);
      } else {
         return null;
      }
   }

   public static Color getPrimaryAccent() {
      return customPrimaryColor != null ? customPrimaryColor : getCurrentTheme().primaryAccent;
   }

   public static Color getPrimaryAccentWithOffset(float offset) {
      return getPrimaryAccent();
   }

   public static Color getOutlineColor() {
      Color rgbColor = getRgbColor();
      return rgbColor != null ? rgbColor : getPrimaryAccent();
   }

   public static Color getOutlineColorWithAlpha(int alpha) {
      Color base = getOutlineColor();
      return new Color(base.getRed(), base.getGreen(), base.getBlue(), alpha);
   }

   public static Color getSecondaryAccent() {
      Color rgbColor = getRgbColorWithOffset(0.1F);
      return rgbColor != null ? rgbColor : getCurrentTheme().secondaryAccent;
   }

   public static Color getBackground() {
      return getCurrentTheme().background;
   }

   public static Color getPanelBackground() {
      return getCurrentTheme().panelBackground;
   }

   public static Color getPanelBackgroundWithAlpha(int alpha) {
      Color bg = getPanelBackground();
      return new Color(bg.getRed(), bg.getGreen(), bg.getBlue(), alpha);
   }

   public static Color getHeaderBackground() {
      Color bg = getPanelBackground();
      int r = Math.min(255, bg.getRed() + 10);
      int g = Math.min(114 ^ 141, bg.getGreen() + (23 ^ 29));
      int b = Math.min(255, bg.getBlue() + 10);
      return new Color(r, g, b, bg.getAlpha());
   }

   public static Color getTextColor() {
      return new Color(200, 179 ^ 123, 210, 255);
   }

   public static Color getBrightTextColor() {
      return new Color(240, 79 ^ 191, 68 ^ 190, 255);
   }

   public static Color getDimTextColor() {
      return new Color(14 ^ 130, 176 ^ 60, 115 ^ 229, 46 ^ 209);
   }

   public static Color getButtonBackground() {
      Color panelBg = getPanelBackground();
      return new Color(Math.max(0, panelBg.getRed() - 10), Math.max(0, panelBg.getGreen() - 10), Math.max(0, panelBg.getBlue() - 10), 255);
   }

   public static Color getButtonBackgroundWithAlpha(int alpha) {
      Color panelBg = getPanelBackground();
      return new Color(Math.max(0, panelBg.getRed() - 10), Math.max(0, panelBg.getGreen() - 10), Math.max(0, panelBg.getBlue() - 10), alpha);
   }

   public static Color getButtonBorder() {
      Color panelBg = getPanelBackground();
      return new Color(Math.min(130 ^ 125, panelBg.getRed() + 30), Math.min(88 ^ 167, panelBg.getGreen() + (118 ^ 104)), Math.min(123 ^ 132, panelBg.getBlue() + 30), 144 ^ 111);
   }

   public static Color getGridBackground() {
      Color panelBg = getPanelBackground();
      return new Color(Math.max(0, panelBg.getRed() - 15), Math.max(0, panelBg.getGreen() - 15), Math.max(0, panelBg.getBlue() - 15), 255);
   }

   public static Color getHoverColor() {
      return new Color(255, 255, 255, 40 ^ 60);
   }

   public static Color getHighlightGlow() {
      Color rgbColor = getRgbColor();
      if (rgbColor != null) {
         return rgbColor;
      } else if (customPrimaryColor != null) {
         float[] hsb = Color.RGBtoHSB(customPrimaryColor.getRed(), customPrimaryColor.getGreen(), customPrimaryColor.getBlue(), (float[])null);
         return Color.getHSBColor(hsb[0], hsb[1] * 0.8F, Math.min(1.0F, hsb[2] * 1.2F));
      } else {
         Color accent = getCurrentTheme().primaryAccent;
         float[] hsb = Color.RGBtoHSB(accent.getRed(), accent.getGreen(), accent.getBlue(), (float[])null);
         return Color.getHSBColor(hsb[0], hsb[1] * 0.8F, Math.min(1.0F, hsb[2] * 1.2F));
      }
   }

   public static Color blend(Color c1, Color c2, float ratio) {
      ratio = Math.max(0.0F, Math.min(1.0F, ratio));
      int r = (int)((float)c1.getRed() * (1.0F - ratio) + (float)c2.getRed() * ratio);
      int g = (int)((float)c1.getGreen() * (1.0F - ratio) + (float)c2.getGreen() * ratio);
      int b = (int)((float)c1.getBlue() * (1.0F - ratio) + (float)c2.getBlue() * ratio);
      int a = (int)((float)c1.getAlpha() * (1.0F - ratio) + (float)c2.getAlpha() * ratio);
      return new Color(r, g, b, a);
   }

   static {
      try {
         astraluxInstance = Astralux.INSTANCE;
      } catch (Exception var1) {
         astraluxInstance = null;
      }

   }

   // $FF: synthetic method
   private static String d49(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 7527 - 558 + var3 & 255);
      }

      return new String(var2);
   }
}
