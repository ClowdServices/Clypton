import java.awt.Color;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_2561;
import net.minecraft.class_332;
import net.minecraft.class_437;
import net.minecraft.class_7923;

public class gy extends class_437 {
   private final ih module;
   private final int SLOT_SIZE = 238 ^ 222;
   private final int SLOT_SPACING = 4;
   private int gridX;
   private int gridY;
   private int selectedSlot = -1;
   private boolean showItemPicker = false;
   private int scrollOffset = 0;
   private final int MAX_VISIBLE_ITEMS = 8;
   private String searchQuery = "";
   private List<class_1792> filteredItems = new ArrayList();

   public gy(ih module) {
      super(class_2561.method_43473());
      this.module = module;
      this.updateFilteredItems();
   }

   protected void method_25426() {
      super.method_25426();
      int screenWidth = this.field_22787.method_22683().method_4480();
      int screenHeight = this.field_22787.method_22683().method_4507();
      int gridWidth = 152;
      int gridHeight = 45 ^ 181;
      this.gridX = (screenWidth - gridWidth) / 2;
      this.gridY = (screenHeight - gridHeight) / 2;
   }

   public void method_25394(class_332 drawContext, int mouseX, int mouseY, float delta) {
      try {
         if (this.field_22787 == null || this.field_22787.method_22683() == null) {
            super.method_25394(drawContext, mouseX, mouseY, delta);
            return;
         }

         iq.unscaledProjection();
         int scaledMouseX = mouseX * (int)this.field_22787.method_22683().method_4495();
         int scaledMouseY = mouseY * (int)this.field_22787.method_22683().method_4495();
         int screenWidth = this.field_22787.method_22683().method_4480();
         int screenHeight = this.field_22787.method_22683().method_4507();
         int bgAlpha = gm.renderBackground.getValue() ? 180 : 0;
         drawContext.method_25294(0, 0, screenWidth, screenHeight, ch.safeColor(0, 0, 0, bgAlpha).getRGB());
         int panelWidth = 428 ^ 110;
         int panelHeight = true;
         int panelX = (screenWidth - 450) / 2;
         int panelY = (screenHeight - 500) / 2;
         int gridWidth = 152;
         int gridHeight = 72 ^ 208;
         this.gridX = panelX + (450 - gridWidth) / 2;
         this.gridY = panelY + 80;
         iq.renderRoundedQuad(drawContext.method_51448(), aq.getPanelBackgroundWithAlpha(240), (double)panelX, (double)panelY, (double)(panelX + (464 ^ 18)), (double)(panelY + (260 ^ 240)), 8.0D, 8.0D, 8.0D, 8.0D, 20.0D);
         iq.renderRoundedQuad(drawContext.method_51448(), aq.getHeaderBackground(), (double)panelX, (double)panelY, (double)(panelX + 450), (double)(panelY + (176 ^ 152)), 8.0D, 8.0D, 0.0D, 0.0D, 20.0D);
         drawContext.method_25294(panelX, panelY + 40, panelX + (332 ^ 142), panelY + 41, bi.getMainColor(37 ^ 218, 1).getRGB());
         i.drawCenteredString(d631("NAMDF1k5CR0bChryodDm5+z24g=="), drawContext, panelX + 225, panelY + (66 ^ 78), aq.getBrightTextColor().getRGB());

         int row;
         int slotIndex;
         int slotY;
         for(row = 0; row < 3; ++row) {
            for(int col = 0; col < 3; ++col) {
               slotIndex = row * 3 + col;
               int slotX = this.gridX + col * 52;
               slotY = this.gridY + row * 52;
               boolean isEnabled = this.module.isSlotEnabled(slotIndex);
               class_1792 recipeItem = this.module.getRecipeSlot(slotIndex);
               Color slotBg = isEnabled ? bi.getMainColor(220 ^ 74, 1) : ch.safeColor(60, 60, 60, 255);
               iq.renderRoundedQuad(drawContext.method_51448(), slotBg, (double)slotX, (double)slotY, (double)(slotX + (15 ^ 63)), (double)(slotY + 48), 6.0D, 6.0D, 6.0D, 6.0D, 20.0D);
               if (recipeItem != class_1802.field_8162) {
                  iq.drawItem(drawContext, new class_1799(recipeItem), slotX + (184 ^ 176), slotY + 8, 32.0F, 0);
               } else {
                  String statusText = isEnabled ? "ON" : d631("OjAx");
                  i.drawCenteredString(statusText, drawContext, slotX + 24, slotY + 24 - 5, aq.getBrightTextColor().getRGB());
               }
            }
         }

         i.drawCenteredString("Click slots to select items • Right-click to clear", drawContext, panelX + (146 ^ 115), this.gridY + gridHeight + 20, aq.getTextColor().getRGB());
         row = panelY + 500 - (179 ^ 129);
         int buttonWidth = true;
         slotIndex = 65 ^ 78;
         int totalButtonWidth = true;
         slotY = panelX + (245 ^ 201);
         iq.renderRoundedQuad(drawContext.method_51448(), aq.getSecondaryAccent(), (double)slotY, (double)row, (double)(slotY + (184 ^ 220)), (double)(row + (209 ^ 207)), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);
         i.drawCenteredString(d631("NhoSGQs="), drawContext, slotY + 50, row + 8, aq.getBrightTextColor().getRGB());
         int saveButtonX = slotY + 100 + (72 ^ 71);
         iq.renderRoundedQuad(drawContext.method_51448(), bi.getMainColor(50 ^ 250, 1), (double)saveButtonX, (double)row, (double)(saveButtonX + 100), (double)(row + 30), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);
         i.drawCenteredString(d631("JhcBHQ=="), drawContext, saveButtonX + 50, row + 8, aq.getBrightTextColor().getRGB());
         int closeButtonX = slotY + 230;
         iq.renderRoundedQuad(drawContext.method_51448(), bi.getMainColor(255, 1), (double)closeButtonX, (double)row, (double)(closeButtonX + 100), (double)(row + 30), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);
         i.drawCenteredString(d631("NhoYCxw="), drawContext, closeButtonX + 50, row + 8, aq.getBrightTextColor().getRGB());
         if (this.showItemPicker && this.selectedSlot != -1) {
            this.renderItemPicker(drawContext, scaledMouseX, scaledMouseY);
         }

         iq.scaledProjection();
         super.method_25394(drawContext, mouseX, mouseY, delta);
      } catch (Exception var25) {
         System.err.println("Error rendering AutoCrafterConfigScreen: " + var25.getMessage());
         var25.printStackTrace();
      }

   }

