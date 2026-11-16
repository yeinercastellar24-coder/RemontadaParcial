package restaurante;

/** Cliente identificado por su documento, nombre y apellido. */
public class Comensal {

    private final String documento;
    private final String nombre;
    private final String apellido;

    public Comensal(String documento, String nombre, String apellido) {
        this.documento = documento;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String getDocumento() { return documento; }
    public String getNombre()    { return nombre; }
    public String getApellido()  { return apellido; }

    @Override
    public String toString() {
        return documento + " - " + nombre + " " + apellido;
    }
}
