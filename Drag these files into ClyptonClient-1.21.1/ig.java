import net.minecraft.class_2382;
import org.joml.Vector3d;

public interface ig {
   void set(double var1, double var3, double var5);

   default void a(class_2382 vec3i) {
      this.set((double)vec3i.method_10263(), (double)vec3i.method_10264(), (double)vec3i.method_10260());
   }

   default void a(Vector3d vector3d) {
      this.set(vector3d.x, vector3d.y, vector3d.z);
   }

   void setXZ(double var1, double var3);

   void setY(double var1);
}
