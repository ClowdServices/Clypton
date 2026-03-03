import java.awt.Color;
import java.util.Base64;
import net.minecraft.class_332;
import net.minecraft.class_3532;
import net.minecraft.class_4587;

public final class cb extends p {
   private boolean draggingMin;
   private boolean draggingMax;
   private double offsetMinX;
   private double offsetMaxX;
   public double lerpedOffsetMinX;
   public double lerpedOffsetMaxX;
   private float hoverAnimation = 0.0F;
   private final fr setting;
   public Color accentColor1;
   public Color accentColor2;
   private static final Color TEXT_COLOR = aq.getTextColor();
   private static final Color HOVER_COLOR = aq.getHoverColor();
   private static final Color TRACK_BG_COLOR = aq.getButtonBorder();
   private static final Color THUMB_COLOR = ch.safeColor(240, 240, 240, 255);
   private static final float TRACK_HEIGHT = 4.0F;
   private static final float TRACK_RADIUS = 2.0F;
   private static final float THUMB_SIZE = 8.0F;
   private static final float ANIMATION_SPEED = 0.25F;

   public cb(bj moduleButton, ab setting, int n) {
      super(moduleButton, setting, n);
      this.setting = (fr)setting;
      this.lerpedOffsetMinX = (double)this.parentX();
      this.lerpedOffsetMaxX = (double)(this.parentX() + this.parentWidth());
   }

