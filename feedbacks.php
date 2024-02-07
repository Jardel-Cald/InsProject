<!DOCTYPE html>
<html lang="de">
<head>
    <title>Auswertung</title>
    <link rel="stylesheet" type="text/css"
          href="style.css">
</head>
<body>
<nav>
    <h3>Navigation</h3>
    <ul>
        <li>
            <a href="startseite.html">Startseite</a>
        </li>
        <li>
            <a href="lebenslauf.html" target="t2">Lebenslauf</a>
        </li>
        <li>
            <a href="zeugnisse.html" target="t2">Zeugnisse</a>
        </li>
        <li>
            <a href="kontakt.html" target="t2">Kontakt</a>
        </li>
        <li>
            <a href="feedback.html" target="t2">Feedback</a>
        </li>
        <li>
            <a href="feedbacks.php">Auswertung</a>
        </li>
    </ul>
</nav>
<hr>
<table>
    <caption>Auswertung der Bewertungen der Webseite</caption>
    <tr style="background-color: forestgreen;">
        <th colspan=7>Alle Feedbacks</th>
    </tr>
    <tr style="background-color: crimson;font-weight: normal; font-style: italic;">
        <td>Name</td>
        <td>Alter</td>
        <td>Datum</td>
        <td>Seite wieder besuchen</td>
        <td>Note Inhalt</td>
        <td>Note Optik</td>
        <td>Verbesserungsvorschlag</td>
    </tr>
    <?php

        function noteUebersetzen($notenName) {
        $ausgabeNote = 0;
        if(strcmp($notenName,"sehr_gut") == 0) {
            $ausgabeNote = 1;
        }elseif(strcmp($notenName,"gut") == 0) {
            $ausgabeNote = 2;
        }elseif(strcmp($notenName,"befriedigend") == 0) {
            $ausgabeNote = 3;
        }elseif(strcmp($notenName,"ausreichend") == 0) {
            $ausgabeNote = 4;
        }
        elseif(strcmp($notenName,"mangelhaft") == 0) {
            $ausgabeNote = 5;
        }elseif(strcmp($notenName,"ungenuegend") == 0) {
            $ausgabeNote = 6;
        }
        return $ausgabeNote;
    }
    $xml = simplexml_load_file("Gesammtauswertung.xml") or die("Error: Cannot create object");



    for ($i = 0; $i < count($xml->children()) - 1; $i++) {

        echo "<tr>";
        echo "<td>" . $xml->feedback[$i]->besucher['vorname'] . " " . $xml->feedback[$i]->besucher['nachname'] . "</td>";
        echo "<td style='text-align: center'>" . $xml->feedback[$i]->besucher->alter . "</td>";
        echo "<td style='text-align: center'>" . $xml->feedback[$i]->info->datum . "</td>";
        echo "<td style='width: 170px; text-align: center'>" . $xml->feedback[$i]->bewertung['erneuter_besuch'] . "</td>";
        if(noteUebersetzen($xml->feedback[$i]->bewertung['note_inhalt']) >= 5) {
            echo "<td style='color: red; width: 120px; text-align: center;'>" . noteUebersetzen($xml->feedback[$i]->bewertung['note_inhalt']). "</td>";
        } else {
            echo "<td style='color: #00bf00; width: 120px; text-align: center;'>" . noteUebersetzen($xml->feedback[$i]->bewertung['note_inhalt']). "</td>";

        }
        if(noteUebersetzen($xml->feedback[$i]->bewertung['note_aussehen']) >= 5) {
            echo "<td style='color: red; width: 120px; text-align: center;'>" . $xml->feedback[$i]->bewertung['note_aussehen']. "</td>";
        } else {
            echo "<td style='color: #00bf00;width: 120px; text-align: center;'>" . $xml->feedback[$i]->bewertung['note_aussehen']. "</td>";

        }
        if(count($xml->feedback[$i]->bewertung->children()) > 0) {
            echo "<td>" . $xml->feedback[$i]->bewertung->vorschlag . "</td>";
        }else {
            echo "<td style='color: #a9a9a9; font-style: italic; width: 600px;'>" ."keine Verbesserungsvorschläge angegeben". "</td>";
        }
        echo "</tr>";
    }

    ?>
</table>

</body>


</html>