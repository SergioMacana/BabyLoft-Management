# 01. Alcance y requisitos del sistema — Baby Loft Management

## 1. Introducción

Baby Loft Management es una aplicación móvil para la administración interna de Baby Loft, un emprendimiento dedicado a la elaboración y comercialización de ropa, accesorios y productos para bebés.

La aplicación busca centralizar la información relacionada con los clientes, los pedidos, los productos y los materiales utilizados durante el proceso de fabricación. Su propósito es facilitar el seguimiento de cada pedido desde su registro inicial hasta su preparación y envío, permitiendo consultar la información necesaria para organizar el trabajo diario del negocio.

El proyecto se desarrolla para dispositivos Android y utiliza Kotlin, Jetpack Compose y Room como parte de su tecnología actual. Room permite almacenar información localmente en el dispositivo, mientras que la interfaz está organizada en diferentes pantallas para consultar pedidos, registrar nuevas órdenes, revisar productos, administrar materiales y visualizar información general del negocio.

A futuro, se contempla ampliar la aplicación para permitir el trabajo desde varios dispositivos, incorporar un servidor central y sincronizar la información entre los equipos. Estas capacidades se consideran parte de la evolución del proyecto y no deben entenderse como funcionalidades terminadas hasta que su implementación y funcionamiento hayan sido comprobados.

## 2. Problema que se busca resolver

La gestión de pedidos de un negocio que fabrica productos personalizados puede implicar el manejo de diferentes datos, como información de los clientes, productos solicitados, cantidades, materiales, colores, telas, personalizaciones y estados de producción.

Cuando esta información se administra de manera dispersa o mediante procesos manuales, pueden presentarse dificultades como:

- Pérdida de información relacionada con los pedidos.
- Dificultad para consultar rápidamente el estado de una orden.
- Falta de centralización de los datos de los clientes.
- Confusión entre pedidos nuevos, pedidos en producción y pedidos terminados.
- Dificultad para consultar los productos y materiales disponibles.
- Mayor esfuerzo para mantener un historial organizado de las órdenes.
- Riesgo de inconsistencias al actualizar información en diferentes lugares.

Baby Loft Management busca reducir estas dificultades mediante una aplicación que permita organizar la información en un único entorno de gestión y facilite el seguimiento de las actividades relacionadas con los pedidos y la producción.

## 3. Objetivos del sistema

### 3.1. Objetivo general

Desarrollar una aplicación móvil para Android que facilite la administración de pedidos, clientes, productos y materiales de Baby Loft, proporcionando herramientas para registrar órdenes, consultar información, realizar seguimiento a los estados de producción y mantener organizada la información operativa del negocio.

### 3.2. Objetivos específicos

- Centralizar el registro y la consulta de los pedidos del negocio dentro de la aplicación.
- Facilitar el almacenamiento y la consulta de la información de los clientes.
- Permitir registrar pedidos con sus respectivos productos y cantidades.
- Organizar el seguimiento de las órdenes mediante estados que representen su avance.
- Facilitar la consulta de productos y categorías del catálogo.
- Permitir administrar atributos relacionados con materiales, telas, colores y otras características configurables.
- Proporcionar pantallas para consultar información general y pedidos pendientes.
- Mantener los datos almacenados localmente mediante Room.
- Establecer una base técnica que permita incorporar posteriormente un servidor central y sincronización entre dispositivos.
- Facilitar el mantenimiento y la ampliación futura de la aplicación mediante una organización modular del código.

## 4. Alcance del proyecto

### 4.1. Funcionalidades contempladas

El alcance funcional de Baby Loft Management comprende los siguientes módulos:

| Módulo | Descripción |
|---|---|
| Panel administrativo | Presentar información general relacionada con la gestión de pedidos y el funcionamiento del negocio. |
| Gestión de pedidos | Consultar las órdenes registradas y acceder a sus detalles. |
| Creación de pedidos | Registrar una nueva orden con la información correspondiente al cliente y los productos solicitados. |
| Detalle del pedido | Consultar la información de una orden específica. |
| Seguimiento de estados | Organizar los pedidos según su etapa de trabajo. |
| Catálogo de productos | Consultar y organizar productos y categorías del negocio. |
| Pedidos archivados | Consultar órdenes que se encuentren en la sección de pedidos archivados, de acuerdo con las reglas implementadas. |
| Gestión de materiales | Administrar o consultar atributos relacionados con los materiales y las características de los productos. |
| Persistencia local | Almacenar información en una base de datos local mediante Room. |
| Evolución hacia sincronización | Preparar el proyecto para una futura integración con un servidor central y varios dispositivos. |

La existencia de una pantalla o módulo no significa necesariamente que todas sus funcionalidades estén completas. El estado real de cada función debe verificarse en el código y mediante pruebas de ejecución.

### 4.2. Estados de los pedidos

La aplicación contempla los siguientes estados para organizar el avance de las órdenes:

| Estado | Descripción |
|---|---|
| `NUEVOS` | Pedidos registrados que todavía no han avanzado a la etapa de producción. |
| `EN_PRODUCCION` | Pedidos que se encuentran en proceso de elaboración. |
| `LISTOS` | Pedidos cuya elaboración ha finalizado y que están pendientes de la siguiente etapa operativa, según el proceso del negocio. |
| `ENVIADOS` | Pedidos que han sido despachados al cliente. |

Estos estados permiten representar las etapas principales del ciclo de vida de una orden. Las reglas específicas para cambiar de un estado a otro deben mantenerse consistentes con el comportamiento implementado en la aplicación.

### 4.3. Alcance técnico actual

La implementación actual del proyecto está orientada a una aplicación Android con almacenamiento local.

Las tecnologías principales contempladas son:

- **Lenguaje de programación:** Kotlin.
- **Interfaz de usuario:** Jetpack Compose.
- **Base de datos local:** Room.
- **Plataforma:** Android.
- **Entorno de desarrollo:** Android Studio.
- **Sistema de construcción:** Gradle.
- **Organización del código:** pantallas, entidades, objetos de acceso a datos (DAO), repositorios y configuración de la base de datos.

El almacenamiento local permite conservar información en el dispositivo, pero no representa por sí mismo un sistema de sincronización entre varios teléfonos.

### 4.4. Funcionalidades futuras

Se contempla una evolución del sistema para incorporar capacidades adicionales:

- Servidor central para almacenar la información compartida del negocio.
- API para la comunicación entre la aplicación Android y el servidor.
- Base de datos centralizada para consultar los mismos datos desde varios dispositivos.
- Sincronización de cambios realizados sin conexión.
- Envío de los cambios pendientes cuando el dispositivo recupere conectividad.
- Identificación de conflictos cuando dos dispositivos modifiquen el mismo registro.
- Autenticación y autorización de los usuarios.
- Copias de seguridad de la información central.
- Posible acceso remoto al servidor desde redes externas.

Estas funcionalidades deberán implementarse, probarse y documentarse por separado antes de considerarse disponibles para uso operativo.

## 5. Actores del sistema

Los siguientes actores representan los perfiles que podrían intervenir en el proceso de gestión. Su existencia describe las necesidades del negocio y no implica que actualmente haya un sistema completo de roles y permisos.

### 5.1. Administrador

Es la persona encargada de gestionar la información general del negocio y supervisar el estado de los pedidos.

Sus responsabilidades pueden incluir:

- Consultar el panel administrativo.
- Registrar y revisar pedidos.
- Consultar la información de los clientes.
- Revisar productos y materiales.
- Hacer seguimiento a las órdenes.
- Consultar pedidos finalizados o archivados, según las funcionalidades disponibles.

### 5.2. Responsable de producción

Es la persona encargada de elaborar los productos solicitados por los clientes.

