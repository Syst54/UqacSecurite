<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="style.css" />
        <title>Projet de sécurité - UQAC 2014</title>
    </head>
    <body>
<?php
		if (!isset($_GET["uid"])){
			print "<div id='erreur'>Aucun identifiant utilisateur fourni</div>";
			exit;
		}
		
		$CHAINEUNIQUE = addslashes($_GET["uid"]);
		include("db_connect.php");
		
		$USER = chargerUtilisateur($bdd, $CHAINEUNIQUE);
		$USERID = $USER->ID;
		
		if ($USER){
			genererUtilisateur($bdd, $USER);
			
			
		}
		else
			print "<div id='erreur'>Les données de l'utilisateur auxquelles vous souhaiter accéder n'existent pas ou ont été supprimées</div>";
			
				
				
		


		/*******************************************************************************************************
		*
		*   CHARGEMENT UTILISATEUR
		*
		*******************************************************************************************************/
		function chargerUtilisateur($bdd, $CHAINEUNIQUE){
			$requete = "select * from UTILISATEUR where CHAINEUNIQUE='$CHAINEUNIQUE'";
			$sql = $bdd->query($requete);
			return $sql->fetch(PDO::FETCH_OBJ);
		}
		
		/*******************************************************************************************************
		*
		*   GENERATION ENCADRE UTILISATEUR
		*
		*******************************************************************************************************/
		function genererUtilisateur($bdd, $USER){
			print "<div class='divEntite'>";
			print "<h1>".$USER->NOM1."</h1>";
			if (strlen($USER->NOM2)>0)
				print "<h2>(".$USER->NOM2.")</h2>";
			print "<p>".$USER->ADRESSE1." <a href='mailto:".$USER->COMPTEMAIL1."'>".$USER->COMPTEMAIL1."</a></p>";
			print "<p>".$USER->ADRESSE2." <a href='mailto:".$USER->COMPTEMAIL2."'>".$USER->COMPTEMAIL2."</a></p>";
			print "<p>Mise à jour des données le : ".$USER->DATEENREGISTREMENTBDD."</a></p>";
			print "</div>";
		}
?>
	</body>
</html>