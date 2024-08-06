var url = "http://localhost:8080/api/libreria/prestamo/";

function listarPrestamos() {
    var urlLocal = url;
    var filtro = document.getElementById("texto").value;
    if (filtro !== "") {
        urlLocal += "busqueda/" + filtro;
    }

    $.ajax({
        url: urlLocal,
        type: "GET",
        success: function(result) {
            console.log(result);

            var cuerpoTablaPrestamos = document.getElementById("cuerpoTablaPrestamos");
            cuerpoTablaPrestamos.innerHTML = "";
            for (var i = 0; i < result.length; i++) {
                var trResgistro = document.createElement("tr");

                var celdaId = document.createElement("td");
                celdaId.innerText = result[i]["id_prestamo"];

                var celdaFechaPrestamo = document.createElement("td");
                celdaFechaPrestamo.innerText = result[i]["fecha_prestamo"];

                var celdaFechaDevolucion = document.createElement("td");
                celdaFechaDevolucion.innerText = result[i]["fecha_devo"];

                var celdaUsuaPrestamo = document.createElement("td");
                if (result[i]["usuario"] != null) {
                    celdaUsuaPrestamo.innerText = result[i]["usuario"]["nombre_usuario"];
                } else {
                    celdaUsuaPrestamo.innerText = "No registra usuario";
                }

                var celdaLibroPrestado = document.createElement("td");
                if (result[i]["libro"] != null) {
                    celdaLibroPrestado.innerText = result[i]["libro"]["titulo"];
                } else {
                    celdaLibroPrestado.innerText = "No registra libro";
                }

                var celdaEstado = document.createElement("td");
                celdaEstado.innerText = result[i]["estado_prestamo"];

                var celdaOpcion = document.createElement("td");
                var botonActualizar = document.createElement("button");
                botonActualizar.value = result[i]["id_prestamo"];
                botonActualizar.innerHTML = "Actualizar";
                botonActualizar.className = "btn btn-warning actualizar-prestamo";
                botonActualizar.onclick = function(e) {
                    $('#exampleModal').modal('show');
                    consultarPrestamoId(this.value);
                };

                var celdaOpcion2 = document.createElement("td");
                var botonEliminar = document.createElement("button");
                botonEliminar.innerHTML = "Eliminar";
                botonEliminar.className = "btn btn-danger eliminar-prestamo";
                botonEliminar.onclick = function() {
                    eliminarPrestamo(result[i]["id_prestamo"]);
                };

                trResgistro.appendChild(celdaId);
                trResgistro.appendChild(celdaFechaPrestamo);
                trResgistro.appendChild(celdaFechaDevolucion);
                trResgistro.appendChild(celdaUsuaPrestamo);
                trResgistro.appendChild(celdaLibroPrestado);
                trResgistro.appendChild(celdaEstado);
                celdaOpcion.appendChild(botonActualizar);
                trResgistro.appendChild(celdaOpcion);
                celdaOpcion2.appendChild(botonEliminar);
                trResgistro.appendChild(celdaOpcion2);
                cuerpoTablaPrestamos.appendChild(trResgistro);
            }
        },
        error: function(error) {
            alert("Error en la petición " + error);
        }
    });
}


function consultarPrestamoId(id) {
    $.ajax({
        url: url + id,
        type: "GET",
        success: function(result) {
            document.getElementById("id_prestamo").value = result["id_prestamo"];
            document.getElementById("fecha_prestamo").value = result["fecha_prestamo"];
            document.getElementById("fecha_devo").value = result["fecha_devo"];
            document.getElementById("usuario").value = result["usuario"];
            document.getElementById("libro").value = result["libro"];
            document.getElementById("estado_prestamo").value = result["estado_prestamo"];
        },
        error: function(error) {
            alert("Error al consultar el libro con ID: " + id);
        }
    });
}

function actualizarPrestamo() {
    var id_prestamo = document.getElementById("id_prestamo").value;
    var formData = {
        "fecha_prestamo": document.getElementById("fecha_prestamo").value,
        "fecha_devo": document.getElementById("fecha_devo").value,
        "usuario": document.getElementById("usuario").value,
        "libro": document.getElementById("libro").value,
        "estado_prestamo": document.getElementById("estado_prestamo").value
    };

    if (validarCampos()) {
        $.ajax({
            url: url + id_prestamo,
            type: "PUT",
            data: formData,
            success: function(result) {
                Swal.fire({
                    title: "¡Excelente!",
                    text: "Se guardó correctamente",
                    icon: "success"
                });
                listarPrestamos();
                $('#exampleModal').modal('hide');
            },
            error: function(error) {
                Swal.fire({
                    title: "¡Error!",
                    text: "No se guardó",
                    icon: "error"
                });
            }
        });
    } else {
        Swal.fire({
            title: "¡Error!",
            text: "Llene todos los campos correctamente",
            icon: "error"
        });
    }
}

