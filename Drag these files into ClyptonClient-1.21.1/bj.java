import client.astralux.Astralux;
import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.class_310;
import net.minecraft.class_332;

public final class bj {
   public List<p> settings = new ArrayList();
   public ah parent;
   public bf module;
   public int offset;
   public boolean extended;
   public int settingOffset;
   public Color currentColor;
   public Color currentAlpha;
   public bo animation = new bo(0.0D);
   private final float CORNER_RADIUS = 6.0F;
   private float hoverAnimation = 0.0F;
   private float enabledAnimation = 0.0F;
   private float expandAnimation = 0.0F;
   private float clickAnimation = 0.0F;
   private float glowPulse = 0.0F;
   private long lastInteractionTime;
   private float settingsExpandProgress = 0.0F;

   public bj(ah parent, bf module, int offset) {
      this.parent = parent;
      this.module = module;
      this.offset = offset;
      this.extended = false;
      this.settingOffset = parent.getHeight();
      this.lastInteractionTime = System.currentTimeMillis();

      for(Iterator var4 = module.getSettings().iterator(); var4.hasNext(); this.settingOffset += parent.getHeight()) {
         Object next = var4.next();
         if (next instanceof ff) {
            this.settings.add(new hs(this, (ab)next, this.settingOffset));
         } else if (next instanceof gn) {
            this.settings.add(new dr(this, (ab)next, this.settingOffset));
         } else if (next instanceof cu) {
            this.settings.add(new dx(this, (ab)next, this.settingOffset));
         } else if (next instanceof fp) {
            this.settings.add(new ey(this, (ab)next, this.settingOffset));
         } else if (next instanceof bu) {
            this.settings.add(new el(this, (ab)next, this.settingOffset));
         } else if (next instanceof fr) {
            this.settings.add(new cb(this, (ab)next, this.settingOffset));
         } else if (next instanceof dn) {
            this.settings.add(new fi(this, (ab)next, this.settingOffset));
         } else if (next instanceof il) {
            if (this.module instanceof dg) {
               this.settings.add(new au(this, (ab)next, ((dg)this.module).getBlockColors(), this.settingOffset));
            } else {
               this.settings.add(new au(this, (ab)next, (Map)null, this.settingOffset));
            }
         } else if (next instanceof gt) {
            if (this.module instanceof if) {
               this.settings.add(new bv(this, (ab)next, ((if)this.module).getStorageColors(), this.settingOffset));
            } else {
               this.settings.add(new bv(this, (ab)next, (Map)null, this.settingOffset));
            }
         } else if (next instanceof ay) {
            this.settings.add(new av(this, (ab)next, this.settingOffset));
         } else if (next instanceof ep) {
            this.settings.add(new s(this, (ep)next, this.settingOffset));
         }
      }

   }

   public void render(class_332 drawContext, int n, int n2, float n3) {
      if (this.parent.getY() + this.offset <= class_310.method_1551().method_22683().method_4507()) {
         Iterator iterator = this.settings.iterator();

         while(iterator.hasNext()) {
            ((p)iterator.next()).onUpdate();
         }

         this.updateAnimations(n, n2, n3);
         int x = this.parent.getX();
         int n4 = this.parent.getY() + this.offset;
         int width = this.parent.getWidth();
         int height = this.parent.getHeight();
         this.renderButtonBackground(drawContext, x, n4, width, height);
         this.renderIndicator(drawContext, x, n4, height);
         this.renderModuleInfo(drawContext, x, n4, width, height);
         if (this.extended) {
            this.renderSettings(drawContext, n, n2, n3);
         }

         if (this.isHovered((double)n, (double)n2) && !this.parent.dragging) {
            Astralux.INSTANCE.GUI.setTooltip(this.module.getDescription(), n + 10, n2 + 10);
         }

      }
   }

