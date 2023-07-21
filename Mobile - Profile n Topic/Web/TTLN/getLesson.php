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


$subjectId = $_GET["param1"];

$query = "SELECT lesson_id, lesson_name FROM tbl_lesson WHERE lesson_id LIKE '$subjectId%'";

$result = mysqli_query($db_con, $query);

try {
    if ($result) {
        $data = array();
        while ($row = mysqli_fetch_assoc($result)) {
            $subjectId = $row["lesson_id"];
            $subject = $row["lesson_name"];
            
            $item = array(
                "lessonId" => $subjectId,
                "lesson" => $subject
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
