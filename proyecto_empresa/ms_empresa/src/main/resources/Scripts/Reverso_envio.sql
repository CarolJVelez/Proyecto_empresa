/*==============================================================================================
Autor         : Angela Vanegas[Kalettre]
Fecha         : 24/04/2023
Item Azure  : 29175 - Envio de giros
Descripcion : Script de reverso
===============================================================================================*/

/*****delete de inseracion ******/

delete from comunicacion_socket where id between 1 and 10;

/*****eliminacion de tabla ******/

drop table comunicacion_socket;

commit;