   private void updateAnimations(int n, int n2, float n3) {
      float n4 = n3 * 0.05F;
      float n5;
      if (this.isHovered((double)n, (double)n2) && !this.parent.dragging) {
         n5 = 1.0F;
      } else {
         n5 = 0.0F;
      }

      this.hoverAnimation = (float)ac.exponentialInterpolate((double)this.hoverAnimation, (double)n5, 0.05000000074505806D, (double)n4);
      float n6;
      if (this.module.isEnabled()) {
         n6 = 1.0F;
      } else {
         n6 = 0.0F;
      }

      this.enabledAnimation = (float)ac.exponentialInterpolate((double)this.enabledAnimation, (double)n6, 0.004999999888241291D, (double)n4);
      this.enabledAnimation = (float)ac.clampValue((double)this.enabledAnimation, 0.0D, 1.0D);
      if (this.clickAnimation > 0.0F) {
         this.clickAnimation = (float)ac.exponentialInterpolate((double)this.clickAnimation, 0.0D, 0.1D, (double)n4);
      }

      if (this.module.isEnabled()) {
         this.glowPulse += n3 * 0.02F;
      }

      float targetExpand = this.extended ? 1.0F : 0.0F;
      this.settingsExpandProgress = (float)ac.approachValue(n3 * 0.15F, (double)this.settingsExpandProgress, (double)targetExpand);
   }

