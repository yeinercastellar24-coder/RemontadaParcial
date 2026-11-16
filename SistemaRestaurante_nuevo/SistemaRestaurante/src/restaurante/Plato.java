package restaurante;

/** Platillo de la carta, con código, nombre y categoría. */
public class Plato {

    private final String codigo;
    private final String nombre;
    private final String categoria;

    public Plato(String codigo, String nombre, String categoria) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
    }

    public String getCodigo()    { return codigo; }
    public String getNombre()    { return nombre; }
    public String getCategoria() { return categoria; }

    @Override
    public String toString() {
        return codigo + " - " + nombre + " (" + categoria + ")";
    }
}
