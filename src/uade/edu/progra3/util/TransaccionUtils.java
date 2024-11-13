package uade.edu.progra3.util;

import uade.edu.progra3.model.Transaccion;

import java.util.ArrayList;
import java.util.List;

public class TransaccionUtils {

    /**
     * Método para crear una lista de transacciones sin
     * dependencias, con valores y tamaños aleatorios
     * @param cantidad
     * @param maxTamanio
     * @param maxValor
     * @param maxFirmas
     * @return List<Transaccion>
     */
    public static List<Transaccion> crearTransaccionesSimples(int cantidad, int maxTamanio, int maxValor, int maxFirmas) {
        List<Transaccion> transacciones = new ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            int tamanio = (int) (Math.random() * maxTamanio) + 1;
            int valor = (int) (Math.random() * maxValor) + 1;
            int firmasRequeridas = (int) (Math.random() * maxFirmas) + 1;

            Transaccion transaccion = new Transaccion(tamanio, valor,
                    null, firmasRequeridas);
            transacciones.add(transaccion);
        }
        return transacciones;
    }

    /**
     * Método para crear transacciones con dependencias dinámicas
     * @param cantidad
     * @param maxTamanio
     * @param maxValor
     * @param maxFirmas
     * @return List<Transaccion>
     */
    public static List<Transaccion>
    crearTransaccionesConDependencias(int cantidad, int maxTamanio,
                                      int maxValor, int maxFirmas) {
        List<Transaccion> transacciones = new ArrayList<>();
        Transaccion transaccionAnterior = null;

        for (int i = 0; i < cantidad; i++) {
            int tamanio = (int) (Math.random() * maxTamanio) + 1;
            int valor = (int) (Math.random() * maxValor) + 1;
            int firmasRequeridas = (int) (Math.random() * maxFirmas) + 1;

            Transaccion dependencia = (i == 0) ? null : transaccionAnterior;

            Transaccion transaccion = new Transaccion(tamanio, valor,
                    dependencia, firmasRequeridas);
            transacciones.add(transaccion);

            transaccionAnterior = transaccion;
        }
        return transacciones;
    }

    /**
     * Método para firmar todas las transacciones en una
     * lista hasta el número requerido de firmas
     * @param transacciones
     */
    public static void firmarTransacciones(List<Transaccion> transacciones) {
        for (Transaccion t : transacciones) {
            while (!t.isFirmada()) {
                t.agregarFirma();
            }
        }
    }

    /**
     * Método para generar una combinación de transacciones con y sin dependencias
     * @param cantidadSimples
     * @param cantidadConDependencias
     * @param maxTamanio
     * @param maxValor
     * @param maxFirmas
     * @return List<Transaccion>
     */
    public static List<Transaccion>
    crearTransaccionesMixtas(int cantidadSimples,
                             int cantidadConDependencias, int maxTamanio,
                             int maxValor, int maxFirmas) {
        List<Transaccion> transacciones = new ArrayList<>();

        transacciones.addAll(crearTransaccionesSimples(cantidadSimples,
                maxTamanio, maxValor, maxFirmas));
        transacciones.addAll(crearTransaccionesConDependencias(cantidadConDependencias,
                maxTamanio, maxValor, maxFirmas));
        firmarTransacciones(transacciones);

        return transacciones;
    }
}
