class Overflow {
  
  public static void main(String[] args) {
    int one = 1;
    print(one, "1");
    int neg_one = -1;
    print(neg_one, "-1");
    print(neg_one - 1, "-1 - 1");
    print(neg_one + 1, "-1 + 1");
    int largest_pos_int =  2147483647; // 2^31 - 1
    print(largest_pos_int, "MAX int");
    print(largest_pos_int - 1, "MAX - 1");
    print(largest_pos_int + 1, "MAX + 1");
    int smallest_neg_int = -2147483648; // -2^31
    print(smallest_neg_int, "MIN int");
    print(smallest_neg_int - 1, "MIN - 1");
    print(smallest_neg_int + 1, "MIN + 1");
    int large_int = largest_pos_int - 10;
    for (int i=0; i<20; i++) {
      print(large_int + i, "1st loop +" + i);
    }
    int small_int = - 10;
    for (int i=0; i<20; i++) {
      print(small_int + i, "2nd loop +" + i);
    }
  }
  
  private static void print(int i, String desc) {
    String s = Integer.toBinaryString(i);
    while(s.length() < 32) s = "0" + s;
    String t = "" + s.charAt(0);
    for (int j=1; j<32; j++) {
      if (j%4==0) t+=" "; // for better readability
      t += s.charAt(j);
    }
    System.out.format("%14s: %s\n", desc, t);
    
  }
  
}