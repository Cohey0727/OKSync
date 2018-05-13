package common.ini.version;

public class Version {

    public final int major;
    public final int minor;
    public final int revision;

    public Version(int major, int minor, int revision) {
        this.major = major;
        this.minor = minor;
        this.revision = revision;
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public int getRevision() {
        return revision;
    }

    @Override
    public String toString() {
        return major + ". " + minor + ". " + revision;
    }
}
