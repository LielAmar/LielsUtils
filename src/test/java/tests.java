import com.packetmanager.lielamar.PacketManager;

import java.lang.reflect.InvocationTargetException;
import java.util.StringJoiner;

public class tests {

    public static void main(String[] args) throws ClassNotFoundException {

        System.out.println(enumToString("DIAMOND_CHESTPLATE"));

    }

    public static String enumToString(String enumName) {
        StringJoiner sb = new StringJoiner(" ");

        for(String s : enumName.split("_"))
            sb.add(s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase());

        return sb.toString();
    }
}
