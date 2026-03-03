import java.awt.Color;
import java.util.Base64;
import net.minecraft.class_332;

public final class hs extends p {
   private final ff setting;
   private float hoverAnimation = 0.0F;
   private float enabledAnimation = 0.0F;
   private final float CORNER_RADIUS = 3.0F;
   private final Color TEXT_COLOR = aq.getTextColor();
   private final Color HOVER_COLOR = aq.getHoverColor();
   private final Color BOX_BORDER = aq.getButtonBorder();
   private final Color BOX_BG = aq.getButtonBackground();
   private final int BOX_SIZE = 128 ^ 141;
   private final float HOVER_ANIMATION_SPEED = 0.005F;
   private final float TOGGLE_ANIMATION_SPEED = 0.002F;

   public hs(bj moduleButton, ab setting, int n) {
      super(moduleButton, setting, n);
      this.setting = (ff)setting;
      float enabledAnimation;
      if (this.setting.getValue()) {
         enabledAnimation = 1.0F;
      } else {
         enabledAnimation = 0.0F;
      }

      this.enabledAnimation = enabledAnimation;
   }

   public void render(class_332 drawContext, int n, int n2, float n3) {
      super.render(drawContext, n, n2, n3);
      this.updateAnimations(n, n2, n3);
      if (!this.parent.parent.dragging) {
         drawContext.method_25294(this.parentX(), this.parentY() + this.parentOffset() + this.offset, this.parentX() + this.parentWidth(), this.parentY() + this.parentOffset() + this.offset + this.parentHeight(), ch.safeColor(this.HOVER_COLOR.getRed(), this.HOVER_COLOR.getGreen(), this.HOVER_COLOR.getBlue(), (int)((float)this.HOVER_COLOR.getAlpha() * this.hoverAnimation)).getRGB());
      }

      i.drawString(this.setting.getName(), drawContext, this.parentX() + 27, this.parentY() + this.parentOffset() + this.offset + this.parentHeight() / 2 - (182 ^ 176), this.TEXT_COLOR.getRGB());
      this.renderModernCheckbox(drawContext);
      this.renderDescription(drawContext, n, n2, n3);
   }

   private void updateAnimations(int n, int n2, float n3) {
      float n4 = n3 * 0.05F;
      float n5;
      if (this.isHovered((double)n, (double)n2) && !this.parent.parent.dragging) {
         n5 = 1.0F;
      } else {
         n5 = 0.0F;
      }

      this.hoverAnimation = (float)ac.exponentialInterpolate((double)this.hoverAnimation, (double)n5, 0.004999999888241291D, (double)n4);
      float n6;
      if (this.setting.getValue()) {
         n6 = 1.0F;
      } else {
         n6 = 0.0F;
      }

      this.enabledAnimation = (float)ac.exponentialInterpolate((double)this.enabledAnimation, (double)n6, 0.0020000000949949026D, (double)n4);
      this.enabledAnimation = (float)ac.clampValue((double)this.enabledAnimation, 0.0D, 1.0D);
   }

   private void renderModernCheckbox(class_332 drawContext) {
      int n = this.parentX() + 8;
      int n2 = this.parentY() + this.parentOffset() + this.offset + this.parentHeight() / 2 - 6;
      Color mainColor = bi.getMainColor(255, this.parent.settings.indexOf(this));
      iq.renderRoundedQuad(drawContext.method_51448(), this.BOX_BORDER, (double)n, (double)n2, (double)(n + (205 ^ 192)), (double)(n2 + (107 ^ 102)), 3.0D, 3.0D, 3.0D, 3.0D, 50.0D);
      iq.renderRoundedQuad(drawContext.method_51448(), this.BOX_BG, (double)(n + 1), (double)(n2 + 1), (double)(n + 13 - 1), (double)(n2 + 13 - 1), 2.5D, 2.5D, 2.5D, 2.5D, 50.0D);
      if (this.enabledAnimation > 0.01F) {
         Color color = ch.safeColor(mainColor.getRed(), mainColor.getGreen(), mainColor.getBlue(), (int)(255.0F * this.enabledAnimation));
         float n3 = (float)(n + 2) + 9.0F * (1.0F - this.enabledAnimation) / 2.0F;
         float n4 = (float)(n2 + 2) + 9.0F * (1.0F - this.enabledAnimation) / 2.0F;
         iq.renderRoundedQuad(drawContext.method_51448(), color, (double)n3, (double)n4, (double)(n3 + 9.0F * this.enabledAnimation), (double)(n4 + 9.0F * this.enabledAnimation), 1.5D, 1.5D, 1.5D, 1.5D, 50.0D);
         if (this.enabledAnimation > 0.7F) {
            iq.renderRoundedQuad(drawContext.method_51448(), ch.safeColor(mainColor.getRed(), mainColor.getGreen(), mainColor.getBlue(), (int)(40.0F * (this.enabledAnimation - 0.7F) * 3.33F)), (double)(n - 1), (double)(n2 - 1), (double)(n + 13 + 1), (double)(n2 + 13 + 1), 3.5D, 3.5D, 3.5D, 3.5D, 50.0D);
         }
      }

   }

   public void keyPressed(int n, int n2, int n3) {
      if (this.mouseOver && this.parent.extended && n == 259) {
         this.setting.setValue(this.setting.getDefaultValue());
      }

      super.keyPressed(n, n2, n3);
   }

   public void mouseClicked(double n, double n2, int n3) {
      if (this.isHovered(n, n2) && n3 == 0) {
         this.setting.toggle();
      }

      super.mouseClicked(n, n2, n3);
   }

   public void onGuiClose() {
      super.onGuiClose();
      this.hoverAnimation = 0.0F;
      float enabledAnimation;
      if (this.setting.getValue()) {
         enabledAnimation = 1.0F;
      } else {
         enabledAnimation = 0.0F;
      }

      this.enabledAnimation = enabledAnimation;
   }

   // $FF: synthetic method
   private static String d56(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 3367 - 882 + var3 & 255);
      }

      return new String(var2);
   }
}
