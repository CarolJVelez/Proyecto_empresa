window.addEventListener('load', function () {
    /* ---------------------- obtenemos variables globales ---------------------- */
     /* ---------------------- obtenemos variables globales ---------------------- */
     const form = document.forms[0]
     // const form = document.querySelector("form")
     const email = document.querySelector("#email")
     const password = document.querySelector("#password")
  

    /* -------------------------------------------------------------------------- */
    /*            FUNCIÓN 1: Escuchamos el submit y preparamos el envío           */
    /* -------------------------------------------------------------------------- */
    form.addEventListener('submit', function (event) {
        event.preventDefault()

        //Creamos el cuerpo de la request (petición al servidor)
       const payload = {
        email: email.value,
        contrasena: password.value

      }
      
      console.log(payload);

        //configuramos la request del Fetch
        const settings = {
            method: "POST",
            body: JSON.stringify(payload),
            headers: {
                "Content-Type": "application/json"
            }
        }
        //console.log(settings);

        realizarRegister(settings)

       form.reset()
    });

    /* -------------------------------------------------------------------------- */
    /*                    FUNCIÓN 2: Realizar el signup [POST]                    */
    /* -------------------------------------------------------------------------- */
    function realizarRegister(settings) {
        console.log(settings);
       console.log("🏄🏻‍♂️Lanzando la consulta a la API....");

        fetch(`http://localhost:8003/login`, settings)
        .then(response => {
            console.log(response)
            if (response.ok) {
                return response.json();
            } /*else {
                return response.text(); // Devuelve el mensaje de error como cadena de texto
            }*/
            return Promise.reject(response)
        })
        .then(data =>{
            console.log(data);
            console.log(data.jwt);

            if (data.jwt) {
                // Guardamos el dato JWT en LocalStorage (ese token de autenticacion)
                localStorage.setItem("jwt", JSON.stringify(data.jwt))

                 // redireccionamos a nuestro dashboard de todo
                 location.replace("crearUsuario.html")
            }

        })

    };
});