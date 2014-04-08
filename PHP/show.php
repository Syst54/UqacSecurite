<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="style.css" />
        <title>Projet de sécurité - UQAC 2014</title>

		<script type="text/javascript">
			function afficher_cacher(id)
			{
				if(document.getElementById(id).style.display=="none")
				{
					document.getElementById(id).style.display="block";
					document.getElementById('bouton_'+id).innerHTML='Cacher le texte';
				}
				else
				{
					document.getElementById(id).style.display="none";
					document.getElementById('bouton_'+id).innerHTML='Afficher le texte';
				}
				return true;
			}
		</script>
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
			genererTelephone($bdd, $USER->ID);
			genererContacts($bdd, $USER->ID);
			genererHistorique($bdd, $USER->ID);
			genererConversations($bdd, $USER->ID);
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
			print "<p>".$USER->ADRESSE1." ".getEmailLink($USER->COMPTEMAIL1)."</p>";
			print "<p>".$USER->ADRESSE2." ".getEmailLink($USER->COMPTEMAIL2)."</p>";
			print "<p>Mise à jour des données le : ".$USER->DATEENREGISTREMENTBDD."</p>";
			print "</div>";
		}
		
		
		
		
		/*******************************************************************************************************
		*
		*   GENERATION ENCADRE TELEPHONE
		*
		*******************************************************************************************************/
		function genererTelephone($bdd, $USERID){
			$requete = "select * from TELEPHONE where ID_UTILISATEUR=$USERID";
			$sql = $bdd->query($requete);
			$TEL = $sql->fetch(PDO::FETCH_OBJ);
			
			print "<div class='divEntite'>";
			print "<h2>Données de l'appareil".(strlen($USER->NUMEROTEL)>0?"(n° tel : ".$TEL->NUMEROTEL.")":"")."</h2>";
			print "<p>Modèle : ".$TEL->MODELE."</p>";
			print "<p>Version : ".$TEL->VERSION."</p>";
			if (strlen($USER->IMEI)>0)
				print "<p>IMEI : ".$TEL->IMEI."</p>";
			print "</div>";
		}
		
		
		
		
		
		/*******************************************************************************************************
		*
		*   GENERATION ENCADRE CONTACTS
		*
		*******************************************************************************************************/
		function genererContacts($bdd, $USERID){
			print "<h2>Contacts</h2><span class='bouton' id='bouton_contact' onclick='afficher_cacher(\"contact\");'>Afficher le texte</span> ";
			print "<div id='contact' class='texte' style='display:none'>";
			print "<table border='1'><tr><td>Nom</td> <td>Numéro(s) de tel.</td> <td>Adresse(s) email</td> <td>Adresse</td> <td>Organisation</td> <td>Contactés</td></tr>";
			$requete = "select * from CONTACT where ID_UTILISATEUR=$USERID order by NBCONTACTSTEL desc";
			$sql = $bdd->query($requete);
			while ($contact=$sql->fetch(PDO::FETCH_OBJ)){
				print "<tr>";
				print "<td><b>".$contact->NOM."</b></td>";
				print "<td>".$contact->NUMEROTEL1."<br>".$contact->NUMEROTEL2."</td>";
				print "<td>".getEmailLink($contact->EMAIL1)."<br>".getEmailLink($contact->EMAIL2)."</td>";
				print "<td>".$contact->ADRESSE."</td>";
				print "<td>".$contact->NOMORGANISATION."<br>".$contact->TITREORGANISATION."</td>";
				print "<td style='font-size:12px'>Par tel:".$contact->NBCONTACTSTEL.($contact->NBCONTACTSSMS>=0?"<br>Par SMS:".$contact->NBCONTACTSSMS:"")."</td>";
				print "</tr>";
			}
			print "</table></div>";
		}
		
		
		
		
		
		
		/*******************************************************************************************************
		*
		*   GENERATION ENCADRE HISTORIQUE
		*
		*******************************************************************************************************/
		function genererHistorique($bdd, $USERID){
			print "<h2>Historique</h2><span class='bouton' id='bouton_historique' onclick='afficher_cacher(\"historique\");'>Afficher le texte</span> ";
			print "<div id='historique' class='texte' style='display:none'>";
			print "<table border='1'><tr><td>Titre du site</td> <td>URL du site</td></tr>";
			$requete = "select * from HISTORIQUE where ID_UTILISATEUR=$USERID";
			$sql = $bdd->query($requete);
			while ($histo=$sql->fetch(PDO::FETCH_OBJ)){
				print "<tr>";
				print "<td>".$histo->TITRE."</td>";
				print "<td style='font-size:12px'><b>".getURLLink($histo->URL)."</b></td>";
				print "</tr>";
			}
			print "</table></div>";
		}
		
		
		
		
		
		/*******************************************************************************************************
		*
		*   GENERATION ENCADRE CONVERSATIONS
		*
		*******************************************************************************************************/
		function genererConversations($bdd, $USERID){
			print "<h2>Conversations</h2><span class='bouton' id='bouton_conv' onclick='afficher_cacher(\"conv\");'>Afficher le texte</span> ";
			print "<div id='conv' class='texte' style='display:none'>";
			$requete = "select distinct conv.ID as ID, cont.NOM as NOM, conv.THREADID as THREADID from CONVERSATION conv, CONTACT cont where conv.ID_UTILISATEUR=$USERID and cont.ID_UTILISATEUR=$USERID and conv.ID_CONTACT=cont.ID";
			$sql = $bdd->query($requete);
			while ($conv=$sql->fetch(PDO::FETCH_OBJ)){
				print "<div class='divEntite'>";
				print "<h3>Conversation n°".$conv->THREADID." avec le contact <b>".$conv->NOM."</b></h3>";
				$requete2 = "select * from SMS where ID_CONVERSATION=".$conv->ID;
				$sql2 = $bdd->query($requete2);
				while ($sms=$sql2->fetch(PDO::FETCH_OBJ)){
					print "<div class='".($sms->ENVOYEPARUTILISATEUR==1?"bulleUser":"bulleContact")."'>";
					print $sms->BODYSMS;
					print "</div>";
				}
				print "</div>";
			}
			print "</div>";
		}
		
		
		function getEmailLink($email){
			if (strlen($email)>0)
				return "<a href='mailto:".$email."'>".$email."</a>";
			return "";
		}
		
		function getURLLink($email){
			if (strlen($email)>0)
				return "<a href='".$email."'>".$email."</a>";
			return "";
		}
?>
	</body>
</html>