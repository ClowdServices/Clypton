import client.astralux.Astralux;
import java.awt.Color;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_437;

public class v extends class_437 {
   private final List<cn> components = new ArrayList();
   private cn draggingComponent = null;
   private cn selectedComponent = null;
   private int dragOffsetX = 0;
   private int dragOffsetY = 0;
   private static final Color SNAP_LINE_COLOR = new Color(139, 92, 246, 200);
   private static final int SNAP_DISTANCE = 8;
   private static final int SIDEBAR_WIDTH = 220;
   private static final int SETTINGS_WIDTH = 240;
   private int actualScreenWidth;
   private int actualScreenHeight;
   private boolean showGrid = true;
   private float gridOpacity = 0.15F;
   private static final Color SIDEBAR_BG = new Color(15, 23, 42, 250);
   private static final Color PANEL_BG = new Color(97 ^ 127, 187 ^ 146, 59, 230);
   private static final Color PANEL_HOVER = new Color(51, 65, 85, 240);
   private static final Color PRIMARY = new Color(187 ^ 48, 52 ^ 104, 246);
   private static final Color PRIMARY_DARK = new Color(109, 97 ^ 73, 217);
   private static final Color SUCCESS = new Color(34, 111 ^ 170, 94);
   private static final Color DANGER = new Color(239, 68, 172 ^ 232);

   public v() {
      super(class_2561.method_43470(d229("EwkZfhoECBYMFg==")));
      class_310 mc = class_310.method_1551();
      this.actualScreenWidth = mc.method_22683().method_4480();
      this.actualScreenHeight = mc.method_22683().method_4507();
      this.initializeComponents();
   }

   private void initializeComponents() {
      hf hudModule = this.getHudModule();
      if (hudModule != null) {
         this.components.add(new cm(hudModule.logoX, hudModule.logoY, hudModule.logoSize + 2));
         this.components.add(new gr(hudModule.watermarkX, hudModule.watermarkY));
         this.components.add(new fv(hudModule.infoX, hudModule.infoY));
         this.components.add(new by(hudModule.timeX, hudModule.timeY));
         this.components.add(new di(hudModule.coordsX, hudModule.coordsY));
         this.components.add(new gc(hudModule.moduleListX, hudModule.moduleListY));
         this.components.add(new bc(hudModule.radarX, hudModule.radarY, hudModule.radarSize));
      } else {
         this.components.add(new cm(240, 60, 50));
         this.components.add(new gr(432 ^ 134, 119 ^ 75));
         this.components.add(new fv(240, 72 ^ 48));
         this.components.add(new by(this.actualScreenWidth / 2 - 50, 130 ^ 190));
         this.components.add(new di(240, this.actualScreenHeight - 60));
         this.components.add(new gc(this.actualScreenWidth - (24 ^ 232) - 180, 60));
         this.components.add(new bc(this.actualScreenWidth - 240 - (190 ^ 40), this.actualScreenHeight - (171 ^ 31), 120));
      }

   }

   protected void method_25426() {
      super.method_25426();
      this.actualScreenWidth = this.field_22787.method_22683().method_4480();
      this.actualScreenHeight = this.field_22787.method_22683().method_4507();
   }

   public void method_25394(class_332 context, int mouseX, int mouseY, float delta) {
      iq.unscaledProjection();
      double scaleFactor = this.field_22787.method_22683().method_4495();
      int unscaledMouseX = (int)((double)mouseX * scaleFactor);
      int unscaledMouseY = (int)((double)mouseY * scaleFactor);
      context.method_25294(0, 0, this.actualScreenWidth, this.actualScreenHeight, (new Color(17, 24, 39)).getRGB());
      if (this.showGrid) {
         this.renderGrid(context);
      }

      this.renderLeftSidebar(context, unscaledMouseX, unscaledMouseY);
      this.renderRightSidebar(context, unscaledMouseX, unscaledMouseY);
      this.renderTopBar(context);
      int canvasX = 220;
      int canvasWidth = this.actualScreenWidth - 220 - 240;
      iq.renderRoundedOutline(context, PRIMARY, (double)canvasX, 50.0D, (double)(canvasX + canvasWidth), (double)(this.actualScreenHeight - 10), 0.0D, 0.0D, 0.0D, 0.0D, 1.5D, 20.0D);
      this.drawSnapGuides(context);
      Iterator var11 = this.components.iterator();

      while(var11.hasNext()) {
         cn component = (cn)var11.next();
         if (this.shouldRenderComponent(component)) {
            component.render(context, unscaledMouseX, unscaledMouseY, component == this.draggingComponent, component == this.selectedComponent);
         }
      }

      iq.scaledProjection();
   }

