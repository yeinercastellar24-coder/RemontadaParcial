package restaurante;

/** Pedido de una mesa que se envía a cocina. Prioridad: 1 = express, 2 = normal, 3 = programada. */
public class Comanda {

    private final String mesa;
    private final int prioridad;
    private final int llegada; // número consecutivo asignado por el sistema (sirve para desempatar)

    public Comanda(String mesa, int prioridad, int llegada) {
        this.mesa = mesa;
        this.prioridad = prioridad;
        this.llegada = llegada;
    }

    public String getMesa()    { return mesa; }
    public int getPrioridad()  { return prioridad; }
    public int getLlegada()    { return llegada; }

    private String nombrePrioridad() {
        switch (prioridad) {
            case 1:  return "express";
            case 2:  return "normal";
            default: return "programada";
        }
    }

    @Override
    public String toString() {
        return "Mesa " + mesa + " | prioridad " + prioridad + " (" + nombrePrioridad()
                + ") | llegada " + llegada;
    }
}
