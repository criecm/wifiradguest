package ecm.arrubaguest.server;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GenPasswd {

private String [] tabpaswd;
    
 public GenPasswd (int n){

  tabpaswd = new String[n];
  
 for (int k=0;k<n;k++) {
       
    int x = 0;
    char res;
    StringBuffer s = new StringBuffer();
    
    for (int i = 0; i <= 7; i++) {
    res= 0;    
    x = 1 + (int) ( Math.random() * ((3 - 1) + 1));
   //     System.out.println("Debut Séquence : " + x);
        switch (x) {
            case 1:
                res = getMinuscule();
                // System.out.print(getMinuscule());
                break;
            case 2:
                res = getMajuscule();
                //System.out.print(getMajuscule());
                break;
            case 3:
                res = getChiffre();
                //System.out.print(getChiffre());
                break;
        }
        s.append(res);
    }
    
 //   System.out.print(s);
 //   System.out.println("   FIN");
    tabpaswd[k] = s.toString();
    s.setLength(0);
 }
} 
 
public String MD5(String s) {
      
        byte[] uniqueKey = s.getBytes();
        byte[] hash      = null;

        try
        {
            hash = MessageDigest.getInstance("MD5").digest(uniqueKey);
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new Error("No MD5 support in this VM.");
        }

        StringBuilder hashString = new StringBuilder();
        for (int i = 0; i < hash.length; i++)
        {
            String hex = Integer.toHexString(hash[i]);
            if (hex.length() == 1)
            {
                hashString.append('0');
                hashString.append(hex.charAt(hex.length() - 1));
            }
            else
                hashString.append(hex.substring(hex.length() - 2));
        }
        return hashString.toString();
    }

 
public String [] gettabpaswd() {
    return tabpaswd;
}
 
public int getUnicodeAleatoire(){
return (int)(Math.random()*(65535+1));
}
    
public char getUnicodeAleatoire(char c1, char c2){
int t = (int) ( c1+Math.random()*(c2-c1+1) );
// System.out.print("-" + t + " ");
return (char)(t);

}

public char getMinuscule(){
return getUnicodeAleatoire('a', 'z');
}

public char getMajuscule(){
return getUnicodeAleatoire('A', 'Z');
}

public char getChiffre(){
return getUnicodeAleatoire('0', '9');
}

}