Sus responsabilidades dentro del proceso del negocio pueden incluir:

- Consultar los pedidos pendientes de elaboración.
- Revisar los productos y sus especificaciones.
- Identificar telas, colores y materiales requeridos.
- Consultar el estado de las órdenes.
- Informar o registrar el avance de producción, de acuerdo con las funciones disponibles.

Este perfil se describe como parte del proceso operativo. La aplicación no debe considerarse un sistema con permisos diferenciados para producción hasta que dichos permisos estén implementados.

### 5.3. Responsable de ventas o registro de pedidos

Es la persona que recibe los pedidos de los clientes y registra la información necesaria para su elaboración.

Sus responsabilidades pueden incluir:

- Registrar los datos del cliente.
- Ingresar los productos solicitados.
- Especificar cantidades y características.
- Registrar las observaciones necesarias para la fabricación.
- Consultar el estado de los pedidos registrados.

Este actor puede coincidir con el administrador cuando una misma persona realiza ambas actividades.

### 5.4. Sistema

Corresponde a los componentes de software que permiten almacenar, consultar y organizar la información.

Sus responsabilidades incluyen:

- Validar los datos según las reglas implementadas.
- Guardar información en la base de datos local.
- Recuperar información para presentarla en las pantallas.
- Mantener las relaciones entre los registros.
- Ejecutar las operaciones de consulta y modificación de datos.

En una futura versión con servidor, el sistema también deberá gestionar la comunicación con la API, la sincronización y el control de conflictos.

## 6. Requisitos funcionales

Los requisitos funcionales describen las acciones que el sistema debe permitir. Para evitar confundir las funcionalidades previstas con las ya terminadas, se debe registrar su estado de implementación y comprobarlo mediante pruebas.

| Código | Requisito funcional | Criterio de aceptación |
|---|---|---|
| RF-01 | Consultar el panel administrativo. | El usuario puede acceder al panel y visualizar la información que la aplicación tenga configurada para presentar. |
| RF-02 | Consultar los pedidos registrados. | El usuario puede acceder a la lista de pedidos y consultar la información disponible de cada orden. |
| RF-03 | Registrar un pedido. | El usuario puede ingresar la información requerida para crear una orden y guardarla cuando los datos cumplan las validaciones establecidas. |
| RF-04 | Consultar el detalle de un pedido. | Al seleccionar una orden, se presenta la información asociada a ella, incluyendo los datos disponibles del cliente y los productos solicitados. |
| RF-05 | Gestionar el estado de un pedido. | El usuario puede consultar el estado de una orden y actualizarlo mediante las opciones que estén implementadas. |
| RF-06 | Consultar el catálogo de productos. | El usuario puede acceder al módulo de productos y consultar los registros disponibles. |
| RF-07 | Consultar categorías de productos. | El sistema permite relacionar los productos con sus categorías, de acuerdo con la estructura de datos implementada. |
| RF-08 | Gestionar atributos y materiales. | El usuario puede consultar o modificar los atributos que el módulo permita administrar, según las operaciones disponibles. |
| RF-09 | Consultar pedidos archivados. | El usuario puede acceder a la sección de pedidos archivados y consultar las órdenes que cumplan las condiciones definidas por la aplicación. |
| RF-10 | Mantener la información relacionada con los pedidos. | La base de datos local permite almacenar y recuperar las entidades relacionadas con clientes, pedidos, productos, elementos del pedido e historial de estados, según las operaciones implementadas. |
| RF-11 | Consultar la información después de cerrar y abrir la aplicación. | Los datos que se hayan guardado correctamente en Room continúan disponibles después de reiniciar la aplicación, siempre que no se hayan eliminado sus datos locales. |
| RF-12 | Validar la información ingresada. | El sistema impide o informa los datos inválidos que estén contemplados en las validaciones implementadas. |
| RF-13 | Registrar cambios en el historial de estados. | Cuando el flujo implementado registra una transición de estado, se conserva la información correspondiente en el historial de la orden. |
| RF-14 | Sincronizar información entre dispositivos. | En una futura versión, los cambios realizados en un dispositivo pueden enviarse al servidor central y quedar disponibles para los demás dispositivos autorizados. |
| RF-15 | Trabajar sin conexión y sincronizar posteriormente. | En una futura versión, las operaciones compatibles pueden realizarse sin conexión y sincronizarse cuando el servidor vuelva a estar disponible. |

