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
public class Logical_Ops {
    public static String checkLogical(char c){
        switch(c){
            case '!':
                return "NOT";
            case '&':
                return "AND";
            case '|':
                return "OR";
            default:
                return null;
        }
    }
    public static String checkConditional(String s){
        switch(s){
            case "[&, &]":
                return "CONDITIONAL_AND";
            case "[|, |]":
                return "CONDITIONAL_OR";
            default:
                return null;
        }
    }
    
}
