<!DOCTYPE html>
<html>
<head>
<title>Registration Form</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
</head>
<body>
	<div class="container">
		<legend>Sign Up</legend>
		<div class="form-actions">
			<form method="post" action="./register" autocomplete="on"
				class="form-horizontal"
				onsubmit="return validateFormOnSubmit(this);">
				<input type="hidden" name="success_url"
					value=<%=request.getAttribute("message")%> /> <input type="hidden"
					name="error_url" value="" />
				<div class="control-group">
					<label class="control-label">First Name</label>
					<div class="controls">
						<div class="input-prepend">
							<span class="add-on"><i class="icon-user" rel="tooltip"
								title="Enter your First Name"></i></span> <input type="text"
								class="input-xlarge" id="fname" name="fname"
								placeholder="First Name" autofocus="autofocus" required>
						</div>
					</div>
				</div>

				<div class="control-group ">
					<label class="control-label">Last Name</label>
					<div class="controls">
						<div class="input-prepend">
							<span class="add-on"><i class="icon-user" rel="tooltip"
								title="Enter your Last Name"></i></span> <input type="text"
								class="input-xlarge" id="lname" name="lname"
								placeholder="Last Name" required>
						</div>
					</div>
				</div>

				<div class="control-group">
					<label class="control-label">Email</label>
					<div class="controls">
						<div class="input-prepend">
							<span class="add-on"><i class="icon-envelope"
								rel="tooltip" title="Enter your Email"></i></span> <input type="email"
								class="input-xlarge" id="email" name="email" placeholder="Email"
								required>
						</div>
					</div>
				</div>



				<div class="control-group">
					<label class="control-label">Password</label>
					<div class="controls">
						<div class="input-prepend">
							<span class="add-on"><i class="icon-lock" rel="tooltip"
								title="Enter your passoword"></i></span> <input type="Password"
								id="password" class="input-xlarge" name="password"
								placeholder="Password" required>
						</div>
						<span id='passwordMessage'> </span>
					</div>
				</div>

				<div class="control-group">
					<label class="control-label">Password</label>
					<div class="controls">
						<div class="input-prepend">
							<span class="add-on"><i class="icon-lock" rel="tooltip"
								title="Repeat your password"></i></span> <input type="Password"
								id="repassword" class="input-xlarge" name="repassword"
								placeholder="Re-enter Password" required disabled="true">
						</div>
						<span id='message'> </span>

					</div>
				</div>

				<div class="control-group">
					<div class="controls">
						<div class="checkbox">
							<label> <input type="checkbox" name="is_subscribed"
								value="1"> Sign Up for Newsletter
							</label>
						</div>
					</div>
				</div>

				<div class="control-group">
					<label class="control-label"></label>
					<div class="controls">
						<button type="submit" class="btn btn-success" id="submit"
							disabled="true">Create My Account</button>
						<button type="reset" class="btn" id="clear">Clear</button>
					</div>
				</div>
			</form>
		</div>

		<script src="http://code.jquery.com/jquery.js"></script>
		<script src="js/bootstrap.min.js"></script>
		<script>
    	$(document).ready(function(){
	  $('#repassword').on('keyup', function () {
	    if (($(this).val().length > 5) && ($(this).val() == $('#password').val())){
	        $('#message').html('matching').css('color', 'green');
	        $('#submit').prop('disabled', false);
	    } else {
	    	$('#message').html('not matching').css('color', 'red');
	    	$('#submit').prop('disabled', true);
	    }
		});

		$('#password').on('keyup', function() {
			if($(this).val().length < 6){
				$('#passwordMessage').html('Password should be 6 character long').css('color', 'red');
				$('#repassword').prop("disabled", true);
			}
			else{
				$('#passwordMessage').text('');
				$('#repassword').prop("disabled", false);
			}
			
		});	  
	  $('#clear').click(function(){
        	$('#message').text('');
        	$('#submit').prop("disabled", true);
		});
	});
    </script>
		<script type="text/javascript">
    	function validateFormOnSubmit(theForm){
    		if(validateName(theForm.fname)){
    			if(validateName(theForm.lname)){
    				if(validateEmail(theForm.email)){
    					if(validatePassword(theForm.password))
    						return true;
    				}
    			}
    		}
    		return false;
    	}

    	function validateName(name){
    		var alp = /^[a-zA-Z]+$/;
	        if (name.value.match(alp) && name.value.length >= 3) {
               return true;
           }
           else {
           		name.value = "";
            	name.setAttribute('placeholder',"Please enter your name to continue");
             	name.style.background = 'yellow';
 	            name.focus();
               return false;
           }
    	}

    	function validateEmail(email){
    	   var atpos = x.indexOf("@");
           var dotpos = x.lastIndexOf(".");
           if (atpos < 1 || dotpos < atpos + 2 || dotpos + 2 >= x.length) {
  				email.style.background = 'yellow';
  				email.value = "Enter valid email id";
                 return false;
             }

           else {
               return true;
           }
    	}

    	function validatePassword(password){

    		if(password.value.length < 6){
    			password.value = "";
    			theForm.repassword.value = "";
    			return false;
    		}
    		return true;
    	}
    </script>
	</div>
</body>
</html>