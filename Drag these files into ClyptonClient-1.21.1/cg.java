import java.awt.Color;
import java.util.Base64;
import java.util.Iterator;
import net.minecraft.class_1542;
import net.minecraft.class_243;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_7833;

public final class cg extends bf {
   private final gn alpha = new gn(db.of(d42("iKa7pKw=")), 1.0D, 255.0D, 125.0D, 1.0D);
   private final ff tracers = (new ff(db.of(d42("nbiqr6i8vA==")), false)).setDescription(db.of(d42("jbiqu77urvC9u72x9bClt7T6orOorP+QjYOagZfGk4fJnoOJzYeblZw=")));

   public cg() {
      super(db.of(d42("gL6uoYidnw==")), db.of(d42("m6+lqKi8vPC4pra5pvajsKu1rru1/qiBjY6Q")), -1, hk.RENDER);
      this.addSettings(new ab[]{this.alpha, this.tracers});
   }

   public void onEnable() {
      super.onEnable();
   }

   public void onDisable() {
      super.onDisable();
   }

   @cp
   public void onRender3D(bg render3DEvent) {
      this.renderItems(render3DEvent);
   }

   private void renderItems(bg event) {
      if (mc.field_1687 != null && mc.field_1724 != null) {
         class_4184 cam = iq.getCamera();
         class_4587 matrixStack;
         if (cam != null) {
            class_243 camPos = iq.getCameraPos();
            matrixStack = event.matrixStack;
            matrixStack.method_22903();
            matrixStack.method_22907(class_7833.field_40714.rotationDegrees(cam.method_19329()));
            matrixStack.method_22907(class_7833.field_40716.rotationDegrees(cam.method_19330() + 180.0F));
            matrixStack.method_22904(-camPos.field_1352, -camPos.field_1351, -camPos.field_1350);
         }

         Color color = new Color(36 ^ 219, 255, 255, this.alpha.getIntValue());
         Iterator var8 = mc.field_1687.method_8390(class_1542.class, mc.field_1724.method_5829().method_1014(128.0D), (entity) -> {
            return true;
         }).iterator();

         while(var8.hasNext()) {
            class_1542 itemEntity = (class_1542)var8.next();
            if (itemEntity != null) {
               class_243 pos = itemEntity.method_19538();
               iq.renderFilledBox(event.matrixStack, (float)(pos.field_1352 - 0.125D), (float)pos.field_1351, (float)(pos.field_1350 - 0.125D), (float)(pos.field_1352 + 0.125D), (float)(pos.field_1351 + 0.25D), (float)(pos.field_1350 + 0.125D), color);
               if (this.tracers.getValue() && mc.field_1765 != null) {
                  iq.renderLine(event.matrixStack, new Color(248 ^ 7, 255, 71 ^ 184, 14 ^ 241), mc.field_1765.method_17784(), new class_243(pos.field_1352, pos.field_1351 + 0.125D, pos.field_1350));
               }
            }
         }

         matrixStack = event.matrixStack;
         matrixStack.method_22909();
      }
   }

   // $FF: synthetic method
   private static String d42(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 10185 + var3 & 255);
      }

      return new String(var2);
   }
}
