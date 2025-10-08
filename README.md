PROYECTO FINAL | PLANTA DE RECICLAJE

Este proyecto consiste en la funcionalidad de una pequeña máquina que acepta entregas de materiales reciclables por parte de usuario para que este reciba una compensación monetaria, esta compensación se calcula a partir del tipo de material entregado y el peso de este, esto para dar mayor compensación a materiales más significativos

--------------------------------------------------------------------------------------------------------------------

FUNCIONALIDAD

La funcionalidad del proyecto consiste en que los usuarios realizan entregas y los administradores gestionan los datos, los usuarios no necesitan registrarse y solamente pueden entregar un tipo de material por cada entrega y los administradores manejan los datos, estos pueden modificar y agregar materiales, esto para modificar su valor en base a la demanda o agregar materiales que se han vuelto significativos para el reciclaje y pueden modificar el nombre del proveedor de las entregas

--------------------------------------------------------------------------------------------------------------------

TECNOLOGÍAS

El proyecto consiste meramente de backend y funciona con la tecnología Springboot, para hacerle funcionar se necesita de Postman y las solicitudes correspondientes

-GET
Esta solicitud sirve para listar los datos, puede realizarse en postman de la siguiente forma:
http://localhost:8080/plantaReciclaje/api/[entidad]

-POST
Esta solicitud sirve para agregar nuevos datos, esta puede realizarse en postman de la siguiente forma:
http://localhost:8080/plantaReciclaje/api/[entidad]

-PUT
Esta solicitud sirve para actualizar los datos existentes, puede realizarse en postman de la siguiente forma:
http://localhost:8080/plantaReciclaje/api/[entidad]/[id]
