package designBuildComponent;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;

public class MethodComponent {
    private String methodName;
    private AccessType accessType;
    private HashMap<String, DataType> paras;
    private DataType returnType;

    public MethodComponent(String methodName, AccessType accessType,
                           HashMap<String, DataType> paras, DataType returnType) {
        this.methodName = methodName;
        this.accessType = accessType;
        this.paras = paras;
        this.returnType = returnType;
    }

    public void generateCode4Method(BufferedWriter bw) {
        try {
            bw.write("    " + accessType.toString().toLowerCase()
                    + " " + returnType.toString().toLowerCase()
                    + " " + methodName + "(");
            this.generateCode4Para(bw);
            bw.write(") {\n");
            //bw.write("        \n");
            //bw.write("        \n");
            bw.write("        \n");
            bw.write("    }\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateCode4CompoMethod(BufferedWriter bw) {
        try {
            bw.write("    " + accessType.toString().toLowerCase()
                    + " " + returnType.toString().toLowerCase()
                    + " " + methodName + "(");
            //this.generateCode4Para(bw);
            bw.write("Dataflow dataflow");
            bw.write(") {\n");
            //bw.write("        \n");
            //bw.write("        \n");
            //if
            //bw.write("sequencer！！！！！！！");

            bw.write("        \n");
            bw.write("    }\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateCode4CompoMethodSel(BufferedWriter bw, String comp1, String comp2) {
        try {
            bw.write("    " + accessType.toString().toLowerCase()
                    + " " + returnType.toString().toLowerCase()
                    + " " + methodName + "(");
            bw.write("Dataflow dataflow");
            bw.write(") {\n");
            bw.write("        if() {\n");
            bw.write("            "+comp1.substring(0,1).toLowerCase() + comp1.substring(1));
            bw.write(".service(dataflow)\n");
            bw.write("        } else {\n");
            bw.write("            "+comp2.substring(0,1).toLowerCase() + comp2.substring(1));
            bw.write(".service(dataflow)\n");
            bw.write("        }\n");
            bw.write("    }\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateCode4CompoMethodSeq(BufferedWriter bw, String comp1, String comp2) {
        try {
            bw.write("    " + accessType.toString().toLowerCase()
                    + " " + returnType.toString().toLowerCase()
                    + " " + methodName + "(");
            //this.generateCode4Para(bw);
            bw.write("Dataflow dataflow");
            bw.write(") {\n");
            //bw.write("        \n");
            //bw.write("        \n");
            //if
            //bw.write("sequencer！！！！！！！");
            bw.write("        ");
            bw.write(comp1.substring(0,1).toLowerCase() + comp1.substring(1));
            bw.write(".receiveRequest(dataflow);");
            bw.write("\n");

            bw.write("        ");
            bw.write("dataflow = ");
            bw.write(comp1.substring(0,1).toLowerCase() + comp1.substring(1) + ".drawRequest();");
            bw.write("\n");

            bw.write("        ");
            bw.write(comp2.substring(0,1).toLowerCase() + comp2.substring(1));
            bw.write(".receiveRequest(dataflow);");
            bw.write("\n");

            bw.write("        \n");
            bw.write("    }\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateCode4Para(BufferedWriter bw) {
        if (this.paras == null || this.paras.isEmpty())
            return;
        String[] paras = this.paras.keySet().toArray(new String[0]);
        try {
            bw.write(this.paras.get(paras[0]).toString().toLowerCase() + " " + paras[0]);
            for(int i = 1; i < paras.length; i++) {
                bw.write(", " + this.paras.get(paras[i]).toString().toLowerCase() + " " + paras[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
