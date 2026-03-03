import java.awt.Color;
import java.util.Base64;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_437;

public class hl extends class_437 {
   private final jb colorSetting;
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
   private final int brightnessBarHeight = 192 ^ 8;
   private boolean draggingWheel = false;
   private boolean draggingBrightness = false;
   private boolean draggingAlpha = false;

   public hl(class_437 parent, jb colorSetting) {
      super(class_2561.method_43470(d949("7N/d3cGU5d/U09zI")));
      this.parentScreen = parent;
      this.colorSetting = colorSetting;
      Color currentColor = new Color(colorSetting.getRed(), colorSetting.getGreen(), colorSetting.getBlue(), colorSetting.getAlpha());
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
      int panelX = (screenWidth - (455 ^ 51)) / 2;
      int panelY = (screenHeight - 450) / 2;
      this.wheelCenterX = panelX + (227 ^ 117);
      this.wheelCenterY = panelY + (34 ^ 234);
      this.brightnessBarX = panelX + (431 ^ 131);
      this.brightnessBarY = panelY + (180 ^ 208);
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getPanelBackgroundWithAlpha(84 ^ 164), (double)panelX, (double)panelY, (double)(panelX + 500), (double)(panelY + 450), 8.0D, 8.0D, 8.0D, 8.0D, 20.0D);
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getHeaderBackground(), (double)panelX, (double)panelY, (double)(panelX + (305 ^ 197)), (double)(panelY + 30), 8.0D, 8.0D, 0.0D, 0.0D, 20.0D);
      drawContext.method_25294(panelX, panelY + 30, panelX + 500, panelY + 31, bi.getMainColor(83 ^ 172, 1).getRGB());
      i.drawCenteredString(d949("7N/d3cGU5d/U09zI"), drawContext, panelX + 250, panelY + 8, aq.getBrightTextColor().getRGB());
      int previewX = panelX + 250 - 32;
      int previewY = panelY + 50;
      Color currentColor = this.getCurrentColor();
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getGridBackground(), (double)(previewX - (35 ^ 41)), (double)(previewY - (87 ^ 93)), (double)(previewX + 74), (double)(previewY + 74), 8.0D, 8.0D, 8.0D, 8.0D, 20.0D);
      iq.renderRoundedQuad(drawContext.method_51448(), currentColor, (double)previewX, (double)previewY, (double)(previewX + 64), (double)(previewY + (187 ^ 251)), 6.0D, 6.0D, 6.0D, 6.0D, 20.0D);
      this.renderColorWheel(drawContext);
      this.renderBrightnessBar(drawContext);
      this.renderAlphaSlider(drawContext, panelX + 50, panelY + (421 ^ 229), 400);
      i.drawCenteredString("RGB: " + currentColor.getRed() + ", " + currentColor.getGreen() + ", " + currentColor.getBlue(), drawContext, panelX + 250, panelY + (509 ^ 158), aq.getTextColor().getRGB());
      i.drawCenteredString("HEX: #" + String.format(d949("ioCD6paEh+6SiIvi"), currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue()), drawContext, panelX + 250, panelY + 370, aq.getTextColor().getRGB());
      int buttonY = panelY + 450 - 45;
      int saveButtonX = panelX + (488 ^ 28) - 80 - 20;
      int cancelButtonX = saveButtonX - 80 - 10;
      iq.renderRoundedQuad(drawContext.method_51448(), bi.getMainColor(255, 1), (double)saveButtonX, (double)buttonY, (double)(saveButtonX + (178 ^ 226)), (double)(buttonY + (209 ^ 207)), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);
      i.drawCenteredString(d949("/NHH1w=="), drawContext, saveButtonX + 40, buttonY + 8, aq.getBrightTextColor().getRGB());
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getButtonBorder(), (double)cancelButtonX, (double)buttonY, (double)(cancelButtonX + 80), (double)(buttonY + 30), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);
      i.drawCenteredString(d949("7NHf0dbY"), drawContext, cancelButtonX + 40, buttonY + (255 ^ 247), aq.getBrightTextColor().getRGB());
      iq.scaledProjection();
   }

   private void renderColorWheel(class_332 drawContext) {
      int segmentCount = true;
      int radiusSteps = 66 ^ 74;

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
      iq.renderCircle(drawContext.method_51448(), ch.safeColor(15 ^ 240, 255, 255, 252 ^ 52), (double)indicatorX, (double)indicatorY, 6.0D, 16);
      iq.renderCircle(drawContext.method_51448(), ch.safeColor(0, 0, 0, 200), (double)indicatorX, (double)indicatorY, 4.0D, 16);
   }

   private void drawFilledQuad(class_332 drawContext, Color color, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
      drawContext.method_25294(Math.min(Math.min(Math.min(x1, x2), x3), x4), Math.min(Math.min(Math.min(y1, y2), y3), y4), Math.max(Math.max(Math.max(x1, x2), x3), x4), Math.max(Math.max(Math.max(y1, y2), y3), y4), color.getRGB());
   }

   private void renderBrightnessBar(class_332 drawContext) {
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getGridBackground(), (double)(this.brightnessBarX - 5), (double)(this.brightnessBarY - 5), (double)(this.brightnessBarX + 30 + 5), (double)(this.brightnessBarY + 200 + 5), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);

      int indicatorY;
      for(indicatorY = 0; indicatorY < (64 ^ 136); ++indicatorY) {
         float b = 1.0F - (float)indicatorY / 200.0F;
         Color gradientColor = Color.getHSBColor(this.hue / 360.0F, this.saturation, b);
         drawContext.method_25294(this.brightnessBarX, this.brightnessBarY + indicatorY, this.brightnessBarX + (136 ^ 150), this.brightnessBarY + indicatorY + 1, gradientColor.getRGB());
      }

      indicatorY = (int)((float)this.brightnessBarY + (1.0F - this.brightness) * 200.0F);
      drawContext.method_25294(this.brightnessBarX - 3, indicatorY - 2, this.brightnessBarX + (55 ^ 41) + 3, indicatorY + 2, ch.safeColor(169 ^ 86, 14 ^ 241, 255, 34 ^ 221).getRGB());
      drawContext.method_25294(this.brightnessBarX - 2, indicatorY - 1, this.brightnessBarX + 30 + 2, indicatorY + 1, ch.safeColor(0, 0, 0, 56 ^ 199).getRGB());
   }

   private void renderAlphaSlider(class_332 drawContext, int x, int y, int width) {
      i.drawString("Alpha: " + this.alpha, drawContext, x, y - (101 ^ 106), ch.safeColor(200, 200, 200, 106 ^ 149).getRGB());
      iq.renderRoundedQuad(drawContext.method_51448(), ch.safeColor(176 ^ 164, 20, 186 ^ 163, 123 ^ 132), (double)x, (double)y, (double)(x + width), (double)(y + 20), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);

      for(int i = 0; i < width; i += 10) {
         Color checker = i / 10 % 2 == 0 ? ch.safeColor(100, 100, 212 ^ 176, 17 ^ 238) : ch.safeColor(150, 199 ^ 81, 150, 255);
         drawContext.method_25294(x + i, y, x + i + 10, y + 20, checker.getRGB());
      }

      Color baseColor = Color.getHSBColor(this.hue / 360.0F, this.saturation, this.brightness);

      int indicatorX;
      for(indicatorX = 0; indicatorX < width; ++indicatorX) {
         int a = (int)((float)indicatorX / (float)width * 255.0F);
         Color alphaColor = ch.safeColor(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), a);
         drawContext.method_25294(x + indicatorX, y, x + indicatorX + 1, y + 20, alphaColor.getRGB());
      }

      indicatorX = (int)((float)x + (float)this.alpha / 255.0F * (float)width);
      drawContext.method_25294(indicatorX - 2, y - 2, indicatorX + 2, y + (124 ^ 106), ch.safeColor(255, 39 ^ 216, 255, 255).getRGB());
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
      int panelY = (screenHeight - 450) / 2;
      int buttonY = panelY + 450 - 45;
      int saveButtonX = panelX + 500 - (37 ^ 117) - 20;
      int cancelButtonX = saveButtonX - (235 ^ 187) - (100 ^ 110);
      if (this.isInBounds(scaledX, scaledY, saveButtonX, buttonY, 80, 30)) {
         this.colorSetting.setValue(this.getCurrentColor());
         this.field_22787.method_1507(this.parentScreen);
         return true;
      } else if (this.isInBounds(scaledX, scaledY, cancelButtonX, buttonY, 80, 123 ^ 101)) {
         this.field_22787.method_1507(this.parentScreen);
         return true;
      } else {
         double dx = scaledX - (double)this.wheelCenterX;
         double dy = scaledY - (double)this.wheelCenterY;
         double distance = Math.sqrt(dx * dx + dy * dy);
         if (distance <= 80.0D) {
            this.draggingWheel = true;
            this.updateColorFromWheel(scaledX, scaledY);
            return true;
         } else if (this.isInBounds(scaledX, scaledY, this.brightnessBarX, this.brightnessBarY, 24 ^ 6, 127 ^ 183)) {
            this.draggingBrightness = true;
            this.updateBrightnessFromBar(scaledY);
            return true;
         } else {
            int alphaSliderX = panelX + (34 ^ 16);
            int alphaSliderY = panelY + (261 ^ 69);
            int alphaSliderWidth = 354 ^ 242;
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
         int panelWidth = 394 ^ 126;
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

   private boolean isInBounds(double x, double y, int bx, int by, int w, int h) {
      return x >= (double)bx && x <= (double)(bx + w) && y >= (double)by && y <= (double)(by + h);
   }

   public void method_25420(class_332 drawContext, int mouseX, int mouseY, float delta) {
   }

   public boolean method_25422() {
      return false;
   }

   // $FF: synthetic method
   private static String d949(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 5807 + var3 & (228 ^ 27));
      }

      return new String(var2);
   }
}
