import java.util.LinkedList;
import java.util.Queue;
import java.util.HashMap;
import java.util.Map;
public class Cine {
    //En el diseño del Formulario existe un label actualice a su Nombre y Apellido
    //No borre ninguna sección del codigo proporcionado,
    // Si borra tendrá la nota minima 1.0
    //Se necesita completar las clases Cine y Formulario
    //Con los algoritmos y métodos necesarios de la estructura FIFO
    //Enunciado: Cada sala de cine tiene una capacidad de 25 espacios, solo se
    //pueden comprar como máximo 4 entradas por cliente a una pelicula.
    //Desplegar cada una de las compras en el textArea.
    //Se necesita consultar cuántos espacios quedan disponibles por cada una
    //de las peliculas.
    //Rubrica de calificación:
    //Completar la clase Cine 4 Puntos.
    //Agregar elemento a la Cola y mostrar en el textArea 2 Puntos.
    //Programar los botones para conocer la cantidad de espacios disponibles 4 Puntos.
    //Suba su programa a la plataforma hasta que finalice la hora.

    //Se declara el mapa para realizar un seguimiento de las entradas vendidas por película
    Map<String, Integer> entradasVendidasXPelicula = new HashMap<>();

    //Declare la referencia Cola para almacenar asistentes
    Queue<Asistente> asistentes;
    public Cine(){
    //inicialice el objeto
        asistentes = new LinkedList<Asistente>();
        entradasVendidasXPelicula = new HashMap<>();

    }
    public Queue<Asistente> getAsistentes(){
        return asistentes;
    }
    public boolean esVacia(){
     // cambie el método para que funcione correctamente
        return asistentes.isEmpty();
    }

    public int tamanio(){
        //actualice la sentencia para que devuelva el tamaño de elementos en la cola
        return asistentes.size();
    }

    public void encolar(String pelicula, int cantidad) {
        // Verifica si el cliente ya ha comprado entradas para esta película
        int entradasVendidas = entradasVendidasXPelicula.getOrDefault(pelicula, 0);
        int entradasPorCliente = asistentes.stream()
                .filter(asistente -> asistente.getPelicula().equals(pelicula))
                .mapToInt(Asistente::getCantidad)
                .sum();

        int entradasRestantes = 4 - entradasPorCliente;

        // Restablece la restricción de 4 entradas por cliente
        if (entradasRestantes > 0) {
            // Verificar que no se exceda la capacidad total de 25 asistentes
            if (asistentes.size() < 25) {
                // Añadir la compra del nuevo cliente
                asistentes.add(new Asistente(pelicula, cantidad));
                // Actualizar el seguimiento de las entradas vendidas
                entradasVendidasXPelicula.put(pelicula, entradasVendidas + cantidad);
            } else {
                System.out.println("La sala está llena, no se pueden agregar más asistentes.");
            }
        } else {
            System.out.println("No se pueden comprar más entradas para " + pelicula + ". Limite de 4 entradas por cliente.");
        }
    }

    public Object desencolar() throws Exception{
       //actualice el método para eliminar el elemento del inicio.
        if (asistentes.isEmpty()){
            throw new Exception("Cola vacía");
        }
        return asistentes.poll() ;
    }

    //cree un método para listar los elementos en una textArea.
    public String listarElementos(){
        String mensaje="";
        for(Asistente a: asistentes){
            mensaje += a.toString() + "\n";
        }
        return mensaje;
    }

    //cree un método para conocer la cantidad de entradas disponibles
    public int espaciosDisponibles(String pelicula){
        int entradasVendidas = entradasVendidasXPelicula.getOrDefault(pelicula, 0);
        return 25 - entradasVendidas;
    }

}
