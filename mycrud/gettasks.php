<?php
require_once 'connection.php';

$dbname="online_todo";

mysql_select_db($dbname);


$query="SELECT * FROM `todolist`";

if($result=mysql_query($query)){
	$alldata=array();
	while($row=mysql_fetch_assoc($result)){
		$alldata[]=$row;
	}
	echo json_encode($alldata);
}else{
	echo mysql_error();
}
?>