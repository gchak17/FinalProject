<html>
    <head>
        <meta charset="UTF-8">
        <title>Name In Use</title>

        <link rel="stylesheet" type="text/css" href="js_lib/css/lib/control/iconselect.css" >
        <script type="text/javascript" src="js_lib/control/iconselect.js"></script>
        
        <script type="text/javascript" src="js_lib/iscroll.js"></script>
    
        
    </head>
    <body>
        
        <h1>Name in Use.</h1>
	
		<p>The Username you entered is already in use. <br> 
			Please enter proposed name and password</p>
        
        
        <form action="AccountCreationServlet" method="post">
			User Name: <input type="text" name="userName"/><br>
			Password: <input type="password" name="password"/><br>
			<div id="my-icon-select"></div>
			<input id = "avatar_id_field" type = "hidden" name = "avatar_id" value = "">
			<input type="submit" onclick = "getAvatarID()" value="Sign Up">
		</form>

        <script>
			var iconSelect = new IconSelect("my-icon-select", 
			    {'selectedIconWidth':48,
			    'selectedIconHeight':48,
			    'selectedBoxPadding':5,
			    'iconsWidth':48,
			    'iconsHeight':48,
			    'boxIconSpace':3,
			    'vectoralIconNumber':2,
			    'horizontalIconNumber':2});
			
			var icons = [];
			icons.push({'iconFilePath':'avatars/1.png', 'iconValue':'1'});
			icons.push({'iconFilePath':'avatars/2.png', 'iconValue':'2'});
			icons.push({'iconFilePath':'avatars/3.png', 'iconValue':'3'});
			icons.push({'iconFilePath':'avatars/4.png', 'iconValue':'4'});
			icons.push({'iconFilePath':'avatars/5.png', 'iconValue':'5'});
			icons.push({'iconFilePath':'avatars/6.png', 'iconValue':'6'});
			icons.push({'iconFilePath':'avatars/7.png', 'iconValue':'7'});
			icons.push({'iconFilePath':'avatars/8.png', 'iconValue':'8'});
			icons.push({'iconFilePath':'avatars/9.png', 'iconValue':'9'});
			
			iconSelect.refresh(icons);      
        </script>

     
     <script>
     function getAvatarID(){
			document.getElementById('avatar_id_field').value = iconSelect.getSelectedValue();
		};
	</script>


    </body>
</html>