   private void renderButtonBackground(class_332 drawContext, int n, int n2, int n3, int n4) {
      Color themePrimary = this.parent.parent.currentTheme.primaryAccent;
      Color themeBg = this.parent.parent.currentTheme.panelBackground;
      long time = System.currentTimeMillis();
      int bgColorInt = hu.colorLerp(ch.safeColor(themeBg.getRed() - 8, themeBg.getGreen() - 8, themeBg.getBlue() - (141 ^ 133), themeBg.getAlpha() - 15).getRGB(), ch.safeColor(themeBg.getRed() + 12, themeBg.getGreen() + 12, themeBg.getBlue() + (79 ^ 67), themeBg.getAlpha()).getRGB(), this.hoverAnimation);
      Color bgColor = ch.clamp(hu.intToColor(bgColorInt));
      boolean isLast = this.parent.moduleButtons.get(this.parent.moduleButtons.size() - 1) == this;
      if (isLast && !this.extended) {
         iq.renderRoundedQuad(drawContext.method_51448(), bgColor, (double)n, (double)n2, (double)(n + n3), (double)(n2 + n4), 0.0D, 0.0D, 6.0D, 6.0D, 40.0D);
      } else if (isLast && this.extended) {
         iq.renderRoundedQuad(drawContext.method_51448(), bgColor, (double)n, (double)n2, (double)(n + n3), (double)(n2 + n4), 0.0D, 0.0D, 0.0D, 0.0D, 40.0D);
      } else {
         iq.renderRoundedQuad(drawContext.method_51448(), bgColor, (double)n, (double)n2, (double)(n + n3), (double)(n2 + n4), 0.0D, 0.0D, 0.0D, 0.0D, 40.0D);
      }

      int outlineAlpha;
      if (this.clickAnimation > 0.1F) {
         outlineAlpha = (int)(80.0F * this.clickAnimation);
         float rippleSize = 1.0F + (1.0F - this.clickAnimation) * 15.0F;
         iq.renderRoundedQuad(drawContext.method_51448(), ch.safeColor(themePrimary.getRed(), themePrimary.getGreen(), themePrimary.getBlue(), outlineAlpha), (double)((float)n - rippleSize), (double)((float)n2 - rippleSize), (double)((float)(n + n3) + rippleSize), (double)((float)(n2 + n4) + rippleSize), 6.0D, 6.0D, 6.0D, 6.0D, 40.0D);
      }

      int glitchOffset;
      Color limeGreen;
      switch(this.parent.parent.currentTheme) {
      case CYBERWAVE:
         if (this.hoverAnimation > 0.1F || this.module.isEnabled()) {
            outlineAlpha = (int)(100.0F * (this.hoverAnimation + 0.2F * this.enabledAnimation));
            limeGreen = ch.safeColor(255, 20, 111 ^ 252, outlineAlpha);
            iq.renderRoundedOutline(drawContext, limeGreen, (double)(n - 2), (double)(n2 - 2), (double)(n + n3 + 2), (double)(n2 + n4 + 2), 6.0D, 6.0D, 6.0D, 6.0D, 2.0D, 40.0D);
            glitchOffset = (int)(time / 20L % (long)n4);
            drawContext.method_25294(n, n2 + glitchOffset, n + n3, n2 + glitchOffset + 1, ch.safeColor(255, 20, 147, 199 ^ 217).getRGB());
         }
         break;
      case QUANTUM_FLUX:
         if (this.hoverAnimation > 0.1F) {
            outlineAlpha = (int)(80.0F * this.hoverAnimation);
            limeGreen = ch.safeColor(0, 255, 155 ^ 255, outlineAlpha);
            iq.renderRoundedOutline(drawContext, limeGreen, (double)(n - 1), (double)(n2 - 1), (double)(n + n3 + 1), (double)(n2 + n4 + 1), 6.0D, 6.0D, 6.0D, 6.0D, 1.5D, 40.0D);
            if (this.module.isEnabled() && time % 300L < 50L) {
               glitchOffset = (int)(Math.random() * 3.0D) - 1;
               drawContext.method_25294(n + glitchOffset, n2, n + 3 + glitchOffset, n2 + n4, ch.safeColor(0, 255, 118 ^ 18, 128 ^ 168).getRGB());
            }
         }
         break;
      case MATRIX:
         if (this.module.isEnabled()) {
            for(outlineAlpha = 0; outlineAlpha < 3; ++outlineAlpha) {
               int rainY = (int)((time / 30L + (long)(outlineAlpha * 10)) % (long)n4);
               drawContext.method_25294(n + outlineAlpha * (129 ^ 149), n2 + rainY, n + outlineAlpha * (175 ^ 187) + 2, n2 + rainY + (172 ^ 164), ch.safeColor(0, 255, 195 ^ 167, 156 ^ 160).getRGB());
            }
         }

         if (this.hoverAnimation > 0.15F) {
            outlineAlpha = (int)(80.0F + 100.0F * this.hoverAnimation);
            iq.renderRoundedOutline(drawContext, ch.safeColor(0, 36 ^ 219, 100, outlineAlpha), (double)n, (double)n2, (double)(n + n3), (double)(n2 + n4), 6.0D, 6.0D, 6.0D, 6.0D, (double)(1.0F + 0.6F * this.hoverAnimation), 40.0D);
         }
         break;
      default:
         if (this.hoverAnimation > 0.15F) {
            outlineAlpha = (int)(60.0F + 90.0F * this.hoverAnimation);
            iq.renderRoundedOutline(drawContext, ch.safeColor(themePrimary.getRed(), themePrimary.getGreen(), themePrimary.getBlue(), outlineAlpha), (double)n, (double)n2, (double)(n + n3), (double)(n2 + n4), 6.0D, 6.0D, 6.0D, 6.0D, (double)(1.0F + 0.6F * this.hoverAnimation), 40.0D);
         }
      }

   }

