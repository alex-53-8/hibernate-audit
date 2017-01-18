package info.biosfood.hibernate.test;

public class Formatting {

    public static String formatTestMethod(String methodName) {
        return format(methodName, "-");
    }

    public static String formatTestClass(String className) {
        return format(className, "*");
    }

    public static String format(String text, String symbol) {
        StringBuffer b = new StringBuffer();

        b.append("\n\n");
        b.append(new String(new char[text.length() + 4]).replace("\0", symbol)).append("\n");
        b.append(symbol).append(" ").append(text).append(" ").append(symbol).append("\n");
        b.append(new String(new char[text.length() + 4]).replace("\0", symbol)).append("\n");

        return b.toString();
    }

}
