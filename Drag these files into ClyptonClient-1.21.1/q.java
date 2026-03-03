import client.astralux.Astralux;
import java.awt.Color;
import java.util.Base64;
import java.util.Map;
import net.minecraft.class_1799;
import net.minecraft.class_2248;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_437;
import net.minecraft.class_7923;

public class q extends class_437 {
   private final class_2248 block;
   private final Map<class_2248, Color> blockColors;
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

   public q(class_437 parent, class_2248 block, Map<class_2248, Color> blockColors) {
      super(class_2561.method_43473());
      this.parentScreen = parent;
      this.block = block;
      this.blockColors = blockColors;
      Color currentColor = (Color)blockColors.getOrDefault(block, this.generateDefaultColor(block));
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
      int panelHeight = 426 ^ 104;
      int panelX = (screenWidth - 500) / 2;
      int panelY = (screenHeight - 450) / 2;
      this.wheelCenterX = panelX + 150;
      this.wheelCenterY = panelY + 200;
      this.brightnessBarX = panelX + 300;
      this.brightnessBarY = panelY + 100;
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getPanelBackgroundWithAlpha(240), (double)panelX, (double)panelY, (double)(panelX + 500), (double)(panelY + 450), 8.0D, 8.0D, 8.0D, 8.0D, 20.0D);
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getHeaderBackground(), (double)panelX, (double)panelY, (double)(panelX + 500), (double)(panelY + 30), 8.0D, 8.0D, 0.0D, 0.0D, 20.0D);
      drawContext.method_25294(panelX, panelY + 30, panelX + (340 ^ 160), panelY + 31, bi.getMainColor(201 ^ 54, 1).getRGB());
      String blockName = this.getBlockName(this.block);
      i.drawCenteredString("Edit Color: " + blockName, drawContext, panelX + 250, panelY + 8, aq.getBrightTextColor().getRGB());
      int previewX = panelX + 250 - (17 ^ 49);
      int previewY = panelY + 50;
      Color currentColor = this.getCurrentColor();
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getGridBackground(), (double)(previewX - 10), (double)(previewY - 10), (double)(previewX + 74), (double)(previewY + (54 ^ 124)), 8.0D, 8.0D, 8.0D, 8.0D, 20.0D);
      iq.renderRoundedQuad(drawContext.method_51448(), currentColor, (double)previewX, (double)previewY, (double)(previewX + (15 ^ 79)), (double)(previewY + 64), 6.0D, 6.0D, 6.0D, 6.0D, 20.0D);
      iq.drawItem(drawContext, new class_1799(this.block.method_8389()), previewX + 8, previewY + (163 ^ 171), 48.0F, 0);
      this.renderColorWheel(drawContext);
      this.renderBrightnessBar(drawContext);
      this.renderAlphaSlider(drawContext, panelX + 50, panelY + (343 ^ 23), 281 ^ 137);
      i.drawCenteredString("RGB: " + currentColor.getRed() + ", " + currentColor.getGreen() + ", " + currentColor.getBlue(), drawContext, panelX + 250, panelY + (356 ^ 7), aq.getTextColor().getRGB());
      i.drawCenteredString("HEX: #" + String.format(d377("h5OW/YOXmvGPm571"), currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue()), drawContext, panelX + 250, panelY + 370, aq.getTextColor().getRGB());
      int buttonY = panelY + 450 - 45;
      int saveButtonX = panelX + (448 ^ 52) - (188 ^ 236) - 20;
      int cancelButtonX = saveButtonX - (85 ^ 5) - 10;
      int resetButtonX = cancelButtonX - (84 ^ 4) - 10;
      iq.renderRoundedQuad(drawContext.method_51448(), bi.getMainColor(255, 1), (double)saveButtonX, (double)buttonY, (double)(saveButtonX + (216 ^ 136)), (double)(buttonY + 30), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);
      i.drawCenteredString(d377("8cLSwA=="), drawContext, saveButtonX + 40, buttonY + 8, aq.getBrightTextColor().getRGB());
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getButtonBorder(), (double)cancelButtonX, (double)buttonY, (double)(cancelButtonX + (221 ^ 141)), (double)(buttonY + 30), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);
      i.drawCenteredString(d377("4cLKxsPL"), drawContext, cancelButtonX + 40, buttonY + 8, aq.getBrightTextColor().getRGB());
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getSecondaryAccent(), (double)resetButtonX, (double)buttonY, (double)(resetButtonX + 80), (double)(buttonY + (123 ^ 101)), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);
      i.drawCenteredString(d377("8MbXwNI="), drawContext, resetButtonX + 40, buttonY + 8, aq.getBrightTextColor().getRGB());
      iq.scaledProjection();
   }

   private void renderColorWheel(class_332 drawContext) {
      int segmentCount = true;
      int radiusSteps = 133 ^ 141;

      for(int segment = 0; segment < 60; ++segment) {
         float hue1 = (float)segment / 60.0F * 360.0F;
         float hue2 = (float)(segment + 1) / 60.0F * 360.0F;
         Color segmentColor = Color.getHSBColor(hue1 / 360.0F, 1.0F, this.brightness);

         for(int radius = 0; radius < 8; ++radius) {
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
      iq.renderCircle(drawContext.method_51448(), ch.safeColor(75 ^ 180, 255, 127 ^ 128, 101 ^ 173), (double)indicatorX, (double)indicatorY, 6.0D, 16);
      iq.renderCircle(drawContext.method_51448(), ch.safeColor(0, 0, 0, 200), (double)indicatorX, (double)indicatorY, 4.0D, 16);
   }

   private void drawFilledQuad(class_332 drawContext, Color color, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
      drawContext.method_25294(Math.min(Math.min(Math.min(x1, x2), x3), x4), Math.min(Math.min(Math.min(y1, y2), y3), y4), Math.max(Math.max(Math.max(x1, x2), x3), x4), Math.max(Math.max(Math.max(y1, y2), y3), y4), color.getRGB());
   }

   private void renderBrightnessBar(class_332 drawContext) {
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getGridBackground(), (double)(this.brightnessBarX - 5), (double)(this.brightnessBarY - 5), (double)(this.brightnessBarX + 30 + 5), (double)(this.brightnessBarY + 200 + 5), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);

      int indicatorY;
      for(indicatorY = 0; indicatorY < (196 ^ 12); ++indicatorY) {
         float b = 1.0F - (float)indicatorY / 200.0F;
         Color gradientColor = Color.getHSBColor(this.hue / 360.0F, this.saturation, b);
         drawContext.method_25294(this.brightnessBarX, this.brightnessBarY + indicatorY, this.brightnessBarX + (187 ^ 165), this.brightnessBarY + indicatorY + 1, gradientColor.getRGB());
      }

      indicatorY = (int)((float)this.brightnessBarY + (1.0F - this.brightness) * 200.0F);
      drawContext.method_25294(this.brightnessBarX - 3, indicatorY - 2, this.brightnessBarX + (190 ^ 160) + 3, indicatorY + 2, ch.safeColor(116 ^ 139, 255, 146 ^ 109, 255).getRGB());
      drawContext.method_25294(this.brightnessBarX - 2, indicatorY - 1, this.brightnessBarX + 30 + 2, indicatorY + 1, ch.safeColor(0, 0, 0, 255).getRGB());
   }

   private void renderAlphaSlider(class_332 drawContext, int x, int y, int width) {
      i.drawString("Alpha: " + this.alpha, drawContext, x, y - (11 ^ 4), ch.safeColor(200, 172 ^ 100, 200, 255).getRGB());
      iq.renderRoundedQuad(drawContext.method_51448(), ch.safeColor(20, 20, 25, 130 ^ 125), (double)x, (double)y, (double)(x + width), (double)(y + 20), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);

      for(int i = 0; i < width; i += 10) {
         Color checker = i / 10 % 2 == 0 ? ch.safeColor(100, 100, 83 ^ 55, 245 ^ 10) : ch.safeColor(150, 150, 16 ^ 134, 4 ^ 251);
         drawContext.method_25294(x + i, y, x + i + (48 ^ 58), y + 20, checker.getRGB());
      }

      Color baseColor = Color.getHSBColor(this.hue / 360.0F, this.saturation, this.brightness);

      int indicatorX;
      for(indicatorX = 0; indicatorX < width; ++indicatorX) {
         int a = (int)((float)indicatorX / (float)width * 255.0F);
         Color alphaColor = ch.safeColor(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), a);
         drawContext.method_25294(x + indicatorX, y, x + indicatorX + 1, y + (5 ^ 17), alphaColor.getRGB());
      }

      indicatorX = (int)((float)x + (float)this.alpha / 255.0F * (float)width);
      drawContext.method_25294(indicatorX - 2, y - 2, indicatorX + 2, y + 22, ch.safeColor(255, 79 ^ 176, 255, 201 ^ 54).getRGB());
      drawContext.method_25294(indicatorX - 1, y, indicatorX + 1, y + 20, this.getCurrentColor().getRGB());
   }

   public boolean method_25402(double mouseX, double mouseY, int button) {
      double scaledX = mouseX * class_310.method_1551().method_22683().method_4495();
      double scaledY = mouseY * class_310.method_1551().method_22683().method_4495();
      int screenWidth = this.field_22787.method_22683().method_4480();
      int screenHeight = this.field_22787.method_22683().method_4507();
      int panelWidth = true;
      int panelHeight = true;
      int panelX = (screenWidth - 500) / 2;
      int panelY = (screenHeight - (453 ^ 7)) / 2;
      int buttonY = panelY + 450 - 45;
      int saveButtonX = panelX + 500 - 80 - (183 ^ 163);
      int cancelButtonX = saveButtonX - 80 - (141 ^ 135);
      int resetButtonX = cancelButtonX - 80 - 10;
      if (this.isInBounds(scaledX, scaledY, saveButtonX, buttonY, 80, 30)) {
         this.saveColor();
         this.field_22787.method_1507(this.parentScreen);
         return true;
      } else if (this.isInBounds(scaledX, scaledY, cancelButtonX, buttonY, 181 ^ 229, 30)) {
         this.field_22787.method_1507(this.parentScreen);
         return true;
      } else if (this.isInBounds(scaledX, scaledY, resetButtonX, buttonY, 2 ^ 82, 30)) {
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
         } else if (this.isInBounds(scaledX, scaledY, this.brightnessBarX, this.brightnessBarY, 70 ^ 88, 103 ^ 175)) {
            this.draggingBrightness = true;
            this.updateBrightnessFromBar(scaledY);
            return true;
         } else {
            int alphaSliderX = panelX + 50;
            int alphaSliderY = panelY + 320;
            int alphaSliderWidth = 276 ^ 132;
            if (this.isInBounds(scaledX, scaledY, alphaSliderX, alphaSliderY, alphaSliderWidth, 20)) {
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
         int panelWidth = 443 ^ 79;
         int panelX = (screenWidth - 500) / 2;
         int alphaSliderX = panelX + (107 ^ 89);
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
      this.blockColors.put(this.block, this.getCurrentColor());

      try {
         if (Astralux.INSTANCE != null) {
            dg blockESP = (dg)Astralux.INSTANCE.getModuleManager().getModuleByClass(dg.class);
            if (blockESP != null) {
               blockESP.saveColors();
            }
         }
      } catch (Exception var2) {
         System.err.println("Failed to save BlockESP colors: " + var2.getMessage());
      }

   }

   private void resetToDefault() {
      Color defaultColor = this.generateDefaultColor(this.block);
      float[] hsv = Color.RGBtoHSB(defaultColor.getRed(), defaultColor.getGreen(), defaultColor.getBlue(), (float[])null);
      this.hue = hsv[0] * 360.0F;
      this.saturation = hsv[1];
      this.brightness = hsv[2];
      this.alpha = defaultColor.getAlpha();
   }

   private Color generateDefaultColor(class_2248 block) {
      int hash = block.hashCode();
      int r = Math.max((hash & 16711817 - 137) >> 16, 100);
      int g = Math.max((hash & '\uff00') >> 8, 100);
      int b = Math.max(hash & (154 ^ 101), 100);
      return ch.safeColor(r, g, b, 202 ^ 53);
   }

   private String getBlockName(class_2248 block) {
      try {
         String translationKey = block.method_9539();
         class_2561 translated = class_2561.method_43471(translationKey);
         String name = translated.getString();
         if (!name.equals(translationKey) && !name.isEmpty()) {
            return name;
         } else {
            String registryName = class_7923.field_41175.method_10221(block).method_12832();
            return this.capitalizeWords(registryName.replace("_", " "));
         }
      } catch (Exception var6) {
         return "?";
      }
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
   private static String d377(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 6281 - 743 + var3 & 255);
      }

      return new String(var2);
   }
}
