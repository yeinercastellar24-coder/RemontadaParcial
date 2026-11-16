package restaurante;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Área de Salón.
 *  - Comensales de la mesa: ArrayList (conserva el orden de llegada, acceso por posición).
 *  - Receta: LinkedList (lista de pasos donde se puede insertar en medio).
 */
public class Salon {

    private final ArrayList<Comensal> comensales = new ArrayList<>();
    private final LinkedList<String> receta = new LinkedList<>();

    // ---------- Comensales (RF-07 a RF-10) ----------

    /** RF-07 */
    public void anotarComensal(Comensal comensal) {
        comensales.add(comensal);
        System.out.println(comensal.getNombre() + " " + comensal.getApellido() + " quedó anotado en la mesa.");
    }

    /** RF-08: lista por apellido SIN modificar el orden original (se ordena una copia). */
    public void listarPorApellido() {
        if (comensales.isEmpty()) {
            System.out.println("No hay comensales en la mesa.");
            return;
        }
        List<Comensal> copia = new ArrayList<>(comensales);
        copia.sort(Comparator.comparing(Comensal::getApellido, String.CASE_INSENSITIVE_ORDER));
        System.out.println("Comensales por apellido:");
        for (Comensal c : copia) {
            System.out.println("  " + c);
        }
    }

    /** Ayuda para comprobar que el orden de llegada no cambió. */
    public void listarEnOrdenDeLlegada() {
        if (comensales.isEmpty()) {
            System.out.println("No hay comensales en la mesa.");
            return;
        }
        System.out.println("Comensales en orden de llegada:");
        for (int i = 0; i < comensales.size(); i++) {
            System.out.println("  [" + i + "] " + comensales.get(i));
        }
    }

    /** RF-09: retira por posición (la primera es 0). Devuelve null si la posición no existe. */
    public Comensal retirarComensal(int posicion) {
        if (posicion < 0 || posicion >= comensales.size()) {
            System.out.println("La posición " + posicion + " no existe.");
            return null;
        }
        Comensal retirado = comensales.remove(posicion);
        System.out.println("Se retiró a " + retirado + ".");
        return retirado;
    }

    /** RF-10 */
    public int cantidadComensales() {
        return comensales.size();
    }

    // ---------- Receta (RF-11 a RF-14) ----------

    /** RF-11: agrega al final. */
    public void agregarPaso(String paso) {
        receta.addLast(paso);
        System.out.println("Paso agregado: " + paso);
    }

    /** RF-12: inserta justo después de un paso existente. */
    public boolean insertarPasoDespuesDe(String referencia, String nuevoPaso) {
        int indice = receta.indexOf(referencia);
        if (indice == -1) {
            System.out.println("El paso de referencia \"" + referencia + "\" no está en la receta. La receta no cambia.");
            return false;
        }
        receta.add(indice + 1, nuevoPaso);
        System.out.println("Paso \"" + nuevoPaso + "\" insertado después de \"" + referencia + "\".");
        return true;
    }

    /** RF-13: remove(Object) devuelve false si no estaba. */
    public boolean eliminarPaso(String paso) {
        if (!receta.remove(paso)) {
            System.out.println("El paso \"" + paso + "\" no estaba en la receta.");
            return false;
        }
        System.out.println("Paso eliminado: " + paso);
        return true;
    }

    /** RF-14 */
    public void mostrarReceta() {
        if (receta.isEmpty()) {
            System.out.println("La receta no tiene pasos.");
            return;
        }
        System.out.println("Receta:");
        int numero = 1;
        for (String paso : receta) {
            System.out.println("  " + numero++ + ". " + paso);
        }
    }
}
