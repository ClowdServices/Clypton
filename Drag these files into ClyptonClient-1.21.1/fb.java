import client.astralux.Astralux;
import java.awt.Color;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.class_332;

public class fb {
   private int x;
   private int y;
   private int width = 439 ^ 39;
   private int height = 550;
   private boolean dragging = false;
   private int dragX;
   private int dragY;
   private boolean visible = false;
   private String inputText = "";
   private boolean inputFocused = false;
   private List<ae> configs;
   private int scrollOffset = 0;
   private ae hoveredConfig = null;
   private int selectedTab = 0;
   private float[] tabAnimations = new float[2];
   private String statusMessage = "";
   private long statusMessageTime = 0L;
   private static final long STATUS_DURATION = 3000L;

   public fb(int x, int y) {
      this.x = x;
      this.y = y;
      this.tabAnimations[0] = 1.0F;
      this.tabAnimations[1] = 0.0F;
      this.refreshConfigs();
   }

   public void toggle() {
      this.visible = !this.visible;
      if (this.visible) {
         this.refreshConfigs();
      }

   }

   public boolean isVisible() {
      return this.visible;
   }

   public void refreshConfigs() {
      this.configs = Astralux.INSTANCE.getConfigManager().listConfigs();
   }

   private List<ae> getFilteredConfigs() {
      if (this.configs == null) {
         return List.of();
      } else {
         return this.selectedTab == 0 ? (List)this.configs.stream().filter((c) -> {
            return !c.name.contains(d923("s9Xw7vDS1cfHjQ=="));
         }).collect(Collectors.toList()) : (List)this.configs.stream().filter((c) -> {
            return c.name.contains(d923("s9Xw7vDS1cfHjQ=="));
         }).collect(Collectors.toList());
      }
   }

   public void render(class_332 drawContext, int mouseX, int mouseY, float delta) {
      if (this.visible) {
         Color outlineColor = aq.getOutlineColor();
         Color fillColor = aq.getPrimaryAccent();
         this.updateTabAnimations(delta);
         iq.renderRoundedQuad(drawContext.method_51448(), ch.safeColor(outlineColor.getRed(), outlineColor.getGreen(), outlineColor.getBlue(), 50), (double)(this.x - 3), (double)(this.y - 3), (double)(this.x + this.width + 3), (double)(this.y + this.height + 3), 15.0D, 15.0D, 15.0D, 15.0D, 32.0D);
         iq.renderRoundedQuad(drawContext.method_51448(), ch.safeColor(16, 41 ^ 57, 24, 140 ^ 121), (double)this.x, (double)this.y, (double)(this.x + this.width), (double)(this.y + this.height), 13.0D, 13.0D, 13.0D, 13.0D, 32.0D);
         iq.renderRoundedOutline(drawContext, ch.safeColor(outlineColor.getRed(), outlineColor.getGreen(), outlineColor.getBlue(), 180), (double)this.x, (double)this.y, (double)(this.x + this.width), (double)(this.y + this.height), 13.0D, 13.0D, 13.0D, 13.0D, 2.0D, 32.0D);
         iq.renderRoundedQuad(drawContext.method_51448(), ch.safeColor(fillColor.getRed() - 30, fillColor.getGreen() - 30, fillColor.getBlue() - 30, 140), (double)this.x, (double)this.y, (double)(this.x + this.width), (double)(this.y + 45), 13.0D, 13.0D, 0.0D, 0.0D, 25.0D);
         i.drawString(d923("2PPz+PbHge/CysTBwto="), drawContext, this.x + 15, this.y + 15, ch.safeColor(92 ^ 172, 240, 250, 255).getRGB());
         int closeX = this.x + this.width - 35;
         int closeY = this.y + 12;
         boolean closeHovered = mouseX >= closeX && mouseX <= closeX + 25 && mouseY >= closeY && mouseY <= closeY + (80 ^ 73);
         iq.renderRoundedQuad(drawContext.method_51448(), ch.safeColor(198 ^ 14, 208 ^ 226, 50, closeHovered ? 245 ^ 65 : 98 ^ 26), (double)closeX, (double)closeY, (double)(closeX + 25), (double)(closeY + (60 ^ 37)), 6.0D, 6.0D, 6.0D, 6.0D, 15.0D);
         i.drawString("X", drawContext, closeX + (58 ^ 50), closeY + 6, ch.safeColor(255, 161 ^ 94, 255, 255).getRGB());
         if (!this.statusMessage.isEmpty() && System.currentTimeMillis() - this.statusMessageTime < 3000L) {
            this.renderStatusMessage(drawContext);
         }

         int tabY = this.y + 55;
         this.renderTabs(drawContext, mouseX, mouseY, tabY);
         int inputY = tabY + 50;
         if (this.selectedTab == 0) {
            this.renderInputSection(drawContext, mouseX, mouseY, inputY);
         }

         int buttonsY = this.selectedTab == 0 ? inputY + (115 ^ 65) : inputY + 10;
         this.renderButtons(drawContext, mouseX, mouseY, buttonsY);
         int listY = this.selectedTab == 0 ? buttonsY + (223 ^ 237) : buttonsY + 95;
         this.renderConfigList(drawContext, mouseX, mouseY, listY);
      }
   }

