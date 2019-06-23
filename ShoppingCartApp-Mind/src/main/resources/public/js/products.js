console.log('In products javascript file');

window.onload = getAllProducts();

function logout(){
	var xhttp = new XMLHttpRequest();
	xhttp.open("POST","/api/user/logout",true);
	xhttp.send();
	
	window.location = "index.html";
}

function changePlaceholder(){
	var filter = document.getElementById("filters").value;
	var searchbar = document.getElementById("filterText");
	var placeholder="";
	if(filter === "Filter By ID"){
		placeholder="Please enter Product ID";
		searchbar.placeholder = placeholder;
		searchbar.disabled = false;
	}
	else if(filter === "Filter By Name"){
		placeholder="Please enter Product Name";
		searchbar.placeholder = placeholder;
		searchbar.disabled = false;
	}
	else if(filter === "Filter By Category"){
		placeholder="Please enter Product Category";
		searchbar.placeholder = placeholder;
		searchbar.disabled = false;
	}
}

function searchProducts(){
	
	var filter = document.getElementById("filters").value;
	var searchFilter = document.getElementById("filterText").value;
	if(searchFilter === ""){
		alert("Filter Value cannot be blank");
		return false;
	}
	
	if(filter === "Filter By ID"){
		var productId = parseInt(document.getElementById("filterText").value);
		
		if(productId > 0){
			var xhttp = new XMLHttpRequest();
			xhttp.open("GET","api/product/id/"+productId,false);
			var readystate1 = xhttp.readyState;
			
			xhttp.send();
			var readystate2 = xhttp.readyState;
			
			if(readystate1!= readystate2){
				
			var data = JSON.parse(xhttp.response);
			
			var product_container = document.getElementById("productByIdConatiner");
			var toAdd = document.createDocumentFragment();
			var newDiv = document.createElement('div');
			var btn = document.createElement('button');
			var name = document.createElement('span');
			var price = document.createElement('span');
			var br = document.createElement('br');
			newDiv.id = data.productId;
			newDiv.className = "productItem";
			
			name.className = 'productName';
			name.textContent = "Product Name: "+data.prodName;
			newDiv.appendChild(name);
			newDiv.appendChild(br);
			
			price.className = 'productPrice';
			price.textContent = "Product Price: "+data.price;
			newDiv.appendChild(price);
			
			btn.id = data.productId;
			btn.className = "addtocart";
			btn.textContent = "Add to Cart";
			newDiv.appendChild(btn);

			toAdd.appendChild(newDiv);
			
			
			product_container.appendChild(toAdd);
			document.getElementById("productContainer").style.display="none";
			document.getElementById("productByIdConatiner").style.display="block";
			
			var btns = document.querySelectorAll("button.addToCart");

			for(var i=0; i<btns.length; i++){
				btns[i].addEventListener('click', addProductToCart);
			}
		}
		}
		
		else{
			alert("Product ID must be a non-zero positive integer");
		}
		
		
	}
	else if(filter === "Filter By Name"){
		var productName = document.getElementById("filterText").value;
			var xhttp = new XMLHttpRequest();
			xhttp.open("GET","api/product/product-name/"+productName,false);
			
			var readystate1 = xhttp.readyState;
			xhttp.send();
			var readystate2 = xhttp.readystate;
		if(readystate1 != readystate2){
		 var data = JSON.parse(xhttp.response);

			var product_container = document.getElementById("productByNameContainer");
			var toAdd = document.createDocumentFragment();			
			var newDiv = document.createElement('div');
			var btn = document.createElement('button');
			var name = document.createElement('span');
			var price = document.createElement('span');
			var br = document.createElement('br');
			newDiv.id = data.productId;
			newDiv.className = "productItem";
			
			name.className = 'productName';
			name.textContent = "Product Name: "+data.prodName;
			newDiv.appendChild(name);
			newDiv.appendChild(br);
			
			price.className = 'productPrice';
			price.textContent = "Product Price: "+data.price;
			newDiv.appendChild(price);
			
			btn.id = data.productId;
			btn.className = "addtocart";
			btn.textContent = "Add to Cart";
			newDiv.appendChild(btn);

			toAdd.appendChild(newDiv);
			
			
			product_container.appendChild(toAdd);
			document.getElementById("productContainer").style.display="none";
			document.getElementById("productByNameContainer").style.display="block";
			
			var btns = document.querySelectorAll("button.addToCart");

			for(var i=0; i<btns.length; i++){
				btns[i].addEventListener('click', addProductToCart);
			}
		
	}
	}
	else if(filter === "Filter By Category"){
		var productCat = document.getElementById("filterText").value;
		if(productCat === "Books" || productCat === "Apparals"){
			var xhttp = new XMLHttpRequest();
			xhttp.open("GET","api/product/category/"+productCat,false);
			var readystate1 = xhttp.readyState;
			xhttp.send();
			var readystate2 = xhttp.readyState;
						
			if(readystate1!= readystate2){
				
			var res =  JSON.parse(xhttp.response);
			
			res.forEach(function(data){
				
				var product_container = document.getElementById("productByCategoryContainer");
				var toAdd = document.createDocumentFragment();			
				var newDiv = document.createElement('div');
				var btn = document.createElement('button');
				var name = document.createElement('span');
				var price = document.createElement('span');
				var br = document.createElement('br');
				newDiv.id = data.productId;
				newDiv.className = "productItem";
			
				name.className = 'productName';
				name.textContent = "Product Name: "+data.prodName;
				newDiv.appendChild(name);
				newDiv.appendChild(br);
			
				price.className = 'productPrice';
				price.textContent = "Product Price: "+data.price;
				newDiv.appendChild(price);
			
				btn.id = data.productId;
				btn.className = "addtocart";
				btn.textContent = "Add to Cart";
				newDiv.appendChild(btn);

				toAdd.appendChild(newDiv);
			
			
				product_container.appendChild(toAdd);
				console.log(product_container);
				document.getElementById("productContainer").style.display="none";
				document.getElementById("productByCategoryContainer").style.display="block";
			
				var btns = document.querySelectorAll("button.addToCart");

				for(var i=0; i<btns.length; i++){	
					btns[i].addEventListener('click', addProductToCart);
				}
			
			});
			}

		}
		else{
			alert("Product Category must be Books or Apparals");
		}
	}
	
}

