
import uade.edu.progra3.AlgoritmoDeBlockchainImpl;
import uade.edu.progra3.model.Transaccion;
import uade.edu.progra3.model.Bloque;

import java.util.Arrays;
import java.util.List;

public class Principal {

	public static void main(String[] args) {

		List<Transaccion> transacciones = crearTransacciones();
		int maxTamanioBloque = 1024;
		int maxValorBloque = 100;
		int maxTransacciones = 3;
		int maxBloques = 2;

		AlgoritmoDeBlockchainImpl algoritmo = new AlgoritmoDeBlockchainImpl();
		List<List<Bloque>> soluciones = algoritmo.construirBlockchain(transacciones, maxTamanioBloque, maxValorBloque, maxTransacciones, maxBloques);
		System.out.println("Soluciones encontradas: " + soluciones.size());
		System.out.println("-------------------------");
		int solucionIndex = 1;
		for (List<Bloque> solucion : soluciones) {
			System.out.println("Solucion " + solucionIndex + ":");
			int bloqueIndex = 1;
			for (Bloque bloque : solucion) {
				System.out.println("");
				System.out.println("Bloque " + bloqueIndex + ":");
				System.out.println("Tamaño: " + bloque.getTamanioTotal());
				System.out.println("Valor: " + bloque.getValorTotal());
				for (Transaccion transaccion : bloque.getTransacciones()) {
					System.out.println("Transacción: " + "T" + transaccion.getTamanio() + " - V" + transaccion.getValor());
				}
				bloqueIndex++;
			}
			solucionIndex++;
			System.out.println("----------------");
		}
	}

	private static List<Transaccion> crearTransacciones(){
		Transaccion t1 = new Transaccion(200, 30, null, 2);
		Transaccion t2 = new Transaccion(150, 40, t1, 1);
		Transaccion t3 = new Transaccion(300, 50, null, 2);
		Transaccion t4 = new Transaccion(100, 20, t3, 1);
		Transaccion t5 = new Transaccion(250, 30, null, 2);

		t1.agregarFirma();
		t1.agregarFirma();
		t2.agregarFirma();
		t3.agregarFirma();
		t3.agregarFirma();
		t4.agregarFirma();
		t5.agregarFirma();
		t5.agregarFirma();
		List<Transaccion> transacciones = Arrays.asList(t1, t2, t3, t4, t5);
		return transacciones;
	}
}
