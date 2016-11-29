INSERT INTO NEWS_KEYWORD (ID, NAME, DESCRIPTION, CREATED_ON, updated_on) VALUES (1, 'Nahverkehr', 'Artikel, die S-Bahn, U-Bahn etc. betreffen.', '2015-10-03', Now());
INSERT INTO NEWS_KEYWORD (ID, NAME, DESCRIPTION, CREATED_ON, updated_on) VALUES (2, 'Berlin', 'Berliner Themen.', '2015-10-03', Now());
INSERT INTO NEWS_KEYWORD (ID, NAME, DESCRIPTION, CREATED_ON, updated_on) VALUES (3, 'Hamburg', 'Hamburg Themen.', '2015-10-03', Now());
INSERT INTO NEWS_KEYWORD (ID, NAME, DESCRIPTION, CREATED_ON, updated_on) VALUES (4, 'Europa', 'Europ. Themen.', '2015-10-03', Now());

INSERT INTO NEWS_AUTHOR(ID, FIRSTNAME, LASTNAME, CREATED_ON, updated_on) VALUES (1, 'Lorenz', 'Vossen', '2015-10-03', Now());
INSERT INTO NEWS_AUTHOR(ID, FIRSTNAME, LASTNAME, CREATED_ON, updated_on) VALUES (2, 'Jochen', 'König', '2015-10-03', Now());
INSERT INTO NEWS_AUTHOR(ID, FIRSTNAME, LASTNAME, CREATED_ON, updated_on) VALUES (3, 'Frau', 'Rust', '2015-10-03', Now());
INSERT INTO NEWS_AUTHOR(ID, FIRSTNAME, LASTNAME, CREATED_ON, updated_on) VALUES (4, 'Angela', 'Merkel', '2015-10-03', Now());

INSERT INTO NEWS_ARTICLE (ID, HEADLINE, DESCRIPTION, TEXT, PUBLISHED_ON, CREATED_ON, updated_on) VALUES (1, 'Die S-Bahn baut auch 2016 fleißig weiter', 'Auch 2016 wird bei der S-Bahn fleißig gebaut. Stadt- und Ringbahn sind betroffen. Fußballfans werden verschont – aber nicht alle.', 'Ein Rekordjahr in Sachen Baumaßnahmen hatte die Deutsche Bahn einst für 2015 angekündigt. Seit Donnerstag ist klar: 2016 wird nicht besser. "Es gibte für die Berliner keine Entwarnung", sagt Alexander Kaczmarek, Konzernbevollmächtigter der Bahn für die Hauptstadt.  Zu den bereits bestehenden Bauvorhaben gesellen sich noch einige hinzu. Mal wieder betroffen ist die Stadtbahn – für ein halbes Jahr. Von Mai bis Oktober 2016 rüstet die Bahn den Abschnitt zwischen Friedrichstraße und Westkreuz mit ihrem Zugbeeinflussungssystem ZBS aus, wobei der größte Teil der Arbeiten auf die Strecke Friedrichstraße–Zoo entfällt.', Now(), Now(), Now());
INSERT INTO NEWS_ARTICLE (ID, HEADLINE, DESCRIPTION, TEXT, PUBLISHED_ON, CREATED_ON, updated_on) VALUES (5, 'Die S-Bahn baut auch 2016 fleißig weiter', 'Auch 2016 wird bei der S-Bahn fleißig gebaut. Stadt- und Ringbahn sind betroffen. Fußballfans werden verschont – aber nicht alle.', 'Ein Rekordjahr in Sachen Baumaßnahmen hatte die Deutsche Bahn einst für 2015 angekündigt. Seit Donnerstag ist klar: 2016 wird nicht besser. "Es gibte für die Berliner keine Entwarnung", sagt Alexander Kaczmarek, Konzernbevollmächtigter der Bahn für die Hauptstadt.  Zu den bereits bestehenden Bauvorhaben gesellen sich noch einige hinzu. Mal wieder betroffen ist die Stadtbahn – für ein halbes Jahr. Von Mai bis Oktober 2016 rüstet die Bahn den Abschnitt zwischen Friedrichstraße und Westkreuz mit ihrem Zugbeeinflussungssystem ZBS aus, wobei der größte Teil der Arbeiten auf die Strecke Friedrichstraße–Zoo entfällt.', Now(), Now(), Now());

INSERT INTO NEWS_ARTICLE_KEYWORD (ARTICLE_ID, KEYWORD_ID) VALUES (1, 1);
INSERT INTO NEWS_ARTICLE_KEYWORD (ARTICLE_ID, KEYWORD_ID) VALUES (1, 2);
INSERT INTO NEWS_ARTICLE_KEYWORD (ARTICLE_ID, KEYWORD_ID) VALUES (5, 3);
INSERT INTO NEWS_ARTICLE_KEYWORD (ARTICLE_ID, KEYWORD_ID) VALUES (5, 4);

INSERT INTO NEWS_ARTICLE_AUTHOR (ARTICLE_ID, AUTHOR_ID) VALUES (1, 1);
INSERT INTO NEWS_ARTICLE_AUTHOR (ARTICLE_ID, AUTHOR_ID) VALUES (1, 2);
INSERT INTO NEWS_ARTICLE_AUTHOR (ARTICLE_ID, AUTHOR_ID) VALUES (5, 3);
INSERT INTO NEWS_ARTICLE_AUTHOR (ARTICLE_ID, AUTHOR_ID) VALUES (5, 4);