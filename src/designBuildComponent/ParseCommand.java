package designBuildComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseCommand {
    private static HashMap<String/*ClassName*/, ClassComponent> ClassMap = new HashMap<>();
    private static String ClassName;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s;
        System.out.println("Please write command to design component");
        ParseCommand commandParser = new ParseCommand();
        while(true) {
            s = sc.nextLine();
            if (s.equals("finished")) {
                break;
            }
            commandParser.parseCommand(s);
        }
    }

    public ParseCommand() {
        return;
    }

    public void parseCommand(String s) {
        String[] command = s.split("\\s");
        switch (command[0]) {
            case "CREATE":
            case "DELETE":
            case "UPDATE":
            case "MODIFY": {
                //String regex = "CN:\\S*"; //CN:<Class Name>
                String regex = ":\\S*";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(s);
                while(matcher.find()) {
                    String CN = matcher.group();
                    ParseCommand.ClassName = CN.substring(1);
                }
                switch (command[0]) {
                    case "CREATE":{
                        ClassComponent newClass = new ClassComponent(ParseCommand.ClassName, true);
                        ParseCommand.ClassMap.put(ParseCommand.ClassName, newClass);
                        break;
                    }
                    case "DELETE":{
                        ParseCommand.ClassMap.remove(ParseCommand.ClassName);
                        ParseCommand.ClassName = null;
                        break;
                    }
                    case "MODIFY":
                    case "UPDATE":{
                        switch (command[command.length-1]) {
                            case "START": {
                                break;
                            }
                            case "END": {
                                ParseCommand.ClassName = null;
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
            case "MODI":
            case "ADD":{
                switch (command[1]) {
                    case "ATTR": {
                        String attrName = null;
                        DataType dataType = null;
                        AccessType accessType = AccessType.PRIVATE;
                        boolean getMethod = false;
                        boolean setMethod = false;
                        //AN:<Attribute Name>
                        String regexAN = "AN:\\S*";
                        Pattern patternAN = Pattern.compile(regexAN);
                        Matcher matcherAN = patternAN.matcher(s);
                        while(matcherAN.find()) {
                            String AN = matcherAN.group();
                            attrName =  AN.substring(3);
                        }
                        //TYPE:<Type>
                        String regexType = "TYPE:\\S*";
                        Pattern patternType = Pattern.compile(regexType);
                        Matcher matcherType = patternType.matcher(s);
                        while(matcherType.find()) {
                            String TYPE = matcherType.group();
                            dataType =  DataType.valueOf(TYPE.substring(5));
                        }
                        //MOD:<Access Modifier>
                        String regexMOD = "MOD:\\S*";
                        Pattern patternMOD = Pattern.compile(regexMOD);
                        Matcher matcherMOD = patternMOD.matcher(s);
                        while(matcherMOD.find()) {
                            String MOD = matcherMOD.group();
                            accessType =  AccessType.valueOf(MOD.substring(4));
                        }
                        //GET
                        String regexGET = "GET:";
                        Pattern patternGET = Pattern.compile(regexGET);
                        Matcher matcherGET = patternGET.matcher(s);
                        while(matcherGET.find()) {
                            //String GET = matcherGET.group();
                            getMethod = true;
                        }
                        //SET
                        String regexSET = "SET:";
                        Pattern patternSET = Pattern.compile(regexSET);
                        Matcher matcherSET = patternSET.matcher(s);
                        while(matcherSET.find()) {
                            //String SET = matcherSET.group();
                            setMethod = true;
                        }
                        ClassComponent nowClass = ParseCommand.ClassMap.get(ParseCommand.ClassName);
                        switch (command[0]){
                            case "ADD": {
                                nowClass.addAttribute(attrName, dataType, accessType, getMethod, setMethod);
                                break;
                            }
                            case "MODI": {
                                nowClass.modifyAttribute(attrName, dataType, accessType, getMethod, setMethod);
                                break;
                            }
                        }
                        //nowClass.addAttribute(attrName, dataType, accessType, getMethod, setMethod);
                        break;
                    }
                    case "METH": {
                        String methodName = null;
                        AccessType accessType = AccessType.PRIVATE;
                        HashMap<String, DataType> paras = null;
                        DataType returnType = DataType.VOID;
                        //MN:<Method Name>
                        String regexMN = "MN:\\S*";
                        Pattern patternMN = Pattern.compile(regexMN);
                        Matcher matcherMN = patternMN.matcher(s);
                        while(matcherMN.find()) {
                            String AN = matcherMN.group();
                            methodName =  AN.substring(3);
                        }
                        //[MOD:<Access Modifier>]
                        String regexMOD = "MOD:\\S*";
                        Pattern patternMOD = Pattern.compile(regexMOD);
                        Matcher matcherMOD = patternMOD.matcher(s);
                        while(matcherMOD.find()) {
                            String MOD = matcherMOD.group();
                            accessType =  AccessType.valueOf(MOD.substring(4));
                        }
                        //[PARA:<Type>,<Name>{;<Type>,<Name>}]
                        String regexPARA = "PARA:\\S*";
                        Pattern patternPARA = Pattern.compile(regexPARA);
                        Matcher matcherPARA = patternPARA.matcher(s);
                        while(matcherPARA.find()) {
                            String PARA = matcherPARA.group();
                            paras = new HashMap<>();
                            String PARAString = PARA.substring(5);
                            String[] paraGroup = PARAString.split(";");
                            //System.out.println(Arrays.toString(paraGroup));
                            for (String paraG: paraGroup) {
                                String[] paraSplit = paraG.split(",");
                                paras.put(paraSplit[1], DataType.valueOf(paraSplit[0]));
                            }
                            //paras已经在上面的for循环中构造好了
                        }
                        //[RET:<Return Type>]
                        String regexReturnType = "RET:\\S*";
                        Pattern patternReturnType = Pattern.compile(regexReturnType);
                        Matcher matcherReturnType = patternReturnType.matcher(s);
                        while(matcherReturnType.find()) {
                            String TYPE = matcherReturnType.group();
                            returnType =  DataType.valueOf(TYPE.substring(4));
                        }
                        ClassComponent nowClass = ParseCommand.ClassMap.get(ParseCommand.ClassName);
                        switch (command[0]){
                            case "ADD": {
                                nowClass.addMethod(methodName, accessType, paras, returnType);
                                break;
                            }
                            case "MODI": {
                                nowClass.modifyMethod(methodName, accessType, paras, returnType);
                                break;
                            }
                        }
                        //nowClass.addMethod(methodName, accessType, paras, returnType);
                        break;
                    }
                }
                break;
            }
            case "DEL":{
                switch (command[1]) {
                    case "ATTR": {
                        String attrName = null;
                        //AN:<Attribute Name>
                        String regexAN = "AN:\\S*";
                        Pattern patternAN = Pattern.compile(regexAN);
                        Matcher matcherAN = patternAN.matcher(s);
                        while(matcherAN.find()) {
                            String AN = matcherAN.group();
                            attrName =  AN.substring(3);
                        }
                        ClassComponent nowClass = ParseCommand.ClassMap.get(ParseCommand.ClassName);
                        nowClass.deleteAttribute(attrName);
                    }
                    case "METH": {
                        String methodName = null;
                        //MN:<Method Name>
                        String regexMN = "MN:\\S*";
                        Pattern patternMN = Pattern.compile(regexMN);
                        Matcher matcherMN = patternMN.matcher(s);
                        while(matcherMN.find()) {
                            String AN = matcherMN.group();
                            methodName =  AN.substring(3);
                        }
                        ClassComponent nowClass = ParseCommand.ClassMap.get(ParseCommand.ClassName);
                        nowClass.deleteMethod(methodName);
                    }
                }
                break;
            }
        }
    }

    public void outputComponent() {
        for(String className : ClassMap.keySet()) {
            ClassComponent classComponent = ClassMap.get(className);
            classComponent.generateCode4Class();
        }
    }

    public void outputComponentDeployment() {
        for(String className : ClassMap.keySet()) {
            ClassComponent classComponent = ClassMap.get(className);
            classComponent.generateCode4ClassDeployment();
        }
    }

    public void parseComposeCommand(String s) {
        String[] command = s.split("\\s");
        String connectorName = null;
        String connectorType = null;
        ArrayList<String> componentList= new ArrayList<>();
        String regexCONNECTORNAME = "CONNECTOR:\\S*";
        Pattern patternCN = Pattern.compile(regexCONNECTORNAME);
        Matcher matcherCN = patternCN.matcher(s);
        while (matcherCN.find()) {
            String CN = matcherCN.group();
            connectorName = CN.substring(10);
        }
        String regexType = "TYPE:\\S*";
        Pattern patternType = Pattern.compile(regexType);
        Matcher matcherType = patternType.matcher(s);
        while (matcherType.find()) {
            String type = matcherType.group();
            connectorType = type.substring(5);
        }
        String regexComponentList = "COMPONENT:\\S*";
        Pattern patternComponent = Pattern.compile(regexComponentList);
        Matcher matcherComponent = patternComponent.matcher(s);
        while (matcherComponent.find()) {
            String compoStr = matcherComponent.group();
            String compoString = compoStr.substring(10);
            String[] compoGroup = compoString.split(",");
            for (String compoG : compoGroup) {
                componentList.add(compoG);
            }
        }
        ClassComponent newClass = new ClassComponent(connectorName, false);
        ParseCommand.ClassMap.put(connectorName, newClass);
        newClass.addCompositeComponent(connectorName, connectorType, componentList);
    }
}