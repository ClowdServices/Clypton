import java.awt.Color;
import java.util.Base64;
import net.minecraft.class_332;

public final class el extends p {
   private final bu setting;
   private float hoverAnimation = 0.0F;
   private Color currentColor;
   private final Color TEXT_COLOR = aq.getTextColor();
   private final Color VALUE_COLOR = aq.getPrimaryAccent();
   private final Color HOVER_COLOR = aq.getHoverColor();
   private final Color INPUT_BG = aq.getButtonBackground();
   private final Color INPUT_BORDER = aq.getButtonBorder();
   private final float CORNER_RADIUS = 4.0F;
   private final float HOVER_ANIMATION_SPEED = 0.25F;
   private final int MAX_VISIBLE_CHARS = 7;

   public el(bj moduleButton, ab setting, int n) {
      super(moduleButton, setting, n);
      this.setting = (bu)setting;
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
      if (!this.parent.parent.dragging) {
         drawContext.method_25294(this.parentX(), this.parentY() + this.parentOffset() + this.offset, this.parentX() + this.parentWidth(), this.parentY() + this.parentOffset() + this.offset + this.parentHeight(), ch.safeColor(this.HOVER_COLOR.getRed(), this.HOVER_COLOR.getGreen(), this.HOVER_COLOR.getBlue(), (int)((float)this.HOVER_COLOR.getAlpha() * this.hoverAnimation)).getRGB());
      }

      int n4 = this.parentX() + 5;
      int n5 = this.parentY() + this.parentOffset() + this.offset + (197 ^ 204);
      i.drawString(String.valueOf(this.setting.getName()), drawContext, n4, n5, this.TEXT_COLOR.getRGB());
      int n6 = n4 + i.getWidth(String.valueOf(this.setting.getName()) + ": ") + 5;
      int n7 = this.parentWidth() - n6 + this.parentX() - 5;
      int n8 = n5 - 2;
      iq.renderRoundedQuad(drawContext.method_51448(), this.INPUT_BORDER, (double)n6, (double)n8, (double)(n6 + n7), (double)(n8 + 18), 4.0D, 4.0D, 4.0D, 4.0D, 50.0D);
      iq.renderRoundedQuad(drawContext.method_51448(), this.INPUT_BG, (double)(n6 + 1), (double)(n8 + 1), (double)(n6 + n7 - 1), (double)(n8 + (180 ^ 166) - 1), 3.5D, 3.5D, 3.5D, 3.5D, 50.0D);
      i.drawString(this.formatDisplayValue(this.setting.getValue()), drawContext, n6 + 4, n8 + 3, this.VALUE_COLOR.getRGB());
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

   private String formatDisplayValue(String s) {
      if (s.isEmpty()) {
         return d833("SktI");
      } else {
         return s.length() <= 7 ? s : s.substring(0, 4) + "...";
      }
   }

   public void mouseClicked(double n, double n2, int n3) {
      if (this.isHovered(n, n2) && n3 == 0) {
         this.mc.method_1507(new ad(this, this.setting));
      }

      super.mouseClicked(n, n2, n3);
   }

   public void onGuiClose() {
      this.currentColor = null;
      this.hoverAnimation = 0.0F;
      super.onGuiClose();
   }

   // $FF: synthetic method
   private static String d833(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 2404 + var3 & 255);
      }

      return new String(var2);
   }
}