   public void render(class_332 drawContext, int n, int n2, float n3) {
      super.render(drawContext, n, n2, n3);
      class_4587 matrices = drawContext.method_51448();
      this.updateAnimations(n, n2, n3);
      this.offsetMinX = (this.setting.getCurrentMin() - this.setting.getMinValue()) / (this.setting.getMaxValue() - this.setting.getMinValue()) * (double)(this.parentWidth() - 10) + 5.0D;
      this.offsetMaxX = (this.setting.getCurrentMax() - this.setting.getMinValue()) / (this.setting.getMaxValue() - this.setting.getMinValue()) * (double)(this.parentWidth() - 10) + 5.0D;
      this.lerpedOffsetMinX = ac.approachValue((float)(0.5D * (double)n3), this.lerpedOffsetMinX, this.offsetMinX);
      this.lerpedOffsetMaxX = ac.approachValue((float)(0.5D * (double)n3), this.lerpedOffsetMaxX, this.offsetMaxX);
      if (!this.parent.parent.dragging) {
         drawContext.method_25294(this.parentX(), this.parentY() + this.parentOffset() + this.offset, this.parentX() + this.parentWidth(), this.parentY() + this.parentOffset() + this.offset + this.parentHeight(), ch.safeColor(HOVER_COLOR.getRed(), HOVER_COLOR.getGreen(), HOVER_COLOR.getBlue(), (int)((float)HOVER_COLOR.getAlpha() * this.hoverAnimation)).getRGB());
      }

      int n4 = this.parentY() + this.offset + this.parentOffset() + (237 ^ 244);
      int n5 = this.parentX() + 5;
      iq.renderRoundedQuad(matrices, TRACK_BG_COLOR, (double)n5, (double)n4, (double)(n5 + (this.parentWidth() - (101 ^ 111))), (double)((float)n4 + 4.0F), 2.0D, 2.0D, 2.0D, 2.0D, 50.0D);
      if (this.lerpedOffsetMaxX > this.lerpedOffsetMinX) {
         iq.renderRoundedQuad(matrices, this.accentColor1, (double)n5 + this.lerpedOffsetMinX - 5.0D, (double)n4, (double)n5 + this.lerpedOffsetMaxX - 5.0D, (double)((float)n4 + 4.0F), 2.0D, 2.0D, 2.0D, 2.0D, 50.0D);
      }

      String displayText = this.getDisplayText();
      i.drawString(this.setting.getName(), drawContext, this.parentX() + 5, this.parentY() + this.parentOffset() + this.offset + 9, TEXT_COLOR.getRGB());
      i.drawString(displayText, drawContext, this.parentX() + this.parentWidth() - i.getWidth(displayText) - 5, this.parentY() + this.parentOffset() + this.offset + 9, this.accentColor1.getRGB());
      float n6 = (float)n4 + 2.0F - 4.0F;
      iq.renderRoundedQuad(matrices, THUMB_COLOR, (double)((float)((double)n5 + this.lerpedOffsetMinX - 5.0D - 4.0D)), (double)n6, (double)((float)((double)n5 + this.lerpedOffsetMinX - 5.0D + 4.0D)), (double)(n6 + 8.0F), 4.0D, 4.0D, 4.0D, 4.0D, 50.0D);
      iq.renderRoundedQuad(matrices, THUMB_COLOR, (double)((float)((double)n5 + this.lerpedOffsetMaxX - 5.0D - 4.0D)), (double)n6, (double)((float)((double)n5 + this.lerpedOffsetMaxX - 5.0D + 4.0D)), (double)(n6 + 8.0F), 4.0D, 4.0D, 4.0D, 4.0D, 50.0D);
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

   private String getDisplayText() {
      if (this.setting.getCurrentMin() == this.setting.getCurrentMax()) {
         return this.formatValue(this.setting.getCurrentMin());
      } else {
         String var10000 = this.formatValue(this.setting.getCurrentMin());
         return var10000 + " - " + this.formatValue(this.setting.getCurrentMax());
      }
   }

   private String formatValue(double d) {
      double m = this.setting.getStep();
      if (m == 0.1D) {
         return String.format(d758("V11FEw=="), d);
      } else if (m == 0.01D) {
         return String.format(d758("V11GEw=="), d);
      } else if (m == 0.001D) {
         return String.format(d758("V11HEw=="), d);
      } else {
         return m >= 1.0D ? String.format(d758("V11EEw=="), d) : String.valueOf(d);
      }
   }

   public void mouseClicked(double n, double n2, int n3) {
      if (n3 == 0 && this.isHovered(n, n2)) {
         if (this.isHoveredMin(n, n2)) {
            this.draggingMin = true;
            this.slideMin(n);
         } else if (this.isHoveredMax(n, n2)) {
            this.draggingMax = true;
            this.slideMax(n);
         } else if (n < (double)this.parentX() + this.offsetMinX) {
            this.draggingMin = true;
            this.slideMin(n);
         } else if (n > (double)this.parentX() + this.offsetMaxX) {
            this.draggingMax = true;
            this.slideMax(n);
         } else if (n - ((double)this.parentX() + this.offsetMinX) < (double)this.parentX() + this.offsetMaxX - n) {
            this.draggingMin = true;
            this.slideMin(n);
         } else {
            this.draggingMax = true;
            this.slideMax(n);
         }
      }

      super.mouseClicked(n, n2, n3);
   }

   public void keyPressed(int n, int n2, int n3) {
      if (this.mouseOver && n == (317 ^ 62)) {
         this.setting.setCurrentMax(this.setting.getDefaultMax());
         this.setting.setCurrentMin(this.setting.getDefaultMin());
      }

      super.keyPressed(n, n2, n3);
   }

   public boolean isHoveredMin(double n, double n2) {
      return this.isHovered(n, n2) && n > (double)this.parentX() + this.offsetMinX - 8.0D && n < (double)this.parentX() + this.offsetMinX + 8.0D;
   }

   public boolean isHoveredMax(double n, double n2) {
      return this.isHovered(n, n2) && n > (double)this.parentX() + this.offsetMaxX - 8.0D && n < (double)this.parentX() + this.offsetMaxX + 8.0D;
   }

   public void mouseReleased(double n, double n2, int n3) {
      if (n3 == 0) {
         this.draggingMin = false;
         this.draggingMax = false;
      }

      super.mouseReleased(n, n2, n3);
   }

   public void mouseDragged(double n, double n2, int n3, double n4, double n5) {
      if (this.draggingMin) {
         this.slideMin(n);
      }

      if (this.draggingMax) {
         this.slideMax(n);
      }

      super.mouseDragged(n, n2, n3, n4, n5);
   }

   public void onGuiClose() {
      this.accentColor1 = null;
      this.accentColor2 = null;
      this.hoverAnimation = 0.0F;
      super.onGuiClose();
   }

   private void slideMin(double n) {
      this.setting.setCurrentMin(Math.min(ac.roundToNearest(class_3532.method_15350((n - (double)(this.parentX() + 5)) / (double)(this.parentWidth() - 10), 0.0D, 1.0D) * (this.setting.getMaxValue() - this.setting.getMinValue()) + this.setting.getMinValue(), this.setting.getStep()), this.setting.getCurrentMax()));
   }

   private void slideMax(double n) {
      this.setting.setCurrentMax(Math.max(ac.roundToNearest(class_3532.method_15350((n - (double)(this.parentX() + 5)) / (double)(this.parentWidth() - 10), 0.0D, 1.0D) * (this.setting.getMaxValue() - this.setting.getMinValue()) + this.setting.getMinValue(), this.setting.getStep()), this.setting.getCurrentMin()));
   }

   public void onUpdate() {
      Color mainColor = bi.getMainColor(255, this.parent.settings.indexOf(this));
      Color mainColor2 = bi.getMainColor(140 ^ 115, this.parent.settings.indexOf(this) + 1);
      if (this.accentColor1 == null) {
         this.accentColor1 = ch.safeColor(mainColor.getRed(), mainColor.getGreen(), mainColor.getBlue(), 0);
      } else {
         this.accentColor1 = ch.safeColor(mainColor.getRed(), mainColor.getGreen(), mainColor.getBlue(), this.accentColor1.getAlpha());
      }

      if (this.accentColor2 == null) {
         this.accentColor2 = ch.safeColor(mainColor2.getRed(), mainColor2.getGreen(), mainColor2.getBlue(), 0);
      } else {
         this.accentColor2 = ch.safeColor(mainColor2.getRed(), mainColor2.getGreen(), mainColor2.getBlue(), this.accentColor2.getAlpha());
      }

      if (this.accentColor1.getAlpha() != (119 ^ 136)) {
         this.accentColor1 = ch.a(0.05F, 255, this.accentColor1);
      }

      if (this.accentColor2.getAlpha() != 255) {
         this.accentColor2 = ch.a(0.05F, 255, this.accentColor2);
      }

      super.onUpdate();
   }

   // $FF: synthetic method
   private static String d758(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 4210 + var3 & (97 ^ 158));
      }

      return new String(var2);
   }
}
