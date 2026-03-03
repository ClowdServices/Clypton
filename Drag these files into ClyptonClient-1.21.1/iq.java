import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.Base64;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_243;
import net.minecraft.class_286;
import net.minecraft.class_287;
import net.minecraft.class_289;
import net.minecraft.class_290;
import net.minecraft.class_293;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_3532;
import net.minecraft.class_4184;
import net.minecraft.class_437;
import net.minecraft.class_4587;
import net.minecraft.class_5944;
import net.minecraft.class_757;
import net.minecraft.class_7833;
import net.minecraft.class_8251;
import net.minecraft.class_293.class_5596;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

public final class iq {
   public static class_8251 vertexSorter;
   public static boolean rendering3D = true;

   public static class_243 getCameraPos() {
      return getCamera().method_19326();
   }

   public static class_4184 getCamera() {
      return class_310.method_1551().field_1773.method_19418();
   }

   public static double deltaTime() {
      class_310 client = class_310.method_1551();
      return client != null && client.method_47599() > 0 ? 1.0D / (double)client.method_47599() : 1.0D;
   }

   public static float fast(float n, float n2, float n3) {
      return (1.0F - class_3532.method_15363((float)(deltaTime() * (double)n3), 0.0F, 1.0F)) * n + class_3532.method_15363((float)(deltaTime() * (double)n3), 0.0F, 1.0F) * n2;
   }

   public static class_243 getPlayerLookVec(class_1657 playerEntity) {
      float cos = class_3532.method_15362(playerEntity.method_36454() * 0.017453292F - 3.1415927F);
      float sin = class_3532.method_15374(playerEntity.method_36454() * 0.017453292F - 3.1415927F);
      float cos2 = class_3532.method_15362(playerEntity.method_36455() * 0.017453292F);
      return (new class_243((double)(sin * cos2), (double)class_3532.method_15374(playerEntity.method_36455() * 0.017453292F), (double)(cos * cos2))).method_1029();
   }

   public static void unscaledProjection() {
      class_310 client = class_310.method_1551();
      if (client.method_22683() != null) {
         vertexSorter = RenderSystem.getVertexSorting();
         RenderSystem.setProjectionMatrix((new Matrix4f()).setOrtho(0.0F, (float)client.method_22683().method_4489(), (float)client.method_22683().method_4506(), 0.0F, 1000.0F, 21000.0F), class_8251.field_43361);
         rendering3D = false;
      }
   }

   public static void scaledProjection() {
      class_310 client = class_310.method_1551();
      if (client.method_22683() != null) {
         float scaleFactor = (float)client.method_22683().method_4495();
         float width = (float)client.method_22683().method_4489() / scaleFactor;
         float height = (float)client.method_22683().method_4506() / scaleFactor;
         RenderSystem.setProjectionMatrix((new Matrix4f()).setOrtho(0.0F, width, height, 0.0F, 1000.0F, 21000.0F), vertexSorter);
         rendering3D = true;
      }
   }

   public static void renderRoundedQuad(class_4587 matrixStack, Color color, double n, double n2, double n3, double n4, double n5, double n6, double n7, double n8, double n9) {
      int rgb = color.getRGB();
      Matrix4f positionMatrix = matrixStack.method_23760().method_23761();
      RenderSystem.enableBlend();
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.setShader(class_757::method_34540);
      renderRoundedQuadInternal(positionMatrix, (float)(rgb >> 16 & 255) / 255.0F, (float)(rgb >> (121 ^ 113) & 255) / 255.0F, (float)(rgb & (102 ^ 153)) / 255.0F, (float)(rgb >> 24 & 255) / 255.0F, n, n2, n3, n4, n5, n6, n7, n8, n9);
      RenderSystem.enableCull();
      RenderSystem.disableBlend();
   }

   private static void setup() {
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
   }

   private static void cleanup() {
      RenderSystem.enableCull();
      RenderSystem.disableBlend();
   }

   public static void renderRoundedQuad(class_4587 matrixStack, Color color, double n, double n2, double n3, double n4, double n5, double n6) {
      renderRoundedQuad(matrixStack, color, n, n2, n3, n4, n5, n5, n5, n5, n6);
   }

