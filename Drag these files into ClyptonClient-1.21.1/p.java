import java.awt.Color;
import java.util.Base64;
import net.minecraft.class_310;
import net.minecraft.class_332;

public abstract class p {
   public class_310 mc = class_310.method_1551();
   public bj parent;
   public ab setting;
   public int offset;
   public Color currentColor;
   public boolean mouseOver;
   int x;
   int y;
   int width;
   int height;

   public p(bj parent, ab setting, int offset) {
      this.parent = parent;
      this.setting = setting;
      this.offset = offset;
      this.x = this.parentX();
      this.y = this.parentY() + this.parentOffset() + offset;
      this.width = this.parentX() + this.parentWidth();
      this.height = this.parentY() + this.parentOffset() + offset + this.parentHeight();
   }

   public int parentX() {
      return this.parent.parent.getX();
   }

   public int parentY() {
      return this.parent.parent.getY();
   }

   public int parentWidth() {
      return this.parent.parent.getWidth();
   }

   public int parentHeight() {
      return this.parent.parent.getHeight();
   }

   public int parentOffset() {
      return this.parent.offset;
   }

   public void render(class_332 drawContext, int n, int n2, float n3) {
      this.updateMouseOver((double)n, (double)n2);
      this.x = this.parentX();
      this.y = this.parentY() + this.parentOffset() + this.offset;
      this.width = this.parentX() + this.parentWidth();
      this.height = this.parentY() + this.parentOffset() + this.offset + this.parentHeight();
      drawContext.method_25294(this.x, this.y, this.width, this.height, this.currentColor.getRGB());
   }

   private void updateMouseOver(double n, double n2) {
      this.mouseOver = this.isHovered(n, n2);
   }

   public void renderDescription(class_332 drawContext, int mouseX, int mouseY, float delta) {
      if (this.isHovered((double)mouseX, (double)mouseY) && this.setting != null && this.setting.getDescription() != null && !this.parent.parent.dragging) {
         CharSequence description = this.setting.getDescription();
         if (description != null && description.length() > 0) {
            this.parent.parent.parent.setTooltip(description, mouseX + 10, mouseY + 10);
         }
      }

   }

   public void onGuiClose() {
      this.currentColor = null;
   }

   public void keyPressed(int n, int n2, int n3) {
   }

   public boolean isHovered(double n, double n2) {
      return n > (double)this.parentX() && n < (double)(this.parentX() + this.parentWidth()) && n2 > (double)(this.offset + this.parentOffset() + this.parentY()) && n2 < (double)(this.offset + this.parentOffset() + this.parentY() + this.parentHeight());
   }

   public void onUpdate() {
      if (this.currentColor == null) {
         this.currentColor = ch.safeColor(0, 0, 0, 0);
      } else {
         this.currentColor = ch.safeColor(0, 0, 0, this.currentColor.getAlpha());
      }

      if (this.currentColor.getAlpha() != 120) {
         this.currentColor = ch.a(0.05F, 185 ^ 193, this.currentColor);
      }

   }

   public void mouseClicked(double n, double n2, int n3) {
   }

   public void mouseReleased(double n, double n2, int n3) {
   }

   public void mouseDragged(double n, double n2, int n3, double n4, double n5) {
   }

   // $FF: synthetic method
   private static String d903(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 5031 + var3 & 255);
      }

      return new String(var2);
   }
}
