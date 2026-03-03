import java.awt.Color;
import java.util.Base64;

public class hu {
   public static float easeInOutQuad(float progress) {
      return progress < 0.5F ? 2.0F * progress * progress : -1.0F + (4.0F - 2.0F * progress) * progress;
   }

   public static float easeOutCubic(float progress) {
      float p = progress - 1.0F;
      return p * p * p + 1.0F;
   }

   public static float easeInOutCubic(float progress) {
      return progress < 0.5F ? 4.0F * progress * progress * progress : 1.0F - (float)Math.pow((double)(-2.0F * progress + 2.0F), 3.0D) / 2.0F;
   }

   public static int colorLerp(int colorA, int colorB, float progress) {
      progress = Math.min(1.0F, Math.max(0.0F, progress));
      int aA = colorA >> 24 & (169 ^ 86);
      int aR = colorA >> (160 ^ 176) & (253 ^ 2);
      int aG = colorA >> 8 & 255;
      int aB = colorA & 255;
      int bA = colorB >> (138 ^ 146) & 255;
      int bR = colorB >> 16 & 255;
      int bG = colorB >> 8 & (65 ^ 190);
      int bB = colorB & 255;
      int rA = clamp((int)((float)aA + (float)(bA - aA) * progress));
      int rR = clamp((int)((float)aR + (float)(bR - aR) * progress));
      int rG = clamp((int)((float)aG + (float)(bG - aG) * progress));
      int rB = clamp((int)((float)aB + (float)(bB - aB) * progress));
      return rA << (89 ^ 65) | rR << (49 ^ 33) | rG << 8 | rB;
   }

   public static Color intToColor(int rgbValue) {
      int a = clamp(rgbValue >> 24 & 255);
      int r = clamp(rgbValue >> 16 & 255);
      int g = clamp(rgbValue >> (76 ^ 68) & 255);
      int b = clamp(rgbValue & 255);
      return ch.safeColor(r, g, b, a);
   }

   private static int clamp(int value) {
      return Math.max(0, Math.min(255, value));
   }

   // $FF: synthetic method
   private static String d840(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 4284 - 880 + var3 & (119 ^ 136));
      }

      return new String(var2);
   }
}
