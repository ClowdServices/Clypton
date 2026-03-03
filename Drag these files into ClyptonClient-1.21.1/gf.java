import client.astralux.Astralux;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import net.minecraft.class_2561;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_437;
import net.minecraft.class_640;
import net.minecraft.class_8685;

public final class gf extends class_437 {
   public List<ah> windows;
   public Color currentColor;
   private CharSequence tooltipText;
   private int tooltipX;
   private int tooltipY;
   private final Color DESCRIPTION_BG;
   public String moduleSearchQuery = "";
   private static final int SEARCH_BAR_WIDTH = 270;
   private static final int SEARCH_BAR_HEIGHT = 40;
   private int searchBarX;
   private int searchBarY;
   private int searchBarPrevX;
   private int searchBarPrevY;
   private boolean draggingSearchBar = false;
   private boolean searchBarFocused = false;
   private int searchDragX;
   private int searchDragY;
   public ai currentTheme;
   private float searchBarGlow;
   private float globalRgbOffset;
   private static final int SKIN_SIZE = 96;
   private static final int SKIN_MARGIN = 20;
   private int skinX;
   private int skinY;
   private int skinPrevX;
   private int skinPrevY;
   private boolean draggingSkin;
   private int skinDragX;
   private int skinDragY;
   private boolean skinVisible;
   private bj selectedSearchModule;
   private float settingsPanelAnimation;
   public fb configPanel;

   public gf() {
      super(class_2561.method_43473());
      this.currentTheme = ai.MIDNIGHT_PURPLE;
      this.searchBarGlow = 0.0F;
      this.globalRgbOffset = 0.0F;
      this.skinX = 20;
      this.skinPrevX = 20;
      this.draggingSkin = false;
      this.skinVisible = true;
      this.selectedSearchModule = null;
      this.settingsPanelAnimation = 0.0F;
      this.windows = new ArrayList();
      this.tooltipText = null;
      this.DESCRIPTION_BG = ch.safeColor(249 ^ 235, 18, 106 ^ 118, 115 ^ 134);
      hk[] values = hk.values();
      int startX = 20;
      int startY = 80;

      for(int i = 0; i < values.length; ++i) {
         this.windows.add(new ah(startX + i * 260, startY, 250, 32, values[i], this));
      }

      this.searchBarX = class_310.method_1551() != null && class_310.method_1551().method_22683() != null ? class_310.method_1551().method_22683().method_4480() - (267 ^ 5) - 20 : -817 ^ 215;
      this.searchBarY = 171 ^ 224;
      this.searchBarPrevX = this.searchBarX;
      this.searchBarPrevY = this.searchBarY;
      this.configPanel = null;
      this.loadThemeFromManager();
   }

   private void loadThemeFromManager() {
      try {
         fd themeManager = (fd)Astralux.INSTANCE.getModuleManager().getModuleByClass(fd.class);
         if (themeManager != null) {
         }
      } catch (Exception var2) {
      }

   }

   public boolean isDraggingAlready() {
      Iterator var1 = this.windows.iterator();

      while(var1.hasNext()) {
         ah window = (ah)var1.next();
         if (window.dragging) {
            return true;
         }
      }

      return this.draggingSearchBar || this.draggingSkin;
   }

   public void setTooltip(CharSequence tooltipText, int tooltipX, int tooltipY) {
      this.tooltipText = tooltipText;
      this.tooltipX = tooltipX;
      this.tooltipY = tooltipY;
   }

   public void method_56131() {
      if (this.field_22787 != null) {
         super.method_56131();
      }
   }