**Importante:** RF-14 y RF-15 son requisitos futuros. No deben marcarse como cumplidos mientras no exista una implementación funcional de servidor y sincronización.

Los criterios de aceptación de los demás requisitos deben comprobarse con la versión real de la aplicación. La documentación describe el comportamiento esperado, no constituye por sí sola evidencia de que cada requisito esté implementado.

## 7. Requisitos no funcionales

Los requisitos no funcionales describen condiciones de calidad, seguridad, facilidad de uso y mantenimiento que debe cumplir el sistema.

### RNF-01. Usabilidad

La aplicación debe ofrecer una interfaz comprensible y organizada, que permita al personal de Baby Loft consultar pedidos y registrar información sin realizar procesos innecesariamente complejos.

**Criterio de aceptación:** un usuario puede identificar las opciones principales y completar las tareas habituales sin necesitar conocimientos técnicos de programación.

### RNF-02. Persistencia de datos

La información guardada correctamente debe permanecer disponible después de cerrar y volver a abrir la aplicación.

**Criterio de aceptación:** los registros almacenados localmente pueden consultarse nuevamente después de reiniciar la aplicación, siempre que no se borren los datos de la aplicación ni se desinstale.

### RNF-03. Integridad de la información

Las relaciones entre clientes, pedidos, productos, elementos del pedido e historial de estados deben mantenerse consistentes.

**Criterio de aceptación:** las operaciones de almacenamiento y consulta respetan las relaciones y restricciones definidas en la base de datos.

### RNF-04. Mantenibilidad

El código debe estar organizado de forma que sea posible modificar módulos, corregir errores y agregar nuevas funcionalidades sin tener que reescribir innecesariamente toda la aplicación.

**Criterio de aceptación:** las responsabilidades de la interfaz, el acceso a datos y la lógica de los repositorios se mantienen separadas de acuerdo con la estructura del proyecto.

### RNF-05. Compatibilidad

La aplicación debe construirse para las versiones de Android definidas en la configuración del proyecto y utilizar las dependencias compatibles con el entorno de desarrollo establecido.

**Criterio de aceptación:** la aplicación puede compilarse y ejecutarse en los dispositivos o emuladores compatibles con la configuración del proyecto. Este requisito debe validarse mediante pruebas de construcción y ejecución.

### RNF-06. Rendimiento

Las operaciones habituales, como abrir la lista de pedidos, consultar detalles y guardar registros, deben realizarse de manera fluida para el volumen de información esperado en el negocio.

**Criterio de aceptación:** las pantallas y operaciones principales no presentan demoras que dificulten el uso normal de la aplicación en los dispositivos objetivo.

### RNF-07. Seguridad de la información

La aplicación debe proteger la información comercial y los datos de los clientes de acuerdo con las capacidades disponibles en cada etapa del proyecto.

**Criterio de aceptación:** antes de utilizar la aplicación en producción, se deben evaluar los riesgos relacionados con el acceso físico al dispositivo, el almacenamiento local, las copias de seguridad y el acceso remoto.

La autenticación, los roles de usuario, la protección de comunicaciones y los permisos de acceso centralizados deberán definirse e implementarse en la etapa correspondiente.

### RNF-08. Recuperación y respaldo

El sistema debe contar con una estrategia para reducir el riesgo de pérdida de información.

