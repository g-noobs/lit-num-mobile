<?php

header('Content-Type: application/json');

require 'connection.php';

$email = mysqli_real_escape_string($db_con, $_GET['param1']);
$password = mysqli_real_escape_string($db_con, $_GET['param2']);
$userId = "CRED1";

$query = "SELECT user_info_id FROM tbl_credentials WHERE uname = '$email' AND pass = '$password' AND credentials_id LIKE '$userId%'";
$result = mysqli_query($db_con, $query);

if ($result && mysqli_num_rows($result) > 0) {
    $row = mysqli_fetch_assoc($result);
    $userId = $row["user_info_id"];

    $aquery = "SELECT name FROM tbl_user_info WHERE user_info_id = '$userId'";
    $result2 = mysqli_query($db_con, $aquery);

    if ($result2 && mysqli_num_rows($result2) > 0) {
        $row2 = mysqli_fetch_assoc($result2);

        $data = array(
            "name" => $row2["name"],
            "userId" => $userId
        );

        $jsonData = json_encode($data);
        echo $jsonData;
    } else {
        $response["error"] = true;
        $response["message"] = "Failed to fetch data from the database";
        echo json_encode($response);
    }
} else {
    $response["error"] = true;
    $response["message"] = "Failed to fetch data from the database";
    echo json_encode($response);
}

mysqli_close($db_con);
?>
