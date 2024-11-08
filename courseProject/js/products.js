window.addEventListener('load',getStock());

function getStock() {
    $.ajax({
        headers: {'Authorization': localStorage.getItem('token')},
        url: "http://localhost:8080/stock/categories",
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            let output = "";
            
			for (var key in data) {
                var value = data[key];
                output+=`<option>${value}</option>`;
             }
             document.querySelector(".food__category--select").innerHTML = output;
        },
        error: function() { alert('Failed!'); },
    });

    $.ajax({
        headers: {'Authorization': localStorage.getItem('token')},
        url: "http://localhost:8080/stock",
        type: 'GET',
        dataType: 'json',
        success: function (data) {
			for (var key in data) {
                var value = data[key];
                const gridContainer = document.querySelector(".grid-container");

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
             }        	
        },
        error: function() { alert('Failed!'); },
    });
};

function playFilter(){
    var select = document.querySelector(".food__category--select");
    var selectedIndex = select.options[select.selectedIndex].text;
    var otherProducts = document.querySelectorAll(".otherproducts__flex--product");
    otherProducts.forEach(e => e.remove());
    
    if(selectedIndex!="All"){
        $.ajax({
            headers: {'Authorization': localStorage.getItem('token')},
            url: "http://localhost:8080/stock/categories/"+selectedIndex,
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                for (var key in data) {
                    var value = data[key];
                    const gridContainer = document.querySelector(".grid-container");
    
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
                 }
            },
            error: function() { alert('Failed!'); },
        });
    }
    else getStock();
}