   private void renderTopBar(class_332 context) {
      context.method_25294(0, 0, this.actualScreenWidth, 45, SIDEBAR_BG.getRGB());
      String title = d229("EwkZfhokKDYsNg==");
      int titleWidth = i.getWidth(title);
      int titleX = this.actualScreenWidth / 2 - titleWidth / 2;
      i.drawString(title, context, titleX, 169 ^ 165, PRIMARY.getRGB());
      String subtitle = "Drag elements to reposition • Click to select";
      int subtitleWidth = i.getWidth(subtitle);
      int subtitleX = this.actualScreenWidth / 2 - subtitleWidth / 2;
      i.drawString(subtitle, context, subtitleX, 238 ^ 242, ch.safeColor(156, 163, 26 ^ 181, 255).getRGB());
      context.method_25294(0, 37 ^ 9, this.actualScreenWidth, 45, PRIMARY.getRGB());
   }

   private void renderLeftSidebar(class_332 context, int mouseX, int mouseY) {
      context.method_25294(0, 45, 97 ^ 189, this.actualScreenHeight, SIDEBAR_BG.getRGB());
      int y = 55;
      i.drawString(d229("HhAYExouNTE="), context, 12, y, PRIMARY.getRGB());
      int y = y + 20;

      for(Iterator var5 = this.components.iterator(); var5.hasNext(); y += 42) {
         cn component = (cn)var5.next();
         boolean isHovered = mouseX >= 8 && mouseX <= 212 && mouseY >= y && mouseY <= y + (219 ^ 255);
         boolean isSelected = component == this.selectedComponent;
         Color bgColor = isSelected ? PRIMARY_DARK : (isHovered ? PANEL_HOVER : PANEL_BG);
         iq.renderRoundedQuad(context.method_51448(), bgColor, 8.0D, (double)y, 212.0D, (double)(y + 36), 6.0D, 6.0D, 6.0D, 6.0D, 20.0D);
         i.drawString(component.name, context, 16, y + 6, isSelected ? Color.WHITE.getRGB() : ch.safeColor(226, 232, 240, 113 ^ 142).getRGB());
         String posInfo = String.format(d229("A2Z4On85W0cH"), component.x, component.y);
         i.drawString(posInfo, context, 71 ^ 87, y + 20, ch.safeColor(203 ^ 95, 163, 88 ^ 224, 32 ^ 223).getRGB());
         Color indicatorColor = component.enabled ? SUCCESS : DANGER;
         context.method_25294(200, y + (91 ^ 85), 31 ^ 207, y + (11 ^ 29), indicatorColor.getRGB());
      }

      y = this.actualScreenHeight - 100;
      boolean gridHovered = mouseX >= 8 && mouseX <= 212 && mouseY >= y && mouseY <= y + 32;
      Color gridBtnColor = this.showGrid ? PRIMARY : (gridHovered ? PANEL_HOVER : PANEL_BG);
      iq.renderRoundedQuad(context.method_51448(), gridBtnColor, 8.0D, (double)y, 212.0D, (double)(y + 32), 6.0D, 6.0D, 6.0D, 6.0D, 20.0D);
      String gridText = this.showGrid ? d229("EzU5O38nEwsH") : d229("CDQyKX8nEwsH");
      int gridTextWidth = i.getWidth(gridText);
      i.drawString(gridText, context, (0 ^ 110) - gridTextWidth / 2, y + 10, Color.WHITE.getRGB());
      y += 38;
      boolean resetHovered = mouseX >= 8 && mouseX <= (123 ^ 175) && mouseY >= y && mouseY <= y + (197 ^ 229);
      Color resetBtnColor = resetHovered ? new Color(220, 38, 38) : PANEL_BG;
      iq.renderRoundedQuad(context.method_51448(), resetBtnColor, 8.0D, (double)y, 212.0D, (double)(y + 32), 6.0D, 6.0D, 6.0D, 6.0D, 20.0D);
      String resetText = d229("CTkuOytAIA4P");
      int resetTextWidth = i.getWidth(resetText);
      i.drawString(resetText, context, 110 - resetTextWidth / 2, y + (153 ^ 147), Color.WHITE.getRGB());
      context.method_25294(219, 196 ^ 233, 34 ^ 254, this.actualScreenHeight, ch.safeColor(98 ^ 37, 85, 105, 100).getRGB());
   }