   public void method_25394(class_332 drawContext, int n, int n2, float n3) {
      if (class_310.method_1551().field_1755 == this) {
         if (Astralux.INSTANCE.screen != null) {
            Astralux.INSTANCE.screen.method_25394(drawContext, 0, 0, n3);
         }

         this.globalRgbOffset += n3 * 0.02F;
         if (this.currentColor == null) {
            this.currentColor = ch.safeColor(0, 0, 0, 0);
         } else {
            this.currentColor = ch.safeColor(0, 0, 0, this.currentColor.getAlpha());
         }

         int alpha = this.currentColor.getAlpha();
         int targetAlpha = gm.renderBackground.getValue() ? 76 ^ 132 : 0;
         if (alpha != targetAlpha) {
            this.currentColor = ch.a(0.05F, targetAlpha, this.currentColor);
         }

         if (class_310.method_1551().field_1755 instanceof gf) {
            drawContext.method_25294(0, 0, class_310.method_1551().method_22683().method_4480(), class_310.method_1551().method_22683().method_4507(), this.currentColor.getRGB());
         }

         iq.unscaledProjection();
         int n6 = n * (int)class_310.method_1551().method_22683().method_4495();
         int n7 = n2 * (int)class_310.method_1551().method_22683().method_4495();
         super.method_25394(drawContext, n6, n7, n3);
         if (this.searchBarX < 0 && class_310.method_1551().method_22683() != null) {
            this.searchBarX = class_310.method_1551().method_22683().method_4480() - 270 - 20;
            this.searchBarPrevX = this.searchBarX;
            this.searchBarY = 35 ^ 104;
            this.searchBarPrevY = this.searchBarY;
         }

         if (this.skinY == 0 && class_310.method_1551().method_22683() != null) {
            this.skinY = class_310.method_1551().method_22683().method_4507() - 96 - 20;
            this.skinPrevY = this.skinY;
         }

         this.renderModernTopBar(drawContext, n3);
         this.updateSearchBarPosition(n6, n7, n3);
         this.renderSearchBar(drawContext, n6, n7, n3);
         this.updateSkinPosition(n6, n7, n3);
         this.renderPlayerSkin(drawContext, n3, n6, n7);
         Iterator var9 = this.windows.iterator();

         while(var9.hasNext()) {
            ah next = (ah)var9.next();
            next.render(drawContext, n6, n7, n3);
            next.updatePosition((double)n6, (double)n7, n3);
         }

         if (this.configPanel != null) {
            this.configPanel.render(drawContext, n6, n7, n3);
         }

         if (this.tooltipText != null) {
            this.renderTooltip(drawContext, this.tooltipText, this.tooltipX, this.tooltipY);
            this.tooltipText = null;
         }

         iq.scaledProjection();
      }

   }

   public boolean method_25404(int keyCode, int scanCode, int modifiers) {
      if (this.configPanel != null && this.configPanel.isVisible()) {
         this.configPanel.keyPressed(keyCode);
         return true;
      } else {
         if (this.searchBarFocused && !this.isAnyKeybindBeingSet()) {
            if (keyCode == 259) {
               if (!this.moduleSearchQuery.isEmpty()) {
                  this.moduleSearchQuery = this.moduleSearchQuery.substring(0, this.moduleSearchQuery.length() - 1);
                  this.updateModuleVisibility();
               }

               return true;
            }

            if (keyCode == (331 ^ 75)) {
               this.searchBarFocused = false;
               return true;
            }
         }

         Iterator var4 = this.windows.iterator();

         while(var4.hasNext()) {
            ah window = (ah)var4.next();
            window.keyPressed(keyCode, scanCode, modifiers);
         }

         return super.method_25404(keyCode, scanCode, modifiers);
      }
   }

   public boolean method_25400(char chr, int modifiers) {
      if (this.configPanel != null && this.configPanel.isVisible()) {
         this.configPanel.charTyped(chr);
         return true;
      } else if (this.searchBarFocused && !this.isAnyKeybindBeingSet()) {
         this.moduleSearchQuery = this.moduleSearchQuery + chr;
         this.updateModuleVisibility();
         return true;
      } else {
         return super.method_25400(chr, modifiers);
      }
   }

   private boolean isAnyKeybindBeingSet() {
      Iterator var1 = this.windows.iterator();

      while(var1.hasNext()) {
         ah window = (ah)var1.next();
         Iterator var3 = window.moduleButtons.iterator();

         while(var3.hasNext()) {
            bj button = (bj)var3.next();
            Iterator var5 = button.settings.iterator();

            while(var5.hasNext()) {
               p component = (p)var5.next();
               if (component instanceof ey) {
                  ey keybind = (ey)component;
                  if (keybind.isBinding()) {
                     return true;
                  }
               }
            }
         }
      }

      return false;
   }

