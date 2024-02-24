let start = 1; 
document.addEventListener('DOMContentLoaded', (event) => {
document.getElementById('loadMoreButton').addEventListener('click', function() {
    fetch(`/bets/json?start=${start}&count=9`)
    
        .then(response => response.json())
        .then(events => {
            let container = document.getElementById('eventsContainer');
            events.forEach(event => {
                let div = document.createElement('div');
                div.className = 'col-lg-4 col-md-6 text-center';
                div.innerHTML = `
                    <div class="single-product-item">
                        <div class="product-image">
                            <a href="/single-product?id=${event.id}"><img src="${event.image}" alt=""></a>
                        </div>
                        <h3>${event.name}</h3>
                        <p class="product-price">${event.championship} <span>${event.sport}</span></p>
                        <a href="single-product?id=${event.id}" class="cart-btn">Realizar apuesta</a>
                    </div>
                `;
                container.appendChild(div);
                console.log("hola");
            });

    
            start++;
        });
});});