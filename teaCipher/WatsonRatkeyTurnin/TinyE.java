package edu.cedarville.cs.crypto;


public class TinyE {
    
        private static int delta = 0x9e3779b9;
	
	public static enum Mode { ECB, CBC, CTR };
        
        
	public Integer[] encrypt(Integer[] plaintext, Integer[] key, 
                                    Mode mode, Integer[] iv) {                        

            //define variables
            int left, right, sum;
            Integer[] ciphertext = new Integer[plaintext.length];                        
            
            if (mode == Mode.ECB) {
                // Electronic Codebook mode                
                
                for (int j = 0; j < plaintext.length; j += 2) {                      
                    sum = 0;                    
                    left = plaintext[j];
                    right = plaintext[j+1];  

                    //run encryption
                    for (int i = 0; i < 32; i++) {
                        sum = sum + delta;
                        left  = left  + (((right << 4) + key[0]) 
                                ^ (right + sum) 
                                ^ ((right >> 5) + key[1]));
                        right = right + (((left  << 4) + key[2]) 
                                ^ (left  + sum) 
                                ^ ((left  >> 5) + key[3]));
                    }

                    ciphertext[j]   = left;
                    ciphertext[j+1] = right;
                }
            }        
            else if (mode == Mode.CBC) {
                // Cipher Block Chaining mode

                //run encryption with Initialization Vector
                sum = 0;
                left  = iv[0] ^ plaintext[0];
                right = iv[1] ^ plaintext[1];
                
                for ( int i = 0; i < 32; i++ ) {
                    sum = sum + delta;
                    left  = left  + (((right << 4) + key[0]) 
                            ^ (right + sum) 
                            ^ ((right >> 5) + key[1]));
                    right = right + (((left  << 4) + key[2]) 
                            ^ (left  + sum) 
                            ^ ((left  >> 5) + key[3]));
                }
                
                ciphertext[0] = left;
                ciphertext[1] = right;
                
                
                //run encryption for subsequent blocks
                for (int j = 2; j < plaintext.length; j += 2) {
                    sum = 0;
                    left  = ciphertext[j-2] ^ plaintext[j];
                    right = ciphertext[j-1] ^ plaintext[j+1];
                    
                    for ( int i = 0; i < 32; i++ ) {
                        sum = sum + delta;
                        left  = left  + (((right << 4) + key[0]) 
                                ^ (right + sum) 
                                ^ ((right >> 5) + key[1]));
                        right = right + (((left  << 4) + key[2]) 
                                ^ (left  + sum) 
                                ^ ((left  >> 5) + key[3]));
                    }
                    
                    ciphertext[j] = left;
                    ciphertext[j+1] = right;
                }

            }
            else {
                // Counter mode                

                //combine iv array into single long integer value
                long ivMSB = (long) iv[0];
                long ivLSB = (long) iv[1];
                long iv64Orig = (( ivMSB << 32 ) & 0xffffffff) | ivLSB;                
                
                for (int j = 0; j < plaintext.length; j += 2) {                    
                    sum = 0;
                    int i = j/2;                    
                    long iv64 = iv64Orig + i; //increment IV
                                        
                    left = (int) (iv64 >> 32);
                    right = (int) (iv64 & 0x00000000ffffffff);
                    
                    //run encryption
                    for ( int k = 0; k < 32; k++ ) {
                        sum = sum + delta;
                        left  = left  + (((right << 4) + key[0]) 
                                ^ (right + sum) 
                                ^ ((right >> 5) + key[1]));
                        right = right + (((left  << 4) + key[2]) 
                                ^ (left  + sum) 
                                ^ ((left  >> 5) + key[3]));
                    }
                    
                    ciphertext[j]   = plaintext[j]   ^ left;
                    ciphertext[j+1] = plaintext[j+1] ^ right;                                       
                }                                                                                                                    
            }                  
                        
            return ciphertext;                
	}

	public Integer[] decrypt(Integer[] ciphertext, Integer[] key, 
                                    Mode mode, Integer[] iv) {
            
            //define variables
            int left, right, sum;
            Integer[] plaintext = new Integer[ciphertext.length];

            if (mode == Mode.ECB) {
                // Electronic Codebook mode                               
                
                for (int j = 0; j < ciphertext.length; j += 2) {                                            
                    sum = delta << 5;
                    left  = ciphertext[j];
                    right = ciphertext[j+1];
                    
                    //run decryption
                    for ( int i = 0; i < 32; i++ ) {
                        right = right - ((( left << 4) + key[2]) 
                                ^ ( left + sum) 
                                ^ (( left >> 5) + key[3]));
                        left  =  left - (((right << 4) + key[0]) 
                                ^ (right + sum) 
                                ^ ((right >> 5) + key[1]));
                        sum = sum - delta;
                    }
                    
                    plaintext[j]   = left;
                    plaintext[j+1] = right;
                }
            }
            else if (mode == Mode.CBC) {
                // Cipher Block Chaining mode
                
                //run decryption with Initialization Vector
                sum = delta << 5;                
                left  = ciphertext[0];
                right = ciphertext[1];
                                
                for ( int i = 0; i < 32; i++ ) {
                    right = right - ((( left << 4) + key[2]) 
                            ^ ( left + sum) 
                            ^ (( left >> 5) + key[3]));
                    left  =  left - (((right << 4) + key[0]) 
                            ^ (right + sum) 
                            ^ ((right >> 5) + key[1]));
                    sum = sum - delta;
                }
                
                plaintext[0] =  left ^ iv[0];
                plaintext[1] = right ^ iv[1];                                                
                
                
                //run decryption for subsequent blocks
                for (int j = 2; j < ciphertext.length; j += 2) {
                    sum = delta << 5;
                    left  = ciphertext[j];
                    right = ciphertext[j+1];
                    
                    for ( int i = 0; i < 32; i++ ) {
                        right = right - ((( left << 4) + key[2]) 
                                ^ ( left + sum) 
                                ^ (( left >> 5) + key[3]));
                        left  =  left - (((right << 4) + key[0]) 
                                ^ (right + sum) 
                                ^ ((right >> 5) + key[1]));
                        sum = sum - delta;
                    }
                    
                    plaintext[j]   = left  ^ ciphertext[j-2];
                    plaintext[j+1] = right ^ ciphertext[j-1];
                }

            }
            else {
                // Counter mode                                               
                
                //combine iv array into single long integer value
                long ivMSB = (long) iv[0];
                long ivLSB = (long) iv[1];
                long iv64Orig = (( ivMSB << 32 ) & 0xffffffff) | ivLSB;                
                
                for (int j = 0; j < ciphertext.length; j += 2) {                    
                    sum = 0;
                    int i = j/2;                    
                    long iv64 = iv64Orig + i; //increment IV
                                        
                    left = (int) (iv64 >> 32);
                    right = (int) (iv64 & 0x00000000ffffffff);
                    
                    //run decryption
                    for ( int k = 0; k < 32; k++ ) {
                        sum = sum + delta;
                        left  = left  + (((right << 4) + key[0]) 
                                ^ (right + sum) 
                                ^ ((right >> 5) + key[1]));
                        right = right + (((left  << 4) + key[2]) 
                                ^ (left  + sum) 
                                ^ ((left  >> 5) + key[3]));
                    }
                    
                    plaintext[j]   = ciphertext[j]   ^ left;
                    plaintext[j+1] = ciphertext[j+1] ^ right;                                       
                }
            }
        
            return plaintext;
        }
	
}