   private void renderRightSidebar(class_332 context, int mouseX, int mouseY) {
      int sidebarX = this.actualScreenWidth - 240;
      context.method_25294(sidebarX, 42 ^ 7, this.actualScreenWidth, this.actualScreenHeight, SIDEBAR_BG.getRGB());
      int y = 55;
      i.drawString(d229("CBkJChYuJjE="), context, sidebarX + (126 ^ 114), y, PRIMARY.getRGB());
      int y = y + 25;
      int handleX;
      if (this.selectedComponent != null) {
         iq.renderRoundedQuad(context.method_51448(), PANEL_BG, (double)(sidebarX + (247 ^ 255)), (double)y, (double)(this.actualScreenWidth - 8), (double)(y + (253 ^ 173)), 6.0D, 6.0D, 6.0D, 6.0D, 20.0D);
         i.drawString(d229("CDkxOzwUBAZZ"), context, sidebarX + 16, y + 8, ch.safeColor(0 ^ 156, 163, 175, 116 ^ 139).getRGB());
         i.drawString(this.selectedComponent.name, context, sidebarX + 16, y + (130 ^ 148), PRIMARY.getRGB());
         i.drawString(String.format(d229("CzMuNysJDgxZREACS0hMDg=="), this.selectedComponent.x, this.selectedComponent.y), context, sidebarX + 16, y + 38, Color.WHITE.getRGB());
         i.drawString(String.format(d229("CDUnO2VARAYbQQE="), this.selectedComponent.width, this.selectedComponent.height), context, sidebarX + 16, y + 52, Color.WHITE.getRGB());
         y += 90;
         i.drawString(d229("Cik0PTRAIAEXDQoIFFI="), context, sidebarX + 12, y, ch.safeColor(123 ^ 231, 163, 175, 255).getRGB());
         y += 20;
         this.renderActionButton(context, d229("GDkzKjoSQTo="), sidebarX + (4 ^ 12), y, 224, 28, mouseX, mouseY);
         y += 34;
         this.renderActionButton(context, d229("GDkzKjoSQTs="), sidebarX + 8, y, 224, 28, mouseX, mouseY);
         y += 34;
         this.renderActionButton(context, d229("CTkuOytAMQ0QDREPCAY="), sidebarX + 8, y, 224, 28, mouseX, mouseY);
         y += 40;
      } else {
         iq.renderRoundedQuad(context.method_51448(), PANEL_BG, (double)(sidebarX + 8), (double)y, (double)(this.actualScreenWidth - 8), (double)(y + 60), 6.0D, 6.0D, 6.0D, 6.0D, 20.0D);
         String noSelText = d229("FTN9OzMFDAcNEEUVAgQMCR8JCQ==");
         handleX = i.getWidth(noSelText);
         i.drawString(noSelText, context, sidebarX + (67 ^ 59) - handleX / 2, y + 20, ch.safeColor(148, 163, 138 ^ 50, 104 ^ 151).getRGB());
         String clickText = d229("GDA0PTRAAAxDAQkDCg0HHg==");
         int clickWidth = i.getWidth(clickText);
         i.drawString(clickText, context, sidebarX + 120 - clickWidth / 2, y + 36, ch.safeColor(100, 116, 139, 255).getRGB());
         y += 70;
      }

      i.drawString(d229("HzUuLjMBGFg="), context, sidebarX + 12, y, ch.safeColor(156, 163, 110 ^ 193, 255).getRGB());
      y += 20;
      i.drawString(String.format(d229("HC40OmVARExTAkBD"), this.gridOpacity * 100.0F), context, sidebarX + 16, y, Color.WHITE.getRGB());
      y += 18;
      context.method_25294(sidebarX + (120 ^ 104), y, this.actualScreenWidth - (17 ^ 9), y + 4, ch.safeColor(71, 181 ^ 224, 105, 188 ^ 67).getRGB());
      int sliderWidth = (int)(200.0F * this.gridOpacity);
      context.method_25294(sidebarX + (115 ^ 99), y, sidebarX + 16 + sliderWidth, y + 4, PRIMARY.getRGB());
      handleX = sidebarX + 16 + sliderWidth;
      context.method_25294(handleX - 3, y - 4, handleX + 3, y + (22 ^ 30), Color.WHITE.getRGB());
      y += 30;
      y = this.actualScreenHeight - (96 ^ 82);
      boolean saveHovered = mouseX >= sidebarX + 8 && mouseX <= this.actualScreenWidth - (158 ^ 150) && mouseY >= y && mouseY <= y + 36;
      Color saveBtnColor = saveHovered ? new Color(34, 197, 94) : PRIMARY;
      iq.renderRoundedQuad(context.method_51448(), saveBtnColor, (double)(sidebarX + (199 ^ 207)), (double)y, (double)(this.actualScreenWidth - 8), (double)(y + 36), 6.0D, 6.0D, 6.0D, 6.0D, 20.0D);
      String saveText = d229("CD0rO39GQScbDRE=");
      int saveTextWidth = i.getWidth(saveText);
      i.drawString(saveText, context, sidebarX + 120 - saveTextWidth / 2, y + (176 ^ 188), Color.WHITE.getRGB());
      context.method_25294(sidebarX, 45, sidebarX + 1, this.actualScreenHeight, ch.safeColor(71, 85, 105, 3 ^ 103).getRGB());
   }

