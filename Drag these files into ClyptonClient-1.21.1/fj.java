import java.util.Base64;
import net.minecraft.class_1703;
import net.minecraft.class_1707;
import net.minecraft.class_1713;
import net.minecraft.class_1802;
import net.minecraft.class_304;
import net.minecraft.class_3675.class_307;

public final class fj extends bf {
   private final cu<do> dropMode;
   private final gn dropDelay;
   private final gn pageSwitchDelay;
   private int delayCounter;
   private boolean isPageSwitching;

   public fj() {
      super(db.of(d483("Iw0NAUUiFQcZGg4e")), db.of(d483("IBcXCwgHEwEKCwcAFE4LAh4CAFQXGRkdClodDhITX/Px4/Tq4PQ=")), -1, hk.DONUT);
      this.dropMode = new cu(db.of(d483("LA0HAQ==")), do.SPAWNER, do.class);
      this.dropDelay = (new gn(db.of(d483("JRAMFEUiAgQIEw==")), 0.0D, 120.0D, 30.0D, 1.0D)).getValue(db.of(d483("KQ0URAoAEw0HSgIYTR0HHwQeF1QGAhYKDVofDhIOD+nv5aPm6uji+6nj5azg5+Hl5ffg")));
      this.pageSwitchDelay = (new gn(db.of(d483("MQMEAUU1EAEdCQNMKQsDEQg=")), 0.0D, 720.0D, 4.0D, 1.0D)).getValue(db.of(d483("KQ0URAoAEw0HSgIYTR0HHwQeF1QGAR4MGhJbDBwZGvOh6+2k9uPk5+fu+A==")));
      this.addSettings(new ab[]{this.dropMode, this.dropDelay, this.pageSwitchDelay});
   }

   public void onEnable() {
      super.onEnable();
      this.delayCounter = 20;
      this.isPageSwitching = false;
   }

   public void onDisable() {
      super.onDisable();
   }

   @cp
   public void onTick(hm event) {
      if (this.delayCounter > 0) {
         --this.delayCounter;
      } else if (mc.field_1724 != null) {
         int k;
         if (this.dropMode.isMode(do.SPAWNER)) {
            if (!(mc.field_1724.field_7512 instanceof class_1707)) {
               class_304.method_1420(class_307.field_1672.method_1447(1));
               this.delayCounter = 20;
               return;
            }

            if (mc.field_1724.field_7512.method_7611(13).method_7677().method_31574(class_1802.field_8398)) {
               mc.field_1761.method_2906(mc.field_1724.field_7512.field_7763, 11, 0, class_1713.field_7790, mc.field_1724);
               this.delayCounter = 20;
               return;
            }

            if (!mc.field_1724.field_7512.method_7611(69 ^ 112).method_7677().method_31574(class_1802.field_8695)) {
               mc.field_1724.method_7346();
               this.delayCounter = 20;
               return;
            }

            if (mc.field_1724.field_7512.method_7611(78 ^ 126).method_7677().method_31574(class_1802.field_8107)) {
               mc.field_1761.method_2906(mc.field_1724.field_7512.field_7763, 48, 0, class_1713.field_7790, mc.field_1724);
               this.delayCounter = 20;
               return;
            }

            boolean b = true;

            for(k = 0; k < 45; ++k) {
               if (!mc.field_1724.field_7512.method_7611(k).method_7677().method_31574(class_1802.field_8606)) {
                  b = false;
                  break;
               }
            }

            if (b) {
               mc.field_1761.method_2906(mc.field_1724.field_7512.field_7763, 52, 1, class_1713.field_7795, mc.field_1724);
               this.isPageSwitching = true;
               this.delayCounter = this.pageSwitchDelay.getIntValue() * 20;
            } else if (this.isPageSwitching) {
               this.isPageSwitching = false;
               mc.field_1761.method_2906(mc.field_1724.field_7512.field_7763, 69 ^ 119, 0, class_1713.field_7790, mc.field_1724);
               this.delayCounter = 20;
            } else {
               mc.field_1761.method_2906(mc.field_1724.field_7512.field_7763, 45, 1, class_1713.field_7795, mc.field_1724);
               this.isPageSwitching = false;
               this.delayCounter = 1200 * this.dropDelay.getIntValue();
            }
         } else {
            class_1703 currentScreenHandler = mc.field_1724.field_7512;
            if (!(mc.field_1724.field_7512 instanceof class_1707)) {
               mc.method_1562().method_45730(d483("DhAHARc="));
               this.delayCounter = 157 ^ 137;
               return;
            }

            if (((class_1707)currentScreenHandler).method_17388() == (62 ^ 56)) {
               if (currentScreenHandler.method_7611(49).method_7677().method_31574(class_1802.field_8895)) {
                  mc.field_1761.method_2906(mc.field_1724.field_7512.field_7763, 78 ^ 125, 0, class_1713.field_7790, mc.field_1724);
                  this.delayCounter = 221 ^ 201;
                  return;
               }

               for(k = 0; k < 45; ++k) {
                  if (currentScreenHandler.method_7611(k).method_7677().method_31574(class_1802.field_8606)) {
                     mc.field_1761.method_2906(mc.field_1724.field_7512.field_7763, k, 1, class_1713.field_7795, mc.field_1724);
                     this.delayCounter = 1200 * this.dropDelay.getIntValue();
                     return;
                  }
               }

               if (this.isPageSwitching) {
                  k = 75 ^ 102;
               } else {
                  k = 53;
               }

               mc.field_1761.method_2906(mc.field_1724.field_7512.field_7763, k, 0, class_1713.field_7790, mc.field_1724);
               this.isPageSwitching = !this.isPageSwitching;
               this.delayCounter = this.pageSwitchDelay.getIntValue();
            } else if (((class_1707)currentScreenHandler).method_17388() == 3) {
               if (mc.field_1755 == null) {
                  return;
               }

               if (mc.field_1755.method_25440().getString().contains(d483("OA0WFkUpFQwMGBg="))) {
                  for(k = 0; k < 26; ++k) {
                     if (currentScreenHandler.method_7611(k).method_7677().method_31574(class_1802.field_8606)) {
                        mc.field_1761.method_2906(mc.field_1724.field_7512.field_7763, k, 0, class_1713.field_7790, mc.field_1724);
                        this.delayCounter = 231 ^ 243;
                        return;
                     }
                  }

                  this.delayCounter = 128 ^ 72;
                  return;
               }

               if (mc.field_1755.method_25440().getString().contains(d483("JAYKEEUpFQwMGA=="))) {
                  if (currentScreenHandler.method_7611(13).method_7677().method_31574(class_1802.field_8106)) {
                     mc.field_1761.method_2906(mc.field_1724.field_7512.field_7763, 4 ^ 9, 0, class_1713.field_7790, mc.field_1724);
                     this.delayCounter = 41 ^ 61;
                     return;
                  }

                  if (currentScreenHandler.method_7611(15).method_7677().method_31574(class_1802.field_8106)) {
                     mc.field_1761.method_2906(mc.field_1724.field_7512.field_7763, 15, 0, class_1713.field_7790, mc.field_1724);
                     this.delayCounter = 102 ^ 114;
                     return;
                  }
               }

               this.delayCounter = 200;
            }
         }

      }
   }

   // $FF: synthetic method
   private static String d483(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 1633 + var3 & (126 ^ 129));
      }

      return new String(var2);
   }
}