   private void updateTabAnimations(float delta) {
      for(int i = 0; i < this.tabAnimations.length; ++i) {
         float target = i == this.selectedTab ? 1.0F : 0.0F;
         this.tabAnimations[i] = (float)ac.approachValue(delta * 0.12F, (double)this.tabAnimations[i], (double)target);
      }

   }

   private void renderTabs(class_332 drawContext, int mouseX, int mouseY, int tabY) {
      Color outlineColor = aq.getOutlineColor();
      Color fillColor = aq.getPrimaryAccent();
      int tabWidth = 180;
      int tabHeight = 35;
      int spacing = 233 ^ 227;
      String[] tabNames = new String[]{d923("1uW93fDOx8vE1w=="), d923("yPT87PrEgeHMysPPwNs=")};

      for(int i = 0; i < 2; ++i) {
         int tabX = this.x + 15 + i * (tabWidth + spacing);
         boolean tabHovered = mouseX >= tabX && mouseX <= tabX + tabWidth && mouseY >= tabY && mouseY <= tabY + tabHeight;
         float hoverAlpha = tabHovered ? Math.min(this.tabAnimations[i] + 0.35F, 1.0F) : this.tabAnimations[i];
         if (hoverAlpha > 0.15F) {
            iq.renderRoundedQuad(drawContext.method_51448(), ch.safeColor(outlineColor.getRed(), outlineColor.getGreen(), outlineColor.getBlue(), (int)(40.0F * hoverAlpha)), (double)(tabX - 2), (double)(tabY - 2), (double)(tabX + tabWidth + 2), (double)(tabY + tabHeight + 2), 9.0D, 9.0D, 9.0D, 9.0D, 20.0D);
         }

         Color bgColor = this.selectedTab == i ? ch.safeColor(fillColor.getRed() / 3, fillColor.getGreen() / 3, fillColor.getBlue() / 3, 180) : ch.safeColor(76 ^ 90, 22, 32, (int)(120.0F + 60.0F * hoverAlpha));
         iq.renderRoundedQuad(drawContext.method_51448(), bgColor, (double)tabX, (double)tabY, (double)(tabX + tabWidth), (double)(tabY + tabHeight), 8.0D, 8.0D, 8.0D, 8.0D, 18.0D);
         int textBrightness;
         if (this.selectedTab == i || hoverAlpha > 0.15F) {
            textBrightness = this.selectedTab == i ? 200 : (int)(80.0F + 120.0F * hoverAlpha);
            float outlineWidth = this.selectedTab == i ? 2.0F : 1.2F + 0.8F * hoverAlpha;
            iq.renderRoundedOutline(drawContext, ch.safeColor(outlineColor.getRed(), outlineColor.getGreen(), outlineColor.getBlue(), textBrightness), (double)tabX, (double)tabY, (double)(tabX + tabWidth), (double)(tabY + tabHeight), 8.0D, 8.0D, 8.0D, 8.0D, (double)outlineWidth, 18.0D);
         }

         textBrightness = this.selectedTab == i ? 240 : (int)(180.0F + 60.0F * hoverAlpha);
         int textWidth = i.getWidth(tabNames[i]);
         i.drawString(tabNames[i], drawContext, tabX + (tabWidth - textWidth) / 2, tabY + 10, ch.safeColor(textBrightness, textBrightness, textBrightness, 255).getRGB());
      }

   }

