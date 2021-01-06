import java.util.StringJoiner;

public class tests {

    public static void main(String[] args) throws ClassNotFoundException {

        String uuid = "a3fed1a706ba4243986fecc7b0cdc1f8";
        uuid = uuid.replaceFirst("(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5");
        System.out.println(uuid);

//        System.out.println(enumToString("DIAMOND_CHESTPLATE"));

    }

    public static String enumToString(String enumName) {
        StringJoiner sb = new StringJoiner(" ");

        for(String s : enumName.split("_"))
            sb.add(s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase());

        return sb.toString();
    }
}
