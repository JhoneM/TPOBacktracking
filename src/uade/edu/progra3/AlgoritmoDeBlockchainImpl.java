package uade.edu.progra3;

import uade.edu.progra3.model.Bloque;
import uade.edu.progra3.model.Transaccion;

import java.util.List;
import java.util.ArrayList;
/**
 * @author AlejandroFoglino
 */
public class AlgoritmoDeBlockchainImpl implements AlgoritmoDeBlockchain {

    @Override
    public List<List<Bloque>> construirBlockchain(List<Transaccion> transacciones, int maxTamanioBloque, int maxValorBloque, int maxTransacciones, int maxBloques) {
        List<List<Bloque>> soluciones = new ArrayList<>();

        if (maxTransacciones * maxBloques < transacciones.size()) {
            System.out.println("No se pueden acomodar todas las transacciones");
            return soluciones;  // Vacio; Imposible acomodar todas las transacciones
        }

        backTracking(soluciones, transacciones, new ArrayList<>(), maxTamanioBloque, maxValorBloque,
                maxTransacciones, maxBloques);
        return soluciones;
    }

    private void backTracking(List<List<Bloque>> soluciones, List<Transaccion> transacciones,
                              List<Bloque> blockchainActual, int maxTamanioBloque, int maxValorBloque,
                              int maxTransacciones, int maxBloques) {

        if (transacciones.isEmpty()) {
            // Verificar que la cantidad de bloques no supere el máximo permitido
            if (blockchainActual.size() <= maxBloques) {
                soluciones.add(clonarBlockchain(blockchainActual));
            }
            return;
        }

        Transaccion transaccionActual = transacciones.get(0);
        List<Transaccion> transaccionesRestantes = new ArrayList<>(transacciones.subList(1, transacciones.size()));

        for (Bloque bloque : new ArrayList<>(blockchainActual)) {
            if (puedeAgregar(bloque, transaccionActual, maxTamanioBloque, maxValorBloque, maxTransacciones)) {
                agregarTransaccion(bloque, transaccionActual);
                backTracking(soluciones, transaccionesRestantes, blockchainActual, maxTamanioBloque,
                        maxValorBloque, maxTransacciones, maxBloques);
                removerTransaccion(bloque, transaccionActual);
            }
        }

        Bloque nuevoBloque = new Bloque();
        agregarTransaccion(nuevoBloque, transaccionActual);
        blockchainActual.add(nuevoBloque);
        backTracking(soluciones, transaccionesRestantes, blockchainActual, maxTamanioBloque,
                maxValorBloque, maxTransacciones, maxBloques);
        blockchainActual.remove(nuevoBloque);
    }

    private boolean puedeAgregar(Bloque bloque, Transaccion transaccion, int maxTamanioBloque, int maxValorBloque, int maxTransacciones) {
        return (bloque.getTamanioTotal() + transaccion.getTamanio() <= maxTamanioBloque) &&
                (bloque.getValorTotal() + transaccion.getValor() <= maxValorBloque) &&
                (bloque.getTransacciones().size() < maxTransacciones) &&
                ((bloque.getValorTotal() + transaccion.getValor()) % 10 == 0) &&
                (transaccion.getDependencia() == null || dependeDeBloqueadosResueltos(transaccion, bloque)) &&
                (transaccion.getFirmasActuales() >= transaccion.getFirmasRequeridas());
    }

    private boolean dependeDeBloqueadosResueltos(Transaccion transaccion, Bloque bloque) {
        Transaccion dependencia = transaccion.getDependencia();
        return dependencia == null || bloque.getTransacciones().contains(dependencia);
    }

    private void agregarTransaccion(Bloque bloque, Transaccion transaccion) {
        bloque.getTransacciones().add(transaccion);
        bloque.setTamanioTotal(bloque.getTamanioTotal() + transaccion.getTamanio());
        bloque.setValorTotal(bloque.getValorTotal() + transaccion.getValor());
    }

    private void removerTransaccion(Bloque bloque, Transaccion transaccion) {
        bloque.getTransacciones().remove(transaccion);
        bloque.setTamanioTotal(bloque.getTamanioTotal() - transaccion.getTamanio());
        bloque.setValorTotal(bloque.getValorTotal() - transaccion.getValor());
    }

    private List<Bloque> clonarBlockchain(List<Bloque> original) {
        List<Bloque> copia = new ArrayList<>();
        for (Bloque bloque : original) {
            Bloque nuevoBloque = new Bloque();
            nuevoBloque.setTamanioTotal(bloque.getTamanioTotal());
            nuevoBloque.setValorTotal(bloque.getValorTotal());
            nuevoBloque.setTransacciones(new ArrayList<>(bloque.getTransacciones()));
            copia.add(nuevoBloque);
        }
        return copia;
    }
}