   private void renderIndicator(class_332 drawContext, int n, int n2, int n3) {
      Color color;
      if (this.module.isEnabled()) {
         color = ch.safeColor(255, 255, 255, 255);
      } else {
         color = ch.safeColor(100, 100, 173 ^ 213, 192 ^ 164);
      }

      float n4 = 5.0F * this.enabledAnimation;
      if (n4 > 0.1F) {
         float pulse;
         if (this.parent.parent.currentTheme == ai.QUANTUM_FLUX && this.module.isEnabled()) {
            pulse = System.nanoTime() / 10000000L % 20L > 10L ? 1.0F : 0.7F;
            color = ch.safeColor((int)((float)color.getRed() * pulse), (int)((float)color.getGreen() * pulse), (int)((float)color.getBlue() * pulse), color.getAlpha());
         } else if (this.parent.parent.currentTheme == ai.CYBERWAVE && this.module.isEnabled()) {
            pulse = (float)(0.800000011920929D + 0.20000000298023224D * Math.sin((double)this.glowPulse));
            color = ch.safeColor((int)((float)color.getRed() * pulse), (int)((float)color.getGreen() * pulse), (int)((float)color.getBlue() * pulse), color.getAlpha());
         }

         Color blendedColor = ch.a(ch.safeColor(100, 100, 120, 100), color, this.enabledAnimation);
         blendedColor = ch.clamp(blendedColor);
         if (this.module.isEnabled()) {
            int glowAlpha = (int)(40.0F * this.enabledAnimation);
            iq.renderRoundedQuad(drawContext.method_51448(), ch.safeColor(blendedColor.getRed(), blendedColor.getGreen(), blendedColor.getBlue(), glowAlpha), (double)(n - 2), (double)(n2 + 1), (double)((float)n + n4 + 2.0F), (double)(n2 + n3 - 1), 2.0D, 2.0D, 2.0D, 2.0D, 60.0D);
         }

         iq.renderRoundedQuad(drawContext.method_51448(), blendedColor, (double)n, (double)(n2 + 2), (double)((float)n + n4), (double)(n2 + n3 - 2), 1.5D, 1.5D, 1.5D, 1.5D, 60.0D);
      }

   }

   private void renderModuleInfo(class_332 drawContext, int n, int n2, int n3, int n4) {
      Color themeAccent = this.parent.parent.currentTheme.primaryAccent;
      Color themeBg = this.parent.parent.currentTheme.panelBackground;
      long time = System.currentTimeMillis();
      int textColor = hu.colorLerp(ch.safeColor(98 ^ 194, 160, 170, 217 ^ 38).getRGB(), themeAccent.getRGB(), this.enabledAnimation);
      Color clampedTextColor = ch.clamp(hu.intToColor(textColor));
      int n5;
      if (this.enabledAnimation > 0.5F) {
         n5 = (int)(30.0F * this.enabledAnimation);
         i.drawString(this.module.getName(), drawContext, n + (104 ^ 99), n2 + n4 / 2 - 7, ch.safeColor(themeAccent.getRed(), themeAccent.getGreen(), themeAccent.getBlue(), n5).getRGB());
      }

      i.drawString(this.module.getName(), drawContext, n + 12, n2 + n4 / 2 - (145 ^ 151), clampedTextColor.getRGB());
      n5 = n + n3 - 40;
      int n6 = n2 + n4 / 2 - 6;
      int bgColorInt = hu.colorLerp(ch.safeColor(themeBg.getRed() + (182 ^ 164), themeBg.getGreen() + 18, themeBg.getBlue() + 18, 130).getRGB(), ch.safeColor(themeBg.getRed() - 3, themeBg.getGreen() - 3, themeBg.getBlue() - 3, 102 ^ 240).getRGB(), this.enabledAnimation);
      Color toggleBg = ch.clamp(hu.intToColor(bgColorInt));
      int outlineAlpha;
      if (this.enabledAnimation > 0.3F) {
         outlineAlpha = (int)(50.0F * this.enabledAnimation);
         iq.renderRoundedQuad(drawContext.method_51448(), ch.safeColor(themeAccent.getRed(), themeAccent.getGreen(), themeAccent.getBlue(), outlineAlpha), (double)(n5 - 2), (double)(n6 - 2), (double)((float)n5 + 30.0F), (double)((float)n6 + 14.0F), 7.0D, 7.0D, 7.0D, 7.0D, 40.0D);
      }

      iq.renderRoundedQuad(drawContext.method_51448(), toggleBg, (double)n5, (double)n6, (double)((float)n5 + 28.0F), (double)((float)n6 + 12.0F), 6.0D, 6.0D, 6.0D, 6.0D, 40.0D);
      if (this.enabledAnimation > 0.2F) {
         outlineAlpha = (int)(80.0F + 100.0F * this.enabledAnimation);
         iq.renderRoundedOutline(drawContext, ch.safeColor(themeAccent.getRed(), themeAccent.getGreen(), themeAccent.getBlue(), outlineAlpha), (double)n5, (double)n6, (double)((float)n5 + 28.0F), (double)((float)n6 + 12.0F), 6.0D, 6.0D, 6.0D, 6.0D, 1.0D, 40.0D);
      }

      float n7 = (float)n5 + 6.0F + 16.0F * this.enabledAnimation;
      int toggleColorInt = hu.colorLerp(ch.safeColor(140, 69 ^ 201, 155, 112 ^ 143).getRGB(), themeAccent.getRGB(), this.enabledAnimation);
      Color toggleColor = ch.clamp(hu.intToColor(toggleColorInt));
      float chevronRotation;
      if (this.parent.parent.currentTheme == ai.QUANTUM_FLUX && this.enabledAnimation > 0.2F) {
         chevronRotation = System.nanoTime() / 5000000L % 100L < 80L ? 1.0F : 0.8F;
         toggleColor = ch.safeColor((int)((float)toggleColor.getRed() * chevronRotation), (int)((float)toggleColor.getGreen() * chevronRotation), (int)((float)toggleColor.getBlue() * chevronRotation), toggleColor.getAlpha());
      }

      if (this.module.isEnabled()) {
         int knobGlowAlpha = (int)(60.0D + 40.0D * Math.sin((double)this.glowPulse));
         iq.renderCircle(drawContext.method_51448(), ch.safeColor(toggleColor.getRed(), toggleColor.getGreen(), toggleColor.getBlue(), knobGlowAlpha), (double)n7, (double)((float)n6 + 6.0F), 7.0D, 59 ^ 53);
      }

      iq.renderCircle(drawContext.method_51448(), toggleColor, (double)n7, (double)((float)n6 + 6.0F), 5.0D, 14);
      if (!this.module.getSettings().isEmpty()) {
         chevronRotation = this.settingsExpandProgress * 180.0F;
         int chevronAlpha = (int)(120.0F + 80.0F * this.hoverAnimation);
         String chevron = this.extended ? "▼" : "▶";
         i.drawString(chevron, drawContext, n + n3 - (195 ^ 204), n2 + n4 / 2 - 4, ch.safeColor(180, 180, 172 ^ 18, chevronAlpha).getRGB());
      }

   }

