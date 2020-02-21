import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Un objeto de esta clase mantiene una 
 * colección map que asocia  categorías (las claves) con
 * la lista (una colección ArrayList) de cursos que pertenecen a esa categoría 
 * Por ej. una entrada del map asocia la categoría 'BASES DE DATOS"' con
 * una lista de cursos de esa categoría
 * 
 * Las claves en el map se recuperan en orden alfabético y 
 * se guardan siempre en mayúsculas
 * 
 * @author Xavier Izurdiaga
 * 
 */
public class PlataformaCursos
{

    private final String ESPACIO = " ";
    private final String SEPARADOR = ":";
    private TreeMap<String, ArrayList<Curso>> plataforma;

    /**
     * Constructor  
     */
    public PlataformaCursos() {

        plataforma = new TreeMap<>();

    }

    /**
     * añadir un nuevo curso al map en la categoría indicada
     * Si ya existe la categoría se añade en ella el nuevo curso
     * (al final de la lista)
     * En caso contrario se creará una nueva entrada en el map con
     * la nueva categoría y el curso que hay en ella
     * Las claves siempre se añaden en mayúsculas  
     *  
     */
    public void addCurso(String categoria, Curso curso) {
        categoria = categoria.toUpperCase();
        if(plataforma.containsKey(categoria)){
            plataforma.get(categoria).add(curso);
        }else{
            ArrayList<Curso> aux = new ArrayList<>();
            aux.add(curso);
            plataforma.put(categoria.toUpperCase(), aux);
        }
    }

    /**
     *  Devuelve la cantidad de cursos en la categoría indicada
     *  Si no existe la categoría devuelve -1
     *
     */
    public int totalCursosEn(String categoria) {
        int total = -1;
        categoria = categoria.toUpperCase();
        if(plataforma.containsKey(categoria)){
            total = plataforma.get(categoria).size();
            return total;
        }
        return total;
    }

    /**
     * Representación textual de la plataforma (el map), cada categoría
     * junto con el nº total de cursos que hay en ella y a continuación
     * la relación de cursos en esa categoría (ver resultados de ejecución)
     * 
     * De forma eficiente ya que habrá muchas concatenaciones
     * 
     * Usar el conjunto de entradas y un iterador
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Set<String> conjuntoClaves = plataforma.keySet();
        Iterator<String> it = conjuntoClaves.iterator();
        sb.append("Cursos on line ofrecidos por la plataforma\n\n");
        while(it.hasNext()){
            String clave = it.next();
            sb.append(clave).append(" (").append(totalCursosEn(clave)).append(")");
            sb.append("\n");
            sb.append(plataforma.get(clave).toString());
            sb.append("\n\n----------\n");
        }
        return sb.toString();
    }

    /**
     * Mostrar la plataforma
     */
    public void escribir() {

        System.out.println(this.toString());
    }

    /**
     *  Lee de un fichero de texto la información de los cursos
     *  En cada línea del fichero se guarda la información de un curso
     *  con el formato "categoria:nombre:fecha publicacion:nivel"
     *  
     */
    public void leerDeFichero() {

        Scanner sc = new Scanner(
                this.getClass().getResourceAsStream("/cursos.csv"));
        while (sc.hasNextLine())  {
            String lineaCurso = sc.nextLine().trim();
            int p = lineaCurso.indexOf(SEPARADOR);
            String categoria = lineaCurso.substring(0, p).trim();
            Curso curso = obtenerCurso(lineaCurso.substring(p + 1));
            this.addCurso(categoria, curso);
        }

    }

