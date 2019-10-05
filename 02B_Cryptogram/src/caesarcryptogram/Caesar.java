package caesarcryptogram;

/**
 * Caesar titkosítás
 * @author Tóth József
 */
public class Caesar {
    /**
     * String kódolása Caesar kódolással
     * @param s: átalakítandó string
     * @param tol: ennyivel kell eltolni a karaktereket (-25...+25)
     * @return kódolt string
     */
    public static String kodol(String s, int tol) {
        s = s.toUpperCase();
        StringBuilder uj = new StringBuilder(1024);
        int hossz = 'Z' - 'A' + 1;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case 'Á': c = 'A'; break;
                case 'É': c = 'E'; break;
                case 'Í': c = 'I'; break;
                case 'Ó':
                case 'Ö':
                case 'Ő': c = 'O'; break;
                case 'Ú':
                case 'Ü':
                case 'Ű': c = 'U'; break;
            }
            if (c < 'A' || c > 'Z')
                continue;
            int kod = c + tol;
            if (kod < 'A') 
                kod += hossz;
            else if (kod > 'Z') 
                kod -= hossz;
            uj.append((char)kod);
        }
        return uj.toString();
    }
    
}
