package edu.cedarville.cs.crypto;

// FIXME: look at formatting to make sure it looks good

public class TinyE {
    
        private static int delta = 0x9e3779b9;
	
	public static enum Mode { ECB, CBC, CTR };
	
        
	public Integer[] encrypt(Integer[] plaintext, Integer[] key, Mode mode, Integer[] iv) {                        
            
            //define variables
            int left, right, sum = 0;
            Integer[] ciphertext = null;
            
            //FIXME: initialize ciphertext to blanks the same size as the plaintext.
            
            for (int j = 0; j < plaintext.length; j++) {
                        
                // FIXME: get first block (64 bits)
                left = plaintext[j];
                right = plaintext[j+1];                                                                
                
                if (mode == Mode.ECB) {
                    for (int i = 0; i < 32; i++) {
                        sum = sum + delta;
                        left  = left  + (((right << 4) + key[0]) ^ (right + sum) ^ ((right >> 5) + key[1]));
                        right = right + (((left  << 4) + key[2]) ^ (left  + sum) ^ ((left  >> 5) + key[3]));
                    }
                    
                    ciphertext[j]   = left;
                    ciphertext[j+1] = right;
                }                       
            }                       
                        
            return null;                
	}

	public Integer[] decrypt(Integer[] ciphertext, Integer[] key, Mode mode, Integer[] iv) {
            
            //define variables
            int left, right, sum;
            Integer[] plaintext = null;
            for (int j = 0; j < ciphertext.length; j++) {
                        
                // FIXME: get first block (64 bits)
                left = ciphertext[j];
                right = ciphertext[j+1];                                                                
                
                // FIXME: for now, assuming that ciphertext is exatcly 64 bits (1 block, two 32-bit halves)
                //int left = ciphertext[0];
                //int right = ciphertext[1];
                if (mode == Mode.ECB) {
                    // Electronic Codebook mode
                    // 
                    sum = delta << 5;
                    for ( int i = 0; i < 32; i++ ) {
                        right = right - ((( left << 4) + key[2]) ^ ( left + sum) ^ (( left >> 5) + key[3]));
                        left  =  left - (((right << 4) + key[0]) ^ (right + sum) ^ ((right >> 5) + key[1]));
                        sum = sum - delta;
                    }
                    plaintext[j] = left;
                    plaintext[j + 1] = right;
                }
                else if (mode == Mode.CBC) {
                    // Cipher Block Chaining mode
                    
                }
                else {
                    // CTR mode
                }
            }
            return plaintext;
	}
	
}
