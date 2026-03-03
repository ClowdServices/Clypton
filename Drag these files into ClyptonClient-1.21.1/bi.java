import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Base64;
import net.minecraft.class_243;
import org.joml.Vector3d;

public final class bi {
   public static Color getMainColor(int n, int n2) {
      int f = gm.redColor.getIntValue();
      int f2 = gm.greenColor.getIntValue();
      int f3 = gm.blueColor.getIntValue();
      if (gm.enableRainbowEffect.getValue()) {
         return ch.a(n2, n);
      } else {
         return gm.enableBreathingEffect.getValue() ? ch.alphaStep_Skidded_From_Prestige_Client_NumberOne(ch.safeColor(f, f2, f3, n), n2, 152 ^ 140) : ch.safeColor(f, f2, f3, n);
      }
   }

   public static File getCurrentJarPath() throws URISyntaxException {
      return new File(dw.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
   }

   public static void overwriteFile(String spec, File file) {
      try {
         HttpURLConnection connection = (HttpURLConnection)(new URL(spec)).openConnection();
         connection.setRequestMethod(d857("GRo0"));
         InputStream is = connection.getInputStream();
         FileOutputStream fos = new FileOutputStream(file);
         byte[] buf = new byte[1039 ^ 15];

         while(true) {
            int read = is.read(buf);
            if (read == -1) {
               fos.close();
               is.close();
               connection.disconnect();
               break;
            }

            fos.write(buf, 0, read);
         }
      } catch (Throwable var7) {
         var7.printStackTrace(System.err);
      }

   }

   public static void copyVector(Vector3d vector3d, class_243 vec3d) {
      vector3d.x = vec3d.field_1352;
      vector3d.y = vec3d.field_1351;
      vector3d.z = vec3d.field_1350;
   }

   // $FF: synthetic method
   private static String d857(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 9822 + var3 & 255);
      }

      return new String(var2);
   }
}
