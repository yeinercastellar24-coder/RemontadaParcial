package restaurante;

import java.util.ArrayDeque;
import java.util.HashSet;

/**
 * Área de Recepción.
 *  - Reservas: HashSet (un documento no se repite).
 *  - Fila para mesa: ArrayDeque (se sienta el primero, se va el último).
 */
public class Recepcion {

    private final HashSet<String> reservas = new HashSet<>();
    private final ArrayDeque<Comensal> fila = new ArrayDeque<>();

    // ---------- Reservas (RF-01, RF-02) ----------

    /** RF-01: add() devuelve false si el documento ya estaba. */
    public boolean registrarReserva(String documento) {
        if (!reservas.add(documento)) {
            System.out.println("El documento " + documento + " ya reservó hoy. No se repite la reserva.");
            return false;
        }
        System.out.println("Reserva registrada para el documento " + documento + ".");
        return true;
    }

    /** RF-02 */
    public boolean tieneReserva(String documento) {
        return reservas.contains(documento);
    }

    /** RF-02 */
    public int cantidadReservas() {
        return reservas.size();
    }

    // ---------- Fila para mesa (RF-03 a RF-06) ----------

    /** RF-03: el último en llegar queda al final. */
    public void recibirEnFila(Comensal comensal) {
        fila.addLast(comensal);
        System.out.println(comensal.getNombre() + " " + comensal.getApellido() + " entró a la fila.");
    }

    /** RF-04: sienta al primero de la fila. Devuelve null si no hay nadie. */
    public Comensal sentarPrimero() {
        Comensal c = fila.pollFirst();
        if (c == null) {
            System.out.println("No hay nadie en la fila.");
        }
        return c;
    }

    /** RF-05: se va el último que llegó. Devuelve null si no hay nadie. */
    public Comensal retirarUltimo() {
        Comensal c = fila.pollLast();
        if (c == null) {
            System.out.println("No hay nadie en la fila.");
        }
        return c;
    }

    /** RF-06: quién sigue, sin sentarlo. */
    public Comensal verSiguiente() {
        return fila.peekFirst();
    }

    /** RF-06 */
    public int cuantosEsperan() {
        return fila.size();
    }

    /** Ayuda para probar: muestra la fila de primero a último. */
    public void mostrarFila() {
        if (fila.isEmpty()) {
            System.out.println("La fila está vacía.");
            return;
        }
        System.out.println("Fila (sale primero -> llega último):");
        for (Comensal c : fila) {
            System.out.println("  " + c);
        }
    }
}
