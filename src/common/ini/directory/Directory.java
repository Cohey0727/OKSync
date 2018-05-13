package common.ini.directory;

public class Directory {

    private final String id;
    private String path;

    public Directory(String _id, String _path) {
        id = _id;
        path = _path;
    }

    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public String getId() {
        return id;
    }

}
