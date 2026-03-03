import java.util.Base64;

public class bk implements bl {
   private double deltaX;
   private double deltaY;
   private final double defaultDeltaX;
   private final double defaultDeltaY;

   public bk(double deltaX, double deltaY) {
      this.deltaX = deltaX;
      this.deltaY = deltaY;
      this.defaultDeltaX = deltaX;
      this.defaultDeltaY = deltaY;
   }

   public double getDeltaX() {
      return this.deltaX;
   }

   public void setDeltaX(double deltaX) {
      this.deltaX = deltaX;
   }

   public double getDeltaY() {
      return this.deltaY;
   }

   public void setDeltaY(double deltaY) {
      this.deltaY = deltaY;
   }

   public double getDefaultDeltaX() {
      return this.defaultDeltaX;
   }

   public double getDefaultDeltaY() {
      return this.defaultDeltaY;
   }

   public void addDeltaX(double deltaX) {
      this.deltaX += deltaX;
   }

   public void addDeltaY(double deltaY) {
      this.deltaY += deltaY;
   }

   // $FF: synthetic method
   private static String d437(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 10934 + var3 & 255);
      }

      return new String(var2);
   }
}
