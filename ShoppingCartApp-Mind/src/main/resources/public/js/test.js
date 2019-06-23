console.log("in test Javascript file");

window.onload = getCartProducts();

function checkCart(price){
	let emptyElement = document.querySelector(".emptyCart");
	let productsElement = document.querySelector(".products");
	if(price == 0){
		productsElement.style.display = "none";
		emptyElement.style.display = "block";
	}
	
	else{
		productsElement.style.display = "block";
		emptyElement.style.display = "none";
	}
}

function clearCart(){
	
	let emptyElement = document.querySelector(".emptyCart");
	let productsElement = document.querySelector(".products");
	
	productsElement.style.display = "none";
	emptyElement.style.display = "block";
	
	console.log("All products from cart has been deleted");
	
	var xhttp = new XMLHttpRequest();
	xhttp.open("","/api/cart/removeall",true);
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
	
	xhttp.open("PUT","",true);
	xhttp.setRequestHeader("content-type","application/JSON");
	
	console.log("Cart Updated");
	document.location.reload(true);
	xhttp.send(JSON.stringify(quantObj));
	
	
}

function deleteProductFromCart(e){
	
	let totprice = document.getElementById("totalPrice").value;
	
	if(totprice.includes("Rs.")){
		totprice = parseInt(totprice.split(" ")[1],10);
	}
	else{
		totprice = parseInt(totprice,10);
	}
	
	let elem = document.getElementById(e.target.id)
	let price = parseInt(elem.getAttribute("price"),10);
	let quantity = parseInt(elem.getAttribute("quantity"),10);
	console.log(totprice+"   "+price+"  "+quantity);
	totprice = totprice - (price*quantity); 
	
	document.getElementById("totalPrice").value = "Rs. "+totprice+"/-";
	
	checkCart(totprice);
	
	var productId = e.target.id.split("_")[1];
	console.log("Product "+productId+" deleted from cartProductsContainer");
	var element = document.getElementById(productId);
    element.style.display = "none";
	
	var xhttp = new XMLHttpRequest();
	xhttp.open("DELETE","",true);
	xhttp.setRequestHeader("content-type","application/plain-text");
	xhttp.send(productId);
}
function getCartProducts(){
	
	console.log("Cart Products");
	let totalAmount = 0;
	let xhttp = new XMLHttpRequest();
	const res = {
				"productIncart":[
					{
						"productId":1,
						"prodName":"C for Programming",
						"price":500,
						"quantity":18
					},
					{
						"productId":3,
						"prodName":"Harry Potter",
						"price":600,
						"quantity":13
					},
					{
						"productId":2,
						"prodName":"Pant",
						"price":1000,
						"quantity":10
					},
					{
						"productId":4,
						"prodName":"T-Shirt",
						"price":400,
						"quantity":53
					}
				],
				"totalAmount":240747
			};
	
		var toAdd = document.createDocumentFragment()
		var products = Object.values(res.productIncart);
		console.log(products);
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
				//quantNumber.addEventListener("focus",updateTotal);
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