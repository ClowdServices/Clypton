import java.awt.Color;
import java.util.Base64;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_2596;
import net.minecraft.class_2824;
import net.minecraft.class_332;
import net.minecraft.class_4587;
import net.minecraft.class_640;
import net.minecraft.class_7532;

public final class ap extends bf {
   private final gn xPosition = new gn(db.of("X"), 0.0D, 1920.0D, 500.0D, 1.0D);
   private final gn yPosition = new gn(db.of("Y"), 0.0D, 1080.0D, 500.0D, 1.0D);
   private final ff timeoutEnabled = (new ff(db.of(d255("Mg4FDAUeGA==")), true)).setDescription(db.of(d255("MgYaDg8fTAUbC1AGGx8YVRIeCxgKCxkcDF/h5/bm9qW3t6j67+jj4+r8")));
   private final gn fadeSpeed = (new gn(db.of(d255("IAYMDEo4HAgLCw==")), 5.0D, 30.0D, 15.0D, 1.0D)).getValue(db.of(d255("NRcNDA5LAwtODh4YHxIAHBkZCw==")));
   private final Color primaryColor = ch.safeColor(255, 50, 100, 255);
   private final Color backgroundColor = ch.safeColor(0, 0, 0, 48 ^ 159);
   private long lastAttackTime = 0L;
   public static float fadeProgress = 1.0F;
   private float currentHealth = 0.0F;
   private y hudHandler;

   public ap() {
      super(db.of(d255("MgYaDg8fTCU7Kw==")), db.of(d255("Ig4bGQYKFR5OCxUFExoYEBJXERccFA4QHwvp7uyj5efp8vyp8+T5/6778eP19uC14f7s8bro6OTy+g==")), -1, hk.RENDER);
      this.addSettings(new ab[]{this.xPosition, this.yPosition, this.timeoutEnabled, this.fadeSpeed});
   }

   public void onEnable() {
      super.onEnable();
   }

   public void onDisable() {
      super.onDisable();
   }

   @cp
   public void a(ez render2DEvent) {
      class_332 a = render2DEvent.context;
      int f = this.xPosition.getIntValue();
      int f2 = this.yPosition.getIntValue();
      float g = this.fadeSpeed.getFloatValue();
      Color h = this.primaryColor;
      Color i = this.backgroundColor;
      iq.unscaledProjection();
      boolean b = mc.field_1724.method_6052() != null && mc.field_1724.method_6052() instanceof class_1657 && mc.field_1724.method_6052().method_5805();
      boolean b2 = !this.timeoutEnabled.getValue() || System.currentTimeMillis() - this.lastAttackTime <= 10000L;
      float n;
      if (b && b2) {
         n = 0.0F;
      } else {
         n = 1.0F;
      }

      fadeProgress = iq.fast(fadeProgress, n, g);
      if (fadeProgress < 0.99F && b) {
         class_1309 getAttacking = mc.field_1724.method_6052();
         class_640 playerListEntry = mc.method_1562().method_2871(getAttacking.method_5667());
         class_4587 matrices = a.method_51448();
         matrices.method_22903();
         float n2 = 1.0F - fadeProgress;
         float n3 = 0.8F + 0.2F * n2;
         matrices.method_46416((float)f, (float)f2, 0.0F);
         matrices.method_22905(n3, n3, 1.0F);
         matrices.method_46416((float)(-f), (float)(-f2), 0.0F);
         this.currentHealth = iq.fast(this.currentHealth, getAttacking.method_6032() + getAttacking.method_6067(), g * 0.5F);
         this.a(a, f, f2, (class_1657)getAttacking, playerListEntry, n2, h, i);
         matrices.method_22909();
      }

      iq.scaledProjection();
   }

