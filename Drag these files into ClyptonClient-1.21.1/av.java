import java.awt.Color;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import net.minecraft.class_1792;
import net.minecraft.class_2561;
import net.minecraft.class_332;

public final class av extends p {
   private final ay setting;
   private float hoverAnimation = 0.0F;
   private Color currentColor;
   private final Color TEXT_COLOR = aq.getTextColor();
   private final Color HOVER_COLOR = aq.getHoverColor();
   private final Color BUTTON_BG = aq.getButtonBackground();
   private final Color BUTTON_BORDER = aq.getButtonBorder();
   private final float CORNER_RADIUS = 4.0F;

   public av(bj moduleButton, ab setting, int n) {
      super(moduleButton, setting, n);
      this.setting = (ay)setting;
   }

   public void onUpdate() {
      Color mainColor = bi.getMainColor(255, this.parent.settings.indexOf(this));
      if (this.currentColor == null) {
         this.currentColor = ch.safeColor(mainColor.getRed(), mainColor.getGreen(), mainColor.getBlue(), 0);
      } else {
         this.currentColor = ch.safeColor(mainColor.getRed(), mainColor.getGreen(), mainColor.getBlue(), this.currentColor.getAlpha());
      }

      if (this.currentColor.getAlpha() != (97 ^ 158)) {
         this.currentColor = ch.a(0.05F, 224 ^ 31, this.currentColor);
      }

      super.onUpdate();
   }

   public void render(class_332 drawContext, int n, int n2, float n3) {
      super.render(drawContext, n, n2, n3);
      this.updateAnimations(n, n2, n3);
      if (!this.parent.parent.dragging) {
         drawContext.method_25294(this.parentX(), this.parentY() + this.parentOffset() + this.offset, this.parentX() + this.parentWidth(), this.parentY() + this.parentOffset() + this.offset + this.parentHeight(), ch.safeColor(this.HOVER_COLOR.getRed(), this.HOVER_COLOR.getGreen(), this.HOVER_COLOR.getBlue(), (int)((float)this.HOVER_COLOR.getAlpha() * this.hoverAnimation)).getRGB());
      }

      int x = this.parentX() + 5;
      int y = this.parentY() + this.parentOffset() + this.offset + this.parentHeight() / 2;
      i.drawString(String.valueOf(this.setting.getName()), drawContext, x, y - 8, this.TEXT_COLOR.getRGB());
      int buttonX = this.parentX() + this.parentWidth() - 85;
      int buttonY = y - 11;
      int buttonWidth = true;
      int buttonHeight = 27 ^ 13;
      iq.renderRoundedQuad(drawContext.method_51448(), this.BUTTON_BORDER, (double)buttonX, (double)buttonY, (double)(buttonX + 80), (double)(buttonY + 22), 4.0D, 4.0D, 4.0D, 4.0D, 50.0D);
      iq.renderRoundedQuad(drawContext.method_51448(), this.BUTTON_BG, (double)(buttonX + 1), (double)(buttonY + 1), (double)(buttonX + 80 - 1), (double)(buttonY + 22 - 1), 3.5D, 3.5D, 3.5D, 3.5D, 50.0D);
      String text = this.setting.getCount() + " items";
      i.drawCenteredString(text, drawContext, buttonX + 40, buttonY + (163 ^ 165), ch.safeColor(244 ^ 60, 200, 69 ^ 141, 255).getRGB());
      if (this.isButtonHovered(n, n2, buttonX, buttonY, 80, 194 ^ 212)) {
         this.renderTooltip(drawContext, n, n2);
      }

   }

   private boolean isButtonHovered(int mouseX, int mouseY, int buttonX, int buttonY, int buttonWidth, int buttonHeight) {
      return mouseX >= buttonX && mouseX <= buttonX + buttonWidth && mouseY >= buttonY && mouseY <= buttonY + buttonHeight;
   }

   private void renderTooltip(class_332 drawContext, int mouseX, int mouseY) {
      if (this.setting.getCount() == 0) {
         drawContext.method_51438(this.mc.field_1772, class_2561.method_43470(d696("AiJuJiQ0PyB0JjM7PTouPjg=")), mouseX, mouseY);
      } else {
         List<class_2561> tooltipLines = new ArrayList();
         tooltipLines.add(class_2561.method_43470("§7Selected Items:"));
         Iterator var5 = this.setting.getItems().iterator();

         while(var5.hasNext()) {
            class_1792 item = (class_1792)var5.next();
            String itemName = item.method_7848().getString();
            tooltipLines.add(class_2561.method_43470("§f• §b" + itemName));
         }

         drawContext.method_51434(this.mc.field_1772, tooltipLines, mouseX, mouseY);
      }
   }

   private void updateAnimations(int n, int n2, float n3) {
      float target = this.isHovered((double)n, (double)n2) && !this.parent.parent.dragging ? 1.0F : 0.0F;
      this.hoverAnimation = (float)ac.exponentialInterpolate((double)this.hoverAnimation, (double)target, 0.25D, (double)(n3 * 0.05F));
   }

   public void mouseClicked(double n, double n2, int button) {
      if (this.isHovered(n, n2) && button == 0) {
         this.mc.method_1507(new gg(this, this.setting));
      }

      super.mouseClicked(n, n2, button);
   }

   public void onGuiClose() {
      this.currentColor = null;
      this.hoverAnimation = 0.0F;
      super.onGuiClose();
   }

   // $FF: synthetic method
   private static String d696(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 10828 + var3 & 255);
      }

      return new String(var2);
   }
}
