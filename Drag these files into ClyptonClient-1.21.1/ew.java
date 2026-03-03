import java.util.Base64;

public class ew extends bf {
   public ew() {
      super(db.of(d728("qoWFioSJnA==")), db.of("Open config manager to save/load/share configs"), -1, hk.CLIENT);
   }

   public void onEnable() {
      super.onEnable();
      if (mc != null && mc.field_1755 instanceof gf) {
         gf gui = (gf)mc.field_1755;
         if (gui.configPanel == null) {
            gui.configPanel = new fb(59 ^ 95, 100);
         }

         gui.configPanel.toggle();
      }

      this.setEnabled(false);
   }

   // $FF: synthetic method
   private static String d728(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 2793 + var3 & 255);
      }

      return new String(var2);
   }
}
