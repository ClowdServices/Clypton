import client.astralux.Astralux;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_437;
import net.minecraft.class_7923;

class gg extends class_437 {
   private final ay setting;
   private String searchQuery;
   private final List<class_1792> allItems;
   private List<class_1792> filteredItems;
   private final Set<class_1792> selectedItems;
   private int scrollOffset;
   private final int ITEMS_PER_ROW = 176 ^ 187;
   private final int MAX_ROWS_VISIBLE = 76 ^ 74;
   private final int ITEM_SIZE = 40;
   final av parent;

   public gg(av parent, ay setting) {
      super(class_2561.method_43473());
      this.parent = parent;
      this.searchQuery = "";
      this.scrollOffset = 0;
      this.setting = setting;
      this.selectedItems = new HashSet(setting.getItems());
      this.allItems = new ArrayList();
      class_7923.field_41178.forEach((item) -> {
         if (item != class_1802.field_8162) {
            this.allItems.add(item);
         }

      });
      this.filteredItems = new ArrayList(this.allItems);
   }

   public void method_25394(class_332 drawContext, int mouseX, int mouseY, float delta) {
      iq.unscaledProjection();
      int scaledMouseX = mouseX * (int)class_310.method_1551().method_22683().method_4495();
      int scaledMouseY = mouseY * (int)class_310.method_1551().method_22683().method_4495();
      super.method_25394(drawContext, scaledMouseX, scaledMouseY, delta);
      int screenWidth = this.parent.mc.method_22683().method_4480();
      int screenHeight = this.parent.mc.method_22683().method_4507();
      int bgAlpha = gm.renderBackground.getValue() ? 180 : 0;
      drawContext.method_25294(0, 0, screenWidth, screenHeight, ch.safeColor(0, 0, 0, bgAlpha).getRGB());
      int panelWidth = 573 ^ 121;
      int panelHeight = true;
      int panelX = (screenWidth - 580) / 2;
      int panelY = (screenHeight - 450) / 2;
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getPanelBackgroundWithAlpha(240), (double)panelX, (double)panelY, (double)(panelX + 580), (double)(panelY + (258 ^ 192)), 8.0D, 8.0D, 8.0D, 8.0D, 20.0D);
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getHeaderBackground(), (double)panelX, (double)panelY, (double)(panelX + (601 ^ 29)), (double)(panelY + (105 ^ 119)), 8.0D, 8.0D, 0.0D, 0.0D, 20.0D);
      drawContext.method_25294(panelX, panelY + 30, panelX + 580, panelY + 31, bi.getMainColor(255, 1).getRGB());
      String var10000 = String.valueOf(this.setting.getName());
      i.drawCenteredString("Select Items: " + var10000 + " (" + this.selectedItems.size() + " selected)", drawContext, panelX + (431 ^ 141), panelY + (190 ^ 182), aq.getBrightTextColor().getRGB());
      int searchX = panelX + 20;
      int searchY = panelY + 50;
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getGridBackground(), (double)searchX, (double)searchY, (double)(searchX + 540), (double)(searchY + (252 ^ 226)), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);
      iq.renderRoundedOutline(drawContext, aq.getButtonBorder(), (double)searchX, (double)searchY, (double)(searchX + 540), (double)(searchY + 30), 5.0D, 5.0D, 5.0D, 5.0D, 1.0D, 20.0D);
      String cursor = System.currentTimeMillis() % 1000L > 500L ? "|" : "";
      i.drawString("Search: " + this.searchQuery + cursor, drawContext, searchX + (106 ^ 96), searchY + 9, aq.getTextColor().getRGB());
      int gridX = panelX + 20;
      int gridY = searchY + (200 ^ 214) + (34 ^ 45);
      int gridHeight = 450 - (gridY - panelY) - (33 ^ 29);
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getGridBackground(), (double)gridX, (double)gridY, (double)(gridX + 540), (double)(gridY + gridHeight), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);
      int totalRows = (int)Math.ceil((double)this.filteredItems.size() / 11.0D);
      int maxScroll = Math.max(0, totalRows - 6);
      this.scrollOffset = Math.min(this.scrollOffset, maxScroll);
      int startIndex;
      int endIndex;
      int buttonY;
      int itemX;
      if (totalRows > (142 ^ 136)) {
         startIndex = gridX + (551 ^ 59) - 6 - 5;
         endIndex = gridY + 5;
         buttonY = gridHeight - 10;
         iq.renderRoundedQuad(drawContext.method_51448(), aq.getButtonBackgroundWithAlpha(150), (double)startIndex, (double)endIndex, (double)(startIndex + 6), (double)(endIndex + buttonY), 3.0D, 3.0D, 3.0D, 3.0D, 20.0D);
         float scrollPercent = (float)this.scrollOffset / (float)maxScroll;
         float thumbHeight = Math.max(40.0F, (float)buttonY * (6.0F / (float)totalRows));
         itemX = endIndex + (int)(((float)buttonY - thumbHeight) * scrollPercent);
         iq.renderRoundedQuad(drawContext.method_51448(), bi.getMainColor(255, 1), (double)startIndex, (double)itemX, (double)(startIndex + (172 ^ 170)), (double)((float)itemX + thumbHeight), 3.0D, 3.0D, 3.0D, 3.0D, 20.0D);
      }

      startIndex = this.scrollOffset * (78 ^ 69);
      endIndex = Math.min(startIndex + Math.min(this.filteredItems.size(), 66), this.filteredItems.size());

      int saveButtonX;
      int cancelButtonX;
      for(buttonY = startIndex; buttonY < endIndex; ++buttonY) {
         saveButtonX = (buttonY - startIndex) % 11;
         cancelButtonX = (buttonY - startIndex) / (80 ^ 91);
         itemX = gridX + 5 + saveButtonX * (124 ^ 76);
         int itemY = gridY + 5 + cancelButtonX * 48;
         class_1792 item = (class_1792)this.filteredItems.get(buttonY);
         boolean isSelected = this.selectedItems.contains(item);
         Color itemBg = isSelected ? bi.getMainColor(116 ^ 16, 1) : aq.getButtonBackground();
         iq.renderRoundedQuad(drawContext.method_51448(), itemBg, (double)itemX, (double)itemY, (double)(itemX + 40), (double)(itemY + 40), 4.0D, 4.0D, 4.0D, 4.0D, 20.0D);
         iq.drawItem(drawContext, new class_1799(item), itemX, itemY, 40.0F, 0);
         if (isSelected) {
            iq.renderRoundedOutline(drawContext, bi.getMainColor(255, 1), (double)itemX, (double)itemY, (double)(itemX + 40), (double)(itemY + 40), 4.0D, 4.0D, 4.0D, 4.0D, 2.0D, 20.0D);
         }

         if (scaledMouseX >= itemX && scaledMouseX <= itemX + 40 && scaledMouseY >= itemY && scaledMouseY <= itemY + 40) {
            iq.renderRoundedOutline(drawContext, bi.getMainColor(191 ^ 119, 1), (double)itemX, (double)itemY, (double)(itemX + 40), (double)(itemY + 40), 4.0D, 4.0D, 4.0D, 4.0D, 1.0D, 20.0D);
         }
      }

      if (this.filteredItems.isEmpty()) {
         i.drawCenteredString(d539("uZfZk4+ZkI3fZm53bWA="), drawContext, gridX + 270, gridY + gridHeight / 2 - 10, ch.safeColor(240 ^ 102, 150, 46 ^ 184, 200).getRGB());
      }

      buttonY = panelY + 450 - 45;
      saveButtonX = panelX + 580 - (94 ^ 14) - 20;
      cancelButtonX = saveButtonX - 80 - (145 ^ 155);
      itemX = cancelButtonX - 80 - 10;
      iq.renderRoundedQuad(drawContext.method_51448(), bi.getMainColor(201 ^ 54, 1), (double)saveButtonX, (double)buttonY, (double)(saveButtonX + 80), (double)(buttonY + (98 ^ 124)), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);
      i.drawCenteredString(d539("pJmPnw=="), drawContext, saveButtonX + 40, buttonY + (184 ^ 176), aq.getBrightTextColor().getRGB());
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getButtonBorder(), (double)cancelButtonX, (double)buttonY, (double)(cancelButtonX + 80), (double)(buttonY + 30), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);
      i.drawCenteredString(d539("tJmXmZ6Q"), drawContext, cancelButtonX + 40, buttonY + 8, aq.getBrightTextColor().getRGB());
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getSecondaryAccent(), (double)itemX, (double)buttonY, (double)(itemX + 80), (double)(buttonY + (74 ^ 84)), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);
      i.drawCenteredString(d539("tJScm4k="), drawContext, itemX + (82 ^ 122), buttonY + (60 ^ 52), aq.getBrightTextColor().getRGB());
      iq.scaledProjection();
   }

   public boolean method_25402(double mouseX, double mouseY, int button) {
      double scaledX = mouseX * class_310.method_1551().method_22683().method_4495();
      double scaledY = mouseY * class_310.method_1551().method_22683().method_4495();
      int screenWidth = this.parent.mc.method_22683().method_4480();
      int screenHeight = this.parent.mc.method_22683().method_4507();
      int panelX = (screenWidth - (670 ^ 218)) / 2;
      int panelY = (screenHeight - (286 ^ 220)) / 2;
      int buttonY = panelY + 450 - 45;
      int saveButtonX = panelX + (606 ^ 26) - (235 ^ 187) - 20;
      int cancelButtonX = saveButtonX - (221 ^ 141) - (9 ^ 3);
      int clearButtonX = cancelButtonX - (71 ^ 23) - 10;
      if (this.isInBounds(scaledX, scaledY, saveButtonX, buttonY, 80, 30)) {
         this.setting.getItems().clear();
         this.setting.getItems().addAll(this.selectedItems);
         this.parent.mc.method_1507(Astralux.INSTANCE.GUI);
         return true;
      } else if (this.isInBounds(scaledX, scaledY, cancelButtonX, buttonY, 80, 171 ^ 181)) {
         this.parent.mc.method_1507(Astralux.INSTANCE.GUI);
         return true;
      } else if (this.isInBounds(scaledX, scaledY, clearButtonX, buttonY, 144 ^ 192, 30)) {
         this.selectedItems.clear();
         return true;
      } else {
         int gridX = panelX + (135 ^ 147);
         int gridY = panelY + (89 ^ 107) + 30 + 15;
         int gridHeight = 450 - (gridY - panelY) - 60;
         if (this.isInBounds(scaledX, scaledY, gridX, gridY, 540, gridHeight)) {
            int startIndex = this.scrollOffset * 11;
            int col = (int)(scaledX - (double)gridX - 5.0D) / 48;
            if (col >= 0 && col < 11) {
               int row = (int)(scaledY - (double)gridY - 5.0D) / 48;
               int clickedIndex = startIndex + row * 11 + col;
               if (clickedIndex >= 0 && clickedIndex < this.filteredItems.size()) {
                  class_1792 clickedItem = (class_1792)this.filteredItems.get(clickedIndex);
                  if (this.selectedItems.contains(clickedItem)) {
                     this.selectedItems.remove(clickedItem);
                  } else {
                     this.selectedItems.add(clickedItem);
                  }

                  return true;
               }
            }
         }

         return super.method_25402(scaledX, scaledY, button);
      }
   }

   public boolean method_25401(double mouseX, double mouseY, double horizAmount, double vertAmount) {
      double scaledX = mouseX * class_310.method_1551().method_22683().method_4495();
      double scaledY = mouseY * class_310.method_1551().method_22683().method_4495();
      int screenWidth = this.parent.mc.method_22683().method_4480();
      int screenHeight = this.parent.mc.method_22683().method_4507();
      int panelX = (screenWidth - 580) / 2;
      int panelY = (screenHeight - 450) / 2;
      int gridX = panelX + 20;
      int gridY = panelY + 50 + (144 ^ 142) + (185 ^ 182);
      int gridHeight = 450 - (gridY - panelY) - 60;
      if (this.isInBounds(scaledX, scaledY, gridX, gridY, 540, gridHeight)) {
         int totalRows = (int)Math.ceil((double)this.filteredItems.size() / 11.0D);
         int maxScroll = Math.max(0, totalRows - (213 ^ 211));
         if (vertAmount > 0.0D) {
            this.scrollOffset = Math.max(0, this.scrollOffset - 1);
         } else if (vertAmount < 0.0D) {
            this.scrollOffset = Math.min(maxScroll, this.scrollOffset + 1);
         }

         return true;
      } else {
         return super.method_25401(scaledX, scaledY, horizAmount, vertAmount);
      }
   }

   public boolean method_25404(int keyCode, int scanCode, int modifiers) {
      if (keyCode == 256) {
         this.setting.getItems().clear();
         this.setting.getItems().addAll(this.selectedItems);
         this.parent.mc.method_1507(Astralux.INSTANCE.GUI);
         return true;
      } else if (keyCode == 259) {
         if (!this.searchQuery.isEmpty()) {
            this.searchQuery = this.searchQuery.substring(0, this.searchQuery.length() - 1);
            this.updateFilteredItems();
         }

         return true;
      } else if (keyCode == 257) {
         this.setting.getItems().clear();
         this.setting.getItems().addAll(this.selectedItems);
         this.parent.mc.method_1507(Astralux.INSTANCE.GUI);
         return true;
      } else {
         return super.method_25404(keyCode, scanCode, modifiers);
      }
   }

   public boolean method_25400(char chr, int modifiers) {
      this.searchQuery = this.searchQuery + chr;
      this.updateFilteredItems();
      return true;
   }

   private void updateFilteredItems() {
      if (this.searchQuery.isEmpty()) {
         this.filteredItems = new ArrayList(this.allItems);
      } else {
         this.filteredItems = (List)this.allItems.stream().filter((item) -> {
            return item.method_7848().getString().toLowerCase().contains(this.searchQuery.toLowerCase());
         }).collect(Collectors.toList());
      }

      this.scrollOffset = 0;
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
   private static String d539(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 9463 + var3 & 255);
      }

      return new String(var2);
   }
}
