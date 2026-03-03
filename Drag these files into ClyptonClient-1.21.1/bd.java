import java.util.Base64;

public class bd extends bf {
   private static bd instance;
   private final gn delay = new gn(d175("+6Wto7rk7bWiq6akr7/k"), 5.0D, 1.0D, 60.0D, 1.0D);

   public bd() {
      super(d175("/rW1reOWoKWopqevqLg="), d175("/rW1ra6lsa+kqaWmsuy/q6y/v7y2t6H2tr6tv6n8ubesg46MjYGGkg=="), 0, hk.CLIENT);
      this.addSettings(new ab[]{this.delay});
      instance = this;
   }

   public static bd getInstance() {
      return instance;
   }

   public int getDelayTicks() {
      return (int)this.delay.getValue() * (238 ^ 250);
   }

   public int getDelaySeconds() {
      return (int)this.delay.getValue();
   }

   // $FF: synthetic method
   private static String d175(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 7589 - 742 + var3 & (31 ^ 224));
      }

      return new String(var2);
   }
}