function addProductToCart(e){
	var xhttp = new XMLHttpRequest();	
	xhttp.open('POST',"/api/cart/add",false);
	xhttp.setRequestHeader('content-type',"application/plain-text");
	
	xhttp.send(e.target.id);
	
}

function getAllProducts(){
		
	var xhttp = new XMLHttpRequest();
	xhttp.open("GET","/api/product/all-products",true);

	xhttp.send();
	
	xhttp.onreadystatechange = function(){
	if(this.readyState == 4 && this.status == 302){
		 var res = JSON.parse(xhttp.response);
		var product_container = document.getElementById("productContainer");
		var toAdd = document.createDocumentFragment();
		
		res.forEach(function(element){
			element.forEach(function(data){
				var newDiv = document.createElement('div');
				var btn = document.createElement('button');
				var name = document.createElement('span');
				var price = document.createElement('span');
				var br = document.createElement('br');
				newDiv.id = data.productId;
				newDiv.className = "productItem";
				
				name.className = 'productName';
				name.textContent = "Product Name: "+data.prodName;
				newDiv.appendChild(name);
				newDiv.appendChild(br);
				
				price.className = 'productPrice';
				price.textContent = "Product Price: "+data.price;
				newDiv.appendChild(price);
				
				btn.id = data.productId;
				btn.className = "addtocart";
				btn.textContent = "Add to Cart";
				newDiv.appendChild(btn);

				console.log(newDiv);
				toAdd.appendChild(newDiv);
			});
		});
		
		document.getElementById('productContainer').appendChild(toAdd);
		
		var btns = document.querySelectorAll("button.addToCart");

		for(var i=0; i<btns.length; i++){
			btns[i].addEventListener('click', addProductToCart);
		}

		document.getElementById("productByIdConatiner").style.display="none";
		document.getElementById("productByNameContainer").style.display="none";
		document.getElementById("productByCategoryContainer").style.display="none";
	}
	
	else if(this.status == 401){
		
		document.querySelector("#main_body").style.display = "none";
		document.querySelector("#unauth").style.display = "block";
	}
	}
}
