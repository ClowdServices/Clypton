import client.astralux.Astralux;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.class_1799;
import net.minecraft.class_2248;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_437;
import net.minecraft.class_7923;

public class eg extends class_437 {
   private final class_437 parentScreen;
   private final Set<class_2248> selectedBlocks;
   private final Map<class_2248, Color> blockColors;
   private final List<class_2248> allBlocks;
   private List<class_2248> blockList;
   private String searchQuery = "";
   private int scrollOffset = 0;
   private static final int ITEM_HEIGHT = 45;
   private static final int MAX_VISIBLE_ITEMS = 9;

   public eg(class_437 parent, Set<class_2248> selectedBlocks, Map<class_2248, Color> blockColors) {
      super(class_2561.method_43473());
      this.parentScreen = parent;
      this.selectedBlocks = selectedBlocks;
      this.blockColors = blockColors;
      this.allBlocks = new ArrayList(selectedBlocks);
      this.blockList = new ArrayList(selectedBlocks);
   }

   public void method_25394(class_332 drawContext, int mouseX, int mouseY, float delta) {
      iq.unscaledProjection();
      int scaledMouseX = mouseX * (int)class_310.method_1551().method_22683().method_4495();
      int scaledMouseY = mouseY * (int)class_310.method_1551().method_22683().method_4495();
      super.method_25394(drawContext, scaledMouseX, scaledMouseY, delta);
      int screenWidth = class_310.method_1551().method_22683().method_4480();
      int screenHeight = class_310.method_1551().method_22683().method_4507();
      int bgAlpha = gm.renderBackground.getValue() ? 34 ^ 150 : 0;
      drawContext.method_25294(0, 0, screenWidth, screenHeight, ch.safeColor(0, 0, 0, bgAlpha).getRGB());
      int panelWidth = true;
      int panelHeight = true;
      int panelX = (screenWidth - 400) / 2;
      int panelY = (screenHeight - 550) / 2;
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getPanelBackgroundWithAlpha(240), (double)panelX, (double)panelY, (double)(panelX + 400), (double)(panelY + 550), 8.0D, 8.0D, 8.0D, 8.0D, 20.0D);
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getHeaderBackground(), (double)panelX, (double)panelY, (double)(panelX + 400), (double)(panelY + (92 ^ 66)), 8.0D, 8.0D, 0.0D, 0.0D, 20.0D);
      drawContext.method_25294(panelX, panelY + 30, panelX + 400, panelY + (227 ^ 252), bi.getMainColor(231 ^ 24, 1).getRGB());
      int var10000 = this.selectedBlocks.size();
      i.drawCenteredString("Block Colors - " + var10000 + " blocks", drawContext, panelX + 200, panelY + 8, aq.getBrightTextColor().getRGB());
      int searchX = panelX + 15;
      int searchY = panelY + 40;
      int searchWidth = 487 ^ 149;
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getGridBackground(), (double)searchX, (double)searchY, (double)(searchX + (378 ^ 8)), (double)(searchY + 25), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);
      iq.renderRoundedOutline(drawContext, aq.getButtonBorder(), (double)searchX, (double)searchY, (double)(searchX + 370), (double)(searchY + (213 ^ 204)), 5.0D, 5.0D, 5.0D, 5.0D, 1.0D, 20.0D);
      String cursor = System.currentTimeMillis() % 1000L > 500L ? "|" : "";
      i.drawString("Search: " + this.searchQuery + cursor, drawContext, searchX + (90 ^ 82), searchY + (30 ^ 25), aq.getTextColor().getRGB());
      int listX = panelX + 15;
      int listY = panelY + 75;
      int listWidth = true;
      int listHeight = true;
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getGridBackground(), (double)listX, (double)listY, (double)(listX + 370), (double)(listY + 415), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);
      int totalItems = this.blockList.size();
      int maxScroll = Math.max(0, totalItems - 9);
      this.scrollOffset = Math.min(this.scrollOffset, maxScroll);
      int contentWidth;
      int i;
      int itemIndex;
      if (totalItems > 9) {
         contentWidth = listX + 370 - 6 - 5;
         i = listY + 5;
         itemIndex = 347 ^ 206;
         iq.renderRoundedQuad(drawContext.method_51448(), aq.getButtonBackgroundWithAlpha(150), (double)contentWidth, (double)i, (double)(contentWidth + (39 ^ 33)), (double)(i + (297 ^ 188)), 3.0D, 3.0D, 3.0D, 3.0D, 20.0D);
         float scrollPercent = maxScroll > 0 ? (float)this.scrollOffset / (float)maxScroll : 0.0F;
         float thumbHeight = Math.max(40.0F, 405.0F * (9.0F / (float)totalItems));
         int thumbY = i + (int)((405.0F - thumbHeight) * scrollPercent);
         iq.renderRoundedQuad(drawContext.method_51448(), bi.getMainColor(255, 1), (double)contentWidth, (double)thumbY, (double)(contentWidth + 6), (double)((float)thumbY + thumbHeight), 3.0D, 3.0D, 3.0D, 3.0D, 20.0D);
      }

      contentWidth = 370 - (totalItems > (255 ^ 246) ? 15 : 5);

      for(i = 0; i < 9; ++i) {
         itemIndex = this.scrollOffset + i;
         if (itemIndex >= this.blockList.size()) {
            break;
         }

         int itemY = listY + 5 + i * 45;
         class_2248 block = (class_2248)this.blockList.get(itemIndex);
         Color blockColor = (Color)this.blockColors.getOrDefault(block, this.generateDefaultColor(block));
         iq.renderRoundedQuad(drawContext.method_51448(), aq.getButtonBackgroundWithAlpha(200), (double)(listX + 5), (double)itemY, (double)(listX + 5 + contentWidth), (double)(itemY + 45 - 5), 4.0D, 4.0D, 4.0D, 4.0D, 20.0D);
         iq.drawItem(drawContext, new class_1799(block.method_8389()), listX + (253 ^ 247), itemY + 2, 40.0F, 0);
         int textX = listX + (28 ^ 32);
         int textY = itemY + 5;
         String blockName = this.getBlockName(block);
         i.drawString(blockName, drawContext, textX, textY, aq.getTextColor().getRGB());
         int colorBoxX = listX + contentWidth - 35;
         int colorBoxY = itemY + 5;
         int colorBoxSize = 234 ^ 201;
         iq.renderRoundedQuad(drawContext.method_51448(), blockColor, (double)colorBoxX, (double)colorBoxY, (double)(colorBoxX + (190 ^ 157)), (double)(colorBoxY + 35), 3.0D, 3.0D, 3.0D, 3.0D, 20.0D);
         iq.renderRoundedOutline(drawContext, ch.safeColor(94 ^ 58, 100, 100, 140 ^ 68), (double)colorBoxX, (double)colorBoxY, (double)(colorBoxX + (119 ^ 84)), (double)(colorBoxY + 35), 3.0D, 3.0D, 3.0D, 3.0D, 1.0D, 20.0D);
         if (scaledMouseX >= colorBoxX && scaledMouseX <= colorBoxX + 35 && scaledMouseY >= colorBoxY && scaledMouseY <= colorBoxY + 35) {
            iq.renderRoundedOutline(drawContext, ch.safeColor(255, 255, 255, 160 ^ 95), (double)(colorBoxX - 2), (double)(colorBoxY - 2), (double)(colorBoxX + 35 + 2), (double)(colorBoxY + 35 + 2), 3.0D, 3.0D, 3.0D, 3.0D, 2.0D, 20.0D);
         }
      }

      i = panelY + 550 - 50;
      itemIndex = panelX + (44 ^ 35);
      int backButtonWidth = true;
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getButtonBorder(), (double)itemIndex, (double)i, (double)(itemIndex + 120), (double)(i + 35), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);
      i.drawCenteredString(d905("xuTl7A=="), drawContext, itemIndex + 60, i + 10, aq.getBrightTextColor().getRGB());
      iq.scaledProjection();
   }

   public boolean method_25402(double mouseX, double mouseY, int button) {
      double scaledX = mouseX * class_310.method_1551().method_22683().method_4495();
      double scaledY = mouseY * class_310.method_1551().method_22683().method_4495();
      int screenWidth = class_310.method_1551().method_22683().method_4480();
      int screenHeight = class_310.method_1551().method_22683().method_4507();
      int panelWidth = true;
      int panelHeight = 518 ^ 32;
      int panelX = (screenWidth - 400) / 2;
      int panelY = (screenHeight - (629 ^ 83)) / 2;
      int listX = panelX + 15;
      int listY = panelY + 75;
      int listWidth = 410 ^ 232;
      int contentWidth = (507 ^ 137) - (this.blockList.size() > 9 ? 15 : 5);
      int i;
      int itemIndex;
      if (scaledX >= (double)listX && scaledX <= (double)(listX + (467 ^ 161)) && scaledY >= (double)listY && scaledY <= (double)(listY + 550 - 160)) {
         for(i = 0; i < (1 ^ 8); ++i) {
            itemIndex = this.scrollOffset + i;
            if (itemIndex >= this.blockList.size()) {
               break;
            }

            int itemY = listY + 5 + i * 45;
            int colorBoxX = listX + contentWidth - 35;
            int colorBoxY = itemY + 5;
            int colorBoxSize = true;
            if (scaledX >= (double)colorBoxX && scaledX <= (double)(colorBoxX + (20 ^ 55)) && scaledY >= (double)colorBoxY && scaledY <= (double)(colorBoxY + 35)) {
               class_2248 block = (class_2248)this.blockList.get(itemIndex);
               class_310.method_1551().method_1507(new q(this, block, this.blockColors));
               return true;
            }
         }
      }

      i = panelX + 15;
      itemIndex = panelY + (754 ^ 212) - 40;
      if (scaledX >= (double)i && scaledX <= (double)(i + 120) && scaledY >= (double)itemIndex && scaledY <= (double)(itemIndex + 30)) {
         this.saveColors();
         class_310.method_1551().method_1507(this.parentScreen);
         return true;
      } else {
         return super.method_25402(scaledX, scaledY, button);
      }
   }

   public boolean method_25401(double mouseX, double mouseY, double horizAmount, double vertAmount) {
      double scaledX = mouseX * class_310.method_1551().method_22683().method_4495();
      double scaledY = mouseY * class_310.method_1551().method_22683().method_4495();
      int screenWidth = class_310.method_1551().method_22683().method_4480();
      int screenHeight = class_310.method_1551().method_22683().method_4507();
      int panelWidth = true;
      int panelHeight = true;
      int panelX = (screenWidth - 400) / 2;
      int panelY = (screenHeight - (623 ^ 73)) / 2;
      int listX = panelX + 15;
      int listY = panelY + (200 ^ 131);
      int listWidth = 485 ^ 151;
      int listHeight = true;
      if (scaledX >= (double)listX && scaledX <= (double)(listX + 370) && scaledY >= (double)listY && scaledY <= (double)(listY + (364 ^ 234))) {
         int maxScroll = Math.max(0, this.blockList.size() - 9);
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
         this.saveColors();
         class_310.method_1551().method_1507(this.parentScreen);
         return true;
      } else if (keyCode == (293 ^ 38)) {
         if (!this.searchQuery.isEmpty()) {
            this.searchQuery = this.searchQuery.substring(0, this.searchQuery.length() - 1);
            this.updateFilteredBlocks();
         }

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
      if (this.searchQuery.isEmpty()) {
         this.blockList = new ArrayList(this.allBlocks);
      } else {
         this.blockList = new ArrayList();
         Iterator var1 = this.allBlocks.iterator();

         while(var1.hasNext()) {
            class_2248 block = (class_2248)var1.next();
            String blockName = this.getBlockName(block).toLowerCase();
            if (blockName.contains(this.searchQuery.toLowerCase())) {
               this.blockList.add(block);
            }
         }
      }

      this.scrollOffset = 0;
   }

   private Color generateDefaultColor(class_2248 block) {
      int hash = block.hashCode();
      int r = Math.max((hash & 16711680) >> (109 ^ 125), 100);
      int g = Math.max((hash & 65885 - 605) >> (183 ^ 191), 100);
      int b = Math.max(hash & 255, 100);
      return ch.safeColor(r, g, b, 255);
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

   private void saveColors() {
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

   public void method_25420(class_332 drawContext, int mouseX, int mouseY, float delta) {
   }

   public boolean method_25422() {
      return false;
   }

   // $FF: synthetic method
   private static String d905(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 9673 - 69 + var3 & 255);
      }

      return new String(var2);
   }
}
