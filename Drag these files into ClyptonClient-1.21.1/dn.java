import java.util.Base64;
import net.minecraft.class_1792;

public class dn extends ab {
   private final class_1792 defaultValue;
   private class_1792 value;

   public dn(CharSequence name, class_1792 value) {
      super(name);
      this.value = value;
      this.defaultValue = value;
   }

   public class_1792 getItem() {
      return this.value;
   }

   public void setItem(class_1792 a) {
      this.value = a;
   }

   public class_1792 getDefaultValue() {
      return this.defaultValue;
   }

   public void resetValue() {
      this.value = this.defaultValue;
   }

   // $FF: synthetic method
   private static String d632(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 6209 + var3 & (24 ^ 231));
      }

      return new String(var2);
   }
}
