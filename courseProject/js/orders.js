window.addEventListener('load',getOrders("Open"));

function getSelectedIndex(){
    var select = document.querySelector(".orders__filter select");
    var selectedIndex = select.options[select.selectedIndex].text;
    var otherProducts = document.querySelectorAll(".subscription");
    otherProducts.forEach(e => e.remove());
    getOrders(selectedIndex);
}

function getOrders(state){
    $.ajax({
        headers: {'Authorization': localStorage.getItem('token')},
        url: "http://localhost:8080/orders/"+state+"/"+localStorage.getItem('user_ID'),
        type: 'GET',
        dataType: 'json',
        success: function (data) {
			for (var key in data) {
                var value = data[key];
                
                const orders = document.querySelector(".orders");

                const subscription = document.createElement("DIV");
                subscription.classList.add("subscription");
                subscription.setAttribute("id", value.id);

                const subscriptionTitle = document.createElement("DIV");
                subscriptionTitle.classList.add("subscription__element");
                subscriptionTitle.classList.add("subscription__element--title");

                const subscriptionaTitleSpan = document.createElement("SPAN");
                subscriptionaTitleSpan.classList.add("subscription__span");
                subscriptionaTitleSpan.innerHTML = "Closed date: " + value.date.split("T")[0] + " | Articles: " + value.subscriptions.length;

                const subscriptionTitleButton = document.createElement("BUTTON");
                subscriptionTitleButton.onclick = function(){clickSubscription(subscription.id)};
                subscriptionTitleButton.setAttribute("type", "button");
                subscriptionTitleButton.classList.add("subscription__element-button");
                
                const subscriptionTitleIcon = document.createElement("I");
                subscriptionTitleIcon.classList.add("fas");
                subscriptionTitleIcon.classList.add("fa-chevron-down");

                const subscriptionOrderNumber = document.createElement("DIV");
                subscriptionOrderNumber.classList.add("subscription__element");
                subscriptionOrderNumber.classList.add("subscription__element--order-number");

                const subscriptionOrderNumberSpan = document.createElement("SPAN");
                subscriptionOrderNumberSpan.classList.add("subscription__span");
                subscriptionOrderNumberSpan.innerHTML = "Order number: " + value.id;

                const subscriptionHidden = document.createElement("DIV");
                subscriptionHidden.classList.add("subscription__hidden");
                
                const subscriptionDelivery = document.createElement("DIV");
                subscriptionDelivery.classList.add("subscription__element");
                subscriptionDelivery.classList.add("subscription__element--delivery");

                const subscriptionDeliveryTitle = document.createElement("SPAN");
                subscriptionDeliveryTitle.classList.add("subscription__span--title");
                subscriptionDeliveryTitle.innerHTML = "Delivery: " + value.subscriptions.length + "/" + value.subscriptions.length;

                const subscriptionDeliveryDescription = document.createElement("DIV");
                subscriptionDeliveryDescription.classList.add("subscription__span--description");

                const subscriptionDescriptionDeliverySpan1 = document.createElement("SPAN");
                subscriptionDescriptionDeliverySpan1.classList.add("subscription__span");
                subscriptionDescriptionDeliverySpan1.innerHTML = "Expected delivery: ";

                const subscriptionDescriptionDeliverySpan2 = document.createElement("SPAN");
                subscriptionDescriptionDeliverySpan2.classList.add("subscription__span");
                subscriptionDescriptionDeliverySpan2.innerHTML = "State: " + value.state;

                subscriptionDeliveryDescription.appendChild(subscriptionDescriptionDeliverySpan1);
                subscriptionDeliveryDescription.appendChild(subscriptionDescriptionDeliverySpan2);

                subscriptionTitleButton.appendChild(subscriptionTitleIcon);
                subscriptionDelivery.appendChild(subscriptionDeliveryTitle);
                subscriptionDelivery.appendChild(subscriptionDeliveryDescription);
            
                subscriptionTitle.appendChild(subscriptionaTitleSpan);
                subscriptionTitle.appendChild(subscriptionTitleButton);
                subscriptionOrderNumber.appendChild(subscriptionOrderNumberSpan);
                subscriptionHidden.appendChild(subscriptionDelivery);

                subscription.appendChild(subscriptionTitle);
                subscription.appendChild(subscriptionOrderNumber);
                subscription.appendChild(subscriptionHidden);

                orders.appendChild(subscription);


                for (var i in value.subscriptions){
                    var a = value.subscriptions[i];
                    if(a.id!=null){
                        const subscriptionProduct = document.createElement("DIV");
                        subscriptionProduct.classList.add("subscription__element");
                        subscriptionProduct.classList.add("subscription__element--product2");

                        const subscriptionImage = document.createElement("DIV");
                        subscriptionImage.classList.add("subscription__image");

                        const image = document.createElement("IMG");
                        var baseStr64=a.product.img;
                        if(baseStr64==""){
                            image.setAttribute('src', "./images/noimage.png");
                        }
                        else image.setAttribute('src', "data:image/jpg;base64," + baseStr64);

                        const subscription2 = document.createElement("DIV");
                        subscription2.classList.add("subscription__element");
                        subscription2.classList.add("subscription__element--delivery");

                        const subscriptionDescription = document.createElement("DIV");
                        subscriptionDescription.classList.add("subscription__description");

                        const descriptionTitle = document.createElement("DIV");
                        descriptionTitle.classList.add("subscription__description-titleName");

                        const descriptionTitleBold = document.createElement("SPAN");
                        descriptionTitleBold.classList.add("subscription__description-titleBold");
                        var valueName = "";
                        if (a.product.name.split(":")[0] != null) valueName = a.product.name.split(":")[0];
                        descriptionTitleBold.innerHTML = valueName;

                        const descriptionTitleThin = document.createElement("SPAN");
                        descriptionTitleThin.classList.add("subscription__description-titleThin");
                        valueName = "";
                        if (a.product.name.split(":")[1] != null) valueName = a.product.name.split(":")[1];
                        descriptionTitleThin.innerHTML = valueName;

                        const descriptionTitlePrice = document.createElement("DIV");
                        descriptionTitlePrice.classList.add("subscription__description-titlePrice");

                        const descriptionTitlePriceBold = document.createElement("SPAN");
                        descriptionTitlePriceBold.classList.add("subscription__description-titlePriceBold");
                        descriptionTitlePriceBold.innerHTML = a.price+"€";

                        const descriptionTitlePriceThin = document.createElement("SPAN");
                        descriptionTitlePriceThin.classList.add("subscription__description-titlePriceThin");
                        descriptionTitlePriceThin.innerHTML = a.product.iva;

                        const descriptionTitleAmount = document.createElement("DIV");
                        descriptionTitleAmount.classList.add("subscription__description-titleAmount");

                        const descriptionTitleAmountBold = document.createElement("SPAN");
                        descriptionTitleAmountBold.classList.add("subscription__description-titleAmountBold");
                        descriptionTitleAmountBold.innerHTML = "Amount: ";

                        const descriptionTitleAmountThin = document.createElement("SPAN");
                        descriptionTitleAmountThin.classList.add("subscription__description-titleAmountThin");
                        descriptionTitleAmountThin.innerHTML = a.amount;

                        const subscriptionChange = document.createElement("DIV");
                        subscriptionChange.classList.add("subscription__change");

                        const subscriptionInput = document.createElement("INPUT");
                        subscriptionInput.setAttribute("type", "number");
                        subscriptionInput.setAttribute("id", a.id);
                        subscriptionInput.setAttribute("min", "0");
                        subscriptionInput.setAttribute("value", a.amount);
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
                        descriptionTitleAmount.appendChild(descriptionTitleAmountBold);
                        descriptionTitleAmount.appendChild(descriptionTitleAmountThin);
                        subscriptionButton.appendChild(buttonText);
                        
                        subscriptionImage.appendChild(image);
                        subscriptionDescription.appendChild(descriptionTitle);
                        subscriptionDescription.appendChild(descriptionTitlePrice);
                        subscriptionDescription.appendChild(descriptionTitleAmount);
                        subscriptionChange.appendChild(subscriptionInput);
                        subscriptionChange.appendChild(subscriptionButton);                  

                        subscriptionProduct.appendChild(subscriptionImage);
                        subscriptionProduct.appendChild(subscriptionDescription);
                        subscriptionProduct.appendChild(subscriptionChange);

                        subscriptionHidden.appendChild(subscriptionProduct);
                    }
                }
            }        	
        },
        error: function() { alert('Failed!'); },
    });
}

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