   private void a(class_332 drawContext, int n, int n2, class_1657 playerEntity, class_640 playerListEntry, float n3, Color color, Color color2) {
      class_4587 matrices = drawContext.method_51448();
      iq.renderRoundedQuad(matrices, ch.safeColor(color.getRed(), color.getGreen(), color.getBlue(), (int)(50.0F * n3)), (double)(n - 5), (double)(n2 - 5), (double)(n + 300 + 5), (double)(n2 + 180 + 5), 15.0D, 15.0D, 15.0D, 15.0D, 30.0D);
      iq.renderRoundedQuad(matrices, ch.safeColor(color2.getRed(), color2.getGreen(), color2.getBlue(), (int)((float)color2.getAlpha() * n3)), (double)n, (double)n2, (double)(n + 300), (double)(n2 + 180), 10.0D, 10.0D, 10.0D, 10.0D, 20.0D);
      Color color3 = ch.safeColor(color.getRed(), color.getGreen(), color.getBlue(), (int)((float)color.getAlpha() * n3));
      iq.renderRoundedQuad(matrices, color3, (double)(n + 20), (double)n2, (double)(n + (352 ^ 76) - (249 ^ 237)), (double)(n2 + 3), 0.0D, 0.0D, 0.0D, 0.0D, 10.0D);
      iq.renderRoundedQuad(matrices, color3, (double)(n + 20), (double)(n2 + (204 ^ 120) - 3), (double)(n + 300 - (155 ^ 143)), (double)(n2 + 180), 0.0D, 0.0D, 0.0D, 0.0D, 10.0D);
      if (playerListEntry != null) {
         iq.renderRoundedQuad(matrices, ch.safeColor(21 ^ 11, 30, 30, (int)(200.0F * n3)), (double)(n + 15), (double)(n2 + 15), (double)(n + 85), (double)(n2 + 85), 5.0D, 5.0D, 5.0D, 5.0D, 10.0D);
         class_7532.method_44443(drawContext, playerListEntry.method_52810().comp_1626(), n + 25, n2 + 25, 50);
         i.drawString(playerEntity.method_5477().getString(), drawContext, n + (177 ^ 213), n2 + 25, ch.a((int)((float)(System.currentTimeMillis() % 1000L) / 1000.0F), 1).getRGB());
         i.drawString(ac.roundToNearest((double)playerEntity.method_5739(mc.field_1724), 1.0D) + " blocks away", drawContext, n + (222 ^ 186), n2 + 45, Color.WHITE.getRGB());
         iq.renderRoundedQuad(matrices, ch.safeColor(60, 47 ^ 19, 138 ^ 182, (int)(200.0F * n3)), (double)(n + (132 ^ 139)), (double)(n2 + (31 ^ 64)), (double)(n + 300 - (44 ^ 35)), (double)(n2 + (195 ^ 173)), 5.0D, 5.0D, 5.0D, 5.0D, 10.0D);
         float b = this.currentHealth / playerEntity.method_6063();
         float n4 = 270.0F * Math.min(1.0F, b);
         iq.renderRoundedQuad(matrices, this.a(b * (float)(0.800000011920929D + 0.20000000298023224D * Math.sin((double)System.currentTimeMillis() / 300.0D)), n3), (double)(n + (200 ^ 199)), (double)(n2 + 95), (double)(n + 15 + (int)n4), (double)(n2 + 110), 5.0D, 5.0D, 5.0D, 5.0D, 10.0D);
         int var10000 = Math.round(this.currentHealth);
         String s = var10000 + "/" + Math.round(playerEntity.method_6063()) + " HP";
         i.drawString(s, drawContext, n + 15 + (int)n4 / 2 - i.getWidth(s) / 2, n2 + (219 ^ 132), Color.WHITE.getRGB());
         int n5 = n2 + 120;
         this.a(drawContext, n + 15, n5, 80, 241 ^ 220, d255("Ni4mLg=="), playerListEntry.method_2959() + "ms", this.a(playerListEntry.method_2959(), n3), color3, n3);
         String s2;
         if (playerListEntry != null) {
            s2 = d255("NispMC85");
         } else {
            s2 = d255("JCg8");
         }

         Color color4;
         if (playerListEntry != null) {
            color4 = ch.safeColor(100, 152 ^ 103, 100, (int)(255.0F * n3));
         } else {
            color4 = ch.safeColor(255, 45 ^ 73, 100, (int)(255.0F * n3));
         }

         this.a(drawContext, n + 100 + 5, n5, 14 ^ 94, 45, d255("Mj44LA=="), s2, color4, color3, n3);
         if (playerEntity.field_6235 > 0) {
            this.a(drawContext, n + 200 + 5, n5, 71 ^ 23, 55 ^ 26, d255("LjI6PQ=="), playerEntity.field_6235.makeConcatWithConstants<invokedynamic>(playerEntity.field_6235), this.b(playerEntity.field_6235, n3), color3, n3);
         } else {
            this.a(drawContext, n + (234 ^ 34) + 5, n5, 169 ^ 249, 45, d255("LjI6PQ=="), "No", ch.safeColor(150, 150, 150, (int)(255.0F * n3)), color3, n3);
         }
      } else {
         i.drawString(d255("JCg8SS4uOCgtOzU1"), drawContext, n + 150 - i.getWidth(d255("JCg8SS4uOCgtOzU1")) / 2, n2 + 90, ch.safeColor(255, 50, 140 ^ 190, 255).getRGB());
      }

   }