   private void renderStatusMessage(class_332 drawContext) {
      Color fillColor = aq.getPrimaryAccent();
      int msgWidth = i.getWidth(this.statusMessage) + 20;
      int msgX = this.x + (this.width - msgWidth) / 2;
      int msgY = this.y + this.height - 40;
      long elapsed = System.currentTimeMillis() - this.statusMessageTime;
      float fade = 1.0F - (float)elapsed / 3000.0F;
      int alpha = (int)(255.0F * fade);
      iq.renderRoundedQuad(drawContext.method_51448(), ch.safeColor(fillColor.getRed() / 3, fillColor.getGreen() / 3, fillColor.getBlue() / 3, (int)(200.0F * fade)), (double)msgX, (double)msgY, (double)(msgX + msgWidth), (double)(msgY + (106 ^ 115)), 6.0D, 6.0D, 6.0D, 6.0D, 15.0D);
      i.drawString(this.statusMessage, drawContext, msgX + (13 ^ 7), msgY + 7, ch.safeColor(185 ^ 95, 185 ^ 95, 180 ^ 68, alpha).getRGB());
   }

   private void renderInputSection(class_332 drawContext, int mouseX, int mouseY, int inputY) {
      Color outlineColor = aq.getOutlineColor();
      boolean inputHovered = mouseX >= this.x + 15 && mouseX <= this.x + this.width - (73 ^ 70) && mouseY >= inputY && mouseY <= inputY + (198 ^ 229);
      int inputAlpha = this.inputFocused ? 61 ^ 245 : (inputHovered ? 160 : 120);
      iq.renderRoundedQuad(drawContext.method_51448(), ch.safeColor(22, 22, 155 ^ 187, inputAlpha), (double)(this.x + 15), (double)inputY, (double)(this.x + this.width - 15), (double)(inputY + 35), 8.0D, 8.0D, 8.0D, 8.0D, 20.0D);
      iq.renderRoundedOutline(drawContext, ch.safeColor(outlineColor.getRed(), outlineColor.getGreen(), outlineColor.getBlue(), this.inputFocused ? 34 ^ 234 : 100), (double)(this.x + 15), (double)inputY, (double)(this.x + this.width - (192 ^ 207)), (double)(inputY + 35), 8.0D, 8.0D, 8.0D, 8.0D, this.inputFocused ? 2.0D : 1.2000000476837158D, 20.0D);
      long time = System.currentTimeMillis();
      String cursor = this.inputFocused && time % 1000L > 500L ? "|" : "";
      String displayText = this.inputText.isEmpty() ? d923("3vLp++2Aws3NwszBh8bIx86Cg4A=") : this.inputText + cursor;
      int textColor = this.inputText.isEmpty() ? ch.safeColor(145 ^ 29, 140, 160, 200).getRGB() : ch.safeColor(230, 230, 240, 8 ^ 247).getRGB();
      i.drawString(displayText, drawContext, this.x + 25, inputY + (196 ^ 206), textColor);
   }

   private void renderButtons(class_332 drawContext, int mouseX, int mouseY, int buttonsY) {
      Color fillColor = aq.getPrimaryAccent();
      Color outlineColor = aq.getOutlineColor();
      int buttonHeight = 35;
      int saveWidth;
      int saveX;
      boolean saveHovered;
      if (this.selectedTab == 0) {
         saveWidth = this.width - (242 ^ 236);
         saveX = this.x + (24 ^ 23);
         saveHovered = mouseX >= saveX && mouseX <= saveX + saveWidth && mouseY >= buttonsY && mouseY <= buttonsY + buttonHeight;
         this.renderButton(drawContext, saveX, buttonsY, saveWidth, buttonHeight, d923("yP3r+7/j1NDRwcvSh+vGxM3Fyg=="), saveHovered, fillColor, outlineColor);
      } else {
         saveWidth = this.width - (176 ^ 174);
         saveX = this.x + 15;
         saveHovered = mouseX >= saveX && mouseX <= saveX + saveWidth && mouseY >= buttonsY && mouseY <= buttonsY + buttonHeight;
         Color importColor = ch.safeColor(80, 150, 255, 255);
         this.renderButton(drawContext, saveX, buttonsY, saveWidth, buttonHeight, d923("0vHt8e3UgcTRy8iG5MTA2snDzNzL"), saveHovered, importColor, outlineColor);
      }

   }

