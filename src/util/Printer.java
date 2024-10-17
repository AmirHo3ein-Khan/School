package util;

import java.util.Set;

import static util.Color.*;
import static util.Color.RESET;

public class Printer {
    public static void printMenu(String[] menu , String message) {
        System.out.println(CYAN+"* * * "+CYAN+ message +RESET+CYAN+" * * *"+RESET);
        System.out.println(PURPLE+"===================="+RESET);
        for (int i = 0; i < menu.length; i++) {
            System.out.printf(CYAN+"%d. %s \n"+RESET , i+1 , CYAN + menu[i] + RESET);
        }
    }
    public static void printDescription(String description) {
        System.out.println(CYAN+description+RESET);
    }
    public static void printError(String error){
        System.out.println(RED+error+RESET);
    }
    public static void printLine1(String line) {
        System.out.println(PURPLE+line+RESET);
    }
    public static void printLine (int numberOfLine){
        for (int i = 0; i <= numberOfLine; i++) {
            System.out.print(PURPLE+"="+RESET);
        }
        System.out.println();
    }
    public static <T> void printSet(Set<T> set) {
        for (T t :set){
            System.out.println(BLUE+t+RESET);
        }
    }
}
