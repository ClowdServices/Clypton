package client.astralux.mixin;

import net.minecraft.class_6122;
import net.minecraft.class_6795;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_6795.class})
public interface HeightRangePlacementModifierAccessor {
   @Accessor
   class_6122 getHeight();
}
