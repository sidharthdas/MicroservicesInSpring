
window.onload = getCartProducts();

function removeCart(){
	console.log("Deleting all the products from the cart");
	
	const http = new XMLHttpRequest();
	
	http.open("delete","/api/cart/mycart",true);
	
	http.onreadystatechange = function(){
		if(this.readyState == 4 && this.status == 202){
			document.querySelector(".emptyCart").style.display = "block";
			document.querySelector("#cart-product-container").style.display = "none";
		}
	};
	
	http.send();
}


function getCartProducts(){
	console.log("Getting all products in the cart");
	let totPrice = 0;
	const http = new XMLHttpRequest();
	http.open("GET","/api/cart/mycart",true);
	
	http.onreadystatechange = function(){
		if(this.readyState == 4 && this.status == 302){
			
			const response = JSON.parse(http.response);
			let products = response.productInCart;
			let totalPrice = 0;
			let cards = document.createElement('div');
			
			products.forEach(function(product){
		
				const card = document.createElement("div");
				card.className = "card";
				card.id = "card"+product.productId;
				
				
				const card_title = document.createElement("h4");
				card_title.className = "card-title";
				card_title.textContent = product.prodName;
				
				
				const card_body = document.createElement("p");
				card_body.className = "card-text";
				card_body.textContent = "Price: Rs "+product.price;
				
				const quant_body = document.createElement("span");
				quant_body.className = "quant_body";
				quant_body.textContent = "Quantity: ";
				
				const quant_no = document.createElement("input");
				quant_no.className = "quantity";
				quant_no.id = "qunt_"+product.productId;
				quant_no.value = product.quantity;
				//quant_no.addEventListener('focus',checkQuantity);
				quant_body.appendChild(quant_no);
				
				
				const deletebtn = document.createElement("button");
				deletebtn.className = "delbtn";
				deletebtn.id = product.productId;
				deletebtn.textContent = "x";
				deletebtn.addEventListener('click',deleteProductFromCart);
				
				
				const updatebtn = document.createElement("button");
				updatebtn.className = "updatebtn";
				updatebtn.id = product.productId;
				updatebtn.textContent = "update";
				updatebtn.addEventListener('click',updateCart);
				
				card.appendChild(deletebtn);
				card.appendChild(card_title);
				card.appendChild(card_body);
				card.appendChild(quant_body);
				card.appendChild(updatebtn);
				
				totPrice = totPrice + (product.quantity * product.price);
				
				document.getElementById("cart-product-container").appendChild(card);
			});
			
			document.getElementById("total_price").value = totPrice;
			
			if(totPrice == 0){
				document.getElementById("cart-product-container").style.display = "none";
				document.querySelector(".emptyCart").style.display = "block";
			}
			else{
				document.getElementById("cart-product-container").style.display = "block";
				document.querySelector(".emptyCart").style.display = "none";
			}
		}
		
	};
	http.send();
	
}

function updateCart(e){
	let quantity = document.getElementById("qunt_"+e.target.id).value;
	
	
	if(quantity < 0){
		alert("Product Qauntity can not be negative number.");
		return false;
	}
	
	if(quantity > 10){
		alert("Product Qauntity can not be greater than 10.");
		return false;
	}
	
	if(quantity == 0){
		deleteProductFromCart(e);
		//location.replace("cart.html");
		return false;
	}
	
	let product = {
		'productId':e.target.id,
		'quantity':quantity
	};
	
	let http = new XMLHttpRequest();
	http.open("PUT","api/cart/mycart",true);
	http.onreadystatechange = function(){
		if(this.readyState == 4 && this.status == 200){
			location.replace("cart.html");
		}
	}
	http.setRequestHeader("content-type",'application/JSON');
	http.send(JSON.stringify(product));
}

function deleteProductFromCart(e){
	
	let productId = e.target.id;
	console.log(productId);
	let http = new XMLHttpRequest();
	http.open("DELETE","api/cart/mycart/"+productId,true);
	
	http.onreadystatechange = function(){
		if(this.readyState == 4 && this.status == 202){
			console.log("Product has been deleted");
			
			document.getElementById("card"+e.target.id).style.display = "none";
			location.replace("cart.html");
		}
	};
	http.send();
	
	
}

function emptyCart(){
	
	let http = new XMLHttpRequest()
	http.open("DELETE","/api/cart/mycart",true);
	
	http.onreadystatechange = function(){
		if(this.readyState == 4 && this.status == 200)
		{
			console.log("Cart has been emptied");
		}
	};
	
	http.send();
	document.querySelector(".emptyCart").style.display = "block";
	
}

function checkQuantity(e){
	
	let quantity = document.getElementById(e.target.id).value;
	if(quantity > 10){
		alert("Product Quantity can not be greater than 10.");
	}
	else if(quantity < 0){
		alert("Product quantity can not be a negative number.");
	}
	else if(quantity = 0){
		deleteProductFromCart(e);
	}
}