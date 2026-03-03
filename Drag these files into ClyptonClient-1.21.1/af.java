import java.util.Base64;
import java.util.function.Predicate;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1820;
import net.minecraft.class_1829;
import net.minecraft.class_1831;
import net.minecraft.class_2202;
import net.minecraft.class_2211;
import net.minecraft.class_2397;
import net.minecraft.class_2680;
import net.minecraft.class_3481;

public final class af extends bf {
   private final ff antiBreak = new ff(db.of(d688("n7GUiMKhloCHjA==")), true);
   private final gn antiBreakPercentage = new gn(db.of(d688("n7GUiMKhloCHjMi5j5mPiICbkZaX")), 1.0D, 100.0D, 5.0D, 1.0D);
   private boolean isToolSwapping;
   private int keybindCounter;
   private int selectedToolSlot;

   public af() {
      super(db.of(d688("n6qUjsK3i4qK")), db.of(d688("k7CElI6GxJGOhpzJi56YgoOOhJiRkpiZj9eLjpOPn5WbjCB1bSNmYHVzKH1lZGA=")), -1, hk.MISC);
      this.addSettings(new ab[]{this.antiBreak, this.antiBreakPercentage});
   }

   public void onEnable() {
      super.onEnable();
   }

   public void onDisable() {
      super.onDisable();
   }

   @cp
   public void onTick(hm event) {
      if (this.keybindCounter <= 0 && this.isToolSwapping && this.selectedToolSlot != -1) {
         gp.swap(this.selectedToolSlot);
         this.isToolSwapping = false;
      } else {
         --this.keybindCounter;
      }

   }

   @cp
   public void handleAttackBlockEvent(fs attackBlockEvent) {
      class_2680 getBlockState = mc.field_1687.method_8320(attackBlockEvent.pos);
      class_1799 getBlockEntity = mc.field_1724.method_6047();
      double n = -1.0D;
      this.selectedToolSlot = -1;

      for(int i = 0; i < 9; ++i) {
         double a = calculateToolEfficiency(mc.field_1724.method_31548().method_5438(i), getBlockState, (itemStack) -> {
            return !this.isToolBreakingSoon(itemStack);
         });
         if (a >= 0.0D && a > n) {
            this.selectedToolSlot = i;
            n = a;
         }
      }

      if (this.selectedToolSlot != -1 && n > calculateToolEfficiency(getBlockEntity, getBlockState, (itemStack2) -> {
         return !this.isToolBreakingSoon(itemStack2);
      }) || this.isToolBreakingSoon(getBlockEntity) || !isToolItemStack(getBlockEntity)) {
         gp.swap(this.selectedToolSlot);
      }

      class_1799 method_8322 = mc.field_1724.method_6047();
      if (this.isToolBreakingSoon(method_8322) && isToolItemStack(method_8322)) {
         mc.field_1690.field_1886.method_23481(false);
         attackBlockEvent.cancel();
      }

   }

   public static double calculateToolEfficiency(class_1799 itemStack, class_2680 blockState, Predicate<class_1799> predicate) {
      if (predicate.test(itemStack) && isToolItemStack(itemStack)) {
         return !itemStack.method_7951(blockState) && (!(itemStack.method_7909() instanceof class_1829) || !(blockState.method_26204() instanceof class_2211) && !(blockState.method_26204() instanceof class_2202)) && (!(itemStack.method_7909() instanceof class_1820) || !(blockState.method_26204() instanceof class_2397)) && !blockState.method_26164(class_3481.field_15481) ? -1.0D : 0.0D + (double)(itemStack.method_7924(blockState) * 1000.0F);
      } else {
         return -1.0D;
      }
   }

   public static boolean isToolItemStack(class_1799 itemStack) {
      return isToolItem(itemStack.method_7909());
   }

   public static boolean isToolItem(class_1792 item) {
      return item instanceof class_1831 || item instanceof class_1820;
   }

   private boolean isToolBreakingSoon(class_1799 itemStack) {
      return this.antiBreak.getValue() && itemStack.method_7936() - itemStack.method_7919() < itemStack.method_7936() * this.antiBreakPercentage.getIntValue() / (244 ^ 144);
   }

   // $FF: synthetic method
   private static String d688(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 7390 + var3 & 255);
      }

      return new String(var2);
   }
}
