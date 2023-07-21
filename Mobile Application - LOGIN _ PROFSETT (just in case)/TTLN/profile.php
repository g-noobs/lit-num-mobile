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

$userId = $_GET['param1'];

// Retrieve the name in MySQL server
$query = "SELECT name, email, gender, birthdate FROM tbl_user_info WHERE user_info_id = '$userId'";

$result = mysqli_query($db_con, $query);

if ($result) {
    $row = mysqli_fetch_assoc($result);

    // Create an associative array with keys and values
    $data = array(
        "name" => $row["name"],
        "email" => $row["email"],
        "gender" => $row["gender"],
        "birthdate" => $row["birthdate"]
    );

    // Encode the associative array as JSON
    $jsonData = json_encode($data);

    echo $jsonData;
} else {
    $response["error"] = true;
    $response["message"] = "Failed to fetch data from the database";
    echo json_encode($response);
}

mysqli_close($db_con);

?>
