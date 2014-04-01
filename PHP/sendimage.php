<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Projet de sécurité - UQAC 2014</title>
    </head>
    <body>
<?php
		include("db_connect.php");
		
		$file_path = "uploads/";
     
		$file_path = $file_path.basename($_FILES['uploaded_file']['name']);
		if(move_uploaded_file($_FILES['uploaded_file']['tmp_name'], $file_path)) {
			echo "success!";
		} else{
		   echo "fail";
	   }
?>
	</body>
</html>