   private void renderButton(class_332 drawContext, int bx, int by, int bw, int bh, String text, boolean hovered, Color fillColor, Color outlineColor) {
      Color bgColor = hovered ? ch.safeColor(fillColor.getRed() / 2, fillColor.getGreen() / 2, fillColor.getBlue() / 2, 180) : ch.safeColor(22, 22, 249 ^ 217, 3 ^ 123);
      if (hovered) {
         iq.renderRoundedQuad(drawContext.method_51448(), ch.safeColor(fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue(), 40), (double)(bx - 2), (double)(by - 2), (double)(bx + bw + 2), (double)(by + bh + 2), 10.0D, 10.0D, 10.0D, 10.0D, 15.0D);
      }

      iq.renderRoundedQuad(drawContext.method_51448(), bgColor, (double)bx, (double)by, (double)(bx + bw), (double)(by + bh), 8.0D, 8.0D, 8.0D, 8.0D, 15.0D);
      iq.renderRoundedOutline(drawContext, ch.safeColor(outlineColor.getRed(), outlineColor.getGreen(), outlineColor.getBlue(), hovered ? 200 : 120), (double)bx, (double)by, (double)(bx + bw), (double)(by + bh), 8.0D, 8.0D, 8.0D, 8.0D, hovered ? 2.0D : 1.2000000476837158D, 15.0D);
      int textWidth = i.getWidth(text);
      i.drawString(text, drawContext, bx + (bw - textWidth) / 2, by + 10, ch.safeColor(196 ^ 47, 235, 245, 255).getRGB());
   }

   private void renderConfigList(class_332 drawContext, int mouseX, int mouseY, int listY) {
      Color outlineColor = aq.getOutlineColor();
      Color fillColor = aq.getPrimaryAccent();
      int listHeight = this.height - (listY - this.y) - 15;
      iq.renderRoundedQuad(drawContext.method_51448(), ch.safeColor(205 ^ 217, 20, 58 ^ 38, 200), (double)(this.x + 15), (double)listY, (double)(this.x + this.width - 15), (double)(listY + listHeight), 8.0D, 8.0D, 8.0D, 8.0D, 20.0D);
      String headerText = this.selectedTab == 0 ? d923("1uW9zf7WxMaD58rIwcHO2ZE=") : d923("0vHt8e3UxMaD58rIwcHO2ZE=");
      i.drawString(headerText, drawContext, this.x + 25, listY + (240 ^ 250), ch.safeColor(180, 137 ^ 61, 200, 11 ^ 244).getRGB());
      int itemY = listY + (37 ^ 6);
      int itemHeight = 11 ^ 35;
      this.hoveredConfig = null;
      List<ae> filteredConfigs = this.getFilteredConfigs();
      if (filteredConfigs.isEmpty()) {
         String emptyText = this.selectedTab == 0 ? d923("1fO9/fDOx8vE14XVxt7MzovVyNo=") : d923("1fO97ffB08fHhMbJyc7AzdiMxMPf38PG1tA=");
         i.drawString(emptyText, drawContext, this.x + 25, itemY + (177 ^ 187), ch.safeColor(140, 140, 160, 174 ^ 102).getRGB());
      } else {
         for(Iterator var12 = filteredConfigs.iterator(); var12.hasNext(); itemY += itemHeight + 5) {
            ae config = (ae)var12.next();
            if (itemY + itemHeight > listY + listHeight - 10) {
               break;
            }

            boolean itemHovered = mouseX >= this.x + 20 && mouseX <= this.x + this.width - 20 && mouseY >= itemY && mouseY <= itemY + itemHeight;
            if (itemHovered) {
               this.hoveredConfig = config;
            }

            int bgAlpha = itemHovered ? 223 ^ 107 : 120;
            iq.renderRoundedQuad(drawContext.method_51448(), ch.safeColor(28, 28, 162 ^ 132, bgAlpha), (double)(this.x + 20), (double)itemY, (double)(this.x + this.width - 20), (double)(itemY + itemHeight), 6.0D, 6.0D, 6.0D, 6.0D, 15.0D);
            if (itemHovered) {
               iq.renderRoundedOutline(drawContext, ch.safeColor(outlineColor.getRed(), outlineColor.getGreen(), outlineColor.getBlue(), 246 ^ 122), (double)(this.x + 20), (double)itemY, (double)(this.x + this.width - (76 ^ 88)), (double)(itemY + itemHeight), 6.0D, 6.0D, 6.0D, 6.0D, 1.5D, 15.0D);
            }

            String displayName = config.name.replace(d923("s9Xw7vDS1cfHjQ=="), "").trim();
            i.drawString(displayName, drawContext, this.x + (69 ^ 91), itemY + (59 ^ 51), ch.safeColor(230, 53 ^ 211, 240, 255).getRGB());
            i.drawString("#" + config.id, drawContext, this.x + (164 ^ 186), itemY + 22, ch.safeColor(140, 140, 252 ^ 72, 168 ^ 96).getRGB());
            int deleteX;
            int deleteY;
            boolean deleteHovered;
            if (this.selectedTab == 0) {
               deleteX = this.x + this.width - 85;
               deleteY = itemY + 8;
               deleteHovered = itemHovered && mouseX >= deleteX && mouseX <= deleteX + 25;
               iq.renderRoundedQuad(drawContext.method_51448(), ch.safeColor(125 ^ 45, 219 ^ 77, 39 ^ 216, deleteHovered ? 200 : 120), (double)deleteX, (double)deleteY, (double)(deleteX + 25), (double)(deleteY + (255 ^ 230)), 5.0D, 5.0D, 5.0D, 5.0D, 12.0D);
               i.drawString("S", drawContext, deleteX + (11 ^ 3), deleteY + 5, ch.safeColor(255, 255, 74 ^ 181, 66 ^ 189).getRGB());
            }

            deleteX = this.x + this.width - 55;
            deleteY = itemY + (16 ^ 24);
            deleteHovered = itemHovered && mouseX >= deleteX && mouseX <= deleteX + 25;
            iq.renderRoundedQuad(drawContext.method_51448(), ch.safeColor(200, 54 ^ 4, 50, deleteHovered ? 180 : 100), (double)deleteX, (double)deleteY, (double)(deleteX + 25), (double)(deleteY + 25), 5.0D, 5.0D, 5.0D, 5.0D, 12.0D);
            i.drawString("D", drawContext, deleteX + (123 ^ 115), deleteY + 5, ch.safeColor(105 ^ 150, 255, 57 ^ 198, 255).getRGB());
         }

      }
   }

