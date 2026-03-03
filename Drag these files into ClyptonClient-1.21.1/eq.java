import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_437;

public class eq extends class_437 {
   private final class_437 parentScreen;
   private final Map<String, Color> storageColors;
   private final List<String> storageTypes;
   private String searchQuery = "";
   private int scrollOffset = 0;
   private static final int ITEM_HEIGHT = 45;
   private static final int MAX_VISIBLE_ITEMS = 9;

   public eq(class_437 parent, Map<String, Color> storageColors) {
      super(class_2561.method_43473());
      this.parentScreen = parent;
      this.storageColors = storageColors;
      String[] var10003 = new String[]{d689("pq6iu70="), d689("sbSmuLmvr5OupqqjpQ=="), d689("oKijrbuVqKSovbs="), d689("p6e1uqym"), d689("tq6ypKKvuZOvobc="), d689("ram3uKy4"), d689("obSouLmvuQ=="), d689("oa+0uKykuKm/"), null, null, null, null, null, null, null};
      var10003[69 ^ 77] = d689("o7O1pqiprg==");
      var10003[9] = d689("tramv6evuQ==");
      var10003[10] = d689("oKikoKikv6WjqZCksLC/sQ==");
      var10003[54 ^ 61] = d689("p7Siv6CkrJO+uq6+tQ==");
      var10003[174 ^ 162] = d689("p6Omq6ak");
      var10003[13] = d689("qaOkvKy4pQ==");
      var10003[38 ^ 40] = d689("ta+0vKak");
      this.storageTypes = new ArrayList(Arrays.asList(var10003));
      Collections.sort(this.storageTypes);
   }

