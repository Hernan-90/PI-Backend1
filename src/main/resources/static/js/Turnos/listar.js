window.addEventListener('load', function () {
  const url = '/turnos/listar';
  const settings = { method: 'GET' }

  fetch( url, settings )
  .then( response => response.json() )
  .then( data => {
    for(turno of data){
        let table = document.getElementById("turnoTable");
        let turnoRow = table.insertRow();
        let tr_id = 'tr_' + turno.id;

        turnoRow.id = tr_id;

        let deleteButton = `
          <a id='btn_delete_${turno.id}' onclick=borrar(${turno.id}) class='btn btn-danger'>
            <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="#ffff" viewBox="0 0 256 256"><path d="M216,48H176V40a24,24,0,0,0-24-24H104A24,24,0,0,0,80,40v8H40a8,8,0,0,0,0,16h8V208a16,16,0,0,0,16,16H192a16,16,0,0,0,16-16V64h8a8,8,0,0,0,0-16ZM96,40a8,8,0,0,1,8-8h48a8,8,0,0,1,8,8v8H96Zm96,168H64V64H192ZM112,104v64a8,8,0,0,1-16,0V104a8,8,0,0,1,16,0Zm48,0v64a8,8,0,0,1-16,0V104a8,8,0,0,1,16,0Z"></path></svg>
          </a>
        `

        let updateButton = `
          <a id='btn_id_${turno.id}' onclick=editar(${turno.id}) class='btn btn-primary'>
            <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="#ffff" viewBox="0 0 256 256"><path d="M227.31,73.37,182.63,28.68a16,16,0,0,0-22.63,0L36.69,152A15.86,15.86,0,0,0,32,163.31V208a16,16,0,0,0,16,16H92.69A15.86,15.86,0,0,0,104,219.31L227.31,96a16,16,0,0,0,0-22.63ZM51.31,160,136,75.31,152.69,92,68,176.68ZM48,179.31,76.69,208H48Zm48,25.38L79.31,188,164,103.31,180.69,120Zm96-96L147.31,64l24-24L216,84.68Z"></path></svg>
          </a>
        `

        turnoRow.innerHTML = `
          <td>${turno.id}</td>
          <td class='td_paciente' id='dato-paciente-${turno.id}'></td>
          <td class='td_odontologo' id='dato-odontologo-${turno.id}'></td>
          <td class='td_fechaTurno'>${turno.fechaTurno}</td>
          <td class='d-flex gap-1'>${updateButton}${deleteButton}</td>
        `

        cargarPaciente(turno.pacienteId, turno.id)
        cargarOdontologo(turno.odontologoId, turno.id)
    };
  })

  editar = (turnoId) => {

    Swal.fire({
      text: `¿Desea editar al turno seleccionado?`,
      icon: 'info',
      showDenyButton: true,
      confirmButtonText: 'Si',
      denyButtonText: 'No'
    })
    .then((result) => {
      if(result.isConfirmed){
        window.location.href = `/views/Turno/editar.html?id=${turnoId}`
      } else if (result.isDenied) {
        Swal.close()
        }
    })
  }

  borrar = (turnoId) => {
    Swal.fire({
      title: 'Cuidado',
      text: `"Desea eliminar el turno seleccionado?"`,
      icon: 'warning',
      showDenyButton: true,
      confirmButtonText: 'Si',
      denyButtonText: 'No'
    })
    .then((result) => {
      if(result.isConfirmed){
        fetch(`/turnos/eliminar/${turnoId}`, {
          method: 'DELETE',
          headers: {
                  'Content-Type': 'application/json',
              },
        })
          .then(response => { response, window.location.reload() })
      } else if (result.isDenied) {
        Swal.close()
        }
    })
  }

  function cargarPaciente(pacienteId, turnoId) {
    const url = `/pacientes/listar/${pacienteId}`;
    const settings = { method: 'GET' }
    let pacienteData = document.getElementById(`dato-paciente-${turnoId}`)

    fetch( url, settings )
    .then( response => response.json() )
    .then( paciente => {
      pacienteData.innerHTML = `${paciente.nombre.toUpperCase()} ${paciente.apellido.toUpperCase()}`
    })
  }

  function cargarOdontologo(odontologoId, turnoId) {
    const url = `/odontologos/listar/${odontologoId}`;
    const settings = { method: 'GET' }
    let odontologoData = document.getElementById(`dato-odontologo-${turnoId}`)

    fetch( url, settings )
    .then( response => response.json() )
    .then( odontologo => {
      odontologoData.innerHTML = `${odontologo.nombre.toUpperCase()} ${odontologo.apellido.toUpperCase()}`
    })
  }

})