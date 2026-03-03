import java.awt.Color;
import java.util.Base64;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_4587;

public final class ey extends p {
   private final fp keybind;
   private Color accentColor;
   private Color currentAlpha;
   private float hoverAnimation = 0.0F;
   private float listenAnimation = 0.0F;
   private static final Color TEXT_COLOR = aq.getTextColor();
   private static final Color LISTENING_TEXT_COLOR = aq.getBrightTextColor();
   private static final Color HOVER_COLOR = aq.getHoverColor();
   private static final Color BUTTON_BG_COLOR = aq.getButtonBorder();
   private static final Color BUTTON_ACTIVE_BG_COLOR = aq.getButtonBackground();
   private static final float BUTTON_RADIUS = 4.0F;
   private static final float ANIMATION_SPEED = 0.25F;
   private static final float LISTEN_ANIMATION_SPEED = 0.35F;
   private static final int BUTTON_MIN_WIDTH = 80;
   private static final int BUTTON_PADDING = 16;

   public ey(bj moduleButton, ab setting, int n) {
      super(moduleButton, setting, n);
      this.keybind = (fp)setting;
   }

   public void render(class_332 drawContext, int n, int n2, float n3) {
      super.render(drawContext, n, n2, n3);
      class_4587 matrices = drawContext.method_51448();
      this.updateAnimations(n, n2, n3);
      if (!this.parent.parent.dragging) {
         drawContext.method_25294(this.parentX(), this.parentY() + this.parentOffset() + this.offset, this.parentX() + this.parentWidth(), this.parentY() + this.parentOffset() + this.offset + this.parentHeight(), ch.safeColor(HOVER_COLOR.getRed(), HOVER_COLOR.getGreen(), HOVER_COLOR.getBlue(), (int)((float)HOVER_COLOR.getAlpha() * this.hoverAnimation)).getRGB());
      }

      i.drawString(this.setting.getName(), drawContext, this.parentX() + 5, this.parentY() + this.parentOffset() + this.offset + 9, TEXT_COLOR.getRGB());
      String string;
      if (this.keybind.isListening()) {
         string = d991("Gj4rLT81NTM5cU5P");
      } else {
         string = dd.getKey(this.keybind.getValue()).toString();
      }

      int a = i.getWidth(string);
      int max = Math.max(80, a + (93 ^ 77));
      int n4 = this.parentX() + this.parentWidth() - max - 5;
      int n5 = this.parentY() + this.parentOffset() + this.offset + (this.parentHeight() - 20) / 2;
      iq.renderRoundedQuad(matrices, ch.a(BUTTON_BG_COLOR, BUTTON_ACTIVE_BG_COLOR, this.listenAnimation), (double)n4, (double)n5, (double)(n4 + max), (double)(n5 + 20), 4.0D, 4.0D, 4.0D, 4.0D, 50.0D);
      float a2 = this.listenAnimation * 0.7F;
      float b;
      if (this.isButtonHovered((double)n, (double)n2, n4, n5, max, 20)) {
         b = 0.2F;
      } else {
         b = 0.0F;
      }

      float max2 = Math.max(a2, b);
      if (max2 > 0.0F) {
         iq.renderRoundedQuad(matrices, ch.safeColor(this.accentColor.getRed(), this.accentColor.getGreen(), this.accentColor.getBlue(), (int)((float)this.accentColor.getAlpha() * max2)), (double)n4, (double)n5, (double)(n4 + max), (double)(n5 + 20), 4.0D, 4.0D, 4.0D, 4.0D, 50.0D);
      }

      i.drawString(string, drawContext, n4 + (max - a) / 2, n5 + 6 - 3, ch.a(TEXT_COLOR, LISTENING_TEXT_COLOR, this.listenAnimation).getRGB());
      if (this.keybind.isListening()) {
         iq.renderRoundedQuad(matrices, ch.safeColor(this.accentColor.getRed(), this.accentColor.getGreen(), this.accentColor.getBlue(), (int)((float)this.accentColor.getAlpha() * (float)Math.abs(Math.sin((double)System.currentTimeMillis() / 500.0D)) * 0.3F)), (double)n4, (double)n5, (double)(n4 + max), (double)(n5 + 20), 4.0D, 4.0D, 4.0D, 4.0D, 50.0D);
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
      float n6;
      if (this.keybind.isListening()) {
         n6 = 1.0F;
      } else {
         n6 = 0.0F;
      }

      this.listenAnimation = (float)ac.exponentialInterpolate((double)this.listenAnimation, (double)n6, 0.3499999940395355D, (double)n4);
   }

   private boolean isButtonHovered(double n, double n2, int n3, int n4, int n5, int n6) {
      return n >= (double)n3 && n <= (double)(n3 + n5) && n2 >= (double)n4 && n2 <= (double)(n4 + n6);
   }

   private boolean isInGameplay() {
      class_310 mc = class_310.method_1551();
      return mc.field_1755 == null;
   }

   public void mouseClicked(double n, double n2, int n3) {
      String string;
      if (this.keybind.isListening()) {
         string = d991("Gj4rLT81NTM5cU5P");
      } else {
         string = dd.getKey(this.keybind.getValue()).toString();
      }

      int max = Math.max(80, i.getWidth(string) + 16);
      if (this.isButtonHovered(n, n2, this.parentX() + this.parentWidth() - max - 5, this.parentY() + this.parentOffset() + this.offset + (this.parentHeight() - (99 ^ 119)) / 2, max, 45 ^ 57)) {
         if (!this.keybind.isListening()) {
            if (n3 == 0) {
               this.keybind.toggleListening();
               this.keybind.setListening(true);
            }
         } else {
            if (this.keybind.isModuleKey()) {
               this.parent.module.setKeybind(n3);
            }

            this.keybind.setValue(n3);
            this.keybind.setListening(false);
         }
      }

      super.mouseClicked(n, n2, n3);
   }

   public void keyPressed(int n, int n2, int n3) {
      if (this.keybind.isListening()) {
         if (n == 256) {
            this.keybind.setListening(false);
         } else if (n == 259) {
            if (this.keybind.isModuleKey()) {
               this.parent.module.setKeybind(-1);
            }

            this.keybind.setValue(-1);
            this.keybind.setListening(false);
         } else {
            if (this.keybind.isModuleKey()) {
               this.parent.module.setKeybind(n);
            }

            this.keybind.setValue(n);
            this.keybind.setListening(false);
         }
      } else if (this.isInGameplay()) {
      }

      super.keyPressed(n, n2, n3);
   }

   public boolean isBinding() {
      return this.keybind.isListening();
   }

   public void onUpdate() {
      Color mainColor = bi.getMainColor(255, this.parent.settings.indexOf(this));
      if (this.accentColor == null) {
         this.accentColor = ch.safeColor(mainColor.getRed(), mainColor.getGreen(), mainColor.getBlue(), 0);
      } else {
         this.accentColor = ch.safeColor(mainColor.getRed(), mainColor.getGreen(), mainColor.getBlue(), this.accentColor.getAlpha());
      }

      if (this.accentColor.getAlpha() != 255) {
         this.accentColor = ch.a(0.05F, 255, this.accentColor);
      }

      super.onUpdate();
   }

   public void onGuiClose() {
      this.accentColor = null;
      this.hoverAnimation = 0.0F;
      this.listenAnimation = 0.0F;
      super.onGuiClose();
   }

   // $FF: synthetic method
   private static String d991(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 6998 + var3 & (48 ^ 207));
      }

      return new String(var2);
   }
}
