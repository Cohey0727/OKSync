package common.ini.preference;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import common.system.SystemUtil;
import common.system.SystemUtil.ResourceType;
import javafx.scene.image.Image;

public class Preference {
    private String id;
    private String label;
    private String xml;
    private String controller;
    private Image image;
    private String description;

    public Preference(ResultSet rs) throws SQLException, IOException {
        id = rs.getString("id");
        label = rs.getString("label");
        xml = rs.getString("xml");
        controller = rs.getString("controller");
        image = new Image(SystemUtil.getResourceURL(rs.getString("image"), ResourceType.IMAGE).openStream());
        description = rs.getString("description");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image img) {
        this.image = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return label;
    }

}
