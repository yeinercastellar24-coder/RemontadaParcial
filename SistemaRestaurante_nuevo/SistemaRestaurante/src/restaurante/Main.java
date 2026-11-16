package restaurante;

import java.util.Scanner;

/** RF-25: menú por consola que prueba todo y no se cierra con datos inválidos. */
public class Main {

    private static final Scanner sc = new Scanner(System.in);

    private static final Recepcion recepcion = new Recepcion();
    private static final Salon salon = new Salon();
    private static final Carta carta = new Carta();
    private static final Cocina cocina = new Cocina();

    // ---------- Lectura segura de datos ----------

    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = sc.nextLine().trim();
            try {
                return Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                System.out.println("Dato inválido: escriba un número entero.");
            }
        }
    }

    private static String leerTexto(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = sc.nextLine().trim();
            if (!texto.isEmpty()) {
                return texto;
            }
            System.out.println("El dato no puede estar vacío.");
        }
    }

    private static Comensal leerComensal() {
        String doc = leerTexto("Documento: ");
        String nombre = leerTexto("Nombre: ");
        String apellido = leerTexto("Apellido: ");
        return new Comensal(doc, nombre, apellido);
    }

    // ---------- Programa principal ----------

    public static void main(String[] args) {
        carta.cargarDatosDePractica(); // A1 a A4; bórrelo si el examen trae otros datos

        int opcion;
        do {
            System.out.println("\n=== SISTEMA DEL RESTAURANTE ===");
            System.out.println("1. Recepción (reservas y fila)");
            System.out.println("2. Salón (comensales y receta)");
            System.out.println("3. Carta (platos, consulta rápida y categorías)");
            System.out.println("4. Cocina (comandas)");
            System.out.println("0. Salir");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1: menuRecepcion(); break;
                case 2: menuSalon();     break;
                case 3: menuCarta();     break;
                case 4: menuCocina();    break;
                case 0: System.out.println("Hasta luego."); break;
                default: System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    // ---------- Recepción ----------

    private static void menuRecepcion() {
        int opcion;
        do {
            System.out.println("\n--- RECEPCIÓN ---");
            System.out.println("1. Registrar reserva");
            System.out.println("2. Consultar reserva y cantidad de reservas");
            System.out.println("3. Recibir cliente en la fila");
            System.out.println("4. Sentar al primero de la fila");
            System.out.println("5. Retirar al último de la fila (se cansó de esperar)");
            System.out.println("6. Ver siguiente y cuántos esperan");
            System.out.println("7. Mostrar la fila");
            System.out.println("0. Volver");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    recepcion.registrarReserva(leerTexto("Documento: "));
                    break;
                case 2:
                    String doc = leerTexto("Documento: ");
                    System.out.println(recepcion.tieneReserva(doc)
                            ? "El documento " + doc + " ya tiene reserva."
                            : "El documento " + doc + " no tiene reserva.");
                    System.out.println("Reservas registradas: " + recepcion.cantidadReservas());
                    break;
                case 3:
                    recepcion.recibirEnFila(leerComensal());
                    break;
                case 4:
                    Comensal sentado = recepcion.sentarPrimero();
                    if (sentado != null) {
                        salon.anotarComensal(sentado); // pasa de la fila a la mesa
                    }
                    break;
                case 5:
                    Comensal retirado = recepcion.retirarUltimo();
                    if (retirado != null) {
                        System.out.println(retirado + " se fue de la fila.");
                    }
                    break;
                case 6:
                    Comensal siguiente = recepcion.verSiguiente();
                    if (siguiente == null) {
                        System.out.println("No hay nadie esperando.");
                    } else {
                        System.out.println("Sigue: " + siguiente);
                    }
                    System.out.println("Personas esperando: " + recepcion.cuantosEsperan());
                    break;
                case 7:
                    recepcion.mostrarFila();
                    break;
                case 0: break;
                default: System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    // ---------- Salón ----------

    private static void menuSalon() {
        int opcion;
        do {
            System.out.println("\n--- SALÓN ---");
            System.out.println("1. Anotar comensal en la mesa");
            System.out.println("2. Listar comensales por apellido");
            System.out.println("3. Listar comensales en orden de llegada");
            System.out.println("4. Retirar comensal por posición (la primera es 0)");
            System.out.println("5. Cantidad de comensales");
            System.out.println("6. Agregar paso al final de la receta");
            System.out.println("7. Insertar paso después de otro");
            System.out.println("8. Eliminar paso de la receta");
            System.out.println("9. Mostrar receta");
            System.out.println("0. Volver");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1: salon.anotarComensal(leerComensal()); break;
                case 2: salon.listarPorApellido(); break;
                case 3: salon.listarEnOrdenDeLlegada(); break;
                case 4: salon.retirarComensal(leerEntero("Posición: ")); break;
                case 5: System.out.println("Comensales en la mesa: " + salon.cantidadComensales()); break;
                case 6: salon.agregarPaso(leerTexto("Paso: ")); break;
                case 7:
                    String referencia = leerTexto("Paso de referencia: ");
                    String nuevo = leerTexto("Nuevo paso: ");
                    salon.insertarPasoDespuesDe(referencia, nuevo);
                    break;
                case 8: salon.eliminarPaso(leerTexto("Paso a eliminar: ")); break;
                case 9: salon.mostrarReceta(); break;
                case 0: break;
                default: System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    // ---------- Carta ----------

    private static void menuCarta() {
        int opcion;
        do {
            System.out.println("\n--- CARTA ---");
            System.out.println("1. Registrar plato");
            System.out.println("2. Buscar plato");
            System.out.println("3. Eliminar plato");
            System.out.println("4. Listar platos");
            System.out.println("5. Consultar plato (consulta rápida)");
            System.out.println("6. Ver memoria de consulta rápida");
            System.out.println("7. Registrar categoría servida hoy");
            System.out.println("8. Listar categorías servidas y cantidad");
            System.out.println("0. Volver");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    String codigo = leerTexto("Código: ");
                    String nombre = leerTexto("Nombre: ");
                    String categoria = leerTexto("Categoría: ");
                    carta.registrarPlato(new Plato(codigo, nombre, categoria));
                    break;
                case 2:
                    Plato encontrado = carta.buscarPlato(leerTexto("Código: "));
                    if (encontrado != null) System.out.println(encontrado);
                    break;
                case 3: carta.eliminarPlato(leerTexto("Código: ")); break;
                case 4: carta.listarPlatos(); break;
                case 5:
                    Plato consultado = carta.consultarRapido(leerTexto("Código: "));
                    if (consultado != null) {
                        System.out.println(consultado);
                        carta.registrarCategoria(consultado.getCategoria()); // paso 6 del flujo
                    }
                    break;
                case 6: carta.mostrarMemoriaRapida(); break;
                case 7: carta.registrarCategoria(leerTexto("Categoría: ")); break;
                case 8: carta.listarCategorias(); break;
                case 0: break;
                default: System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    // ---------- Cocina ----------

    private static void menuCocina() {
        int opcion;
        do {
            System.out.println("\n--- COCINA ---");
            System.out.println("1. Recibir comanda");
            System.out.println("2. Ver cuál se prepara ahora");
            System.out.println("3. Preparar la siguiente");
            System.out.println("4. Cuántas comandas esperan");
            System.out.println("0. Volver");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    String mesa = leerTexto("Mesa: ");
                    int prioridad = leerEntero("Prioridad (1 express, 2 normal, 3 programada): ");
                    cocina.recibirComanda(mesa, prioridad);
                    break;
                case 2:
                    Comanda proxima = cocina.verSiguiente();
                    if (proxima != null) System.out.println("Se prepara ahora: " + proxima);
                    break;
                case 3:
                    Comanda lista = cocina.prepararSiguiente();
                    if (lista != null) System.out.println("Preparando: " + lista);
                    break;
                case 4: System.out.println("Comandas en espera: " + cocina.cuantasEsperan()); break;
                case 0: break;
                default: System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }
}
