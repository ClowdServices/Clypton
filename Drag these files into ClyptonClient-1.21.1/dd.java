import client.astralux.Astralux;
import java.util.Base64;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.glfw.GLFW;

public final class dd {
   public static CharSequence getKey(int key) {
      switch(key) {
      case -1:
         return db.of(d81("sIiMhoadhQ=="));
      case 0:
         return db.of(d81("qaul"));
      case 1:
         return db.of(d81("t6ul"));
      case 2:
         return db.of(d81("qKul"));
      case 32:
         return db.of(d81("tpaGi4w="));
      case 39:
         return db.of(d81("pJaIm52YhJyFiw=="));
      case 44:
         return db.of(d81("pomKhYg="));
      case 59:
         return db.of(d81("toOKgYqFh4OD"));
      case 61:
         return db.of(d81("oJeSiYWZ"));
      case 91:
         return db.of(d81("qYOBnMmomY2OhYqE"));
      case 92:
         return db.of(d81("p4eEg5qGip+F"));
      case 93:
         return db.of(d81("t4+AgJ3KqZ6MjYSVhQ=="));
      case 96:
         return db.of(d81("opSGnozKqo+Oi4GE"));
      case 161:
         return db.of(d81("somVhI3K2g=="));
      case 162:
         return db.of(d81("somVhI3K2Q=="));
      case 256:
         return db.of(d81("oJWE"));
      case 257:
         return db.of(d81("oIiTjZs="));
      case 258:
         return db.of(d81("sYeF"));
      case 259:
         return db.of(d81("p4eEg5qaio+I"));
      case 260:
         return db.of(d81("rIiUjZue"));
      case 261:
         return db.of(d81("oYOLjZ2P"));
      case 262:
         return db.of(d81("pJSVh57KuYWKhps="));
      case 263:
         return db.of(d81("pJSVh57Kp4mLmg=="));
      case 264:
         return db.of(d81("pJSVh57Kr4OagA=="));
      case 265:
         return db.of(d81("pJSVh57Kvpw="));
      case 266:
         return db.of(d81("tYeAjcm/mw=="));
      case 267:
         return db.of(d81("tYeAjcmuhJuD"));
      case 268:
         return db.of(d81("rYmKjQ=="));
      case 269:
         return db.of(d81("oIiD"));
      case 280:
         return db.of(d81("poeXm8mmhI+G"));
      case 281:
         return db.of(d81("toWVh4WGy6CCjYQ="));
      case 282:
         return db.of(d81("q5OKyKWFiIc="));
      case 283:
         return db.of(d81("tZSOhp3KuI+fi4qe"));
      case 284:
         return db.of(d81("tYeSm4w="));
      case 290:
         return db.of("F1");
      case 291:
         return db.of("F2");
      case 292:
         return db.of("F3");
      case 293:
         return db.of("F4");
      case 294:
         return db.of("F5");
      case 295:
         return db.of("F6");
      case 296:
         return db.of("F7");
      case 297:
         return db.of("F8");
      case 298:
         return db.of("F9");
      case 299:
         return db.of(d81("o9fX"));
      case 300:
         return db.of(d81("o9fW"));
      case 301:
         return db.of(d81("o9fV"));
      case 302:
         return db.of(d81("o9fU"));
      case 303:
         return db.of(d81("o9fT"));
      case 304:
         return db.of(d81("o9fS"));
      case 305:
         return db.of(d81("o9fR"));
      case 306:
         return db.of(d81("o9fQ"));
      case 307:
         return db.of(d81("o9ff"));
      case 308:
         return db.of(d81("o9fe"));
      case 309:
         return db.of(d81("o9TX"));
      case 310:
         return db.of(d81("o9TW"));
      case 311:
         return db.of(d81("o9TV"));
      case 312:
         return db.of(d81("o9TU"));
      case 313:
         return db.of(d81("o9TT"));
      case 314:
         return db.of(d81("o9TS"));
      case 335:
         return db.of(d81("q5OKmIiOy6mDmoqC"));
      case 340:
         return db.of(d81("qYOBnMm5g4WLmg=="));
      case 341:
         return db.of(d81("qYOBnMmphIKZnICc"));
      case 342:
         return db.of(d81("qYOBnMmrh5g="));
      case 343:
         return db.of(d81("qYOBnMm5npyInA=="));
      case 344:
         return db.of(d81("t4+AgJ3KuISEiJs="));
      case 345:
         return db.of(d81("t4+AgJ3KqIODmp2fnQ=="));
      case 346:
         return db.of(d81("t4+AgJ3KqoCZ"));
      case 347:
         return db.of(d81("t4+AgJ3KuJmdi50="));
      case 348:
         return db.of(d81("qIOJnQ=="));
      default:
         String keyName = GLFW.glfwGetKeyName(key, 0);
         return (CharSequence)(keyName == null ? db.of(d81("q4mJjQ==")) : StringUtils.capitalize(keyName));
      }
   }

   public static boolean isKeyPressed(int n) {
      if (n <= (158 ^ 150)) {
         return GLFW.glfwGetMouseButton(Astralux.mc.method_22683().method_4490(), n) == 1;
      } else {
         return GLFW.glfwGetKey(Astralux.mc.method_22683().method_4490(), n) == 1;
      }
   }

   // $FF: synthetic method
   private static String d81(String var0) {
      byte[] var1 = Base64.getDecoder().decode(var0);
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = (byte)(var1[var3] ^ 5038 - 201 + var3 & 255);
      }

      return new String(var2);
   }
}
