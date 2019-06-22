//This Function matches password both the passwords on signup.

function matchPassword(){
	let pwd = document.querySelector("#sign_pwd").value;
	let cpwd = document.querySelector("#cpwd").value;
	
	if(pwd != cpwd){
		alert("Password does not match");
		return false;
	}
	
	return true;
}


// This function hits the UserSignup in the backend.

function saveUser(){
	
	let uname = document.getElementById("sign_uname").value;
	let pwd = document.getElementById("sign_pwd").value;
	
	if(! matchPassword()){
		return false;
	}
	
	let user = {
		"userName":uname,
		"password":pwd
	};
	
	let http = new XMLHttpRequest();
	
	http.open("POST","api/user/signup",true);
	http.onreadystatechange = function(){
		
		if(this.readyState == 4 && this.status == 200){
			location.replace('products.html');
		}
		
	};
	http.setRequestHeader("content-type","application/JSON");
	console.log(JSON.stringify(user));
	http.send(JSON.stringify(user));
	
}

// This function hits the UserLogin in the backend.
function login(){
	let uname = document.getElementById("log_uname").value;
	let pwd = document.getElementById("log_pwd").value;
	
	let user = {
		"userName":uname,
		"password":pwd
	};
	
	let http = new XMLHttpRequest();
	
	http.open("POST","/api/user/login",true);
	http.onreadystatechange = function(){
		
		if(this.readyState == 4 && this.status == 200){
			location.replace('products.html');
		}
		
	};
	
	http.setRequestHeader("content-type","application/JSON");
	http.send(JSON.stringify(user));
}