   public boolean method_25402(double n, double n2, int n3) {
      double n4 = n * (double)((int)class_310.method_1551().method_22683().method_4495());
      double n5 = n2 * (double)((int)class_310.method_1551().method_22683().method_4495());
      if (this.configPanel != null && this.configPanel.isVisible()) {
         this.configPanel.mouseClicked(n4, n5, n3);
         return true;
      } else if (this.handleSkinClick(n4, n5, n3)) {
         return true;
      } else if (n4 >= (double)this.searchBarPrevX && n4 <= (double)(this.searchBarPrevX + 270) && n5 >= (double)this.searchBarPrevY && n5 <= (double)(this.searchBarPrevY + 40)) {
         if (n3 == 0) {
            this.draggingSearchBar = true;
            this.searchDragX = (int)(n4 - (double)this.searchBarPrevX);
            this.searchDragY = (int)(n5 - (double)this.searchBarPrevY);
            this.searchBarFocused = true;
         }

         return true;
      } else {
         this.searchBarFocused = false;
         if (this.handleSearchResultClick(n4, n5, n3)) {
            return true;
         } else {
            Iterator var10 = this.windows.iterator();

            while(var10.hasNext()) {
               ah window = (ah)var10.next();
               window.mouseClicked(n4, n5, n3);
            }

            return super.method_25402(n4, n5, n3);
         }
      }
   }

   public boolean method_25403(double n, double n2, int n3, double n4, double n5) {
      double n6 = n * (double)((int)class_310.method_1551().method_22683().method_4495());
      double n7 = n2 * (double)((int)class_310.method_1551().method_22683().method_4495());
      if (this.configPanel != null && this.configPanel.isVisible()) {
         this.configPanel.mouseDragged(n6, n7);
         return true;
      } else if (this.draggingSearchBar) {
         return true;
      } else if (this.draggingSkin) {
         return true;
      } else {
         Iterator var14 = this.windows.iterator();

         while(var14.hasNext()) {
            ah window = (ah)var14.next();
            window.mouseDragged(n6, n7, n3, n4, n5);
         }

         return super.method_25403(n6, n7, n3, n4, n5);
      }
   }

   public boolean method_25401(double n, double n2, double n3, double n4) {
      double n5 = n2 * class_310.method_1551().method_22683().method_4495();
      if (this.selectedSearchModule != null && !this.moduleSearchQuery.isEmpty()) {
         int panelX = this.searchBarPrevX;
         int panelWidth = true;
         int panelStartY = this.searchBarPrevY + 40;
         int panelHeight = 287 ^ 211;
         if (n * class_310.method_1551().method_22683().method_4495() >= (double)panelX && n * class_310.method_1551().method_22683().method_4495() <= (double)(panelX + 270) && n5 >= (double)panelStartY && n5 <= (double)(panelStartY + 460)) {
            return true;
         }
      }

      Iterator var15 = this.windows.iterator();

      while(var15.hasNext()) {
         ah window = (ah)var15.next();
         window.mouseScrolled(n, n5, n3, n4);
      }

      return super.method_25401(n, n5, n3, n4);
   }

   public boolean method_25421() {
      return false;
   }

   public void method_25419() {
      bf astraluxModule = Astralux.INSTANCE.getModuleManager().getModuleByClass(gm.class);
      if (astraluxModule != null) {
         astraluxModule.setEnabled(false);
      }

      this.onGuiClose();
   }

   public void onGuiClose() {
      class_310.method_1551().method_29970(Astralux.INSTANCE.screen);
      this.currentColor = null;
      this.searchBarFocused = false;
      this.selectedSearchModule = null;
      Iterator var1 = this.windows.iterator();

      while(var1.hasNext()) {
         ah window = (ah)var1.next();
         window.onGuiClose();
      }

   }

   public boolean method_25406(double n, double n2, int n3) {
      double n4 = n * (double)((int)class_310.method_1551().method_22683().method_4495());
      double n5 = n2 * (double)((int)class_310.method_1551().method_22683().method_4495());
      if (this.configPanel != null && this.configPanel.isVisible()) {
         this.configPanel.mouseReleased();
         return true;
      } else {
         this.draggingSearchBar = false;
         this.draggingSkin = false;
         Iterator var10 = this.windows.iterator();

         while(var10.hasNext()) {
            ah window = (ah)var10.next();
            window.mouseReleased(n4, n5, n3);
         }

         return super.method_25406(n4, n5, n3);
      }
   }

