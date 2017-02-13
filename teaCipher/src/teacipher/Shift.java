class Shift {
  
  public static void main(String[] args) {
    int one = 1;
    print(one, "1");
    print(one << 4, "1 << 4");
    print(one >> 4, "1 >> 4");
    int neg_one = -1;
    print(neg_one, "-1");
    print(neg_one << 4, "-1 << 4");
    print(neg_one >> 4, "-1 >> 4");
    int largest_pos_int =  2147483647; // 2^31 - 1
    print(largest_pos_int, "MAX int");
    print(largest_pos_int << 4, "MAX << 4");
    print(largest_pos_int >> 4, "MAX >> 4");
    int smallest_neg_int = -2147483648; // -2^31
    print(smallest_neg_int, "MIN int");
    print(smallest_neg_int << 4, "MIN << 4");
    print(smallest_neg_int >> 4, "MIN >> 4");
  }
  
  private static void print(int i, String desc) {
    String s = Integer.toBinaryString(i);
    while(s.length() < 32) s = "0" + s;
    String t = "" + s.charAt(0);
    for (int j=1; j<32; j++) {
      if (j%4==0) t+=" "; // for better readability
      t += s.charAt(j);
    }
    System.out.format("%14s: %s = %d\n", desc, t, i);
    
  }
  
}