   public void mouseClicked(double mouseX, double mouseY, int button) {
      if (this.visible) {
         int closeX = this.x + this.width - 35;
         int closeY = this.y + (29 ^ 17);
         if (mouseX >= (double)closeX && mouseX <= (double)(closeX + (221 ^ 196)) && mouseY >= (double)closeY && mouseY <= (double)(closeY + (79 ^ 86))) {
            this.visible = false;
         } else if (mouseY >= (double)this.y && mouseY <= (double)(this.y + 45)) {
            this.dragging = true;
            this.dragX = (int)(mouseX - (double)this.x);
            this.dragY = (int)(mouseY - (double)this.y);
         } else {
            int tabY = this.y + 55;
            int tabWidth = 87 ^ 227;
            int tabHeight = 35;
            int spacing = 132 ^ 142;

            int inputY;
            int buttonsY;
            for(inputY = 0; inputY < 2; ++inputY) {
               buttonsY = this.x + 15 + inputY * (tabWidth + spacing);
               if (mouseX >= (double)buttonsY && mouseX <= (double)(buttonsY + tabWidth) && mouseY >= (double)tabY && mouseY <= (double)(tabY + tabHeight)) {
                  this.selectedTab = inputY;
                  this.inputFocused = false;
                  return;
               }
            }

            if (this.selectedTab == 0) {
               inputY = tabY + 50;
               if (mouseX >= (double)(this.x + (103 ^ 104)) && mouseX <= (double)(this.x + this.width - (71 ^ 72)) && mouseY >= (double)inputY && mouseY <= (double)(inputY + 35)) {
                  this.inputFocused = true;
                  return;
               }
            }

            this.inputFocused = false;
            inputY = tabY + 50;
            buttonsY = this.selectedTab == 0 ? inputY + 50 : inputY + 10;
            int buttonHeight = 142 ^ 173;
            int buttonWidth = this.width - 30;
            int buttonX = this.x + (234 ^ 229);
            if (mouseX >= (double)buttonX && mouseX <= (double)(buttonX + buttonWidth) && mouseY >= (double)buttonsY && mouseY <= (double)(buttonsY + buttonHeight)) {
               if (this.selectedTab == 0) {
                  this.handleSave();
               } else {
                  this.handleImport();
               }

            } else {
               if (this.hoveredConfig != null) {
                  int deleteX;
                  if (this.selectedTab == 0) {
                     deleteX = this.x + this.width - 85;
                     if (mouseX >= (double)deleteX && mouseX <= (double)(deleteX + (130 ^ 155))) {
                        this.handleShare(this.hoveredConfig);
                        return;
                     }
                  }

                  deleteX = this.x + this.width - 55;
                  if (mouseX >= (double)deleteX && mouseX <= (double)(deleteX + 25)) {
                     this.handleDelete(this.hoveredConfig);
                     return;
                  }

                  this.handleLoadConfig(this.hoveredConfig);
               }

            }
         }
      }
   }