**Criterio de aceptación:** antes de depender de la aplicación para la operación diaria, debe definirse y probarse un procedimiento de respaldo y recuperación de los datos relevantes.

La base de datos Room local no equivale a una copia de seguridad centralizada.

### RNF-09. Funcionamiento sin conexión

La arquitectura prevista debe permitir que las funciones que dependan únicamente de los datos locales continúen disponibles sin conexión a internet.

**Criterio de aceptación:** las operaciones locales compatibles pueden utilizarse sin conectividad. La disponibilidad de datos compartidos y la sincronización entre dispositivos dependen de la implementación futura del servidor.

### RNF-10. Consistencia entre dispositivos

En una futura versión con varios dispositivos, el sistema debe contar con reglas para evitar duplicaciones y manejar modificaciones concurrentes.

**Criterio de aceptación:** se definen identificadores globalmente únicos, mecanismos de sincronización y reglas para resolver conflictos antes de considerar confiable el uso simultáneo desde varios dispositivos.

### RNF-11. Privacidad

Los datos personales de los clientes deben utilizarse únicamente para los fines operativos del negocio y manejarse de acuerdo con las obligaciones legales aplicables.

**Criterio de aceptación:** se documentan los datos recopilados, quién puede acceder a ellos, dónde se almacenan y cómo se protegen, eliminan o respaldan.

## 8. Reglas de negocio

Las siguientes reglas describen principios que deben orientar el comportamiento del sistema. Las reglas que no estén respaldadas por la implementación actual deben considerarse pendientes de desarrollo o validación.

### RN-01. Identificación de los pedidos

Cada pedido debe poder identificarse de forma inequívoca dentro del sistema.

En la versión local, la generación de identificadores puede depender de la base de datos del dispositivo. Si se habilita el trabajo simultáneo en varios dispositivos, será necesario definir una estrategia de identificación global que evite colisiones.

### RN-02. Relación entre pedidos y clientes

Un pedido debe conservar la información del cliente al que corresponde mediante la relación definida en la base de datos.

Las reglas para permitir pedidos sin un cliente previamente registrado deben establecerse explícitamente, según las necesidades operativas del negocio.

### RN-03. Relación entre pedidos y productos

Una orden puede contener uno o varios elementos de pedido. Cada elemento debe permitir representar el producto solicitado y la información adicional que defina el modelo de datos.

### RN-04. Seguimiento de estados

Los estados deben representar el avance del pedido y utilizarse de forma consistente en las pantallas y consultas de la aplicación.

Las condiciones para cambiar de estado deben ser claras y no deben depender únicamente de la apariencia visual de una pantalla.

### RN-05. Historial de estados

Cuando se registre una transición de estado, el sistema debe conservar el historial correspondiente si la operación está contemplada en el flujo implementado.

El historial debe permitir distinguir el estado actual de los cambios que se hayan registrado previamente.

### RN-06. Productos y categorías

Los productos deben organizarse de acuerdo con las categorías definidas en el catálogo, respetando las relaciones establecidas en la base de datos.

### RN-07. Materiales y atributos

Los atributos relacionados con telas, colores, tallas u otras características deben manejarse de acuerdo con la estructura configurada en el sistema.

La disponibilidad de cada atributo para un producto o pedido dependerá de las reglas que se implementen en los módulos correspondientes.

### RN-08. Conservación de datos locales

La información que se guarde correctamente en la base de datos local debe permanecer disponible entre ejecuciones de la aplicación.

Los datos locales no deben considerarse automáticamente respaldados ni disponibles en otros dispositivos.

### RN-09. Sincronización futura

Cuando se incorpore el servidor central, las operaciones de sincronización deberán distinguir entre los cambios guardados localmente y los cambios confirmados por el servidor.

Una operación guardada en Room no debe interpretarse como sincronizada hasta que exista confirmación del mecanismo de sincronización correspondiente.

## 9. Restricciones del sistema

