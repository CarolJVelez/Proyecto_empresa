/*==============================================================================================
Autor         : AngelaAndrade[Kalettre]
Fecha         : 24/04/2023
Item Azure  : 29175 - Envio de giros
Descripcion : Creación de la tabla comunicacion_socket
===============================================================================================*/

CREATE TABLE public.comunicacion_socket (
	id varchar NOT NULL,
	ip varchar NULL,
	puerto varchar NULL,
	CONSTRAINT comunicacion_socket_pk PRIMARY KEY (id)
);

COMMENT ON TABLE public.comunicacion_socket IS 'DENTRO DE ESTA TABLA SE ALMACENA INFORMACIÓN NECESARIA PARA HACER LA CONEXIÓN CON EL SOCKET';

-- Column comments

COMMENT ON COLUMN public.comunicacion_socket.ID IS 'ID DEL PARAMETRO';
COMMENT ON COLUMN public.comunicacion_socket.IP IS 'IP DE LA CONEXIÓN';
COMMENT ON COLUMN public.comunicacion_socket.PUERTO IS 'PUERTO DE LA CONEXIÓN';