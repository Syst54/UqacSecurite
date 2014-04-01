<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Projet de sécurité - UQAC 2014</title>
    </head>
    <body>
<?php
		include("db_connect.php");
		
		if (isset($_POST["nom"]) && isset($_POST["gmail"])){
			$nom = $_POST["nom"];
			$gmail = $_POST["gmail"];
			
			echo "nom : $nom    gmail : $gmail";
			
			$sql = $bdd->exec("insert into UTILISATEUR(NOM, GMAIL) values ('$nom', '$gmail')");
			
			if ($sql2>=0)
				echo "<div>Utilisateur correctement ajouté.</div>";
			else
				echo "<div>Utilisateur non ajouté.</div>";
		}
		else
			echo "<div>Aucune info envoyée</div>";
?>
	</body>
</html>