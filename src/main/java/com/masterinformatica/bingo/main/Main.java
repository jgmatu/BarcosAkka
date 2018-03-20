package com.masterinformatica.bingo.main;

import com.masterinformatica.barcos.actors.Manager;

public class Main {
	
	
    public static void main(String[] args) throws InterruptedException{  
        akka.Main.main(new String[]{Manager.class.getName()});
    } 
}
