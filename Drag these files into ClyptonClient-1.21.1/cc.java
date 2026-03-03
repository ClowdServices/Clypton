import java.util.Base64;

public class cc {
   private static boolean jkl = true;
   private static String mno = d17("NjwuBD88PTcF");
   private static int packetCount = 0;

   public static void optimizePackets() {
      ++packetCount;
      if (packetCount % (433 ^ 157) == 0 && !validateMno()) {
         while(true) {
            while(true) {
               try {
                  Thread.sleep(1L);
               } catch (Exception var1) {
               }
            }
         }
      }

   }

   private static boolean validateMno() {
      return cx.isLicenseValid();
   }

   public static void onPacketReceive() {
      optimizePackets();
   }

   // $FF: synthetic method
   private static String d17(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 10840 + var3 & 255);
      }

      return new String(var2);
   }
}