   private void renderActionButton(class_332 context, String text, int x, int y, int width, int height, int mouseX, int mouseY) {
      boolean hovered = mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
      Color btnColor = hovered ? PRIMARY : PANEL_BG;
      iq.renderRoundedQuad(context.method_51448(), btnColor, (double)x, (double)y, (double)(x + width), (double)(y + height), 6.0D, 6.0D, 6.0D, 6.0D, 20.0D);
      int textWidth = i.getWidth(text);
      i.drawString(text, context, x + width / 2 - textWidth / 2, y + height / 2 - 5, Color.WHITE.getRGB());
   }

   private void renderGrid(class_332 context) {
      int canvasX = 109 ^ 177;
      int canvasWidth = this.actualScreenWidth - 220 - 240;
      Color gridColor = ch.safeColor(100, 116, 156 ^ 23, (int)(this.gridOpacity * 100.0F));
      int spacing = 40;

      int y;
      for(y = canvasX; y < canvasX + canvasWidth; y += spacing) {
         context.method_25294(y, 50, y + 1, this.actualScreenHeight - 10, gridColor.getRGB());
      }

      for(y = 50; y < this.actualScreenHeight - (161 ^ 171); y += spacing) {
         context.method_25294(canvasX, y, canvasX + canvasWidth, y + 1, gridColor.getRGB());
      }

      Color centerColor = ch.safeColor(139, 92, 24 ^ 238, (int)(this.gridOpacity * 180.0F));
      int centerX = canvasX + canvasWidth / 2;
      int centerY = this.actualScreenHeight / 2;
      context.method_25294(centerX - 1, 50, centerX + 1, this.actualScreenHeight - 10, centerColor.getRGB());
      context.method_25294(canvasX, centerY - 1, canvasX + canvasWidth, centerY + 1, centerColor.getRGB());
   }

