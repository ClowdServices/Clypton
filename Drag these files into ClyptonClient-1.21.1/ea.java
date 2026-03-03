import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Enumeration;

public class ea {
   public static String getHWID() {
      try {
         StringBuilder hwid = new StringBuilder();
         hwid.append(getMACAddress());
         hwid.append(System.getProperty(d32("hZjCg4+ClQ==")));
         hwid.append(System.getProperty(d32("hZjCjJyMmA==")));
         hwid.append(System.getProperty(d32("hZjCm4udg5idnQ==")));
         hwid.append(System.getProperty(d32("n5iJn8CBkZyX")));

         try {
            hwid.append(InetAddress.getLocalHost().getHostName());
         } catch (Exception var2) {
         }

         return hashString(hwid.toString());
      } catch (Exception var3) {
         var3.printStackTrace();
         String var10000 = System.getProperty(d32("n5iJn8CBkZyX"));
         return hashString(var10000 + System.getProperty(d32("hZjCg4+ClQ==")));
      }
   }

   private static String getMACAddress() {
      try {
         Enumeration networkInterfaces = NetworkInterface.getNetworkInterfaces();

         while(networkInterfaces.hasMoreElements()) {
            NetworkInterface ni = (NetworkInterface)networkInterfaces.nextElement();
            byte[] mac = ni.getHardwareAddress();
            if (mac != null && mac.length > 0) {
               StringBuilder sb = new StringBuilder();
               byte[] var4 = mac;
               int var5 = mac.length;

               for(int var6 = 0; var6 < var5; ++var6) {
                  byte b = var4[var6];
                  sb.append(String.format(d32("z9vetQ=="), b));
               }

               return sb.toString();
            }
         }
      } catch (Exception var8) {
         var8.printStackTrace();
      }

      return d32("v6Wno6G4vq6/src=");
   }

   private static String hashString(String input) {
      try {
         MessageDigest digest = MessageDigest.getInstance(d32("uaOtwNzaxg=="));
         byte[] hash = digest.digest(input.getBytes(d32("v7+qwNY=")));
         StringBuilder hexString = new StringBuilder();
         byte[] var4 = hash;
         int var5 = hash.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            byte b = var4[var6];
            String hex = Integer.toHexString(255 & b);
            if (hex.length() == 1) {
               hexString.append('0');
            }

            hexString.append(hex);
         }

         return hexString.toString().toUpperCase();
      } catch (Exception var9) {
         var9.printStackTrace();
         return input;
      }
   }

   // $FF: synthetic method
   private static String d32(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 7934 - 788 + var3 & 255);
      }

      return new String(var2);
   }
}
