import java.util.Base64;
import java.util.function.Consumer;

public class ep extends ab {
   private final Consumer<Void> onClick;

   public ep(db name, Runnable onClick) {
      super(name);
      this.onClick = (v) -> {
         onClick.run();
      };
   }

   public ep(db name, Consumer<Void> onClick) {
      super(name);
      this.onClick = onClick;
   }

   public void onClick() {
      if (this.onClick != null) {
         this.onClick.accept((Object)null);
      }

   }

   public ep setDescription(CharSequence description) {
      super.setDescription(description);
      return this;
   }

   // $FF: synthetic method
   private static String d114(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 3835 + var3 & 255);
      }

      return new String(var2);
   }
}