   public void mouseDragged(double mouseX, double mouseY) {
      if (this.dragging) {
         this.x = (int)(mouseX - (double)this.dragX);
         this.y = (int)(mouseY - (double)this.dragY);
      }

   }

   public void mouseReleased() {
      this.dragging = false;
   }

   public void keyPressed(int keyCode) {
      if (this.inputFocused) {
         if (keyCode == (421 ^ 166)) {
            if (!this.inputText.isEmpty()) {
               this.inputText = this.inputText.substring(0, this.inputText.length() - 1);
            }
         } else if (keyCode == (318 ^ 63)) {
            this.handleSave();
         } else if (keyCode == 256) {
            this.inputFocused = false;
         }

      }
   }

   public void charTyped(char chr) {
      if (this.inputFocused && this.inputText.length() < 20) {
         this.inputText = this.inputText + chr;
      }

   }

   private void showStatus(String message) {
      this.statusMessage = message;
      this.statusMessageTime = System.currentTimeMillis();
   }

   private void handleSave() {
      if (this.inputText.isEmpty()) {
         this.showStatus(d923("3vLp++2AwILAy8vAzs+JxMrByA=="));
      } else {
         String configId = Astralux.INSTANCE.getConfigManager().saveConfigWithId(this.inputText);
         if (configId != null) {
            this.showStatus("Saved config #" + configId);
            this.inputText = "";
            this.refreshConfigs();
         } else {
            this.showStatus(d923("3f308vrEgdbMhNbH0c2JycTCy8fI"));
         }

      }
   }

   private void handleLoadConfig(ae config) {
      boolean success = Astralux.INSTANCE.getConfigManager().loadConfigById(config.id);
      if (success) {
         String var10001 = config.name;
         String var10002 = d923("s9Xw7vDS1cfHjQ==");
         this.showStatus("Loaded " + var10001.replace(var10002, "").trim());
      } else {
         this.showStatus(d923("3f308vrEgdbMhMnJxsyJycTCy8fI"));
      }

   }

   private void handleDelete(ae config) {
      boolean success = Astralux.INSTANCE.getConfigManager().deleteConfig(config.id);
      if (success) {
         String var10001 = config.name;
         String var10002 = d923("s9Xw7vDS1cfHjQ==");
         this.showStatus("Deleted " + var10001.replace(var10002, "").trim());
         this.refreshConfigs();
      } else {
         this.showStatus(d923("3f308vrEgdbMhMHDy83dz4vPwsDJ2dY="));
      }

   }

   private void handleShare(ae config) {
      String result = Astralux.INSTANCE.getConfigManager().exportConfigToClipboard(config.id);
      if (result != null) {
         this.showStatus(d923("2PPt9/rEgdbMhMbKztjLxcreyY8="));
      } else {
         this.showStatus(d923("3f308vrEgdbMhMbJ19GJycTCy8fI"));
      }

   }

   private void handleImport() {
      String clipboardContent = Astralux.INSTANCE.getConfigManager().getClipboardContent();
      if (clipboardContent != null && !clipboardContent.isEmpty()) {
         if (!clipboardContent.contains(d923("2s/JzN7s9Pr85+ro4eHukA=="))) {
            this.showStatus(d923("0vLr//PJxYLAy8vAzs+JzMTewM/b"));
         } else {
            boolean success = Astralux.INSTANCE.getConfigManager().importConfigFromClipboard(clipboardContent);
            if (success) {
               this.showStatus(d923("0vHt8e3UxMaDx8rIwcHOiw=="));
               this.refreshConfigs();
            } else {
               this.showStatus(d923("3f308vrEgdbMhMzL18fb3ovPwsDJ2dY="));
            }

         }
      } else {
         this.showStatus(d923("2PD07v3PwNDHhMzVh83E2t/V"));
      }
   }

   // $FF: synthetic method
   private static String d923(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 2715 + var3 & 255);
      }

      return new String(var2);
   }
}
