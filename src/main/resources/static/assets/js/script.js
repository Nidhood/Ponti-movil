// Manejo del Registro
document.getElementById('registerForm')?.addEventListener('submit', function(e) {
    e.preventDefault();

    let nombre = document.querySelector('input[name="nombre"]').value;
    let apellido = document.querySelector('input[name="apellido"]').value;
    let correo = document.querySelector('input[name="correo"]').value;
    let nombreUsuario = document.querySelector('input[name="nombreUsuario"]').value;
    let contrasena = document.querySelector('input[name="contrasena"]').value;

    let usuario = {
        nombre: nombre,
        apellido: apellido,
        correo: correo,
        nombreUsuario: nombreUsuario,
        contrasena: contrasena,
        tipoUsuario: "usuario" // Tipo de usuario
    };

    fetch('/usuarios/crear', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(usuario)
    })
        .then(response => {
            if (response.ok) {
                alert("Registro exitoso");
                window.location.href = "login.html";
            } else {
                alert("Error en el registro");
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert("Error en el registro");
        });
});

// Manejo del Login
document.getElementById('loginForm')?.addEventListener('submit', function(e) {
    e.preventDefault();

    let nombreUsuario = document.querySelector('input[name="username"]').value;
    let contrasena = document.querySelector('input[name="password"]').value;

    // Fetch para obtener todos los usuarios
    fetch('/usuarios')
        .then(response => response.json())
        .then(usuarios => {
            let usuarioEncontrado = usuarios.find(usuario => usuario.nombreUsuario === nombreUsuario && usuario.contrasena === contrasena);

            if (usuarioEncontrado) {
                alert("Login exitoso");
                window.location.href = "dashboard.html"; // Redirigir al usuario a su dashboard o página principal
            } else {
                alert("Nombre de usuario o contraseña incorrectos");
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert("Error en el inicio de sesión");
        });
});
function confirmarEliminacion(event) {
    const confirmacion = confirm("¿Está seguro que quiere eliminar?");
    if (!confirmacion) {
        event.preventDefault(); // Evita que el enlace se siga si no se confirma la eliminación
    }
}

function toggleDropdown(button) {
    var dropdown = button.nextElementSibling;
    if (dropdown.style.display === 'block') {
        dropdown.style.display = 'none';
    } else {
        dropdown.style.display = 'block';
    }

    // Hide dropdown if clicked outside
    document.addEventListener('click', function(event) {
        if (!button.contains(event.target) && !dropdown.contains(event.target)) {
            dropdown.style.display = 'none';
        }
    });
}

function submitForm(form) {
    const checkboxes = form.parentElement.parentElement.querySelectorAll('input[name="diasSeleccionados"]:checked');
    const selectedDays = Array.from(checkboxes).map(checkbox => checkbox.value);

    if (selectedDays.length > 0) {
        selectedDays.forEach(day => {
            let hiddenInput = document.createElement('input');
            hiddenInput.type = 'hidden';
            hiddenInput.name = 'diasSemana';
            hiddenInput.value = day;
            form.appendChild(hiddenInput);
        });
        form.submit();
    } else {
        alert('Por favor, seleccione al menos un día.');
    }
}