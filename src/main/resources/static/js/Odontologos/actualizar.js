window.addEventListener('load', function () {

    const formulario = document.querySelector('#update_odontologo_form');
    let siteParams= new URLSearchParams(window.location.search)
    let odontologoId = siteParams.get('id')

    findBy(odontologoId)

    formulario.addEventListener('submit', function (event) {
      event.preventDefault()

      let formData = {
          id: odontologoId,
          matricula: document.querySelector('#matricula').value,
          nombre: document.querySelector('#nombre').value,
          apellido: document.querySelector('#apellido').value,
      };

      const url = `/odontologos/actualizar`;
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
            text: `Ya se actualizaron los datos del odontólogo en la base de datos. ¿Desea continuar?`,
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
  const url = `/odontologos/listar/${id}`;
  const settings = {
      method: 'GET'
  }
  fetch(url, settings)
    .then(response => response.json())
    .then(data => {
      let odontologo = data;

      document.querySelector('#matricula').value = odontologo.matricula;
      document.querySelector('#nombre').value = odontologo.nombre;
      document.querySelector('#apellido').value = odontologo.apellido;
    }).catch(error => {
        alert("Error: " + error);
      })
}