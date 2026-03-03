import java.util.Base64;
import net.minecraft.class_2596;
import net.minecraft.class_2708;

public final class fx extends bf {
   private final ff filterExtremePositions = (new ff(db.of(d273("9tjex9HHlvLAzcje0die76+yq7etqqi0")), true)).setDescription(db.of(d273("9tjex9HHxZfI1snSyNTR0eCxo6CvoLK06L6jv6TtraC/o7a6urSisqv5uL6lsrC7wI+NkYmEiselgISOj5+PiYTRkJyBm5KE")));

   public fx() {
      super(db.of(d273("5MPT0NHHxfHRwQ==")), db.of(d273("9tjex9HHxZfR18za0NTan7CusaqwrKmp6LmrqKeourzwt6C8ufWlsqqvv6n8qba+lMGBgpGWg8eturrLm4KMjZyYnJQ=")), -1, hk.DONUT);
      this.addSettings(new ab[]{this.filterExtremePositions});
      this.setVisible(false);
      this.setEnabled(true);
   }

   @cp
   public void onPacketReceive(ak event) {
      if (this.isEnabled() && this.filterExtremePositions.getValue()) {
         class_2596<?> packet = event.packet;
         if (packet instanceof class_2708) {
            class_2708 posPacket = (class_2708)packet;
            double x = posPacket.method_11734();
            double y = posPacket.method_11735();
            double z = posPacket.method_11738();
            boolean isInvalid = Double.isNaN(x) || Double.isNaN(y) || Double.isNaN(z) || Double.isInfinite(x) || Double.isInfinite(y) || Double.isInfinite(z) || Math.abs(x) > 3.0E7D || Math.abs(y) > 3.0E7D || Math.abs(z) > 3.0E7D;
            if (isInvalid) {
               event.cancel();
            }
         }

      }
   }

   // $FF: synthetic method
   private static String d273(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 5040 + var3 & (72 ^ 183));
      }

      return new String(var2);
   }
}
