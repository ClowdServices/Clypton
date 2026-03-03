import java.util.Base64;
import java.util.HashSet;
import java.util.Iterator;
import net.minecraft.class_1703;
import net.minecraft.class_1707;
import net.minecraft.class_1713;
import net.minecraft.class_1735;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;

public final class gd extends bf {
   private final cu<cl> mode;
   private final ay targetItems;
   private final gn delay;
   private int delayCounter;
   private boolean hasExecuted;
   private boolean inSellGUI;

   public gd() {
      super(db.of(d455("79rE3pLg0dna")), db.of(d455("79rE3t/SwNzV1tTVw5vP2NLTs+Grt6GotQ==")), -1, hk.DONUT);
      this.mode = new cu(db.of(d455("48DU1A==")), cl.SELL, cl.class);
      this.targetItems = new ay(db.of(d455("+s7C1tfHlPzC0tXK")), new HashSet());
      this.delay = new gn(db.of(d455("ysrc0Ms=")), 20.0D, 200.0D, 60.0D, 1.0D);
      this.addSettings(new ab[]{this.mode, this.targetItems, this.delay});
   }

   public void onEnable() {
      super.onEnable();
      this.delayCounter = 0;
      this.hasExecuted = false;
      this.inSellGUI = false;
   }

   public void onDisable() {
      super.onDisable();
      if (mc.field_1724 != null && mc.field_1724.field_7512 != null) {
         mc.field_1724.method_7346();
      }

   }

   @cp
   public void onTick(hm event) {
      if (mc.field_1724 != null) {
         if (this.delayCounter > 0) {
            --this.delayCounter;
         } else {
            class_1792 targetItem = this.getSelectedItem();
            if (!targetItem.equals(class_1802.field_8162)) {
               if (((cl)this.mode.getValue()).equals(cl.SELL)) {
                  class_1703 currentScreenHandler = mc.field_1724.field_7512;
                  boolean isInSellGUI = currentScreenHandler instanceof class_1707 && ((class_1707)currentScreenHandler).method_17388() == 5;
                  if (!isInSellGUI) {
                     if (this.inSellGUI) {
                        this.inSellGUI = false;
                        this.hasExecuted = false;
                        this.delayCounter = this.delay.getIntValue();
                        return;
                     }

                     if (!this.hasItemInInventory(targetItem)) {
                        this.delayCounter = 20;
                        return;
                     }

                     if (!this.hasExecuted) {
                        mc.method_1562().method_45730(d455("3crc3Q=="));
                        this.hasExecuted = true;
                        this.inSellGUI = true;
                        this.delayCounter = 20;
                     }

                     return;
                  }

                  this.inSellGUI = true;
                  this.hasExecuted = false;
                  int containerSize = 112 ^ 93;
                  boolean movedItem = false;

                  for(int slot = containerSize; slot < currentScreenHandler.field_7761.size(); ++slot) {
                     class_1799 stack = ((class_1735)currentScreenHandler.field_7761.get(slot)).method_7677();
                     if (!stack.method_7960() && stack.method_31574(targetItem)) {
                        mc.field_1761.method_2906(currentScreenHandler.field_7763, slot, 0, class_1713.field_7794, mc.field_1724);
                        movedItem = true;
                        this.delayCounter = 3;
                        break;
                     }
                  }

                  if (!movedItem) {
                     mc.field_1724.method_7346();
                     this.delayCounter = 5;
                  }
               } else {
                  if (!this.hasItemInInventory(targetItem)) {
                     this.delayCounter = 20;
                     return;
                  }

                  if (this.hasExecuted) {
                     this.hasExecuted = false;
                     this.delayCounter = this.delay.getIntValue();
                     return;
                  }

                  mc.method_1562().method_45730("order " + this.getOrderCommand());
                  this.hasExecuted = true;
               }

            }
         }
      }
   }

   private boolean hasItemInInventory(class_1792 item) {
      if (mc.field_1724 == null) {
         return false;
      } else {
         Iterator var2 = mc.field_1724.method_31548().field_7547.iterator();

         class_1799 stack;
         do {
            if (!var2.hasNext()) {
               return false;
            }

            stack = (class_1799)var2.next();
         } while(!stack.method_31574(item));

         return true;
      }
   }

   private class_1792 getSelectedItem() {
      return this.targetItems.getItems().isEmpty() ? class_1802.field_8162 : (class_1792)this.targetItems.getItems().iterator().next();
   }

   private String getOrderCommand() {
      class_1792 j = this.getSelectedItem();
      return j.equals(class_1802.field_8606) ? d455("7MDe1ME=") : j.method_7848().getString();
   }

   public ay getTargetItems() {
      return this.targetItems;
   }

   // $FF: synthetic method
   private static String d455(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 8110 + var3 & 255);
      }

      return new String(var2);
   }
}
