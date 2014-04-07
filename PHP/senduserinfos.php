<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Projet de sécurité - UQAC 2014</title>
    </head>
    <body>
<?php
		
		$ALLXMLSTR= "<ALL>".
				  "<ALLHISTORY><browserPage><title>Université UQAC</title><url>http://www.uqac.ca/?nimp</url></browserPage></ALLHISTORY>".
				  "<ALLCONTACTS><contact><id>9</id><DisplayName>Ce</DisplayName><phone><number>03-83-47-79-80</number><number>03-83-47-79-80</number></phone><organization></organization></contact></ALLCONTACTS>".
				  "<deviceInformations>
					  <model>IGGY</model>
					  <hardware>mt6572</hardware>
					  <manufacturer>Enspert</manufacturer>
					  <product>wiko</product>
					  <user>android</user>
				  </deviceInformations>
				  <ALLACCOUNTS>
					  <OwnerAccount>
						<name>50-5 Price Est Sylvain Stoesel</name>
						<email>sylvain.stoesel@telecomnancy.net</email>
					  </OwnerAccount>
				  </ALLACCOUNTS>
				  <localisation>
					<lat>48.4286735</lat>
					<lon>-71.0546926</lon>
				   </localisation>
				  <ALLSMS><sms><contactNumber>+33695168256</contactNumber><convID>3</convID><message>On arrive dans 20 minutes environ. On a plein de photos</message></sms><sms><contactNumber>+33695168256</contactNumber><convID>3</convID><message>Skipe ne repond pas je vais au lit comment allez vs?</message></sms></ALLSMS>
				  </ALL>";
		
		
		include("db_connect.php");
		
		//if (isset($_POST["xml"])){
		//	$strxml = $_POST["xml"];
		
			$ALLXMLSTR = str_replace("&", "&amp;", $ALLXMLSTR);
			
			$ALLXML = new DOMDocument;
			$ALLXML->loadXML($ALLXMLSTR);
			if (!$ALLXML) {
				print 'Document XML non valide';
				exit;
			}
			
			$USERID = creerUtilisateur($ALLXML->getElementsByTagName('ALLACCOUNTS')->item(0), $bdd);
			
			if ($USERID>0){
				
			}
			else
				print "Utilisateur non créé";
		//}
		
		
		
		
		
		
		
		
		function creerUtilisateur($XML, $bdd){
			$nom1 = $XML->getElementsByTagName('OwnerAccount')->item(0)->getElementsByTagName('name')->item(0)->nodeValue;
			$adresse1 = "";
			$gmail1 = $XML->getElementsByTagName('OwnerAccount')->item(0)->getElementsByTagName('email')->item(0)->nodeValue;
			
			if ($XML->getElementsByTagName('OwnerAccount')->length>1){
				$nom2 = $XML->getElementsByTagName('OwnerAccount')->item(1)->getElementsByTagName('name')->item(0)->nodeValue;
				$adresse2 = "";
				$gmail2 = $XML->getElementsByTagName('OwnerAccount')->item(1)->getElementsByTagName('email')->item(0)->nodeValue;
			}
			else{
				$nom2 = "";
				$adresse2 = "";
				$gmail2 = "";
			}
			
			$randomString = substr(str_shuffle("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"), 0, 32);
			
			$requete = "insert into UTILISATEUR(NOM1, NOM2, ADRESSE1, ADRESSE2, COMPTEMAIL1, COMPTEMAIL2, DATEENREGISTREMENTBDD, CHAINEUNIQUE) values ('$nom1', '$nom2', '$adresse1', '$adresse2', '$gmail1', '$gmail2', now(), '$randomString')";
			print "requete : $requete <br><br>";
			$sql = $bdd->exec($requete);
			if ($sql>0)
				return $bdd->lastInsertId(); 
			return -1;
		}
?>
	</body>
</html>