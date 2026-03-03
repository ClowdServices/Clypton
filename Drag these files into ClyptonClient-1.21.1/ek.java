import client.astralux.Astralux;
import java.util.Base64;
import net.minecraft.class_1268;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_3965;
import net.minecraft.class_239.class_240;

public final class ek extends bf {
   private final ff lockView = new ff(db.of(d834("XH5xeDRDf3Jv")), true);
   private final gn pitch = new gn(db.of(d834("QHhmcHw=")), -180.0D, 180.0D, 0.0D, 0.1D);
   private final gn yaw = new gn(db.of(d834("SXBl")), -180.0D, 180.0D, 0.0D, 0.1D);
   private final ff autoWalk = new ff(db.of(d834("UWRmfDRCd3tz")), false);

   public ek() {
      super(db.of(d834("UWRmfDRYf3l9")), db.of(d834("XX52ZnhwNmNweG47fXFycFdSAlNIRF9CWloKX0MNT1pEXl9SQFxVVlRVQxtRVFBa")), -1, hk.MISC);
      this.addSettings(new ab[]{this.lockView, this.pitch, this.yaw, this.autoWalk});
   }

   public void onEnable() {
      super.onEnable();
   }

   public void onDisable() {
      super.onDisable();
      if (this.autoWalk.getValue()) {
         mc.field_1690.field_1894.method_23481(false);
      }

   }

   @cp
   public void onTick(hm event) {
      if (mc.field_1755 == null) {
         bf moduleByClass = Astralux.INSTANCE.getModuleManager().getModuleByClass(hz.class);
         if (!moduleByClass.isEnabled() || !((hz)moduleByClass).shouldEat()) {
            this.processMiningAction(true);
            if (this.autoWalk.getValue()) {
               mc.field_1690.field_1894.method_23481(true);
            }

            if (this.lockView.getValue()) {
               float getYaw = mc.field_1724.method_36454();
               float getPitch = mc.field_1724.method_36455();
               float g = this.yaw.getFloatValue();
               float g2 = this.pitch.getFloatValue();
               if (getYaw != g || getPitch != g2) {
                  mc.field_1724.method_36456(g);
                  mc.field_1724.method_36457(g2);
               }
            }

         }
      }
   }

   private void processMiningAction(boolean b) {
      if (!mc.field_1724.method_6115()) {
         if (b && mc.field_1765 != null && mc.field_1765.method_17783() == class_240.field_1332) {
            class_3965 blockHitResult = (class_3965)mc.field_1765;
            class_2338 blockPos = ((class_3965)mc.field_1765).method_17777();
            if (!mc.field_1687.method_8320(blockPos).method_26215()) {
               class_2350 side = blockHitResult.method_17780();
               if (mc.field_1761.method_2902(blockPos, side)) {
                  mc.field_1713.method_3054(blockPos, side);
                  mc.field_1724.method_6104(class_1268.field_5808);
               }
            }
         } else {
            mc.field_1761.method_2925();
         }
      }

   }

   // $FF: synthetic method
   private static String d834(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 8208 + var3 & (54 ^ 201));
      }

      return new String(var2);
   }
}
