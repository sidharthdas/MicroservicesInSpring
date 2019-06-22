window.onload = getAllProducts();

// This function attaches the addtocard function as event on all add to cart buttons in the page.
function addtocart_event(){
	let buttons = document.querySelectorAll(".addtocart");
	
	buttons.forEach(function(button){
		button.addEventListener('click', addtocart);
	});
}


// This function add the product to cart as per product ID of the product.
function addtocart(e){
	
	console.log("product "+ e.target.id+" getting added to cart");
	var http = new XMLHttpRequest();
	
	http.open('POST',"/api/cart/add",true);
	
	http.onreadystatechange = function(){
		if(this.readyState == 4 && this.status == 200){
			const response = http.response
			if(response === "added sucesssfully"){
				alert("Product has been added");
			}
		}
	}
	http.setRequestHeader('content-type',"application/plain-text");
	
	http.send(e.target.id);
}


// This function changes the placeholer of the filter Seacrh bar as per the filter option selected.
function changePlaceholder(){
	const filter = document.querySelector("#filters").value.split(" ")[2];
	const searchbar = document.getElementById("searchBox");
	let placeholder="";
	
	if(filter === "ID"){
		
		document.getElementById("productCategory").style.display = "none";
		searchbar.style.display = "inline";
		document.getElementById("filterSearch").style.display = "block";
		
		placeholder="Please enter Product ID";
		searchbar.placeholder = placeholder;
		searchbar.disabled = false;
		
		
	}
	else if(filter === "Name"){
		placeholder="Please enter Product Name";
		searchbar.placeholder = placeholder;
		searchbar.disabled = false;
		
		document.getElementById("productCategory").style.display = "none";
		searchbar.style.display = "inline";
		document.getElementById("filterSearch").style.display = "block";
	}
	else if(filter === "Category"){
		
		document.getElementById("productCategory").style.display = "block";
		searchbar.style.display = "none";
		document.getElementById("filterSearch").style.display = "none";
		
	}
	return false;
}

// This function retrieves all the products and displays as card.
function getAllProducts(){
	
	let http = new XMLHttpRequest();
	
	http.open("GET","/api/product/all-products",true);
	http.onreadystatechange = function(){
		if(this.readyState == 4 && this.status == 200){
			const response = JSON.parse(http.response);
			
			let card_columns = document.createElement('div');
	
			for(let cat=0; cat<response.length; cat++){
				
				card_columns.className = "card-columns";
				response[cat].forEach(function(product){
					console.log(product);
					
					
					let card = document.createElement('div');
					let card_body = document.createElement('div');
					let price_body = document.createElement('div');
					let card_title = document.createElement('h4');
					let view_btn = document.createElement('a');
					let add_toCart = document.createElement('button');
					
					card.id = product.productId;
					card.className = "card";
					
					card_body.className = "card-body";
					
					card_title.className = "card-title card-text";
					card_title.textContent = product.prodName;
					
					price_body.textContent = "Price: "+product.price;
					
					add_toCart.id = product.productId;
					add_toCart.className = "addtocart";
					add_toCart.textContent = "Add to Cart";
					
					card_body.appendChild(card_title);
					card_body.appendChild(price_body);
					card_body.appendChild(add_toCart);
					
					card.appendChild(card_body);
					
					console.log(card);
					card_columns.appendChild(card);
				});
			}
				
				
			document.querySelector('#product-container').appendChild(card_columns);
			
			addtocart_event();
		}
		
		else if(this.status == 401){
			alert("You are not logged in. Please return to Login page");
		}
	};
	
	http.send();
	
	/*
	const response = [
		[
			{
				"productId": 1,
				"prodName": "C for Computer Science",
				"price": 500,
				"genre": "Programming",
				"auther": "Dennis Ritchie",
				"publications": "unknown"
			},
			{
				"productId": 3,
				"prodName": "Harry Potter",
				"price": 600,
				"genre": "Novel",
				"auther": "J.K Rowling",
				"publications": "unknown"
			}
		],
		[
			{
				"productId": 2,
				"prodName": "Pant",
				"price": 1000,
				"type": "Jeans",
				"brand": "Something",
				"design": "Manish Malhotra"
			},
			{
				"productId": 4,
				"prodName": "T-Shirt",
				"price": 3999,
				"type": "Shirt",
				"brand": "Levis",
				"design": "Levis Jeans"
			}
		]
	];
	
	let card_columns = document.createElement('div');
	card_columns.className = "card-columns";
	
	for(let cat=0; cat<response.length; cat++){
		response[cat].forEach(function(product){			
			
			let card = document.createElement('div');
			let card_body = document.createElement('div');
			let card_title = document.createElement('h4');
			let view_btn = document.createElement('a');
			let add_toCart = document.createElement('button');
			
			card.id = product.productId;
			card.className = "card";
			
			card_body.className = "card-body";
			
			card_title.className = "card-title card-text";
			card_title.textContent = product.prodName;
			
			add_toCart.id = product.productId;
			add_toCart.className = "addtocart";
			add_toCart.textContent = "Add to Cart";
			
			card_body.appendChild(card_title);
			card_body.appendChild(add_toCart);
			
			card.appendChild(card_body);
			
			card_columns.appendChild(card);
		});
	}
		
		
		document.querySelector('#product-container').appendChild(card_columns);
		
		addtocart_event();
	*/
}

