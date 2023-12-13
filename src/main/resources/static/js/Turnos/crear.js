window.addEventListener('load', function () {

    const formulario = document.querySelector('#add_new_turno');

    cargarPacientes()
    cargarOdontologos()

    //Ante un submit del formulario se ejecutará la siguiente funcion
    formulario.addEventListener('submit', function (event) {
      event.preventDefault()
        
       //creamos un JSON que tendrá los datos de la nueva película
        const formData = {
          pacienteId: document.querySelector('#paciente').value,
          odontologoId: document.querySelector('#odontologo').value,
          fechaTurno: document.querySelector('#fechaTurno').value,
        };

        //invocamos utilizando la función fetch la API peliculas con el método POST que guardará
        //la película que enviaremos en formato JSON
        const url = '/turnos/guardar';
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
                  text: `Ya se guardo el turno en la base de datos. ¿Desea continuar agregando turnos?`,
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

    function cargarPacientes() {
      const url = '/pacientes/listar';
      const settings = { method: 'GET' }

      fetch( url, settings )
      .then( response => response.json() )
      .then( data => {
        for(paciente of data){
            let select = document.getElementById("paciente")

            select.innerHTML += `<option value="${paciente.id}">${paciente.nombre.toUpperCase()} ${paciente.apellido.toUpperCase()}</option>`
        };
      })
    }

    function cargarOdontologos() {
      const url = '/odontologos/listar';
      const settings = { method: 'GET' }

      fetch( url, settings )
      .then( response => response.json() )
      .then( data => {
        for(odontologo of data){
            let select = document.getElementById("odontologo")

            select.innerHTML += `<option value="${odontologo.id}">${odontologo.nombre.toUpperCase()} ${odontologo.apellido.toUpperCase()}</option>`
        };
      })
    }

});