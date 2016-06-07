/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Operators;

/**
 *
 * @author saroshmadara
 */
public class Arithmatic_Ops {
    public static class ADDSUB{
        public static String check(char c) {
            switch(c){
                case '+':
                    return "ADDSUB";
                case '-':
                    return "ADDSUB";
                default:
                    return null;
            }
        }
    }
    public static class MULDIV{
        public static String check(char c) {
            switch(c){
                case '*':
                    return "MULDIV";
                case '/':
                    return "MULDIV";
                default:
                    return null;
            }
        }
    }
    
}
