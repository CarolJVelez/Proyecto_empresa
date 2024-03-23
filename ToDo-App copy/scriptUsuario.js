if (!localStorage.jwt) {
    console.log("No estas logeado");
    location.replace("./index.html")
  }

window.addEventListener('load', function () {

    /* ---------------------- obtenemos variables globales ---------------------- */
     /* ---------------------- obtenemos variables globales ---------------------- */
     const form = document.forms[0]
     const nombre = document.querySelector("#nombre")
     const email = document.querySelector("#email")
     const contrasena = document.querySelector("#contrasena")
     const rol = document.querySelector("#rol")
     const formularioContainer = document.getElementById('formularioContainer');
     const usuariosBtn = document.getElementById('usuariosBtn');
     const productosBtn = document.getElementById('productosBtn');
     const devolverPrincipal = document.querySelector('#devolverPrincipal');
     const usuariosContent = document.querySelector('.usuariosContent');
    const listarUsuariosBtn = document.querySelector('#listarUsuariosBtn');
    const crearUsuarioBtn = document.querySelector('#crearUsuarioBtn');
    const devolverBtn = document.querySelector('#devolver');
    const registroBtn = document.querySelector('#registroForm .submit-button');
  
  /* -------------------------------------------------------------------------- */
    /*            FUNCIÓN 1: Escuchamos el clic en "Crear Usuario"               */
    /* -------------------------------------------------------------------------- */
    devolverPrincipal.addEventListener('click', function() {
        usuariosBtn.style.display = 'inline';
        productosBtn.style.display = 'inline';
        usuariosContent.style.display = 'none';
    });
    
    usuariosContent.style.display = 'none';

    usuariosBtn.addEventListener('click', function() {
        if (usuariosContent.style.display === 'none') {
            usuariosContent.style.display = 'block';
            usuariosBtn.style.display = 'none';
            productosBtn.style.display = 'none';
            
        } else {
            usuariosContent.style.display = 'none';
        }
    });

    crearUsuarioBtn.addEventListener('click', function() {
        formularioContainer.style.display = 'block';
        listarUsuariosBtn.style.display = 'none';
        crearUsuarioBtn.style.display = 'none';
        devolverPrincipal.style.display = 'none';

        const guardarUsuarioBtn = document.querySelector('.change-button');
        guardarUsuarioBtn.style.display = 'none';

    });

    devolverBtn.addEventListener('click', function() {
        formularioContainer.style.display = 'none';
        usuariosContent.style.display = 'block';
        listarUsuariosBtn.style.display = 'inline';
        crearUsuarioBtn.style.display = 'inline';
        devolverPrincipal.style.display = 'inline';
    });

    

    /* -------------------------------------------------------------------------- */
    /*            FUNCIÓN 1: Realizamos el crear un usuario        */
    /* -------------------------------------------------------------------------- */
    form.addEventListener('submit', function (event) {
        event.preventDefault()
       const payload = {
        nombre: nombre.value,
        email: email.value,
        contrasena: contrasena.value,
        rol: rol.value
      }
        const settings = {
            method: "POST",
            body: JSON.stringify(payload),
            headers: {
                "Content-Type": "application/json"
            }
        }
        realizarRegister(settings)
       form.reset()
    });

    function realizarRegister(settings) {

        fetch(`http://localhost:8003/usuarios`, settings)
        .then( response => {
            console.log(response);
            if (response.ok) return response.json()
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

    /* -------------------------------------------------------------------------- */
    /*            FUNCIÓN 3: Realizamos el listar usuarios       */
    /* -------------------------------------------------------------------------- */
    listarUsuariosBtn.addEventListener('click', function() {
        listarUsuariosBtn.style.display = 'none';
        devolverPrincipal.style.display = 'none';
        crearUsuarioBtn.style.display = 'none';
        const settings = {
            method: "GET"
          }
        fetch('http://localhost:8003/usuarios',settings)
            .then(response => {
                if (response.ok) {
                    console.log(response)
                    return response.json();
                } else {
                    throw new Error('Error al obtener la lista de usuarios');
                }
            })
            .then(data => {
                mostrarUsuarios(data);
                console.log(data)
            })
            
    });
    
    function mostrarUsuarios(usuarios) {
        document.getElementById('usuariosContainer').style.display = 'inline';
        const usuariosContainer = document.getElementById('usuariosContainer');

        usuariosContainer.innerHTML = '';
    
        const table = document.createElement('table');
        const headerRow = table.insertRow();
        headerRow.innerHTML = '<th>Nombre</th><th>Email</th><th>Rol</th><th>Acciones</th>';    

        usuarios.forEach(usuario => {
            const row = table.insertRow();
            row.innerHTML = `<td>${usuario.nombre}</td><td>${usuario.email}</td><td>${usuario.rol}</td>`;

       const actionsCell = row.insertCell();
       const eliminarBtn = document.createElement('button');
       eliminarBtn.textContent = 'Eliminar';
       eliminarBtn.addEventListener('click', function() {
           botonBorrarUsuario(usuario)
           
       });
       const modificarBtn = document.createElement('button');
       modificarBtn.textContent = 'Modificar';
       modificarBtn.addEventListener('click', function() {
        const guardarUsuarioBtn = document.querySelector('.change-button');
        guardarUsuarioBtn.style.display = 'inline';
            abrirFormularioModificacion(usuario);
           console.log('Modificar usuario:', usuario);
       });
       actionsCell.appendChild(eliminarBtn);
       actionsCell.appendChild(modificarBtn);
   });

   usuariosContainer.appendChild(table);

        const devolverUsuariosBtn = document.createElement('button');
        devolverUsuariosBtn.textContent = 'Devolver'; 
        devolverUsuariosBtn.id = 'devolverUsuariosBtn'; 
        usuariosContainer.appendChild(devolverUsuariosBtn);

        devolverUsuariosBtn.addEventListener('click', function() {
            table.style.display = 'none';
            usuariosContent.style.display = 'block';
            listarUsuariosBtn.style.display = 'inline';
            crearUsuarioBtn.style.display = 'inline';
            devolverUsuariosBtn.style.display = 'none';
            devolverPrincipal.style.display = 'inline';
        });
    }

    /* -------------------------------------------------------------------------- */
  /*                     FUNCIÓN 4 - Eliminar usuario [DELETE]                    */
  /* -------------------------------------------------------------------------- */
  function botonBorrarUsuario(usuario) {
    const confirmacion = confirm("¿Estás seguro de que deseas eliminar este usuario?");
    if (!confirmacion) {
        return; 
    }
    const settings = {
        method: "DELETE"
      }

    fetch(`http://localhost:8003/usuarios/${usuario.id}`, settings )
    .then( response => {
      console.log(response);  
      if (response.ok) {
        //mostrarUsuarios();
        alert("Usuario eliminado satisfactoriamente, recargue la tabla.");
        const listarUsuariosBtn = document.querySelector('#listarUsuariosBtn');
        listarUsuariosBtn.style.display = 'inline';
    } else {
        throw new Error('Error al eliminar el usuario.');
    }
    })

  };

    /* -------------------------------------------------------------------------- */
  /*                     FUNCIÓN 5 - Modificar usuario                    */
  /* -------------------------------------------------------------------------- */
  function abrirFormularioModificacion(usuario) {
    document.getElementById('usuariosContainer').style.display = 'none';
    document.getElementById('formularioContainer').style.display = 'block';

    formularioContainer.style.display = 'block';
    document.getElementById('nombre').value = usuario.nombre;
    document.getElementById('email').value = usuario.email;
    document.getElementById('contrasena').value = usuario.contrasena;
    document.getElementById('rol').value = usuario.rol;

    registroBtn.style.display = 'none';

    const guardarUsuarioBtn = document.querySelector('.change-button');
    guardarUsuarioBtn.addEventListener('click', function(event) {
        event.preventDefault(); 

        const nombre = document.getElementById('nombre').value;
        const email = document.getElementById('email').value;
        const contrasena = document.getElementById('contrasena').value;
        const rol = document.getElementById('rol').value;
        const usuarioActualizado = {
            id: usuario.id,
            nombre: nombre,
            email: email,
            contrasena: contrasena,
            rol: rol
        };
        // Enviar la solicitud PUT al servidor para modificar el usuario
        guardarCambiosUsuario(usuarioActualizado);
    });
}

function guardarCambiosUsuario(usuario) {
    // Verificar si se han realizado cambios en los campos del formulario
    const nombreActualizado = document.getElementById('nombre').value;
    const emailActualizado = document.getElementById('email').value;
    const contrasenaActualizada = document.getElementById('contrasena').value;
    const rolActualizado = document.getElementById('rol').value;

    if (
        nombreActualizado === usuario.nombre &&
        emailActualizado === usuario.email &&
        contrasenaActualizada === usuario.contrasena &&
        rolActualizado === usuario.rol
    ) {
        // No se han realizado cambios, así que no se envía la solicitud
        console.log("No se han realizado cambios. No se enviará la solicitud.");
        return;
    }

    // Se han realizado cambios, se procede a enviar la solicitud al servidor
    const payload = {
        nombre: nombreActualizado,
        email: emailActualizado,
        contrasena: contrasenaActualizada,
        rol: rolActualizado
    };

    // Verificar si la contraseña ha sido modificada
    if (contrasenaActualizada !== usuario.contrasena) {
        payload.contrasena = contrasenaActualizada;
    }

    const settings = {
        method: "PUT",
        body: JSON.stringify(payload),
        headers: {
            "Content-Type": "application/json"
        }
    };

    fetch(`http://localhost:8003/usuarios/${usuario.id}`, settings)
    .then( response => {
        console.log(response); 
        if (response.ok) {
            alert("Usuario modificado satisfactoriamente, recargue la tabla.");
            document.getElementById('formularioContainer').style.display = 'none';
            document.getElementById('listarUsuariosBtn').style.display = 'inline';
        } else {
            throw new Error('Error al modificar el usuario.');
        }
    })
}
    
});