import java.lang.reflect.Method;
import java.util.Base64;

class fm {
   private final Object instance;
   private final Method method;

   fm(Object instance, Method method) {
      this.instance = instance;
      this.method = method;
   }

   void invoke(Object event) throws Exception {
      this.method.invoke(this.instance, event);
   }

   // $FF: synthetic method
   private static String d926(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 4369 + var3 & 255);
      }

      return new String(var2);
   }
}
