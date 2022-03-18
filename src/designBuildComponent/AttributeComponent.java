package designBuildComponent;

import java.io.BufferedWriter;
import java.io.IOException;

public class AttributeComponent {
    private String attrName;
    private DataType dataType;
    private AccessType accessType;
    private String componentType;

    public AttributeComponent(String attrName, DataType dataType, AccessType accessType) {
        this.attrName = attrName;
        this.dataType = dataType;
        this.accessType = accessType;
        this.componentType = null;
    }

    public AttributeComponent(String attrName, String componentType) {
        this.attrName = attrName;
        this.dataType = null;
        this.accessType = AccessType.PRIVATE;
        this.componentType = componentType;
    }

    public void generateCode4Attr(BufferedWriter bw) {
        try {
            bw.write("    " + accessType.toString().toLowerCase()
                    + " " + dataType.toString().toLowerCase()
                    + " " + attrName + ";\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateCode4CompoAttr(BufferedWriter bw) {
        try {
            bw.write("    " + accessType.toString().toLowerCase()
                    + " " + componentType
                    + " " + attrName + ";\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
