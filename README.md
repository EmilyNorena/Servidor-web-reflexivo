#  🌐Servidor web reflexivo 

### Arquitectura Empresarial  
**Autora:** Emily Noreña Cardozo  
**Fecha:** 30 de agosto de 2025  


## Taller  
**Servidores de Aplicaciones, Meta protocolos de objetos, Patrón IoC, Reflexión**  

---

## Descripción del proyecto  
Este proyecto implementa un servidor web en Java, utilizando únicamente librerías. El servidor cumple con las siguientes características: 

- Atiende múltiples solicitudes seguidas de forma no concurrente.  
- Archivos estáticos (HTML, CSS, JS, imágenes) servidos desde un directorio configurable.
- Comunicación asíncrona con servicios REST (métodos GET y POST).
- Rutas REST dinámicas mediante expresiones lambda.
- Manejo de parámetros de consulta (query params) en las solicitudes.

---

## Scaffolding
<pre> 
src        
├───main
│   ├───java
│   │   └───com
│   │       └───mycompany
│   │           ├───annotations
│   │           │       GetMapping.java
│   │           │       RequestParam.java
│   │           │       RestController.java
│   │           │
│   │           └───httpserver
│   │                   ApiHandler.java
│   │                   FileHandler.java
│   │                   HelloController.java
│   │                   MicroSpringBoot.java
│   │                   Request.java
│   │                   RequestHandler.java
│   │                   Response.java
│   │                   Route.java
│   │                   Router.java
│   │                   WebServer.java
│   │
│   └───resources
│       └───webroot
│               404.html
│               favicon.ico
│               img.png
│               index.html
│               koala.jpg
│               script.js
│               styles.css
│
└───test
    └───java
        └───com
            └───mycompany
                └───httpserver
                        WebServerTest.java
</pre>

---

