import java.util.Base64;

public enum it {
   LENGTH(d311("86Wvpbes")),
   ALPHABETICAL(d311("/qyxqqKmoLKuq6im")),
   CATEGORY(d311("/KG1p6Srt78="));

   private final String displayName;

   private it(String displayName) {
      this.displayName = displayName;
   }

   public String toString() {
      return this.displayName;
   }

   // $FF: synthetic method
   private static it[] $values() {
      return new it[]{LENGTH, ALPHABETICAL, CATEGORY};
   }

   // $FF: synthetic method
   private static String d311(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 10175 + var3 & (130 ^ 125));
      }

      return new String(var2);
   }
}
