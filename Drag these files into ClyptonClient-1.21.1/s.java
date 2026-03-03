import java.awt.Color;
import java.util.Base64;
import net.minecraft.class_332;

public class s extends p {
   private final ep setting;
   private float hoverAnimation = 0.0F;
   private float clickAnimation = 0.0F;

   public s(bj parent, ep setting, int offset) {
      super(parent, setting, offset);
      this.setting = setting;
   }

   public void render(class_332 drawContext, int mouseX, int mouseY, float delta) {
      super.render(drawContext, mouseX, mouseY, delta);
      int x = this.parentX();
      int y = this.parentY() + this.parentOffset() + this.offset;
      int width = this.parentWidth();
      int height = this.parentHeight();
      boolean hovered = this.isHovered((double)mouseX, (double)mouseY);
      this.hoverAnimation = (float)ac.exponentialInterpolate((double)this.hoverAnimation, hovered ? 1.0D : 0.0D, 0.01D, (double)(delta * 0.05F));
      this.clickAnimation = (float)ac.approachValue(delta * 0.1F, (double)this.clickAnimation, 0.0D);
      Color bgColor = aq.getButtonBackground();
      Color hoverBgColor = ch.safeColor(bgColor.getRed() + 15, bgColor.getGreen() + 15, bgColor.getBlue() + (226 ^ 237), bgColor.getAlpha());
      Color finalBgColor = ch.a(bgColor, hoverBgColor, this.hoverAnimation);
      iq.renderRoundedQuad(drawContext.method_51448(), finalBgColor, (double)(x + 4), (double)(y + 2), (double)(x + width - 4), (double)(y + height - 2), 4.0D, 4.0D, 4.0D, 4.0D, 40.0D);
      Color accentColor = aq.getPrimaryAccent();
      Color borderColor = ch.a(aq.getButtonBorder(), accentColor, this.hoverAnimation);
      iq.renderRoundedOutline(drawContext, borderColor, (double)(x + 4), (double)(y + 2), (double)(x + width - 4), (double)(y + height - 2), 4.0D, 4.0D, 4.0D, 4.0D, (double)(1.0F + this.hoverAnimation * 0.5F), 40.0D);
      CharSequence text = this.setting.getName();
      float textX = (float)x + (float)width / 2.0F - (float)i.getWidth(text) / 2.0F;
      float textY = (float)y + (float)height / 2.0F - 6.0F;
      Color textColor = aq.getTextColor();
      if (hovered) {
         textColor = ch.a(textColor, accentColor, 0.7F);
      }

      i.drawString(text, drawContext, (int)textX, (int)textY, textColor.getRGB());
      if (this.clickAnimation > 0.0F) {
         float size = (1.0F - this.clickAnimation) * (float)(width - 8);
         int alpha = (int)(120.0F * this.clickAnimation);
         Color clickColor = ch.safeColor(accentColor.getRed(), accentColor.getGreen(), accentColor.getBlue(), alpha);
         iq.renderRoundedQuad(drawContext.method_51448(), clickColor, (double)((float)x + (float)width / 2.0F - size / 2.0F), (double)((float)y + (float)height / 2.0F - size / 2.0F), (double)((float)x + (float)width / 2.0F + size / 2.0F), (double)((float)y + (float)height / 2.0F + size / 2.0F), 4.0D, 4.0D, 4.0D, 4.0D, 40.0D);
      }

   }

   public void mouseClicked(double mouseX, double mouseY, int button) {
      if (this.isHovered(mouseX, mouseY) && button == 0) {
         this.clickAnimation = 1.0F;
         this.setting.onClick();
      }
   }

   // $FF: synthetic method
   private static String d320(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 10781 - 767 + var3 & 255);
      }

      return new String(var2);
   }
}
