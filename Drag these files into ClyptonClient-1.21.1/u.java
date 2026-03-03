import java.awt.Color;
import java.util.Base64;
import java.util.Iterator;
import net.minecraft.class_1657;
import net.minecraft.class_243;
import net.minecraft.class_2561;
import net.minecraft.class_332;
import net.minecraft.class_3532;
import net.minecraft.class_4184;
import net.minecraft.class_437;
import net.minecraft.class_4587;
import net.minecraft.class_7833;

public final class u extends bf {
   private final gn alpha = new gn(db.of(d310("DjwhOjI=")), 0.0D, 255.0D, 100.0D, 1.0D);
   private final gn lineWidth = new gn(db.of(d310("Azk/N3MjPDIjMA==")), 1.0D, 10.0D, 1.0D, 1.0D);
   private final ff tracers = (new ff(db.of(d310("GyIwMTYmJg==")), true)).setDescription(db.of(d310("CyIwJSB0NHY7MTc/ezovMTJAGA0WFkUWCwkQDxlMGQFPBBkXUxsBHhIK")));
   private final jb color = (new jb(db.of(d310("DD89PSE=")), new Color(44 ^ 134, 0, 240 ^ 15, 255))).setDescription(db.of(d310("Gzg0cjA7OTkleDY8eyg1O38lMjI=")));
   private final ep openColorPicker = (new ep(db.of(d310("ACA0PHMXOjo4KnkKMj82Oy0=")), () -> {
      if (mc != null) {
         mc.method_1507(new class_437(this, class_2561.method_43470(d310("DD89PSF0BT80Mzwo"))) {
            public void method_25394(class_332 context, int mouseX, int mouseY, float delta) {
               this.method_25420(context, mouseX, mouseY, delta);
               super.method_25394(context, mouseX, mouseY, delta);
               context.method_25300(this.field_22793, d120("2/b29O69zvbDysfRhI3x7viA"), this.field_22789 / 2, 20, -1);
            }

            public void method_25419() {
               super.method_25419();
            }

            // $FF: synthetic method
            private static String d120(String var0) {
               byte[] var1 = Base64.getDecoder().decode(var0);
               byte[] var2 = new byte[var1.length];

               for(int var3 = 0; var3 < var1.length; ++var3) {
                  var2[var3] = (byte)(var1[var3] ^ 8600 + var3 & (86 ^ 169));
               }

               return new String(var2);
            }
         });
      }

   })).setDescription(db.of(d310("ACA0PHM3Ojo4KnkqMj82Oy0=")));

   public u() {
      super(db.of(d310("HzwwKzYmdRMECA==")), db.of(d310("HTU/NjYmJnYnNDgjPi4ufisIEw0WAw1GEAkFBhg=")), -1, hk.RENDER);
      this.addSettings(new ab[]{this.alpha, this.lineWidth, this.tracers, this.openColorPicker, this.color});
   }

   public void onEnable() {
      super.onEnable();
   }

   public void onDisable() {
      super.onDisable();
   }

   @cp
   public void onRender3D(bg render3DEvent) {
      if (mc.field_1687 != null && mc.field_1724 != null) {
         class_4184 camera = iq.getCamera();
         if (camera != null) {
            class_243 cameraPos = iq.getCameraPos();
            class_4587 matrixStack = render3DEvent.matrixStack;
            float tickDelta = render3DEvent.tickDelta;
            matrixStack.method_22903();
            matrixStack.method_22907(class_7833.field_40714.rotationDegrees(camera.method_19329()));
            matrixStack.method_22907(class_7833.field_40716.rotationDegrees(camera.method_19330() + 180.0F));
            matrixStack.method_22904(-cameraPos.field_1352, -cameraPos.field_1351, -cameraPos.field_1350);
            Color espColor = new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), this.alpha.getIntValue());
            Iterator var7 = mc.field_1687.method_18456().iterator();

            while(var7.hasNext()) {
               class_1657 player = (class_1657)var7.next();
               if (player != mc.field_1724) {
                  double x = class_3532.method_16436((double)tickDelta, player.field_6014, player.method_23317());
                  double y = class_3532.method_16436((double)tickDelta, player.field_6036, player.method_23318());
                  double z = class_3532.method_16436((double)tickDelta, player.field_5969, player.method_23321());
                  iq.renderFilledBox(matrixStack, (float)x - player.method_17681() / 2.0F, (float)y, (float)z - player.method_17681() / 2.0F, (float)x + player.method_17681() / 2.0F, (float)y + player.method_17682(), (float)z + player.method_17681() / 2.0F, espColor.brighter());
                  if (this.tracers.getValue() && mc.field_1765 != null) {
                     class_243 target = new class_243(x, y + (double)player.method_17682() / 2.0D, z);
                     iq.renderLine(matrixStack, espColor, mc.field_1765.method_17784(), target, this.lineWidth.getFloatValue());
                  }
               }
            }

            matrixStack.method_22909();
         }
      }
   }

   // $FF: synthetic method
   private static String d310(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 2383 + var3 & 255);
      }

      return new String(var2);
   }
}
