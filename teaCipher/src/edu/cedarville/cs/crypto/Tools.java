package edu.cedarville.cs.crypto;

public class Tools {
	
        //Josiah work on bytes to ints and vice versa and test
        //Ben work on hex string to ints and vice versa and test
    
        //Josiah study CTR mode
        //Ben study CBC mode
    
	public static Integer[] convertFromBytesToInts(byte[] bs) {                                   
            int padNum = bs.length % 8; //number of padding bytes for 64-bit blocks                                               
            byte[] paddedBS = new byte[bs.length + padNum]; //create new byte array
            
            //initialize padded bytes
            for(int i = bs.length; i < paddedBS.length; i++) {
                paddedBS[i] = 0x00;
            }            
            
            //create integer array to hold byte values
            Integer[] intArray = new Integer[paddedBS.length/4];            
            
            //convert bytes to ints
            int convInt;
            for (int i = 0; i < paddedBS.length; i++) {
                //convert 4 bytes to integer
                convInt = 0;
                //form integer using bytes from left to right
                for (int j = 0; j < 4; j++) {
                    convInt = convInt | (paddedBS[i*4+j] << (3-j)*8);                    
                }
                //append an integer to array
                intArray[i] = convInt;
            }     
                                       
            return intArray;
	}
	
	public static Integer[] convertFromHexStringToInts(String s) {
            //read in 8 hex char
                //if less than 8 add padding
            //run through for loop to convert each char to binary
            //concatenate together
            //add resulting integer to array
            
            return null;
	}
	
	public static byte[] convertFromIntsToBytes(Integer[] ints) {
            byte[] byteArray = new byte[ints.length*4]; //create byte array
            
            Integer tempInt;            
            
            //unravel integers
            for (int i = 0; i < ints.length; i++) {                
                for (int j = 0; j < 3; j++) {
                    tempInt = ints[i] >> (3-j)*8;
                    byteArray[i*4+j] = tempInt.byteValue();
                }
            }
            
            return byteArray;
	}
	
	public static String convertFromIntsToHexString(Integer[] ints) {
		return null;
	}
	
}
