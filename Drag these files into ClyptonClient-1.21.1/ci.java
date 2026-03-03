import java.awt.Color;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.class_1297;
import net.minecraft.class_1604;
import net.minecraft.class_1923;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_243;
import net.minecraft.class_2818;
import net.minecraft.class_3532;
import net.minecraft.class_3986;
import net.minecraft.class_3989;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_7833;

public final class ci extends bf {
   private final gn alpha = new gn(db.of(d116("JwsYAQs=")), 1.0D, 255.0D, 120.0D, 1.0D);
   private final ff tracers = new ff(db.of(d116("MhUJCg8ZHw==")), true);
   private final ff vanderingGuy = new ff(db.of(d116("MAYGDQ8ZBQMJTzcECw==")), true);
   private final ff lama = new ff(db.of(d116("KgYFCA==")), true);
   private final ff pilliger = new ff(db.of(d116("Ng4EBQMMCR8=")), true);
   private final ff deepslateBlocks = new ff(db.of(d116("IgINGRkHDRkL")), true);
   private final ff highKelp = new ff(db.of(d116("Lg4PAUogCQEeTzMZBx0fBg==")), true);
   private final gn kelpPercentage = new gn(db.of(d116("LQIEGUo7CR8NCh4FExQR")), 50.0D, 100.0D, 75.0D, 5.0D);
   private final Color TRADER_COLOR = new Color(255, 255, 0);
   private final Color LLAMA_COLOR = new Color(200, 200, 0);
   private final Color PILLAGER_COLOR = new Color(64 ^ 191, 0, 0);
   private final Color SUS_BLOCK_COLOR = new Color(255, 0, 255);
   private final Color KELP_CHUNK_COLOR = new Color(0, 100, 0);
   private final AtomicReference<List<class_2338>> cachedSusBlocks = new AtomicReference(Collections.emptyList());
   private final AtomicReference<Set<class_1923>> cachedKelpChunks = new AtomicReference(Collections.emptySet());
   private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
   private ScheduledFuture<?> scanTask;

   public ci() {
      super(db.of(d116("NRIbLDk7")), db.of(d116("Lg4PAQYCCwUaHFACBwBUFBUDEQ8TDxUYDQ==")), -1, hk.DONUT);
      ab[] var10001 = new ab[]{this.alpha, this.tracers, this.vanderingGuy, this.lama, this.pilliger, this.deepslateBlocks, this.highKelp, null};
      var10001[108 ^ 107] = this.kelpPercentage;
      this.addSettings(var10001);
   }

   public void onEnable() {
      this.scanTask = this.executor.scheduleAtFixedRate(this::scanForSus, 0L, 400L, TimeUnit.MILLISECONDS);
   }

   public void onDisable() {
      if (this.scanTask != null) {
         this.scanTask.cancel(true);
      }

      this.cachedSusBlocks.set(Collections.emptyList());
      this.cachedKelpChunks.set(Collections.emptySet());
   }

   private void scanForSus() {
      if (mc.field_1687 != null && mc.field_1724 != null) {
         if (this.deepslateBlocks.getValue()) {
            this.scanForSusBlocks();
         }

         if (this.highKelp.getValue()) {
            this.scanForHighKelpChunks();
         }

      }
   }

   private void scanForSusBlocks() {
      List<class_2338> found = new ArrayList();
      Iterator var2 = ft.getLoadedChunks().toList().iterator();

      while(var2.hasNext()) {
         class_2818 chunk = (class_2818)var2.next();

         for(int x = 0; x < 16; ++x) {
            for(int z = 0; z < 16; ++z) {
               for(int y = 154 ^ 139; y < mc.field_1687.method_31600(); ++y) {
                  class_2338 pos = new class_2338((chunk.method_12004().field_9181 << 4) + x, y, (chunk.method_12004().field_9180 << 4) + z);
                  class_2248 block = chunk.method_8320(pos).method_26204();
                  if (block == class_2246.field_28888 || block == class_2246.field_29031 || block == class_2246.field_29029 || block == class_2246.field_29026) {
                     found.add(pos);
                  }
               }
            }
         }
      }

      this.cachedSusBlocks.set(found);
   }

   private void scanForHighKelpChunks() {
      Set<class_1923> susChunks = new HashSet();
      List<class_2818> chunks = ft.getLoadedChunks().toList();
      double requiredPercentage = this.kelpPercentage.getValue() / 100.0D;
      Iterator var5 = chunks.iterator();

      while(var5.hasNext()) {
         class_2818 chunk = (class_2818)var5.next();
         class_1923 cpos = chunk.method_12004();
         int xStart = cpos.method_8326();
         int zStart = cpos.method_8328();
         int kelpColumns = 0;
         int fullGrownKelp = 0;

         for(int x = xStart; x < xStart + 16; ++x) {
            for(int z = zStart; z < zStart + 16; ++z) {
               int kelpTop = -1;
               int kelpBottom = -1;

               for(int y = mc.field_1687.method_8615(); y >= mc.field_1687.method_31607(); --y) {
                  class_2248 block = chunk.method_8320(new class_2338(x, y, z)).method_26204();
                  if (block != class_2246.field_9993 && block != class_2246.field_10463) {
                     if (kelpTop != -1) {
                        break;
                     }
                  } else {
                     if (kelpTop == -1) {
                        kelpTop = y;
                     }

                     kelpBottom = y;
                  }
               }

               if (kelpTop != -1 && kelpBottom != -1 && kelpTop - kelpBottom + 1 >= 8) {
                  ++kelpColumns;
                  if (kelpTop == 62) {
                     ++fullGrownKelp;
                  }
               }
            }
         }

         if (kelpColumns >= (177 ^ 187)) {
            double percentage = (double)fullGrownKelp / (double)kelpColumns;
            if (percentage >= requiredPercentage) {
               susChunks.add(cpos);
            }
         }
      }

      this.cachedKelpChunks.set(susChunks);
   }

