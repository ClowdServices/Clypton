import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public final class cu<T extends Enum<T>> extends ab {
   public int index;
   private final List<T> possibleValues;
   private final int originalValue;

   public cu(CharSequence charSequence, T defaultValue, Class<T> type) {
      super(charSequence);
      if (type == null) {
         throw new IllegalArgumentException("ModeSetting type cannot be null for: " + String.valueOf(charSequence));
      } else {
         T[] enumConstants = (Enum[])type.getEnumConstants();
         if (enumConstants != null && enumConstants.length != 0) {
            this.possibleValues = Arrays.asList(enumConstants);
            this.index = this.possibleValues.indexOf(defaultValue);
            this.originalValue = this.index;
         } else {
            String var10002 = String.valueOf(charSequence);
            throw new IllegalArgumentException("ModeSetting type must be an enum with values for: " + var10002 + " (got: " + type.getName() + ")");
         }
      }
   }

   public T getValue() {
      return (Enum)this.possibleValues.get(this.index);
   }

   public void setValue(T value) {
      this.index = this.possibleValues.indexOf(value);
   }

   public void setMode(Enum<T> enum1) {
      this.index = this.possibleValues.indexOf(enum1);
   }

   public void setModeIndex(int a) {
      this.index = a;
   }

   public int getModeIndex() {
      return this.index;
   }

   public int getOriginalValue() {
      return this.originalValue;
   }

   public void cycleUp() {
      if (this.index < this.possibleValues.size() - 1) {
         ++this.index;
      } else {
         this.index = 0;
      }

   }

   public void cycleDown() {
      if (this.index > 0) {
         --this.index;
      } else {
         this.index = this.possibleValues.size() - 1;
      }

   }

   public boolean isMode(Enum<T> enum1) {
      return this.index == this.possibleValues.indexOf(enum1);
   }

   public List<T> getPossibleValues() {
      return this.possibleValues;
   }

   public cu<T> setDescription(CharSequence charSequence) {
      super.setDescription(charSequence);
      return this;
   }

   // $FF: synthetic method
   private static String d213(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 6129 + var3 & 255);
      }

      return new String(var2);
   }
}
