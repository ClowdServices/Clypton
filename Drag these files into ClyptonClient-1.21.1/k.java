import java.util.Base64;
import java.util.Iterator;
import net.minecraft.class_1703;
import net.minecraft.class_1707;
import net.minecraft.class_1713;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1836;
import net.minecraft.class_2561;
import net.minecraft.class_304;
import net.minecraft.class_1792.class_9635;
import net.minecraft.class_3675.class_307;

public final class k extends bf {
   private final gn dropDelay = (new gn(db.of(d439("1OP947TR8/v54A==")), 0.0D, 120.0D, 30.0D, 1.0D)).getValue(db.of(d439("2P7ls/vz4vL2ufPvvO728NXNxoPX0cfV3InO2cPd3sbe1pLR29vTxJjQ1JvR1NDKtKSx")));
   private final gn pageAmount = (new gn(db.of(d439("wPD19rTU+/jt9+4=")), 1.0D, 10.0D, 2.0D, 1.0D)).getValue(db.of(d439("2P7ls/n0+O646fv8+e6+7MjO18/Ahc/TiM3YxNyNzMrW3sDWlMbT29TQ1Nw=")));
   private final gn pageSwitchDelay = (new gn(db.of(d439("wPD19rTG4f7s+vK72Pjy/tk=")), 0.0D, 720.0D, 4.0D, 1.0D)).getValue(db.of(d439("2P7ls/vz4vL2ufPvvO728NXNxoPX0s/Ty8GK283Ky9yQ2NyTx9DV2NbdyQ==")));
   private final gn delay = (new gn(db.of(d439("9PT+8u0=")), 0.0D, 20.0D, 1.0D, 1.0D)).getValue(db.of(d439("x/nz57Tm/vjt9f67/vi++8XNw9qEzMiH3MDJwN8=")));
   private int delayCounter;
   private int pageCounter;
   private boolean isProcessing;
   private boolean isSelling;
   private boolean isPageSwitching;

   public k() {
      super(db.of(d439("0eTm/LTG5vbv9//pvM7788w=")), db.of(d439("0eTm/Pn04v77+Pb35b367c/R0YPGysjC24nM2cPAjtzA0MXd0ceW1tbdmsjZ0dLM4LWqpqk=")), -1, hk.DONUT);
      this.addSettings(new ab[]{this.dropDelay, this.pageAmount, this.pageSwitchDelay, this.delay});
   }

   public void onEnable() {
      super.onEnable();
      this.delayCounter = 6 ^ 18;
      this.isProcessing = false;
   }

   public void onDisable() {
      super.onDisable();
   }