   public static void renderRoundedOutlineInternal(Matrix4f matrix4f, float n, float n2, float n3, float n4, double n5, double n6, double n7, double n8, double n9, double n10, double n11, double n12, double n13, double n14) {
      class_287 begin = class_289.method_1348().method_60827(class_5596.field_27380, class_290.field_1576);
      double[][] array = new double[][]{{n7 - n12, n8 - n12, n12}, {n7 - n10, n6 + n10, n10}, {n5 + n9, n6 + n9, n9}, {n5 + n11, n8 - n11, n11}};

      for(int i = 0; i < 4; ++i) {
         double[] array2 = array[i];
         double n15 = array2[2];

         double radians2;
         double sin2;
         double n18;
         double cos2;
         double n19;
         for(radians2 = (double)i * 90.0D; radians2 < 90.0D + (double)i * 90.0D; radians2 += 90.0D / n14) {
            sin2 = Math.toRadians(radians2);
            n18 = Math.sin((double)((float)sin2));
            cos2 = n18 * n15;
            n19 = Math.cos((double)((float)sin2));
            double n17 = n19 * n15;
            begin.method_22918(matrix4f, (float)array2[0] + (float)cos2, (float)array2[1] + (float)n17, 0.0F).method_22915(n, n2, n3, n4);
            begin.method_22918(matrix4f, (float)(array2[0] + (double)((float)cos2) + n18 * n13), (float)(array2[1] + (double)((float)n17) + n19 * n13), 0.0F).method_22915(n, n2, n3, n4);
         }

         radians2 = Math.toRadians(90.0D + (double)i * 90.0D);
         sin2 = Math.sin((double)((float)radians2));
         n18 = sin2 * n15;
         cos2 = Math.cos((double)((float)radians2));
         n19 = cos2 * n15;
         begin.method_22918(matrix4f, (float)array2[0] + (float)n18, (float)array2[1] + (float)n19, 0.0F).method_22915(n, n2, n3, n4);
         begin.method_22918(matrix4f, (float)(array2[0] + (double)((float)n18) + sin2 * n13), (float)(array2[1] + (double)((float)n19) + cos2 * n13), 0.0F).method_22915(n, n2, n3, n4);
      }

      double[] array3 = array[0];
      double n20 = array3[2];
      begin.method_22918(matrix4f, (float)array3[0], (float)array3[1] + (float)n20, 0.0F).method_22915(n, n2, n3, n4);
      begin.method_22918(matrix4f, (float)array3[0], (float)(array3[1] + (double)((float)n20) + n13), 0.0F).method_22915(n, n2, n3, n4);
      class_286.method_43433(begin.method_60800());
   }

   public static void setScissorRegion(int n, int n2, int n3, int n4) {
      class_310 instance = class_310.method_1551();
      class_437 currentScreen = instance.field_1755;
      int n5;
      if (instance.field_1755 == null) {
         n5 = 0;
      } else {
         n5 = currentScreen.field_22790 - n4;
      }

      double scaleFactor = class_310.method_1551().method_22683().method_4495();
      GL11.glScissor((int)((double)n * scaleFactor), (int)((double)n5 * scaleFactor), (int)((double)(n3 - n) * scaleFactor), (int)((double)(n4 - n2) * scaleFactor));
      GL11.glEnable(3089);
   }

   public static void renderCircle(class_4587 matrixStack, Color color, double n, double n2, double n3, int n4) {
      int clamp = class_3532.method_15340(n4, 4, 360);
      int rgb = color.getRGB();
      Matrix4f positionMatrix = matrixStack.method_23760().method_23761();
      setup();
      RenderSystem.setShader(class_757::method_34540);
      class_287 begin = class_289.method_1348().method_60827(class_5596.field_27381, class_290.field_1576);

      for(int i = 0; i < (436 ^ 220); i += Math.min(360 / clamp, (474 ^ 178) - i)) {
         double radians = Math.toRadians((double)i);
         begin.method_22918(positionMatrix, (float)(n + Math.sin(radians) * n3), (float)(n2 + Math.cos(radians) * n3), 0.0F).method_22915((float)(rgb >> (79 ^ 95) & 255) / 255.0F, (float)(rgb >> (215 ^ 223) & 255) / 255.0F, (float)(rgb & 255) / 255.0F, (float)(rgb >> (167 ^ 191) & 255) / 255.0F);
      }

      class_286.method_43433(begin.method_60800());
      cleanup();
   }

