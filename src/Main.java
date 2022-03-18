import designBuildComponent.ParseCommand;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s;
        //System.out.println(System.getProperty("user.dir"));
        File file = new File("repository");
        if (!file.exists()) {
            file.mkdir();
        }
        file = new File("deployment");
        if (!file.exists()) {
            file.mkdir();
        }
        System.out.println("Please write command to design atomic component");
        ParseCommand commandParser = new ParseCommand();
        while(true) {
            s = sc.nextLine();
            if (s.equals("finished design atomic component")) {
                break;
            }
            commandParser.parseCommand(s);
        }
        System.out.println("Design atomic component has been ended");
        //System.out.println("Start to generate code for components");
        commandParser.outputComponent();

        System.out.println("Start to compose components");
        while(true) {
            s = sc.nextLine();
            if (s.equals("finished composition")) {
                break;
            }
            commandParser.parseComposeCommand(s);
        }
        System.out.println("Design phase has been ended");
        System.out.println("Start to generate code for components");
        commandParser.outputComponent();

        System.out.println("Start deployment phase");
        while(true) {
            s = sc.nextLine();
            if (s.equals("finished deployment")) {
                break;
            }
            commandParser.parseComposeCommand(s);
        }
        System.out.println("");
        commandParser.outputComponentDeployment();
    }
}
