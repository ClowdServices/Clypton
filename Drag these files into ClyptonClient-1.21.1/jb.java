import java.awt.Color;
import java.util.Base64;

public class jb extends ab {
   private Color value;

   public jb(CharSequence name, Color value) {
      super(name);
      this.value = value;
   }

   public jb(CharSequence name, Color value, CharSequence description) {
      super(name);
      this.value = value;
      this.description = description;
   }

   public Color getValue() {
      return this.value;
   }

   public void setValue(Color value) {
      this.value = value;
   }

   public int getRed() {
      return this.value.getRed();
   }

   public int getGreen() {
      return this.value.getGreen();
   }

   public int getBlue() {
      return this.value.getBlue();
   }

   public int getAlpha() {
      return this.value.getAlpha();
   }

   public int getRGB() {
      return this.value.getRGB();
   }

   public jb setDescription(CharSequence description) {
      super.setDescription(description);
      return this;
   }

   // $FF: synthetic method
   private static String d797(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 1918 - 556 + var3 & 255);
      }

      return new String(var2);
   }
}
