# Challenge ONE | Back End | Foro Alura

Este proyecto trata de una API REST creada con Spring Boot que proporciona las funcionalidades básicas de un foro, centrándose principalmente en los tópicos.

### Funcionalidades principales:

La API utiliza Spring Boot, un framework de desarrollo de aplicaciones Java, para facilitar la creación de la API REST.

- Creación de tópicos:

 Permite a los usuarios crear nuevos tópicos proporcionando un título, un mensaje y asociándolos a un curso y autor existente.

- Obtención de tópicos: 

Los usuarios pueden obtener una lista de todos los tópicos disponibles, incluyendo información relevante como el título, el mensaje, la fecha de creación, el estado, el autor y el curso asociado.

- Actualización de tópicos: 

Permite a los usuarios actualizar el contenido de un tópico existente, incluyendo el título y el mensaje.

- Cambio de estado de tópicos: 

Los usuarios pueden cambiar el estado de un tópico, marcándolo como activo, cerrado o eliminado.

- Eliminación de tópicos: 

Permite a los usuarios eliminar un tópico existente.

Además de las funcionalidades relacionadas con los tópicos, la API también incluye endpoints para gestionar usuarios, cursos y respuestas. Se integra con una base de datos, que cuenta con las siguientes tablas:

-   "usuarios": Almacena la información de los usuarios, como el nombre, el email y la contraseña.
-   "respuestas": Almacena las respuestas a los tópicos, incluyendo el mensaje, la fecha de creación, el autor y si es una solución.
-   "cursos": Almacena la información de los cursos, como el nombre y la categoría.
-   "topicos": Almacena la información de los tópicos, incluyendo el título, el mensaje, la fecha de creación, el estado, el autor y el curso asociado. También tiene relaciones con las tablas de usuarios y cursos.

Este proyecto tiene como objetivo proporcionar una base sólida para desarrollar un sistema de gestión de foros más completo, permitiendo a los usuarios interactuar y compartir conocimientos en un entorno educativo.

### Herramientas utilizadas

  - [IntelliJ IDEA](https://www.eclipse.org/)
  - [MySql](https://www.mysql.com/)
  - [Java](https://www.java.com/en/)

  - [Spring Security](https://start.spring.io/)
  - [Token JWT](https://jwt.io/)
  - [Insomnia]()

### Funcionamiento 

- [Funcion 1](https://raw.githubusercontent.com/Agathion-JD/Assets/main/foro_alura/Captura.PNG)
- [Funcion 2](https://raw.githubusercontent.com/Agathion-JD/Assets/main/foro_alura/Captura2.PNG)


- [Funcion 3](https://raw.githubusercontent.com/Agathion-JD/Assets/main/foro_alura/Captura5.PNG)
- [Funcion 4](https://raw.githubusercontent.com/Agathion-JD/Assets/main/foro_alura/Captura6.PNG)
- [Funcion 5](https://raw.githubusercontent.com/Agathion-JD/Assets/main/foro_alura/Captura7.PNG)
- [Funcion 6](https://raw.githubusercontent.com/Agathion-JD/Assets/main/foro_alura/Captura8.PNG)

### AGRADACIMIENTO

- [ALURA LATAM][(https://camo.githubusercontent.com/c00f87aeebbec37f3ee0857cc4c20b21fefde8a96caf4744383ebfe44a47fe3f/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f2d4c696e6b6564496e2d2532333030373742353f7374796c653d666f722d7468652d6261646765266c6f676f3d6c696e6b6564696e266c6f676f436f6c6f723d7768697465)](https://www.linkedin.com/company/alura-latam/mycompany/)
- [ORACLE][(https://camo.githubusercontent.com/c00f87aeebbec37f3ee0857cc4c20b21fefde8a96caf4744383ebfe44a47fe3f/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f2d4c696e6b6564496e2d2532333030373742353f7374796c653d666f722d7468652d6261646765266c6f676f3d6c696e6b6564696e266c6f676f436f6c6f723d7768697465)](https://www.linkedin.com/company/oracle/)