   private void renderItemPicker(class_332 drawContext, int mouseX, int mouseY) {
      try {
         int screenWidth = this.field_22787.method_22683().method_4480();
         int screenHeight = this.field_22787.method_22683().method_4507();
         int pickerWidth = true;
         int pickerHeight = true;
         int pickerX = (screenWidth - 400) / 2;
         int pickerY = (screenHeight - 450) / 2;
         drawContext.method_25294(0, 0, screenWidth, screenHeight, ch.safeColor(0, 0, 0, 150).getRGB());
         iq.renderRoundedQuad(drawContext.method_51448(), aq.getPanelBackgroundWithAlpha(255), (double)pickerX, (double)pickerY, (double)(pickerX + 400), (double)(pickerY + 450), 8.0D, 8.0D, 8.0D, 8.0D, 20.0D);
         iq.renderRoundedQuad(drawContext.method_51448(), aq.getHeaderBackground(), (double)pickerX, (double)pickerY, (double)(pickerX + 400), (double)(pickerY + 35), 8.0D, 8.0D, 0.0D, 0.0D, 20.0D);
         i.drawCenteredString(d631("JhMbHRoOWzUJGxI="), drawContext, pickerX + (135 ^ 79), pickerY + (25 ^ 19), aq.getBrightTextColor().getRGB());
         int searchBoxX = pickerX + (209 ^ 197);
         int searchBoxY = pickerY + (119 ^ 69);
         int searchBoxWidth = true;
         iq.renderRoundedQuad(drawContext.method_51448(), aq.getGridBackground(), (double)searchBoxX, (double)searchBoxY, (double)(searchBoxX + 360), (double)(searchBoxY + (47 ^ 49)), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);
         i.drawString(this.searchQuery.isEmpty() ? d631("JhMWChoSVVJT") : this.searchQuery, drawContext, searchBoxX + (169 ^ 163), searchBoxY + (4 ^ 12), this.searchQuery.isEmpty() ? ch.safeColor(133 ^ 225, 156 ^ 248, 1 ^ 101, 255).getRGB() : aq.getTextColor().getRGB());
         int itemsPerRow = true;
         int itemSize = 222 ^ 238;
         int itemSpacing = 216 ^ 208;
         int gridStartX = pickerX + 20;
         int gridStartY = pickerY + 95;
         int visibleStart = this.scrollOffset;
         int visibleEnd = Math.min(visibleStart + (62 ^ 14), this.filteredItems.size());

         int closeButtonX;
         int closeButtonY;
         for(closeButtonX = visibleStart; closeButtonX < visibleEnd; ++closeButtonX) {
            closeButtonY = closeButtonX - visibleStart;
            int row = closeButtonY / (97 ^ 103);
            int col = closeButtonY % 6;
            int itemX = gridStartX + col * (68 ^ 124);
            int itemY = gridStartY + row * 56;
            class_1792 item = (class_1792)this.filteredItems.get(closeButtonX);
            boolean isHovered = this.isInBounds((double)mouseX, (double)mouseY, itemX, itemY, 48, 48);
            Color slotColor = isHovered ? bi.getMainColor(150, 1) : aq.getGridBackground();
            iq.renderRoundedQuad(drawContext.method_51448(), slotColor, (double)itemX, (double)itemY, (double)(itemX + (92 ^ 108)), (double)(itemY + 48), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);
            iq.drawItem(drawContext, new class_1799(item), itemX + (230 ^ 238), itemY + 8, 32.0F, 0);
         }

         closeButtonX = pickerX + 400 - (171 ^ 241);
         closeButtonY = pickerY + 450 - (118 ^ 91);
         iq.renderRoundedQuad(drawContext.method_51448(), bi.getMainColor(255, 1), (double)closeButtonX, (double)closeButtonY, (double)(closeButtonX + 70), (double)(closeButtonY + 30), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);
         i.drawCenteredString(d631("NhcZGxwW"), drawContext, closeButtonX + 35, closeButtonY + 8, aq.getBrightTextColor().getRGB());
      } catch (Exception var29) {
         System.err.println("Error rendering item picker: " + var29.getMessage());
         var29.printStackTrace();
      }

   }