function eliminarPrestamo(id) {
    Swal.fire({
        title: '¿Está seguro?',
        text: "Esta acción no se puede deshacer",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sí, eliminar!'
    }).then((result) => {
        if (result.isConfirmed) {
            $.ajax({
                url: url + id,
                type: "DELETE",
                success: function(result) {
                    Swal.fire(
                        'Eliminado!',
                        'El registro ha sido eliminado.',
                        'success'
                    );
                    listarPrestamos();
                },
                error: function(error) {
                    Swal.fire(
                        'Error!',
                        'No se pudo eliminar el registro.',
                        'error'
                    );
                }
            });
        }
    });
}

function registrarPrestamo() {
    var formData = {
        "fecha_prestamo": document.getElementById("fecha_prestamo").value,
        "fecha_devo": document.getElementById("fecha_devo").value,
        "estado_prestamo": document.getElementById("estado_prestamo").value
    };

    if (validarCampos()) {
        $.ajax({
            url: url,
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(formData),
            success: function(result) {
                Swal.fire({
                    title: "¡Excelente!",
                    text: "Se guardó correctamente",
                    icon: "success"
                });
                $('#exampleModal').modal('hide');
            },
            error: function(error) {
                Swal.fire({
                    title: "¡Error!",
                    text: "No se guardó",
                    icon: "error"
                });
            }
        });
    } else {
        Swal.fire({
            title: "¡Error!",
            text: "Llene todos los campos correctamente",
            icon: "error"
        });
    }
}

function validarCampos() {
    var fechaPrestamo = document.getElementById("fecha_prestamo");
    var fechaDevolucion = document.getElementById("fecha_devo");
    var usuaPrestamo = document.getElementById("usuario");
    var libroPrestado = document.getElementById("libro");

    return validarFechaPres(fechaPrestamo) && validarFechaDevo(fechaDevolucion) && validarUsuaPrestamo(usuaPrestamo) &&
    validarLibroPresta(libroPrestado);
}
function validarFechaPres(fechaPrestamo) {
    var valor = fechaPrestamo.value.trim();
    var valido = valor.length >= 1 && valor.length <= 50;

    actualizarClaseValidacion(fechaPrestamo, valido);
    return valido;
}
function validarFechaDevo(fechaDevolucion) {
    var valor = fechaDevolucion.value.trim();
    var valido = valor.length >= 1 && valor.length <= 50;

    actualizarClaseValidacion(fechaDevolucion, valido);
    return valido;
}
function validarUsuaPrestamo(usuaPrestamo) {
    var valor = usuaPrestamo.value.trim();
    var valido = valor.length >= 3 && valor.length <= 100;

    actualizarClaseValidacion(usuaPrestamo, valido);
    return valido;
}
function validarLibroPresta(libroPrestado) {
    var valor = libroPrestado.value.trim();
    var valido = valor.length >= 3 && valor.length <= 100;

    actualizarClaseValidacion(libroPrestado, valido);
    return valido;
}

function actualizarClaseValidacion(elemento, valido) {
    if (valido) {
        elemento.classList.remove("is-invalid");
        elemento.classList.add("is-valid");
    } else {
        elemento.classList.remove("is-valid");
        elemento.classList.add("is-invalid");
    }
}

function limpiar() {
    document.getElementById("fecha_prestamo").value = "";
    document.getElementById("fecha_devo").value = "";
    document.getElementById("usuario").value = "";
    document.getElementById("libro").value = "";
    document.getElementById("estado_prestamo").value = "";

    document.getElementById("fecha_prestamo").classList.remove("is-valid", "is-invalid");
    document.getElementById("fecha_devo").classList.remove("is-valid", "is-invalid");
    document.getElementById("usuario").classList.remove("is-valid", "is-invalid");
    document.getElementById("libro").classList.remove("is-valid", "is-invalid");
    document.getElementById("estado_prestamo").classList.remove("is-valid", "is-invalid");
}


$(document).ready(function() {
    listarPrestamos();
    cargarFormulario();
});

function cargarFormulario() {
    cargarLibro();
    cargarUsuario();
}

function cargarLibro() {
    let urlLibro = "http://localhost:8080/api/libreria/libro/";
    $.ajax({
        url: urlLibro,
        type: "GET",
        success: function (result) {
            let libro = document.getElementById("libro");
            libro.innerHTML = "";
            for (let i = 0; i < result.length; i++) {
                let nombreLibro = document.createElement("option");
                nombreLibro.value = result[i]["id_libro"];
                nombreLibro.innerText = titulo =
                    result[i]["titulo"];
                    libro.appendChild(nombreLibro);
            }
        },
    });
}

function cargarUsuario() {
    let urlUsuario = "http://localhost:8080/api/libreria/usuario/";
    $.ajax({
        url: urlUsuario,
        type: "GET",
        success: function (result) {
            let usuario = document.getElementById("usuario");
            usuario.innerHTML = "";
            for (let i = 0; i < result.length; i++) {
                let nombreUsuario = document.createElement("option");
                nombreUsuario.value = result[i]["id_usuario"];
                nombreUsuario.innerText = nombre_usuario =
                    result[i]["nombre_usuario"];
                    usuario.appendChild(nombreUsuario);
            }
        },
    });
}
