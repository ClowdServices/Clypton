import java.util.Base64;

public class cy extends bf {
   private final bu fakeName = new bu(d779("AiQtImgHKyYp"), d779("FCknPi07"));

   public cy() {
      super(db.of(d779("CiQrImgZOCQ4KC07")), db.of(d779("FiA2KykqLzhsNCE6InE8MjkwdiAxLTJ7OzQoOg5BDQ0BSw==")), -1, hk.MISC);
      this.addSettings(new ab[]{this.fakeName});
   }

   public void onEnable() {
      super.onEnable();
   }

   public void onDisable() {
      super.onDisable();
   }

   public String getFakeName() {
      return this.fakeName.getValue();
   }

   // $FF: synthetic method
   private static String d779(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 4983 - 51 + var3 & 255);
      }

      return new String(var2);
   }
}