   public boolean method_25402(double mouseX, double mouseY, int button) {
      try {
         if (this.field_22787 != null && this.field_22787.method_22683() != null) {
            double scaledX = mouseX * this.field_22787.method_22683().method_4495();
            double scaledY = mouseY * this.field_22787.method_22683().method_4495();
            int screenWidth = this.field_22787.method_22683().method_4480();
            int screenHeight = this.field_22787.method_22683().method_4507();
            int panelWidth = true;
            int panelHeight = true;
            int panelX = (screenWidth - 450) / 2;
            int panelY = (screenHeight - (394 ^ 126)) / 2;
            int gridWidth = 152;
            this.gridX = panelX + (450 - gridWidth) / 2;
            this.gridY = panelY + 80;
            if (this.showItemPicker) {
               return this.handleItemPickerClick(scaledX, scaledY);
            } else {
               int buttonY;
               int col;
               int slotY;
               for(buttonY = 0; buttonY < 3; ++buttonY) {
                  for(col = 0; col < 3; ++col) {
                     int slotIndex = buttonY * 3 + col;
                     int slotX = this.gridX + col * 52;
                     slotY = this.gridY + buttonY * (211 ^ 231);
                     if (this.isInBounds(scaledX, scaledY, slotX, slotY, 163 ^ 147, 226 ^ 210)) {
                        if (button == 1) {
                           this.module.setRecipeSlot(slotIndex, class_1802.field_8162);
                        } else {
                           this.selectedSlot = slotIndex;
                           this.showItemPicker = true;
                           this.scrollOffset = 0;
                           this.updateFilteredItems();
                        }

                        return true;
                     }
                  }
               }

               buttonY = panelY + 500 - 50;
               col = 3 ^ 103;
               int buttonSpacing = true;
               int totalButtonWidth = true;
               slotY = panelX + 60;
               int saveButtonX = slotY + 100 + 15;
               int closeButtonX = slotY + 230;
               if (this.isInBounds(scaledX, scaledY, slotY, buttonY, 80 ^ 52, 30)) {
                  this.module.clearRecipe();
                  return true;
               } else if (this.isInBounds(scaledX, scaledY, saveButtonX, buttonY, 101 ^ 1, 30)) {
                  this.module.saveRecipe();
                  return true;
               } else if (this.isInBounds(scaledX, scaledY, closeButtonX, buttonY, 100, 56 ^ 38)) {
                  this.method_25419();
                  return true;
               } else {
                  return super.method_25402(scaledX, scaledY, button);
               }
            }
         } else {
            return false;
         }
      } catch (Exception var25) {
         System.err.println("Error handling mouse click: " + var25.getMessage());
         var25.printStackTrace();
         return false;
      }
   }

