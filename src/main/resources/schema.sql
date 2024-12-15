CREATE TABLE Parser_Entity (
                              id SERIAL PRIMARY KEY,
                              name VARCHAR(100) NOT NULL,
                              segment_delimiter VARCHAR(50) NOT NULL,
                              component_delimiter VARCHAR(10)  NULL,
                              sub_component_delimiter VARCHAR(10) NULL ,
                              escape_character VARCHAR(10) NULL,
                              repetition_delimiter VARCHAR(10) NULL,
                              use_header_delimiters VARCHAR(1) default 'N',
                              created_at TIMESTAMP DEFAULT NOW(),
                              updated_at TIMESTAMP DEFAULT NOW()
);
CREATE TABLE Segment (
                         id SERIAL PRIMARY KEY,
                         parser_id INT NOT NULL,
                         name VARCHAR(100) NOT NULL,
                         regex VARCHAR(255) NOT NULL,
                         multi_line BOOLEAN DEFAULT FALSE,
                         created_at TIMESTAMP DEFAULT NOW(),
                         updated_at TIMESTAMP DEFAULT NOW(),
                         FOREIGN KEY (parser_id) REFERENCES Parser_Entity(id) ON DELETE CASCADE
);
CREATE TABLE Field (
                       id SERIAL PRIMARY KEY,
                       segment_id INT NOT NULL,
                       name VARCHAR(100) NOT NULL,
                       regex VARCHAR(255) NOT NULL,
                       position INT NOT NULL,
                       component_index INT DEFAULT NULL,
                       sub_component_index INT DEFAULT NULL,
                       created_at TIMESTAMP DEFAULT NOW(),
                       updated_at TIMESTAMP DEFAULT NOW(),
                       FOREIGN KEY (segment_id) REFERENCES Segment(id) ON DELETE CASCADE
);

CREATE TABLE Lab_Device (
                           id SERIAL  PRIMARY KEY,
                           device_id VARCHAR(255) NOT NULL,
                           port INT NOT NULL,
                           name VARCHAR(255),
                           parser_type VARCHAR(255) NOT NULL
);
