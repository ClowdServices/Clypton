import java.util.Base64;

public final class bo {
   private double value;
   private final double end;

   public bo(double end) {
      this.value = end;
      this.end = end;
   }

   public void animate(double speed, double target) {
      if (gm.animationMode.isMode(gu.NORMAL)) {
         this.value = ac.approachValue((float)speed, this.value, target);
      } else if (gm.animationMode.isMode(gu.POSITIVE)) {
         this.value = ac.smoothStep(speed, this.value, target);
      } else if (gm.animationMode.isMode(gu.OFF)) {
         this.value = target;
      }

   }

   public double getAnimation() {
      return this.value;
   }

   public void setAnimation(double factor) {
      this.value = ac.smoothStep(factor, this.value, this.end);
   }

   // $FF: synthetic method
   private static String d116(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 9353 + var3 & 255);
      }

      return new String(var2);
   }
}
