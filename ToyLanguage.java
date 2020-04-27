import java.util.*;
import java.lang.Math;
import java.io.*;
import java.lang.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
public class ToyLanguage {
    private String s;
    private int currIndex;
    private int n;
    private char inputToken;
    private ArrayList<String> Tokens = new ArrayList<String>();
    private ArrayList<Integer> values = new ArrayList<Integer>();

    public ExpEvaluator(String s){
        this.s = s;
        currIndex = 0;
        n = s.length();
        //nextToken();
    }
    void nextIdenfier(){
        char c;
        c=s.charAt(currIndex);
        String b="";
        while(!(Character.isWhitespace(c))){
           c = s.charAt(currIndex++);
           b = b + c;
        }
        b = b.replaceAll("\\s", "");
        String patternString = "([a-z]|[A-Z]|[_])([a-z]|[A-Z]|[_]|[0-9])*";

        Pattern pattern = Pattern.compile(patternString);

        Matcher matcher = pattern.matcher(b);
        boolean matches = matcher.matches();
        if (matches){
             Tokens.add(b);
        } else {
             throw new RuntimeException("error");
        }
    }

     void nextToken(){
     if (currIndex == n){
                inputToken = '$';
                return;
                }
        char c;
        c = s.charAt(currIndex++);
        inputToken = c;
                
    }   
        
    
    void match(char token){
       if (inputToken == token){
           nextToken();
       } else {
           throw new RuntimeException("syntax error");
       }
   }

  void eval(){
        
      while(inputToken != '$'){
      Assigment();
       }
       int k =Tokens.size();
       for (int i=0; i<k; i++){
         System.out.println(Tokens.get(i) + " = " + values.get(i));
       }
   }
   void Assigment(){
      nextIdenfier();
      nextToken();
      if (inputToken != '='){
         throw new RuntimeException("syntax error");
      }
      nextToken();
      nextToken();
      //nextToken();
      
      int z = exp();
      values.add(z);
      if (inputToken == ';'){
         nextToken();
      }
      else throw new RuntimeException("syntax error");
      }

  int exp(){
       int x = term();
       while (inputToken == '+' || inputToken == '-'){
           char op = inputToken;
           nextToken();
           int y = term();
           x = apply(op, x, y);
       }
       return x;
   }

   int term(){
       int x = factor();
       while (inputToken == '*'){
           char op = inputToken;
           nextToken();
           int y = factor();
           x = apply(op, x, y);
       }
       return x;
   }

    int factor(){
       int b;
       int count = 0;
       String s;
       s="";
       s = s.replaceAll("\\s", "");
       int x;
       if (inputToken == '-' || inputToken == '+') {
            while((inputToken == '-' || inputToken =='+') && inputToken != '$'){
                if(inputToken =='-') count++;
                nextToken();
            }
            if (inputToken == '('){
                     nextToken();
                     b = exp();
                     match(')');
                     if (count%2 == 0){
                        return b;
                     } else{
                        return (-b);
                     } 
            }
            while(inputToken != '-' && inputToken != '+' && inputToken != '$' && inputToken != '*' && inputToken != ';'){
                s=s+inputToken;
                nextToken();
                if (inputToken == ';') break;
                if (inputToken == ')') break;
            }
            if (checkidentifier(s)){
                int k = Tokens.indexOf(s);
                if (k == -1){
                    throw new RuntimeException("error");
                } 
                else
                { 
                    if (count%2 == 0){
                        b = values.get(k);
                } else{
                        b = (-values.get(k));
                }
            }
            }
            else if(checkliteral(s)){
                int result = Integer.parseInt(s);
                if (count%2 == 0){
                        b = result;
                } else{
                        b = (-result);
                }
            } else throw new RuntimeException("error");
            }
        else if((inputToken >= 48 && inputToken <= 57) || (inputToken >= 65 && inputToken <= 90) || (inputToken >= 97 && inputToken <= 122) || inputToken == 95){
                if (inputToken == '('){
                     nextToken();
                     b = exp();
                     match(')');
                     return b;
            }
                while(inputToken != '-' && inputToken != '+' && inputToken != '$' && inputToken != '*') {
                s=s+inputToken;
                nextToken();
                if (inputToken == ';') break;
                if (inputToken == ')') break;
            }
                if (checkidentifier(s)){
                int k = Tokens.indexOf(s);
                if (k == -1){
                    throw new RuntimeException("error");
                } 
                else
                { 
                    if (count%2 == 0){
                        b = (values.get(k));
                } else{
                        b = (-values.get(k));
                }
            }
            }
            else if(checkliteral(s)){
                int result = Integer.parseInt(s);
                if (count%2 == 0){
                        b = result;
                } else{
                        b = (-result);
                }
            } else throw new RuntimeException("error");
            }
            else if (inputToken == '('){
                     nextToken();
                     b = exp();
                     match(')');
            }
            else throw new RuntimeException("error");
            return b;
            }
       
       
       boolean checkidentifier(String s){
        String patternString = "([a-z]|[A-Z]|[_])([a-z]|[A-Z]|[_]|[0-9])*";

        Pattern pattern = Pattern.compile(patternString);

        Matcher matcher = pattern.matcher(s);
        boolean matches = matcher.matches();
        if (matches) return true; else return false; 
    }
    boolean checkliteral(String s){
        String patternString = "[0]|[1-9]([0-9])*";

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(s);
        boolean matches = matcher.matches();
        if (matches) return true; else return false;
    }
    static int apply(char op, int x, int y){
        int z = 0;
        switch (op){
            case '+': z = x + y; break;
            case '-': z = x - y; break;
            case '*': z = x * y; break;
        }
        return z;
    
}

     public static void main(String []args){
         ExpEvaluator ee = new ExpEvaluator("x = 1;\ny = 2;\nz = ---(x+(y+10));");
         ee.eval();
     }
}


       
         
	
 