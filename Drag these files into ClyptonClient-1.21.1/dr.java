import java.awt.Color;
import java.util.Base64;
import net.minecraft.class_332;
import net.minecraft.class_3532;

public final class dr extends p {
   public boolean dragging;
   public double offsetX;
   public double lerpedOffsetX = 0.0D;
   private float hoverAnimation = 0.0F;
   private final gn setting;
   public Color currentColor1;
   private Color currentAlpha;
   private final Color TEXT_COLOR = aq.getTextColor();
   private final Color HOVER_COLOR = aq.getHoverColor();
   private final Color TRACK_BG_COLOR = aq.getButtonBorder();
   private final float TRACK_HEIGHT = 4.0F;
   private final float TRACK_RADIUS = 2.0F;
   private final float ANIMATION_SPEED = 0.25F;

   public dr(bj moduleButton, ab setting, int n) {
      super(moduleButton, setting, n);
      this.setting = (gn)setting;
   }

   public void onUpdate() {
      Color mainColor = bi.getMainColor(255, this.parent.settings.indexOf(this));
      if (this.currentColor1 == null) {
         this.currentColor1 = ch.safeColor(mainColor.getRed(), mainColor.getGreen(), mainColor.getBlue(), 0);
      } else {
         this.currentColor1 = ch.safeColor(mainColor.getRed(), mainColor.getGreen(), mainColor.getBlue(), this.currentColor1.getAlpha());
      }

      if (this.currentColor1.getAlpha() != 255) {
         this.currentColor1 = ch.a(0.05F, 255, this.currentColor1);
      }

      super.onUpdate();
   }

   public void render(class_332 drawContext, int n, int n2, float n3) {
      super.render(drawContext, n, n2, n3);
      this.updateAnimations(n, n2, n3);
      this.offsetX = (this.setting.getValue() - this.setting.getMin()) / (this.setting.getMax() - this.setting.getMin()) * (double)this.parentWidth();
      this.lerpedOffsetX = ac.approachValue((float)(0.5D * (double)n3), this.lerpedOffsetX, this.offsetX);
      if (!this.parent.parent.dragging) {
         drawContext.method_25294(this.parentX(), this.parentY() + this.parentOffset() + this.offset, this.parentX() + this.parentWidth(), this.parentY() + this.parentOffset() + this.offset + this.parentHeight(), ch.safeColor(this.HOVER_COLOR.getRed(), this.HOVER_COLOR.getGreen(), this.HOVER_COLOR.getBlue(), (int)((float)this.HOVER_COLOR.getAlpha() * this.hoverAnimation)).getRGB());
      }

      int n4 = this.parentY() + this.offset + this.parentOffset() + (51 ^ 42);
      int n5 = this.parentX() + 5;
      iq.renderRoundedQuad(drawContext.method_51448(), this.TRACK_BG_COLOR, (double)n5, (double)n4, (double)(n5 + (this.parentWidth() - (8 ^ 2))), (double)((float)n4 + 4.0F), 2.0D, 2.0D, 2.0D, 2.0D, 50.0D);
      if (this.lerpedOffsetX > 2.5D) {
         iq.renderRoundedQuad(drawContext.method_51448(), this.currentColor1, (double)n5, (double)n4, (double)n5 + Math.max(this.lerpedOffsetX - 5.0D, 0.0D), (double)((float)n4 + 4.0F), 2.0D, 2.0D, 2.0D, 2.0D, 50.0D);
      }

      String displayValue = this.getDisplayValue();
      i.drawString(this.setting.getName(), drawContext, this.parentX() + 5, this.parentY() + this.parentOffset() + this.offset + 9, this.TEXT_COLOR.getRGB());
      i.drawString(displayValue, drawContext, this.parentX() + this.parentWidth() - i.getWidth(displayValue) - 5, this.parentY() + this.parentOffset() + this.offset + 9, this.currentColor1.getRGB());
   }

   private void updateAnimations(int n, int n2, float n3) {
      float n4;
      if (this.isHovered((double)n, (double)n2) && !this.parent.parent.dragging) {
         n4 = 1.0F;
      } else {
         n4 = 0.0F;
      }

      this.hoverAnimation = (float)ac.exponentialInterpolate((double)this.hoverAnimation, (double)n4, 0.25D, (double)(n3 * 0.05F));
   }

   private String getDisplayValue() {
      double a = this.setting.getValue();
      double c = this.setting.getFormat();
      if (c == 0.1D) {
         return String.format(d137("joKcyA=="), a);
      } else if (c == 0.01D) {
         return String.format(d137("joKfyA=="), a);
      } else if (c == 0.001D) {
         return String.format(d137("joKeyA=="), a);
      } else if (c == 1.0E-4D) {
         return String.format(d137("joKZyA=="), a);
      } else {
         return c >= 1.0D ? String.format(d137("joKdyA=="), a) : String.valueOf(a);
      }
   }

   public void onGuiClose() {
      this.currentColor1 = null;
      this.hoverAnimation = 0.0F;
      super.onGuiClose();
   }

   private void slide(double n) {
      this.setting.getValue(ac.roundToNearest(class_3532.method_15350((n - (double)(this.parentX() + 5)) / (double)(this.parentWidth() - (101 ^ 111)), 0.0D, 1.0D) * (this.setting.getMax() - this.setting.getMin()) + this.setting.getMin(), this.setting.getFormat()));
   }

   public void keyPressed(int n, int n2, int n3) {
      if (this.mouseOver && this.parent.extended && n == (378 ^ 121)) {
         this.setting.getValue(this.setting.getDefaultValue());
      }

      super.keyPressed(n, n2, n3);
   }

   public void mouseClicked(double n, double n2, int n3) {
      if (this.isHovered(n, n2) && n3 == 0) {
         this.dragging = true;
         this.slide(n);
      }

      super.mouseClicked(n, n2, n3);
   }

   public void mouseReleased(double n, double n2, int n3) {
      if (this.dragging && n3 == 0) {
         this.dragging = false;
      }

      super.mouseReleased(n, n2, n3);
   }

   public void mouseDragged(double n, double n2, int n3, double n4, double n5) {
      if (this.dragging) {
         this.slide(n);
      }

      super.mouseDragged(n, n2, n3, n4, n5);
   }

   // $FF: synthetic method
   private static String d137(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 5433 - 910 + var3 & 255);
      }

      return new String(var2);
   }
}