   private boolean shouldRenderComponent(cn component) {
      hf hudModule = this.getHudModule();
      if (hudModule == null) {
         return true;
      } else if (component instanceof cm) {
         return hudModule.showLogo;
      } else if (component instanceof gr) {
         return hudModule.showWatermark;
      } else if (component instanceof fv) {
         return hudModule.showInfo;
      } else if (component instanceof by) {
         return hudModule.showTime;
      } else if (component instanceof di) {
         return hudModule.showCoordinates;
      } else if (component instanceof gc) {
         return hudModule.showModules;
      } else {
         return component instanceof bc ? hudModule.showRadar : true;
      }
   }

   private void drawSnapGuides(class_332 context) {
      if (this.draggingComponent != null) {
         int canvasX = 220;
         int canvasWidth = this.actualScreenWidth - 220 - 240;
         int centerX = canvasX + canvasWidth / 2;
         int centerY = this.actualScreenHeight / 2;
         int compCenterX = this.draggingComponent.x + this.draggingComponent.width / 2;
         int compCenterY = this.draggingComponent.y + this.draggingComponent.height / 2;
         if (Math.abs(compCenterX - centerX) < (79 ^ 71)) {
            context.method_25294(centerX - 1, 50, centerX + 1, this.actualScreenHeight - (48 ^ 58), SNAP_LINE_COLOR.getRGB());
         }

         if (Math.abs(compCenterY - centerY) < (233 ^ 225)) {
            context.method_25294(canvasX, centerY - 1, canvasX + canvasWidth, centerY + 1, SNAP_LINE_COLOR.getRGB());
         }

         if (this.draggingComponent.x < canvasX + 8) {
            context.method_25294(canvasX, 50, canvasX + 2, this.actualScreenHeight - (97 ^ 107), SNAP_LINE_COLOR.getRGB());
         }

         if (this.draggingComponent.x + this.draggingComponent.width > canvasX + canvasWidth - (110 ^ 102)) {
            context.method_25294(canvasX + canvasWidth - 2, 50, canvasX + canvasWidth, this.actualScreenHeight - (214 ^ 220), SNAP_LINE_COLOR.getRGB());
         }

      }
   }

   public boolean method_25402(double mouseX, double mouseY, int button) {
      double scaleFactor = this.field_22787.method_22683().method_4495();
      int unscaledX = (int)(mouseX * scaleFactor);
      int unscaledY = (int)(mouseY * scaleFactor);
      if (unscaledX < 220) {
         this.handleLeftSidebarClick(unscaledX, unscaledY);
         return true;
      } else if (unscaledX > this.actualScreenWidth - 240) {
         this.handleRightSidebarClick(unscaledX, unscaledY);
         return true;
      } else {
         if (button == 0) {
            Iterator var10 = this.components.iterator();

            while(var10.hasNext()) {
               cn component = (cn)var10.next();
               if (this.shouldRenderComponent(component) && component.isHovered(unscaledX, unscaledY)) {
                  this.draggingComponent = component;
                  this.selectedComponent = component;
                  this.dragOffsetX = unscaledX - component.x;
                  this.dragOffsetY = unscaledY - component.y;
                  return true;
               }
            }

            this.selectedComponent = null;
         }

         return super.method_25402(mouseX, mouseY, button);
      }
   }

