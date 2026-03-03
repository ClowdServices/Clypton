import java.util.Base64;

public class ae {
   public final String name;
   public final String id;
   public final long timestamp;

   public ae(String name, String id, long timestamp) {
      this.name = name;
      this.id = id;
      this.timestamp = timestamp;
   }

   // $FF: synthetic method
   private static String d604(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 6813 + var3 & 255);
      }

      return new String(var2);
   }
}
