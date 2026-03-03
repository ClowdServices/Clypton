import client.astralux.Astralux;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.class_1799;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_437;
import net.minecraft.class_7923;

class dy extends class_437 {
   private final il setting;
   private String searchQuery;
   private final List<class_2248> allBlocks;
   private List<class_2248> filteredBlocks;
   private final Set<class_2248> selectedBlocks;
   private int scrollOffset;
   private final int ITEMS_PER_ROW = 22 ^ 29;
   private final int MAX_ROWS_VISIBLE = 6;
   private final int ITEM_SIZE = 40;
   private boolean showingOnlySelected = false;
   private boolean colorEditMode = false;
   final au parent;
   public final Map<class_2248, Color> blockColors;

   public dy(au parent, il setting, Map<class_2248, Color> blockColors) {
      super(class_2561.method_43473());
      this.parent = parent;
      this.searchQuery = "";
      this.scrollOffset = 0;
      this.setting = setting;
      this.selectedBlocks = new HashSet();
      this.allBlocks = new ArrayList();
      this.blockColors = blockColors;
      class_7923.field_41175.forEach((blockx) -> {
         if (this.isValidBlock(blockx)) {
            this.allBlocks.add(blockx);
         }

      });
      Iterator var4 = setting.getBlocks().iterator();

      while(var4.hasNext()) {
         class_2248 block = (class_2248)var4.next();
         if (this.isValidBlock(block)) {
            this.selectedBlocks.add(block);
         }
      }

      this.filteredBlocks = new ArrayList(this.allBlocks);
   }

