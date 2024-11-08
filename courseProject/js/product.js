window.addEventListener('load', getProduct());


function getProduct() {
    const queryString = window.location.search;
    const urlParam = new URLSearchParams(queryString);
    $.ajax({
        headers: {'Authorization': localStorage.getItem('token')},
        url: "http://localhost:8080/stock/" + urlParam.get('p'),
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            const image = document.getElementById("productImage");
            var baseStr64 = data.img;
            if (baseStr64 == "") image.setAttribute('src', "./images/noimage.png");
            else image.setAttribute('src', "data:image/jpg;base64," + baseStr64);
            localStorage.setItem("nameSubscription", data.name);

            const category = document.querySelector(".product__description--category");
            category.innerHTML = data.category;
            
            const titleBold = document.querySelector(".product__description--titleBold");
            titleBold.innerHTML = data.name.split(":")[0];

            const titleThin = document.querySelector(".product__description--titleThin");
            var valueName = "";
            if (data.name.split(":")[1] != null) valueName = data.name.split(":")[1];
            titleThin.innerHTML = valueName;

            const price = document.querySelector(".product__description--titlePriceBold");
            price.innerHTML = data.price + "€";

            const iva = document.querySelector(".product__description--titlePriceThin");
            iva.innerHTML = data.iva;

            const provider = document.querySelector(".product__description-span");
            provider.innerHTML = data.provider;

            $.ajax({
                headers: {'Authorization': localStorage.getItem('token')},
                url: "http://localhost:8080/subscriptions/"+localStorage.getItem('user_ID'),
                type: 'GET',
                dataType: 'json',
                success: function (a) {
                    for (var key in a) {
                        var value = a[key];
                        if (value.product.id == urlParam.get('p')){
                            const amount=document.getElementById('quantity');
                            greenButton();
                        }
                    }        	
                },
                error: function() { alert('Failed!'); },
            });

        },
        error: function () { alert('Failed!'); },
    });
}

function postSubscription() {
    greenButton();
    const amount=document.getElementById('quantity');
    const queryString = window.location.search;
    const urlParam = new URLSearchParams(queryString);
    
    $.ajax({
        headers: {'Authorization': localStorage.getItem('token')},
        url: "http://localhost:8080/subscription/"+localStorage.getItem('user_ID')+"/"+urlParam.get('p')+"/"+amount.value,
        type: 'POST',
        dataType: 'json',
        success: function () {},
        error: function () {},
    });
}

function deleteSubscription(){
    blueButton();
    const queryString = window.location.search;
    const urlParam = new URLSearchParams(queryString);
    $.ajax({
        headers: {'Authorization': localStorage.getItem('token')},
        url: "http://localhost:8080/subscriptions/"+localStorage.getItem('user_ID'),
        type: 'GET',
        dataType: 'json',
        success: function (data) {
			for (var key in data) {
                var value = data[key];
                if(value.product.id == urlParam.get('p')){

                    var token = localStorage.getItem('token').split(" ")[1];
                    var base64Url = token.split('.')[1];
                    var decodedValue = JSON.parse(window.atob(base64Url));
                    var role = decodedValue.authorities[0].authority;

                    if("ROLE_USER" == role){
                        $.ajax({
                            headers: {'Authorization': localStorage.getItem('token')},
                            url: "http://localhost:8080/subscription/"+localStorage.getItem('user_ID')+"/"+value.id,
                            type: 'DELETE',
                            dataType: 'json',
                            success: function () {},
                            error: function () {},
                        });
                    }
                    else if("ROLE_ADMIN" == role){
                        $.ajax({
                            headers: {'Authorization': localStorage.getItem('token')},
                            url: "http://localhost:8080/subscriptionAdmin/"+localStorage.getItem('user_ID')+"/"+value.id,
                            type: 'DELETE',
                            dataType: 'json',
                            success: function () {},
                            error: function () {},
                        });
                    }
                }
            }        	
        },
        error: function() { alert('Failed!'); },
    });
}

function greenButton(){
    const svg = document.getElementById('svgTick');
    const button = document.getElementById('before__button');
    const amount = document.getElementById('quantity');
    const buttonText = document.getElementById('buttonText');

    buttonText.remove();

    button.setAttribute("onclick","deleteSubscription()");
    button.removeAttribute("id");
    button.setAttribute("id", 'after__button');
    
    amount.setAttribute("type", "text");
    amount.style.pointerEvents ='none';
    svg.style.visibility = 'visible';
}

function blueButton(){
    const svg = document.getElementById('svgTick');
    const button = document.querySelector('.product__description--buy button');
    const amount = document.getElementById('quantity');
    const buttonText = document.createElement("SPAN");
    buttonText.setAttribute("id","buttonText");
    buttonText.innerHTML = "Subscribe";
    button.appendChild(buttonText);

    button.removeAttribute("style");
    button.setAttribute("onclick","postSubscription()");
    button.removeAttribute("id");
    button.setAttribute("id", 'before__button');
    
    amount.setAttribute("type","number");
    amount.style.pointerEvents="all";
    svg.style.visibility ='hidden';
}
