<?php
header('Content-Type: application/json');

require 'connection.php';

$lessonId = $_GET['param1'];

try {
    $query = "SELECT quiz_id, quiz_question, quiz_selectionA, quiz_selectionB, quiz_selectionC, quiz_selectionD, score FROM tbl_quiz WHERE quiz_id = '$lessonId'";
    $aquery = "SELECT image_path FROM tbl_quiz_image WHERE quiz_id = '$lessonId'";
    $result = mysqli_query($db_con, $query);
    $result2 = mysqli_query($db_con, $aquery);

    $data = array();

    while (($row = mysqli_fetch_assoc($result)) && ($row2 = mysqli_fetch_assoc($result2))) {
        $quizId = $row['quiz_id'];
        $quizImg = $row2['image_path'];
        $quizQuestion = $row['quiz_question'];
        $choiceA = $row['quiz_selectionA'];
        $choiceB = $row['quiz_selectionB'];
        $choiceC = $row['quiz_selectionC'];
        $choiceD = $row['quiz_selectionD'];
        $score = $row['score'];

        $item = array(
            "quizId" => $quizId,
            "quizImg" => $quizImg,
            "quizQuestion" => $quizQuestion,
            "choiceA" => $choiceA,
            "choiceB" => $choiceB,
            "choiceC" => $choiceC,
            "choiceD" => $choiceD,
            "score" => $score
        );

        $data[] = $item;
    }

    $jsonData = json_encode($data);
    echo $jsonData;
} catch (Exception $e) {
    $response = "Failed to retrieve data";
    echo json_encode($response);
}
mysqli_close($db_con)
?>