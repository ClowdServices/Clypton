import java.util.Base64;
import net.minecraft.class_310;

public class fd extends bf {
   private final ff rgbMode = new ff(d331("V0FFKERlb2k="), false);
   private final gn rgbSpeed = new gn(d331("V0FFKFp6bmlp"), 1.0D, 5.0D, 1.0D, 0.1D);
   private final gn rgbSaturation = new gn(d331("V0FFKFprf3l/b3t5fnw="), 0.0D, 1.0D, 0.8D, 0.05D);
   private final gn rgbBrightness = new gn(d331("V0FFKEt4YmtlemF1YmE="), 0.0D, 1.0D, 1.0D, 0.05D);
   private final ff syncRGB = new ff(d331("Vn9paylYTE4="), true);
   private final ff customColors = new ff(d331("RnN0fGZnK09iYmBiYg=="), false);
   private final cu<du> colorToEdit;

   public fd() {
      super(d331("UW5iZWxHamJsaWpi"), d331("RnN0fGZnYnZoLkx8eHF4U0BfN2xxf3Z5PX1wTE5QUA=="), 0, hk.CLIENT);
      this.colorToEdit = new cu(d331("QGJufClJZGBifA=="), du.PRIMARY, du.class);
      this.addSettings(new ab[]{this.rgbMode, this.rgbSpeed, this.rgbSaturation, this.rgbBrightness, this.syncRGB, this.customColors, this.colorToEdit});
   }

   public void onEnable() {
      super.onEnable();
      class_310.method_1551().execute(() -> {
         if (class_310.method_1551().field_1755 != null) {
            class_310.method_1551().method_1507(new id(class_310.method_1551().field_1755));
         }

      });
      this.setEnabled(false);
   }

   public ff getRgbMode() {
      return this.rgbMode;
   }

   public gn getRgbSpeed() {
      return this.rgbSpeed;
   }

   public gn getRgbSaturation() {
      return this.rgbSaturation;
   }

   public gn getRgbBrightness() {
      return this.rgbBrightness;
   }

   public ff getSyncRGB() {
      return this.syncRGB;
   }

   public ff getCustomColors() {
      return this.customColors;
   }

   public cu<du> getColorToEdit() {
      return this.colorToEdit;
   }

   // $FF: synthetic method
   private static String d331(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 9775 - 42 + var3 & 255);
      }

      return new String(var2);
   }
}
