import java.util.Base64;

public final class gn extends ab {
   private final double min;
   private final double max;
   private double value;
   private final double format;
   private final double defaultValue;

   public gn(CharSequence charSequence, double min, double max, double defaultValue, double format) {
      super(charSequence);
      this.min = min;
      this.max = max;
      this.value = defaultValue;
      this.format = format;
      this.defaultValue = defaultValue;
   }

   public double getValue() {
      return this.value;
   }

   public double getDefaultValue() {
      return this.defaultValue;
   }

   public double getFormat() {
      return this.format;
   }

   public double getMin() {
      return this.min;
   }

   public double getMax() {
      return this.max;
   }

   public int getIntValue() {
      return (int)this.value;
   }

   public float getFloatValue() {
      return (float)this.value;
   }

   public double getDoubleValue() {
      return this.value;
   }

   public long getLongValue() {
      return (long)this.value;
   }

   public void getValue(double b) {
      this.setValue(b);
   }

   public gn getValue(CharSequence charSequence) {
      super.setDescription(charSequence);
      return this;
   }

   public void setValue(double newValue) {
      double step = 1.0D / this.format;
      double clamped = Math.max(this.min, Math.min(this.max, newValue));
      this.value = (double)Math.round(clamped * step) / step;
   }

   // $FF: synthetic method
   private static String d818(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 3498 - 301 + var3 & 255);
      }

      return new String(var2);
   }
}
