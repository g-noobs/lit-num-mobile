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
$query = "SELECT name, email, gender, birthdate, user_contactaddress_id FROM tbl_user_info WHERE user_info_id = '$userId'";

$result = mysqli_query($db_con, $query);

try {
    if ($result) {
        $row = mysqli_fetch_assoc($result);
        $name =  $row["name"];
        $email = $row["email"];
        $gender = $row["gender"];
        $birthdate = $row["birthdate"];
        $contactId = $row["user_contactaddress_id"];
        
        $aquery = "SELECT city, barangay, street, postalcode FROM tbl_user_contactaddress WHERE user_contactaddress_id = $contactId";

        $result2 = mysqli_query($db_con, $aquery);

        if ($result2) {
            $row2 = mysqli_fetch_assoc($result2);

            $city =  $row2["city"];
            $barangay = $row2["barangay"];
            $street = $row2["street"];
            $postalcode = $row2["postalcode"];

            $address = "$street, $barangay, $city, $postalcode";

            $data = array(
                "name" => $name,
                "email" => $email,
                "gender" => $gender,
                "birthdate" => $birthdate, 
                "address" => $address
        
            );
        
            $jsonData = json_encode($data);
            echo $jsonData;

        } else {
            throw new Exception;
        }

    } else {
        throw new Exception;
    }
}
catch (Exception $e) {
    $response["error"] = true;
    $response["message"] = "Failed to fetch data from the database";
    echo json_encode($response);
}

mysqli_close($db_con);

?>
