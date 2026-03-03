import java.util.ArrayList;
import java.util.Base64;
import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_1297.class_5529;

public final class ga extends bf {
   public ga() {
      super(db.of(d195("Ez0gPHYDKjgq")), db.of(d195("HzwwIDoyeC0yOih9NjoMERFDHQoTRw0aCQocCE4/Hx0bABxVAgUZCQk=")), -1, hk.DONUT);
      this.addSettings(new ab[0]);
   }

   public void onEnable() {
      this.removeTrapEntities();
      super.onEnable();
   }

   public void onDisable() {
      super.onDisable();
   }

   @cp
   public void onEntitySpawn(es entitySpawnEvent) {
      if (this.isTrapEntity(entitySpawnEvent.packet.method_11169())) {
         entitySpawnEvent.cancel();
      }

   }

   private void removeTrapEntities() {
      if (mc.field_1687 != null) {
         ArrayList<class_1297> trapEntities = new ArrayList();
         mc.field_1687.method_18112().forEach((entity) -> {
            if (entity != null && this.isTrapEntity(entity.method_5864())) {
               trapEntities.add(entity);
            }

         });
         trapEntities.forEach((trapEntity) -> {
            if (!trapEntity.method_31481()) {
               trapEntity.method_5650(class_5529.field_26999);
            }

         });
      }
   }

   private boolean isTrapEntity(class_1299<?> entityType) {
      return entityType != null && (entityType.equals(class_1299.field_6131) || entityType.equals(class_1299.field_6126));
   }

   // $FF: synthetic method
   private static String d195(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 2898 + var3 & 255);
      }

      return new String(var2);
   }
}
