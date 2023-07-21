<?php
$response = array();
header('Content-Type: application/json');

$host = "localhost:3307";
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

// handle request
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $requestData = json_decode(file_get_contents('php://input'), true);

    if (isset($requestData["type"])) {
        $type = $requestData["type"];

        if ($type === "fetchUser") {
            // retrieve user data from the database
            $query = "SELECT name FROM tbl_user_info";
            $result = mysqli_query($db_con, $query);

            if ($result) {
                $users = array();

                // Fetch rows from the result set
                while ($row = mysqli_fetch_assoc($result)) {
                    $users[] = $row;
                }

                $response["error"] = false;
                $response["message"] = "User data fetched successfully.";
                $response["users"] = $users;
                echo json_encode($response);
                exit;
            } else {
                $response["error"] = true;
                $response["message"] = "Failed to fetch user data. Error: " . mysqli_error($db_con);
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
