import java.util.Base64;

public final class aj extends bf {
   public aj() {
      super(db.of(d303("gb+viamnu7+j")), db.of(d303("hrquor7uu7i08puBkfayvLCutK79qrDAgpeQkIqLjpKMypKDmJzPuKS205GZk5qdl46I")), -1, hk.CLIENT);
   }

   public void onEnable() {
      super.onEnable();
      if (mc != null && mc.field_1724 != null) {
         mc.method_1507(new fe());
      }

      this.setEnabled(false);
   }

   // $FF: synthetic method
   private static String d303(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 3017 + var3 & 255);
      }

      return new String(var2);
   }
}
