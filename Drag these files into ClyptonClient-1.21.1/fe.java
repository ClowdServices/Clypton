import client.astralux.Astralux;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_437;

public class fe extends class_437 {
   private final hf hudModule;
   private final class_310 mc;
   private final List<t> widgets = new ArrayList();
   public t currentDragging;
   private bb draggingElement;
   private int elementDragX;
   private int elementDragY;
   private static int controlPanelX = 10;
   private static int controlPanelY = 137 ^ 131;
   private static int stylePanelX = 10;
   private static int stylePanelY = 300;

   public fe() {
      super(class_2561.method_43470(d203("ChYAZQMjIT0lOQ==")));
      this.hudModule = (hf)Astralux.INSTANCE.getModuleManager().getModuleByClass(hf.class);
      this.mc = class_310.method_1551();
      this.loadPanelPositions();
      this.loadWidgets();
   }

   private void loadWidgets() {
      t controlPanel = new t(this, d203("By8hKCMpPDo="), controlPanelX, controlPanelY, 170 ^ 196, true);
      controlPanel.addButton(new cr(d203("DiwjKg=="), () -> {
         return this.hudModule.showLogo;
      }, (v) -> {
         this.hudModule.showLogo = v;
      }));
      controlPanel.addButton(new cr(d203("FSIwIDQqKTsh"), () -> {
         return this.hudModule.showWatermark;
      }, (v) -> {
         this.hudModule.showWatermark = v;
      }));
      controlPanel.addButton(new cr(d203("Cy0iKg=="), () -> {
         return this.hudModule.showInfo;
      }, (v) -> {
         this.hudModule.showInfo = v;
      }));
      controlPanel.addButton(new cr(d203("FiopIA=="), () -> {
         return this.hudModule.showTime;
      }, (v) -> {
         this.hudModule.showTime = v;
      }));
      controlPanel.addButton(new cr(d203("ASwrNyI0"), () -> {
         return this.hudModule.showCoordinates;
      }, (v) -> {
         this.hudModule.showCoordinates = v;
      }));
      controlPanel.addButton(new cr(d203("DywgMCoiOw=="), () -> {
         return this.hudModule.showModules;
      }, (v) -> {
         this.hudModule.showModules = v;
      }));
      controlPanel.addButton(new cr(d203("ECIgJDQ="), () -> {
         return this.hudModule.showRadar;
      }, (v) -> {
         this.hudModule.showRadar = v;
      }));
      controlPanel.addButton(new cr(d203("ByUiICUzOw=="), () -> {
         return this.hudModule.showEffects;
      }, (v) -> {
         this.hudModule.showEffects = v;
      }));
      controlPanel.addButton(new cr(d203("CiwwJyc1"), () -> {
         return this.hudModule.showHotbar;
      }, (v) -> {
         this.hudModule.showHotbar = v;
      }));
      controlPanel.addButton(new cr(d203("CSY9NjI1JyIvOA=="), () -> {
         return this.hudModule.showKeystrokes;
      }, (v) -> {
         this.hudModule.showKeystrokes = v;
      }));
      controlPanel.addButton(new cr(d203("ASwpNSc0Ow=="), () -> {
         return this.hudModule.showCompass;
      }, (v) -> {
         this.hudModule.showCompass = v;
      }));
      controlPanel.addButton(new cr(d203("ASwpJyczaBo+Kjg+"), () -> {
         return this.hudModule.showCombatStats;
      }, (v) -> {
         this.hudModule.showCombatStats = v;
      }));
      controlPanel.addButton(new cr(d203("AzEpKjRnDDw4ZQ=="), () -> {
         return this.hudModule.showArmorDurability;
      }, (v) -> {
         this.hudModule.showArmorDurability = v;
      }));
      controlPanel.addButton(new cr(d203("ECItKyQoPw=="), () -> {
         return this.hudModule.enableRainbow;
      }, (v) -> {
         this.hudModule.enableRainbow = v;
      }));
      controlPanel.addButton(new ij(d203("DTMlJi8zMQ=="), () -> {
         return this.hudModule.opacity;
      }, (v) -> {
         this.hudModule.opacity = v;
      }, 0.0F, 1.0F));
      this.widgets.add(controlPanel);
      t stylePanel = new t(this, d203("ETc9KSM="), stylePanelX, stylePanelY, 110, true);
      stylePanel.addButton(new ij(d203("DiwjKmYUITMv"), () -> {
         return (float)this.hudModule.logoSize;
      }, (v) -> {
         this.hudModule.logoSize = (int)v;
      }, 16.0F, 96.0F));
      stylePanel.addButton(new ij(d203("ECIgJDRnGyAwLg=="), () -> {
         return (float)this.hudModule.radarSize;
      }, (v) -> {
         this.hudModule.radarSize = (int)v;
      }, 80.0F, 180.0F));
      stylePanel.addButton(new ij(d203("ECIgJDRnGigkLCk="), () -> {
         return (float)this.hudModule.radarRange;
      }, (v) -> {
         this.hudModule.radarRange = (int)v;
      }, 16.0F, 96.0F));
      stylePanel.addButton(new ij(d203("ECItKyQoP2kZOykoKg=="), () -> {
         return this.hudModule.rainbowSpeed;
      }, (v) -> {
         this.hudModule.rainbowSpeed = v;
      }, 0.1F, 10.0F));
      stylePanel.addButton(new ij(d203("ASw2KyM1aBsrLyU4PQ=="), () -> {
         return this.hudModule.cornerRadius;
      }, (v) -> {
         this.hudModule.cornerRadius = v;
      }, 0.0F, 12.0F));
      this.widgets.add(stylePanel);
   }

