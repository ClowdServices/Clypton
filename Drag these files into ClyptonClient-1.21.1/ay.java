import java.util.Base64;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.class_1792;

public class ay extends ab {
   private final Set<class_1792> items;

   public ay(CharSequence name, Set<class_1792> defaultItems) {
      super(name);
      this.items = new HashSet(defaultItems);
   }

   public Set<class_1792> getItems() {
      return this.items;
   }

   public int getCount() {
      return this.items.size();
   }

   public void addItem(class_1792 item) {
      this.items.add(item);
   }

   public void removeItem(class_1792 item) {
      this.items.remove(item);
   }

   public boolean contains(class_1792 item) {
      return this.items.contains(item);
   }

   public void clear() {
      this.items.clear();
   }

   // $FF: synthetic method
   private static String d797(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 7437 + var3 & (73 ^ 182));
      }

      return new String(var2);
   }
}