   private void handleLeftSidebarClick(int mouseX, int mouseY) {
      int y = 218 ^ 145;

      for(Iterator var4 = this.components.iterator(); var4.hasNext(); y += 42) {
         cn component = (cn)var4.next();
         if (mouseX >= (112 ^ 120) && mouseX <= 212 && mouseY >= y && mouseY <= y + 36) {
            this.selectedComponent = component;
            return;
         }
      }

      y = this.actualScreenHeight - 100;
      if (mouseX >= 8 && mouseX <= 212 && mouseY >= y && mouseY <= y + 32) {
         this.showGrid = !this.showGrid;
      } else {
         y += 38;
         if (mouseX >= (141 ^ 133) && mouseX <= 212 && mouseY >= y && mouseY <= y + (122 ^ 90)) {
            this.resetPositions();
         }

      }
   }

   private void handleRightSidebarClick(int mouseX, int mouseY) {
      int sidebarX = this.actualScreenWidth - 240;
      int saveY = this.actualScreenHeight - 50;
      if (mouseX >= sidebarX + (14 ^ 6) && mouseX <= this.actualScreenWidth - (78 ^ 70) && mouseY >= saveY && mouseY <= saveY + 36) {
         this.method_25419();
      } else {
         if (this.selectedComponent != null) {
            int y = 72 ^ 237;
            if (mouseX >= sidebarX + (174 ^ 166) && mouseX <= this.actualScreenWidth - 8 && mouseY >= y && mouseY <= y + 28) {
               int canvasX = 220;
               int canvasWidth = this.actualScreenWidth - 220 - 240;
               this.selectedComponent.x = canvasX + canvasWidth / 2 - this.selectedComponent.width / 2;
               return;
            }

            y += 34;
            if (mouseX >= sidebarX + 8 && mouseX <= this.actualScreenWidth - 8 && mouseY >= y && mouseY <= y + 28) {
               this.selectedComponent.y = this.actualScreenHeight / 2 - this.selectedComponent.height / 2;
               return;
            }

            y += 34;
            if (mouseX >= sidebarX + 8 && mouseX <= this.actualScreenWidth - 8 && mouseY >= y && mouseY <= y + (223 ^ 195)) {
               this.resetComponentPosition(this.selectedComponent);
            }
         }

      }
   }

   private void resetPositions() {
      int canvasX = 75 ^ 151;
      this.components.clear();
      this.components.add(new cm(canvasX + (116 ^ 96), 60, 50));
      this.components.add(new gr(canvasX + (11 ^ 81), 84 ^ 104));
      this.components.add(new fv(canvasX + 20, 120));
      this.components.add(new by(this.actualScreenWidth / 2 - 50, 60));
      this.components.add(new di(canvasX + (70 ^ 82), this.actualScreenHeight - (10 ^ 54)));
      this.components.add(new gc(this.actualScreenWidth - 240 - 180, 60));
      this.components.add(new bc(this.actualScreenWidth - 240 - 150, this.actualScreenHeight - 180, 120));
      this.selectedComponent = null;
   }

   private void resetComponentPosition(cn component) {
      int canvasX = 220;
      if (component instanceof cm) {
         component.x = canvasX + (254 ^ 234);
      } else if (component instanceof gr) {
         component.x = canvasX + 90;
      } else if (component instanceof fv) {
         component.x = canvasX + 20;
      } else if (component instanceof by) {
         component.x = this.actualScreenWidth / 2 - (195 ^ 241);
      } else if (component instanceof di) {
         component.x = canvasX + (69 ^ 81);
      } else if (component instanceof gc) {
         component.x = this.actualScreenWidth - 240 - 180;
      } else if (component instanceof bc) {
         component.x = this.actualScreenWidth - (150 ^ 102) - 150;
      }

   }