   public static void renderShaderRect(class_4587 matrixStack, Color color, Color color2, Color color3, Color color4, float n, float n2, float n3, float n4, float n5, float n6) {
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      class_287 begin = class_289.method_1348().method_60827(class_5596.field_27382, class_290.field_1592);
      begin.method_22918(matrixStack.method_23760().method_23761(), n - 10.0F, n2 - 10.0F, 0.0F);
      begin.method_22918(matrixStack.method_23760().method_23761(), n - 10.0F, n2 + n4 + 20.0F, 0.0F);
      begin.method_22918(matrixStack.method_23760().method_23761(), n + n3 + 20.0F, n2 + n4 + 20.0F, 0.0F);
      begin.method_22918(matrixStack.method_23760().method_23761(), n + n3 + 20.0F, n2 - 10.0F, 0.0F);
      class_286.method_43433(begin.method_60800());
      RenderSystem.disableBlend();
   }

   public static void renderRoundedOutline(class_332 drawContext, Color color, double n, double n2, double n3, double n4, double n5, double n6, double n7, double n8, double n9, double n10) {
      int rgb = color.getRGB();
      Matrix4f positionMatrix = drawContext.method_51448().method_23760().method_23761();
      setup();
      RenderSystem.setShader(class_757::method_34540);
      renderRoundedOutlineInternal(positionMatrix, (float)(rgb >> 16 & 255) / 255.0F, (float)(rgb >> 8 & (220 ^ 35)) / 255.0F, (float)(rgb & (204 ^ 51)) / 255.0F, (float)(rgb >> 24 & (123 ^ 132)) / 255.0F, n, n2, n3, n4, n5, n6, n7, n8, n9, n10);
      cleanup();
   }

   public static class_4587 matrixFrom(double n, double n2, double n3) {
      class_4587 matrixStack = new class_4587();
      class_4184 camera = class_310.method_1551().field_1773.method_19418();
      matrixStack.method_22907(class_7833.field_40714.rotationDegrees(camera.method_19329()));
      matrixStack.method_22907(class_7833.field_40716.rotationDegrees(camera.method_19330() + 180.0F));
      matrixStack.method_22904(n - camera.method_19326().field_1352, n2 - camera.method_19326().field_1351, n3 - camera.method_19326().field_1350);
      return matrixStack;
   }

   public static void renderQuad(class_4587 matrixStack, float n, float n2, float n3, float n4, int n5) {
      float n6 = (float)(n5 >> 24 & 255) / 255.0F;
      float n7 = (float)(n5 >> 16 & 255) / 255.0F;
      float n8 = (float)(n5 >> 8 & 255) / 255.0F;
      float n9 = (float)(n5 & (183 ^ 72)) / 255.0F;
      matrixStack.method_22903();
      matrixStack.method_22905(0.5F, 0.5F, 0.5F);
      matrixStack.method_22904((double)n, (double)n2, 0.0D);
      class_289 instance = class_289.method_1348();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      class_287 begin = instance.method_60827(class_5596.field_27382, class_290.field_1576);
      begin.method_22912(0.0F, 0.0F, 0.0F).method_22915(n7, n8, n9, n6);
      begin.method_22912(0.0F, n4, 0.0F).method_22915(n7, n8, n9, n6);
      begin.method_22912(n3, n4, 0.0F).method_22915(n7, n8, n9, n6);
      begin.method_22912(n3, 0.0F, 0.0F).method_22915(n7, n8, n9, n6);
      class_286.method_43433(begin.method_60800());
      RenderSystem.disableBlend();
      matrixStack.method_22909();
   }

   public static void renderRoundedQuadInternal(Matrix4f matrix4f, float n, float n2, float n3, float n4, double n5, double n6, double n7, double n8, double n9, double n10, double n11, double n12, double n13) {
      class_287 begin = class_289.method_1348().method_60827(class_5596.field_27381, class_290.field_1576);
      double[][] array = new double[][]{{n7 - n12, n8 - n12, n12}, {n7 - n10, n6 + n10, n10}, {n5 + n9, n6 + n9, n9}, {n5 + n11, n8 - n11, n11}};

      for(int i = 0; i < 4; ++i) {
         double[] array2 = array[i];
         double n14 = array2[2];

         double radians2;
         for(radians2 = (double)i * 90.0D; radians2 < 90.0D + (double)i * 90.0D; radians2 += 90.0D / n13) {
            double radians = Math.toRadians(radians2);
            begin.method_22918(matrix4f, (float)array2[0] + (float)(Math.sin((double)((float)radians)) * n14), (float)array2[1] + (float)(Math.cos((double)((float)radians)) * n14), 0.0F).method_22915(n, n2, n3, n4);
         }

         radians2 = Math.toRadians(90.0D + (double)i * 90.0D);
         begin.method_22918(matrix4f, (float)array2[0] + (float)(Math.sin((double)((float)radians2)) * n14), (float)array2[1] + (float)(Math.cos((double)((float)radians2)) * n14), 0.0F).method_22915(n, n2, n3, n4);
      }

      class_286.method_43433(begin.method_60800());
   }

