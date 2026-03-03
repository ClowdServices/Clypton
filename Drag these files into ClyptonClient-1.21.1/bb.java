import java.util.Base64;

enum bb {
   LOGO,
   WATERMARK,
   INFO,
   TIME,
   COORDINATES,
   MODULE_LIST,
   RADAR,
   EFFECTS,
   HOTBAR,
   KEYSTROKES,
   COMPASS,
   COMBAT_STATS,
   ARMOR_DURABILITY;

   // $FF: synthetic method
   private static bb[] $values() {
      bb[] var10000 = new bb[]{LOGO, WATERMARK, INFO, TIME, COORDINATES, MODULE_LIST, RADAR, null, null, null, null, null, null};
      var10000[48 ^ 55] = EFFECTS;
      var10000[8] = HOTBAR;
      var10000[9] = KEYSTROKES;
      var10000[71 ^ 77] = COMPASS;
      var10000[128 ^ 139] = COMBAT_STATS;
      var10000[12] = ARMOR_DURABILITY;
      return var10000;
   }

   // $FF: synthetic method
   private static String d818(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 8398 + var3 & (172 ^ 83));
      }

      return new String(var2);
   }
}
