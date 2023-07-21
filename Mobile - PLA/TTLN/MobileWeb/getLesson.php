<?php

header('Content-Type: application/json');

require 'connection.php';

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
