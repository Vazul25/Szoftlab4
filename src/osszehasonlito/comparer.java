package osszehasonlito;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;


public class comparer {

   public static void main(String args[]) throws Exception {

     FileInputStream fstream1 = new FileInputStream(System.getProperty("user.dir")+"\\"+args[0]);
     FileInputStream fstream2 = new FileInputStream(System.getProperty("user.dir")+"\\"+args[1]);
      
     DataInputStream in1= new DataInputStream(fstream1);
     DataInputStream in2= new DataInputStream(fstream2);
      
     BufferedReader br1 = new BufferedReader(new InputStreamReader(in1));
     BufferedReader br2 = new BufferedReader(new InputStreamReader(in2));
      
     String strLine1, strLine2;
      
      
     while((strLine1 = br1.readLine()) != null && (strLine2 = br2.readLine()) != null){
         if(strLine1.equals(strLine2)){
             System.out.println();
              
         }
         else        System.out.println(strLine1);
          
     }
      br1.close();br2.close();
   }
}