class test4{
    public static void main(String[] args) {
        String str = "0.1#";
        str = str.substring(0,str.length()-1);
        Double temp = Double.valueOf(str);

        Double one = 1D;
        Double two = 0.2D;
        System.out.println(one-temp);
    }
}