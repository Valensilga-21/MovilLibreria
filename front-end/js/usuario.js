var url = "http://localhost:8080/api/libreria/usuario/";

function listarUsuarios() {
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

            var cuerpoTablaUsuarios = document.getElementById("cuerpoTablaUsuarios");
            cuerpoTablaUsuarios.innerHTML = "";
            for (var i = 0; i < result.length; i++) {
                var trResgistro = document.createElement("tr");

                var celdaId = document.createElement("td");
                celdaId.innerText = result[i]["id_usuario"];

                var celdaNombre = document.createElement("td");
                celdaNombre.innerText = result[i]["nombre_usuario"];

                var celdaDireccion = document.createElement("td");
                celdaDireccion.innerText = result[i]["direccion_usuario"];

                var celdaCorreo = document.createElement("td");
                celdaCorreo.innerText = result[i]["correo_usuario"];

                var celdaTipoUsuario = document.createElement("td");
                celdaTipoUsuario.innerText = result[i]["tipo_usuario"];

                var celdaOpcion = document.createElement("td");
                var botonActualizar = document.createElement("button");
                botonActualizar.value = result[i]["id_usuario"];
                botonActualizar.innerHTML = "Actualizar";
                botonActualizar.className = "btn btn-warning actualizar-usuario";
                botonActualizar.onclick = function(e) {
                    $('#exampleModal').modal('show');
                    consultarUsuarioId(this.value);
                };

                var celdaOpcion2 = document.createElement("td");
                var botonEliminar = document.createElement("button");
                botonEliminar.innerHTML = "Eliminar";
                botonEliminar.className = "btn btn-danger eliminar-usuario";
                botonEliminar.onclick = function() {
                    eliminarUsuario(["id_usuario"]);
                };

                trResgistro.appendChild(celdaId);
                trResgistro.appendChild(celdaNombre);
                trResgistro.appendChild(celdaDireccion);
                trResgistro.appendChild(celdaCorreo);
                trResgistro.appendChild(celdaTipoUsuario);
                celdaOpcion.appendChild(botonActualizar);
                trResgistro.appendChild(celdaOpcion);
                celdaOpcion2.appendChild(botonEliminar);
                trResgistro.appendChild(celdaOpcion2);
                cuerpoTablaUsuarios.appendChild(trResgistro);
            }
        },
        error: function(error) {
            alert("Error en la petición " + error);
        }
    });
}

function consultarUsuarioId(id) {
    $.ajax({
        url: url + id,
        type: "GET",
        success: function(result) {
            document.getElementById("id_usuario").value = result["id_usuario"];
            document.getElementById("nombre_usuario").value = result["nombre_usuario"];
            document.getElementById("direccion_usuario").value = result["direccion_usuario"];
            document.getElementById("correo_usuario").value = result["correo_usuario"];
            document.getElementById("tipo_usuario").value = result["tipo_usuario"];
        },
        error: function(error) {
            alert("Error al consultar el libro con ID: " + id);
        }
    });
}

function actualizarUsuario() {
    var id_usuario = document.getElementById("id_usuario").value;
    var formData = {
        "nombre_usuario": document.getElementById("nombre_usuario").value,
        "direccion_usuario": document.getElementById("direccion_usuario").value,
        "correo_usuario": document.getElementById("correo_usuario").value,
        "tipo_usuario": document.getElementById("tipo_usuario").value,
    };

    if (validarCampos()) {
        $.ajax({
            url: url + id_usuario,
            type: "PUT",
            data: formData,
            success: function(result) {
                Swal.fire({
                    title: "¡Excelente!",
                    text: "Se guardó correctamente",
                    icon: "success"
                });
                listarUsuarios();
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

function eliminarUsuario(id) {
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
                    listarUsuarios();
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

function registrarUsuario() {
    var formData = {
        "nombre_usuario": document.getElementById("nombre_usuario").value,
        "direccion_usuario": document.getElementById("direccion_usuario").value,
        "correo_usuario": document.getElementById("correo_usuario").value,
        "tipo_usuario": document.getElementById("tipo_usuario").value
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
    var nombre_usuario = document.getElementById("nombre_usuario");
    var direccion_usuario = document.getElementById("direccion_usuario");
    var correo_usuario = document.getElementById("correo_usuario");

    return validarNombre(nombre_usuario) && 
    validarDireccion(direccion_usuario) && 
    validarCorreo(correo_usuario);
}
function validarNombre(nombre_usuario) {
    var valor = nombre_usuario.value.trim();
    var valido = valor.length >= 3 && valor.length <= 100;

    actualizarClaseValidacion(nombre_usuario, valido);
    return valido;
}
function validarDireccion(direccion_usuario) {
    var valor = direccion_usuario.value.trim();
    var valido = valor.length >= 5 && valor.length <= 26;

    actualizarClaseValidacion(direccion_usuario, valido);
    return valido;
}
function validarCorreo(correo_usuario) {
    var valor = correo_usuario.value.trim();
    var valido = valor.length >= 4 && valor.length <= 255;

    actualizarClaseValidacion(correo_usuario, valido);
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
    document.getElementById("nombre_usuario").value = "";
    document.getElementById("direccion_usuario").value = "";
    document.getElementById("correo_usuario").value = "";
    document.getElementById("tipo_usuario").value = "";

    document.getElementById("nombre_usuario").classList.remove("is-valid", "is-invalid");
    document.getElementById("direccion_usuario").classList.remove("is-valid", "is-invalid");
    document.getElementById("correo_usuario").classList.remove("is-valid", "is-invalid");
    document.getElementById("tipo_usuario").classList.remove("is-valid", "is-invalid");
}


$(document).ready(function() {
    listarUsuarios();
});


