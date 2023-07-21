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
$address = $_GET['param2'];
$userId = $_GET['param3'];

$components = explode(', ', $address);
$street = $components[0];
$barangay = $components[1];
$city = $components[2];
$postalcode = $components[3];

// Update the name in MySQL server
$query = "UPDATE tbl_user_info SET name = '$name' WHERE user_info_id = '$userId'";
$result = mysqli_query($db_con, $query);

$aquery = "SELECT user_contactaddress_id FROM tbl_user_info WHERE user_info_id = '$userId'";
$result2 = mysqli_query($db_con, $aquery);

if ($result && $result2) {
    $row = mysqli_fetch_assoc($result2);

    $addressId = $row["user_contactaddress_id"];

    $aquery = "UPDATE tbl_user_contactaddress SET city = '$street', barangay = '$barangay', city = '$city', postalcode = '$postalcode' WHERE user_contactaddress_id = '$addressId'";
    $result2 = mysqli_query($db_con, $aquery);

    $response["success"] = true;
    $response["message"] = "Name updated successfully";
    echo json_encode($response);
}

elseif ($result) {
    $response["success"] = true;
    $response["message"] = "Name updated successfully";
    echo json_encode($response);
}

elseif ($result2) {
    $row = mysqli_fetch_assoc($result2);

    $addressId = $row["user_contactaddress_id"];

    $aquery = "UPDATE tbl_user_contactaddress SET city = '$street', barangay = '$barangay', street = '$street', postalcode = '$postalcode' WHERE user_contactaddress_id = '$addressId'";
    $result2 = mysqli_query($db_con, $aquery);

    $response["success"] = true;
    $response["message"] = "Address updated successfully";
    echo json_encode($response);
}

else {
    $response["error"] = true;
    $response["message"] = "Failed to update name";
    echo json_encode($response);
}

mysqli_close($db_con);
?>