// This function retrieves product based on Category filter provided.
function getProductByCategory(){
	
	const filter = document.querySelector("#filters").value.split(" ")[2];
	
	const container = document.getElementById("product-container");
		
	while(container.firstChild){
		container.removeChild(container.firstChild);
	}
	
	if(filter === "Category"){
		const category = document.getElementById("productCategory").value;
		console.log()
		const http = new XMLHttpRequest();
		
		http.open("GET","/api/product/category/"+category,true);
		
		http.onreadystatechange = function(){
			
			if(this.readyState == 4 && this.status == 302){
				console.log(this.response);
				
				const response = JSON.parse(this.response);
				let card_columns = document.createElement('div');
				card_columns.className = "card-columns";
				
				response.forEach(function(product){
					console.log(product);
			
			
					let card = document.createElement('div');
					let card_body = document.createElement('div');
					let card_title = document.createElement('h4');
					let view_btn = document.createElement('a');
					let add_toCart = document.createElement('button');
					
					card.id = product.productId;
					card.className = "card";
					
					card_body.className = "card-body";
					
					card_title.className = "card-title card-text";
					card_title.textContent = product.prodName;
					
					add_toCart.id = product.productId;
					add_toCart.className = "addtocart";
					add_toCart.textContent = "Add to Cart";
					
					card_body.appendChild(card_title);
					card_body.appendChild(add_toCart);
					
					card.appendChild(card_body);
					
					console.log(card);
					card_columns.appendChild(card);
				});
				
				document.querySelector('#product-container').appendChild(card_columns);
				
		
				addtocart_event();
			}
		}
		
		http.send();
		
		/*const response = [
					{
						"productId": 1,
						"prodName": "C for Computer Science",
						"price": 500,
						"genre": "Programming",
						"auther": "Dennis Ritchie",
						"publications": "unknown"
					},
					{
						"productId": 3,
						"prodName": "Harry Potter",
						"price": 600,
						"genre": "Novel",
						"auther": "J.K Rowling",
						"publications": "unknown"
					}
			];
		
		let card_columns = document.createElement('div');
		card_columns.className = "card-columns";
		
		response.forEach(function(product){
			console.log(product);
		
		
			let card = document.createElement('div');
			let card_body = document.createElement('div');
			let card_title = document.createElement('h4');
			let view_btn = document.createElement('a');
			let add_toCart = document.createElement('button');
			
			card.id = product.productId;
			card.className = "card";
			
			card_body.className = "card-body";
			
			card_title.className = "card-title card-text";
			card_title.textContent = product.prodName;
			
			add_toCart.id = product.productId;
			add_toCart.className = "addtocart";
			add_toCart.textContent = "Add to Cart";
			
			card_body.appendChild(card_title);
			card_body.appendChild(add_toCart);
			
			card.appendChild(card_body);
			
			console.log(card);
			card_columns.appendChild(card);
		});
		
		document.querySelector('#product-container').appendChild(card_columns);
		
		
		addtocart_event();
		*/
	} // End of first IF
	
	else{
		
		let productFilter = document.getElementById("searchBox").value;
		
		if(productFilter === ""){
			alert("Filter value can't be Empty.");
			
		} //End of second IF
		
		else{
			
			if(filter === "ID"){
				productFilter = parseInt(productFilter);
				if(productFilter <= 0){
					alert("Product ID should be a Positive Number.");
				}
				else{
					
					const http = new XMLHttpRequest();
					
					http.open("GET","/api/product/id/"+productFilter,true);
					
					http.onreadystatechange = function(){
						if(this.readyState == 4 && this.status == 302){
							const product = JSON.parse(http.response);
							
							let card_columns = document.createElement('div');
							card_columns.className = "card-columns";
						
							console.log(product);
						
						
							let card = document.createElement('div');
							let card_body = document.createElement('div');
							let card_title = document.createElement('h4');
							let view_btn = document.createElement('a');
							let add_toCart = document.createElement('button');
							
							card.id = product.productId;
							card.className = "card";
							
							card_body.className = "card-body";
							
							card_title.className = "card-title card-text";
							card_title.textContent = product.prodName;
							
							add_toCart.id = product.productId;
							add_toCart.className = "addtocart";
							add_toCart.textContent = "Add to Cart";
							
							card_body.appendChild(card_title);
							card_body.appendChild(add_toCart);
							
							card.appendChild(card_body);
							
							console.log(card);
							card_columns.appendChild(card);
						
						
							document.querySelector('#product-container').appendChild(card_columns);
					
							addtocart_event();
						}
					}
					
					http.setRequestHeader("content-type","application/plain-text");
					
					http.send(productFilter);
					
					/*const response = {
							"productId": 1,
							"prodName": "C for Computer Science",
							"price": 500,
							"genre": "Programming",
							"auther": "Dennis Ritchie",
							"publications": "unknown"
						}
					
					let card_columns = document.createElement('div');
					card_columns.className = "card-columns";

					console.log(response);
					
					let card = document.createElement('div');
					let card_body = document.createElement('div');
					let card_title = document.createElement('h4');
					let view_btn = document.createElement('a');
					let add_toCart = document.createElement('button');
					
					card.id = response.productId;
					card.className = "card";
					
					card_body.className = "card-body";
					
					card_title.className = "card-title card-text";
					card_title.textContent = response.prodName;
					
					add_toCart.id = response.productId;
					add_toCart.className = "addtocart";
					add_toCart.textContent = "Add to Cart";
					
					card_body.appendChild(card_title);
					card_body.appendChild(add_toCart);
					
					card.appendChild(card_body);
					
					console.log(card);
					card_columns.appendChild(card);
					
					
					document.querySelector('#product-container').appendChild(card_columns);
					
					addtocart_event();
					*/
				}
				
			} // End of third IF
			
			else if(filter === "Name"){
				
				const http = new XMLHttpRequest();
				
				http.open("GET","/api/product/name/"+productFilter,true);
				
				http.onreadystatechange = function(){
					
					if(this.readyState == 4 && this.status == 302){
						const product = JSON.parse(http.response);
						
						let card_columns = document.createElement('div');
						card_columns.className = "card-columns";
				
						//response.forEach(function(product){
						console.log(product);
						
						
						let card = document.createElement('div');
						let card_body = document.createElement('div');
						let card_title = document.createElement('h4');
						let view_btn = document.createElement('a');
						let add_toCart = document.createElement('button');
						
						card.id = product.productId;
						card.className = "card";
						
						card_body.className = "card-body";
						
						card_title.className = "card-title card-text";
						card_title.textContent = product.prodName;
						
						add_toCart.id = product.productId;
						add_toCart.className = "addtocart";
						add_toCart.textContent = "Add to Cart";
						
						card_body.appendChild(card_title);
						card_body.appendChild(add_toCart);
						
						card.appendChild(card_body);
						
						console.log(card);
						card_columns.appendChild(card);
						
						
						document.querySelector('#product-container').appendChild(card_columns);
				
		
						addtocart_event();
					}
				};
				
				http.setRequestHeader("content-type","application/plain-text");
				
				http.send(productFilter);
				
			} // End of third ELSE
			
		} // End of second ELSE
		
	} // End of first ELSE
	
}