package restaurante;

import java.util.ArrayDeque;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

/**
 * Área de Carta.
 *  - Platos por código: LinkedHashMap (búsqueda rápida por clave y listado estable).
 *  - Consulta rápida: ArrayDeque con los últimos 3 códigos (primero = menos usado, último = más reciente).
 *  - Categorías servidas hoy: LinkedHashSet (sin repetir, en orden de aparición).
 */
public class Carta {

    private static final int CAPACIDAD_MEMORIA = 3;

    private final LinkedHashMap<String, Plato> platos = new LinkedHashMap<>();
    private final ArrayDeque<String> recientes = new ArrayDeque<>();
    private final LinkedHashSet<String> categoriasServidas = new LinkedHashSet<>();

    // ---------- Platos (RF-15, RF-16) ----------

    /** RF-15: si el código ya existe, avisa y conserva el original. */
    public boolean registrarPlato(Plato plato) {
        if (platos.containsKey(plato.getCodigo())) {
            System.out.println("El código " + plato.getCodigo() + " ya existe. Se conserva el plato original: "
                    + platos.get(plato.getCodigo()));
            return false;
        }
        platos.put(plato.getCodigo(), plato);
        System.out.println("Plato registrado: " + plato);
        return true;
    }

    /** RF-16 */
    public Plato buscarPlato(String codigo) {
        Plato p = platos.get(codigo);
        if (p == null) {
            System.out.println("El código " + codigo + " no existe en la carta.");
        }
        return p;
    }

    /** RF-16: además, un plato eliminado no debe seguir en la memoria rápida. */
    public boolean eliminarPlato(String codigo) {
        Plato p = platos.remove(codigo);
        if (p == null) {
            System.out.println("El código " + codigo + " no existe en la carta.");
            return false;
        }
        recientes.remove(codigo);
        System.out.println("Plato eliminado: " + p);
        return true;
    }

    /** RF-16 */
    public void listarPlatos() {
        if (platos.isEmpty()) {
            System.out.println("La carta está vacía.");
            return;
        }
        System.out.println("=== CARTA ===");
        for (Plato p : platos.values()) {
            System.out.println("  " + p);
        }
    }

    // ---------- Consulta rápida (RF-17 a RF-19) ----------

    /**
     * RF-17: si está entre los últimos 3, responde y lo marca como el más reciente;
     * si no, lo busca en la carta y lo recuerda.
     * RF-18: si la memoria está llena, olvida el que hace más tiempo no se consulta.
     * RF-19: un plato que no existe en la carta no se recuerda.
     */
    public Plato consultarRapido(String codigo) {
        if (recientes.contains(codigo)) {
            recientes.remove(codigo);   // lo saco de donde esté...
            recientes.addLast(codigo);  // ...y lo vuelvo el más reciente
            System.out.println("(respondido desde la memoria rápida)");
            return platos.get(codigo);
        }

        Plato p = platos.get(codigo);
        if (p == null) {
            System.out.println("El código " + codigo + " no existe en la carta. No se recuerda.");
            return null;
        }

        recientes.addLast(codigo);
        if (recientes.size() > CAPACIDAD_MEMORIA) {
            String olvidado = recientes.pollFirst();
            System.out.println("(la memoria estaba llena: se olvidó " + olvidado + ")");
        }
        System.out.println("(buscado en la carta y recordado)");
        return p;
    }

    /** Ayuda para probar: muestra la memoria de menos usado a más reciente. */
    public void mostrarMemoriaRapida() {
        if (recientes.isEmpty()) {
            System.out.println("La memoria rápida está vacía.");
            return;
        }
        System.out.println("Memoria rápida (menos usado -> más reciente): " + recientes);
    }

    // ---------- Categorías servidas hoy (RF-20) ----------

    /** RF-20: add() devuelve false si ya estaba. */
    public boolean registrarCategoria(String categoria) {
        if (!categoriasServidas.add(categoria)) {
            System.out.println("La categoría " + categoria + " ya estaba registrada hoy.");
            return false;
        }
        System.out.println("Categoría registrada: " + categoria);
        return true;
    }

    /** RF-20: en el orden en que aparecieron por primera vez. */
    public void listarCategorias() {
        if (categoriasServidas.isEmpty()) {
            System.out.println("Aún no se han servido categorías hoy.");
            return;
        }
        System.out.println("Categorías servidas hoy: " + categoriasServidas);
        System.out.println("Cantidad: " + categoriasServidas.size());
    }

    public int cantidadCategorias() {
        return categoriasServidas.size();
    }

    /** Datos de práctica del documento (A1 a A4). */
    public void cargarDatosDePractica() {
        platos.put("A1", new Plato("A1", "Sopa de pollo", "Sopas"));
        platos.put("A2", new Plato("A2", "Arroz con pollo", "Fuertes"));
        platos.put("A3", new Plato("A3", "Flan", "Postres"));
        platos.put("A4", new Plato("A4", "Carne asada", "Fuertes"));
    }
}
