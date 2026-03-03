import client.astralux.Astralux;
import java.awt.Color;
import java.util.Base64;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_437;

public class id extends class_437 {
   private static Astralux astraluxInstance;
   private final class_437 parentScreen;
   private String editingColor = d824("s5aMi4aakA==");
   private float hue = 0.0F;
   private float saturation = 1.0F;
   private float brightness = 1.0F;
   private int alpha = 255;
   private boolean rgbEnabled = false;
   private float rgbSpeed = 1.0F;
   private float rgbSat = 0.8F;
   private float rgbBright = 1.0F;
   private boolean syncRGB = true;
   private final int wheelRadius = 80;
   private int wheelCenterX;
   private int wheelCenterY;
   private int brightnessBarX;
   private int brightnessBarY;
   private final int brightnessBarWidth = 180 ^ 170;
   private final int brightnessBarHeight = 159 ^ 87;
   private boolean draggingWheel = false;
   private boolean draggingBrightness = false;
   private boolean draggingAlpha = false;
   private boolean draggingRGBSpeed = false;
   private boolean draggingRGBSat = false;
   private boolean draggingRGBBright = false;
   private String[] tabs = new String[]{d824("oIuJiZXIuYOIh4ic"), d824("saOnxrSNnZ6Cgoqd"), d824("s5aAlYKcmg=="), d824("t4yAi4Kb")};
   private int selectedTab = 0;
   private float[] tabAnimations = new float[4];
   private float[] themeButtonAnimations;
   private ai selectedTheme;
   private final io[] presets = new io[]{new io(d824("oJ2Hg5XIq4aeiQ=="), Color.decode(d824("wNTVot6urw==")), Color.decode(d824("wNWE14bajA=="))), new io(d824("rYGKiMe4gISA"), Color.decode(d824("wKKj1tferA==")), Color.decode(d824("wNSD1oHZjA=="))), new io(d824("t4udj4TIrpiOiYM="), Color.decode(d824("wNfcoKHZ3Q==")), Color.decode(d824("wNSE1obYiA=="))), new io(d824("s5GXlouNyaKKlog="), Color.decode(d824("wN2h0qKsrQ==")), Color.decode(d824("wNWA19Db2g=="))), new io(d824("pY2Xg8enm4uFi4g="), Color.decode(d824("wKKj0KXb3A==")), Color.decode(d824("wNaE19/Z2Q=="))), new io(d824("qoeAxqWEnI8="), Color.decode(d824("wKbRo9Curw==")), Color.decode(d824("wNSB14XaiA==")))};

   public id(class_437 parent) {
      super(class_2561.method_43473());
      this.parentScreen = parent;
      this.loadCurrentColor();
      ai[] themes = ai.values();
      this.themeButtonAnimations = new float[themes.length];
      this.selectedTheme = this.getCurrentThemeFromClickGUI();

      for(int i = 0; i < this.themeButtonAnimations.length; ++i) {
         this.themeButtonAnimations[i] = themes[i] == this.selectedTheme ? 1.0F : 0.0F;
      }

      try {
         if (astraluxInstance == null) {
            astraluxInstance = Astralux.INSTANCE;
         }

         fd themeManager = (fd)astraluxInstance.getModuleManager().getModuleByClass(fd.class);
         if (themeManager != null) {
            this.rgbEnabled = themeManager.getRgbMode().getValue();
            this.rgbSpeed = themeManager.getRgbSpeed().getFloatValue();
            this.rgbSat = themeManager.getRgbSaturation().getFloatValue();
            this.rgbBright = themeManager.getRgbBrightness().getFloatValue();
            this.syncRGB = themeManager.getSyncRGB().getValue();
         }
      } catch (Exception var4) {
      }

      this.tabAnimations[0] = 1.0F;
   }

   private ai getCurrentThemeFromClickGUI() {
      try {
         if (this.parentScreen instanceof gf) {
            return ((gf)this.parentScreen).currentTheme;
         }
      } catch (Exception var2) {
      }

      return ai.CYBER_NEON;
   }

