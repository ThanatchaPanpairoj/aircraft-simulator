
/**
 * Write a description of class Translator here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Translator
{
    public static void translate(String s) {
        while(s.indexOf('/') != -1)
            s = s.substring(0, s.indexOf('/')) + s.substring(s.indexOf(' ', s.indexOf('/')));
        System.out.println(s);
    }
}
