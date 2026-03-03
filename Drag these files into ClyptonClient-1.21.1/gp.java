import client.astralux.Astralux;
import client.astralux.mixin.ClientPlayerInteractionManagerAccessor;
import java.util.Base64;
import java.util.function.Predicate;
import net.minecraft.class_1661;
import net.minecraft.class_1703;
import net.minecraft.class_1707;
import net.minecraft.class_1792;
import net.minecraft.class_1799;

public final class gp {
   public static void swap(int selectedSlot) {
      if (selectedSlot >= 0 && selectedSlot <= 8) {
         Astralux.mc.field_1724.method_31548().field_7545 = selectedSlot;
         ((ClientPlayerInteractionManagerAccessor)Astralux.mc.field_1761).syncSlot();
      }
   }

   public static boolean swapStack(Predicate<class_1799> predicate) {
      class_1661 getInventory = Astralux.mc.field_1724.method_31548();

      for(int i = 0; i < 9; ++i) {
         if (predicate.test(getInventory.method_5438(i))) {
            getInventory.field_7545 = i;
            return true;
         }
      }

      return false;
   }

   public static boolean swapItem(Predicate<class_1792> predicate) {
      class_1661 getInventory = Astralux.mc.field_1724.method_31548();

      for(int i = 0; i < 9; ++i) {
         if (predicate.test(getInventory.method_5438(i).method_7909())) {
            getInventory.field_7545 = i;
            return true;
         }
      }

      return false;
   }

   public static boolean swap(class_1792 item) {
      return swapItem((item2) -> {
         return item2 == item;
      });
   }

   public static int getSlot(class_1792 obj) {
      class_1703 currentScreenHandler = Astralux.mc.field_1724.field_7512;
      if (Astralux.mc.field_1724.field_7512 instanceof class_1707) {
         int n = 0;

         for(int i = 0; i < ((class_1707)Astralux.mc.field_1724.field_7512).method_17388() * 9; ++i) {
            if (currentScreenHandler.method_7611(i).method_7677().method_7909().equals(obj)) {
               ++n;
            }
         }

         return n;
      } else {
         return 0;
      }
   }

   // $FF: synthetic method
   private static String d128(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 2929 - 793 + var3 & 255);
      }

      return new String(var2);
   }
}
