package restaurante;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Área de Cocina.
 * PriorityQueue: sale primero la prioridad más baja y, si empatan, la que llegó antes.
 */
public class Cocina {

    private final PriorityQueue<Comanda> comandas = new PriorityQueue<>(
            Comparator.comparingInt(Comanda::getPrioridad)
                      .thenComparingInt(Comanda::getLlegada));

    private int contadorLlegada = 0;

    /** RF-21: el sistema asigna el número de llegada. Prioridad válida: 1, 2 o 3. */
    public Comanda recibirComanda(String mesa, int prioridad) {
        if (prioridad < 1 || prioridad > 3) {
            System.out.println("Prioridad inválida. Use 1 (express), 2 (normal) o 3 (programada).");
            return null;
        }
        Comanda c = new Comanda(mesa, prioridad, ++contadorLlegada);
        comandas.add(c);
        System.out.println("Comanda recibida: " + c);
        return c;
    }

    /** RF-22: mira cuál se preparará ahora, sin retirarla. */
    public Comanda verSiguiente() {
        Comanda c = comandas.peek();
        if (c == null) {
            System.out.println("No hay comandas en espera.");
        }
        return c;
    }

    /** RF-23: prepara la siguiente (la retira). */
    public Comanda prepararSiguiente() {
        Comanda c = comandas.poll();
        if (c == null) {
            System.out.println("No hay comandas en espera.");
        }
        return c;
    }

    /** RF-24 */
    public int cuantasEsperan() {
        return comandas.size();
    }
}
