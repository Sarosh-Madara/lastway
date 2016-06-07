/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modal;

import java.util.Arrays;

/**
 *
 * @author saroshmadara
 */
public class Token {
     public int ln;
    public String classpart;
    public char[] valuePart;

    public int getLn() {
        return ln;
    }

    public void setLn(int ln) {
        this.ln = ln;
    }

    public String getClasspart() {
        return classpart;
    }

    public void setClasspart(String classpart) {
        this.classpart = classpart;
    }

    public char[] getValuePart() {
        return valuePart;
    }

    public void setValuePart(char[] valuePart) {
        this.valuePart = valuePart;
    }

    public Token(int ln, String classpart, char[] valuePart) {
        this.ln = ln;
        this.classpart = classpart;
        this.valuePart = valuePart;
    }

    @Override
    public String toString() {
        return "( " + classpart+ " , " + Arrays.toString(valuePart) + " , " + ln + " )"; //To change body of generated methods, choose Tools | Templates.
    }
    
}

