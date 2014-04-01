<?php

//$mysql_host = "127.0.0.1";
//$mysql_database = "sceas";
//$mysql_user = "root";
//$mysql_password = "";

$mysql_host = "mysql7.000webhost.com";
$mysql_database = "a7129387_android";
$mysql_user = "a7129387_uqac";
$mysql_password = "uqacsécurité";

try
{
	$bdd = new PDO('mysql:host='.$mysql_host.';dbname='.$mysql_database, $mysql_user, $mysql_password);
	$bdd->query("SET NAMES UTF8");
}
catch (Exception $e)
{
	die('Erreur : ' . $e->getMessage());
}
?>
