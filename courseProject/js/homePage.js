window.addEventListener('load',getHomePageProducts(),getNewProducts());

function getHomePageProducts() {
    var counter=0;
            $.ajax({
                headers: {'Authorization': localStorage.getItem('token')},
                url: "http://localhost:8080/stock/",
                type: 'GET',
                dataType: 'json',
                success: function (data) {
                    for (var key in shuffle(data)) {
                        var value = data[key];
                        if(counter<8){

                        const gridContainer = document.querySelector(".weekly-offers__list");
                        const newProductsContainer = document.querySelector(".new-products__list");
        
                        const otherProducts = document.createElement("A");
                        otherProducts.setAttribute("href", "product.html?p="+value.id+"");
                        otherProducts.classList.add("otherproducts__flex--product");
        
                        const image = document.createElement("IMG");
                        var baseStr64=value.img;
                        if(baseStr64==""){
                            image.setAttribute('src', "./images/noimage.png");
                        }
                        else image.setAttribute('src', "data:image/jpg;base64," + baseStr64);
                        
        
                        const productDescription = document.createElement("DIV");
                        productDescription.classList.add("otherproducts__flex--productDescription");
        
                        const div = document.createElement("DIV");
                        const price = document.createElement("SPAN");
                        price.classList.add("productDescription__title--price");
                        price.innerHTML = value.price+"€";
        
                        const titleBold = document.createElement("SPAN");
                        titleBold.classList.add("productDescription__title--bold");
                        titleBold.innerHTML = value.name.split(":")[0];
                        const titleThin = document.createElement("SPAN");
                        titleThin.classList.add("productDescription__title--thin");
                        var valueName = "";
                        if(value.name.split(":")[1]!=null)valueName=value.name.split(":")[1];
                        titleThin.innerHTML = valueName;
        
                        div.appendChild(titleBold);
                        div.appendChild(titleThin);
                        productDescription.appendChild(div);
                        productDescription.appendChild(price);
                        otherProducts.appendChild(image);
                        otherProducts.appendChild(productDescription);
                        gridContainer.appendChild(otherProducts);
                        counter++;}
                        else{
                            return;
                        }
                     }
                },
                error: function() { alert('Failed!'); },
            });
}

function getNewProducts() {
    var counter=0;
            $.ajax({
                headers: {'Authorization': localStorage.getItem('token')},
                url: "http://localhost:8080/stock/",
                type: 'GET',
                dataType: 'json',
                success: function (data) {
                    for (var key in shuffle(data)) {
                        var value = data[key];
                        if(counter<4){

                        const newProductsContainer = document.querySelector(".new-products__list");
        
                        const otherProducts = document.createElement("A");
                        otherProducts.setAttribute("href", "product.html?p="+value.id+"");
                        otherProducts.classList.add("otherproducts__flex--product");
        
                        const image = document.createElement("IMG");
                        var baseStr64=value.img;
                        if(baseStr64==""){
                            image.setAttribute('src', "./images/noimage.png");
                        }
                        else image.setAttribute('src', "data:image/jpg;base64," + baseStr64);
                        
        
                        const productDescription = document.createElement("DIV");
                        productDescription.classList.add("otherproducts__flex--productDescription");
        
                        const div = document.createElement("DIV");
                        const price = document.createElement("SPAN");
                        price.classList.add("productDescription__title--price");
                        price.innerHTML = value.price+"€";
        
                        const titleBold = document.createElement("SPAN");
                        titleBold.classList.add("productDescription__title--bold");
                        titleBold.innerHTML = value.name.split(":")[0];
                        const titleThin = document.createElement("SPAN");
                        titleThin.classList.add("productDescription__title--thin");
                        var valueName = "";
                        if(value.name.split(":")[1]!=null)valueName=value.name.split(":")[1];
                        titleThin.innerHTML = valueName;
        
                        div.appendChild(titleBold);
                        div.appendChild(titleThin);
                        productDescription.appendChild(div);
                        productDescription.appendChild(price);
                        otherProducts.appendChild(image);
                        otherProducts.appendChild(productDescription);
                        newProductsContainer.appendChild(otherProducts);
                        counter++;}
                        else{
                            return;
                        }
                     }
                },
                error: function() { alert('Failed!'); },
            });
}

function shuffle(arra1) {
    var ctr = arra1.length, temp, index;
    while (ctr > 0) {
        index = Math.floor(Math.random() * ctr);
        ctr--;
        temp = arra1[ctr];
        arra1[ctr] = arra1[index];
        arra1[index] = temp;
    }
    return arra1;
}