   private void renderSettings(class_332 drawContext, int n, int n2, float n3) {
      int n4 = this.parent.getY() + this.offset + this.parent.getHeight();
      double animation = this.animation.getAnimation();
      RenderSystem.enableScissor(this.parent.getX(), Astralux.mc.method_22683().method_4507() - (n4 + (int)animation), this.parent.getWidth(), (int)animation);
      Iterator iterator = this.settings.iterator();

      while(iterator.hasNext()) {
         ((p)iterator.next()).render(drawContext, n, n2 - n4, n3);
      }

      this.renderSliderControls(drawContext);
      RenderSystem.disableScissor();
   }

   private void renderSliderControls(class_332 drawContext) {
      Iterator var2 = this.settings.iterator();

      while(var2.hasNext()) {
         p next = (p)var2.next();
         if (next instanceof dr) {
            dr numberBox = (dr)next;
            this.renderModernSliderKnob(drawContext, (double)next.parentX() + Math.max(numberBox.lerpedOffsetX, 2.5D), (double)(next.parentY() + numberBox.offset + next.parentOffset()) + 27.5D, numberBox.currentColor1);
         } else if (next instanceof cb) {
            this.renderModernSliderKnob(drawContext, (double)next.parentX() + Math.max(((cb)next).lerpedOffsetMinX, 2.5D), (double)(next.parentY() + next.offset + next.parentOffset()) + 27.5D, ((cb)next).accentColor1);
            this.renderModernSliderKnob(drawContext, (double)next.parentX() + Math.max(((cb)next).lerpedOffsetMaxX, 2.5D), (double)(next.parentY() + next.offset + next.parentOffset()) + 27.5D, ((cb)next).accentColor1);
         }
      }

   }

