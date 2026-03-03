import client.astralux.Astralux;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import org.reflections.Reflections;

public final class ho {
   private final Map<Class<?>, List<ic>> EVENTS = new HashMap();
   private static final List<Reflections> reflections = new ArrayList();

   public void register(Object o) {
      Method[] declaredMethods = o.getClass().getDeclaredMethods();
      Method[] var3 = declaredMethods;
      int var4 = declaredMethods.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Method method = var3[var5];
         if (method.isAnnotationPresent(cp.class) && method.getParameterCount() == 1 && bl.class.isAssignableFrom(method.getParameterTypes()[0])) {
            this.addListener(o, method, (cp)method.getAnnotation(cp.class));
         }
      }

   }

   private void addListener(Object o, Method method, cp eventListener) {
      Class<?> key = method.getParameterTypes()[0];
      method.setAccessible(true);
      ((List)this.EVENTS.computeIfAbsent(key, (p0) -> {
         return new CopyOnWriteArrayList();
      })).add(new ic(o, method, eventListener.priority()));
      ((List)this.EVENTS.get(key)).sort(Comparator.comparingInt((listener) -> {
         return listener.getPriority().getValue();
      }));
   }

   public void unregister(Object v12) {
      Iterator var2 = this.EVENTS.values().iterator();

      while(var2.hasNext()) {
         List<ic> listeners = (List)var2.next();
         listeners.removeIf((v1) -> {
            return v1.getInstance() == v12;
         });
      }

   }

   public void clear() {
      this.EVENTS.clear();
   }

   public void a(bl event) {
      List<ic> listeners = (List)this.EVENTS.get(event.getClass());
      if (listeners != null) {
         Iterator var3 = listeners.iterator();

         while(var3.hasNext()) {
            ic listener = (ic)var3.next();

            try {
               Object holder = listener.getInstance();
               if ((!(holder instanceof bf) || ((bf)holder).isEnabled()) && (!event.isCancelled() || event instanceof iw)) {
                  listener.invoke(event);
               }
            } catch (Throwable var6) {
               PrintStream var10000 = System.err;
               String var10001 = event.getClass().getSimpleName();
               var10000.println("Error dispatching event " + var10001 + " to " + (listener.getInstance() != null ? listener.getInstance().getClass().getSimpleName() : d432("LDQwMjIpMQ==")));
               var6.printStackTrace(System.err);
            }
         }

      }
   }

   public static void b(bl evt) {
      if (Astralux.INSTANCE != null && Astralux.INSTANCE.getEventBus() != null) {
         Astralux.INSTANCE.getEventBus().a(evt);
      }
   }

   // $FF: synthetic method
   private static String d432(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 3417 + var3 & 255);
      }

      return new String(var2);
   }
}
