import java.lang.reflect.Method;
import java.util.Base64;

public class ic {
   private final Object instance;
   private final Method method;
   private final bp priority;

   public ic(Object instance, Method method, bp priority) {
      this.instance = instance;
      this.method = method;
      this.priority = priority;
   }

   public void invoke(bl event) throws Throwable {
      this.method.invoke(this.instance, event);
   }

   public Object getInstance() {
      return this.instance;
   }

   public bp getPriority() {
      return this.priority;
   }

   // $FF: synthetic method
   private static String d921(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 7162 + var3 & 255);
      }

      return new String(var2);
   }
}
