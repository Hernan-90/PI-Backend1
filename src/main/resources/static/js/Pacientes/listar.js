window.addEventListener('load', function () {
  const url = '/pacientes/listar';
  const settings = { method: 'GET' }

  fetch( url,settings )
  .then( response => response.json() )
  .then( data => {

    for(paciente of data){
        let table = document.getElementById("pacienteTable");
        let pacienteRow = table.insertRow();
        let tr_id = 'tr_' + paciente.id;

        pacienteRow.id = tr_id;

        let deleteButton = `
          <a id='btn_delete_${paciente.id}' onclick=borrar(${paciente.id}) class='btn btn-danger'>
            <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="#ffff" viewBox="0 0 256 256"><path d="M216,48H176V40a24,24,0,0,0-24-24H104A24,24,0,0,0,80,40v8H40a8,8,0,0,0,0,16h8V208a16,16,0,0,0,16,16H192a16,16,0,0,0,16-16V64h8a8,8,0,0,0,0-16ZM96,40a8,8,0,0,1,8-8h48a8,8,0,0,1,8,8v8H96Zm96,168H64V64H192ZM112,104v64a8,8,0,0,1-16,0V104a8,8,0,0,1,16,0Zm48,0v64a8,8,0,0,1-16,0V104a8,8,0,0,1,16,0Z"></path></svg>
          </a>
        `

        let updateButton = `
          <a id='btn_id_${paciente.id}' onclick=editar(${paciente.id}) class='btn btn-primary'>
            <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="#ffff" viewBox="0 0 256 256"><path d="M227.31,73.37,182.63,28.68a16,16,0,0,0-22.63,0L36.69,152A15.86,15.86,0,0,0,32,163.31V208a16,16,0,0,0,16,16H92.69A15.86,15.86,0,0,0,104,219.31L227.31,96a16,16,0,0,0,0-22.63ZM51.31,160,136,75.31,152.69,92,68,176.68ZM48,179.31,76.69,208H48Zm48,25.38L79.31,188,164,103.31,180.69,120Zm96-96L147.31,64l24-24L216,84.68Z"></path></svg>
          </a>
        `

        pacienteRow.innerHTML = `
          <td>${paciente.id}</td>
          <td class='td_nombre'>${paciente.nombre.toUpperCase()}</td>
          <td class='td_apellido'>${paciente.apellido.toUpperCase()}</td>
          <td class='td_email'>${paciente.email}</td>
          <td class='d-flex gap-1'>${updateButton}${deleteButton}</td>
        `
    };
  })

  editar = (pacienteId) => {

    Swal.fire({
      text: `¿Desea editar al paciente seleccionado?`,
      icon: 'info',
      showDenyButton: true,
      confirmButtonText: 'Si',
      denyButtonText: 'No'
    })
    .then((result) => {
      if(result.isConfirmed){
        window.location.href = `/views/Paciente/editar.html?id=${pacienteId}`
      } else if (result.isDenied) {
        Swal.close()
        }
    })
  }

  borrar = (pacienteId) => {

    Swal.fire({
      title: 'Cuidado',
      text: `"Desea eliminar al paciente seleccionado?"`,
      icon: 'warning',
      showDenyButton: true,
      confirmButtonText: 'Si',
      denyButtonText: 'No'
    })
    .then((result) => {
      if(result.isConfirmed){
        fetch(`/pacientes/eliminar/${pacienteId}`, {
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

})