   private void renderModernTopBar(class_332 drawContext, float delta) {
      int width = class_310.method_1551().method_22683().method_4480();
      Color accentColor = aq.getOutlineColor();
      drawContext.method_25294(0, 0, width, 55, ch.safeColor(95 ^ 83, 12, 18, 220).getRGB());
      drawContext.method_25294(0, 54, width, 56, accentColor.getRGB());
      String clientName = d833("kp6KhIGZmQ==");
      i.drawString(clientName, drawContext, 25, 233 ^ 251, ch.safeColor(255, 255, 69 ^ 186, 255).getRGB());
   }

   private boolean handleSkinClick(double mouseX, double mouseY, int button) {
      if (!this.skinVisible) {
         int textX = 20;
         int textY = class_310.method_1551().method_22683().method_4507() - 96 - (187 ^ 175);
         String text = d833("kr66t772mr35jrT8jrawl8G7jJGXxrSDgITLrYqPhp4=");
         int textWidth = i.getWidth(text);
         if (mouseX >= (double)textX && mouseX <= (double)(textX + textWidth + 20) && mouseY >= (double)textY && mouseY <= (double)(textY + 40)) {
            this.skinVisible = true;
            return true;
         }
      } else if (mouseX >= (double)this.skinPrevX && mouseX <= (double)(this.skinPrevX + (164 ^ 196) + 10) && mouseY >= (double)(this.skinPrevY - 5) && mouseY <= (double)(this.skinPrevY + (184 ^ 216) + (12 ^ 21))) {
         if (button == 0) {
            this.draggingSkin = true;
            this.skinDragX = (int)(mouseX - (double)this.skinPrevX);
            this.skinDragY = (int)(mouseY - (double)this.skinPrevY);
            return true;
         }

         if (button == 1) {
            this.skinVisible = false;
            return true;
         }
      }

      return false;
   }

   private void updateSkinPosition(int mouseX, int mouseY, float delta) {
      this.skinPrevX = this.skinX;
      this.skinPrevY = this.skinY;
      if (this.draggingSkin) {
         this.skinX = (int)ac.approachValue(0.3F * delta, (double)this.skinX, (double)(mouseX - this.skinDragX));
         this.skinY = (int)ac.approachValue(0.3F * delta, (double)this.skinY, (double)(mouseY - this.skinDragY));
      }

   }

