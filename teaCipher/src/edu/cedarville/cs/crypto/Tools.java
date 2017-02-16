package edu.cedarville.cs.crypto;

public class Tools {

    public static Integer[] convertFromBytesToInts(byte[] bs) { 

        //number of padding bytes for 64-bit blocks
        int padNum = (8 - (bs.length % 8)) % 8;               
        //create new byte array
        byte[] paddedBS = new byte[bs.length + padNum]; 

        //initialize bytes
        for(int i = 0; i < bs.length; i++) {
            paddedBS[i] = bs[i];
        }
        //initialize padded bytes
        for(int i = bs.length; i < paddedBS.length; i++) {
            paddedBS[i] = 0x20;
        }            

        //create integer array to hold byte values
        Integer[] intArray = new Integer[paddedBS.length/4];            

        //convert bytes to ints
        int convInt;
        for (int i = 0; i < intArray.length; i++) {
            //convert byte to string
            String[] bts = new String[4];

            for (int j = 0; j < 4; j++) {
                bts[j] = Integer.toHexString(paddedBS[i*4+j]);

                if (bts[j].length() < 2) {
                    bts[j] = "0".concat(bts[j]);
                }
                else if (bts[j].length() > 2) {
                    bts[j] = bts[j].substring(6,8);
                }                  
            }

            //convert 4 bytes to integer
            int i1, i2;                
            String sub1 = bts[0] + bts[1]; 
            String sub2 = bts[2] + bts[3];
            i1 = Integer.parseInt(sub1, 16);
            i2 = Integer.parseInt(sub2, 16);
            convInt = (i1 << 16) | i2;

            //append an integer to array 
            intArray[i] = convInt;
        }     

        return intArray;
    }



    public static Integer[] convertFromHexStringToInts(String s) {

        // add padding to ensure that the length of the hex String is a
        // multiple of 8. 
        // (because each "chunk" of 8 characters will become an Integer)
        int padNum = (8 - (s.length() % 8)) % 8;
        for (int i = 0; i < padNum; i++) {
            s += "0";
        }

        // Declare our array of Integers to be the proper size
        // (note that every 8 hex characters make one Integer)
        Integer[] ints = new Integer[ s.length() / 8 ];

        // Take 8-character substrings of the hex String, parse each as
        // a base-16 number and convert it to an Integer, then store that
        // Integer into the array
        int i1, i2;

        for(int i = 0; i < ints.length; i++) {
            String sub = s.substring(8*i, 8*i+8);
            String sub1 = sub.substring(0, 4);
            String sub2 = sub.substring(4, 8);
            System.out.println("for i = "+i+". "+"substring: "+sub1+sub2);
            i1 = Integer.parseInt(sub1, 16);
            i2 = Integer.parseInt(sub2, 16);
            ints[i] = (i1 << 16) | i2;
        }

        // Return the array of Integers we produced
        return ints;

    }

    public static byte[] convertFromIntsToBytes(Integer[] ints) {
        System.out.println("converting ints to bytes tools.java");

        //create byte array
        byte[] byteArray = new byte[ints.length*4]; 

        Integer tempInt;            

        //unravel integers
        for (int i = 0; i < ints.length; i++) {                
            for (int j = 0; j < 4; j++) {
                tempInt = ints[i] >> (3-j)*8;
                byteArray[i*4+j] = tempInt.byteValue();
            }
        }

        return byteArray;
    }

    public static String convertFromIntsToHexString(Integer[] ints) {
        // The String we will build from Integers and eventually return at
        // the end of the method.
        String hexString = "";
        // Each Integer gets converted into a String and first stored here.
        String tempStr;

        // Iterate through the entire array of Integers that 
        // was passed to us.
        for(int i = 0; i < ints.length; i++) {
            // Turn this Integer into a String of hex characters.
            tempStr = Integer.toHexString( ints[i] );
            // Since the hex String in tempStr does not have any leading
            // zeroes, we may have to add some in order to ensure that
            // each Integer gets represented as 8 hex characters.
            for (int j = 0; j < 8 - tempStr.length(); j++) {
                hexString += "0";
            }
            // Append to hexString.
            hexString += tempStr;
        }

        //Return the String of hex characters we produced.
        return hexString;
    }
}
