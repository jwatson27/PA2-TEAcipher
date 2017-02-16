package edu.cedarville.cs.crypto;

// FIXME: look at formatting to make sure it looks good


public class TinyE {
    
        private static int delta = 0x9e3779b9;
	
	public static enum Mode { ECB, CBC, CTR };
        
	public Integer[] encrypt(Integer[] plaintext, Integer[] key, Mode mode, Integer[] iv) {                        
            
            //define variables
            int left, right, sum = 0;
            Integer[] ciphertext = new Integer[plaintext.length];
            
            //FIXME: initialize ciphertext to blanks the same size as the plaintext.
            
            if (mode == Mode.ECB) {
                // Electronic Codebook mode

                System.out.println("executing encrypt ecb in tinye.java");
                
                for (int j = 0; j < plaintext.length; j += 2) {  
                    // FIXME: get first block (64 bits)
                    left = plaintext[j];
                    right = plaintext[j+1];  

                    for (int i = 0; i < 32; i++) {
                        sum = sum + delta;
                        left  = left  + (((right << 4) + key[0]) ^ (right + sum) ^ ((right >> 5) + key[1]));
                        right = right + (((left  << 4) + key[2]) ^ (left  + sum) ^ ((left  >> 5) + key[3]));
                    }

                    ciphertext[j]   = left;
                    ciphertext[j+1] = right;
                }
            }        
            else if (mode == Mode.CBC) {
                // Cipher Block Chaining mode

                left  = iv[0] ^ plaintext[0];
                right = iv[1] ^ plaintext[1];
                sum = sum + delta;
                left  = left  + (((right << 4) + key[0]) ^ (right + sum) ^ ((right >> 5) + key[1]));
                right = right + (((left  << 4) + key[2]) ^ (left  + sum) ^ ((left  >> 5) + key[3]));
                ciphertext[0] = left;
                ciphertext[1] = right;
                
                for (int j = 2; j < plaintext.length; j += 2) {
                    left  = ciphertext[j-2] ^ plaintext[j];
                    right = ciphertext[j-1] ^ plaintext[j+1];
                    sum = sum + delta;
                    left  = left  + (((right << 4) + key[0]) ^ (right + sum) ^ ((right >> 5) + key[1]));
                    right = right + (((left  << 4) + key[2]) ^ (left  + sum) ^ ((left  >> 5) + key[3]));
                    ciphertext[j] = left;
                    ciphertext[j+1] = right;
                }

            }
            else {
                // Counter mode
                // Ci = Pi ^ E(IV + i, K)
                
                long iv64 = (iv[0] << 32) | (iv[1]);
                
                for (int j = 0; j < plaintext.length; j += 2) {
                    int i = j/2;
                    iv64 += i;
                    
                    left = (int) iv64 >> 32;
                    right = (int) iv64;                                        
                    
                    for ( int k = 0; k < 32; k++ ) {
                        sum = sum + delta;
                        left  = left  + (((right << 4) + key[0]) ^ (right + sum) ^ ((right >> 5) + key[1]));
                        right = right + (((left  << 4) + key[2]) ^ (left  + sum) ^ ((left  >> 5) + key[3]));
                    }
                    
                    ciphertext[j]   = plaintext[j]   ^ left;
                    ciphertext[j+1] = plaintext[j+1] ^ right;
                }
            }                  
                        
            return ciphertext;                
	}