   private void renderPlayerSkin(class_332 drawContext, float delta, int mouseX, int mouseY) {
      if (this.skinVisible) {
         class_310 mc = class_310.method_1551();
         if (mc.field_1724 != null) {
            Color bgColor = ch.safeColor(15, 15, 232 ^ 254, 114 ^ 186);
            drawContext.method_25294(this.skinPrevX - 5, this.skinPrevY - 5, this.skinPrevX + 96 + 5, this.skinPrevY + 96 + 5, bgColor.getRGB());
            Color accentColor = aq.getOutlineColor();
            drawContext.method_25294(this.skinPrevX - 5, this.skinPrevY - 5, this.skinPrevX + 96 + 5, this.skinPrevY - 3, accentColor.getRGB());

            try {
               class_640 playerListEntry = mc.method_1562().method_2871(mc.field_1724.method_5667());
               if (playerListEntry != null) {
                  class_8685 skinTextures = playerListEntry.method_52810();
                  class_2960 skinTexture = skinTextures.comp_1626();
                  drawContext.method_25293(skinTexture, this.skinPrevX + 20, this.skinPrevY + 5, 32, 138 ^ 170, 8.0F, 8.0F, 143 ^ 135, 120 ^ 112, 64, 64);
                  drawContext.method_25293(skinTexture, this.skinPrevX + (223 ^ 203), this.skinPrevY + 5, 32, 32, 40.0F, 8.0F, 8, 8, 64, 64);
                  drawContext.method_25293(skinTexture, this.skinPrevX + (73 ^ 81), this.skinPrevY + 37, 24, 134 ^ 166, 20.0F, 20.0F, 8, 12, 64, 64);
                  drawContext.method_25293(skinTexture, this.skinPrevX + 24, this.skinPrevY + 37, 248 ^ 224, 32, 20.0F, 36.0F, 8, 12, 163 ^ 227, 64);
                  drawContext.method_25293(skinTexture, this.skinPrevX + 12, this.skinPrevY + (190 ^ 155), 12, 32, 44.0F, 20.0F, 4, 12, 64, 64);
                  drawContext.method_25293(skinTexture, this.skinPrevX + 12, this.skinPrevY + 37, 12, 32, 44.0F, 36.0F, 4, 86 ^ 90, 243 ^ 179, 217 ^ 153);
                  drawContext.method_25293(skinTexture, this.skinPrevX + 48, this.skinPrevY + 37, 12, 23 ^ 55, 36.0F, 52.0F, 4, 12, 175 ^ 239, 64);
                  drawContext.method_25293(skinTexture, this.skinPrevX + 48, this.skinPrevY + 37, 12, 32, 52.0F, 52.0F, 4, 12, 64, 21 ^ 85);
                  drawContext.method_25293(skinTexture, this.skinPrevX + 28, this.skinPrevY + 69, 80 ^ 92, 130 ^ 154, 4.0F, 20.0F, 4, 12, 64, 64);
                  drawContext.method_25293(skinTexture, this.skinPrevX + 28, this.skinPrevY + (135 ^ 194), 12, 24, 4.0F, 36.0F, 4, 72 ^ 68, 15 ^ 79, 64);
                  drawContext.method_25293(skinTexture, this.skinPrevX + 40, this.skinPrevY + 69, 12, 24, 20.0F, 52.0F, 4, 12, 64, 255 ^ 191);
                  drawContext.method_25293(skinTexture, this.skinPrevX + 40, this.skinPrevY + 69, 12, 24, 4.0F, 52.0F, 4, 12, 64, 64);
               }
            } catch (Exception var12) {
               String playerName = mc.field_1724.method_5477().getString();
               i.drawString(playerName, drawContext, this.skinPrevX + 5, this.skinPrevY + 48 - 4, ch.safeColor(81 ^ 174, 255, 99 ^ 156, 255).getRGB());
            }

         }
      } else {
         int textX = 20;
         int textY = class_310.method_1551().method_22683().method_4507() - (245 ^ 149) - 20;
         String text = d833("kr66t772mr35jrT8jrawl8G7jJGXxrSDgITLrYqPhp4=");
         int textWidth = i.getWidth(text);
         boolean isHovered = mouseX >= textX && mouseX <= textX + textWidth + (89 ^ 77) && mouseY >= textY && mouseY <= textY + 40;
         Color bgColor = isHovered ? ch.safeColor(36 ^ 61, 25, 35, 232 ^ 52) : ch.safeColor(18, 18, 26, 200);
         drawContext.method_25294(textX, textY, textX + textWidth + 20, textY + 40, bgColor.getRGB());
         Color accentColor = aq.getOutlineColor();
         drawContext.method_25294(textX, textY, textX + textWidth + 20, textY + 2, accentColor.getRGB());
         i.drawString(text, drawContext, textX + (20 ^ 30), textY + 13, ch.safeColor(220, 220, 230, 99 ^ 156).getRGB());
      }
   }

   private void updateSearchBarPosition(int mouseX, int mouseY, float delta) {
      this.searchBarPrevX = this.searchBarX;
      this.searchBarPrevY = this.searchBarY;
      if (this.draggingSearchBar) {
         this.searchBarX = (int)ac.approachValue(0.3F * delta, (double)this.searchBarX, (double)(mouseX - this.searchDragX));
         this.searchBarY = (int)ac.approachValue(0.3F * delta, (double)this.searchBarY, (double)(mouseY - this.searchDragY));
      }

      float targetGlow = this.searchBarFocused ? 1.0F : 0.0F;
      this.searchBarGlow = (float)ac.approachValue(delta * 0.18F, (double)this.searchBarGlow, (double)targetGlow);
      float targetSettings = this.selectedSearchModule != null && !this.moduleSearchQuery.isEmpty() ? 1.0F : 0.0F;
      this.settingsPanelAnimation = (float)ac.approachValue(delta * 0.15F, (double)this.settingsPanelAnimation, (double)targetSettings);
   }

