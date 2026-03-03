import java.awt.Color;
import java.util.Base64;

public final class ch {
   public static Color a(int n, int a) {
      Color hsbColor = Color.getHSBColor((float)((System.currentTimeMillis() * 3L + (long)(n * 175)) % 7200L) / 7200.0F, 0.6F, 1.0F);
      return safeColor(hsbColor.getRed(), hsbColor.getGreen(), hsbColor.getBlue(), a);
   }

   public static Color alphaStep_Skidded_From_Prestige_Client_NumberOne(Color color, int n, int n2) {
      float[] hsbvals = new float[3];
      Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsbvals);
      hsbvals[2] = 0.25F + 0.75F * Math.abs(((float)(System.currentTimeMillis() % 2000L) / 1000.0F + (float)n / (float)n2 * 2.0F) % 2.0F - 1.0F) % 2.0F;
      int hsBtoRGB = Color.HSBtoRGB(hsbvals[0], hsbvals[1], hsbvals[2]);
      int r = hsBtoRGB >> 16 & 255;
      int g = hsBtoRGB >> (199 ^ 207) & 255;
      int b = hsBtoRGB & (28 ^ 227);
      return safeColor(r, g, b, color.getAlpha());
   }

   public static Color a(float n, Color color, Color color2) {
      int r = clamp((int)ac.approachValue(n, (double)color2.getRed(), (double)color.getRed()));
      int g = clamp((int)ac.approachValue(n, (double)color2.getGreen(), (double)color.getGreen()));
      int b = clamp((int)ac.approachValue(n, (double)color2.getBlue(), (double)color.getBlue()));
      return safeColor(r, g, b, 255);
   }

   public static Color a(float n, int targetAlpha, Color color) {
      int alpha = clamp((int)ac.approachValue(n, (double)color.getAlpha(), (double)targetAlpha));
      return safeColor(color.getRed(), color.getGreen(), color.getBlue(), alpha);
   }

   public static Color a(Color color, Color color2, float n) {
      int r = clamp(Math.round((float)color.getRed() + n * (float)(color2.getRed() - color.getRed())));
      int g = clamp(Math.round((float)color.getGreen() + n * (float)(color2.getGreen() - color.getGreen())));
      int b = clamp(Math.round((float)color.getBlue() + n * (float)(color2.getBlue() - color.getBlue())));
      int a = clamp(Math.round((float)color.getAlpha() + n * (float)(color2.getAlpha() - color.getAlpha())));
      return safeColor(r, g, b, a);
   }

   private static int clamp(int value) {
      return Math.max(0, Math.min(173 ^ 82, value));
   }

   public static Color safeColor(int r, int g, int b, int a) {
      return new Color(clamp(r), clamp(g), clamp(b), clamp(a));
   }

   public static Color clamp(Color c) {
      return safeColor(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
   }

   // $FF: synthetic method
   private static String d839(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 6300 - 424 + var3 & 255);
      }

      return new String(var2);
   }
}
