import java.awt.Color;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class gt extends ab {
   private final Map<String, Color> storageColors = new HashMap();
   private Runnable onChange;

   public gt(db name) {
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

   public Map<String, Color> getColors() {
      return this.storageColors;
   }

   public void setColor(String storageType, Color color) {
      this.storageColors.put(storageType, color);
      this.notifyChange();
   }

   public Color getColor(String storageType) {
      return (Color)this.storageColors.get(storageType);
   }

   public boolean hasColor(String storageType) {
      return this.storageColors.containsKey(storageType);
   }

   public void clear() {
      this.storageColors.clear();
      this.notifyChange();
   }

   public int getCount() {
      return this.storageColors.size();
   }

   public String serialize() {
      StringBuilder sb = new StringBuilder();
      Iterator var2 = this.storageColors.entrySet().iterator();

      while(var2.hasNext()) {
         Entry<String, Color> entry = (Entry)var2.next();
         if (sb.length() > 0) {
            sb.append(";");
         }

         Color color = (Color)entry.getValue();
         sb.append((String)entry.getKey()).append(":").append(color.getRed()).append(",").append(color.getGreen()).append(",").append(color.getBlue());
      }

      return sb.toString();
   }

   public void deserialize(String data) {
      if (data != null && !data.isEmpty()) {
         this.storageColors.clear();
         String[] entries = data.split(";");
         String[] var3 = entries;
         int var4 = entries.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String entry = var3[var5];

            try {
               String[] parts = entry.split(":");
               if (parts.length == 2) {
                  String type = parts[0].trim();
                  String[] rgb = parts[1].split(",");
                  if (rgb.length == 3) {
                     int r = Integer.parseInt(rgb[0].trim());
                     int g = Integer.parseInt(rgb[1].trim());
                     int b = Integer.parseInt(rgb[2].trim());
                     this.storageColors.put(type, new Color(r, g, b));
                  }
               }
            } catch (Exception var13) {
               System.err.println("Failed to load storage color: " + entry);
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
   private static String d830(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 8263 - 252 + var3 & 255);
      }

      return new String(var2);
   }
}
