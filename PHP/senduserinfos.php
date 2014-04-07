<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Projet de sécurité - UQAC 2014</title>
    </head>
    <body>
<?php
		$ALLXMLSTR= "<ALL>".
				  "<ALLHISTORY><browserPage><title>Universit? du Qu?bec ? Chicoutimi - UQAC</title><url>http://www.uqac.ca/</url></browserPage><browserPage><title>Portail des ?tudiants - Universit? du Qu?bec ? Chicoutimi</title><url>http://www.uqac.ca/etudiants/</url></browserPage><browserPage><title>UQAC - Universit? du Qu?bec ? Chicoutimi</title><url>https://wprodl.uqac.ca/dossier_etudiant/</url></browserPage><browserPage><title>UQAC - Universit? du Qu?bec ? Chicoutimi</title><url>https://wprodl.uqac.ca/dossier_etudiant/fenetre_princ.html</url></browserPage><browserPage><title>UQAC - Universit? du Qu?bec ? Chicoutimi</title><url>https://wprodl.uqac.ca/dossier_etudiant/resultats.html</url></browserPage><browserPage><title>UQAC - Universit? du Qu?bec ? Chicoutimi</title><url>https://wprodl.uqac.ca/site_cours/pcow023.html?sigle=8INF847&groupe=11&session1=20141</url></browserPage><browserPage><title>UQAC - Universit? du Qu?bec ? Chicoutimi</title><url>https://wprodl.uqac.ca/dossier_etudiant/courrier/courrier.html</url></browserPage><browserPage><title>Rencontre du 14 avril - Outlook Web Access Light</title><url>https://mailetud.uqac.ca/OWA/?ae=Item&t=IPM.Note&id=RgAAAAAS%2b1AI%2bstlT6jk93b5wPjyBwA4l5OIgLGlRZRjwc5gdDVaAbZPHteUAAA4l5OIgLGlRZRjwc5gdDVaBE1DPdLlAAAJ</url></browserPage><browserPage><title>Ordre de Pr?sentation - Outlook Web Access Light</title><url>https://mailetud.uqac.ca/OWA/?ae=Item&t=IPM.Note&id=RgAAAAAS%2b1AI%2bstlT6jk93b5wPjyBwA4l5OIgLGlRZRjwc5gdDVaAbZPHteUAAA4l5OIgLGlRZRjwc5gdDVaBE1DPdLmAAAJ</url></browserPage><browserPage><title>Mardi 15 avril - Outlook Web Access Light</title><url>https://mailetud.uqac.ca/OWA/?ae=Item&t=IPM.Note&id=RgAAAAAS%2b1AI%2bstlT6jk93b5wPjyBwA4l5OIgLGlRZRjwc5gdDVaAbZPHteUAAA4l5OIgLGlRZRjwc5gdDVaBE1DPdLsAAAJ</url></browserPage><browserPage><title>Bo?te de r?ception - Outlook Web Access Light</title><url>https://mailetud.uqac.ca/OWA/?ae=StartPage&id=LgAAAAAS%2b1AI%2bstlT6jk93b5wPjyAQA4l5OIgLGlRZRjwc5gdDVaAbZPHteUAAAB&slUsng=0&pg=3</url></browserPage><browserPage><title>Bo?te de r?ception - Outlook Web Access Light</title><url>https://mailetud.uqac.ca/OWA/?ae=StartPage&id=LgAAAAAS%2b1AI%2bstlT6jk93b5wPjyAQA4l5OIgLGlRZRjwc5gdDVaAbZPHteUAAAB&slUsng=0&pg=2</url></browserPage><browserPage><title>S?ance du 20 mars - Outlook Web Access Light</title><url>https://mailetud.uqac.ca/OWA/?ae=Item&t=IPM.Note&id=RgAAAAAS%2b1AI%2bstlT6jk93b5wPjyBwA4l5OIgLGlRZRjwc5gdDVaAbZPHteUAAA4l5OIgLGlRZRjwc5gdDVaBE1DPdLSAAAJ</url></browserPage><browserPage><title>Outlook Web App</title><url>https://mailetud.uqac.ca/CookieAuth.dll?GetLogon?curl=Z2FOWAZ2FZ3FaeZ3DStartPageZ26idZ3DLgAAAAASZ252b1AIZ252bstlT6jk93b5wPjyAQA4l5OIgLGlRZ5ARjwc5gdDVaAbZ5APHteUAAABZ26slUsngZ3D0Z26pgZ3D2&reason=0&formdir=1</url></browserPage><browserPage><title>Google</title><url>http://www.google.com/webhp?source=android-home</url></browserPage></ALLHISTORY>".
				  "<ALLCONTACTS><contact><id>9</id><DisplayName>Ce</DisplayName><phone><number>03-83-47-79-80</number><number>03-83-47-79-80</number></phone><organization></organization></contact><contact><id>10</id><DisplayName>Enf. disparus</DisplayName><phone><number>116000</number><number>116000</number></phone><organization></organization></contact><contact><id>11</id><DisplayName>Enf.maltrait?</DisplayName><phone><number>119</number><number>119</number></phone><organization></organization></contact><contact><id>12</id><DisplayName>Police</DisplayName><phone><number>17</number><number>17</number></phone><organization></organization></contact><contact><id>13</id><DisplayName>Pompiers</DisplayName><phone><number>18</number><number>18</number></phone><organization></organization></contact></ALLCONTACTS>".
				  "<deviceInformations><model>IGGY</model><hardware>mt6572</hardware><manufacturer>Enspert</manufacturer><product>wiko</product><user>android</user></deviceInformations>".
				  "<localisation><lat>48.4286735</lat><lon>-71.0546926</lon></localisation>".
				  "<ALLSMS><sms><contactNumber>+33695168256</contactNumber><convID>3</convID><message>On arrive dans 20 minutes environ. On a plein de photos</message></sms><sms><contactNumber>+33695168256</contactNumber><convID>3</convID><message>Skipe ne repond pas je vais au lit comment allez vs?</message></sms></ALLSMS>".
				  "</ALL>";
		
		
		include("db_connect.php");
		
		//if (isset($_POST["xml"])){
		//	$strxml = $_POST["xml"];
			$ALLXML = simplexml_load_string("<?xml version='1.0'?>".$ALLXMLSTR."</xml>");
			
			createUtilisateur();
			
			print $ALLXML;
			
		//}
		
		
		function createUtilisateur(){
			$nom = "USERNAME";   //xml...
			$gmail1 = "gmail.com"; //xml...
			$gmail2 = "gmail.com"; //xml...
			$randomString = substr(str_shuffle("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"), 0, 32);
			$sql = $bdd->exec("insert into UTILISATEUR(NOM, COMPTEMAIL1, COMPTEMAIL2, DATEENREGISTREMENTBDD, CHAINEUNIQUE) values ('$nom', '$gmail1', '$gmail2', now, '$randomString')");
		}
?>
	</body>
</html>