   public static void renderFilledBox(class_4587 matrixStack, float n, float n2, float n3, float n4, float n5, float n6, Color color) {
      RenderSystem.enableBlend();
      RenderSystem.disableDepthTest();
      RenderSystem.setShaderColor((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, (float)color.getAlpha() / 255.0F);
      RenderSystem.setShader(class_757::method_34539);
      class_287 begin = class_289.method_1348().method_60827(class_5596.field_27380, class_290.field_1592);
      begin.method_22918(matrixStack.method_23760().method_23761(), n, n2, n3);
      begin.method_22918(matrixStack.method_23760().method_23761(), n, n2, n3);
      begin.method_22918(matrixStack.method_23760().method_23761(), n, n2, n3);
      begin.method_22918(matrixStack.method_23760().method_23761(), n, n2, n6);
      begin.method_22918(matrixStack.method_23760().method_23761(), n, n5, n3);
      begin.method_22918(matrixStack.method_23760().method_23761(), n, n5, n6);
      begin.method_22918(matrixStack.method_23760().method_23761(), n, n5, n6);
      begin.method_22918(matrixStack.method_23760().method_23761(), n, n2, n6);
      begin.method_22918(matrixStack.method_23760().method_23761(), n4, n5, n6);
      begin.method_22918(matrixStack.method_23760().method_23761(), n4, n2, n6);
      begin.method_22918(matrixStack.method_23760().method_23761(), n4, n2, n6);
      begin.method_22918(matrixStack.method_23760().method_23761(), n4, n2, n3);
      begin.method_22918(matrixStack.method_23760().method_23761(), n4, n5, n6);
      begin.method_22918(matrixStack.method_23760().method_23761(), n4, n5, n3);
      begin.method_22918(matrixStack.method_23760().method_23761(), n4, n5, n3);
      begin.method_22918(matrixStack.method_23760().method_23761(), n4, n2, n3);
      begin.method_22918(matrixStack.method_23760().method_23761(), n, n5, n3);
      begin.method_22918(matrixStack.method_23760().method_23761(), n, n2, n3);
      begin.method_22918(matrixStack.method_23760().method_23761(), n, n2, n3);
      begin.method_22918(matrixStack.method_23760().method_23761(), n4, n2, n3);
      begin.method_22918(matrixStack.method_23760().method_23761(), n, n2, n6);
      begin.method_22918(matrixStack.method_23760().method_23761(), n4, n2, n6);
      begin.method_22918(matrixStack.method_23760().method_23761(), n4, n2, n6);
      begin.method_22918(matrixStack.method_23760().method_23761(), n, n5, n3);
      begin.method_22918(matrixStack.method_23760().method_23761(), n, n5, n3);
      begin.method_22918(matrixStack.method_23760().method_23761(), n, n5, n6);
      begin.method_22918(matrixStack.method_23760().method_23761(), n4, n5, n3);
      begin.method_22918(matrixStack.method_23760().method_23761(), n4, n5, n6);
      begin.method_22918(matrixStack.method_23760().method_23761(), n4, n5, n6);
      begin.method_22918(matrixStack.method_23760().method_23761(), n4, n5, n6);
      class_286.method_43433(begin.method_60800());
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.enableDepthTest();
      RenderSystem.disableBlend();
   }

   public static void renderLine(class_4587 matrixStack, Color color, class_243 vec3d, class_243 vec3d2) {
      Matrix4f positionMatrix = matrixStack.method_23760().method_23761();
      if (gm.enableMSAA.getValue()) {
         GL11.glEnable('茔' - 631);
         GL11.glEnable(2848);
         GL11.glHint(3307 ^ 185, 4354);
      }

      GL11.glDepthFunc(519);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.defaultBlendFunc();
      RenderSystem.enableBlend();
      RenderSystem.setShader(class_757::method_34540);
      float red = (float)color.getRed() / 255.0F;
      float green = (float)color.getGreen() / 255.0F;
      float blue = (float)color.getBlue() / 255.0F;
      float alpha = (float)color.getAlpha() / 255.0F;
      class_287 begin = class_289.method_1348().method_60827(class_5596.field_29344, class_290.field_1576);
      begin.method_22918(positionMatrix, (float)vec3d.field_1352, (float)vec3d.field_1351, (float)vec3d.field_1350).method_22915(red, green, blue, alpha);
      begin.method_22918(positionMatrix, (float)vec3d2.field_1352, (float)vec3d2.field_1351, (float)vec3d2.field_1350).method_22915(red, green, blue, alpha);
      class_286.method_43433(begin.method_60800());
      GL11.glDepthFunc(515);
      RenderSystem.disableBlend();
      if (gm.enableMSAA.getValue()) {
         GL11.glDisable(2848);
         GL11.glDisable(32925);
      }

   }

   public static void renderLine(class_4587 matrixStack, Color color, class_243 vec3d, class_243 vec3d2, float lineWidth) {
      Matrix4f positionMatrix = matrixStack.method_23760().method_23761();
      if (gm.enableMSAA.getValue()) {
         GL11.glEnable('胾' - 97);
         GL11.glEnable(2848);
         GL11.glHint(3250 ^ 224, 4354);
      }

      GL11.glLineWidth(lineWidth);
      GL11.glDepthFunc(519);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.defaultBlendFunc();
      RenderSystem.enableBlend();
      RenderSystem.setShader(class_757::method_34540);
      float red = (float)color.getRed() / 255.0F;
      float green = (float)color.getGreen() / 255.0F;
      float blue = (float)color.getBlue() / 255.0F;
      float alpha = (float)color.getAlpha() / 255.0F;
      class_287 begin = class_289.method_1348().method_60827(class_5596.field_29344, class_290.field_1576);
      begin.method_22918(positionMatrix, (float)vec3d.field_1352, (float)vec3d.field_1351, (float)vec3d.field_1350).method_22915(red, green, blue, alpha);
      begin.method_22918(positionMatrix, (float)vec3d2.field_1352, (float)vec3d2.field_1351, (float)vec3d2.field_1350).method_22915(red, green, blue, alpha);
      class_286.method_43433(begin.method_60800());
      GL11.glLineWidth(1.0F);
      GL11.glDepthFunc(515);
      RenderSystem.disableBlend();
      if (gm.enableMSAA.getValue()) {
         GL11.glDisable(2848);
         GL11.glDisable(32925);
      }

   }

   public static void drawItem(class_332 drawContext, class_1799 itemStack, int n, int n2, float n3, int n4) {
      if (!itemStack.method_7960()) {
         float n5 = n3 / 16.0F;
         class_4587 matrices = drawContext.method_51448();
         matrices.method_22903();
         matrices.method_46416((float)n, (float)n2, (float)n4);
         matrices.method_22905(n5, n5, 1.0F);
         drawContext.method_51427(itemStack, 0, 0);
         matrices.method_22909();
      }
   }

   private static void genericAABBRender(class_5596 mode, class_293 format, Supplier<class_5944> shader, Matrix4f stack, class_243 start, class_243 dimensions, Color color, j action) {
      float red = (float)color.getRed() / 255.0F;
      float green = (float)color.getGreen() / 255.0F;
      float blue = (float)color.getBlue() / 255.0F;
      float alpha = (float)color.getAlpha() / 255.0F;
      class_243 end = start.method_1019(dimensions);
      float x1 = (float)start.field_1352;
      float y1 = (float)start.field_1351;
      float z1 = (float)start.field_1350;
      float x2 = (float)end.field_1352;
      float y2 = (float)end.field_1351;
      float z2 = (float)end.field_1350;
      useBuffer(mode, format, shader, (bufferBuilder) -> {
         action.run(bufferBuilder, x1, y1, z1, x2, y2, z2, red, green, blue, alpha, stack);
      });
   }

   private static void useBuffer(class_5596 vertexFormat$DrawMode, class_293 vertexFormat, Supplier<class_5944> shader, Consumer<class_287> consumer) {
      class_287 begin = class_289.method_1348().method_60827(vertexFormat$DrawMode, vertexFormat);
      consumer.accept(begin);
      setup();
      RenderSystem.setShader(shader);
      class_286.method_43433(begin.method_60800());
      cleanup();
   }

   // $FF: synthetic method
   private static String d186(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 9350 + var3 & (74 ^ 181));
      }

      return new String(var2);
   }
}
