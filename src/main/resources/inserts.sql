
INSERT INTO Parser_Entity (name, segment_delimiter , component_delimiter , sub_component_delimiter , escape_character , repetition_delimiter)
VALUES ('DXH500', '(\r\n|\r|\n)' , '!' , '^' , '~' , '\\' );

-- INSERT INTO Segment (parser_id, name, regex, multi_line)
-- VALUES
--     (1, 'Header', '^H\|[^\r\n]*$', FALSE);

INSERT INTO Segment (parser_id, name, regex, multi_line) VALUES(1, 'PID', '^P\|[^\r\n]*$', FALSE);
INSERT INTO Segment (parser_id, name, regex, multi_line) VALUES(1, 'OBR', '^O\|[^\r\n]*$', FALSE);
INSERT INTO Segment (parser_id, name, regex, multi_line) VALUES(1, 'OBX', '^R\|[^\r\n]*$', TRUE);


INSERT INTO Field (segment_id, name, regex , position)
VALUES
    (1, 'patientid', '',4);

-- INSERT INTO Field (segment_id, name, regex , position)
-- VALUES
--     (2, 'PatientName', '' , 5);

INSERT INTO Field (segment_id, name, regex , position , component_index)
VALUES
    (1, 'dateofbirth', '' , 8 , 1);

INSERT INTO Field (segment_id, name, regex , position)
VALUES
    (1, 'gender', '' , 9);



INSERT INTO Field (segment_id, name, regex, position )
VALUES -- Field 1: Sequence Number
(2, 'SpecimenID', '', 3),     -- Field 2: Specimen ID
(2, 'ordertimestamp', '', 8); -- Field 7: Order Timestamp
--(3, 'SpecimenType', '', 14);  -- Field 14: Specimen Type

INSERT INTO Field (segment_id, name, regex, position , component_index )
VALUES
    (3, 'SequenceNumber', '', 1 , 1 ), -- Field 1: Sequence Number
(3, 'TestType', '', 3 , 4),       -- Field 2: Test Type
(3, 'Value', '', 4, 1),          -- Field 3: Measured Value
(3, 'Units', '', 5 , 1),          -- Field 4: Units of Measurement
--(4, 'Normality', '', 7),      -- Field 5: Normality (N, L, H)
(3, 'status', '', 13 , 1);-- Field 6: Timestamp of the result
