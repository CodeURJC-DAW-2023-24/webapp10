
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
              <p class="product-price"><span>Per Kg</span> ${match.price} </p>
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