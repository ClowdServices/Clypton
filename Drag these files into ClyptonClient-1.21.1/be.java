import java.util.Base64;

public final class be extends bf {
   public final ff removeWater = new ff(db.of(d375("QXZsfGg=")), true);
   public final ff removeLava = new ff(db.of(d375("WnZueA==")), true);

   public be() {
      super(db.of(d375("WHhedW9yeFJoelJNQ1o=")), db.of(d375("RHJ1dmx+bz1qd0UBRE9RTEIHR19PWUBMVw9WQ11eFEJXQ11LGlpSWR5ZKTMnYg==")), -1, hk.RENDER);
      this.addSettings(new ab[]{this.removeWater, this.removeLava});
   }

   public float getOverlayOffset() {
      return this.isEnabled() ? 0.6F : 0.0F;
   }

   // $FF: synthetic method
   private static String d375(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 7446 + var3 & 255);
      }

      return new String(var2);
   }
}
