import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

class iy {
   private final HashMap<String, Object> a = new HashMap();

   void put(String key, Object value) {
      if (value != null) {
         this.a.put(key, value);
      }

   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      Set<Entry<String, Object>> entrySet = this.a.entrySet();
      sb.append("{");
      int n = 0;
      Iterator var4 = entrySet.iterator();

      while(var4.hasNext()) {
         Entry<String, Object> next = (Entry)var4.next();
         Object value = next.getValue();
         sb.append(this.a((String)next.getKey())).append(":");
         if (value instanceof String) {
            sb.append(this.a(String.valueOf(value)));
         } else if (value instanceof Integer) {
            sb.append(Integer.valueOf(String.valueOf(value)));
         } else if (value instanceof Boolean) {
            sb.append(value);
         } else if (value instanceof iy) {
            sb.append(value);
         } else if (value.getClass().isArray()) {
            sb.append("[");
            int length = Array.getLength(value);

            for(int i = 0; i < length; ++i) {
               StringBuilder append = sb.append(Array.get(value, i).toString());
               String str;
               if (i != length - 1) {
                  str = ",";
               } else {
                  str = "";
               }

               append.append(str);
            }

            sb.append("]");
         }

         ++n;
         sb.append(n == entrySet.size() ? "}" : ",");
      }

      return sb.toString();
   }

   private String a(String s) {
      return "\"" + s;
   }
}
