package designBuildComponent;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

enum DataType
{
    INT, SHORT, DOUBLE, FLOAT, CHAR, BOOL, STRING, VOID, ARRAY;
}

enum AccessType
{
    PRIVATE, PUBLIC, PROTECTED, DEFAULT;
}

enum ReturnType //Use DataType
{
    VOID, INT, SHORT, DOUBLE, FLOAT, CHAR, BOOL, STRING, ARRAY;
}

// add a hashmap to store the realization of the services

public class ClassComponent {
    private String className;
    private AccessType accessType;
    private HashMap<String, AttributeComponent> attributes;
    private HashMap<String, MethodComponent> methods;
    private boolean isAtomic;
    private String component1;
    private String component2;

    public ClassComponent(String className, boolean isAtomic) {
        this.className = className;
        this.accessType = AccessType.PUBLIC;
        this.attributes = new HashMap<>();
        this.methods = new HashMap<>();
        this.isAtomic = isAtomic;
        this.component1 = null;
        this.component2 = null;
    }

    public void addAttribute(String attrName, DataType dataType, AccessType accessType,
                             boolean getMethod, boolean setMethod) {
        AttributeComponent attr = new AttributeComponent(attrName, dataType, accessType);
        attributes.put(attrName, attr);
        if (getMethod) {
            this.addMethod("get"+attrName, AccessType.PUBLIC, null, dataType); /*添加get方法*/
        }
        if (setMethod) {
            HashMap<String, DataType> paras = new HashMap<>();
            paras.put(attrName, dataType);
            this.addMethod("set"+attrName, AccessType.PUBLIC,
                    paras, DataType.VOID); /*添加set方法*/
        }
    }

    public void addMethod(String methodName, AccessType accessType,
                          HashMap<String, DataType> paras, DataType returnType) {
        MethodComponent meth = new MethodComponent(methodName, accessType, paras, returnType);
        methods.put(methodName, meth);
    }

    public void modifyAttribute(String attrName, DataType dataType, AccessType accessType,
                                boolean getMethod, boolean setMethod) {
        attributes.remove(attrName);
        this.addAttribute(attrName, dataType, accessType, getMethod, setMethod);
    }

    public void modifyMethod(String methodName, AccessType accessType,
                             HashMap<String, DataType> paras, DataType returnType) {
        methods.remove(methodName);
        this.addMethod(methodName, accessType, paras, returnType);
    }

    public void deleteAttribute(String attrName) {
        attributes.remove(attrName);
    }

    public void deleteMethod(String methName) {
        methods.remove(methName);
    }

