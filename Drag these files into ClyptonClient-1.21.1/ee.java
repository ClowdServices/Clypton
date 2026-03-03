import client.astralux.Astralux;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1935;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_437;
import net.minecraft.class_7923;

class ee extends class_437 {
   private final dn setting;
   private String searchQuery;
   private final List<class_1792> allItems;
   private List<class_1792> filteredItems;
   private int scrollOffset;
   private final int ITEMS_PER_ROW = 11;
   private final int MAX_ROWS_VISIBLE = 6;
   private int selectedIndex;
   private final int ITEM_SIZE = 40;
   private final int ITEM_SPACING = 8;
   final fi this$0;

   public ee(fi this$0, dn setting) {
      super(class_2561.method_43473());
      this.this$0 = this$0;
      this.searchQuery = "";
      this.scrollOffset = 0;
      this.selectedIndex = -1;
      this.setting = setting;
      this.allItems = new ArrayList();
      class_7923.field_41178.forEach((item) -> {
         if (item != class_1802.field_8162) {
            this.allItems.add(item);
         }

      });
      this.filteredItems = new ArrayList(this.allItems);
      if (setting.getItem() != null && setting.getItem() != class_1802.field_8162) {
         for(int i = 0; i < this.filteredItems.size(); ++i) {
            if (this.filteredItems.get(i) == setting.getItem()) {
               this.selectedIndex = i;
               break;
            }
         }
      }

   }

