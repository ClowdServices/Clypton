import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.Base64;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_5321;
import net.minecraft.class_6880;
import net.minecraft.class_9304;
import net.minecraft.class_9334;

public class dq {
   public static boolean hasEnchantment(class_1799 itemStack, class_5321<?> registryKey) {
      if (itemStack.method_7960()) {
         return false;
      } else {
         Object2IntArrayMap<?> enchantmentMap = new Object2IntArrayMap();
         populateEnchantmentMap(itemStack, enchantmentMap);
         return containsEnchantment(enchantmentMap, registryKey);
      }
   }

   private static boolean containsEnchantment(Object2IntMap<?> enchantmentMap, class_5321<?> registryKey) {
      ObjectIterator var2 = enchantmentMap.keySet().iterator();

      Object enchantment;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         enchantment = var2.next();
      } while(!((class_6880)enchantment).method_40225(registryKey));

      return true;
   }

   public static void populateEnchantmentMap(class_1799 itemStack, Object2IntMap enchantmentMap) {
      enchantmentMap.clear();
      if (!itemStack.method_7960()) {
         Set enchantments;
         if (itemStack.method_7909() == class_1802.field_8598) {
            enchantments = ((class_9304)itemStack.method_57824(class_9334.field_49643)).method_57539();
         } else {
            enchantments = itemStack.method_58657().method_57539();
         }

         Iterator var3 = enchantments.iterator();

         while(var3.hasNext()) {
            Object enchantmentEntry = var3.next();
            enchantmentMap.put(((Entry)enchantmentEntry).getKey(), ((Entry)enchantmentEntry).getIntValue());
         }
      }

   }

   // $FF: synthetic method
   private static String d734(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 8415 + var3 & (1 ^ 254));
      }

      return new String(var2);
   }
}