    public void generateCode4Class() {
        //step1: write frame for Class
        //File f = new File("componentFile/" + this.className + ".java");
        if (this.isAtomic) {
            try {
                //OutputStream fOut = new FileOutputStream(f);
                FileWriter fw = new FileWriter("repository/" + this.className + ".java");
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("public class " + this.className + " {\n");
                //step2: write attributes
                for(String attrName : attributes.keySet()) {
                    AttributeComponent attributeComponent = attributes.get(attrName);
                    attributeComponent.generateCode4Attr(bw);
                }
                //step3: write methods
                for (String methodName : methods.keySet()) {
                    bw.write("\n");
                    MethodComponent methodComponent = methods.get(methodName);
                    methodComponent.generateCode4Method(bw);
                }
                bw.write("}\n");
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            this.generateCode4CompositeClass();
        }
    }

    public void addCompositeComponent(
            String connectorName, String connectorType, ArrayList<String> connectorComponent) {
        //step1:implement addAttribute
        int i = 0;
        for (String componentType : connectorComponent) {
            String componentName = componentType.substring(0,1).toLowerCase() + componentType.substring(1);
            AttributeComponent attributeComponent = new AttributeComponent(componentName, componentType);
            attributes.put(componentName, attributeComponent);
            if (i == 0) {
                this.component1 = componentType;
            } else {
                this.component2 = componentType;
            }
            i++;
        }
        //AttributeComponent attributeComponent = new AttributeComponent(connectorName, c)
        //step2:implement addMethod sequencerMethod/selectorMethod
        String connectMethodName = "";
        if (connectorType.equals("SEQ")) {
            connectMethodName = "sequencerMethod";
        } else if (connectorType.equals("SEL")) {
            connectMethodName = "selectorMethod";
        }
        MethodComponent meth = new MethodComponent(connectMethodName, AccessType.PRIVATE, null, DataType.VOID);
        methods.put(connectMethodName, meth);
    }

    public void generateCode4CompositeClass() {
        //step1: write frame for Class
        try {
            FileWriter fw = new FileWriter("repository/" + this.className + ".java");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("public class " + this.className + " {\n");
            //step2: write attributes
            for(String attrName : attributes.keySet()) {
                AttributeComponent attributeComponent = attributes.get(attrName);
                attributeComponent.generateCode4CompoAttr(bw);
            }
            //step3: write methods
            //step3.1: write constructor
            bw.write("\n");
            bw.write("    public " + this.className + "() {\n");
            for(String attrName : attributes.keySet()) {
                bw.write("        this." + attrName + " = new " + attrName.substring(0,1).toUpperCase() + attrName.substring(1) + "();\n");
            }
            //bw.write("        this." + elevator + " = new " + Elevator + "();");
            bw.write("    }\n");
            //step3.2: write seq/sel method
            for (String methodName : methods.keySet()) {
                bw.write("\n");
                MethodComponent methodComponent = methods.get(methodName);
                //methodComponent.generateCode4CompoMethod(bw);
                //methodComponent.generateCode4CompoMethodSeq(bw, this.component1, this.component2);
                if (methodName.equals("selectorMethod")) {
                    methodComponent.generateCode4CompoMethodSel(bw, this.component1, this.component2);
                } else {
                    methodComponent.generateCode4CompoMethodSeq(bw, this.component1, this.component2);
                }
            }
            bw.write("}\n");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateCode4ClassDeployment() {
        //step1: write frame for Class
        //File f = new File("componentFile/" + this.className + ".java");
        if (this.isAtomic) {
            try {
                //OutputStream fOut = new FileOutputStream(f);
                FileWriter fw = new FileWriter("deployment/" + this.className + ".java");
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("public class " + this.className + " {\n");
                //step2: write attributes
                for(String attrName : attributes.keySet()) {
                    AttributeComponent attributeComponent = attributes.get(attrName);
                    attributeComponent.generateCode4Attr(bw);
                }
                //step3: write methods
                for (String methodName : methods.keySet()) {
                    bw.write("\n");
                    MethodComponent methodComponent = methods.get(methodName);
                    methodComponent.generateCode4Method(bw);
                }
                bw.write("}\n");
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            this.generateCode4CompositeClassDeployment();
        }
    }

    public void generateCode4CompositeClassDeployment() {
        //step1: write frame for Class
        try {
            FileWriter fw = new FileWriter("deployment/" + this.className + ".java");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("public class " + this.className + " {\n");
            //step2: write attributes
            for(String attrName : attributes.keySet()) {
                AttributeComponent attributeComponent = attributes.get(attrName);
                attributeComponent.generateCode4CompoAttr(bw);
            }
            //step3: write methods
            //step3.1: write constructor
            bw.write("\n");
            bw.write("    public " + this.className + "() {\n");
            for(String attrName : attributes.keySet()) {
                bw.write("        this." + attrName + " = new " + attrName.substring(0,1).toUpperCase() + attrName.substring(1) + "();\n");
            }
            //bw.write("        this." + elevator + " = new " + Elevator + "();");
            bw.write("    }\n");
            //step3.2: write seq/sel method
            for (String methodName : methods.keySet()) {
                bw.write("\n");
                MethodComponent methodComponent = methods.get(methodName);
                //methodComponent.generateCode4CompoMethodSeq(bw, this.component1, this.component2);
                if (methodName.equals("selectorMethod")) {
                    methodComponent.generateCode4CompoMethodSel(bw, this.component1, this.component2);
                } else {
                    methodComponent.generateCode4CompoMethodSeq(bw, this.component1, this.component2);
                }
            }
            bw.write("}\n");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
