

/**
 * This is a utility class that encrypts and decrypts a phrase using three
 * different approaches. 
 * 
 * The first approach is called the Vigenere Cipher.Vigenere encryption 
 * is a method of encrypting alphabetic text based on the letters of a keyword.
 * 
 * The second approach is Playfair Cipher. It encrypts two letters (a digraph) 
 * at a time instead of just one.
 * 
 * The third approach is Caesar Cipher. It is a simple replacement cypher. 
 * 
 * @author Jonathan Alapatt
 * @version 10/22/2026
 */

package assignment3;
public class CryptoManager { 

    private static final char LOWER_RANGE = ' ';
    private static final char UPPER_RANGE = '_';
    private static final int RANGE = UPPER_RANGE - LOWER_RANGE + 1;
    // Use 64-character matrix (8X8) for Playfair cipher  
    private static final String ALPHABET64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 !\"#$%&'()*+,-./:;<=>?@[\\]^_";

    public static boolean isStringInBounds(String plainText) {
        for (int i = 0; i < plainText.length(); i++) {
            if (!(plainText.charAt(i) >= LOWER_RANGE && plainText.charAt(i) <= UPPER_RANGE)) {
                return false;
            }
        }
        return true;
    }

	/**
	 * Vigenere Cipher is a method of encrypting alphabetic text 
	 * based on the letters of a keyword. It works as below:
	 * 		Choose a keyword (e.g., KEY).
	 * 		Repeat the keyword to match the length of the plaintext.
	 * 		Each letter in the plaintext is shifted by the position of the 
	 * 		corresponding letter in the keyword (A = 0, B = 1, ..., Z = 25).
	 */   

    public static String vigenereEncryption(String plainText, String key) {
    	String encrypt = "";
    	
    	for(int i =0;i<plainText.length();i++) {
    		char keyChar = key.charAt(i % key.length());
    		
    		int position = plainText.charAt(i) - LOWER_RANGE;
    		int keyShift = keyChar - 'A';
    		
    		int newPos = (position + keyShift) % RANGE;
    		
    		char encrypted = (char) (LOWER_RANGE + newPos);
    		encrypt += encrypted;
    	}
         return encrypt;
    }

    // Vigenere Decryption
    public static String vigenereDecryption(String encryptedText, String key) {
         //to be implemented by students
    	String decrypt = "";
    	
    	for(int i =0;i<encryptedText.length();i++) {
    		char keyChar = key.charAt(i % key.length());
    		
    		int newPos = encryptedText.charAt(i) - LOWER_RANGE;
    		int keyShift = keyChar - 'A';
    		
    		int position = (newPos-keyShift+RANGE) % RANGE;
    		
    		char decrypted = (char) (LOWER_RANGE + position);
    		decrypt += decrypted;
    	}
         return decrypt;
    
    }


	/**
	 * Playfair Cipher encrypts two letters at a time instead of just one.
	 * It works as follows:
	 * A matrix (8X8 in our case) is built using a keyword
	 * Plaintext is split into letter pairs (e.g., ME ET YO UR).
	 * Encryption rules depend on the positions of the letters in the matrix:
	 *     Same row: replace each letter with the one to its right.
	 *     Same column: replace each with the one below.
	 *     Rectangle: replace each letter with the one in its own row but in the column of the other letter in the pair.
	 */    

    public static String playfairEncryption(String plainText, String key) {
         //to be implemented by students
    	String encrypt = "";
    	
    	
    	plainText = preparePlainText(plainText);
    	
    	for (int i = 0; i < plainText.length(); i += 2) {
    		
    		char[][] matrix = buildMatrix(key);
    	    char firstChar = plainText.charAt(i);
    	    char secondChar = plainText.charAt(i + 1);
    	    
    	    int[] pos1 = findPosition(matrix, firstChar);
        	int[] pos2 = findPosition(matrix, secondChar);
        	if (pos1 == null || pos2 == null) {
        	    System.out.println("Invalid character pair: '"
        	        + firstChar + "' and '" + secondChar + "'");
        	    return "";
        	}

        	int row1 = pos1[0];
        	int col1 = pos1[1];

        	int row2 = pos2[0];
        	int col2 = pos2[1];
        	
        	int newRow1 = row1;
    		int newRow2 = row2;
    		int newCol1 = col2;
    		int newCol2 = col1;	
        	
        	if(row1==row2) {
        		 newCol1 = (col1 + 1) % 8;
        		 newCol2 = (col2 + 1) % 8;
        	}else if (col1==col2) {
        		 newRow1 = (row1 + 1) % 8;
        		 newRow2 = (row2 + 1) % 8;
        	}
        	
        	encrypt+=matrix[newRow1][newCol1];
        	encrypt += matrix[newRow2][newCol2];
        	
    	}
    	
    	return encrypt;
    	
    	
    }

