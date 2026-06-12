package com.demo;

import java.util.Scanner;

public class Main {
	static Scanner cin = new Scanner(System.in);
	public static void main(String[] args) {
		
		String opc = "";
		do {
			Consultor.executeConsulta();
			opc= respuesta();
		}while(opc.equals("y"));
		System.out.print("[Programa] Terminado");
		cin.close();
	}
	private static String respuesta() {
		System.out.print("[Programa] Otra consulta(y/n): ");
		return cin.next();
	}
}