   private void loadCurrentColor() {
      Color currentColor = aq.getPrimaryAccent();
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
      int bgAlpha = Astralux.renderBackground.getValue() ? 180 : 0;
      drawContext.method_25294(0, 0, screenWidth, screenHeight, ch.safeColor(0, 0, 0, bgAlpha).getRGB());
      int panelWidth = true;
      int panelHeight = true;
      int panelX = (screenWidth - 550) / 2;
      int panelY = (screenHeight - (273 ^ 229)) / 2;
      iq.renderRoundedQuad(drawContext.method_51448(), ch.safeColor(111 ^ 101, 10, 30 ^ 12, 186 ^ 64), (double)(panelX - 2), (double)(panelY - 2), (double)(panelX + 550 + 2), (double)(panelY + 500 + 2), 10.0D, 10.0D, 10.0D, 10.0D, 20.0D);
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getPanelBackgroundWithAlpha(27 ^ 235), (double)panelX, (double)panelY, (double)(panelX + 550), (double)(panelY + 500), 8.0D, 8.0D, 8.0D, 8.0D, 20.0D);
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getHeaderBackground(), (double)panelX, (double)panelY, (double)(panelX + 550), (double)(panelY + (132 ^ 172)), 8.0D, 8.0D, 0.0D, 0.0D, 20.0D);
      if (this.rgbEnabled) {
         long time = System.currentTimeMillis();

         for(int x = 0; x < 550; x += 10) {
            float hue = ((float)time * this.rgbSpeed / 1000.0F + (float)x / 100.0F) % 1.0F;
            Color rgbColor = Color.getHSBColor(hue, this.rgbSat, this.rgbBright);
            drawContext.method_25294(panelX + x, panelY + (85 ^ 125), panelX + x + 8, panelY + 43, ch.safeColor(rgbColor.getRed(), rgbColor.getGreen(), rgbColor.getBlue(), 200).getRGB());
         }
      } else {
         drawContext.method_25294(panelX, panelY + 40, panelX + (690 ^ 148), panelY + 42, bi.getMainColor(255, 1).getRGB());
      }

      i.drawCenteredString(d824("t4yAi4LIqoWHg5/OopGfk5SRhw=="), drawContext, panelX + (364 ^ 127), panelY + 12, aq.getBrightTextColor().getRGB());
      this.updateTabAnimations(delta);
      this.updateThemeButtonAnimations(delta);
      this.renderTabs(drawContext, panelX, panelY, 687 ^ 137, scaledMouseX, scaledMouseY);
      switch(this.selectedTab) {
      case 0:
         this.renderColorPickerTab(drawContext, panelX, panelY, 550, 500, scaledMouseX, scaledMouseY);
         break;
      case 1:
         this.renderRGBSettingsTab(drawContext, panelX, panelY, 550, 500, scaledMouseX, scaledMouseY);
         break;
      case 2:
         this.renderPresetsTab(drawContext, panelX, panelY, 550, 393 ^ 125, scaledMouseX, scaledMouseY);
         break;
      case 3:
         this.renderThemesTab(drawContext, panelX, panelY, 741 ^ 195, 499 ^ 7, scaledMouseX, scaledMouseY);
      }

      this.renderBottomButtons(drawContext, panelX, panelY, 550, 500);
      iq.scaledProjection();
   }

   private void updateTabAnimations(float delta) {
      for(int i = 0; i < this.tabAnimations.length; ++i) {
         float target = i == this.selectedTab ? 1.0F : 0.0F;
         float[] var10000 = this.tabAnimations;
         var10000[i] += (target - this.tabAnimations[i]) * delta * 0.15F;
      }

   }

   private void updateThemeButtonAnimations(float delta) {
      ai[] themes = ai.values();

      for(int i = 0; i < this.themeButtonAnimations.length; ++i) {
         float target = themes[i] == this.selectedTheme ? 1.0F : 0.0F;
         float[] var10000 = this.themeButtonAnimations;
         var10000[i] += (target - this.themeButtonAnimations[i]) * delta * 0.12F;
      }

   }

   private void renderTabs(class_332 drawContext, int panelX, int panelY, int panelWidth, int mouseX, int mouseY) {
      int tabY = panelY + 55;
      int tabHeight = true;
      int tabWidth = 93 ^ 37;
      int spacing = true;
      int totalWidth = this.tabs.length * 120 + (this.tabs.length - 1) * 8;
      int startX = panelX + (panelWidth - totalWidth) / 2;

      for(int i = 0; i < this.tabs.length; ++i) {
         int tabX = startX + i * 128;
         boolean isHovered = mouseX >= tabX && mouseX <= tabX + (145 ^ 233) && mouseY >= tabY && mouseY <= tabY + (44 ^ 15);
         float anim = this.tabAnimations[i];
         Color bgColor = ch.a(ch.safeColor(137 ^ 151, 107 ^ 117, 102 ^ 76, 150), aq.getPrimaryAccent(), anim * 0.6F);
         iq.renderRoundedQuad(drawContext.method_51448(), bgColor, (double)tabX, (double)tabY, (double)(tabX + 120), (double)(tabY + 35), 6.0D, 6.0D, 6.0D, 6.0D, 20.0D);
         int textBrightness;
         if (anim > 0.2F || isHovered) {
            textBrightness = (int)(100.0F + 100.0F * anim);
            iq.renderRoundedOutline(drawContext, ch.safeColor(aq.getPrimaryAccent().getRed(), aq.getPrimaryAccent().getGreen(), aq.getPrimaryAccent().getBlue(), textBrightness), (double)tabX, (double)tabY, (double)(tabX + 120), (double)(tabY + 35), 6.0D, 6.0D, 6.0D, 6.0D, 1.5D, 20.0D);
         }

         textBrightness = (int)(180.0F + 75.0F * anim);
         i.drawCenteredString(this.tabs[i], drawContext, tabX + (200 ^ 244), tabY + 11, ch.safeColor(textBrightness, textBrightness, textBrightness, 255).getRGB());
      }

   }

   private void renderThemesTab(class_332 drawContext, int panelX, int panelY, int panelWidth, int panelHeight, int mouseX, int mouseY) {
      i.drawCenteredString(d824("sIGJg4Scyb6DiYCL"), drawContext, panelX + panelWidth / 2, panelY + (112 ^ 30), aq.getBrightTextColor().getRGB());
      ai[] themes = ai.values();
      int buttonSize = true;
      int spacing = 8 ^ 7;
      int cols = true;
      int totalWidth = true;
      int startX = panelX + (panelWidth - 240) / 2;
      int startY = panelY + (144 ^ 28);
      long time = System.currentTimeMillis();

      for(int i = 0; i < themes.length; ++i) {
         int row = i / 3;
         int col = i % 3;
         int btnX = startX + col * 85;
         int btnY = startY + row * (201 ^ 167);
         ai theme = themes[i];
         boolean isSelected = this.selectedTheme == theme;
         boolean isHovered = mouseX >= btnX && mouseX <= btnX + 70 && mouseY >= btnY && mouseY <= btnY + (116 ^ 50);
         iq.renderRoundedQuad(drawContext.method_51448(), ch.safeColor(20, 20, 77 ^ 83, 114 ^ 22), (double)(btnX - 4), (double)(btnY - 4), (double)(btnX + (194 ^ 132) + 4), (double)(btnY + (90 ^ 28) + 4), 11.0D, 11.0D, 11.0D, 11.0D, 40.0D);
         int bgAlpha = (int)(150.0F + 105.0F * this.themeButtonAnimations[i]);
         Color bgColor = ch.safeColor(theme.primaryAccent.getRed(), theme.primaryAccent.getGreen(), theme.primaryAccent.getBlue(), bgAlpha);
         iq.renderRoundedQuad(drawContext.method_51448(), bgColor, (double)btnX, (double)btnY, (double)(btnX + 70), (double)(btnY + (114 ^ 52)), 10.0D, 10.0D, 10.0D, 10.0D, 40.0D);
         int outlineAlpha;
         if (isSelected) {
            outlineAlpha = (int)(150.0D + 50.0D * Math.sin((double)time / 500.0D));
            iq.renderRoundedQuad(drawContext.method_51448(), ch.safeColor(theme.primaryAccent.getRed(), theme.primaryAccent.getGreen(), theme.primaryAccent.getBlue(), outlineAlpha / 3), (double)(btnX - (183 ^ 177)), (double)(btnY - 6), (double)(btnX + (219 ^ 157) + 6), (double)(btnY + 70 + 6), 10.0D, 10.0D, 10.0D, 10.0D, 40.0D);
         }

         if (this.themeButtonAnimations[i] > 0.15F || isHovered) {
            outlineAlpha = (int)(80.0F + 120.0F * this.themeButtonAnimations[i]);
            float outlineWidth = 1.5F + 0.5F * this.themeButtonAnimations[i];
            iq.renderRoundedOutline(drawContext, ch.safeColor(theme.primaryAccent.getRed(), theme.primaryAccent.getGreen(), theme.primaryAccent.getBlue(), outlineAlpha), (double)btnX, (double)btnY, (double)(btnX + 70), (double)(btnY + (2 ^ 68)), 10.0D, 10.0D, 10.0D, 10.0D, (double)outlineWidth, 40.0D);
         }

         String themeInitial = theme.displayName.substring(0, 1);
         int textWidth = i.getWidth(themeInitial);
         int textAlpha = (int)(180.0F + 75.0F * this.themeButtonAnimations[i]);
         i.drawString(themeInitial, drawContext, btnX + ((131 ^ 197) - textWidth) / 2, btnY + 27, ch.safeColor(255, 255, 255, textAlpha).getRGB());
         int nameWidth = i.getWidth(theme.displayName);
         i.drawString(theme.displayName, drawContext, btnX + ((8 ^ 78) - nameWidth) / 2, btnY + 70 + (100 ^ 108), ch.safeColor(200, 200, 210, 255).getRGB());
      }

   }

   private void renderColorPickerTab(class_332 drawContext, int panelX, int panelY, int panelWidth, int panelHeight, int mouseX, int mouseY) {
      this.wheelCenterX = panelX + (78 ^ 216);
      this.wheelCenterY = panelY + 220;
      this.brightnessBarX = panelX + 320;
      this.brightnessBarY = panelY + 130;
      int previewSize = true;
      int previewX = panelX + panelWidth - 80 - 40;
      int previewY = panelY + 130;
      Color currentColor = this.getCurrentColor();
      iq.renderRoundedQuad(drawContext.method_51448(), ch.safeColor(20, 105 ^ 125, 30, 98 ^ 170), (double)(previewX - 5), (double)(previewY - 5), (double)(previewX + (65 ^ 17) + 5), (double)(previewY + (43 ^ 123) + 5), 8.0D, 8.0D, 8.0D, 8.0D, 20.0D);
      iq.renderRoundedQuad(drawContext.method_51448(), currentColor, (double)previewX, (double)previewY, (double)(previewX + 80), (double)(previewY + 80), 6.0D, 6.0D, 6.0D, 6.0D, 20.0D);
      i.drawCenteredString(d824("s5aAkI6Nng=="), drawContext, previewX + 40, previewY - 20, aq.getTextColor().getRGB());
      this.renderColorWheel(drawContext);
      this.renderBrightnessBar(drawContext);
      this.renderAlphaSlider(drawContext, panelX + 50, panelY + (393 ^ 225), panelWidth - 100);
      i.drawCenteredString("RGB: " + currentColor.getRed() + ", " + currentColor.getGreen() + ", " + currentColor.getBlue(), drawContext, panelX + panelWidth / 2, panelY + (497 ^ 122), aq.getTextColor().getRGB());
      i.drawCenteredString("HEX: #" + String.format(d824("xtTXvsLY27LO3N+2"), currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue()), drawContext, panelX + panelWidth / 2, panelY + (273 ^ 139), aq.getTextColor().getRGB());
   }

   private void renderRGBSettingsTab(class_332 drawContext, int panelX, int panelY, int panelWidth, int panelHeight, int mouseX, int mouseY) {
      int yOffset = panelY + 120;
      int xStart = panelX + 50;
      int settingWidth = panelWidth - (206 ^ 170);
      this.renderToggle(drawContext, d824("saOnxqqHjY8="), this.rgbEnabled, xStart, yOffset, settingWidth);
      yOffset += 50;
      if (this.rgbEnabled) {
         this.renderSliderSetting(drawContext, d824("saOnxrSYjI+P"), this.rgbSpeed, 0.1F, 5.0F, xStart, yOffset, settingWidth);
         yOffset += 60;
         this.renderSliderSetting(drawContext, d824("sIWRk5WJnYOEgg=="), this.rgbSat, 0.0F, 1.0F, xStart, yOffset, settingWidth);
         yOffset += 60;
         this.renderSliderSetting(drawContext, d824("oZaMgY+ch4+Ynw=="), this.rgbBright, 0.0F, 1.0F, xStart, yOffset, settingWidth);
         yOffset += 60;
         this.renderToggle(drawContext, d824("sJ2Lhce6rqg="), this.syncRGB, xStart, yOffset, settingWidth);
         yOffset += 50;
         i.drawCenteredString(d824("saOnxreajJyCiZo="), drawContext, panelX + panelWidth / 2, yOffset, aq.getTextColor().getRGB());
         yOffset += 25;
         long time = System.currentTimeMillis();

         for(int x = 0; x < settingWidth; x += 5) {
            float hue = ((float)time * this.rgbSpeed / 1000.0F + (float)x / 100.0F) % 1.0F;
            Color rgbColor = Color.getHSBColor(hue, this.rgbSat, this.rgbBright);
            drawContext.method_25294(xStart + x, yOffset, xStart + x + 4, yOffset + (232 ^ 246), rgbColor.getRGB());
         }
      }

   }

   private void renderPresetsTab(class_332 drawContext, int panelX, int panelY, int panelWidth, int panelHeight, int mouseX, int mouseY) {
      i.drawCenteredString(d824("oIuJiZXIuZiOn4ianA=="), drawContext, panelX + panelWidth / 2, panelY + 110, aq.getBrightTextColor().getRGB());
      int cols = 3;
      int presetWidth = 140;
      int presetHeight = 80;
      int spacing = 20;
      int startX = panelX + (panelWidth - (cols * presetWidth + (cols - 1) * spacing)) / 2;
      int startY = panelY + 140;

      for(int i = 0; i < this.presets.length; ++i) {
         int row = i / cols;
         int col = i % cols;
         int x = startX + col * (presetWidth + spacing);
         int y = startY + row * (presetHeight + spacing);
         io preset = this.presets[i];
         boolean isHovered = mouseX >= x && mouseX <= x + presetWidth && mouseY >= y && mouseY <= y + presetHeight;
         iq.renderRoundedQuad(drawContext.method_51448(), ch.safeColor(25, 234 ^ 243, 35, 200), (double)x, (double)y, (double)(x + presetWidth), (double)(y + presetHeight), 6.0D, 6.0D, 6.0D, 6.0D, 20.0D);
         iq.renderRoundedQuad(drawContext.method_51448(), preset.primary, (double)(x + 5), (double)(y + 5), (double)(x + presetWidth / 2 - 2), (double)(y + presetHeight - 30), 4.0D, 0.0D, 0.0D, 4.0D, 20.0D);
         iq.renderRoundedQuad(drawContext.method_51448(), preset.background, (double)(x + presetWidth / 2 + 2), (double)(y + 5), (double)(x + presetWidth - 5), (double)(y + presetHeight - 30), 0.0D, 4.0D, 4.0D, 0.0D, 20.0D);
         i.drawCenteredString(preset.name, drawContext, x + presetWidth / 2, y + presetHeight - 18, ch.safeColor(220, 132 ^ 88, 11 ^ 237, 255).getRGB());
         if (isHovered) {
            iq.renderRoundedOutline(drawContext, aq.getPrimaryAccent(), (double)x, (double)y, (double)(x + presetWidth), (double)(y + presetHeight), 6.0D, 6.0D, 6.0D, 6.0D, 2.0D, 20.0D);
         }
      }

   }

   private void renderToggle(class_332 drawContext, String label, boolean value, int x, int y, int width) {
      i.drawString(label, drawContext, x, y, aq.getTextColor().getRGB());
      int toggleX = x + width - 50;
      int toggleY = y - 2;
      Color bgColor = value ? aq.getPrimaryAccent() : ch.safeColor(40, 127 ^ 87, 55, 200);
      iq.renderRoundedQuad(drawContext.method_51448(), bgColor, (double)toggleX, (double)toggleY, (double)(toggleX + 40), (double)(toggleY + (200 ^ 220)), 10.0D, 10.0D, 10.0D, 10.0D, 20.0D);
      float knobX = value ? (float)(toggleX + (136 ^ 158)) : (float)(toggleX + (224 ^ 234));
      iq.renderCircle(drawContext.method_51448(), ch.safeColor(57 ^ 198, 255, 111 ^ 144, 255), (double)knobX, (double)(toggleY + (213 ^ 223)), 8.0D, 16);
   }

   private void renderSliderSetting(class_332 drawContext, String label, float value, float min, float max, int x, int y, int width) {
      i.drawString(label + ": " + String.format(d824("xsrXgA=="), value), drawContext, x, y, aq.getTextColor().getRGB());
      int sliderY = y + (32 ^ 52);
      iq.renderRoundedQuad(drawContext.method_51448(), ch.safeColor(25, 25, 35, 129 ^ 73), (double)x, (double)sliderY, (double)(x + width), (double)(sliderY + (88 ^ 87)), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);
      float normalized = (value - min) / (max - min);
      int fillWidth = (int)((float)width * normalized);
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getPrimaryAccent(), (double)x, (double)sliderY, (double)(x + fillWidth), (double)(sliderY + 15), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);
      int knobX = x + fillWidth;
      iq.renderCircle(drawContext.method_51448(), ch.safeColor(255, 255, 255, 255), (double)knobX, (double)((float)sliderY + 7.5F), 9.0D, 16);
   }

   private void renderBottomButtons(class_332 drawContext, int panelX, int panelY, int panelWidth, int panelHeight) {
      int buttonY = panelY + panelHeight - (51 ^ 1);
      int buttonWidth = 239 ^ 181;
      int buttonHeight = 92 ^ 127;
      int spacing = true;
      int applyX = panelX + panelWidth - 90 - 30;
      int cancelX = applyX - (149 ^ 207) - 15;
      int resetX = cancelX - 90 - 15;
      iq.renderRoundedQuad(drawContext.method_51448(), bi.getMainColor(255, 1), (double)applyX, (double)buttonY, (double)(applyX + 90), (double)(buttonY + 35), 6.0D, 6.0D, 6.0D, 6.0D, 20.0D);
      i.drawCenteredString(d824("opSVip4="), drawContext, applyX + (169 ^ 132), buttonY + (131 ^ 136), aq.getBrightTextColor().getRGB());
      iq.renderRoundedQuad(drawContext.method_51448(), ch.safeColor(40, 4 ^ 44, 49 ^ 6, 37 ^ 237), (double)cancelX, (double)buttonY, (double)(cancelX + (178 ^ 232)), (double)(buttonY + (132 ^ 167)), 6.0D, 6.0D, 6.0D, 6.0D, 20.0D);
      i.drawCenteredString(d824("oIWLhYKE"), drawContext, cancelX + 45, buttonY + (149 ^ 158), aq.getBrightTextColor().getRGB());
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getSecondaryAccent(), (double)resetX, (double)buttonY, (double)(resetX + 90), (double)(buttonY + 35), 6.0D, 6.0D, 6.0D, 6.0D, 20.0D);
      i.drawCenteredString(d824("sYGWg5M="), drawContext, resetX + (176 ^ 157), buttonY + 11, aq.getBrightTextColor().getRGB());
   }

   private void renderColorWheel(class_332 drawContext) {
      int segments = 121 ^ 69;
      int radiusSteps = true;

      for(int segment = 0; segment < (196 ^ 248); ++segment) {
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
      iq.renderCircle(drawContext.method_51448(), ch.safeColor(255, 195 ^ 60, 255, 123 ^ 179), (double)indicatorX, (double)indicatorY, 6.0D, 16);
      iq.renderCircle(drawContext.method_51448(), ch.safeColor(0, 0, 0, 200), (double)indicatorX, (double)indicatorY, 4.0D, 16);
   }

   private void drawFilledQuad(class_332 drawContext, Color color, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
      drawContext.method_25294(Math.min(Math.min(Math.min(x1, x2), x3), x4), Math.min(Math.min(Math.min(y1, y2), y3), y4), Math.max(Math.max(Math.max(x1, x2), x3), x4), Math.max(Math.max(Math.max(y1, y2), y3), y4), color.getRGB());
   }

   private void renderBrightnessBar(class_332 drawContext) {
      iq.renderRoundedQuad(drawContext.method_51448(), ch.safeColor(20, 208 ^ 196, 30, 111 ^ 167), (double)(this.brightnessBarX - 5), (double)(this.brightnessBarY - 5), (double)(this.brightnessBarX + 30 + 5), (double)(this.brightnessBarY + 200 + 5), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);

      int indicatorY;
      for(indicatorY = 0; indicatorY < (100 ^ 172); ++indicatorY) {
         float b = 1.0F - (float)indicatorY / 200.0F;
         Color gradientColor = Color.getHSBColor(this.hue / 360.0F, this.saturation, b);
         drawContext.method_25294(this.brightnessBarX, this.brightnessBarY + indicatorY, this.brightnessBarX + 30, this.brightnessBarY + indicatorY + 1, gradientColor.getRGB());
      }

      indicatorY = (int)((float)this.brightnessBarY + (1.0F - this.brightness) * 200.0F);
      drawContext.method_25294(this.brightnessBarX - 3, indicatorY - 2, this.brightnessBarX + (221 ^ 195) + 3, indicatorY + 2, ch.safeColor(108 ^ 147, 231 ^ 24, 207 ^ 48, 238 ^ 17).getRGB());
      drawContext.method_25294(this.brightnessBarX - 2, indicatorY - 1, this.brightnessBarX + (172 ^ 178) + 2, indicatorY + 1, ch.safeColor(0, 0, 0, 255).getRGB());
   }

   private void renderAlphaSlider(class_332 drawContext, int x, int y, int width) {
      i.drawString("Alpha: " + this.alpha, drawContext, x, y - 15, ch.safeColor(200, 85 ^ 157, 97 ^ 169, 255).getRGB());
      iq.renderRoundedQuad(drawContext.method_51448(), ch.safeColor(20, 20, 246 ^ 239, 255), (double)x, (double)y, (double)(x + width), (double)(y + (217 ^ 205)), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);

      for(int i = 0; i < width; i += 10) {
         Color checker = i / 10 % 2 == 0 ? ch.safeColor(53 ^ 81, 203 ^ 175, 100, 249 ^ 6) : ch.safeColor(150, 150, 86 ^ 192, 255);
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
      drawContext.method_25294(indicatorX - 2, y - 2, indicatorX + 2, y + 22, ch.safeColor(103 ^ 152, 205 ^ 50, 255, 255).getRGB());
      drawContext.method_25294(indicatorX - 1, y, indicatorX + 1, y + 20, this.getCurrentColor().getRGB());
   }

   public boolean method_25402(double mouseX, double mouseY, int button) {
      double scaledX = mouseX * class_310.method_1551().method_22683().method_4495();
      double scaledY = mouseY * class_310.method_1551().method_22683().method_4495();
      int screenWidth = this.field_22787.method_22683().method_4480();
      int screenHeight = this.field_22787.method_22683().method_4507();
      int panelWidth = true;
      int panelHeight = true;
      int panelX = (screenWidth - (709 ^ 227)) / 2;
      int panelY = (screenHeight - 500) / 2;
      int tabY = panelY + (67 ^ 116);
      int tabHeight = true;
      int tabWidth = true;
      int spacing = true;
      int totalWidth = this.tabs.length * 120 + (this.tabs.length - 1) * (202 ^ 194);
      int startX = panelX + (550 - totalWidth) / 2;

      int buttonY;
      int tabX;
      for(buttonY = 0; buttonY < this.tabs.length; ++buttonY) {
         tabX = startX + buttonY * 128;
         if (this.isInBounds(scaledX, scaledY, tabX, tabY, 120, 35)) {
            this.selectedTab = buttonY;
            return true;
         }
      }

      buttonY = panelY + (477 ^ 41) - 50;
      tabX = 117 ^ 47;
      int buttonHeight = true;
      int buttonSpacing = 175 ^ 160;
      int applyX = panelX + (622 ^ 72) - (251 ^ 161) - 30;
      int cancelX = applyX - 90 - (224 ^ 239);
      int resetX = cancelX - 90 - 15;
      if (this.isInBounds(scaledX, scaledY, applyX, buttonY, 243 ^ 169, 35)) {
         this.applyColors();
         this.field_22787.method_1507(this.parentScreen);
         return true;
      } else if (this.isInBounds(scaledX, scaledY, cancelX, buttonY, 90, 35)) {
         this.field_22787.method_1507(this.parentScreen);
         return true;
      } else if (this.isInBounds(scaledX, scaledY, resetX, buttonY, 212 ^ 142, 15 ^ 44)) {
         this.resetToDefaults();
         return true;
      } else if (this.selectedTab == 0) {
         return this.handleColorPickerClick(scaledX, scaledY, panelX, panelY, 561 ^ 23, 441 ^ 77);
      } else if (this.selectedTab == 1) {
         return this.handleRGBSettingsClick(scaledX, scaledY, panelX, panelY, 715 ^ 237);
      } else if (this.selectedTab == 2) {
         return this.handlePresetsClick(scaledX, scaledY, panelX, panelY, 550);
      } else if (this.selectedTab == 3) {
         return this.handleThemesClick(scaledX, scaledY, panelX, panelY, 550);
      } else {
         return super.method_25402(scaledX, scaledY, button);
      }
   }

   private boolean handleThemesClick(double mouseX, double mouseY, int panelX, int panelY, int panelWidth) {
      ai[] themes = ai.values();
      int buttonSize = true;
      int spacing = true;
      int cols = true;
      int totalWidth = 179 ^ 67;
      int startX = panelX + (panelWidth - (196 ^ 52)) / 2;
      int startY = panelY + 140;

      for(int i = 0; i < themes.length; ++i) {
         int row = i / 3;
         int col = i % 3;
         int btnX = startX + col * 85;
         int btnY = startY + row * 110;
         if (this.isInBounds(mouseX, mouseY, btnX, btnY, 70, 159 ^ 217)) {
            this.selectedTheme = themes[i];
            if (this.parentScreen instanceof gf) {
               ((gf)this.parentScreen).currentTheme = themes[i];
            }

            return true;
         }
      }

      return false;
   }

   private boolean handleColorPickerClick(double mouseX, double mouseY, int panelX, int panelY, int panelWidth, int panelHeight) {
      double dx = mouseX - (double)this.wheelCenterX;
      double dy = mouseY - (double)this.wheelCenterY;
      double distance = Math.sqrt(dx * dx + dy * dy);
      if (distance <= 80.0D) {
         this.draggingWheel = true;
         this.updateColorFromWheel(mouseX, mouseY);
         return true;
      } else if (this.isInBounds(mouseX, mouseY, this.brightnessBarX, this.brightnessBarY, 30, 47 ^ 231)) {
         this.draggingBrightness = true;
         this.updateBrightnessFromBar(mouseY);
         return true;
      } else {
         int alphaSliderX = panelX + (40 ^ 26);
         int alphaSliderY = panelY + 360;
         int alphaSliderWidth = panelWidth - 100;
         if (this.isInBounds(mouseX, mouseY, alphaSliderX, alphaSliderY, alphaSliderWidth, 20)) {
            this.draggingAlpha = true;
            this.updateAlphaFromSlider(mouseX, alphaSliderX, alphaSliderWidth);
            return true;
         } else {
            return false;
         }
      }
   }

   private boolean handleRGBSettingsClick(double mouseX, double mouseY, int panelX, int panelY, int panelWidth) {
      int yOffset = panelY + 120;
      int xStart = panelX + 50;
      int settingWidth = panelWidth - 100;
      if (this.isInBounds(mouseX, mouseY, xStart + settingWidth - (43 ^ 25), yOffset - 2, 211 ^ 251, 34 ^ 54)) {
         this.rgbEnabled = !this.rgbEnabled;
         return true;
      } else {
         yOffset += 50;
         if (this.rgbEnabled) {
            int sliderY = yOffset + 20;
            if (this.isInBounds(mouseX, mouseY, xStart, sliderY, settingWidth, 15)) {
               this.draggingRGBSpeed = true;
               this.updateSliderValue(mouseX, xStart, settingWidth, 0.1F, 5.0F, d824("kYOHtZeNjI4="));
               return true;
            }

            yOffset += 60;
            sliderY = yOffset + 20;
            if (this.isInBounds(mouseX, mouseY, xStart, sliderY, settingWidth, 15)) {
               this.draggingRGBSat = true;
               this.updateSliderValue(mouseX, xStart, settingWidth, 0.0F, 1.0F, d824("kYOHtYac"));
               return true;
            }

            yOffset += 60;
            sliderY = yOffset + (102 ^ 114);
            if (this.isInBounds(mouseX, mouseY, xStart, sliderY, settingWidth, 15)) {
               this.draggingRGBBright = true;
               this.updateSliderValue(mouseX, xStart, settingWidth, 0.0F, 1.0F, d824("kYOHpJWBjoKf"));
               return true;
            }

            yOffset += 60;
            if (this.isInBounds(mouseX, mouseY, xStart + settingWidth - 50, yOffset - 2, 40, 189 ^ 169)) {
               this.syncRGB = !this.syncRGB;
               return true;
            }
         }

         return false;
      }
   }

   private boolean handlePresetsClick(double mouseX, double mouseY, int panelX, int panelY, int panelWidth) {
      int cols = 3;
      int presetWidth = 32 ^ 172;
      int presetHeight = 219 ^ 139;
      int spacing = 183 ^ 163;
      int startX = panelX + (panelWidth - (cols * presetWidth + (cols - 1) * spacing)) / 2;
      int startY = panelY + (81 ^ 221);

      for(int i = 0; i < this.presets.length; ++i) {
         int row = i / cols;
         int col = i % cols;
         int x = startX + col * (presetWidth + spacing);
         int y = startY + row * (presetHeight + spacing);
         if (this.isInBounds(mouseX, mouseY, x, y, presetWidth, presetHeight)) {
            this.applyPreset(this.presets[i]);
            return true;
         }
      }

      return false;
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
      } else {
         int screenWidth;
         int panelX;
         int xStart;
         short settingWidth;
         if (this.draggingAlpha) {
            screenWidth = this.field_22787.method_22683().method_4480();
            int panelWidth = 592 ^ 118;
            panelX = (screenWidth - 550) / 2;
            xStart = panelX + 50;
            settingWidth = 450;
            this.updateAlphaFromSlider(scaledX, xStart, settingWidth);
            return true;
         } else if (!this.draggingRGBSpeed && !this.draggingRGBSat && !this.draggingRGBBright) {
            return super.method_25403(scaledX, scaledY, button, deltaX, deltaY);
         } else {
            screenWidth = this.field_22787.method_22683().method_4480();
            int panelWidth = true;
            panelX = (screenWidth - 550) / 2;
            xStart = panelX + 50;
            settingWidth = 450;
            if (this.draggingRGBSpeed) {
               this.updateSliderValue(scaledX, xStart, settingWidth, 0.1F, 5.0F, d824("kYOHtZeNjI4="));
            } else if (this.draggingRGBSat) {
               this.updateSliderValue(scaledX, xStart, settingWidth, 0.0F, 1.0F, d824("kYOHtYac"));
            } else if (this.draggingRGBBright) {
               this.updateSliderValue(scaledX, xStart, settingWidth, 0.0F, 1.0F, d824("kYOHpJWBjoKf"));
            }

            return true;
         }
      }
   }

   public boolean method_25406(double mouseX, double mouseY, int button) {
      this.draggingWheel = false;
      this.draggingBrightness = false;
      this.draggingAlpha = false;
      this.draggingRGBSpeed = false;
      this.draggingRGBSat = false;
      this.draggingRGBBright = false;
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

   private void updateSliderValue(double mouseX, int sliderX, int sliderWidth, float min, float max, String setting) {
      float normalized = (float)((mouseX - (double)sliderX) / (double)sliderWidth);
      normalized = Math.max(0.0F, Math.min(1.0F, normalized));
      float value = min + normalized * (max - min);
      byte var11 = -1;
      switch(setting.hashCode()) {
      case -1991457241:
         if (setting.equals(d824("kYOHpJWBjoKf"))) {
            var11 = 2;
         }
         break;
      case -933119015:
         if (setting.equals(d824("kYOHtYac"))) {
            var11 = 1;
         }
         break;
      case 921227130:
         if (setting.equals(d824("kYOHtZeNjI4="))) {
            var11 = 0;
         }
      }

      switch(var11) {
      case 0:
         this.rgbSpeed = value;
         break;
      case 1:
         this.rgbSat = value;
         break;
      case 2:
         this.rgbBright = value;
      }

   }

   private Color getCurrentColor() {
      Color baseColor = Color.getHSBColor(this.hue / 360.0F, this.saturation, this.brightness);
      return ch.safeColor(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), this.alpha);
   }

   private void applyColors() {
      try {
         if (astraluxInstance != null) {
            fd themeManager = (fd)astraluxInstance.getModuleManager().getModuleByClass(fd.class);
            if (themeManager != null) {
               themeManager.getRgbMode().setValue(this.rgbEnabled);
               themeManager.getRgbSpeed().setValue((double)this.rgbSpeed);
               themeManager.getRgbSaturation().setValue((double)this.rgbSat);
               themeManager.getRgbBrightness().setValue((double)this.rgbBright);
               themeManager.getSyncRGB().setValue(this.syncRGB);
            }
         }
      } catch (Exception var2) {
      }

   }

   private void applyPreset(io preset) {
      Color primary = preset.primary;
      float[] hsv = Color.RGBtoHSB(primary.getRed(), primary.getGreen(), primary.getBlue(), (float[])null);
      this.hue = hsv[0] * 360.0F;
      this.saturation = hsv[1];
      this.brightness = hsv[2];
      this.alpha = primary.getAlpha();
   }

   private void resetToDefaults() {
      this.loadCurrentColor();
      this.rgbEnabled = false;
      this.rgbSpeed = 1.0F;
      this.rgbSat = 0.8F;
      this.rgbBright = 1.0F;
      this.syncRGB = true;
   }

   private boolean isInBounds(double x, double y, int bx, int by, int w, int h) {
      return x >= (double)bx && x <= (double)(bx + w) && y >= (double)by && y <= (double)(by + h);
   }

   public void method_25420(class_332 drawContext, int mouseX, int mouseY, float delta) {
   }

   public boolean method_25422() {
      return true;
   }

   // $FF: synthetic method
   private static String d824(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 3299 + var3 & (5 ^ 250));
      }

      return new String(var2);
   }
}
