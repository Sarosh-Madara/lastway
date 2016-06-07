/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spireon;

import Operators.Arithmatic_Ops;
import Operators.INC_DEC_Ops;
import Operators.Logical_Ops;
import Operators.Relational_Ops;
import com.sun.org.apache.xerces.internal.util.SymbolTable;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Stack;
import modal.SymbolTables;
import modal.Token;

/**
 *
 * @author saroshmadara
 */
public class Spireon extends javax.swing.JFrame {

    /**
     * Creates new form Spireon
     */
    
    
    
    static ArrayList<Token> tokens = new ArrayList();
    static String input;
    static String[] classParts = {"DT",".","ID","ASSIGN_OP","CONST",";"};
    static String[] DTs = {"num","deci","char","str","bool"};
    static String[] Ops = {"=",">=","<=","=="};
    static String[] keywords = {"do","function","default","new","static","scene","break","case","void","public","private","try","catch","finally","return","import","jump","interface","now","round","do_round","class"};
    static String[] Const = {"INT_CONST","FLT_CONST","STR_CONST","CHAR_CONST","BOOL_CONST"};
    static char[] breakers = {' ', '=', '.', '\n', '(', ')', '{', '}', ';', ',', '\'', '\"', '[', ']','+','-','*','/','>','<','-',':','!','&','|'};
    static String VP;
    static char current,next;
    static char[] doubleOps = {'+','-','*','/','=','>','<','!','&'};
    static String[] IdConst = {"INT_CONST","FLT_CONST","STR_CONST","CHAR_CONST","BOOL_CONST"};
    static String[] comparisions = {"SM_TH_E_T","COMPARES","NOT_E_T","GR_TH_E_T","SM_TH","GR_TH"};
    static String[] logOps = {"CONDITIONAL_AND","CONDITIONAL_OR"};
    static String t;
    static int ts = 0,te = 0, ln = 1, chToIgnore,index = 0;
    private static boolean equFlagSet = false,INC_DEC_FLAG= false,CONDITIONAL_OP=false;
    static int totalLines,i ;
    private static boolean veryLastComment=false;
    private static boolean NOT_DO_ROUND = false;
    static private boolean FLOAT_FOUND = false;
    static String Float_value = "";
    
   
    //  Semantic Items begins here
    static ArrayList<SymbolTables> symbolTable = new ArrayList<>();
    static Stack scopeStack = new Stack();
//    static int symbolTablePtr = 0;
   
    public static void insertID(String name,String type,String scope){
        symbolTable.add(new SymbolTables(name, type, scope));
    }
    
    public static void insertFunction(String name,String type,String scope){
        
    }
    
    
    
    
    
    

    private static boolean checkAttribute() {
        if(index < tokens.size() && tokens.get(index).classpart == "DT"){
            index++;
            if(index < tokens.size() && checkAttribute()){
                return true;
            }
        }else if(index < tokens.size() && checkConstructor()){
            return true;
        }
        return false;
    }

