import client.astralux.Astralux;
import java.awt.Color;
import java.util.Base64;
import java.util.Map;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_437;

public class ba extends class_437 {
   private final String storageType;
   private final Map<String, Color> storageColors;
   private final class_437 parentScreen;
   private float hue = 0.0F;
   private float saturation = 1.0F;
   private float brightness = 1.0F;
   private int alpha = 255;
   private final int wheelRadius = 80;
   private int wheelCenterX;
   private int wheelCenterY;
   private int brightnessBarX;
   private int brightnessBarY;
   private final int brightnessBarWidth = 30;
   private final int brightnessBarHeight = 200;
   private boolean draggingWheel = false;
   private boolean draggingBrightness = false;
   private boolean draggingAlpha = false;

   public ba(class_437 parent, String storageType, Map<String, Color> storageColors) {
      super(class_2561.method_43473());
      this.parentScreen = parent;
      this.storageType = storageType;
      this.storageColors = storageColors;
      Color currentColor = (Color)storageColors.getOrDefault(storageType, new Color(255, 95 ^ 160, 255));
      float[] hsv = Color.RGBtoHSB(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(), (float[])null);
      this.hue = hsv[0] * 360.0F;
      this.saturation = hsv[1];
      this.brightness = hsv[2];
      this.alpha = currentColor.getAlpha();
   }

