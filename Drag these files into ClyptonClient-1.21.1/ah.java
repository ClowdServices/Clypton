import client.astralux.Astralux;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import net.minecraft.class_332;

public final class ah {
   public List<bj> moduleButtons = new ArrayList();
   public int x;
   public int y;
   private final int width;
   private final int height;
   public Color currentColor;
   private final hk category;
   public boolean dragging;
   public boolean extended;
   private int dragX;
   private int dragY;
   private int prevX;
   private int prevY;
   public gf parent;
   private float hoverAnimation = 0.0F;

   public ah(int x, int y, int width, int height, hk category, gf parent) {
      this.x = x;
      this.y = y;
      this.width = width;
      this.dragging = false;
      this.extended = true;
      this.height = height;
      this.category = category;
      this.parent = parent;
      this.prevX = x;
      this.prevY = y;
      List<bf> modules = new ArrayList(Astralux.INSTANCE.getModuleManager().getModulesByCategory(category));
      int offset = height;

      for(Iterator var9 = modules.iterator(); var9.hasNext(); offset += height) {
         bf module = (bf)var9.next();
         this.moduleButtons.add(new bj(this, module, offset));
      }

   }

   public void render(class_332 context, int n, int n2, float n3) {
      Color themeBg = this.parent.currentTheme.panelBackground;
      Color accentColor = aq.getOutlineColor();
      Color color = ch.safeColor(themeBg.getRed(), themeBg.getGreen(), themeBg.getBlue(), gm.windowAlpha.getIntValue());
      if (this.currentColor == null) {
         this.currentColor = ch.safeColor(themeBg.getRed(), themeBg.getGreen(), themeBg.getBlue(), 0);
      } else {
         this.currentColor = ch.a(0.05F, color, this.currentColor);
      }

      float n4 = this.isHovered((double)n, (double)n2) && !this.dragging ? 1.0F : 0.0F;
      this.hoverAnimation = (float)ac.approachValue(n3 * 0.1F, (double)this.hoverAnimation, (double)n4);
      int totalHeight = this.height;
      bj button;
      if (this.extended) {
         for(Iterator var10 = this.moduleButtons.iterator(); var10.hasNext(); totalHeight += (int)button.animation.getAnimation()) {
            button = (bj)var10.next();
         }
      }

      Color bgColor = ch.safeColor(15, 15, 22, this.currentColor.getAlpha());
      context.method_25294(this.prevX, this.prevY, this.prevX + this.width, this.prevY + totalHeight, bgColor.getRGB());
      int accentAlpha = (int)(200.0F + 55.0F * this.hoverAnimation);
      context.method_25294(this.prevX, this.prevY, this.prevX + this.width, this.prevY + 2, ch.safeColor(accentColor.getRed(), accentColor.getGreen(), accentColor.getBlue(), accentAlpha).getRGB());
      CharSequence categoryName = this.category.name;
      int textX = this.prevX + (this.width - i.getWidth(this.category.name)) / 2;
      int textY = this.prevY + (58 ^ 50);
      int textBrightness = (int)(175.0F + 80.0F * this.hoverAnimation);
      i.drawString(categoryName, context, textX, textY, ch.safeColor(textBrightness, textBrightness, textBrightness, 255).getRGB());
      String arrow = this.extended ? "▲" : "▼";
      int arrowX = this.prevX + this.width - (86 ^ 68);
      i.drawString(arrow, context, arrowX, textY, ch.safeColor(textBrightness, textBrightness, textBrightness, 200).getRGB());
      this.updateButtons(n3);
      if (this.extended) {
         this.renderModuleButtons(context, n, n2, n3);
      }

   }

   private void renderModuleButtons(class_332 context, int n, int n2, float n3) {
      Iterator var5 = this.moduleButtons.iterator();

      while(var5.hasNext()) {
         bj module = (bj)var5.next();
         module.render(context, n, n2, n3);
      }

   }

