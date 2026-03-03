import java.util.Base64;
import java.util.Objects;
import java.util.stream.Stream;
import net.minecraft.class_1268;
import net.minecraft.class_1269;
import net.minecraft.class_1923;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2818;
import net.minecraft.class_310;
import net.minecraft.class_3965;
import net.minecraft.class_4969;

public final class ft {
   public static Stream<class_2818> getLoadedChunks() {
      int radius = Math.max(2, class_310.method_1551().field_1690.method_38521()) + 3;
      int diameter = radius * 2 + 1;
      class_1923 center = class_310.method_1551().field_1724.method_31476();
      class_1923 min = new class_1923(center.field_9181 - radius, center.field_9180 - radius);
      class_1923 max = new class_1923(center.field_9181 + radius, center.field_9180 + radius);
      return Stream.iterate(min, (pos) -> {
         int x = pos.field_9181;
         int z = pos.field_9180;
         ++x;
         if (x > max.field_9181) {
            x = min.field_9181;
            ++z;
         }

         if (z > max.field_9180) {
            throw new IllegalStateException(d151("oYeGkJea2JWTlpWJ3ptpZWwkcCVxaHpiJA=="));
         } else {
            return new class_1923(x, z);
         }
      }).limit((long)diameter * (long)diameter).filter((c) -> {
         return class_310.method_1551().field_1687.method_8393(c.field_9181, c.field_9180);
      }).map((c) -> {
         return class_310.method_1551().field_1687.method_8497(c.field_9181, c.field_9180);
      }).filter(Objects::nonNull);
   }

   public static boolean isBlockAtPosition(class_2338 blockPos, class_2248 block) {
      return class_310.method_1551().field_1687.method_8320(blockPos).method_26204() == block;
   }

   public static boolean isRespawnAnchorCharged(class_2338 blockPos) {
      return isBlockAtPosition(blockPos, class_2246.field_23152) && (Integer)class_310.method_1551().field_1687.method_8320(blockPos).method_11654(class_4969.field_23153) != 0;
   }

   public static boolean isRespawnAnchorUncharged(class_2338 blockPos) {
      return isBlockAtPosition(blockPos, class_2246.field_23152) && (Integer)class_310.method_1551().field_1687.method_8320(blockPos).method_11654(class_4969.field_23153) == 0;
   }

   public static void interactWithBlock(class_3965 blockHitResult, boolean shouldSwingHand) {
      class_310 mc = class_310.method_1551();
      class_1269 result = mc.field_1761.method_2896(mc.field_1724, class_1268.field_5808, blockHitResult);
      if (result.method_23665() && result.method_23666() && shouldSwingHand) {
         mc.field_1724.method_6104(class_1268.field_5808);
      }

   }

   public static boolean debrisIsExposed(class_2338 blockPos) {
      for(int x = -1; x <= 1; ++x) {
         for(int y = -1; y <= 1; ++y) {
            for(int z = -1; z <= 1; ++z) {
               if (x != 0 || y != 0 || z != 0) {
                  class_2338 checkPos = blockPos.method_10069(x, y, z);
                  class_2248 block = class_310.method_1551().field_1687.method_8320(checkPos).method_26204();
                  if (block == class_2246.field_10124 || block == class_2246.field_10164) {
                     return true;
                  }
               }
            }
         }
      }

      return false;
   }

   // $FF: synthetic method
   private static String d151(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 10226 + var3 & 255);
      }

      return new String(var2);
   }
}