   public void method_25394(class_332 ctx, int mouseX, int mouseY, float delta) {
      super.method_25394(ctx, mouseX, mouseY, delta);
      if (this.hudModule != null) {
         if (!this.hudModule.isEnabled()) {
            this.hudModule.setEnabled(true);
         }

         this.hudModule.saveConfig();
      }

      this.renderElementOutlines(ctx, mouseX, mouseY);
      Iterator var5 = this.widgets.iterator();

      while(var5.hasNext()) {
         t widget = (t)var5.next();
         widget.render(ctx, mouseX, mouseY, delta);
      }

   }

   private void renderElementOutlines(class_332 ctx, int mouseX, int mouseY) {
      bb[] elements = new bb[]{this.hudModule.showLogo ? bb.LOGO : null, this.hudModule.showWatermark ? bb.WATERMARK : null, this.hudModule.showInfo ? bb.INFO : null, this.hudModule.showTime ? bb.TIME : null, this.hudModule.showCoordinates ? bb.COORDINATES : null, this.hudModule.showModules ? bb.MODULE_LIST : null, this.hudModule.showRadar ? bb.RADAR : null, this.hudModule.showEffects ? bb.EFFECTS : null, this.hudModule.showHotbar ? bb.HOTBAR : null, this.hudModule.showKeystrokes ? bb.KEYSTROKES : null, this.hudModule.showCompass ? bb.COMPASS : null, this.hudModule.showCombatStats ? bb.COMBAT_STATS : null, this.hudModule.showArmorDurability ? bb.ARMOR_DURABILITY : null};
      bb[] var5 = elements;
      int var6 = elements.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         bb elem = var5[var7];
         if (elem != null) {
            int x = this.getElementX(elem);
            int y = this.getElementY(elem);
            int w = this.getElementWidth(elem);
            int h = this.getElementHeight(elem);
            boolean hovered = mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
            boolean selected = this.draggingElement == elem;
            if (hovered || selected) {
               Color outlineColor = selected ? ch.safeColor(140 ^ 251, 0, 192 ^ 63, 255) : ch.safeColor(100, 181, 214 ^ 32, 255);
               ctx.method_25294(x - 2, y - 2, x + w + 2, y, outlineColor.getRGB());
               ctx.method_25294(x - 2, y + h, x + w + 2, y + h + 2, outlineColor.getRGB());
               ctx.method_25294(x - 2, y - 2, x, y + h + 2, outlineColor.getRGB());
               ctx.method_25294(x + w, y - 2, x + w + 2, y + h + 2, outlineColor.getRGB());
               if (selected || hovered) {
                  String label = this.formatElementName(elem);
                  int lw = i.getWidth(label);
                  int lx = x + (w - lw) / 2;
                  int ly = y - (224 ^ 238);
                  ctx.method_25294(lx - 3, ly - 2, lx + lw + 3, ly + (246 ^ 252), ch.safeColor(0, 0, 0, 220).getRGB());
                  i.drawString(label, ctx, lx, ly, -1);
               }
            }
         }
      }

   }

   public boolean method_25402(double mouseX, double mouseY, int button) {
      int mx = (int)mouseX;
      int my = (int)mouseY;
      Iterator var8 = this.widgets.iterator();

      while(var8.hasNext()) {
         t widget = (t)var8.next();
         if (widget.mouseClicked(mx, my, button)) {
            return true;
         }
      }

      if (button == 0) {
         bb[] var10000 = new bb[7 ^ 10];
         var10000[0] = this.hudModule.showLogo ? bb.LOGO : null;
         var10000[1] = this.hudModule.showWatermark ? bb.WATERMARK : null;
         var10000[2] = this.hudModule.showInfo ? bb.INFO : null;
         var10000[3] = this.hudModule.showTime ? bb.TIME : null;
         var10000[4] = this.hudModule.showCoordinates ? bb.COORDINATES : null;
         var10000[5] = this.hudModule.showModules ? bb.MODULE_LIST : null;
         var10000[6] = this.hudModule.showRadar ? bb.RADAR : null;
         var10000[7] = this.hudModule.showEffects ? bb.EFFECTS : null;
         var10000[8] = this.hudModule.showHotbar ? bb.HOTBAR : null;
         var10000[9] = this.hudModule.showKeystrokes ? bb.KEYSTROKES : null;
         var10000[242 ^ 248] = this.hudModule.showCompass ? bb.COMPASS : null;
         var10000[11] = this.hudModule.showCombatStats ? bb.COMBAT_STATS : null;
         var10000[1 ^ 13] = this.hudModule.showArmorDurability ? bb.ARMOR_DURABILITY : null;
         bb[] elements = var10000;
         bb[] var18 = elements;
         int var10 = elements.length;

         for(int var11 = 0; var11 < var10; ++var11) {
            bb elem = var18[var11];
            if (elem != null) {
               int x = this.getElementX(elem);
               int y = this.getElementY(elem);
               int w = this.getElementWidth(elem);
               int h = this.getElementHeight(elem);
               if (mx >= x && mx <= x + w && my >= y && my <= y + h) {
                  this.draggingElement = elem;
                  this.elementDragX = mx - x;
                  this.elementDragY = my - y;
                  return true;
               }
            }
         }
      }

      return super.method_25402(mouseX, mouseY, button);
   }

   public boolean method_25406(double mouseX, double mouseY, int button) {
      this.currentDragging = null;
      this.draggingElement = null;
      int mx = (int)mouseX;
      int my = (int)mouseY;
      Iterator var8 = this.widgets.iterator();

      while(var8.hasNext()) {
         t widget = (t)var8.next();
         widget.mouseReleased(mx, my, button);
      }

      return super.method_25406(mouseX, mouseY, button);
   }

   public boolean method_25403(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
      int mx = (int)mouseX;
      int my = (int)mouseY;
      if (this.draggingElement != null) {
         int newX = mx - this.elementDragX;
         int newY = my - this.elementDragY;
         newX = Math.max(0, Math.min(this.field_22789 - this.getElementWidth(this.draggingElement), newX));
         newY = Math.max(0, Math.min(this.field_22790 - this.getElementHeight(this.draggingElement), newY));
         this.setElementPosition(this.draggingElement, newX, newY);
         return true;
      } else if (this.currentDragging != null) {
         this.currentDragging.onDrag(mx, my);
         return true;
      } else {
         return super.method_25403(mouseX, mouseY, button, deltaX, deltaY);
      }
   }

   public boolean method_25401(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
      int mx = (int)mouseX;
      int my = (int)mouseY;
      Iterator var11 = this.widgets.iterator();

      t widget;
      do {
         if (!var11.hasNext()) {
            return super.method_25401(mouseX, mouseY, horizontalAmount, verticalAmount);
         }

         widget = (t)var11.next();
      } while(!widget.isHovered(mx, my));

      widget.onScroll(verticalAmount);
      return true;
   }

   private int getElementX(bb e) {
      int scaleFactor = (int)this.mc.method_22683().method_4495();
      int unscaledScreenWidth = this.mc.method_22683().method_4480();
      int var10000;
      switch(e.ordinal()) {
      case 0:
         var10000 = this.hudModule.logoX;
         break;
      case 1:
         var10000 = this.hudModule.watermarkX;
         break;
      case 2:
         var10000 = this.hudModule.infoX;
         break;
      case 3:
         var10000 = this.hudModule.timeX;
         break;
      case 4:
         var10000 = this.hudModule.coordsX;
         break;
      case 5:
         var10000 = this.hudModule.moduleListX;
         break;
      case 6:
         var10000 = this.hudModule.radarX;
         break;
      case 7:
         var10000 = this.hudModule.effectsX;
         break;
      case 8:
         var10000 = this.hudModule.hotbarX;
         break;
      case 9:
         var10000 = this.hudModule.keystrokesX;
         break;
      case 10:
         var10000 = this.hudModule.compassX;
         break;
      case 11:
         var10000 = this.hudModule.combatStatsX;
         break;
      case 12:
         var10000 = this.hudModule.armorDurabilityX;
         break;
      default:
         throw new MatchException((String)null, (Throwable)null);
      }

      int baseX = var10000;
      return e == bb.MODULE_LIST && baseX > unscaledScreenWidth / 2 ? baseX / scaleFactor - this.getElementWidth(e) : baseX / scaleFactor;
   }

   private int getElementY(bb e) {
      int scaleFactor = (int)this.mc.method_22683().method_4495();
      int var10000;
      switch(e.ordinal()) {
      case 0:
         var10000 = this.hudModule.logoY / scaleFactor;
         break;
      case 1:
         var10000 = this.hudModule.watermarkY / scaleFactor;
         break;
      case 2:
         var10000 = this.hudModule.infoY / scaleFactor;
         break;
      case 3:
         var10000 = this.hudModule.timeY / scaleFactor;
         break;
      case 4:
         var10000 = this.hudModule.coordsY / scaleFactor;
         break;
      case 5:
         var10000 = this.hudModule.moduleListY / scaleFactor;
         break;
      case 6:
         var10000 = this.hudModule.radarY / scaleFactor;
         break;
      case 7:
         var10000 = this.hudModule.effectsY / scaleFactor;
         break;
      case 8:
         var10000 = this.hudModule.hotbarY / scaleFactor;
         break;
      case 9:
         var10000 = this.hudModule.keystrokesY / scaleFactor;
         break;
      case 10:
         var10000 = this.hudModule.compassY / scaleFactor;
         break;
      case 11:
         var10000 = this.hudModule.combatStatsY / scaleFactor;
         break;
      case 12:
         var10000 = this.hudModule.armorDurabilityY / scaleFactor;
         break;
      default:
         throw new MatchException((String)null, (Throwable)null);
      }

      return var10000;
   }

   private int getElementWidth(bb e) {
      if (e == bb.MODULE_LIST) {
         return this.getModuleListWidth();
      } else if (e == bb.EFFECTS) {
         return 141 ^ 15;
      } else {
         int var10000;
         switch(e.ordinal()) {
         case 0:
            var10000 = this.hudModule.logoSize;
            break;
         case 1:
            var10000 = i.getWidth(d203("AxAQFwcLHRE=")) + (27 ^ 5);
            break;
         case 2:
            var10000 = 140;
            break;
         case 3:
            var10000 = 90;
            break;
         case 4:
            var10000 = 144 ^ 232;
            break;
         case 5:
            var10000 = 141 ^ 1;
            break;
         case 6:
            var10000 = this.hudModule.radarSize;
            break;
         case 7:
            var10000 = 130;
            break;
         case 8:
            var10000 = 32 ^ 232;
            break;
         case 9:
            var10000 = 87;
            break;
         case 10:
            var10000 = 100;
            break;
         case 11:
            var10000 = 100;
            break;
         case 12:
            var10000 = 104;
            break;
         default:
            throw new MatchException((String)null, (Throwable)null);
         }

         return var10000;
      }
   }

   private int getElementHeight(bb e) {
      if (e == bb.EFFECTS) {
         return this.getEffectsHeight();
      } else {
         int var10000;
         switch(e.ordinal()) {
         case 0:
            var10000 = this.hudModule.logoSize;
            break;
         case 1:
            var10000 = 26;
            break;
         case 2:
            var10000 = 29 ^ 47;
            break;
         case 3:
            var10000 = 26;
            break;
         case 4:
            var10000 = 24;
            break;
         case 5:
            var10000 = this.getModuleListHeight();
            break;
         case 6:
            var10000 = this.hudModule.radarSize;
            break;
         case 7:
            var10000 = 28;
            break;
         case 8:
            var10000 = 51 ^ 41;
            break;
         case 9:
            var10000 = 115;
            break;
         case 10:
            var10000 = 24;
            break;
         case 11:
            var10000 = 42;
            break;
         case 12:
            var10000 = 28;
            break;
         default:
            throw new MatchException((String)null, (Throwable)null);
         }

         return var10000;
      }
   }

   private int getModuleListWidth() {
      int maxWidth = 60;

      try {
         hj moduleManager = Astralux.INSTANCE.getModuleManager();
         Iterator var3 = moduleManager.getEnabledModules().iterator();

         while(var3.hasNext()) {
            bf module = (bf)var3.next();
            int width = i.getWidth(module.getName()) + (84 ^ 64);
            if (width > maxWidth) {
               maxWidth = width;
            }
         }
      } catch (Exception var6) {
         maxWidth = 140;
      }

      return maxWidth;
   }

   private int getModuleListHeight() {
      boolean var1 = false;

      int enabledCount;
      try {
         hj moduleManager = Astralux.INSTANCE.getModuleManager();
         enabledCount = moduleManager.getEnabledModules().size();
      } catch (Exception var3) {
         enabledCount = 71 ^ 77;
      }

      return Math.max(2 ^ 20, enabledCount * 24);
   }

   private int getEffectsHeight() {
      if (this.mc.field_1724 == null) {
         return 28;
      } else {
         int effectCount = this.mc.field_1724.method_6026().size();
         return effectCount == 0 ? 89 ^ 69 : effectCount * 31;
      }
   }

   private void setElementPosition(bb e, int x, int y) {
      int scaleFactor = (int)this.mc.method_22683().method_4495();
      int unscaledScreenWidth = this.mc.method_22683().method_4480();
      int scaledX = x * scaleFactor;
      int scaledY = y * scaleFactor;
      if (e == bb.MODULE_LIST && scaledX > unscaledScreenWidth / 2) {
         scaledX += this.getElementWidth(e) * scaleFactor;
      }

      switch(e.ordinal()) {
      case 0:
         this.hudModule.logoX = scaledX;
         this.hudModule.logoY = scaledY;
         break;
      case 1:
         this.hudModule.watermarkX = scaledX;
         this.hudModule.watermarkY = scaledY;
         break;
      case 2:
         this.hudModule.infoX = scaledX;
         this.hudModule.infoY = scaledY;
         break;
      case 3:
         this.hudModule.timeX = scaledX;
         this.hudModule.timeY = scaledY;
         break;
      case 4:
         this.hudModule.coordsX = scaledX;
         this.hudModule.coordsY = scaledY;
         break;
      case 5:
         this.hudModule.moduleListX = scaledX;
         this.hudModule.moduleListY = scaledY;
         break;
      case 6:
         this.hudModule.radarX = scaledX;
         this.hudModule.radarY = scaledY;
         break;
      case 7:
         this.hudModule.effectsX = scaledX;
         this.hudModule.effectsY = scaledY;
         break;
      case 8:
         this.hudModule.hotbarX = scaledX;
         this.hudModule.hotbarY = scaledY;
         break;
      case 9:
         this.hudModule.keystrokesX = scaledX;
         this.hudModule.keystrokesY = scaledY;
         break;
      case 10:
         this.hudModule.compassX = scaledX;
         this.hudModule.compassY = scaledY;
         break;
      case 11:
         this.hudModule.combatStatsX = scaledX;
         this.hudModule.combatStatsY = scaledY;
         break;
      case 12:
         this.hudModule.armorDurabilityX = scaledX;
         this.hudModule.armorDurabilityY = scaledY;
      }

   }

   private String formatElementName(bb e) {
      String var10000;
      switch(e.ordinal()) {
      case 0:
         var10000 = d203("DiwjKg==");
         break;
      case 1:
         var10000 = d203("FSIwIDQqKTsh");
         break;
      case 2:
         var10000 = d203("Cy0iKg==");
         break;
      case 3:
         var10000 = d203("FiopIA==");
         break;
      case 4:
         var10000 = d203("ASwrNyIuJig+Lj8=");
         break;
      case 5:
         var10000 = d203("DywgMCoiOw==");
         break;
      case 6:
         var10000 = d203("ECIgJDQ=");
         break;
      case 7:
         var10000 = d203("ByUiICUzOw==");
         break;
      case 8:
         var10000 = d203("CiwwJyc1");
         break;
      case 9:
         var10000 = d203("CSY9NjI1JyIvOA==");
         break;
      case 10:
         var10000 = d203("ASwpNSc0Ow==");
         break;
      case 11:
         var10000 = d203("ASwpJyczaBo+Kjg+");
         break;
      case 12:
         var10000 = d203("AzEpKjRnDDw4Ki4kIiYkKA==");
         break;
      default:
         throw new MatchException((String)null, (Throwable)null);
      }

      return var10000;
   }

   public boolean method_25421() {
      return false;
   }

   public void method_25419() {
      this.savePanelPositions();
      if (this.hudModule != null) {
         this.hudModule.saveConfig();
      }

      super.method_25419();
   }

   private void savePanelPositions() {
      Astralux.INSTANCE.getConfigManager().set(d203("KjYgayUoJj04JCAdLyE1PQo="), controlPanelX);
      Astralux.INSTANCE.getConfigManager().set(d203("KjYgayUoJj04JCAdLyE1PQs="), controlPanelY);
      Astralux.INSTANCE.getConfigManager().set(d203("KjYgazUzMSUvGy0jKyMI"), stylePanelX);
      Astralux.INSTANCE.getConfigManager().set(d203("KjYgazUzMSUvGy0jKyMJ"), stylePanelY);
      Astralux.INSTANCE.getConfigManager().save();
   }

   private void loadPanelPositions() {
      controlPanelX = Astralux.INSTANCE.getConfigManager().getInt(d203("KjYgayUoJj04JCAdLyE1PQo="), 10);
      controlPanelY = Astralux.INSTANCE.getConfigManager().getInt(d203("KjYgayUoJj04JCAdLyE1PQs="), 252 ^ 246);
      stylePanelX = Astralux.INSTANCE.getConfigManager().getInt(d203("KjYgazUzMSUvGy0jKyMI"), 10);
      stylePanelY = Astralux.INSTANCE.getConfigManager().getInt(d203("KjYgazUzMSUvGy0jKyMJ"), 300);
   }

   // $FF: synthetic method
   private static String d203(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 9011 - 497 + var3 & (236 ^ 19));
      }

      return new String(var2);
   }
}
