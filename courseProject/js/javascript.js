window.addEventListener('mouseup', function(event){
    let box = document.getElementById('header__drop-down-user');
    let box3 = document.querySelector('.settings__shipping-address-configuration');
    let dark = document.querySelector(".darkBackground");
    if (event.target != box && event.target.parentNode != box){
        box.style.width='0%'
        box.style.visibility = 'hidden';
    }
    if (event.target != box3 && event.target.parentNode != box3 && box3!=null){
        box3.style.position = 'absolute';
        box3.style.top='-680px'
    }
    if (event.target != box && event.target.parentNode != box){
        dark.style.width='0%'
        dark.style.visibility = 'hidden';
    }
});

function clickShippingAddress(){
    let element = document.querySelector('.settings__shipping-address-configuration');
    let dark = document.querySelector(".darkBackground");
    element.style.top = '20px';
    element.style.position = 'fixed';
    dark.style.width='100vw'
    dark.style.visibility = 'visible';
}

function clickAccount(){
    var element = document.getElementById('header__drop-down-user');
    var dark = document.querySelector(".darkBackground");
    let body = document.body;
    element.style.visibility = 'visible';
    element.style.width = '320px';
    dark.style.width='100vw'
    dark.style.visibility = 'visible';
}

function clickQuestion(number){
	let name = ".frequent-questions__li--"+number;
	let header = document.querySelector(name+" .frequent-questions__header-question");
	let element = document.querySelector(name+' .frequent-questions__answer-question');
	let icon = document.querySelector(name+' .frequent-questions__header-question i');
	if(icon.getAttribute("class") == 'fas fa-chevron-down'){
		header.style.borderBottom = '1.5px solid #E1EBEB'
		element.style.height = 'auto';
		element.style.top = '0';
		element.style.zIndex = '2';
		element.style.padding = '6px';
		icon.setAttribute("class", "fas fa-chevron-up");
	}
	else{
		header.style.borderBottom = 'none';
		element.style.height = '0';
		element.style.top = '-48px';
		element.style.zIndex = '-1';
		element.style.padding = '0';
		icon.setAttribute("class", "fas fa-chevron-down");
	}
}

function clickSubscription(id){

    let subscription = document.getElementById(''+id+'');
    let subscriptionHidden = subscription.querySelector(".subscription__hidden");
    let orderNumber = subscription.querySelector(".subscription__element--order-number");
    let subscriptionButton = subscription.querySelector(".subscription__element-button i");
    if(subscriptionButton.getAttribute("class") == "fas fa-chevron-down"){
        subscriptionHidden.style.display = "block";
        subscriptionHidden.style.height = "auto";
        orderNumber.style.borderBottom = "none";
        subscriptionButton.classList.remove("fa-chevron-down");
        subscriptionButton.classList.add("fas");
        subscriptionButton.classList.add("fa-chevron-up");
    }
    else if (subscriptionButton.getAttribute("class")=="fas fa-chevron-up"){
        subscriptionHidden.style.display = "none";
        subscriptionHidden.style.height = "none";
        orderNumber.style.borderBottom = "1px solid #BDBDBD";
        subscriptionButton.classList.remove("fa-chevron-up");
        subscriptionButton.classList.add("fas");
        subscriptionButton.classList.add("fa-chevron-down");
    }

}



function rightClick(){
    let imgList = document.querySelectorAll('.featured__img');
    var active;
    var cont=0;
    for(var i=0;i<imgList.length;i++){
        if(imgList[i].getAttribute("class") == ("featured__img featured__img--active")){
            cont=i;
            active=imgList[i];
        }
    }

    if(cont==imgList.length-1){
    	active.setAttribute("class","featured__img");
    	imgList[0].setAttribute("class","featured__img featured__img--active");
    }
    else{
		active.setAttribute("class","featured__img");
    	imgList[cont+1].setAttribute("class","featured__img featured__img--active");
    }
}

function leftClick(){
    let imgList = document.querySelectorAll('.featured__img');
    var active;
    var cont=0;
    for(var i=0;i<imgList.length;i++){
        if(imgList[i].getAttribute("class") == ("featured__img featured__img--active")){
            cont=i;
            active=imgList[i];
        }
    }
    if(cont==0){
    	active.setAttribute("class","featured__img");
    	imgList[imgList.length-1].setAttribute("class","featured__img featured__img--active");
    }
    else{
		active.setAttribute("class","featured__img");
    	imgList[cont-1].setAttribute("class","featured__img featured__img--active");
    }
}