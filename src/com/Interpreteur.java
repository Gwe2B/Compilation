package com;

import java.util.Scanner;

/**
 * @author Calpena Marion, Guiraud Gwenaël
 * @created 14/12/2020
 */
public class Interpreteur {

/* ----------------------------- Class Constants ---------------------------- */
    public static final int MAX_MEM = 100;

/* --------------------------- Instance attributes -------------------------- */
    private String[] PCODE;
    private int PC = 0;

    private int[] MEM = new int[Interpreteur.MAX_MEM];
    private int SP = 0;

    private boolean PS = false;

/* ------------------------------ Constructors ------------------------------ */
    /**
     * Default constructor
     */
    public Interpreteur()
    { this(new String[] {}); }

    /**
     * Constructor
     * @param pcode The P-CODE instructions
     */
    public Interpreteur(String[] pcode) {
        this.setPCODE(pcode);
        this.MEM = new int[Interpreteur.MAX_MEM];
    }

/* --------------------------- Accessor & Mutators -------------------------- */
    /**
     * P-CODE setter
     * @param pcode The P-CODE to execute
     */
    public void setPCODE(String[] pcode) {
        this.PCODE = (String[]) pcode.clone();
        this.MEM = new int[Interpreteur.MAX_MEM];
        this.PC = 0;
        this.SP = 0;
    }

/* ---------------------------- Instance methods ---------------------------- */
    /**
     * Interprete the P-CODE
     */
    public void run() {
        this.PS = true;

        while(this.PS && this.PC < this.PCODE.length) {
            String[] instruction = this.PCODE[this.PC++].split(" ");
            
            switch (instruction[0]) {
                case "BZE":
                    // while(condition) equivalent
                    if(this.SP == 0)
                        this.PC = Integer.parseInt(instruction[1]);
                    else
                        this.SP--;

                    break;

                case "BRN":
                    // while(true) equivalent
                    this.PC = Integer.parseInt(instruction[1]);
                    break;

                case "HLT":
                    // stop the program
                    this.PS = false;
                    break;

                case "INT":
                    //Réservation de X cases mémoires
                    this.SP += Integer.parseInt(instruction[1])-1;
                    break;

                case "LDA":
                    //Empile la valeur de l'adresse X
                    this.MEM[++this.SP] = this.MEM[Integer.parseInt(instruction[1])];
                    break;

                case "LDI":
                    //Empile la valeur X
                    this.MEM[++this.SP] = Integer.parseInt(instruction[1]);
                    break;

                case "LDV":
                    /*
                    Prend la valeur de l'adresse correspondant au sommet de pile
                    et la stocke en sommet de pile
                    Ex: pour la pile (sommet à droite) (5, 2, 4, 1)
                        LDV => (5, 2, 4, 2) puisque la valeur de l'adresse 1
                        a pour valeur 2
                    */
                    this.MEM[this.SP] = this.MEM[this.MEM[this.SP]];
                    break;

                case "STO":
                    /*Prend le sommet de pile le bouge de deux cases mémoires et
                      dépile deux fois*/
                    int buf = this.MEM[this.SP--];
                    this.MEM[this.MEM[this.SP--]] = buf;
                    break;

                case "PRN":
                    //Affiche le sommet de pile
                    System.out.println(this.MEM[this.SP]);
                    break;
                
                case "INN":
                    /*
                    Lit une entrée utilisateur et la stock à l'adresse trouvé en
                    sommet de pile
                    */
                    Scanner scan = new Scanner(System.in);
                    this.MEM[this.MEM[this.SP]] = scan.nextInt();
                    scan.close();
                    this.SP--;
                    break;

                case "ADD":
                    this.MEM[--this.SP] = this.MEM[this.SP] + this.MEM[this.SP + 1];
                    break;

                case "SUB":
                    this.MEM[--this.SP] = this.MEM[this.SP] - this.MEM[this.SP + 1];
                    break;

                case "MULT":
                    this.MEM[--this.SP] = this.MEM[this.SP] * this.MEM[this.SP + 1];
                    break;

                case "DIV":
                    this.MEM[--this.SP] = this.MEM[this.SP] / this.MEM[this.SP + 1];
                    break;

                case "EQL":
                    this.MEM[this.SP] = (this.MEM[this.SP] == this.MEM[this.SP - 1]) ? 1 : 0;
                    break;

                case "NEQ":
                    this.MEM[this.SP] = (this.MEM[this.SP] != this.MEM[this.SP - 1]) ? 1 : 0;
                    break;

                case "GTR":
                    this.MEM[this.SP] = (this.MEM[this.SP] > this.MEM[this.SP - 1]) ? 1 : 0;
                    break;

                case "LSS":
                    this.MEM[this.SP] = (this.MEM[this.SP] < this.MEM[this.SP - 1]) ? 1 : 0;
                    break;

                case "GEQ":
                    this.MEM[this.SP] = (this.MEM[this.SP] >= this.MEM[this.SP - 1]) ? 1 : 0;
                    break;

                case "LEQ":
                    this.MEM[this.SP] = (this.MEM[this.SP] <= this.MEM[this.SP - 1]) ? 1 : 0;
                    break;
            
                default:
                    throw new RuntimeException("Unknown instruction: " +
                        instruction[0] + " " + instruction[1]);
            }
        }
    }
}
    
