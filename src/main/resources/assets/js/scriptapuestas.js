/*
    let matches = [
        {
          image: "assets/img/partidos/madridatleti.webp",
          name: "Atlético de Madrid - Real Madrid",
          price: "85$",
          date : ""
  
        },
        {
          image: "assets/img/partidos/sevillatenerife.webp",
          name: "Villarreal - Tenerife",
          price: "70$",
      
        },
        {
          image: "assets/img/partidos/levanteleganes.webp",
          name: "Levante - Leganés",
          price: "35$",
        
        },
        {
          image: "assets/img/partidos/madridgirona.webp",
          name: "Real Madrid - Girona",
          price: "50$",
      
        },
        {
          image: "assets/img/partidos/cadizbetis.webp",
          name: "Cádiz - Betis",
          price: "45$",
      
        },
        {
          image: "assets/img/partidos/getafecelta.webp",
          name: "Getafe - Celta",
          price: "80$",
     
        },
       
      ];
  
      let matchContainer = document.querySelector('.product-lists');

      if (matchContainer) { 
        matches.forEach((match, index) => {
          let matchElement = document.createElement('div');
          matchElement.className = 'col-lg-4 col-md-6 text-center';
          matchElement.innerHTML = `
            <div class="single-product-item">
              <div class="product-image">
                <a href="single-product.html?id=${index}"><img src="${match.image}" alt=""></a>
              </div>
              <h3>${match.name}</h3>
              <p> </p>
              <a href="single-product.html?id=${index}" class="cart-btn"></i>Realizar apuesta</a>
            </div>
          `;
      
          matchContainer.appendChild(matchElement);
        });
      }
      
      let params = new URLSearchParams(window.location.search);
      let matchId = params.get('id');
      
      if (matchId) { 
        let match = matches[matchId];
      
        document.querySelector('.single-product-img img').src = match.image;
        document.querySelector('.single-product-content h3').textContent = match.name;
        document.querySelector('.single-product-pricing').textContent = `${match.price} `;
        console.log(match.name);
      } 
*/
      function validateForm() {
        var inputs = document.getElementsByTagName('input');
        console.log("eee");
        for (var i = 0; i < inputs.length; i++) {
            if (inputs[i].value === '') {
                alert('Por favor, rellene todos los campos antes de enviar el formulario.');
                return false;
            }
        }
        return true;
    }

    function apuesta() {
        var apostado = document.getElementById("apuesta-goles").value;
        document.getElementById("total-apuesta").innerHTML = apostado + "€";
      
    }
 
document.getElementById('showFormButton').addEventListener('click', function() {
  document.getElementById('newmatch').style.display = 'block';
});

document.getElementById('editEventButton').addEventListener('click', function() {

  var matches = document.getElementsByClassName('single-product-item');
  for (var i = 0; i < matches.length; i++) {
      var match = matches[i];
      var name = match.querySelector('h3').innerText;
      var price = match.querySelector('.product-price').innerText;
      match.querySelector('h3').innerHTML = '<input type="text" value="' + name + '">';
      match.querySelector('.product-price').innerHTML = '<input type="text" value="' + price + '">';
  }
});

var deleteButtons = document.getElementsByClassName('deleteEventButton');
for (var i = 0; i < deleteButtons.length; i++) {
  deleteButtons[i].addEventListener('click', function() {

      this.closest('.single-product-item').remove();
  });
}


function select_bet(elemento) {
    var botones = document.getElementsByClassName('bordered-btn-bets');

    // Deseleccionar todos los botones
    for (var i = 0; i < botones.length; i++) {
        botones[i].style.backgroundColor = ''; // Restaurar el color por defecto
    }

    // Seleccionar el botón actual
    elemento.style.backgroundColor = "black";
    document.getElementById('selected-bet').value = elemento.innerText;
}


function tarjetaValida(){
    var card = document.forms["form-card"]["card-number"].value;
    var date = document.forms["form-card"]["card-date"].value;
    var cvv = document.forms["form-card"]["card-cvv"].value;
    var amount = document.forms["form-card"]["amount"].value;

    function isValidDate(date) {
        var parts = date.split('/');
        var month = parseInt(parts[0], 10);
        var year = parseInt(parts[1], 10);

        // Obtener la fecha actual
        var currentDate = new Date();
        var currentYear = currentDate.getFullYear();
        var currentMonth = currentDate.getMonth() + 1; // El método getMonth() devuelve el mes indexado desde 0, por lo que sumamos 1

        // Comprobar si la fecha es válida y posterior a la fecha actual
        return year > currentYear || (year === currentYear && month >= currentMonth);
    }

    if (card.length() !== 12 && !isValidDate(date) && cvv.length() !== 3 && amount < 0) {
        alert("La tarjeta tiene que tener 12 digitos, la fecha posterior a la actual, el cvv tres cifras y la cantidad mayor que 0");
        return false;
    }
    return true;

}

function validCVV() {
    var introduced_cvv = document.forms["form-card"]["card-cvv"].value;
    var amount = document.forms["form-card"]["amount"].value;
    var real_cvv = document.forms["form-card"]["real-cvv"].value;

    if (introduced_cvv !== real_cvv) {
        alert("No coinciden los cvv");
        return false;
    }else {
        return true;
    }

}

function comprobarApuesta(){
    var money = document.forms["form-bet"]["bet-amount"].value;
    var result = document.getElementById("selected-bet").value;
    console.log(result);
    if (money <= 0 || money == null || result === '') {
        alert("Se te olvida apostar pasta o a que apostar ludopatilla");
        return false;
    }
    return true;
}

let groupedEvents = [];
for(let i = 0; i < events.length; i += 3) {
    groupedEvents.push(events.slice(i, i+3));
}