   @cp
   public void onRender3D(bg event) {
      if (mc.field_1687 != null && mc.field_1724 != null) {
         class_4184 camera = iq.getCamera();
         if (camera != null) {
            class_4587 matrices = event.matrixStack;
            class_243 camPos = iq.getCameraPos();
            matrices.method_22903();
            matrices.method_22907(class_7833.field_40714.rotationDegrees(camera.method_19329()));
            matrices.method_22907(class_7833.field_40716.rotationDegrees(camera.method_19330() + 180.0F));
            matrices.method_22904(-camPos.field_1352, -camPos.field_1351, -camPos.field_1350);
            Iterator var5 = mc.field_1687.method_18112().iterator();

            while(true) {
               while(var5.hasNext()) {
                  class_1297 entity = (class_1297)var5.next();
                  if (entity instanceof class_3989 && this.vanderingGuy.getValue()) {
                     this.renderTarget(matrices, entity, this.TRADER_COLOR);
                  } else if (entity instanceof class_3986 && this.lama.getValue()) {
                     this.renderTarget(matrices, entity, this.LLAMA_COLOR);
                  } else if (entity instanceof class_1604 && this.pilliger.getValue()) {
                     this.renderTarget(matrices, entity, this.PILLAGER_COLOR);
                  }
               }

               Color kelpCol;
               Iterator var14;
               if (this.deepslateBlocks.getValue()) {
                  kelpCol = new Color(this.SUS_BLOCK_COLOR.getRed(), this.SUS_BLOCK_COLOR.getGreen(), this.SUS_BLOCK_COLOR.getBlue(), this.alpha.getIntValue());
                  var14 = ((List)this.cachedSusBlocks.get()).iterator();

                  while(var14.hasNext()) {
                     class_2338 pos = (class_2338)var14.next();
                     iq.renderFilledBox(matrices, (float)pos.method_10263() + 0.05F, (float)pos.method_10264() + 0.05F, (float)pos.method_10260() + 0.05F, (float)pos.method_10263() + 0.95F, (float)pos.method_10264() + 0.95F, (float)pos.method_10260() + 0.95F, kelpCol);
                     if (this.tracers.getValue()) {
                        class_243 crosshairPos = mc.field_1765 != null ? mc.field_1765.method_17784() : mc.field_1724.method_5828(1.0F).method_1021(4.0D).method_1019(mc.field_1724.method_19538());
                        iq.renderLine(matrices, this.SUS_BLOCK_COLOR, crosshairPos, pos.method_46558());
                     }
                  }
               }

               if (this.highKelp.getValue()) {
                  kelpCol = new Color(this.KELP_CHUNK_COLOR.getRed(), this.KELP_CHUNK_COLOR.getGreen(), this.KELP_CHUNK_COLOR.getBlue(), this.alpha.getIntValue());
                  var14 = ((Set)this.cachedKelpChunks.get()).iterator();

                  while(var14.hasNext()) {
                     class_1923 chunkPos = (class_1923)var14.next();
                     int startX = chunkPos.field_9181 << 4;
                     int startZ = chunkPos.field_9180 << 4;
                     int y = 55;
                     iq.renderFilledBox(matrices, (float)startX, (float)y, (float)startZ, (float)(startX + 16), (float)(y + 1), (float)(startZ + 16), kelpCol);
                     if (this.tracers.getValue()) {
                        class_243 crosshairPos = mc.field_1765 != null ? mc.field_1765.method_17784() : mc.field_1724.method_5828(1.0F).method_1021(4.0D).method_1019(mc.field_1724.method_19538());
                        class_243 chunkCenter = new class_243((double)(startX + (209 ^ 217)), (double)(mc.field_1687.method_8615() - 10), (double)(startZ + (87 ^ 95)));
                        iq.renderLine(matrices, this.KELP_CHUNK_COLOR, crosshairPos, chunkCenter);
                     }
                  }
               }

               matrices.method_22909();
               return;
            }
         }
      }
   }

   private void renderTarget(class_4587 matrices, class_1297 entity, Color color) {
      float delta = mc.method_60646().method_60637(true);
      double x = class_3532.method_16436((double)delta, entity.field_6014, entity.method_23317());
      double y = class_3532.method_16436((double)delta, entity.field_6036, entity.method_23318());
      double z = class_3532.method_16436((double)delta, entity.field_5969, entity.method_23321());
      float w = entity.method_17681() / 2.0F;
      float h = entity.method_17682();
      Color drawCol = new Color(color.getRed(), color.getGreen(), color.getBlue(), this.alpha.getIntValue());
      iq.renderFilledBox(matrices, (float)x - w, (float)y, (float)z - w, (float)x + w, (float)y + h, (float)z + w, drawCol);
      if (this.tracers.getValue()) {
         class_243 crosshairPos = mc.field_1765 != null ? mc.field_1765.method_17784() : mc.field_1724.method_5828(1.0F).method_1021(4.0D).method_1019(mc.field_1724.method_19538());
         iq.renderLine(matrices, color, crosshairPos, entity.method_30950(delta));
      }

   }

   // $FF: synthetic method
   private static String d116(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 10874 - 532 + var3 & (102 ^ 153));
      }

      return new String(var2);
   }
}