Las siguientes restricciones delimitan el funcionamiento de la versión actual y los aspectos que deben tenerse en cuenta durante su evolución.

- La aplicación está orientada a dispositivos Android.
- El proyecto se desarrolla en Kotlin y utiliza Jetpack Compose para la interfaz.
- Room se utiliza como mecanismo de persistencia local.
- La información almacenada en una base de datos local no se comparte automáticamente con otros teléfonos.
- Si un dispositivo pierde sus datos locales, la información que no tenga una copia de respaldo podría perderse.
- El uso simultáneo desde varios dispositivos requiere una estrategia adicional de sincronización.
- Los identificadores autoincrementales locales no deben asumirse como identificadores globales para un sistema distribuido.
- Las funcionalidades de autenticación, permisos, servidor y copias de seguridad deben verificarse por separado.
- Las pantallas disponibles no garantizan que todas las operaciones de negocio estén terminadas.
- La aplicación no debe considerarse lista para producción hasta que se hayan ejecutado las pruebas correspondientes.

## 10. Funcionalidades fuera del alcance actual

Salvo que se implementen expresamente en una versión posterior, los siguientes elementos no se consideran parte confirmada del alcance funcional actual:

- Una tienda virtual pública para que los clientes compren directamente.
- Un sistema de registro e inicio de sesión para clientes.
- Una pasarela de pago integrada.
- La gestión contable completa del negocio.
- La facturación electrónica.
- La integración automática con empresas transportadoras.
- El envío garantizado de notificaciones push.
- La sincronización confirmada en tiempo real entre varios teléfonos.
- La administración de roles y permisos con controles completos.
- La operación del servidor central y la base de datos remota.
- La recuperación automática de datos locales perdidos.

Algunos de estos elementos podrían incorporarse en el futuro si las necesidades de Baby Loft lo requieren.

## 11. Criterios generales de aceptación del proyecto

Antes de considerar que una versión es apta para el uso operativo, se deben comprobar, como mínimo, los siguientes aspectos:

1. La aplicación se construye sin errores.
2. Las pantallas principales se abren y permiten navegar entre los módulos disponibles.
3. Los pedidos pueden registrarse mediante el flujo implementado.
4. La información de las órdenes se guarda correctamente.
5. Los datos almacenados pueden consultarse después de cerrar y abrir la aplicación.
6. Las relaciones entre pedidos, clientes, productos y elementos del pedido se conservan correctamente.
7. Los estados de los pedidos se muestran y actualizan de acuerdo con las reglas implementadas.
8. Las operaciones inválidas reciben el tratamiento correspondiente.
9. Los módulos de productos, categorías, materiales y pedidos archivados funcionan según sus comportamientos definidos.
10. Los errores detectados durante las pruebas quedan documentados y se corrigen antes de una entrega estable.

Para una futura versión con servidor central, también se deberán comprobar:

11. La comunicación segura entre la aplicación y el servidor.
12. La disponibilidad de los mismos registros en los dispositivos autorizados.
13. La sincronización de operaciones pendientes después de recuperar la conectividad.
14. La prevención de duplicados durante la sincronización.
15. El manejo de modificaciones concurrentes y conflictos.
16. La autenticación, los permisos y la recuperación de la información central.

Los criterios deben marcarse como cumplidos únicamente cuando exista evidencia obtenida mediante pruebas o revisión de la implementación.

## 12. Matriz de trazabilidad de requisitos

La siguiente matriz permite relacionar los requisitos con los módulos responsables y las pruebas que deberán realizarse.

