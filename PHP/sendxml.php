<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Projet de sécurité - UQAC 2014</title>
    </head>
    <body>
<?php
		include("db_connect.php");
		
		if (isset($_POST["xml"])){
			$xml = $_POST["xml"];
			$sql = $bdd->exec("insert into BIDON2(value) values ('$xml')");
			
			if ($sql2>=0)
				echo "<div>XML correctement ajouté.</div>";
			else
				echo "<div>XML non ajouté.</div>";
		}
		else{
			echo "<div>Aucune info envoyée</div>";
			$sql = $bdd->exec("insert into BIDON2(value) values ('RIEN')");
		}
?>
	</body>
</html>