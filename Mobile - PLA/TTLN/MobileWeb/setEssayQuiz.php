<?php

header('Content-Type: application/json');

require 'connection.php';

$lessonId = $_GET['param1'];
$quizQuestion = $_GET['param2'];
$answer = $_GET['param3'];
$score = $_GET['param4'];

$query = "UPDATE tbl_quiz SET quiz_selectionA = '$answer', score = '$score' WHERE quiz_id = '$lessonId' AND quiz_question = '$quizQuestion'";
$result = mysqli_query($db_con, $query);

if($result) {
    $response["success"] = true;
    $response["message"] = "Successfully took quiz";
    echo json_encode($response);
}

else {
    $response["error"] = true;
    $response["message"] = "Failed to update quiz";
    echo json_encode($response);
}

mysqli_close($db_con);
?>