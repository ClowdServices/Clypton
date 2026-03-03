import java.util.Base64;
import java.util.function.Predicate;
import net.minecraft.class_1268;
import net.minecraft.class_1304;
import net.minecraft.class_1713;
import net.minecraft.class_1738;
import net.minecraft.class_1802;

public final class o extends bf {
   private final fp activateKey = new fp(db.of(d499("Wn9pd2lBVUcDb0Bf")), 71, false);
   private final gn swapDelay = new gn(db.of(d499("X3lxf2Y=")), 0.0D, 20.0D, 0.0D, 1.0D);
   private final ff switchBack = new ff(db.of(d499("SGt0anxIAWBCR04=")), true);
   private final gn switchDelay = new gn(db.of(d499("SGt0anxIAWZGSERf")), 0.0D, 20.0D, 0.0D, 1.0D);
   private final ff moveToSlot = (new ff(db.of(d499("VnNrez9UTgJQSEpS")), true)).setDescription(d499("Uno9e3NZVVBCBExVB0ZGXgtFQw5HX0VQUkYVX0MYTlNXUB1TUDYkYiowZSA1JyRqIiI7KyEkPiAqdCE5dygrPz05Lyw6BEERDwsR"));
   private final gn elytraSlot = (new gn(db.of(d499("XnBkam1BAXFPS1E=")), 1.0D, 9.0D, 9.0D, 1.0D)).getValue(db.of(d499("QnNobD9QU0dFQVdUQkwJT0dVWVxOEEJeXEA=")));
   private boolean isSwapping;
   private boolean isSwinging;
   private boolean isItemSwapped;
   private int swapCounter;
   private int switchCounter;
   private int activationCooldown;
   private int originalSlot;

   public o() {
      super(db.of(d499("XnBkam1BAXFURVU=")), db.of(d499("SHl8c3NFUlFPXQVVUElZCklJWVlKVV8SUloVc1tBTUhaHFxQW2AgYgAsIDUzOCUrPyltOSYkOXIydDY5OT4wPS4uPDwzBUEJBh0HDwkMAAQM")), -1, hk.COMBAT);
      this.addSettings(new ab[]{this.activateKey, this.swapDelay, this.switchBack, this.switchDelay, this.moveToSlot, this.elytraSlot});
   }

   public void onEnable() {
      this.resetState();
      super.onEnable();
   }

   public void onDisable() {
      super.onDisable();
   }

   @cp
   public void onTick(hm event) {
      if (mc.field_1755 == null) {
         if (mc.field_1724 != null) {
            if (this.activationCooldown > 0) {
               --this.activationCooldown;
            } else if (dd.isKeyPressed(this.activateKey.getValue())) {
               this.isSwapping = true;
               this.activationCooldown = 4;
            }

            if (this.isSwapping) {
               if (this.originalSlot == -1) {
                  this.originalSlot = mc.field_1724.method_31548().field_7545;
               }

               if (this.swapCounter < this.swapDelay.getIntValue()) {
                  ++this.swapCounter;
                  return;
               }

               Predicate predicate;
               if (mc.field_1724.method_31548().method_7372(class_1304.field_6174.method_5927()).method_31574(class_1802.field_8833)) {
                  predicate = (item) -> {
                     return item instanceof class_1738 && ((class_1738)item).method_7685() == class_1304.field_6174;
                  };
               } else {
                  predicate = (item2) -> {
                     return item2.equals(class_1802.field_8833);
                  };
               }

               if (!this.isItemSwapped) {
                  if (!gp.swapItem(predicate)) {
                     if (!this.moveToSlot.getValue()) {
                        this.resetState();
                        return;
                     }

                     mc.field_1761.method_2906(mc.field_1724.field_7512.field_7763, 9, this.elytraSlot.getIntValue() - 1, class_1713.field_7791, mc.field_1724);
                     this.swapCounter = 0;
                     return;
                  }

                  this.isItemSwapped = true;
               }

               if (!this.isSwinging) {
                  mc.field_1761.method_2919(mc.field_1724, class_1268.field_5808);
                  mc.field_1724.method_6104(class_1268.field_5808);
                  this.isSwinging = true;
               }

               if (this.switchBack.getValue()) {
                  this.handleSwitchBack();
               } else {
                  this.resetState();
               }
            }

         }
      }
   }

   private void handleSwitchBack() {
      if (this.switchCounter < this.switchDelay.getIntValue()) {
         ++this.switchCounter;
      } else {
         gp.swap(this.originalSlot);
         this.resetState();
      }
   }

   private void resetState() {
      this.originalSlot = -1;
      this.switchCounter = 0;
      this.swapCounter = 0;
      this.isSwapping = false;
      this.isSwinging = false;
      this.isItemSwapped = false;
   }

   // $FF: synthetic method
   private static String d499(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 2504 - 941 + var3 & 255);
      }

      return new String(var2);
   }
}
