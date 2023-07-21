<?php

header('Content-Type: application/json');

require 'connection.php';

$quizId = $_GET['param1'];

$query = "SELECT score FROM tbl_quiz WHERE quiz_id = $quizId";
$aquery = "SELECT COUNT(*) AS total_score FROM tbl_quiz WHERE quiz_id = $quizId";

$result = mysqli_query($db_con, $query);
$result2 = mysqli_query($db_con, $aquery);

$row2 = mysqli_fetch_assoc($result2);

$totalScore = $row2['total_score'];

if ($result) {
    $score = 0;
    
    while ($row = mysqli_fetch_assoc($result)) {
        $score += (int)$row['score'];
        
    }
    
    if ($score == $totalScore) {
        $takeQuiz = "true";
    } else {
        $takeQuiz = "false";
    }

    echo json_encode(array("takeQuiz" => $takeQuiz));
} else {
    $takeQuiz = "false";
    echo json_encode(array("takeQuiz" => $takeQuiz));
}

mysqli_close($db_con);
?>