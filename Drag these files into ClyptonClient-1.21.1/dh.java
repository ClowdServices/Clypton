import java.util.Base64;
import net.minecraft.class_1944;

public final class dh extends bf {
   private final gn minimumLightLevel = new gn(db.of(d410("a0FOQl8MYUtZVV0=")), 0.0D, 15.0D, 15.0D, 1.0D);
   private final cu<class_1944> lightType;

   public dh() {
      super(db.of(d410("YV1FRkleRElHRA==")), db.of(d410("a0FOQl9fDVtfEEhdRkYVQVhKVV4a")), -1, hk.RENDER);
      this.lightType = new cu(d410("a0FOQl8MeVdfVQ=="), class_1944.field_9282, class_1944.class);
      this.addSettings(new ab[]{this.minimumLightLevel, this.lightType});
   }

   public void onEnable() {
      try {
         mc.field_1769.method_3279();
      } catch (Exception var2) {
      }

   }

   public void onDisable() {
      try {
         mc.field_1769.method_3279();
      } catch (Exception var2) {
      }

   }

   public int getLuminance(class_1944 type) {
      return this.isEnabled() && type == this.lightType.getValue() ? this.minimumLightLevel.getIntValue() : 0;
   }

   // $FF: synthetic method
   private static String d410(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 1319 + var3 & (195 ^ 60));
      }

      return new String(var2);
   }
}