   public void method_25394(class_332 drawContext, int n, int n2, float n3) {
      iq.unscaledProjection();
      int n4 = n * (int)class_310.method_1551().method_22683().method_4495();
      int n5 = n2 * (int)class_310.method_1551().method_22683().method_4495();
      super.method_25394(drawContext, n4, n5, n3);
      int width = this.this$0.mc.method_22683().method_4480();
      int height = this.this$0.mc.method_22683().method_4507();
      int a;
      if (gm.renderBackground.getValue()) {
         a = 0 ^ 180;
      } else {
         a = 0;
      }

      drawContext.method_25294(0, 0, width, height, ch.safeColor(0, 0, 0, a).getRGB());
      int n6 = (this.this$0.mc.method_22683().method_4480() - (701 ^ 249)) / 2;
      int n7 = (this.this$0.mc.method_22683().method_4507() - (474 ^ 24)) / 2;
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getPanelBackgroundWithAlpha(240), (double)n6, (double)n7, (double)(n6 + (638 ^ 58)), (double)(n7 + (372 ^ 182)), 8.0D, 8.0D, 8.0D, 8.0D, 20.0D);
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getHeaderBackground(), (double)n6, (double)n7, (double)(n6 + 580), (double)(n7 + (181 ^ 171)), 8.0D, 8.0D, 0.0D, 0.0D, 20.0D);
      drawContext.method_25294(n6, n7 + (205 ^ 211), n6 + 580, n7 + 31, bi.getMainColor(184 ^ 71, 1).getRGB());
      i.drawCenteredString("Select Item: " + String.valueOf(this.setting.getName()), drawContext, n6 + 290, n7 + 8, aq.getBrightTextColor().getRGB());
      int n8 = n6 + (200 ^ 220);
      int n9 = n7 + (39 ^ 21);
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getGridBackground(), (double)n8, (double)n9, (double)(n8 + 540), (double)(n9 + (0 ^ 30)), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);
      iq.renderRoundedOutline(drawContext, aq.getButtonBorder(), (double)n8, (double)n9, (double)(n8 + 540), (double)(n9 + 30), 5.0D, 5.0D, 5.0D, 5.0D, 1.0D, 20.0D);
      String searchQuery = this.searchQuery;
      String s;
      if (System.currentTimeMillis() % 1000L > 500L) {
         s = "|";
      } else {
         s = "";
      }

      i.drawString("Search: " + searchQuery + s, drawContext, n8 + 10, n9 + 9, aq.getTextColor().getRGB());
      int n10 = n6 + 20;
      int n11 = n9 + 30 + 15;
      int n12 = 450 - (n11 - n7) - (57 ^ 5);
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getGridBackground(), (double)n10, (double)n11, (double)(n10 + (525 ^ 17)), (double)(n11 + n12), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);
      double ceil = Math.ceil((double)this.filteredItems.size() / 11.0D);
      int max = Math.max(0, (int)ceil - 6);
      this.scrollOffset = Math.min(this.scrollOffset, max);
      int i;
      int n21;
      int n19;
      if ((int)ceil > 6) {
         i = n10 + (547 ^ 63) - (176 ^ 182) - 5;
         n21 = n11 + 5;
         n19 = n12 - 10;
         iq.renderRoundedQuad(drawContext.method_51448(), aq.getButtonBackgroundWithAlpha(150), (double)i, (double)n21, (double)(i + (184 ^ 190)), (double)(n21 + n19), 3.0D, 3.0D, 3.0D, 3.0D, 20.0D);
         float n16 = (float)this.scrollOffset / (float)max;
         float max2 = Math.max(40.0F, (float)n19 * (6.0F / (float)((int)ceil)));
         int n17 = n21 + (int)(((float)n19 - max2) * n16);
         iq.renderRoundedQuad(drawContext.method_51448(), bi.getMainColor(52 ^ 203, 1), (double)i, (double)n17, (double)(i + 6), (double)((float)n17 + max2), 3.0D, 3.0D, 3.0D, 3.0D, 20.0D);
      }

      int n20;
      for(n21 = i = this.scrollOffset * 11; i < Math.min(n21 + Math.min(this.filteredItems.size(), 66), this.filteredItems.size()); ++i) {
         n19 = n10 + 5 + (i - n21) % 11 * 48;
         n20 = n11 + 5 + (i - n21) / (123 ^ 112) * 48;
         Color mainColor;
         if (i == this.selectedIndex) {
            mainColor = bi.getMainColor(100, 1);
         } else {
            mainColor = aq.getGridBackground();
         }

         iq.renderRoundedQuad(drawContext.method_51448(), mainColor, (double)n19, (double)n20, (double)(n19 + 40), (double)(n20 + (96 ^ 72)), 4.0D, 4.0D, 4.0D, 4.0D, 20.0D);
         iq.drawItem(drawContext, new class_1799((class_1935)this.filteredItems.get(i)), n19, n20, 40.0F, 0);
         if (n4 >= n19 && n4 <= n19 + 40 && n5 >= n20 && n5 <= n20 + 40) {
            iq.renderRoundedOutline(drawContext, bi.getMainColor(200, 1), (double)n19, (double)n20, (double)(n19 + 40), (double)(n20 + 40), 4.0D, 4.0D, 4.0D, 4.0D, 1.0D, 20.0D);
         }
      }

      if (this.filteredItems.isEmpty()) {
         i.drawCenteredString(d174("XXs1f2N9dGk7enJrcUQ="), drawContext, n10 + (338 ^ 92), n11 + n12 / 2 - (156 ^ 150), ch.safeColor(189 ^ 43, 150, 150, 200).getRGB());
      }

      n21 = n7 + 450 - (219 ^ 246);
      n19 = n6 + 580 - (32 ^ 112) - 20;
      n20 = n19 - 80 - 10;
      int n24 = n20 - 80 - 10;
      iq.renderRoundedQuad(drawContext.method_51448(), bi.getMainColor(1 ^ 254, 1), (double)n19, (double)n21, (double)(n19 + 80), (double)(n21 + (155 ^ 133)), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);
      i.drawCenteredString(d174("QHVjcw=="), drawContext, n19 + 40, n21 + 8, aq.getBrightTextColor().getRGB());
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getButtonBorder(), (double)n20, (double)n21, (double)(n20 + 80), (double)(n21 + 30), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);
      i.drawCenteredString(d174("UHV7dXJ0"), drawContext, n20 + (53 ^ 29), n21 + 8, aq.getBrightTextColor().getRGB());
      iq.renderRoundedQuad(drawContext.method_51448(), aq.getSecondaryAccent(), (double)n24, (double)n21, (double)(n24 + 80), (double)(n21 + (55 ^ 41)), 5.0D, 5.0D, 5.0D, 5.0D, 20.0D);
      i.drawCenteredString(d174("QXFmc2M="), drawContext, n24 + 40, n21 + 8, aq.getBrightTextColor().getRGB());
      iq.scaledProjection();
   }

   public boolean method_25402(double n, double n2, int n3) {
      double n4 = n * class_310.method_1551().method_22683().method_4495();
      double n5 = n2 * class_310.method_1551().method_22683().method_4495();
      int n6 = (this.this$0.mc.method_22683().method_4480() - 600) / 2;
      int n7 = (this.this$0.mc.method_22683().method_4507() - 450) / 2;
      int n8 = n7 + (468 ^ 22) - (81 ^ 124);
      int n9 = n6 + (599 ^ 15) - 80 - 20;
      int n10 = n9 - 80 - 10;
      if (this.isInBounds(n4, n5, n9, n8, 80, 30)) {
         if (this.selectedIndex >= 0 && this.selectedIndex < this.filteredItems.size()) {
            this.setting.setItem((class_1792)this.filteredItems.get(this.selectedIndex));
         }

         this.this$0.mc.method_1507(Astralux.INSTANCE.GUI);
         return true;
      } else if (this.isInBounds(n4, n5, n10, n8, 80, 30)) {
         this.this$0.mc.method_1507(Astralux.INSTANCE.GUI);
         return true;
      } else {
         int i;
         if (!this.isInBounds(n4, n5, n10 - 80 - 10, n8, 80, 30)) {
            i = n6 + 20;
            int n12 = n7 + 50 + 30 + 15;
            if (this.isInBounds(n4, n5, i, n12, 560, (392 ^ 74) - (n12 - n7) - (28 ^ 32))) {
               int n13 = this.scrollOffset * (201 ^ 194);
               int n14 = (int)(n4 - (double)i - 5.0D) / 48;
               if (n14 >= 0 && n14 < 11) {
                  int selectedIndex = n13 + (int)(n5 - (double)n12 - 5.0D) / 48 * 11 + n14;
                  if (selectedIndex >= 0 && selectedIndex < this.filteredItems.size()) {
                     this.selectedIndex = selectedIndex;
                     return true;
                  }
               }
            }

            return super.method_25402(n4, n5, n3);
         } else {
            this.setting.setItem(this.setting.getDefaultValue());
            this.selectedIndex = -1;

            for(i = 0; i < this.filteredItems.size(); ++i) {
               if (this.filteredItems.get(i) == this.setting.getDefaultValue()) {
                  this.selectedIndex = i;
                  break;
               }
            }

            return true;
         }
      }
   }

   public boolean method_25401(double n, double n2, double n3, double n4) {
      double n5 = n * class_310.method_1551().method_22683().method_4495();
      double n6 = n2 * class_310.method_1551().method_22683().method_4495();
      int width = this.this$0.mc.method_22683().method_4480();
      int n7 = (this.this$0.mc.method_22683().method_4507() - (407 ^ 85)) / 2;
      int n8 = n7 + 50 + 30 + 15;
      if (this.isInBounds(n5, n6, (width - 600) / 2 + (79 ^ 91), n8, 560, 450 - (n8 - n7) - 60)) {
         int max = Math.max(0, (int)Math.ceil((double)this.filteredItems.size() / 11.0D) - (42 ^ 44));
         if (n4 > 0.0D) {
            this.scrollOffset = Math.max(0, this.scrollOffset - 1);
         } else if (n4 < 0.0D) {
            this.scrollOffset = Math.min(max, this.scrollOffset + 1);
         }

         return true;
      } else {
         return super.method_25401(n5, n6, n3, n4);
      }
   }

   public boolean method_25404(int n, int n2, int n3) {
      if (n == 256) {
         if (this.selectedIndex >= 0 && this.selectedIndex < this.filteredItems.size()) {
            this.setting.setItem((class_1792)this.filteredItems.get(this.selectedIndex));
         }

         this.this$0.mc.method_1507(Astralux.INSTANCE.GUI);
         return true;
      } else if (n == 259) {
         if (!this.searchQuery.isEmpty()) {
            this.searchQuery = this.searchQuery.substring(0, this.searchQuery.length() - 1);
            this.updateFilteredItems();
         }

         return true;
      } else if (n == 265) {
         if (this.selectedIndex >= (129 ^ 138)) {
            this.selectedIndex -= 11;
            this.ensureSelectedItemVisible();
         }

         return true;
      } else if (n == 264) {
         if (this.selectedIndex + 11 < this.filteredItems.size()) {
            this.selectedIndex += 150 ^ 157;
            this.ensureSelectedItemVisible();
         }

         return true;
      } else if (n == 263) {
         if (this.selectedIndex > 0) {
            --this.selectedIndex;
            this.ensureSelectedItemVisible();
         }

         return true;
      } else if (n == 262) {
         if (this.selectedIndex < this.filteredItems.size() - 1) {
            ++this.selectedIndex;
            this.ensureSelectedItemVisible();
         }

         return true;
      } else if (n == (339 ^ 82)) {
         if (this.selectedIndex >= 0 && this.selectedIndex < this.filteredItems.size()) {
            this.setting.setItem((class_1792)this.filteredItems.get(this.selectedIndex));
            this.this$0.mc.method_1507(Astralux.INSTANCE.GUI);
         }

         return true;
      } else {
         return super.method_25404(n, n2, n3);
      }
   }

   public boolean method_25400(char c, int n) {
      this.searchQuery = this.searchQuery + c;
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
      this.selectedIndex = -1;
      class_1792 a = this.setting.getItem();
      if (a != null) {
         for(int i = 0; i < this.filteredItems.size(); ++i) {
            if (this.filteredItems.get(i) == a) {
               this.selectedIndex = i;
               break;
            }
         }
      }

   }

   private void ensureSelectedItemVisible() {
      if (this.selectedIndex >= 0) {
         int scrollOffset = this.selectedIndex / 11;
         if (scrollOffset < this.scrollOffset) {
            this.scrollOffset = scrollOffset;
         } else if (scrollOffset >= this.scrollOffset + 6) {
            this.scrollOffset = scrollOffset - 6 + 1;
         }

      }
   }

   private boolean isInBounds(double n, double n2, int n3, int n4, int n5, int n6) {
      return n >= (double)n3 && n <= (double)(n3 + n5) && n2 >= (double)n4 && n2 <= (double)(n4 + n6);
   }

   public void method_25420(class_332 drawContext, int n, int n2, float n3) {
   }

   public boolean method_25422() {
      return false;
   }

   // $FF: synthetic method
   private static String d174(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 8211 + var3 & (193 ^ 62));
      }

      return new String(var2);
   }
}
