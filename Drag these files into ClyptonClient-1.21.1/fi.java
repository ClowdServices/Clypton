import java.awt.Color;
import java.util.Base64;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_332;

public final class fi extends p {
   private final dn setting;
   private float hoverAnimation = 0.0F;
   private Color currentColor;
   private final Color TEXT_COLOR = aq.getTextColor();
   private final Color HOVER_COLOR = aq.getHoverColor();
   private final Color ITEM_BG = aq.getButtonBackground();
   private final Color ITEM_BORDER = aq.getButtonBorder();
   private final float CORNER_RADIUS = 4.0F;
   private final float HOVER_ANIMATION_SPEED = 0.25F;

   public fi(bj moduleButton, ab setting, int n) {
      super(moduleButton, setting, n);
      this.setting = (dn)setting;
   }

   public void onUpdate() {
      Color mainColor = bi.getMainColor(255, this.parent.settings.indexOf(this));
      if (this.currentColor == null) {
         this.currentColor = ch.safeColor(mainColor.getRed(), mainColor.getGreen(), mainColor.getBlue(), 0);
      } else {
         this.currentColor = ch.safeColor(mainColor.getRed(), mainColor.getGreen(), mainColor.getBlue(), this.currentColor.getAlpha());
      }

      if (this.currentColor.getAlpha() != (74 ^ 181)) {
         this.currentColor = ch.a(0.05F, 11 ^ 244, this.currentColor);
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
      int n5 = this.parentY() + this.parentOffset() + this.offset + this.parentHeight() / 2;
      i.drawString(String.valueOf(this.setting.getName()), drawContext, n4, n5 - 8, this.TEXT_COLOR.getRGB());
      int n6 = n4 + i.getWidth(String.valueOf(this.setting.getName()) + ": ") + 5;
      int n7 = n5 - (102 ^ 109);
      iq.renderRoundedQuad(drawContext.method_51448(), this.ITEM_BORDER, (double)n6, (double)n7, (double)(n6 + 22), (double)(n7 + (78 ^ 88)), 4.0D, 4.0D, 4.0D, 4.0D, 50.0D);
      iq.renderRoundedQuad(drawContext.method_51448(), this.ITEM_BG, (double)(n6 + 1), (double)(n7 + 1), (double)(n6 + 22 - 1), (double)(n7 + 22 - 1), 3.5D, 3.5D, 3.5D, 3.5D, 50.0D);
      class_1792 a = this.setting.getItem();
      if (a != null && a != class_1802.field_8162) {
         drawContext.method_51427(new class_1799(a), n6 + 3, n7 + 3);
      } else {
         i.drawCenteredString("?", drawContext, n6 + 11 - 1, n7 + 4, ch.safeColor(168 ^ 62, 150, 150, 88 ^ 144).getRGB());
      }

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

   public void mouseClicked(double n, double n2, int n3) {
      if (this.isHovered(n, n2) && n3 == 0) {
         this.mc.method_1507(new ee(this, this.setting));
      }

      super.mouseClicked(n, n2, n3);
   }

   public void onGuiClose() {
      this.currentColor = null;
      this.hoverAnimation = 0.0F;
      super.onGuiClose();
   }

   // $FF: synthetic method
   private static String d514(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 7145 - 582 + var3 & 255);
      }

      return new String(var2);
   }
}
