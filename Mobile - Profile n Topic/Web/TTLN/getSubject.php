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

// Retrieve the name in MySQL server
$query = "SELECT * FROM tbl_subject";

$result = mysqli_query($db_con, $query);

try {
    if ($result) {
        $data = array();
        while ($row = mysqli_fetch_assoc($result)) {
            $subjectId = $row["subject_id"];
            $subject = $row["subject_name"];
            
            $item = array(
                "subjectId" => $subjectId,
                "subject" => $subject
            );
        
            $data[] = $item;
        }


        $jsonData = json_encode($data);
        echo $jsonData;

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