	public Integer[] decrypt(Integer[] ciphertext, Integer[] key, Mode mode, Integer[] iv) {
            
            //define variables
            int left, right, sum;
            Integer[] plaintext = new Integer[ciphertext.length];

            // FIXME: for now, assuming that ciphertext is exatcly 64 bits (1 block, two 32-bit halves)
            //int left = ciphertext[0];
            //int right = ciphertext[1];
            if (mode == Mode.ECB) {
                // Electronic Codebook mode
                
                System.out.println("executing decrypt ecb in tinye.java");
                
                for (int j = 0; j < ciphertext.length; j += 2) {                    
                    // Get first block (64 bits)    
                    left = ciphertext[j];
                    right = ciphertext[j+1];
                    sum = delta << 5;
                    for ( int i = 0; i < 32; i++ ) {
                        right = right - ((( left << 4) + key[2]) ^ ( left + sum) ^ (( left >> 5) + key[3]));
                        left  =  left - (((right << 4) + key[0]) ^ (right + sum) ^ ((right >> 5) + key[1]));
                        sum = sum - delta;
                    }
                    plaintext[j] = left;
                    plaintext[j + 1] = right;
                }
            }
            else if (mode == Mode.CBC) {
                // Cipher Block Chaining mode
                
                sum = delta << 5;               
                
                left  = ciphertext[0];
                right = ciphertext[1];
                for ( int i = 0; i < 32; i++ ) {
                    right = right - ((( left << 4) + key[2]) ^ ( left + sum) ^ (( left >> 5) + key[3]));
                    left  =  left - (((right << 4) + key[0]) ^ (right + sum) ^ ((right >> 5) + key[1]));
                    sum = sum - delta;
                }
                plaintext[0] =  left ^ iv[0];
                plaintext[1] = right ^ iv[1];                                                
                
                for (int j = 2; j < ciphertext.length; j += 2) {
                    sum = delta << 5;
                    left  = ciphertext[j];
                    right = ciphertext[j+1];
                    for ( int i = 0; i < 32; i++ ) {
                        right = right - ((( left << 4) + key[2]) ^ ( left + sum) ^ (( left >> 5) + key[3]));
                        left  =  left - (((right << 4) + key[0]) ^ (right + sum) ^ ((right >> 5) + key[1]));
                        sum = sum - delta;
                    }
                    plaintext[j]   = left  ^ ciphertext[j-2];
                    plaintext[j+1] = right ^ ciphertext[j-1];
                }

            }
            else {
                // CTR mode
                // Pi = Ci ^ E(IV + i, K)
                
                sum = 0;
                
                //long iv64 = (long) ((( (long) iv[0] << 32) & 0xffffffff00000000) |  (iv[1]));
                long ivMSB = (long) iv[0];
                long ivLSB = (long) iv[1];
                long iv64Orig = (( ivMSB << 32 ) & 0xffffffff) | ivLSB;
//                    System.out.println("iv[0]: "+iv[0]);
//                    System.out.println("iv[0] << 32: "+(iv[0]<<32));
//                    System.out.println("iv[1]: "+iv[1]);
//                    
//                    left = (int) (iv64 >> 32);
//                    right = (int) (iv64 & 0x0000ffff);   
//                    
//                    System.out.println("iv64: "+iv64);
//                    System.out.println("left: "+left);
//                    System.out.println("right: "+right);
//                    
//                    for ( int k = 0; k < 32; k++ ) {
//                        sum = sum + delta;
//                        left  = left  + (((right << 4) + key[0]) ^ (right + sum) ^ ((right >> 5) + key[1]));
//                        right = right + (((left  << 4) + key[2]) ^ (left  + sum) ^ ((left  >> 5) + key[3]));
//                    }
//                    
//                    plaintext[0]   = ciphertext[0]   ^ left;
//                    plaintext[1] = ciphertext[1] ^ right;
                
//                    left = 0xffffffff;
//                    right = 0xfffffff0; 
//
//                    for ( int k = 0; k < 32; k++ ) {
//                        sum = sum + delta;
//                        left  = left  + (((right << 4) + key[0]) ^ (right + sum) ^ ((right >> 5) + key[1]));
//                        right = right + (((left  << 4) + key[2]) ^ (left  + sum) ^ ((left  >> 5) + key[3]));
//                    }
//
//                    plaintext[0] = ciphertext[0] ^ left;
//                    plaintext[1] = ciphertext[1] ^ right;
//
//                
//                    left = 0xffffffff;
//                    right = 0xfffffff1; 
//
//                    sum = 0;
//                    
//                    for ( int k = 0; k < 32; k++ ) {
//                        sum = sum + delta;
//                        left  = left  + (((right << 4) + key[0]) ^ (right + sum) ^ ((right >> 5) + key[1]));
//                        right = right + (((left  << 4) + key[2]) ^ (left  + sum) ^ ((left  >> 5) + key[3]));
//                    }
//
//                    plaintext[2] = ciphertext[2] ^ left;
//                    plaintext[3] = ciphertext[3] ^ right;
                
                
                for (int j = 0; j < ciphertext.length; j += 2) {                    
                    sum = 0;
                    int i = j/2;                    
                    long iv64 = iv64Orig + i;
                                        
                    left = (int) (iv64 >> 32);
                    right = (int) (iv64 & 0x00000000ffffffff);                       

                    if (i < 40) {                                                
                        System.out.println("i: "+i);
                        System.out.println("iv64: "+(iv64));
                        //System.out.println("iv64 >> 32: "+(iv64>>32));
                        //System.out.println("iv64 & 0x00000000ffffffff: "+(iv64 & 0x00000000ffffffff));                        
                        System.out.println("left: "+left);
                        System.out.println("right: "+right);
                    }
                    
                    for ( int k = 0; k < 32; k++ ) {
                        sum = sum + delta;
                        left  = left  + (((right << 4) + key[0]) ^ (right + sum) ^ ((right >> 5) + key[1]));
                        right = right + (((left  << 4) + key[2]) ^ (left  + sum) ^ ((left  >> 5) + key[3]));
                    }
                    
                    plaintext[j]   = ciphertext[j]   ^ left;
                    plaintext[j+1] = ciphertext[j+1] ^ right;                                       
                }
            }
        
            return plaintext;
        }
	
}
