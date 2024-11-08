window.addEventListener('load',getSubscriptions());

function getSubscriptions() {
    console.log(localStorage.getItem('token'));
    $.ajax({
        headers: {'Authorization': localStorage.getItem('token')},
        url: "http://localhost:8080/subscriptions/"+localStorage.getItem('user_ID'),
        type: 'GET',
        dataType: 'json',
        success: function (data) {
			for (var key in data) {
                var value = data[key];
                const orders = document.querySelector(".orders");

                const subscription = document.createElement("DIV");
                subscription.classList.add("subscription");

                const subscriptionProduct = document.createElement("DIV");
                subscriptionProduct.classList.add("subscription__element");
                subscriptionProduct.classList.add("subscription__element--product");

                const subscriptionImage = document.createElement("DIV");
                subscriptionImage.classList.add("subscription__image");

                const image = document.createElement("IMG");
                var baseStr64=value.product.img;
                if(baseStr64==""){
                    image.setAttribute('src', "./images/noimage.png");
                }
                else image.setAttribute('src', "data:image/jpg;base64," + baseStr64);

                const subscriptionDescription = document.createElement("DIV");
                subscriptionDescription.classList.add("subscription__description");

                const descriptionTitle = document.createElement("DIV");
                descriptionTitle.classList.add("subscription__description-titleName");

                const descriptionTitleBold = document.createElement("SPAN");
                descriptionTitleBold.classList.add("subscription__description-titleBold");
                descriptionTitleBold.innerHTML = value.product.name.split(":")[0];

                const descriptionTitleThin = document.createElement("SPAN");
                descriptionTitleThin.classList.add("subscription__description-titleThin");
                descriptionTitleThin.innerHTML = value.product.name.split(":")[1];

                const descriptionTitlePrice = document.createElement("DIV");
                descriptionTitlePrice.classList.add("subscription__description-titlePrice");

                const descriptionTitlePriceBold = document.createElement("SPAN");
                descriptionTitlePriceBold.classList.add("subscription__description-titlePriceBold");
                descriptionTitlePriceBold.innerHTML = value.product.price;

                const descriptionTitlePriceThin = document.createElement("SPAN");
                descriptionTitlePriceThin.classList.add("subscription__description-titlePriceThin");
                descriptionTitlePriceThin.innerHTML = value.product.iva;

                const subscriptionChange = document.createElement("DIV");
                subscriptionChange.classList.add("subscription__change");

                const subscriptionInput = document.createElement("INPUT");
                subscriptionInput.setAttribute("type", "number");
                subscriptionInput.setAttribute("id", value.id);
                subscriptionInput.setAttribute("min", "0");
                subscriptionInput.setAttribute("value", value.amount);
                subscriptionInput.setAttribute("title", "Quantity");

                const subscriptionButton = document.createElement("BUTTON");
                subscriptionButton.onclick = function(){updateSubscription(subscriptionInput.id,subscriptionInput.value),window.location.reload()}
                subscriptionButton.setAttribute("id", "addToCart");

                const buttonText = document.createElement("SPAN");
                buttonText.innerHTML = "Change";

                descriptionTitle.appendChild(descriptionTitleBold);
                descriptionTitle.appendChild(descriptionTitleThin);
                descriptionTitlePrice.appendChild(descriptionTitlePriceBold);
                descriptionTitlePrice.appendChild(descriptionTitlePriceThin);
                subscriptionButton.appendChild(buttonText);
                
                subscriptionImage.appendChild(image);
                subscriptionDescription.appendChild(descriptionTitle);
                subscriptionDescription.appendChild(descriptionTitlePrice);
                subscriptionChange.appendChild(subscriptionInput);
                subscriptionChange.appendChild(subscriptionButton);

                subscriptionProduct.appendChild(subscriptionImage);
                subscriptionProduct.appendChild(subscriptionDescription);
                subscriptionProduct.appendChild(subscriptionChange);

                subscription.appendChild(subscriptionProduct);

                orders.appendChild(subscription);
            }        	
        },
        error: function() { alert('Failed!'); },
    });
};

function updateSubscription(subId,amount) {
    
    var token = localStorage.getItem('token').split(" ")[1];
    var base64Url = token.split('.')[1];
    var decodedValue = JSON.parse(window.atob(base64Url));
    var role = decodedValue.authorities[0].authority;

    if("ROLE_USER" == role){
        $.ajax({
            headers: {'Authorization': localStorage.getItem('token')},
            url: "http://localhost:8080/subscription/"+localStorage.getItem('user_ID')+"/"+subId+"/"+amount,
            type: 'PUT',
            dataType: 'json',
            success: function () {},
            error: function () {},
        });
    }
    else if("ROLE_ADMIN" == role){
        $.ajax({
            headers: {'Authorization': localStorage.getItem('token')},
            url: "http://localhost:8080/subscriptionAdmin/"+localStorage.getItem('user_ID')+"/"+subId+"/"+amount,
            type: 'PUT',
            dataType: 'json',
            success: function () {},
            error: function () {},
        });
    }
}