   private boolean handleItemPickerClick(double scaledX, double scaledY) {
      try {
         int screenWidth = this.field_22787.method_22683().method_4480();
         int screenHeight = this.field_22787.method_22683().method_4507();
         int pickerWidth = true;
         int pickerHeight = true;
         int pickerX = (screenWidth - 400) / 2;
         int pickerY = (screenHeight - (493 ^ 47)) / 2;
         int closeButtonX = pickerX + 400 - (252 ^ 166);
         int closeButtonY = pickerY + (454 ^ 4) - (59 ^ 22);
         if (this.isInBounds(scaledX, scaledY, closeButtonX, closeButtonY, 139 ^ 205, 30)) {
            this.showItemPicker = false;
            this.selectedSlot = -1;
            return true;
         } else {
            int itemsPerRow = 62 ^ 56;
            int itemSize = true;
            int itemSpacing = true;
            int gridStartX = pickerX + (73 ^ 93);
            int gridStartY = pickerY + (68 ^ 27);
            int visibleStart = this.scrollOffset;
            int visibleEnd = Math.min(visibleStart + 48, this.filteredItems.size());

            for(int i = visibleStart; i < visibleEnd; ++i) {
               int localIndex = i - visibleStart;
               int row = localIndex / (174 ^ 168);
               int col = localIndex % 6;
               int itemX = gridStartX + col * (104 ^ 80);
               int itemY = gridStartY + row * (227 ^ 219);
               if (this.isInBounds(scaledX, scaledY, itemX, itemY, 48, 48)) {
                  class_1792 selectedItem = (class_1792)this.filteredItems.get(i);
                  this.module.setRecipeSlot(this.selectedSlot, selectedItem);
                  this.showItemPicker = false;
                  this.selectedSlot = -1;
                  return true;
               }
            }

            return true;
         }
      } catch (Exception var27) {
         System.err.println("Error handling item picker click: " + var27.getMessage());
         var27.printStackTrace();
         return false;
      }
   }

   public boolean method_25401(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
      try {
         if (this.showItemPicker) {
            this.scrollOffset = Math.max(0, Math.min(this.scrollOffset - (int)(verticalAmount * 6.0D), Math.max(0, this.filteredItems.size() - (73 ^ 121))));
            return true;
         } else {
            return super.method_25401(mouseX, mouseY, horizontalAmount, verticalAmount);
         }
      } catch (Exception var10) {
         System.err.println("Error handling mouse scroll: " + var10.getMessage());
         var10.printStackTrace();
         return false;
      }
   }

   public boolean method_25400(char chr, int modifiers) {
      try {
         if (this.showItemPicker) {
            this.searchQuery = this.searchQuery + chr;
            this.updateFilteredItems();
            this.scrollOffset = 0;
            return true;
         } else {
            return super.method_25400(chr, modifiers);
         }
      } catch (Exception var4) {
         System.err.println("Error handling character typed: " + var4.getMessage());
         var4.printStackTrace();
         return false;
      }
   }

   public boolean method_25404(int keyCode, int scanCode, int modifiers) {
      try {
         if (this.showItemPicker && keyCode == 259 && !this.searchQuery.isEmpty()) {
            this.searchQuery = this.searchQuery.substring(0, this.searchQuery.length() - 1);
            this.updateFilteredItems();
            this.scrollOffset = 0;
            return true;
         } else {
            return super.method_25404(keyCode, scanCode, modifiers);
         }
      } catch (Exception var5) {
         System.err.println("Error handling key press: " + var5.getMessage());
         var5.printStackTrace();
         return false;
      }
   }

   private void updateFilteredItems() {
      try {
         this.filteredItems.clear();
         if (class_7923.field_41178 != null) {
            String query = this.searchQuery.toLowerCase();
            int maxItems = 288 ^ 212;
            int count = 0;
            Iterator var4 = class_7923.field_41178.iterator();

            while(true) {
               class_1792 item;
               String itemName;
               do {
                  do {
                     do {
                        if (!var4.hasNext()) {
                           return;
                        }

                        item = (class_1792)var4.next();
                     } while(item == null);
                  } while(item == class_1802.field_8162);

                  if (count >= maxItems) {
                     return;
                  }

                  itemName = item.toString().toLowerCase();
               } while(!query.isEmpty() && !itemName.contains(query));

               this.filteredItems.add(item);
               ++count;
            }
         }
      } catch (Exception var7) {
         System.err.println("Error updating filtered items: " + var7.getMessage());
         var7.printStackTrace();
      }
   }

   private boolean isInBounds(double x, double y, int bx, int by, int w, int h) {
      return x >= (double)bx && x <= (double)(bx + w) && y >= (double)by && y <= (double)(by + h);
   }

   public void method_25420(class_332 context, int mouseX, int mouseY, float delta) {
      if (this.field_22787 != null && this.field_22787.field_1687 == null) {
         super.method_25420(context, mouseX, mouseY, delta);
      }

   }

   public void method_25419() {
      try {
         if (this.module != null) {
            this.module.savePattern();
         }
      } catch (Exception var2) {
         System.err.println("Error saving pattern: " + var2.getMessage());
         var2.printStackTrace();
      }

      super.method_25419();
   }

   public boolean method_25422() {
      if (this.showItemPicker) {
         this.showItemPicker = false;
         this.selectedSlot = -1;
         return false;
      } else {
         return true;
      }
   }

   // $FF: synthetic method
   private static String d631(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 5460 - 223 + var3 & 255);
      }

      return new String(var2);
   }
}
