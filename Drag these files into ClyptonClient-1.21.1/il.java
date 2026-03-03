import java.util.Base64;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2960;
import net.minecraft.class_7923;

public class il extends ab {
   private final Set<class_2248> selectedBlocks = new HashSet();
   private Runnable onChange;

   public il(CharSequence name) {
      super(name);
   }

   public void setOnChange(Runnable callback) {
      this.onChange = callback;
   }

   private void notifyChange() {
      if (this.onChange != null) {
         this.onChange.run();
      }

   }

   public Set<class_2248> getBlocks() {
      return this.selectedBlocks;
   }

   public void addBlock(class_2248 block) {
      this.selectedBlocks.add(block);
      this.notifyChange();
   }

   public void removeBlock(class_2248 block) {
      this.selectedBlocks.remove(block);
      this.notifyChange();
   }

   public boolean hasBlock(class_2248 block) {
      return this.selectedBlocks.contains(block);
   }

   public void toggleBlock(class_2248 block) {
      if (this.selectedBlocks.contains(block)) {
         this.selectedBlocks.remove(block);
      } else {
         this.selectedBlocks.add(block);
      }

      this.notifyChange();
   }

   public void clear() {
      this.selectedBlocks.clear();
      this.notifyChange();
   }

   public int getCount() {
      return this.selectedBlocks.size();
   }

   public String serialize() {
      return (String)this.selectedBlocks.stream().map((block) -> {
         return class_7923.field_41175.method_10221(block).toString();
      }).collect(Collectors.joining(","));
   }

   public void deserialize(String data) {
      if (data != null && !data.isEmpty()) {
         this.selectedBlocks.clear();
         String[] blockIds = data.split(",");
         String[] var3 = blockIds;
         int var4 = blockIds.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String blockId = var3[var5];

            try {
               class_2960 id = class_2960.method_60654(blockId.trim());
               class_2248 block = (class_2248)class_7923.field_41175.method_10223(id);
               if (block != null && block != class_2246.field_10124) {
                  this.selectedBlocks.add(block);
               }
            } catch (Exception var9) {
               System.err.println("Failed to load block: " + blockId);
            }
         }

         this.notifyChange();
      }
   }

   public String getValueAsString() {
      return this.serialize();
   }

   public void setValueFromString(String value) {
      if (value != null) {
         this.deserialize(value);
      }

   }

   // $FF: synthetic method
   private static String d550(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 2912 + var3 & (105 ^ 150));
      }

      return new String(var2);
   }
}
