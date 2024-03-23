if (!localStorage.jwt) {
    console.log("No estás logueado");
    location.replace("./index.html");
}

window.addEventListener('load', function () {

    /* ---------------------- obtenemos variables globales ---------------------- */
    /* ---------------------- obtenemos variables globales ---------------------- */
    const form = document.forms[0]
    const nombre = document.querySelector("#nombre")
    const descripcion = document.querySelector("#descripcion")
    const cantidadBodega = document.querySelector("#cantidadBodega")
    const modelo = document.querySelector("#modelo")
    const valorVenta = document.querySelector("#valorVenta")
    const formularioContainer = document.getElementById('formularioContainer');
    const usuariosBtn = document.getElementById('usuariosBtn');
    const productosBtn = document.getElementById('productosBtn');
    const devolverPrincipal = document.querySelector('#devolverPrincipal');
    const productosContent = document.querySelector('.productosContent');
    const listarProductosBtn = document.querySelector('#listarProductosBtn');
    const crearProductoBtn = document.querySelector('#crearProductoBtn');
    const devolverBtn = document.querySelector('#devolver');
    const registroBtn = document.querySelector('#registroForm .submit-button');

    /* -------------------------------------------------------------------------- */
    /*            FUNCIÓN 1: Escuchamos el clic en "Crear Producto"               */
    /* -------------------------------------------------------------------------- */
    document.addEventListener('DOMContentLoaded', function() {
        const listarProductosBtn = document.querySelector('#listarProductosBtn');
        const crearProductoBtn = document.querySelector('#crearProductoBtn');
        const devolverPrincipal = document.querySelector('#devolverPrincipal');

        document.addEventListener('productosButtonClick', function() {
            listarProductosBtn.style.display = 'block';
            crearProductoBtn.style.display = 'block';
            devolverPrincipal.style.display = 'none';
        });
    
    });

    devolverPrincipal.addEventListener('click', function () {
        location.replace("crearUsuario.html");
    });

    crearProductoBtn.addEventListener('click', function () {
        formularioContainer.style.display = 'block';
        listarProductosBtn.style.display = 'none';
        crearProductoBtn.style.display = 'none';
        devolverPrincipal.style.display = 'none';

        const guardarUsuarioBtn = document.querySelector('.change-button');
        guardarUsuarioBtn.style.display = 'none';

    });

    devolverBtn.addEventListener('click', function () {
        formularioContainer.style.display = 'none';
        productosContent.style.display = 'block';
        listarProductosBtn.style.display = 'inline';
        crearProductoBtn.style.display = 'inline';
        devolverPrincipal.style.display = 'inline';
    });

    /* -------------------------------------------------------------------------- */
    /*            FUNCIÓN 1: Realizamos el crear un producto        */
    /* -------------------------------------------------------------------------- */
    form.addEventListener('submit', function (event) {
        event.preventDefault()
        const payload = {
            nombre: nombre.value,
            descripcion: descripcion.value,
            cantidadBodega: cantidad.value,
            modelo: modelo.value,
            valorVenta: valorVenta.value
        }
console.log(payload)
        const settings = {
            method: "POST",
            body: JSON.stringify(payload),
            headers: {
                "Content-Type": "application/json"
            }
        };

        realizarCrearProducto(settings);

        form.reset();
    });

    function realizarCrearProducto(settings) {
        fetch(`http://localhost:8003/productos`, settings)
            .then(response => {
                console.log(response);
                if (response.ok) return response.json()
                return Promise.reject(response)
            })
            .then(data => {
                console.log(data);
                console.log(data.jwt);

                if (data.jwt) {
                    localStorage.setItem("jwt", JSON.stringify(data.jwt))
                }
            })
    };


    /* -------------------------------------------------------------------------- */
    /*            FUNCIÓN 3: Realizamos el listar productos       */
    /* -------------------------------------------------------------------------- */
    listarProductosBtn.addEventListener('click', function () {
        listarProductosBtn.style.display = 'none';
        devolverPrincipal.style.display = 'none';
        crearProductoBtn.style.display = 'none';
        const settings = {
            method: "GET"
        }
        fetch('http://localhost:8003/productos', settings)
            .then(response => {
                if (response.ok) {
                    console.log(response)
                    return response.json();
                } else {
                    throw new Error('Error al obtener la lista de productos');
                }
            })
            .then(data => {
                mostrarProductos(data);
                console.log(data)
            })

    });

    function mostrarProductos(productos) {
        document.getElementById('productoContainer').style.display = 'inline';
        const productoContainer = document.getElementById('productoContainer');

        productoContainer.innerHTML = '';

        const table = document.createElement('table');
        const headerRow = table.insertRow();
        headerRow.innerHTML = '<th>Nombre</th><th>Descripcion</th><th>Cantidad en Bodega</th><th>Modelo</th><th>Valor de venta</th><th>Acciones</th>';

        productos.forEach(producto => {
            const row = table.insertRow();
            row.innerHTML = `<td>${producto.nombre}</td><td>${producto.descripcion}</td><td>${producto.cantidadBodega}</td><td>${producto.modelo}</td><td>${producto.valorVenta}</td>`;

            const actionsCell = row.insertCell();
            const eliminarBtn = document.createElement('button');
            eliminarBtn.textContent = 'Eliminar';
            eliminarBtn.addEventListener('click', function () {
                botonBorrarProducto(producto)

            });
            const modificarBtn = document.createElement('button');
            modificarBtn.textContent = 'Modificar';
            modificarBtn.addEventListener('click', function () {
                const guardarProductoBtn = document.querySelector('.change-button');
                guardarProductoBtn.style.display = 'inline';
                abrirFormularioModificacionProducto(producto);
                console.log('Modificar producto:', producto);
            });
            actionsCell.appendChild(eliminarBtn);
            actionsCell.appendChild(modificarBtn);
        });

        productoContainer.appendChild(table);

        const devolverProductoBtn = document.createElement('button');
        devolverProductoBtn.textContent = 'Devolver';
        devolverProductoBtn.id = 'devolverProductoBtn';
        productoContainer.appendChild(devolverProductoBtn);

        devolverProductoBtn.addEventListener('click', function () {
            table.style.display = 'none';
           productosContent.style.display = 'block';
           listarProductosBtn.style.display = 'inline';
           crearProductoBtn.style.display = 'inline';
           devolverProductoBtn.style.display = 'none';
           devolverPrincipal.style.display = 'inline';
        });
    }

    /* -------------------------------------------------------------------------- */
    /*                     FUNCIÓN 4 - Eliminar productos [DELETE]                    */
    /* -------------------------------------------------------------------------- */
    function botonBorrarProducto(producto) {
        const confirmacion = confirm("¿Estás seguro de que deseas eliminar este usuario?");
        if (!confirmacion) {
            return;
        }
        const settings = {
            method: "DELETE"
        }

        fetch(`http://localhost:8003/productos/${producto.id}`, settings)
            .then(response => {
                console.log(response);
                if (response.ok) {
                    alert("El producto ha sido eliminado satisfactoriamente, recargue la tabla.");
                    const listarProductosBtn = document.querySelector('#listarProductosBtn');
                    listarProductosBtn.style.display = 'inline';
                } else {
                    throw new Error('Error al eliminar el producto.');
                }
            })

    };

    /* -------------------------------------------------------------------------- */
    /*                     FUNCIÓN 5 - Modificar producto                   */
    /* -------------------------------------------------------------------------- */
    function abrirFormularioModificacionProducto(producto) {
        document.getElementById('productoContainer').style.display = 'none';
        document.getElementById('formularioContainer').style.display = 'block';
    
        formularioContainer.style.display = 'block';
        document.getElementById('nombre').value = producto.nombre;
        document.getElementById('cantidad').value = producto.cantidadBodega; // Ajustado a "cantidadBodega"
        document.getElementById('descripcion').value = producto.descripcion;
        document.getElementById('modelo').value = producto.modelo;
        document.getElementById('valorVenta').value = producto.valorVenta;
    
        registroBtn.style.display = 'none';
    
        const guardarUsuarioBtn = document.querySelector('.change-button');
        guardarUsuarioBtn.addEventListener('click', function (event) {
            event.preventDefault();
    
            const nombre = document.getElementById('nombre').value;
            const cantidadBodega = document.getElementById('cantidad').value; // Ajustado a "cantidadBodega"
            const descripcion = document.getElementById('descripcion').value;
            const modelo = document.getElementById('modelo').value;
            const valorVenta = document.getElementById('valorVenta').value;
    
            const productoActualizado = {
                id: producto.id,
                nombre: nombre,
                cantidadBodega: cantidadBodega, // Ajustado a "cantidadBodega"
                descripcion: descripcion,
                modelo: modelo,
                valorVenta: valorVenta
            };
            // Enviar la solicitud PUT al servidor para modificar el usuario
            guardarCambiosProducto(productoActualizado);
        });
    }

    function guardarCambiosProducto(producto) {
        // Verificar si se han realizado cambios en los campos del formulario
        const nombreActualizado = document.getElementById('nombre').value;
    const cantidadActualizada = document.getElementById('cantidad').value;
    const descripcionActualizada = document.getElementById('descripcion').value;
    const modeloActualizado = document.getElementById('modelo').value;
    const valorVentaActualizado = document.getElementById('valorVenta').value;

    // Comparar los valores actuales con los valores originales del producto
    if (
        nombreActualizado === producto.nombre &&
        cantidadActualizada === producto.cantidad &&
        descripcionActualizada === producto.descripcion &&
        modeloActualizado === producto.modelo &&
        valorVentaActualizado === producto.valorVenta
    ) {
        // No se han realizado cambios, así que no se envía la solicitud
        console.log("No se han realizado cambios. No se enviará la solicitud.");
        alert("No se han realizado cambios. No se enviará la solicitud.");
        return;
    }

    // Se han realizado cambios, se procede a enviar la solicitud al servidor
    const payload = {
        nombre: nombreActualizado,
        cantidadBodega: cantidadActualizada,
        descripcion: descripcionActualizada,
        modelo: modeloActualizado,
        valorVenta: valorVentaActualizado
    };

        
        const settings = {
            method: "PUT",
            body: JSON.stringify(payload),
            headers: {
                "Content-Type": "application/json"
            }
        };

        fetch(`http://localhost:8003/productos/${producto.id}`, settings)
            .then(response => {
                console.log(response);
                if (response.ok) {
                    alert("Producto modificado satisfactoriamente, recargue la tabla.");
                    document.getElementById('formularioContainer').style.display = 'none';
                    document.getElementById('listarProductosBtn').style.display = 'inline';
                } else {
                    throw new Error('Error al modificar el usuario.');
                }
            })
    }

});