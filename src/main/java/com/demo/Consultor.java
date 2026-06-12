package com.demo;

import java.util.List;
import java.util.Scanner;

public class Consultor {
	public static void executeConsulta() {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Ingresa el número de empleado: ");
		int empNo = scanner.nextInt();

		RedisCache cache = new RedisCache();
		EmployeeRepository repo = new EmployeeRepository();

		System.out.println("\n--- Buscando en Redis ---");
		long startTotal = System.nanoTime();

		List<EmployeeInfo> empleados = cache.getEmployeeData(empNo);

		if (empleados != null && !empleados.isEmpty()) {
			// ─── HIT: datos encontrados en Redis ───
			long endTotal = System.nanoTime();
			System.out.println("[FUENTE] Datos obtenidos desde REDIS (caché)");
			System.out.printf("[TOTAL]  Tiempo total de consulta: %.3f ms%n", (endTotal - startTotal) / 1_000_000.0);
		} else {
			// ─── MISS: Redis vacío, consultar MySQL ───
			System.out.println("[MISS] No encontrado en Redis. Consultando MySQL...\n");

			System.out.println("--- Consultando MySQL ---");
			long startMySQL = System.nanoTime();
			empleados = repo.getEmployeeData(empNo);
			long endMySQL = System.nanoTime();

			System.out.printf("[MySQL]  Tiempo de consulta MySQL: %.3f ms%n", (endMySQL - startMySQL) / 1_000_000.0);

			if (empleados != null && !empleados.isEmpty()) {
				// Guardar en Redis para futuras consultas
				System.out.println("\n--- Guardando en Redis ---");
				cache.saveEmployeeData(empNo, empleados);
				System.out.println("[CACHE] Datos guardados en Redis exitosamente.");
			} else {
				System.out.println("[INFO] No se encontraron resultados en MySQL para emp_no=" + empNo);
			}

			long endTotal = System.nanoTime();
			System.out.println("[FUENTE] Datos obtenidos desde MYSQL");
			System.out.printf("[TOTAL]  Tiempo total de consulta: %.3f ms%n", (endTotal - startTotal) / 1_000_000.0);
		}

		// ─── Mostrar resultados ───
		if (empleados != null && !empleados.isEmpty()) {
			System.out.println("\n========== RESULTADOS ==========");
			System.out.printf("%-15s %-15s %-8s %-25s %10s%n", "Nombre", "Apellido", "Género", "Departamento",
					"Salario");
			System.out.println("-".repeat(75));
			for (EmployeeInfo emp : empleados) {
				System.out.printf("%-15s %-15s %-8s %-25s %10.2f%n", emp.getFirstName(), emp.getLastName(),
						emp.getGender(), emp.getDeptName(), emp.getSalary());
			}
			System.out.println("=".repeat(75));
			System.out.println("Total registros: " + empleados.size());
		}
		
	}

}