| Requisito | Módulo relacionado | Verificación propuesta |
|---|---|---|
| RF-01 | Panel administrativo | Abrir el panel y comprobar la información presentada. |
| RF-02 | Lista de pedidos | Consultar la lista y abrir una orden existente. |
| RF-03 | Creación de pedidos | Registrar una orden y comprobar que queda almacenada. |
| RF-04 | Detalle del pedido | Abrir una orden y revisar sus datos asociados. |
| RF-05 | Estados del pedido | Cambiar el estado de una orden y verificar el resultado. |
| RF-06 | Catálogo de productos | Consultar los productos disponibles. |
| RF-07 | Categorías | Verificar la relación entre productos y categorías. |
| RF-08 | Gestión de materiales | Consultar o modificar los atributos disponibles. |
| RF-09 | Pedidos archivados | Consultar las órdenes que cumplan las condiciones de archivo. |
| RF-10 | Base de datos y repositorios | Verificar las operaciones de almacenamiento y consulta. |
| RF-11 | Persistencia local | Reiniciar la aplicación y comprobar que los datos siguen disponibles. |
| RF-12 | Formularios y validaciones | Ingresar datos inválidos y comprobar su tratamiento. |
| RF-13 | Historial de estados | Realizar un cambio de estado y revisar el historial guardado. |
| RF-14 | API y sincronización futura | Verificar el envío y la recepción de registros entre dispositivos. |
| RF-15 | Persistencia local y sincronización futura | Crear cambios sin conexión y verificar su sincronización posterior. |

Esta matriz debe actualizarse a medida que avance el desarrollo, indicando el estado de cada requisito y la prueba que demuestra su cumplimiento.

## 13. Gestión del estado de implementación

Para mantener una documentación confiable, se recomienda clasificar cada requisito utilizando uno de los siguientes estados:

- **Pendiente:** todavía no se ha implementado.
- **En desarrollo:** la funcionalidad se está construyendo o modificando.
- **Implementado, pendiente de prueba:** existe código relacionado, pero todavía no se ha comprobado su comportamiento.
- **Verificado:** se ejecutaron las pruebas definidas y se obtuvo el resultado esperado.
- **Bloqueado:** existe una dependencia o un problema que impide avanzar.

La clasificación debe actualizarse con base en el estado real del código y las pruebas ejecutadas. No debe utilizarse el estado “Verificado” únicamente porque una pantalla exista o porque el código parezca estar completo.

## 14. Consideraciones para futuras versiones

La evolución de Baby Loft Management debe priorizar la confiabilidad de los datos y la continuidad de las operaciones del negocio.

Antes de incorporar la sincronización entre dispositivos, será necesario revisar especialmente:

- El uso de identificadores únicos que no dependan exclusivamente de una base de datos local.
- La generación de números de pedido sin duplicaciones.
- La estructura de los datos que se enviarán al servidor.
- La definición de operaciones para crear, consultar, modificar y sincronizar registros.
- La estrategia para identificar los cambios pendientes.
- El comportamiento ante fallos de conexión.
- Las reglas para resolver modificaciones simultáneas.
- La seguridad de las comunicaciones y el acceso a los datos.
- La estrategia de copias de seguridad y restauración.
- La migración de los datos existentes cuando sea necesaria.

La incorporación de estas capacidades debe realizarse por etapas, manteniendo el funcionamiento de las funcionalidades existentes y verificando cada cambio mediante pruebas.

## 15. Conclusión

Baby Loft Management busca proporcionar una herramienta organizada para gestionar los pedidos y apoyar el proceso operativo de Baby Loft. Su alcance inicial se centra en la aplicación Android, la administración de la información y la persistencia local mediante Room.

La documentación de requisitos establece las funcionalidades esperadas, las reglas generales del negocio, las restricciones y los criterios que deben utilizarse para comprobar el comportamiento del sistema.

La arquitectura futura contempla ampliar la solución mediante un servidor central y sincronización entre dispositivos. Esta evolución deberá implementarse de forma gradual, sin confundir los objetivos futuros con las capacidades que ya están disponibles en la versión actual.

Este documento debe mantenerse actualizado durante el desarrollo para que refleje fielmente el estado del proyecto, sus funcionalidades comprobadas y las mejoras pendientes.