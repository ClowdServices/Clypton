import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class cv {
   private static final cv INSTANCE = new cv();
   private final Map<Class<?>, List<fm>> listeners = new ConcurrentHashMap();

   private cv() {
   }

   public static cv getInstance() {
      return INSTANCE;
   }

   public static void post(Object event) {
      INSTANCE.postEvent(event);
   }

   public void postEvent(Object event) {
      if (event != null) {
         Class<?> eventClass = event.getClass();
         List<fm> eventListeners = (List)this.listeners.get(eventClass);
         if (eventListeners != null) {
            Iterator var4 = eventListeners.iterator();

            while(var4.hasNext()) {
               fm listener = (fm)var4.next();

               try {
                  listener.invoke(event);
               } catch (Exception var7) {
                  System.err.println("Error invoking event listener: " + var7.getMessage());
                  var7.printStackTrace();
               }
            }
         }

      }
   }

   public void register(Object object) {
      if (object != null) {
         Method[] var2 = object.getClass().getDeclaredMethods();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Method method = var2[var4];
            if (method.isAnnotationPresent(cp.class) && method.getParameterCount() == 1) {
               Class<?> eventClass = method.getParameterTypes()[0];
               method.setAccessible(true);
               ((List)this.listeners.computeIfAbsent(eventClass, (k) -> {
                  return new ArrayList();
               })).add(new fm(object, method));
            }
         }

      }
   }

   public void unregister(Object object) {
      if (object != null) {
         this.listeners.values().forEach((list) -> {
            list.removeIf((listener) -> {
               return listener.instance == object;
            });
         });
      }
   }

   public void clear() {
      this.listeners.clear();
   }

   // $FF: synthetic method
   private static String d190(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 4445 + var3 & (241 ^ 14));
      }

      return new String(var2);
   }
}