   public void method_25394(class_332 drawContext, int mouseX, int mouseY, float delta) {
      iq.unscaledProjection();
      int scaledMouseX = mouseX * (int)class_310.method_1551().method_22683().method_4495();
      int scaledMouseY = mouseY * (int)class_310.method_1551().method_22683().method_4495();
      super.method_25394(drawContext, scaledMouseX, scaledMouseY, delta);
      int screenWidth = class_310.method_1551().method_22683().method_4480();
      int screenHeight = class_310.method_1551().method_22683().method_4507();
      int bgAlpha = gm.renderBackground.getValue() ? 180 : 0;
      drawContext.method_25294(0, 0, screenWidth, screenHeight, ch.safeColor(0, 0, 0, bgAlpha).getRGB());
      int panelWidth = true;
      int panelHeight = true;
      int panelX = (screenWidth - 500) / 2;
      int panelY = (screenHeight - (471 ^ 21)) / 2;
      this.wheelCenterX = panelX + 150;
      this.wheelCenterY = panelY + 200;
      this.brightnessBarX = panelX + (459 ^ 231);
      this.brightnessBarY = panelY + 100;
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getPanelBackgroundWithAlpha(35 ^ 211), (double)panelX, (double)panelY, (double)(panelX + (488 ^ 28)), (double)(panelY + 450), 8.0D, 8.0D, 8.0D, 8.0D, 20.0D);
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getHeaderBackground(), (double)panelX, (double)panelY, (double)(panelX + (383 ^ 139)), (double)(panelY + 30), 8.0D, 8.0D, 0.0D, 0.0D, 20.0D);
      drawContext.method_25294(panelX, panelY + 30, panelX + 500, panelY + 31, bi.getMainColor(255, 1).getRGB());
      String displayName = this.formatStorageTypeName(this.storageType);
      i.drawCenteredString("Edit Color: " + displayName, drawContext, panelX + (162 ^ 88), panelY + (18 ^ 26), aq.getBrightTextColor().getRGB());
      int previewX = panelX + 250 - 32;
      int previewY = panelY + 50;
      Color currentColor = this.getCurrentColor();
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getGridBackground(), (double)(previewX - 10), (double)(previewY - (192 ^ 202)), (double)(previewX + 74), (double)(previewY + (86 ^ 28)), 8.0D, 8.0D, 8.0D, 8.0D, 20.0D);
      iq.renderRoundedQuad(drawContext.method_51448(), currentColor, (double)previewX, (double)previewY, (double)(previewX + 64), (double)(previewY + (143 ^ 207)), 6.0D, 6.0D, 6.0D, 6.0D, 20.0D);
      this.renderColorWheel(drawContext);
      this.renderBrightnessBar(drawContext);
      this.renderAlphaSlider(drawContext, panelX + 50, panelY + (310 ^ 118), 451 ^ 83);
      i.drawCenteredString("RGB: " + currentColor.getRed() + ", " + currentColor.getGreen() + ", " + currentColor.getBlue(), drawContext, panelX + 250, panelY + 355, aq.getTextColor().getRGB());
      i.drawCenteredString("HEX: #" + String.format(d118("Oy8SeQcTFn0DFxpx"), currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue()), drawContext, panelX + 250, panelY + 370, aq.getTextColor().getRGB());
      int buttonY = panelY + 450 - (215 ^ 250);
      int saveButtonX = panelX + 500 - 80 - (213 ^ 193);
      int cancelButtonX = saveButtonX - (161 ^ 241) - 10;
      int resetButtonX = cancelButtonX - 80 - (139 ^ 129);
      iq.renderRoundedQuad(drawContext.method_51448(), bi.getMainColor(255, 1), (double)saveButtonX, (double)buttonY, (double)(saveButtonX + 80), (double)(buttonY + (12 ^ 18)), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);
      i.drawCenteredString(d118("TX5WRA=="), drawContext, saveButtonX + 40, buttonY + (132 ^ 140), aq.getBrightTextColor().getRGB());
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getButtonBorder(), (double)cancelButtonX, (double)buttonY, (double)(cancelButtonX + (167 ^ 247)), (double)(buttonY + (72 ^ 86)), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);
      i.drawCenteredString(d118("XX5OQkdP"), drawContext, cancelButtonX + (209 ^ 249), buttonY + (29 ^ 21), aq.getBrightTextColor().getRGB());
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getSecondaryAccent(), (double)resetButtonX, (double)buttonY, (double)(resetButtonX + (73 ^ 25)), (double)(buttonY + (64 ^ 94)), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);
      i.drawCenteredString(d118("THpTRFY="), drawContext, resetButtonX + 40, buttonY + 8, aq.getBrightTextColor().getRGB());
      iq.scaledProjection();
   }

   private void renderColorWheel(class_332 drawContext) {
      int segmentCount = true;
      int radiusSteps = 203 ^ 195;

      for(int segment = 0; segment < (95 ^ 99); ++segment) {
         float hue1 = (float)segment / 60.0F * 360.0F;
         float hue2 = (float)(segment + 1) / 60.0F * 360.0F;
         Color segmentColor = Color.getHSBColor(hue1 / 360.0F, 1.0F, this.brightness);

         for(int radius = 0; radius < (73 ^ 65); ++radius) {
            float innerRadius = (float)radius / 8.0F * 80.0F;
            float outerRadius = (float)(radius + 1) / 8.0F * 80.0F;
            double angle1Rad = Math.toRadians((double)hue1);
            double angle2Rad = Math.toRadians((double)hue2);
            int x1 = (int)((double)this.wheelCenterX + Math.cos(angle1Rad) * (double)innerRadius);
            int y1 = (int)((double)this.wheelCenterY + Math.sin(angle1Rad) * (double)innerRadius);
            int x2 = (int)((double)this.wheelCenterX + Math.cos(angle2Rad) * (double)innerRadius);
            int y2 = (int)((double)this.wheelCenterY + Math.sin(angle2Rad) * (double)innerRadius);
            int x3 = (int)((double)this.wheelCenterX + Math.cos(angle2Rad) * (double)outerRadius);
            int y3 = (int)((double)this.wheelCenterY + Math.sin(angle2Rad) * (double)outerRadius);
            int x4 = (int)((double)this.wheelCenterX + Math.cos(angle1Rad) * (double)outerRadius);
            int y4 = (int)((double)this.wheelCenterY + Math.sin(angle1Rad) * (double)outerRadius);
            this.drawFilledQuad(drawContext, segmentColor, x1, y1, x2, y2, x3, y3, x4, y4);
         }
      }

      float rad = (float)Math.toRadians((double)this.hue);
      int indicatorX = (int)((double)this.wheelCenterX + Math.cos((double)rad) * (double)this.saturation * 80.0D);
      int indicatorY = (int)((double)this.wheelCenterY + Math.sin((double)rad) * (double)this.saturation * 80.0D);
      iq.renderCircle(drawContext.method_51448(), ch.safeColor(255, 255, 174 ^ 81, 241 ^ 57), (double)indicatorX, (double)indicatorY, 6.0D, 16);
      iq.renderCircle(drawContext.method_51448(), ch.safeColor(0, 0, 0, 200), (double)indicatorX, (double)indicatorY, 4.0D, 16);
   }

   private void drawFilledQuad(class_332 drawContext, Color color, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
      drawContext.method_25294(Math.min(Math.min(Math.min(x1, x2), x3), x4), Math.min(Math.min(Math.min(y1, y2), y3), y4), Math.max(Math.max(Math.max(x1, x2), x3), x4), Math.max(Math.max(Math.max(y1, y2), y3), y4), color.getRGB());
   }

   private void renderBrightnessBar(class_332 drawContext) {
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getGridBackground(), (double)(this.brightnessBarX - 5), (double)(this.brightnessBarY - 5), (double)(this.brightnessBarX + (171 ^ 181) + 5), (double)(this.brightnessBarY + 200 + 5), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);

      int indicatorY;
      for(indicatorY = 0; indicatorY < 200; ++indicatorY) {
         float b = 1.0F - (float)indicatorY / 200.0F;
         Color gradientColor = Color.getHSBColor(this.hue / 360.0F, this.saturation, b);
         drawContext.method_25294(this.brightnessBarX, this.brightnessBarY + indicatorY, this.brightnessBarX + (7 ^ 25), this.brightnessBarY + indicatorY + 1, gradientColor.getRGB());
      }

      indicatorY = (int)((float)this.brightnessBarY + (1.0F - this.brightness) * 200.0F);
      drawContext.method_25294(this.brightnessBarX - 3, indicatorY - 2, this.brightnessBarX + 30 + 3, indicatorY + 2, ch.safeColor(255, 255, 30 ^ 225, 37 ^ 218).getRGB());
      drawContext.method_25294(this.brightnessBarX - 2, indicatorY - 1, this.brightnessBarX + 30 + 2, indicatorY + 1, ch.safeColor(0, 0, 0, 196 ^ 59).getRGB());
   }

   private void renderAlphaSlider(class_332 drawContext, int x, int y, int width) {
      i.drawString("Alpha: " + this.alpha, drawContext, x, y - 15, ch.safeColor(149 ^ 93, 200, 11 ^ 195, 255).getRGB());
      iq.renderRoundedQuad(drawContext.method_51448(), ch.safeColor(20, 166 ^ 178, 25, 89 ^ 166), (double)x, (double)y, (double)(x + width), (double)(y + 20), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);

      for(int i = 0; i < width; i += 10) {
         Color checker = i / 10 % 2 == 0 ? ch.safeColor(100, 100, 100, 255) : ch.safeColor(150, 63 ^ 169, 213 ^ 67, 255);
         drawContext.method_25294(x + i, y, x + i + (11 ^ 1), y + 20, checker.getRGB());
      }

      Color baseColor = Color.getHSBColor(this.hue / 360.0F, this.saturation, this.brightness);

      int indicatorX;
      for(indicatorX = 0; indicatorX < width; ++indicatorX) {
         int a = (int)((float)indicatorX / (float)width * 255.0F);
         Color alphaColor = ch.safeColor(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), a);
         drawContext.method_25294(x + indicatorX, y, x + indicatorX + 1, y + 20, alphaColor.getRGB());
      }

      indicatorX = (int)((float)x + (float)this.alpha / 255.0F * (float)width);
      drawContext.method_25294(indicatorX - 2, y - 2, indicatorX + 2, y + (15 ^ 25), ch.safeColor(255, 255, 255, 255).getRGB());
      drawContext.method_25294(indicatorX - 1, y, indicatorX + 1, y + (192 ^ 212), this.getCurrentColor().getRGB());
   }

   public boolean method_25402(double mouseX, double mouseY, int button) {
      double scaledX = mouseX * class_310.method_1551().method_22683().method_4495();
      double scaledY = mouseY * class_310.method_1551().method_22683().method_4495();
      int screenWidth = this.field_22787.method_22683().method_4480();
      int screenHeight = this.field_22787.method_22683().method_4507();
      int panelWidth = 469 ^ 33;
      int panelHeight = 498 ^ 48;
      int panelX = (screenWidth - 500) / 2;
      int panelY = (screenHeight - 450) / 2;
      int buttonY = panelY + 450 - 45;
      int saveButtonX = panelX + (484 ^ 16) - 80 - 20;
      int cancelButtonX = saveButtonX - 80 - 10;
      int resetButtonX = cancelButtonX - (252 ^ 172) - (90 ^ 80);
      if (this.isInBounds(scaledX, scaledY, saveButtonX, buttonY, 80, 30)) {
         this.saveColor();
         this.field_22787.method_1507(this.parentScreen);
         return true;
      } else if (this.isInBounds(scaledX, scaledY, cancelButtonX, buttonY, 80, 30)) {
         this.field_22787.method_1507(this.parentScreen);
         return true;
      } else if (this.isInBounds(scaledX, scaledY, resetButtonX, buttonY, 80, 30)) {
         this.resetToDefault();
         return true;
      } else {
         double dx = scaledX - (double)this.wheelCenterX;
         double dy = scaledY - (double)this.wheelCenterY;
         double distance = Math.sqrt(dx * dx + dy * dy);
         if (distance <= 80.0D) {
            this.draggingWheel = true;
            this.updateColorFromWheel(scaledX, scaledY);
            return true;
         } else if (this.isInBounds(scaledX, scaledY, this.brightnessBarX, this.brightnessBarY, 43 ^ 53, 129 ^ 73)) {
            this.draggingBrightness = true;
            this.updateBrightnessFromBar(scaledY);
            return true;
         } else {
            int alphaSliderX = panelX + 50;
            int alphaSliderY = panelY + 320;
            int alphaSliderWidth = 400;
            if (this.isInBounds(scaledX, scaledY, alphaSliderX, alphaSliderY, alphaSliderWidth, 190 ^ 170)) {
               this.draggingAlpha = true;
               this.updateAlphaFromSlider(scaledX, alphaSliderX, alphaSliderWidth);
               return true;
            } else {
               return super.method_25402(scaledX, scaledY, button);
            }
         }
      }
   }

   public boolean method_25403(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
      double scaledX = mouseX * class_310.method_1551().method_22683().method_4495();
      double scaledY = mouseY * class_310.method_1551().method_22683().method_4495();
      if (this.draggingWheel) {
         this.updateColorFromWheel(scaledX, scaledY);
         return true;
      } else if (this.draggingBrightness) {
         this.updateBrightnessFromBar(scaledY);
         return true;
      } else if (this.draggingAlpha) {
         int screenWidth = this.field_22787.method_22683().method_4480();
         int panelWidth = true;
         int panelX = (screenWidth - 500) / 2;
         int alphaSliderX = panelX + 50;
         int alphaSliderWidth = 400;
         this.updateAlphaFromSlider(scaledX, alphaSliderX, alphaSliderWidth);
         return true;
      } else {
         return super.method_25403(scaledX, scaledY, button, deltaX, deltaY);
      }
   }

   public boolean method_25406(double mouseX, double mouseY, int button) {
      this.draggingWheel = false;
      this.draggingBrightness = false;
      this.draggingAlpha = false;
      return super.method_25406(mouseX, mouseY, button);
   }

   private void updateColorFromWheel(double mouseX, double mouseY) {
      double dx = mouseX - (double)this.wheelCenterX;
      double dy = mouseY - (double)this.wheelCenterY;
      double distance = Math.sqrt(dx * dx + dy * dy);
      double angle = Math.toDegrees(Math.atan2(dy, dx));
      if (angle < 0.0D) {
         angle += 360.0D;
      }

      this.hue = (float)angle;
      this.saturation = (float)Math.min(distance / 80.0D, 1.0D);
   }

   private void updateBrightnessFromBar(double mouseY) {
      float normalizedY = (float)((mouseY - (double)this.brightnessBarY) / 200.0D);
      this.brightness = 1.0F - Math.max(0.0F, Math.min(1.0F, normalizedY));
   }

   private void updateAlphaFromSlider(double mouseX, int sliderX, int sliderWidth) {
      float normalizedX = (float)((mouseX - (double)sliderX) / (double)sliderWidth);
      this.alpha = (int)(Math.max(0.0F, Math.min(1.0F, normalizedX)) * 255.0F);
   }

   private Color getCurrentColor() {
      Color baseColor = Color.getHSBColor(this.hue / 360.0F, this.saturation, this.brightness);
      return ch.safeColor(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), this.alpha);
   }

   private void saveColor() {
      this.storageColors.put(this.storageType, this.getCurrentColor());

      try {
         if (Astralux.INSTANCE != null) {
            if storageESP = (if)Astralux.INSTANCE.getModuleManager().getModuleByClass(if.class);
            if (storageESP != null) {
               storageESP.saveColors();
            }
         }
      } catch (Exception var2) {
         System.err.println("Failed to save StorageESP colors: " + var2.getMessage());
      }

   }

   private void resetToDefault() {
      Color defaultColor = this.getDefaultColor(this.storageType);
      float[] hsv = Color.RGBtoHSB(defaultColor.getRed(), defaultColor.getGreen(), defaultColor.getBlue(), (float[])null);
      this.hue = hsv[0] * 360.0F;
      this.saturation = hsv[1];
      this.brightness = hsv[2];
      this.alpha = defaultColor.getAlpha();
   }

   private Color getDefaultColor(String type) {
      int var3 = -1;
      switch(type.hashCode()) {
      case -2048964040:
         if (type.equals(d118("bXdVTUlGVnpESFA="))) {
            var3 = 4;
         }
         break;
      case -2011558552:
         if (type.equals(d118("bW9BVkxGVg=="))) {
            var3 = 3;
         }
         break;
      case -1396219994:
         if (type.equals(d118("fH5SU0dP"))) {
            var3 = 106 ^ 108;
         }
         break;
      case -1393046460:
         if (type.equals(d118("fHpBQk1N"))) {
            var3 = 12;
         }
         break;
      case -1211577292:
         if (type.equals(d118("dnBQUUdR"))) {
            var3 = 7;
         }
         break;
      case -987991687:
         if (type.equals(d118("bnZTVU1N"))) {
            var3 = 14;
         }
         break;
      case -761143606:
         if (type.equals(d118("e3FERFB8R01DVFw="))) {
            var3 = 2;
         }
         break;
      case -758067976:
         if (type.equals(d118("e3FDSUNNUExIQHddS0lASA=="))) {
            var3 = 10;
         }
         break;
      case -505639592:
         if (type.equals(d118("eGpST0NAQQ=="))) {
            var3 = 5;
         }
         break;
      case -155548860:
         if (type.equals(d118("am1BUVJGQHpFT01aXg=="))) {
            var3 = 0;
         }
         break;
      case 52679031:
         if (type.equals(d118("cnpDVUdRSg=="))) {
            var3 = 35 ^ 46;
         }
         break;
      case 94627585:
         if (type.equals(d118("fXdFUlY="))) {
            var3 = 1;
         }
         break;
      case 241511093:
         if (type.equals(d118("enZTUUdNV0BU"))) {
            var3 = 9;
         }
         break;
      case 1807616631:
         if (type.equals(d118("fG1FVktNQ3pVU0lHTg=="))) {
            var3 = 167 ^ 172;
         }
         break;
      case 1925736398:
         if (type.equals(d118("em1PUVJGVg=="))) {
            var3 = 8;
         }
      }

      switch(var3) {
      case 0:
         return new Color(200, 91, 0);
      case 1:
         return new Color(152 ^ 4, 91, 0);
      case 2:
         return new Color(117, 0, 255);
      case 3:
         return new Color(138, 126, 40 ^ 142);
      case 4:
         return new Color(246 ^ 112, 0, 158);
      case 5:
         return new Color(125, 125, 125);
      case 6:
         return new Color(255, 63 ^ 179, 206 ^ 66);
      case 7:
         return new Color(50, 217 ^ 235, 50);
      case 8:
         return new Color(128, 128, 128);
      case 9:
         return new Color(100, 100, 100);
      case 10:
         return new Color(130 ^ 210, 80, 166 ^ 89);
      case 11:
         return new Color(99 ^ 156, 200, 100);
      case 12:
         return new Color(0, 146 ^ 109, 47 ^ 208);
      case 13:
         return new Color(139, 69, 178 ^ 161);
      case 14:
         return new Color(35, 226, 0);
      default:
         return new Color(255, 236 ^ 19, 18 ^ 237);
      }
   }

   private String formatStorageTypeName(String type) {
      return this.capitalizeWords(type.replace("_", " "));
   }

   private String capitalizeWords(String str) {
      if (str != null && !str.isEmpty()) {
         String[] words = str.split(" ");
         StringBuilder result = new StringBuilder();
         String[] var4 = words;
         int var5 = words.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String word = var4[var6];
            if (!word.isEmpty()) {
               result.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1).toLowerCase()).append(" ");
            }
         }

         return result.toString().trim();
      } else {
         return "?";
      }
   }

   private boolean isInBounds(double x, double y, int bx, int by, int w, int h) {
      return x >= (double)bx && x <= (double)(bx + w) && y >= (double)by && y <= (double)(by + h);
   }

   public void method_25420(class_332 drawContext, int mouseX, int mouseY, float delta) {
   }

   public boolean method_25422() {
      return false;
   }

   // $FF: synthetic method
   private static String d118(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 10867 - 597 + var3 & (99 ^ 156));
      }

      return new String(var2);
   }
}
