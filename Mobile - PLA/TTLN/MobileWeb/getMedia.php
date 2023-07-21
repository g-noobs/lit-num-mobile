<?php
header('Content-Type: application/json');

require 'connection.php';

$imgOnly = $_GET['param1'];
$mediaId = $_GET['param2'];

if ($imgOnly) {
    $query = "SELECT image_path FROM tbl_content_image WHERE content_imagepath_id = '$mediaId'";
    $result = mysqli_query($db_con, $query);

    $row = mysqli_fetch_assoc($result);

    $path = $row['tbl_video_path'];

    $data = array(
        "path" => $path,
    );

    $jsonData = json_encode($data);
    echo $jsonData;
}

else if (!$imgOnly) {
    $query = "SELECT tbl_video_path FROM tbl_video WHERE tbl_video_id = '$mediaId'";
    $result = mysqli_query($db_con, $query);

    $row = mysqli_fetch_assoc($result);

    $path = $row['tbl_video_path'];

    $data = array(
        "path" => $path,
    );

    $jsonData = json_encode($data);
    echo $jsonData;
}

else {
    $response["error"] = true;
    $response["message"] = "Failed to fetch data from the database";
    echo json_encode($response);
}

mysqli_close($db_con)

?>