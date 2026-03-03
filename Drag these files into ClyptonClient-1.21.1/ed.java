import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class ed {
   public String title;
   public String description;
   public String url;
   public Color color;
   public an footer;
   public bq thumbnail;
   public eb image;
   public bm author;
   public final List<fw> fields = new ArrayList();

   public ed setDescription(String b) {
      this.description = b;
      return this;
   }

   public ed setColor(Color d) {
      this.color = d;
      return this;
   }

   public ed setTitle(String a) {
      this.title = a;
      return this;
   }

   public ed setUrl(String c) {
      this.url = c;
      return this;
   }

   public ed setFooter(String e, String f) {
      this.footer = new an(e, f);
      return this;
   }

   public ed setImage(eb f) {
      this.image = f;
      return this;
   }

   public ed setThumbnail(String g) {
      this.thumbnail = new bq(g);
      return this;
   }

   public ed setAuthor(bm h) {
      this.author = h;
      return this;
   }

   public ed addField(String a, String b, boolean c) {
      this.fields.add(new fw(a, b, c));
      return this;
   }
}