   private void renderModernSliderKnob(class_332 drawContext, double n, double n2, Color color) {
      iq.renderCircle(drawContext.method_51448(), ch.safeColor(0, 0, 0, 154 ^ 226), n, n2, 8.0D, 18);
      iq.renderCircle(drawContext.method_51448(), ch.safeColor(color.getRed(), color.getGreen(), color.getBlue(), 189 ^ 237), n, n2, 7.0D, 233 ^ 251);
      iq.renderCircle(drawContext.method_51448(), color, n, n2, 5.5D, 18 ^ 2);
      iq.renderCircle(drawContext.method_51448(), ch.safeColor(255, 192 ^ 63, 255, 159 ^ 197), n, n2 - 1.0D, 3.0D, 220 ^ 208);
   }

   public void onExtend() {
      for(Iterator iterator = this.parent.moduleButtons.iterator(); iterator.hasNext(); ((bj)iterator.next()).extended = false) {
      }

   }

   public void keyPressed(int n, int n2, int n3) {
      Iterator iterator = this.settings.iterator();

      while(iterator.hasNext()) {
         ((p)iterator.next()).keyPressed(n, n2, n3);
      }

   }

   public void mouseDragged(double n, double n2, int n3, double n4, double n5) {
      if (this.extended) {
         Iterator iterator = this.settings.iterator();

         while(iterator.hasNext()) {
            ((p)iterator.next()).mouseDragged(n, n2, n3, n4, n5);
         }
      }

   }

   public void mouseClicked(double n, double n2, int button) {
      if (this.isHovered(n, n2)) {
         this.clickAnimation = 1.0F;
         if (button == 0) {
            int n4 = this.parent.getX() + this.parent.getWidth() - (251 ^ 229);
            int n5 = this.parent.getY() + this.offset + this.parent.getHeight() / 2 - 3;
            if (n >= (double)n4 && n <= (double)(n4 + 12) && n2 >= (double)n5 && n2 <= (double)(n5 + (254 ^ 248))) {
               this.module.toggle();
               this.lastInteractionTime = System.currentTimeMillis();
            } else if (!this.module.getSettings().isEmpty() && n > (double)(this.parent.getX() + this.parent.getWidth() - (223 ^ 198))) {
               if (!this.extended) {
                  this.onExtend();
               }

               this.extended = !this.extended;
            } else {
               this.module.toggle();
               this.lastInteractionTime = System.currentTimeMillis();
            }
         } else if (button == 1) {
            if (this.module.getSettings().isEmpty()) {
               return;
            }

            if (!this.extended) {
               this.onExtend();
            }

            this.extended = !this.extended;
         }
      }

      if (this.extended) {
         Iterator var8 = this.settings.iterator();

         while(var8.hasNext()) {
            p setting = (p)var8.next();
            setting.mouseClicked(n, n2, button);
         }
      }

   }

   public void onGuiClose() {
      this.currentAlpha = null;
      this.currentColor = null;
      this.hoverAnimation = 0.0F;
      this.clickAnimation = 0.0F;
      float enabledAnimation;
      if (this.module.isEnabled()) {
         enabledAnimation = 1.0F;
      } else {
         enabledAnimation = 0.0F;
      }

      this.enabledAnimation = enabledAnimation;
      Iterator iterator = this.settings.iterator();

      while(iterator.hasNext()) {
         ((p)iterator.next()).onGuiClose();
      }

   }

   public void mouseReleased(double n, double n2, int n3) {
      Iterator iterator = this.settings.iterator();

      while(iterator.hasNext()) {
         ((p)iterator.next()).mouseReleased(n, n2, n3);
      }

   }

   public boolean isHovered(double n, double n2) {
      return n > (double)this.parent.getX() && n < (double)(this.parent.getX() + this.parent.getWidth()) && n2 > (double)(this.parent.getY() + this.offset) && n2 < (double)(this.parent.getY() + this.offset + this.parent.getHeight());
   }

   // $FF: synthetic method
   private static String d885(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 8863 + var3 & 255);
      }

      return new String(var2);
   }
}
