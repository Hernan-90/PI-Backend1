window.addEventListener('load', function () {

    const formulario = document.querySelector('#update_turno_form');
    let siteParams= new URLSearchParams(window.location.search)
    let turnoId = siteParams.get('id')

    findBy(turnoId)
    cargarPacientes()
    cargarOdontologos()

    formulario.addEventListener('submit', function (event) {
      event.preventDefault()

      let formData = {
          id: turnoId,
          pacienteId: document.querySelector('#paciente').value,
          odontologoId: document.querySelector('#odontologo').value,
          fechaTurno: document.querySelector('#fechaTurno').value,
      };

      const url = `/turnos/actualizar`;
      const settings = {
          method: 'PUT',
          headers: {
              'Content-Type': 'application/json',
          },
          body: JSON.stringify(formData)
      }

      fetch(url, settings)
        .then(response => response)
        .then( data => {
          Swal.fire({
            title: 'Excelente',
            text: `Ya se actualizaron los datos del turno en la base de datos. ¿Desea continuar?`,
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
  const url = `/turnos/listar/${id}`;
  const settings = {
      method: 'GET'
  }
  fetch(url, settings)
    .then(response => response.json())
    .then(data => {
      let turno = data;

      cargarPacienteTurno(turno.pacienteId)
      cargarOdontologoTurno(turno.odontologoId)
      document.querySelector('#fechaTurno').value = turno.fechaTurno
    }).catch(error => {
        alert("Error: " + error);
      })
}

function cargarPacientes() {
  const url = '/pacientes/listar';
  const settings = { method: 'GET' }

  fetch( url, settings )
  .then( response => response.json() )
  .then( data => {
    for(paciente of data){
        let select = document.getElementById("paciente")

        select.innerHTML += `<option value="${paciente.id}" id="paciente-${paciente.id}">${paciente.nombre.toUpperCase()} ${paciente.apellido.toUpperCase()}</option>`
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

        select.innerHTML += `<option value="${odontologo.id}" id="odontologo-${odontologo.id}">${odontologo.nombre.toUpperCase()} ${odontologo.apellido.toUpperCase()}</option>`
    };
  })
}

function cargarPacienteTurno(pacienteId) {
  const url = `/pacientes/listar/${pacienteId}`;
  const settings = { method: 'GET' }
  
  fetch( url, settings )
  .then( response => response.json() )
  .then( paciente => {
    let select = document.getElementById(`paciente-${pacienteId}`)
    select.setAttribute('selected', '')
  })
}

function cargarOdontologoTurno(odontologoId) {
  const url = `/odontologos/listar/${odontologoId}`;
  const settings = { method: 'GET' }
  
  fetch( url, settings )
  .then( response => response.json() )
  .then( odontologo => {
    let select = document.getElementById(`odontologo-${odontologoId}`)
    select.setAttribute('selected', '')
  })
}