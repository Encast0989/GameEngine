package club.encast.gameengine.util;

public class Version {

    public String version;

    public Version(String version) {
        this.version = version;
    }

    public String getRawVersion() {
        return version;
    }

    public String getVersion() {
        // v1_8_R3 -> 1.8.3
        return version.replaceAll("_", ".").replaceAll("R", "").replaceAll("v", "");
    }
}
