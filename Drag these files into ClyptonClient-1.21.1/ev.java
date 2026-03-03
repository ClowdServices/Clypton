import java.util.Base64;
import net.minecraft.class_1657;

public final class ev extends bf {
   private final gn expand = new gn(db.of(d672("9MrD1dvS")), 0.0D, 2.0D, 0.5D, 0.05D);
   private final ff enableRender = new ff(d672("9NzS1tnTl+rc1N/Zzw=="), true);

   public ev() {
      super(db.of(d672("+dvH9trO")), db.of(d672("9MrD1dvSxJjYmsvQ3Mfasuax46yssqWnseQ=")), -1, hk.COMBAT);
      this.addSettings(new ab[]{this.enableRender, this.expand});
   }

   public void onEnable() {
      super.onEnable();
   }

   public void onDisable() {
      super.onDisable();
   }

   @cp
   public void onTargetMargin(bs targetMarginEvent) {
      if (targetMarginEvent.entity instanceof class_1657) {
         targetMarginEvent.cir.setReturnValue((float)this.expand.getValue());
      }

   }

   public double getHitboxExpansion() {
      return !this.enableRender.getValue() ? 0.0D : this.expand.getValue();
   }

   // $FF: synthetic method
   private static String d672(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 4409 - 392 + var3 & 255);
      }

      return new String(var2);
   }
}
