package club.encast.gameengine.util;

import org.bukkit.Bukkit;

public class Util {

    public static String VERSION = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

    public static Version getServerVersion() {
        String packageName = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        return new Version(packageName);
    }

    public static String[] toCharArrayAsString(String string) {
        String[] array = new String[string.toCharArray().length];
        for(int i = 0; i < string.toCharArray().length; i++) {
            array[i] = String.valueOf(string.toCharArray()[i]);
        }
        return array;
    }
}