    // Vigenere Decryption
    public static String playfairDecryption(String encryptedText, String key) {
    	String decrypt = "";
        char[][] matrix = buildMatrix(key);

        for (int i = 0; i < encryptedText.length(); i += 2) {
            char firstChar = encryptedText.charAt(i);
            char secondChar = encryptedText.charAt(i + 1);

            int[] pos1 = findPosition(matrix, firstChar);
            int[] pos2 = findPosition(matrix, secondChar);

            int row1 = pos1[0];
            int col1 = pos1[1];
            int row2 = pos2[0];
            int col2 = pos2[1];

            int newRow1 = row1;
            int newCol1 = col2;
            int newRow2 = row2;
            int newCol2 = col1;

            if (row1 == row2) {
                // Same row: move left
                newCol1 = (col1 - 1 + 8) % 8;
                newCol2 = (col2 - 1 + 8) % 8;
            } else if (col1 == col2) {
                // Same column: move up
                newRow1 = (row1 - 1 + 8) % 8;
                newRow2 = (row2 - 1 + 8) % 8;
            }
            // Otherwise, keep the rows and swap the columns.

            decrypt += matrix[newRow1][newCol1];
            decrypt += matrix[newRow2][newCol2];
        }

        decrypt = decrypt.replace("X", "");
        return decrypt;
    }

    /**
     * Caesar Cipher is a simple substitution cipher that replaces each letter in a message 
     * with a letter some fixed number of positions down the alphabet. 
     * For example, with a shift of 3, 'A' would become 'D', 'B' would become 'E', and so on.
     */    
 
    public static String caesarEncryption(String plainText, int key) {
	//to be implemented by students'
    	String encrypt = "";
    	
    	key = ((key % RANGE) + RANGE) % RANGE;
    	
    	for(int i =0; i< plainText.length();i++) {
    		int position = plainText.charAt(i) - LOWER_RANGE;
    		int newPos = (position+key) % 64;
    		
    		char encrypted = (char) (LOWER_RANGE+newPos);
    		
    		encrypt+=encrypted;
    		
    	}
    	
    	return encrypt;
    }

    // Caesar Decryption
    public static String caesarDecryption(String encryptedText, int key) {
	//to be implemented by students
    	
    	String decrypt = "";
    	
    	key = ((key % RANGE) + RANGE) % RANGE;
    	
    	for(int i = 0; i<encryptedText.length();i++) {
    		int newPos = encryptedText.charAt(i)-LOWER_RANGE;
    		int position = (newPos-key+64)%64;
    		
    		char decrypted = (char)(LOWER_RANGE+position);
    		
    		decrypt+=decrypted;
    	}
    	
    	return decrypt;
    }   
    
    
    private static char[][] buildMatrix(String key) {
        char[][] matrix = new char[8][8];
        String sequence = "";
        
       
      
    	   for(int i = 0;i<key.length();i++) {
    		   char c = key.charAt(i);
    		   
    		   if (!sequence.contains(String.valueOf(c))) {
    		        sequence += c;
    		    }
    		   
    	   }
       
    	   for(int i = 0;i<ALPHABET64.length();i++) {
    		   char c = ALPHABET64.charAt(i);
    		   
    		   if(!sequence.contains(String.valueOf(c))) {
    			   sequence+=c;
    		   }
    		   
    	   }
    	   
    	   int index = 0;
    	   for(int i = 0;i<matrix.length;i++) {
    		  for(int j = 0; j< matrix[0].length;j++) {
    			  matrix[i][j] = sequence.charAt(index);
    			  index++;
    		  }
    	   }
        

        return matrix;
    }
    
    private static int[] findPosition(char[][] matrix, char target) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == target) {
                    return new int[] {i, j};
                }
            }
        }

        return null;
    } 
    
    private static String preparePlainText(String plainText) {
        String result = "";
        int i = 0;

        while (i < plainText.length()) {
            char first = plainText.charAt(i);
            result += first;
            i++;

            if (i < plainText.length()) {
                char second = plainText.charAt(i);

                if (first == second) {
                    result += 'X';
                } else {
                    result += second;
                    i++;
                }
            } else {
                
                result += 'X';
            }
        }

        return result;
    }

}