   public void keyPressed(int n, int n2, int n3) {
      Iterator var4 = this.moduleButtons.iterator();

      while(var4.hasNext()) {
         bj moduleButton = (bj)var4.next();
         moduleButton.keyPressed(n, n2, n3);
      }

   }

   public void onGuiClose() {
      this.currentColor = null;
      Iterator var1 = this.moduleButtons.iterator();

      while(var1.hasNext()) {
         bj moduleButton = (bj)var1.next();
         moduleButton.onGuiClose();
      }

      this.dragging = false;
   }

   public void mouseClicked(double x, double y, int button) {
      if (this.isHovered(x, y) && y >= (double)this.prevY && y <= (double)(this.prevY + this.height)) {
         if (button == 0) {
            this.dragging = true;
            this.dragX = (int)(x - (double)this.prevX);
            this.dragY = (int)(y - (double)this.prevY);
            return;
         }

         if (button == 1) {
            this.extended = !this.extended;
            return;
         }
      }

      if (this.extended) {
         Iterator var6 = this.moduleButtons.iterator();

         while(var6.hasNext()) {
            bj moduleButton = (bj)var6.next();
            moduleButton.mouseClicked(x, y, button);
         }
      }

   }

   public void mouseDragged(double n, double n2, int n3, double n4, double n5) {
      if (this.dragging && n3 == 0) {
         this.x = (int)(n - (double)this.dragX);
         this.y = (int)(n2 - (double)this.dragY);
      }

      if (this.extended) {
         Iterator var10 = this.moduleButtons.iterator();

         while(var10.hasNext()) {
            bj moduleButton = (bj)var10.next();
            moduleButton.mouseDragged(n, n2, n3, n4, n5);
         }
      }

   }

   public void updateButtons(float n) {
      int height = this.height;

      double animation2;
      for(Iterator var3 = this.moduleButtons.iterator(); var3.hasNext(); height += (int)animation2) {
         bj next = (bj)var3.next();
         bo animation = next.animation;
         double n2;
         if (next.extended) {
            n2 = (double)(this.height * (next.settings.size() + 1));
         } else {
            n2 = (double)this.height;
         }

         animation.animate(0.5D * (double)n, n2);
         animation2 = next.animation.getAnimation();
         next.offset = height;
      }

   }

   public void mouseReleased(double n, double n2, int n3) {
      if (n3 == 0 && this.dragging) {
         this.dragging = false;
      }

      Iterator var6 = this.moduleButtons.iterator();

      while(var6.hasNext()) {
         bj moduleButton = (bj)var6.next();
         moduleButton.mouseReleased(n, n2, n3);
      }

   }

   public void mouseScrolled(double n, double n2, double n3, double n4) {
      this.prevX = this.x;
      this.prevY = this.y;
      this.prevY += (int)(n4 * 20.0D);
      this.setY((int)((double)this.y + n4 * 20.0D));
   }

   public int getX() {
      return this.prevX;
   }

   public int getY() {
      return this.prevY;
   }

   public void setY(int y) {
      this.y = y;
   }

   public void setX(int x) {
      this.x = x;
   }

   public int getWidth() {
      return this.width;
   }

   public int getHeight() {
      return this.height;
   }

   public boolean isHovered(double n, double n2) {
      return n > (double)this.x && n < (double)(this.x + this.width) && n2 > (double)this.y && n2 < (double)(this.y + this.height);
   }

   public boolean isPrevHovered(double n, double n2) {
      return n > (double)this.prevX && n < (double)(this.prevX + this.width) && n2 > (double)this.prevY && n2 < (double)(this.prevY + this.height);
   }

   public void updatePosition(double n, double n2, float n3) {
      this.prevX = (int)ac.approachValue(0.3F * n3, (double)this.prevX, (double)this.x);
      this.prevY = (int)ac.approachValue(0.3F * n3, (double)this.prevY, (double)this.y);
   }

   // $FF: synthetic method
   private static String d692(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 3644 - 353 + var3 & (91 ^ 164));
      }

      return new String(var2);
   }
}
