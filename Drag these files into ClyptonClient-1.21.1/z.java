import java.util.Base64;
import net.minecraft.class_1703;
import net.minecraft.class_1707;
import net.minecraft.class_1713;
import net.minecraft.class_1802;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2846;
import net.minecraft.class_2846.class_2847;

public final class z extends bf {
   private final gn delay = new gn(db.of(d100("jq6grLc=")), 0.0D, 20.0D, 1.0D, 1.0D);
   private int delayCounter = 0;

   public z() {
      super(db.of(d100("maO5oaWqovGWobulprKq")), db.of(d100("jaSpvu67v/Ghu7ul9rWtoKn7r7Wrs4uEkJDEhIiDyI2YhJyezo6FhZ2elYGflJmVloI=")), -1, hk.DONUT);
      this.addSettings(new ab[]{this.delay});
   }

   public void onEnable() {
      super.onEnable();
   }

   public void onDisable() {
      super.onDisable();
   }

   @cp
   public void onTick(hm event) {
      if (mc.field_1724 != null) {
         if (this.delayCounter > 0) {
            --this.delayCounter;
         } else {
            class_1703 currentScreenHandler = mc.field_1724.field_7512;
            if (!(mc.field_1724.field_7512 instanceof class_1707)) {
               mc.method_1562().method_45730(d100("uaOjvQ=="));
               this.delayCounter = 87 ^ 67;
            } else if (((class_1707)currentScreenHandler).method_17388() == 3) {
               if (currentScreenHandler.method_7611(11).method_7677().method_31574(class_1802.field_20399) && currentScreenHandler.method_7611(11).method_7677().method_7947() == 1) {
                  mc.field_1761.method_2906(mc.field_1724.field_7512.field_7763, 149 ^ 158, 0, class_1713.field_7790, mc.field_1724);
                  this.delayCounter = 20;
               } else if (currentScreenHandler.method_7611(17).method_7677().method_31574(class_1802.field_8545)) {
                  mc.field_1761.method_2906(mc.field_1724.field_7512.field_7763, 17, 0, class_1713.field_7790, mc.field_1724);
                  this.delayCounter = 20;
               } else {
                  if (currentScreenHandler.method_7611(13).method_7677().method_31574(class_1802.field_8545)) {
                     mc.field_1761.method_2906(mc.field_1724.field_7512.field_7763, 237 ^ 250, 0, class_1713.field_7790, mc.field_1724);
                     this.delayCounter = this.delay.getIntValue();
                     mc.field_1724.field_3944.method_52787(new class_2846(class_2847.field_12970, class_2338.field_10980, class_2350.field_11033));
                  }

               }
            }
         }
      }
   }

   // $FF: synthetic method
   private static String d100(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 6346 + var3 & (25 ^ 230));
      }

      return new String(var2);
   }
}
