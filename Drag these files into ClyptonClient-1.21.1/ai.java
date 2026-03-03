import java.awt.Color;
import java.util.Base64;

public enum ai {
   CYBER_NEON(d406("CTIuKDxvHjQ9PQ=="), new Color(0, 207 ^ 22, 255), new Color(255, 0, 150), new Color(15, 15, 25), new Color(204 ^ 213, 25, 40), new Color(0, 255, 226 ^ 42)),
   CYBERWAVE(d406("CTIuKDw4MSc3"), new Color(76 ^ 179, 20, 147), new Color(39 ^ 173, 43, 161 ^ 67), new Color(177 ^ 187, 10, 18), new Color(20, 20, 102 ^ 70), new Color(239 ^ 16, 100, 200)),
   QUANTUM_FLUX(d406("Gz4tIzo6PXEUPyEt"), new Color(0, 224 ^ 31, 58 ^ 94), new Color(0, 57 ^ 241, 27 ^ 141), new Color(8, 12, 16), new Color(141 ^ 159, 22, 28), new Color(0, 255, 194 ^ 84)),
   MATRIX(d406("Byo4Pyc3"), new Color(0, 1 ^ 254, 100), new Color(0, 200, 80), new Color(0, 0, 0), new Color(10, 32 ^ 47, 10), new Color(0, 189 ^ 66, 100)),
   TECH_BLUEPRINT(d406("Hi4vJW4NPCQ3IyY8OCM="), new Color(0, 245 ^ 99, 255), new Color(0, 218 ^ 18, 255), new Color(5, 93 ^ 87, 20), new Color(15, 20, 35), new Color(77 ^ 41, 200, 174 ^ 81)),
   FROSTBYTE(d406("DDkjPjotKSU3"), new Color(65 ^ 37, 200, 255), new Color(180, 65 ^ 157, 255), new Color(8, 12, 86 ^ 68), new Color(18, 152 ^ 129, 35), new Color(150, 29 ^ 193, 103 ^ 152)),
   PURPLE_DREAM(d406("Gj4+PSIqcBUgNjU4"), new Color(86 ^ 220, 43, 226), new Color(136 ^ 27, 128 ^ 240, 219), new Color(15, 10, 25), new Color(25, 18, 35), new Color(20 ^ 160, 130, 182 ^ 73)),
   MIDNIGHT_PURPLE(d406("ByIoIycoOCVyNzUnPQ=="), new Color(82 ^ 175, 180 ^ 72, 252, 59 ^ 196), new Color(232 ^ 182, 28 ^ 71, 79 ^ 20, 55 ^ 200), new Color(0, 0, 0, 240), new Color(60, 56, 56, 150), new Color(220, 180, 255));

   public final String displayName;
   public final Color primaryAccent;
   public final Color secondaryAccent;
   public final Color background;
   public final Color panelBackground;
   public final Color highlightGlow;

   private ai(String displayName, Color primaryAccent, Color secondaryAccent, Color background, Color panelBackground, Color highlightGlow) {
      this.displayName = displayName;
      this.primaryAccent = primaryAccent;
      this.secondaryAccent = secondaryAccent;
      this.background = background;
      this.panelBackground = panelBackground;
      this.highlightGlow = highlightGlow;
   }

   // $FF: synthetic method
   private static ai[] $values() {
      ai[] var10000 = new ai[16 ^ 24];
      var10000[0] = CYBER_NEON;
      var10000[1] = CYBERWAVE;
      var10000[2] = QUANTUM_FLUX;
      var10000[3] = MATRIX;
      var10000[4] = TECH_BLUEPRINT;
      var10000[5] = FROSTBYTE;
      var10000[6] = PURPLE_DREAM;
      var10000[7] = MIDNIGHT_PURPLE;
      return var10000;
   }

   // $FF: synthetic method
   private static String d406(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 5067 - 897 + var3 & 255);
      }

      return new String(var2);
   }
}
