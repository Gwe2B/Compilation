import java.io.IOException;
import java.util.Scanner;

import javax.sound.midi.SysexMessage;

import com.Interpreteur;

public class App {
    public static void main(String[] args) throws Exception {
        //Equivalent de A:=5
        String[] affectation = {
            "INT 1",
            "LDA 0",
            "LDI 5",
            "STO",
            "PRN"
        };

        String[] read = {
            "INT 1",
            "LDI 0",
            "INN",
            "PRN"
        };

        System.out.println("Test affectation de type A:=5");
        Interpreteur inter = new Interpreteur(affectation);
        inter.run();

        System.out.println("Test saisie");
        inter.setPCODE(read);
        inter.run();
    }
}
