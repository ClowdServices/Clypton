import java.util.Base64;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_2246;
import net.minecraft.class_239;
import net.minecraft.class_3965;

public final class at extends bf {
   private final fp activateKey = (new fp(db.of(d260("ZktdQ11NWUsPe1RL")), 71, false)).setDescription(db.of(d260("bE1QCl9ETFoPQ0VTQUBGFlNXTFhXWR1fUSMpLTEtKyE=")));
   private final gn switchDelay = new gn(db.of(d260("dF9AXkhEDWpKXFBL")), 0.0D, 20.0D, 0.0D, 1.0D);
   private final gn totemSlot = new gn(db.of(d260("c0ddT0YMfkJARA==")), 1.0D, 9.0D, 1.0D, 1.0D);
   private int delayCounter = 0;
   private int step = 0;
   private boolean isAnchoring = false;

   public at() {
      super(db.of(d260("Y0dcSEdJDW9BU1ldQQ==")), db.of(d260("Zl1dRUZNWUdMUV1eShRlWlZbXEkbDh1fUSMpLTE3")), -1, hk.COMBAT);
      this.addSettings(new ab[]{this.switchDelay, this.totemSlot, this.activateKey});
   }

   public void onEnable() {
      super.onEnable();
   }

   public void onDisable() {
      super.onDisable();
   }

   @cp
   public void onTick(hm event) {
      if (mc.field_1755 == null) {
         if (mc.field_1724 != null) {
            if (this.hasRequiredItems()) {
               if (this.isAnchoring || this.checkActivationKey()) {
                  class_239 crosshairTarget = mc.field_1765;
                  if (mc.field_1765 instanceof class_3965 && !ft.isBlockAtPosition(((class_3965)crosshairTarget).method_17777(), class_2246.field_10124)) {
                     if (this.delayCounter < this.switchDelay.getIntValue()) {
                        ++this.delayCounter;
                     } else {
                        if (this.step == 0) {
                           gp.swap(class_1802.field_23141);
                        } else if (this.step == 1) {
                           ft.interactWithBlock((class_3965)crosshairTarget, true);
                        } else if (this.step == 2) {
                           gp.swap(class_1802.field_8801);
                        } else if (this.step == 3) {
                           ft.interactWithBlock((class_3965)crosshairTarget, true);
                        } else if (this.step == 4) {
                           gp.swap(class_1802.field_23141);
                        } else if (this.step == 5) {
                           ft.interactWithBlock((class_3965)crosshairTarget, true);
                           ft.interactWithBlock((class_3965)crosshairTarget, true);
                        } else if (this.step == (119 ^ 113)) {
                           gp.swap(class_1802.field_8801);
                        } else if (this.step == 7) {
                           ft.interactWithBlock((class_3965)crosshairTarget, true);
                        } else if (this.step == 8) {
                           gp.swap(this.totemSlot.getIntValue() - 1);
                        } else if (this.step == 9) {
                           ft.interactWithBlock((class_3965)crosshairTarget, true);
                        } else if (this.step == 10) {
                           this.isAnchoring = false;
                           this.step = 0;
                           this.resetState();
                           return;
                        }

                        ++this.step;
                     }
                  } else {
                     this.isAnchoring = false;
                     this.resetState();
                  }
               }
            }
         }
      }
   }

   private boolean hasRequiredItems() {
      boolean b = false;
      boolean b2 = false;

      for(int i = 0; i < (165 ^ 172); ++i) {
         class_1799 getStack = mc.field_1724.method_31548().method_5438(i);
         if (getStack.method_7909().equals(class_1802.field_23141)) {
            b = true;
         }

         if (getStack.method_7909().equals(class_1802.field_8801)) {
            b2 = true;
         }
      }

      return b && b2;
   }

   private boolean checkActivationKey() {
      int d = this.activateKey.getValue();
      if (d != -1 && dd.isKeyPressed(d)) {
         return this.isAnchoring = true;
      } else {
         this.resetState();
         return false;
      }
   }

   private void resetState() {
      this.delayCounter = 0;
   }

   public boolean isAnchoringActive() {
      return this.isAnchoring;
   }

   // $FF: synthetic method
   private static String d260(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 6504 - 321 + var3 & 255);
      }

      return new String(var2);
   }
}
