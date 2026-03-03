import java.awt.Color;
import java.util.Base64;
import net.minecraft.class_332;

public final class dx extends p {
   private final cu<?> setting;
   private float hoverAnimation = 0.0F;
   private float selectAnimation = 0.0F;
   private float previousSelectAnimation = 0.0F;
   private boolean wasClicked = false;
   public Color currentColor;
   private final Color TEXT_COLOR = aq.getTextColor();
   private final Color HOVER_COLOR = aq.getHoverColor();
   private final Color SELECTOR_BG = aq.getButtonBackground();
   private final float SELECTOR_HEIGHT = 4.0F;
   private final float SELECTOR_RADIUS = 2.0F;
   private final float HOVER_ANIMATION_SPEED = 0.25F;
   private final float SELECT_ANIMATION_SPEED = 0.15F;

   public dx(bj moduleButton, ab setting, int n) {
      super(moduleButton, setting, n);
      this.setting = (cu)setting;
   }

   public void onUpdate() {
      Color mainColor = bi.getMainColor(255, this.parent.settings.indexOf(this));
      if (this.currentColor == null) {
         this.currentColor = ch.safeColor(mainColor.getRed(), mainColor.getGreen(), mainColor.getBlue(), 0);
      } else {
         this.currentColor = ch.safeColor(mainColor.getRed(), mainColor.getGreen(), mainColor.getBlue(), this.currentColor.getAlpha());
      }

      if (this.currentColor.getAlpha() != 255) {
         this.currentColor = ch.a(0.05F, 255, this.currentColor);
      }

      super.onUpdate();
   }

   public void render(class_332 drawContext, int n, int n2, float n3) {
      super.render(drawContext, n, n2, n3);
      this.updateAnimations(n, n2, n3);
      int index = this.setting.getPossibleValues().indexOf(this.setting.getValue());
      int size = this.setting.getPossibleValues().size();
      this.parentWidth();
      this.parentX();
      if (!this.parent.parent.dragging) {
         drawContext.method_25294(this.parentX(), this.parentY() + this.parentOffset() + this.offset, this.parentX() + this.parentWidth(), this.parentY() + this.parentOffset() + this.offset + this.parentHeight(), ch.safeColor(this.HOVER_COLOR.getRed(), this.HOVER_COLOR.getGreen(), this.HOVER_COLOR.getBlue(), (int)((float)this.HOVER_COLOR.getAlpha() * this.hoverAnimation)).getRGB());
      }

      i.drawString(String.valueOf(this.setting.getName()), drawContext, this.parentX() + 5, this.parentY() + this.parentOffset() + this.offset + (68 ^ 77), this.TEXT_COLOR.getRGB());
      i.drawString(this.setting.getValue().name(), drawContext, this.parentX() + i.getWidth(String.valueOf(this.setting.getName()) + ": ") + (98 ^ 106), this.parentY() + this.parentOffset() + this.offset + 9, this.currentColor.getRGB());
      int n4 = this.parentY() + this.offset + this.parentOffset() + (82 ^ 75);
      int n5 = this.parentX() + 5;
      int n6 = this.parentWidth() - (202 ^ 192);
      iq.renderRoundedQuad(drawContext.method_51448(), this.SELECTOR_BG, (double)n5, (double)n4, (double)(n5 + n6), (double)((float)n4 + 4.0F), 2.0D, 2.0D, 2.0D, 2.0D, 50.0D);
      int n7 = index - 1;
      float n8 = (float)n7;
      if ((float)n7 < 0.0F) {
         n8 = (float)(size - 1);
      }

      int n9 = index + 1;
      float n10 = (float)n9;
      if ((float)n9 >= (float)size) {
         n10 = 0.0F;
      }

      int n11 = n6 / size;
      float n12;
      if (this.previousSelectAnimation > 0.01F) {
         n12 = (float)ac.linearInterpolate((double)((float)n5 + n8 * (float)n11), (double)((float)n5 + (float)index * (float)n11), (double)(1.0F - this.previousSelectAnimation));
      } else if (this.selectAnimation > 0.01F) {
         n12 = (float)ac.linearInterpolate((double)((float)n5 + (float)index * (float)n11), (double)((float)n5 + n10 * (float)n11), (double)this.selectAnimation);
      } else {
         n12 = (float)n5 + (float)index * (float)n11;
      }

      iq.renderRoundedQuad(drawContext.method_51448(), this.currentColor, (double)n12, (double)n4, (double)(n12 + (float)n11), (double)((float)n4 + 4.0F), 2.0D, 2.0D, 2.0D, 2.0D, 50.0D);
      int n13 = this.parentY() + this.parentOffset() + this.offset + 9;
      int parentX = this.parentX();
      int parentX2 = this.parentX();
      int parentWidth = this.parentWidth();
      i.drawString("◄", drawContext, parentX + this.parentWidth() - 25, n13, this.TEXT_COLOR.getRGB());
      i.drawString("►", drawContext, parentX2 + parentWidth - (193 ^ 205), n13, this.TEXT_COLOR.getRGB());
      if (this.wasClicked) {
         this.wasClicked = false;
         this.previousSelectAnimation = 0.0F;
         this.selectAnimation = 0.01F;
      }

   }

   private void updateAnimations(int n, int n2, float n3) {
      float n4 = n3 * 0.05F;
      float n5;
      if (this.isHovered((double)n, (double)n2) && !this.parent.parent.dragging) {
         n5 = 1.0F;
      } else {
         n5 = 0.0F;
      }

      this.hoverAnimation = (float)ac.exponentialInterpolate((double)this.hoverAnimation, (double)n5, 0.25D, (double)n4);
      if (this.selectAnimation > 0.01F) {
         this.selectAnimation = (float)ac.exponentialInterpolate((double)this.selectAnimation, 0.0D, 0.15000000596046448D, (double)n4);
         if (this.selectAnimation < 0.01F) {
            this.previousSelectAnimation = 0.99F;
         }
      }

      if (this.previousSelectAnimation > 0.01F) {
         this.previousSelectAnimation = (float)ac.exponentialInterpolate((double)this.previousSelectAnimation, 0.0D, 0.15000000596046448D, (double)n4);
      }

   }

   public void keyPressed(int n, int n2, int n3) {
      if (this.mouseOver && this.parent.extended) {
         if (n == 259) {
            this.setting.setModeIndex(this.setting.getOriginalValue());
         } else if (n == (471 ^ 209)) {
            this.cycleModeForward();
         } else if (n == 263) {
            this.cycleModeBackward();
         }
      }

      super.keyPressed(n, n2, n3);
   }

   private void cycleModeForward() {
      this.setting.cycleUp();
      this.wasClicked = true;
   }

   private void cycleModeBackward() {
      this.setting.cycleDown();
      this.wasClicked = true;
   }

   public void mouseClicked(double n, double n2, int n3) {
      if (this.isHovered(n, n2)) {
         if (n3 == 0) {
            this.cycleModeForward();
         } else if (n3 == 1) {
            this.cycleModeBackward();
         } else if (n3 == 2) {
            this.setting.setModeIndex(this.setting.getOriginalValue());
         }
      }

      super.mouseClicked(n, n2, n3);
   }

   public void onGuiClose() {
      this.currentColor = null;
      this.hoverAnimation = 0.0F;
      this.selectAnimation = 0.0F;
      this.previousSelectAnimation = 0.0F;
      super.onGuiClose();
   }

   // $FF: synthetic method
   private static String d819(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 2054 + var3 & 255);
      }

      return new String(var2);
   }
}
