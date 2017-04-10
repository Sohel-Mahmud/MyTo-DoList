<?php
require_once 'connection.php';

$dbname="online_todo";

mysql_select_db($dbname);

$name=$_POST['name'];
$email=$_POST['email'];
$password=$_POST['password'];

$query="INSERT INTO `users` (`user_id`, `name`, `email`, `password`) VALUES (NULL, '".$name."', '".$email."', '".$password."')";

if(mysql_query($query)){
	echo "data Entered";
}else{
	echo mysql_error();
}
?>