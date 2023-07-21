<?php

$response = array();
header('Content-Type: application/json');

$host = "127.0.0.1:3307";
$username = "root";
$password = "";
$database = "db_tagakaulo";

$db_con = mysqli_connect($host, $username, $password, $database);

if (mysqli_connect_errno()) {
    $response["error"] = true;
    $response["message"] = "Failed to connect to the database";
    echo json_encode($response);
    exit;
}

// handle login request
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $requestData = json_decode(file_get_contents('php://input'), true);

    if (isset($requestData["type"])) {
        $type = $requestData["type"];

        if ($type === "login") {
            $username = mysqli_real_escape_string($db_con, $requestData["username"]);
            $password = mysqli_real_escape_string($db_con, $requestData["password"]);

            // retrieve user data from the database
            $userQuery = "SELECT * FROM tbl_user_credentials WHERE username = '$username' AND password = '$password'";
            $userResult = mysqli_query($db_con, $userQuery);

            if (mysqli_num_rows($userResult) == 0) {
                $response["error"] = true;
                $response["message"] = "User not found or invalid login details.";
                echo json_encode($response);
                exit;
            } else {
                $user = mysqli_fetch_assoc($userResult);
                $response["error"] = false;
                $response["message"] = "Successfully logged in.";
                $response["user"] = $user;
                echo json_encode($response);
                exit;
            }
        }
    }
}

$response["error"] = true;
$response["message"] = "Invalid request";
echo json_encode($response);
exit;

?>