   private void renderSearchBar(class_332 drawContext, int mouseX, int mouseY, float delta) {
      int panelHeight = 484 ^ 40;
      int searchHeight = true;
      int btnHeight = 154 ^ 190;
      long time = System.currentTimeMillis();
      boolean hasQuery = !this.moduleSearchQuery.isEmpty();
      Color outlineColor = new Color(255, 31 ^ 224, 192 ^ 63);
      drawContext.method_25294(this.searchBarPrevX, this.searchBarPrevY, this.searchBarPrevX + 270, this.searchBarPrevY + 40, ch.safeColor(20, 20, 28, 235).getRGB());
      int accentAlpha = (int)(180.0F + 75.0F * this.searchBarGlow);
      drawContext.method_25294(this.searchBarPrevX, this.searchBarPrevY, this.searchBarPrevX + 270, this.searchBarPrevY + 2, ch.safeColor(outlineColor.getRed(), outlineColor.getGreen(), outlineColor.getBlue(), accentAlpha).getRGB());
      String cursor = this.searchBarFocused && time % 1000L > 500L ? "|" : "";
      String displayText = this.moduleSearchQuery.isEmpty() ? "\ud83d\udd0d Search..." : "\ud83d\udd0d " + this.moduleSearchQuery + cursor;
      int textColor = this.moduleSearchQuery.isEmpty() ? ch.safeColor(140, 21 ^ 153, 160, 220).getRGB() : ch.safeColor(234 ^ 12, 245 ^ 19, 34 ^ 210, 255).getRGB();
      i.drawString(displayText, drawContext, this.searchBarPrevX + 14, this.searchBarPrevY + 12, textColor);
      if (hasQuery) {
         drawContext.method_25294(this.searchBarPrevX, this.searchBarPrevY + 40, this.searchBarPrevX + 270, this.searchBarPrevY + 460, ch.safeColor(245 ^ 231, 207 ^ 221, 86 ^ 76, 245).getRGB());
         int resultY = this.searchBarPrevY + 40 + 12;
         Iterator var17 = this.windows.iterator();

         label97:
         while(var17.hasNext()) {
            ah window = (ah)var17.next();
            Iterator var19 = window.moduleButtons.iterator();

            while(true) {
               bj button;
               String moduleName;
               do {
                  if (!var19.hasNext()) {
                     continue label97;
                  }

                  button = (bj)var19.next();
                  moduleName = button.module.getName().toString();
               } while(!moduleName.toLowerCase().contains(this.moduleSearchQuery.toLowerCase()));

               if (resultY > this.searchBarPrevY + 460 - 48) {
                  break;
               }

               boolean isEnabled = button.module.isEnabled();
               boolean isSelected = this.selectedSearchModule == button;
               if (isEnabled) {
                  drawContext.method_25294(this.searchBarPrevX + 7, resultY, this.searchBarPrevX + 10, resultY + (241 ^ 213), ch.safeColor(255, 255, 101 ^ 154, 255).getRGB());
               }

               int bgAlpha = isEnabled ? 81 ^ 53 : 40;
               Color bgColor = ch.safeColor(25, 25, 35, bgAlpha);
               drawContext.method_25294(this.searchBarPrevX + (224 ^ 234), resultY, this.searchBarPrevX + (258 ^ 12) - 7, resultY + (198 ^ 226), bgColor.getRGB());
               if (isEnabled) {
                  drawContext.method_25294(this.searchBarPrevX + 7, resultY, this.searchBarPrevX + (336 ^ 94) - (145 ^ 150), resultY + 2, outlineColor.getRGB());
               }

               if (isSelected) {
                  drawContext.method_25294(this.searchBarPrevX + 7, resultY, this.searchBarPrevX + (155 ^ 145), resultY + 36, ch.safeColor(187 ^ 68, 255, 193 ^ 62, 255).getRGB());
               }

               int textBrightness = isEnabled ? 255 : 116 ^ 212;
               i.drawString(moduleName, drawContext, this.searchBarPrevX + 18, resultY + 11, ch.safeColor(textBrightness, textBrightness, textBrightness, 255).getRGB());
               if (isSelected && this.settingsPanelAnimation > 0.1F) {
                  int settingsX = this.searchBarPrevX + 270 + 10;
                  int settingsWidth = 255 ^ 5;
                  int settingsHeight = Math.min(300, button.settings.size() * (23 ^ 52) + 20);
                  drawContext.method_25294(settingsX, resultY, settingsX + settingsWidth, resultY + settingsHeight, ch.safeColor(20, 20, 28, 138 ^ 97).getRGB());
                  drawContext.method_25294(settingsX, resultY, settingsX + settingsWidth, resultY + 2, outlineColor.getRGB());
                  i.drawString(moduleName + " Settings", drawContext, settingsX + 10, resultY + 8, ch.safeColor(220, 19 ^ 207, 114 ^ 148, 255).getRGB());
                  int settingY = resultY + (80 ^ 78);

                  for(Iterator var31 = button.settings.iterator(); var31.hasNext(); settingY += 35) {
                     p component = (p)var31.next();
                     if (settingY > resultY + settingsHeight - (194 ^ 220)) {
                        break;
                     }

                     component.render(drawContext, settingsX + 10, settingY, (float)mouseX);
                  }
               }

               resultY += 44;
            }
         }
      }

   }

