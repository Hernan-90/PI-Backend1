window.addEventListener('load', function () {

    //Al cargar la pagina buscamos y obtenemos el formulario donde estarán
    //los datos que el usuario cargará de la nueva pelicula
    const formulario = document.querySelector('#add_new_paciente');

    //Ante un submit del formulario se ejecutará la siguiente funcion
    formulario.addEventListener('submit', function (event) {
      event.preventDefault()

       //creamos un JSON que tendrá los datos de la nueva película
        const formData = {
          nombre: document.querySelector('#nombre').value,
          apellido: document.querySelector('#apellido').value,
          email: document.querySelector('#email').value,
          cedula: document.querySelector('#cedula').value,
          fechaIngreso: document.querySelector('#fechaIngreso').value,
          domicilio: {
            calle: document.querySelector('#calle').value,
            numero: document.querySelector('#numero').value,
            localidad: document.querySelector('#localidad').value,
            provincia: document.querySelector('#provincia').value,           
          }
        };

        //invocamos utilizando la función fetch la API peliculas con el método POST que guardará
        //la película que enviaremos en formato JSON
        const url = '/pacientes/guardar';
        const settings = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        }

        fetch(url, settings)
            .then(response => response.json())
            .then(data => {
                Swal.fire({
                  title: 'Excelente',
                  text: `Ya se guardo el paciente en la base de datos. ¿Desea continuar agregando pacientes?`,
                  icon: 'success',
                  showDenyButton: true,
                  confirmButtonText: 'Si',
                  denyButtonText: 'No'
                })
                .then((result) => {
                  if(result.isConfirmed){
                    Swal.close()
                  } else if (result.isDenied) {
                    window.location.href = "./listarTodos.html"
                    }
                })
            })
            .catch(error => {
                    Swal.fire({
                      icon: "error",
                      title: "Oops...",
                      text: "Something went wrong!",
                    });
            })
    });

    function resetUploadForm(){
        document.querySelector('#matricula').value = "";
        document.querySelector('#nombre').value = "";
        document.querySelector('#apellido').value = "";
    }

});