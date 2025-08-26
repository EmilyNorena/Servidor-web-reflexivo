package co.edu.escuelaing.reflexion;
import java.lang.reflect.Method;

public class Reflexion {
    public static void main(String[] args) throws ClassNotFoundException {
        //Class c = "hello".getClass();
        //Class c = Persona.class; //Obtienen la instancia de una clase
        Class c = Class.forName("co.edu.escuelaing.reflexion.Persona");

        Method[] methods = c.getDeclaredMethods();
        String classname = c.getName();
        System.out.println("Métodos de la clase " + classname);

        if(c.isAnnotationPresent(Component.class)) {
            for (Method m : methods) {
                System.out.println(m.getName() + ": " + m.getParameterTypes());
            }
        }
    }
}
