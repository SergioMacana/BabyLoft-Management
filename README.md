# Baby Loft Management

## Descripción

Baby Loft Management es una aplicación móvil Android para la gestión interna de pedidos, clientes, productos y procesos de producción del emprendimiento Baby Loft, dedicado a la confección y comercialización de ropa y productos personalizados para bebés.

La aplicación busca centralizar la información de los pedidos y facilitar el seguimiento de su ciclo de vida, desde el registro del cliente y la selección de productos hasta la actualización del estado de producción y entrega.

## Objetivo

Desarrollar una herramienta de gestión móvil que permita organizar los pedidos, administrar el catálogo de productos, configurar materiales y consultar el estado de producción desde una interfaz sencilla y adaptada a las necesidades de Baby Loft.

## Funcionalidades principales

- Consulta del panel de administración y resumen de pedidos.
- Creación y consulta de pedidos.
- Registro de información de clientes.
- Gestión de productos y categorías.
- Administración de telas, colores y tallas.
- Seguimiento de estados de los pedidos.
- Consulta de pedidos enviados y archivados.
- Persistencia local mediante Room.

## Tecnologías

- Kotlin
- Android Studio
- Jetpack Compose
- Material 3
- Navigation Compose
- Room
- Kotlin Symbol Processing (KSP)
- Gradle

## Estado del proyecto

El proyecto cuenta con una interfaz Android desarrollada en Jetpack Compose y una capa de persistencia local con Room. La arquitectura de servidor central y sincronización entre dispositivos se encuentra dentro de la planificación de evolución del sistema.

Las funcionalidades se documentan según su estado real de implementación y pruebas.

## Documentación

- [Ideas y justificación](docs/ideas.md)
- [Funcionalidades](docs/funcionalidades.md)
- [Diseño de interfaz](docs/ui.md)
- [Alcance y requisitos](docs/01-alcance-y-requisitos.md)
- [Arquitectura](docs/02-arquitectura.md)
- [Base de datos](docs/03-base-de-datos.md)
- [Módulos y pantallas](docs/04-modulos-y-pantallas.md)
- [Configuración y ejecución](docs/05-configuracion-y-ejecucion.md)
- [Pruebas](docs/06-pruebas.md)
- [Sincronización y servidor](docs/07-sincronizacion-y-servidor.md)
- [Seguridad y copias de seguridad](docs/08-seguridad-y-copias.md)
- [Hoja de ruta](docs/09-hoja-de-ruta.md)
- [Decisiones técnicas](docs/10-decisiones-tecnicas.md)

## Autores

- Sergio Macana
- Kevin Ospina