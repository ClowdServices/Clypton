import java.util.Base64;

public enum bz {
   Overworld,
   Nether,
   End;

   public bz opposite() {
      bz var10000;
      switch(this.ordinal()) {
      case 0:
         var10000 = Nether;
         break;
      case 1:
         var10000 = Overworld;
         break;
      default:
         var10000 = this;
      }

      return var10000;
   }

   // $FF: synthetic method
   private static bz[] $values() {
      return new bz[]{Overworld, Nether, End};
   }

   // $FF: synthetic method
   private static String d672(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 3638 - 716 + var3 & 255);
      }

      return new String(var2);
   }
}
