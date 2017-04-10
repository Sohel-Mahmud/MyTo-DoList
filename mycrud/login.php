<?php
require_once 'connection.php';

$dbname="online_todo";

mysql_select_db($dbname);

$email=$_POST['email'];
$password=$_POST['password'];

$query="Select * From `users` WHERE `email`='".$email."'";

if($result=mysql_query($query)){
	$userData=array();
	$data=mysql_fetch_assoc($result);
		
		if($password===$data['password']){
	$userData['email']=$data['email'];
	$userData['name']=$data['name'];
	$userData['password']=$data['password'];

		echo json_encode($userData);
		}else{
			echo "Wrong password";
		}
}else{
	echo mysql_error();
}
?>