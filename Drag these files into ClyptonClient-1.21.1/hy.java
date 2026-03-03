import java.util.Base64;
import net.minecraft.class_1268;
import net.minecraft.class_1799;
import net.minecraft.class_1802;

public final class hy extends bf {
   private final ff keyPressMode = (new ff(db.of(d368("ET4lfQ4tBRIRQykKAgI=")), true)).setDescription(db.of(d368("FTUwJH4qEwRCBQ0XAxAHGwFLGwULAVAaFwpUHAVXCAsfCA8YGg==")));
   private final fp activateKey = (new fp(db.of(d368("GzgoNCg+FARCKAEc")), 71, false)).setDescription(db.of(d368("ET4lfSowQBQRBkQDDxUNHgUZBw==")));
   private final ff switchBack = (new ff(db.of(d368("CSw1KT03QCMDAA8=")), true)).setDescription(db.of(d368("CSw1KT03QAMDAA9FEghIBhgCCwQADhxRAR8bAVYWHg0fCVwIDRbu5g==")));
   private final gn holdTime = (gn)(new gn(db.of(d368("EjQwOX4LCQwH")), 5.0D, 40.0D, 0.0D, 1.0D)).setDescription(db.of(d368("DjI/Ni1/FA5CCwsJAkcOABgOGwIcBFATFxUbBxNXCw4TDx8VFxHnoarq4qX18OH96eOs7+/s+7H2+uf09Pv9/bM=")));
   private final gn switchDelay = new gn(db.of(d368("CSw1KT03QCUHDwUc")), 0.0D, 20.0D, 0.0D, 1.0D);
   private final gn switchBackDelay = new gn(db.of(d368("CSw1KT03QCMDAA9FIgIECBM=")), 0.0D, 20.0D, 0.0D, 1.0D);
   private int delayCounter = 0;
   private int switchBackCounter = 0;
   private int holdCounter = 0;
   private int step = 0;
   private int originalSlot = -1;
   private boolean isActive = false;

   public hy() {
      super(db.of(d368("Gy4oMn4ZCRMHFAsXDQ==")), db.of(d368("Gy4oMjM+FAgBAggJH0cdGg8YTAsHHRUGHQEfVQQYGxIfDw8=")), -1, hk.MISC);
      this.addSettings(new ab[]{this.keyPressMode, this.activateKey, this.switchBack, this.holdTime, this.switchDelay, this.switchBackDelay});
   }

   public void onEnable() {
      super.onEnable();
      this.resetState();
   }

   public void onDisable() {
      super.onDisable();
      this.resetState();
   }

   @cp
   public void onTick(hm event) {
      if (mc.field_1755 == null) {
         if (mc.field_1724 != null) {
            if (this.hasFirework()) {
               if (this.keyPressMode.getValue()) {
                  if (!this.checkActivationKey()) {
                     return;
                  }
               } else {
                  this.isActive = true;
               }

               if (this.delayCounter < this.switchDelay.getIntValue()) {
                  ++this.delayCounter;
               } else {
                  if (this.step == 0) {
                     this.originalSlot = mc.field_1724.method_31548().field_7545;
                     gp.swap(class_1802.field_8639);
                  } else if (this.step == 1) {
                     mc.field_1761.method_2919(mc.field_1724, class_1268.field_5808);
                  } else if (this.step == 2) {
                     if (!this.switchBack.getValue()) {
                        if (this.holdCounter < this.holdTime.getIntValue()) {
                           ++this.holdCounter;
                           return;
                        }

                        this.isActive = false;
                        this.step = 0;
                        this.resetState();
                        return;
                     }

                     if (this.switchBackCounter < this.switchBackDelay.getIntValue()) {
                        ++this.switchBackCounter;
                        return;
                     }

                     if (this.originalSlot != -1) {
                        gp.swap(this.originalSlot);
                     }
                  } else if (this.step == 3) {
                     this.isActive = false;
                     this.step = 0;
                     this.resetState();
                     return;
                  }

                  ++this.step;
               }
            }
         }
      }
   }

   private boolean hasFirework() {
      for(int i = 0; i < 9; ++i) {
         class_1799 stack = mc.field_1724.method_31548().method_5438(i);
         if (stack.method_7909().equals(class_1802.field_8639)) {
            return true;
         }
      }

      return false;
   }

   private boolean checkActivationKey() {
      int key = this.activateKey.getValue();
      if (key != -1 && dd.isKeyPressed(key)) {
         return this.isActive = true;
      } else {
         this.resetState();
         return false;
      }
   }

   private void resetState() {
      this.delayCounter = 0;
      this.switchBackCounter = 0;
      this.holdCounter = 0;
      this.step = 0;
      this.originalSlot = -1;
   }

   public boolean isFireworkActive() {
      return this.isActive;
   }

   // $FF: synthetic method
   private static String d368(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 4698 + var3 & 255);
      }

      return new String(var2);
   }
}
