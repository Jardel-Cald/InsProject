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
<div style="background-color: #003566; margin-left: 200px;margin-right: 200px;">
    <p>
        <?php
        error_reporting(0);


        class MyException extends Exception
        {
            function __construct($exception)
            {
                $this->exception = $exception;
            }

            function Display()
            {
                echo "MyException: $this->exception\n";
            }
        }

        if (strcmp($_GET["password"], "Internetsprachen") == 0) {
        } else {
            throw new MyException("PW falsch");
        }

        if (isset($_GET["mail"])) {
            if (strcmp($_GET["emailadresse"], "") == 0) {
                trigger_error("Es ist keine EMail Adresse angegeben. Es kann keine Kopie gesendet werden.", E_USER_WARNING);
            }
        }


        if (isset($_GET["kontakt"])) {
            if (strcmp($_GET["emailadresse"], "") == 0
                && strcmp($_GET["telefon"], "") == 0
                && strcmp($_GET["website"], "") == 0) {
                trigger_error("Es gibt keine Kontaktmöglichkeit geben sie eine E-Mail Adresse, Telefonnummer oder Webseite an.", E_USER_WARNING);

            }
        }

        ?>
        Die Anrede war: <strong><?php echo $_GET["anrede"]; ?></strong> <br>
        Der Vorname war: <strong> <?php echo $_GET["vorname"]; ?></strong><br>
        Der Nachname war: <strong><?php echo $_GET["nachname"]; ?></strong><br>
        Das Alter war: <strong><?php echo $_GET["alter"]; ?></strong><br>
        <br>

        Die E-Mail Checkbox:
        <strong><?php echo isset($_GET["mail"]) ? "ausgewählt" : "nicht ausgewählt"; ?></strong><br>
        Die Rückmeldungs Checkbox:
        <strong><?php echo isset($_GET["kontakt"]) ? "ausgewählt" : "nicht ausgewählt"; ?></strong><br>
        Die E-Mail Adresse war: <strong><?php echo $_GET["emailadresse"]; ?></strong><br>
        Die Telefonnummer war: <strong><?php echo $_GET["telefon"]; ?></strong><br>
        Die Webseite war: <strong><?php echo $_GET["website"]; ?></strong><br>

        <br>
        Die Aufmerksamkeit auf die Website wurde hervorgerufen durch:
        <strong><?php echo $_GET["aufmerksam"]; ?></strong><br>
        Note für Inhalt: <strong><?php
            $noten = array('sehr gut', 'gut', 'befriedigend', 'ausreichend', 'mangelhaft', 'ungenügend');
            echo $noten[$_GET["note"] - 1]; ?></strong><br>
        Note für das Aussehen: <strong><?php
            echo $noten[$_GET["anote"] - 1]; ?></strong><br>
        Ihr Verbesserungsvorschlag war:
            <strong><?php echo $_GET["verbesserung"]; ?></strong><br>
        <br>
        Viele Dank <?php echo $_GET["anrede"] . " ";
        echo $_GET["vorname"] . " ";
        echo $_GET["nachname"] . " "; ?>
        ,dass Sie meine Webseite besuchen.
        <br>
        <?php
        function notenText($note)
        {
            if ($note == 1 || $note == 2) {
                echo "Ich freue mich das Ihnen der Inhalt der Seite gefallen hat";
            } elseif ($note == 3) {
                echo "Ich werde mich bemühen, in Zukunft den Inhalt der Seite zu optimieren.";
            } else {
                echo "Schade, dass ihnen meine Seite nicht gefällt.";
            }
        }

        notenText($_GET["note"]);


        ?><br>
        <?php
        function aussehen($note)
        {
            if ($note == 1) {
                echo "Ich freue mich, dass Ihnen das Aussehen der Seite sehr gut gefallen hat";
            } elseif ($note == 2) {
                echo "Ich find es gut, dass Sie die Optik meine Seite mögen.";
            } elseif ($note == 3) {
                echo "Ich werde das Aussehen verbessern.";
            } elseif ($note == 4) {
                echo "schade, dass Ihnen die Optik nicht gefällt.";
            } elseif ($note == 5) {
                echo "vielleicht ist eine andere Seite für Sie etwas.";
            } elseif ($note == 6) {
                echo "vielleicht gefällt Ihnen die Optik von anderen Seiten besser.";
            }
        }

        aussehen($_GET["anote"]);


        ?><br>
        <?php
        if (($_GET["note"] > 3 || $_GET["anote"] > 3) && strlen($_GET["verbesserung"]) < 10) {
            echo "Wenn Ihnen die Seite nicht gefällt, können Sie wenigstens einige Verbesserungsvorschläge geben.";
            echo("<br>");

        }

        ?>
        <?php
        setlocale(LC_TIME, "de_DE", "deu_deu");
        echo "Ihre Feedbackwerte wurden am " . date("d.m.Y H:i") . " Uhr" . " aufgenommen.";

        ?>
        <br>

        <?php
        $fileName = "Auswertungen/" . $_GET["nachname"] . time() . ".xml";
        $xml = new DOMDocument("1.0", "utf-8");
        $implementation = new DOMImplementation();
        $xml->appendChild($implementation->createDocumentType('Formular SYSTEM "Feedback.dtd"'));


        $root = $xml->createElement("Formular");
        $xml->appendChild($root);
        $root->setAttribute("Datum", date("d.m.Y"));
        $root->setAttribute("Uhrzeit", date("H:i"));

        $root->appendChild($nextNode = $xml->createElement("Person"));
        $nextNode->appendChild($xml->createElement("Anrede", $_GET["anrede"]));
        $nextNode->appendChild($xml->createElement("Vorname", $_GET["vorname"]));
        $nextNode->appendChild($xml->createElement("Nachname", $_GET["nachname"]));
        $nextNode->appendChild($xml->createElement("Datum", date("d.m.Y")));
        $nextNode->appendChild($xml->createElement("Uhrzeit", date("H:i")));

        $nextNode->appendChild($xml->createElement("Alter", $_GET["alter"]));
        $root->appendChild($nextNode = $xml->createElement("Kontakt"));
        if (strlen($_GET["emailadresse"]) > 0) {
            $nextNode->appendChild($xml->createElement("E-Mail", $_GET["emailadresse"]));
        }
        if (strlen($_GET["telefon"]) > 0) {
            $nextNode->appendChild($xml->createElement("Telefonnummer", $_GET["telefon"]));
        }
        if (strlen($_GET["website"]) > 0) {
            $nextNode->appendChild($xml->createElement("Webseite", $_GET["website"]));
        }
        $nextNode->appendChild($xml->createElement("Kopie_erstellen", isset($_GET["mail"]) ? "ja" : "nein"));
        $nextNode->appendChild($xml->createElement("Zurückmelden", isset($_GET["kontakt"]) ? "ja" : "nein"));

        $root->appendChild($nextNode = $xml->createElement("Feedback"));
        if (strlen($_GET["verbesserung"]) > 0) {
            $nextNode->appendChild($xml->createElement("Verbesserungen", $_GET["verbesserung"]));
        }
        $nextNode->appendChild($xml->createElement("Erneut_besuchen", isset($_GET["besuchen"]) ? "ja" : "nein"));
        $nextNode->appendChild($xml->createElement("Aufmerksam", $_GET["aufmerksam"]));
        if(strlen($_GET["note"]) > 0) {
            $nextNode->appendChild($xml->createElement("Inhalt_bewertung", $_GET["note"]));
        }
        if(strlen($_GET["anote"]) > 0) {
            $nextNode->appendChild($xml->createElement("Aussehen_bewertung", $_GET["anote"]));
        }


        $xml->save($fileName);

        ?>
    </p>
</div>
</body>