    private static boolean checkConstructor() {
        if(index < tokens.size() && checkAM()){
            index++;
            if(index < tokens.size() && checkClassName()){
                index++;
                if(index < tokens.size() && tokens.get(index).classpart == "ROUND_BRAC_OP"){
                    index++;
                    if(index < tokens.size() && checkArgList()){
                        index++;
                        if(index < tokens.size() && tokens.get(index).classpart == "ROUND_BRAC_CL"){
                            index++;
                            if(index < tokens.size() && tokens.get(index).classpart == "CURLI_BRAC_OP"){
                                index++;
                                if(index < tokens.size() && tokens.get(index).classpart == "DT"){
                                    index++;
                                    if(index < tokens.size() && tokens.get(index).classpart == "CURLI_BRAC_CL"){
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }else if(index < tokens.size() && checkForMethod()){
                return true;
            }
        }
        return false;
    }

    private static boolean checkAM() {
        if(index < tokens.size() && tokens.get(index).classpart == "PUBLIC"){
            return true;
        }else if(index < tokens.size() && tokens.get(index).classpart == "PRIVATE"){
            return true;
        }else if(index < tokens.size() && tokens.get(index).classpart == "PROTECTED"){
            return true;
        }else if(index < tokens.size() && tokens.get(index).classpart == "CLASS"){
            return true;
        }else if(index < tokens.size() && tokens.get(index).classpart == "ID"){
            return true;
        }
        return false;
    }

    private static boolean checkClassName() {
        if(index < tokens.size() && tokens.get(index).classpart == "ID"){
            return true;
        }
        return false;
    }

    private static boolean checkArgList() {
        if(index < tokens.size() && tokens.get(index).classpart == "DT"){
            index++;
            if(index < tokens.size() && tokens.get(index).classpart == "ID"){
                index++;
                if(index < tokens.size() && checkListArg()){
                    return true;
                }
            }
        }else if(index < tokens.size() && tokens.get(index).classpart == "ROUND_BRAC_CL"){
            return true;
        }
        return false;
    }

    private static boolean checkListArg() {
        if(index < tokens.size() && tokens.get(index).classpart == "COMMA"){
            index++;
            if(index < tokens.size() && checkArgList()){
                return true;
            }
        }else if(index < tokens.size() && tokens.get(index).classpart == "ROUND_BRAC_CL"){
            return true;
        }
        return false;
    }

    private static boolean checkForMethod() {
        if(index < tokens.size() && checkForFunction()){
            index++;
            if(index < tokens.size() && checkForMethod()){
                return true;
            }
        }else if(index < tokens.size() && tokens.get(index).classpart == "CURLI_BRAC_CL"){
            return true;
        }
        return false;
    }

    private static boolean checkForFunction() {
        if(index < tokens.size() && tokens.get(index).classpart == "FUNCTION"){
            index++;
            if(index < tokens.size() && checkAM()){
                index++;
                if(index < tokens.size() && checkStatic()){
                    index++;
                    if(index < tokens.size() && checkRetType()){
                        index++;
                        if(index < tokens.size() && checkFuncName()){
                            index++;
                            if(index < tokens.size() && tokens.get(index).classpart == "ROUND_BRAC_OP"){
                                index++;
                                if(index < tokens.size() && checkArgList()){
                                    index++;
                                    if(index < tokens.size() && tokens.get(index).classpart == "ROUND_BRAC_CL"){
                                        index++;
                                        if(index < tokens.size() && tokens.get(index).classpart == "CURLI_BRAC_OP"){
                                            index++;
                                            if(index < tokens.size() && insideFunc()){
                                                index++;
                                                if(index < tokens.size() && tokens.get(index).classpart == "CURLI_BRAC_CL"){
                                                    return true;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private static boolean checkStatic() {
        if(index < tokens.size() && tokens.get(index).classpart == "STATIC"){
            return true;
        }else if(index < tokens.size() && tokens.get(index).classpart == "VOID"){
            return true;
        }else if(index < tokens.size() && tokens.get(index).classpart == "DT"){
            return true;
        }
        return false;
    }

    private static boolean checkRetType() {
        if(index < tokens.size() && tokens.get(index).classpart == "VOID"){
            return true;
        }else if(index < tokens.size() && tokens.get(index).classpart == "DT"){
            return true;
        }
        return false;
    }

    private static boolean checkFuncName() {
        if(index < tokens.size() && tokens.get(index).classpart == "ID"){
            return true;
        }
        return false;
    }

    private static boolean insideFunc() {
        if(index < tokens.size() && functionBody()){
            index++;
            if(index < tokens.size() && insideFuncPrime()){
                
            }
        }
        return false;
    }

    private static boolean functionBody() {
        if(index < tokens.size() && funcS_St()){
            index++;
            if(index < tokens.size() && functionBody()){
                return true;
            }
        }
        return false;
    }

    private static boolean funcS_St() {
        if(index < tokens.size() && tokens.get(index).classpart == "ID"){
            index++;
            if(index < tokens.size() && checkValue()){
                index++;
                System.out.println("Initialization ok");
                if(index < tokens.size() && parseSingleState()){
                    return true;
                }
            }
        }else if(index < tokens.size() && tokens.get(index).classpart == "ROUND" && !NOT_DO_ROUND){
            index++;
            if(index < tokens.size() && whileFunc()){
                index++;
                if(index < tokens.size() && parseSingleState()){
                    return true;
                }
            }else{
                System.out.println("SYNTAX ERROR!!!!!!!! @ line "+ tokens.get(index).ln);
                return false;
            }
        }
        else if(index < tokens.size() && tokens.get(index).classpart == "IF"){
            index++;
            if(index < tokens.size() && ifFunc()){
                return true;
            }else{
                System.out.println("SYNTAX Error @::"+tokens.get(index).ln);
            }
        }
        else if(index < tokens.size() && tokens.get(index).classpart == "DO_ROUND"){
            index++;
            if(index < tokens.size() && doWhileFunc()){
                System.out.println("valid::do_round");
                index++;
                if(index < tokens.size() && parseSingleState()){
                    return true;
                }
            }
        }else if(index < tokens.size() && tokens.get(index).classpart == "NOW"){
            index++;
            if(index < tokens.size() && forFunc()){
                System.out.println("parsed forloop");
            }else{
                System.out.println("SYNTAX Error @::"+tokens.get(index).ln);
            }
        }else if(index < tokens.size() && tokens.get(index).classpart == "JUMP"){
            index++;
            if(index < tokens.size() && switchFunc()){
                return true;
            }
        }
        return false;
    }

    private static boolean insideFuncPrime() {
        if(index < tokens.size() && tokens.get(index).classpart == "RETURN"){
            index++;
            if(index < tokens.size() && checkRetType()){
                return true;
            }
        }else if(index < tokens.size() && tokens.get(index).classpart == "CURLI_BRAC_CL"){
            return true;
        }
        return false;
    }
    
    
    public Spireon() {
        initComponents();
       TextLineNumber tln = new TextLineNumber(source);
       jScrollPane1.setRowHeaderView( tln );
       setIcon();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        source = new javax.swing.JTextPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        output = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        compile = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Spireon");
        setBackground(new java.awt.Color(255, 0, 0));

        source.setText("class A{\nvoid scene(){\njump(a){\ncase a:\n\tb= 3;\n\tbreak;\ndefault:\n\tc=4;\n\tbreak;\n}\n}\n}");
        jScrollPane1.setViewportView(source);

        output.setEditable(false);
        output.setColumns(20);
        output.setRows(5);
        jScrollPane2.setViewportView(output);

        jLabel1.setBackground(new java.awt.Color(66, 124, 255));
        jLabel1.setFont(new java.awt.Font("Noto Sans", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 111, 130));
        jLabel1.setText("Code");

        jLabel2.setBackground(new java.awt.Color(66, 124, 255));
        jLabel2.setFont(new java.awt.Font("Noto Sans", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 111, 130));
        jLabel2.setText("Output");

        compile.setText("Compile");
        compile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compileActionPerformed(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/spireon/spireon-logo.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 99, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addGap(139, 139, 139))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addGap(365, 365, 365)
                .addComponent(compile, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(compile)
                .addGap(39, 39, 39))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void compileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_compileActionPerformed
        // TODO add your handling code here:
        veryLastComment = false;
        ln = 1;
        output.setText("");
        tokens = new ArrayList<>();
        te = 0; 
        ts = 0;
        chToIgnore = 0;
        output.setText(null);
        input = source.getText();
        index = 0;
        INC_DEC_FLAG = false;
        CONDITIONAL_OP=false;
        equFlagSet = false;
        NOT_DO_ROUND = false;
        FLOAT_FOUND =false;
        totalLines = 0;
        Float_value = "";
        i=0;
        VP = "";
        
    
            
        for(i = 0; i< input.length(); i++){
            current = input.charAt(i);
            if(i+1 < input.length()-1){
                next = input.charAt(i+1);
            }
            
            
            // when comments found
            if(current=='-' && next==current & input.charAt(i+2)=='-'){
                   int counter = 0;
                   chToIgnore = i+3;
                   while(counter++ < input.length() && chToIgnore < input.length() && input.charAt(chToIgnore) != '\n'){
                       
                       chToIgnore++;
                   }
                   
//                   System.out.println("counter part: "+counter+"  chToIgnore: " + chToIgnore);
                   i = chToIgnore;
                   veryLastComment=true;
            }
            else if(check4Br(input.charAt(i)) || i==input.length()-1){
                
                
                if(i==input.length()-1 && !check4Br(input.charAt(i))){
                    VP = input.substring(ts,i+1);
                }else if( current == '\n' && ts > 0 ){
                    VP = input.substring(ts-1,i);
                }else if(!equFlagSet && !INC_DEC_FLAG){
                    VP = input.substring(ts, i);
                }
                else{
                    VP=input.substring(ts,i);
                }
                
                 // check for floating point number
                if(input.charAt(i)=='.'){
                    Float_value = VP+".";
                    VP = "";
                    FLOAT_FOUND = true;
                }
                
                if(veryLastComment){
                    if(input.charAt(chToIgnore) == '\n'){
                        VP = input.substring(chToIgnore,i);
                        ln++;
                    }else {
                        VP = input.substring(chToIgnore-1,i);
                    }
                    veryLastComment = false;
                }
                VP  = VP.trim();
                
                String CP;
                
                
                if(check4Keyword(VP) != null){
                    tokens.add(new Token(ln, check4Keyword(VP), VP.toCharArray()));
                    
                    ts = i+1;
                    te = 0;
                    String br = null;
                }else if((CP = check4DT(VP))  != null){
                    
                    tokens.add(new Token(ln, CP, VP.toCharArray()));
                    
                    ts = i+1;
                    te = 0;
                   
                }
                else if(check4Const(VP) && !FLOAT_FOUND){
                    tokens.add(new Token(ln, "INT_CONST", VP.toCharArray()));
                    
                    ts = i+1;
                    te = 0;
                    String br = null;
                }
                else if(check4Float(Float_value.concat(VP)) ){
                    
                    tokens.add(new Token(ln, "FLT_CONST", (Float_value.concat(VP)).toCharArray()));
                    FLOAT_FOUND = false;
                    Float_value = "";
                }
                
                // 4 char constant
                else if(current=='\'' && check4CharConst(VP+current)){ 
                    tokens.add(new Token(ln, "CHAR_CONST", (VP+current+"").toCharArray()));
                    VP = "";
                }
                else if(check4StrConst(VP)){
                    tokens.add(new Token(ln, "STR_CONST", (VP).toCharArray()));
                }
                else if(check4ID(VP)){
                    CP = "ID";
                    tokens.add(new Token(ln, CP, VP.toCharArray()));
                    ts = i+1;
                    te = 0;
                }
                
                
                
                // REST OF THE TERMINATORS & OPERATORS
                if(check4Terminator(input.charAt(i)) != null){
                       tokens.add(new Token(ln, check4Terminator(input.charAt(i)), new char[]{input.charAt(i)}));
//                       ts++;
                       te = 0;
                       ts = i+1;
               }
                // ops that can be doubled

               if(checkIfSingle(input.charAt(i)) && i+1<input.length() && input.charAt(i+1) == '=' && !equFlagSet){
                    equFlagSet=true;
                    VP = String.valueOf(current);
                }else if(!equFlagSet){
                    String t1;
                   
                    if((t1 = Relational_Ops.checkSingleOp(input.charAt(i))) != null){
                        tokens.add(new Token(ln,t1,new char[]{input.charAt(i)}));
                        te = 0;
                        ts = i+1;
                    }
                    if(input.charAt(i)== '='){
                        tokens.add(new Token(ln,"ASSIGN",new char[]{input.charAt(i)}));
                        te = 0;
                        ts = i+1;
                    }
                    t1 = null;
                }
                else if(input.charAt(i) == '=' && checkIfSingle(input.charAt(i-1))){
                     
                    tokens.add(new Token(ln,Relational_Ops.checkDoubleOp("["+input.charAt(i-1)+", "+current+"]") , new char[]{input.charAt(i-1),input.charAt(i)}));
                    equFlagSet = false;
                    ts = i+1;
                }
               
               
               
               if(checkIfSingle(current) && i+1<input.length() && next == current && !INC_DEC_FLAG){
                    INC_DEC_FLAG = true;
                    VP = String.valueOf(current);
                }else if(!INC_DEC_FLAG){
                    String t1;
                    if(( t1= Arithmatic_Ops.ADDSUB.check(input.charAt(i)))!=null){
                        tokens.add(new Token(ln,t1,new char[]{input.charAt(i)}));
                        te = 0;
                        ts = i+1;
                    }
                    t1 = null;
                    
                    if((t1=Arithmatic_Ops.MULDIV.check(input.charAt(i))) != null){
                        tokens.add(new Token(ln,t1,new char[]{input.charAt(i)}));
                        te = 0;
                        ts = i+1;
                    }
                }
                else if((current == '+' || current=='-' || current == '!' || current == '*' || current == '/') && input.charAt(i-1) == current){
                    tokens.add(new Token(ln,INC_DEC_Ops.check("["+input.charAt(i-1)+", "+current+"]") , new char[]{input.charAt(i-1),input.charAt(i)}));
                    INC_DEC_FLAG = false;
                    ts = i+1;
                }
               
               
               
//               Single and Double Logical Operator
               if((current == '&' || current=='|') && i+1<input.length() && next == current && !CONDITIONAL_OP){
                    CONDITIONAL_OP = true;
                    t=String.valueOf(current);
                    VP = String.valueOf(current);
                }else if(!CONDITIONAL_OP){
                    String t1;
                    if(( t1= Logical_Ops.checkLogical(input.charAt(i)))!=null){
                        tokens.add(new Token(ln,t1,new char[]{input.charAt(i)}));
                        te = 0;
                        ts = i+1;
                    }
                    t1 = null;
                }
                else if((current == '&' || current=='|') && input.charAt(i-1) == current){
                    tokens.add(new Token(ln,Logical_Ops.checkConditional("["+input.charAt(i-1)+", "+current+"]") , new char[]{input.charAt(i-1),input.charAt(i)}));
                    CONDITIONAL_OP = false;
                    ts = i+1;
                }
               
               
               
               
               // when space occurs increments by 1
                if(input.charAt(i) == ' '){
                    ts = i+1;
                }
                
                Float_value = "";
               
                
                
            }else{
                te++;
            }
        }
        
        outputTokens(tokens);
        
//        lexical phase ends & tokens generated.




        


//        syntax analyzer phase begins here


        if(parse()){
            System.out.println("Valid Dec");
        }

          

        
//        output.append(tokens);

        
        
    }//GEN-LAST:event_compileActionPerformed

    
    static public boolean classFunc(){
        
        if(tokens.get(index).classpart == "ID") {
            index++;
            if(tokens.get(index).classpart == "CURLI_BRAC_OP"){
                index++;
                if(index < tokens.size() && checkAttribute()){
                    return true;
                }
            }
        }
        return false;
    }
    
    static public boolean mainFunc(){
       if(index < tokens.size() && tokens.get(index).classpart == "ROUND_BRAC_OP"){
            index++;
            if(index < tokens.size() && tokens.get(index).classpart == "ROUND_BRAC_CL"){
                index++;
                if(index < tokens.size() && tokens.get(index).classpart == "CURLI_BRAC_OP"){
//                    System.out.println("main() ok");
                    return true;
                }
                else 
                    return false;
            }else {
                return false;
            }
        }else{
           return false;
       }
        
    }
    
    static public boolean forFunc(){
        if(index < tokens.size() && tokens.get(index).classpart == "ROUND_BRAC_OP"){
            index++;
            if(index < tokens.size() && checkX()){
                index++;
                if(index < tokens.size() && checkY()){
                    index++;
                    if(index < tokens.size() && checkZ()){
                        index++;
                        if(index < tokens.size() && tokens.get(index).classpart == "ROUND_BRAC_CL"){
                            index++;
                            if(index < tokens.size() && tokens.get(index).classpart == "CURLI_BRAC_OP"){
                                index++;
                                if(index < tokens.size() && checkBody()){
                                    index++;
                                    if(index < tokens.size() && tokens.get(index).classpart == "CURLI_BRAC_CL"){
                                        return true;
                                    }
                                }else if(index < tokens.size() && tokens.get(index).classpart == "CURLI_BRAC_CL"){
                                    return true;
                                }else
                                    return false;
                            }
                            System.out.println("popoSyntax Error!! @"+tokens.get(index).ln);
                            return false;
                        }
                    }
                }
            }
        }
       return false;
    }
    
    
    static public boolean whileFunc(){
           if(index < tokens.size() && tokens.get(index).classpart == "ROUND_BRAC_OP"){
               index++;
               if(index < tokens.size() && checkCond()){
                   if(index <tokens.size() && tokens.get(index).classpart == "ROUND_BRAC_CL"){
                       index++;
                        if(index <tokens.size() && tokens.get(index).classpart == "CURLI_BRAC_OP"){
                            index++;
                            if(index <tokens.size() && parseSingleState()){
                                index++;
                                if(index <tokens.size() && tokens.get(index).classpart == "CURLI_BRAC_CL"){
                                    return true;
                                }
                            }else if(index <tokens.size() && tokens.get(index).classpart == "CURLI_BRAC_CL"){
                                System.out.println("valid::round");
                                return true;
                            }
                        }
                   }
               }
               
           }
        return false;
    }
    
    static public boolean doWhileFunc(){
           if(index < tokens.size() && tokens.get(index).classpart == "CURLI_BRAC_OP"){
               index++;
               NOT_DO_ROUND = true;
               if(index < tokens.size() && parseSingleState()){
                   System.out.println("code in do_round");
                   index++;
                   if(index <tokens.size() && tokens.get(index).classpart == "CURLI_BRAC_CL"){
                       index++;
                       if(index <tokens.size() && tokens.get(index).classpart == "ROUND"){
                           index++;
                           if(index <tokens.size() && tokens.get(index).classpart == "ROUND_BRAC_OP"){
                                index++;
                                if(index <tokens.size() && checkCond()){
                                    index++;
                                    if(index <tokens.size() && tokens.get(index).classpart == "ROUND_BRAC_CL"){
                                        index++;
                                        if(index <tokens.size() && tokens.get(index).classpart == "SEMICOLON"){
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                   }
               }else if(index <tokens.size() && tokens.get(index).classpart == "CURLI_BRAC_CL"){
                   index++;
                   if(index <tokens.size() && tokens.get(index).classpart == "ROUND"){
                        index++;
                        if(index <tokens.size() && tokens.get(index).classpart == "ROUND_BRAC_OP"){
                             index++;
                             if(index <tokens.size() && checkCond()){
                                 index++;
                                 if(index <tokens.size() && tokens.get(index).classpart == "ROUND_BRAC_CL"){
                                     index++;
                                     if(index <tokens.size() && tokens.get(index).classpart == "SEMICOLON"){
                                         return true;
                                     }
                                 }
                             }
                         }
                    }
               }
               System.out.println("oeee:: "+tokens.get(index));
           }
                 System.out.println("before false");  
       return false;
    }
    
    
    static public boolean switchFunc(){
        if(index < tokens.size() && tokens.get(index).classpart == "ROUND_BRAC_OP"){
                index++;
                if(index < tokens.size() && checkIdConst() || tokens.get(index).classpart == "ID"){
                    index++;
                    if(index < tokens.size() && tokens.get(index).classpart == "ROUND_BRAC_CL"){
                        index++;
                        if(index < tokens.size() && tokens.get(index).classpart == "CURLI_BRAC_OP"){
                            index++;
                            if(index < tokens.size() && checkCaseState()){
                                System.out.println("step:6");
                                index++;
                                if(index < tokens.size() && tokens.get(index).classpart == "CURLI_BRAC_CL"){
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        return false;
    }
    
    static public boolean ifFunc(){
        if(index < tokens.size() && tokens.get(index).classpart == "ROUND_BRAC_OP"){
            index++;
            if(index < tokens.size() && checkCond1()){
                index++;
                if(index < tokens.size() && tokens.get(index).classpart == "ROUND_BRAC_CL"){
                    index++;
                    if(index < tokens.size() && parseSingleState()){
                        index++;
                        if(index < tokens.size() && checkOpElse()){
                            index++;
                        }
                    }
                }
            }else if(index < tokens.size() && checkCond2()){
                index++;
                if(index < tokens.size() && tokens.get(index).classpart == "ROUND_BRAC_CL"){
                    index++;
                    if(index < tokens.size() && parseSingleState()){
                        index++;
                    }
                }
            }
        }
       return false;
    }
    
    
    
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Spireon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Spireon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Spireon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Spireon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                new Spireon().setVisible(true);
            }
        });
    }
    
    
    private static String check4DT(String VP) {
        for (String DT : DTs) {
            if(DT.equalsIgnoreCase(VP))
                return "DT";
        }
        return null;
    }
    
    private static String check4Ops(String VP){
        for(String Op: Ops)
            if(Op.equalsIgnoreCase(VP)){
                return Op;
            }
        return null;
    }
    
    
    private static boolean check4Const(String VP){
        return VP.matches("[0-9]{1,}");
    }
    
    private static boolean check4Float(String VP){
        return VP.matches("[-+]?(\\d*[.])?\\d+");
    }

    private static boolean check4Br(char c) {
        for(int i = 0; i<breakers.length; i++){
            if(breakers[i] == c)
                return true;
        }
        return false;
    }
    
    private static boolean check4ID(String str){
        return str.matches("[A-Za-z]{1,}.*");
//        return str.matches("[^A-Za-z|(_(/w*)[A-Za-z0-9]){2,}]{1,}");
//        return str.matches("^[A-Za-z]_((/w*)[A-Za-z0-9])*$");
    }

    private static String check4Terminator(char c) {
        switch(c){
            case ';':
                return "SEMICOLON";
            case ':':
                return "COLON";
            case ')':
                return "ROUND_BRAC_CL";
            case '(':
                return "ROUND_BRAC_OP";
            case '[':
                return "SQUARE BRAC_OP";
            case ']':
                return "SQUARE_BRAC_CL";
            case '{':
                return "CURLI_BRAC_OP";
            case '}':
                return "CURLI_BRAC_CL";
            case ',':
                return "COMMA";
            case '\n':
                ln++;
                return null;
            default:
                return null;
        }
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton compile;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private static javax.swing.JTextArea output;
    private javax.swing.JTextPane source;
    // End of variables declaration//GEN-END:variables

    private void setIcon() {
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("favicon.png")));
    }

    private void outputTokens(ArrayList<Token> tokens) {
        tokens.stream().forEach((t) -> {
            output.append(t.toString() + "\n");
        });
    }

    private String check4Keyword(String VP) {
        switch(VP){
            case "new":
                return "NEW";
            case "break":
                return "BREAK";
            case "case":
                return "CASE";
            case "scene":
                return "SCENE";
            case "void":
                return "VOID";
            case "default":
                return "DEFAULT";
            case "public":
                return "ACCESS_MODIFIER";
            case "private":
                return "ACCESS_MODIFIER";
            case "protected":
                return "ACCESS_MODIFIER";
            case "class":
                return "CLASS";
            case "try":
                return "TRY";
            case "catch":
                return "CATCH";
            case "finally":
                return "FINALLY";
            case "jump":
                return "JUMP";
            case "import":
                return "IMPORT";
            case "interface":
                return "INTERFACE";
            case "now":
                return "NOW";
            case "round":
                return "ROUND";
            case "do":
                return "DO_ROUND";
            case "if":
                return "IF";
            case "else":
                    return "ELSE";
            case "static":
                return "STATIC";
            default:
                    return null;
        }
    }

    private boolean checkIfSingle(char c) {
        for(char ch:doubleOps)
            if(ch==c)
                return true;
        return false;
    }

    private boolean check4CharConst(String VP) {
        return VP.matches("\\'(\\s|[A-Za-z0-9])\\'");
    }

    private boolean check4StrConst(String VP) {
        return VP.matches("\"(\\s*|[A-Za-z0-9])*\"");
    }

    static private boolean checkInit() {
        if(index < tokens.size() && tokens.get(index).classpart == "ASSIGN"){
            index++;
            if(index < tokens.size() && initPrime()){
                
            }else if(index < tokens.size() &&  tokens.get(index).classpart == "COMMA"){
                return true;
            }else if(index < tokens.size() &&  tokens.get(index).classpart == "SEMICOLON"){
                return true;
            }
        }
        return false;
    }

    static private boolean checkList() {
        if(index < tokens.size() && tokens.get(index).classpart == "SEMICOLON"){
            return true;
        }
        else if(index < tokens.size() && tokens.get(index).classpart == "COMMA"){
            index++;
            if(index < tokens.size() && tokens.get(index).classpart == "ID"){
                index++;
                if(checkInit()){
                    index++;
                    if(checkList()){
                        
                    }
                }
            }
        }
        return false;
    }

    private boolean parse() {
        if(index < tokens.size() && tokens.get(index).classpart == "ACCESS_MODIFIER"){
            index++;
            if(index < tokens.size() && tokens.get(index).classpart == "CLASS"){
                index++;
                if(index < tokens.size() && classTempFunc()){
                    index++;
                    if(index < tokens.size() && parseClassBody()){
                    }else{
                        if(index < tokens.size() && tokens.get(index).classpart == "CURLI_BRAC_CL"){
                            return true;
                        }else {
                            System.out.println("reached end of file while parsing.");
                            return false;
                        }
                    }
                }else{
                    System.out.println("Syntax Error  @ line: " + tokens.get(index-1).ln );
                }
            }
        }else if(index < tokens.size() && tokens.get(index).classpart == "CLASS"){
            index++;
            if(index < tokens.size() && classTempFunc()){
                index++;
                if(index < tokens.size() && parseClassBody()){
                    index++;
                    if(index < tokens.size() && mainFunc()){
                        index++;
                        if(index < tokens.size() && parseSingleState()){
                            return true;
                        }
                    }
                }
            }else{
                System.out.println("Syntax ERROR!!!!!!!!");
            }
        }
        
        return false;
                    
    }

    private boolean parseClassBody() {
        if(index < tokens.size() && tokens.get(index).classpart == "VOID"){
            index++;
            if(index < tokens.size() && tokens.get(index).classpart == "SCENE"){
                index++;
                return true;
            }else{
                System.out.println("Syntax Error  @ line: " + tokens.get(index-1).ln );
            }
        }
        
        return false;
    }
    
    void printarray(){
        for (Token token : tokens) {
            System.out.println(token);
        }
    }

    static private boolean initAndDec() {
        if(index < tokens.size() && tokens.get(index).classpart == "ID"){
            index++;
            if(checkInit()){
                index++;
                if(checkList()){
                    
                }
            }
        }
        return false;
    }

    static private boolean initPrime() {
        if(index < tokens.size() && tokens.get(index).classpart == "INT_CONST"){
            index++;
            if(index < tokens.size() && tokens.get(index).classpart == "SEMICOLON"){
                return true;
            }
        }else if(index < tokens.size() && tokens.get(index).classpart == "ID"){
            index++;
            if(index < tokens.size() && checkInit()){
                return true;
            }
        }
        return false;
    }

    private static boolean checkIdConst() {
       for(int i=0; i<IdConst.length; i++){
           if(IdConst[i] == tokens.get(index).classpart){
               return true;
           }
       }
    return false;
    }

    private static boolean checkCond() {
        System.out.println("aya k nahi::"+ tokens.get(index));
        if(index < tokens.size() && tokens.get(index).classpart == "ID" || checkIdConst()){
            index++;
            if(check4Comparisions()){
                index++;
                if(index < tokens.size() && tokens.get(index).classpart == "ID" || checkIdConst()){
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean check4Comparisions() {
        for(int i = 0; i<comparisions.length; i++){
            if(comparisions[i] == tokens.get(index).classpart){
                return true;
            }
        }
        return false;
    }

    private static boolean parseSingleState() {
        
        if(index < tokens.size() && tokens.get(index).classpart == "ID"){
            index++;
            if(index < tokens.size() && checkValue()){
                index++;
                System.out.println("Initialization ok");
                if(index < tokens.size() && parseSingleState()){
                    return true;
                }
            }
        }else if(index < tokens.size() && tokens.get(index).classpart == "DT"){
            index++;
            if(index < tokens.size() && checkInit()){
                index++;
                if(index < tokens.size() && checkList2()){
                    
                }
            }
        }else if(index < tokens.size() && tokens.get(index).classpart == "ROUND" && !NOT_DO_ROUND){
            index++;
            if(index < tokens.size() && whileFunc()){
                index++;
                if(index < tokens.size() && parseSingleState()){
                    return true;
                }
            }else{
                System.out.println("SYNTAX ERROR!!!!!!!! @ line "+ tokens.get(index).ln);
                return false;
            }
        }
        else if(index < tokens.size() && tokens.get(index).classpart == "IF"){
            index++;
            if(index < tokens.size() && ifFunc()){
                
            }
        }
        else if(index < tokens.size() && tokens.get(index).classpart == "DO_ROUND"){
            index++;
            if(index < tokens.size() && doWhileFunc()){
                System.out.println("valid::do_round");
                index++;
                if(index < tokens.size() && parseSingleState()){
                    return true;
                }
            }
        }else if(index < tokens.size() && tokens.get(index).classpart == "NOW"){
            index++;
            if(index < tokens.size() && forFunc()){
                System.out.println("parsed forloop");
            }else{
                System.out.println("SYNTAX Error @::"+tokens.get(index).ln);
            }
        }else if(index < tokens.size() && tokens.get(index).classpart == "JUMP"){
            index++;
            
            if(index < tokens.size() && switchFunc()){
                return true;
            }
        }
        else if(index < tokens.size() && checkIdConst()){
            index++;
//            if(){
//                
//            }
        }
        
        else if(index < tokens.size() && checkIdConst()){
            index++;
            if(initAndDec()){
                index++;
//                parseBody();
            }
        }else if(index < tokens.size() && tokens.get(index).classpart == "STATIC"){
            
        }
        return false;
    }

//    private boolean parseBody() {
//        return false;
//    }

    private static boolean checkValue() {
        if(index < tokens.size() && tokens.get(index).classpart == "ASSIGN"){
            index++;
            if(index < tokens.size() && checkValuePrime()){
                return true;
            }
        }
        return false;
    }

    private static boolean checkValuePrime() {
        if(index < tokens.size() && checkIdConst()){
            index++;
            if(index < tokens.size() && tokens.get(index).classpart == "SEMICOLON"){
                return true;
            }
        }else if(index < tokens.size() && checkList1()){
                return true;
        }
        return false;
    }

    private boolean checkListInit() {
        if(index < tokens.size() && checkValue()){
            return true;
        } 
        return false;
    }

    static private boolean checkCond1() {
        if(index < tokens.size() && checkIdConst()){
            index++;
            if(index < tokens.size() && checkOperatros()){
                index++;
//                if(index < tokens.size() && tokens.get(index).classpart == "ROUND_BRAC_OP"){
//                    
//                }
            }
        }
        return false;
    }

    static private boolean checkOperatros() {
        if(index < tokens.size() && checkRelOp()){
            
        }else if(index < tokens.size() && checkLogOp()){
            
        }
        return false;
    }

    static private boolean checkRelOp() {
        for (String op : comparisions) {
            if(tokens.get(index).classpart == op)
                return true;
        }
        return false;
    }

    static boolean checkLogOp() {
        for (String op : logOps) {
            if(tokens.get(index).classpart == op){
                return true;
            }
        }
        return false;
    }

    static private boolean checkCond2() {
        if(index < tokens.size() && tokens.get(index).classpart == "ID"){
            
        }else if(index < tokens.size() && tokens.get(index).classpart == "NOT"){
            index++;
            if(index < tokens.size() && tokens.get(index).classpart == "ID"){
                index++;
                if(index < tokens.size() && checkCond2Prime()){
                    index++;
                    if(index < tokens.size() && checkOperatros()){
                        
                    }
                }
            }
        }
        return false;
    }   

    static private boolean checkCond2Prime() {
        
        return false;
    }

    static private boolean checkOpElse() {
        if(index < tokens.size() && tokens.get(index).classpart == "ELSE"){
            index++;
            if(index < tokens.size() && checkBody()){
                index++;
            }
        }
        return false;
    }

    static private boolean checkBody() {
        System.out.println("roget::");
        if(index < tokens.size() && tokens.get(index).classpart == "CURLI_BRAC_OP"){
            index++;
            if(index < tokens.size() && parseMultiState()){
                index++;
                if(index < tokens.size() && tokens.get(index).classpart == "CURLI_BRAC_CL"){
                    return true;
                }
            }
        }else if(index < tokens.size() && parseSingleState()){
            return true;
        }
        return false;
    }

    static private boolean parseMultiState() {
        if(index < tokens.size() && parseSingleState()){
            index++;
            if(index < tokens.size() && parseMultiState()){
                
            }
        }else if(index < tokens.size() && tokens.get(index).classpart == "SEMICOLON"){
            return true;
        }
        else if(index < tokens.size() && tokens.get(index).classpart == "DT"){
            return true;
        }
        else if(index < tokens.size() && tokens.get(index).classpart == "ID"){
            return true;
        }
        else if(index < tokens.size() && tokens.get(index).classpart == "ROUND"){
            return true;
        }
        else if(index < tokens.size() && tokens.get(index).classpart == "JUMP"){
            return true;
        }else if(index < tokens.size() && tokens.get(index).classpart == "DO"){
            return true;
        }else if(index < tokens.size() && tokens.get(index).classpart == "NOW"){
            index++;
//            if(index < tokens.size() && tokens.get(index).classpart == "SEMICOLON"){
//                index++;
//                
//            }
        }else if(index < tokens.size() && tokens.get(index).classpart == "INC_DEC"){
            return true;
        }else if(index < tokens.size() && tokens.get(index).classpart == "IF"){
            return true;
        }else if(index < tokens.size() && tokens.get(index).classpart == "CURLI_BRAC_CL"){
            return true;
        }
        return false;
    }

    static private boolean checkX() {
        if(index < tokens.size() && tokens.get(index).classpart == "ID"){
            index++;
            if(index < tokens.size() && checkValue()){
                System.out.println("Initialization ok::for");
                return true;
            }
        }
        return false;
    }

    static private boolean checkY() {
        if(index < tokens.size() && checkCond()){
            index++;
            if(index < tokens.size() && tokens.get(index).classpart == "SEMICOLON"){
                return true;
            }
        }else if(index <tokens.size() && tokens.get(index).classpart == "SEMICOLON"){
            return true;
        }
        return false;
    }

    static private boolean checkZ() {
        if(index < tokens.size() && tokens.get(index).classpart == "ID"){
            index++;
            if(index < tokens.size() && tokens.get(index).classpart == "INC_DEC"){
                return true;
            }
        }else if(index < tokens.size() && tokens.get(index).classpart == "INC_DEC"){
            index++;
            if(index < tokens.size() && tokens.get(index).classpart == "ID"){
                return true;
            }
        }else if(index < tokens.size() && tokens.get(index).classpart == "ROUND_BRAC_CL"){
            return true;
        }
        return false;
    }

    static private boolean checkList2() {
        if(index < tokens.size() && tokens.get(index).classpart == "COMMA"){
            index++;
            if(index < tokens.size() && tokens.get(index).classpart == "ID"){
                index++;
                if(index < tokens.size() && checkInit()){
                    index++;
                    if(index < tokens.size() && checkList2()){
                        return true;
                    }
                }
            }
        }else if(index < tokens.size() && tokens.get(index).classpart == "SEMICOLON"){
            return true;
        }
        return false;
    }

    static private boolean checkCaseState() {
        if(index < tokens.size() && tokens.get(index).classpart == "CASE"){
            index++;
            if(index < tokens.size() && checkIdConst() || tokens.get(index).classpart == "ID"){
                index++;
                if(index < tokens.size() && tokens.get(index).classpart == "COLON"){
                    index++;
                    if(index < tokens.size() && parseSingleState()){
                        index++;
                        if(index < tokens.size() && tokens.get(index).classpart == "BREAK"){
                            index++;
                            if(index < tokens.size() && tokens.get(index).classpart == "SEMICOLON"){
                                index++;
                                if(index < tokens.size() && checkCaseLoop()){
                                    return true;
                                }
                            }
                        }
                    }else if(index < tokens.size() && tokens.get(index).classpart == "BREAK"){
                         index++;
                            if(index < tokens.size() && tokens.get(index).classpart == "SEMICOLON"){
                                index++;
                                if(index < tokens.size() && checkCaseLoop()){
                                    return true;
                                }
                            }
                    }
                }
            }
        }
        return false;
    }

    static private boolean checkCaseLoop() {
        if(index < tokens.size() && checkCaseState()){
            return true;
        }else if(index < tokens.size() && checkDefault()){
            return true;
        }else if(index < tokens.size() && tokens.get(index).classpart == "CURLI_BRAC_CL"){
            return true;
        }
        return false;
    }

    static private boolean checkDefault() {
        if(index < tokens.size() && tokens.get(index).classpart == "DEFAULT"){
            index++;
            if(index < tokens.size() && tokens.get(index).classpart == "COLON"){
                index++;
                if(index < tokens.size() && checkBody()){
                    index++;
                    if(index < tokens.size() && tokens.get(index).classpart == "BREAK"){
                        index++;
                        if(index < tokens.size() && tokens.get(index).classpart == "SEMICOLON"){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    static private boolean checkList1() {
//        if(index < tokens.size() && check)
        return false;
    }

    private boolean classTempFunc() {
        if(index < tokens.size() && tokens.get(index).classpart == "ID"){
            index++;
            if(index < tokens.size() && tokens.get(index).classpart == "CURLI_BRAC_OP"){
                return true;
            }
        }
        return false;
    }
}