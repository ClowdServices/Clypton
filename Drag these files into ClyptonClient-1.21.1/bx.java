import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Base64;
import java.util.Iterator;
import net.minecraft.class_1657;
import net.minecraft.class_243;
import net.minecraft.class_286;
import net.minecraft.class_287;
import net.minecraft.class_289;
import net.minecraft.class_290;
import net.minecraft.class_327;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_640;
import net.minecraft.class_757;
import net.minecraft.class_7833;
import net.minecraft.class_293.class_5596;
import net.minecraft.class_327.class_6415;
import net.minecraft.class_4597.class_4598;
import org.joml.Matrix4f;

public final class bx extends bf {
   private final ff showHealth = new ff(db.of(d723("Azk9JHQdMzY0LTI=")), true);
   private final ff showDistance = new ff(db.of(d723("Azk9JHQRPyQsODQ4OQ==")), true);
   private final ff showPing = new ff(db.of(d723("Azk9JHQFPzk/")), false);
   private final gn scale = new gn(db.of(d723("AzIzPzE=")), 0.5D, 3.0D, 1.0D, 0.1D);
   private final gn range = new gn(db.of(d723("AjA8NDE=")), 16.0D, 512.0D, 128.0D, 16.0D);
   private final ff background = new ff(db.of(d723("EjAxODMnOSI2PQ==")), true);
   private final ff scaleWithDistance = new ff(db.of("Scale w/ Distance"), true);
   private final ff showSelf = new ff(db.of(d723("Azk9JHQGMzs+")), false);

   public bx() {
      super(db.of(d723("HjA/NiA0MRILCQ==")), db.of(d723("Azk9JCd1ODY1PC46Oy5+KwgTDRYDDUYQCQUGGA==")), -1, hk.RENDER);
      ab[] var10001 = new ab[52 ^ 60];
      var10001[0] = this.showHealth;
      var10001[1] = this.showDistance;
      var10001[2] = this.showPing;
      var10001[3] = this.scale;
      var10001[4] = this.range;
      var10001[5] = this.background;
      var10001[232 ^ 238] = this.scaleWithDistance;
      var10001[203 ^ 204] = this.showSelf;
      this.addSettings(var10001);
   }

   @cp
   public void onRender3D(bg event) {
      if (mc.field_1687 != null && mc.field_1724 != null) {
         class_4184 cam = iq.getCamera();
         class_243 camPos = iq.getCameraPos();
         if (cam != null && camPos != null) {
            class_4587 matrices = event.matrixStack;
            matrices.method_22903();
            matrices.method_22907(class_7833.field_40714.rotationDegrees(cam.method_19329()));
            matrices.method_22907(class_7833.field_40716.rotationDegrees(cam.method_19330() + 180.0F));
            matrices.method_22904(-camPos.field_1352, -camPos.field_1351, -camPos.field_1350);
            Iterator var5 = mc.field_1687.method_18456().iterator();

            while(true) {
               class_1657 player;
               do {
                  do {
                     if (!var5.hasNext()) {
                        matrices.method_22909();
                        return;
                     }

                     player = (class_1657)var5.next();
                  } while(!this.showSelf.getValue() && player == mc.field_1724);
               } while(player.method_5767() && player != mc.field_1724);

               if (!player.method_7325()) {
                  double dist = (double)mc.field_1724.method_5739(player);
                  if (!(dist > this.range.getValue())) {
                     player.method_5880(false);
                     this.renderPlayerNametag(player, matrices, event.tickDelta, dist);
                  }
               }
            }
         }
      }
   }

   private void renderPlayerNametag(class_1657 player, class_4587 matrices, float tickDelta, double distance) {
      class_243 playerPos = player.method_30950(tickDelta);
      double yOffset = (double)player.method_17682() + 0.5D;
      if (player.method_5715()) {
         yOffset -= 0.25D;
      }

      double x = playerPos.field_1352;
      double y = playerPos.field_1351 + yOffset;
      double z = playerPos.field_1350;
      String text = this.buildNametagText(player, distance);
      this.renderNametag(matrices, text, x, y, z, distance);
   }

