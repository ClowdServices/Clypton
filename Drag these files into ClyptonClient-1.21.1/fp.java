import java.util.Base64;

public final class fp extends ab {
   private final boolean moduleKey;
   private final int defaultValue;
   private boolean listening;
   private int value;

   public fp(CharSequence name, int value, boolean isModule) {
      super(name);
      this.value = value;
      this.defaultValue = value;
      this.moduleKey = isModule;
   }

   public boolean isModuleKey() {
      return this.moduleKey;
   }

   public boolean isListening() {
      return this.listening;
   }

   public int getDefaultValue() {
      return this.defaultValue;
   }

   public void setListening(boolean listening) {
      this.listening = listening;
   }

   public int getValue() {
      return this.value;
   }

   public void setValue(int a) {
      this.value = a;
   }

   public void toggleListening() {
      this.listening = !this.listening;
   }

   public fp setDescription(CharSequence charSequence) {
      super.setDescription(charSequence);
      return this;
   }

   // $FF: synthetic method
   private static String d123(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 5209 - 817 + var3 & (120 ^ 135));
      }

      return new String(var2);
   }
}
