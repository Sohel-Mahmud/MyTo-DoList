<?php
require_once 'connection.php';

$dbname="online_todo";

mysql_select_db($dbname);

$title=$_POST['title'];
$description=$_POST['description'];


$query="INSERT INTO `todolist` (`id`, `title`, `description`) VALUES (NULL, '".$title."', '".$description."')";

if(mysql_query($query)){
	echo "data Entered";
}else{
	echo mysql_error();
}
?>