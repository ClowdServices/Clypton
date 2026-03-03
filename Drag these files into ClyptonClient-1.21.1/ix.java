import java.util.Base64;
import java.util.Iterator;
import java.util.function.Predicate;
import net.minecraft.class_1661;
import net.minecraft.class_1713;
import net.minecraft.class_1723;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_490;

public final class ix extends bf {
   private final gn delay = new gn(db.of(d984("jKymqrU=")), 0.0D, 20.0D, 0.0D, 1.0D);
   private final ff hotbar = (new ff(db.of(d984("gKa+qa2/")), true)).setDescription(db.of(d984("mLy+uOys7ru/pbe+9Ly496G2r6n8tbGrgoCQw4WWxpCNhYY=")));
   private final gn totemSlot = new gn(db.of(d984("nKa+rqHtnaO/pQ==")), 1.0D, 9.0D, 1.0D, 1.0D);
   private final ff autoSwitch = (new ff(db.of(d984("iby+pOyeuaaksro=")), false)).setDescription(db.of(d984("m76jv6+lq7zwpb3zoLqisrX5qbezqf6oiISMw42Lxo6Gn4+FmIKclg==")));
   private final ff forceTotem = (new ff(db.of(d984("jqa4qKntmqCktL8=")), false)).setDescription(db.of(d984("mqy6p62uq7zwuKa2uab2vrb5qbezqf6oiZWKw5CKkoKF")));
   private final ff autoOpen = (new ff(db.of(d984("iby+pOyCvqq+")), false)).setDescription(db.of(d984("iby+pKGsuqazsL6/rfW5p723qfu1s6i6jpWNkZ0=")));
   private final gn stayOpenDuration = new gn(db.of(d984("m72rsuyCvqq+8ZS8pg==")), 0.0D, 20.0D, 0.0D, 1.0D);
   private int delayCounter = -1;
   private int stayOpenCounter = -1;
   private boolean wasInInventory = false;

   public ix() {
      super(db.of(d984("iby+pOyEoLnwhb2nsbg=")), db.of(d984("iby+pKGsuqazsL6/rfWzpq2wqqj8qbGrhYyR")), -1, hk.COMBAT);
      ab[] var10001 = new ab[]{this.delay, this.hotbar, this.totemSlot, this.autoSwitch, this.forceTotem, this.autoOpen, null};
      var10001[169 ^ 175] = this.stayOpenDuration;
      this.addSettings(var10001);
   }

   public void onEnable() {
      this.delayCounter = -1;
      this.stayOpenCounter = -1;
      this.wasInInventory = false;
      super.onEnable();
   }

   @cp
   public void onTick(hm event) {
      boolean isInInventory = mc.field_1755 instanceof class_490 || mc.field_1755 instanceof ax;
      if (this.shouldOpenInventory() && this.autoOpen.getValue() && !isInInventory) {
         mc.method_1507(new ax(mc.field_1724));
         isInInventory = true;
      }

      if (!isInInventory) {
         if (this.wasInInventory) {
            this.delayCounter = -1;
            this.stayOpenCounter = -1;
         }

         this.wasInInventory = false;
      } else {
         this.wasInInventory = true;
         if (this.delayCounter == -1) {
            this.delayCounter = this.delay.getIntValue();
         }

         if (this.stayOpenCounter == -1) {
            this.stayOpenCounter = this.stayOpenDuration.getIntValue();
         }

         if (this.delayCounter > 0) {
            --this.delayCounter;
         } else {
            class_1661 inventory = mc.field_1724.method_31548();
            if (this.autoSwitch.getValue()) {
               inventory.field_7545 = this.totemSlot.getIntValue() - 1;
            }

            if (((class_1799)inventory.field_7544.get(0)).method_7909() != class_1802.field_8288) {
               int totemSlot = this.findTotemSlot();
               if (totemSlot != -1) {
                  mc.field_1761.method_2906(((class_1723)((class_490)mc.field_1755).method_17577()).field_7763, totemSlot, 40, class_1713.field_7791, mc.field_1724);
                  return;
               }
            }

            if (this.hotbar.getValue()) {
               class_1799 mainHandStack = inventory.method_7391();
               boolean shouldReplace = mainHandStack.method_7960() || this.forceTotem.getValue() && mainHandStack.method_7909() != class_1802.field_8288;
               if (shouldReplace) {
                  int totemSlot = this.findTotemSlot();
                  if (totemSlot != -1) {
                     mc.field_1761.method_2906(((class_1723)((class_490)mc.field_1755).method_17577()).field_7763, totemSlot, inventory.field_7545, class_1713.field_7791, mc.field_1724);
                     return;
                  }
               }
            }

            if (this.isTotemEquipped() && this.autoOpen.getValue()) {
               if (this.stayOpenCounter > 0) {
                  --this.stayOpenCounter;
               } else {
                  mc.field_1755.method_25419();
                  this.stayOpenCounter = this.stayOpenDuration.getIntValue();
               }
            }

         }
      }
   }

   private boolean isTotemEquipped() {
      boolean offhandHasTotem = mc.field_1724.method_6079().method_7909() == class_1802.field_8288;
      if (this.hotbar.getValue()) {
         boolean hotbarHasTotem = mc.field_1724.method_31548().method_5438(this.totemSlot.getIntValue() - 1).method_7909() == class_1802.field_8288;
         return offhandHasTotem && hotbarHasTotem;
      } else {
         return offhandHasTotem;
      }
   }

   private boolean shouldOpenInventory() {
      boolean needsTotemInOffhand = mc.field_1724.method_6079().method_7909() != class_1802.field_8288;
      boolean hasTotems = this.countTotems((item) -> {
         return item == class_1802.field_8288;
      }) > 0;
      if (this.hotbar.getValue()) {
         boolean needsTotemInHotbar = mc.field_1724.method_31548().method_5438(this.totemSlot.getIntValue() - 1).method_7909() != class_1802.field_8288;
         return (needsTotemInOffhand || needsTotemInHotbar) && hasTotems;
      } else {
         return needsTotemInOffhand && hasTotems;
      }
   }

   private int findTotemSlot() {
      class_1661 inventory = mc.field_1724.method_31548();

      for(int i = 0; i < inventory.field_7547.size(); ++i) {
         if (((class_1799)inventory.field_7547.get(i)).method_7909() == class_1802.field_8288) {
            return i < (65 ^ 72) ? i + 36 : i;
         }
      }

      return -1;
   }

   private int countTotems(Predicate<class_1792> predicate) {
      int count = 0;
      class_1661 inventory = mc.field_1724.method_31548();
      Iterator var4 = inventory.field_7547.iterator();

      while(var4.hasNext()) {
         class_1799 stack = (class_1799)var4.next();
         if (predicate.test(stack.method_7909())) {
            count += stack.method_7947();
         }
      }

      return count;
   }

   // $FF: synthetic method
   private static String d984(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 5832 + var3 & (61 ^ 194));
      }

      return new String(var2);
   }
}
