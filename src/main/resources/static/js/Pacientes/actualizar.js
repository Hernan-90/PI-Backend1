window.addEventListener('load', function () {

    const formulario = document.querySelector('#update_paciente_form');
    let siteParams= new URLSearchParams(window.location.search)
    let pacienteId = siteParams.get('id')

    findBy(pacienteId)

    formulario.addEventListener('submit', function (event) {
      event.preventDefault()

      let formData = {
          id: pacienteId,
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

      const url = `/pacientes/actualizar`;
      const settings = {
          method: 'PUT',
          headers: {
              'Content-Type': 'application/json',
          },
          body: JSON.stringify(formData)
      }

      fetch(url, settings)
        .then(response => response)
        .then(data => {
          Swal.fire({
            title: 'Excelente',
            text: `Ya se actualizaron los datos del paciente en la base de datos. ¿Desea continuar?`,
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
    })
})

function findBy(id) {
  const url = `/pacientes/listar/${id}`;
  const settings = {
      method: 'GET'
  }
  fetch(url, settings)
    .then(response => response.json())
    .then(data => {
      let paciente = data;
      document.querySelector('#nombre').value = paciente.nombre;
      document.querySelector('#apellido').value = paciente.apellido;
      document.querySelector('#email').value = paciente.email;
      document.querySelector('#cedula').value = paciente.cedula;
      document.querySelector('#fechaIngreso').value = paciente.fechaIngreso;
      document.querySelector('#calle').value = paciente.domicilio.calle;
      document.querySelector('#numero').value = paciente.domicilio.numero;
      document.querySelector('#localidad').value = paciente.domicilio.localidad;
      document.querySelector('#provincia').value = paciente.domicilio.provincia;
    }).catch(error => {
        alert("Error: " + error);
      })
}