   private String buildNametagText(class_1657 player, double distance) {
      StringBuilder text = new StringBuilder();
      text.append(player.method_5477().getString());
      if (this.showHealth.getValue()) {
         float health = player.method_6032();
         float absorption = player.method_6067();
         int healthInt = (int)Math.ceil((double)(health + absorption));
         String healthColor = this.getHealthColor(health, player.method_6063());
         text.append(" ").append(healthColor).append(healthInt);
      }

      if (this.showDistance.getValue()) {
         text.append(" §7[§e").append((int)distance).append("m§7]");
      }

      if (this.showPing.getValue() && mc.method_1562() != null) {
         class_640 playerListEntry = mc.method_1562().method_2871(player.method_5667());
         if (playerListEntry != null) {
            int ping = playerListEntry.method_2959();
            String pingColor = this.getPingColor(ping);
            text.append(" §7[").append(pingColor).append(ping).append("ms§7]");
         }
      }

      return text.toString();
   }

   private String getHealthColor(float health, float maxHealth) {
      float percentage = health / maxHealth;
      if (percentage > 0.75F) {
         return "§a";
      } else if (percentage > 0.5F) {
         return "§e";
      } else {
         return percentage > 0.25F ? "§6" : "§c";
      }
   }

   private String getPingColor(int ping) {
      if (ping < (125 ^ 79)) {
         return "§a";
      } else if (ping < 100) {
         return "§e";
      } else {
         return ping < (90 ^ 204) ? "§6" : "§c";
      }
   }

   private void renderNametag(class_4587 matrices, String text, double x, double y, double z, double distance) {
      matrices.method_22903();
      matrices.method_22904(x, y, z);
      matrices.method_22907(class_7833.field_40716.rotationDegrees(-mc.field_1773.method_19418().method_19330()));
      matrices.method_22907(class_7833.field_40714.rotationDegrees(mc.field_1773.method_19418().method_19329()));
      float baseScale = (float)(0.025D * this.scale.getValue());
      if (this.scaleWithDistance.getValue()) {
         baseScale *= (float)Math.max(1.0D, distance / 8.0D);
      }

      matrices.method_22905(-baseScale, -baseScale, baseScale);
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableDepthTest();
      RenderSystem.depthMask(false);
      class_327 textRenderer = mc.field_1772;
      int textWidth = textRenderer.method_1727(text);
      float halfWidth = (float)textWidth / 2.0F;
      Matrix4f matrix = matrices.method_23760().method_23761();
      if (this.background.getValue()) {
         float x1 = -halfWidth - 2.0F;
         float x2 = halfWidth + 2.0F;
         float y1 = -10.0F;
         float y2 = 1.0F;
         class_289 tessellator = class_289.method_1348();
         RenderSystem.setShader(class_757::method_34540);
         class_287 buffer = tessellator.method_60827(class_5596.field_27382, class_290.field_1576);
         buffer.method_22918(matrix, x1, y1, 0.0F).method_1336(0, 0, 0, 100);
         buffer.method_22918(matrix, x1, y2, 0.0F).method_1336(0, 0, 0, 195 ^ 167);
         buffer.method_22918(matrix, x2, y2, 0.0F).method_1336(0, 0, 0, 100);
         buffer.method_22918(matrix, x2, y1, 0.0F).method_1336(0, 0, 0, 2 ^ 102);
         class_286.method_43433(buffer.method_60800());
      }

      class_4598 immediate = mc.method_22940().method_23000();
      textRenderer.method_27521(text, -halfWidth, -8.0F, -1, false, matrix, immediate, class_6415.field_33994, 0, 15729265 - 385);
      immediate.method_22993();
      RenderSystem.depthMask(true);
      RenderSystem.enableDepthTest();
      RenderSystem.disableBlend();
      matrices.method_22909();
   }

   // $FF: synthetic method
   private static String d723(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 8016 + var3 & 255);
      }

      return new String(var2);
   }
}
