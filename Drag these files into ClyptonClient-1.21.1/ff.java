import java.util.Base64;

public final class ff extends ab {
   private boolean value;
   private final boolean defaultValue;

   public ff(CharSequence charSequence, boolean value) {
      super(charSequence);
      this.value = value;
      this.defaultValue = value;
   }

   public void toggle() {
      this.setValue(!this.value);
   }

   public void setValue(boolean a) {
      this.value = a;
   }

   public boolean getValue() {
      return this.value;
   }

   public boolean getDefaultValue() {
      return this.defaultValue;
   }

   public ff setDescription(CharSequence charSequence) {
      super.setDescription(charSequence);
      return this;
   }

   // $FF: synthetic method
   private static String d776(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 5664 + var3 & (205 ^ 50));
      }

      return new String(var2);
   }
}
