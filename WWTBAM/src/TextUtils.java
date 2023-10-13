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
     * validates if user input is 1 character only, and valid available input
     * params: String of user input, String of valid characters
     * returns: boolean if input valid
     */
    public static boolean validateChoice(String input, String validInputs)
    {
        char[] chars = validInputs.toUpperCase().toCharArray();
        
        if (input.length() == 1)
        {
            for (char c : chars)
            {
                if (c == input.toUpperCase().charAt(0))
                {
                    return true;
                }
            }
        }
        
        return false;
    }
}
