import java.util.Base64;

public abstract class ab {
   private CharSequence name;
   public CharSequence description;

   public ab(CharSequence a) {
      this.name = a;
   }

   public void getDescription(CharSequence a) {
      this.name = a;
   }

   public CharSequence getName() {
      return this.name;
   }

   public CharSequence getDescription() {
      return this.description;
   }

   public ab setDescription(CharSequence description) {
      this.description = description;
      return this;
   }

   // $FF: synthetic method
   private static String d158(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 3177 - 870 + var3 & 255);
      }

      return new String(var2);
   }
}