   public void method_25394(class_332 drawContext, int mouseX, int mouseY, float delta) {
      iq.unscaledProjection();
      int scaledMouseX = mouseX * (int)class_310.method_1551().method_22683().method_4495();
      int scaledMouseY = mouseY * (int)class_310.method_1551().method_22683().method_4495();
      super.method_25394(drawContext, scaledMouseX, scaledMouseY, delta);
      int screenWidth = class_310.method_1551().method_22683().method_4480();
      int screenHeight = class_310.method_1551().method_22683().method_4507();
      int bgAlpha = gm.renderBackground.getValue() ? 20 ^ 160 : 0;
      drawContext.method_25294(0, 0, screenWidth, screenHeight, ch.safeColor(0, 0, 0, bgAlpha).getRGB());
      int panelWidth = 303 ^ 191;
      int panelHeight = 712 ^ 238;
      int panelX = (screenWidth - 400) / 2;
      int panelY = (screenHeight - 550) / 2;
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getPanelBackgroundWithAlpha(240), (double)panelX, (double)panelY, (double)(panelX + 400), (double)(panelY + (601 ^ 127)), 8.0D, 8.0D, 8.0D, 8.0D, 20.0D);
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getHeaderBackground(), (double)panelX, (double)panelY, (double)(panelX + (491 ^ 123)), (double)(panelY + (85 ^ 75)), 8.0D, 8.0D, 0.0D, 0.0D, 20.0D);
      drawContext.method_25294(panelX, panelY + 30, panelX + 400, panelY + (202 ^ 213), bi.getMainColor(181 ^ 74, 1).getRGB());
      i.drawCenteredString(d689("lrKouqitruyInZ/wkr2/u6el"), drawContext, panelX + 200, panelY + 8, aq.getBrightTextColor().getRGB());
      int searchX = panelX + (247 ^ 248);
      int searchY = panelY + (8 ^ 32);
      int searchWidth = true;
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getGridBackground(), (double)searchX, (double)searchY, (double)(searchX + (416 ^ 210)), (double)(searchY + (247 ^ 238)), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);
      iq.renderRoundedOutline(drawContext, aq.getButtonBorder(), (double)searchX, (double)searchY, (double)(searchX + 370), (double)(searchY + 25), 5.0D, 5.0D, 5.0D, 5.0D, 1.0D, 20.0D);
      String cursor = System.currentTimeMillis() % 1000L > 500L ? "|" : "";
      i.drawString("Search: " + this.searchQuery + cursor, drawContext, searchX + 8, searchY + 7, aq.getTextColor().getRGB());
      int listX = panelX + (211 ^ 220);
      int listY = panelY + (115 ^ 56);
      int listWidth = true;
      int listHeight = true;
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getGridBackground(), (double)listX, (double)listY, (double)(listX + (490 ^ 152)), (double)(listY + 415), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);
      List<String> filteredTypes = this.getFilteredTypes();
      int totalItems = filteredTypes.size();
      int maxScroll = Math.max(0, totalItems - 9);
      this.scrollOffset = Math.min(this.scrollOffset, maxScroll);
      int contentWidth;
      int i;
      if (totalItems > 9) {
         contentWidth = listX + 370 - (211 ^ 213) - 5;
         i = listY + 5;
         int scrollbarHeight = true;
         iq.renderRoundedQuad(drawContext.method_51448(), aq.getButtonBackgroundWithAlpha(181 ^ 35), (double)contentWidth, (double)i, (double)(contentWidth + 6), (double)(i + 405), 3.0D, 3.0D, 3.0D, 3.0D, 20.0D);
         float scrollPercent = maxScroll > 0 ? (float)this.scrollOffset / (float)maxScroll : 0.0F;
         float thumbHeight = Math.max(40.0F, 405.0F * (9.0F / (float)totalItems));
         int thumbY = i + (int)((405.0F - thumbHeight) * scrollPercent);
         iq.renderRoundedQuad(drawContext.method_51448(), bi.getMainColor(255, 1), (double)contentWidth, (double)thumbY, (double)(contentWidth + 6), (double)((float)thumbY + thumbHeight), 3.0D, 3.0D, 3.0D, 3.0D, 20.0D);
      }

      contentWidth = 370 - (totalItems > (35 ^ 42) ? 15 : 5);

      int itemIndex;
      for(i = 0; i < 9; ++i) {
         itemIndex = this.scrollOffset + i;
         if (itemIndex >= filteredTypes.size()) {
            break;
         }

         int itemY = listY + 5 + i * 45;
         String storageType = (String)filteredTypes.get(itemIndex);
         Color storageColor = (Color)this.storageColors.getOrDefault(storageType, new Color(255, 255, 255));
         iq.renderRoundedQuad(drawContext.method_51448(), aq.getButtonBackgroundWithAlpha(248 ^ 48), (double)(listX + 5), (double)itemY, (double)(listX + 5 + contentWidth), (double)(itemY + 45 - 5), 4.0D, 4.0D, 4.0D, 4.0D, 20.0D);
         int textX = listX + (5 ^ 10);
         int textY = itemY + 5;
         String displayName = this.formatStorageTypeName(storageType);
         i.drawString(displayName, drawContext, textX, textY, aq.getTextColor().getRGB());
         String rgbText = String.format(d689("l4GF8unvr+Dt66v88fe3"), storageColor.getRed(), storageColor.getGreen(), storageColor.getBlue());
         i.drawString(rgbText, drawContext, textX, textY + 15, ch.safeColor(150, 150, 187 ^ 45, 255).getRGB());
         int colorBoxX = listX + contentWidth - (200 ^ 235);
         int colorBoxY = itemY + 5;
         int colorBoxSize = 9 ^ 42;
         iq.renderRoundedQuad(drawContext.method_51448(), storageColor, (double)colorBoxX, (double)colorBoxY, (double)(colorBoxX + 35), (double)(colorBoxY + (116 ^ 87)), 3.0D, 3.0D, 3.0D, 3.0D, 20.0D);
         iq.renderRoundedOutline(drawContext, ch.safeColor(230 ^ 130, 231 ^ 131, 100, 200), (double)colorBoxX, (double)colorBoxY, (double)(colorBoxX + 35), (double)(colorBoxY + 35), 3.0D, 3.0D, 3.0D, 3.0D, 1.0D, 20.0D);
         if (scaledMouseX >= colorBoxX && scaledMouseX <= colorBoxX + 35 && scaledMouseY >= colorBoxY && scaledMouseY <= colorBoxY + 35) {
            iq.renderRoundedOutline(drawContext, ch.safeColor(255, 255, 249 ^ 6, 255), (double)(colorBoxX - 2), (double)(colorBoxY - 2), (double)(colorBoxX + 35 + 2), (double)(colorBoxY + 35 + 2), 3.0D, 3.0D, 3.0D, 3.0D, 2.0D, 20.0D);
         }
      }

      i = panelY + 550 - (16 ^ 34);
      itemIndex = panelX + (75 ^ 68);
      int backButtonWidth = true;
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getButtonBorder(), (double)itemIndex, (double)i, (double)(itemIndex + 120), (double)(i + 35), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);
      i.drawCenteredString(d689("h6ekow=="), drawContext, itemIndex + 60, i + 10, aq.getBrightTextColor().getRGB());
      iq.scaledProjection();
   }

   public boolean method_25402(double mouseX, double mouseY, int button) {
      double scaledX = mouseX * class_310.method_1551().method_22683().method_4495();
      double scaledY = mouseY * class_310.method_1551().method_22683().method_4495();
      int screenWidth = class_310.method_1551().method_22683().method_4480();
      int screenHeight = class_310.method_1551().method_22683().method_4507();
      int panelWidth = 443 ^ 43;
      int panelHeight = true;
      int panelX = (screenWidth - (264 ^ 152)) / 2;
      int panelY = (screenHeight - (766 ^ 216)) / 2;
      int listX = panelX + 15;
      int listY = panelY + 75;
      int listWidth = true;
      List<String> filteredTypes = this.getFilteredTypes();
      int contentWidth = (289 ^ 83) - (filteredTypes.size() > 9 ? 15 : 5);
      int i;
      int itemIndex;
      if (scaledX >= (double)listX && scaledX <= (double)(listX + 370) && scaledY >= (double)listY && scaledY <= (double)(listY + (655 ^ 169) - 160)) {
         for(i = 0; i < (22 ^ 31); ++i) {
            itemIndex = this.scrollOffset + i;
            if (itemIndex >= filteredTypes.size()) {
               break;
            }

            int itemY = listY + 5 + i * 45;
            int colorBoxX = listX + contentWidth - 35;
            int colorBoxY = itemY + 5;
            int colorBoxSize = true;
            if (scaledX >= (double)colorBoxX && scaledX <= (double)(colorBoxX + 35) && scaledY >= (double)colorBoxY && scaledY <= (double)(colorBoxY + (43 ^ 8))) {
               String storageType = (String)filteredTypes.get(itemIndex);
               class_310.method_1551().method_1507(new ba(this, storageType, this.storageColors));
               return true;
            }
         }
      }

      i = panelX + (151 ^ 152);
      itemIndex = panelY + 550 - (174 ^ 134);
      if (scaledX >= (double)i && scaledX <= (double)(i + (233 ^ 145)) && scaledY >= (double)itemIndex && scaledY <= (double)(itemIndex + 30)) {
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
      int panelX = (screenWidth - (301 ^ 189)) / 2;
      int panelY = (screenHeight - 550) / 2;
      int listX = panelX + 15;
      int listY = panelY + 75;
      int listWidth = 401 ^ 227;
      int listHeight = true;
      if (scaledX >= (double)listX && scaledX <= (double)(listX + 370) && scaledY >= (double)listY && scaledY <= (double)(listY + 390)) {
         List<String> filteredTypes = this.getFilteredTypes();
         int maxScroll = Math.max(0, filteredTypes.size() - 9);
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
      if (keyCode == (451 ^ 195)) {
         this.saveColors();
         class_310.method_1551().method_1507(this.parentScreen);
         return true;
      } else if (keyCode == 259) {
         if (!this.searchQuery.isEmpty()) {
            this.searchQuery = this.searchQuery.substring(0, this.searchQuery.length() - 1);
            this.scrollOffset = 0;
         }

         return true;
      } else {
         return super.method_25404(keyCode, scanCode, modifiers);
      }
   }

   public boolean method_25400(char chr, int modifiers) {
      this.searchQuery = this.searchQuery + chr;
      this.scrollOffset = 0;
      return true;
   }

   private List<String> getFilteredTypes() {
      if (this.searchQuery.isEmpty()) {
         return new ArrayList(this.storageTypes);
      } else {
         List<String> filtered = new ArrayList();
         Iterator var2 = this.storageTypes.iterator();

         while(var2.hasNext()) {
            String type = (String)var2.next();
            String displayName = this.formatStorageTypeName(type).toLowerCase();
            if (displayName.contains(this.searchQuery.toLowerCase())) {
               filtered.add(type);
            }
         }

         return filtered;
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

   private void saveColors() {
   }

   public void method_25420(class_332 drawContext, int mouseX, int mouseY, float delta) {
   }

   public boolean method_25422() {
      return false;
   }

   // $FF: synthetic method
   private static String d689(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 8215 - 594 + var3 & (214 ^ 41));
      }

      return new String(var2);
   }
}