   private boolean isValidBlock(class_2248 block) {
      if (block == null) {
         return false;
      } else if (block != class_2246.field_10124 && block != class_2246.field_10543 && block != class_2246.field_10243) {
         if (block.method_8389() == null) {
            return false;
         } else {
            try {
               String registryName = class_7923.field_41175.method_10221(block).method_12832();
               return !registryName.contains(d242("FRwE"));
            } catch (Exception var3) {
               return false;
            }
         }
      } else {
         return false;
      }
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
      int panelWidth = true;
      int panelHeight = true;
      int panelX = (screenWidth - 580) / 2;
      int panelY = (screenHeight - (451 ^ 1)) / 2;
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getPanelBackgroundWithAlpha(240), (double)panelX, (double)panelY, (double)(panelX + 580), (double)(panelY + 450), 8.0D, 8.0D, 8.0D, 8.0D, 20.0D);
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getHeaderBackground(), (double)panelX, (double)panelY, (double)(panelX + 580), (double)(panelY + 30), 8.0D, 8.0D, 0.0D, 0.0D, 20.0D);
      drawContext.method_25294(panelX, panelY + 30, panelX + 580, panelY + (80 ^ 79), bi.getMainColor(255, 1).getRGB());
      String var10000 = String.valueOf(this.setting.getName());
      String headerText = "Select Blocks: " + var10000 + " (" + this.selectedBlocks.size() + " selected)";
      if (this.showingOnlySelected && this.colorEditMode) {
         headerText = d242("NxkfFBNZG1seEREc66H27KTg4u78qeP//63t4Pz+4A==");
      } else if (this.showingOnlySelected) {
         headerText = headerText + " - Showing Selected";
      }

      i.drawCenteredString(headerText, drawContext, panelX + 290, panelY + 8, ch.safeColor(245, 245, 245, 255).getRGB());
      int searchX = panelX + 20;
      int searchY = panelY + 50;
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getGridBackground(), (double)searchX, (double)searchY, (double)(searchX + (653 ^ 145)), (double)(searchY + 30), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);
      iq.renderRoundedOutline(drawContext, aq.getButtonBorder(), (double)searchX, (double)searchY, (double)(searchX + 540), (double)(searchY + (120 ^ 102)), 5.0D, 5.0D, 5.0D, 5.0D, 1.0D, 20.0D);
      String cursor = System.currentTimeMillis() % 1000L > 500L ? "|" : "";
      i.drawString("Search: " + this.searchQuery + cursor, drawContext, searchX + 10, searchY + 9, aq.getTextColor().getRGB());
      int gridX = panelX + 20;
      int gridY = searchY + (181 ^ 171) + (214 ^ 217);
      int gridHeight = (443 ^ 121) - (gridY - panelY) - (90 ^ 102);
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getGridBackground(), (double)gridX, (double)gridY, (double)(gridX + 540), (double)(gridY + gridHeight), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);
      int totalRows = (int)Math.ceil((double)this.filteredBlocks.size() / 11.0D);
      int maxScroll = Math.max(0, totalRows - (177 ^ 183));
      this.scrollOffset = Math.min(this.scrollOffset, maxScroll);
      int startIndex;
      int endIndex;
      int buttonY;
      if (totalRows > (5 ^ 3)) {
         startIndex = gridX + (591 ^ 83) - 6 - 5;
         endIndex = gridY + 5;
         int scrollbarHeight = gridHeight - 10;
         iq.renderRoundedQuad(drawContext.method_51448(), aq.getButtonBackgroundWithAlpha(150), (double)startIndex, (double)endIndex, (double)(startIndex + (118 ^ 112)), (double)(endIndex + scrollbarHeight), 3.0D, 3.0D, 3.0D, 3.0D, 20.0D);
         float scrollPercent = (float)this.scrollOffset / (float)maxScroll;
         float thumbHeight = Math.max(40.0F, (float)scrollbarHeight * (6.0F / (float)totalRows));
         buttonY = endIndex + (int)(((float)scrollbarHeight - thumbHeight) * scrollPercent);
         iq.renderRoundedQuad(drawContext.method_51448(), bi.getMainColor(255, 1), (double)startIndex, (double)buttonY, (double)(startIndex + 6), (double)((float)buttonY + thumbHeight), 3.0D, 3.0D, 3.0D, 3.0D, 20.0D);
      }

      startIndex = this.scrollOffset * 11;
      endIndex = Math.min(startIndex + Math.min(this.filteredBlocks.size(), 66), this.filteredBlocks.size());
      class_2248 hoveredBlock = null;
      int hoveredMouseX = 0;
      int hoveredMouseY = 0;

      int saveButtonX;
      int cancelButtonX;
      int blockX;
      int blockY;
      for(buttonY = startIndex; buttonY < endIndex; ++buttonY) {
         saveButtonX = (buttonY - startIndex) % 11;
         cancelButtonX = (buttonY - startIndex) / (123 ^ 112);
         blockX = gridX + 5 + saveButtonX * 48;
         blockY = gridY + 5 + cancelButtonX * 48;
         class_2248 block = (class_2248)this.filteredBlocks.get(buttonY);
         boolean isSelected = this.selectedBlocks.contains(block);
         Color blockBg;
         Color hoverColor;
         if (this.colorEditMode && isSelected) {
            hoverColor = (Color)this.blockColors.getOrDefault(block, this.generateDefaultColor(block));
            blockBg = ch.safeColor(hoverColor.getRed(), hoverColor.getGreen(), hoverColor.getBlue(), 4 ^ 96);
         } else if (isSelected) {
            blockBg = bi.getMainColor(100, 1);
         } else {
            blockBg = aq.getGridBackground();
         }

         iq.renderRoundedQuad(drawContext.method_51448(), blockBg, (double)blockX, (double)blockY, (double)(blockX + (34 ^ 10)), (double)(blockY + (224 ^ 200)), 4.0D, 4.0D, 4.0D, 4.0D, 20.0D);
         iq.drawItem(drawContext, new class_1799(block.method_8389()), blockX, blockY, 40.0F, 0);
         if (isSelected) {
            hoverColor = this.colorEditMode ? (Color)this.blockColors.getOrDefault(block, this.generateDefaultColor(block)) : bi.getMainColor(255, 1);
            iq.renderRoundedOutline(drawContext, hoverColor, (double)blockX, (double)blockY, (double)(blockX + 40), (double)(blockY + (97 ^ 73)), 4.0D, 4.0D, 4.0D, 4.0D, 2.0D, 20.0D);
         }

         if (scaledMouseX >= blockX && scaledMouseX <= blockX + 40 && scaledMouseY >= blockY && scaledMouseY <= blockY + (26 ^ 50)) {
            hoverColor = this.colorEditMode && isSelected ? ch.safeColor(255, 255, 255, 106 ^ 252) : bi.getMainColor(145 ^ 89, 1);
            iq.renderRoundedOutline(drawContext, hoverColor, (double)blockX, (double)blockY, (double)(blockX + 40), (double)(blockY + 40), 4.0D, 4.0D, 4.0D, 4.0D, 1.0D, 20.0D);
            hoveredBlock = block;
            hoveredMouseX = mouseX;
            hoveredMouseY = mouseY;
         }
      }

      if (this.filteredBlocks.isEmpty()) {
         String emptyMessage = this.showingOnlySelected ? d242("OhpWFRQWGRAPXQ0a7OTh9+Hh") : d242("OhpWFRQWGRAPXRgQ9e/m");
         i.drawCenteredString(emptyMessage, drawContext, gridX + 270, gridY + gridHeight / 2 - 10, ch.safeColor(150, 150, 150, 200).getRGB());
      }

      buttonY = panelY + (269 ^ 207) - 45;
      saveButtonX = panelX + (739 ^ 167) - 80 - 20;
      cancelButtonX = saveButtonX - (93 ^ 13) - (121 ^ 115);
      blockX = cancelButtonX - 80 - 10;
      blockY = blockX - (83 ^ 43) - 10;
      iq.renderRoundedQuad(drawContext.method_51448(), bi.getMainColor(255, 1), (double)saveButtonX, (double)buttonY, (double)(saveButtonX + 80), (double)(buttonY + (220 ^ 194)), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);
      i.drawCenteredString(d242("JxQAEg=="), drawContext, saveButtonX + (34 ^ 10), buttonY + 8, aq.getBrightTextColor().getRGB());
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getButtonBorder(), (double)cancelButtonX, (double)buttonY, (double)(cancelButtonX + 80), (double)(buttonY + (231 ^ 249)), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);
      i.drawCenteredString(d242("NxQYFB0V"), drawContext, cancelButtonX + (85 ^ 125), buttonY + 8, aq.getBrightTextColor().getRGB());
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getSecondaryAccent(), (double)blockX, (double)buttonY, (double)(blockX + (255 ^ 175)), (double)(buttonY + 30), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);
      i.drawCenteredString(d242("NxkTFgo="), drawContext, blockX + 40, buttonY + (136 ^ 128), aq.getBrightTextColor().getRGB());
      Color buttonColor;
      String buttonText;
      if (this.colorEditMode) {
         buttonColor = aq.getSecondaryAccent();
         buttonText = d242("MBoYElg8HhIIFBAY");
      } else if (this.showingOnlySelected) {
         buttonColor = aq.getPrimaryAccent();
         buttonText = d242("MREfA1g6FRcTDw0=");
      } else {
         buttonColor = aq.getHighlightGlow();
         buttonText = d242("Jx0ZAFgqHxcZHgoa5A==");
      }

      iq.renderRoundedQuad(drawContext.method_51448(), buttonColor, (double)blockY, (double)buttonY, (double)(blockY + (62 ^ 70)), (double)(buttonY + 30), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);
      i.drawCenteredString(buttonText, drawContext, blockY + (180 ^ 136), buttonY + (207 ^ 199), aq.getBrightTextColor().getRGB());
      int quickColorsButtonX = blockY - 110 - (110 ^ 100);
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getSecondaryAccent(), (double)quickColorsButtonX, (double)buttonY, (double)(quickColorsButtonX + 110), (double)(buttonY + 30), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);
      i.drawCenteredString(d242("JQAfFBNZORQQEgwM"), drawContext, quickColorsButtonX + (235 ^ 220), buttonY + 8, aq.getBrightTextColor().getRGB());
      if (hoveredBlock != null && !this.colorEditMode) {
         iq.scaledProjection();
         String blockName = this.getBlockName(hoveredBlock);
         drawContext.method_51438(this.field_22793, class_2561.method_43470(blockName), hoveredMouseX, hoveredMouseY);
         iq.unscaledProjection();
      } else if (hoveredBlock != null && this.colorEditMode && this.selectedBlocks.contains(hoveredBlock)) {
         iq.scaledProjection();
         drawContext.method_51438(this.field_22793, class_2561.method_43470("§eClick to edit color"), hoveredMouseX, hoveredMouseY);
         iq.unscaledProjection();
      }

      iq.scaledProjection();
   }

   public boolean method_25402(double mouseX, double mouseY, int button) {
      double scaledX = mouseX * class_310.method_1551().method_22683().method_4495();
      double scaledY = mouseY * class_310.method_1551().method_22683().method_4495();
      int screenWidth = this.parent.mc.method_22683().method_4480();
      int screenHeight = this.parent.mc.method_22683().method_4507();
      int panelX = (screenWidth - (618 ^ 46)) / 2;
      int panelY = (screenHeight - 450) / 2;
      int buttonY = panelY + 450 - (67 ^ 110);
      int saveButtonX = panelX + 580 - 80 - (225 ^ 245);
      int cancelButtonX = saveButtonX - 80 - 10;
      int clearButtonX = cancelButtonX - (253 ^ 173) - (168 ^ 162);
      int showSelectedButtonX = clearButtonX - 120 - (141 ^ 135);
      int quickColorsButtonX = showSelectedButtonX - (11 ^ 101) - 10;
      if (this.isInBounds(scaledX, scaledY, saveButtonX, buttonY, 117 ^ 37, 30)) {
         this.setting.getBlocks().clear();
         this.setting.getBlocks().addAll(this.selectedBlocks);
         Astralux.INSTANCE.getConfigManager().shutdown();
         this.parent.mc.method_1507(Astralux.INSTANCE.GUI);
         return true;
      } else if (this.isInBounds(scaledX, scaledY, cancelButtonX, buttonY, 57 ^ 105, 30)) {
         this.parent.mc.method_1507(Astralux.INSTANCE.GUI);
         return true;
      } else if (this.isInBounds(scaledX, scaledY, clearButtonX, buttonY, 80, 75 ^ 85)) {
         this.selectedBlocks.clear();
         return true;
      } else if (this.isInBounds(scaledX, scaledY, quickColorsButtonX, buttonY, 110, 135 ^ 153)) {
         if (!this.selectedBlocks.isEmpty()) {
            this.parent.mc.method_1507(new eg(this, this.selectedBlocks, this.blockColors));
         }

         return true;
      } else if (this.isInBounds(scaledX, scaledY, showSelectedButtonX, buttonY, 120, 30)) {
         if (this.colorEditMode) {
            this.colorEditMode = false;
         } else if (this.showingOnlySelected) {
            this.colorEditMode = true;
         } else {
            this.showingOnlySelected = true;
            this.updateFilteredBlocks();
         }

         return true;
      } else {
         int gridX = panelX + 20;
         int gridY = panelY + 50 + (195 ^ 221) + 15;
         int gridHeight = 450 - (gridY - panelY) - (255 ^ 195);
         if (this.isInBounds(scaledX, scaledY, gridX, gridY, 540, gridHeight)) {
            int startIndex = this.scrollOffset * 11;
            int col = (int)(scaledX - (double)gridX - 5.0D) / (22 ^ 38);
            if (col >= 0 && col < 11) {
               int row = (int)(scaledY - (double)gridY - 5.0D) / 48;
               int clickedIndex = startIndex + row * 11 + col;
               if (clickedIndex >= 0 && clickedIndex < this.filteredBlocks.size()) {
                  class_2248 clickedBlock = (class_2248)this.filteredBlocks.get(clickedIndex);
                  if (button == 1 && this.selectedBlocks.contains(clickedBlock)) {
                     this.parent.mc.method_1507(new q(this, clickedBlock, this.blockColors));
                     return true;
                  }

                  if (button == 0 && !this.colorEditMode) {
                     if (this.selectedBlocks.contains(clickedBlock)) {
                        this.selectedBlocks.remove(clickedBlock);
                        if (this.showingOnlySelected) {
                           this.updateFilteredBlocks();
                        }
                     } else {
                        this.selectedBlocks.add(clickedBlock);
                     }
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
      int gridX = panelX + (146 ^ 134);
      int gridY = panelY + 50 + 30 + 15;
      int gridHeight = 450 - (gridY - panelY) - 60;
      if (this.isInBounds(scaledX, scaledY, gridX, gridY, 759 ^ 235, gridHeight)) {
         int totalRows = (int)Math.ceil((double)this.filteredBlocks.size() / 11.0D);
         int maxScroll = Math.max(0, totalRows - (98 ^ 100));
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
      if (keyCode == (510 ^ 254)) {
         if (this.colorEditMode) {
            this.colorEditMode = false;
            return true;
         } else {
            this.setting.getBlocks().clear();
            this.setting.getBlocks().addAll(this.selectedBlocks);
            Astralux.INSTANCE.getConfigManager().shutdown();
            this.parent.mc.method_1507(Astralux.INSTANCE.GUI);
            return true;
         }
      } else if (keyCode == 259) {
         if (!this.searchQuery.isEmpty()) {
            this.searchQuery = this.searchQuery.substring(0, this.searchQuery.length() - 1);
            this.updateFilteredBlocks();
         }

         return true;
      } else if (keyCode == (279 ^ 22)) {
         this.setting.getBlocks().clear();
         this.setting.getBlocks().addAll(this.selectedBlocks);
         Astralux.INSTANCE.getConfigManager().shutdown();
         this.parent.mc.method_1507(Astralux.INSTANCE.GUI);
         return true;
      } else {
         return super.method_25404(keyCode, scanCode, modifiers);
      }
   }

   public boolean method_25400(char chr, int modifiers) {
      this.searchQuery = this.searchQuery + chr;
      this.updateFilteredBlocks();
      return true;
   }

   private void updateFilteredBlocks() {
      ArrayList baseList;
      if (!this.showingOnlySelected && !this.colorEditMode) {
         baseList = new ArrayList(this.allBlocks);
      } else {
         baseList = new ArrayList(this.selectedBlocks);
      }

      if (this.searchQuery.isEmpty()) {
         this.filteredBlocks = baseList;
      } else {
         this.filteredBlocks = (List)baseList.stream().filter((block) -> {
            return block.method_9518().getString().toLowerCase().contains(this.searchQuery.toLowerCase());
         }).collect(Collectors.toList());
      }

      this.scrollOffset = 0;
   }

   private boolean isInBounds(double x, double y, int bx, int by, int w, int h) {
      return x >= (double)bx && x <= (double)(bx + w) && y >= (double)by && y <= (double)(by + h);
   }

   private Color generateDefaultColor(class_2248 block) {
      int hash = block.hashCode();
      int r = Math.max((hash & 16711695 - 15) >> 16, 100);
      int g = Math.max((hash & '\uff00') >> 8, 108 ^ 8);
      int b = Math.max(hash & 255, 100);
      return ch.safeColor(r, g, b, 236 ^ 19);
   }

   private String getBlockName(class_2248 block) {
      try {
         String translationKey = block.method_9539();
         class_2561 translated = class_2561.method_43471(translationKey);
         String name = translated.getString();
         return !name.equals(translationKey) && !name.isEmpty() ? name : this.formatRegistryName(block);
      } catch (Exception var5) {
         return this.formatRegistryName(block);
      }
   }

   private String formatRegistryName(class_2248 block) {
      try {
         String registryName = class_7923.field_41175.method_10221(block).method_12832();
         return this.capitalizeWords(registryName.replace("_", " "));
      } catch (Exception var3) {
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

   public void method_25420(class_332 drawContext, int mouseX, int mouseY, float delta) {
   }

   public boolean method_25422() {
      return false;
   }

   // $FF: synthetic method
   private static String d242(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 10152 - 820 + var3 & 255);
      }

      return new String(var2);
   }
}
