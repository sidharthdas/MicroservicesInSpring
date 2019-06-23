console.log("in Cart Javascript file");

window.onload = getCartProducts();

function checkCart(price){
	var emptyElement = document.querySelector(".emptyCart");
	var productsElement = document.querySelector(".products");
	if(price == 0){
		productsElement.style.display = "none";
		emptyElement.style.display = "block";
	}
	
	else{
		productsElement.style.display = "block";
		emptyElement.style.display = "none";
	}
}

function logout(){
	var xhttp = new XMLHttpRequest();
	xhttp.open("POST","/api/user/logout",true);
	xhttp.send();
	
	window.location = "index.html";
}

function clearCart(){
	
	let emptyElement = document.querySelector(".emptyCart");
	let productsElement = document.querySelector(".products");
	
	productsElement.style.display = "none";
	emptyElement.style.display = "block";
	
	var xhttp = new XMLHttpRequest();
	xhttp.open("DELETE","/api/cart/remove",true);
	xhttp.send();
}

function updateProductToCart(e){
	const id = e.target.id.split("_")[1];
	const quant = parseInt(document.getElementById("quantNumber_"+id).value,10);
	if(quant<0){
		alert("Product Quantity can not be less than 0");
		return false;
	}
	let quantObj = {
		"productId":id,
		"quantity":quant
	};
	
	let xhttp = new XMLHttpRequest();
	
	xhttp.open("PUT","/api/cart/mycart",true);
	xhttp.setRequestHeader("content-type","application/JSON");
	
	xhttp.onreadystatechange = function(){
		document.location.reload(true);
		console.log("Cart Updated");
	};
	
	xhttp.send(JSON.stringify(quantObj));
	
	
	
}

function deleteProductFromCart(e){
	
	var totprice = document.getElementById("totalPrice").value;
	
	if(totprice.includes("Rs.")){
		totprice = parseInt(totprice.split(" ")[1],10);
	}
	else{
		totprice = parseInt(totprice,10);
	}
	
	var elem = document.getElementById(e.target.id)
	var price = parseInt(elem.getAttribute("price"),10);
	var quantity = parseInt(elem.getAttribute("quantity"),10);
	totprice = totprice - (price*quantity); 
	
	document.getElementById("totalPrice").value = "Rs. "+totprice+"/-";
	
	checkCart(totprice);
	
	var productId = e.target.id.split("_")[1];
	console.log("Product "+productId+" deleted from cartProductsContainer");
	var element = document.getElementById(productId);
    element.style.display = "none";
	
	var xhttp = new XMLHttpRequest();
	xhttp.open("DELETE","/api/cart/remove/"+productId,true);
	xhttp.setRequestHeader("content-type","application/plain-text");
	xhttp.send(productId);
}


function getCartProducts(){
	document.querySelector("#unauth").style.display = "none";
	console.log("Cart Products");
	var totalAmount = 0;
	var xhttp = new XMLHttpRequest();
	xhttp.open("GET","/api/cart/mycart",true);
	
	xhttp.send();
	
	xhttp.onreadystatechange = function(){
		console.log(this.readyState+"    "+this.status);
		if(this.readyState ==4 && this.status == 302){
		
			var res = JSON.parse(xhttp.response);
		
			var toAdd = document.createDocumentFragment()
			var products = res.productInCart
			for(var i=0; i<products.length; i++){
				
					var newDiv = document.createElement('div');
					var btn = document.createElement('button');
					var delbtn = document.createElement('button');
					var name = document.createElement('span');
					var price = document.createElement('span');
					var br = document.createElement('br');
					var quanti = document.createElement('span');
					var quantNumber = document.createElement('input');
					newDiv.id = products[i].productId;
					newDiv.className = "productItem";
					
					name.className = 'productName';
					name.textContent = "Product Name: "+products[i].prodName;
					newDiv.appendChild(name);
					newDiv.appendChild(br);
					
					price.className = 'productPrice';
					price.textContent = "Product Price: "+products[i].price;
					newDiv.appendChild(price);
					
					console.log(products[i].prodName+"    "+products[i].quantity);
					
					delbtn.id = "del_"+products[i].productId;
					delbtn.setAttribute("quantity",products[i].quantity);
					delbtn.setAttribute("price",products[i].price);
					delbtn.className = "delete";
					delbtn.textContent = "Delete";
					delbtn.addEventListener('click',deleteProductFromCart);
					newDiv.appendChild(delbtn);
					
					btn.id = "update_"+products[i].productId;
					btn.className = "update";
					btn.textContent = "update";
					btn.addEventListener('click',updateProductToCart);				
					newDiv.appendChild(btn);
					
					quanti.id = "quant_"+products[i].productId;
					quanti.className = "quantity";
					quanti.textContent = "Quantity: ";
					
					quantNumber.id = "quantNumber_"+products[i].productId;
					quantNumber.className = "quantNumber";
					quantNumber.value = products[i].quantity;
					quanti.appendChild(quantNumber);
					
					newDiv.appendChild(quanti);
	
					
					console.log(newDiv);
					toAdd.appendChild(newDiv);
					
					totalAmount += products[i].price * products[i].quantity;
				
				};
				
				document.getElementById("cartProductsContainer").appendChild(toAdd);
				document.getElementById("totalPrice").value = "Rs. "+totalAmount+"/-";
			
				checkCart(totalAmount);
			}
			
			else if(this.status == 401){
				document.querySelector("#main_body").style.display = "none";
				document.querySelector("#unauth").style.display = "block";
			}
		}
	}