   @cp
   public void onTick(hm event) {
      if (this.delayCounter > 0) {
         --this.delayCounter;
      } else if (mc.field_1724 != null) {
         if (this.pageCounter >= this.pageAmount.getIntValue()) {
            this.isSelling = true;
            this.pageCounter = 0;
            this.delayCounter = 40;
         } else {
            class_1703 currentScreenHandler;
            int k;
            if (!this.isSelling) {
               currentScreenHandler = mc.field_1724.field_7512;
               if (!(mc.field_1724.field_7512 instanceof class_1707)) {
                  class_304.method_1420(class_307.field_1672.method_1447(1));
                  this.delayCounter = 20;
                  return;
               }

               if (currentScreenHandler.method_7611(15).method_7677().method_31574(class_1802.field_8581)) {
                  mc.field_1761.method_2906(mc.field_1724.field_7512.field_7763, 34 ^ 45, 1, class_1713.field_7794, mc.field_1724);
                  this.delayCounter = 19 ^ 25;
                  return;
               }

               if (mc.field_1724.field_7512.method_7611(13).method_7677().method_31574(class_1802.field_8398)) {
                  mc.field_1761.method_2906(mc.field_1724.field_7512.field_7763, 64 ^ 75, 0, class_1713.field_7790, mc.field_1724);
                  this.delayCounter = 20;
                  return;
               }

               if (!mc.field_1724.field_7512.method_7611(53).method_7677().method_31574(class_1802.field_8695)) {
                  mc.field_1724.method_7346();
                  this.delayCounter = 214 ^ 194;
                  return;
               }

               if (mc.field_1724.field_7512.method_7611(68 ^ 116).method_7677().method_31574(class_1802.field_8107)) {
                  mc.field_1761.method_2906(mc.field_1724.field_7512.field_7763, 48, 0, class_1713.field_7790, mc.field_1724);
                  this.delayCounter = 115 ^ 103;
                  return;
               }

               boolean b2 = true;

               for(k = 0; k < 45; ++k) {
                  if (!mc.field_1724.field_7512.method_7611(k).method_7677().method_31574(class_1802.field_8606)) {
                     b2 = false;
                     break;
                  }
               }

               if (b2) {
                  mc.field_1761.method_2906(mc.field_1724.field_7512.field_7763, 52, 1, class_1713.field_7795, mc.field_1724);
                  this.isProcessing = true;
                  this.delayCounter = this.pageSwitchDelay.getIntValue() * (51 ^ 39);
                  ++this.pageCounter;
               } else if (this.isProcessing) {
                  this.isProcessing = false;
                  mc.field_1761.method_2906(mc.field_1724.field_7512.field_7763, 50, 0, class_1713.field_7790, mc.field_1724);
                  this.delayCounter = 20;
               } else {
                  this.isProcessing = false;
                  if (this.pageCounter != 0) {
                     this.pageCounter = 0;
                     this.isSelling = true;
                     this.delayCounter = 40;
                     return;
                  }

                  mc.field_1761.method_2906(mc.field_1724.field_7512.field_7763, 45, 1, class_1713.field_7795, mc.field_1724);
                  this.delayCounter = 1200 * this.dropDelay.getIntValue();
               }
            } else {
               currentScreenHandler = mc.field_1724.field_7512;
               if (!(mc.field_1724.field_7512 instanceof class_1707)) {
                  mc.method_1562().method_45730("order " + this.getOrderCommand());
                  this.delayCounter = 20;
                  return;
               }

               if (((class_1707)currentScreenHandler).method_17388() != (84 ^ 82)) {
                  if (((class_1707)currentScreenHandler).method_17388() == 4) {
                     int b = gp.getSlot(class_1802.field_8162);
                     if (b <= 0) {
                        mc.field_1724.method_7346();
                        this.delayCounter = 10;
                        return;
                     }

                     if (this.isPageSwitching && b == (73 ^ 109)) {
                        this.isPageSwitching = false;
                        mc.field_1724.method_7346();
                        return;
                     }

                     class_1792 j = this.getInventoryItem();

                     for(int i = 0; i < mc.field_1724.field_7512.method_7602().size(); ++i) {
                        class_1792 item = ((class_1799)mc.field_1724.field_7512.method_7602().get(i)).method_7909();
                        if (item != class_1802.field_8162 && item == j) {
                           mc.field_1761.method_2906(mc.field_1724.field_7512.field_7763, i, 1, class_1713.field_7794, mc.field_1724);
                           this.delayCounter = this.delay.getIntValue();
                           return;
                        }
                     }
                  } else if (((class_1707)currentScreenHandler).method_17388() == 3) {
                     mc.field_1761.method_2906(mc.field_1724.field_7512.field_7763, 15, 1, class_1713.field_7794, mc.field_1724);
                     this.isPageSwitching = true;
                     this.delayCounter = 10;
                  }
               } else {
                  class_1799 stack = currentScreenHandler.method_7611(28 ^ 51).method_7677();
                  if (stack.method_31574(class_1802.field_8162)) {
                     this.delayCounter = 2;
                     mc.field_1724.method_7346();
                     return;
                  }

                  Iterator var4 = stack.method_7950(class_9635.method_59528(mc.field_1687), mc.field_1724, class_1836.field_41070).iterator();

                  Object next;
                  String string;
                  do {
                     do {
                        if (!var4.hasNext()) {
                           for(k = 0; k < (178 ^ 158); ++k) {
                              if (currentScreenHandler.method_7611(k).method_7677().method_31574(this.getInventoryItem())) {
                                 mc.field_1761.method_2906(mc.field_1724.field_7512.field_7763, k, 1, class_1713.field_7794, mc.field_1724);
                                 this.delayCounter = 10;
                                 return;
                              }
                           }

                           this.delayCounter = 58 ^ 18;
                           mc.field_1724.method_7346();
                           return;
                        }

                        next = var4.next();
                        string = next.toString();
                     } while(!string.contains(d439("3f7h57TY+fn94LrL+e++1tTEzw==")));
                  } while(!((class_2561)next).method_10866().toString().contains(d439("5/n75/E=")) && !string.contains(d439("5/n75/E=")));

                  mc.field_1761.method_2906(mc.field_1724.field_7512.field_7763, 47, 1, class_1713.field_7794, mc.field_1724);
                  this.delayCounter = 5;
                  return;
               }
            }

         }
      }
   }

   private class_1792 getInventoryItem() {
      for(int i = 0; i < (77 ^ 110); ++i) {
         class_1799 stack = mc.field_1724.method_31548().method_5438(i);
         if (!stack.method_31574(class_1802.field_8162)) {
            return stack.method_7909();
         }
      }

      return class_1802.field_8162;
   }

   private String getOrderCommand() {
      class_1792 j = this.getInventoryItem();
      return j.equals(class_1802.field_8606) ? d439("0v789uc=") : j.method_7848().getString();
   }

   // $FF: synthetic method
   private static String d439(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 2448 + var3 & (16 ^ 239));
      }

      return new String(var2);
   }
}
