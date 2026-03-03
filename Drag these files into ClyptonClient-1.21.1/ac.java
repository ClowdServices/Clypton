import java.util.Base64;

public final class ac {
   public static double roundToNearest(double value, double step) {
      return step * (double)Math.round(value / step);
   }

   public static double smoothStep(double factor, double start, double end) {
      double max = Math.max(0.0D, Math.min(1.0D, factor));
      return start + (end - start) * max * max * (3.0D - 2.0D * max);
   }

   public static double approachValue(float speed, double current, double target) {
      double ceil = Math.ceil(Math.abs(target - current) * (double)speed);
      return current < target ? Math.min(current + (double)((int)ceil), target) : Math.max(current - (double)((int)ceil), target);
   }

   public static double linearInterpolate(double factor, double start, double end) {
      return start + (end - start) * factor;
   }

   public static double exponentialInterpolate(double start, double end, double base, double exponent) {
      return linearInterpolate((double)(1.0F - (float)Math.pow(base, exponent)), start, end);
   }

   public static double clampValue(double value, double min, double max) {
      return Math.max(min, Math.min(value, max));
   }

   public static int clampInt(int value, int min, int max) {
      return Math.max(min, Math.min(value, max));
   }

   // $FF: synthetic method
   private static String d671(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 7838 + var3 & 255);
      }

      return new String(var2);
   }
}
