/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Aron
 */
public class TextUtils {
    
    /* 
     * returns titlecase string based on given string
     * params: string to convert
     * returns: titlecase converted string
     */
    public static String titleCase(String s)
    {
        if (s == null || s.isEmpty())
        {
            return s;
        }

        StringBuilder out = new StringBuilder();

        boolean convertNext = true;
        for (char ch : s.toCharArray()) {
            if (Character.isSpaceChar(ch)) {
                convertNext = true;
            } else if (convertNext) {
                ch = Character.toTitleCase(ch);
                convertNext = false;
            } else {
                ch = Character.toLowerCase(ch);
            }
            out.append(ch);
        }

        return out.toString();
    }
    
    /* 
     * converts given string to be compatible as swing gui text
     * params: string to convert
     * returns: converted string
     */
    public static String swingText(String input)
    {
        return "<html>" + input.replaceAll("\n", "<br>") + "</html>";
    }
}