## ¿Cómo ejecutar?
Para correr el proyecto localmente, debes tener instalado:
1. Java (versión 17 https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html). Puedes verificar la versión ejecutando en la terminal: <pre> java -version </pre>
2. Maven (https://maven.apache.org/download.cgi). Puedes verificar la versión ejecutando en la terminal: <pre> mvn -v </pre>
3. Git (https://git-scm.com/downloads). Puedes verificar la versión ejecutando en la terminal: <pre> git --version </pre>

Posterior a esto, es necesario clonar el repositorio de la siguiente manera:
<pre> https://github.com/EmilyNorena/Servidor-web-reflexivo.git </pre>

Finalmente, sigue estos pasos:
1. Construye el proyecto: <pre>mvn clean package</pre>
   La salida debe ser BUILD SUCCESS.
2. Ejecuta la aplicación: <pre> java -cp target/classes com.mycompany.httpserver.WebServer </pre>
   La consola debe mostrar el siguiente mensaje: Server started on port 8080.

---

## ¿Cómo finalizar un proceso que está utilizando el puerto 8080?
### En Windows
1. Identifica el proceso que ocupa el puerto: <pre> netstat -ano | findstr :8080 </pre>
El último número de la línea corresponde al PID del proceso.
   
2. Finaliza el proceso con el PID: <pre> taskkill /PID PID /F </pre>

### En Linux
1. Identifica el proceso que ocupa el puerto: <pre> lsof -i :8080 </pre>
2. <pre> kill -9 PID </pre>

---

## ¿Qué debes ver?
En tu navegador busca http://localhost:8080


<img width="1907" height="1011" alt="image" src="https://github.com/user-attachments/assets/1efe09d8-82b9-407b-818f-9d43ca7bd77d" />



Si buscas un recurso inexistente, verás esta página



<img width="1907" height="919" alt="image" src="https://github.com/user-attachments/assets/1a3c89f6-2973-4dea-a9d9-d1dfa5ff6bd0" />

---

## Ejemplos de uso

**Para servicios REST**

Este fragmento de código configura un servidor web en Java con soporte para archivos estáticos y servicios REST.

<img width="700" height="121" alt="image" src="https://github.com/user-attachments/assets/4684408a-68ae-4454-8694-1345416826a0" />
 
- Los archivos estáticos se sirven desde la carpeta target/classes/webroot (o desde la ruta definida en staticFilesPath).
- Se exponen endpoints REST con el método GET a través de la clase Router:
    - /api/helloworld → retorna el texto fijo "hello world!".
    - /api/hello?name=Pedro → retorna un saludo personalizado con el valor recibido por parámetro ("hello Pedro").
    - /api/pi → retorna el valor numérico de π (3.141592653589793).

De esta forma, el servidor puede manejar tanto contenido estático (HTML, CSS, JS) como peticiones dinámicas mediante endpoints REST.

<img width="400" height="240" alt="image" src="https://github.com/user-attachments/assets/fd67dd6f-0c5d-4d1b-aefb-984b0f6c6fce" />
<img width="400" height="303" alt="image" src="https://github.com/user-attachments/assets/2baa6d44-6ffc-4aaf-8a8a-c3898523e58e" /><br>

<br>**Para servicios cargar POJO's desde la línea de comandos**

<img width="450" height="185" alt="image" src="https://github.com/user-attachments/assets/19c4faf2-ec3b-4dd3-a48b-a7e7115141f3" />

MicroSpringBoot: Es el punto de entrada. Recibe como argumento(s) el nombre de la clase a cargar en tiempo de ejecución. 

<img width="340" height="175" alt="image" src="https://github.com/user-attachments/assets/c7c9862f-8f89-4fe5-a172-e05134f1729f" />

HelloController: Es un POJO con anotaciones que define un servicio web. Describe qué ruta HTTP publicar y qué respuesta dar.

- @RestController: Marca la clase como un componente web que debe ser cargado por MicroSpringBoot.
- @GetMapping("/"): Marca el método index() como un endpoint HTTP GET.

Cargando HelloController desde la línea de comandos:

<pre>java -cp target/classes com.mycompany.httpserver.MicroSpringBoot com.mycompany.httpserver.HelloController</pre>

---


## Arquitectura
<img width="860" height="746" alt="image" src="https://github.com/user-attachments/assets/98565b94-b48e-498c-a8dc-3118c373272b" />

1. WebServer: 
   
   - Inicializa el servidor en el puerto 8080.
   - Acepta conexiones entrantes con ServerSocket.
   - Procesa una sola conexión a la vez.
   - Delega la solicitud entrante RequestHandler.
   - Permite detener el servidor de forma controlada.
   - Permite definir un directorio base con staticfiles(String path)
   - Registra rutas dinámicas
  
     
2. RequestHandler: 
   
   - Recibe el Socket de un cliente y gestiona su ciclo de vida.
   - Lee y parsea la petición HTTP (método, path, headers, query params).
   - Determina si la solicitud es de tipo estática (archivos) o dinámica (API).
   - Crea Request para encapsular path y query params.
   - Usa Response para configurar status code, headers y body antes de enviarlo al cliente.
  
3. FileHandler

   - Localiza archivos en el directorio establecido.
   - Protege contra ataques directory traversal.
   - Determina el Content-Type según la extensión.
   - Sirve contenido texto (HTML, CSS, JS) con UTF-8 o binario (imágenes).
   - Maneja errores como 404 Not Found.

4. ApiHandler

   - Procesa peticiones dirigidas a rutas /api/.
   - Enruta según el path y el método HTTP (GET, POST).
   - Lee query params.
   - Genera respuestas JSON.

5. Router

   - Permite registrar y buscar rutas dinámicas.
   - Implementa un mecanismo de búsqueda de rutas según el path solicitado.

6. Route (Interfaz funcional)

   - Cualquier ruta debe implementar handle(Request, Response).
   - Usa funciones lambda.
   - Asegura que cada ruta pueda acceder tanto a la petición como a la respuesta.
  
7. Request

   - Representa una petición HTTP
   - Separa el path de los parámetros de consulta (query params).
   
8. Response

   - Encapsula todos los detalles de la respuesta HTTP: código de estado, cabeceras y cuerpo.
   - Soporta tanto respuestas de texto como binarias (HTML e imágenes).
   - Es responsable de formatear y enviar la respuesta completa al cliente siguiendo el protocolo HTTP.


---

## Diagrama de clases

<img width="1310" height="751" alt="image" src="https://github.com/user-attachments/assets/5c19ddf2-33ab-4171-ae68-878e08f08740" />


### Relaciones entre clases
- WebServer -> ServerSocket: La clase WebServer utiliza ServerSocket para escuchar conexiones entrantes de clientes en un puerto específico.
- WebServer -> RequestHandler: Por cada cliente que se conecta, WebServer crea un objeto RequestHandler. RequestHandler depende de WebServer para su creación, pero no forma parte permanente del WebServer.
- RequestHandler -> FileHandler: RequestHandler utiliza FileHandler para servir archivos estáticos solicitados por el cliente.
- RequestHandler -> ApiHandler: RequestHandler utiliza ApiHandler para procesar solicitudes a endpoints de la API.
- RequestHandler -> Request: RequestHandler crea un Request a partir de la petición y los parámetros definidos.
- RequestHandler -> Response: RequestHandler inicializa un Response, el cual contiene código de estados, cabecera y cuerpo.
- RequestHandler -> Router: RequestHandler consulta al Router para saber si existe un Route registrado para el path solicitado.
- Router -> Route: Router mantiene un mapa de rutas y las asocia a objetos Route, que son funciones lambda.
  
---

## Pruebas
<img width="800" height="262" alt="image" src="https://github.com/user-attachments/assets/270e8287-ab45-4dbb-839e-f1de3644f8e9" />


1. <pre>shouldLoadStaticFileHtml</pre> Verifica que el servidor retorne código 200 (OK) al solicitar un archivo HTML existente (index.html).

2. <pre>notShouldLoadStaticFileHtml</pre> Confirma que el servidor retorne 404 (No encontrado) al solicitar un archivo HTML inexistente.

3. <pre>shouldLoadStaticFileCss </pre> Comprueba que el servidor sirve correctamente archivos CSS existentes (código 200).

4. <pre> notShouldLoadStaticFileCss </pre> Valida el manejo de archivos CSS inexistentes (código 404).

5. <pre> shouldLoadStaticFileJs </pre> Testea la correcta entrega de archivos JavaScript existentes.

6. <pre> notShouldLoadStaticFileJs </pre> Verifica el comportamiento con archivos JS que no existen.

7. <pre> shouldLoadStaticImagePNG </pre> Confirma que el servidor puede servir imágenes PNG existentes.

8. <pre> shouldLoadStaticImageJPG </pre> Similar a la anterior pero para imágenes JPG/JPEG.

9. <pre> notShouldLoadStaticImagePNG </pre> Valida el código 404 para imágenes PNG inexistentes.

10. <pre> notShouldLoadStaticImageJPG </pre> Igual que la anterior pero para formato JPG.

11. <pre> shouldLoadRestGet </pre> Prueba un endpoint REST con método GET, esperando respuesta exitosa (200).

12. <pre>shouldLoadRestPost </pre> Verifica el comportamiento de un endpoint REST con método POST, esperando respuesta exitosa (200).

13. <pre>shouldLoadHelloRouteWithQueryParam </pre> Verifica que el endpoint /api/hello con parámetro name=Pedro se cargue correctamente y devuelva estado 200 OK.

14. <pre>shouldReturnCorrectResponseFromHelloRoute </pre> Comprueba que la respuesta del endpoint /api/hello?name=Pedro contenga el saludo personalizado "hello Pedro".

15. <pre>shouldLoadPiRoute </pre> Valida que el endpoint /api/pi responda correctamente con estado 200 OK.

16. <pre>shouldReturnCorrectValueFromPiRoute </pre> Verifica que la respuesta del endpoint /api/pi coincida exactamente con el valor de Math.PI.

17. <pre>shouldServeStaticFileFromCustomDirectory </pre> Comprueba que se sirvan archivos estáticos desde el directorio configurado, por ejemplo index.html, devolviendo 200 OK.

18. <pre>shouldReturn404ForUnregisteredRoute </pre> Asegura que cualquier ruta no registrada (como unknown/route) devuelva correctamente un error 404 Not Found.