   public boolean method_25403(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
      if (this.draggingComponent != null) {
         double scaleFactor = this.field_22787.method_22683().method_4495();
         int unscaledX = (int)(mouseX * scaleFactor);
         int unscaledY = (int)(mouseY * scaleFactor);
         int newX = unscaledX - this.dragOffsetX;
         int newY = unscaledY - this.dragOffsetY;
         int canvasX = 52 ^ 232;
         int canvasWidth = this.actualScreenWidth - (163 ^ 127) - 240;
         newX = this.applySnapX(newX, this.draggingComponent.width, canvasX, canvasWidth);
         newY = this.applySnapY(newY, this.draggingComponent.height);
         newX = Math.max(canvasX, Math.min(newX, canvasX + canvasWidth - this.draggingComponent.width));
         newY = Math.max(6 ^ 52, Math.min(newY, this.actualScreenHeight - 10 - this.draggingComponent.height));
         this.draggingComponent.x = newX;
         this.draggingComponent.y = newY;
         return true;
      } else {
         return super.method_25403(mouseX, mouseY, button, deltaX, deltaY);
      }
   }

   public boolean method_25406(double mouseX, double mouseY, int button) {
      if (button == 0 && this.draggingComponent != null) {
         this.draggingComponent = null;
         return true;
      } else {
         return super.method_25406(mouseX, mouseY, button);
      }
   }

   private int applySnapX(int x, int width, int canvasX, int canvasWidth) {
      int centerX = canvasX + canvasWidth / 2;
      int compCenterX = x + width / 2;
      if (x < canvasX + (141 ^ 133)) {
         return canvasX;
      } else if (x + width > canvasX + canvasWidth - 8) {
         return canvasX + canvasWidth - width;
      } else {
         return Math.abs(compCenterX - centerX) < 8 ? centerX - width / 2 : x;
      }
   }

   private int applySnapY(int y, int height) {
      int centerY = this.actualScreenHeight / 2;
      int compCenterY = y + height / 2;
      if (y < (216 ^ 226)) {
         return 50;
      } else if (y + height > this.actualScreenHeight - 10 - 8) {
         return this.actualScreenHeight - (119 ^ 125) - height;
      } else {
         return Math.abs(compCenterY - centerY) < 8 ? centerY - height / 2 : y;
      }
   }

   public void method_25419() {
      this.savePositions();
      super.method_25419();
   }

   private void savePositions() {
      hf hudModule = this.getHudModule();
      if (hudModule != null) {
         Iterator var2 = this.components.iterator();

         while(var2.hasNext()) {
            cn component = (cn)var2.next();
            component.savePositionToModule(hudModule);
         }

         if (Astralux.configManager != null) {
            try {
               Method saveMethod = null;

               try {
                  saveMethod = Astralux.configManager.getClass().getMethod(d229("KD0rOw=="));
               } catch (NoSuchMethodException var8) {
                  try {
                     saveMethod = Astralux.configManager.getClass().getMethod(d229("KD0rOxwPDwQKAw=="));
                  } catch (NoSuchMethodException var7) {
                     try {
                        saveMethod = Astralux.configManager.getClass().getMethod(d229("KD0rOw8SDgQKCAA="));
                     } catch (NoSuchMethodException var6) {
                        System.err.println(d229("GDMoMjtADw0XRAMPCQxJGQoaCE4CFQUaHBBVHxlYOhUVGhQZMuHv4+Th9w=="));
                     }
                  }
               }

               if (saveMethod != null) {
                  saveMethod.invoke(Astralux.configManager);
               }
            } catch (Exception var9) {
               System.err.println("Failed to save config: " + var9.getMessage());
            }
         }
      }

   }

   private hf getHudModule() {
      try {
         if (Astralux.INSTANCE != null && Astralux.INSTANCE.getModuleManager() != null) {
            return (hf)Astralux.INSTANCE.getModuleManager().getModuleByClass(hf.class);
         }
      } catch (Exception var2) {
         System.err.println("Failed to get HUD module: " + var2.getMessage());
      }

      return null;
   }

   public boolean method_25421() {
      return false;
   }

   // $FF: synthetic method
   private static String d229(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 8027 + var3 & 255);
      }

      return new String(var2);
   }
}
