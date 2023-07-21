<?php

header('Content-Type: application/json');

$host = "localhost:3307"; //change ip host here
$username = "root"; //change username
$password = ""; //change password
$database = "db_tagakaulo"; //change database name if applicable

$db_con = mysqli_connect($host, $username, $password, $database);

if (mysqli_connect_errno()) {
    $response["error"] = true;
    $response["message"] = "Failed to connect to the database";
    echo json_encode($response);
    exit;
}

$name = $_GET['param1'];
$userId = $_GET['param2'];

// Update the name in MySQL server
$query = "UPDATE tbl_user_info SET name = '$name' WHERE user_info_id = '$userId'";
$result = mysqli_query($db_con, $query);

if ($result) {
    $response["success"] = true;
    $response["message"] = "Name updated successfully";
    echo json_encode($response);
} else {
    $response["error"] = true;
    $response["message"] = "Failed to update name";
    echo json_encode($response);
}

mysqli_close($db_con);

?>