    /**
     *  Dado un String con los datos de un curso
     *  obtiene y devuelve un objeto Curso
     *
     *  Ej. a partir de  "sql essential training: 3/12/2019 : principiante " 
     *  obtiene el objeto Curso correspondiente
     *  
     *  Asumimos todos los valores correctos aunque puede haber 
     *  espacios antes y después de cada dato
     */
    private Curso obtenerCurso(String lineaCurso) {
        lineaCurso.trim();
        String[] aux = lineaCurso.split(SEPARADOR);
        ArrayList<String> linea = new ArrayList<>();
        for(String dato: aux){
            linea.add(dato.trim());
        }
        
        String[] fecha = linea.get(1).split("/");
        
        LocalDate date = LocalDate.of(Integer.valueOf(fecha[2]), Integer.valueOf(fecha[1]), Integer.valueOf(fecha[0]));
        
        return new Curso(linea.get(0), date, Nivel.valueOf(linea.get(2).toUpperCase()));
    }

    /**
     * devuelve un nuevo conjunto con los nombres de todas las categorías  
     *  
     */
    public TreeSet<String> obtenerCategorias() {
        TreeSet<String> categorias = new TreeSet<>();
        Set<String> conjuntoClaves = plataforma.keySet();
        categorias.addAll(conjuntoClaves);
        return categorias;
    }

    /**
     * borra de la plataforma los cursos de la categoría y nivel indicados
     * Se devuelve un conjunto (importa el orden) con los nombres de los cursos borrados 
     * 
     * Asumimos que existe la categoría
     *  
     */

    public TreeSet<String> borrarCursosDe(String categoria, Nivel nivel) {
        TreeSet<String> cursosBorrados = new TreeSet<>();
        
        ArrayList<Curso> cursos = plataforma.get(categoria.toUpperCase());
        Iterator<Curso> it = cursos.iterator();
        
        while(it.hasNext()){
            Curso curso = it.next();
            if(curso.getNivel().equals(nivel)){
                cursosBorrados.add(curso.getNombre());
                it.remove();
            }
        }
        
        return cursosBorrados;
    }

    /**
     *   Devuelve el nombre del curso más antiguo en la
     *   plataforma (el primero publicado)
     */

    public String cursoMasAntiguo() {
        String devuelto = "";
        
        Set<String> categorias = plataforma.keySet();
        Iterator<String> categoria = categorias.iterator();
        
        Curso cursoMasAntiguo = new Curso(" a ",
                LocalDate.now(), Nivel.PRINCIPIANTE);
        
        while(categoria.hasNext()){
            String nombreCategoria = categoria.next();
            
            ArrayList<Curso> cursos = plataforma.get(nombreCategoria.toUpperCase());
            Iterator<Curso> it = cursos.iterator();
            
            while(it.hasNext()){
                Curso curso = it.next();
                if(curso.getFecha().compareTo(cursoMasAntiguo.getFecha()) < 0){
                    cursoMasAntiguo = curso;
                }
            }
        
        }
        return cursoMasAntiguo.getNombre();
    }

    /**
     *  
     */
    public static void main(String[] args) {

        PlataformaCursos plataforma = new PlataformaCursos();
        plataforma.leerDeFichero();
        plataforma.escribir();

        System.out.println(
            "Curso más antiguo: " + plataforma.cursoMasAntiguo()
            + "\n");

        String categoria = "bases de datos";
        Nivel nivel = Nivel.AVANZADO;
        System.out.println("------------------");
        System.out.println(
            "Borrando cursos de " + categoria.toUpperCase()
            + " y nivel "
            + nivel);
        TreeSet<String> borrados = plataforma.borrarCursosDe(categoria, nivel);

        System.out.println("Borrados " + " = " + borrados.toString() + "\n");
        categoria = "cms";
        nivel = Nivel.INTERMEDIO;
        System.out.println(
            "Borrando cursos de " + categoria.toUpperCase()
            + " y nivel "
            + nivel);
        borrados = plataforma.borrarCursosDe(categoria, nivel);
        System.out.println("Borrados " + " = " + borrados.toString() + "\n");
        System.out.println("------------------\n");
        System.out.println(
            "Después de borrar ....");
        plataforma.escribir();

    }
}
