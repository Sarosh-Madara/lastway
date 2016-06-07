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
public class INC_DEC_Ops {
    public static String check(String str){
        switch(str){
            case "[+, +]":
            case "[-, -]":
                return "INC_DEC";
            default:
                return null;
        }
    }
    public static void main(String a[]){
        
//        System.out.println("elvis".matches("^\\belvis\\b$"));
//        System.out.println("elvis alive".matches("^\\belvis\\b.*\\balive\\b$"));
//        System.out.println("021-3421".matches("\\b\\d\\d\\d-\\d\\d\\d\\d"));
//        System.out.println("+923442306572".matches("^\\+92[0-9]{10}$"));
//        System.out.println("923411747977".matches("^\\+92[0-9]{10}|92[0-9]{10}|\\b\\d\\d\\d\\d\\b\\-\\d\\d\\d\\d\\d\\d\\d$"));
//        System.out.println("0341-1747977".matches("\\b\\d{4}-{0,1}\\d{7}"));
//        System.out.println("apple".matches("\\bab\\w*\\b"));
//        System.out.println("".matches("\\d*"));
//        System.out.println("letter".matches("\\b\\w{6}\\b"));
//        System.out.println("what".matches("\\b\\w{4}\\b"));
//        System.out.println("app11kasfhkadsfhdsjafhkjadshfjkadshkjfhadskfhhasdjkfjkdshakjfhsadjk".matches("\\b\\w{3,}\\b"));
//        System.out.println("".matches(""));


        // String to be scanned to find the pattern.
//        String line = "This A300 order was placed for QT3000! OK?";
//        String pattern = "(.*)(\\d+)(.*)";
//
//        // Create a Pattern object
//        Pattern r = Pattern.compile(pattern);
//
//        // Now create matcher object.
//        Matcher m = r.matcher(line);
//        if (m.find( )) {
//           System.out.println("Found value: " + m.group(0) );
//           System.out.println("Found value: " + m.group(1) );
//           System.out.println("Found value: " + m.group(2) );
//        } else {
//           System.out.println("NO MATCH");
//        }
//            System.out.println("1m\t\t\t\t\t".matches(".+\\d.*"));
//        System.out.println("[a]".matches("[...]"));
//        System.out.println("021-1234".matches("\\d\\d\\d-?\\d\\d\\d\\d"));
//        System.out.println("'\n'".matches("\\'(\\s|[A-Za-z0-9])\\'"));
        System.out.println("25".matches("[-+]?[0-9]*\\.?[0-9]*"));
        
        
//        String av = "\'a\'";
//        
//        System.out.println(av.charAt(0));
//        System.out.println(av.charAt(1));
//        System.out.println(av.charAt(2));
        
//        ArrayList<Token> tokens = new ArrayList<>();
//        String s = "======;";
//        char[] vp = new char[100];
//        boolean noRead =false;
//        for(int i=0; i<s.length(); i++){
//            
//            
//            if(s.charAt(i) == '=' && i+1<s.length() && s.charAt(i+1) == '=' && !noRead){
//                noRead=true;
//                vp[0] = '=';
//            }else if(!noRead){
////                 System.out.println("didn't come");
//                char[] temp  ;
//                switch (s.charAt(i)) {
//                    case ' ':
//                        break;
//                    case ';':
//                        vp = new char[]{';'};
//                        tokens.add(new Token(1, "SEMI_COLON", vp));
//                        break;
//                    default:
//                        temp = new char[]{'='};
//                        tokens.add(new Token(1, "COMPARES", temp));
//                        break;
//                }
//            }
//            else if(s.charAt(i) == '=' && s.charAt(i-1) == '='){
//                vp[1] = '=';
//                char[] temp = Arrays.copyOfRange(vp, 0, 2);
//                tokens.add(new Token(1, "COMPARES", temp));
//                noRead = false;
//            }
//            
//        }
//        System.out.println(tokens);
       
    }
}