   private void a(class_332 drawContext, int n, int n2, int n3, int n4, String s, String s2, Color color, Color color2, float n5) {
      class_4587 matrices = drawContext.method_51448();
      iq.renderRoundedQuad(matrices, color2, (double)n, (double)n2, (double)(n + n3), (double)(n2 + 3), 3.0D, 3.0D, 0.0D, 0.0D, 6.0D);
      iq.renderRoundedQuad(matrices, ch.safeColor(30, 30, 99 ^ 125, (int)(200.0F * n5)), (double)n, (double)(n2 + 3), (double)(n + n3), (double)(n2 + n4), 0.0D, 0.0D, 3.0D, 3.0D, 6.0D);
      i.drawString(s, drawContext, n + n3 / 2 - i.getWidth(s) / 2, n2 + 5, ch.safeColor(200, 200, 200, (int)(255.0F * n5)).getRGB());
      i.drawString(s2, drawContext, n + n3 / 2 - i.getWidth(s2) / 2, n2 + n4 - 17, color.getRGB());
   }

   private Color a(float n, float n2) {
      Color color;
      if (n > 0.75F) {
         color = ch.a(ch.safeColor(100, 255, 47 ^ 75, 93 ^ 162), ch.safeColor(255, 125 ^ 130, 100, 255), (1.0F - n) * 4.0F);
      } else if (n > 0.25F) {
         color = ch.a(ch.safeColor(255, 255, 100, 218 ^ 37), ch.safeColor(214 ^ 41, 100, 100, 255), (0.75F - n) * 2.0F);
      } else {
         float n3;
         if (n < 0.1F) {
            n3 = (float)(0.699999988079071D + 0.30000001192092896D * Math.sin((double)System.currentTimeMillis() / 200.0D));
         } else {
            n3 = 1.0F;
         }

         color = ch.safeColor((int)(255.0F * n3), (int)(100.0F * n3), (int)(100.0F * n3), 235 ^ 20);
      }

      return ch.safeColor(color.getRed(), color.getGreen(), color.getBlue(), (int)((float)color.getAlpha() * n2));
   }

   private Color a(int n, float n2) {
      Color color;
      if (n < 50) {
         color = ch.safeColor(100, 255, 100, 255);
      } else if (n < 100) {
         color = ch.a(ch.safeColor(100, 35 ^ 220, 100, 255), ch.safeColor(179 ^ 76, 255, 100, 90 ^ 165), (float)(n - 50) / 50.0F);
      } else if (n < 200) {
         color = ch.a(ch.safeColor(89 ^ 166, 255, 100, 110 ^ 145), ch.safeColor(255, 150, 50, 255), (float)(n - 100) / 100.0F);
      } else {
         color = ch.a(ch.safeColor(139 ^ 116, 129 ^ 23, 50, 255), ch.safeColor(48 ^ 207, 80, 80, 255), Math.min(1.0F, (float)(n - 200) / 300.0F));
      }

      return ch.safeColor(color.getRed(), color.getGreen(), color.getBlue(), (int)((float)color.getAlpha() * n2));
   }

   private Color b(int n, float n2) {
      double n3 = 0.699999988079071D + 0.30000001192092896D * Math.sin((double)System.currentTimeMillis() / 150.0D);
      float min = Math.min(1.0F, (float)n / 10.0F);
      Color color = ch.safeColor(246 ^ 9, (int)(50.0F + 100.0F * (1.0F - min)), (int)(50.0F + 100.0F * (1.0F - min)), 117 ^ 138);
      return ch.safeColor((int)((float)color.getRed() * (float)n3), (int)((float)color.getGreen() * (float)n3), (int)((float)color.getBlue() * (float)n3), (int)(255.0F * n2));
   }

   @cp
   public void a(ib packetEvent) {
      class_2596 var3 = packetEvent.packet;
      if (var3 instanceof class_2824) {
         class_2824 playerInteractEntityC2SPacket = (class_2824)var3;
         if (this.hudHandler == null) {
            this.hudHandler = new y(this);
         }

         if (this.hudHandler.isAttackPacket(playerInteractEntityC2SPacket)) {
            this.lastAttackTime = System.currentTimeMillis();
         }
      }

   }

   // $FF: synthetic method
   private static String d255(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 2406 + var3 & (40 ^ 215));
      }

      return new String(var2);
   }
}
