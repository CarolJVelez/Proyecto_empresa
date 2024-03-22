if (!localStorage.jwt) {
    console.log("No estas logeado");
    location.replace("./index.html")
  }

window.addEventListener('load', function () {

    /* ---------------------- obtenemos variables globales ---------------------- */
     /* ---------------------- obtenemos variables globales ---------------------- */
     const form = document.forms[0]
     // const form = document.querySelector("form")
     const nombre = document.querySelector("#nombre")
     const email = document.querySelector("#email")
     const contrasena = document.querySelector("#contrasena")
     const rol = document.querySelector("#rol")
     const formularioContainer = document.getElementById('formularioContainer');
     const crearUsuarioBtn = document.getElementById('crearUsuarioBtn');
     const devolverBtn = document.getElementById('devolver');
  
  /* -------------------------------------------------------------------------- */
    /*            FUNCIÓN 1: Escuchamos el clic en "Crear Usuario"               */
    /* -------------------------------------------------------------------------- */
    /*crearUsuarioBtn.addEventListener('click', function () {
        if (formularioContainer.style.display === 'none') {
            formularioContainer.style.display = 'block';
        } else {
            formularioContainer.style.display = 'none';
        }
    });

    devolverBtn.addEventListener('click', function () {
        if (formularioContainer.style.display === 'block') {
            formularioContainer.style.display = 'none';
        } else {
            formularioContainer.style.display = 'block';
        }
    });*/
    

    /* -------------------------------------------------------------------------- */
    /*            FUNCIÓN 1: Escuchamos el submit y preparamos el envío           */
    /* -------------------------------------------------------------------------- */
    form.addEventListener('submit', function (event) {
        event.preventDefault()

        //Creamos el cuerpo de la request (petición al servidor)
       const payload = {
        nombre: nombre.value,
        email: email.value,
        contrasena: contrasena.value,
        rol: rol.value

      }

     // console.log(payload);

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

        fetch(`http://localhost:8003/usuarios`, settings)
        .then( response => {
            console.log(response);
            // manejar el error de la request, si todo va bien, esta respuesta la capturaremos en el siguiente .then
            if (response.ok) return response.json()
            
            // Si hay un error, fuerzo el error para capturarlo en el .catch
            return Promise.reject(response)

        })
        .then(data =>{
            console.log(data);
            console.log(data.jwt);

            if (data.jwt) {
                localStorage.setItem("jwt", JSON.stringify(data.jwt))
            }

        })
        
    };


});