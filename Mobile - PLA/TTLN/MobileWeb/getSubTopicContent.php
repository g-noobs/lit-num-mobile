<?php
header('Content-Type: application/json');

require 'connection.php';

$lessonId = $_GET["param1"];

$query = "SELECT * FROM tbl_subtopic WHERE lesson_id LIKE '$lessonId%'";

$result = mysqli_query($db_con, $query);

try {
    if ($result) {
        $data = array();
        while ($row = mysqli_fetch_assoc($result)) {
            $subTopicId = $row["subtopic_id"];
            $topicTitle = $row["subtopic_title"];
            $subtopicContent = $row["subtopic_content"];
            $contentImgPath = $row["content_imagepath_id"];
            $audioId = $row["audio_id"];
            $videoId = $row["video_id"];

            if ($videoId == 0) {
                $item = array(
                    "imgOnly" => true,
                    "subTopicId" => $subTopicId,
                    "topicTitle" => $topicTitle,
                    "subtopicContent" => $subtopicContent,
                    "contentImgPath" => $contentImgPath,
                    "audioId" => $audioId,
                    "videoId" => 0
                );

                $data[] = $item;
            } else {
                $item = array(
                    "imgOnly" => false,
                    "subTopicId" => $subTopicId,
                    "topicTitle" => $topicTitle,
                    "subtopicContent" => $subtopicContent,
                    "contentImgPath" => $contentImgPath,
                    "audioId" => $audioId,
                    "videoId" => $videoId
                );

                $data[] = $item;
            }
        }

        $jsonData = json_encode($data);
        echo $jsonData;

    } else {
        throw new Exception();
    }
} catch (Exception $e) {
    $response["error"] = true;
    $response["message"] = "Failed to fetch data from the database";
    echo json_encode($response);
}

mysqli_close($db_con);
?>
