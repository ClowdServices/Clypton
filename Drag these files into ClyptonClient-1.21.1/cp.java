import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Base64;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface cp {
   bp priority() default bp.NORMAL;

   // $FF: synthetic method
   private static String d771(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 10525 - 306 + var3 & 255);
      }

      return new String(var2);
   }
}
