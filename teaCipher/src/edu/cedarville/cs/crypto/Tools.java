package edu.cedarville.cs.crypto;

public class Tools {
	
        //Josiah work on bytes to ints and vice versa and test
        //Ben work on hex string to ints and vice versa and test
    
        //Josiah study CTR mode
        //Ben study CBC mode
    
	public static Integer[] convertFromBytesToInts(byte[] bs) {           
            // FIXME: Maybe I'm thinking about this wrong, but I'm making a note so
            // we'll remember to talk about it. Suppose the array of bytes (bs) that
            // is given as an argument is 11 bytes long. Then
            // bs.length % 8
            // will equal 3. The new padded array is then initialized to a length
            // of 14 bytes, or 112 bits. That doesn't provide us with 64-bit blocks.
            // Maybe this line should be:
            // int padNum = 8 - (bs.length % 8)
            //
            // In which case, given the example I wrote above, we would end up
            // with a new padded array of length 16 bytes, or 128 bits.
            //
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
            
            // add padding to ensure that the length of the hex String is a
            // multiple of 8. (because each "chunk" of 8 characters will become
            // an Integer)
            int padNum = 8 - (s.length() % 8);
            for (int i = 0; i < padNum; i++) {
                s += " ";
            }
            
            // Declare our array of Integers to be the proper size
            // (note that every 8 hex characters make one Integer)
            Integer[] ints = new Integer[ s.length() / 8 ];
            
            // Take 8-character substrings of the hex String, parse each as
            // a base-16 number and convert it to an Integer, then store that
            // Integer into the array
            for(int i = 0; i < ints.length; i++) {
                ints[i] = Integer.parseInt( s.substring(i,i+7) , 16 );
            }
            
            // Return the array of Integers we produced
            return ints;
            
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
            String hexString = "";
            for (int i = 0; i < ints.length; i++) {
                hexString += Integer.toHexString(ints[i]);
            }
            return hexString;
	}
	
        // main method included only for easy testing of Tools.java helper methods.
        // This can be deleted once confident that all 4 conversion methods
        // are working correctly.
        public static void main(String[] args) {
            // TODO code application logic here
        }
    
}
