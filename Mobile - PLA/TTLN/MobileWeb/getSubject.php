<?php

header('Content-Type: application/json');

require 'connection.php';

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