   private boolean handleSearchResultClick(double mouseX, double mouseY, int button) {
      if (this.moduleSearchQuery.isEmpty()) {
         this.selectedSearchModule = null;
         return false;
      } else {
         int panelHeight = true;
         if (!(mouseX < (double)this.searchBarPrevX) && !(mouseX > (double)(this.searchBarPrevX + 270)) && !(mouseY < (double)(this.searchBarPrevY + 40)) && !(mouseY > (double)(this.searchBarPrevY + 500))) {
            int resultY = this.searchBarPrevY + 52;
            Iterator var8 = this.windows.iterator();

            while(var8.hasNext()) {
               ah window = (ah)var8.next();
               Iterator var10 = window.moduleButtons.iterator();

               while(var10.hasNext()) {
                  bj btn = (bj)var10.next();
                  if (btn.module.getName().toString().toLowerCase().contains(this.moduleSearchQuery.toLowerCase())) {
                     if (resultY > this.searchBarPrevY + 500 - 48) {
                        break;
                     }

                     int btnHeight = 16 ^ 52;
                     if (mouseY >= (double)resultY && mouseY <= (double)(resultY + (231 ^ 195))) {
                        if (button == 0) {
                           btn.module.toggle();
                           return true;
                        }

                        if (button == 1) {
                           this.selectedSearchModule = this.selectedSearchModule == btn ? null : btn;
                           return true;
                        }
                     }

                     resultY += 44;
                  }
               }
            }

            return false;
         } else {
            return false;
         }
      }
   }

   private void updateModuleVisibility() {
   }

   private void renderTooltip(class_332 drawContext, CharSequence charSequence, int n, int n2) {
      if (charSequence != null && !charSequence.isEmpty()) {
         int textWidth = i.getWidth(charSequence);
         int framebufferWidth = class_310.method_1551().method_22683().method_4489();
         if (n + textWidth + (209 ^ 219) > framebufferWidth) {
            n = framebufferWidth - textWidth - (7 ^ 13);
         }

         Color themeAccent = aq.getPrimaryAccent();
         drawContext.method_25294(n - (22 ^ 30), n2 - (92 ^ 84), n + textWidth + 8, n2 + 18, ch.safeColor(20, 20, 208 ^ 206, 57 ^ 195).getRGB());
         drawContext.method_25294(n - 8, n2 - (146 ^ 154), n + textWidth + 8, n2 - 6, themeAccent.getRGB());
         i.drawString(charSequence, drawContext, n, n2, ch.safeColor(235, 35 ^ 200, 245, 255).getRGB());
      }
   }

   // $FF: synthetic method
   private static String d833(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 7633 + var3 & 255);
      }

      return new String(var2);
   }
}
