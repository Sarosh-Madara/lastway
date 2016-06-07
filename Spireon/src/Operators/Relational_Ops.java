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
public class Relational_Ops {
    public static String checkSingleOp(char c){
        switch(c){
                case '<':
                    return "SM_TH";
                case '>':
                    return "GR_TH";
                default:
                    return null;
            }
    }
    
    public static String checkDoubleOp(String str){
        switch(str){
            case "[<, =]":
                return "SM_TH_E_T";
            case "[=, =]":
                return "COMPARES";
            case "[>, =]":
                return "GR_TH_E_T";
            case "[!, =]":
                return "NOT_E_T";
            case "[+, =]":
                return "ASSIGN_EQUAL";
            case "[-, =]":
                return "ASSIGN_EQUAL";
            case "[*, =]":
                return "ASSIGN_EQUAL";
            case "[/, =]":
                return "ASSIGN_EQUAL";
            default:
                return null;
        }
    }
    public static void main(String args[]){
        System.out.println("---a".matches("---.+"));
    }

}
