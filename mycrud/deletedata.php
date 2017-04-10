<?php
require_once 'connection.php';

$dbname="online_todo";

mysql_select_db($dbname);

$id=$_POST['id'];



$query="DELETE FROM `todolist` WHERE `todolist`.`id` =".$id;

if(mysql_query($query)){
	echo "data Deleted";
}else{
	echo mysql_error();
}
?>