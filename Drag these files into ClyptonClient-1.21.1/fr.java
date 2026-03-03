import java.util.Base64;
import java.util.Random;

public class fr extends ab {
   private final double min;
   private final double max;
   private final double step;
   private final double defaultMin;
   private final double defaultMax;
   private double currentMin;
   private double currentMax;

   public fr(CharSequence charSequence, double min, double max, double step, double defaultMin, double defaultMax) {
      super(charSequence);
      this.min = min;
      this.max = max;
      this.step = step;
      this.currentMin = defaultMin;
      this.currentMax = defaultMax;
      this.defaultMin = defaultMin;
      this.defaultMax = defaultMax;
   }

   public int getMinInt() {
      return (int)this.currentMin;
   }

   public float getMinFloat() {
      return (float)this.currentMin;
   }

   public long getMinLong() {
      return (long)this.currentMin;
   }

   public int getMaxInt() {
      return (int)this.currentMax;
   }

   public float getMaxFloat() {
      return (float)this.currentMax;
   }

   public long getMaxLong() {
      return (long)this.currentMax;
   }

   public double getMinValue() {
      return this.min;
   }

   public double getMaxValue() {
      return this.max;
   }

   public double getCurrentMin() {
      return this.currentMin;
   }

   public double getCurrentMax() {
      return this.currentMax;
   }

   public double getDefaultMin() {
      return this.defaultMin;
   }

   public double getDefaultMax() {
      return this.defaultMax;
   }

   public double getStep() {
      return this.step;
   }

   public double getRandomDoubleInRange() {
      return this.getCurrentMax() > this.getCurrentMin() ? (new Random()).nextDouble(this.getCurrentMin(), this.getCurrentMax()) : this.getCurrentMin();
   }

   public int getRandomIntInRange() {
      return this.getCurrentMax() > this.getCurrentMin() ? (new Random()).nextInt(this.getMinInt(), this.getMaxInt()) : this.getMinInt();
   }

   public float getRandomFloatInRange() {
      return this.getCurrentMax() > this.getCurrentMin() ? (new Random()).nextFloat(this.getMinFloat(), this.getMaxFloat()) : this.getMinFloat();
   }

   public long getRandomLongInRange() {
      return this.getCurrentMax() > this.getCurrentMin() ? (new Random()).nextLong(this.getMinLong(), this.getMaxLong()) : this.getMinLong();
   }

   public void setCurrentMin(double value) {
      double n = 1.0D / this.step;
      this.currentMin = (double)Math.round(Math.max(this.min, Math.min(this.max, value)) * n) / n;
   }

   public void setCurrentMax(double value) {
      double n = 1.0D / this.step;
      this.currentMax = (double)Math.round(Math.max(this.min, Math.min(this.max, value)) * n) / n;
   }

   // $FF: synthetic method
   private static String d244(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 5851 - 764 + var3 & 255);
      }

      return new String(var2);
   }
}
