import java.awt.Color;
import java.util.Base64;
import net.minecraft.class_332;

public abstract class cn {
   protected int x;
   protected int y;
   protected int width;
   protected int height;
   protected String name;
   protected boolean enabled = true;

   public cn(String name, int x, int y, int width, int height) {
      this.name = name;
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
   }

   public abstract void render(class_332 var1, int var2, int var3, boolean var4, boolean var5);

   protected void renderBorder(class_332 context, boolean dragging, boolean selected) {
      Color borderColor = dragging ? v.PRIMARY : (selected ? v.PRIMARY_DARK : ch.safeColor(100, 48 ^ 68, 139, 180));
      Color bgColor = selected ? ch.safeColor(139, 92, 16 ^ 230, 25) : ch.safeColor(30, 135 ^ 174, 59, 60);
      iq.renderRoundedQuad(context.method_51448(), bgColor, (double)this.x, (double)this.y, (double)(this.x + this.width), (double)(this.y + this.height), 8.0D, 8.0D, 8.0D, 8.0D, 20.0D);
      iq.renderRoundedOutline(context, borderColor, (double)this.x, (double)this.y, (double)(this.x + this.width), (double)(this.y + this.height), 8.0D, 8.0D, 8.0D, 8.0D, selected ? 2.0D : (double)(dragging ? 2.5F : 1.0F), 20.0D);
      int infoWidth;
      int infoX;
      if (selected || dragging) {
         int nameWidth = i.getWidth(this.name);
         infoWidth = this.x + this.width / 2 - nameWidth / 2;
         infoX = this.y - 22;
         iq.renderRoundedQuad(context.method_51448(), v.PRIMARY, (double)(infoWidth - 8), (double)(infoX - 3), (double)(infoWidth + nameWidth + 8), (double)(infoX + 13), 4.0D, 4.0D, 4.0D, 4.0D, 20.0D);
         i.drawString(this.name, context, infoWidth, infoX, Color.WHITE.getRGB());
      }

      if (dragging) {
         String posInfo = String.format(d683("Vyo0djNNLzNz"), this.x, this.y);
         infoWidth = i.getWidth(posInfo);
         infoX = this.x + this.width / 2 - infoWidth / 2;
         int infoY = this.y + this.height + 6;
         iq.renderRoundedQuad(context.method_51448(), ch.safeColor(0, 0, 0, 220), (double)(infoX - 6), (double)(infoY - 2), (double)(infoX + infoWidth + 6), (double)(infoY + 12), 4.0D, 4.0D, 4.0D, 4.0D, 20.0D);
         i.drawString(posInfo, context, infoX, infoY, ch.safeColor(139, 92, 246, 240 ^ 15).getRGB());
      }

   }

   public boolean isHovered(int mouseX, int mouseY) {
      return mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + this.height;
   }

   public abstract void savePositionToModule(hf var1);

   // $FF: synthetic method
   private static String d683(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 7183 + var3 & 255);
      }

      return new String(var2);
   }
}
