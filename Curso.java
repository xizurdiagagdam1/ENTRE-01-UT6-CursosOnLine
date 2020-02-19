import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.Arrays;

/**
 *  Modela un curso ofrecido por la plataforma
 *  on line
 *  
 *  @autor Xavier Izurdiaga
 *  
 */
public class Curso
{

    private final String ESPACIO = " ";
    private String nombre;
    private LocalDate fecha;
    private Nivel nivel;

    /**
     * Constructor  
     */
    public Curso(String nombre, LocalDate fecha, Nivel nivel) {

        this.nombre = capitalizarNombre(nombre);
        this.fecha = fecha;
        this.nivel = nivel;

    }

    /**
     * Dado un nombre de curso lo devuelve capitalizado, es decir,
     * cada una de las palabras del nombre empiezan por mayúscula y el
     * resto en minúsculas. Se eliminan espacios antes y después del nombre
     * (asumimos entre palabras un único espacio)
     * 
     * Si nombre = " sql essential training " devuelve "Sql Essential Training"
     */
    private String capitalizarNombre(String nombre) {
        StringBuilder sb = new StringBuilder();
        String aux = nombre.trim();
        String[] palabras = aux.split(ESPACIO);
        for(String palabra: palabras){
            sb.append(palabra.substring(0, 1).toUpperCase());
            sb.append(palabra.substring(1) + " ");
        }
        return sb.toString();
    }

    /**
     *  accesor para el nombre
     */
    public String getNombre() {

        return this.nombre;
    }

    /**
     *  accesor para el nivel
     */
    public Nivel getNivel() {

        return this.nivel;
    }

    /**
     *  accesor para la fecha 
     */
    public LocalDate getFecha() {

        return fecha;
    }

    /**
     *  accesor para la fecha formateada
     *  Se devuelve la fecha de publicación del curso como una cadena formateada
     *  de la forma "día nombre-mes año"  Ej. "03 diciembre 2019" 
     */
    public String getFechaFormateada() {
        LocalDate fecha1 = LocalDate.of(fecha.getYear(), fecha.getMonth(), fecha.getDayOfMonth());
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        String strFecha1 = fecha1.format(formateador);
        return strFecha1;
    }

    /**
     *  Representación textual del curso
     */
    public String toString() {

        String str = String.format("%20s: %s\n", "Nombre", this.nombre);
        str += String.format("%20s: %s\n%20s: %s\n", "Publicado desde",
            this.getFechaFormateada(),
            "Nivel", this.nivel.toString());

        str += "\n";
        return str;
    }

    /**
     *  Probar la clase Curso
     */
    public static void main(String[] args) {

        Curso curso1 = new Curso("  sql essential training ",
                LocalDate.of(2019, 12, 3), Nivel.PRINCIPIANTE);
        System.out.println(curso1.toString());
        Curso curso2 = new Curso(" wordpress PLugin development   ",
                LocalDate.of(2011, 11, 30), Nivel.INTERMEDIO);
        System.out.println(curso2.toString());
    }

}
