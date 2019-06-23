console.log("In Users Javascript file");
function save(){
	console.log('signup');
	var uname = document.getElementById('sign_uname').value;
	var pwd = document.getElementById('sign_pwd').value;	
	var cpwd = document.getElementById('cpwd').value;
	
	if(pwd != cpwd){
		alert("Password do not match");
		return false;
	}
	
	var user = {
				'userName':uname,
				'password':pwd,
				'confirmPassword':cpwd
	};
	
	var xhttp = new XMLHttpRequest();
	xhttp.open("POST","/api/user/signup",true);
	xhttp.setRequestHeader("Content-Type","application/JSON");

	xhttp.send(JSON.stringify(user));
	xhttp.onreadystatechange = function(){
		
		
		if(this.readyState ==4 && this.status == 202){
			location.replace("dashboard.html");
		}
		else if(this.status == 200){
			alert("Username already registered. please use a different Username");
			location.replace("index.html");
			return false;
		}
	}
	
}


function login(){
	
	var uname = document.getElementById('log_uname').value;
	var pwd = document.getElementById('log_pwd').value;
	
	var user = {
				'userName':uname,
				'password':pwd,
	};

	var xhttp = new XMLHttpRequest();
	
	xhttp.open("POST","/api/user/login",true);
	xhttp.setRequestHeader("Content-Type","application/JSON");
	
	xhttp.send(JSON.stringify(user));
	xhttp.onreadystatechange = function(){
		if(this.readyState ==4 && this.status == 202){ 
			location.replace("dashboard.html");
			return true;
		}
		else if(this.status == 200){
			alert("Username or Password is Incorrect");
		}
	
	}
}